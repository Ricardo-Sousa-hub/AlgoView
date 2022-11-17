package algoview;

import sun.font.FontFamily;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SplashScreen extends JFrame {

    static JFrame splashFrame;

    public static void main(String[] args) {

        splashFrame = new JFrame();
        splashFrame.setUndecorated(true); // Turn off title bar and borders
        splashFrame.setLayout(new BorderLayout()); // Configure a border layout to add a panel and a button

        JPanel panel = new SplashPanel();
        splashFrame.add(panel, BorderLayout.CENTER);

        splashFrame.pack();
        splashFrame.setLocationRelativeTo(null); // Set location to the center of screen

        splashFrame.setVisible(true); // Set visibility

        panel.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                createApplicationFrame();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

    }

    public static void createApplicationFrame() {
        // Dispose the splash screen frame
        JFrame main = new Main();
        centerWindow(main);
        splashFrame.dispose();
    }

    private static void centerWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}

class SplashPanel extends JPanel {

    private Image image = null;

    public SplashPanel() {

        URL url = getClass().getClassLoader().getResource("imagens/image1.jpg");
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setPreferredSize(new Dimension(600, 600));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(image, 0, 0, null);

        Stroke stroke = new BasicStroke(3.0f);

        g2.setStroke(stroke);
        g2.setPaint(Color.white);

        Font f = new Font("Arial", Font.BOLD, 24);

        g2.setFont(f);
        g2.drawString("AlgoView", (getWidth()-100)/2, getHeight()/2);
    }

}