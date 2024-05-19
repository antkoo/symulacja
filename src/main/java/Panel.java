import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Panel extends JPanel implements ActionListener {

    public List<Plant> Plants = new ArrayList<>();
    //public List<Zombie> Zombies = new ArrayList<>();
    int x, y;
    public static List<List<int[]>> SpawnSquares = new ArrayList<>();
    private final ResourceManager resourceManager;
    private GameTimer timer;
    public static final int SQUARE_SIZE = 100;
    public static final int ROWS = 5;
    public static final int PLANT_COLUMNS = 5;
    public static final int ZOMBIE_COLUMNS = 4;
    public static final int COLUMNS = PLANT_COLUMNS + ZOMBIE_COLUMNS;

    JLabel sunPointsDisplay = new JLabel();
    JButton toSpawnSelector = new JButton("To Spawn Selector");
    JButton startSimulation = new JButton("Start Simulation");
    public Panel() {
        resourceManager = ResourceManager.getInstance();
        timer = new GameTimer();
        timer.addActionListener(this);
        this.setSize(SQUARE_SIZE*COLUMNS,SQUARE_SIZE*(ROWS+1));
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(new Color(0x4f7942));

        toSpawnSelector.setBounds(100, 40, 200, 40);
        toSpawnSelector.setFocusable(false);
        toSpawnSelector.addActionListener(this);
        startSimulation.setBounds(300, 40, 200, 40);
        startSimulation.setFocusable(false);
        startSimulation.addActionListener(this);
        sunPointsDisplay.setBounds(500,40,100,40);
        this.add(toSpawnSelector);
        this.add(startSimulation);
        this.add(sunPointsDisplay);
    }

    public void initializeBoard() {

        for (int i = 0; i<10; i++) {
            x = ThreadLocalRandom.current().nextInt(0,(PLANT_COLUMNS-1)*SQUARE_SIZE);
            y = ThreadLocalRandom.current().nextInt(SQUARE_SIZE,ROWS*SQUARE_SIZE);
            for (int j = 0; j < SpawnSquares.size(); j++) {
                List<int[]> squareType = SpawnSquares.get(j);
                for (int[] square : squareType) {
                    if (x >= square[0] * SQUARE_SIZE && x <= (square[0] + 1) * SQUARE_SIZE && y >= square[1] * SQUARE_SIZE && y <= (square[1] + 1) * SQUARE_SIZE) {
                        if (j==0) {
                            Plants.add(new Sunflower(x, y, 50));
                        } else if (j==1) {
                            Plants.add(new Peashooter(x, y, 50));
                        } else if (j==2) {
                            //Entities.add(new Sunflower(x, y, 1));
                        } else if (j==3) {
                            //Entities.add(new Sunflower(x, y, 1));
                        }

                        System.out.println(x + " " + y);
                        System.out.println(square[0] * SQUARE_SIZE + " " + square[1] * SQUARE_SIZE);
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==toSpawnSelector) {
            new SpawnSelector();
            System.out.println("hm");
        }
        if (e.getSource()==startSimulation) {
            timer.start();
            resourceManager.addSunPoints(1000);
            initializeBoard();
            startSimulation.setEnabled(false);

        }
        repaint();

        sunPointsDisplay.setText("Sun Points: " + resourceManager.getSunPoints());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < Plants.size(); i++) {
            Plant plant = Plants.get(i);

            if (plant.health==0) {
                Plants.remove(i);
                i--;
            }
            plant.paint(g);



        }

        //image = new ImageIcon("src/Sunflower.png").getImage();
        Graphics2D g2D = (Graphics2D) g;
        //g2D.drawImage(image, 100, 100, null);
        //g2D.drawRect(100,100,100,100);
        //g2D.draw



    }
}
