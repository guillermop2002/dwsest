package com.example.dwspractica;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int stars;
    private String title;
    private String comment;
    private long id_ur;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JoinColumn(name = "id_game")
    private Game game;

    public Rating(int stars, String title, String comment, long id_ur) {
        this.stars = stars;
        this.title = title;
        this.comment = comment;
        this.id_ur=id_ur;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public long getId_ur() {
        return id_ur;
    }

    public void setId_ur(long id_ur) {
        this.id_ur = id_ur;
    }
}
