package springboot.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.crud.dao.UserDao;
import springboot.crud.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl (UserDao userDao) { this.userDao = userDao; }

    @Transactional
    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public void removeUserById(int id) {
        userDao.removeUserById(id);
    }

    @Transactional
    @Override
    public void update(User newUser) {
        userDao.update(newUser);
    }

    @Transactional
    @Override
    public User show(int id) {
        return userDao.show(id);
    }
}
