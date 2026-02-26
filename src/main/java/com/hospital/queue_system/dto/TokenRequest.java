package com.hospital.queue_system.dto;

import lombok.Data;

@Data
public class TokenRequest {

    private String patientName;
    private String patientPhone;
    private String patientAge;
    private String patientGender;
    private String problem;
    private String doctorId;
    private String departmentId;
    private String priority;    // NORMAL or EMERGENCY
}