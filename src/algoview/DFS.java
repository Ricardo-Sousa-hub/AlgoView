package algoview;

import java.util.ArrayList;

public class DFS {
    private Cell celulas[][];
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    private ArrayList<Cell> stack = new ArrayList<>();
    private ArrayList<Cell> visited = new ArrayList<>();

    public DFS(Cell[][] celulas) {
        this.celulas = celulas;
    }

    public Cell[][] getCelulas() {
        return celulas;
    }

    public void setCelulas(Cell[][] celulas) {
        this.celulas = celulas;
    }

    public void getVizinhos(int x, int y){

        try{
            Cell celulaCima = celulas[y-1][x];

            if(!celulas[y-1][x].isParedeBaixo() && !celulas[y][x].isParedeCima() && !celulas[y-1][x].isCorrectPath()){
                stack.add(0,celulaCima);
            }
        }catch (ArrayIndexOutOfBoundsException ex){

        }

        try{
            Cell celulaEsq = celulas[y][x-1];

            if(!celulas[y][x-1].isParedeDir() && !celulas[y][x].isParedeEsq() && !celulas[y][x-1].isCorrectPath()){
                stack.add(0, celulaEsq);
            }
        }catch (ArrayIndexOutOfBoundsException ex){

        }

        try{
            Cell celulaBaixo = celulas[y+1][x];

            if(!celulas[y+1][x].isParedeCima() && !celulas[y][x].isParedeBaixo() && !celulas[y+1][x].isCorrectPath()){
                stack.add(0,celulaBaixo);
            }
        }catch (ArrayIndexOutOfBoundsException ex){

        }
        try{
            Cell celulaDir = celulas[y][x+1];

            if(!celulas[y][x+1].isParedeEsq() && !celulas[y][x].isParedeDir() && !celulas[y][x+1].isCorrectPath()){
                stack.add(0, celulaDir);
            }
        }catch (ArrayIndexOutOfBoundsException ex){

        }
    }

    public void reset(){
        visited.clear();
        stack.clear();
    }

    public Cell[][] startMazeSolver(){
        if(!(startX == endX && startY == endY)) {
            visited.add(celulas[startY][startX]);
            celulas[startY][startX].setCorrectPath(true);
            getVizinhos(startX, startY);

            startX = stack.get(0).getIndexX();
            startY = stack.get(0).getIndexY();
            stack.remove(0);
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
}
