package algoview;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Maze extends JPanel {

    private final int CELL_SIZE = 25;
    private final int qtdCellX = 1000/CELL_SIZE;
    private final int qtdCellY = 600/CELL_SIZE;

    private Cell[][] celulas = new Cell[qtdCellY][qtdCellX];

    ArrayList<Cell> stack = new ArrayList<>();
    public Maze(){
        setPreferredSize(new Dimension(1001, 601));
        setBackground(Color.WHITE);

        preencherLbirinto();
    }

    private void preencherLbirinto(){
        int posX = 0;
        int posY = 0;
        for(int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                Cell cell = new Cell(posX, posY, CELL_SIZE, CELL_SIZE);

                celulas[y][x] = cell;

                posX += CELL_SIZE;
            }
            posY += CELL_SIZE;
            posX = 0;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        buildBoard(g2);

    }

    private void buildBoard(Graphics2D g2){

        for(int y = 0; y < qtdCellY; y++) {
            for (int x = 0; x < qtdCellX; x++) {
                celulas[y][x].drawCell(g2);
            }
        }

    }

    private boolean celulasPorVisitar(){
        for (Cell[] c: celulas) {
            for(Cell c1: c){
                if(!c1.isVisitada()){
                    return true;
                }
            }
        }
        return false;
    }



    public void gerarLabirinto(){

        for(int y = 0; y < qtdCellY; y++){
            int run_start = 0;
            for (int x = 0; x < qtdCellX; x++){
                Random r = new Random();
                if(y > 0 && (x+1 == qtdCellX || r.nextInt(2) == 0)){
                    int cell = run_start + r.nextInt(x-run_start+1);
                    celulas[y][cell].setVisitada(true);
                    celulas[y][cell].setParedeCima(false);
                    celulas[y-1][cell].setVisitada(true);
                    celulas[y-1][cell].setParedeBaixo(false);
                    run_start = x + 1;
                }
                else if(x+1 < qtdCellX)
                {
                    celulas[y][x].setVisitada(true);
                    celulas[y][x].setParedeDir(false);
                    celulas[y][x+1].setVisitada(true);
                    celulas[y][x+1].setParedeEsq(false);
                }
            }
        }

        repaint();

    }

    public int[] getIndex(Cell cell){
        int arr[] = new int[2];
        for (int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                if(celulas[y][x] == cell){
                    arr[0] = y;
                    arr[1] = x;
                }
            }
        }
        return  arr;
    }

}
