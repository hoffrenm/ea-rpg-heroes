package hoffrenm;

public class Weapon extends Item {
    private WeaponType type;
    private int weaponDamage;

    public Weapon(String name, int requiredLevel, WeaponType type, int weaponDamage) {
        super(name, requiredLevel, Slot.Weapon);
        this.type = type;
        this.weaponDamage = weaponDamage;
    }
}
