package com.ufrpe.hmenon.user.domain;


import com.ufrpe.hmenon.favourite.FavouritePoint;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;

import java.util.ArrayList;

public class User {
    private long id;
    private String email;
    private String name;
    private String password;
    private ArrayList<FavouritePoint> favourites = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    public ArrayList<FavouritePoint> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<FavouritePoint> favourites) {
        this.favourites = favourites;
    }
    public void addFavourite(TouristicPoint point){
        FavouritePoint favouritePoint = new FavouritePoint();
        favouritePoint.setPoint(point);
        favourites.add(favouritePoint);
    }
    public void removeFavourite(TouristicPoint point){
        FavouritePoint toRemove = null;
        for (FavouritePoint favouritePoint : favourites){
            if (favouritePoint.getPoint().getId().equals(point.getId())){
                toRemove = favouritePoint;
            }
        }
        if (toRemove != null){
            favourites.remove(toRemove);
        }
    }

    public boolean isFavourite(String name){
        boolean has = false;
        for (FavouritePoint point : favourites){
            if (point.getPoint().getName().equals(name)){
                has = true;
            }
        }
        return has;
    }
}
