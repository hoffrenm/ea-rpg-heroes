package hoffrenm;

import java.util.Objects;

public class HeroAttribute {
    private int strength;
    private int dexterity;
    private int intelligence;

    public HeroAttribute(int strength, int dexterity, int intelligence) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
    }

    public int getStrength() { return strength; }
    public int getDexterity() { return dexterity; }
    public int getIntelligence() { return intelligence; }

    public void addAttributes(HeroAttribute attributes) {
        this.strength += attributes.getStrength();
        this.dexterity += attributes.getDexterity();
        this.intelligence += attributes.getIntelligence();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeroAttribute that = (HeroAttribute) o;
        return strength == that.strength && dexterity == that.dexterity && intelligence == that.intelligence;
    }

    @Override
    public int hashCode() {
        return Objects.hash(strength, dexterity, intelligence);
    }
}
