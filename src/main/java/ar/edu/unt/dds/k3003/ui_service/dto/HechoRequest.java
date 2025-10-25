package ar.edu.unt.dds.k3003.ui_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HechoRequest {
    @JsonProperty("nombre_coleccion")
    private String coleccionId;
    private String titulo;
    private String descripcion;
}
