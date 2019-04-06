package pompei.maths.multi_tree;


import static org.fest.assertions.Assertions.assertThat;

public abstract class MultiTreeMapParent {

  protected Node node(int count, String... keys) {

    Node node = new Node(count, null);

    for (int i = 0; i < keys.length; i++) {
      if (keys[i] != null) {
        node.elements[i] = new KeyValue(keys[i], "w");
        node.count++;
      }
    }

    return node;

  }

  protected String currentTestName = null;

  protected void paintNode(MultiTreeMap map, String name) {
    String outPngFileName = "build/" + (getClass().getSimpleName()) + "/" + currentTestName + "/" + name + ".png";

    //noinspection unchecked
    PaintFacade.paintMapToFile(map, outPngFileName);

  }


  protected void assertElem(Node node, int i, String key) {
    KeyValue keyValue = (KeyValue) node.elements[i];
    if (key == null) {
      assertThat(keyValue).isNull();
    } else {
      assertThat(keyValue).isNotNull();
      assertThat(keyValue.key).isEqualTo(key);
    }

  }

}
