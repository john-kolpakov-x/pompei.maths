package pompei.maths.letters;

import javax.swing.JFrame;

public class LetterT {

  public static void main(String[] args) {

    LetterForm letterForm = new LetterForm();

    System.out.println(letterForm.main);

    JFrame frame = new JFrame();
    frame.setContentPane(letterForm.main);

    frame.setSize(800, 600);
    frame.setLocation(4000, 100);
    frame.setVisible(true);
  }

}
