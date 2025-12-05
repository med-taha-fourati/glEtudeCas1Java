package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Grade.GradePasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.GradeDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Grade;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.GradeRep;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/grade")
@RestController
@CrossOrigin("*")
public class GradeController {
    @Autowired
    GradeRep gradeRep;

    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    @GetMapping("/")
    public ResponseEntity<List<Grade>> fetchGrade() {
        try {
            ArrayList<Grade> grades = (ArrayList<Grade>) gradeRep.findAll();

            return new ResponseEntity<>(grades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    @GetMapping("/fetch")
    public ResponseEntity<Grade> fetchGradeId(@Valid @RequestParam Integer gradeId) {
        try {
            Grade gradeTrv = gradeRep.findById(gradeId).orElseThrow(()-> new GradePasTrouveeException("grade pas trouvee"));
            return new ResponseEntity<Grade>(gradeTrv, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public Grade addGrade(@RequestBody GradeDTO grade) {
        try {
            Grade grade1 = new Grade();
            grade1.setChargeSurveillance(grade.chargeSurveillance);
            grade1.setGrade(grade.grade);

            gradeRep.save(grade1);

            return grade1;
        } catch (Exception exception) {
            return null;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit")
    public Grade editGrade(@RequestParam int id, @RequestBody GradeDTO grade) {
        try {
            Grade touvee = gradeRep.findById(id)
                    .orElseThrow(() -> new GradePasTrouveeException("Grade pas trouve"));

            touvee.setChargeSurveillance(grade.chargeSurveillance);
            touvee.setGrade(grade.grade);

            gradeRep.save(touvee);

            return touvee;

        } catch (Exception e) {
            return null;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public boolean deleteGrade(@RequestParam int id) {
        try {
            Grade touvee = gradeRep.findById(id)
                    .orElseThrow(() -> new GradePasTrouveeException("Grade pas trouve"));

            gradeRep.delete(touvee);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
