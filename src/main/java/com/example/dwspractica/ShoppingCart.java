package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class ShoppingCart {

    @Autowired
    GameService gameService;

    private float price = 0;
    private Map<Long, Game> games = new ConcurrentHashMap<>();


    public float getPrecio() {
        return games.values().stream().map(Game::getPrice).reduce(Float::sum).orElse((float) 0);
    }

    public void addGame(Game game) {
        games.put(game.getId(), game);
        this.price += game.getPrice();
    }

    public void deleteGame(long id) {
        if (this.containsGame(id)){
            Game aux = gameService.getGames(id);
            games.remove(id);
            this.price -= aux.getPrice();
        }
    }

    public void clearCart() {
        this.games.clear();
        this.price = 0;
    }

    public boolean esVacia() {
        return this.games.isEmpty();
    }

    public Collection<Game> getCart() {
        return this.games.values();
    }

    public void updateCart(Game game) {
        if (this.containsGame(game.getId())){
            games.put(game.getId(), game);
        }
    }
    public boolean containsGame(long id) {
        return this.games.containsKey(id);
    }
}
