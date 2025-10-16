package com.fsegs.genie_logiciel_etude_cas_1.Exceptions;

import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Grade.GradeMetadonneeException;
import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Seance.SeancePasTrouveeException;
import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Utilisateur.UtilisateurMetadonneeException;
import com.fsegs.genie_logiciel_etude_cas_1.Exceptions.Utilisateur.UtilisateurPasTrouveeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UtilisateurMetadonneeException.class)
    public ResponseEntity<?> handleUtilisateurMetadonneeException(UtilisateurMetadonneeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UtilisateurPasTrouveeException.class)
    public ResponseEntity<?> handleUtilisateurPasTrouveeException(UtilisateurPasTrouveeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GradeMetadonneeException.class)
    public ResponseEntity<?> handleGradeMetadonneeException(GradeMetadonneeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SeancePasTrouveeException.class)
    public ResponseEntity<?> handleSeancePasTrouveeException(SeancePasTrouveeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
