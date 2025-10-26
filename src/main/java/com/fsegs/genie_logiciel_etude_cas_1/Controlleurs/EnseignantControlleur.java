package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Grade.GradePasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Utilisateur.UtilisateurPasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.EnseignantDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Matiere;
import com.fsegs.genie_logiciel_etude_cas_1.Middleware.DTO.TokenDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.UtilisateurDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enseignant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.EtatSurveillant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Grade;
import com.fsegs.genie_logiciel_etude_cas_1.Middleware.JWTUtil;
import com.fsegs.genie_logiciel_etude_cas_1.Middleware.DTO.JwtResponse;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.EnseignantRep;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.GradeRep;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.MatiereRep;
import com.fsegs.genie_logiciel_etude_cas_1.Services.EnseignantService;
import com.fsegs.genie_logiciel_etude_cas_1.Services.UtilisateurService;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/enseignant")
public class EnseignantControlleur {
    @Autowired
    private EnseignantRep enseignantRep;
    @Autowired
    private GradeRep gradeRep;
    @Autowired
    private JWTUtil jwtUtil;

    private final EnseignantService enseignantService;
    private final UtilisateurService utilisateurService;
    @Autowired
    private MatiereRep matiereRep;

    public EnseignantControlleur(UtilisateurService utilisateurService, EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<Enseignant>> fetchEnseignant() {
        try {
            ArrayList<Enseignant> enseignants = (ArrayList<Enseignant>) enseignantService.fetchAllEnseignants();

            log.info("endpoint /fetch yielded: {} ", enseignants.size());

            return new ResponseEntity<>(enseignants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<Enseignant>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/profile")
    public ResponseEntity<Enseignant> displayProfileFromToken(@RequestBody @Valid TokenDTO tokenDTO) {
        try {
            log.debug("Token: {}", tokenDTO.token);

            if (jwtUtil.isTokenExpired(tokenDTO.token)) {
                throw new JwtException("token expired");
            }

            String username = jwtUtil.extractUsername(tokenDTO.token);
//            if (jwtUtil.validateToken(token, username)) {
//                throw new JwtException("unauthorized token");
//            }
            Enseignant trouve = enseignantService.fetchEnseignantFromUsername(username);

            return new  ResponseEntity<>(trouve, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UtilisateurDTO details) {
        try {
            log.debug("username: {} password: {}", details.username, details.password);
            // to be deleted
            Enseignant trouve = enseignantService.fetchEnseignantFromUsernameAndPassword(details.username, details.password);

            // verify with jwt
            UserDetails trouveeDetails = utilisateurService.loadUserByUsername(details.username);
            if (trouveeDetails == null) {
                throw new UtilisateurPasTrouveeException("pas trouvee");
            }

            // generateToken
            String token = jwtUtil.generateToken(trouveeDetails.getUsername());
            JwtResponse jwtResponse = new JwtResponse(token, jwtUtil.getIssuedAt(token));

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    //@PostMapping("/retreiveFromJwt")

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
    @Cascade(value = CascadeType.ALL) // ill change dtos later
    public ResponseEntity<?> edit(@RequestParam int id, @RequestBody EnseignantDTO details) {
        try {
            Enseignant tourve = enseignantRep.findById(id).orElseThrow(
                    () -> new UtilisateurPasTrouveeException("pas trouvee")
            );

            Grade trove = gradeRep.findById(details.gradeId).orElseThrow(()->new GradePasTrouveeException("grade pas trouvee"));
            Set<Matiere> trv = matiereRep.findAllByIdIn(details.matiere);
            tourve.setUsername(details.username);
            tourve.setPassword(details.password);
            tourve.setNom(details.nom);
            tourve.setPrenom(details.prenom);
            tourve.setGrade(trove);
            tourve.setTel(details.tel);
            tourve.setMatieres(trv);
            tourve.setEtatSurveillant(details.etatSurveillant);

            enseignantRep.save(tourve);

            return new ResponseEntity<>(details, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    //TODO: delete method, but only admin can do it
}
