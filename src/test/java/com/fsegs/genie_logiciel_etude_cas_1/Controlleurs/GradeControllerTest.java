package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.GradeDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Grade;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.GradeRep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
class GradeControllerTest {

    @Mock
    private GradeRep gradeRep;

    private GradeController gradeController;

    @BeforeEach
    void setUp() {
        gradeController = new GradeController();
        ReflectionTestUtils.setField(gradeController, "gradeRep", gradeRep);
    }

    @Test
    void fetchGradeReturnsGrades() {
        List<Grade> grades = new ArrayList<>();
        grades.add(new Grade());
        when(gradeRep.findAll()).thenReturn(grades);

        ResponseEntity<List<Grade>> response = gradeController.fetchGrade();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(grades, response.getBody());
    }

    @Test
    void fetchGradeIdReturnsGrade() {
        Grade grade = new Grade();
        grade.setId(1);
        when(gradeRep.findById(1)).thenReturn(Optional.of(grade));

        ResponseEntity<Grade> response = gradeController.fetchGradeId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(grade, response.getBody());
    }

    @Test
    void addGradeSavesAndReturnsGrade() {
        GradeDTO dto = new GradeDTO();
        dto.grade = 2;
        dto.chargeSurveillance = 5;

        when(gradeRep.save(any(Grade.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Grade result = gradeController.addGrade(dto);

        assertNotNull(result);
        assertEquals(dto.grade, result.getGrade());
        assertEquals(dto.chargeSurveillance, result.getChargeSurveillance());
        verify(gradeRep).save(any(Grade.class));
    }
}
