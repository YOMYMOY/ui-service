package ar.edu.unt.dds.k3003.ui_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SolicitudEstadoUpdateRequest {
    private String id;
    private String estado;

    public SolicitudEstadoUpdateRequest(String id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    public SolicitudEstadoUpdateRequest() {
    }
}
