package ru.era.distributionoftasks.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Entity
@Data
@Accessors(chain = true)
public class UnallocatedTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BANK_ID")
    private Bank bank;
    private LocalDate desiredDate;
}
