package com.hospital.queue_system.repository;

import com.hospital.queue_system.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {

    // WAITING queue — EMERGENCY first, then by token number ascending
    @Query(value = "{ 'doctorId': ?0, 'status': 'WAITING' }", 
           sort = "{ 'priority': -1, 'tokenNumber': 1 }")
    List<Token> findWaitingByDoctorIdSorted(String doctorId);

    // All tokens for a doctor sorted
    @Query(value = "{ 'doctorId': ?0 }", 
           sort = "{ 'priority': -1, 'tokenNumber': 1 }")
    List<Token> findAllByDoctorIdSorted(String doctorId);

    Optional<Token> findByTokenNumber(int tokenNumber);

    long countByDoctorIdAndStatus(String doctorId, String status);

    // For auto-increment — get the highest token number ever
    Optional<Token> findTopByOrderByTokenNumberDesc();
}