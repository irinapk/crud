package springboot.crud.dao;
import springboot.crud.model.User;

import java.util.List;

public interface UserDao {

    void saveUser(User user);

    List<User> getAllUsers();

    void removeUserById(int id);

    void update(User newUser);

    User getUserById(int id);

    User getUserByName (String username);

}


