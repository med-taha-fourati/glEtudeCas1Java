package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.UtilisateurPasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.EnseignantDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.UtilisateurDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enseignant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.Surveillant;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.EnseignantRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enseignant")
public class EnseignantControlleur {
    @Autowired
    private EnseignantRep enseignantRep;

    @PostMapping("/login")
    public ResponseEntity<?> login(UtilisateurDTO details) {
        try {
            Enseignant trouve = enseignantRep
                    .findAllByUsername(details.username())
                    .orElseThrow(() -> new UtilisateurPasTrouveeException("pas trouvee"));
            return new ResponseEntity<>(trouve, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(EnseignantDTO details) {
        try {
            // Enseignant uh .. new something im tired
            Enseignant nouvel = new Enseignant();
            nouvel.setUsername(details.username());
            nouvel.setPassword(details.password());
            nouvel.setNom(details.nom());
            nouvel.setPrenom(details.prenom());
            nouvel.setGrade(details.grade());
            nouvel.setTel(details.tel());
            nouvel.setSurveillant(Surveillant.PAS_SURVEILLANT); // par default

            enseignantRep.save(nouvel);

            return new ResponseEntity<>(details, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam int id, EnseignantDTO details) {
        try {
            Enseignant tourve = enseignantRep.findById(id).orElseThrow(
                    () -> new UtilisateurPasTrouveeException("pas trouvee")
            );
            tourve.setUsername(details.username());
            tourve.setPassword(details.password());
            tourve.setNom(details.nom());
            tourve.setPrenom(details.prenom());
            tourve.setGrade(details.grade());
            tourve.setTel(details.tel());
            tourve.setSurveillant(details.surveillant());

            enseignantRep.save(tourve);

            return new ResponseEntity<>(details, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    //TODO: delete method, but only admin can do it
}
