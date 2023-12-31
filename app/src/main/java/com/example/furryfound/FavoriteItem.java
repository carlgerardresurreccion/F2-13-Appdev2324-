package com.example.furryfound;

import com.google.firebase.database.PropertyName;

public class FavoriteItem {
    @PropertyName("pet_id")
    private String petID;
    @PropertyName("adopter_id")
    private String adopterID;
    @PropertyName("favorite_id")
    private String favoriteID;
    public FavoriteItem(String favoriteID, String petID, String adopterID) {
        this.favoriteID = favoriteID;
        this.petID = petID;
        this.adopterID = adopterID;
    }

    public FavoriteItem() {

    }

    public String getFavoriteID() {
        return favoriteID;
    }

    public void setFavoriteID(String favoriteID) {
        this.favoriteID = favoriteID;
    }
    @PropertyName("pet_id")
    public String getPetID() {
        return petID;
    }

    @PropertyName("pet_id")
    public void setPetID(String petID) {
        this.petID = petID;
    }
    @PropertyName("adopter_id")

    public String getAdopterID() {
        return adopterID;
    }
    @PropertyName("adopter_id")

    public void setAdopterID(String adopterID) {
        this.adopterID = adopterID;
    }
}
