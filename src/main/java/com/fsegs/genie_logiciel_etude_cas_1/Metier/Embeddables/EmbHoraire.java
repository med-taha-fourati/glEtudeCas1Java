package com.fsegs.genie_logiciel_etude_cas_1.Metier.Embeddables;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class EmbHoraire {
    private int hDebut;
    private int hFin;
}
