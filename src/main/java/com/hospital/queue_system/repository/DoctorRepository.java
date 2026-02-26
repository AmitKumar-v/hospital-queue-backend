package com.hospital.queue_system.repository;

import com.hospital.queue_system.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    List<Doctor> findByActiveTrue();
    List<Doctor> findByDepartmentIdAndActiveTrue(String departmentId);
}