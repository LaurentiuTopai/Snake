import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {
    static final int screen_width = 1000;
    static final int screen_height = 800;
    static final int unitSize = 15;
    /// units pt size sarpe si fruncte
    static final int game_units = (screen_width * screen_height) / unitSize;
    static final int Delay = 80;
    final int[] x = new int[game_units];
    /// coordonate pt x body-parts
    final int[] y = new int[game_units];
    /// coordonate pt y body-parts
    int bodyParts = 3;
    int nr_fructeMancate;
    int Fructx;
    int Fructy;
    int direction = 3;
    int nr_fructe = 0;
    boolean start = false;
    Timer timer;
    Random random;

    Panel() {
        random = new Random();
        this.setPreferredSize(new Dimension(screen_width, screen_height)); ///Size Panel
        this.setBackground(new Color(45, 145, 72));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newFruct();
        start = true;
        timer = new Timer(Delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (start) {
            g.setColor(Color.red);
            g.fillOval(Fructx, Fructy, unitSize, unitSize);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("Score: "+nr_fructe, screen_width/2, screen_height/2);

        } else {
            gameOver(g);
        }

    }
    public void Fruct() {
        if (x[0] == Fructx && y[0] == Fructy) {
            bodyParts++;
            nr_fructe++;
            newFruct();
        }
    }
    public void newFruct() {
        boolean overlap = true;
        while (overlap) {

            Fructx = random.nextInt(screen_width / unitSize) * unitSize;
            Fructy = random.nextInt(screen_height / unitSize) * unitSize;

            overlap = false;
            for (int i = 0; i < bodyParts; i++) {
                if (x[i] == Fructx && y[i] == Fructy) {
                    overlap = true;
                    break;
                }
            }
        }
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 0:
                y[0] = y[0] - unitSize;///sus
                break;
            case 1:
                y[0] = y[0] + unitSize; ///jos
                break;
            case 2:
                x[0] = x[0] - unitSize; ///stanga
                break;
            case 3:
                x[0] = x[0] + unitSize; ///dreapta
                break;
        }
    }


    public void checkCollisions() {
        ///Cap atinge corp
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                start = false;
            }
        }
        ///Verifica daca capul atinge borderul
        if (x[0] < 0)
            x[0] = screen_width-unitSize;
        if (x[0] >= screen_width)
            x[0] = 0;
        if (y[0] < 0)
            y[0] = screen_height-unitSize;
        if (y[0] > screen_height)
            y[0] = 0;

        if (start == false)
            timer.stop();
    }

    public void gameOver(Graphics g) {
        String message = "Game Over! score:" + nr_fructe;
        g.setColor(Color.white);
        g.drawString(message, screen_width / 2 - 50, screen_height / 2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (start) {
            move();
            Fruct();
            checkCollisions();
        }
        repaint();
    }

    class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP && direction != 1) {
                direction=0;
            } else if (key == KeyEvent.VK_DOWN && direction != 0) {
                direction = 1;
            } else if (key == KeyEvent.VK_LEFT && direction!=3) {
                direction = 2;

            } else if (key == KeyEvent.VK_RIGHT && direction!=2) {
                direction = 3;
            }
        }
    }
}