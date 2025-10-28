package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Seance.SeancePasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.SeanceDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Seance;
import com.fsegs.genie_logiciel_etude_cas_1.Services.SeanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
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

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/disponibles")
    public ResponseEntity<?> fetchDisponibles() {
        try {
            List<Seance> seances = seanceService.getSeancesDisponibles();
            return new ResponseEntity<>(seances, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching available seances", e);
            return new ResponseEntity<>("Erreur lors de la recuperation des seances disponibles",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @PostMapping("/soumettre-voeu")
    public ResponseEntity<?> soumettreVoeu(@RequestParam int enseignantId, @RequestParam int seanceId) {
        try {
            seanceService.soumettreVoeu(enseignantId, seanceId);
            return new ResponseEntity<>("Voeu soumis avec succes", HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.error("Error submitting wish", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error submitting wish", e);
            return new ResponseEntity<>("Erreur lors de la soumission du voeu",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/verrouiller")
    public ResponseEntity<?> verrouillerCalendrier(@RequestParam boolean verrouiller) {
        try {
            seanceService.verrouillerCalendrier(verrouiller);
            String message = verrouiller ? "Calendrier verrouille" : "Calendrier deverrouille";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error locking/unlocking calendar", e);
            return new ResponseEntity<>("Erreur lors du verrouillage du calendrier",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/affecter-automatiquement")
    public ResponseEntity<?> affecterAutomatiquement() {
        try {
            seanceService.affecterAutomatiquement();
            return new ResponseEntity<>("Affectations automatiques effectuees avec succes", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error during automatic assignment", e);
            return new ResponseEntity<>("Erreur lors des affectations automatiques",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
