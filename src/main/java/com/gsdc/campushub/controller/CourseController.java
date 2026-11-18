package com.gsdc.campushub.controller;

import com.gsdc.campushub.dto.CourseRequest;
import com.gsdc.campushub.model.Course;
import com.gsdc.campushub.model.Enrollment;
import com.gsdc.campushub.service.CourseService;
import com.gsdc.campushub.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(Authentication authentication) {
        String userId = null;
        String role = null;
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            userId = (String) authentication.getPrincipal();
            role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst().orElse(null);
        }
        return ResponseEntity.ok(courseService.getAllCourses(userId, role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CourseRequest request, Authentication authentication) {
        String instructorId = (String) authentication.getPrincipal();
        Course course = courseService.createCourse(request, instructorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<Course> updateCourse(@PathVariable String id,
                                               @Valid @RequestBody CourseRequest request,
                                               Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        Course updated = courseService.updateCourse(id, request, userId, role);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id, Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        courseService.deleteCourse(id, userId, role);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/enroll")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Enrollment> enrollInCourse(@PathVariable String id, Authentication authentication) {
        String studentId = (String) authentication.getPrincipal();
        Enrollment enrollment = enrollmentService.enroll(id, studentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
    }

    @GetMapping("/{id}/students")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<List<Enrollment>> getEnrolledStudents(@PathVariable String id, Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        List<Enrollment> students = enrollmentService.getEnrollmentsForCourse(id, userId, role);
        return ResponseEntity.ok(students);
    }
}
