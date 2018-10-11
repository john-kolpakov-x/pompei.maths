package pompei.maths.collada.core;

import java.io.PrintStream;

public class Node {
  public final String id;
  public String name;
  public Geometry geometry;

  public final Matrix4 transform = new Matrix4();

  public Node(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public Node setGeometry(Geometry geometry) {
    this.geometry = geometry;
    return this;
  }

  public void printTo(PrintStream out) {
    out.println("<node id=\"" + id + "\" name=\"" + name + "\" type=\"NODE\">");
    out.println("<matrix sid=\"transform\">" + transform.spacedCoord() +      "</matrix>");
    out.println("<instance_geometry url=\"#" + geometry.id + "\" name=\"" + geometry.name + "\"/>");
    out.println("</node>");
  }
}
