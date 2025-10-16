package com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO;

import jakarta.validation.constraints.Min;

public class GradeDTO {
    @Min(value = 1, message="Erreur grade < 1")
    public int grade;
    @Min(value = 1, message="Erreur charge < 1")
    public int chargeSurveillance;
}
