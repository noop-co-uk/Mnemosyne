package uk.co.noop.mnemosyne.mneme;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import uk.co.noop.mnemosyne.eunomia.S3BucketNameEunomia;
import uk.co.noop.themis.Themis;

import java.util.Set;
import java.util.stream.Collectors;

public class S3Mneme extends AbstractMneme {

  private final AmazonS3 s3;
  private final String bucketName;

  public S3Mneme(final AmazonS3 s3, final String bucketName) {

    super();

    Themis.validate("s3", s3).againstNullObjects();
    Themis.validate("bucketName", bucketName, S3BucketNameEunomia.class)
        .againstNonExistentS3BucketNames(s3);

    this.s3 = s3;
    this.bucketName = bucketName;
  }

  /**
   * {@inheritDoc}
   *
   * @param key key whose presence in this {@link Mneme} is to be tested
   *
   * @return <code>true</code> if this <code>Mneme</code> contains a mapping for
   * the specified <b>key</b>
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see Mneme
   * @see Themis
   * @see uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  @Override
  public boolean containsKey(final String key) {

    Themis.validate("key", key).againstBlankStrings();

    return s3.doesObjectExist(bucketName, key);
  }

  /**
   * {@inheritDoc}
   *
   * @param value value whose presence in this {@link Mneme} is to be tested
   *
   * @return <code>true</code> if this <code>Mneme</code> maps one or more keys
   * to the specified <b>value</b>
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see Mneme
   * @see Themis
   * @see uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  @Override
  public boolean containsValue(final String value) {

    Themis.validate("value", value).againstBlankStrings();

    return values().contains(value);
  }

  /**
   * {@inheritDoc}
   *
   * @param key the key whose associated value is to be returned
   *
   * @return the value to which the specified <b>key</b> is mapped, or
   * <code>null</code> if this {@link Mneme} contains no mapping for the
   * <b>key</b>
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see Mneme
   * @see Mneme#containsKey(String)
   * @see Themis
   * @see uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  @Override
  public String get(final String key) {

    Themis.validate("key", key).againstBlankStrings();

    return s3.getObjectAsString(bucketName, key);
  }

  /**
   * {@inheritDoc}
   *
   * @param key key with which the specified <b>value</b> is to be associated
   * @param value value to be associated with the specified <b>key</b>
   *
   * @return the previous value associated with <b>key</b>, or <code>null</code>
   * if there was no mapping for <b>key</b>. (A <code>null</code> return can
   * also indicate that the {@link Mneme} previously associated
   * <code>null</code> with <b>key</b>, if the implementation supports
   * <code>null</code> values.)
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see Mneme
   * @see Themis
   * @see uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  @Override
  public String put(final String key, final String value) {

    Themis.validate("key", key).againstBlankStrings();
    Themis.validate("value", value).againstBlankStrings();

    String previousValue = null;

    if (containsKey(key)) {
      previousValue = get(key);
    }

    s3.putObject(bucketName, key, value);

    return previousValue;
  }

  /**
   * {@inheritDoc}
   *
   * @param key key whose mapping is to be removed from the {@link Mneme}
   *
   * @return the previous value associated with <b>key</b>, or <code>null</code>
   * if there was no mapping for <b>key</b>.
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see Mneme
   * @see Themis
   * @see uk.co.noop.themis.eunomia.ObjectEunomia#againstNullObjects()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  @Override
  public String remove(final String key) {

    Themis.validate("key", key).againstBlankStrings();

    String previousValue = null;

    if (containsKey(key)) {
      previousValue = get(key);
      s3.deleteObject(bucketName, key);
    }

    return previousValue;
  }

  /**
   * {@inheritDoc}
   *
   * @return a set view of the keys contained in this {@link Mneme}
   *
   * @see Mneme
   */
  @Override
  public Set<String> keySet() {

    return s3.listObjects(bucketName)
        .getObjectSummaries()
        .stream()
        .map(S3ObjectSummary::getKey)
        .collect(Collectors.toSet());
  }

}
