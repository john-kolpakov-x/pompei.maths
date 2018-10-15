package pompei.maths.collada.core;

import java.io.PrintStream;

public class Node {
  public final String id;
  public String name;
  public Geometry geometry;
  public Material material;

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
    out.println("<matrix sid=\"transform\">" + transform.spacedCoord() + "</matrix>");

    if (material == null) {
      out.println("<instance_geometry url=\"#" + geometry.id + "\" name=\"" + geometry.name + "\"/>");
    } else {
      out.println("<instance_geometry url=\"#" + geometry.id + "\" name=\"" + geometry.name + "\">");
      out.println("<bind_material>\n" +
        "            <technique_common>\n" +
        "              <instance_material symbol=\"" + material.materialId() + "\" target=\"#" + material.materialId() + "\"/>\n" +
        "            </technique_common>\n" +
        "          </bind_material>");
      out.println("</instance_geometry>");
    }

    out.println("</node>");
  }
}
