package ar.edu.unt.dds.k3003.ui_service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PdiResponse {
    private Long id;
    
    @JsonProperty("hecho_id")  
    private String hechoId;
    
    private String contenido;
    private List<String> etiquetas;
    
    @JsonProperty("imagen_url")
    private String imagenUrl;
    
    @JsonProperty("ocr_resultado")
    private String ocrResultado;
    
    @JsonProperty("etiquetado_resultado")
    private String etiquetadoResultado;
    
    private Boolean procesado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getOcrResultado() {
        return ocrResultado;
    }

    public void setOcrResultado(String ocrResultado) {
        this.ocrResultado = ocrResultado;
    }

    public String getEtiquetadoResultado() {
        return etiquetadoResultado;
    }

    public void setEtiquetadoResultado(String etiquetadoResultado) {
        this.etiquetadoResultado = etiquetadoResultado;
    }

    public Boolean getProcesado() {
        return procesado;
    }

    public void setProcesado(Boolean procesado) {
        this.procesado = procesado;
    }
}