package com.gsdc.campushub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "courses")
public class Course {
    @Id
    private String id;

    private String title;
    private String description;
    private String category;
    private String instructorId;
    private String instructorName;
    private CourseStatus status = CourseStatus.DRAFT;
    private List<Lesson> lessons = new ArrayList<>();
    private Date createdAt = new Date();

    public Course() {}

    public Course(String title, String description, String category, String instructorId, String instructorName) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.status = CourseStatus.DRAFT;
        this.lessons = new ArrayList<>();
        this.createdAt = new Date();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }

    public CourseStatus getStatus() { return status; }
    public void setStatus(CourseStatus status) { this.status = status; }

    public List<Lesson> getLessons() { return lessons; }
    public void setLessons(List<Lesson> lessons) { this.lessons = lessons; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
