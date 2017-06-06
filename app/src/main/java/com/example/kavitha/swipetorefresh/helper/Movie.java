package com.example.kavitha.swipetorefresh.helper;

/**
 * Created by Kavitha on 06-06-2017.
 */

public class Movie {

    public int id;
    public String title;

    public Movie(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
