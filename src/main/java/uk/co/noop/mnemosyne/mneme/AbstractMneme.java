package uk.co.noop.mnemosyne.mneme;

import uk.co.noop.themis.Themis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractMneme implements Mneme {

  protected AbstractMneme() {
    super();
  }

  /**
   * {@inheritDoc}
   *
   * @return the number of key-value mappings in this {@link Mneme}
   *
   * @see Mneme
   */
  @Override
  public int size() {
    return keySet().size();
  }

  /**
   * {@inheritDoc}
   *
   * @return <code>true</code> if this {@link Mneme} contains no key-value
   * mappings
   *
   * @see Mneme
   */
  @Override
  public boolean isEmpty() {
    return keySet().isEmpty();
  }

  /**
   * {@inheritDoc}
   *
   * <p>Where possible {@link Mneme#containsKey(String)} should be used instead
   * to avoid any potential <code>ClassCastException</code> scenarios.</p>
   *
   * <p>Note:</p>
   *
   * <p>The <b>key</b> parameter is validated against <code>null</code> values
   * using {@link Themis}. (See: {@link
   * uk.co.noop.themis.eunomia.ObjectEunomia#againstNullObjects()
   * ObjectEunomia#againstNullObjects()} for more information.)</p>
   *
   * @param key key whose presence in this {@link Mneme} is to be tested
   *
   * @return <code>true</code> if this <code>Mneme</code> contains a mapping for
   * the specified <code>key</code>
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   * @throws ClassCastException if the <b>key</b> is of an inappropriate type
   * for this <code>Mneme</code>
   *
   * @see Mneme
   * @see Mneme#containsKey(String)
   * @see Themis
   * @see uk.co.noop.themis.eunomia.ObjectEunomia#againstNullObjects()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  @Override
  public boolean containsKey(final Object key) {

    Themis.validate("key", key).againstNullObjects();

    if (key instanceof String) {
      return containsKey((String) key);
    }

    throw new ClassCastException();
  }

  /**
   * {@inheritDoc}
   *
   * <p>Where possible {@link Mneme#containsValue(String)} should be used
   * instead to avoid any potential <code>ClassCastException</code>
   * scenarios.</p>
   *
   * <p>Note:</p>
   *
   * <p>The <b>value</b> parameter is validated against <code>null</code> values
   * using {@link Themis}. (See: {@link
   * uk.co.noop.themis.eunomia.ObjectEunomia#againstNullObjects()
   * ObjectEunomia#againstNullObjects()} for more information.)</p>
   *
   * @param value value whose presence in this {@link Mneme} is to be tested
   *
   * @return <code>true</code> if this <code>Mneme</code> maps one or more keys
   * to the specified <b>value</b>
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   * @throws ClassCastException if the <b>value</b> is of an inappropriate type
   * for this <code>Mneme</code>
   *
   * @see Mneme
   * @see Mneme#containsValue(String)
   * @see Themis
   * @see uk.co.noop.themis.eunomia.ObjectEunomia#againstNullObjects()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  @Override
  public boolean containsValue(final Object value) {

    Themis.validate("value", value).againstNullObjects();

    if (value instanceof String) {
      return containsValue((String) value);
    }

    throw new ClassCastException();
  }

  /**
   * {@inheritDoc}
   *
   * <p>Where possible {@link Mneme#get(String)} should be used instead to avoid
   * any potential <code>ClassCastException</code> scenarios.</p>
   *
   * <p>Note:</p>
   *
   * <p>The <b>key</b> parameter is validated against <code>null</code> values
   * using {@link Themis}. (See: {@link
   * uk.co.noop.themis.eunomia.ObjectEunomia#againstNullObjects()
   * ObjectEunomia#againstNullObjects()} for more information.)</p>
   *
   * @param key the key whose associated value is to be returned
   *
   * @return the value to which the specified <b>key</b> is mapped, or
   * <code>null</code> if this {@link Mneme} contains no mapping for the
   * <b>key</b>
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   * @throws ClassCastException if the <b>key</b> is of an inappropriate type
   * for this <code>Mneme</code>
   *
   * @see Mneme
   * @see Mneme#get(String)
   * @see Themis
   * @see uk.co.noop.themis.eunomia.ObjectEunomia#againstNullObjects()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  @Override
  public String get(final Object key) {

    Themis.validate("key", key).againstNullObjects();

    if (key instanceof String) {
      return get((String) key);
    }

    throw new ClassCastException();
  }

  /**
   * {@inheritDoc}
   *
   * <p>Where possible {@link Mneme#remove(String)} should be used instead to
   * avoid any potential <code>ClassCastException</code> scenarios.</p>
   *
   * <p>Note:</p>
   *
   * <p>The <b>key</b> parameter is validated against <code>null</code> values
   * using {@link Themis}. (See: {@link
   * uk.co.noop.themis.eunomia.ObjectEunomia#againstNullObjects()
   * ObjectEunomia#againstNullObjects()} for more information.)</p>
   *
   * @param key key whose mapping is to be removed from the {@link Mneme}
   *
   * @return the previous value associated with <b>key</b>, or <code>null</code>
   * if there was no mapping for <b>key</b>.
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   * @throws ClassCastException if the <b>key</b> is of an inappropriate type
   * for this <code>Mneme</code>
   *
   * @see Mneme
   * @see Mneme#remove(String)
   * @see Themis
   * @see uk.co.noop.themis.eunomia.ObjectEunomia#againstNullObjects()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  @Override
  public String remove(final Object key) {

    Themis.validate("key", key).againstNullObjects();

    if (key instanceof String) {
      return remove((String) key);
    }

    throw new ClassCastException();
  }

  /**
   * {@inheritDoc}
   *
   * <p>See: {@link Mneme#put(String, String)} for more information.</p>
   *
   * <p>Note:</p>
   *
   * <p>The <b>map</b> parameter is validated against <code>null</code> values
   * using {@link Themis}. (See: {@link
   * uk.co.noop.themis.eunomia.ObjectEunomia#againstNullObjects()
   * ObjectEunomia#againstNullObjects()} for more information.)</p>
   *
   * @param map mappings to be stored in this {@link Mneme}
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see Mneme
   * @see Mneme#put(String, String)
   * @see Themis
   * @see uk.co.noop.themis.eunomia.ObjectEunomia#againstNullObjects()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  @Override
  public void putAll(final Map<? extends String, ? extends String> map) {

    Themis.validate("map", map).againstNullObjects();

    map.forEach(this::put);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {

    final List<String> keys = new ArrayList<>(keySet());

    keys.forEach(this::remove);
  }

  /**
   * {@inheritDoc}
   *
   * @return a set view of the keys contained in this {@link Mneme}
   *
   * @see Mneme
   */
  @Override
  public abstract Set<String> keySet();

  /**
   * <p>Returns a <code>Collection</code> view of the values contained in this
   * {@link Mneme}. The collection is <b>not</b> backed by the
   * <code>Mneme</code>, so changes to the <code>Mneme</code> are <b>not</b>
   * reflected in the collection, and vice-versa.</p>
   *
   * @return a collection view of the values contained in this
   * <code>Mneme</code>
   *
   * @see Mneme
   */
  @Override
  public Collection<String> values() {

    return keySet()
        .stream()
        .map(this::get)
        .collect(Collectors.toSet());
  }

  /**
   * <p>Returns a <code>Set</code> view of the mappings contained in this
   * {@link Mneme}. The set is <b>not</b> backed by the <code>Mneme</code>, so
   * changes to the <code>Mneme</code> are <b>not</b> reflected in the set, and
   * vice-versa.</p>
   *
   * @return a set view of the mappings contained in this <code>Mneme</code>
   *
   * @see Mneme
   */
  @Override
  public Set<Entry<String, String>> entrySet() {

    return keySet()
        .stream()
        .map(MnemeEntry::new)
        .collect(Collectors.toSet());
  }

  private class MnemeEntry implements Entry<String, String> {

    private final String key;

    private MnemeEntry(final String key) {

      super();

      Themis.validate("key", key).againstBlankStrings();

      this.key = key;
    }

    /**
     * {@inheritDoc}
     *
     * @return the key corresponding to this entry
     */
    @Override
    public String getKey() {
      return key;
    }

    /**
     * <p>Returns the value corresponding to this entry.</p>
     * 
     * <p>See: {@link Mneme#get(String)} for more information.</p>
     *
     * @return the value corresponding to this entry
     * 
     * @see Mneme#get(String)
     */
    @Override
    public String getValue() {
      return get(key);
    }

    /**
     * <p>Replaces the value corresponding to this entry with the specified
     * <b>value</b> (optional operation). (Writes through to the
     * {@link Mneme}.)</p>
     *
     * <p>See: {@link Mneme#put(String, String)} for more information.</p>
     *
     * @param value new value to be stored in this entry
     *
     * @return old value corresponding to the entry
     * 
     * @see Mneme
     * @see Mneme#put(String, String)
     */
    @Override
    public String setValue(final String value) {
      return put(key, value);
    }

  }

}
