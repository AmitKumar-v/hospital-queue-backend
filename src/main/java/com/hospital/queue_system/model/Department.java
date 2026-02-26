package com.hospital.queue_system.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "departments")
public class Department {

    @Id
    private String id;

    private String name;        // e.g. "Cardiology"

    private String description; // e.g. "Heart related issues"

    private boolean active = true;
}