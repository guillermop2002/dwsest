package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    UserService userService;
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("user", request.isUserInRole("USER"));
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        return "index";
    }
    @GetMapping("/login")
    public String login(){return "Login";}
    @GetMapping("/loginerror")
    public String loginerror(){
        return "LoginError";
    }
    @GetMapping("/logout")
    public String logout(){
        return "Logout";
    }
    @GetMapping("/register")
    public String register(){
        return "Register";
    }
    @PostMapping("/registered")
    public String registered(@RequestParam String username, @RequestParam String password){
        if (!username.equals("") && !password.equals("")){
            if (!userService.usernameAlreadyExists(username)){
                userService.addUser(new User(username, password, "USER"));
                return "Registered";
            }else{
                return "UAE";
            }
        }
       else{
           return "error/400";
        }
    }
}
