package com.example.furryfound;

import android.os.Build;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.PropertyName;

public class PetItem implements Parcelable {
    private String name, breed, color, type, imageUrl, description, dateArrived, gender, age, weight;
    @PropertyName("pet_id")
    private String pet_id;
    private String shelter_id;
    private int daysAtShelter, status;

    public PetItem() {

    }

    public PetItem(String age, String color, String breed, String dateArrived, int daysAtShelter, String description, String imageUrl, String gender, String name, String pet_id, String shelter_id, int status, String type, String weight) {
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
        this.pet_id = pet_id;
        this.shelter_id = shelter_id;
        this.breed = breed;
    }

    public String getShelter_id() {
        return shelter_id;
    }

    public void setShelter_id(String shelter_id) {
        this.shelter_id = shelter_id;
    }

    @PropertyName("pet_id")
    public String getPetID() {
        return pet_id;
    }

    @PropertyName("pet_id")
    public void setPetID(String pet_id) {
        this.pet_id = pet_id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String  weight) {
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
        weight = in.readString();
        gender = in.readString();
        pet_id = in.readString();
        shelter_id = in.readString();
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
        dest.writeString(weight);
        dest.writeString(gender);
        dest.writeString(pet_id);
        dest.writeString(shelter_id);
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