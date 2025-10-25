package ar.edu.unt.dds.k3003.ui_service.service;

import ar.edu.unt.dds.k3003.ui_service.dto.PdiRequest;
import ar.edu.unt.dds.k3003.ui_service.dto.PdiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProcesadorPdiMicroservicioService {

    private final WebClient webClient;

    @Autowired
    public ProcesadorPdiMicroservicioService(@Qualifier("pdiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    //Función 4: Agregar un PDI a un hecho
    public PdiResponse agregarPdi(PdiRequest request) {
        return webClient.post()
                .uri(("/pdis"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PdiResponse.class)
                .block();
    }

}
