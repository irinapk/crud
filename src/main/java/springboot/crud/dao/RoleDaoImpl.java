package springboot.crud.dao;

import org.springframework.stereotype.Repository;
import springboot.crud.model.Role;
import springboot.crud.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void saveRole (Role role) {
        em.persist(role);
    }

    @Transactional
    @Override
    public void assignRole (User user, Role role) {
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        em.merge(user);
    }

    @Transactional
    @Override
    public void deleteRole (int id) {
        em.remove(em.find(Role.class, id));
    }

    @Override
    public Set<Role> showAllRoles() {
        return new HashSet<>(em.createQuery("from Role", Role.class).getResultList());
    }

    @Override
    public Role findRoleByName (String name) {
        Role role = em.createQuery(
                "SELECT r from Role r WHERE r.role = :name", Role.class).
                setParameter("name", name).getSingleResult();
        return role;
    }

    @Override
    public Role findRoleById (int id) {
        Role role = em.createQuery(
                "SELECT r from Role r WHERE r.id = :id", Role.class).
                setParameter("id", id).getSingleResult();
        return role;
    }

}
