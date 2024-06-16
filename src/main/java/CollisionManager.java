import java.util.List;
/**
 * It has all functions connected with checking, whether different entities hit each other.
 */
public class CollisionManager {
    /**
     * Checks, whether a zombie attacked any plant
     * @param plants list of plants alive
     * @param zombies list of zombies alive
     */
    public static void checkAttacks(List<Plant> plants, List<Zombie> zombies) {
        //check if each zombie has eaten a plant in the list
        for (Zombie zombie : zombies) {
            for (Plant plant : plants) {
                if (plant.getBounds().intersects(zombie.getBounds())) {
                    plant.takeDamage(zombie.getAttackDamage());//if zombie touches the plant, it takes damage
                    zombie.x += zombie.getSpeed();//when zombie eats a plant, it stays still, so it returns to the same place as before
                    break;
                }
            }
        }
    }
    /**
     * Checks, whether a projectile hit a zombie.
     * @param projectile a projectile to check
     * @param zombies list of zombies alive
     * @return if the projectile hit anything
     */
    public static boolean checkProjectileHit(Projectile projectile, List<Zombie> zombies) {
        //check if a given projectile hit any zombie in the list
        for (Zombie zombie : zombies) {
            if (projectile.getBounds().intersects(zombie.getBounds())) {
                //if hit, zombie takes damage
                zombie.takeDamage(projectile.getDamage());
                return true;
            }
        }
        return false;//didn't hit anything
    }
    /**
     * Checks, whether an explosion hit a zombie.
     * @param explosion an explosion to check
     * @param zombies list of zombies alive
     */
    public static void checkExplosionDeaths(Explosion explosion, List<Zombie> zombies) {
        //check if a given explosion hit any zombie in the list
        for (Zombie zombie : zombies) {
            if (explosion.getBounds().intersects(zombie.getBounds())) {
                //if in explosion range, zombie takes damage
                zombie.takeDamage(explosion.getDamage());
            }
        }
    }
}