package com.fsegs.genie_logiciel_etude_cas_1.Services;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Utilisateur.UtilisateurPasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enseignant;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.EnseignantRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnseignantService {
    private final EnseignantRep enseignantRep;
    public EnseignantService(EnseignantRep enseignantRep) {
        this.enseignantRep = enseignantRep;
    }

    public int calculerM(Enseignant enseignant) {
        return enseignant.calculerM();
    }

    public Enseignant fetchEnseignantFromUsername(String username) throws UtilisateurPasTrouveeException {
        return enseignantRep.findByUsername(username).orElseThrow(() -> new UtilisateurPasTrouveeException("pas trouvee"));
    }

    public Enseignant fetchEnseignantFromUsernameAndPassword(String username, String password) throws UtilisateurPasTrouveeException {
        return enseignantRep
                .findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new UtilisateurPasTrouveeException("pas trouvee"));
    }

    public List<Enseignant> fetchAllEnseignants() {
        return enseignantRep.findAll();
    }
}
