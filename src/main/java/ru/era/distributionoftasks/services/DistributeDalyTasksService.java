package ru.era.distributionoftasks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.entities.Employee;
import ru.era.distributionoftasks.entities.TaskLog;
import ru.era.distributionoftasks.graphhopper.RoutesService;
import ru.era.distributionoftasks.graphhopper.jsonobjects.Point;
import ru.era.distributionoftasks.repositories.BankRepository;
import ru.era.distributionoftasks.services.distributor.DistributorConnector;
import ru.era.distributionoftasks.services.distributor.OverdueTasksService;
import ru.era.distributionoftasks.services.entities.MatrixWeightWithBanks;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class DistributeDalyTasksService {
    @Autowired
    RoutesService routesService;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    DistributorConnector distributorConnector;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    OverdueTasksService overdueTasksService;


    // outBankList - сюда записываются точки в порядке матрицы
    public MatrixWeightWithBanks getWeightRoutesMatrix() {
        MatrixWeightWithBanks result = new MatrixWeightWithBanks();
        result.setBanksOrder((List<Bank>) bankRepository.findAll());
        result.setMatrixWeightsAnswer(routesService.getMatrixWeightsAnswers(
                result.getBanksOrder().stream()
                        .map(b -> new Point().setLat(b.getLatitude()).setLng(b.getLongitude()))
                        .toList()
        ));
        return result;
    }

    public List<TaskLog> distribute (LocalDate today) {
        List<Employee> employees = employeeService.getAllEmployers();
        List<Bank> banks = (List<Bank>) bankRepository.findAll();
        Map<Bank, Integer> overdueBanks = overdueTasksService.getOverdueTasks(today);
        return distributorConnector.getData(employees, banks, overdueBanks);
    }
}
