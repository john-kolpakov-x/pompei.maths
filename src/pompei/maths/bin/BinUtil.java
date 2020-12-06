package pompei.maths.bin;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class BinUtil {

  public static final int SIZEOF_LONG = 8;
  public static final int SIZEOF_INT = 4;
  public static final int SIZEOF_SHORT = 2;

  @SuppressWarnings("PointlessArithmeticExpression")
  public static void writeInt(int value, byte[] buffer, int offset) {

    //@formatter:off
    buffer[offset + 0] = (byte) ((value & 0x000000FF) >> (8 * 0));
    buffer[offset + 1] = (byte) ((value & 0x0000FF00) >> (8 * 1));
    buffer[offset + 2] = (byte) ((value & 0x00FF0000) >> (8 * 2));
    buffer[offset + 3] = (byte) ((value & 0xFF000000) >> (8 * 3));
    //@formatter:on

  }

  @SuppressWarnings("PointlessArithmeticExpression")
  public static int readInt(byte[] buffer, int offset) {
    //@formatter:off
    return ((((int) buffer[ offset + 0 ]) & 0xFF) << (8 * 0))
         | ((((int) buffer[ offset + 1 ]) & 0xFF) << (8 * 1))
         | ((((int) buffer[ offset + 2 ]) & 0xFF) << (8 * 2))
         | ((((int) buffer[ offset + 3 ]) & 0xFF) << (8 * 3))
      ;
    //@formatter:on
  }

  @SuppressWarnings("PointlessArithmeticExpression")
  public static void writeLong(long value, byte[] buffer, int offset) {

    //@formatter:off
    buffer[offset +  0] = (byte) ((value & 0x00000000000000FFL) >> (8 * 0));
    buffer[offset +  1] = (byte) ((value & 0x000000000000FF00L) >> (8 * 1));
    buffer[offset +  2] = (byte) ((value & 0x0000000000FF0000L) >> (8 * 2));
    buffer[offset +  3] = (byte) ((value & 0x00000000FF000000L) >> (8 * 3));
    buffer[offset +  4] = (byte) ((value & 0x000000FF00000000L) >> (8 * 4));
    buffer[offset +  5] = (byte) ((value & 0x0000FF0000000000L) >> (8 * 5));
    buffer[offset +  6] = (byte) ((value & 0x00FF000000000000L) >> (8 * 6));
    buffer[offset +  7] = (byte) ((value & 0xFF00000000000000L) >> (8 * 7));
    //@formatter:on

  }

  @SuppressWarnings("PointlessArithmeticExpression")
  public static long readLong(byte[] buffer, int offset) {
    //@formatter:off
    return ((((long) buffer[ offset +  0 ]) & 0xFFL) << (8 * 0))
         | ((((long) buffer[ offset +  1 ]) & 0xFFL) << (8 * 1))
         | ((((long) buffer[ offset +  2 ]) & 0xFFL) << (8 * 2))
         | ((((long) buffer[ offset +  3 ]) & 0xFFL) << (8 * 3))
         | ((((long) buffer[ offset +  4 ]) & 0xFFL) << (8 * 4))
         | ((((long) buffer[ offset +  5 ]) & 0xFFL) << (8 * 5))
         | ((((long) buffer[ offset +  6 ]) & 0xFFL) << (8 * 6))
         | ((((long) buffer[ offset +  7 ]) & 0xFFL) << (8 * 7))
      ;
    //@formatter:on
  }

  public static void writeStr(String value, byte[] buffer, int offset) {
    if (value == null) {
      writeInt(-1, buffer, offset);
      return;
    }

    byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
    writeInt(bytes.length, buffer, offset);

    System.arraycopy(bytes, 0, buffer, offset + SIZEOF_INT, bytes.length);

  }

  public static int sizeOfStr(String value) {
    return SIZEOF_INT + (value == null ? 0 : value.getBytes(StandardCharsets.UTF_8).length);
  }

  public static String readStr(byte[] buffer, int offset) {
    int length = readInt(buffer, offset);
    if (length < 0) {
      return null;
    }
    return new String(buffer, offset + SIZEOF_INT, length, StandardCharsets.UTF_8);
  }

  @SuppressWarnings("PointlessArithmeticExpression")
  public static void writeShort(short value, byte[] buffer, int offset) {
    //@formatter:off
    buffer[offset + 0] = (byte) ((value & 0x00FF) >> (8 * 0));
    buffer[offset + 1] = (byte) ((value & 0xFF00) >> (8 * 1));
    //@formatter:on
  }

  @SuppressWarnings("PointlessArithmeticExpression")
  public static short readShort(byte[] buffer, int offset) {
    //@formatter:off
    return (short) (

      ((((int) buffer[ offset + 0 ]) & 0xFF) << (8 * 0))
        | ((((int) buffer[ offset + 1 ]) & 0xFF) << (8 * 1))

    );
    //@formatter:on
  }

  public static void writeBigInt(BigInteger value, byte[] buffer, int offset) {
    if (value == null) {
      writeInt(-1, buffer, offset);
      return;
    }
    byte[] bytes = value.toByteArray();
    writeInt(bytes.length, buffer, offset);
    System.arraycopy(bytes, 0, buffer, offset + SIZEOF_INT, bytes.length);
  }

  public static BigInteger readBigInt(byte[] buffer, int offset) {
    int length = readInt(buffer, offset);
    if (length < 0) {
      return null;
    }
    byte[] bytes = new byte[length];
    System.arraycopy(buffer, offset + SIZEOF_INT, bytes, 0, bytes.length);
    return new BigInteger(bytes);
  }

  public static int sizeOfBigInt(BigInteger value) {
    return SIZEOF_INT + (value == null ? 0 : value.toByteArray().length);
  }

  public static int sizeOfBd(BigDecimal value) {
    if (value == null) {
      return SIZEOF_INT;
    }
    byte[] bytes = value.unscaledValue().toByteArray();
    return SIZEOF_INT * 2 + bytes.length;
  }

  public static void writeBd(BigDecimal value, byte[] buffer, int offset) {
    if (value == null) {
      writeInt(-1, buffer, offset);
      return;
    }

    byte[] bytes = value.unscaledValue().toByteArray();
    int scale = value.scale();

    writeInt(bytes.length, buffer, offset);
    writeInt(scale, buffer, offset + SIZEOF_INT);
    System.arraycopy(bytes, 0, buffer, offset + SIZEOF_INT * 2, bytes.length);
  }

  public static BigDecimal readBd(byte[] buffer, int offset) {
    int length = readInt(buffer, offset);
    if (length < 0) {
      return null;
    }
    int scale = readInt(buffer, offset + SIZEOF_INT);
    byte[] bytes = new byte[length];
    System.arraycopy(buffer, offset + SIZEOF_INT * 2, bytes, 0, length);
    BigInteger unscaledValue = new BigInteger(bytes);
    return new BigDecimal(unscaledValue, scale);
  }

}
