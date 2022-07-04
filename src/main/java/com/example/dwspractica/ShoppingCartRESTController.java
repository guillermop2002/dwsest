package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ShoppingCartRESTController {

    @Autowired
    GameService gameService;
    @Autowired
    ShoppingCart shoppingCart;
    @Autowired
    UserService userService;

    // @ResponseStatus(value = HttpStatus.BAD_REQUEST) public @ResponseBody String handleException() { return "error/400"; }

    @GetMapping("/shoppingCart")
    public ResponseEntity<Collection<Game>> showAll() {
        return new ResponseEntity<>(shoppingCart.getCart(), HttpStatus.OK);
    }

    @PostMapping("/addToCart/{idGame}")
    public ResponseEntity<Game> addToCart(@PathVariable int idGame) {
        if (!gameService.containsGame(idGame)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            shoppingCart.addGame(gameService.getGames(idGame));
            return new ResponseEntity<>(gameService.getGames(idGame), HttpStatus.OK);
        }
    }

    @DeleteMapping("/remove/{idGame}")
    public ResponseEntity<Game> delete(@PathVariable int idGame) {
        if (!shoppingCart.containsGame(idGame)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            shoppingCart.deleteGame(idGame);
            return new ResponseEntity<>(gameService.getGames(idGame), HttpStatus.OK);
        }
    }

    @DeleteMapping("/removeAllCart")
    public ResponseEntity<Game> removeAll() {
        shoppingCart.clearCart();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/makeOrder")
    public ResponseEntity<Collection<Game>>makeOrder(HttpServletRequest request){
        List<Game>cart=new ArrayList<>(shoppingCart.getCart());
        userService.makeOrder(userService.getIdFromName(request.getUserPrincipal().getName()));
        shoppingCart.clearCart();
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @GetMapping("/myOrders")
    public ResponseEntity<Collection<Game>>showOrders(HttpServletRequest request){
        return new ResponseEntity<>(userService.showOrders(userService.getIdFromName(request.getUserPrincipal().getName())), HttpStatus.OK);
    }
    @DeleteMapping("/deleteOrder/{idGame}")
    public ResponseEntity<Game>deleteOrder(@PathVariable int idGame, HttpServletRequest request){
        if (gameService.containsGame(idGame)){
            userService.deleteOrder(userService.getIdFromName(request.getUserPrincipal().getName()), idGame);
            return new ResponseEntity<>(gameService.getGames(idGame), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
