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


    public static boolean checkProjectileHit(Projectile projectile, List<Zombie> zombies) {
        for (Zombie zombie : zombies) {
            if (projectile.getBounds().intersects(zombie.getBounds())) {
                zombie.takeDamage(projectile.getDamage());
                return true;
            }
        }
        return false;
    }
    public static void checkExplosionDeaths(Explosion explosion, List<Zombie> zombies) {
        for (Zombie zombie : zombies) {
            if (explosion.getBounds().intersects(zombie.getBounds())) {
                zombie.takeDamage(explosion.getDamage());
            }
        }
    }
}