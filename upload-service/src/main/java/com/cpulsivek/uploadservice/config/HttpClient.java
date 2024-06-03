package com.cpulsivek.uploadservice.config;

import com.cpulsivek.uploadservice.client.UserClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClient {
  @Bean
  public WebClient userClient() {
    return WebClient.builder().baseUrl("http://localhost:8090").build();
  }

  @Bean
  UserClient customerAuthService() {
    HttpServiceProxyFactory factory =
        HttpServiceProxyFactory.builderFor(WebClientAdapter.create(userClient())).build();
    return factory.createClient(UserClient.class);
  }
}
