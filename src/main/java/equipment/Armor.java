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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Armor armor = (Armor) o;
        return type == armor.type && armorAttributes.equals(armor.armorAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, armorAttributes);
    }
}
