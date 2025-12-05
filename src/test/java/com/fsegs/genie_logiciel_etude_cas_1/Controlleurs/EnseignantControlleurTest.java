package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.EnseignantDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.CalculerMResponse;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.UtilisateurDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enseignant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.EtatSurveillant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Grade;
import com.fsegs.genie_logiciel_etude_cas_1.Middleware.DTO.JwtResponse;
import com.fsegs.genie_logiciel_etude_cas_1.Middleware.JWTUtil;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.EnseignantRep;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.GradeRep;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.MatiereRep;
import com.fsegs.genie_logiciel_etude_cas_1.Services.EnseignantService;
import com.fsegs.genie_logiciel_etude_cas_1.Services.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnseignantControlleurTest {

    @Mock
    private EnseignantRep enseignantRep;

    @Mock
    private GradeRep gradeRep;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private MatiereRep matiereRep;

    @Mock
    private EnseignantService enseignantService;

    @Mock
    private UtilisateurService utilisateurService;

    private EnseignantControlleur enseignantControlleur;

    @BeforeEach
    void setUp() {
        enseignantControlleur = new EnseignantControlleur(utilisateurService, enseignantService);
        ReflectionTestUtils.setField(enseignantControlleur, "enseignantRep", enseignantRep);
        ReflectionTestUtils.setField(enseignantControlleur, "gradeRep", gradeRep);
        ReflectionTestUtils.setField(enseignantControlleur, "jwtUtil", jwtUtil);
        ReflectionTestUtils.setField(enseignantControlleur, "matiereRep", matiereRep);
    }

    @Test
    void fetchEnseignantReturnsList() {
        List<Enseignant> list = new ArrayList<>();
        list.add(new Enseignant());
        when(enseignantService.fetchAllEnseignants()).thenReturn(list);

        ResponseEntity<List<Enseignant>> response = enseignantControlleur.fetchEnseignant();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void loginReturnsTokenOnSuccess() {
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.username = "user";
        dto.password = "pass";

        Enseignant enseignant = new Enseignant();
        when(enseignantService.fetchEnseignantFromUsernameAndPassword("user", "pass")).thenReturn(enseignant);

        UserDetails userDetails = User.withUsername("user").password("pass").roles("USER").build();
        when(utilisateurService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtUtil.generateToken("user")).thenReturn("token");
        when(jwtUtil.getIssuedAt("token")).thenReturn(new Date());

        ResponseEntity<?> response = enseignantControlleur.login(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Object body = response.getBody();
        assertNotNull(body);
        JwtResponse jwtResponse = (JwtResponse) body;
        assertEquals("token", jwtResponse.getToken());
    }

    @Test
    void registerCreatesEnseignant() {
        EnseignantDTO dto = new EnseignantDTO();
        dto.username = "user";
        dto.password = "pass";
        dto.nom = "Nom";
        dto.prenom = "Prenom";
        dto.tel = 123;
        dto.gradeId = 1;

        Grade grade = new Grade();
        when(gradeRep.findById(1)).thenReturn(Optional.of(grade));
        when(enseignantService.calculerM(any(Enseignant.class))).thenReturn(5);
        when(enseignantRep.save(any(Enseignant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<?> response = enseignantControlleur.register(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(enseignantRep).save(any(Enseignant.class));
    }

    @Test
    void calculerMReturnsCalculerMResponse() {
        Enseignant enseignant = new Enseignant();
        when(enseignantRep.findById(1)).thenReturn(Optional.of(enseignant));
        when(enseignantService.calculerM(enseignant)).thenReturn(35);

        ResponseEntity<?> response = enseignantControlleur.calculerM(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        CalculerMResponse calculerMResponse = (CalculerMResponse) response.getBody();
        assertEquals(1, calculerMResponse.getEnseignantId());
        assertEquals(35, calculerMResponse.getM());
    }

    @Test
    void calculerMReturnsInternalServerErrorWhenEnseignantNotFound() {
        when(enseignantRep.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = enseignantControlleur.calculerM(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erreur lors du calcul de M pour l'enseignant", response.getBody());
    }
}
