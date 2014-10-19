package pompei.maths.syms.visitors;

public interface DivConf {
  int paddingUp(int level);
  
  int paddingDown(int level);
  
  int paddingLeft(int level);
  
  int paddingRight(int level);
}
