package ar.edu.unt.dds.k3003.ui_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SolicitudRequest {
    @JsonProperty("hechoId")
    private String hechoId;
    private String descripcion;
    private String estado;

    public SolicitudRequest() {
    }

    public SolicitudRequest(String hechoId, String descripcion, String estado) {
        this.hechoId = hechoId;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public String getHechoId() {
        return hechoId;
    }

    public void setHechoId(String hechoId) {
        this.hechoId = hechoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
