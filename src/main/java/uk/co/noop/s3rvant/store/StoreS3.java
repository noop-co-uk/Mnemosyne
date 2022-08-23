package uk.co.noop.s3rvant.store;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import uk.co.noop.guardian.Guardian;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StoreS3 implements Store {

  private final AmazonS3 s3;
  private final String bucket;

  public StoreS3(final AmazonS3 s3, final String bucket) {
    super();

    Guardian.guard("s3", s3).againstNullObjects();
    Guardian.guard("bucket", bucket)
        .againstBlankStrings()
        .againstInvalidStrings(b -> !s3.doesBucketExistV2(b));

    this.s3 = s3;
    this.bucket = bucket;
  }

  @Override
  public int size() {
    return keySet().size();
  }

  @Override
  public boolean isEmpty() {
    return keySet().isEmpty();
  }

  public boolean containsKey(final String key) {

    Guardian.guard("key", key).againstBlankStrings();

    return s3.doesObjectExist(bucket, key);
  }

  @Override
  public boolean containsKey(final Object key) {

    Guardian.guard("key", key).againstNullObjects();

    if (key instanceof String) {
      return containsKey((String) key);
    }

    throw new ClassCastException();
  }

  public boolean containsValue(final String value) {

    Guardian.guard("value", value).againstBlankStrings();

    return values().contains(value);
  }

  @Override
  public boolean containsValue(final Object value) {

    Guardian.guard("value", value).againstNullObjects();

    if (value instanceof String) {
      return containsValue((String) value);
    }

    throw new ClassCastException();
  }

  public String get(final String key) {

    Guardian.guard("key", key).againstBlankStrings();

    return s3.getObjectAsString(bucket, key);
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
  public String put(final String key, final String value) {

    Guardian.guard("key", key).againstBlankStrings();
    Guardian.guard("value", value).againstBlankStrings();

    String previousValue = null;

    if (containsKey(key)) {
      previousValue = get(key);
    }

    s3.putObject(bucket, key, value);

    return previousValue;
  }

  public String remove(final String key) {

    Guardian.guard("key", key).againstBlankStrings();

    String previousValue = null;

    if (containsKey(key)) {
      previousValue = get(key);
    }

    s3.deleteObject(bucket, key);

    return previousValue;
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
  public void putAll(Map<? extends String, ? extends String> map) {

    Guardian.guard("map", map).againstNullObjects();

    map.forEach(this::put);
  }

  @Override
  public void clear() {
    keySet().forEach(this::remove);
  }

  @Override
  public Set<String> keySet() {
    return s3.listObjects(bucket)
        .getObjectSummaries()
        .stream()
        .map(S3ObjectSummary::getKey)
        .collect(Collectors.toSet());
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
        .map(EntryS3::new)
        .collect(Collectors.toSet());
  }

  private class EntryS3 implements Entry<String, String> {

    private final String key;

    private EntryS3(final String key) {
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
