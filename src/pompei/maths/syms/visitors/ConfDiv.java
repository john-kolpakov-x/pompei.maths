package pompei.maths.syms.visitors;

public interface ConfDiv {
  int paddingUp(int level);

  int paddingDown(int level);

  int paddingLeft(int level);

  int paddingRight(int level);

  int lineWidth(int level);
}
