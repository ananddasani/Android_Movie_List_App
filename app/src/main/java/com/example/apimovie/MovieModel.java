package com.example.apimovie;

public class MovieModel {

    double rating;
    String title;
    String poster;
    String overView;

    public MovieModel(double rating, String title, String poster, String overView) {
        this.rating = rating;
        this.title = title;
        this.poster = poster;
        this.overView = overView;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }
}
