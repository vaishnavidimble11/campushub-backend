package com.gsdc.campushub.config;

import com.gsdc.campushub.model.*;
import com.gsdc.campushub.repository.CourseRepository;
import com.gsdc.campushub.repository.EnrollmentRepository;
import com.gsdc.campushub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            System.out.println("==> Seeding initial CampusHub database users...");

            // 1. Admin
            User admin = new User(
                    "System Admin",
                    "admin@campushub.edu",
                    passwordEncoder.encode("Admin@123"),
                    Role.ADMIN
            );
            userRepository.save(admin);

            // 2. Instructor
            User instructor = new User(
                    "Prof. Alan Turing",
                    "instructor@campushub.edu",
                    passwordEncoder.encode("Instructor@123"),
                    Role.INSTRUCTOR
            );
            instructor = userRepository.save(instructor);

            // 3. Student
            User student = new User(
                    "Jane Doe",
                    "student@campushub.edu",
                    passwordEncoder.encode("Student@123"),
                    Role.STUDENT
            );
            student = userRepository.save(student);

            System.out.println("==> Users seeded: admin@campushub.edu, instructor@campushub.edu, student@campushub.edu (pw: Role@123)");

            // Seed courses
            Course course1 = new Course(
                    "Full Stack Web Development with React & Spring Boot",
                    "Build enterprise-grade single page applications with React, Vite, Spring Boot REST APIs, and MongoDB Atlas.",
                    "Computer Science",
                    instructor.getId(),
                    instructor.getName()
            );
            course1.setStatus(CourseStatus.PUBLISHED);
            course1.setLessons(Arrays.asList(
                    new Lesson(UUID.randomUUID().toString(), "1. Introduction to Spring Boot & Mongo", "Setting up project architecture and connecting to Atlas", "https://www.youtube.com/embed/dQw4w9WgXcQ", 25),
                    new Lesson(UUID.randomUUID().toString(), "2. JWT Authentication & Spring Security", "Implementing stateless RBAC filters and token validation", "https://www.youtube.com/embed/dQw4w9WgXcQ", 35),
                    new Lesson(UUID.randomUUID().toString(), "3. React Frontend Integration", "Consuming REST APIs, protected routes, and state handling", "https://www.youtube.com/embed/dQw4w9WgXcQ", 40)
            ));
            course1 = courseRepository.save(course1);

            Course course2 = new Course(
                    "Cloud-Native Microservices & Docker",
                    "Containerization with Docker, deploying on Render and Vercel, and cloud data persistence.",
                    "Cloud Computing",
                    instructor.getId(),
                    instructor.getName()
            );
            course2.setStatus(CourseStatus.PUBLISHED);
            course2.setLessons(Arrays.asList(
                    new Lesson(UUID.randomUUID().toString(), "1. Dockerfile Optimization", "Multi-stage builds for Java applications", "https://www.youtube.com/embed/dQw4w9WgXcQ", 20),
                    new Lesson(UUID.randomUUID().toString(), "2. Continuous Deployment to Render", "Configuring web services and environment variables", "https://www.youtube.com/embed/dQw4w9WgXcQ", 30)
            ));
            courseRepository.save(course2);

            // Enroll student in course 1 with 35% progress
            Enrollment enrollment = new Enrollment(student.getId(), student.getName(), course1.getId(), course1.getTitle());
            enrollment.setProgress(35);
            enrollmentRepository.save(enrollment);

            System.out.println("==> Sample courses and enrollment seeded successfully!");
        }
    }
}
