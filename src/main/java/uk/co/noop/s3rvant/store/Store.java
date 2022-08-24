package uk.co.noop.s3rvant.store;

import java.util.Map;

public interface Store extends Map<String, String> {

  boolean containsKey(String key);

  boolean containsValue(String value);

  String get(String key);

  String remove(String key);

}
