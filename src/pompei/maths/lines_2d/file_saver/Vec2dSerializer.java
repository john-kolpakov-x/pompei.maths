package pompei.maths.lines_2d.file_saver;

import pompei.maths.lines_2d.model.Vec2d;
import pompei.maths.lines_2d.model.ViewVec2d;
import pompei.maths.lines_2d.model.WorldVec2d;

public abstract class Vec2dSerializer<T extends Vec2d> implements ObjectSerializer<T> {

  @Override
  public String toStr(T vec2d) {
    return vec2d.x + " " + vec2d.y;
  }

  abstract protected T newInstance();

  @Override
  public T fromStr(String str) {
    String[] split = str.trim().split("\\s+");
    T ret = newInstance();
    ret.x = Double.parseDouble(split[0]);
    ret.y = Double.parseDouble(split[1]);
    return ret;
  }

  public static World newWorld() {
    return new World();
  }

  public static class World extends Vec2dSerializer<WorldVec2d> {
    public World() {}

    @Override
    protected WorldVec2d newInstance() {
      return new WorldVec2d();
    }
  }

  public static View newView() {
    return new View();
  }

  public static class View extends Vec2dSerializer<ViewVec2d> {
    private View() {}

    @Override
    protected ViewVec2d newInstance() {
      return new ViewVec2d();
    }
  }

}
