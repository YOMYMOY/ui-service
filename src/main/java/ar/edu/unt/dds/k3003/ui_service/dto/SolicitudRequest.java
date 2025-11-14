package ar.edu.unt.dds.k3003.ui_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SolicitudRequest {
    @JsonProperty("hechoId")
    private String hechoId;
    private String descripcion;
    private String estado;

    public SolicitudRequest(String hechoId, String descripcion, String estado) {
        this.hechoId = hechoId;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public SolicitudRequest() {
    }
}
