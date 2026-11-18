package com.gsdc.campushub.dto;

import com.gsdc.campushub.model.CourseStatus;
import com.gsdc.campushub.model.Lesson;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class CourseRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    private CourseStatus status = CourseStatus.DRAFT;
    private List<Lesson> lessons = new ArrayList<>();

    public CourseRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public CourseStatus getStatus() { return status; }
    public void setStatus(CourseStatus status) { this.status = status; }

    public List<Lesson> getLessons() { return lessons; }
    public void setLessons(List<Lesson> lessons) { this.lessons = lessons; }
}
