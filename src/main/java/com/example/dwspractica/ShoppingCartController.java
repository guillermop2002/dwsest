package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ShoppingCartController {

    @Autowired
    GameService gameService;
    @Autowired
    ShoppingCart shoppingCart;
    @Autowired
    UserService userService;

    @GetMapping("/addedToCart/{idGame}")
    public String newGameToCart(@PathVariable int idGame) {
        if (gameService.containsGame(idGame)){
            shoppingCart.addGame(gameService.getGames(idGame));
            return "AddedToCart";
        }else{
            return "error/404";
        }
    }

    @GetMapping("/shoppingCart")
    public String showShoppingCart(Model model) {

        model.addAttribute("cart", shoppingCart.getCart());
        float sum = shoppingCart.getPrecio();

        model.addAttribute("sum", sum);
        return "ShoppingCart";
    }

    @GetMapping("/RemoveGame/{idGame}")
    public String removeGameShoppingCart(@PathVariable int idGame) {
        if (gameService.containsGame(idGame)){
            if (shoppingCart.containsGame(idGame)){
                shoppingCart.deleteGame(idGame);
                return "RemovedGame";
            }else{
                return "error/404";
            }
        }else{
            return "error/404";
        }
    }

    @GetMapping("/RemoveShoppingCart")
    public String removeShoppingCart(Model model) {
        shoppingCart.clearCart();
        return "RemovedShoppingCart";
    }

    @GetMapping("/MadeOrder")
    public String madeOrder(HttpServletRequest request){
        userService.makeOrder(userService.getIdFromName(request.getUserPrincipal().getName()));
        shoppingCart.clearCart();
        return "CreatedOrder";
    }
    @GetMapping("/showOrders")
    public String showOrders(Model model, HttpServletRequest request){
        model.addAttribute("orders", userService.showOrders(userService.getIdFromName(request.getUserPrincipal().getName())));
        model.addAttribute("sum", userService.getUsers(userService.getIdFromName(request.getUserPrincipal().getName())).getPriceOrders());
        return "MyOrders";
    }
    @GetMapping("/deleteOrder/{idGame}")
    public String deleteOrder(@PathVariable int idGame, HttpServletRequest request){
        userService.deleteOrder(userService.getIdFromName(request.getUserPrincipal().getName()), idGame);
        return "DeletedOrder";
    }
}


