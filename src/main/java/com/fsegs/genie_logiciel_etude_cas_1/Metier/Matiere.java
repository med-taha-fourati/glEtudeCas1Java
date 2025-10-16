package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Matiere {
    @Id private int id;
    private String nom;
    private int nbPaquets;

    @ManyToMany
    private Set<Enseignant> enseignants;

    @ManyToOne
    private Seance seance;
}
