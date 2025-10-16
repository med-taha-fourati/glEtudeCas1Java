package com.fsegs.genie_logiciel_etude_cas_1.Repertoires;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Horaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoraireRep extends JpaRepository<Horaire, Integer> {
}
