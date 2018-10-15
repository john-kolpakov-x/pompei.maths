package pompei.maths.collada.core;

import pompei.maths.utils.Conv;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Geometry {
  public final String id;
  public String name;
  public Material material = null;

  Geometry(String id, String name) {
    this.id = id;
    this.name = name;
  }

  private final List<VecXYZ> positionList = new ArrayList<>();
  private final List<VecXYZ> normalList = new ArrayList<>();
  private final List<Triangle> triangleList = new ArrayList<>();

  public Geometry addPosition(double x, double y, double z) {
    positionList.add(new VecXYZ(x, y, z));
    return this;
  }

  @SuppressWarnings("UnusedReturnValue")
  public Geometry addPosition(VecXYZ position) {
    return addPosition(position.x, position.y, position.z);
  }

  public Geometry addNormal(double x, double y, double z) {
    normalList.add(new VecXYZ(x, y, z));
    return this;
  }

  @SuppressWarnings("UnusedReturnValue")
  public Geometry addNormal(VecXYZ normal) {
    return addNormal(normal.x, normal.y, normal.z);
  }

  @SuppressWarnings("UnusedReturnValue")
  public Geometry addTriangle(int p1, int p2, int p3, int n1, int n2, int n3) {
    triangleList.add(new Triangle(p1, n1, p2, n2, p3, n3));
    return this;
  }

  public void printTo(PrintStream out) {
    out.println("<geometry id=\"" + id + "\" name=\"" + name + "\">");
    out.println("<mesh>");
    {
      printSource(out, getPositionsId(), getPositionsArrayId(), positionList);
      printSource(out, getNormalsId(), getNormalsArrayId(), normalList);

      printVertices(out);

      printTriangles(out);
    }
    out.println("</mesh>");
    out.println("</geometry>");
  }

  private void printTriangles(PrintStream out) {

    String data = triangleList.stream()
      .flatMapToDouble(t -> DoubleStream.of(t.p1, t.n1, t.p2, t.n2, t.p3, t.n3))
      .mapToObj(Conv::doubleToStr)
      .collect(Collectors.joining(" "));

    out.println("<triangles " + materialAttribute() + "count=\"" + triangleList.size() + "\">");
    out.println("<input semantic=\"VERTEX\" source=\"#" + getVerticesId() + "\" offset=\"0\"/>");
    out.println("<input semantic=\"NORMAL\" source=\"#" + getNormalsId() + "\" offset=\"1\"/>");
    out.println("<p>" + data + "</p>");
    out.println("</triangles>");
  }

  private String materialAttribute() {
    if (material == null) {
      return "";
    }
    return "material=\"" + material.materialId() + "\" ";
  }

  private void printVertices(PrintStream out) {
    out.println("<vertices id=\"" + getVerticesId() + "\">");
    out.println("<input semantic=\"POSITION\" source=\"#" + getPositionsId() + "\"/>");
    out.println("</vertices>");
  }

  private void printSource(PrintStream out, String meshId, String arrayId, List<VecXYZ> vector) {
    String spacedVector = vector.stream()
      .flatMapToDouble(p -> DoubleStream.of(p.x, p.y, p.z))
      .mapToObj(Conv::doubleToStr)
      .collect(Collectors.joining(" "));

    out.println("<source id=\"" + meshId + "\">");
    out.println("<float_array id=\"" + arrayId
      + "\" count=\"" + (vector.size() * 3) + "\">" + spacedVector + "</float_array>");
    out.println("<technique_common>");
    out.println("<accessor source=\"#" + arrayId + "\" count=\"" + vector.size() + "\" stride=\"3\">");
    out.println("<param name=\"X\" type=\"double\"/>");
    out.println("<param name=\"Y\" type=\"double\"/>");
    out.println("<param name=\"Z\" type=\"double\"/>");
    out.println("</accessor>");
    out.println("</technique_common>");
    out.println("</source>");
  }

  private String getVerticesId() {
    return id + "-vertices";
  }

  private String getPositionsId() {
    return id + "-positions";
  }

  private String getPositionsArrayId() {
    return id + "-positions-array";
  }

  private String getNormalsId() {
    return id + "-normals";
  }

  private String getNormalsArrayId() {
    return id + "-normals-array";
  }
}
