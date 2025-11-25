package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Embeddables.EmbHoraire;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HoraireTest {
    @Test
    void creationHoraire() {
        int heureDebut = 10;
        int heureFin = 12;

        EmbHoraire emb = new  EmbHoraire(heureDebut, heureFin);
        Horaire horaire = new Horaire();
        horaire.setEmbHoraire(emb);
        horaire.setSeances(new HashSet<>());

        assertEquals(heureDebut - heureFin, horaire.getEmbHoraire().getHDebut() - horaire.getEmbHoraire().getHFin());
    }
}
