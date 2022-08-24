package uk.co.noop.s3rvant.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.noop.guardian.exception.GuardianBlankTargetStringException;
import uk.co.noop.guardian.exception.GuardianEmptyTargetException;
import uk.co.noop.guardian.exception.GuardianNullTargetException;

import java.util.HashMap;
import java.util.Map;

public class StoreLocalTest {

  @Test
  public void size_empty_shouldReturnZero() {
    Assertions.assertEquals(0, new StoreLocal().size());
  }

  @Test
  public void size_nonEmpty_shouldReturnCorrectValue() {

    final Store store = new StoreLocal();

    store.put("Test Key 1", "Test Value 1");
    store.put("Test Key 2", "Test Value 2");

    Assertions.assertEquals(2, store.size());
  }

  @Test
  public void isEmpty_empty_shouldReturnTrue() {
    Assertions.assertTrue(new StoreLocal().isEmpty());
  }

  @Test
  public void isEmpty_nonEmpty_shouldReturnFalse() {

    final Store store = new StoreLocal();

    store.put("Test Key 1", "Test Value 1");
    store.put("Test Key 2", "Test Value 2");

    Assertions.assertFalse(store.isEmpty());
  }

  @Test
  public void containsKey_string_nullKey_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreLocal().containsKey(null));
  }

  @Test
  public void containsKey_string_emptyKey_shouldThrowEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> new StoreLocal().containsKey(""));
  }

  @Test
  public void containsKey_string_blankKey_shouldThrowBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> new StoreLocal().containsKey(" "));
  }

  @Test
  public void containsKey_string_doesNotContain_shouldReturnFalse() {
    Assertions.assertFalse(new StoreLocal().containsKey("Test Key 1"));
  }

  @Test
  public void containsKey_string_doesContain_shouldReturnTrue() {

    final Store store = new StoreLocal();

    store.put("Test Key 1", "Test Value 1");
    store.put("Test Key 2", "Test Value 2");

    Assertions.assertTrue(store.containsKey("Test Key 1"));
  }

  @Test
  public void containsKey_object_nullKey_shouldThrowNullTarget() {

    final Object key = null;

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreLocal().containsKey(key));
  }

  @Test
  public void containsKey_object_nonStringKey_shouldThrowClassCast() {

    final Object key = new Object();

    Assertions.assertThrows(
        ClassCastException.class,
        () -> new StoreLocal().containsKey(key));
  }

  @Test
  public void containsKey_object_happyPath_shouldReturnTrue() {

    final Store store = new StoreLocal();

    store.put("Test Key 1", "Test Value 1");
    store.put("Test Key 2", "Test Value 2");

    final Object key = "Test Key 1";

    Assertions.assertTrue(store.containsKey(key));
  }

  @Test
  public void containsValue_string_nullValue_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreLocal().containsValue(null));
  }

  @Test
  public void containsValue_string_emptyValue_shouldThrowEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> new StoreLocal().containsValue(""));
  }

  @Test
  public void containsValue_string_blankValue_shouldThrowBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> new StoreLocal().containsValue(" "));
  }

  @Test
  public void containsValue_string_doesNotContain_shouldReturnFalse() {
    Assertions.assertFalse(new StoreLocal().containsValue("Test Value 1"));
  }

  @Test
  public void containsValue_string_doesContain_shouldReturnTrue() {

    final Store store = new StoreLocal();

    store.put("Test Key 1", "Test Value 1");
    store.put("Test Key 2", "Test Value 2");

    Assertions.assertTrue(store.containsValue("Test Value 1"));
  }

  @Test
  public void containsValue_object_nullValue_shouldThrowNullTarget() {

    final Object value = null;

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreLocal().containsValue(value));
  }

  @Test
  public void containsValue_object_nonStringValue_shouldThrowClassCast() {

    final Object value = new Object();

    Assertions.assertThrows(
        ClassCastException.class,
        () -> new StoreLocal().containsValue(value));
  }

  @Test
  public void containsValue_object_happyPath_shouldReturnTure() {

    final Store store = new StoreLocal();

    store.put("Test Key 1", "Test Value 1");
    store.put("Test Key 2", "Test Value 2");

    final Object value = "Test Value 1";

    Assertions.assertTrue(store.containsValue(value));
  }

  @Test
  public void get_string_nullKey_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreLocal().get(null));
  }

  @Test
  public void get_string_emptyKey_shouldThrowEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> new StoreLocal().get(""));
  }

  @Test
  public void get_string_blankKey_shouldThrowBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> new StoreLocal().get(" "));
  }

  @Test
  public void get_string_shouldReturnValue() {

    final Store store = new StoreLocal();

    store.put("Test Key 1", "Test Value 1");
    store.put("Test Key 2", "Test Value 2");

    Assertions.assertEquals("Test Value 1", store.get("Test Key 1"));
  }

  @Test
  public void get_object_nullKey_shouldThrowNullTarget() {

    final Object key = null;

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreLocal().get(key));
  }

  @Test
  public void get_object_nonStringKey_shouldThrowClassCast() {

    final Object key = new Object();

    Assertions.assertThrows(
        ClassCastException.class,
        () -> new StoreLocal().get(key));
  }

  @Test
  public void get_object_happyPath_shouldReturnValue() {

    final Store store = new StoreLocal();

    store.put("Test Key 1", "Test Value 1");
    store.put("Test Key 2", "Test Value 2");

    final Object key = "Test Key 1";

    Assertions.assertEquals("Test Value 1", store.get(key));
  }

  @Test
  public void put_nullKey_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreLocal().put(null, "Test Value 1"));
  }

  @Test
  public void put_emptyKey_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> new StoreLocal().put("", "Test Value 1"));
  }

  @Test
  public void put_blankKey_shouldThrowBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> new StoreLocal().put(" ", "Test Value 1"));
  }

  @Test
  public void put_nullValue_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreLocal().put("Test Key 1", null));
  }

  @Test
  public void put_emptyValue_shouldThrowEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> new StoreLocal().put("Test Key 1", ""));
  }

  @Test
  public void put_blankValue_shouldThrowBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> new StoreLocal().put("Test Key 1", " "));
  }

  @Test
  public void put_noPreviousValue_shouldReturnNull() {
    Assertions.assertNull(new StoreLocal().put("Test Key 1", "Test Value 1"));
  }

  @Test
  public void put_withPreviousValue_shouldReturnPreviousValue() {

    final Store store = new StoreLocal();

    store.put("Test Key 1", "Test Value 1");
    store.put("Test Key 2", "Test Value 2");

    Assertions.assertEquals(
        "Test Value 1",
        store.put("Test Key 1", "Test Value 1B"));
  }

  @Test
  public void remove_string_nullKey_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreLocal().remove(null));
  }

  @Test
  public void remove_string_emptyKey_shouldThrowEmptyTarget() {

    Assertions.assertThrows(
        GuardianEmptyTargetException.class,
        () -> new StoreLocal().remove(""));
  }

  @Test
  public void remove_string_blankKey_shouldThrowBlankTargetString() {

    Assertions.assertThrows(
        GuardianBlankTargetStringException.class,
        () -> new StoreLocal().remove(" "));
  }

  @Test
  public void remove_string_noPreviousValue_shouldReturnNull() {
    Assertions.assertNull(new StoreLocal().remove("Test Key 1"));
  }

  @Test
  public void remove_string_withPreviousValue_shouldReturnPreviousValue() {

    final Store store = new StoreLocal();

    store.put("Test Key 1", "Test Value 1");
    store.put("Test Key 2", "Test Value 2");

    Assertions.assertEquals("Test Value 1", store.remove("Test Key 1"));
  }

  @Test
  public void remove_object_nullKey_shouldThrowNullTarget() {

    final Object key = null;

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreLocal().remove(key));
  }

  @Test
  public void remove_object_nonStringKey_shouldThrowClassCast() {

    final Object key = new Object();

    Assertions.assertThrows(
        ClassCastException.class,
        () -> new StoreLocal().remove(key));
  }

  @Test
  public void remove_object_happyPath_shouldReturnPreviousValue() {

    final Store store = new StoreLocal();

    store.put("Test Key 1", "Test Value 1");
    store.put("Test Key 2", "Test Value 2");

    final Object key = "Test Key 1";

    Assertions.assertEquals("Test Value 1", store.remove(key));
  }

  @Test
  public void putAll_nullMap_shouldThrowNullTarget() {

    Assertions.assertThrows(
        GuardianNullTargetException.class,
        () -> new StoreLocal().putAll(null));
  }

  @Test
  public void putAll_happyPath_shouldPutAll() {

    final Map<String, String> map = new HashMap<>();
    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    final Store store = new StoreLocal();
    store.putAll(map);

    Assertions.assertEquals("Test Value 1", store.get("Test Key 1"));
    Assertions.assertEquals("Test Value 2", store.get("Test Key 2"));
  }

  @Test
  public void clear_shouldRemoveAll() {

    final Store store = new StoreLocal();

    store.put("Test Key 1", "Test Value 1");
    store.put("Test Key 2", "Test Value 2");

    store.clear();

    Assertions.assertTrue(store.isEmpty());
  }

  @Test
  public void keySet_shouldReturnAllKeys() {

    final Map<String, String> map = new HashMap<>();
    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    final Store store = new StoreLocal();
    store.putAll(map);

    Assertions.assertTrue(store.keySet().containsAll(map.keySet()));
  }

  @Test
  public void values_shouldReturnAllValues() {

    final Map<String, String> map = new HashMap<>();
    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    final Store store = new StoreLocal();
    store.putAll(map);

    Assertions.assertTrue(store.values().containsAll(map.values()));
  }

  @Test
  public void entrySet_shouldReturnAllEntries() {

    final Map<String, String> map = new HashMap<>();
    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    final Store store = new StoreLocal();
    store.putAll(map);

    Assertions.assertEquals(map.size(), store.entrySet().size());
  }

}
