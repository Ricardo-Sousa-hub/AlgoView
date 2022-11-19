package algoview;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("ALL")
public class Cell extends Rectangle {

    public Cell(int x, int y, int width, int height){
        paredeEsq = true;
        paredeDir = true;
        paredeBaixo = true;
        paredeCima = true;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        color = Color.BLUE;
        visitada = false;
        start = false;
        end = false;
        vizinhos = new ArrayList<>(4);
        vizinhos.add(null);
        vizinhos.add(null);
        vizinhos.add(null);
        vizinhos.add(null);
    }

    private ArrayList<Cell> vizinhos;
    private Color color;
    private boolean paredeEsq;
    private boolean paredeDir;
    private boolean paredeBaixo;
    private boolean paredeCima;
    private boolean visitada;
    private boolean start;
    private boolean end;

    private void desenharInteriorCelula(Color color, Graphics2D g2){
        GeneralPath cell = new GeneralPath();
        cell.moveTo(x, y);
        cell.lineTo(x + width, y);
        cell.lineTo(x + width, y + width);
        cell.lineTo(x, y + width);
        cell.lineTo(x, y);
        cell.closePath();

        if(visitada){
            g2.setColor(Color.WHITE);
        }
        if(start){
            g2.setColor(Color.GREEN);
        }
        if(end){
            g2.setColor(Color.RED);
        }
        if(!visitada){
            g2.setColor(color);
        }

        g2.fill(cell);
        g2.draw(cell);
    }

    public void desenharParedesCelula(Color color, Graphics2D g2){
        g2.setColor(color);

        if(paredeCima){
            g2.drawLine(x, y, x+width, y);
        }

        if(paredeDir){
            g2.drawLine(x+width, y, x+width, y+height);
        }

        if(paredeBaixo){
            g2.drawLine(x+width, y+height, x, y+height);
        }

        if(paredeEsq){
            g2.drawLine(x, y+height, x, y);
        }

    }

    public void drawCell(Graphics2D g2){
        desenharInteriorCelula(color, g2);

        desenharParedesCelula(Color.BLACK, g2);
    }

    public void addItemToVizinhos(Cell vizinho, int pos)
    {
        vizinhos.add(pos, vizinho);
    }

    public Cell getVizinho(int i){
        return vizinhos.get(i);
    }

    public int vizinhosLenght(){
        return vizinhos.size();
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isVisitada() {
        return visitada;
    }

    public boolean temVizinhosPorVisitar(){
        for(Cell cell : vizinhos){
            if(cell != null && !cell.isVisitada()){
                return true;
            }
        }
        return false;
    }

    public Cell randomVizinho(){
        Random r = new Random();
        Cell vizinho = vizinhos.get(r.nextInt(vizinhos.size()));

        while (vizinho == null){
            vizinho = vizinhos.get(r.nextInt(vizinhos.size()));
        }

        return vizinho;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isParedeEsq() {
        return paredeEsq;
    }

    public void setParedeEsq(boolean paredeEsq) {
        this.paredeEsq = paredeEsq;
    }

    public boolean isParedeDir() {
        return paredeDir;
    }

    public void setParedeDir(boolean paredeDir) {
        this.paredeDir = paredeDir;
    }

    public boolean isParedeBaixo() {
        return paredeBaixo;
    }

    public void setParedeBaixo(boolean paredeBaixo) {
        this.paredeBaixo = paredeBaixo;
    }

    public boolean isParedeCima() {
        return paredeCima;
    }

    public void setParedeCima(boolean paredeCima) {
        this.paredeCima = paredeCima;
    }

    public void removerParedeEntreCelulas(Cell currentCell, Cell nextCell){
        if(currentCell.getX() == nextCell.getX()-25){
            currentCell.setParedeEsq(false);
            nextCell.setParedeDir(false);
        }
        if(currentCell.getX() == nextCell.getX()+25){
            currentCell.setParedeDir(false);
            currentCell.setParedeEsq(false);
        }
        if(currentCell.getY() == nextCell.getY()-25){
            currentCell.setParedeCima(false);
            nextCell.setParedeBaixo(false);
        }
        if(currentCell.getY() == nextCell.getY()+25){
            currentCell.setParedeBaixo(false);
            currentCell.setParedeCima(false);
        }
    }
}
