package com.example.furryfound;

import java.util.Date;

public class NotificationItem {

    private int remarks;
    private String petName;
    private String evaluation;
    private String shelter_name;
    private String profile_picture;
    private String message;
    private String feedback;
    private String application_id;
    private int isRead;
    private Date latestDate;
    private String dateApproved;
    private String dateDisapproved;
    private String dateCancelled;
    private String dateConfirmationSent;

    public NotificationItem(String shelter_name, String profile_picture, String message, String application_id) {
        this.shelter_name = shelter_name;
        this.profile_picture = profile_picture;
        this.message = message;
        this.application_id = application_id;
    }

    public NotificationItem(String message) {
    }

    public NotificationItem() {

    }

    // Getters
    public int getRemarks() {
        return remarks;
    }

    public String getPetName() {
        return petName;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public String getShelterName() {
        return shelter_name;
    }

    public String getShelterProfilePictureUrl() {
        return profile_picture;
    }

    public String getMessage() {
        return message;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getApplicationId() {
        return application_id;
    }

    // Setters
    public void setRemarks(int remarks) {
        this.remarks = remarks;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public void setShelterName(String shelter_name) {
        this.shelter_name = shelter_name;
    }

    public void setShelterProfilePictureUrl(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setApplicationId(String application_id) {
        this.application_id = application_id;
    }


    public String getFirstLineOfMessage() {
        if (message != null && !message.isEmpty()) {
            return message.split("\n", 2)[0];
        } else {
            return ""; // or some default message
        }
    }




    // Add getters and setters for new fields
    public int isRead() {
        return isRead;
    }

    public void setRead(int isRead) {
        this.isRead = isRead;
    }

    public Date getLatestDate() {
        return latestDate;
    }

    public void setLatestDate(Date latestDate) {
        this.latestDate = latestDate;
    }

    public String getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(String dateApproved) {
        this.dateApproved = dateApproved;
    }

    public String getDateDisapproved() {
        return dateDisapproved;
    }

    public void setDateDisapproved(String dateDisapproved) {
        this.dateDisapproved = dateDisapproved;
    }

    public String getDateCancelled() {
        return dateCancelled;
    }

    public void setDateCancelled(String dateCancelled) {
        this.dateCancelled = dateCancelled;
    }

    public String getDateConfirmationSent() {
        return dateConfirmationSent;
    }

    public void setDateConfirmationSent(String dateConfirmationSent) {
        this.dateConfirmationSent = dateConfirmationSent;
    }

}
