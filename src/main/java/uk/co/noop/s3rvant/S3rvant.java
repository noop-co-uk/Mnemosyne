package uk.co.noop.s3rvant;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import uk.co.noop.guardian.Guardian;
import uk.co.noop.s3rvant.store.Store;
import uk.co.noop.s3rvant.store.StoreLocal;
import uk.co.noop.s3rvant.store.StoreS3;

import java.util.HashMap;
import java.util.Map;

public class S3rvant {

  private static final Map<String, Store> STORE_LOCALS = new HashMap<>();

  /**
   * <p>
   * Creates a new {@link StoreLocal} with the specified <code>name</code>, or
   * retrieves the existing instance if one has already been created.
   * </p>
   * <p>
   * The returned {@link Store} can be used for storing and retrieving various
   * key/value <code>String</code> pairs locally. This is ideal for dev/testing
   * scenarios.
   * </p>
   * <p>
   * For example:
   * </p>
   * <p>
   * Given a JSON response from an HTTP request, you can store and retrieve the
   * response as a file with the following (See: {@link
   * Store#put(String, String)} and {@link Store#get(String)} for more
   * information.);
   * </p>
   * <p>
   * <code>
   * final Store store = S3rvant.getStore("data-cache");<br>
   * store.put("file1", "Lorem ipsum...");<br>
   * store.get("file1"); // will return "Lorem Ipsum..."
   * </p>
   * <p>
   * Note:
   * </p>
   * <p>
   * The <code>name</code> parameter is guarded against blank values using
   * {@link Guardian} (See: {@link
   * uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * StringSidekick#againstBlankStrings()} for more information.).
   * </p>
   *
   * @param name The name of the <code>Store</code>.
   *
   * @return A new <code>StoreLocal</code> with the specified <code>name</code>
   * or the existing instance if one has already been created. This will never
   * be <code>null</code>.
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   *
   * @see StoreLocal
   * @see Store
   * @see Store#put(String, String)
   * @see Store#get(String)
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  public static Store getStore(final String name) {

    Guardian.guard("name", name).againstBlankStrings();

    return STORE_LOCALS.computeIfAbsent(name, __ -> new StoreLocal());
  }

  /**
   * <p>
   * Creates a new {@link StoreS3} for the specified <code>s3Bucket</code>.
   * </p>
   * <p>
   * The returned {@link Store} can be used for storing and retrieving various
   * key/value <code>String</code> pairs in <i>S3</i>. This is ideal for
   * production scenarios.
   * </p>
   * <p>
   * For example:
   * </p>
   * <p>
   * Given a JSON response from an HTTP request, you can store and retrieve the
   * response as a file with the following (See: {@link
   * Store#put(String, String)} and {@link Store#get(String)} for more
   * information.);
   * </p>
   * <p>
   * <code>
   * final Store store =<br>
   * &nbsp; S3rvant.getStore(AWS_ACCESS_KEY, AWS_SECRET_KEY, AWS_REGION, "data-cache");<br>
   * store.put("file1", "Lorem ipsum...");<br>
   * store.get("file1"); // will return "Lorem Ipsum..."
   * </p>
   * <p>
   * Note:
   * </p>
   * <p>
   * The <code>awsAccessKey</code>, <code>awsSecretKey</code> and
   * <code>awsRegion</code> parameters are guarded against blank values using
   * {@link Guardian} (See: {@link
   * uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * StringSidekick#againstBlankStrings()} for more information.).
   * </p>
   * <p>
   * The <code>s3Bucket</code> parameter is validated by <code>StoreS3</code>
   * (See: {@link StoreS3#StoreS3(AmazonS3, String)} for more information.).
   * </p>
   *
   * @param awsAccessKey The AWS access key to use to connect to S3.
   * @param awsSecretKey The AWS secret key to use to connect to S3.
   * @param awsRegion The AWS region to use to connect to S3.
   * @param s3Bucket The name of the S3 bucket.
   *
   * @return A new <code>StoreS3</code> for the specified <code>s3Bucket</code>.
   * This will never be <code>null</code>.
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   *
   * @see StoreS3
   * @see Store
   * @see Store#put(String, String)
   * @see Store#get(String)
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   * @see StoreS3#StoreS3(AmazonS3, String)
   */
  public static Store getStore(
      final String awsAccessKey,
      final String awsSecretKey,
      final String awsRegion,
      final String s3Bucket) {

    return new StoreS3(getS3(awsAccessKey, awsSecretKey, awsRegion), s3Bucket);
  }

  private static AmazonS3 getS3(
      final String accessKey,
      final String secretKey,
      final String region) {

    Guardian.guard("region", region).againstBlankStrings();

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

    Guardian.guard("accessKey", accessKey).againstBlankStrings();
    Guardian.guard("secretKey", secretKey).againstBlankStrings();

    return new BasicAWSCredentials(accessKey, secretKey);
  }

}
