package equipment;

public class Weapon extends Item {
    private final WeaponType type;
    private final int weaponDamage;

    public Weapon(String name, int requiredLevel, WeaponType type, int weaponDamage) {
        super(name, requiredLevel, Slot.Weapon);
        this.type = type;
        this.weaponDamage = weaponDamage;
    }

    public WeaponType getType() {
        return type;
    }

    public int getWeaponDamage() {
        return weaponDamage;
    }
}
