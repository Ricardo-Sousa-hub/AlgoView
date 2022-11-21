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

    ArrayList<Cell> stack = new ArrayList<>();
    private MazeCreator mazeCreator;
    private DFS dfs;
    private boolean isRunning;
    private boolean hasStartingPoint = false;
    private boolean hasEndPoint = false;

    private int startX;
    private int startY;
    private int endX;
    private int endY;
    public Maze(){
        setPreferredSize(new Dimension(1001, 601));
        setBackground(Color.WHITE);
        preencherLbirinto();
        mazeCreator = new MazeCreator(celulas, qtdCellX);
        dfs = new DFS(celulas, qtdCellX, qtdCellY);

        isRunning = false;
    }

    public void startSolver(){
        isRunning = true;
        dfs.setStartX(startX);
        dfs.setStartY(startY);
        dfs.setEndX(endX);
        dfs.setEndY(endY);

        Timer solverTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(celulasVisitadas() || !isRunning){
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
        solverTimer.start();
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

    public boolean celulasVisitadas(){
        for (int y = 0; y < qtdCellY; y++){
            for (int x = 0; x < qtdCellX; x++){
                if(!celulas[y][x].isCorrectPath()) return false;
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
}
