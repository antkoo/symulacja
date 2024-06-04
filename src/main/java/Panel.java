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
    public static List<Projectile> Projectiles = new ArrayList<>();
    public static List<Explosion> Explosions = new ArrayList<>();
    int x, y, plantSpawnCycle, zombieSpawnCycle;
    double chance;

    public static List<List<int[]>> SpawnSquares = new ArrayList<>();
    private ResourceManager resourceManager;
    public static Timer theTimer;
    public static final int DELAY = 150;
    public static final int PLANT_SPAWN_INTERVAL = 1;
    public static final int ZOMBIE_SPAWN_INTERVAL = 6;
    public static final int START_SUN_POINTS = 1000;
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
        theTimer = new Timer(DELAY, this);
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

    public void spawnRandomPlant() {
        x = ThreadLocalRandom.current().nextInt(0,(PLANT_COLUMNS-1)*SQUARE_SIZE);
        y = ThreadLocalRandom.current().nextInt(SQUARE_SIZE,ROWS*SQUARE_SIZE);
        chance = ThreadLocalRandom.current().nextDouble(0,100);
        int chosen;
        if (chance<45) {
            chosen = 0;
        } else if (chance<65) {
            chosen = 1;
        } else if (chance<90) {
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
        Zombies.add(new BasicZombie(x, y));
    }

    public void initializeBoard() {
        plantSpawnCycle = 0;
        while (resourceManager.getSunPoints()>0) {
            spawnRandomPlant();
        }

        for (int i = 0; i<10; i++) {
            spawnRandomZombie();
        }
    }

    public void gameRestart() {
        Zombies.clear();
        Plants.clear();
        Projectiles.clear();
        resourceManager.spendSunPoints(resourceManager.getSunPoints());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==toSpawnSelector) {
            new SpawnSelector();
        }
        if (e.getSource()==startSimulation) {
            theTimer.start();
            resourceManager.addSunPoints(START_SUN_POINTS);
            initializeBoard();
            startSimulation.setEnabled(false);

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
                if (zombie.x <=0) {
                    System.out.println("GAME OVER");
                    theTimer.stop();
                    //gameRestart();
                    break;
                }
            }

            if(Zombies.isEmpty() && theTimer.isRunning()) {
                System.out.println("WINNER");
                theTimer.stop();
                //gameRestart();
            }

            sunPointsDisplay.setText("Sun Points: " + resourceManager.getSunPoints());
            CollisionManager.checkCollisions(Projectiles,Zombies);
            CollisionManager.checkAttacks(Plants,Zombies);
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < Plants.size(); i++) {
            Plant plant = Plants.get(i);
            if (plant.getHealth()<=0) {
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
        Graphics2D g2D = (Graphics2D) g;
    }
}
