package pompei.maths.compress;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.fest.assertions.Assertions.assertThat;

public class Bzip2Compress {
  public static void main(String[] args) throws Exception {
    new Bzip2Compress().run();
  }

  private void run() throws Exception {
    InputStream inputStream = getClass().getResourceAsStream("ViewPanel.java.txt");

    byte[] sourceBytes = inputStream.readAllBytes();

    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    try (BZip2CompressorOutputStream out = new BZip2CompressorOutputStream(bOut)) {
      out.write(sourceBytes);
    }

    byte[] compressedBytes = bOut.toByteArray();

    System.out.println("sourceBytes.len = " + sourceBytes.length + ", compressedBytes.len = " + compressedBytes.length);

    ByteArrayInputStream inBytes = new ByteArrayInputStream(compressedBytes);

    BZip2CompressorInputStream in = new BZip2CompressorInputStream(inBytes);

    byte[] uncompressedBytes = in.readAllBytes();

    assertThat(uncompressedBytes).isEqualTo(sourceBytes);

  }
}
