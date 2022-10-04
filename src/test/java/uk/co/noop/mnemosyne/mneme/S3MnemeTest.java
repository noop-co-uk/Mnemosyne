package uk.co.noop.mnemosyne.mneme;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.noop.themis.exception.ThemisBlankTargetStringException;
import uk.co.noop.themis.exception.ThemisEmptyTargetException;
import uk.co.noop.themis.exception.ThemisInvalidTargetException;
import uk.co.noop.themis.exception.ThemisNullTargetException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class S3MnemeTest {

  @Mock
  private AmazonS3 s3;

  @Mock
  private ObjectListing objectListing;

  private Map<String, String> map;

  private static final String TEST_BUCKET_NAME = "Test Bucket Name";

  @BeforeEach
  public void mock_happyPath() {

    map = new HashMap<>();

    lenient().when(s3.doesBucketExistV2(eq(TEST_BUCKET_NAME))).thenReturn(true);

    lenient().when(s3.doesObjectExist(eq(TEST_BUCKET_NAME), anyString()))
        .thenAnswer(i -> map.containsKey((String) i.getArguments()[1]));

    lenient().when(s3.getObjectAsString(eq(TEST_BUCKET_NAME), anyString()))
        .thenAnswer(i -> map.get(i.getArgument(1, String.class)));

    lenient().when(s3.putObject(eq(TEST_BUCKET_NAME), anyString(), anyString()))
        .thenAnswer(i -> {
          map.put(
              i.getArgument(1, String.class),
              i.getArgument(2, String.class));
          return null;
        });

    lenient().doAnswer(i -> map.remove(i.getArgument(1, String.class)))
        .when(s3).deleteObject(eq(TEST_BUCKET_NAME), anyString());

    lenient().when(s3.listObjects(eq(TEST_BUCKET_NAME)))
        .thenReturn(objectListing);

    mock_objectSummaries_happyPath();
  }

  public void mock_objectSummaries_happyPath() {

    final List<S3ObjectSummary> objectSummaries =
        map.keySet()
            .stream()
            .map(key -> {
              final S3ObjectSummary objectSummary = mock(S3ObjectSummary.class);
              lenient().when(objectSummary.getKey()).thenReturn(key);
              return objectSummary;
        })
        .collect(toList());

    lenient().when(objectListing.getObjectSummaries())
        .thenReturn(objectSummaries);
  }

  @AfterEach
  public void verify_NoMoreInteractions() {
    verifyNoMoreInteractions(s3, objectListing);
  }

  @Test
  public void constructor_nullS3_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(null, TEST_BUCKET_NAME));
  }

  @Test
  public void constructor_nullBucketName_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, null));
  }

  @Test
  public void constructor_emptyBucketName_shouldThrowThemisEmptyTarget() {

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> new S3Mneme(s3, ""));
  }

  @Test
  public void constructor_blankBucketName_shouldThrowThemisBlankTargetString() {

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new S3Mneme(s3, " "));
  }

  @Test
  public void constructor_bucketDoesNotExist_shouldThrowThemisInvalidTarget() {

    when(s3.doesBucketExistV2(eq(TEST_BUCKET_NAME))).thenReturn(false);

    assertThrows(
        ThemisInvalidTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void constructor_happyPath() {

    new S3Mneme(s3, TEST_BUCKET_NAME);

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void size_empty_shouldReturnZero() {

    assertEquals(0, new S3Mneme(s3, TEST_BUCKET_NAME).size());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
  }

  @Test
  public void size_nonEmpty_shouldReturnCorrectValue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    assertEquals(2, new S3Mneme(s3, TEST_BUCKET_NAME).size());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
  }

  @Test
  public void isEmpty_empty_shouldReturnTrue() {

    assertTrue(new S3Mneme(s3, TEST_BUCKET_NAME).isEmpty());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
  }

  @Test
  public void isEmpty_nonEmpty_shouldReturnFalse() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    assertFalse(new S3Mneme(s3, TEST_BUCKET_NAME).isEmpty());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
  }

  @Test
  public void containsKey_string_nullKey_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).containsKey(null));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void containsKey_string_emptyKey_shouldThrowThemisEmptyTarget() {

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).containsKey(""));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void containsKey_string_blankKey_shouldThrowThemisBlankTargetString() {

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).containsKey(" "));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void containsKey_string_doesNotContain_shouldReturnFalse() {

    assertFalse(
        new S3Mneme(s3, TEST_BUCKET_NAME).containsKey("Test Key 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).doesObjectExist(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
  }

  @Test
  public void containsKey_string_doesContain_shouldReturnTrue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    assertTrue(
        new S3Mneme(s3, TEST_BUCKET_NAME).containsKey("Test Key 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).doesObjectExist(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
  }

  @Test
  public void containsKey_object_nullKey_shouldThrowThemisNullTarget() {

    final Object key = null;

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).containsKey(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void containsKey_object_nonStringKey_shouldThrowClassCast() {

    final Object key = new Object();

    assertThrows(
        ClassCastException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).containsKey(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void containsKey_object_happyPath_shouldReturnTrue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    final Object key = "Test Key 1";

    assertTrue(new S3Mneme(s3, TEST_BUCKET_NAME).containsKey(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).doesObjectExist(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
  }

  @Test
  public void containsValue_string_nullValue_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).containsValue(null));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void containsValue_string_emptyValue_shouldThrowThemisEmptyTarget() {

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).containsValue(""));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void containsValue_string_blankValue_shouldThrowThemisBlankTargetString() {

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).containsValue(" "));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void containsValue_string_doesNotContain_shouldReturnFalse() {

    assertFalse(
        new S3Mneme(s3, TEST_BUCKET_NAME).containsValue("Test Value 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
  }

  @Test
  public void containsValue_string_doesContain_shouldReturnTrue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    assertTrue(
        new S3Mneme(s3, TEST_BUCKET_NAME).containsValue("Test Value 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();

    verify(s3, times(map.size()))
        .getObjectAsString(eq(TEST_BUCKET_NAME), anyString());
  }

  @Test
  public void containsValue_object_nullValue_shouldThrowThemisNullTarget() {

    final Object value = null;

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).containsValue(value));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void containsValue_object_nonStringValue_shouldThrowClassCast() {

    final Object value = new Object();

    assertThrows(
        ClassCastException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).containsValue(value));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void containsValue_object_happyPath_shouldReturnTure() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    final Object value = "Test Value 1";

    assertTrue(
        new S3Mneme(s3, TEST_BUCKET_NAME).containsValue(value));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();

    verify(s3, times(map.size()))
        .getObjectAsString(eq(TEST_BUCKET_NAME), anyString());
  }

  @Test
  public void get_string_nullKey_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).get(null));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void get_string_emptyKey_shouldThrowThemisEmptyTarget() {

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).get(""));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void get_string_blankKey_shouldThrowThemisBlankTargetString() {

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).get(" "));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void get_string_shouldReturnValue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    assertEquals(
        "Test Value 1",
        new S3Mneme(s3, TEST_BUCKET_NAME).get("Test Key 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).getObjectAsString(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
  }

  @Test
  public void get_object_nullKey_shouldThrowThemisNullTarget() {

    final Object key = null;

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).get(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void get_object_nonStringKey_shouldThrowClassCast() {

    final Object key = new Object();

    assertThrows(
        ClassCastException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).get(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void get_object_happyPath_shouldReturnValue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    final Object key = "Test Key 1";

    assertEquals(
        "Test Value 1",
        new S3Mneme(s3, TEST_BUCKET_NAME).get(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).getObjectAsString(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
  }

  @Test
  public void put_nullKey_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).put(null, "Test Value 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void put_emptyKey_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).put("", "Test Value 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void put_blankKey_shouldThrowThemisBlankTargetString() {

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).put(" ", "Test Value 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void put_nullValue_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).put("Test Key 1", null));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void put_emptyValue_shouldThrowThemisEmptyTarget() {

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).put("Test Key 1", ""));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void put_blankValue_shouldThrowThemisBlankTargetString() {

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).put("Test Key 1", " "));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void put_noPreviousValue_shouldReturnNull() {

    assertNull(
        new S3Mneme(s3, TEST_BUCKET_NAME).put("Test Key 1", "Test Value 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).doesObjectExist(eq(TEST_BUCKET_NAME), eq("Test Key 1"));

    verify(s3)
        .putObject(eq(TEST_BUCKET_NAME), eq("Test Key 1"), eq("Test Value 1"));
  }

  @Test
  public void put_withPreviousValue_shouldReturnPreviousValue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    assertEquals(
        "Test Value 1",
        new S3Mneme(s3, TEST_BUCKET_NAME).put("Test Key 1", "Test Value 1B"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).doesObjectExist(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
    verify(s3).getObjectAsString(eq(TEST_BUCKET_NAME), eq("Test Key 1"));

    verify(s3)
        .putObject(eq(TEST_BUCKET_NAME), eq("Test Key 1"), eq("Test Value 1B"));
  }

  @Test
  public void remove_string_nullKey_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).remove(null));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void remove_string_emptyKey_shouldThrowThemisEmptyTarget() {

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).remove(""));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void remove_string_blankKey_shouldThrowThemisBlankTargetString() {

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).remove(" "));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void remove_string_noPreviousValue_shouldReturnNull() {

    assertNull(new S3Mneme(s3, TEST_BUCKET_NAME).remove("Test Key 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).doesObjectExist(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
  }

  @Test
  public void remove_string_withPreviousValue_shouldReturnPreviousValue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    assertEquals(
        "Test Value 1",
        new S3Mneme(s3, TEST_BUCKET_NAME).remove("Test Key 1"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).doesObjectExist(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
    verify(s3).getObjectAsString(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
    verify(s3).deleteObject(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
  }

  @Test
  public void remove_object_nullKey_shouldThrowThemisNullTarget() {

    final Object key = null;

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).remove(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void remove_object_nonStringKey_shouldThrowClassCast() {

    final Object key = new Object();

    assertThrows(
        ClassCastException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).remove(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void remove_object_happyPath_shouldReturnPreviousValue() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    final Object key = "Test Key 1";

    assertEquals("Test Value 1", new S3Mneme(s3, TEST_BUCKET_NAME).remove(key));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).doesObjectExist(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
    verify(s3).getObjectAsString(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
    verify(s3).deleteObject(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
  }

  @Test
  public void putAll_nullMap_shouldThrowThemisNullTarget() {

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).putAll(null));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
  }

  @Test
  public void putAll_happyPath_shouldPutAll() {

    final Map<String, String> map = new HashMap<>();
    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    final Mneme mneme = new S3Mneme(s3, TEST_BUCKET_NAME);
    mneme.putAll(map);

    assertEquals("Test Value 1", mneme.get("Test Key 1"));
    assertEquals("Test Value 2", mneme.get("Test Key 2"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));

    verify(s3, times(map.size()))
        .doesObjectExist(eq(TEST_BUCKET_NAME), anyString());

    verify(s3, times(map.size()))
        .putObject(eq(TEST_BUCKET_NAME), anyString(), anyString());

    verify(s3, times(map.size()))
        .getObjectAsString(eq(TEST_BUCKET_NAME), anyString());
  }

  @Test
  public void clear_shouldRemoveAll() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    final int mapSize = map.size();

    mock_objectSummaries_happyPath();

    new S3Mneme(s3, TEST_BUCKET_NAME).clear();

    assertTrue(map.isEmpty());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();

    verify(s3, times(mapSize))
        .doesObjectExist(eq(TEST_BUCKET_NAME), anyString());

    verify(s3, times(mapSize))
        .getObjectAsString(eq(TEST_BUCKET_NAME), anyString());

    verify(s3, times(mapSize)).deleteObject(eq(TEST_BUCKET_NAME), anyString());
  }

  @Test
  public void keySet_shouldReturnAllKeys() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    assertTrue(
        new S3Mneme(s3, TEST_BUCKET_NAME).keySet().containsAll(map.keySet()));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
  }

  @Test
  public void values_shouldReturnAllValues() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    assertTrue(
        new S3Mneme(s3, TEST_BUCKET_NAME).values().containsAll(map.values()));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();

    verify(s3, times(map.size()))
        .getObjectAsString(eq(TEST_BUCKET_NAME), anyString());
  }

  @Test
  public void entrySet_shouldReturnAllEntries() {

    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    mock_objectSummaries_happyPath();

    assertEquals(
        map.size(),
        new S3Mneme(s3, TEST_BUCKET_NAME).entrySet().size());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
    verify(s3, never()).getObjectAsString(eq(TEST_BUCKET_NAME), anyString());
  }

  @Test
  public void entry_constructor_nullKey_shouldThrowThemisNullTarget() {

    when(objectListing.getObjectSummaries())
        .thenReturn(Collections.singletonList(mock(S3ObjectSummary.class)));

    assertThrows(
        ThemisNullTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).entrySet());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
    verify(s3, never()).getObjectAsString(eq(TEST_BUCKET_NAME), anyString());
  }

  @Test
  public void entry_constructor_emptyKey_shouldThrowThemisEmptyTarget() {

    final S3ObjectSummary objectSummary = mock(S3ObjectSummary.class);

    when(objectListing.getObjectSummaries())
        .thenReturn(Collections.singletonList(objectSummary));

    when(objectSummary.getKey()).thenReturn("");

    assertThrows(
        ThemisEmptyTargetException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).entrySet());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
    verify(s3, never()).getObjectAsString(eq(TEST_BUCKET_NAME), anyString());
  }

  @Test
  public void entry_constructor_blankKey_shouldThrowThemisBlankTargetString() {

    final S3ObjectSummary objectSummary = mock(S3ObjectSummary.class);

    when(objectListing.getObjectSummaries())
        .thenReturn(Collections.singletonList(objectSummary));

    when(objectSummary.getKey()).thenReturn(" ");

    assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new S3Mneme(s3, TEST_BUCKET_NAME).entrySet());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
    verify(s3, never()).getObjectAsString(eq(TEST_BUCKET_NAME), anyString());
  }

  @Test
  public void entry_constructor_getKey_happyPath() {

    map.put("Test Key 1", "Test Value 1");

    mock_objectSummaries_happyPath();

    assertEquals(
        "Test Key 1",
        new S3Mneme(s3, TEST_BUCKET_NAME)
            .entrySet()
            .iterator()
            .next()
            .getKey());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
    verify(s3, never()).getObjectAsString(eq(TEST_BUCKET_NAME), anyString());
  }

  @Test
  public void entry_getValue_shouldLazyLoadValue() {

    map.put("Test Key 1", "Test Value 1");

    mock_objectSummaries_happyPath();

    assertEquals(
        "Test Value 1",
        new S3Mneme(s3, TEST_BUCKET_NAME)
            .entrySet()
            .iterator()
            .next()
            .getValue());

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
    verify(s3).getObjectAsString(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
  }

  @Test
  public void entry_setValue_happyPath_shouldReturnPreviousValue() {

    map.put("Test Key 1", "Test Value 1");

    mock_objectSummaries_happyPath();

    assertEquals(
        "Test Value 1",
        new S3Mneme(s3, TEST_BUCKET_NAME)
            .entrySet()
            .iterator()
            .next()
            .setValue("Test Value 1B"));

    verify(s3).doesBucketExistV2(eq(TEST_BUCKET_NAME));
    verify(s3).listObjects(eq(TEST_BUCKET_NAME));
    verify(objectListing).getObjectSummaries();
    verify(s3).doesObjectExist(eq(TEST_BUCKET_NAME), eq("Test Key 1"));
    verify(s3).getObjectAsString(eq(TEST_BUCKET_NAME), eq("Test Key 1"));

    verify(s3)
        .putObject(eq(TEST_BUCKET_NAME), eq("Test Key 1"), eq("Test Value 1B"));
  }

}
