package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.HoraireDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Horaire;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.HoraireRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/horaire")
public class HoraireController {
    @Autowired
    private HoraireRep horaireRep;

    @PostMapping("/add")
    public ResponseEntity<?> addHoraire(@RequestBody HoraireDTO horaireDTO) {
        try {
            Horaire horaire = new Horaire();
            horaire.getHoraireId().setHDebut(horaireDTO.hDebut);
            horaire.getHoraireId().setHFin(horaireDTO.hFin);

            horaireRep.save(horaire);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erreur dans lenregistrement de horiaire", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editHoraire(@RequestParam int id, @RequestBody HoraireDTO horaireDTO) {
        try {
            Horaire horaire = horaireRep.findById(id).orElseThrow(RuntimeException::new);
            horaire.getHoraireId().setHDebut(horaireDTO.hDebut);
            horaire.getHoraireId().setHFin(horaireDTO.hFin);

            horaireRep.save(horaire);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteHoraire(@RequestParam int id) {
        try {
            Horaire horaire = horaireRep.findById(id).orElseThrow(RuntimeException::new);

            horaireRep.delete(horaire);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
