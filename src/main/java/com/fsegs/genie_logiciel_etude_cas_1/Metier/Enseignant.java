package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.EtatSurveillant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"matieres", "seances"})
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Enseignant extends Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private int tel;
//    private String username;
//    private String password;

    private EtatSurveillant etatSurveillant;

    @ManyToOne
    private Grade grade;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"enseignants", "seance"})
    private Set<Matiere> matieres = new  HashSet<>();

    @Transient
    @JsonIgnore
    private int M = calculerM();

    private int anciennete = 0;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"enseignants", "matieres", "horaire"})
    private Set<Seance> seances = new HashSet<>();

    public int calculerM() {
        assert matieres != null;
        return (int) (matieres.stream()
                .mapToInt(Matiere::getNbPaquets)
                .reduce(0, Integer::sum) * 1.5);
    }

    public int calculerChargeSurveillance() {
        if (grade == null) return 0;
        int chargeEnseignement = grade.getChargeSurveillance();
        int nbSeancesMatieres = seances.size();
        return (int) ((chargeEnseignement * 1.5) - nbSeancesMatieres);
    }
}
