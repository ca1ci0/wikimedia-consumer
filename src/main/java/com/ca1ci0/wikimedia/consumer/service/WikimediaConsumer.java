package com.ca1ci0.wikimedia.consumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.data.client.orhlc.OpenSearchRestTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WikimediaConsumer {

  private final OpenSearchRestTemplate openSearchTemplate;

  @KafkaListener(topics = "${wikimedia.topic}")
  public void consume(@Payload String message, @Header(value = KafkaHeaders.KEY, required = false) Long key) {
    log.info("Received Message. Key: {}, payload: {}", key, message);
  }
}
