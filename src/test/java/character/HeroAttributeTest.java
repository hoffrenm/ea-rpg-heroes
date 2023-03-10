package character;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroAttributeTest {

    @Test
    void heroAttribute_sameValues_shouldBeEqual() {
        HeroAttribute heroAttr1 = new HeroAttribute(13, 12, 11);
        HeroAttribute heroAttr2 = new HeroAttribute(13, 12, 11);

        boolean isSame = heroAttr1.equals(heroAttr2);

        assertTrue(isSame);
    }

    @Test
    void heroAttribute_differentStrength_shouldNotBeEqual() {
        HeroAttribute heroAttr1 = new HeroAttribute(9, 11, 13);
        HeroAttribute heroAttr2 = new HeroAttribute(13, 12, 13);

        boolean isNotSame = heroAttr1.equals(heroAttr2);

        assertFalse(isNotSame);
    }

    @Test
    void heroAttribute_differentDexterity_shouldNotBeEqual() {
        HeroAttribute heroAttr1 = new HeroAttribute(9, 11, 13);
        HeroAttribute heroAttr2 = new HeroAttribute(9, 13, 13);

        boolean isNotSame = heroAttr1.equals(heroAttr2);

        assertFalse(isNotSame);
    }

    @Test
    void heroAttribute_differentIntelligence_shouldNotBeEqual() {
        HeroAttribute heroAttr1 = new HeroAttribute(13, 12, 11);
        HeroAttribute heroAttr2 = new HeroAttribute(13, 12, 99);

        boolean isNotSame = heroAttr1.equals(heroAttr2);

        assertFalse(isNotSame);
    }

    @Test
    void addAttributes_firstOne_shouldHaveItsValuesIncreased() {
        HeroAttribute heroAttr1 = new HeroAttribute(12, 1, 104);
        HeroAttribute heroAttr2 = new HeroAttribute(84, 76, 3);
        HeroAttribute total = new HeroAttribute(96, 77, 107);

        heroAttr1.addAttributes(heroAttr2);
        boolean isSame = heroAttr1.equals(total);

        assertTrue(isSame);
    }
}
