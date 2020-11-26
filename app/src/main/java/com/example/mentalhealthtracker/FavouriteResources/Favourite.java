package com.example.mentalhealthtracker.FavouriteResources;

public class Favourite {
    // attributes
    private int FavouriteNumber;
    private String Resource;

    // methods
    public Favourite() {}

    public Favourite(int favouriteNumber, String resource) {
        FavouriteNumber = favouriteNumber;
        Resource = resource;
    }

    public int getFavouriteNumber() {
        return FavouriteNumber;
    }

    public void setFavouriteNumber(int favouriteNumber) {
        FavouriteNumber = favouriteNumber;
    }

    public String getResource() {
        return Resource;
    }

    public void setResource(String resource) {
        Resource = resource;
    }
}
