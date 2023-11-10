package ru.era.distributionoftasks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.era.distributionoftasks.entities.*;
import ru.era.distributionoftasks.repositories.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

// TODO: 09.11.2023 Не забыть удалить его после тестирования 
@RestController()
@RequestMapping("/api/test")
public class TestApiController {
    @Autowired
    BankRepository bankRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    TaskLogRepository taskLogRepository;
    @Autowired
    TaskTypeRepository taskTypeRepository;

    @GetMapping("/prepare-repo")
    public List<TaskLog> prepareRepo() {
        clearAll();
        Grade senior = gradeRepository.save(new Grade()
                .setName("Синьор")
                .setRank(0));
        Grade middle = gradeRepository.save(new Grade()
                .setName("Мидл")
                .setRank(1));
        Grade junior = gradeRepository.save(new Grade()
                .setName("Джун")
                .setRank(2));

        Employee employee1 = employeeRepository.save(new Employee()
                .setFio("Дерягин Никита Владимирович")
                .setGrade(senior)
                .setAddress("Краснодар, Красная, д. 139")
                .setActive(true));
        Employee employee2 = employeeRepository.save(new Employee()
                .setFio("Петрошев Валерий Павлович")
                .setGrade(middle)
                .setAddress("Краснодар, Красная, д. 139")
                .setActive(true));
        Employee employee3 = employeeRepository.save(new Employee()
                .setFio("Евдокимов Давид Тихонович")
                .setGrade(junior)
                .setAddress("Краснодар, Красная, д. 139")
                .setActive(true));
        Employee employee4 = employeeRepository.save(new Employee()
                .setFio("Андреев Гордий Данилович")
                .setGrade(senior)
                .setAddress("Краснодар, В.Н. Мачуги, 41")
                .setActive(true));
        Employee employee5 = employeeRepository.save(new Employee()
                .setFio("Иванов Адам Федорович")
                .setGrade(middle)
                .setAddress("Краснодар, В.Н. Мачуги, 41")
                .setActive(true));
        Employee employee6 = employeeRepository.save(new Employee()
                .setFio("Бобылёв Ипполит Альбертович")
                .setGrade(junior)
                .setAddress("Краснодар, В.Н. Мачуги, 41")
                .setActive(true));
        Employee employee7 = employeeRepository.save(new Employee()
                .setFio("Беляева Евгения Антоновна")
                .setGrade(middle)
                .setAddress("Краснодар, Красных Партизан, 321")
                .setActive(true));
        Employee employee8 = employeeRepository.save(new Employee()
                .setFio("Николаев Азарий Платонович")
                .setGrade(junior)
                .setAddress("Краснодар, Красных Партизан, 321")
                .setActive(true));

        LocalDateTime today = LocalDateTime.now();
        TaskType taskType1 = taskTypeRepository.save(new TaskType()
                .setPriority(0)
                .setTimeReq(LocalTime.of(4, 0))
                .setName("Выезд на точку для стимулирования выдач")
                .setGrade(senior)
                .setCondition1("Дата выдачи последней карты более 7 дней назад, при этом есть одобренные заявки")
                .setCondition2("Дата выдачи последней карты более 14 дней назад")
        );
        TaskType taskType2 = taskTypeRepository.save(new TaskType()
                .setPriority(1)
                .setTimeReq(LocalTime.of(2, 0))
                .setName("Обучение агента")
                .setGrade(middle)
                .setCondition1("Отношение кол-ва выданных карт к одобренным заявкам менее 50%, если выдано больше 0 карт")
                .setCondition2("")
        );
        TaskType taskType3 = taskTypeRepository.save(new TaskType()
                .setPriority(2)
                .setTimeReq(LocalTime.of(1, 30))
                .setName("Доставка карт и материалов")
                .setGrade(junior)
                .setCondition1("Точка подключена вчера")
                .setCondition2("Карты и материалы не доставлялись")
        );

        Bank bank1 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Ставропольская, д. 140")
                .setRegistrationDate("Вчера")
                .setMaterialsDelivered(false)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(0)
                .setIssuanceCardsNum(0)
        );
        Bank bank2 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. Максима Горького, д. 128")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(3)
                .setApprovedApplicationsNum(15)
                .setIssuanceCardsNum(3)
        );
        Bank bank3 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, им. Дзержинского, д. 100")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(3)
                .setApprovedApplicationsNum(9)
                .setIssuanceCardsNum(1)
        );
        Bank bank4 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Красноармейская, д. 126")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(38)
                .setIssuanceCardsNum(23)
        );
        Bank bank5 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, х. Ленина, п/о. 37")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(false)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(14)
                .setIssuanceCardsNum(0)
        );
        Bank bank6 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, тер. Пашковский жилой массив, ул. Крылатая, д. 2")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(12)
                .setApprovedApplicationsNum(19)
                .setIssuanceCardsNum(1)
        );
        Bank bank7 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Восточно-Кругликовская, д. 64/2")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(27)
                .setApprovedApplicationsNum(19)
                .setIssuanceCardsNum(12)
        );
        Bank bank8 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Красных Партизан, д. 439")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(33)
                .setApprovedApplicationsNum(84)
                .setIssuanceCardsNum(63)
        );
        Bank bank9 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Таманская, д. 153 к. 3, кв. 2")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(2)
                .setApprovedApplicationsNum(15)
                .setIssuanceCardsNum(1)
        );
        Bank bank10 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. Дзержинского, д. 165")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(19)
                .setIssuanceCardsNum(0)
        );
        Bank bank11 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ст-ца. Елизаветинская, ул. Широкая, д. 260")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(15)
                .setApprovedApplicationsNum(29)
                .setIssuanceCardsNum(15)
        );
        Bank bank12 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. Тургенева, д. 174, 1 этаж")
                .setRegistrationDate("Вчера")
                .setMaterialsDelivered(false)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(0)
                .setIssuanceCardsNum(0)
        );
        Bank bank13 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Уральская, д. 162")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(4)
                .setApprovedApplicationsNum(21)
                .setIssuanceCardsNum(5)
        );
        Bank bank14 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Уральская, д. 79/1")
                .setRegistrationDate("Вчера")
                .setMaterialsDelivered(false)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(5)
                .setIssuanceCardsNum(0)
        );
        Bank bank15 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. Селезнева, д. 197/5")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(7)
                .setApprovedApplicationsNum(14)
                .setIssuanceCardsNum(3)
        );
        Bank bank16 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Уральская, д. 117")
                .setRegistrationDate("Вчера")
                .setMaterialsDelivered(false)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(0)
                .setIssuanceCardsNum(0)
        );
        Bank bank17 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Зиповская, д. 1")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(6)
                .setApprovedApplicationsNum(32)
                .setIssuanceCardsNum(9)
        );
        Bank bank18 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. 40-летия Победы, д. 20/1")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(4)
                .setApprovedApplicationsNum(35)
                .setIssuanceCardsNum(15)
        );
        Bank bank19 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. Атарбекова, д. 24")
                .setRegistrationDate("Вчера")
                .setMaterialsDelivered(false)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(6)
                .setIssuanceCardsNum(0)
        );
        Bank bank20 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. Героя Аверкиева А.А., д. 8")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(6)
                .setApprovedApplicationsNum(18)
                .setIssuanceCardsNum(6)
        );
        Bank bank21 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. Героя Аверкиева А.А., д. 8/1 к. мая, кв. 268")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(15)
                .setIssuanceCardsNum(5)
        );
        Bank bank22 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. Тургенева, д. 106")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(2)
                .setApprovedApplicationsNum(96)
                .setIssuanceCardsNum(20)
        );
        Bank bank23 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Красных Партизан, д. 117")
                .setRegistrationDate("Вчера")
                .setMaterialsDelivered(false)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(0)
                .setIssuanceCardsNum(0)
        );
        Bank bank24 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Северная, д. 389")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(16)
                .setIssuanceCardsNum(0)
        );
        Bank bank25 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Уральская, д. 166/3")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(3)
                .setApprovedApplicationsNum(43)
                .setIssuanceCardsNum(29)
        );
        Bank bank26 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Северная, д. 524")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(3)
                .setApprovedApplicationsNum(13)
                .setIssuanceCardsNum(14)
        );
        Bank bank27 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. Кирилла Россинского, д. 61/1")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(6)
                .setApprovedApplicationsNum(19)
                .setIssuanceCardsNum(5)
        );
        Bank bank28 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Коммунаров, д. 258")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(16)
                .setApprovedApplicationsNum(45)
                .setIssuanceCardsNum(30)
        );
        Bank bank29 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. Дзержинского, д. 100")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(1)
                .setApprovedApplicationsNum(19)
                .setIssuanceCardsNum(4)
        );
        Bank bank30 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Северная, д. 326")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(3)
                .setApprovedApplicationsNum(20)
                .setIssuanceCardsNum(9)
        );
        Bank bank31 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. 40-летия Победы, д. 34")
                .setRegistrationDate("Вчера")
                .setMaterialsDelivered(false)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(19)
                .setIssuanceCardsNum(0)
        );
        Bank bank32 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Красная, д. 176")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(76)
                .setApprovedApplicationsNum(82)
                .setIssuanceCardsNum(72)
        );
        Bank bank33 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Уральская, д. 79/1")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(23)
                .setApprovedApplicationsNum(32)
                .setIssuanceCardsNum(21)
        );
        Bank bank34 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Северная, д. 326")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(4)
                .setApprovedApplicationsNum(19)
                .setIssuanceCardsNum(4)
        );
        Bank bank35 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Красная, д. 149")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(9)
                .setApprovedApplicationsNum(10)
                .setIssuanceCardsNum(7)
        );
        Bank bank36 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, п. Березовый, ул. Целиноградская, д. 6/1")
                .setRegistrationDate("Вчера")
                .setMaterialsDelivered(false)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(13)
                .setIssuanceCardsNum(0)
        );
        Bank bank37 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. Дзержинского, д. 100")
                .setRegistrationDate("Вчера")
                .setMaterialsDelivered(false)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(10)
                .setIssuanceCardsNum(0)
        );
        Bank bank38 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Российская, д. 418")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(6)
                .setApprovedApplicationsNum(30)
                .setIssuanceCardsNum(14)
        );
        Bank bank39 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. им. Володи Головатого, д. 313")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(6)
                .setApprovedApplicationsNum(65)
                .setIssuanceCardsNum(12)
        );
        Bank bank40 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Красная, д. 145")
                .setRegistrationDate("Давно")
                .setMaterialsDelivered(true)
                .setLastCardIssuanceDays(3)
                .setApprovedApplicationsNum(20)
                .setIssuanceCardsNum(4)
        );
        Bank bank41 = bankRepository.save(new Bank()
                .setAddress("г. Краснодар, ул. Красная, д. 154")
                .setRegistrationDate("Вчера")
                .setMaterialsDelivered(false)
                .setLastCardIssuanceDays(0)
                .setApprovedApplicationsNum(0)
                .setIssuanceCardsNum(0)
        );
//        clearAll();
        //return Arrays.asList(senior, middle, junior, employee1, employee2);

        return Arrays.asList();
    }

    private void clearAll() {
        Stream.of(bankRepository, employeeRepository, gradeRepository, taskLogRepository, taskTypeRepository)
                .forEach(CrudRepository::deleteAll);
    }

}
