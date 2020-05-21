package by.artbax.mvc.app;

import by.artbax.mvc.dao.UsersDao;
import by.artbax.mvc.dao.UsersDaoJdbcTemplateImpl;
import by.artbax.mvc.models.User;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("Admin");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/Java_DB");


        UsersDao usersDao = new UsersDaoJdbcTemplateImpl(dataSource);

        List<User> users = usersDao.findAll();
        System.out.println(users);
    }
}
