package com.fsegs.genie_logiciel_etude_cas_1.Repertoires;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeanceRep extends JpaRepository<Seance, Integer> {
}
