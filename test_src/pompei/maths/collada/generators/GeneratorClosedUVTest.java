package pompei.maths.collada.generators;

import org.testng.annotations.Test;
import pompei.maths.collada.core.Collada;
import pompei.maths.collada.core.Geometry;

import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GeneratorClosedUVTest {

  @Test
  public void append() throws Exception {

    Collada collada = new Collada();
    Geometry tor = collada.upsertGeometry("Tor-mesh");
    collada.upsertNode("Tor", tor);

    TorDomainUV torDomain = new TorDomainUV();
    TorSurface torSurface = new TorSurface();

    torDomain.Nu = 320;
    torDomain.Nv = 160;

    //
    //
    GeneratorClosedUV.append(tor, torDomain, torSurface, false);
    //
    //

    Path pathToFile = Paths.get(System.getProperty("user.home") + "/tmp/gen_tor.dae");

    try (PrintStream printStream = new PrintStream(pathToFile.toFile(), "UTF-8")) {
      collada.printTo(printStream);
    }
  }
}
