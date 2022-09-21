package uk.co.noop.mnemosyne.mneme;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.noop.themis.exception.ThemisBlankTargetStringException;
import uk.co.noop.themis.exception.ThemisEmptyTargetException;
import uk.co.noop.themis.exception.ThemisNullTargetException;

import java.util.HashMap;
import java.util.Map;

public class LocalMnemeTest {

  @Test
  public void size_empty_shouldReturnZero() {
    Assertions.assertEquals(0, new LocalMneme().size());
  }

  @Test
  public void size_nonEmpty_shouldReturnCorrectValue() {

    final Mneme mneme = new LocalMneme();

    mneme.put("Test Key 1", "Test Value 1");
    mneme.put("Test Key 2", "Test Value 2");

    Assertions.assertEquals(2, mneme.size());
  }

  @Test
  public void isEmpty_empty_shouldReturnTrue() {
    Assertions.assertTrue(new LocalMneme().isEmpty());
  }

  @Test
  public void isEmpty_nonEmpty_shouldReturnFalse() {

    final Mneme mneme = new LocalMneme();

    mneme.put("Test Key 1", "Test Value 1");
    mneme.put("Test Key 2", "Test Value 2");

    Assertions.assertFalse(mneme.isEmpty());
  }

  @Test
  public void containsKey_string_nullKey_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> new LocalMneme().containsKey(null));
  }

  @Test
  public void containsKey_string_emptyKey_shouldThrowThemisEmptyTarget() {

    Assertions.assertThrows(
        ThemisEmptyTargetException.class,
        () -> new LocalMneme().containsKey(""));
  }

  @Test
  public void containsKey_string_blankKey_shouldThrowThemisBlankTargetString() {

    Assertions.assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new LocalMneme().containsKey(" "));
  }

  @Test
  public void containsKey_string_doesNotContain_shouldReturnFalse() {
    Assertions.assertFalse(new LocalMneme().containsKey("Test Key 1"));
  }

  @Test
  public void containsKey_string_doesContain_shouldReturnTrue() {

    final Mneme mneme = new LocalMneme();

    mneme.put("Test Key 1", "Test Value 1");
    mneme.put("Test Key 2", "Test Value 2");

    Assertions.assertTrue(mneme.containsKey("Test Key 1"));
  }

  @Test
  public void containsKey_object_nullKey_shouldThrowThemisNullTarget() {

    final Object key = null;

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> new LocalMneme().containsKey(key));
  }

  @Test
  public void containsKey_object_nonStringKey_shouldThrowClassCast() {

    final Object key = new Object();

    Assertions.assertThrows(
        ClassCastException.class,
        () -> new LocalMneme().containsKey(key));
  }

  @Test
  public void containsKey_object_happyPath_shouldReturnTrue() {

    final Mneme mneme = new LocalMneme();

    mneme.put("Test Key 1", "Test Value 1");
    mneme.put("Test Key 2", "Test Value 2");

    final Object key = "Test Key 1";

    Assertions.assertTrue(mneme.containsKey(key));
  }

  @Test
  public void containsValue_string_nullValue_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> new LocalMneme().containsValue(null));
  }

  @Test
  public void containsValue_string_emptyValue_shouldThrowThemisEmptyTarget() {

    Assertions.assertThrows(
        ThemisEmptyTargetException.class,
        () -> new LocalMneme().containsValue(""));
  }

  @Test
  public void containsValue_string_blankValue_shouldThrowThemisBlankTargetString() {

    Assertions.assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new LocalMneme().containsValue(" "));
  }

  @Test
  public void containsValue_string_doesNotContain_shouldReturnFalse() {
    Assertions.assertFalse(new LocalMneme().containsValue("Test Value 1"));
  }

  @Test
  public void containsValue_string_doesContain_shouldReturnTrue() {

    final Mneme mneme = new LocalMneme();

    mneme.put("Test Key 1", "Test Value 1");
    mneme.put("Test Key 2", "Test Value 2");

    Assertions.assertTrue(mneme.containsValue("Test Value 1"));
  }

  @Test
  public void containsValue_object_nullValue_shouldThrowThemisNullTarget() {

    final Object value = null;

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> new LocalMneme().containsValue(value));
  }

  @Test
  public void containsValue_object_nonStringValue_shouldThrowClassCast() {

    final Object value = new Object();

    Assertions.assertThrows(
        ClassCastException.class,
        () -> new LocalMneme().containsValue(value));
  }

  @Test
  public void containsValue_object_happyPath_shouldReturnTure() {

    final Mneme mneme = new LocalMneme();

    mneme.put("Test Key 1", "Test Value 1");
    mneme.put("Test Key 2", "Test Value 2");

    final Object value = "Test Value 1";

    Assertions.assertTrue(mneme.containsValue(value));
  }

  @Test
  public void get_string_nullKey_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> new LocalMneme().get(null));
  }

  @Test
  public void get_string_emptyKey_shouldThrowThemisEmptyTarget() {

    Assertions.assertThrows(
        ThemisEmptyTargetException.class,
        () -> new LocalMneme().get(""));
  }

  @Test
  public void get_string_blankKey_shouldThrowThemisBlankTargetString() {

    Assertions.assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new LocalMneme().get(" "));
  }

  @Test
  public void get_string_shouldReturnValue() {

    final Mneme mneme = new LocalMneme();

    mneme.put("Test Key 1", "Test Value 1");
    mneme.put("Test Key 2", "Test Value 2");

    Assertions.assertEquals("Test Value 1", mneme.get("Test Key 1"));
  }

  @Test
  public void get_object_nullKey_shouldThrowNullTarget() {

    final Object key = null;

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> new LocalMneme().get(key));
  }

  @Test
  public void get_object_nonStringKey_shouldThrowClassCast() {

    final Object key = new Object();

    Assertions.assertThrows(
        ClassCastException.class,
        () -> new LocalMneme().get(key));
  }

  @Test
  public void get_object_happyPath_shouldReturnValue() {

    final Mneme mneme = new LocalMneme();

    mneme.put("Test Key 1", "Test Value 1");
    mneme.put("Test Key 2", "Test Value 2");

    final Object key = "Test Key 1";

    Assertions.assertEquals("Test Value 1", mneme.get(key));
  }

  @Test
  public void put_nullKey_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> new LocalMneme().put(null, "Test Value 1"));
  }

  @Test
  public void put_emptyKey_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisEmptyTargetException.class,
        () -> new LocalMneme().put("", "Test Value 1"));
  }

  @Test
  public void put_blankKey_shouldThrowThemisBlankTargetString() {

    Assertions.assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new LocalMneme().put(" ", "Test Value 1"));
  }

  @Test
  public void put_nullValue_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> new LocalMneme().put("Test Key 1", null));
  }

  @Test
  public void put_emptyValue_shouldThrowThemisEmptyTarget() {

    Assertions.assertThrows(
        ThemisEmptyTargetException.class,
        () -> new LocalMneme().put("Test Key 1", ""));
  }

  @Test
  public void put_blankValue_shouldThrowThemisBlankTargetString() {

    Assertions.assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new LocalMneme().put("Test Key 1", " "));
  }

  @Test
  public void put_noPreviousValue_shouldReturnNull() {
    Assertions.assertNull(new LocalMneme().put("Test Key 1", "Test Value 1"));
  }

  @Test
  public void put_withPreviousValue_shouldReturnPreviousValue() {

    final Mneme mneme = new LocalMneme();

    mneme.put("Test Key 1", "Test Value 1");
    mneme.put("Test Key 2", "Test Value 2");

    Assertions.assertEquals(
        "Test Value 1",
        mneme.put("Test Key 1", "Test Value 1B"));
  }

  @Test
  public void remove_string_nullKey_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> new LocalMneme().remove(null));
  }

  @Test
  public void remove_string_emptyKey_shouldThrowThemisEmptyTarget() {

    Assertions.assertThrows(
        ThemisEmptyTargetException.class,
        () -> new LocalMneme().remove(""));
  }

  @Test
  public void remove_string_blankKey_shouldThrowThemisBlankTargetString() {

    Assertions.assertThrows(
        ThemisBlankTargetStringException.class,
        () -> new LocalMneme().remove(" "));
  }

  @Test
  public void remove_string_noPreviousValue_shouldReturnNull() {
    Assertions.assertNull(new LocalMneme().remove("Test Key 1"));
  }

  @Test
  public void remove_string_withPreviousValue_shouldReturnPreviousValue() {

    final Mneme mneme = new LocalMneme();

    mneme.put("Test Key 1", "Test Value 1");
    mneme.put("Test Key 2", "Test Value 2");

    Assertions.assertEquals("Test Value 1", mneme.remove("Test Key 1"));
  }

  @Test
  public void remove_object_nullKey_shouldThrowThemisNullTarget() {

    final Object key = null;

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> new LocalMneme().remove(key));
  }

  @Test
  public void remove_object_nonStringKey_shouldThrowClassCast() {

    final Object key = new Object();

    Assertions.assertThrows(
        ClassCastException.class,
        () -> new LocalMneme().remove(key));
  }

  @Test
  public void remove_object_happyPath_shouldReturnPreviousValue() {

    final Mneme mneme = new LocalMneme();

    mneme.put("Test Key 1", "Test Value 1");
    mneme.put("Test Key 2", "Test Value 2");

    final Object key = "Test Key 1";

    Assertions.assertEquals("Test Value 1", mneme.remove(key));
  }

  @Test
  public void putAll_nullMap_shouldThrowThemisNullTarget() {

    Assertions.assertThrows(
        ThemisNullTargetException.class,
        () -> new LocalMneme().putAll(null));
  }

  @Test
  public void putAll_happyPath_shouldPutAll() {

    final Map<String, String> map = new HashMap<>();
    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    final Mneme mneme = new LocalMneme();
    mneme.putAll(map);

    Assertions.assertEquals("Test Value 1", mneme.get("Test Key 1"));
    Assertions.assertEquals("Test Value 2", mneme.get("Test Key 2"));
  }

  @Test
  public void clear_shouldRemoveAll() {

    final Mneme mneme = new LocalMneme();

    mneme.put("Test Key 1", "Test Value 1");
    mneme.put("Test Key 2", "Test Value 2");

    mneme.clear();

    Assertions.assertTrue(mneme.isEmpty());
  }

  @Test
  public void keySet_shouldReturnAllKeys() {

    final Map<String, String> map = new HashMap<>();
    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    final Mneme mneme = new LocalMneme();
    mneme.putAll(map);

    Assertions.assertTrue(mneme.keySet().containsAll(map.keySet()));
  }

  @Test
  public void values_shouldReturnAllValues() {

    final Map<String, String> map = new HashMap<>();
    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    final Mneme mneme = new LocalMneme();
    mneme.putAll(map);

    Assertions.assertTrue(mneme.values().containsAll(map.values()));
  }

  @Test
  public void entrySet_shouldReturnAllEntries() {

    final Map<String, String> map = new HashMap<>();
    map.put("Test Key 1", "Test Value 1");
    map.put("Test Key 2", "Test Value 2");

    final Mneme mneme = new LocalMneme();
    mneme.putAll(map);

    Assertions.assertEquals(map.size(), mneme.entrySet().size());
  }

}
