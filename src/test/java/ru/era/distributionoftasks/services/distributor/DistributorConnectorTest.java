package ru.era.distributionoftasks.services.distributor;

import org.junit.jupiter.api.Test;
import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.entities.Employee;
import ru.era.distributionoftasks.entities.Grade;

import java.lang.reflect.Array;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DistributorConnectorTest {

    @Test
    public void name() {
        DistributorConnector distributorConnector = new DistributorConnector();
        Grade grade = new Grade().setName("Мидл");
        List<Employee> employeeList = List.of(
                new Employee().setGrade(grade).setId(1).setFio("BBB").setAddress("Краснодар, Красная, д. 139"),
                new Employee().setGrade(grade).setId(2).setFio("PPP").setAddress("Краснодар, Красная, д. 139"),
                new Employee().setGrade(grade).setId(3).setFio("LLL").setAddress("Краснодар, В.Н. Мачуги, 41")
        );
        List<Bank> banks = List.of(
                new Bank().setId(1).setAddress("г. Краснодар, ул. им. Атарбекова, д. 24"),
                new Bank().setId(2).setAddress("г. Краснодар, ул. им. Тургенева, д. 106"),
                new Bank().setId(3).setAddress("г. Краснодар, ул. Красных Партизан, д. 117"),
                new Bank().setId(4).setAddress("г. Краснодар, ул. Северная, д. 389")
        );
        distributorConnector.getData(employeeList, banks);
    }
}