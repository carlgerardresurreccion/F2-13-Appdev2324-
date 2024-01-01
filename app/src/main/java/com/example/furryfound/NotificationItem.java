package com.example.furryfound;

public class NotificationItem {
    private String shelter_name;
    private String profile_picture;
    private String message;

    public NotificationItem(String shelter_name, String profile_picture, String message) {
        this.shelter_name = shelter_name;
        this.profile_picture = profile_picture;
        this.message = message;
    }

    public NotificationItem(String message) {
    }

    // Getters
    public String getShelterName() {
        return shelter_name;
    }

    public String getShelterProfilePictureUrl() {
        return profile_picture;
    }

    public String getMessage() {
        return message;
    }

    public String getFirstLineOfMessage() {
        return message.split("\n", 2)[0]; // Splits the feedback by newline and returns the first line.
    }
}
