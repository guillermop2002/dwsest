package com.example.dwspractica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    GameService gameService;

    public void addRating(long idGame, Rating rating) {
        if (gameService.containsGame(idGame)){
            rating.setGame(gameService.getGames(idGame));
            ratingRepository.save(rating);
        }
    }

    public void deleteRating(long idGame, long idRating) {
        if (gameService.containsGame(idGame)){
            if (ratingRepository.existsById(idRating)){
                ratingRepository.deleteById(idRating);
            }
        }
    }

    public Collection<Rating> getRatings(long idGame) {
        if (gameService.containsGame(idGame)){
            return ratingRepository.findBygame(gameService.getGames(idGame));
        }else{
            return null;
        }
    }
    public Rating getRatings(long idGame, long id){
        if (gameService.containsGame(idGame)){
            return ratingRepository.findById(id);
        }else{
            return null;
        }
    }

    public boolean containsRating(long idGame, long id) {
        if(gameService.containsGame(idGame)){
            return ratingRepository.existsById(id);
        }else{
            return false;
        }
    }
}
