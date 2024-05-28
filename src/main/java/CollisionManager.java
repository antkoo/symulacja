import java.util.List;

public class CollisionManager {

    public static void checkAttacks(List<Plant> plants, List<Zombie> zombies) {
        for (Zombie zombie : zombies) {
            for (Plant plant : plants) {
                if (plant.getBounds().intersects(zombie.getBounds())) {
                    plant.takeDamage(zombie.getAttackDamage());
                    zombie.x += zombie.getSpeed();
                    break;
                }
            }
        }
    }
    public static void checkCollisions(List<Projectile> projectiles, List<Zombie> zombies) {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            for (int j = 0; j < zombies.size(); j++) {
                Zombie zombie = zombies.get(j);
                if (projectile.getBounds().intersects(zombie.getBounds())) {
                    zombie.takeDamage(projectile.getDamage());
                    projectiles.remove(i);
                    break;
                }
            }
        }
    }
    public static void checkExplosionDeaths(Explosion explosion, List<Zombie> zombies) {
        for (int j = 0; j < zombies.size(); j++) {
            Zombie zombie = zombies.get(j);
            if (explosion.getBounds().intersects(zombie.getBounds())) {
                zombie.takeDamage(explosion.getDamage());
            }
        }
    }
}