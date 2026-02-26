package com.hospital.queue_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.queue_system.dto.TokenRequest;
import com.hospital.queue_system.model.Token;
import com.hospital.queue_system.service.TokenService;

@RestController
@RequestMapping("/api")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    // Receptionist generates token for patient
    @PostMapping("/receptionist/tokens/generate")
    public ResponseEntity<Token> generateToken(@RequestBody TokenRequest request) {
        return ResponseEntity.ok(tokenService.generateToken(request));
    }

    // Doctor gets their waiting queue
    @GetMapping("/doctor/queue/{doctorId}")
    public ResponseEntity<List<Token>> getDoctorQueue(@PathVariable String doctorId) {
        return ResponseEntity.ok(tokenService.getDoctorQueue(doctorId));
    }

    // Doctor calls next patient
    @PutMapping("/doctor/queue/{doctorId}/next")
    public ResponseEntity<Token> callNext(@PathVariable String doctorId) {
        return ResponseEntity.ok(tokenService.callNextPatient(doctorId));
    }

    // Doctor completes a consultation
    @PutMapping("/doctor/tokens/{tokenId}/complete")
    public ResponseEntity<Token> completeToken(@PathVariable String tokenId) {
        return ResponseEntity.ok(tokenService.completeToken(tokenId));
    }

    // PUBLIC - Patient tracks token (no login needed)
    @GetMapping("/tokens/track/{tokenNumber}")
    public ResponseEntity<Token> trackToken(@PathVariable int tokenNumber) {
        return ResponseEntity.ok(tokenService.trackToken(tokenNumber));
    }

    // Get all tokens for doctor
    @GetMapping("/doctor/tokens/{doctorId}")
    public ResponseEntity<List<Token>> getAllDoctorTokens(@PathVariable String doctorId) {
        return ResponseEntity.ok(tokenService.getAllDoctorTokens(doctorId));
    }
}