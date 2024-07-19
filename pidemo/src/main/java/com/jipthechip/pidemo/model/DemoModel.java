package com.jipthechip.pidemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
public class DemoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private LocalDateTime date;

    public DemoModel(long id, LocalDateTime date){
        this.id = id;
        this.date = date;
    }

    public DemoModel() {

    }

    @PrePersist
    protected void onCreate(){
        date = LocalDateTime.now();
    }
}
