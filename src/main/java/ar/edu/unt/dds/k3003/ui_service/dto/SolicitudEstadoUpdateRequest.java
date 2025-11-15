package ar.edu.unt.dds.k3003.ui_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SolicitudEstadoUpdateRequest {
    private String id;
    private String estado;

    public SolicitudEstadoUpdateRequest() {
    }

    public SolicitudEstadoUpdateRequest(String id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
