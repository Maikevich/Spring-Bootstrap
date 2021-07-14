package jm.SpringBoot.controllers;

import jm.SpringBoot.dao.UserDao;
import jm.SpringBoot.model.Role;
import jm.SpringBoot.model.User;
import jm.SpringBoot.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDao userService;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AdminController(UserDao userService, UserDetailsServiceImpl userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public String getUsers(Principal principal, Model model) {
        model.addAttribute("user", userDetailsService.loadUserByUsername(principal.getName()));
        Iterable<User> user = userService.findAll();
        model.addAttribute("users", user);
        return "index";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") long id, Model model) {
        Optional<User> userById = userService.findById(id);
        ArrayList<User> users = new ArrayList<>();
        userById.ifPresent(users::add);
        User user = new User();
        user = users.get(0);
        model.addAttribute("user", user);
        return "show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    //
    @PostMapping
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @ModelAttribute("role") String my_role) {
        if (bindingResult.hasErrors()) {
            return "new";
        } else {
            Role role = new Role((my_role.equals("ADMIN") ? 1L : 2L), "ROLE" + my_role);
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            userService.save(user);
            return "redirect:/admin";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        Optional<User> userById = userService.findById(id);
        ArrayList<User> users = new ArrayList<>();
        userById.ifPresent(users::add);
        User user = new User();
        user = users.get(0);
        model.addAttribute("user", user);
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "edit";
        } else {
            if (user.getId() != 0) {
                user.setId(id);
                userService.save(user);
                return "redirect:/admin";
            } else {
                userService.save(user);
                return "redirect:/admin";
            }
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        Optional<User> userById = userService.findById(id);
        ArrayList<User> users = new ArrayList<>();
        userById.ifPresent(users::add);
        User user = new User();
        user = users.get(0);
        userService.delete(user);
        return "redirect:/admin";
    }


}
