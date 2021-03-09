package springboot.crud.service;

import springboot.crud.model.Role;
import springboot.crud.model.User;

import java.util.Set;

public interface RoleService {

    public void assignRole (User user, Role role);

    public void saveRole (Role role);

    public void deleteRole (int id);

    public Set<Role> showAllRoles ();

    public Role findRoleByName (String name);

    Role findRoleById(int id);

}
