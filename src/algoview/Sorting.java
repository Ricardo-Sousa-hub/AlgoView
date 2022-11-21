package algoview;

import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class Sorting extends JPanel  implements Printable {

    public int[] array = {268, 148, 218, 179, 469, 310, 421, 332, 66, 252, 387, 470, 144, 490, 392, 377, 273, 370, 71, 346, 337, 150, 181, 380, 99, 357, 385, 221, 170, 479, 457, 342, 424, 417, 360, 297, 161, 127, 148, 419, 287, 117, 217, 472, 76, 187, 238, 420, 477, 260};
    private boolean isRunning = false;

    private String sortAlgorithm;

    private BubbleSort bubbleSort;
    private SelectionSort selectionSort;
    private InsertionSort insertionSort;
    private QuickSort quickSort;
    private int delay;

    public Sorting(){
        setPreferredSize(new Dimension(1000, 600));

        setBackground(Color.WHITE);

        bubbleSort = new BubbleSort(array);
        selectionSort = new SelectionSort(array);
        insertionSort = new InsertionSort(array);
        quickSort = new QuickSort(array);
    }

    public void reset(){
        int[] array = {268, 148, 218, 179, 469, 310, 421, 332, 66, 252, 387, 470, 144, 490, 392, 377, 273, 370, 71, 346, 337, 150, 181, 380, 99, 357, 385, 221, 170, 479, 457, 342, 424, 417, 360, 297, 161, 127, 148, 419, 287, 117, 217, 472, 76, 187, 238, 420, 477, 260};

        this.array = array;

        // reset bubbleSort object
        bubbleSort.setCompareIndex(Integer.MAX_VALUE);
        bubbleSort.setArrayIndex(0);
        bubbleSort.setArray(this.array);

        // reset insertionSort object
        insertionSort.setCompareIndex(Integer.MAX_VALUE);
        insertionSort.setArrayIndex(Integer.MAX_VALUE);
        insertionSort.setArray(this.array);
        insertionSort.setStartOfIteration(false);

        // reset selectionSort
        selectionSort.setArrayIndex(0);
        selectionSort.setCompareIndex(1);
        selectionSort.setMinIndex(0);
        selectionSort.setFindMin(false);
        selectionSort.setArray(this.array);

        // reset quickSort object
        quickSort.setSP(-1);
        quickSort.push(0);
        quickSort.push(this.array.length-1);
        quickSort.setArrayIndex(Integer.MAX_VALUE);
        quickSort.setCompareIndex(Integer.MAX_VALUE);
        quickSort.setPartition(-1);
        quickSort.setHigh(this.array.length-1);
        quickSort.setIsPartioning(false);
        quickSort.setArray(array);

        isRunning = false;
        repaint();
    }

    public void setRunning(Boolean isRunning){
        this.isRunning = isRunning;
    }

    public Boolean getRunning(){
        return isRunning;
    }

    public void setDelay(int delay){
        this.delay = delay;
    }

    public void setsortAlgorithm(String sortAlgorithm){
        this.sortAlgorithm = sortAlgorithm;
    }

    public void animate() {
        if(sortAlgorithm.equals("Bubble Sort")){
            bubbleSort(delay);
        }
        else if(sortAlgorithm.equals("Selection Sort")){
            selectionSort(delay);
        }
        else if(sortAlgorithm.equals("Insertion Sort")){
            insertionSort(delay);
        }
        else if(sortAlgorithm.equals("Quick Sort")) {
            quickSort(delay);
        }
    }

    public boolean isSorted() {

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public void bubbleSort(int delay){
        bubbleSort.setCompareIndex(0);

        Timer bubbleTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isSorted() || !isRunning){
                    bubbleSort.setCompareIndex(Integer.MAX_VALUE);
                    isRunning = false;
                    ((Timer)e.getSource()).stop();
                }
                else{
                    if(isRunning){
                        array = bubbleSort.sortOnlyOneItem();
                    }
                }
                repaint();
            }
        });
        bubbleTimer.start();
    }

    public void selectionSort(int delay){
        selectionSort.setArrayIndex(0);
        selectionSort.setCompareIndex(1);
        selectionSort.setMinIndex(0);

        Timer selectionTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isSorted() || !isRunning){
                    selectionSort.setCompareIndex(Integer.MAX_VALUE);
                    selectionSort.setArrayIndex(Integer.MAX_VALUE);
                    selectionSort.setMinIndex(Integer.MAX_VALUE);
                    isRunning = false;
                    ((Timer)e.getSource()).stop();
                }else{
                    if(isRunning){
                        array = selectionSort.sortOnlyOneItem();
                    }
                }
                repaint();
            }
        });

        selectionTimer.start();
    }

    public void insertionSort(int delay){
        insertionSort.setArrayIndex(1);
        Timer insertionTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isSorted() || !isRunning){
                    insertionSort.setCompareIndex(Integer.MAX_VALUE);
                    insertionSort.setArrayIndex(Integer.MAX_VALUE);
                    insertionSort.setStartOfIteration(false);
                    isRunning = false;
                    ((Timer)e.getSource()).stop();
                }
                else{
                    if(isRunning){
                        array = insertionSort.sortOnlyOneItem();
                    }
                }

                repaint();
            }
        });
        insertionTimer.start();
    }

    public void quickSort(int delay){
        Timer quickTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isSorted() || !isRunning){
                    quickSort.setSP(-1);
                    quickSort.push(0);
                    quickSort.push(array.length-1);
                    quickSort.setArrayIndex(Integer.MAX_VALUE);
                    quickSort.setCompareIndex(Integer.MAX_VALUE);
                    quickSort.setPartition(-1);
                    quickSort.setIsPartioning(false);
                    ((Timer)e.getSource()).stop();
                }
                else{
                    if(isRunning){
                        array = quickSort.sortOnlyOneItem();
                    }
                }

                repaint();
            }
        });

        quickTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        GradientPaint paint = new GradientPaint(0,0, Color.GRAY, getWidth(), getHeight(), Color.BLACK, true);

        g2.setPaint(paint);
        Shape s = new Rectangle2D.Double(0,0, getWidth(), getHeight());
        g2.fill(s);
        g2.draw(s);


        int bar_width = 1000/array.length;

        for(int i = 0; i < array.length; i++){
            int height = array[i]/2;
            int xBegin = i + (bar_width - 1) * i;
            int yBegin = 600 - height;

            g2.setColor(Color.WHITE);

            if(sortAlgorithm != null && sortAlgorithm.equals("Bubble Sort")){
                if(i == bubbleSort.getCompareIndex() || i == (bubbleSort.getCompareIndex() + 1)){
                    g2.setColor(Color.GREEN);
                }else{
                    g2.setColor(Color.WHITE);
                }
            }

            if(sortAlgorithm != null && sortAlgorithm.equals("Selection Sort")){
                if(i == selectionSort.getCompareIndex() || i == selectionSort.getMinIndex()){
                    g2.setColor(Color.MAGENTA);
                }
                if(i == selectionSort.getArrayIndex()){
                    g2.setColor(Color.GREEN);
                }
            }

            if(sortAlgorithm != null && sortAlgorithm.equals("Insertion Sort")){
                if(i == insertionSort.getCompareIndex() || i == (insertionSort.getCompareIndex() + 1)){
                    g2.setColor(Color.MAGENTA);
                }

                if(i == insertionSort.getArrayIndex()){
                    g2.setColor(Color.GREEN);
                }
            }

            if(sortAlgorithm != null && sortAlgorithm.equals("Quick Sort")){
                if(i == quickSort.getArrayIndex()){
                    g2.setColor(Color.MAGENTA);
                }
                if(i == quickSort.getCompareIndex()){
                    g2.setColor(Color.BLUE);
                }
                if(i == quickSort.getPartition()){
                    g2.setColor(Color.GREEN);
                }
            }

            g2.fillRect(xBegin, yBegin, 600/array.length, height);
        }

    }


    public void setArray(int[] array) {
        this.array = array;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

        switch (pageIndex){
            case 0:

                Graphics2D g2 = (Graphics2D) graphics;

                GradientPaint paint = new GradientPaint(0,0, Color.GRAY, 600, 400, Color.BLACK, true);

                g2.setPaint(paint);
                Shape s = new Rectangle2D.Double(0,0, getWidth(), getHeight());
                g2.fill(s);
                g2.draw(s);


                int bar_width = 600/array.length;

                for(int i = 0; i < array.length; i++){
                    int height = array[i]/2;
                    int xBegin = i + (bar_width - 1) * i;
                    int yBegin = 400 - height;

                    g2.setColor(Color.WHITE);

                    g2.fillRect(xBegin, yBegin, 400/array.length, height);
                }
                break;
            default:
                return NO_SUCH_PAGE;
        }

        return PAGE_EXISTS;
    }

}
