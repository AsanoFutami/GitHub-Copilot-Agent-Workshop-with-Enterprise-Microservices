package com.skishop.frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.client.RestTemplate;
import reactor.netty.http.client.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.core5.util.Timeout;

import java.time.Duration;

/**
 * WebClient Configuration
 * Configures communication with backend APIs
 */
@Configuration
public class WebClientConfig {

    @Value("${app.api-gateway.url}")
    private String apiGatewayUrl;

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.ofSeconds(30))
            .option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

        return WebClient.builder()
            .baseUrl(apiGatewayUrl)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(1024 * 1024) // 1MB
            )
            .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        // Short timeout settings for cart service
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(3)) // 3 seconds
                .setConnectionRequestTimeout(Timeout.ofSeconds(3)) // 3 seconds
                .setResponseTimeout(Timeout.ofSeconds(5)) // 5 seconds
                .build();

        CloseableHttpClient client = HttpClients
                .custom()
                .setDefaultRequestConfig(config)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(client);

        return new RestTemplate(factory);
    }
}
