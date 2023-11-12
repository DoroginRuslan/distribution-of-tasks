package ru.era.distributionoftasks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.Bank;
import ru.era.distributionoftasks.graphhopper.RoutesService;
import ru.era.distributionoftasks.graphhopper.jsonobjects.Point;
import ru.era.distributionoftasks.repositories.BankRepository;
import ru.era.distributionoftasks.yandexgeocoder.YandexGeocoderService;
import ru.era.distributionoftasks.yandexgeocoder.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BankService {
    @Autowired
    BankRepository bankRepository;

    @Autowired
    RoutesService routesService;

    @Autowired
    YandexGeocoderService yandexGeocoderService;

    public List<Bank> getAll() {
        return (List<Bank>) bankRepository.findAll();
    }

    public Bank addBank(Bank bank) {
        fillGeoPoint(bank);
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
        if(!Objects.equals(bankRepository.findById(bankId).orElseThrow().getAddress(), bank.getAddress())){
            fillGeoPoint(bank);
        }
        return bankRepository.save(bank.setId(bankId));
    }

    public void deleteBank(Long bankId) {
        bankRepository.deleteById(bankId);
    }

    private void fillGeoPoint(Bank bank) {
        if(bank.getAddress() != null) {
            try {
                GeoPoint geoPont = yandexGeocoderService.sendRequestForConverting(bank.getAddress());
                bank.setLatitude(Double.toString(geoPont.lat));
                bank.setLongitude(Double.toString(geoPont.lon));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Bank> addBankList(List<Bank> banks) {
        List<Bank> result = new ArrayList<>(banks.size());
        for(Bank bank : banks) {
            result.add(addBank(bank));
        }
        return result;
    }
}
