package ar.edu.unt.dds.k3003.ui_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${urls.agregador}")
    private String agregadorBaseUrl;

    @Value("${urls.fuentes}")
    private String fuentesBaseUrl;

    @Value("${urls.pdi}")
    private String pdiBaseUrl;

    @Value("${urls.solicitudes}")
    private String solicitudesBaseUrl;

    @Bean("agregadorWebClient")
    public WebClient agregadorWebClient(WebClient.Builder builder) {
        return builder.baseUrl(trimSlash(agregadorBaseUrl)).build();
    }

    @Bean("fuentesWebClient")
    public WebClient fuentesWebClient(WebClient.Builder builder) {
        return builder.baseUrl(trimSlash(fuentesBaseUrl)).build();
    }

    @Bean("pdiWebClient")
    public WebClient pdiWebClient(WebClient.Builder builder) {
        return builder.baseUrl(trimSlash(pdiBaseUrl)).build();
    }

    @Bean("solicitudesWebClient")
    public WebClient solicitudesWebClient(WebClient.Builder builder) {
        return builder.baseUrl(trimSlash(solicitudesBaseUrl)).build();
    }

    private static String trimSlash(String base) {
        return (base != null && base.endsWith("/")) ? base.substring(0, base.length() - 1) : base;
    }

}
