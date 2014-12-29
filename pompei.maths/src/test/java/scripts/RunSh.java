package scripts;

import java.io.InputStream;
import java.io.OutputStream;

public class RunSh {
  public static void main(String[] args) throws Exception {
    Runtime r = Runtime.getRuntime();
    Process p = r.exec("sh scripts/images_to_avi.sh build");
    
    Thread errT = assign(p.getErrorStream(), System.err);
    Thread outT = assign(p.getInputStream(), System.out);
    
    errT.join();
    outT.join();
    
    System.out.println("OK exit code = " + p.exitValue());
  }
  
  private static Thread assign(final InputStream in, final OutputStream out) {
    Thread ret = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          byte buf[] = new byte[1024 * 8];
          while (true) {
            int count = in.read(buf);
            if (count < 0) return;
            out.write(buf, 0, count);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    
    ret.start();
    
    return ret;
  }
}
