package com.fsegs.genie_logiciel_etude_cas_1.Metier.Embeddables;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmbHoraire {
    private int hDebut;
    private int hFin;
}
