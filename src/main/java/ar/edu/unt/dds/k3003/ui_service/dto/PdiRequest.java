package ar.edu.unt.dds.k3003.ui_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PdiRequest {
    @JsonProperty("hechoId")
    private String hechoId;
    private String contenido;

    public PdiRequest(String hechoId, String contenido) {
        this.hechoId = hechoId;
        this.contenido = contenido;
    }

    public PdiRequest() {
    }

    public String getHechoId() {
        return hechoId;
    }

    public void setHechoId(String hechoId) {
        this.hechoId = hechoId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
