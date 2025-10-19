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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"enseignants", "matieres", "horaire"})
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Seance {
    @Id private int id;
    @Column(name = "\"DATE\"")
    private LocalDate seanceDate;

    @Transient
    @JsonIgnore
    private int N;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "seances")
    @JsonIgnoreProperties({"seances", "matieres", "grade"})
    private Set<Enseignant> enseignants = new HashSet<>();

    @OneToMany(mappedBy = "seance", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"seance", "enseignants"})
    private Set<Matiere> matieres = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"seances"})
    private Horaire horaire;

    public int calculerN() {
        assert matieres != null;
        return (int) (enseignants.stream()
                .map((k)->k.getGrade().getChargeSurveillance())
                .reduce(0, Integer::sum) * 1.5) - (matieres.size());
    }
}
