package springboot.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springboot.crud.model.Role;
import springboot.crud.model.User;
import springboot.crud.service.RoleService;
import springboot.crud.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/")
    public String getLoginPage() {
        return "login";
    }

    // NEW ADMIN PAGE
    @GetMapping("/admin")
    public String viewTestPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("updateUser", new User());
        Set<Role> roles = roleService.showAllRoles();
        model.addAttribute("allRoles", roles);
        return "users/admin-rest";
    }

    // FORMER ADMIN PAGE
//    @GetMapping("/admin")
//    public String viewAdminPage(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//        model.addAttribute("user", new User());
//        model.addAttribute("updateUser", new User());
//        Set<Role> roles = roleService.showAllRoles();
//        model.addAttribute("allRoles", roles);
//        return "users/admin";
//    }

    @PostMapping("/admin")
    public String createNewUser (@ModelAttribute("user") @Valid User user,
                                 @RequestParam(value="roleID") int[] roleID,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/admin";
        }
        Set<Role> roleSet = new HashSet<>();
        for (int i = 0; i < roleID.length; i++) {
            roleSet.add(roleService.findRoleById(roleID[i]));
        }
        user.setRoles(roleSet);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin")
    public String deleteUser(@RequestParam("userID") String userID) {
        int id = Integer.valueOf(userID);
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/{id}")
    public String showUserToUpdate (@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "redirect:/admin";
    }

    @PostMapping ("/admin/{id}")
    public String updateUser (@ModelAttribute("user") @Valid User user,
                         @RequestParam(value="roleID") int[] roleID,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "users/admin";
        }
        Set<Role> roleSet = new HashSet<>();
        for (int i = 0; i < roleID.length; i++) {
            roleSet.add(roleService.findRoleById(roleID[i]));
        }
        user.setRoles(roleSet);
        userService.update(user);
        return "redirect:/admin";
    }


    //ADMIN USER VIEW PAGE
    @GetMapping("user/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/show";
    }

    //CREATING A NEW USER
    @GetMapping("user/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        Set<Role> roles = roleService.showAllRoles();
        model.addAttribute("allRoles", roles);
        return "users/new";
    }

    @PostMapping("user/new")
    public String create(@ModelAttribute("user") @Valid User user,
                         @RequestParam(value="roleID") int[] roleID,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        Set<Role> roleSet = new HashSet<>();
        for (int i = 0; i < roleID.length; i++) {
           roleSet.add(roleService.findRoleById(roleID[i]));
        }
        user.setRoles(roleSet);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    // EDITING THE USER
    @GetMapping("user/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        Set<Role> roles = roleService.showAllRoles();
        model.addAttribute("allRoles", roles);
        return "users/edit";
    }

    @PatchMapping("user/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         @RequestParam(value="roleID") int[] roleID,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        Set<Role> roleSet = new HashSet<>();
        for (int i = 0; i < roleID.length; i++) {
            roleSet.add(roleService.findRoleById(roleID[i]));
        }
        user.setRoles(roleSet);
        userService.update(user);
        return "redirect:/admin";
    }

    //DELETING THE USER
    @DeleteMapping("user/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    //USER PAGE VIEW FOR NON-ADMINS
    @GetMapping("/user")
    public String viewUserInfo(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        springboot.crud.model.User user = userService.getUserByName(username);
        model.addAttribute("user", user);
        return "users/user";
    }
}
