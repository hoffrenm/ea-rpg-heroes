package hoffrenm;

public class Armor extends Item {
    private HeroAttribute armorAttribute;

    public Armor(String name, int requiredLevel, Slot slot, HeroAttribute armorAttribute) {
        super(name, requiredLevel, slot);
        this.armorAttribute = armorAttribute;
    }
}
