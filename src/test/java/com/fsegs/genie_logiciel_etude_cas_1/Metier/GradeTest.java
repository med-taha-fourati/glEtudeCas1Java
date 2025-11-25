package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.EtatSurveillant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.Role;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GradeTest {

    @Test
    void gradeCreationTest() {
        Grade grade = new Grade();
        grade.setGrade(1);
        grade.setChargeSurveillance(5);

        Enseignant  enseignant = new Enseignant();
        enseignant.setNom("Foulen");
        enseignant.setPrenom("Foulen");
        enseignant.setTel(97234983);
        enseignant.setEtatSurveillant(EtatSurveillant.PAS_SURVEILLANT);
        enseignant.setUsername("Foulen");
        enseignant.setPassword("Foulen");
        enseignant.setRole(Role.ENSEIGNANT);

        enseignant.setGrade(grade);

        assertEquals(1, enseignant.getGrade().getGrade());
        assertEquals(5, enseignant.getGrade().getChargeSurveillance());
    }
}
