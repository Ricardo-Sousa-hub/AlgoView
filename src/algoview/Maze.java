package algoview;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Maze extends JPanel {

    Cell[][] tabuleiro;
    int CELL_SIZE = 50;
    int qtdCellX = 1000/CELL_SIZE;
    int qtdCellY = 600/CELL_SIZE;

    public Maze(){
        setPreferredSize(new Dimension(1000, 600));
        setBackground(Color.WHITE);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        buildBoard(g2);
    }

    private void buildBoard(Graphics2D g2){
        int posX = 0;
        int posY = 0;
        System.out.println(posY);
        for(int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                Cell cell = new Cell(posX, posY, CELL_SIZE, CELL_SIZE);
                cell.drawCell(g2);
                posX += CELL_SIZE;
            }
            posY += CELL_SIZE;
            posX = 0;
        }
    }
}
