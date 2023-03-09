package com.ca1ci0.wikimedia.consumer.service;

import com.ca1ci0.wikimedia.consumer.util.WikimediaMessageParser;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.action.bulk.BulkRequest;
import org.opensearch.action.bulk.BulkResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenSearchService {

  private final RestHighLevelClient openSearchClient;

  @Value("${opensearch.index.wikimedia}")
  private String wikimediaIndex;

  @SneakyThrows
  public void saveAll(List<String> batch) {
    BulkRequest bulkRequest = new BulkRequest();
    batch.stream()
        .map(message -> new IndexRequest(wikimediaIndex)
            .source(message, XContentType.JSON)
            .id(WikimediaMessageParser.extractIdFromWikimediaMessage(message)))
        .forEach(bulkRequest::add);

    BulkResponse response = openSearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    log.info("Bulk response: {}", response);
  }

  @PostConstruct
  public void createIndexIfNotExist() throws IOException {
    boolean indexExists = openSearchClient.indices()
        .exists(new GetIndexRequest(wikimediaIndex), RequestOptions.DEFAULT);

    if (!indexExists) {
      var createIndexRequest = new CreateIndexRequest(wikimediaIndex);
      openSearchClient.indices()
          .create(createIndexRequest, RequestOptions.DEFAULT);
      log.info("The Wikimedia Index has been created!");
    } else {
      log.info("The Wikimedia Index already exits");
    }
  }
}
