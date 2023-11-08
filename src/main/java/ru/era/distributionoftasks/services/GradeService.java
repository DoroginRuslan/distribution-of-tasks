package ru.era.distributionoftasks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.era.distributionoftasks.entities.Grade;
import ru.era.distributionoftasks.repositories.GradeRepository;

import java.util.List;

@Service
public class GradeService {
    @Autowired
    GradeRepository gradeRepository;

    public List<Grade> getAll() {
        return (List<Grade>) gradeRepository.findAll();
    }

    public Grade addGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    public Grade updateGrade(Grade grade, Long gradeId) {
        return gradeRepository.save(grade.setId(gradeId));
    }

    public void deleteAllGrades() {
        gradeRepository.deleteAll();
    }

    public Grade getGrade(Long employeeId) {
        return gradeRepository.findById(employeeId).orElseThrow();
    }

    public List<Grade> updateGrades(List<Grade> grades) {
        return (List<Grade>) gradeRepository.saveAll(grades);
    }

    public void deleteGrade(Long gradeId) {
        gradeRepository.deleteById(gradeId);
    }
}
