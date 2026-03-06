package com.hospital.queue_system.repository;

import com.hospital.queue_system.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {

    // EMERGENCY first (priorityOrder desc), then token number ascending
    @Query(value = "{ 'doctorId': ?0, 'status': 'WAITING' }",
           sort  = "{ 'priorityOrder': -1, 'tokenNumber': 1 }")
    List<Token> findWaitingByDoctorIdSorted(String doctorId);

    // All tokens for a doctor
    @Query(value = "{ 'doctorId': ?0 }",
           sort  = "{ 'priorityOrder': -1, 'tokenNumber': 1 }")
    List<Token> findAllByDoctorIdSorted(String doctorId);

    Optional<Token> findByTokenNumber(int tokenNumber);

    long countByDoctorIdAndStatus(String doctorId, String status);

    Optional<Token> findTopByOrderByTokenNumberDesc();
}