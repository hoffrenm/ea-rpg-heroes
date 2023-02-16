package character;

import equipment.ArmorType;
import equipment.Slot;
import equipment.Weapon;
import equipment.WeaponType;

import java.util.Arrays;
import java.util.List;

public class Mage extends Hero {

    public Mage(String name) {
        super(name);

        HeroAttribute baseAttributes = new HeroAttribute(1, 1, 8);
        HeroAttribute levelUpAttributes = new HeroAttribute(1, 1, 5);

        List<WeaponType> validWeapons = Arrays.asList(WeaponType.Staff, WeaponType.Wand);
        List<ArmorType> validArmors = Arrays.asList(ArmorType.Cloth);

        this.setLevelAttributes(baseAttributes);
        this.setLevelUpAttributes(levelUpAttributes);
        this.setValidWeaponTypes(validWeapons);
        this.setValidArmorTypes(validArmors);
    }

    public double damage() {
        Weapon equippedWeapon = (Weapon) this.getEquipment().get(Slot.Weapon);
        int baseDamage = equippedWeapon != null ? equippedWeapon.getWeaponDamage() : 1;

        double modifier = 1 + (this.totalAttributes().getIntelligence() / 100d);

        return baseDamage * modifier;
    }
}
