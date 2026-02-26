package com.hospital.queue_system.repository;

import com.hospital.queue_system.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface DepartmentRepository extends MongoRepository<Department, String> {
    List<Department> findByActiveTrue();
    boolean existsByName(String name);
}