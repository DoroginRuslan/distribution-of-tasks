package ru.era.distributionoftasks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.repositories.BankRepository;

import java.util.List;

@Service
public class BankService {
    @Autowired
    BankRepository bankRepository;

    public List<Bank> getAll() {
        return (List<Bank>) bankRepository.findAll();
    }

    public Bank addBank(Bank bank) {
        return bankRepository.save(bank);
    }

    public List<Bank> updateBanks(List<Bank> banks) {
        return (List<Bank>) bankRepository.saveAll(banks);
    }

    public void deleteAll() {
        bankRepository.deleteAll();
    }

    public Bank getBank(Long bankId) {
        return bankRepository.findById(bankId).orElseThrow();
    }

    public Bank updateBank(Bank bank, Long bankId) {
        return bankRepository.save(bank.setId(bankId));
    }

    public void deleteBank(Long bankId) {
        bankRepository.deleteById(bankId);
    }
}
