package uk.co.noop.mnemosyne;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.co.noop.mnemosyne.mneme.Mneme;
import uk.co.noop.themis.exception.ThemisBlankTargetStringException;
import uk.co.noop.themis.exception.ThemisEmptyTargetException;
import uk.co.noop.themis.exception.ThemisNullTargetException;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
      
      if (value instanceof Map<?, ?> map) {
        map.clear();
      }
      
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  @BeforeAll
  public static void teardown_test() {

    Mneme mneme = Mnemosyne.getMneme(TEST_NAME);

    assertNotNull(mneme);

    mneme.put(TEST_KEY, TEST_VALUE);
    assertEquals(TEST_VALUE, mneme.get(TEST_KEY));

    mneme = Mnemosyne.getMneme(TEST_NAME);
    assertEquals(TEST_VALUE, mneme.get(TEST_KEY));

    new MnemosyneTest().teardown();
    mneme = Mnemosyne.getMneme(TEST_NAME);
    assertFalse(mneme.containsKey(TEST_KEY));
  }

  @Test
  public void getMneme_local_nullName_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> Mnemosyne.getMneme(null));
  }

  @Test
  public void getMneme_local_emptyName_shouldThrowThemisEmptyTarget() {

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> Mnemosyne.getMneme(""));
  }

  @Test
  public void getMneme_local_blankName_shouldThrowThemisBlankTargetString() {

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> Mnemosyne.getMneme(" "));
  }

  @Test
  public void getMneme_local_shouldNotReturnNull() {
    assertNotNull(Mnemosyne.getMneme(TEST_NAME));
  }

  @Test
  public void getMneme_local_shouldReturnSingleInstance() {

    Mneme mneme = Mnemosyne.getMneme(TEST_NAME);
    mneme.put(TEST_KEY, TEST_VALUE);

    mneme = Mnemosyne.getMneme(TEST_NAME);
    assertEquals(TEST_VALUE, mneme.get(TEST_KEY));
  }

  @Test
  public void getMneme_s3_nullAwsAccessKey_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> Mnemosyne.getMneme(
            null,
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_emptyAwsAccessKey_shouldThrowThemisEmptyTarget() {

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> Mnemosyne.getMneme(
            "",
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_blankAwsAccessKey_shouldThrowThemisBlankTargetString() {

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> Mnemosyne.getMneme(
            " ",
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_nullAwsSecretKey_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            null,
            TEST_AWS_REGION,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_emptyAwsSecretKey_shouldThrowThemisEmptyTarget() {

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            "",
            TEST_AWS_REGION,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_blankAwsSecretKey_shouldThrowThemisBlankTargetString() {

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            " ",
            TEST_AWS_REGION,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_nullAwsRegion_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            null,
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_emptyAwsRegion_shouldThrowThemisEmptyTarget() {

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            "",
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_blankAwsRegion_shouldThrowThemisBlankTargetString() {

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            " ",
            TEST_S3_BUCKET_NAME));
  }

  @Test
  public void getMneme_s3_nullS3BucketName_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            null));
  }

  @Test
  public void getMneme_s3_emptyS3BucketName_shouldThrowThemisEmptyTarget() {

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            ""));
  }

  @Test
  public void getMneme_s3_blankS3BucketName_shouldThrowThemisBlankTargetString() {

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> Mnemosyne.getMneme(
            TEST_AWS_ACCESS_KEY,
            TEST_AWS_SECRET_KEY,
            TEST_AWS_REGION,
            " "));
  }

}
