package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.UtilisateurPasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.EnseignantDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.UtilisateurDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enseignant;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.EnseignantRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
            return new ResponseEntity<>(details, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
