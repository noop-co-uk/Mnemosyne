package uk.co.noop.s3rvant.store;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.noop.guardian.exception.GuardianBlankTargetStringException;
import uk.co.noop.guardian.exception.GuardianEmptyTargetException;
import uk.co.noop.guardian.exception.GuardianInvalidTargetException;
import uk.co.noop.guardian.exception.GuardianNullTargetException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreS3Test {

  @Mock
  private AmazonS3 s3;

  @Mock
  private ObjectListing objectListing;

  private Map<String, String> map;

  private static final String TEST_BUCKET = "Test Bucket";

  @BeforeEach
  public void mock_happyPath() {

    map = new HashMap<>();

    lenient().when(s3.doesBucketExistV2(eq(TEST_BUCKET))).thenReturn(true);

    lenient().when(s3.doesObjectExist(eq(TEST_BUCKET), anyString()))
        .thenAnswer(i -> map.containsKey((String) i.getArguments()[1]));

    lenient().when(s3.getObjectAsString(eq(TEST_BUCKET), anyString()))
        .thenAnswer(i -> map.get(i.getArgument(1, String.class)));

    lenient().when(s3.putObject(eq(TEST_BUCKET), anyString(), anyString()))
        .thenAnswer(i -> {
          map.put(
              i.getArgument(1, String.class),
              i.getArgument(2, String.class));
          return null;
        });

    lenient().doAnswer(i -> map.remove(i.getArgument(1, String.class)))
        .when(s3).deleteObject(eq(TEST_BUCKET), anyString());

    lenient().when(s3.listObjects(eq(TEST_BUCKET))).thenReturn(objectListing);

    mock_objectSummaries_happyPath();
  }

  public void mock_objectSummaries_happyPath() {

    final List<S3ObjectSummary> objectSummaries = map.keySet()
        .stream()
        .map(key -> {
          final S3ObjectSummary objectSummary = mock(S3ObjectSummary.class);
          lenient().when(objectSummary.getKey()).thenReturn(key);
          return objectSummary;
        })
        .collect(Collectors.toList());

    lenient().when(objectListing.getObjectSummaries())
        .thenReturn(objectSummaries);
  }

  @AfterEach
  public void verify_NoMoreInteractions() {
    verifyNoMoreInteractions(s3, objectListing);
  }

  @Test
  public void constructor_nullS3_shouldThrowNullTarget() {
    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreS3(null, TEST_BUCKET));
  }

  @Test
  public void constructor_nullBucket_shouldThrowNullTarget() {
    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreS3(s3, null));
  }

  @Test
  public void constructor_emptyBucket_shouldThrowEmptyTarget() {
    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> new StoreS3(s3, ""));
  }

  @Test
  public void constructor_blankBucket_shouldThrowBlankTargetString() {
    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> new StoreS3(s3, " "));
  }

  @Test
  public void constructor_bucketDoesNotExist_shouldThrowInvalidTarget() {

    when(s3.doesBucketExistV2(eq(TEST_BUCKET))).thenReturn(false);

    Assertions.assertThrows(
        GuardianInvalidTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void constructor_happyPath() {

    new StoreS3(s3, TEST_BUCKET);

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void size_empty_shouldReturnZero() {

    Assertions.assertEquals(0, new StoreS3(s3, TEST_BUCKET).size());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
    verify(s3).listObjects(eq(TEST_BUCKET));
    verify(objectListing).getObjectSummaries();
  }

  @Test
  public void size_nonEmpty_shouldReturnCorrectValue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    Assertions.assertEquals(2, new StoreS3(s3, TEST_BUCKET).size());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
    verify(s3).listObjects(eq(TEST_BUCKET));
    verify(objectListing).getObjectSummaries();
  }

  @Test
  public void isEmpty_empty_shouldReturnTrue() {

    Assertions.assertTrue(new StoreS3(s3, TEST_BUCKET).isEmpty());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
    verify(s3).listObjects(eq(TEST_BUCKET));
    verify(objectListing).getObjectSummaries();
  }

  @Test
  public void isEmpty_nonEmpty_shouldReturnFalse() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    Assertions.assertFalse(new StoreS3(s3, TEST_BUCKET).isEmpty());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
    verify(s3).listObjects(eq(TEST_BUCKET));
    verify(objectListing).getObjectSummaries();
  }

  @Test
  public void containsKey_string_nullKey_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).containsKey(null));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void containsKey_string_emptyKey_shouldThrowEmptyTarget() {
    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).containsKey(""));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void containsKey_string_blankKey_shouldThrowBlankTargetString() {
    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> new StoreS3(s3, TEST_BUCKET).containsKey(" "));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void containsKey_string_doesNotContain_shouldReturnFalse() {

    Assertions.assertFalse(
        new StoreS3(s3, TEST_BUCKET).containsKey("Test Key 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
    verify(s3).doesObjectExist(eq(TEST_BUCKET), eq("Test Key 1"));
  }

  @Test
  public void containsKey_string_doesContain_shouldReturnTrue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    Assertions.assertTrue(
        new StoreS3(s3, TEST_BUCKET).containsKey("Test Key 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
    verify(s3).doesObjectExist(eq(TEST_BUCKET), eq("Test Key 1"));
  }

  @Test
  public void containsKey_object_nullKey_shouldThrowNullTarget() {

    final Object key = null;

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).containsKey(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void containsKey_object_nonStringKey_shouldThrowClassCast() {

    final Object key = new Object();

    Assertions.assertThrows(
        ClassCastException.class,
        () -> new StoreS3(s3, TEST_BUCKET).containsKey(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void containsValue_string_nullValue_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).containsValue(null));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void containsValue_string_emptyValue_shouldThrowEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).containsValue(""));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void containsValue_string_blankValue_shouldThrowBlankTargetString() {
    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> new StoreS3(s3, TEST_BUCKET).containsValue(" "));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void containsValue_string_doesNotContain_shouldReturnFalse() {

    Assertions.assertFalse(
        new StoreS3(s3, TEST_BUCKET).containsValue("Test Value 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
    verify(s3).listObjects(eq(TEST_BUCKET));
    verify(objectListing).getObjectSummaries();
  }

  @Test
  public void containsValue_string_doesContain_shouldReturnTrue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    Assertions.assertTrue(
        new StoreS3(s3, TEST_BUCKET).containsValue("Test Value 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
    verify(s3).listObjects(eq(TEST_BUCKET));
    verify(objectListing).getObjectSummaries();

    verify(s3, times(map.size()))
        .getObjectAsString(eq(TEST_BUCKET), anyString());
  }

  @Test
  public void containsValue_object_nullValue_shouldThrowNullTarget() {

    final Object value = null;

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).containsValue(value));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void containsValue_object_nonStringValue_shouldThrowClassCast() {

    final Object value = new Object();

    Assertions.assertThrows(
        ClassCastException.class,
        () -> new StoreS3(s3, TEST_BUCKET).containsValue(value));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void get_string_nullKey_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).get(null));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void get_string_emptyKey_shouldThrowEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).get(""));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void get_string_blankKey_shouldThrowBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> new StoreS3(s3, TEST_BUCKET).get(" "));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void get_string_shouldReturnValue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    Assertions.assertEquals(
        "Test Value 1",
        new StoreS3(s3, TEST_BUCKET).get("Test Key 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
    verify(s3).getObjectAsString(eq(TEST_BUCKET), eq("Test Key 1"));
  }

  @Test
  public void get_object_nullKey_shouldThrowNullTarget() {

    final Object key = null;

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).get(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void get_object_nonStringKey_shouldThrowClassCast() {

    final Object key = new Object();

    Assertions.assertThrows(
        ClassCastException.class,
        () -> new StoreS3(s3, TEST_BUCKET).get(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void put_nullKey_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).put(null, "Test Value 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void put_emptyKey_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).put("", "Test Value 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void put_blankKey_shouldThrowBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> new StoreS3(s3, TEST_BUCKET).put(" ", "Test Value 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void put_nullValue_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).put("Test Key 1", null));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void put_emptyValue_shouldThrowEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> new StoreS3(s3, TEST_BUCKET).put("Test Key 1", ""));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void put_blankValue_shouldThrowBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> new StoreS3(s3, TEST_BUCKET).put("Test Key 1", " "));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
  }

  @Test
  public void put_noPreviousValue_shouldReturnNull() {

    Assertions.assertNull(
        new StoreS3(s3, TEST_BUCKET).put("Test Key 1", "Test Value 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
    verify(s3).doesObjectExist(eq(TEST_BUCKET), eq("Test Key 1"));
    verify(s3).putObject(eq(TEST_BUCKET), eq("Test Key 1"), eq("Test Value 1"));
  }

  @Test
  public void put_withPreviousValue_shouldReturnPreviousValue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    Assertions.assertEquals(
        "Test Value 1",
        new StoreS3(s3, TEST_BUCKET).put("Test Key 1", "Test Value 1B"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET));
    verify(s3).doesObjectExist(eq(TEST_BUCKET), eq("Test Key 1"));
    verify(s3).getObjectAsString(eq(TEST_BUCKET), eq("Test Key 1"));
    verify(s3).putObject(eq(TEST_BUCKET), eq("Test Key 1"), eq("Test Value 1B"));
  }

}
