package com.example.furryfound;

import android.os.Build;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import android.os.Parcel;
import android.os.Parcelable;

public class PetItem implements Parcelable {
    private String name, color, type, imageUrl, description, dateArrived, gender, age, petID;
    private int daysAtShelter, status;
    private float weight;

    public PetItem() {

    }

    public PetItem(String age, String color, String dateArrived, int daysAtShelter, String description, String imageUrl, String gender, String name, String petID, int status, String type, int weight) {
        this.name = name;
        this.color = color;
        this.type = type;
        this.status = status;
        this.imageUrl = imageUrl;
        this.description = description;
        this.age = age;
        this.weight = weight;
        this.dateArrived = dateArrived;
        this.daysAtShelter = daysAtShelter;
        this.gender = gender;
        this.petID = petID;
    }

    public String getPetID() {
        return petID;
    }

    public void setPetID(String petID) {
        this.petID = petID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDaysAtShelter(int daysAtShelter) {
        this.daysAtShelter = daysAtShelter;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

    public String getDateArrived() {
        return dateArrived;
    }

    public void setDateArrived(String dateArrived) {
        this.dateArrived = dateArrived;
    }

    protected PetItem(Parcel in) {
        name = in.readString();
        color = in.readString();
        type = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        dateArrived = in.readString();
        age = in.readString();
        daysAtShelter = in.readInt();
        status = in.readInt();
        weight = in.readFloat();
        gender = in.readString();
        petID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(color);
        dest.writeString(type);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeString(dateArrived);
        dest.writeString(age);
        dest.writeInt(daysAtShelter);
        dest.writeInt(status);
        dest.writeFloat(weight);
        dest.writeString(gender);
        dest.writeString(petID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PetItem> CREATOR = new Creator<PetItem>() {
        @Override
        public PetItem createFromParcel(Parcel in) {
            return new PetItem(in);
        }

        @Override
        public PetItem[] newArray(int size) {
            return new PetItem[size];
        }
    };
}