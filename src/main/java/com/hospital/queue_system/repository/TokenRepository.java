package com.hospital.queue_system.repository;

import com.hospital.queue_system.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {

    List<Token> findByDoctorIdOrderByPriorityDescTokenNumberAsc(String doctorId);

    List<Token> findByDoctorIdAndStatusOrderByPriorityDescTokenNumberAsc(
            String doctorId, String status);

    Optional<Token> findByTokenNumber(int tokenNumber);

    long countByDoctorIdAndStatus(String doctorId, String status);

    Optional<Token> findTopByOrderByTokenNumberDesc();
}