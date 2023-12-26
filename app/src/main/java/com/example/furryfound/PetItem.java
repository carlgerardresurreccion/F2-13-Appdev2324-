package com.example.furryfound;

import android.os.Build;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PetItem {
    private String name, color, type, status, imageUrl, description;
    private int age
    private long daysAtShelter;
    private float weight;
    private LocalDate arrivalDate;

    public PetItem() {
    }

    public PetItem(String name, String color, String type, String status, String imageUrl, String description, int age, float weight, LocalDate arrivalDate) {
        this.name = name;
        this.color = color;
        this.type = type;
        this.status = status;
        this.imageUrl = imageUrl;
        this.description = description;
        this.age = age;
        this.weight = weight;
        this.arrivalDate = arrivalDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getDaysAtShelter() {
        return daysAtShelter;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
        this.daysAtShelter = calculateDaysAtShelter();
    }

    private long calculateDaysAtShelter() {
        if (arrivalDate != null) {
            LocalDate currentDate = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                currentDate = LocalDate.now();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return ChronoUnit.DAYS.between(arrivalDate, currentDate);
            }
        }
        return 0;
    }
}
