package com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Min;

public class HoraireDTO {
    @Min(value=1, message = "Heure debut invalide")
    public int hDebut;
    public int hFin;
}
