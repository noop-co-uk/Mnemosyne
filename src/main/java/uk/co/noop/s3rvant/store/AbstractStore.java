package uk.co.noop.s3rvant.store;

import uk.co.noop.guardian.Guardian;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractStore implements Store {

  protected AbstractStore() {
    super();
  }

  @Override
  public int size() {
    return keySet().size();
  }

  @Override
  public boolean isEmpty() {
    return keySet().isEmpty();
  }

  @Override
  public boolean containsKey(final Object key) {

    Guardian.guard("key", key).againstNullObjects();

    if (key instanceof String) {
      return containsKey((String) key);
    }

    throw new ClassCastException();
  }

  @Override
  public boolean containsValue(final Object value) {

    Guardian.guard("value", value).againstNullObjects();

    if (value instanceof String) {
      return containsValue((String) value);
    }

    throw new ClassCastException();
  }

  @Override
  public String get(final Object key) {

    Guardian.guard("key", key).againstNullObjects();

    if (key instanceof String) {
      return get((String) key);
    }

    throw new ClassCastException();
  }

  @Override
  public String remove(final Object key) {

    Guardian.guard("key", key).againstNullObjects();

    if (key instanceof String) {
      return remove((String) key);
    }

    throw new ClassCastException();
  }

  @Override
  public void putAll(final Map<? extends String, ? extends String> map) {

    Guardian.guard("map", map).againstNullObjects();

    map.forEach(this::put);
  }

  @Override
  public void clear() {
    final List<String> keys = new ArrayList<>(keySet());
    keys.forEach(this::remove);
  }

  @Override
  public Collection<String> values() {
    return keySet()
        .stream()
        .map(this::get)
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Entry<String, String>> entrySet() {
    return keySet()
        .stream()
        .map(StoreEntry::new)
        .collect(Collectors.toSet());
  }

  private class StoreEntry implements Entry<String, String> {

    private final String key;

    private StoreEntry(final String key) {
      super();

      Guardian.guard("key", key).againstBlankStrings();

      this.key = key;
    }

    @Override
    public String getKey() {
      return key;
    }

    @Override
    public String getValue() {
      return get(key);
    }

    @Override
    public String setValue(final String value) {
      return put(key, value);
    }

  }

}
