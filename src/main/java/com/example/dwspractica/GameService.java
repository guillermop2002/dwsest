package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;
    @Lazy
    @Autowired
    UserService userService;

    public void addGame(Game game) {
        gameRepository.save(game);
    }

    public void deleteGame(long n) {
        if (gameRepository.existsById(n)){
            userService.deleteSameOrders(n);
            gameRepository.deleteById(n);
        }
    }

    public Game getGames(long n) {
        return gameRepository.findById(n).orElse(null);
    }

    public Collection<Game> getGames() {
        return gameRepository.findAll();
    }

    public void updateGame(long id, Game game) {
        if (gameRepository.existsById(id)){
            game.setId(id);
            gameRepository.save(game);
        }
    }

    public boolean containsGame(long n) {
        return gameRepository.existsById(n);
    }
}
