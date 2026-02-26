package com.hospital.queue_system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hospital.queue_system.model.Patient;

public interface PatientRepository extends MongoRepository<Patient, String> {
    // Basic CRUD is enough for now
}