package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Seance.SeancePasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.SeanceDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Seance;
import com.fsegs.genie_logiciel_etude_cas_1.Services.SeanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/seance")
public class SeanceController {
    private final SeanceService seanceService;

    public SeanceController(SeanceService seanceService) {
        this.seanceService = seanceService;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<?> fetchAll() {
        try {
            List<Seance> seances = seanceService.getAllSeances();
            return new ResponseEntity<>(seances, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all seances", e);
            return new ResponseEntity<>("Erreur lors de la recuperation des seances",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<?> fetch(@RequestParam("id") int id) {
        try {
            Seance seance = seanceService.getSeanceById(id)
                    .orElseThrow(() -> new SeancePasTrouveeException("Seance pas trouvee avec id: " + id));

            return new ResponseEntity<>(seance, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching seance with id: {}", id, e);
            return new ResponseEntity<>("Erreur lors de la recuperation de la seance",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSeance(@RequestBody SeanceDTO seanceDTO) {
        try {
            Seance savedSeance = seanceService.createSeance(seanceDTO);
            return new ResponseEntity<>(savedSeance, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error adding seance", e);
            return new ResponseEntity<>("Erreur lors de l'ajout de la seance",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editSeance(@RequestParam int id, @RequestBody SeanceDTO seanceDTO) {
        try {
            Seance updatedSeance = seanceService.updateSeance(id, seanceDTO);
            return new ResponseEntity<>(updatedSeance, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error editing seance with id: {}", id, e);
            return new ResponseEntity<>("Erreur lors de la modification de la seance",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSeance(@RequestParam int id) {
        try {
            seanceService.deleteSeance(id);
            return new ResponseEntity<>("Seance supprimee avec succes", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting seance with id: {}", id, e);
            return new ResponseEntity<>("Erreur lors de la suppression de la seance",
                    HttpStatus.BAD_REQUEST);
        }
    }
}