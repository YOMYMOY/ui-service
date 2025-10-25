package ar.edu.unt.dds.k3003.ui_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdiRequest {
    @JsonProperty("hechoId")
    private String hechoId;
    private String contenido;
}
