package pompei.maths.syms_diff.visitors;

import pompei.maths.syms_diff.visitable.Power;

public class CannotKillDiffVisitorForPower extends RuntimeException {
  
  public final Power power;
  
  public CannotKillDiffVisitorForPower(Power power) {
    this.power = power;
  }
}
