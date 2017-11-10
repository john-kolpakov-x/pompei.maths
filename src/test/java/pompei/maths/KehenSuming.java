package pompei.maths;

import java.util.Random;

import static java.util.stream.LongStream.of;

public class KehenSuming {

  public static void main(String[] args) {

    long[] votes = new Random(42).longs(1000).toArray();

    double ave1 = of(votes) .average().getAsDouble();

    double ave2 = of(votes).asDoubleStream().average().getAsDouble();

    double ave3 = of(votes).asDoubleStream().parallel().average().getAsDouble();

    of(votes).asDoubleStream().sum();

    double ave4;
    {
      double sum = 0;
      int i = 1;
      for (long val : votes) {
        StringBuilder s = new StringBuilder("" + val);
        while (s.length() < 30) s.insert(0, ' ');
        StringBuilder I = new StringBuilder("" + i++);
        while (I.length() < 5) I.insert(0, ' ');
        System.out.println(s.toString() + I);
        sum += val;
      }
      ave4 = sum / votes.length;
    }

    double ave5;
    {
      double sum = 0, c = 0;
      for (long val : votes) {
        double y = val - c;
        double t = sum + y;
        c = (t - sum) - y;
        sum = t;
      }
      ave5 = sum / votes.length;
    }

    {
      Random rnd = new Random();
      for (int i = 0, c = votes.length; i < c; i++) {
        int j = rnd.nextInt(c);
        long tmp = votes[i];
        votes[i] = votes[j];
        votes[j] = tmp;
      }
    }

    double ave6;
    {
      double sum = 0, c = 0;
      for (long val : votes) {
        double y = val - c;
        double t = sum + y;
        c = (t - sum) - y;
        sum = t;
      }
      ave6 = sum / votes.length;
    }

    System.out.println("ave1 = " + ave1);
    System.out.println("ave2 = " + ave2);
    System.out.println("ave3 = " + ave3);
    System.out.println("ave4 = " + ave4);
    System.out.println("ave5 = " + ave5);
    System.out.println("ave6 = " + ave6);

    System.out.println("java.version = " + System.getProperty("java.version"));
  }

}
