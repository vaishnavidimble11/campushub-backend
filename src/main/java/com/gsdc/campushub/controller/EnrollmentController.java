package com.gsdc.campushub.controller;

import com.gsdc.campushub.dto.ProgressUpdateRequest;
import com.gsdc.campushub.model.Enrollment;
import com.gsdc.campushub.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<Enrollment>> getMyEnrollments(Authentication authentication) {
        String studentId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(enrollmentService.getStudentEnrollments(studentId));
    }

    @PatchMapping("/{id}/progress")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Enrollment> updateProgress(@PathVariable String id,
                                                    @Valid @RequestBody ProgressUpdateRequest request,
                                                    Authentication authentication) {
        String studentId = (String) authentication.getPrincipal();
        Enrollment updated = enrollmentService.updateProgress(id, request, studentId);
        return ResponseEntity.ok(updated);
    }
}
