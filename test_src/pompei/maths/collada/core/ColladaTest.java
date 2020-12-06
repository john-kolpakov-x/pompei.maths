package pompei.maths.collada.core;

import org.testng.annotations.Test;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ColladaTest {
  @Test
  public void generate_cude() throws Exception {
    Collada collada = new Collada();

    Geometry cube = collada.upsertGeometry("Cube-mesh", "Cube");
    cube.addPosition(0, 0, 0);//0
    cube.addPosition(1, 0, 0);//1
    cube.addPosition(1, 1, 0);//2
    cube.addPosition(0, 1, 0);//3

    cube.addPosition(0, 0, 1);//4
    cube.addPosition(1, 0, 1);//5
    cube.addPosition(1, 1, 1);//6
    cube.addPosition(0, 1, 1);//7

    cube.addNormal(+1, 0, 0);//0
    cube.addNormal(-1, 0, 0);//1
    cube.addNormal(0, +1, 0);//2
    cube.addNormal(0, -1, 0);//3
    cube.addNormal(0, 0, +1);//4
    cube.addNormal(0, 0, -1);//5

    cube.addTriangle(0, 2, 1, 5, 5, 5);
    cube.addTriangle(0, 3, 2, 5, 5, 5);

    cube.addTriangle(2, 3, 6, 0, 0, 0);
    cube.addTriangle(3, 7, 6, 0, 0, 0);

    cube.addTriangle(0, 1, 5, 1, 1, 1);
    cube.addTriangle(0, 5, 4, 1, 1, 1);

    cube.addTriangle(4, 5, 6, 4, 4, 4);
    cube.addTriangle(4, 6, 7, 4, 4, 4);

    cube.addTriangle(1, 2, 5, 0, 0, 0);
    cube.addTriangle(5, 2, 6, 0, 0, 0);

    cube.addTriangle(0, 4, 3, 1, 1, 1);
    cube.addTriangle(3, 4, 7, 1, 1, 1);

    collada.upsertNode("Cube", cube);

    Path pathToFile = Paths.get(System.getProperty("user.home") + "/tmp/gen_cube.dae.xml");

    try (PrintStream printStream = new PrintStream(pathToFile.toFile(), StandardCharsets.UTF_8)) {
      collada.printTo(printStream);
    }
  }
}
