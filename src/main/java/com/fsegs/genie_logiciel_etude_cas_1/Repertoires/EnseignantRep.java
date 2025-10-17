package com.fsegs.genie_logiciel_etude_cas_1.Repertoires;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnseignantRep extends JpaRepository<Enseignant, Integer> {
    Optional<Enseignant> findByUsername(String username);
    Optional<Enseignant> findByUsernameAndPassword(String username, String password);
}
