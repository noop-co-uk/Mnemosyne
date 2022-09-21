package uk.co.noop.mnemosyne;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.co.noop.mnemosyne.mneme.Mneme;
import uk.co.noop.themis.exception.ThemisBlankTargetStringException;
import uk.co.noop.themis.exception.ThemisEmptyTargetException;
import uk.co.noop.themis.exception.ThemisNullTargetException;

import java.lang.reflect.Field;
import java.util.Map;

public class MnemosyneTest {

  private static final String TEST_NAME = "Test Name";

  private static final String TEST_AWS_ACCESS_KEY = "Test AWS Access Key";
  private static final String TEST_AWS_SECRET_KEY = "Test AWS Secret Key";
  private static final String TEST_AWS_REGION = "Test AWS Region";
  private static final String TEST_S3_BUCKET_NAME = "Test S3 Bucket";

  private static final String TEST_KEY = "Test Key";
  private static final String TEST_VALUE = "Test Value";

  @AfterEach
  public void teardown() {

    final Field field;

    try {

      field = Mnemosyne.class.getDeclaredField("LOCAL_MNEMES");
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

    Mneme mneme = Mnemosyne.getMneme(TEST_NAME);

    Assertions.assertNotNull(mneme);

    mneme.put(TEST_KEY, TEST_VALUE);
    Assertions.assertEquals(TEST_VALUE, mneme.get(TEST_KEY));

    mneme = Mnemosyne.getMneme(TEST_NAME);
    Assertions.assertEquals(TEST_VALUE, mneme.get(TEST_KEY));

    new MnemosyneTest().teardown();
    mneme = Mnemosyne.getMneme(TEST_NAME);
    Assertions.assertFalse(mneme.containsKey(TEST_KEY));
  }

  @Test
  public void getMneme_local_nullName_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> Mnemosyne.getMneme(null));
  }

  @Test
  public void getMneme_local_emptyName_shouldThrowThemisEmptyTarget() {

    Assertions.assertThrows(
        ThemisEmptyTargetException.class,
        () -> Mnemosyne.getMneme(""));
  }

  @Test
  public void getMneme_local_blankName_shouldThrowThemisBlankTargetString() {

    Assertions.assertThrows(
        ThemisBlankTargetStringException.class,
        () -> Mnemosyne.getMneme(" "));
  }

  @Test
  public void getMneme_local_shouldNotReturnNull() {
    Assertions.assertNotNull(Mnemosyne.getMneme(TEST_NAME));
  }

  @Test
  public void getMneme_local_shouldReturnSingleInstance() {

    Mneme mneme = Mnemosyne.getMneme(TEST_NAME);
    mneme.put(TEST_KEY, TEST_VALUE);

    mneme = Mnemosyne.getMneme(TEST_NAME);
    Assertions.assertEquals(TEST_VALUE, mneme.get(TEST_KEY));
  }

  @Test
  public void getMneme_s3_nullAwsAccessKey_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> Mnemosyne.getMneme(
            null,
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_emptyAwsAccessKey_shouldThrowThemisEmptyTarget() {

    Assertions.assertThrows(
        ThemisEmptyTargetException.class,
        () -> Mnemosyne.getMneme(
            "",
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_blankAwsAccessKey_shouldThrowThemisBlankTargetString() {

    Assertions.assertThrows(
        ThemisBlankTargetStringException.class,
        () -> Mnemosyne.getMneme(
            " ",
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_nullAwsSecretKey_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            null,
            TEST_AWS_REGION,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_emptyAwsSecretKey_shouldThrowThemisEmptyTarget() {

    Assertions.assertThrows(
        ThemisEmptyTargetException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            "",
            TEST_AWS_REGION,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_blankAwsSecretKey_shouldThrowThemisBlankTargetString() {

    Assertions.assertThrows(
        ThemisBlankTargetStringException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            " ",
            TEST_AWS_REGION,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_nullAwsRegion_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            null,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_emptyAwsRegion_shouldThrowThemisEmptyTarget() {

    Assertions.assertThrows(
        ThemisEmptyTargetException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            "",
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_blankAwsRegion_shouldThrowThemisBlankTargetString() {

    Assertions.assertThrows(
        ThemisBlankTargetStringException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            " ",
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_nullS3BucketName_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            null));
  }

  @Test
  public void getMneme_s3_emptyS3BucketName_shouldThrowThemisEmptyTarget() {

    Assertions.assertThrows(
        ThemisEmptyTargetException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            ""));
  }

  @Test
  public void getMneme_s3_blankS3BucketName_shouldThrowThemisBlankTargetString() {

    Assertions.assertThrows(
        ThemisBlankTargetStringException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            " "));
  }

}
