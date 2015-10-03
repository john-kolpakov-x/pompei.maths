package pompei.maths.syms_diff.model;

public interface Const extends Form {
  String asStr();
  
  int sign();
  
  double doubleValue();
  
  Const minis();
  
  boolean isOne();
}
