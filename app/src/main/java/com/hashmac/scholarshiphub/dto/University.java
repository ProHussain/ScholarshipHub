package com.hashmac.scholarshiphub.dto;

public class University {
    private String title;
    private String number;
    private String image;

    public University(String title, String number, String image) {
        this.title = title;
        this.number = number;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getNumber() {
        return number;
    }

    public String getImage() {
        return image;
    }
}
