package springboot.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.crud.model.Role;
import springboot.crud.model.User;
import springboot.crud.service.RoleService;
import springboot.crud.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MyRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MyRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> listUsers() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // required JSON body input: { "name": "Name",
    //        "lastName": "Last Name",
    //        "age": 99,
    //        "email": "mail@gmail.com",
    //        "password": "pass",
    //        "roles" : 1 ,2 }

    @PostMapping("/users/save")
    public User addNewUser(@RequestBody Map<String, String> jsonMap) {
        User user = new User();
        Set<Role> roles = new HashSet<>();
        user.setName(jsonMap.get("name"));
        user.setLastName(jsonMap.get("lastName"));
        user.setAge(Integer.parseInt(jsonMap.get("age")));
        user.setEmail(jsonMap.get("email"));
        user.setPassword(jsonMap.get("password"));
        String[] roleID = (jsonMap.get("roles")).split(",");
        for (String s : roleID) {
            roles.add(roleService.findRoleById(Integer.parseInt(s)));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return user;
    }

    @PostMapping ("/users/update/{id}")
    public User updateUser (@PathVariable int id, @RequestBody Map<String, String> jsonMap) {
        User user = userService.getUserById(id);
        Set<Role> roles = new HashSet<>();
        user.setName(jsonMap.get("name"));
        user.setLastName(jsonMap.get("lastName"));
        user.setAge(Integer.parseInt(jsonMap.get("age")));
        user.setEmail(jsonMap.get("email"));
        user.setPassword(jsonMap.get("password"));
        String[] roleID = (jsonMap.get("roles")).split(",");
        for (String s : roleID) {
            roles.add(roleService.findRoleById(Integer.parseInt(s)));
        }
        user.setRoles(roles);
        userService.update(user);
        return user;
    }

    @DeleteMapping("/users/delete/{id}")
    public String deleteUser (@PathVariable int id) {
        userService.removeUserById(id);
        return "User " + id + " was successfully deleted.";
    }

}
