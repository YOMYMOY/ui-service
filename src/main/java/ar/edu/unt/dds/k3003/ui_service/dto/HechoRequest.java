package ar.edu.unt.dds.k3003.ui_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class HechoRequest {
    @JsonProperty("nombre_coleccion")
    private String coleccionId;
    private String titulo;
    private String descripcion;

    public HechoRequest() {
    }

    public HechoRequest(String coleccionId, String titulo, String descripcion) {
        this.coleccionId = coleccionId;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getColeccionId() {
        return coleccionId;
    }

    public void setColeccionId(String coleccionId) {
        this.coleccionId = coleccionId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
