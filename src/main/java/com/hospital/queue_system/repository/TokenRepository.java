package com.hospital.queue_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hospital.queue_system.model.Token;

public interface TokenRepository extends MongoRepository<Token, String> {

    @Query(value = "{ 'doctorId': ?0, 'status': 'WAITING' }",
           sort  = "{ 'priorityOrder': -1, 'tokenNumber': 1 }")
    List<Token> findWaitingByDoctorIdSorted(String doctorId);

    @Query(value = "{ 'doctorId': ?0 }",
           sort  = "{ 'priorityOrder': -1, 'tokenNumber': 1 }")
    List<Token> findAllByDoctorIdSorted(String doctorId);

    Optional<Token> findByTokenNumber(int tokenNumber);

    long countByDoctorIdAndStatus(String doctorId, String status);

    // Count only WAITING + IN_PROGRESS for this doctor
    long countByDoctorIdAndStatusIn(String doctorId, List<String> statuses);

    Optional<Token> findTopByOrderByTokenNumberDesc();
}
