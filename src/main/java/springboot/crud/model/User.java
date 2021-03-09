package springboot.crud.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "User name cannot be empty")
    @Size(min = 2, max = 30, message = "Name should consist of 2 to 30 characters")
    @Column(name = "name", unique = true)
    private String name;

    @NotEmpty(message = "Lat name cannot be empty")
    @Size(min = 2, max = 30, message = "Last name should consist of 2 to 30 characters")
    @Column(name = "last_name")
    private String lastName;


    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 1, max = 30, message = "Password should contain at least one character")
    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_with_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    public User(int id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }
    public User(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User "+ getId() + ": " + getName() + " " + getLastName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles (Set<Role> roles) {
        this.roles = roles;
    }
}
