package com.gsdc.campushub.service;

import com.gsdc.campushub.dto.ProgressUpdateRequest;
import com.gsdc.campushub.model.Course;
import com.gsdc.campushub.model.CourseStatus;
import com.gsdc.campushub.model.Enrollment;
import com.gsdc.campushub.model.User;
import com.gsdc.campushub.repository.CourseRepository;
import com.gsdc.campushub.repository.EnrollmentRepository;
import com.gsdc.campushub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public Enrollment enroll(String courseId, String studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        if (course.getStatus() != CourseStatus.PUBLISHED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot enroll in an unpublished course");
        }

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Student is already enrolled in this course");
        }

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        Enrollment enrollment = new Enrollment(student.getId(), student.getName(), course.getId(), course.getTitle());
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getEnrollmentsForCourse(String courseId, String userId, String role) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        if (!"ROLE_ADMIN".equalsIgnoreCase(role) && !course.getInstructorId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied to enrolled student roster");
        }

        return enrollmentRepository.findByCourseId(courseId);
    }

    public List<Enrollment> getStudentEnrollments(String studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public Enrollment updateProgress(String enrollmentId, ProgressUpdateRequest req, String studentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));

        if (!enrollment.getStudentId().equals(studentId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to update this enrollment");
        }

        enrollment.setProgress(req.getProgress());
        return enrollmentRepository.save(enrollment);
    }
}
