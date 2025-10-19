package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Grade.GradePasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Utilisateur.UtilisateurPasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.EnseignantDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.UtilisateurDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enseignant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.EtatSurveillant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Grade;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.EnseignantRep;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.GradeRep;
import com.fsegs.genie_logiciel_etude_cas_1.Services.EnseignantService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/enseignant")
public class EnseignantControlleur {
    @Autowired
    private EnseignantRep enseignantRep;
    @Autowired
    private GradeRep gradeRep;

    private final EnseignantService enseignantService;
    public EnseignantControlleur(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Enseignant>> fetchEnseignant() {
        try {
            ArrayList<Enseignant> enseignants = (ArrayList<Enseignant>) enseignantRep.findAll();

            log.info("/fetch yielded: {} ", enseignants.size());

            return new ResponseEntity<>(enseignants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<Enseignant>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(UtilisateurDTO details) {
        try {
            Enseignant trouve = enseignantRep
                    .findByUsernameAndPassword(details.username, details.password)
                    .orElseThrow(() -> new UtilisateurPasTrouveeException("pas trouvee"));
            return new ResponseEntity<>(trouve, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody EnseignantDTO details) {
        try {
            // Enseignant uh .. new something im tired

            Grade er = gradeRep.findById(details.gradeId).orElseThrow(()->new GradePasTrouveeException("grade pas trouvee"));

            Enseignant nouvel = new Enseignant();
            //logger.info(String.valueOf(details.tel));
            nouvel.setUsername(details.username);
            nouvel.setPassword(details.password);
            nouvel.setNom(details.nom);
            nouvel.setPrenom(details.prenom);

            nouvel.setGrade(er);
            nouvel.setTel(details.tel);
            nouvel.setEtatSurveillant(EtatSurveillant.PAS_SURVEILLANT); // par default

            //NOTE: for further testing
            nouvel.setM(enseignantService.calculerM(nouvel));

            enseignantRep.save(nouvel);

            log.info("Enseignant cree avec succes {}", nouvel.getNom());


            return new ResponseEntity<>(details, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam int id, @RequestBody EnseignantDTO details) {
        try {
            Enseignant tourve = enseignantRep.findById(id).orElseThrow(
                    () -> new UtilisateurPasTrouveeException("pas trouvee")
            );

            Grade trove = gradeRep.findById(tourve.getGrade().getId()).orElseThrow(()->new GradePasTrouveeException("grade pas trouvee"));
            tourve.setUsername(details.username);
            tourve.setPassword(details.password);
            tourve.setNom(details.nom);
            tourve.setPrenom(details.prenom);
            tourve.setGrade(trove);
            tourve.setTel(details.tel);
            tourve.setEtatSurveillant(details.etatSurveillant);

            enseignantRep.save(tourve);

            return new ResponseEntity<>(details, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    //TODO: delete method, but only admin can do it
}
