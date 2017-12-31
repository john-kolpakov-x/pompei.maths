package pompei.maths.difur;

public interface ModelAdapter {
  void prepare(DiffUr diffUr);

  @SuppressWarnings("unused")
  void readFromModel();

  @SuppressWarnings("unused")
  void writeToModel();
}
