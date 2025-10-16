package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seance {
    @Id private int id;
    private LocalDate date;

    @Transient
    private int N;

    @ManyToMany
    private Map<Integer, Enseignant> enseignants = new HashMap<>(N);

    @OneToMany(mappedBy = "matiere")
    private Set<Matiere> matieres;

    @ManyToOne
    private Horaire horaire;

    public int calculerN() {
        assert matieres != null;
        return (int) (enseignants.values().stream()
                .map((k)->k.getGrade().getChargeSurveillance())
                .reduce(0, Integer::sum) * 1.5) - (matieres.size());
    }
}
