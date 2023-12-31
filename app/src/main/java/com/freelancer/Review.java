package com.freelancer;

/*
this class is used for the attributes of a single review object
 */
public class Review {


    String name;
    float rating;
    String comment;

    int image;

    /**
     * returns the image ID that was set
     * @return
     */
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Review(String name, float rating, String comment, int image) {
        this.name = name;
        this.rating = rating;
        this.comment = comment;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
