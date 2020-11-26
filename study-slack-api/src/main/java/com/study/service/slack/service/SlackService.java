package com.study.service.slack.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.study.service.slack.dto.MessageRequest;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class SlackService {
    private static final String SLACK_API_BASE_URL = "https://hooks.slack.com";
    private final WebClient webClient;

    @Value("${slack.token}")
    private String token;

    @Value("${slack.url}")
    private String url;

    public SlackService() {
        final ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 50))
                .build();
        exchangeStrategies
                .messageWriters().stream()
                .filter(LoggingCodecSupport.class::isInstance)
                .forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));

        this.webClient = WebClient.builder()
                .baseUrl(SLACK_API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8")
                .defaultHeader(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                .exchangeStrategies(exchangeStrategies)
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                        clientRequest -> {
                            log.info("Request: {} {} {}", clientRequest.method(), clientRequest.url(),
                                    clientRequest.body());
                            clientRequest.headers()
                                    .forEach((name, values) -> values.forEach(
                                            value -> log.error("{} : {}", name, value)));
                            return Mono.just(clientRequest);
                        }
                ))
                .build();
    }

    public String sendMessage(final MessageRequest messageRequest) {
        return webClient.post().uri(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromValue(messageRequest))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}