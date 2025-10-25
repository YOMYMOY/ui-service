package ar.edu.unt.dds.k3003.ui_service.service;

import ar.edu.unt.dds.k3003.ui_service.dto.HechoRequest;
import ar.edu.unt.dds.k3003.ui_service.dto.HechoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FuentesMicroservicioService {

    private final WebClient webClient;

    @Autowired
    public FuentesMicroservicioService(@Qualifier("fuentesWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    //Función 2: Visualizar un hecho
    public HechoResponse obtenerDetalleHecho(String hechoId) {
        return webClient.get()
                .uri("/hechos/{id}", hechoId)
                .retrieve()
                .bodyToMono(HechoResponse.class)
                .block();
    }

    //Función 3: Agregar un hecho
    public HechoResponse agregarHecho(HechoRequest request) {
        return webClient.post()
                .uri("/hechos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(HechoResponse.class)
                .block();
    }

}
