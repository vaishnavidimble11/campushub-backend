package com.gsdc.campushub.model;

public class Lesson {
    private String id;
    private String title;
    private String description;
    private String videoUrl;
    private int durationMinutes;

    public Lesson() {}

    public Lesson(String id, String title, String description, String videoUrl, int durationMinutes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.durationMinutes = durationMinutes;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
}
