package pompei.maths.multi_tree;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


public class MultiTreeMap_removeHole_Test extends MultiTreeMapParent {

  @Test
  public void first_noReferences_notFull() {
    currentTestName = "first_noReferences_notFull";

    MultiTreeMap map = new MultiTreeMap(8);

    map.root = node(8, null, "k02", "k03", "k04", "k05");
    map.root.count = 5;

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 0);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k02");
    assertElem(map.root, 1, "k03");
    assertElem(map.root, 2, "k04");
    assertElem(map.root, 3, "k05");
    assertElem(map.root, 4, null);
    assertElem(map.root, 5, null);
    assertElem(map.root, 6, null);
  }

  @Test
  public void middle_noReferences_notFull() {
    currentTestName = "first_noReferences_notFull";

    MultiTreeMap map = new MultiTreeMap(8);

    map.root = node(8, "k01", "k02", null, "k04", "k05");
    map.root.count = 5;

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 2);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k04");
    assertElem(map.root, 3, "k05");
    assertElem(map.root, 4, null);
    assertElem(map.root, 5, null);
    assertElem(map.root, 6, null);
  }

  @Test
  public void last_noReferences_notFull() {
    currentTestName = "last_noReferences_notFull";

    MultiTreeMap map = new MultiTreeMap(8);

    map.root = node(8, "k01", "k02", "k03", "k04", null);
    map.root.count = 5;

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 4);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, null);
    assertElem(map.root, 5, null);
    assertElem(map.root, 6, null);
  }


  @Test
  public void first_noReferences_full() {
    currentTestName = "first_noReferences_full";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, null, "k02", "k03", "k04", "k05", "k06", "k07");
    map.root.count = 5;

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 0);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k02");
    assertElem(map.root, 1, "k03");
    assertElem(map.root, 2, "k04");
    assertElem(map.root, 3, "k05");
    assertElem(map.root, 4, "k06");
    assertElem(map.root, 5, "k07");
    assertElem(map.root, 6, null);
  }

  @Test
  public void middle_noReferences_full() {
    currentTestName = "middle_noReferences_full";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, "k01", "k02", "k03", "k04", null, "k06", "k07");
    map.root.count = 5;

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 4);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k06");
    assertElem(map.root, 5, "k07");
    assertElem(map.root, 6, null);
  }

  @Test
  public void last_noReferences_full() {
    currentTestName = "last_noReferences_full";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, "k01", "k02", "k03", "k04", "k05", "k06", null);
    map.root.count = 5;

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 6);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, null);
  }


  @Test
  public void first_referenceAt0_refsMoreOne() {
    currentTestName = "first_referenceAt0_refsMoreOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, null, "k02", "k03", "k04", "k05", "k06", "k07");
    map.root.count = 5;
    map.root.references[0] = node(7, "k00-01", "k00-02");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 0);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k00-02");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07");

    assertThat(map.root.references[0]).isNotNull();
  }

  @Test
  public void first_referenceAt0_refWithOne() {
    currentTestName = "first_referenceAt0_refWithOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, null, "k02", "k03", "k04", "k05", "k06", "k07");
    map.root.count = 5;
    map.root.references[0] = node(7, "k00-01");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 0);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k00-01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07");

    assertThat(map.root.references[0]).isNull();
  }

  @Test
  public void first_referenceAt1_refsMoreOne() {
    currentTestName = "first_referenceAt0_refWithOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, null, "k02", "k03", "k04", "k05", "k06", "k07");
    map.root.count = 5;
    map.root.references[1] = node(7, "k01-01", "k01-02");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 0);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01-01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07");

    assertThat(map.root.references[1]).isNotNull();
  }

  @Test
  public void first_referenceAt1_refWithOne() {
    currentTestName = "first_referenceAt0_refWithOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, null, "k02", "k03", "k04", "k05", "k06", "k07");
    map.root.count = 5;
    map.root.references[1] = node(7, "k01-01");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 0);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01-01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07");

    assertThat(map.root.references[1]).isNull();
  }

  @Test
  public void first_referenceAt4_refsMoreOne() {
    currentTestName = "first_referenceAt4_refsMoreOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, null, "k02", "k03", "k04", "k05", "k06", "k07");
    map.root.count = 5;
    map.root.references[4] = node(7, "k04-01", "k04-02");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 0);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k02");
    assertElem(map.root, 1, "k03");
    assertElem(map.root, 2, "k04");
    assertElem(map.root, 3, "k04-01");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07");

    assertThat(map.root.references[4]).isNotNull();
  }

  @Test
  public void first_referenceAt4_refWithOne() {
    currentTestName = "first_referenceAt4_refWithOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, null, "k02", "k03", "k04", "k05", "k06", "k07");
    map.root.count = 5;
    map.root.references[4] = node(7, "k04-01");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 0);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k02");
    assertElem(map.root, 1, "k03");
    assertElem(map.root, 2, "k04");
    assertElem(map.root, 3, "k04-01");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07");

    assertThat(map.root.references[4]).isNull();
  }

  @Test
  public void first_referenceAtEnd_refsMoreOne() {
    currentTestName = "first_referenceAtEnd_refsMoreOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, null, "k02", "k03", "k04", "k05", "k06", "k07");
    map.root.count = 5;
    map.root.references[7] = node(7, "k07-01", "k07-02");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 0);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k02");
    assertElem(map.root, 1, "k03");
    assertElem(map.root, 2, "k04");
    assertElem(map.root, 3, "k05");
    assertElem(map.root, 4, "k06");
    assertElem(map.root, 5, "k07");
    assertElem(map.root, 6, "k07-01");

    assertThat(map.root.references[7]).isNotNull();
  }

  @Test
  public void first_referenceAtEnd_refWithOne() {
    currentTestName = "first_referenceAtEnd_refWithOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, null, "k02", "k03", "k04", "k05", "k06", "k07");
    map.root.count = 5;
    map.root.references[7] = node(7, "k07-01");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 0);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k02");
    assertElem(map.root, 1, "k03");
    assertElem(map.root, 2, "k04");
    assertElem(map.root, 3, "k05");
    assertElem(map.root, 4, "k06");
    assertElem(map.root, 5, "k07");
    assertElem(map.root, 6, "k07-01");

    assertThat(map.root.references[7]).isNull();
  }


  @Test
  public void last_referenceAt0_refsMoreOne() {
    currentTestName = "last_referenceAt0_refsMoreOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, "k01", "k02", "k03", "k04", "k05", "k06", null);
    map.root.count = 5;
    map.root.references[0] = node(7, "k00-01", "k00-02");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 6);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k00-02");
    assertElem(map.root, 1, "k01");
    assertElem(map.root, 2, "k02");
    assertElem(map.root, 3, "k03");
    assertElem(map.root, 4, "k04");
    assertElem(map.root, 5, "k05");
    assertElem(map.root, 6, "k06");

    assertThat(map.root.references[0]).isNotNull();
  }

  @Test
  public void last_referenceAt0_refWithOne() {
    currentTestName = "last_referenceAt0_refWithOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, "k01", "k02", "k03", "k04", "k05", "k06", null);
    map.root.count = 5;
    map.root.references[0] = node(7, "k00-01");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 6);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k00-01");
    assertElem(map.root, 1, "k01");
    assertElem(map.root, 2, "k02");
    assertElem(map.root, 3, "k03");
    assertElem(map.root, 4, "k04");
    assertElem(map.root, 5, "k05");
    assertElem(map.root, 6, "k06");

    assertThat(map.root.references[0]).isNull();
  }

  @Test
  public void last_referenceAtBeforeEnd_refsMoreOne() {
    currentTestName = "last_referenceAtBeforeEnd_refsMoreOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, "k01", "k02", "k03", "k04", "k05", "k06", null);
    map.root.count = 5;
    map.root.references[6] = node(7, "k06-01", "k06-02");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 6);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k06-01");

    assertThat(map.root.references[6]).isNotNull();
  }

  @Test
  public void last_referenceAtBeforeEnd_refWithOne() {
    currentTestName = "last_referenceAtBeforeEnd_refWithOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, "k01", "k02", "k03", "k04", "k05", "k06", null);
    map.root.count = 5;
    map.root.references[6] = node(7, "k06-01");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 6);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k06-01");

    assertThat(map.root.references[6]).isNull();
  }

  @Test
  public void last_referenceAt4_refsMoreOne() {
    currentTestName = "last_referenceAt4_refsMoreOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, "k01", "k02", "k03", "k04", "k05", "k06", null);
    map.root.count = 5;
    map.root.references[4] = node(7, "k04-01", "k04-02");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 6);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k04-02");
    assertElem(map.root, 5, "k05");
    assertElem(map.root, 6, "k06");

    assertThat(map.root.references[4]).isNotNull();
  }

  @Test
  public void last_referenceAt4_refWithOne() {
    currentTestName = "last_referenceAt4_refWithOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, "k01", "k02", "k03", "k04", "k05", "k06", null);
    map.root.count = 5;
    map.root.references[4] = node(7, "k04-01");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 6);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k04-01");
    assertElem(map.root, 5, "k05");
    assertElem(map.root, 6, "k06");

    assertThat(map.root.references[4]).isNull();
  }

  @Test
  public void last_referenceAtEnd_refsMoreOne() {
    currentTestName = "last_referenceAtEnd_refsMoreOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, "k01", "k02", "k03", "k04", "k05", "k06", null);
    map.root.count = 5;
    map.root.references[7] = node(7, "k07-01", "k07-02");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 6);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07-01");

    assertThat(map.root.references[7]).isNotNull();
  }

  @Test
  public void last_referenceAtEnd_refWithOne() {
    currentTestName = "last_referenceAtEnd_refWithOne";

    MultiTreeMap map = new MultiTreeMap(7);

    map.root = node(7, "k01", "k02", "k03", "k04", "k05", "k06", null);
    map.root.count = 5;
    map.root.references[7] = node(7, "k07-01");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 6);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07-01");

    assertThat(map.root.references[7]).isNull();
  }

  @Test
  public void middle_doubleReference_leftCloser_refsMoreOne() {
    currentTestName = "middle_doubleReference_leftCloser_refsMoreOne";

    MultiTreeMap map = new MultiTreeMap(9);

    map.root = node(9, "k01", "k02", "k03", null, "k05", "k06", "k07", "k08", "k09");
    map.root.count = 5;
    map.root.references[2] = node(9, "k02-01", "k02-02");
    map.root.references[7] = node(9, "k07-01", "k07-02");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 3);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k02-02");
    assertElem(map.root, 3, "k03");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07");
    assertElem(map.root, 7, "k08");
    assertElem(map.root, 8, "k09");

    assertThat(map.root.references[2]).isNotNull();
    assertThat(map.root.references[7]).isNotNull();
  }

  @Test
  public void middle_doubleReference_leftCloser_refWithOne() {
    currentTestName = "middle_doubleReference_leftCloser_refWithOne";

    MultiTreeMap map = new MultiTreeMap(9);

    map.root = node(9, "k01", "k02", "k03", null, "k05", "k06", "k07", "k08", "k09");
    map.root.count = 5;
    map.root.references[2] = node(9, "k02-01");
    map.root.references[7] = node(9, "k07-01");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 3);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k02-01");
    assertElem(map.root, 3, "k03");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k06");
    assertElem(map.root, 6, "k07");
    assertElem(map.root, 7, "k08");
    assertElem(map.root, 8, "k09");

    assertThat(map.root.references[2]).isNull();
    assertThat(map.root.references[7]).isNotNull();
  }

  @Test
  public void middle_doubleReference_rightCloser_refsMoreOne() {
    currentTestName = "middle_doubleReference_rightCloser_refsMoreOne";

    MultiTreeMap map = new MultiTreeMap(9);

    map.root = node(9, "k01", "k02", "k03", "k04", "k05", null, "k07", "k08", "k09");
    map.root.count = 5;
    map.root.references[2] = node(9, "k02-01", "k02-02");
    map.root.references[8] = node(9, "k08-01", "k08-02");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 5);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k07");
    assertElem(map.root, 6, "k08");
    assertElem(map.root, 7, "k08-01");
    assertElem(map.root, 8, "k09");

    assertThat(map.root.references[2]).isNotNull();
    assertThat(map.root.references[8]).isNotNull();
  }

  @Test
  public void middle_doubleReference_rightCloser_refWithOne() {
    currentTestName = "middle_doubleReference_rightCloser_refsMoreOne";

    MultiTreeMap map = new MultiTreeMap(9);

    map.root = node(9, "k01", "k02", "k03", "k04", "k05", null, "k07", "k08", "k09");
    map.root.count = 5;
    map.root.references[2] = node(9, "k02-01");
    map.root.references[8] = node(9, "k08-01");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 5);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);

    assertElem(map.root, 0, "k01");
    assertElem(map.root, 1, "k02");
    assertElem(map.root, 2, "k03");
    assertElem(map.root, 3, "k04");
    assertElem(map.root, 4, "k05");
    assertElem(map.root, 5, "k07");
    assertElem(map.root, 6, "k08");
    assertElem(map.root, 7, "k08-01");
    assertElem(map.root, 8, "k09");

    assertThat(map.root.references[2]).isNotNull();
    assertThat(map.root.references[8]).isNull();
  }

  @Test
  public void nearDoubleReference_first_refsMoreOne() {
    currentTestName = "nearDoubleReference_first_refWithOne";

    MultiTreeMap map = new MultiTreeMap(9);

    map.root = node(9, null, "k02", "k03", "k04", "k05", "k06", "k07", "k08", "k09");
    map.root.count = 5;
    map.root.references[0] = node(9, "k00-01", "k00-02");
    map.root.references[1] = node(9, "k01-01", "k01-02");

    paintNode(map, "01-before");

    assertThat(map.root.count).isEqualTo(5);

    //
    //
    map.removeHole(map.root, 5);
    //
    //

    paintNode(map, "02-after");

    assertThat(map.root.count).isEqualTo(5);


  }
}
