package pompei.maths.fast_file_work;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import pompei.maths.utils.RND;

public class PrepareHtmlWithLargeJs {
  public static void main(String[] args) throws Exception {
    new PrepareHtmlWithLargeJs().exec();
  }

  String outDir = "/home/pompei/IdeaProjects/cache-js/src/main/webapp";

  private final List<String> jsNameList = new ArrayList<>();

  private void exec() throws Exception {
    for (int i = 1; i <= 100; i++) {
      createJs(i);
    }

    createHtml();
  }


  private void createJs(int fileNumber) throws Exception {
    File file = new File(outDir + "/asd" + fileNumber + ".js");
    jsNameList.add(file.getName());

    try (PrintStream pr = new PrintStream(file, "UTF-8")) {

      pr.println("/*");
      for (int i = 0; i < 1000; i++) {
        pr.println(RND.str(500));
      }
      pr.println("*/");

      pr.println("console.log('JS: Loaded " + file.getName() + "')");
    }
  }

  private void createHtml() throws Exception {
    File file = new File(outDir + "/asd.html");

    try (PrintStream pr = new PrintStream(file, "UTF-8")) {

      pr.println("<html>");
      pr.println("  <head>");
      pr.println("    <title>Asd tester</title>");
      for (String name : jsNameList) {
        pr.println("    <script type=\"text/javascript\" src='" + name + "'></script>");
      }
      pr.println("  </head>");
      pr.println("  <body>");
      pr.println("    Hello from ASD <span id='asd123'>loading...</span>");
      pr.println("    <script>");
      pr.println("      console.log('File OK');");
      pr.println("      document.getElementById('asd123').innerHTML = '' + new Date();");
      pr.println("    </script>");
      pr.println("  </body>");
      pr.println("</html>");

    }
  }
}
