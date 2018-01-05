package pompei.maths.end_element;

import java.util.HashMap;
import java.util.Map;

public class Task1 {
  private Scene scene;

  public Task1(Scene scene) {
    this.scene = scene;
  }

  @SuppressWarnings("PointlessArithmeticExpression")
  public void createField(double dx, double dy, int Nx, int Ny) {

    PointIndex pointIndex = new PointIndex(dx * 0.99, dy * 0.99);

    for (int j = 0; j < Ny + 1; j++) {
      for (int i = 0; i < Nx + 1; i++) {

        double x = dx * i;
        double y = dy * j;

        pointIndex.addIndex(x, y, scene.pointList.size());
        scene.pointList.add(new Point(x, y));

      }
    }

    final Map<IntPair, Side> sideIndex = new HashMap<>();

    for (int j = 0; j <= Ny; j++) {
      for (int i = 0; i <= Nx; i++) {
        if (i < Nx) {
          SideType sideType = SideType.WORKING;
          if (j == 0) sideType = SideType.RIGHT_WALL;
          else if (j == Ny) sideType = SideType.LEFT_WALL;
          Side side = new Side(j * (Nx + 1) + i, j * (Nx + 1) + i + 1, sideType);
          scene.sideList.add(side);
          sideIndex.put(new IntPair(side.p1, side.p2), side);
        }
        if (j < Ny) {
          SideType sideType = SideType.WORKING;
          if (i == 0) sideType = SideType.LEFT_INPUT;
          else if (i == Nx) sideType = SideType.RIGHT_OUTPUT;
          Side side = new Side(j * (Nx + 1) + i, (j + 1) * (Nx + 1) + i, sideType);
          scene.sideList.add(side);
          sideIndex.put(new IntPair(side.p1, side.p2), side);
        }
      }
    }

    for (int j = 0; j < Ny; j++) {
      for (int i = 0; i < Nx; i++) {
        Cell cell = new Cell(dx * i, dy * j, dx * (i + 1), dy * (j + 1));
        scene.cellList.add(cell);
        cell.p1 = pointIndex.getNearestIndex(cell.x1, cell.y1);
        cell.p2 = pointIndex.getNearestIndex(cell.x2, cell.y1);
        cell.p3 = pointIndex.getNearestIndex(cell.x2, cell.y2);
        cell.p4 = pointIndex.getNearestIndex(cell.x1, cell.y2);

        cell.sides.add(findSide(cell, cell.p1, cell.p2, sideIndex));
        cell.sides.add(findSide(cell, cell.p2, cell.p3, sideIndex));
        cell.sides.add(findSide(cell, cell.p3, cell.p4, sideIndex));
        cell.sides.add(findSide(cell, cell.p4, cell.p1, sideIndex));

      }
    }

    for (Cell cell : scene.cellList) {
      for (SideRef ref : cell.sides) {
        if (ref.right) {
          ref.side.rightRef = ref;
        } else {
          ref.side.leftRef = ref;
        }
      }
    }

    for (Side side : scene.sideList) {
      Point p1 = scene.pointList.get(side.p1);
      Point p2 = scene.pointList.get(side.p2);
      double dpx = p2.x - p1.x, dpy = p2.y - p1.y;
      double len = Math.sqrt(dpx * dpx + dpy * dpy);
      side.S = len;
      side.px = dpx / len;
      side.py = dpy / len;
      side.nx = -side.py;// (x, y) ---> (-y, x)  ==  rotated to right on 90 deg
      side.ny = side.px;
    }

    for (Cell cell : scene.cellList) {
      cell.p = 1;
      cell.ro = 1;
      cell.vx = cell.vy = 0;
      cell.V = Math.abs((cell.x1 - cell.x2) * (cell.y1 - cell.y2));
    }

  }

  private static SideRef findSide(Cell owner, int p1, int p2, Map<IntPair, Side> sideIndex) {

    {
      Side side = sideIndex.get(new IntPair(p1, p2));
      if (side != null) return new SideRef(owner, side, false);
    }

    {
      Side side = sideIndex.get(new IntPair(p2, p1));
      if (side != null) return new SideRef(owner, side, true);
    }

    throw new RuntimeException("Cannot find side for " + p1 + " -> " + p2);
  }
}
