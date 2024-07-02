package com.cpulsivek.uploadservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import util.CastResponseImpl;
import wrapper.HttpClientWrapper;

@Configuration
public class HttpClient {
  private final Environment env;

  @Autowired
  public HttpClient(Environment env) {
    this.env = env;
  }

  @Bean
  public WebClient client() {
    return WebClient.builder()
        .baseUrl(Objects.requireNonNull(env.getProperty("user.service.origin")))
        .filter(decodePath())
        .build();
  }

  @Bean
  HttpClientWrapper customerAuthService() {
    HttpServiceProxyFactory factory =
        HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client())).build();
    return factory.createClient(HttpClientWrapper.class);
  }

  @Bean
  public CastResponseImpl castResponseImpl() {
    return new CastResponseImpl<>(new ObjectMapper());
  }

  private ExchangeFilterFunction decodePath() {
    return ExchangeFilterFunction.ofRequestProcessor(
        clientRequest -> {
          String decodedUrl =
              URLDecoder.decode(clientRequest.url().toString(), StandardCharsets.UTF_8);
          clientRequest = ClientRequest.from(clientRequest).url(URI.create(decodedUrl)).build();
          return Mono.just(clientRequest);
        });
  }
}
