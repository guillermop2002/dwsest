package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class GameRESTController {
    @Autowired
    GameService gameService;
    @Autowired
    ShoppingCart shoppingCart;

    @PostMapping("/addGame")
    public ResponseEntity<Game> create(@RequestBody Game game) {
        gameService.addGame(game);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @GetMapping("/games")
    public ResponseEntity<Collection<Game>> showAll() {
        return new ResponseEntity<>(gameService.getGames(), HttpStatus.OK);
    }

    @GetMapping("/game/{idGame}")
    public ResponseEntity<Game> showGame(@PathVariable int idGame) {
        Game aux = gameService.getGames(idGame);
        if (aux == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(aux, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{idGame}")
    public ResponseEntity<Game> delete(@PathVariable int idGame) {
        Game aux = gameService.getGames(idGame);
        if (aux == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            shoppingCart.deleteGame(idGame);
            gameService.deleteGame(idGame);
            return new ResponseEntity<>(aux, HttpStatus.OK);
        }
    }

    @PutMapping("/update/{idGame}")
    public ResponseEntity<Game> update(@PathVariable int idGame, @RequestBody Game game) {
        if (!gameService.containsGame(idGame)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            game.setId(idGame);
            gameService.updateGame(idGame, game);
            if (shoppingCart.containsGame(game.getId())) {
                shoppingCart.updateCart(game);
            }
            return new ResponseEntity<>(game, HttpStatus.OK);
        }

    }
}
