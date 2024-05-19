import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Panel extends JPanel implements ActionListener {

    public List<Plant> Plants = new ArrayList<>();
    public List<Zombie> Zombies = new ArrayList<>();
    int x, y;
    double chance;

    public static List<List<int[]>> SpawnSquares = new ArrayList<>();
    private ResourceManager resourceManager;
    public static Timer theTimer;
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
        theTimer = new Timer(1000, this);
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

        while (resourceManager.getSunPoints()>0) {
            x = ThreadLocalRandom.current().nextInt(0,(PLANT_COLUMNS-1)*SQUARE_SIZE);
            y = ThreadLocalRandom.current().nextInt(SQUARE_SIZE,ROWS*SQUARE_SIZE);
            chance = ThreadLocalRandom.current().nextDouble(0,100);
            int chosen;
            if (chance<50) {
                chosen = 0;
            } else if (chance<70) {
                chosen = 1;
            } else if (chance<90) {
                chosen = 2;
            } else {
                chosen = 3;
            }
            System.out.println("Chosen " + chosen);
                List<int[]> squareType = SpawnSquares.get(chosen);
                for (int[] square : squareType) {
                    if (x >= square[0] * SQUARE_SIZE && x <= (square[0] + 1) * SQUARE_SIZE && y >= square[1] * SQUARE_SIZE && y <= (square[1] + 1) * SQUARE_SIZE) {
                        if (chosen==0) {
                            Plants.add(new Sunflower(x, y));
                            if (!resourceManager.spendSunPoints(Plants.getLast().getCost())) {
                                Plants.removeLast();
                            }
                        } else if (chosen==1) {
                            Plants.add(new Peashooter(x, y));
                            if (!resourceManager.spendSunPoints(Plants.getLast().getCost())) {
                                Plants.removeLast();
                            }
                        } else if (chosen==2) {
                            //Entities.add(new Sunflower(x, y, 1));
                        } else if (chosen==3) {
                            //Entities.add(new Sunflower(x, y, 1));
                        }
                    }
                }



        }

        for (int i = 0; i<10; i++) {
            x = ThreadLocalRandom.current().nextInt((COLUMNS-1)*SQUARE_SIZE, (COLUMNS) * SQUARE_SIZE);
            y = ThreadLocalRandom.current().nextInt(SQUARE_SIZE, ROWS * SQUARE_SIZE);
            Zombies.add(new BasicZombie(x, y));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==toSpawnSelector) {
            new SpawnSelector();
        }
        if (e.getSource()==startSimulation) {
            theTimer.start();
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
            if (plant.getHealth()==0) {
                Plants.remove(i);
                i--;
            }
            plant.paint(g);
        }
        for (int i = 0; i < Zombies.size(); i++) {
            Zombie zombie = Zombies.get(i);
            if (zombie.getHealth()==0) {
                Zombies.remove(i);
                i--;
            }
            zombie.paint(g);
        }
        Graphics2D g2D = (Graphics2D) g;
    }
}
