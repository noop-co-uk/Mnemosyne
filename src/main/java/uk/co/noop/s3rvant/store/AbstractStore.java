package uk.co.noop.s3rvant.store;

import uk.co.noop.guardian.Guardian;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractStore implements Store {

  protected AbstractStore() {
    super();
  }

  /**
   * {@inheritDoc}
   *
   * @return the number of key-value mappings in this map
   */
  @Override
  public int size() {
    return keySet().size();
  }

  /**
   * {@inheritDoc}
   *
   * @return <code>true</code> if this map contains no key-value mappings
   */
  @Override
  public boolean isEmpty() {
    return keySet().isEmpty();
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * Where possible {@link Store#containsKey(String)} should be used instead to
   * avoid any potential <code>ClassCastException</code> scenarios.
   * </p>
   * <p>
   * Note:
   * </p>
   * <p>
   * The <code>key</code> parameter is guarded against <code>null</code> values
   * using {@link Guardian} (See: {@link
   * uk.co.noop.guardian.sidekick.ObjectSidekick#againstNullObjects()
   * ObjectSidekick#againstNullObjects()} for more information.).
   * </p>
   *
   * @param key key whose presence in this map is to be tested
   *
   * @return <code>true</code> if this map contains a mapping for the specified
   * <code>key</code>
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   * @throws ClassCastException if the key is of an inappropriate type for this
   * map
   *
   * @see Store#containsKey(String)
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.ObjectSidekick#againstNullObjects()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  @Override
  public boolean containsKey(final Object key) {

    Guardian.guard("key", key).againstNullObjects();

    if (key instanceof String) {
      return containsKey((String) key);
    }

    throw new ClassCastException();
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * Where possible {@link Store#containsValue(String)} should be used instead
   * to avoid any potential <code>ClassCastException</code> scenarios.
   * </p>
   * <p>
   * Note:
   * </p>
   * <p>
   * The <code>value</code> parameter is guarded against <code>null</code>
   * values using {@link Guardian} (See: {@link
   * uk.co.noop.guardian.sidekick.ObjectSidekick#againstNullObjects()
   * ObjectSidekick#againstNullObjects()} for more information.).
   * </p>
   *
   * @param value value whose presence in this map is to be tested
   *
   * @return <code>true</code> if this map maps one or more keys to the
   * specified <code>value</code>
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   * @throws ClassCastException if the value is of an inappropriate type for
   * this map
   *
   * @see Store#containsValue(String)
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.ObjectSidekick#againstNullObjects()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  @Override
  public boolean containsValue(final Object value) {

    Guardian.guard("value", value).againstNullObjects();

    if (value instanceof String) {
      return containsValue((String) value);
    }

    throw new ClassCastException();
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * Where possible {@link Store#get(String)} should be used instead to avoid
   * any potential <code>ClassCastException</code> scenarios.
   * </p>
   * <p>
   * Note:
   * </p>
   * <p>
   * The <code>key</code> parameter is guarded against <code>null</code> values
   * using {@link Guardian} (See: {@link
   * uk.co.noop.guardian.sidekick.ObjectSidekick#againstNullObjects()
   * ObjectSidekick#againstNullObjects()} for more information.).
   * </p>
   *
   * @param key the key whose associated value is to be returned
   *
   * @return the value to which the specified <code>key</code> is mapped, or
   * <code>null</code> if this map contains no mapping for the key
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   * @throws ClassCastException if the key is of an inappropriate type for this
   * map
   *
   * @see Store#get(String)
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.ObjectSidekick#againstNullObjects()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  @Override
  public String get(final Object key) {

    Guardian.guard("key", key).againstNullObjects();

    if (key instanceof String) {
      return get((String) key);
    }

    throw new ClassCastException();
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * Where possible {@link Store#remove(String)} should be used instead to avoid
   * any potential <code>ClassCastException</code> scenarios.
   * </p>
   * <p>
   * Note:
   * </p>
   * <p>
   * The <code>key</code> parameter is guarded against <code>null</code> values
   * using {@link Guardian} (See: {@link
   * uk.co.noop.guardian.sidekick.ObjectSidekick#againstNullObjects()
   * ObjectSidekick#againstNullObjects()} for more information.).
   * </p>
   *
   * @param key key whose mapping is to be removed from the map
   *
   * @return the previous value associated with <code>key</code>, or
   * <code>null</code> if there was no mapping for <code>key</code>.
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   * @throws ClassCastException if the key is of an inappropriate type for this
   * map
   *
   * @see Store#remove(String)
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.ObjectSidekick#againstNullObjects()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  @Override
  public String remove(final Object key) {

    Guardian.guard("key", key).againstNullObjects();

    if (key instanceof String) {
      return remove((String) key);
    }

    throw new ClassCastException();
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * See: {@link Store#put(String, String)} for more information.
   * </p>
   * <p>
   * Note:
   * </p>
   * <p>
   * The <code>map</code> parameter is guarded against <code>null</code> values
   * using {@link Guardian} (See: {@link
   * uk.co.noop.guardian.sidekick.ObjectSidekick#againstNullObjects()
   * ObjectSidekick#againstNullObjects()} for more information.).
   * </p>
   *
   * @param map mappings to be stored in this map
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   *
   * @see Store#put(String, String)
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.ObjectSidekick#againstNullObjects()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  @Override
  public void putAll(final Map<? extends String, ? extends String> map) {

    Guardian.guard("map", map).againstNullObjects();

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
   * @return a set view of the keys contained in this map
   */
  @Override
  public abstract Set<String> keySet();

  /**
   * {@inheritDoc}
   *
   * @return a collection view of the values contained in this map
   */
  @Override
  public Collection<String> values() {

    return keySet()
        .stream()
        .map(this::get)
        .collect(Collectors.toSet());
  }

  /**
   * {@inheritDoc}
   *
   * @return a set view of the mappings contained in this map
   */
  @Override
  public Set<Entry<String, String>> entrySet() {

    return keySet()
        .stream()
        .map(StoreEntry::new)
        .collect(Collectors.toSet());
  }

  private class StoreEntry implements Entry<String, String> {

    private final String key;

    private StoreEntry(final String key) {

      super();

      Guardian.guard("key", key).againstBlankStrings();

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
     * {@inheritDoc}
     * 
     * <p>
     * See: {@link Store#get(String)} for more information.
     * </p>
     *
     * @return the value corresponding to this entry
     * 
     * @see Store#get(String)
     */
    @Override
    public String getValue() {
      return get(key);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * See: {@link Store#put(String, String)} for more information.
     * </p>
     *
     * @param value new value to be stored in this entry
     * @return old value corresponding to the entry
     */
    @Override
    public String setValue(final String value) {
      return put(key, value);
    }

  }

}
