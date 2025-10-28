package com.fsegs.genie_logiciel_etude_cas_1.Metier;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Enumerations.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class Utilisateur {
    @Column(name="user_name")
    private String username;
    @Column(name="user_password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role = Role.ENSEIGNANT;
}
