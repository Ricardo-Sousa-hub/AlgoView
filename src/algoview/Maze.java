package algoview;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Maze extends JPanel {

    private final int CELL_SIZE = 25;
    private final int qtdCellX = 1000/CELL_SIZE;
    private final int qtdCellY = 600/CELL_SIZE;

    private Cell[][] celulas = new Cell[qtdCellY][qtdCellX];

    ArrayList<Cell> stack = new ArrayList<>();
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
        for(int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                Cell cell = new Cell(posX, posY, CELL_SIZE, CELL_SIZE);
                cell.drawCell(g2);

                celulas[y][x] = cell;

                posX += CELL_SIZE;
            }
            posY += CELL_SIZE;
            posX = 0;
        }

        //add vizinhos
        for(int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){

                if(y > 0){ //parede cima
                    celulas[y][x].addItemToVizinhos(celulas[y-1][x], 0);
                }
                if(x < qtdCellX-1){ //parede direita
                    celulas[y][x].addItemToVizinhos(celulas[y][x+1], 1);
                }
                if(y < qtdCellY-1){ //parede baixo
                    celulas[y][x].addItemToVizinhos(celulas[y+1][x], 2);

                }
                if(x > 0){ //parede esquerdo
                    celulas[y][x].addItemToVizinhos(celulas[y][x], 3);
                }
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

        Cell currentCell = celulas[0][0];
        currentCell.setVisitada(true);
        int stackIndex = 1;

        while (celulasPorVisitar()){
            if(currentCell.temVizinhosPorVisitar()){
                stack.add(currentCell);
                stackIndex++;
                Cell chosenCell = currentCell.randomVizinho();
                chosenCell.removerParedeEntreCelulas(currentCell, chosenCell);
                celulas[getIndex(currentCell)[0]][getIndex(currentCell)[1]] = currentCell;
                currentCell = chosenCell;
                currentCell.setVisitada(true);
                celulas[getIndex(currentCell)[0]][getIndex(currentCell)[1]] = currentCell;
            }
            else if(!stack.isEmpty()){
                currentCell = stack.get(stackIndex);
                stackIndex--;
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
