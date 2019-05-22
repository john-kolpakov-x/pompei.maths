package pompei.maths.lines_2d.file_saver;

import pompei.maths.lines_2d.model.Rect2d;
import pompei.maths.lines_2d.model.ViewRect2d;
import pompei.maths.lines_2d.model.WorldRect2d;

public abstract class Rect2dSerializer<T extends Rect2d> implements ObjectSerializer<T> {

  @Override
  public String toStr(T value) {
    return value.x + " " + value.y + " " + value.width + " " + value.height;
  }

  abstract protected T newInstance();

  @Override
  public T fromStr(String str) {
    String[] split = str.trim().split("\\s+");
    T ret = newInstance();
    ret.x = Double.parseDouble(split[0]);
    ret.y = Double.parseDouble(split[1]);
    ret.width = Double.parseDouble(split[2]);
    ret.height = Double.parseDouble(split[3]);
    return ret;
  }

  public static World newWorld() {
    return new World();
  }

  public static class World extends Rect2dSerializer<WorldRect2d> {
    public World() {}

    @Override
    protected WorldRect2d newInstance() {
      return new WorldRect2d();
    }
  }

  public static View newView() {
    return new View();
  }

  public static class View extends Rect2dSerializer<ViewRect2d> {
    private View() {}

    @Override
    protected ViewRect2d newInstance() {
      return new ViewRect2d();
    }
  }

}
