// src/main/java/ar/edu/unt/dds/k3003/ui_service/service/ProcesadorPdiMicroservicioService.java
package ar.edu.unt.dds.k3003.ui_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.unt.dds.k3003.ui_service.dto.PdiRequest;
import ar.edu.unt.dds.k3003.ui_service.dto.PdiResponse;

@Service
public class ProcesadorPdiMicroservicioService {

    private final WebClient webClient;

    public ProcesadorPdiMicroservicioService(
            @Value("${urls.pdi}") String pdiUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(pdiUrl)
                .build();
    }

    public PdiResponse agregarPdi(PdiRequest request) {
        return webClient.post()
                .uri("/pdis")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PdiResponse.class)
                .block();
    }
    //Se usa en HechoCommand
    public List<PdiResponse> obtenerPdisPorHecho(String hechoId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/pdis")
                        .queryParam("hecho", hechoId)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PdiResponse>>() {})
                .block();
    }
}
