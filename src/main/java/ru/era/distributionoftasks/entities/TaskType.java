package ru.era.distributionoftasks.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalTime;

@Entity
@Data
@Accessors(chain = true)
public class TaskType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int priority;
    private LocalTime timeReq;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GRADE_ID")
    private Grade grade;

    @Column(name = "CONDITION_1")
    private String condition1;

    @Column(name = "CONDITION_2")
    private String condition2;
}
