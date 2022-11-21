package algoview;

import java.util.ArrayList;

public class BFS {

    private Cell celulas[][];
    private int qtdCellX;
    private int qtdCellY;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private ArrayList<Cell> queue = new ArrayList<>();
    private ArrayList<Cell> visited = new ArrayList<>();

    public BFS(Cell[][] celulas, int qtdCellX, int qtdCellY) {
        this.celulas = celulas;
        this.qtdCellX = qtdCellX;
        this.qtdCellY = qtdCellY;
    }

    public void reset(){
        queue.clear();
        visited.clear();
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

    }

    public Cell[][] startMazeSolver(){
        if(!(startX == endX && startY == endY)) {
            celulas[startY][startX].setCorrectPath(true);
            visited.add(celulas[startY][startX]);

            getVizinhos(startX, startY);

            startX = queue.get(0).getIndexX();
            startY = queue.get(0).getIndexY();
            queue.remove(0);
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
