package pompei.maths.end_element;

public interface DrawCellsFilter {
  boolean drawCell(Cell cell, int cellIndex);

  boolean drawSideWithIndex(Cell cell, int sideIndex);

  boolean drawPointLabels(Cell cell);

  boolean drawCellLabel(Cell cell, int cellIndex);
}
