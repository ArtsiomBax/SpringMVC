package by.artbax.mvc.dao;

import by.artbax.mvc.models.Car;
import by.artbax.mvc.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class UsersDaoJdbcTemplateImpl implements UsersDao {

    private JdbcTemplate template;
    private NamedParameterJdbcTemplate namedTemplate;
    private Map<Long, User> usersMap = new HashMap<>();

    //language=SQL
    private final String SQL_SELECT_ALL_BY_FIRST_NAME = "SELECT * FROM java_user WHERE first_name = ?";

    //language=SQL
    private final String SQL_SELECT_USER_WITH_CAR
            = "SELECT java_user.*, user_car.id as car_id, user_car.model FROM java_user LEFT JOIN user_car " +
            "ON java_user.id = user_car.owner_id WHERE java_user.id = ?";

    //language=SQL
    private final String SQL_SELECT_USERS_WITH_CARS =
            "SELECT java_user.*, user_car.id as car_id, user_car.model FROM java_user LEFT JOIN user_car ON java_user.id = user_car.owner_id";

    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "SELECT * FROM java_user WHERE id = :id";

    //language=SQL
    private final String SQL_INSERT_USER =
            "INSERT INTO java_user(first_name, last_name) VALUES (:firstName, :lastName)";

    @Autowired
    public UsersDaoJdbcTemplateImpl(DataSource dataSource) {

        this.template = new JdbcTemplate(dataSource);
        this.namedTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapperWithOutCars = (resultSet, i) -> {
        return User.builder().id(resultSet.getLong("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name")).build();
    };

    private RowMapper<User> userRowMapper = (resultSet, i) -> {
        Long id = resultSet.getLong("id");
        if (!usersMap.containsKey(id)) {

            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");

            User user = new User(id, firstName, lastName, new ArrayList<>());
            usersMap.put(id, user);
        }
        Car car = new Car(resultSet.getLong("car_id"),
                resultSet.getString("model"),
                usersMap.get(id));
        usersMap.get(id).getCars().add(car);
        return usersMap.get(id);

    };

    @Override
    public List<User> findAllByFirstName(String firstName) {
        return template.query(SQL_SELECT_ALL_BY_FIRST_NAME, userRowMapperWithOutCars, firstName);
    }

    @Override
    public Optional<User> find(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<User> result = namedTemplate.query(SQL_SELECT_BY_ID, params, userRowMapperWithOutCars);

        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));


    }

    @Override
    public void save(User model) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", model.getFirstName());
        params.put("lastName", model.getLastName());
        namedTemplate.update(SQL_INSERT_USER, params);
    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<User> findAll() {
        List<User> result = template.query(SQL_SELECT_USERS_WITH_CARS, userRowMapper);
        usersMap.clear();
        return result;
    }
}
