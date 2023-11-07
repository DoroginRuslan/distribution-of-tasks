package ru.era.distributionoftasks.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.era.distributionoftasks.entities.Grade;
import ru.era.distributionoftasks.services.GradeService;

import java.util.List;

@RestController
@RequestMapping("api/grades")
public class gradeApiController {
    @Autowired
    GradeService gradeService;

    @GetMapping("")
    public List<Grade> getAllGrades() {
        return gradeService.getAll();
    }

    @PostMapping("")
    public Grade addGrade(@RequestBody Grade grade) {
        return gradeService.addGrade(grade);
    }

    @PutMapping("")
    public List<Grade> updateGrade(@RequestBody List<Grade> grades) {
        return gradeService.updateGrades(grades);
    }

    @DeleteMapping("")
    public void deleteAllGrades() {
        gradeService.deleteAllGrades();
    }

    @GetMapping("/{gradeId}")
    public Grade getGrade(@PathVariable Long gradeId) {
        return gradeService.getGrade(gradeId);
    }

    @PutMapping("/{gradeId}")
    public Grade updateGrade(@RequestBody Grade grade, @PathVariable Long gradeId) {
        return gradeService.updateGrade(grade, gradeId);
    }

    @DeleteMapping("/{gradeId}")
    public void deleteGrade(@PathVariable Long gradeId) {
        gradeService.deleteGrade(gradeId);
    }

}
