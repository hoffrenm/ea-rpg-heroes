package equipment;

/**
 * Represent Weapon object that Hero can wield and
 * gain additional damage provided by weapon.
 */
public class Weapon extends Item {
    private final WeaponType type;
    private final int weaponDamage;

    /**
     * Creates Weapon object with given name, required level, type and damage.
     *
     * @param name          name of the weapon.
     * @param requiredLevel minimum level required to wield weapon
     * @param type          type of the weapon
     * @param weaponDamage  damage that the weapon will grant to hero
     */
    public Weapon(String name, int requiredLevel, WeaponType type, int weaponDamage) {
        super(name, requiredLevel, Slot.Weapon);
        this.type = type;
        this.weaponDamage = weaponDamage;
    }

    /**
     * Returns type of the weapon as WeaponType enum.
     *
     * @return type of weapon
     * @see WeaponType
     */
    public WeaponType getType() {
        return type;
    }

    /**
     * Returns damage multiplier as integer that will be given to the hero equipping weapon.
     *
     * @return weapon damage as integer
     */
    public int getWeaponDamage() {
        return weaponDamage;
    }
}
