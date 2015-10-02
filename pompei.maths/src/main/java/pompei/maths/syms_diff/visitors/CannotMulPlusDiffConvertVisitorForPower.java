package pompei.maths.syms_diff.visitors;

import pompei.maths.syms_diff.visitable.Power;

public class CannotMulPlusDiffConvertVisitorForPower extends RuntimeException {
  
  public final Power power;
  
  public CannotMulPlusDiffConvertVisitorForPower(Power power) {
    this.power = power;
  }
}
