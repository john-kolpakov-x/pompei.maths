package pompei.maths.collada.core;

import java.io.PrintStream;

public class Material {
  public final String id;

  public Material(String id) {
    this.id = id;
  }

  public String effectId() {
    return id + "-effect";
  }

  public String materialId() {
    return id + "-material";
  }

  public void printEffect(PrintStream out) {
    out.println("<effect id=\"" + effectId() + "\">");
    out.println("<profile_COMMON>\n" +
      "        <technique sid=\"common\">\n" +
      "          <phong>\n" +
      "            <emission>\n" +
      "              <color sid=\"emission\">0 0 0 1</color>\n" +
      "            </emission>\n" +
      "            <ambient>\n" +
      "              <color sid=\"ambient\">0 0 0 1</color>\n" +
      "            </ambient>\n" +
      "            <diffuse>\n" +
      "              <color sid=\"diffuse\">0.64 0.64 0.64 1</color>\n" +
      "            </diffuse>\n" +
      "            <specular>\n" +
      "              <color sid=\"specular\">0.5 0.5 0.5 1</color>\n" +
      "            </specular>\n" +
      "            <shininess>\n" +
      "              <float sid=\"shininess\">50</float>\n" +
      "            </shininess>\n" +
      "            <index_of_refraction>\n" +
      "              <float sid=\"index_of_refraction\">1</float>\n" +
      "            </index_of_refraction>\n" +
      "          </phong>\n" +
      "        </technique>\n" +
      "      </profile_COMMON>");
    out.println("</effect>");
  }

  public void printMaterial(PrintStream out) {
    out.println("<material id=\"" + materialId() + "\" name=\"" + id + "\">");
    out.println("<instance_effect url=\"#" + effectId() + "\"/>");
    out.println("</material>");
  }
}
