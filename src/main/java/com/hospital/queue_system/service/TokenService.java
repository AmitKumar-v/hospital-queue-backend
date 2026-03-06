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

    @Autowired private TokenRepository tokenRepository;
    @Autowired private DoctorRepository doctorRepository;
    @Autowired private PatientRepository patientRepository;

    public Token generateToken(TokenRequest request) {
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Save patient record
        Patient patient = new Patient();
        patient.setName(request.getPatientName());
        patient.setPhone(request.getPatientPhone());
        patient.setAge(request.getPatientAge());
        patient.setGender(request.getPatientGender());
        patient.setProblem(request.getProblem());
        patientRepository.save(patient);

        // Auto-increment token number globally
        int lastTokenNumber = tokenRepository.findTopByOrderByTokenNumberDesc()
                .map(Token::getTokenNumber).orElse(0);
        int tokenNumber = lastTokenNumber + 1;

        // Estimated wait time
        long waitingCount = tokenRepository.countByDoctorIdAndStatus(
                request.getDoctorId(), "WAITING");
        int estimatedWait = (int) waitingCount * 10;

        // Set priority string and numeric order for sorting
        String priority = (request.getPriority() != null && 
                          !request.getPriority().isEmpty())
                          ? request.getPriority() : "NORMAL";
        int priorityOrder = priority.equals("EMERGENCY") ? 1 : 0;

        // Create token
        Token token = new Token();
        token.setTokenNumber(tokenNumber);
        token.setPatientId(patient.getId());
        token.setPatientName(request.getPatientName());
        token.setPatientPhone(request.getPatientPhone());
        token.setProblem(request.getProblem());
        token.setDoctorId(request.getDoctorId());
        token.setDoctorName(doctor.getName());
        token.setDepartmentId(request.getDepartmentId());
        token.setDepartmentName(doctor.getDepartmentName());
        token.setPriority(priority);
        token.setPriorityOrder(priorityOrder);
        token.setStatus("WAITING");
        token.setCreatedAt(LocalDateTime.now());
        token.setEstimatedWaitMinutes(estimatedWait);

        return tokenRepository.save(token);
    }

    public List<Token> getDoctorQueue(String doctorId) {
        return tokenRepository.findWaitingByDoctorIdSorted(doctorId);
    }

    public Token callNextPatient(String doctorId) {
        List<Token> waiting = tokenRepository.findWaitingByDoctorIdSorted(doctorId);
        if (waiting.isEmpty()) throw new RuntimeException("No patients waiting");

        // First item is always highest priority — emergency or earliest token
        Token next = waiting.get(0);
        next.setStatus("IN_PROGRESS");
        return tokenRepository.save(next);
    }

    public Token completeToken(String tokenId) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
        token.setStatus("COMPLETED");
        token.setCompletedAt(LocalDateTime.now());
        return tokenRepository.save(token);
    }

    public Token trackToken(int tokenNumber) {
        return tokenRepository.findByTokenNumber(tokenNumber)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }

    public List<Token> getAllTokensForDoctor(String doctorId) {
        return tokenRepository.findAllByDoctorIdSorted(doctorId);
    }
}
