package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.HoraireDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Horaire;
import com.fsegs.genie_logiciel_etude_cas_1.Services.HoraireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(MockitoExtension.class)
class HoraireControllerTest {

    @Mock
    private HoraireService horaireService;

    private HoraireController horaireController;

    @BeforeEach
    void setUp() {
        horaireController = new HoraireController();
        ReflectionTestUtils.setField(horaireController, "horaireService", horaireService);
    }

    @Test
    void listeHoraireReturnsHoraires() {
        List<Horaire> horaires = new ArrayList<>();
        horaires.add(new Horaire());
        when(horaireService.getAllHoraires()).thenReturn(new ArrayList<>(horaires));

        ResponseEntity<?> response = horaireController.listeHoraire();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(horaires, response.getBody());
    }

    @Test
    void getHoraireReturnsHoraireWhenFound() {
        Horaire horaire = new Horaire();
        when(horaireService.getHoraireById(8, 10)).thenReturn(Optional.of(horaire));

        ResponseEntity<?> response = horaireController.getHoraire(8, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(horaire, response.getBody());
    }

    @Test
    void getHoraireReturnsNotFoundWhenMissing() {
        when(horaireService.getHoraireById(8, 10)).thenReturn(Optional.empty());

        ResponseEntity<?> response = horaireController.getHoraire(8, 10);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addHoraireCreatesHoraire() {
        HoraireDTO dto = new HoraireDTO();
        dto.hDebut = 8;
        dto.hFin = 10;

        Horaire horaire = new Horaire();
        when(horaireService.createHoraire(any(HoraireDTO.class))).thenReturn(horaire);

        ResponseEntity<?> response = horaireController.addHoraire(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(horaire, response.getBody());
    }

    @Test
    void editHoraireUpdatesHoraire() {
        HoraireDTO dto = new HoraireDTO();
        dto.hDebut = 8;
        dto.hFin = 10;

        Horaire updated = new Horaire();
        when(horaireService.updateHoraire(8, 9, dto)).thenReturn(updated);

        ResponseEntity<?> response = horaireController.editHoraire(8, 9, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
    }

    @Test
    void deleteHoraireDeletesAndReturnsOk() {
        ResponseEntity<?> response = horaireController.deleteHoraire(8, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(horaireService).deleteHoraire(8, 10);
    }
}
