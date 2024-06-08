import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Panel extends JPanel implements ActionListener {

    public static List<Plant> Plants = new ArrayList<>();
    public static List<Zombie> Zombies = new ArrayList<>();
    int x, y, plantSpawnCycle, zombieSpawnCycle;
    double chance;
    public static List<List<int[]>> SpawnSquares = new ArrayList<>();
    private final ResourceManager resourceManager;
    public static Timer theTimer;
    public static final int DELAY = 30;
    public static final int START_ZOMBIE_AMOUNT = 10;
    public static final int PLANT_SPAWN_INTERVAL = 1;
    public static final int ZOMBIE_SPAWN_INTERVAL = 6;
    public static final int START_SUN_POINTS = 1000;
    public static final int BASIC_ZOMBIE_SPAWN_CHANCE = 70;
    public static final int BUCKETHEAD_ZOMBIE_SPAWN_CHANCE = 30;
    public static final int SUNFLOWER_SPAWN_CHANCE = 45;
    public static final int PEASHOOTER_SPAWN_CHANCE = 25;
    public static final int CHERRY_BOMB_SPAWN_CHANCE = 10;
    public static final int WALNUT_SPAWN_CHANCE = 20;

    public static final int SQUARE_SIZE = 100;
    public static final int ROWS = 5;
    public static final int PLANT_COLUMNS = 5;
    public static final int ZOMBIE_COLUMNS = 4;
    public static final int COLUMNS = PLANT_COLUMNS + ZOMBIE_COLUMNS;
    JLabel sunPointsDisplay = new JLabel();
    JButton toSpawnSelector = new JButton("To Spawn Selector");
    JButton startSimulation = new JButton("Start Simulation");
    JButton pauseSimulation = new JButton("Pause Simulation");
    public Panel() {
        resourceManager = ResourceManager.getInstance();
        theTimer = new Timer(DELAY, this);
        this.setSize(SQUARE_SIZE*COLUMNS,SQUARE_SIZE*(ROWS+1));
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(new Color(0x4f7942));

        toSpawnSelector.setBounds(SQUARE_SIZE, SQUARE_SIZE/3, 2*SQUARE_SIZE, SQUARE_SIZE/3);
        toSpawnSelector.setFocusable(false);
        toSpawnSelector.addActionListener(this);
        startSimulation.setBounds(3*SQUARE_SIZE, SQUARE_SIZE/3, 2*SQUARE_SIZE, SQUARE_SIZE/3);
        startSimulation.setFocusable(false);
        startSimulation.addActionListener(this);
        pauseSimulation.setBounds(3*SQUARE_SIZE, SQUARE_SIZE/3, 2*SQUARE_SIZE, SQUARE_SIZE/3);
        pauseSimulation.setFocusable(false);
        pauseSimulation.addActionListener(this);
        pauseSimulation.setVisible(false);
        pauseSimulation.setEnabled(false);
        sunPointsDisplay.setBounds(5*SQUARE_SIZE,SQUARE_SIZE/3,2*SQUARE_SIZE,SQUARE_SIZE/3);
        this.add(toSpawnSelector);
        this.add(startSimulation);
        this.add(pauseSimulation);
        this.add(sunPointsDisplay);
    }

    public void spawnRandomPlant() {
        x = ThreadLocalRandom.current().nextInt(0,(PLANT_COLUMNS-1)*SQUARE_SIZE);
        y = ThreadLocalRandom.current().nextInt(SQUARE_SIZE,ROWS*SQUARE_SIZE);
        chance = ThreadLocalRandom.current().nextDouble(0,SUNFLOWER_SPAWN_CHANCE+PEASHOOTER_SPAWN_CHANCE+CHERRY_BOMB_SPAWN_CHANCE+WALNUT_SPAWN_CHANCE);
        int chosen;
        if (chance<SUNFLOWER_SPAWN_CHANCE) {
            chosen = 0;
        } else if (chance<SUNFLOWER_SPAWN_CHANCE+PEASHOOTER_SPAWN_CHANCE) {
            chosen = 1;
        } else if (chance<SUNFLOWER_SPAWN_CHANCE+PEASHOOTER_SPAWN_CHANCE+CHERRY_BOMB_SPAWN_CHANCE) {
            chosen = 2;
        } else {
            chosen = 3;
        }
        List<int[]> squareType = SpawnSquares.get(chosen);
        if (squareType.isEmpty()) {
            spawnRandomPlant();
        }
        for (int[] square : squareType) {
            if (x >= square[0] * SQUARE_SIZE && x <= (square[0] + 1) * SQUARE_SIZE && y >= square[1] * SQUARE_SIZE && y <= (square[1] + 1) * SQUARE_SIZE) {
                if (chosen==0) {
                    Plants.add(new Sunflower(x, y));
                    if (!resourceManager.spendSunPoints(Plants.get(Plants.size()-1).getCost())) {
                        Plants.remove(Plants.size()-1);
                        spawnRandomPlant();
                    }
                } else if (chosen==1) {
                    Plants.add(new Peashooter(x, y));
                    if (!resourceManager.spendSunPoints(Plants.get(Plants.size()-1).getCost())) {
                        Plants.remove(Plants.size()-1);
                        spawnRandomPlant();
                    }
                } else if (chosen==2) {
                    Plants.add(new CherryBomb(x, y));
                    if (!resourceManager.spendSunPoints(Plants.get(Plants.size()-1).getCost())) {
                        Plants.remove(Plants.size()-1);
                        spawnRandomPlant();
                    }
                } else {
                    Plants.add(new Walnut(x, y));
                    if (!resourceManager.spendSunPoints(Plants.get(Plants.size()-1).getCost())) {
                        Plants.remove(Plants.size()-1);
                        spawnRandomPlant();
                    }
                }
            }
        }
    }

    public void spawnRandomZombie() {
        x = ThreadLocalRandom.current().nextInt((COLUMNS-1)*SQUARE_SIZE, (COLUMNS) * SQUARE_SIZE);
        y = ThreadLocalRandom.current().nextInt(SQUARE_SIZE, ROWS * SQUARE_SIZE);

        chance = ThreadLocalRandom.current().nextDouble(0,BASIC_ZOMBIE_SPAWN_CHANCE+BUCKETHEAD_ZOMBIE_SPAWN_CHANCE);
        if (chance<BASIC_ZOMBIE_SPAWN_CHANCE) {
            Zombies.add(new BasicZombie(x, y));
        } else {
            Zombies.add(new BucketheadZombie(x, y));
        }

    }

    public void gameStart() {
        Zombies.clear();
        Plants.clear();
        resourceManager.spendSunPoints(resourceManager.getSunPoints());
        theTimer.start();
        resourceManager.addSunPoints(START_SUN_POINTS);
        plantSpawnCycle = 0;
        while (resourceManager.getSunPoints()>0) {
            spawnRandomPlant();
        }
        for (int i = 0; i<START_ZOMBIE_AMOUNT; i++) {
            spawnRandomZombie();
        }
        startSimulation.setEnabled(false);
        startSimulation.setVisible(false);
        pauseSimulation.setVisible(true);
        pauseSimulation.setEnabled(true);
        toSpawnSelector.setEnabled(false);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==toSpawnSelector) {
            new SpawnSelector();
        }
        if (e.getSource()==startSimulation) {
            boolean allEmpty = true;
            for (List<int[]> spawnSquare : SpawnSquares) {
                if (!spawnSquare.isEmpty()) {
                    allEmpty = false;
                    break;
                }
            }
            if (allEmpty) {
                JOptionPane.showMessageDialog(null, "ALL SPAWN SQUARES EMPTY","ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                gameStart();
            }
        }
        if (e.getSource()==pauseSimulation) {
            if (theTimer.isRunning()) {
                theTimer.stop();
                pauseSimulation.setText("Unpause Simulation");
            } else {
                theTimer.start();
                pauseSimulation.setText("Pause Simulation");
            }

        }
        if (theTimer.isRunning()) {
            plantSpawnCycle++;
            if (plantSpawnCycle>=PLANT_SPAWN_INTERVAL) {
                spawnRandomPlant();
                plantSpawnCycle=0;
            }

            zombieSpawnCycle++;
            if (zombieSpawnCycle>=ZOMBIE_SPAWN_INTERVAL) {
                spawnRandomZombie();
                zombieSpawnCycle=0;
            }
            for(Zombie zombie : Zombies) {
                if (zombie.x<=0) {
                    System.out.println("GAME OVER");
                    theTimer.stop();
                    pauseSimulation.setVisible(false);
                    pauseSimulation.setEnabled(false);
                    startSimulation.setVisible(true);
                    startSimulation.setEnabled(true);
                    toSpawnSelector.setEnabled(true);
                    break;
                }
            }

            if(Zombies.isEmpty() && theTimer.isRunning()) {
                System.out.println("WINNER");
                theTimer.stop();
                pauseSimulation.setVisible(false);
                pauseSimulation.setEnabled(false);
                startSimulation.setText("Restart Simulation");
                startSimulation.setVisible(true);
                startSimulation.setEnabled(true);
                toSpawnSelector.setEnabled(true);
            }

            sunPointsDisplay.setText("Sun Points: " + resourceManager.getSunPoints());
            repaint();
            CollisionManager.checkAttacks(Plants,Zombies);
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < Plants.size(); i++) {
            Plant plant = Plants.get(i);
            if (plant.getHealth()<=0) {
                if (plant.getType()==2) {
                    CherryBomb cherryBomb = (CherryBomb) plant; //downcasting to access CherryBomb-exclusive function
                    cherryBomb.explode();
                }
                Plants.remove(i);
                System.out.println("KILLED");
                i--;
            }
            plant.paint(g);
        }
        for (int i = 0; i < Zombies.size(); i++) {
            Zombie zombie = Zombies.get(i);
            if (zombie.getHealth()<=0) {
                Zombies.remove(i);
                i--;
            }
            zombie.paint(g);
        }
    }
}
