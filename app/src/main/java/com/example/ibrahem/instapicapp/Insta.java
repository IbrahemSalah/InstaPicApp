package com.example.ibrahem.instapicapp;

/**
 * Created by Eng_I on 11/20/2017.
 */

public class Insta {
    private String title;
    private String desc;
    private String image;
    private String username;

    public Insta() {

    }

    public Insta(String title, String desc, String image, String username) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}

