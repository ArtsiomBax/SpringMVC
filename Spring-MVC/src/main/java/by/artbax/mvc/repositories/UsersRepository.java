package by.artbax.mvc.repositories;

import by.artbax.mvc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long> {
    List<User> findAllByFirstName(String firstName);
}
