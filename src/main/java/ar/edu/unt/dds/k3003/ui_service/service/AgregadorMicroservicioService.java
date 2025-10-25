package ar.edu.unt.dds.k3003.ui_service.service;

import ar.edu.unt.dds.k3003.ui_service.dto.HechoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class AgregadorMicroservicioService {

    private final WebClient webClient;

    @Autowired
    public AgregadorMicroservicioService(@Qualifier("agregadorWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    //Función 1: Listar hecho de una colección
    public List<HechoResponse> listarHechosPorColeccion(String coleccion) {
        return webClient.get()
                .uri("/api/coleccion/{nombre}/hechos", coleccion)
                .retrieve()
                .bodyToFlux(HechoResponse.class)
                .collectList()
                .block();
    }

}
