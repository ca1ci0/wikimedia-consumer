package com.ca1ci0.wikimedia.consumer.listener;

import com.ca1ci0.wikimedia.consumer.service.OpenSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WikimediaListener {

  private final OpenSearchService openSearchService;

  @KafkaListener(topics = "${kafka.topic.wikimedia}")
  public void processWikimediaRecentChangeBatch(List<String> messageBatch) {
    log.info("Received Message: {}", messageBatch);
    openSearchService.saveBatch(messageBatch);
  }
}
