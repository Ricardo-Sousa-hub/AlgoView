package algoview;

import java.util.Random;

public class MazeCreator {

    private Cell[][] celulas;
    private int qtdCellX;
    private int run_start;
    private int startCellX, startCellY;
    private boolean running;

    public MazeCreator(Cell[][] celulas, int qtdCellX){
        this.celulas = celulas;
        startCellX = 0;
        startCellY = 0;
        this.qtdCellX = qtdCellX;
    }

    public Cell[][] createOnlyOneCell(){
        if(running){
            Random r = new Random();
            if(startCellY > 0 && (startCellX+1 == qtdCellX || r.nextInt(2) == 0)){
                int cell = run_start + r.nextInt(startCellX-run_start+1);
                celulas[startCellY][cell].setVisitada(true);
                celulas[startCellY][cell].setParedeCima(false);
                celulas[startCellY-1][cell].setVisitada(true);
                celulas[startCellY-1][cell].setParedeBaixo(false);
                run_start = startCellX + 1;
            }
            else if(startCellX+1 < qtdCellX)
            {
                celulas[startCellY][startCellX].setVisitada(true);
                celulas[startCellY][startCellX].setParedeDir(false);
                celulas[startCellY][startCellX + 1].setVisitada(true);
                celulas[startCellY][startCellX + 1].setParedeEsq(false);
            }
            startCellX++;
            if(startCellX == qtdCellX){
                startCellY++;
                run_start = 0;
                startCellX = 0;
            }
        }
        return celulas;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getRun_start() {
        return run_start;
    }

    public void setRun_start(int run_start) {
        this.run_start = run_start;
    }

    public int getStartCellX() {
        return startCellX;
    }

    public void setStartCellX(int startCellX) {
        this.startCellX = startCellX;
    }

    public int getStartCellY() {
        return startCellY;
    }

    public void setStartCellY(int startCellY) {
        this.startCellY = startCellY;
    }

    public Cell[][] getCelulas() {
        return celulas;
    }

    public void setCelulas(Cell[][] celulas) {
        this.celulas = celulas;
    }
}
