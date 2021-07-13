package jm.SpringBoot.controllers;


import jm.SpringBoot.dao.UserDao;
import jm.SpringBoot.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/user")
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserController( UserDetailsServiceImpl userDetailsService ) {
        this.userDetailsService = userDetailsService;

    }

    @GetMapping()
    public String getCurrentUser(Model model ){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        model.addAttribute("users", userDetailsService.loadUserByUsername(username));
        return "user";
    }

}
