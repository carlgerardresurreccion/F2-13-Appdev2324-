package com.example.furryfound;

public class PetItem {
    private String imageUrl;
    private String description;

    public PetItem(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public PetItem() {

    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}
