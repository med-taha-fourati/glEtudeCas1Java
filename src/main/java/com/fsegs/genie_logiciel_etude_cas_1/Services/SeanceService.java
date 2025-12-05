package com.fsegs.genie_logiciel_etude_cas_1.Services;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Seance.SeancePasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.SeanceDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Embeddables.EmbHoraire;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enseignant;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Horaire;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Seance;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.EnseignantRep;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.HoraireRep;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.SeanceRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeanceService {
    private final SeanceRep seanceRep;
    private final HoraireRep horaireRep;
    private final EnseignantRep enseignantRep;

    public SeanceService(SeanceRep seanceRep, HoraireRep horaireRep, EnseignantRep enseignantRep) {
        this.seanceRep = seanceRep;
        this.horaireRep = horaireRep;
        this.enseignantRep = enseignantRep;
    }

    public int calculerN(Seance seance) {
        return seance.calculerN();
    }

    public int calculerSurveillantsRequis(Seance seance ) {
        return seance.calculerSurveillantsRequis();
    }

    public List<Seance> getAllSeances() {
        return seanceRep.findAll();
    }

    public List<Seance> getSeancesDisponibles() {
        return seanceRep.findAll().stream()
                .filter(s -> !s.isVerrouillee() && !s.estSaturee())
                .collect(Collectors.toList());
    }

    public Optional<Seance> getSeanceById(int id) {
        return seanceRep.findById(id);
    }

    @Transactional
    public Seance createSeance(SeanceDTO seanceDTO) {
        EmbHoraire embHoraire = new EmbHoraire(seanceDTO.horaireHDebut, seanceDTO.horaireHFin);

        Horaire horaire = horaireRep.findById(embHoraire)
                .orElseThrow(() -> new SeancePasTrouveeException("Horaire pas trouve avec hDebut: "
                        + seanceDTO.horaireHDebut + " et hFin: " + seanceDTO.horaireHFin));

        Seance seance = new Seance();
        seance.setHoraire(horaire);
        seance.setSeanceDate(LocalDate.of(seanceDTO.annee, seanceDTO.mois, seanceDTO.jour));
        seance.setVerrouillee(false);

        seance.setN(calculerN(seance));

        return seanceRep.save(seance);
    }

    @Transactional
    public Seance updateSeance(int id, SeanceDTO seanceDTO) {
        Seance seance = seanceRep.findById(id)
                .orElseThrow(() -> new SeancePasTrouveeException("Seance pas trouvee avec id: " + id));

        EmbHoraire embHoraire = new EmbHoraire(seanceDTO.horaireHDebut, seanceDTO.horaireHFin);

        Horaire horaire = horaireRep.findById(embHoraire)
                .orElseThrow(() -> new SeancePasTrouveeException("Horaire pas trouve avec hDebut: "
                        + seanceDTO.horaireHDebut + " et hFin: " + seanceDTO.horaireHFin));
        seance.setHoraire(horaire);
        seance.setSeanceDate(LocalDate.of(seanceDTO.annee, seanceDTO.mois, seanceDTO.jour));
        seance.setN(calculerN(seance));

        return seanceRep.save(seance);
    }

    @Transactional
    public void deleteSeance(int id) {
        Seance seance = seanceRep.findById(id)
                .orElseThrow(() -> new SeancePasTrouveeException("Seance pas trouvee avec id: " + id));
        seanceRep.delete(seance);
    }

    public boolean existsById(int id) {
        return seanceRep.existsById(id);
    }

    private boolean enseignantASeanceEnConflit(Enseignant enseignant, LocalDate date, Horaire horaire) {
        return enseignant.getSeances().stream()
                .anyMatch(s -> s.getSeanceDate().equals(date) &&
                        s.getHoraire().getEmbHoraire().equals(horaire.getEmbHoraire()));
    }

    @Transactional
    public void soumettreVoeu(int enseignantId, int seanceId) {
        Enseignant enseignant = enseignantRep.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant pas trouve"));

        Seance seance = seanceRep.findById(seanceId)
                .orElseThrow(() -> new SeancePasTrouveeException("Seance pas trouvee"));

        if (seance.isVerrouillee()) {
            throw new IllegalStateException("Le calendrier est verrouille, impossible de soumettre des voeux");
        }

        if (seance.estSaturee()) {
            throw new IllegalStateException("Cette seance est saturee");
        }

        if (enseignant.getSeances().contains(seance)) {
            throw new IllegalStateException("Vous avez deja soumis un voeu pour cette seance");
        }

        if (enseignantASeanceEnConflit(enseignant, seance.getSeanceDate(), seance.getHoraire())) {
            throw new IllegalStateException("Vous avez deja une seance a cet horaire");
        }

        enseignant.getSeances().add(seance);
        seance.getEnseignants().add(enseignant);

        enseignantRep.save(enseignant);
        seanceRep.save(seance);
    }

    @Transactional
    public void verrouillerCalendrier(boolean verrouiller) {
        List<Seance> seances = seanceRep.findAll();
        seances.forEach(s -> s.setVerrouillee(verrouiller));
        seanceRep.saveAll(seances);
    }

    @Transactional
    public void affecterAutomatiquement() {
        List<Seance> seances = seanceRep.findAll();
        List<Enseignant> enseignants = enseignantRep.findAll();

        List<Enseignant> enseignantsTriees = enseignants.stream()
                .sorted(Comparator.comparing(Enseignant::getAnciennete).reversed()
                        .thenComparing(Enseignant::calculerChargeSurveillance))
                .toList();

        for (Seance seance : seances) {
            int requis = seance.calculerSurveillantsRequis();
            int actuels = seance.getEnseignants().size();

            // Skip if already has enough surveillants
            if (actuels >= requis) {
                continue;
            }

            int aAffecter = requis - actuels;

            for (Enseignant enseignant : enseignantsTriees) {
                if (aAffecter <= 0) break;
                if (seance.getEnseignants().contains(enseignant)) continue;
                if (enseignantASeanceEnConflit(enseignant, seance.getSeanceDate(), seance.getHoraire())) continue;
                if (enseignant.calculerChargeSurveillance() <= 0) continue;

                seance.getEnseignants().add(enseignant);
                enseignant.getSeances().add(seance);
                aAffecter--;
            }
        }

        seanceRep.saveAll(seances);
        enseignantRep.saveAll(enseignants);
    }
    
    @Transactional
    public boolean terminerSeance(int seanceId) {
    	Seance seance = seanceRep.findById(seanceId)
              .orElseThrow(() -> new SeancePasTrouveeException("Seance pas trouvee"));
    	
    	if (!seance.getSeanceDate().isBefore(LocalDate.now())) {
    		return false;
    	}
    	seance.setPasseeExamen(true);
    	if (seanceRep.save(seance) == null) {
    		return false;
    	}
    	return true;
    	
    	
    }
    
}
