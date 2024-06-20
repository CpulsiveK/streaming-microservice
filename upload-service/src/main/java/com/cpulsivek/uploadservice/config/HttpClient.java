package com.cpulsivek.uploadservice.config;

import com.cpulsivek.uploadservice.client.NotificationClient;
import com.cpulsivek.uploadservice.client.UserClient;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClient {
  private final Environment env;

  @Autowired
  public HttpClient(Environment env) {
    this.env = env;
  }

  @Bean
  public WebClient userClient() {
    return WebClient.builder()
        .baseUrl(Objects.requireNonNull(env.getProperty("user.service.origin")))
        .build();
  }

  @Bean
  public WebClient notificationClient() {
    return WebClient.builder()
        .baseUrl(Objects.requireNonNull(env.getProperty("notification.service.origin")))
        .build();
  }

  @Bean
  UserClient customerAuthService() {
    HttpServiceProxyFactory factory =
        HttpServiceProxyFactory.builderFor(WebClientAdapter.create(userClient())).build();
    return factory.createClient(UserClient.class);
  }

  @Bean
  NotificationClient customerNotificationService() {
    HttpServiceProxyFactory factory =
        HttpServiceProxyFactory.builderFor(WebClientAdapter.create(notificationClient())).build();
    return factory.createClient(NotificationClient.class);
  }
}
