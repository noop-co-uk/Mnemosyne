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

  /**
   * {@inheritDoc}
   *
   * @param key key whose presence in this <code>Store</code> is to be tested
   *
   * @return <code>true</code> if this <code>Store</code> contains a mapping for
   * the specified <code>key</code>
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   *
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  @Override
  public boolean containsKey(final String key) {

    Guardian.guard("key", key).againstBlankStrings();

    return s3.doesObjectExist(bucket, key);
  }

  /**
   * {@inheritDoc}
   *
   * @param value value whose presence in this <code>Store</code> is to be
   *              tested
   *
   * @return <code>true</code> if this <code>Store</code> maps one or more keys
   * to the specified <code>value</code>
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   *
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  @Override
  public boolean containsValue(final String value) {

    Guardian.guard("value", value).againstBlankStrings();

    return values().contains(value);
  }

  /**
   * {@inheritDoc}
   *
   * @param key the key whose associated value is to be returned
   *
   * @return the value to which the specified <code>key</code> is mapped, or
   * <code>null</code> if this map contains no mapping for the key
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   *
   * @see Store#containsKey(String)
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  @Override
  public String get(final String key) {

    Guardian.guard("key", key).againstBlankStrings();

    return s3.getObjectAsString(bucket, key);
  }

  /**
   * {@inheritDoc}
   *
   * @param key key with which the specified <code>value</code> is to be associated
   * @param value value to be associated with the specified <code>key</code>
   *
   * @return the previous value associated with key, or <code>null</code> if
   * there was no mapping for key. (A <code>null</code> return can also indicate
   * that the <code>Store</code> previously associated <code>null</code> with
   * key, if the implementation supports <code>null</code> values.)
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   *
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
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

  /**
   * {@inheritDoc}
   *
   * @param key key whose mapping is to be removed from the map
   *
   * @return the previous value associated with <code>key</code>, or
   * <code>null</code> if there was no mapping for <code>key</code>.
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   *
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.ObjectSidekick#againstNullObjects()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
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

  /**
   * {@inheritDoc}
   *
   * @return a set view of the keys contained in this <code>Store</code>
   */
  @Override
  public Set<String> keySet() {

    return s3.listObjects(bucket)
        .getObjectSummaries()
        .stream()
        .map(S3ObjectSummary::getKey)
        .collect(Collectors.toSet());
  }

}
