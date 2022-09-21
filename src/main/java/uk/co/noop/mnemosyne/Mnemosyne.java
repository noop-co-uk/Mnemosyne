package uk.co.noop.mnemosyne;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import uk.co.noop.mnemosyne.mneme.Bucket;
import uk.co.noop.mnemosyne.mneme.Mneme;
import uk.co.noop.mnemosyne.mneme.LocalMneme;
import uk.co.noop.mnemosyne.mneme.S3Mneme;
import uk.co.noop.themis.Themis;

import java.util.HashMap;
import java.util.Map;

public class Mnemosyne {

  private static final Map<String, Mneme> LOCAL_MNEMES = new HashMap<>();

  /**
   * <p>Creates a new {@link LocalMneme} with the specified <b>name</b>, or
   * retrieves the existing instance if one has already been created.</p>
   *
   * <p>The returned {@link Mneme} can be used for storing and retrieving
   * various key/value <code>String</code> pairs locally. This is ideal for
   * dev/testing scenarios.</p>
   *
   * <p>For example:</p>
   *
   * <p>Given a <code>Mneme</code> with the name <code>data-cache</code> and an
   * ID <code>String</code> (e.g. <code>id123</code>) you can store and retrieve
   * the data with the following;</p>
   *
   * <p><code>
   * final Mneme mneme = Mnemosyne.getMneme("data-cache");<br>
   * mneme.put("id123", "Lorem ipsum...");<br>
   * mneme.get("id123"); // will return "Lorem Ipsum..."
   * </code></p>
   *
   * <p>(See: {@link Mneme#put(String, String)} and {@link Mneme#get(String)}
   * for more information.)</p>
   *
   * <p>Note:</p>
   *
   * <p>The <b>name</b> parameter is validated against blank values using {@link
   * Themis}. (See: {@link
   * uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * StringEunomia#againstBlankStrings()} for more information.)</p>
   *
   * @param name The name of the <code>Mneme</code>.
   *
   * @return A new <code>LocalMneme</code> with the specified <b>name</b> or the
   * existing instance if one has already been created. This will never be
   * <code>null</code>.
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see LocalMneme
   * @see Mneme
   * @see Mneme#put(String, String)
   * @see Mneme#get(String)
   * @see Themis
   * @see uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  public static Mneme getMneme(final String name) {

    Themis.validate("name", name).againstBlankStrings();

    return LOCAL_MNEMES.computeIfAbsent(name, __ -> new LocalMneme());
  }

  /**
   * <p>Overloads {@link Mnemosyne#getMneme(String)}.</p>
   *
   * @param name The name of the {@link Mneme}.
   *
   * @return A new {@link LocalMneme} with the specified <b>name</b> or the
   * existing instance if one has already been created. This will never be
   * <code>null</code>.
   * 
   * @see Mnemosyne#getMneme(String)
   * @see Mneme
   * @see LocalMneme
   */
  public static Mneme mnemosyne(final String name) {
    return getMneme(name);
  }

  /**
   * <p>Overloads {@link Mnemosyne#getMneme(String)}.</p>
   *
   * @param name The name of the {@link Mneme}.
   *
   * @return A new {@link LocalMneme} with the specified <b>name</b> or the
   * existing instance if one has already been created. This will never be
   * <code>null</code>.
   *
   * @see Mnemosyne#getMneme(String)
   * @see Mneme
   * @see LocalMneme
   */
  public static Bucket bucket(final String name) {
    return getMneme(name);
  }

  /**
   * <p>Creates a new {@link S3Mneme} for the specified <b>s3BucketName</b>.</p>
   *
   * <p>The returned {@link Mneme} can be used for storing and retrieving
   * various key/value <code>String</code> pairs in <i>S3</i>. This is ideal for
   * production scenarios.</p>
   *
   * <p>For example:</p>
   *
   * <p>Given an <i>S3</i> bucket with the name <code>data-cache</code> and a
   * path <code>String</code> (e.g. <code>dir/file123</code>) you can store and
   * retrieve the data with the following;</p>
   *
   * <p><code>
   * final Mneme mneme =<br>
   * &nbsp; Mnemosyne.getMneme(<br>
   * &nbsp; &nbsp; AWS_ACCESS_KEY,<br>
   * &nbsp; &nbsp; AWS_SECRET_KEY,<br>
   * &nbsp; &nbsp; AWS_REGION,<br>
   * &nbsp; &nbsp; "data-cache");<br>
   * mneme.put("dir/file123", "Lorem ipsum...");<br>
   * mneme.get("dir/file123"); // will return "Lorem Ipsum..."</p>
   *
   * <p>(See: {@link Mneme#put(String, String)} and {@link Mneme#get(String)}
   * for more information.);</p>
   *
   * <p>Note:</p>
   *
   * <p>The <b>awsAccessKey</b>, <b>awsSecretKey</b> and <b>awsRegion</b>
   * parameters are validated against blank values using {@link Themis}. (See:
   * {@link uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * StringEunomia#againstBlankStrings()} for more information.)</p>
   *
   * <p>The <b>s3BucketName</b> parameter is validated by <code>S3Mneme</code>.
   * (See: {@link S3Mneme#S3Mneme(AmazonS3, String)} for more information.)</p>
   *
   * @param awsAccessKey The AWS access key to use to connect to S3.
   * @param awsSecretKey The AWS secret key to use to connect to S3.
   * @param awsRegion The AWS region to use to connect to S3.
   * @param s3BucketName The name of the S3 bucket.
   *
   * @return A new <code>S3Mneme</code> for the specified <b>s3BucketName</b>.
   * This will never be <code>null</code>.
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see S3Mneme
   * @see Mneme
   * @see Mneme#put(String, String)
   * @see Mneme#get(String)
   * @see Themis
   * @see uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   * @see S3Mneme#S3Mneme(AmazonS3, String)
   */
  public static Mneme getMneme(
      final String awsAccessKey,
      final String awsSecretKey,
      final String awsRegion,
      final String s3BucketName) {

    return new S3Mneme(
        getS3(awsAccessKey, awsSecretKey, awsRegion),
        s3BucketName);
  }

  /**
   * <p>Overloads {@link
   * Mnemosyne#getMneme(String, String, String, String)}.</p>
   *
   * @param awsAccessKey The AWS access key to use to connect to S3.
   * @param awsSecretKey The AWS secret key to use to connect to S3.
   * @param awsRegion The AWS region to use to connect to S3.
   * @param s3BucketName The name of the S3 bucket.

   * @return A new {@link S3Mneme} for the specified <b>s3BucketName</b>. This
   * will never be <code>null</code>.
   *
   * @see Mnemosyne#getMneme(String, String, String, String)
   * @see S3Mneme
   */
  public static Mneme mnemosyne(
      final String awsAccessKey,
      final String awsSecretKey,
      final String awsRegion,
      final String s3BucketName) {

    return getMneme(awsAccessKey, awsSecretKey, awsRegion, s3BucketName);
  }

  /**
   * <p>Overloads {@link
   * Mnemosyne#getMneme(String, String, String, String)}.</p>
   *
   * @param awsAccessKey The AWS access key to use to connect to S3.
   * @param awsSecretKey The AWS secret key to use to connect to S3.
   * @param awsRegion The AWS region to use to connect to S3.
   * @param s3BucketName The name of the S3 bucket.

   * @return A new {@link S3Mneme} for the specified <b>s3BucketName</b>. This
   * will never be <code>null</code>.
   *
   * @see Mnemosyne#getMneme(String, String, String, String)
   * @see S3Mneme
   */
  public static Bucket bucket(
      final String awsAccessKey,
      final String awsSecretKey,
      final String awsRegion,
      final String s3BucketName) {

    return getMneme(awsAccessKey, awsSecretKey, awsRegion, s3BucketName);
  }

  private static AmazonS3 getS3(
      final String accessKey,
      final String secretKey,
      final String region) {

    Themis.validate("region", region).againstBlankStrings();

    return AmazonS3ClientBuilder.standard()
        .withCredentials(getAwsCredentialsProvider(accessKey, secretKey))
        .withRegion(region)
        .build();
  }

  private static AWSCredentialsProvider getAwsCredentialsProvider(
      final String accessKey,
      final String secretKey) {

    return new AWSStaticCredentialsProvider(
        getAwsCredentials(accessKey, secretKey));
  }

  private static AWSCredentials getAwsCredentials(
      final String accessKey,
      final String secretKey) {

    Themis.validate("accessKey", accessKey).againstBlankStrings();
    Themis.validate("secretKey", secretKey).againstBlankStrings();

    return new BasicAWSCredentials(accessKey, secretKey);
  }

}
