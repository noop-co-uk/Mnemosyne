package uk.co.noop.s3rvant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.co.noop.guardian.exception.GuardianBlankTargetStringException;
import uk.co.noop.guardian.exception.GuardianEmptyTargetException;
import uk.co.noop.guardian.exception.GuardianNullTargetException;
import uk.co.noop.s3rvant.store.Store;

import java.lang.reflect.Field;
import java.util.Map;

public class S3rvantTest {

  private static final String TEST_NAME = "Test Name";

  private static final String TEST_AWS_ACCESS_KEY = "Test AWS Access Key";
  private static final String TEST_AWS_SECRET_KEY = "Test AWS Secret Key";
  private static final String TEST_AWS_REGION = "Test AWS Region";
  private static final String TEST_S3_BUCKET = "Test S3 Bucket";

  private static final String TEST_KEY = "Test Key";
  private static final String TEST_VALUE = "Test Value";

  @AfterEach
  public void teardown() {

    final Field field;

    try {

      field = S3rvant.class.getDeclaredField("STORE_LOCALS");
      field.setAccessible(true);
      
      final Object value = field.get(null);
      
      if (value instanceof Map) {
        ((Map<?, ?>) value).clear();
      }
      
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  @BeforeAll
  public static void teardown_test() {

    Store store = S3rvant.getStore(TEST_NAME);

    Assertions.assertNotNull(store);

    store.put(TEST_KEY, TEST_VALUE);
    Assertions.assertEquals(TEST_VALUE, store.get(TEST_KEY));

    store = S3rvant.getStore(TEST_NAME);
    Assertions.assertEquals(TEST_VALUE, store.get(TEST_KEY));

    new S3rvantTest().teardown();
    store = S3rvant.getStore(TEST_NAME);
    Assertions.assertFalse(store.containsKey(TEST_KEY));
  }

  @Test
  public void getStore_local_nullName_shouldThrowGuardianNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> S3rvant.getStore(null));
  }

  @Test
  public void getStore_local_emptyName_shouldThrowGuardianEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> S3rvant.getStore(""));
  }

  @Test
  public void getStore_local_blankName_shouldThrowGuardianBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> S3rvant.getStore(" "));
  }

  @Test
  public void getStore_local_shouldNotReturnNull() {
    Assertions.assertNotNull(S3rvant.getStore(TEST_NAME));
  }

  @Test
  public void getStore_local_shouldReturnSingleInstance() {

    Store store = S3rvant.getStore(TEST_NAME);
    store.put(TEST_KEY, TEST_VALUE);

    store = S3rvant.getStore(TEST_NAME);
    Assertions.assertEquals(TEST_VALUE, store.get(TEST_KEY));
  }

  @Test
  public void getStore_s3_nullAwsAccessKey_shouldThrowGuardianNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> S3rvant.getStore(
            null,
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            TEST_S3_BUCKET));
  }

  @Test
  public void getStore_s3_emptyAwsAccessKey_shouldThrowGuardianEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> S3rvant.getStore(
            "",
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            TEST_S3_BUCKET));
  }

  @Test
  public void getStore_s3_blankAwsAccessKey_shouldThrowGuardianBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> S3rvant.getStore(
            " ",
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            TEST_S3_BUCKET));
  }

  @Test
  public void getStore_s3_nullAwsSecretKey_shouldThrowGuardianNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> S3rvant.getStore(
            TEST_AWS_ACCESS_KEY,
            null,
            TEST_AWS_REGION,
            TEST_S3_BUCKET));
  }

  @Test
  public void getStore_s3_emptyAwsSecretKey_shouldThrowGuardianEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> S3rvant.getStore(
            TEST_AWS_ACCESS_KEY,
            "",
            TEST_AWS_REGION,
            TEST_S3_BUCKET));
  }

  @Test
  public void getStore_s3_blankAwsSecretKey_shouldThrowGuardianBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> S3rvant.getStore(
            TEST_AWS_ACCESS_KEY,
            " ",
            TEST_AWS_REGION,
            TEST_S3_BUCKET));
  }

  @Test
  public void getStore_s3_nullAwsRegion_shouldThrowGuardianNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> S3rvant.getStore(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            null,
            TEST_S3_BUCKET));
  }

  @Test
  public void getStore_s3_emptyAwsRegion_shouldThrowGuardianEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> S3rvant.getStore(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            "",
            TEST_S3_BUCKET));
  }

  @Test
  public void getStore_s3_blankAwsRegion_shouldThrowGuardianBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> S3rvant.getStore(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            " ",
            TEST_S3_BUCKET));
  }

  @Test
  public void getStore_s3_nullS3Bucket_shouldThrowGuardianNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> S3rvant.getStore(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            null));
  }

  @Test
  public void getStore_s3_emptyS3Bucket_shouldThrowGuardianEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> S3rvant.getStore(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            ""));
  }

  @Test
  public void getStore_s3_blankS3Bucket_shouldThrowGuardianBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> S3rvant.getStore(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            " "));
  }

}
