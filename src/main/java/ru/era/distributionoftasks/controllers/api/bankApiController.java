package ru.era.distributionoftasks.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.services.BankService;

import java.util.List;

@RestController
@RequestMapping("/api/banks")
public class bankApiController {
    @Autowired
    BankService bankService;

    @GetMapping("")
    public List<Bank> getAllBanks() {
        return bankService.getAll();
    }

    @PostMapping("")
    public Bank getBank(@RequestBody Bank bank) {
        return bankService.addBank(bank);
    }

    @PutMapping("")
    public List<Bank> updateBanks(@RequestBody List<Bank> banks) {
        return bankService.updateBanks(banks);
    }

    @DeleteMapping("")
    public void deleteAllBanks() {
        bankService.deleteAll();
    }

    @GetMapping("/{bankId}")
    public Bank getBank(@PathVariable Long bankId) {
        return bankService.getBank(bankId);
    }

    @PutMapping("/{bankId}")
    public Bank updateBank(@RequestBody Bank bank, @PathVariable Long bankId) {
        return bankService.updateBank(bank, bankId);
    }

    @DeleteMapping("/{bankId}")
    public void deleteBank(@PathVariable Long bankId) {
        bankService.deleteBank(bankId);
    }
}
