package com.fsegs.genie_logiciel_etude_cas_1.Metier;

//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Embeddables.EmbHoraire;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"seances"})
@Entity
public class Horaire {
    @EmbeddedId
    private EmbHoraire embHoraire;

    public boolean twoHourDurationCheck(int horaireDebut, int horaireFin) {
        return (horaireFin - horaireDebut) == 2;
    }

    @OneToMany(mappedBy = "horaire", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"seances", "horaire", "enseignants", "matieres"})
    private Set<Seance> seances;
}
