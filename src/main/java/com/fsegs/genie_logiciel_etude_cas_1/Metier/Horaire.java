package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Horaire {
    @Id private int id;
    private int hDebut;
    private int hFin;

    @OneToMany(mappedBy = "horaire")
    private Set<Seance> seances;
}
