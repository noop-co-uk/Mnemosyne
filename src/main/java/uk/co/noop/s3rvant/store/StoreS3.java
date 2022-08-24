package uk.co.noop.s3rvant.store;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import uk.co.noop.guardian.Guardian;

import java.util.Set;
import java.util.stream.Collectors;

public class StoreS3 extends AbstractStore {

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
  public boolean containsKey(final String key) {

    Guardian.guard("key", key).againstBlankStrings();

    return s3.doesObjectExist(bucket, key);
  }

  @Override
  public boolean containsValue(final String value) {

    Guardian.guard("value", value).againstBlankStrings();

    return values().contains(value);
  }

  @Override
  public String get(final String key) {

    Guardian.guard("key", key).againstBlankStrings();

    return s3.getObjectAsString(bucket, key);
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

  @Override
  public String remove(final String key) {

    Guardian.guard("key", key).againstBlankStrings();

    String previousValue = null;

    if (containsKey(key)) {
      previousValue = get(key);
      s3.deleteObject(bucket, key);
    }

    return previousValue;
  }

  @Override
  public Set<String> keySet() {
    return s3.listObjects(bucket)
        .getObjectSummaries()
        .stream()
        .map(S3ObjectSummary::getKey)
        .collect(Collectors.toSet());
  }

}
