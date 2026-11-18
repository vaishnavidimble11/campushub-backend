package com.gsdc.campushub.service;

import com.gsdc.campushub.dto.CourseRequest;
import com.gsdc.campushub.model.Course;
import com.gsdc.campushub.model.CourseStatus;
import com.gsdc.campushub.model.Role;
import com.gsdc.campushub.model.User;
import com.gsdc.campushub.repository.CourseRepository;
import com.gsdc.campushub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Course> getAllCourses(String currentUserId, String role) {
        if ("ROLE_ADMIN".equalsIgnoreCase(role)) {
            return courseRepository.findAll();
        } else if ("ROLE_INSTRUCTOR".equalsIgnoreCase(role)) {
            return courseRepository.findByInstructorId(currentUserId);
        } else {
            // Students or public guests only see published courses
            return courseRepository.findByStatus(CourseStatus.PUBLISHED);
        }
    }

    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }

    public Course createCourse(CourseRequest req, String instructorId) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor not found"));

        Course course = new Course(
                req.getTitle(),
                req.getDescription(),
                req.getCategory(),
                instructor.getId(),
                instructor.getName()
        );
        if (req.getStatus() != null) {
            course.setStatus(req.getStatus());
        }
        if (req.getLessons() != null) {
            course.setLessons(req.getLessons());
        }

        return courseRepository.save(course);
    }

    public Course updateCourse(String id, CourseRequest req, String userId, String role) {
        Course course = getCourseById(id);

        // RBAC / Ownership verification: Admin can edit any, Instructor can only edit their own
        if (!"ROLE_ADMIN".equalsIgnoreCase(role) && !course.getInstructorId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this course");
        }

        course.setTitle(req.getTitle());
        course.setDescription(req.getDescription());
        course.setCategory(req.getCategory());
        if (req.getStatus() != null) {
            course.setStatus(req.getStatus());
        }
        if (req.getLessons() != null) {
            course.setLessons(req.getLessons());
        }

        return courseRepository.save(course);
    }

    public void deleteCourse(String id, String userId, String role) {
        Course course = getCourseById(id);

        if (!"ROLE_ADMIN".equalsIgnoreCase(role) && !course.getInstructorId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this course");
        }

        courseRepository.delete(course);
    }
}
