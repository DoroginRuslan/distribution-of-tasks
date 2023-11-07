package ru.era.distributionoftasks.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Accessors(chain = true)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fio;
    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GRADE_ID")
    private Grade grade;
    private boolean isActive;
}
