package uk.co.noop.mnemosyne.mneme;

import java.util.Map;
import java.util.Set;

public interface Mneme extends Map<String, String>, Bucket {

  /**
   * <p>Returns <code>true</code> if this <code>Mneme</code> contains a mapping
   * for the specified <b>key</b>.</p>
   *
   * <p>More formally, returns <code>true</code> if and only if this
   * <code>Mneme</code> contains a mapping for a <b>key</b> <code>k</code> such
   * that;</p>
   *
   * <p><code>
   * (key == null ? k == null : key.equals(k))
   * </code></p>
   *
   * <p>(There can be at most one such mapping.)</p>
   *
   * <p>Note:</p>
   *
   * <p>The <b>key</b> parameter is validated against blank values using {@link
   * uk.co.noop.themis.Themis Themis}. (See: {@link
   * uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * StringEunomia#againstBlankStrings()} for more information.)
   * </p>
   *
   * @param key key whose presence in this <code>Mneme</code> is to be tested
   *
   * @return <code>true</code> if this <code>Mneme</code> contains a mapping for
   * the specified <b>key</b>
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see uk.co.noop.themis.Themis
   * @see uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  boolean containsKey(String key);

  /**
   * <p>Returns <code>true</code> if this <code>Mneme</code> maps one or more
   * keys to the specified <b>value</b>.</p>
   *
   * <p>More formally, returns <code>true</code> if and only if this
   * <code>Mneme</code> contains at least one mapping to a <b>value</b>
   * <code>v</code> such that;</p>
   *
   * <p><code>
   * (value == null ? v == null : value.equals(v))
   * </code></p>
   *
   * <p>This operation will probably require time linear in the
   * <code>Mneme</code> size for most implementations of the <code>Mneme</code>
   * interface.</p>
   *
   * <p>Note:</p>
   *
   * <p>The <b>value</b> parameter is validated against blank values using
   * {@link uk.co.noop.themis.Themis Themis}. (See: {@link
   * uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * StringEunomia#againstBlankStrings()} for more information.)</p>
   *
   * @param value value whose presence in this <code>Mneme</code> is to be
   *              tested
   *
   * @return <code>true</code> if this <code>Mneme</code> maps one or more keys
   * to the specified <b>value</b>
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see uk.co.noop.themis.Themis
   * @see uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  boolean containsValue(String value);

  /**
   * <p>Returns the value to which the specified <b>key</b> is mapped, or
   * <code>null</code> if this <code>Mneme</code> contains no mapping for the
   * <b>key</b>.</p>
   *
   * <p>More formally, if this <code>Mneme</code> contains a mapping from a
   * <b>key</b> <code>k</code> to a value <code>v</code> such that;</p>
   *
   * <p><code>
   * (key == null ? k == null : key.equals(k))
   * </code></p>
   *
   * <p>... then this method returns <code>v</code>; otherwise it returns
   * <code>null</code>. (There can be at most one such mapping.)</p>
   *
   * <p>If this <code>Mneme</code> permits <code>null</code> values, then a
   * return value of <code>null</code> does not necessarily indicate that the
   * <code>Mneme</code> contains no mapping for the <b>key</b>; it's also
   * possible that the <code>Mneme</code> explicitly maps the <b>key</b> to
   * <code>null</code>. The {@link Mneme#containsKey(String)} operation may be
   * used to distinguish these two cases.</p>
   *
   * <p>Note:</p>
   *
   * <p>The <b>key</b> parameter is validated against blank values using {@link
   * uk.co.noop.themis.Themis Themis}. (See: {@link
   * uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * StringEunomia#againstBlankStrings()} for more information.)</p>
   *
   * @param key the key whose associated value is to be returned
   *
   * @return the value to which the specified <b>key</b> is mapped, or
   * <code>null</code> if this <code>Mneme</code> contains no mapping for the
   * <b>key</b>
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see Mneme#containsKey(String)
   * @see uk.co.noop.themis.Themis
   * @see uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  String get(String key);

  /**
   * <p>Associates the specified <b>value</b> with the specified <b>key</b> in
   * this <code>Mneme</code> (optional operation). If the <code>Mneme</code>
   * previously contained a mapping for the <b>key</b>, the old value is
   * replaced by the specified <b>value</b>. (A <code>Mneme</code>
   * <code>m</code> is said to contain a mapping for a <b>key</b> <code>k</code>
   * if and only if <code>m.containsKey(k)</code> would return
   * <code>true</code>.)</p>
   *
   * <p>Note:</p>
   *
   * <p>The <b>key</b> and <b>value</b> parameters are validated against blank
   * values using {@link uk.co.noop.themis.Themis Themis}. (See:
   * {@link uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * StringEunomia#againstBlankStrings()} for more information.)</p>
   *
   * @param key key with which the specified <b>value</b> is to be associated
   * @param value value to be associated with the specified <b>key</b>
   *
   * @return the previous value associated with <b>key</b>, or <code>null</code>
   * if there was no mapping for <b>key</b>. (A <code>null</code> return can
   * also indicate that the <code>Mneme</code> previously associated
   * <code>null</code> with <b>key</b>, if the implementation supports
   * <code>null</code> values.)
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see uk.co.noop.themis.Themis
   * @see uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  String put(String key, String value);

  /**
   * <p>Removes the mapping for a <b>key</b> from this <code>Mneme</code> if
   * it is present (optional operation).</p>
   *
   * <p>More formally, if this <code>Mneme</code> contains a mapping from
   * <b>key</b> <code>k</code> to value <code>v</code> such that;</p>
   *
   * <p><code>
   * (key == null ? k == null : key.equals(k))
   * </code></p>
   *
   * <p>... that mapping is removed. (The <code>Mneme</code> can contain at most
   * one such mapping.)</p>
   *
   * <p>Returns the value to which this <code>Mneme</code> previously associated
   * the <b>key</b>, or <code>null</code> if the <code>Mneme</code> contained no
   * mapping for the <b>key</b>.</p>
   *
   * <p>If this <code>Mneme</code> permits <code>null</code> values, then a
   * return value of <code>null</code> does not necessarily indicate that the
   * <code>Mneme</code> contained no mapping for the <b>key</b>; it's also
   * possible that the <code>Mneme</code> explicitly mapped the <b>key</b> to
   * <code>null</code>.</p>
   *
   * <p>The <code>Mneme</code> will not contain a mapping for the specified
   * <b>key</b> once the call returns.</p>
   *
   * <p>Note:</p>
   *
   * <p>The <b>key</b> parameter is validated against blank values using {@link
   * uk.co.noop.themis.Themis Themis}. (See: {@link
   * uk.co.noop.themis.eunomia.StringEunomia#againstBlankStrings()
   * StringEunomia#againstBlankStrings()} for more information.)</p>
   *
   * @param key key whose mapping is to be removed from the <code>Mneme</code>
   *
   * @return the previous value associated with <b>key</b>, or <code>null</code>
   * if there was no mapping for <b>key</b>.
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * {@link uk.co.noop.themis.exception.ThemisTargetException
   * ThemisTargetException} will be thrown for any invalid parameter scenarios.
   *
   * @see uk.co.noop.themis.Themis
   * @see uk.co.noop.themis.eunomia.ObjectEunomia#againstNullObjects()
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  String remove(String key);

  /**
   * <p>Returns a <code>Set</code> view of the keys contained in this
   * <code>Mneme</code>. The set is <b>not</b> backed by the <code>Mneme</code>,
   * so changes to the <code>Mneme</code> are <b>not</b> reflected in the set,
   * and vice-versa.</p>
   *
   * @return a set view of the keys contained in this <code>Mneme</code>
   */
  Set<String> keySet();

}
