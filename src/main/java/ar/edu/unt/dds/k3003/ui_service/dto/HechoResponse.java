package ar.edu.unt.dds.k3003.ui_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HechoResponse {
    private String id;
    private String titulo;
    private String descripcion;
    private String estado;
}
