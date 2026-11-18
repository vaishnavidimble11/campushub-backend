package com.gsdc.campushub.dto;

import java.util.Map;

public class PlatformStatsResponse {
    private long totalUsers;
    private long totalStudents;
    private long totalInstructors;
    private long totalCourses;
    private long publishedCourses;
    private long totalEnrollments;
    private Map<String, Long> coursesByCategory;

    public PlatformStatsResponse() {}

    public PlatformStatsResponse(long totalUsers, long totalStudents, long totalInstructors,
                                 long totalCourses, long publishedCourses, long totalEnrollments,
                                 Map<String, Long> coursesByCategory) {
        this.totalUsers = totalUsers;
        this.totalStudents = totalStudents;
        this.totalInstructors = totalInstructors;
        this.totalCourses = totalCourses;
        this.publishedCourses = publishedCourses;
        this.totalEnrollments = totalEnrollments;
        this.coursesByCategory = coursesByCategory;
    }

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }

    public long getTotalInstructors() { return totalInstructors; }
    public void setTotalInstructors(long totalInstructors) { this.totalInstructors = totalInstructors; }

    public long getTotalCourses() { return totalCourses; }
    public void setTotalCourses(long totalCourses) { this.totalCourses = totalCourses; }

    public long getPublishedCourses() { return publishedCourses; }
    public void setPublishedCourses(long publishedCourses) { this.publishedCourses = publishedCourses; }

    public long getTotalEnrollments() { return totalEnrollments; }
    public void setTotalEnrollments(long totalEnrollments) { this.totalEnrollments = totalEnrollments; }

    public Map<String, Long> getCoursesByCategory() { return coursesByCategory; }
    public void setCoursesByCategory(Map<String, Long> coursesByCategory) { this.coursesByCategory = coursesByCategory; }
}
