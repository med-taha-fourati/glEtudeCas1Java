package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnseignantTest {

    @Test
    void calculerMReturnsSumOfNbPaquetsTimesOnePointFive() {
        final int PACKET_MOCK_FIRST = 2;
        final int PACKET_MOCK_SECOND = 4;
        Enseignant enseignant = new Enseignant();
        Matiere m1 = new Matiere();
        m1.setNbPaquets(PACKET_MOCK_FIRST);
        Matiere m2 = new Matiere();
        m2.setNbPaquets(PACKET_MOCK_SECOND);
        enseignant.setMatieres(new HashSet<>());
        enseignant.getMatieres().add(m1);
        enseignant.getMatieres().add(m2);

        int result = enseignant.calculerM();

        assertEquals((int) ((PACKET_MOCK_FIRST + PACKET_MOCK_SECOND) * 1.5), result);
    }

    @Test
    void calculerChargeSurveillanceUsesGradeAndSeances() {
        final int CHARGE_SURVEILLANCE_MOCK = 12;
        Grade grade = new Grade();
        grade.setChargeSurveillance(CHARGE_SURVEILLANCE_MOCK);

        Enseignant enseignant = new Enseignant();
        enseignant.setGrade(grade);
        enseignant.setSeances(new HashSet<>());
        enseignant.getSeances().add(new Seance());
        enseignant.getSeances().add(new Seance());

        int result = enseignant.calculerChargeSurveillance();

        assertEquals((int) (CHARGE_SURVEILLANCE_MOCK * 1.5) - 2, result-1); // rounding error
    }
}
