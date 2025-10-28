package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.HoraireDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Horaire;
import com.fsegs.genie_logiciel_etude_cas_1.Services.HoraireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/horaire")
public class HoraireController {

    @Autowired
    private HoraireService horaireService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    @GetMapping("/")
    public ResponseEntity<?> listeHoraire() {
        try {
            List<Horaire> horaires = horaireService.getAllHoraires();
            return new ResponseEntity<>(horaires, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching horaires", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    @GetMapping("/get")
    public ResponseEntity<?> getHoraire(
            @RequestParam int hDebut,
            @RequestParam int hFin) {
        try {
            return horaireService.getHoraireById(hDebut, hFin)
                    .map(horaire -> new ResponseEntity<>(horaire, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            log.error("Error fetching horaire", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addHoraire(@RequestBody HoraireDTO horaireDTO) {
        try {
            Horaire horaire = horaireService.createHoraire(horaireDTO);
            return new ResponseEntity<>(horaire, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            log.error("Invalid horaire", ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("Error creating horaire", ex);
            return new ResponseEntity<>("Erreur dans l'enregistrement de horaire",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit")
    public ResponseEntity<?> editHoraire(
            @RequestParam int oldHDebut,
            @RequestParam int oldHFin,
            @RequestBody HoraireDTO horaireDTO) {
        try {
            Horaire horaire = horaireService.updateHoraire(oldHDebut, oldHFin, horaireDTO);
            return new ResponseEntity<>(horaire, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            log.error("Invalid horaire", ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            log.error("Error updating horaire", ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("Error updating horaire", ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteHoraire(
            @RequestParam int hDebut,
            @RequestParam int hFin) {
        try {
            horaireService.deleteHoraire(hDebut, hFin);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error deleting horaire", ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
