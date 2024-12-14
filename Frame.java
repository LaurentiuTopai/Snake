import javax.swing.*;

public class Frame extends JFrame {

    Frame()
    {
        this.add(new Panel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

}
