package com.fsegs.genie_logiciel_etude_cas_1.Repertoires;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatiereRep extends JpaRepository<Matiere, Integer> {
}
