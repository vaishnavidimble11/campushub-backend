package com.gsdc.campushub.repository;

import com.gsdc.campushub.model.Course;
import com.gsdc.campushub.model.CourseStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    List<Course> findByStatus(CourseStatus status);
    List<Course> findByInstructorId(String instructorId);
    long countByStatus(CourseStatus status);
}
