package com.hospital.queue_system.controller;

import com.hospital.queue_system.dto.DoctorRequest;
import com.hospital.queue_system.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/doctors/department/{departmentId}")
    public ResponseEntity<?> getDoctorsByDepartment(@PathVariable String departmentId) {
        return ResponseEntity.ok(doctorService.getDoctorsByDepartment(departmentId));
    }

    @PostMapping("/admin/doctors")
    public ResponseEntity<?> createDoctor(@RequestBody DoctorRequest request) {
        return ResponseEntity.ok(doctorService.createDoctor(request));
    }

    @DeleteMapping("/admin/doctors/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable String id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted");
    }

    @PutMapping("/admin/doctors/{id}/toggle")
    public ResponseEntity<?> toggleAvailability(@PathVariable String id) {
        return ResponseEntity.ok(doctorService.toggleAvailability(id));
    }

    @PutMapping("/admin/doctors/{id}/timing")
    public ResponseEntity<?> updateTiming(@PathVariable String id,
            @RequestParam String from,
            @RequestParam String to) {
        return ResponseEntity.ok(doctorService.updateTiming(id, from, to));
    }
}