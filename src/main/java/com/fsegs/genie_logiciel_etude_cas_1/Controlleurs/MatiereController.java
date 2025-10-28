package com.fsegs.genie_logiciel_etude_cas_1.Controlleurs;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO.MatiereDTO;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Matiere;
import com.fsegs.genie_logiciel_etude_cas_1.Metier.Seance;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.MatiereRep;
import com.fsegs.genie_logiciel_etude_cas_1.Repertoires.SeanceRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/matiere")
public class MatiereController {
    @Autowired
    private MatiereRep matiereRep;
    @Autowired
    private SeanceRep seanceRep;

    @PreAuthorize("hasAnyRole('ADMIN', 'ENSEIGNANT')")
    @GetMapping("/fetchAll")
    public ResponseEntity<List<Matiere>>  fetchAll() {
        try {
            ArrayList<Matiere> matieres = (ArrayList<Matiere>)matiereRep.findAll();
            return  new ResponseEntity<>(matieres, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addMatiere(@RequestBody MatiereDTO matiere) {
        try {
            Matiere matierew = new Matiere();

            matierew.setNom(matiere.nom);
            matierew.setNbPaquets(matiere.nbPaquets);

            Seance tr = seanceRep.findById(matiere.seanceId).orElseThrow(()->new RuntimeException());
            matierew.setSeance(tr);

            matiereRep.save(matierew);

            return new ResponseEntity<Matiere>(matierew, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit")
    public ResponseEntity<?> editMatiere(@RequestParam int id, @RequestBody MatiereDTO matiere) {
        try {
            Matiere matierew = matiereRep.findById(id).orElseThrow(()->new RuntimeException());

            matierew.setNom(matiere.nom);
            matierew.setNbPaquets(matiere.nbPaquets);

            Seance tr = seanceRep.findById(matiere.seanceId).orElseThrow(()->new RuntimeException());
            matierew.setSeance(tr);

            matiereRep.save(matierew);

            return new ResponseEntity<Matiere>(matierew, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMatiere(@RequestParam int id) {
        try {
            Matiere matierew = matiereRep.findById(id).orElseThrow(()->new RuntimeException());

            matiereRep.delete(matierew);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
