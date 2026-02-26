package com.hospital.queue_system.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "tokens")
public class Token {

    @Id
    private String id;

    private int tokenNumber;        // e.g. 1, 2, 3...

    private String patientId;
    private String patientName;
    private String patientPhone;
    private String problem;

    private String doctorId;
    private String doctorName;

    private String departmentId;
    private String departmentName;

    private String priority;        // NORMAL or EMERGENCY

    private String status;          // WAITING, IN_PROGRESS, COMPLETED, SKIPPED

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    private int estimatedWaitMinutes; // Estimated wait time in minutes
}