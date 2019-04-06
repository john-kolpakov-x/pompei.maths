package pompei.maths.multi_tree;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MultiTreePainter {
  private final PaintContext context;

  public MultiTreePainter(PaintContext context) {
    this.context = context;
  }

  private String elementToStr(Object element) {
    return element == null ? "-" : element.toString();
  }

  private String countText(Node node) {
    return "" + node.count;
  }

  public Point nodeSize(Node node) {

    List<String> strList = new ArrayList<>();

    strList.add(countText(node));

    for (Object element : node.elements) {
      strList.add(elementToStr(element));
    }

    int width = 0;
    Graphics2D g = context.graphics();

    FontMetrics fontMetrics = g.getFontMetrics();

    for (String str : strList) {
      int w = fontMetrics.stringWidth(str);
      if (width < w) {
        width = w;
      }
    }

    Point ret = new Point();

    ret.x = width + context.paddingLeft() + context.paddingRight();

    int linesHeight = fontMetrics.getHeight() * (node.elements.length + 1);
    int spaces = context.elementSpace() * (2 * node.elements.length + 1);
    int refSize = context.referenceRadius() * 2 * node.references.length;

    ret.y = linesHeight + spaces + refSize + context.paddingTop() + context.paddingBottom();

    return ret;
  }

  public Point referenceCenterPosition(@SuppressWarnings("unused") Node node,
                                       int x, int y, int index) {

    Graphics2D g = context.graphics();
    FontMetrics fontMetrics = g.getFontMetrics();
    int lineHeight = fontMetrics.getHeight();

    int top = context.paddingTop() + lineHeight + context.elementSpace();

    int height = context.referenceRadius() * 2 + context.elementSpace() * 2 + lineHeight;

    int X = x + context.paddingLeft() + context.referenceRadius();
    int Y = y + top + index * height + context.referenceRadius();

    return new Point(X, Y);
  }

  public void paintNodeAt(Node node, int x, int y) {

    Graphics2D g = context.graphics();
    g.setColor(Color.BLACK);

    FontMetrics fontMetrics = g.getFontMetrics();

    int referenceRadius = context.referenceRadius();
    int elementSpace = context.elementSpace();

    int lineHeight = fontMetrics.getHeight();

    Point nodeSize = nodeSize(node);

    g.drawRect(x, y, nodeSize.x, nodeSize.y);

    int X = x + context.paddingLeft();
    int Y = y + context.paddingTop();

    Y = Y + lineHeight;

    g.drawString(countText(node), X, Y - 3);

    Y = Y + elementSpace;

    g.fillOval(X, Y, referenceRadius * 2, referenceRadius * 2);

    Y = Y + referenceRadius * 2;

    for (Object element : node.elements) {
      String elementStr = elementToStr(element);
      Y = Y + elementSpace + lineHeight;
      g.drawString(elementStr, X, Y - 3);
      Y = Y + elementSpace;
//      g.fillOval(X, Y, referenceRadius * 2, referenceRadius * 2);
      Y = Y + referenceRadius * 2;
    }

//    g.setColor(Color.RED);
    for (int i = 0; i <= node.elements.length; i++) {

      Point point = referenceCenterPosition(node, x, y, i);
      g.fillOval(point.x - referenceRadius, point.y - referenceRadius, referenceRadius * 2, referenceRadius * 2);

    }
  }


  public void connectArrow(int i, Node node1, int x1, int y1,
                           @SuppressWarnings("unused") Node node2, int x2, int y2) {

    Point point = referenceCenterPosition(node1, x1, y1, i);

    int x0 = point.x;
    int y0 = point.y;

    int x3 = x2 + context.paddingLeft() + context.referenceRadius();
    //noinspection UnnecessaryLocalVariable
    int y3 = y2;

    Graphics2D g = context.graphics();
    g.setColor(Color.BLACK);

    g.drawLine(x0, y0, x3, y0);
    g.drawLine(x3, y0, x3, y3);
    g.drawLine(x3, y3, x3 - 3, y3 - 7);
    g.drawLine(x3, y3, x3 + 3, y3 - 7);

  }

  public void paintMultiTree(MultiTreeMap multiTreeMap) {
    Node root = multiTreeMap.root;

    Graphics2D gt = context.graphics();
    gt.setColor(Color.BLACK);
    FontMetrics fontMetrics = gt.getFontMetrics();

    int x = context.pageLeft();
    int y = context.pageTop() + fontMetrics.getHeight();

    int top = y + context.elementSpace();

    if (root == null) {
      context.setSize(context.pageLeft() + context.pageRight(), top);
      Graphics2D g = context.graphics();
      g.setColor(Color.BLACK);
      g.drawString("mod=" + multiTreeMap.modCount, x, y);
      return;
    }

    {
      Point size = treeSize(root);

      context.setSize(
          context.pageLeft() + context.pageRight() + size.x,
          top + context.elementSpace() + context.pageBottom() + size.y
      );

      Graphics2D g = context.graphics();
      g.setColor(Color.BLACK);
      g.drawString("mod=" + multiTreeMap.modCount, x, y);

      paintTree(root, context.pageLeft(), top);
    }
  }

  private static class NodeRef {
    final Node node;
    final int index;
    Point treeSize;
    int dx = 0;

    public NodeRef(Node node, int index) {
      this.node = node;
      this.index = index;
    }
  }

  private List<NodeRef> collectRefs(Node node) {
    List<NodeRef> refs = new ArrayList<>();

    for (int i = 0; i < node.references.length; i++) {
      Node n = node.references[i];
      if (n != null) {
        NodeRef ref = new NodeRef(n, i);
        ref.treeSize = treeSize(n);
        refs.add(ref);
      }
    }

    for (int i = 0, n = refs.size() / 2; i < n; i++) {
      int j = refs.size() - i - 1;
      NodeRef tmp = refs.get(i);
      refs.set(i, refs.get(j));
      refs.set(j, tmp);
    }

    int size = refs.size();

    if (size == 0) {
      return refs;
    }

    refs.get(0).dx = 0;

    Point nodeSize = nodeSize(node);

    if (refs.get(0).index < node.references.length - 1) {
      refs.get(0).dx = nodeSize.x;
    }

    if (size >= 2) {

      refs.get(1).dx = refs.get(0).dx + refs.get(0).treeSize.x + context.nodeSpaceH();

      if (refs.get(1).dx < nodeSize.x) {
        refs.get(1).dx = nodeSize.x;
      }

    }

    for (int i = 2; i < size; i++) {

      refs.get(i).dx = refs.get(i - 1).dx + refs.get(i - 1).treeSize.x + context.nodeSpaceH();

    }

    return refs;
  }

  public Point treeSize(Node node) {

    Point nodeSize = nodeSize(node);

    List<NodeRef> refs = collectRefs(node);

    if (refs.isEmpty()) {
      return nodeSize;
    }

    int height = 0;

    for (NodeRef ref : refs) {
      if (height < ref.treeSize.y) {
        height = ref.treeSize.y;
      }
    }

    NodeRef last = refs.get(refs.size() - 1);

    return new Point(last.dx + last.treeSize.x, nodeSize.y + context.nodeSpaceV() + height);
  }

  public void paintTree(Node node, int x, int y) {

    paintNodeAt(node, x, y);

    Point nodeSize = nodeSize(node);

    List<NodeRef> nodeRefs = collectRefs(node);

    int Y = y + nodeSize.y + context.nodeSpaceV();

    for (NodeRef ref : nodeRefs) {

      int x2 = x + ref.dx, y2 = Y;

      paintTree(ref.node, x2, y2);

      connectArrow(ref.index, node, x, y, ref.node, x2, y2);
    }

  }
}
