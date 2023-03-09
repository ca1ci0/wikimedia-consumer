package com.ca1ci0.wikimedia.consumer.config;

import org.opensearch.client.RestHighLevelClient;
import org.opensearch.data.client.orhlc.AbstractOpenSearchConfiguration;
import org.opensearch.data.client.orhlc.ClientConfiguration;
import org.opensearch.data.client.orhlc.OpenSearchRestTemplate;
import org.opensearch.data.client.orhlc.RestClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSearchConfig extends AbstractOpenSearchConfiguration {

  @Value("${opensearch.server}")
  private String opensearchServer;

  @Override
  @Bean
  public RestHighLevelClient opensearchClient() {

    final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
        .connectedTo(opensearchServer)
        .build();

    return RestClients.create(clientConfiguration)
        .rest();
  }

  @Bean
  public OpenSearchRestTemplate opensearchRestTemplate() {
    return new OpenSearchRestTemplate(opensearchClient());
  }
}

