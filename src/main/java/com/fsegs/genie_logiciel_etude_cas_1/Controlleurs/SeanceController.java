package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Grade.GradePasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.GradeDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.SeanceDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Grade;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Horaire;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Seance;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.HoraireRep;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.SeanceRep;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seance")
public class SeanceController {
    @Autowired
    private SeanceRep seanceRep;
    @Autowired
    private HoraireRep horaireRep;

    @PostMapping("/add")
    public ResponseEntity<?> addSeance(@RequestBody SeanceDTO seanceDTO) {
        try {
            Horaire horaireTrv = horaireRep.findById(seanceDTO.horaireId).orElseThrow(()->new Exception());

            Seance seance = new Seance();
            seance.setHoraire(horaireTrv);
            seance.setSeanceDate(seanceDTO.date);

            //NOTE: calculation of the N thing for the enseignants
            seanceRep.save(seance);

            return new ResponseEntity<>(seance, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editSeance(@RequestParam int id, @RequestBody SeanceDTO seance) {
        try {
            Seance touvee = seanceRep.findById(id)
                    .orElseThrow(() -> new GradePasTrouveeException("Grade pas trouve"));
            Horaire horaireTrv = horaireRep.findById(seance.horaireId).orElseThrow(()->new Exception());

            touvee.setHoraire(horaireTrv);
            touvee.setSeanceDate(seance.date);

            seanceRep.save(touvee);

            return new  ResponseEntity<>(seance, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSeance(@RequestParam int id) {
        try {
            Seance touvee = seanceRep.findById(id)
                    .orElseThrow(() -> new GradePasTrouveeException("Grade pas trouve"));

            seanceRep.delete(touvee);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
