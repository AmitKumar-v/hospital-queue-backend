package com.hospital.queue_system.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "doctors")
public class Doctor {

    @Id
    private String id;
    private String name;
    private String specialization;
    private String departmentId;
    private String departmentName;
    private String userId;
    private String email;
    private boolean available = true;
    private boolean active = true;
    private String availableFrom = "09:00";
    private String availableTo = "17:00";
}