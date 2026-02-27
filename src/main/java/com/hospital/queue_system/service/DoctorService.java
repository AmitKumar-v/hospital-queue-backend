package com.hospital.queue_system.service;

import com.hospital.queue_system.dto.DoctorRequest;
import com.hospital.queue_system.model.Department;
import com.hospital.queue_system.model.Doctor;
import com.hospital.queue_system.model.User;
import com.hospital.queue_system.repository.DepartmentRepository;
import com.hospital.queue_system.repository.DoctorRepository;
import com.hospital.queue_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findByActiveTrue();
    }

    public List<Doctor> getDoctorsByDepartment(String departmentId) {
        return doctorRepository.findByDepartmentIdAndActiveTrue(departmentId);
    }

    public Doctor createDoctor(DoctorRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // Create login account
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("DOCTOR");
        User savedUser = userRepository.save(user);

        // Create doctor profile
        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setDepartmentId(request.getDepartmentId());
        doctor.setDepartmentName(department.getName());
        doctor.setUserId(savedUser.getId());
        doctor.setEmail(request.getEmail());
        doctor.setAvailable(true);
        doctor.setAvailableFrom(request.getAvailableFrom() != null ? request.getAvailableFrom() : "09:00");
        doctor.setAvailableTo(request.getAvailableTo() != null ? request.getAvailableTo() : "17:00");

        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(String doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setActive(false);
        doctorRepository.save(doctor);
    }

    public Doctor toggleAvailability(String doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setAvailable(!doctor.isAvailable());
        return doctorRepository.save(doctor);
    }

    public Doctor updateTiming(String doctorId, String from, String to) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setAvailableFrom(from);
        doctor.setAvailableTo(to);
        return doctorRepository.save(doctor);
    }
}