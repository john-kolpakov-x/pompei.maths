package pompei.maths.syms_diff.model;

public interface Form {
  <T> T visit(FormVisitor<T> v);
}
