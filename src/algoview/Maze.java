package algoview;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Maze extends JPanel {

    private final int CELL_SIZE = 25;
    private final int qtdCellX = 1000/CELL_SIZE;
    private final int qtdCellY = 600/CELL_SIZE;

    private Cell[][] celulas = new Cell[qtdCellY][qtdCellX];

    ArrayList<Cell> stack = new ArrayList<>();
    private MazeCreator mazeCreator;
    private boolean isRunning = false;
    private boolean hasStartingPoint = false;
    public Maze(){
        setPreferredSize(new Dimension(1001, 601));
        setBackground(Color.WHITE);
        preencherLbirinto();
        mazeCreator = new MazeCreator(celulas, qtdCellX);
    }

    public void preencherLbirinto(){
        hasStartingPoint = false;
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
        repaint();
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

    public boolean celulasVisitadas(){
        for (Cell[] c: celulas) {
            for(Cell c1: c){
                if(!c1.isVisitada()){
                    return false;
                }
            }
        }
        return true;
    }



    public void gerarLabirinto(){
        mazeCreator.setRunning(true);
        mazeCreator.setRun_start(0);
        mazeCreator.reset(celulas);
        Timer gerarLabirinto = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(celulasVisitadas() || !isRunning){
                    mazeCreator.setRunning(false);
                    isRunning = false;
                    ((Timer)e.getSource()).stop();
                }
                else
                {
                    if(isRunning){
                        celulas = mazeCreator.createOnlyOneCell();
                    }
                }
                repaint();
            }
        });
        gerarLabirinto.start();

        //Codigo anterior
        /*for(int y = 0; y < qtdCellY; y++){
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

        repaint();*/

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

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isHasStartingPoint() {
        return hasStartingPoint;
    }

    public void setHasStartingPoint(boolean hasStartingPoint) {
        this.hasStartingPoint = hasStartingPoint;
    }

    public void selecionarCelulaStart(int posX, int posY){
        for (int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                if(celulas[y][x].contains(posY, posX)){
                    celulas[x][y].setStart(true);
                    repaint();
                }
            }
        }
    }
}
