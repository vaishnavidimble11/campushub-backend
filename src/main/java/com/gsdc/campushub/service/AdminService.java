package com.gsdc.campushub.service;

import com.gsdc.campushub.dto.PlatformStatsResponse;
import com.gsdc.campushub.dto.RoleUpdateRequest;
import com.gsdc.campushub.model.Course;
import com.gsdc.campushub.model.CourseStatus;
import com.gsdc.campushub.model.Role;
import com.gsdc.campushub.model.User;
import com.gsdc.campushub.repository.CourseRepository;
import com.gsdc.campushub.repository.EnrollmentRepository;
import com.gsdc.campushub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUserRoleAndStatus(String userId, RoleUpdateRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (req.getRole() != null) {
            user.setRole(req.getRole());
        }
        if (req.getActive() != null) {
            user.setActive(req.getActive());
        }

        return userRepository.save(user);
    }

    public PlatformStatsResponse getPlatformStats() {
        long totalUsers = userRepository.count();
        long totalStudents = userRepository.countByRole(Role.STUDENT);
        long totalInstructors = userRepository.countByRole(Role.INSTRUCTOR);
        long totalCourses = courseRepository.count();
        long publishedCourses = courseRepository.countByStatus(CourseStatus.PUBLISHED);
        long totalEnrollments = enrollmentRepository.count();

        Map<String, Long> categoryMap = new HashMap<>();
        List<Course> courses = courseRepository.findAll();
        for (Course c : courses) {
            String cat = c.getCategory() != null ? c.getCategory() : "General";
            categoryMap.put(cat, categoryMap.getOrDefault(cat, 0L) + 1L);
        }

        return new PlatformStatsResponse(
                totalUsers,
                totalStudents,
                totalInstructors,
                totalCourses,
                publishedCourses,
                totalEnrollments,
                categoryMap
        );
    }
}
