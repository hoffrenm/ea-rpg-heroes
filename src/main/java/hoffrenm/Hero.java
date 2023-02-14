package hoffrenm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class Hero {

    private final String name;
    private int level;
    private HeroAttribute levelAttributes;
    private Map<Slot, Item> equipment;
    private List<WeaponType> validWeaponTypes;
    private List<ArmorType> validArmorTypes;

    public Hero(String name, HeroAttribute levelAttributes, List<WeaponType> validWeaponTypes, List<ArmorType> validArmorTypes) {
        this.name = name;
        this.level = 1;
        this.levelAttributes = levelAttributes;
        this.validWeaponTypes = validWeaponTypes;
        this.validArmorTypes = validArmorTypes;
        this.equipment = new HashMap<Slot, Item>();

        for (Slot slot : Slot.values()) {
            this.equipment.put(slot, null);
        }
    }

    void levelUp() {}
    void equipWeapon() {}
    void equipArmor() {}
    void damage() {}
    void totalAttributes() {}
    void display() {}
}
