package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatiereTest {

    @Test
    void matiereCreationTest() {
        Matiere matiere = new Matiere();
        matiere.setId(1);
        matiere.setNom("matiere");
        matiere.setNbPaquets(5);

        Seance s = new  Seance();
        Set<Matiere> matieres = new HashSet<>();
        matieres.add(matiere);
        s.setMatieres(matieres);

        matiere.setSeance(s);

        assertEquals(1, s.getMatieres().stream().filter((e)->e.equals(matiere)).count());
        assertEquals(1, matiere.getId());
    }
}
