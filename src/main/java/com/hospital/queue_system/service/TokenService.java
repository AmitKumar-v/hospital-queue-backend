package com.hospital.queue_system.service;

import com.hospital.queue_system.dto.TokenRequest;
import com.hospital.queue_system.model.Doctor;
import com.hospital.queue_system.model.Patient;
import com.hospital.queue_system.model.Token;
import com.hospital.queue_system.repository.DoctorRepository;
import com.hospital.queue_system.repository.PatientRepository;
import com.hospital.queue_system.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Generate a new token for a patient
    public Token generateToken(TokenRequest request) {

        // Step 1: Find the doctor
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Step 2: Save patient info
        Patient patient = new Patient();
        patient.setName(request.getPatientName());
        patient.setPhone(request.getPatientPhone());
        patient.setAge(request.getPatientAge());
        patient.setGender(request.getPatientGender());
        patient.setProblem(request.getProblem());
        Patient savedPatient = patientRepository.save(patient);

        // Step 3: Generate token number
        // Count all existing tokens and add 1
        long totalTokens = tokenRepository.count();
        int newTokenNumber = (int) totalTokens + 1;

        // Step 4: Calculate estimated wait time
        // Count how many patients are WAITING ahead in this doctor's queue
        long waitingAhead = tokenRepository.countByDoctorIdAndStatus(
            request.getDoctorId(), "WAITING");
        // Assume each consultation takes 10 minutes
        int estimatedWait = (int) waitingAhead * 10;

        // Step 5: Create the token
        Token token = new Token();
        token.setTokenNumber(newTokenNumber);
        token.setPatientId(savedPatient.getId());
        token.setPatientName(request.getPatientName());
        token.setPatientPhone(request.getPatientPhone());
        token.setProblem(request.getProblem());
        token.setDoctorId(doctor.getId());
        token.setDoctorName(doctor.getName());
        token.setDepartmentId(doctor.getDepartmentId());
        token.setDepartmentName(doctor.getDepartmentName());
        token.setPriority(request.getPriority() != null ? 
            request.getPriority() : "NORMAL");
        token.setStatus("WAITING");
        token.setCreatedAt(LocalDateTime.now());
        token.setEstimatedWaitMinutes(estimatedWait);

        return tokenRepository.save(token);
    }

    // Get queue for a specific doctor
    public List<Token> getDoctorQueue(String doctorId) {
        return tokenRepository
            .findByDoctorIdAndStatusOrderByPriorityDescTokenNumberAsc(
                doctorId, "WAITING");
    }

    // Doctor calls next patient
    public Token callNextPatient(String doctorId) {
        List<Token> waitingTokens = tokenRepository
            .findByDoctorIdAndStatusOrderByPriorityDescTokenNumberAsc(
                doctorId, "WAITING");

        if (waitingTokens.isEmpty()) {
            throw new RuntimeException("No patients waiting");
        }

        // Get first patient (emergency patients come first due to ordering)
        Token nextToken = waitingTokens.get(0);
        nextToken.setStatus("IN_PROGRESS");
        return tokenRepository.save(nextToken);
    }

    // Doctor marks consultation as complete
    public Token completeToken(String tokenId) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
        token.setStatus("COMPLETED");
        token.setCompletedAt(LocalDateTime.now());
        return tokenRepository.save(token);
    }

    // Patient tracks their token
    public Token trackToken(int tokenNumber) {
        return tokenRepository.findByTokenNumber(tokenNumber)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }

    // Get all tokens for a doctor (all statuses)
    public List<Token> getAllDoctorTokens(String doctorId) {
        return tokenRepository
            .findByDoctorIdOrderByPriorityDescTokenNumberAsc(doctorId);
    }
}