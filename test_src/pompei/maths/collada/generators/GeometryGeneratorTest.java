package pompei.maths.collada.generators;

import org.testng.annotations.Test;
import pompei.maths.collada.core.Collada;
import pompei.maths.collada.core.Geometry;
import pompei.maths.collada.core.Material;

import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GeometryGeneratorTest {

  @Test
  public void append() throws Exception {

    Collada collada = new Collada();
    Geometry tor = collada.upsertGeometry("Tor-mesh");
    collada.upsertNode("Tor", tor);

    tor.material = collada.upsertMaterial("White");

    TorRectDomainUV torDomain = new TorRectDomainUV();
    TorSurface torSurface = new TorSurface();

    torDomain.Nu = 32;
    torDomain.Nv = 16;

    //
    //
    GeometryGenerator.append(tor, torDomain, torSurface, false, false);
    //
    //

    Path pathToFile = Paths.get(System.getProperty("user.home") + "/tmp/gen_tor.dae");

    try (PrintStream printStream = new PrintStream(pathToFile.toFile(), "UTF-8")) {
      collada.printTo(printStream);
    }
  }
}
