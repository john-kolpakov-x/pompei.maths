package pompei.maths.multi_tree;

public class PaintFacade {

  public static <K extends Comparable<K>, V> void paintMapToFile(MultiTreeMap<K, V> map, String destPngFileName) {

    PaintContext paintContext = new PaintContext();
    paintContext.setOutPngFileName(destPngFileName);
    MultiTreePainter painter = new MultiTreePainter(paintContext);

    painter.paintMultiTree(map);

    paintContext.finish();

  }

}
