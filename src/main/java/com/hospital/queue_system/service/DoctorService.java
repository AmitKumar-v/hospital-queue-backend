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

    // Get all active doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findByActiveTrue();
    }

    // Get doctors by department
    public List<Doctor> getDoctorsByDepartment(String departmentId) {
        return doctorRepository.findByDepartmentIdAndActiveTrue(departmentId);
    }

    // Create a new doctor (also creates login account)
    public Doctor createDoctor(DoctorRequest request) {

        // Find the department first
        Department dept = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // Create a login account for the doctor
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("DOCTOR");
        User savedUser = userRepository.save(user);

        // Create the doctor profile
        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setDepartmentId(dept.getId());
        doctor.setDepartmentName(dept.getName());
        doctor.setUserId(savedUser.getId());

        return doctorRepository.save(doctor);
    }

    // Delete a doctor (soft delete)
    public void deleteDoctor(String id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setActive(false);
        doctorRepository.save(doctor);
    }
}