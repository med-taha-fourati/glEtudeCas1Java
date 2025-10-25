package com.fsegs.genie_logiciel_etude_cas_1.Repertoires;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Embeddables.EmbHoraire;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmbHoraireRep extends JpaRepository<EmbHoraire, EmbHoraire> {
    Optional<EmbHoraire> findByHDebutAndHFin(int hDebut, int hFin);
}
