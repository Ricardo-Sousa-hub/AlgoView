package algoview;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Main extends JFrame implements ActionListener {
    JFileChooser fileChooser = new JFileChooser();

    Sorting sortingPanel = new Sorting();
    Maze mazePanel = new Maze();
    JPanel opcoesMazePanel = new JPanel();

    String[] algoritmos = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort"};
    String[] algoritmosMaze = {"DFS", "BFS", "AStar"};

    String operacao;
    String sortingOrMaze = "sorting";

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

        opcoesMazePanel.setPreferredSize(new Dimension(150, 600));
        opcoesMazePanel.setBackground(Color.WHITE);

        Container cp = this.getContentPane();
        cp.setLayout(new FlowLayout());
        cp.add(sortingPanel);
        cp.add(mazePanel);
        cp.add(opcoesMazePanel);
        opcoesMazePanel.setVisible(false);
        mazePanel.setVisible(false);

        JComboBox listaAlgoritmosMazeJComboBox = new JComboBox(algoritmosMaze);
        opcoesMazePanel.add(listaAlgoritmosMazeJComboBox);

        JTextField delayMazeTextField = new JTextField("10");
        opcoesMazePanel.add(delayMazeTextField);

        JButton generateBaseButton = new JButton("Generate Maze Board");
        generateBaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortingOrMaze = "maze";
                if(!mazePanel.isRunning()){
                    mazePanel.preencherLabirinto();
                }else{
                    JOptionPane.showMessageDialog(getParent(), "Aguarde enquanto o labirinto é gerado");
                }
            }
        });
        opcoesMazePanel.add(generateBaseButton);

        JButton generateMaze = new JButton("Generate Maze");
        generateMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mazePanel.isRunning()) {
                    if (!mazePanel.isRunning()) {//não gerar 2 labirintos um por cima do outro //relativo?
                        mazePanel.setRunning(true);
                        try {
                            mazePanel.setDelay(Integer.parseInt(delayMazeTextField.getText()));
                            mazePanel.preencherLabirinto();
                            mazePanel.gerarLabirinto();
                        }catch (NumberFormatException ex){
                            JOptionPane.showMessageDialog(getParent(), "Por favor introduza a velocidade de execução");
                        }
                    } else {
                        mazePanel.preencherLabirinto();
                    }
                } else {
                    JOptionPane.showMessageDialog(getParent(), "Já está a ser gerado um labirinto");
                }

            }
        });
        opcoesMazePanel.add(generateMaze);

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mazePanel.setDelay(Integer.parseInt(delayMazeTextField.getText()));
                    if(mazePanel.isHasEndPoint() && mazePanel.isHasStartingPoint()){
                        mazePanel.startSolver(listaAlgoritmosMazeJComboBox.getSelectedItem().toString());
                    }
                    else{
                        JOptionPane.showMessageDialog(getParent(), "Por favor selecione um ponto de inicio e um ponto final");
                    }
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(getParent(), "Por favor introduza a velocidade de execução");
                }

            }
        });
        opcoesMazePanel.add(solveButton);

        JButton resetMazeButton = new JButton("Limpar");
        resetMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazePanel.reset();
            }
        });
        opcoesMazePanel.add(resetMazeButton);

        JComboBox listaAlgoritmosJComboBox = new JComboBox(algoritmos);
        sortingPanel.add(listaAlgoritmosJComboBox);

        JTextField delayTextField = new JTextField("10");
        sortingPanel.add(delayTextField);

        JButton startButton = new JButton("EXECUTAR");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!sortingPanel.getRunning()){
                    sortingPanel.setsortAlgorithm(listaAlgoritmosJComboBox.getSelectedItem().toString());
                    try {
                        sortingPanel.setDelay(Integer.parseInt(delayTextField.getText()));
                        sortingPanel.setRunning(true);
                        sortingPanel.animate();
                    }catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(getParent(), "Por favor introduza a velocidade de execução");
                    }

                }
            }
        });
        sortingPanel.add(startButton);

        JTextField arraySizeTextField = new JTextField("50");
        sortingPanel.add(arraySizeTextField);

        JButton randomArrayButton = new JButton("Gerar Array");
        randomArrayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int size = Integer.parseInt(arraySizeTextField.getText());
                    if(size <= 0){
                        size = 10;
                    }
                    sortingPanel.gerarArray2(size);
                    repaint();
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(getParent(), "Por favor introduza um tamanho valido para a array");
                }
            }
        });
        sortingPanel.add(randomArrayButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortingPanel.reset();
            }
        });

        sortingPanel.add(resetButton);

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

        menuItem = new JMenuItem("Selecionar Inicio");
        //Adicionar opção ao menu
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Selecionar Final");
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
            switch (sortingOrMaze){
                case "sorting":
                    pj.setPrintable(sortingPanel);
                    break;
                case "maze":
                    pj.setPrintable(mazePanel);
                    break;
            }
            HashPrintRequestAttributeSet printParams = new HashPrintRequestAttributeSet();
            printParams.add(new MediaPrintableArea(0f, 0, getWidth()/72f, getHeight()/72f, MediaPrintableArea.INCH));

            if (pj.printDialog(printParams)) {
                try {
                    pj.print(printParams);
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }
            }
        }
        else if(e.getActionCommand().equals("Generate Maze")){
            sortingOrMaze = "maze";
            sortingPanel.setVisible(false);
            mazePanel.setVisible(true);
            opcoesMazePanel.setVisible(true);
        }
        else if(e.getActionCommand().equals("Selecionar Inicio")){
            operacao = "Selecionar Inicio";
            mazePanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(!mazePanel.isHasStartingPoint()){
                        mazePanel.selecionarCelulaStart(e.getX(), e.getY());
                        mazePanel.setHasStartingPoint(true);
                    }
                    else if(mazePanel.isHasStartingPoint() && operacao.equals("Selecionar Inicio"))
                    {
                        mazePanel.removerCelulaStart();
                        mazePanel.selecionarCelulaStart(e.getX(), e.getY());
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        else if(e.getActionCommand().equals("Selecionar Final")){
            operacao = "Selecionar Final";
            mazePanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(!mazePanel.isHasEndPoint()){
                        mazePanel.selecionarCelulaEnd(e.getX(), e.getY());
                        mazePanel.setHasEndPoint(true);
                    }
                    else if(mazePanel.isHasStartingPoint() && operacao.equals("Selecionar Final"))
                    {
                        mazePanel.removerCelulaEnd();
                        mazePanel.selecionarCelulaEnd(e.getX(), e.getY());
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        else if(e.getActionCommand().equals("Sort")){
            sortingOrMaze = "sorting";
            opcoesMazePanel.setVisible(false);
            mazePanel.setVisible(false);
            sortingPanel.setVisible(true);
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


    /*public byte[] extractBytes (String ImageName) throws IOException { //Não recolhe a cor CORRIGIR
        // open image
        File imgPath = new File(ImageName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        return imageBytes;
    }*/


}