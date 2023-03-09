package com.ca1ci0.wikimedia.consumer.util;

import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WikimediaMessageParser {

  public static String extractIdFromWikimediaMessage(String json) {
    return JsonParser.parseString(json)
        .getAsJsonObject()
        .get("meta")
        .getAsJsonObject()
        .get("id")
        .getAsString();
  }
}
