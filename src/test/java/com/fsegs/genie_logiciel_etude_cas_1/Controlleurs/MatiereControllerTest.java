package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.MatiereDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Matiere;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Seance;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.MatiereRep;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.SeanceRep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
class MatiereControllerTest {

    @Mock
    private MatiereRep matiereRep;

    @Mock
    private SeanceRep seanceRep;

    private MatiereController matiereController;

    @BeforeEach
    void setUp() {
        matiereController = new MatiereController();
        ReflectionTestUtils.setField(matiereController, "matiereRep", matiereRep);
        ReflectionTestUtils.setField(matiereController, "seanceRep", seanceRep);
    }

    @Test
    void fetchAllReturnsMatieres() {
        List<Matiere> matieres = new ArrayList<>();
        matieres.add(new Matiere());
        when(matiereRep.findAll()).thenReturn(matieres);

        ResponseEntity<List<Matiere>> response = matiereController.fetchAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(matieres, response.getBody());
    }

    @Test
    void addMatiereSavesAndReturnsMatiere() {
        MatiereDTO dto = new MatiereDTO();
        dto.nom = "Test";
        dto.nbPaquets = 3;
        dto.seanceId = 1;

        Seance seance = new Seance();
        when(seanceRep.findById(1)).thenReturn(Optional.of(seance));
        when(matiereRep.save(any(Matiere.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<?> response = matiereController.addMatiere(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Matiere body = (Matiere) response.getBody();
        assertNotNull(body);
        assertEquals(dto.nom, body.getNom());
        assertEquals(dto.nbPaquets, body.getNbPaquets());
        verify(matiereRep).save(any(Matiere.class));
    }
}
