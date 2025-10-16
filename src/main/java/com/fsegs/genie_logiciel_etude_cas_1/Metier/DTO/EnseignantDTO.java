package com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.Surveillant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Grade;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Matiere;

import java.util.Set;

public record EnseignantDTO(String username,
                            String password,
                            String nom,
                            int tel,
                            String prenom,
                            Grade grade,
                            Set<Matiere> matiere,
                            Surveillant surveillant
                            ) {
}
