package character;

import equipment.ArmorType;
import equipment.Slot;
import equipment.Weapon;
import equipment.WeaponType;

import java.util.Arrays;
import java.util.List;

public class Warrior extends Hero {

    public Warrior(String name) {
        super(name);

        HeroAttribute baseAttributes = new HeroAttribute(5, 2, 1);
        HeroAttribute levelUpAttributes = new HeroAttribute(3, 2, 1);

        List<WeaponType> validWeapons = Arrays.asList(WeaponType.Axe, WeaponType.Hammer, WeaponType.Sword);
        List<ArmorType> validArmors = Arrays.asList(ArmorType.Mail, ArmorType.Plate);

        this.setLevelAttributes(baseAttributes);
        this.setLevelUpAttributes(levelUpAttributes);
        this.setValidWeaponTypes(validWeapons);
        this.setValidArmorTypes(validArmors);
    }

    public double damage() {
        Weapon equippedWeapon = (Weapon) this.getEquipment().get(Slot.Weapon);
        int baseDamage = equippedWeapon != null ? equippedWeapon.getWeaponDamage() : 1;

        double modifier = 1 + (this.totalAttributes().getStrength() / 100d);

        return baseDamage * modifier;
    }
}
