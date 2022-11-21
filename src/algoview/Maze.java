package algoview;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Maze extends JPanel implements Printable {

    private final int CELL_SIZE = 25;
    private final int qtdCellX = 1000/CELL_SIZE;
    private final int qtdCellY = 600/CELL_SIZE;

    private Cell[][] celulas = new Cell[qtdCellY][qtdCellX];
    private MazeCreator mazeCreator;
    private DFS dfs;
    private BFS bfs;
    private boolean isRunning;
    private boolean hasStartingPoint = false;
    private boolean hasEndPoint = false;

    private int startX = -1;
    private int startY = -1;
    private int endX = -1;
    private int endY = -1;
    private int delay;
    public Maze(){
        setPreferredSize(new Dimension(1001, 601));
        setBackground(Color.WHITE);
        preencherLbirinto();
        mazeCreator = new MazeCreator(celulas, qtdCellX);
        dfs = new DFS(celulas, qtdCellX, qtdCellY);
        bfs = new BFS(celulas, qtdCellX, qtdCellY);

        isRunning = false;
    }

    public void dfsAlgorithm(){
        isRunning = true;
        dfs.setStartX(startX);
        dfs.setStartY(startY);
        dfs.setEndX(endX);
        dfs.setEndY(endY);
        dfs.reset();

        Timer dfsTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(celulasCorrect() || !isRunning){
                    isRunning = false;
                    ((Timer)e.getSource()).stop();
                }
                else{
                    if(isRunning){
                        celulas = dfs.startMazeSolver();
                    }
                }
                repaint();
            }
        });
        dfsTimer.start();
    }

    public void bfsAlgorithm(){
        isRunning = true;
        bfs.setStartX(startX);
        bfs.setStartY(startY);
        bfs.setEndX(endX);
        bfs.setEndY(endY);
        bfs.reset();

        Timer bfsTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(celulasCorrect() || !isRunning){
                    isRunning = false;
                    ((Timer)e.getSource()).stop();
                }
                else{
                    if(isRunning){
                        celulas = bfs.startMazeSolver();
                    }
                }
                repaint();
            }
        });
        bfsTimer.start();
    }

    public void startSolver(String algoritmo){
        if(!(startX == -1 && startY == -1 && endX == -1 && endY == -1)) {
            switch (algoritmo) {
                case "DFS":
                    dfsAlgorithm();
                    break;
                case "BFS":
                    bfsAlgorithm();
                    break;
            }
        }

    }

    public void preencherLbirinto(){
        hasStartingPoint = false;
        hasEndPoint = false;
        int posX = 0;
        int posY = 0;
        for(int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                Cell cell = new Cell(posX, posY, CELL_SIZE, CELL_SIZE, x, y);

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

    public boolean celulasCorrect(){
        for (int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                if(!celulas[y][x].isCorrectPath()) return false;
            }
        }
        return true;
    }

    public boolean celulasVisitadas(){
        for (int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                if(!celulas[y][x].isVisitada()) return false;
            }
        }
        return true;
    }

    public void reset(){
        for (int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                celulas[y][x].setCorrectPath(false);
                celulas[y][x].setStart(false);
                celulas[y][x].setEnd(false);
            }
        }
        repaint();
    }

    public void gerarLabirinto(){
        mazeCreator.setRunning(true);
        mazeCreator.setRun_start(0);
        mazeCreator.reset(celulas);
        Timer gerarLabirinto = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mazeCreator.getStartCellY() == qtdCellY || !isRunning){
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
        dfs.setCelulas(celulas);
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

    public boolean isHasEndPoint() {
        return hasEndPoint;
    }

    public void setHasEndPoint(boolean hasEndPoint) {
        this.hasEndPoint = hasEndPoint;
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
                if(celulas[y][x].contains(posX, posY) && !celulas[y][x].isEnd()){
                    startX = x;
                    startY = y;
                    celulas[y][x].setStart(true);
                    repaint();
                }
            }
        }
    }

    public void removerCelulaStart(){
        for (int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                if(celulas[y][x].isStart()){
                    celulas[y][x].setStart(false);
                    repaint();
                }
            }
        }
    }

    public void selecionarCelulaEnd(int posX, int posY){
        for (int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                if(celulas[y][x].contains(posX, posY) && !celulas[y][x].isStart()){
                    celulas[y][x].setEnd(true);
                    endX = x;
                    endY = y;
                    repaint();
                }
            }
        }
    }

    public void removerCelulaEnd() {
        for (int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                if(celulas[y][x].isEnd()){
                    celulas[y][x].setEnd(false);
                    repaint();
                }
            }
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        switch (pageIndex){
            case 0:

                Image image = null;
                Graphics2D g2 = (Graphics2D) graphics;

                BufferedImage bufferedImage = new BufferedImage(1000, 600, BufferedImage.TYPE_INT_RGB);

                g2 = bufferedImage.createGraphics();

                //codigo da função drawBoard
                for(int y = 0; y < qtdCellY; y++) {
                    for (int x = 0; x < qtdCellX; x++) {
                        celulas[y][x].drawCell(g2);
                    }
                }

                //salvar labirinto para "imagens"
                String userDirectory = System.getProperty("user.dir");
                File file = new File(userDirectory+"/src/imagens/maze.png");
                try {
                    ImageIO.write(bufferedImage, "png", file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //imagens 2x mais pequena
                graphics.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth()/2, bufferedImage.getHeight()/2, null);

                break;
            default:
                return NO_SUCH_PAGE;
        }

        return PAGE_EXISTS;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
