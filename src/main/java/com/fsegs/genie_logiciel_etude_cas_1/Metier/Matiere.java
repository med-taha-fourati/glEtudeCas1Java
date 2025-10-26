package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"enseignants", "seance"})
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private int nbPaquets;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "matieres")
    //@JsonIgnoreProperties({"matieres", "seances", "grade"})
    @JsonIgnore
    private Set<Enseignant> enseignants;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JsonIgnoreProperties({"matieres", "enseignants", "horaire"})
    @JsonIgnore
    private Seance seance;
}
