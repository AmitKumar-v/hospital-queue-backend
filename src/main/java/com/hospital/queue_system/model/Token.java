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

    private int tokenNumber;

    private String patientId;
    private String patientName;
    private String patientPhone;
    private String problem;

    private String doctorId;
    private String doctorName;

    private String departmentId;
    private String departmentName;

    private String priority;        // "NORMAL" or "EMERGENCY"
    private int priorityOrder;      // EMERGENCY = 1, NORMAL = 0 (for sorting)

    private String status;          // WAITING, IN_PROGRESS, COMPLETED, SKIPPED

    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    private int estimatedWaitMinutes;
}