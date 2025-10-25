package ar.edu.unt.dds.k3003.ui_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudEstadoUpdateRequest {
    private String id;
    private String estado;
}
