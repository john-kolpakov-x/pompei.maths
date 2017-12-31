package pompei.maths.end_element;

public interface DrawDestination {
  void vector(double x1, double y1, double x2, double y2);

  void line(double x1, double y1, double x2, double y2);

  void setSideType(SideType sideType);

  void labelPoint(double x, double y, String label, LabelPlace place);
}
