package pompei.maths.multi_tree;

public class Node {
  public final Object[] elements;
  public final Node[] references;
  public int count = 0;
  public final Node parent;

  public Node(int nodeSize, Node parent) {
    elements = new KeyValue[nodeSize];
    references = new Node[nodeSize + 1];
    this.parent = parent;
  }
}
