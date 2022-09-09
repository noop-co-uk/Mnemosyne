package uk.co.noop.s3rvant.store;

import uk.co.noop.guardian.Guardian;

import java.util.Map;
import java.util.Set;

public interface Store extends Map<String, String> {

  /**
   * <p>
   * Returns <code>true</code> if this <code>Store</code> contains a mapping for
   * the specified <code>key</code>. More formally, returns <code>true</code> if
   * and only if this <code>Store</code> </code>contains a mapping for a key
   * <code>k</code> such that <code>(key==null ? k==null :
   * key.equals(k))</code>. (There can be at most one such mapping.)
   * </p>
   * <p>
   * Note:
   * </p>
   * <p>
   * The <code>key</code> parameter is guarded against blank values using
   * {@link Guardian} (See: {@link
   * uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * StringSidekick#againstBlankStrings()} for more information.).
   * </p>
   *
   * @param key key whose presence in this <code>Store</code> is to be tested
   *
   * @return <code>true</code> if this <code>Store</code> contains a mapping for
   * the specified <code>key</code>
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   *
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  boolean containsKey(String key);

  /**
   * <p>
   * Returns <code>true</code> if this <code>Store</code> maps one or more keys
   * to the specified <code>value</code>. More formally, returns
   * <code>true</code> if and only if this <code>Store</code> contains at least
   * one mapping to a value <code>v</code> such that <code>(value==null ?
   * v==null : value.equals(v))</code>. This operation will probably require
   * time linear in the map size for most implementations of the
   * <code>Store</code> interface.
   * </p>
   * <p>
   * Note:
   * </p>
   * <p>
   * The <code>value</code> parameter is guarded against blank values using
   * {@link Guardian} (See: {@link
   * uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * StringSidekick#againstBlankStrings()} for more information.).
   * </p>
   *
   * @param value value whose presence in this <code>Store</code> is to be
   *              tested
   *
   * @return <code>true</code> if this <code>Store</code> maps one or more keys
   * to the specified <code>value</code>
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   *
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  boolean containsValue(String value);

  /**
   * <p>
   * Returns the value to which the specified <code>key</code> is mapped, or
   * <code>null</code> if this <code>Store</code> contains no mapping for the
   * key.
   * </p>
   * <p>
   * More formally, if this <code>Store</code> contains a mapping from a key
   * <code>k</code> to a value <code>v</code> such that <code>(key==null ?
   * k==null : key.equals(k))</code>, then this method returns <code>v</code>;
   * otherwise it returns <code>null</code>. (There can be at most one such
   * mapping.)
   * </p>
   * <p>
   * If this <code>Store</code> permits <code>null</code> values, then a return
   * value of <code>null</code> does not necessarily indicate that the
   * <code>Store</code> contains no mapping for the key; it's also possible that
   * the <code>Store</code> explicitly maps the key to <code>null</code>. The
   * {@link Store#containsKey(String)} operation may be used to distinguish these two
   * cases.
   * </p>
   * <p>
   * Note:
   * </p>
   * <p>
   * The <code>key</code> parameter is guarded against blank values using
   * {@link Guardian} (See: {@link
   * uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * StringSidekick#againstBlankStrings()} for more information.).
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
   *
   * @see Store#containsKey(String)
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  String get(String key);

  /**
   * <p>
   * Associates the specified <code>value</code> with the specified
   * <code>key</code> in this <code>Store</code> (optional operation). If the
   * <code>Store</code> previously contained a mapping for the key, the old
   * value is replaced by the specified <code>value</code>. (A
   * <code>Store</code> <code>s</code> is said to contain a mapping for a key
   * <code>k</code> if and only if <code>s.containsKey(k)</code> would return
   * <code>true</code>.)
   * </p>
   * <p>
   * Note:
   * </p>
   * <p>
   * The <code>key</code> and <code>value</code> parameters are guarded against
   * blank values using {@link Guardian} (See: {@link
   * uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * StringSidekick#againstBlankStrings()} for more information.).
   * </p>
   *
   * @param key key with which the specified <code>value</code> is to be associated
   * @param value value to be associated with the specified <code>key</code>
   *
   * @return the previous value associated with key, or <code>null</code> if
   * there was no mapping for key. (A <code>null</code> return can also indicate
   * that the <code>Store</code> previously associated <code>null</code> with
   * key, if the implementation supports <code>null</code> values.)
   *
   * @throws uk.co.noop.guardian.exception.GuardianTargetException Extensions of
   * {@link uk.co.noop.guardian.exception.GuardianTargetException
   * GuardianTargetException} will be thrown for any invalid parameter
   * scenarios.
   *
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  String put(String key, String value);

  /**
   * <p>
   * Removes the mapping for a <code>key</code> from this <code>Store</code> if
   * it is present (optional operation). More formally, if this
   * <code>Store</code> contains a mapping from key <code>k</code> to value
   * <code>v</code> such that <code>(key==null ? k==null :
   * key.equals(k))</code>, that mapping is removed. (The <code>Store</code> can
   * contain at most one such mapping.)
   * </p>
   * <p>
   * Returns the value to which this <code>Store</code> previously associated
   * the key, or <code>null</code> if the <code>Store</code> contained no
   * mapping for the key.
   * </p>
   * <p>
   * If this <code>Store</code> permits <code>null</code> values, then a return
   * value of <code>null</code> does not necessarily indicate that the
   * <code>Store</code> contained no mapping for the key; it's also possible
   * that the <code>Store</code> explicitly mapped the key to <code>null</code>.
   * </p>
   * The <code>Store</code> will not contain a mapping for the specified
   * <code>key</code> once the call returns.
   * </p>
   * <p>
   * Note:
   * </p>
   * <p>
   * The <code>key</code> parameter is guarded against blank values using
   * {@link Guardian} (See: {@link
   * uk.co.noop.guardian.sidekick.StringSidekick#againstBlankStrings()
   * StringSidekick#againstBlankStrings()} for more information.).
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
   *
   * @see Guardian
   * @see uk.co.noop.guardian.sidekick.ObjectSidekick#againstNullObjects()
   * @see uk.co.noop.guardian.exception.GuardianTargetException
   */
  String remove(String key);

  /**
   * <p>
   * Returns a <code>Set</code> view of the keys contained in this
   * <code>Store</code>. The set is backed by the <code>Store</code>, so changes
   * to the <code>Store</code> are reflected in the set, and vice-versa. If the
   * <code>Store</code> is modified while an iteration over the set is in
   * progress (except through the iterator's own remove operation), the results
   * of the iteration are undefined. The set supports element removal, which
   * removes the corresponding mapping from the map, via the
   * <code>Iterator.remove</code>, <code>Set.remove</code>,
   * <code>removeAll</code>, <code>retainAll</code>, and <code>clear</code>
   * operations. It does not support the <code>add</code> or <code>addAll</code>
   * operations.
   * </p>
   *
   * @return a set view of the keys contained in this <code>Store</code>
   */
  Set<String> keySet();

}
