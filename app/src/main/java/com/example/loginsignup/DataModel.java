package com.example.loginsignup;

public class DataModel {

    private String name;
    private String family;
    private String description;
    private int image;
    private int id_;

    // Constructor with all 4 fields
    public DataModel(String name, String family, String description, int image, int id_) {
        this.name = name;
        this.family = family;
        this.description = description;
        this.image = image;
        this.id_ = id_;
    }

    // Getters for each field
    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public int getId_() {
        return id_;
    }
}
