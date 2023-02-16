package equipment;

import character.HeroAttribute;

import java.util.Objects;

public class Armor extends Item {

    private ArmorType type;
    private HeroAttribute armorAttributes;

    public Armor(String name, int requiredLevel, Slot slot, ArmorType type, HeroAttribute armorAttributes) {
        super(name, requiredLevel, slot);
        this.type = type;
        this.armorAttributes = armorAttributes;
    }

    public ArmorType getType() {
        return type;
    }

    public HeroAttribute getArmorAttributes() {
        return armorAttributes;
    }
}
