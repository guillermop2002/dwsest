package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/myProfile")
    public String myProfile(Model model, HttpServletRequest request){
        model.addAttribute("nombre", request.getUserPrincipal().getName());
        return "Profile";
    }
    @GetMapping("/deregister")
    public String deregister(HttpServletRequest request) throws ServletException {
        userService.deleteUser(userService.getIdFromName(request.getUserPrincipal().getName()));
        request.logout();
        return "Deregister";
    }
    @GetMapping("/changePassword")
    public String changePassword(){
        return "ChangePassword";
    }
    @PostMapping("/passwordChanged")
    public String passwordChanged(@RequestParam String password, HttpServletRequest request){
        userService.changePassword(userService.getIdFromName(request.getUserPrincipal().getName()), password);
        return "PasswordChanged";
    }
    @GetMapping("/manageUsers")
    public String manageUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        return "ManageUsers";
    }
    @GetMapping("/deleteUser/{id_usuario}")
    public String deleteUser(@PathVariable int id_usuario){
        userService.deleteUser(id_usuario);
        return "DeletedUser";
    }
}

