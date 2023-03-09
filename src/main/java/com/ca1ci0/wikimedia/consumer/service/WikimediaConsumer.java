package com.ca1ci0.wikimedia.consumer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WikimediaConsumer {

  private final OpenSearchService openSearchService;

  @KafkaListener(topics = "${kafka.topic.wikimedia}")
  public void consume(List<String> messageBatch) {
    log.info("Received Message: {}", messageBatch);
    openSearchService.saveAll(messageBatch);
  }
}
