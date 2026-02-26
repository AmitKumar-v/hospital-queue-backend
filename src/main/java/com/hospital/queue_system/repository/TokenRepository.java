package com.hospital.queue_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hospital.queue_system.model.Token;

public interface TokenRepository extends MongoRepository<Token, String> {

    // Get all tokens for a specific doctor
    List<Token> findByDoctorIdOrderByPriorityDescTokenNumberAsc(String doctorId);

    // Get waiting tokens for a doctor
    List<Token> findByDoctorIdAndStatusOrderByPriorityDescTokenNumberAsc(
        String doctorId, String status);

    // Find token by token number for patient tracking
    Optional<Token> findByTokenNumber(int tokenNumber);

    // Count tokens for a doctor today with specific status
    long countByDoctorIdAndStatus(String doctorId, String status);

    // Get all tokens for today's analytics
    List<Token> findByStatus(String status);
}