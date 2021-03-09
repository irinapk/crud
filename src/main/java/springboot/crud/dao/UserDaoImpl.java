package springboot.crud.dao;

import org.springframework.stereotype.Repository;
import springboot.crud.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final RoleDao roleDao;

    @PersistenceContext
    private EntityManager em;

    public UserDaoImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        em.persist(user);
    }

    @Transactional
    @Override
    public void removeUserById(int id) {
        em.remove(em.find(User.class, id));;
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    @Transactional
    public void update(User newUser) {
        em.merge(newUser);
    }

    @Override
    public User show(int id) {
        return em.find(User.class, id);
    }

    public User getUserByName (String username) {
        User user = em.createQuery(
                "SELECT u from User u WHERE u.name = :username", User.class).
                setParameter("username", username).getSingleResult();
        return user;
    }
}
