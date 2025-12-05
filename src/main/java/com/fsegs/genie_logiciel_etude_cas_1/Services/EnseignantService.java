package com.fsegs.genie_logiciel_etude_cas_1.Services;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Utilisateur.UtilisateurPasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enseignant;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.EnseignantRep;
import jakarta.transaction.Transactional;
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

    public int calculerChargeSurveillance(Enseignant enseignant) {
        return enseignant.calculerChargeSurveillance();
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

    @Transactional
    public void recalculerCharges() {
        List<Enseignant> enseignants = enseignantRep.findAll();
        enseignants.forEach(e -> {
            e.setM(e.calculerM());
        });
        enseignantRep.saveAll(enseignants);
    }
}
