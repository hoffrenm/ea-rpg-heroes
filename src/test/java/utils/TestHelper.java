package utils;

import character.Hero;
import character.HeroAttribute;
import equipment.*;

public class TestHelper {

    private HeroAttribute armorAttributes = new HeroAttribute(100, 100, 100);

    private HeroAttribute rogueBaseAttributes = new HeroAttribute(2, 6, 1);
    private HeroAttribute rangerBaseAttributes = new HeroAttribute(1, 7, 1);
    private HeroAttribute mageBaseAttributes = new HeroAttribute(1, 1, 8);
    private HeroAttribute warriorBaseAttributes = new HeroAttribute(5, 2, 1);

    private HeroAttribute rogueLevelUpAttributes = new HeroAttribute(1, 4, 1);
    private HeroAttribute rangerLevelUAttributes = new HeroAttribute(1, 5, 1);
    private HeroAttribute mageLevelUpAttributes = new HeroAttribute(1, 1, 5);
    private HeroAttribute warriorLevelUpAttributes = new HeroAttribute(3, 2, 1);

    public Armor getArmorOfSlotAndTypeAndLevel(Slot slot, ArmorType type, int level) {
        return new Armor("Test_equipment", level, slot, type, armorAttributes);
    }

    public Weapon getWeaponOfTypeAndLevel(WeaponType type, int level) {
        return new Weapon("Test_weapon", level, type, 150);
    }

    public HeroAttribute getAttributesAfterLeveling(Hero hero, int numOfLevels) {
        HeroAttribute stats = new HeroAttribute(0, 0, 0);
        HeroAttribute heroAttributes = getHeroBaseAttributes(hero);
        HeroAttribute levelUpAttributes = getHeroLevelUpAttributes(hero);

        stats.addAttributes(heroAttributes);

        for (int i = 0; i < numOfLevels; i++) {
            stats.addAttributes(levelUpAttributes);
        }

        return stats;
    }

    public HeroAttribute getAttributesWithLevelAndNumberOfArmorsEquipped(Hero hero, int heroLevel, int armorPieces) {
        HeroAttribute heroAttributes = getAttributesAfterLeveling(hero, heroLevel - 1);

        for (int i = 0; i < armorPieces; i++) {
            heroAttributes.addAttributes(armorAttributes);
        }

        return heroAttributes;
    }

    public double getHeroDamageByLevelAndArmorsAndWeapon(Hero hero, int level, int armorPieces, Weapon weapon) {
        String heroClass = hero.getClass().getSimpleName();
        HeroAttribute totalAttributes = getAttributesWithLevelAndNumberOfArmorsEquipped(hero, level, armorPieces);
        int mainAttribute = 0;

        switch (heroClass) {
            case "Rogue", "Ranger" -> {
                mainAttribute = totalAttributes.getDexterity();
            }
            case "Mage" -> {
                mainAttribute = totalAttributes.getIntelligence();
            }
            case "Warrior" -> {
                mainAttribute = totalAttributes.getStrength();
            }
        }

        int baseDamage = weapon != null ? weapon.getWeaponDamage() : 1;
        double modifier = 1 + (mainAttribute / 100d);

        return baseDamage * modifier;
    }

    public HeroAttribute getHeroBaseAttributes(Hero hero) {
        String heroClass = hero.getClass().getSimpleName();

        switch (heroClass) {
            case "Rogue" -> {
                return rogueBaseAttributes;
            }
            case "Mage" -> {
                return mageBaseAttributes;
            }
            case "Warrior" -> {
                return warriorBaseAttributes;
            }
            case "Ranger" -> {
                return rangerBaseAttributes;
            }
        }

        return new HeroAttribute(0, 0, 0);
    }

    private HeroAttribute getHeroLevelUpAttributes(Hero hero) {
        String heroClass = hero.getClass().getSimpleName();

        switch (heroClass) {
            case "Rogue" -> {
                return rogueLevelUpAttributes;
            }
            case "Mage" -> {
                return mageLevelUpAttributes;
            }
            case "Warrior" -> {
                return warriorLevelUpAttributes;
            }
            case "Ranger" -> {
                return rangerLevelUAttributes;
            }
        }

        return new HeroAttribute(0, 0, 0);
    }
}
