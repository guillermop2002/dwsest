package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class GameController {
    @Autowired
    GameService gameService;
    @Autowired
    ShoppingCart shoppingCart;
    @Autowired
    RatingService ratingService;
    @Autowired
    UserService userService;
    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        userService.addUser(new User("Derith", "DerithPass", "USER"));
        userService.addUser(new User("TotalIx", "AlexPass", "USER"));
        userService.addUser(new User("admin", "admin", "ADMIN"));
        gameService.addGame(new Game("Elden Ring", "PC", 60));
        gameService.addGame(new Game("God of War", "PlayStation", 50));
        gameService.addGame(new Game("Red Dead Redemption 2", "PlayStation", 70));
        ratingService.addRating(1, new Rating(5, "Juegazo", "La libertad al crear el personaje hace que las distintas builds le den variedad al juego", 1));
        ratingService.addRating(1, new Rating(5, "El mejor juego que he jugado", "La dificultad de los bosses hace de esto un desafío a la altura. ",2));
        ratingService.addRating(2, new Rating(3, "Decente", "No está mal, pero se nota que se ha perdido un poco la esencia del juego. Prefiero el nivel de la historia y el personaje de Kratos de entregas anteriores",1));
        ratingService.addRating(2, new Rating(4, "Muy bueno", "Escenas épicas y QTE muy bien usados",1));
        ratingService.addRating(3, new Rating(4, "Increible", "La libertad que te da el juego lo hace muy variado y divertido",2));
        ratingService.addRating(3, new Rating(5, "Maravilloso", "Los NPCs se comportan como personas de verdad, muy realista",2));
    }

    @GetMapping("/addGame")
    public String addGame() {
        return "NewGame";
    }

    @PostMapping("/addedGame")
    public String newGame(@RequestParam String name, @RequestParam String platform, @RequestParam float price) {
        gameService.addGame(new Game(name, platform, price));
        return "CreatedGame";
    }

    @GetMapping("/allGames")
    public String allGames(Model model) {
        model.addAttribute("games", gameService.getGames());
        return "ShowGames";
    }

    @GetMapping("/gameDetails/{idGame}")
    public String showGame(Model model, @PathVariable int idGame, HttpServletRequest request) {
        if (gameService.containsGame(idGame)) {
            model.addAttribute("game", gameService.getGames(idGame));
            model.addAttribute("user", request.isUserInRole("USER"));
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            return "ShowGame";
        }else{
            return "error/404";
        }
    }

    @GetMapping("/deleted/{idGame}")
    public String deleteGame(@PathVariable int idGame) {
        if (gameService.containsGame(idGame)){
            shoppingCart.deleteGame(idGame);
            gameService.deleteGame(idGame);
            return "DeletedGame";
        }else{
            return "error/404";
        }
    }

    @GetMapping("/update/{idGame}")
    public String updateGame(Model model, @PathVariable int idGame) {
        if (gameService.containsGame(idGame)){
            model.addAttribute("game", gameService.getGames(idGame));
            return "updateGame";
        }else{
            return "error/404";
        }
    }

    @GetMapping("/updated/{idGame}")
    public String updated(@RequestParam String name, @RequestParam String platform, @RequestParam float price, @PathVariable int idGame) {
        Game aux = new Game(name, platform, price);
        aux.setId(idGame);
        gameService.updateGame(idGame, aux);
        shoppingCart.updateCart(aux);
        return "updated";
    }
    @PostMapping("/filteredGames")
    public String filteredGames(Model model, @RequestParam String platform, @RequestParam float minprice, @RequestParam float maxprice){
        if (platform.equals("Any")){
            TypedQuery<Game>q1=entityManager.createQuery("SELECT g FROM Game g WHERE g.price BETWEEN ?1 AND ?2", Game.class);
            q1.setParameter(1, minprice);
            q1.setParameter(2, maxprice);
            List<Game>aux=q1.getResultList();
            model.addAttribute("games", aux);
            return "FilteredGames";
        }else{
            TypedQuery<Game>q2=entityManager.createQuery("SELECT g FROM Game g WHERE g.platform=?3 AND g.price BETWEEN ?1 AND ?2", Game.class);
            q2.setParameter(1, minprice);
            q2.setParameter(2, maxprice);
            q2.setParameter(3, platform);
            List<Game>aux2=q2.getResultList();
            model.addAttribute("games", aux2);
            return "FilteredGames";
        }
    }
}
