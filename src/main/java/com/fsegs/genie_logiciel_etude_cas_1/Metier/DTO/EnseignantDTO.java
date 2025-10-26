package com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.EtatSurveillant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Matiere;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Set;

public class EnseignantDTO {
        @NotBlank(message = "Nom utilisateur pas vide")
        public String username;
        @NotBlank(message = "Mot de passe invalide")
        public String password;
        public String nom;
        @Min(value=10000000, message = "Minimum de 8 characteres")
        public int tel;
        public String prenom;
        public int gradeId;
        public Set<Integer> matiere;
        public EtatSurveillant etatSurveillant;

}
