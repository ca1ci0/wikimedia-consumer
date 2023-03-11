package com.ca1ci0.wikimedia.consumer.service.impl;

import com.ca1ci0.wikimedia.consumer.service.OpenSearchService;
import com.ca1ci0.wikimedia.consumer.util.WikimediaMessageParser;
import jakarta.annotation.PostConstruct;
import java.util.List;
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
@Service
public class WikimediaOpenSearchServiceImpl implements OpenSearchService {

  private final RestHighLevelClient openSearchClient;
  private final String wikimediaIndex;

  public WikimediaOpenSearchServiceImpl(RestHighLevelClient openSearchClient,
      @Value("${opensearch.index.wikimedia}") String wikimediaIndex) {
    this.openSearchClient = openSearchClient;
    this.wikimediaIndex = wikimediaIndex;
  }

  @SneakyThrows
  public void saveBatch(List<String> messageBatch) {
    BulkRequest bulkRequest = new BulkRequest();
    messageBatch.stream()
        .map(message -> new IndexRequest(wikimediaIndex)
            .id(WikimediaMessageParser.extractIdFromWikimediaMessage(message))
            .source(message, XContentType.JSON))
        .forEach(bulkRequest::add);

    BulkResponse response = openSearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    log.info("Saved items size: {}", response.getItems().length);
  }

  @PostConstruct
  @SneakyThrows
  void createIndexIfNotExist() {
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
