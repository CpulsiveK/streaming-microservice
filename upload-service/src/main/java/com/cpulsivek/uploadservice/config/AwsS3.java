package com.cpulsivek.uploadservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;

@Configuration
public class AwsS3 {
  private final Environment env;

  @Autowired
  public AwsS3(Environment env) {
    this.env = env;
  }

  @Bean
  public S3Client s3Client() {
    final AwsBasicCredentials awsBasicCredentials =
        AwsBasicCredentials.create(
            env.getProperty("aws.access.key"), env.getProperty("aws.secret.key"));
    return S3Client.builder()
        .region(Region.EU_NORTH_1)
        .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
        .build();
  }
}
