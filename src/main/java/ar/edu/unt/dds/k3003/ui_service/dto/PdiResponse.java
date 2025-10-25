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
}