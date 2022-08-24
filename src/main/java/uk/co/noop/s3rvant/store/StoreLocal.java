package uk.co.noop.s3rvant.store;

import uk.co.noop.guardian.Guardian;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StoreLocal extends AbstractStore {

  private final Map<String, String> map = new HashMap<>();

  public StoreLocal() {
    super();
  }

  @Override
  public boolean containsKey(final String key) {

    Guardian.guard("key", key).againstBlankStrings();

    return map.containsKey(key);
  }

  @Override
  public boolean containsValue(final String value) {

    Guardian.guard("value", value).againstBlankStrings();

    return map.containsValue(value);
  }

  @Override
  public String get(final String key) {

    Guardian.guard("key", key).againstBlankStrings();

    return map.get(key);
  }

  @Override
  public String put(final String key, final String value) {

    Guardian.guard("key", key).againstBlankStrings();
    Guardian.guard("value", value).againstBlankStrings();

    return map.put(key, value);
  }

  @Override
  public String remove(final String key) {

    Guardian.guard("key", key).againstBlankStrings();

    return map.remove(key);
  }

  @Override
  public Set<String> keySet() {
    return map.keySet();
  }

}
