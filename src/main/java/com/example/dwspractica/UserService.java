package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GameService gameService;
    @Autowired
    ShoppingCart shoppingCart;

    public void addUser(User user){
        userRepository.save(user);
    }
    public void deleteUser(long n){
        userRepository.deleteById(n);
    }
    public User getUsers(long n){
        return userRepository.findById(n).orElse(null);
    }
    public Collection<User>getUsers(){
        return userRepository.findAll();
    }
    public void updateUser(long id, User user){
        if (userRepository.existsById(id)){
            user.setId_usuario(id);
            userRepository.save(user);
        }
    }
    public boolean containsUser(long id){
        return userRepository.existsById(id);
    }
    public void makeOrder(long u){
        List<Game>cart=new ArrayList<>(shoppingCart.getCart());
        User uaux=userRepository.getById(u);
        for (Game g:cart){
            uaux.getJuegosPedidos().add(g);
        }
        userRepository.save(uaux);
    }
    public void deleteOrder(long uid, long idGame){
        User u=userRepository.getById(uid);
        u.deleteOrder(gameService.getGames(idGame));
        userRepository.save(u);
    }
    public List<Game>showOrders(long u){
        if (userRepository.existsById(u)){
            User aux=userRepository.getById(u);
            return aux.getJuegosPedidos();
        }else return null;
    }
    public void deleteSameOrders(long idGame){
        List<User>aux=userRepository.findAll();
        for(User u:aux){
            u.deleteOrder(gameService.getGames(idGame));
            userRepository.save(u);
        }
    }
    public int getIdFromName(String name){
        User u=userRepository.findByNombre(name).orElse(null);
        return (int)u.getId_usuario();
    }
    public boolean usernameAlreadyExists(String name){
        return userRepository.existsByNombre(name);
    }
    public void changePassword(long uid, String password){
        User u=userRepository.getById(uid);
        u.setPassword(password);
        userRepository.save(u);
    }
}
