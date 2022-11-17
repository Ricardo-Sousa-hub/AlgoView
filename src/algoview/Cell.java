package algoview;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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
    }

    private Color color;
    private Shape cell;
    private int hight;
    private int width;
    private boolean paredeEsq;
    private boolean paredeDir;
    private boolean paredeBaixo;
    private boolean paredeCima;

    public void drawCell(Graphics2D g2){
        cell = new Rectangle2D.Double(x, y, width, height);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3.0f));
        g2.draw(cell);
        g2.setColor(color);
        g2.fill(cell);
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

    public void setHight(int hight) {
        this.hight = hight;
    }


    public void setWidth(int width) {
        this.width = width;
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

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public boolean contains(Point2D p) {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return false;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return null;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return null;
    }
}
