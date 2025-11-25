package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeanceTest {

    @Test
    void calculerNUsesEnseignantsAndMatieres() {
        Grade grade = new Grade();
        grade.setChargeSurveillance(8);

        Enseignant e1 = new Enseignant();
        e1.setId(1);
        e1.setGrade(grade);
        Enseignant e2 = new Enseignant();
        e2.setId(2);
        e2.setGrade(grade);

        Seance seance = new Seance();
        seance.setEnseignants(new HashSet<>());
        seance.getEnseignants().add(e1);
        seance.getEnseignants().add(e2);

        Matiere m1 = new Matiere();
        m1.setId(1);
        Matiere m2 = new Matiere();
        m2.setId(2);
        seance.setMatieres(new HashSet<>());
        seance.getMatieres().add(m1);
        seance.getMatieres().add(m2);

        int result = seance.calculerN();

        int expected = (int) ((8 + 8) * 1.5) - 2;
        assertEquals(expected, result);
    }

    @Test
    void calculerSurveillantsRequisReturnsZeroWhenNoMatiere() {
        Seance seance = new Seance();
        seance.setMatieres(null);

        assertEquals(0, seance.calculerSurveillantsRequis());
    }

    @Test
    void calculerSurveillantsRequisReturnsCeilOfNbPaquetsTimesOnePointFive() {
        Seance seance = new Seance();
        seance.setMatieres(new HashSet<>());
        Matiere m1 = new Matiere();
        m1.setNbPaquets(1);
        Matiere m2 = new Matiere();
        m2.setNbPaquets(2);
        seance.getMatieres().add(m1);
        seance.getMatieres().add(m2);

        int result = seance.calculerSurveillantsRequis();

        assertEquals((int) Math.ceil((1 + 2) * 1.5), result);
    }

    @Test
    void estSatureeDependsOnNombreEnseignantsEtSurveillantsRequis() {
        Seance seance = new Seance();
        seance.setMatieres(new HashSet<>());
        Matiere m1 = new Matiere();
        m1.setNbPaquets(1);
        Matiere m2 = new Matiere();
        m2.setNbPaquets(2);
        seance.getMatieres().add(m1);
        seance.getMatieres().add(m2);

        int requis = seance.calculerSurveillantsRequis();

        seance.setEnseignants(new HashSet<>());
        assertFalse(seance.estSaturee());

        for (int i = 0; i < requis; i++) {
            Enseignant e1 = new Enseignant();
            e1.setId(i);
            seance.getEnseignants().add(e1);
        }

        assertTrue(seance.estSaturee());
    }
}
