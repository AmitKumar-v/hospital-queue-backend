package com.hospital.queue_system.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "doctors")
public class Doctor {

    @Id
    private String id;

    private String name;            // Doctor's full name

    private String specialization;  // e.g. "Cardiologist"

    private String departmentId;    // Links to Department

    private String departmentName;  // Stored for easy display

    private String userId;          // Links to User account (for login)

    private boolean available = true;

    private boolean active = true;
}