package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.SeanceDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.CalculerNResponse;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Seance;
import com.fsegs.genie_logiciel_etude_cas_1.Services.SeanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeanceControllerTest {

    @Mock
    private SeanceService seanceService;

    private SeanceController seanceController;

    @BeforeEach
    void setUp() {
        seanceController = new SeanceController(seanceService);
    }

    @Test
    void fetchAllReturnsSeances() {
        List<Seance> seances = new ArrayList<>();
        seances.add(new Seance());
        when(seanceService.getAllSeances()).thenReturn(seances);

        ResponseEntity<?> response = seanceController.fetchAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(seances, response.getBody());
    }

    @Test
    void fetchReturnsSeanceById() {
        Seance seance = new Seance();
        when(seanceService.getSeanceById(1)).thenReturn(Optional.of(seance));

        ResponseEntity<?> response = seanceController.fetch(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(seance, response.getBody());
    }

    @Test
    void addSeanceCreatesSeance() {
        SeanceDTO dto = new SeanceDTO();
        dto.jour = 1;
        dto.mois = 1;
        dto.annee = 2025;
        dto.horaireHDebut = 8;
        dto.horaireHFin = 10;

        Seance seance = new Seance();
        when(seanceService.createSeance(any(SeanceDTO.class))).thenReturn(seance);

        ResponseEntity<?> response = seanceController.addSeance(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(seance, response.getBody());
    }

    @Test
    void soumettreVoeuReturnsOkOnSuccess() {
        doNothing().when(seanceService).soumettreVoeu(1, 2);

        ResponseEntity<?> response = seanceController.soumettreVoeu(1, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void soumettreVoeuReturnsBadRequestOnIllegalState() {
        RuntimeException ex = new IllegalStateException("error");
        org.mockito.Mockito.doThrow(ex).when(seanceService).soumettreVoeu(1, 2);

        ResponseEntity<?> response = seanceController.soumettreVoeu(1, 2);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("error", response.getBody());
    }

    @Test
    void terminerExamenSeanceReturnsOk() {
        when(seanceService.terminerSeance(1)).thenReturn(true);

        ResponseEntity<?> response = seanceController.terminerExamenSeance(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void calculerNReturnsCalculerNResponse() {
        Seance seance = new Seance();
        when(seanceService.getSeanceById(1)).thenReturn(Optional.of(seance));
        when(seanceService.calculerN(seance)).thenReturn(42);

        ResponseEntity<?> response = seanceController.calculerN(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        CalculerNResponse calculerNResponse = (CalculerNResponse) response.getBody();
        assertEquals(1, calculerNResponse.getSeanceId());
        assertEquals(42, calculerNResponse.getN());
    }

    @Test
    void calculerNReturnsInternalServerErrorWhenSeanceNotFound() {
        when(seanceService.getSeanceById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = seanceController.calculerN(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erreur lors du calcul de N pour la seance", response.getBody());
    }
}
