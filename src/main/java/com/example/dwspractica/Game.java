package com.example.dwspractica;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idGame;

    private String name;
    private String platform;
    private float price;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<Rating>ratings;

    @JsonIgnore
    @ManyToMany(mappedBy = "juegosPedidos")
    List<User>usuarios;

    public Game(String name, String platform, float price) {
        this.name = name;
        this.platform = platform;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getId() {
        return idGame;
    }

    public void setId(long id) {
        this.idGame = id;
    }

    public long getIdGame() {
        return idGame;
    }

    public void setIdGame(long idGame) {
        this.idGame = idGame;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<User> usuarios) {
        this.usuarios = usuarios;
    }
}
