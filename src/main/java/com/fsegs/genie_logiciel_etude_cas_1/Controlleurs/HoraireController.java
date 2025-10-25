package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.HoraireDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Embeddables.EmbHoraire;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Horaire;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.HoraireRep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/horaire")
public class HoraireController {
    @Autowired
    private HoraireRep horaireRep;

    @GetMapping("/")
    public ResponseEntity<?> listeHoraire() {
        try {
            ArrayList<Horaire> trv = (ArrayList<Horaire>)horaireRep.findAll();
            return new ResponseEntity<>(trv, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> addHoraire(@RequestBody HoraireDTO horaireDTO) {
        try {
            Horaire horaire = new Horaire();
            horaire.setEmbHoraire(new EmbHoraire());
            horaire.getEmbHoraire().setHDebut(horaireDTO.hDebut);
            horaire.getEmbHoraire().setHFin(horaireDTO.hFin);

            horaireRep.save(horaire);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>("Erreur dans lenregistrement de horiaire", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editHoraire(@RequestParam int id, @RequestBody HoraireDTO horaireDTO) {
        try {
            Horaire horaire = horaireRep.findById(id).orElseThrow(RuntimeException::new);
            horaire.getEmbHoraire().setHDebut(horaireDTO.hDebut);
            horaire.getEmbHoraire().setHFin(horaireDTO.hFin);

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
