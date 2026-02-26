package com.hospital.queue_system.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "patients")
public class Patient {

    @Id
    private String id;

    private String name;

    private String phone;

    private String age;

    private String gender;  // MALE, FEMALE, OTHER

    private String problem; // Brief description of problem
}