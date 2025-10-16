package com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.Surveillant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Grade;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Matiere;
import jakarta.validation.constraints.Min;
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
        public Grade grade;
        public Set<Matiere> matiere;
        public Surveillant surveillant;

}
