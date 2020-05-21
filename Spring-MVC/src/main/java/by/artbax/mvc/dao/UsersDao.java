package by.artbax.mvc.dao;

import by.artbax.mvc.models.User;

import java.util.List;

public interface UsersDao extends CrudDao<User> {
    List<User> findAllByFirstName(String firstName);
}
