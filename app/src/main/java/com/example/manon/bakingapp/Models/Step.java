package com.example.manon.bakingapp.Models;

public class Step {
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    // constructor
    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL){
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    // getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    // toString
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Step ").append(Integer.toString(id)).append(" : ").append(shortDescription).append("\n")
                .append(description).append("\n")
                .append("Video URL : ").append(videoURL).append(".\n")
                .append("Thumbnail URL :").append(thumbnailURL).append(".\n");
        return str.toString();
    }
}