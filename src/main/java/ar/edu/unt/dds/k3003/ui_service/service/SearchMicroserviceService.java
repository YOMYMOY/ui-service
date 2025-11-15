package ar.edu.unt.dds.k3003.ui_service.service;

import ar.edu.unt.dds.k3003.ui_service.dto.HechoDTO;
import ar.edu.unt.dds.k3003.ui_service.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class SearchMicroserviceService {
    private final WebClient webClient;

    @Autowired
    public SearchMicroserviceService(@Qualifier("searchWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<String> listarTags() {
        return webClient.get()
                .uri("/search/tags")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                .block();
    }

    public PageDTO<HechoDTO> buscarHechos(String palabra, List<String> tags, int page) {
        ParameterizedTypeReference<PageDTO<HechoDTO>> responseType =
                new ParameterizedTypeReference<>() {};

        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/search")
                            .queryParam("palabra", palabra)
                            .queryParam("page", page)
                            .queryParam("size", 2);

                    if (tags != null && !tags.isEmpty()) {
                        tags.forEach(tag -> uriBuilder.queryParam("tags", tag));
                    }

                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
}
