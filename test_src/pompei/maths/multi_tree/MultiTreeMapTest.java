package pompei.maths.multi_tree;

import org.testng.annotations.Test;

public class MultiTreeMapTest {

  @Test
  public void simplePaint() {

    PaintContext paintContext = new PaintContext();
    paintContext.setOutPngFileName("build/MultiTreeMapTest/simplePaint.png");
    paintContext.setSize(800, 600);

    MultiTreePainter painter = new MultiTreePainter(paintContext);

    var node1 = new Node(3, null);
    node1.count = 37;
    node1.elements[0] = new KeyValue("node001", "x");
    node1.elements[1] = null;
    node1.elements[2] = new KeyValue("node003", "x");

    painter.paintNodeAt(node1, 30, 30);

    var node2 = new Node(3, null);
    node2.count = 137;
    node2.elements[0] = new KeyValue("node009", "x");
    node2.elements[1] = null;
    node2.elements[2] = new KeyValue("node017", "x");

    painter.paintNodeAt(node2, 130, 230);

    painter.connectArrow(1, node1, 30, 30, node2, 130, 230);

    paintContext.finish();

  }

  @Test
  public void paintTree() {

    var node1 = new Node(3, null);
    node1.count = 1;
    node1.elements[0] = new KeyValue("node001", "x");
    node1.elements[1] = null;
    node1.elements[2] = new KeyValue("node003", "x");

    var node2 = new Node(3, null);
    node2.count = 2;
    node2.elements[0] = new KeyValue("node009", "x");
    node2.elements[1] = null;
    node2.elements[2] = new KeyValue("node017", "x");

    node1.references[3] = node2;

    var node3 = new Node(3, null);
    node3.count = 3;
    node3.elements[0] = new KeyValue("node011", "x");
    node3.elements[1] = null;
    node3.elements[2] = new KeyValue("node012", "x");

    node1.references[1] = node3;

    var node4 = new Node(3, null);
    node4.count = 4;
    node4.elements[0] = new KeyValue("node031", "x");
    node4.elements[1] = new KeyValue("Hello", "x");
    node4.elements[2] = new KeyValue("node037", "x");

    node2.references[2] = node4;

    var node5 = new Node(3, null);
    node5.count = 5;
    node5.elements[0] = new KeyValue("node071", "x");
    node5.elements[1] = new KeyValue("Pause", "x");
    node5.elements[2] = new KeyValue("node077", "x");

    node2.references[0] = node5;

    var node6 = new Node(3, null);
    node6.count = 6;
    node6.elements[0] = new KeyValue("node074", "x");
    node6.elements[1] = new KeyValue("Pause1", "x");
    node6.elements[2] = new KeyValue("node078", "x");

    node3.references[3] = node6;

    var node7 = new Node(3, null);
    node7.count = 7;
    node7.elements[0] = new KeyValue("vvv", "x");
    node7.elements[1] = new KeyValue("vvv", "x");
    node7.elements[2] = new KeyValue("vvv", "x");

    node3.references[1] = node7;

    //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
    //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
    //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //

    PaintContext paintContext = new PaintContext();
    paintContext.setOutPngFileName("build/MultiTreeMapTest/paintTree.png");
    paintContext.setSize(800, 600);

    MultiTreePainter painter = new MultiTreePainter(paintContext);

    painter.paintTree(node1, 30, 30);

    paintContext.finish();
  }

  @Test
  public void paintMultiTree() {

    var node1 = new Node(3, null);
    node1.count = 1;
    node1.elements[0] = new KeyValue("node001", "x");
    node1.elements[1] = null;
    node1.elements[2] = new KeyValue("node003", "x");

    var node2 = new Node(3, null);
    node2.count = 2;
    node2.elements[0] = new KeyValue("node009", "x");
    node2.elements[1] = null;
    node2.elements[2] = new KeyValue("node017", "x");

    node1.references[3] = node2;

    var node3 = new Node(3, null);
    node3.count = 3;
    node3.elements[0] = new KeyValue("node011", "x");
    node3.elements[1] = null;
    node3.elements[2] = new KeyValue("node012", "x");

    node1.references[2] = node3;

    var node4 = new Node(3, null);
    node4.count = 4;
    node4.elements[0] = new KeyValue("node031", "x");
    node4.elements[1] = new KeyValue("Hello", "x");
    node4.elements[2] = new KeyValue("node037", "x");

    node2.references[3] = node4;

    var node5 = new Node(3, null);
    node5.count = 5;
    node5.elements[0] = new KeyValue("node071", "x");
    node5.elements[1] = new KeyValue("Pause", "x");
    node5.elements[2] = new KeyValue("node077", "x");

    node2.references[0] = node5;

    var node6 = new Node(3, null);
    node6.count = 6;
    node6.elements[0] = new KeyValue("node074", "x");
    node6.elements[1] = new KeyValue("Pause1", "x");
    node6.elements[2] = new KeyValue("node078", "x");

    node3.references[3] = node6;

    var node7 = new Node(3, null);
    node7.count = 7;
    node7.elements[0] = new KeyValue("node101", "x");
    node7.elements[1] = new KeyValue("node102", "x");
    node7.elements[2] = new KeyValue("node103", "x");

    node3.references[2] = node7;

    var node8 = new Node(3, null);
    node8.count = 8;
    node8.elements[0] = new KeyValue("node111", "x");
    node8.elements[1] = new KeyValue("node112", "x");
    node8.elements[2] = new KeyValue("node113", "x");

    node3.references[1] = node8;

    var node9 = new Node(3, null);
    node9.count = 9;
    node9.elements[0] = new KeyValue("node121", "x");
    node9.elements[1] = new KeyValue("node122", "x");
    node9.elements[2] = new KeyValue("node123", "x");

    node3.references[0] = node9;

    var node300 = new Node(3, null);
    node300.count = 300;
    node300.elements[0] = new KeyValue("node1031", "x");
    node300.elements[1] = new KeyValue("node1032", "x");
    node300.elements[2] = new KeyValue("node1033", "x");

    node1.references[0] = node300;

    var node301 = new Node(3, null);
    node301.count = 301;
    node301.elements[0] = new KeyValue("node1041", "x");
    node301.elements[1] = new KeyValue("node1042", "x");
    node301.elements[2] = new KeyValue("node1043", "x");

    node300.references[3] = node301;

    var node302 = new Node(3, null);
    node302.count = 302;
    node302.elements[0] = new KeyValue("node1051", "x");
    node302.elements[1] = new KeyValue("node1052", "x");
    node302.elements[2] = new KeyValue("node1053", "x");

    node300.references[2] = node302;

    var node200 = new Node(3, null);
    node200.count = 200;
    node200.elements[0] = new KeyValue("node1001", "x");
    node200.elements[1] = new KeyValue("node1002", "x");
    node200.elements[2] = new KeyValue("node1003", "x");

    node1.references[1] = node200;

    var node201 = new Node(3, null);
    node201.count = 201;
    node201.elements[0] = new KeyValue("node1011", "x");
    node201.elements[1] = new KeyValue("node1012", "x");
    node201.elements[2] = new KeyValue("node1013", "x");

    node200.references[3] = node201;

    var node202 = new Node(3, null);
    node202.count = 202;
    node202.elements[0] = new KeyValue("node1021", "x");
    node202.elements[1] = new KeyValue("node1022", "x");
    node202.elements[2] = new KeyValue("node1023", "x");

    node200.references[1] = node202;

    //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
    //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //
    //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //


    MultiTreeMap multiTreeMap = new MultiTreeMap();
    multiTreeMap.root = node1;

    PaintContext paintContext = new PaintContext();
    paintContext.setOutPngFileName("build/MultiTreeMapTest/paintMultiTree.png");
    MultiTreePainter painter = new MultiTreePainter(paintContext);

    painter.paintMultiTree(multiTreeMap);

    paintContext.finish();

  }

  @Test
  public void mod() {

    MultiTreeMap<String, String> map = new MultiTreeMap<>(8);

    putToMap(map, "01", "k02", "o");
    putToMap(map, "02", "k03", "o");
    putToMap(map, "03", "k05", "o");
    putToMap(map, "04", "k06", "o");
    putToMap(map, "05", "k08", "o");
    putToMap(map, "06", "k09", "o");
    putToMap(map, "07", "k07", "o");
    putToMap(map, "08", "k01", "o");

    putToMap(map, "09", "k04", "o");
    putToMap(map, "10", "k04-01", "o");
    putToMap(map, "11", "k04-02", "o");
    putToMap(map, "12", "k04-03", "o");

    putToMap(map, "13", "k10", "o");
    putToMap(map, "14", "k11", "o");
    putToMap(map, "15", "k12", "o");
    putToMap(map, "16", "k13", "o");
    putToMap(map, "17", "k14", "o");
    putToMap(map, "18", "k15", "o");
    putToMap(map, "19", "k16", "o");
    putToMap(map, "20", "k17", "o");

    putToMap(map, "21", "k04-02-01", "o");
    putToMap(map, "22", "k04-02-02", "o");
    putToMap(map, "23", "k04-02-03", "o");
    putToMap(map, "24", "k04-02-04", "o");

    putToMap(map, "25", "k07-05", "o");
    putToMap(map, "26", "k07-01", "o");
    putToMap(map, "27", "k07-02", "o");
    putToMap(map, "28", "k07-03", "o");
    putToMap(map, "29", "k07-04", "o");


    removeFromMap(map, "30", "k07-04");
  }

  @SuppressWarnings("SameParameterValue")
  private void removeFromMap(MultiTreeMap<String, String> map, String no, String key) {
    String removedValue = map.remove(key);
    System.out.println("removedValue = " + removedValue);
    String outPngFileName = "build/MultiTreeMapTest/mod-" + no + ".png";
    PaintFacade.paintMapToFile(map, outPngFileName);
  }

  @SuppressWarnings("SameParameterValue")
  private void putToMap(MultiTreeMap<String, String> map, String no, String key, String value) {

    map.put(key, value);

    String outPngFileName = "build/MultiTreeMapTest/mod-" + no + ".png";

    PaintFacade.paintMapToFile(map, outPngFileName);
  }
}
