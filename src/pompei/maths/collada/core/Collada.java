package pompei.maths.collada.core;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Collada {

  private final Map<String, Geometry> geometryMap = new HashMap<>();

  public Geometry upsertGeometry(String id) {
    return upsertGeometry(id, id);
  }

  public Geometry upsertGeometry(String id, String name) {
    {
      Geometry ret = geometryMap.get(id);
      if (ret != null) {
        return ret;
      }
    }
    {
      Geometry geometry = new Geometry(id, name);
      geometryMap.put(id, geometry);
      return geometry;
    }
  }

  private final Map<String, Material> materialMap = new HashMap<>();

  public Material upsertMaterial(String id) {
    {
      Material material = materialMap.get(id);
      if (material != null) {
        return material;
      }
    }
    {
      Material material = new Material(id);
      materialMap.put(material.id, material);
      return material;
    }
  }

  public void printTo(PrintStream out) {
    out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
    out.println("<COLLADA xmlns=\"http://www.collada.org/2005/11/COLLADASchema\" version=\"1.4.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");

    printMaterials(out);

    out.println("<library_geometries>");
    geometryMap.entrySet().stream()
      .sorted(Comparator.comparing(Map.Entry::getKey))
      .map(Map.Entry::getValue)
      .forEachOrdered(g -> g.printTo(out));
    out.println("</library_geometries>");

    printScene(out);

    out.println("</COLLADA>");
  }

  private void printMaterials(PrintStream out) {
    if (materialMap.isEmpty()) {
      return;
    }

    {
      out.println("<library_effects>");
      materialMap.entrySet().stream()
        .sorted(Comparator.comparing(Map.Entry::getKey))
        .map(Map.Entry::getValue)
        .forEachOrdered(m -> m.printEffect(out));
      out.println("</library_effects>");
    }
    {
      out.println("<library_materials>");
      materialMap.entrySet().stream()
        .sorted(Comparator.comparing(Map.Entry::getKey))
        .map(Map.Entry::getValue)
        .forEachOrdered(m -> m.printMaterial(out));
      out.println("</library_materials>");
    }
  }

  private final Map<String, Node> nodeMap = new HashMap<>();

  @SuppressWarnings("UnusedReturnValue")
  public Node upsertNode(String nodeId, Geometry geometry) {
    if (!geometryMap.containsKey(geometry.id)) {
      throw new RuntimeException("No geometry.id = " + geometry.id);
    }

    return ensureNode(nodeId).setGeometry(geometry);
  }

  private Node ensureNode(String nodeId) {
    {
      Node node = nodeMap.get(nodeId);
      if (node != null) {
        return node;
      }
    }

    {
      Node node = new Node(nodeId, nodeId);
      nodeMap.put(node.id, node);
      return node;
    }
  }

  private void printScene(PrintStream out) {
    out.println("<library_visual_scenes>");
    out.println("<visual_scene id=\"Scene\" name=\"Scene\">");

    nodeMap.entrySet().stream()
      .sorted(Comparator.comparing(Map.Entry::getKey))
      .map(Map.Entry::getValue)
      .forEachOrdered(node -> node.printTo(out))
    ;

    out.println("</visual_scene>");
    out.println("</library_visual_scenes>");
    out.println("<scene>");
    out.println("<instance_visual_scene url=\"#Scene\"/>");
    out.println("</scene>");
  }
}
