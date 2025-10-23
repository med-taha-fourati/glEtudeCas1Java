package com.fsegs.genie_logiciel_etude_cas_1.Metier.DTO;

import jakarta.validation.constraints.NotEmpty;

public class UtilisateurDTO {
    @NotEmpty(message = "nom utilisateur ne doit pas etre vide")
    public String username;
    @NotEmpty(message = "mot de passe champ ne doit pas etre vide")
    public String password;
}
