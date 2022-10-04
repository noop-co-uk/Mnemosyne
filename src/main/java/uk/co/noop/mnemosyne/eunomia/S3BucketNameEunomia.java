package uk.co.noop.mnemosyne.eunomia;

import com.amazonaws.services.s3.AmazonS3;
import uk.co.noop.themis.eunomia.AbstractEunomia;
import uk.co.noop.themis.eunomia.StringEunomia;
import uk.co.noop.themis.exception.ThemisBlankTargetStringException;

import static uk.co.noop.themis.Themis.themis;
import static uk.co.noop.themis.Themis.validate;

/**
 * <p>A Eunomia instance for validating against various invalid <b>target</b>
 * <i>S3</i> bucket name <code>String</code> scenarios.</p>
 */
public class S3BucketNameEunomia
    extends AbstractEunomia<String, S3BucketNameEunomia> {

  private final StringEunomia parent;

  /**
   * <p>Creates a new instance of <code>S3BucketNameEunomia</code> using the
   * specified <b>targetName</b> and <b>target</b>.</p>
   *
   * @param targetName The <b>target</b> name; this should not be
   *                   <code>null</code>, empty or contain only whitespace
   *                   characters but this is not validated.
   * @param target The target.
   */
  public S3BucketNameEunomia(final String targetName, final String target) {
    super(targetName, target);
    parent = themis(targetName, target);
  }

  /**
   * {@inheritDoc}
   *
   * @return <code>this</code> instance of <code>S3BucketNameEunomia</code>.
   */
  @Override
  protected S3BucketNameEunomia getEunomia() { return this; }

  /**
   * <p>Redirects to {@link StringEunomia#againstNullStrings()}.</p>
   *
   * @return <code>this</code> instance of <code>S3BucketNameEunomia</code> to
   * further validate the <b>target</b> <i>S3</i> bucket name
   * <code>String</code>. This will never be <code>null</code>.
   *
   * @see StringEunomia#againstNullStrings()
   */
  public S3BucketNameEunomia againstNullS3BucketNames() {
    parent.againstNullStrings();
    return this;
  }

  /**
   * <p>Redirects to {@link StringEunomia#againstEmptyStrings()}.</p>
   *
   * @return <code>this</code> instance of <code>S3BucketNameEunomia</code> to
   * further validate the <b>target</b> <i>S3</i> bucket name
   * <code>String</code>. This will never be <code>null</code>.
   *
   * @see StringEunomia#againstEmptyStrings()
   */
  public S3BucketNameEunomia againstEmptyS3BucketNames() {
    parent.againstEmptyStrings();
    return this;
  }

  /**
   * <p>Redirects to {@link StringEunomia#againstBlankStrings()}.</p>
   *
   * @return <code>this</code> instance of <code>S3BucketNameEunomia</code> to
   * further validate the <b>target</b> <i>S3</i> bucket name
   * <code>String</code>. This will never be <code>null</code>.
   *
   * @see StringEunomia#againstBlankStrings()
   */
  public S3BucketNameEunomia againstBlankS3BucketNames() {
    parent.againstBlankStrings();
    return this;
  }

  /**
   * <p>Validates against non-existent <b>target</b> values.</p>
   *
   * <p>This will result in a {@link
   * uk.co.noop.themis.exception.ThemisInvalidTargetException
   * ThemisInvalidTargetException} if the <b>target</b> <i>S3</i> bucket name
   * <code>String</code> is non-existent.</p>
   *
   * @param s3 An instance of {@link AmazonS3}.
   *
   * @return <code>this</code> instance of <code>S3BucketNameEunomia</code> to
   * further validate the <b>target</b> <i>S3</i> bucket name
   * <code>String</code>. This will never be <code>null</code>.
   *
   * @throws uk.co.noop.themis.exception.ThemisTargetException Extensions of
   * <code>ThemisTargetException</code> will be thrown for any invalid parameter
   * scenarios.
   *
   * @see AmazonS3
   * @see uk.co.noop.themis.exception.ThemisInvalidTargetException
   * @see uk.co.noop.themis.exception.ThemisTargetException
   */
  public S3BucketNameEunomia againstNonExistentS3BucketNames(
      final AmazonS3 s3) {

    validate("s3", s3).againstNullObjects();

    return againstBlankS3BucketNames()
        .againstInvalidValues(target -> !s3.doesBucketExistV2(target));
  }

}
