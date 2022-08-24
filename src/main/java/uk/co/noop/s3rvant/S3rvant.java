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

  public static Store getStore(final String name) {

    Guardian.guard("name", name).againstBlankStrings();

    return STORE_LOCALS.computeIfAbsent(name, __ -> new StoreLocal());
  }

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
