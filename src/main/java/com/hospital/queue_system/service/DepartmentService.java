package com.hospital.queue_system.service;

import com.hospital.queue_system.dto.DepartmentRequest;
import com.hospital.queue_system.model.Department;
import com.hospital.queue_system.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Get all active departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findByActiveTrue();
    }

    // Create a new department
    public Department createDepartment(DepartmentRequest request) {
        if (departmentRepository.existsByName(request.getName())) {
            throw new RuntimeException("Department already exists");
        }
        Department dept = new Department();
        dept.setName(request.getName());
        dept.setDescription(request.getDescription());
        return departmentRepository.save(dept);
    }

    // Delete a department (soft delete - just marks inactive)
    public void deleteDepartment(String id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        dept.setActive(false);
        departmentRepository.save(dept);
    }
}