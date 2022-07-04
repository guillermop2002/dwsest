package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class UserRESTController {
    @Autowired
    UserService userService;

    @PostMapping("/newUser")
    public ResponseEntity<User>newUser(@RequestBody SingUp singUp){
        User aux=new User(singUp.getNombre(), singUp.getPassword(), "USER");
        userService.addUser(aux);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/showUsers")
    public ResponseEntity<Collection<User>>showUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
    @DeleteMapping("/deleteUser/{id_usuario}")
    public ResponseEntity<User>deleteUser(@PathVariable int id_usuario){
        userService.deleteUser(id_usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
