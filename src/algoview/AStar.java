package algoview;

import java.util.*;

public class AStar {

    private Cell celulas[][];
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private double posEndX;
    private double posEndY;

    private ArrayList<Cell> queue = new ArrayList<>();

    public AStar(Cell[][] celulas) {
        this.celulas = celulas;
    }

    public void setCelulas(Cell[][] celulas) {
        this.celulas = celulas;
    }

    public void getVizinhos(int x, int y){

        try{
            Cell celulaCima = celulas[y-1][x];

            if(!celulas[y-1][x].isParedeBaixo() && !celulas[y][x].isParedeCima() && !celulas[y-1][x].isCorrectPath()){
                queue.add(celulaCima);
            }
        }catch (ArrayIndexOutOfBoundsException ex){

        }

        try{
            Cell celulaEsq = celulas[y][x-1];

            if(!celulas[y][x-1].isParedeDir() && !celulas[y][x].isParedeEsq() && !celulas[y][x-1].isCorrectPath()){
                queue.add(celulaEsq);
            }
        }catch (ArrayIndexOutOfBoundsException ex){

        }

        try{
            Cell celulaBaixo = celulas[y+1][x];

            if(!celulas[y+1][x].isParedeCima() && !celulas[y][x].isParedeBaixo() && !celulas[y+1][x].isCorrectPath()){
                queue.add(celulaBaixo);
            }
        }catch (ArrayIndexOutOfBoundsException ex){

        }
        try{
            Cell celulaDir = celulas[y][x+1];

            if(!celulas[y][x+1].isParedeEsq() && !celulas[y][x].isParedeDir() && !celulas[y][x+1].isCorrectPath()){
                queue.add(celulaDir);
            }
        }catch (ArrayIndexOutOfBoundsException ex){

        }

        sortQueue();

    }

    private void sortQueue(){
        boolean sorted = false;
        while (!sorted){
            sorted = true;
            for (int i = 0; i < queue.size()-1; i++){
                double distA = Math.sqrt((queue.get(i).getX() - posEndX) * (queue.get(i).getX() - posEndX) + (queue.get(i).getY() - posEndY) * (queue.get(i).getY() - posEndY));
                double distB = Math.sqrt((queue.get(i+1).getX() - posEndX) * (queue.get(i+1).getX() - posEndX) + (queue.get(i+1).getY() - posEndY) * (queue.get(i+1).getY() - posEndY));
                if(distA > distB){
                    Cell temp = queue.get(i);
                    queue.set(i, queue.get(i+1));
                    queue.set(i+1, temp);
                    sorted = false;
                }
            }
        }
    }

    public Cell[][] startMazeSolver(){
        if(!(startX == endX && startY == endY)) {
            celulas[startY][startX].setCorrectPath(true);

            getVizinhos(startX, startY);

            startX = queue.get(0).getIndexX();
            startY = queue.get(0).getIndexY();
            queue.remove(0);
            sortQueue();

        }
        return celulas;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public void reset() {
        queue.clear();
    }

    public double getPosEndX() {
        return posEndX;
    }

    public void setPosEndX(double posEndX) {
        this.posEndX = posEndX;
    }

    public double getPosEndY() {
        return posEndY;
    }

    public void setPosEndY(double posEndY) {
        this.posEndY = posEndY;
    }
}
