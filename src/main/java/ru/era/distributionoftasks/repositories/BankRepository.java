package ru.era.distributionoftasks.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.era.distributionoftasks.entities.Bank;

public interface BankRepository extends CrudRepository<Bank, Long> {
}
