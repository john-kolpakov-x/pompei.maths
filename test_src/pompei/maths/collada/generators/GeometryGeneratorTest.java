package pompei.maths.collada.generators;

import org.testng.annotations.Test;
import pompei.maths.collada.core.Collada;
import pompei.maths.collada.core.Geometry;

import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

public class GeometryGeneratorTest {

  @Test
  public void append() throws Exception {

    Collada collada = new Collada();
    Geometry tor = collada.upsertGeometry("Tor-mesh");


    tor.material = collada.upsertMaterial("Boom");

    collada.upsertNode("Tor", tor).material = tor.material;

    TorRectDomainUV torDomain = new TorRectDomainUV();
    TorSurface torSurface = new TorSurface();

    torDomain.Nu = 320;
    torDomain.Nv = 160;

    //
    //
    GeometryGenerator.append(tor, torDomain, torSurface, false, false);
    //
    //

    Path pathToFile = Paths.get(
      System.getProperty("user.home") + "/tmp/gen_tor_with_material-" + torDomain.Nu + "x" + torDomain.Nv + ".dae"
    );

    try (PrintStream printStream = new PrintStream(pathToFile.toFile(), UTF_8)) {
      collada.printTo(printStream);
    }
  }
}
