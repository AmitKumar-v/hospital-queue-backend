package com.hospital.queue_system.dto;

import lombok.Data;

@Data
public class DoctorRequest {
    private String name;
    private String specialization;
    private String departmentId;
    private String email;       // For creating login account
    private String password;    // For creating login account
}