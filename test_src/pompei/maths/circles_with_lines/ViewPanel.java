package pompei.maths.circles_with_lines;

import pompei.maths.circles_with_lines.model.Circle;
import pompei.maths.circles_with_lines.model.Line;
import pompei.maths.utils.Vec2;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ViewPanel extends JPanel {

  int mouseX = -1, mouseY = -1;
  int mouseDragX = -1, mouseDragY = -1;

  private final List<MouseEvent> mouseDownList = new ArrayList<>();

  private final MovingCalculus movingCalculus = new MovingCalculus();

  public ViewPanel() {

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {

        if (mouseDownList.isEmpty()) {
          mouseDragX = mouseX;
          mouseDragY = mouseY;
        }

        mouseDownList.add(e);

        onMouseDown();

        updateScreen();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        onMouseUp();
        mouseDownList.remove(mouseDownList.size() - 1);

        if (mouseDownList.isEmpty()) {
          mouseX = mouseDragX;
          mouseY = mouseDragY;
        }

        updateScreen();
      }
    });

    addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        updateScreen();
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        mouseDragX = e.getX();
        mouseDragY = e.getY();
        updateScreen();
      }
    });

    addMouseWheelListener(new MouseAdapter() {
      @Override
      public void mouseWheelMoved(MouseWheelEvent e) {
        if (mouseDownList.isEmpty()) {

          double K = 2;
          if (e.getWheelRotation() > 0) {
            K = 1 / K;
          }

          changeScale(K);
        }
      }
    });

    setCursor(getToolkit().createCustomCursor(
        new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
        new Point(),
        null));
  }

  protected final DownKeyStack keyDownStack = new DownKeyStack();

  public void keyPressed(KeyEvent e) {
    keyDownStack.down(new KeyName(e));
    onKeyDown();
  }

  public void keyReleased(KeyEvent e) {
    KeyName upKeyName = new KeyName(e);
    onKeyUp(upKeyName);
    keyDownStack.up(upKeyName);
  }

  private void updateScreen() {
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    paint2G(g2);
  }


  private final List<Circle> circleList = new ArrayList<>();
  private final List<Line> lineList = new ArrayList<>();

  {
    Circle c11 = new Circle(0, 0, 0.1, 1);
    Circle c12 = new Circle(1, 0, 0.1, 1);
    Circle c13 = new Circle(2, 0, 0.1, 1);
    Circle c14 = new Circle(3, 0, 0.1, 1);
    Circle c21 = new Circle(0, 1, 0.1, 1);
    Circle c22 = new Circle(1, 1, 0.1, 1);
    Circle c23 = new Circle(2, 1, 0.1, 1);
    Circle c24 = new Circle(3, 1, 0.1, 1);
    Circle c31 = new Circle(0, 2, 0.1, 1);
    Circle c32 = new Circle(1, 2, 0.1, 1);
    Circle c33 = new Circle(2, 2, 0.1, 1);
    Circle c34 = new Circle(3.5, 2.5, 0.2, 1);

    circleList.add(c11);
    circleList.add(c12);
    circleList.add(c13);
    circleList.add(c14);
    circleList.add(c21);
    circleList.add(c22);
    circleList.add(c23);
    circleList.add(c24);
    circleList.add(c31);
    circleList.add(c32);
    circleList.add(c33);
    circleList.add(c34);

    Line l12_1 = new Line(c11, c12);
    Line l23_1 = new Line(c12, c13);
    Line l34_1 = new Line(c13, c14);
    Line l12_2 = new Line(c21, c22);
    Line l23_2 = new Line(c22, c23);
    Line l34_2 = new Line(c23, c24);
    Line l12_3 = new Line(c31, c32);
    Line l23_3 = new Line(c32, c33);
    Line l34_3 = new Line(c33, c34);
    Line l1_12 = new Line(c11, c21);
    Line l1_23 = new Line(c21, c31);
    Line l2_12 = new Line(c12, c22);
    Line l2_23 = new Line(c22, c32);
    Line l3_12 = new Line(c13, c23);
    Line l3_23 = new Line(c23, c33);
    Line l4_12 = new Line(c14, c24);
    Line l4_23 = new Line(c24, c34);

    lineList.add(l12_1);
    lineList.add(l23_1);
    lineList.add(l34_1);
    lineList.add(l12_2);
    lineList.add(l23_2);
    lineList.add(l34_2);
    lineList.add(l12_3);
    lineList.add(l23_3);
    lineList.add(l34_3);
    lineList.add(l1_12);
    lineList.add(l1_23);
    lineList.add(l2_12);
    lineList.add(l2_23);
    lineList.add(l3_12);
    lineList.add(l3_23);
    lineList.add(l4_12);
    lineList.add(l4_23);

    movingCalculus.register(lineList, circleList);
  }

  double scale = 100;
  double centerX = -2, centerY = -1;

  double centerX() {
    return mouseMode == MouseMode.MOVE ? (mouseDragX - mouseX) / scale + centerX : centerX;
  }

  double centerY() {
    return mouseMode == MouseMode.MOVE ? (mouseY - mouseDragY) / scale + centerY : centerY;
  }

  private void paint2G(Graphics2D g) {
    cleanScreen(g);
    drawCoors(g);
    drawLines(g);
    drawCircles(g);
    drawCursor(g);
  }

  private void cleanScreen(Graphics2D g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight());
  }

  private Vec2 toScreen(Vec2 real) {
    double SW = getWidth(), SH = getHeight();
    double dx = real.x + centerX(), dy = real.y + centerY();
    dx *= scale;
    dy *= scale;
    double X = SW / 2 + dx;
    double Y = SH / 2 - dy;
    return Vec2.xy(X, Y);
  }

  private void drawCircles(Graphics2D g) {
    double SW = getWidth(), SH = getHeight();

    for (Circle c : circleList) {
      Vec2 S = toScreen(c.center());
      double r = c.r;
      r *= scale;
      double R = r;
      if (R < 2) {
        R = 2;
      }
      if (R > SW) {
        R = SW;
      }
      if (S.x < 0) {
        continue;
      }
      if (S.x > SW) {
        continue;
      }
      if (S.y < 0) {
        continue;
      }
      if (S.y > SH) {
        continue;
      }

      int iX = (int) Math.round(S.x - R);
      int iY = (int) Math.round(S.y - R);
      int iW = (int) Math.round(2 * R);

      g.setColor(Color.BLUE);
      g.fillOval(iX, iY, iW, iW);
    }
  }

  private void drawLines(Graphics2D g) {
    for (Line line : lineList) {
      Vec2 C1 = toScreen(line.c1.center());
      Vec2 C2 = toScreen(line.c2.center());

      g.setColor(Color.GREEN);
      g.drawLine(C1.X(), C1.Y(), C2.X(), C2.Y());
    }
  }

  private void drawCoors(Graphics2D g) {
    double SW = getWidth(), SH = getHeight();

    double dx = centerX() * scale, dy = centerY() * scale;

    double X = SW / 2 + dx, Y = SH / 2 - dy;

    g.setColor(Color.DARK_GRAY);

    if (0 <= X && X <= SW) {
      int iX = (int) Math.round(X);
      g.drawLine(iX, 0, iX, getHeight());
    }

    if (0 <= Y && Y <= SH) {
      int iY = (int) Math.round(Y);
      g.drawLine(0, iY, getWidth(), iY);
    }
  }

  private void drawCursor(Graphics2D g0) {
    {
      Graphics2D g = (Graphics2D) g0.create();
      try {
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g.setStroke(dashed);

        if (mouseMode != MouseMode.MOVE) {
          g.setColor(Color.GRAY);
          paintCursor(g, mouseX, mouseY);
        }


        if (!mouseDownList.isEmpty()) {
          g.setColor(Color.CYAN);
          paintCursor(g, mouseDragX, mouseDragY);
        }


      } finally {
        g.dispose();
      }
    }

    if (!mouseDownList.isEmpty()) {
      Graphics2D g = (Graphics2D) g0.create();
      try {
        g.setColor(Color.GREEN);
        g.drawLine(mouseX, mouseY, mouseDragX, mouseDragY);
      } finally {
        g.dispose();
      }
    }
  }

  private void paintCursor(Graphics2D g, int x, int y) {
    if (0 <= x && x <= getWidth()) {

      if (0 <= y && y <= getHeight()) {
        g.drawLine(x, y, x, 0);
        g.drawLine(x, y, x, getHeight());
      } else {
        g.drawLine(x, 0, x, getHeight());
      }

    }

    if (0 <= y && y <= getHeight()) {

      if (0 <= x && x <= getWidth()) {
        g.drawLine(x, y, 0, y);
        g.drawLine(x, y, getWidth(), y);
      } else {
        g.drawLine(0, y, getWidth(), y);
      }

    }
  }


  private void changeScale(double k) {

    double k2 = 1 / k - 1;

    double AX = (mouseX - getWidth() / 2.0) / scale;
    double AY = (getHeight() / 2.0 - mouseY) / scale;

    centerX += AX * k2;
    centerY += AY * k2;
    scale *= k;

    updateScreen();
  }

  private MouseMode mouseMode = MouseMode.EMPTY;

  private void onMouseDown() {

//    System.out.println(mouseDownList.stream().map(MouseEvent::getButton).collect(Collectors.toList()));

    if (mouseDownList.size() == 1 && mouseDownList.get(0).getButton() == 2) {
      mouseMode = MouseMode.MOVE;
      return;
    }

    mouseMode = MouseMode.EMPTY;
  }

  private void onMouseUp() {
    if (mouseDownList.size() == 1 && mouseDownList.get(0).getButton() == 2 && mouseMode == MouseMode.MOVE) {
//      System.out.println("Commit MOVE (" + (mouseDragX - mouseX) + ", " + (mouseDragY - mouseY) + ")");
      commitMouseMove();
      mouseMode = MouseMode.EMPTY;
      return;
    }

    mouseMode = MouseMode.EMPTY;
  }

  private void commitMouseMove() {
    centerX += (mouseDragX - mouseX) / scale;
    centerY += (mouseY - mouseDragY) / scale;
  }

  private void onKeyDown() {

  }

  private void onKeyUp(KeyName upKeyName) {

    if (keyDownStack.does(upKeyName, "Simple_C")) {
      command_ToCenter();
      return;
    }

    System.out.println("UP: " + upKeyName + " :: " + keyDownStack);
  }

  private void command_ToCenter() {
    if (circleList.isEmpty()) {
      return;
    }
    double totalMass = circleList.stream().mapToDouble(c -> c.m).sum();
    double ccx = circleList.stream().mapToDouble(c -> c.x * c.m).sum() / totalMass;
    double ccy = circleList.stream().mapToDouble(c -> c.y * c.m).sum() / totalMass;

    centerX = -ccx;
    centerY = -ccy;

    double vx = circleList.stream().mapToDouble(c -> c.vx * c.m).sum() / totalMass;
    double vy = circleList.stream().mapToDouble(c -> c.vy * c.m).sum() / totalMass;
    for (Circle c : circleList) {
      c.vx -= vx;
      c.vy -= vy;
    }

    updateScreen();
  }
}
