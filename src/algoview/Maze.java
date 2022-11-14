package algoview;

import javax.swing.*;
import java.awt.*;

public class Maze extends JPanel {

    public Maze(){
        setPreferredSize(new Dimension(1000, 600));
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
    }

}
