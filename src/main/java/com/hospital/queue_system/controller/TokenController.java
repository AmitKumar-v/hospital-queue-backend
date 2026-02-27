package com.hospital.queue_system.controller;

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
import com.hospital.queue_system.service.TokenService;

@RestController
@RequestMapping("/api")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/receptionist/tokens/generate")
    public ResponseEntity<?> generateToken(@RequestBody TokenRequest request) {
        return ResponseEntity.ok(tokenService.generateToken(request));
    }

    @GetMapping("/doctor/queue/{doctorId}")
    public ResponseEntity<?> getDoctorQueue(@PathVariable String doctorId) {
        return ResponseEntity.ok(tokenService.getDoctorQueue(doctorId));
    }

    @PutMapping("/doctor/queue/{doctorId}/next")
    public ResponseEntity<?> callNextPatient(@PathVariable String doctorId) {
        return ResponseEntity.ok(tokenService.callNextPatient(doctorId));
    }

    @PutMapping("/doctor/tokens/{tokenId}/complete")
    public ResponseEntity<?> completeToken(@PathVariable String tokenId) {
        return ResponseEntity.ok(tokenService.completeToken(tokenId));
    }

    @GetMapping("/tokens/track/{tokenNumber}")
    public ResponseEntity<?> trackToken(@PathVariable int tokenNumber) {
        return ResponseEntity.ok(tokenService.trackToken(tokenNumber));
    }

    @GetMapping("/doctor/tokens/{doctorId}")
    public ResponseEntity<?> getAllTokensForDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(tokenService.getAllTokensForDoctor(doctorId));
    }
}
