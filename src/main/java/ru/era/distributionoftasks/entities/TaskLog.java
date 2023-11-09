package ru.era.distributionoftasks.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Data
@Accessors(chain = true)
public class TaskLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_ID")
    Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TASK_TYPE_ID")
    TaskType taskType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BANK_ID")
    private Bank bank;

    LocalDateTime taskSetDate;
    boolean isCompleted;
    String commentary;
}
