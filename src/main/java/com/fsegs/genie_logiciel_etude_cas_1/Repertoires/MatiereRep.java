package com.fsegs.genie_logiciel_etude_cas_1.Repertoires;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MatiereRep extends JpaRepository<Matiere, Integer> {
    Set<Matiere> findAllByIdIn(Set<Integer> ids);
}
