package ru.era.distributionoftasks.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    LocalDateTime taskSetDate;
    boolean isCompleted;
    String commentary;
}
