import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Splash implements Runnable {
    private JFrame fr;
    private JProgressBar pb;
    private int progress = 0;

    private Splash() {
        fr = new JFrame();
        fr.setSize(640, 510);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setUndecorated(true);

        MyJPanel p1 = new MyJPanel("images/splash.jpg");
        p1.setBounds(0, 0, 640, 480);
        p1.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED));
        p1.setLayout(null);

        JLabel l1 = new JLabel(new ImageIcon("images/progressW.gif"));
        l1.setBounds(0, 0, 250, 200);
        p1.add(l1);

        JPanel p2 = new JPanel();
        p2.setBounds(0, 480, 640, 30);
        p2.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED));
        p2.setLayout(null);

        pb = new JProgressBar();
        pb.setValue(progress);
        pb.setBounds(1, 1, 637, 28);
        pb.setStringPainted(true);
        p2.add(pb);

        fr.add(p1);
        fr.add(p2);
        fr.setVisible(true);

        Thread th = new Thread(this);
        th.start();
    }

    public void run() {
        while (progress <= 100) {
            progress++;
            pb.setValue(progress);
            try {
                Thread.sleep(15);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        fr.dispose();
        new Login();
    }

    public static void main(String[] args) {
        new Splash();
    }

    static class MyJPanel extends JPanel {
        private BufferedImage image;
        private int w, h;

        MyJPanel(String fname) {
            //reads the image
            try {
                image = ImageIO.read(new File(fname));
                w = image.getWidth();
                h = image.getHeight();
            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
        }

        public Dimension getPreferredSize() {
            return new Dimension(w, h);
        }

        //this will draw the image
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }
}
