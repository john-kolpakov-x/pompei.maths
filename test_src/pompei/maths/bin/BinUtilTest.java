package pompei.maths.bin;

import org.testng.annotations.Test;
import pompei.maths.TestUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

import static org.fest.assertions.Assertions.assertThat;
import static pompei.maths.TestUtil.rndStr;

public class BinUtilTest {

  Random rnd = new Random();

  @Test
  public void writeInt__readInt() {

    byte[] buffer = new byte[30];
    rnd.nextBytes(buffer);

    int value = rnd.nextInt();

    //
    //
    BinUtil.writeInt(value, buffer, 10);
    int actual = BinUtil.readInt(buffer, 10);
    //
    //

    assertThat(actual).isEqualTo(value);

  }

  @Test
  public void writeLong__readLong() {

    byte[] buffer = new byte[30];
    rnd.nextBytes(buffer);

    long value = rnd.nextLong();

    //
    //
    BinUtil.writeLong(value, buffer, 10);
    long actual = BinUtil.readLong(buffer, 10);
    //
    //

    assertThat(actual).isEqualTo(value);
  }

  @Test
  public void writeStr__readStr() {

    byte[] buffer = new byte[3000];
    rnd.nextBytes(buffer);

    String value = rndStr(rnd, 30 + rnd.nextInt(30));

    //
    //
    BinUtil.writeStr(value, buffer, 10);
    String actual = BinUtil.readStr(buffer, 10);
    //
    //

    assertThat(actual).isEqualTo(value);

  }

  @Test
  public void sizeOfStr() {

    String value = rndStr(rnd, 30 + rnd.nextInt(30));

    //
    //
    int size = BinUtil.sizeOfStr(value);
    //
    //

    byte[] buffer = new byte[size];
    rnd.nextBytes(buffer);

    BinUtil.writeStr(value, buffer, 0);
    String actual = BinUtil.readStr(buffer, 0);

    assertThat(actual).isEqualTo(value);

  }


  @Test
  public void sizeOfStr__null() {

    //
    //
    int size = BinUtil.sizeOfStr(null);
    //
    //

    byte[] buffer = new byte[size];
    rnd.nextBytes(buffer);

    BinUtil.writeStr(null, buffer, 0);
    String actual = BinUtil.readStr(buffer, 0);

    assertThat(actual).isNull();

  }

  @Test
  public void writeStr__readStr___withSpace() {

    byte[] buffer = new byte[3000];
    rnd.nextBytes(buffer);

    String value = rndStr(rnd, 30 + rnd.nextInt(30)) + "   " + rndStr(rnd, 10);

    //
    //
    BinUtil.writeStr(value, buffer, 10);
    String actual = BinUtil.readStr(buffer, 10);
    //
    //

    assertThat(actual).isEqualTo(value);

  }

  @Test
  public void writeStr__readStr___null() {

    byte[] buffer = new byte[30];
    rnd.nextBytes(buffer);


    //
    //
    BinUtil.writeStr(null, buffer, 10);
    String actual = BinUtil.readStr(buffer, 10);
    //
    //

    assertThat(actual).isNull();

  }

  @Test
  public void writeStr__readStr___empty() {

    byte[] buffer = new byte[30];
    rnd.nextBytes(buffer);

    //
    //
    BinUtil.writeStr("", buffer, 10);
    String actual = BinUtil.readStr(buffer, 10);
    //
    //

    assertThat(actual).isEmpty();

  }

  @Test
  public void writeShort__readShort() {

    byte[] buffer = new byte[30];
    rnd.nextBytes(buffer);

    short value = (short) rnd.nextInt();

    //
    //
    BinUtil.writeShort(value, buffer, 10);
    short actual = BinUtil.readShort(buffer, 10);
    //
    //

    assertThat(actual).isEqualTo(value);

  }

  @Test
  public void writeBigInt__readBigInt() {

    byte[] intBytes = new byte[30 + rnd.nextInt(30)];
    rnd.nextBytes(intBytes);

    BigInteger value = new BigInteger(intBytes);

    byte[] buffer = new byte[2000];
    rnd.nextBytes(buffer);

    //
    //
    BinUtil.writeBigInt(value, buffer, 10);
    BigInteger actual = BinUtil.readBigInt(buffer, 10);
    //
    //

    assertThat(actual).isEqualTo(value);

  }

  @Test
  public void sizeOfBigInt() {

    byte[] intBytes = new byte[30 + rnd.nextInt(30)];
    rnd.nextBytes(intBytes);

    BigInteger value = new BigInteger(intBytes);

    //
    //
    int size = BinUtil.sizeOfBigInt(value);
    //
    //

    byte[] buffer = new byte[size];
    rnd.nextBytes(buffer);

    BinUtil.writeBigInt(value, buffer, 0);
    BigInteger actual = BinUtil.readBigInt(buffer, 0);

    assertThat(actual).isEqualTo(value);
  }

  @Test
  public void writeBigInt__readBigInt___null() {

    byte[] intBytes = new byte[30 + rnd.nextInt(30)];
    rnd.nextBytes(intBytes);

    byte[] buffer = new byte[100];
    rnd.nextBytes(buffer);

    //
    //
    BinUtil.writeBigInt(null, buffer, 10);
    BigInteger actual = BinUtil.readBigInt(buffer, 10);
    //
    //

    assertThat(actual).isNull();

  }

  @Test
  public void sizeOfBigInt__null() {

    //
    //
    int size = BinUtil.sizeOfBigInt(null);
    //
    //

    assertThat(size).isNotEqualTo(0);

    byte[] buffer = new byte[size];
    rnd.nextBytes(buffer);

    BinUtil.writeBigInt(null, buffer, 0);
    BigInteger actual = BinUtil.readBigInt(buffer, 0);

    assertThat(actual).isNull();
  }


  @Test
  public void writeBd__readBd() {

    BigDecimal value = TestUtil.rndBd(rnd);

    byte[] buffer = new byte[2000];
    rnd.nextBytes(buffer);

    //
    //
    BinUtil.writeBd(value, buffer, 10);
    BigDecimal actual = BinUtil.readBd(buffer, 10);
    //
    //

    assertThat(actual).isEqualTo(value);

  }

  @Test
  public void sizeOfBd() {

    BigDecimal value = TestUtil.rndBd(rnd);

    //
    //
    int size = BinUtil.sizeOfBd(value);
    //
    //

    byte[] buffer = new byte[size];
    rnd.nextBytes(buffer);

    BinUtil.writeBd(value, buffer, 0);
    BigDecimal actual = BinUtil.readBd(buffer, 0);

    assertThat(actual).isEqualTo(value);
  }

  @Test
  public void writeBd__readBd___null() {

    byte[] buffer = new byte[100];
    rnd.nextBytes(buffer);

    //
    //
    BinUtil.writeBd(null, buffer, 10);
    BigDecimal actual = BinUtil.readBd(buffer, 10);
    //
    //

    assertThat(actual).isNull();

  }

  @Test
  public void sizeOfBd__null() {

    //
    //
    int size = BinUtil.sizeOfBd(null);
    //
    //

    assertThat(size).isNotEqualTo(0);

    byte[] buffer = new byte[size];
    rnd.nextBytes(buffer);

    BinUtil.writeBd(null, buffer, 0);
    BigDecimal actual = BinUtil.readBd(buffer, 0);

    assertThat(actual).isNull();
  }

}
