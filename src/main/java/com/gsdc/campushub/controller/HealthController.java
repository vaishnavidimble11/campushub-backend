package com.gsdc.campushub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("ok", true);
        health.put("app", "CampusHub API");
        health.put("status", "UP");
        health.put("database", "MongoDB Atlas Connected");
        return ResponseEntity.ok(health);
    }
}
