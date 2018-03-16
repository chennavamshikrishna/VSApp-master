package com.vsapp.petcare.datamodels;

/**
 * Created by venkat on 23/1/18.
 */

public class DogList {
    String dogName;
    String dogImage;
    String description;

    public DogList() {
        super();
    }

    public DogList(String dogName, String dogImage, String description) {
        this.dogName = dogName;
        this.dogImage = dogImage;
        this.description = description;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getDogImage() {
        return dogImage;
    }

    public void setDogImage(String dogImage) {
        this.dogImage = dogImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
