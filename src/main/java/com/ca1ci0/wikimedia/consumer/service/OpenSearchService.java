package com.ca1ci0.wikimedia.consumer.service;

import java.util.List;

public interface OpenSearchService {

  void saveBatch(List<String> batch);
}
