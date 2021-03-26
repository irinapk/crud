package springboot.crud.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role_name", unique = true)
    private String role;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users;

    public Role() {

    }

    public Role(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public Role(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        if (this.id == 1) {
            return "ADMIN";
        } else if (this.id == 2) {
            return "USER";
        } else return this.role;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (((Integer)id == null) ? 0 : ((Integer)id).hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        if ((Integer)id == null) {
            if ((Integer)other.id != null)
                return false;
        } else if (!((Integer)id).equals((Integer)other.id))
            return false;
        return true;
    }
}
