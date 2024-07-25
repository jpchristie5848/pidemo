package com.jipthechip.pidemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
public class LightLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private float lightLevel;

    private LocalDateTime date;

    @PrePersist
    protected void onCreate(){
        date = LocalDateTime.now();
    }
}
