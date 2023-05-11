package com.freelancer.joblisting.creation.model;

import java.io.Serializable;

public class JobInfoModel implements Serializable {
    public String title;
    public String description;
    public String phone;
    public String city;
    public String basePrice;
    public String category;
    public String radius;
    public String jobLocation;

    public JobInfoModel(String title, String description, String phone, String city, String basePrice, String category, String radius, String jobLocation) {
        this.title = title;
        this.description = description;
        this.phone = phone;
        this.city = city;
        this.basePrice = basePrice;
        this.category = category;
        this.radius = radius;
        this.jobLocation = jobLocation;
    }

    public JobInfoModel() {
        this.title = "";
        this.description = "";
        this.phone = "";
        this.city = "";
        this.basePrice = "";
        this.category = "";
        this.radius = "";
        this.jobLocation = "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "JobListingModel{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", basePrice='" + basePrice + '\'' +
                ", category='" + category + '\'' +
                ", radius='" + radius + '\'' +
                ", jobLocation='" + jobLocation + '\'' +
                '}';
    }
}