package uk.co.noop.mnemosyne.eunomia;

import com.amazonaws.services.s3.AmazonS3;
import uk.co.noop.themis.Themis;
import uk.co.noop.themis.eunomia.AbstractEunomia;
import uk.co.noop.themis.eunomia.StringEunomia;

public class S3BucketNameEunomia
    extends AbstractEunomia<String, S3BucketNameEunomia> {

  private final StringEunomia parent;

  public S3BucketNameEunomia(final String targetName, final String target) {
    super(targetName, target);
    parent = Themis.validate(targetName, target);
  }

  @Override
  protected S3BucketNameEunomia getEunomia() { return this; }

  public S3BucketNameEunomia againstNullS3BucketNames() {
    parent.againstNullStrings();
    return this;
  }

  public S3BucketNameEunomia againstEmptyS3BucketNames() {
    parent.againstEmptyStrings();
    return this;
  }

  public S3BucketNameEunomia againstBlankS3BucketNames() {
    parent.againstBlankStrings();
    return this;
  }

  public S3BucketNameEunomia againstNonExistentS3BucketNames(
      final AmazonS3 s3) {

    Themis.validate("s3", s3).againstNullObjects();

    return againstBlankS3BucketNames()
        .againstInvalidValues(target -> !s3.doesBucketExistV2(target));
  }

}
