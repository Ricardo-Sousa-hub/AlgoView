package algoview;

import javax.imageio.ImageIO;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Hashtable;

public class Main extends JFrame implements ActionListener {

    public Image startImage = null;
    JFileChooser fileChooser = new JFileChooser();

    Sorting panelSorting = new Sorting();
    Maze panelMaze = new Maze();

    String[] algoritmos = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort"};

    PrinterJob pj;

    public Main() { //mudar nome da função
        setPreferredSize(new Dimension(1280, 720));
        setTitle("AlgoView");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        setVisible(true);

        pj = PrinterJob.getPrinterJob();
        criarMenuBarEContainer();
    }


    private void criarMenuBarEContainer() {

        Container cp = this.getContentPane();
        cp.setLayout(new FlowLayout());
        cp.add(panelSorting);
        cp.add(panelMaze);
        panelMaze.setVisible(false);

        JComboBox listaAlgoritmos = new JComboBox(algoritmos);
        panelSorting.add(listaAlgoritmos);

        JTextField delayTextField = new JTextField("10");
        panelSorting.add(delayTextField);

        JButton start = new JButton("EXECUTAR");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!panelSorting.getRunning()){
                    panelSorting.setsortAlgorithm(listaAlgoritmos.getSelectedItem().toString());
                    panelSorting.setDelay(Integer.parseInt(delayTextField.getText()));
                    panelSorting.setRunning(true);
                    panelSorting.animate();
                }
            }
        });
        panelSorting.add(start);

        JButton reset = new JButton("RESET");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelSorting.reset();
            }
        });

        panelSorting.add(reset);

        JButton gerarMaze = new JButton("Gerar labirinto");
        gerarMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelMaze.gerarLabirinto();
            }
        });

        panelMaze.add(gerarMaze);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        JMenuItem menuItem = new JMenuItem("Exit");
        //Adicionar opção ao menu
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Mudar imagem de splashScreen");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Imprimir");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuBar.add(menu);

        menu = new JMenu("Maze");
        menuItem = new JMenuItem("Generate Maze");
        //Adicionar opção ao menu
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuBar.add(menu);

        menu = new JMenu("Sort");
        menuItem = new JMenuItem("Sort");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //Adicionar menu a menu bar
        menuBar.add(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
        else if(e.getActionCommand().equals("Mudar imagem de splashScreen")){
            mudarSplashScreen();
        }
        else if(e.getActionCommand().equals("Imprimir")){
            pj.setPrintable(panelSorting);
            HashPrintRequestAttributeSet printParams = new HashPrintRequestAttributeSet();
            printParams.add(new MediaPrintableArea(0f, 0f, getWidth()/72f, getHeight()/72f, MediaPrintableArea.INCH));

            if (pj.printDialog(printParams)) {
                try {
                    pj.print(printParams);
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }
            }
        }
        else if(e.getActionCommand().equals("Generate Maze")){
            panelSorting.setVisible(false);
            panelMaze.setVisible(true);
        }
        else if(e.getActionCommand().equals("Sort")){
            panelMaze.setVisible(false);
            panelSorting.setVisible(true);
        }
    }

    public void mudarSplashScreen(){
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                byte[] image = Files.readAllBytes(fileChooser.getSelectedFile().toPath());

                //byte[] image = extractBytes(fileChooser.getSelectedFile().getAbsolutePath());

                String userDirectory = System.getProperty("user.dir");

                File file = new File(userDirectory+"/src/imagens/image1.jpg");

                FileOutputStream fosFor = new FileOutputStream(file);

                fosFor.write(image);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public byte[] extractBytes (String ImageName) throws IOException { //TODO CORRIGIR
        // open image
        File imgPath = new File(ImageName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        return imageBytes;
    }
}