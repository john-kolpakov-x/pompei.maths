package pompei.maths.multi_tree;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class MultiTreeMap_removeRightest_Test extends MultiTreeMapParent {

  @Test
  public void withNullsInElements() {
    currentTestName = "withNullsInElements";

    MultiTreeMap map = new MultiTreeMap(8);

    map.root = node(8, "k01", "k02", "k03");

    paintNode(map, "01-before-removeRightest");

    assertThat(map.root.count).isEqualTo(3);

    //
    //
    KeyValue keyValue = map.removeRightest(map.root);
    //
    //

    paintNode(map, "02-after-removeRightest");

    assertThat(keyValue).isNotNull();
    assertThat(keyValue.key).isEqualTo("k03");

    assertThat(map.root.count).isEqualTo(2);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, null);
    assertElem(map.root, 3, null);
    assertElem(map.root, 4, null);
    assertElem(map.root, 5, null);
    assertElem(map.root, 6, null);
    assertElem(map.root, 7, null);
  }

  /**
   * Это супер пупер навороченный тест о том как присходит тестирвоание
   */
  @Test
  public void allElementsNotNull_referencesAllIsNull() {
    currentTestName = "allElementsNotNull_referencesAllIsNull";

    MultiTreeMap map = new MultiTreeMap(8);

    map.root = node(8, "k01", "k02", "k03", "k04", "k05", "k06", "k07", "k08");

    paintNode(map, "01-before-removeRightest");

    assertThat(map.root.count).isEqualTo(8);

    //
    //
    KeyValue keyValue = map.removeRightest(map.root);
    //
    //

    paintNode(map, "02-after-removeRightest");

    assertThat(keyValue).isNotNull();
    assertThat(keyValue.key).isEqualTo("k08");

    assertThat(map.root.count).isEqualTo(7);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07");
    assertElem(map.root, 7, null);
  }

  @Test
  public void firstReferenceDefined_refContainsMoreThenOne() {
    currentTestName = "firstReferenceDefined_refContainsMoreThenOne";

    MultiTreeMap map = new MultiTreeMap(8);

    map.root = node(8, "k01", "k02", "k03", "k04", "k05", "k06", "k07", "k08");
    map.root.references[0] = node(8, "k00-01", "k00-02", "k00-03");

    map.root.count += 3;

    paintNode(map, "01-before-removeRightest");

    assertThat(map.root.count).isEqualTo(8 + 3);
    assertThat(map.root.references[0].count).isEqualTo(3);

    //
    //
    KeyValue keyValue = map.removeRightest(map.root);
    //
    //

    paintNode(map, "02-after-removeRightest");

    assertThat(keyValue).isNotNull();
    assertThat(keyValue.key).isEqualTo("k08");

    assertThat(map.root.count).isEqualTo(8 + 2);
    assertThat(map.root.references[0].count).isEqualTo(2);

    assertElem(map.root, 0, "k00-03");
    assertElem(map.root, 1, "k01");
    assertElem(map.root, 2, "k02");
    assertElem(map.root, 3, "k03");
    assertElem(map.root, 4, "k04");
    assertElem(map.root, 5, "k05");
    assertElem(map.root, 6, "k06");
    assertElem(map.root, 7, "k07");

    assertElem(map.root.references[0], 0, "k00-01");
    assertElem(map.root.references[0], 1, "k00-02");
    assertElem(map.root.references[0], 2, null);
  }

  @Test
  public void firstReferenceDefined_refContainsOne() {
    currentTestName = "firstReferenceDefined_refContainsOne";

    MultiTreeMap map = new MultiTreeMap(8);

    map.root = node(8, "k01", "k02", "k03", "k04", "k05", "k06", "k07", "k08");
    map.root.references[0] = node(8, "k00-01");

    map.root.count += 1;

    paintNode(map, "01-before-removeRightest");

    assertThat(map.root.count).isEqualTo(8 + 1);
    assertThat(map.root.references[0].count).isEqualTo(1);

    //
    //
    KeyValue keyValue = map.removeRightest(map.root);
    //
    //

    paintNode(map, "02-after-removeRightest");

    assertThat(keyValue).isNotNull();
    assertThat(keyValue.key).isEqualTo("k08");

    assertThat(map.root.count).isEqualTo(8);
    assertThat(map.root.references[0]).isNull();

    assertElem(map.root, 0, "k00-01");
    assertElem(map.root, 1, "k01");
    assertElem(map.root, 2, "k02");
    assertElem(map.root, 3, "k03");
    assertElem(map.root, 4, "k04");
    assertElem(map.root, 5, "k05");
    assertElem(map.root, 6, "k06");
    assertElem(map.root, 7, "k07");
  }

  @Test
  public void thirdReferenceDefined_refContainsMoreThenOne() {
    currentTestName = "thirdReferenceDefined_refContainsMoreThenOne";

    MultiTreeMap map = new MultiTreeMap(8);

    map.root = node(8, "k01", "k02", "k03", "k04", "k05", "k06", "k07", "k08");
    map.root.references[3] = node(8, "k03-01", "k03-02", "k03-03");

    map.root.count += 3;

    paintNode(map, "01-before-removeRightest");

    assertThat(map.root.count).isEqualTo(8 + 3);
    assertThat(map.root.references[3].count).isEqualTo(3);

    //
    //
    KeyValue keyValue = map.removeRightest(map.root);
    //
    //

    paintNode(map, "02-after-removeRightest");

    assertThat(keyValue).isNotNull();
    assertThat(keyValue.key).isEqualTo("k08");

    assertThat(map.root.count).isEqualTo(8 + 2);
    assertThat(map.root.references[3].count).isEqualTo(2);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k03-03");
    assertElem(map.root, 4, "k04");
    assertElem(map.root, 5, "k05");
    assertElem(map.root, 6, "k06");
    assertElem(map.root, 7, "k07");

    assertElem(map.root.references[3], 0, "k03-01");
    assertElem(map.root.references[3], 1, "k03-02");
    assertElem(map.root.references[3], 2, null);
  }

  @Test
  public void thirdReferenceDefined_refContainsOne() {
    currentTestName = "thirdReferenceDefined_refContainsOne";

    MultiTreeMap map = new MultiTreeMap(8);

    map.root = node(8, "k01", "k02", "k03", "k04", "k05", "k06", "k07", "k08");
    map.root.references[3] = node(8, "k03-01");

    map.root.count += 1;

    paintNode(map, "01-before-removeRightest");

    assertThat(map.root.count).isEqualTo(8 + 1);
    assertThat(map.root.references[3].count).isEqualTo(1);

    //
    //
    KeyValue keyValue = map.removeRightest(map.root);
    //
    //

    paintNode(map, "02-after-removeRightest");

    assertThat(keyValue).isNotNull();
    assertThat(keyValue.key).isEqualTo("k08");

    assertThat(map.root.count).isEqualTo(8);
    assertThat(map.root.references[3]).isNull();

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k03-01");
    assertElem(map.root, 4, "k04");
    assertElem(map.root, 5, "k05");
    assertElem(map.root, 6, "k06");
    assertElem(map.root, 7, "k07");
  }


  @Test
  public void lastReferenceDefined_refContainsMoreThenOne() {
    currentTestName = "lastReferenceDefined_refContainsMoreThenOne";

    MultiTreeMap map = new MultiTreeMap(8);

    map.root = node(8, "k01", "k02", "k03", "k04", "k05", "k06", "k07", "k08");
    map.root.references[8] = node(8, "k08-01", "k08-02", "k08-03");

    map.root.count += 3;

    paintNode(map, "01-before-removeRightest");

    assertThat(map.root.count).isEqualTo(8 + 3);
    assertThat(map.root.references[8].count).isEqualTo(3);

    //
    //
    KeyValue keyValue = map.removeRightest(map.root);
    //
    //

    paintNode(map, "02-after-removeRightest");

    assertThat(keyValue).isNotNull();
    assertThat(keyValue.key).isEqualTo("k08-03");

    assertThat(map.root.count).isEqualTo(8 + 2);
    assertThat(map.root.references[8].count).isEqualTo(2);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07");
    assertElem(map.root, 7, "k08");

    assertElem(map.root.references[8], 0, "k08-01");
    assertElem(map.root.references[8], 1, "k08-02");
    assertElem(map.root.references[8], 2, null);
  }

  @Test
  public void lastReferenceDefined_refContainsOne() {
    currentTestName = "lastReferenceDefined_refContainsOne";

    MultiTreeMap map = new MultiTreeMap(8);

    map.root = node(8, "k01", "k02", "k03", "k04", "k05", "k06", "k07", "k08");
    map.root.references[8] = node(8, "k08-01");

    map.root.count += 1;

    paintNode(map, "01-before-removeRightest");

    assertThat(map.root.count).isEqualTo(8 + 1);
    assertThat(map.root.references[8].count).isEqualTo(1);

    //
    //
    KeyValue keyValue = map.removeRightest(map.root);
    //
    //

    paintNode(map, "02-after-removeRightest");

    assertThat(keyValue).isNotNull();
    assertThat(keyValue.key).isEqualTo("k08-01");

    assertThat(map.root.count).isEqualTo(8);
    assertThat(map.root.references[8]).isNull();

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07");
    assertElem(map.root, 7, "k08");
  }

}
