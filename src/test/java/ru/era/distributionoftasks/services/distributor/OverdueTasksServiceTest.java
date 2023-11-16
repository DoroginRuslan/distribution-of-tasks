package ru.era.distributionoftasks.services.distributor;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class OverdueTasksServiceTest {
    @Test
    void name() {
        LocalDate date1 = LocalDate.of(2023, 11, 15);
        LocalDate date2 = LocalDate.of(2023, 11, 17);
        System.out.println(date1.until(date2, ChronoUnit.DAYS));
    }
}