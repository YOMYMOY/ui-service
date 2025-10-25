package ar.edu.unt.dds.k3003.ui_service.service;

import ar.edu.unt.dds.k3003.ui_service.dto.SolicitudEstadoUpdateRequest;
import ar.edu.unt.dds.k3003.ui_service.dto.SolicitudRequest;
import ar.edu.unt.dds.k3003.ui_service.dto.SolicitudResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class SolicitudMicroserviceService {
    private final WebClient webClient;

    public SolicitudMicroserviceService(@Qualifier("solicitudesWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    //Función 5: Crear la solicitud de borrado
    public SolicitudResponse crearSolicitud(SolicitudRequest request) {
        return webClient.post()
                .uri("/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SolicitudResponse.class)
                .block();
    }

    //Función 6: Cambiar el estado de una solicitud
    public SolicitudResponse cambiarEstadoSolicitud(SolicitudEstadoUpdateRequest request) {
        return webClient.patch()
                .uri("/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SolicitudResponse.class)
                .block();
    }

}
