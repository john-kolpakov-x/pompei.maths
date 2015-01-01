package pompei.maths.difur;

public interface ModelAdapter {
  void prepare(DiffUr diffUr);
  
  void readFromModel();
  
  void writeToModel();
}
