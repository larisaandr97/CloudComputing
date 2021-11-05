package com.javaproject.storeapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String signature;

    @Column(name = "class")
    private String targetClass;

    @Column(name = "timestamp_value")
    private LocalDateTime timestampValue;

    public Audit(String signature, String targetClass, LocalDateTime timestampValue) {
        this.signature = signature;
        this.targetClass = targetClass;
        this.timestampValue = timestampValue;
    }

    public Audit() {
    }
}
