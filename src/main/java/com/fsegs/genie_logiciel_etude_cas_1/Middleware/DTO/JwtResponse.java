package com.fsegs.genie_logiciel_etude_cas_1.Middleware.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
@Builder
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class JwtResponse {
    private String token;
    private Date expiresAt;
}
