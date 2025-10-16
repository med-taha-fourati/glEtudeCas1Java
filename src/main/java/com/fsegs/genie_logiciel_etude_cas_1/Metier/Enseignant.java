package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.Surveillant;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Enseignant extends Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private int tel;

    private Surveillant surveillant;

    @ManyToOne
    private Grade grade;

    @ManyToMany
    private Set<Matiere> matieres;

    @Transient
    private int M = calculerM();
    @ManyToMany
    private Set<Seance> seances = new HashSet<>(M);

    public int calculerM() {
        assert matieres != null;
        return (int) (matieres.stream()
                .mapToInt(Matiere::getNbPaquets)
                .reduce(0, Integer::sum) * 1.5);
    }
}
