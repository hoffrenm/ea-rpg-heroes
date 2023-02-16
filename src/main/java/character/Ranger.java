package character;

import equipment.ArmorType;
import equipment.Slot;
import equipment.Weapon;
import equipment.WeaponType;

import java.util.Arrays;
import java.util.List;

public class Ranger extends Hero {

    public Ranger(String name) {
        super(name);

        HeroAttribute baseAttributes = new HeroAttribute(1, 7, 1);
        HeroAttribute levelUpAttributes = new HeroAttribute(1, 5, 1);

        List<WeaponType> validWeapons = Arrays.asList(WeaponType.Bow);
        List<ArmorType> validArmors = Arrays.asList(ArmorType.Leather, ArmorType.Mail);

        this.setLevelAttributes(baseAttributes);
        this.setLevelUpAttributes(levelUpAttributes);
        this.setValidWeaponTypes(validWeapons);
        this.setValidArmorTypes(validArmors);
    }

    public double damage() {
        Weapon equippedWeapon = (Weapon) this.getEquipment().get(Slot.Weapon);
        int baseDamage = equippedWeapon != null ? equippedWeapon.getWeaponDamage() : 1;

        double modifier = 1 + (this.totalAttributes().getDexterity() / 100d);

        return baseDamage * modifier;
    }
}
