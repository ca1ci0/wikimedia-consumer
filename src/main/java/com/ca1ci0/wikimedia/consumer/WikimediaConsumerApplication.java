package com.ca1ci0.wikimedia.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;

@SpringBootApplication(exclude = ElasticsearchDataAutoConfiguration.class)
public class WikimediaConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(WikimediaConsumerApplication.class, args);
  }
}
