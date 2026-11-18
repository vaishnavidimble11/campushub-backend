package com.gsdc.campushub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "enrollments")
@CompoundIndex(name = "student_course_idx", def = "{'studentId': 1, 'courseId': 1}", unique = true)
public class Enrollment {
    @Id
    private String id;

    private String studentId;
    private String studentName;
    private String courseId;
    private String courseTitle;
    private int progress = 0; // 0 - 100
    private Date enrolledAt = new Date();

    public Enrollment() {}

    public Enrollment(String studentId, String studentName, String courseId, String courseTitle) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.progress = 0;
        this.enrolledAt = new Date();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }

    public Date getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(Date enrolledAt) { this.enrolledAt = enrolledAt; }
}
