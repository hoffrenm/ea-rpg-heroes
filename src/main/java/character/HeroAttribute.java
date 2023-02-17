package character;

import java.util.Objects;

/**
 * Represents attribute object that heroes and armors have.
 * Consists of three attributes: Strength, Dexterity and Intelligence.
 */
public class HeroAttribute {
    private int strength;
    private int dexterity;
    private int intelligence;

    /**
     * Creates HeroAttribute object with given strength, dexterity and intelligence values.
     *
     * @param strength     value of strength
     * @param dexterity    value of dexterity
     * @param intelligence value of intelligence
     */
    public HeroAttribute(int strength, int dexterity, int intelligence) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
    }

    /**
     * Returns value of strength as integer
     *
     * @return value of strength
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Returns value of dexterity as integer
     *
     * @return value of dexterity
     */
    public int getDexterity() {
        return dexterity;
    }

    /**
     * Returns value of intelligence as integer
     *
     * @return value of intelligence
     */
    public int getIntelligence() {
        return intelligence;
    }

    /**
     * Adds two instances of HeroAttributes togethers. Object that uses this
     * method will have each of its attributes incremented by amount of that given parameter.
     * HeroAttribute given as parameter will remain unchanged.
     *
     * @param attributes HeroAttribute object that will increase attributes
     */
    public void addAttributes(HeroAttribute attributes) {
        this.strength += attributes.getStrength();
        this.dexterity += attributes.getDexterity();
        this.intelligence += attributes.getIntelligence();
    }

    /**
     * Checks if both HeroAttributes have equal values for strength, dexterity and intelligence.
     *
     * @param o HeroAttribute object
     * @return true if both HeroAttribute objects have same attribute values
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeroAttribute that = (HeroAttribute) o;
        return strength == that.strength && dexterity == that.dexterity && intelligence == that.intelligence;
    }
}
