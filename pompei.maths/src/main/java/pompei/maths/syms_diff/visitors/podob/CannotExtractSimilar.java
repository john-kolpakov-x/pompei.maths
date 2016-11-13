package pompei.maths.syms_diff.visitors.podob;

public class CannotExtractSimilar extends RuntimeException {
  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
