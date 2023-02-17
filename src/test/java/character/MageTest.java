package character;

import equipment.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestHelper;

import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class MageTest {

    private Hero hero;
    private final TestHelper helper = new TestHelper();

    @BeforeEach
    void setUp() {
        hero = new Mage("Minerva");
    }

    @Test
    void createMage_validName_shouldReturnSameName() {
        hero = new Mage("Alice");

        String name = hero.getName();

        assertEquals("Alice", name);
    }

    @Test
    void createMage_atBeginning_shouldStartAtLevelOne() {
        int startingLevel = hero.getLevel();

        assertEquals(1, startingLevel);
    }

    @Test
    void createMage_atBeginning_shouldHaveCorrectBaseAttributes() {
        HeroAttribute attributes = hero.getLevelAttributes();
        HeroAttribute expectedAttributes = helper.getHeroBaseAttributes(hero);

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void levelUp_byOneLevel_shouldIncreaseLevelByOne() {
        hero.levelUp();

        int level = hero.getLevel();

        assertEquals(2, level);
    }

    @Test
    void levelUp_byFourLevelUps_shouldIncreaseLevelByFour() {
        int levels = 4;
        IntStream.range(0, levels).forEach((i) -> hero.levelUp());

        int level = hero.getLevel();

        assertEquals(levels + 1, level);
    }

    @Test
    void levelUp_byOneLevelUp_shouldIncreaseLevelAttributesByOne() {
        HeroAttribute expected = helper.getAttributesAfterLeveling(hero, 1);

        hero.levelUp();
        HeroAttribute attributes = hero.getLevelAttributes();

        assertEquals(expected, attributes);
    }

    @Test
    void levelUp_byFourLevelUps_shouldIncreaseLevelAttributesFourTimes() {
        int levels = 4;
        HeroAttribute expected = helper.getAttributesAfterLeveling(hero, levels);

        IntStream.range(0, levels).forEach((i) -> hero.levelUp());
        HeroAttribute attributes = hero.getLevelAttributes();

        assertEquals(expected, attributes);
    }

    @Test
    void equipment_atStart_shouldHaveNothingEquipped() {
        Map<Slot, Item> items = hero.getEquipment();

        boolean hasNoItems = items.values().stream().allMatch(Objects::isNull);

        assertTrue(hasNoItems);
    }

    @Test
    void equipWeapon_validTypeStaff_shouldBeAbleToEquip() throws InvalidWeaponException {
        Weapon weapon = helper.getWeaponOfTypeAndLevel(WeaponType.Staff, 1);
        hero.equip(weapon);

        Item equippedItem = hero.getEquipment().get(Slot.Weapon);

        assertEquals(weapon, equippedItem);
    }

    @Test
    void equipWeapon_validTypeWand_shouldBeAbleToEquip() throws InvalidWeaponException {
        Weapon weapon = helper.getWeaponOfTypeAndLevel(WeaponType.Wand, 1);
        hero.equip(weapon);

        Item equippedItem = hero.getEquipment().get(Slot.Weapon);

        assertEquals(weapon, equippedItem);
    }

    @Test
    void equipWeapon_validTypeInvalidLevel_shouldThrowInvalidWeaponException() {
        Weapon highLvlWeapon = helper.getWeaponOfTypeAndLevel(WeaponType.Wand, 10);
        String errorMessage = "Your level is too low to wield that weapon.";

        InvalidWeaponException exception = assertThrows(InvalidWeaponException.class, () -> hero.equip(highLvlWeapon));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipment_afterLevelingUp_shouldBeAbleToEquipHigherLevelItem() throws InvalidWeaponException {
        int levels = 5;
        Weapon highLvlWeapon = helper.getWeaponOfTypeAndLevel(WeaponType.Staff, levels);
        IntStream.range(0, levels + 1).forEach((i) -> hero.levelUp());

        hero.equip(highLvlWeapon);
        Item equippedItem = hero.getEquipment().get(Slot.Weapon);

        assertEquals(highLvlWeapon, equippedItem);
    }

    @Test
    void equipWeapon_invalidType_shouldThrowInvalidWeaponException() {
        Weapon weapon = helper.getWeaponOfTypeAndLevel(WeaponType.Axe, 1);
        String errorMessage = "Mage cannot equip weapon of type " + weapon.getType() + ".";

        InvalidWeaponException exception = assertThrows(InvalidWeaponException.class, () -> hero.equip(weapon));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_validClothLegs_shouldBeAbleToEquip() throws InvalidArmorException {
        Armor armor = helper.getArmorOfSlotAndTypeAndLevel(Slot.Legs, ArmorType.Cloth, 1);

        hero.equip(armor);
        Item equippedItem = hero.getEquipment().get(Slot.Legs);

        assertEquals(armor, equippedItem);
    }

    @Test
    void equipArmor_validClothBody_shouldBeAbleToEquip() throws InvalidArmorException {
        Armor armor = helper.getArmorOfSlotAndTypeAndLevel(Slot.Body, ArmorType.Cloth, 1);

        hero.equip(armor);
        Item equippedItem = hero.getEquipment().get(Slot.Body);

        assertEquals(armor, equippedItem);
    }

    @Test
    void equipArmor_validClothHead_shouldBeAbleToEquip() throws InvalidArmorException {
        Armor armor = helper.getArmorOfSlotAndTypeAndLevel(Slot.Head, ArmorType.Cloth, 1);

        hero.equip(armor);
        Item equippedItem = hero.getEquipment().get(Slot.Head);

        assertEquals(armor, equippedItem);
    }

    @Test
    void equipArmor_invalidLevelValidType_shouldThrowInvalidArmorException() {
        Armor armor = helper.getArmorOfSlotAndTypeAndLevel(Slot.Legs, ArmorType.Cloth, 5);
        String errorMessage = "Your level is too low to equip that armor.";

        InvalidArmorException exception = assertThrows(InvalidArmorException.class, () -> hero.equip(armor));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_invalidTypeValidLevel_shouldThrowInvalidArmorException() {
        Armor armor = helper.getArmorOfSlotAndTypeAndLevel(Slot.Legs, ArmorType.Plate, 1);
        String errorMessage = "Mage cannot equip armor type of " + armor.getType() + ".";

        InvalidArmorException exception = assertThrows(InvalidArmorException.class, () -> hero.equip(armor));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_toSameSlot_shouldReplaceOldArmor() throws InvalidArmorException {
        Armor old = helper.getArmorOfSlotAndTypeAndLevel(Slot.Legs, ArmorType.Cloth, 1);
        Armor replacement = helper.getArmorOfSlotAndTypeAndLevel(Slot.Legs, ArmorType.Cloth, 1);

        hero.equip(old);
        hero.equip(replacement);
        Item equippedItem = hero.getEquipment().get(Slot.Legs);

        assertEquals(replacement, equippedItem);
    }

    @Test
    void totalAttributes_atBeginning_shouldEqualBaseAttributes() {
        HeroAttribute expectedAttributes = helper.getHeroBaseAttributes(hero);

        HeroAttribute attributes = hero.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_afterLevelingUpFiveTimes_shouldEqualTheirSum() {
        int levels = 5;
        HeroAttribute expectedAttributes = helper.getAttributesAfterLeveling(hero, 5);

        IntStream.range(0, levels).forEach((i) -> hero.levelUp());
        HeroAttribute attributes = hero.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withOneArmorEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = helper.getAttributesWithLevelAndNumberOfArmorsEquipped(hero, 1, 1);
        Armor armor = helper.getArmorOfSlotAndTypeAndLevel(Slot.Legs, ArmorType.Cloth, 1);

        hero.equip(armor);
        HeroAttribute attributes = hero.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withTwoArmorEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = helper.getAttributesWithLevelAndNumberOfArmorsEquipped(hero, 1, 2);
        Armor armor1 = helper.getArmorOfSlotAndTypeAndLevel(Slot.Legs, ArmorType.Cloth, 1);
        Armor armor2 = helper.getArmorOfSlotAndTypeAndLevel(Slot.Body, ArmorType.Cloth, 1);

        hero.equip(armor1);
        hero.equip(armor2);
        HeroAttribute attributes = hero.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withAllSlotsEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = helper.getAttributesWithLevelAndNumberOfArmorsEquipped(hero, 1, 3);
        Armor armor1 = helper.getArmorOfSlotAndTypeAndLevel(Slot.Head, ArmorType.Cloth, 1);
        Armor armor2 = helper.getArmorOfSlotAndTypeAndLevel(Slot.Legs, ArmorType.Cloth, 1);
        Armor armor3 = helper.getArmorOfSlotAndTypeAndLevel(Slot.Body, ArmorType.Cloth, 1);

        hero.equip(armor1);
        hero.equip(armor2);
        hero.equip(armor3);
        HeroAttribute attributes = hero.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withReplacedArmor_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute newArmorAttributes = new HeroAttribute(11, 22, 33);
        HeroAttribute expectedAttributes = helper.getHeroBaseAttributes(hero);
        expectedAttributes.addAttributes(newArmorAttributes);
        Armor armor1 = helper.getArmorOfSlotAndTypeAndLevel(Slot.Body, ArmorType.Cloth, 1);
        Armor armor2 = new Armor("Replacement", 1, Slot.Body, ArmorType.Cloth, newArmorAttributes);

        hero.equip(armor1);
        hero.equip(armor2);
        HeroAttribute attributes = hero.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void damage_withoutWeaponOrArmor_shouldEqualBaseDamageModifiedByBaseAttributes() {
        double expectedDamage = helper.getHeroDamageByLevelAndArmorsAndWeapon(hero, 1, 0, null);
        double damage = hero.damage();

        assertEquals(expectedDamage, damage, 1e-3);
    }

    @Test
    void damage_withWeapon_shouldEqualWeaponDamageModifiedByBaseAttributes() throws InvalidWeaponException {
        Weapon weapon = helper.getWeaponOfTypeAndLevel(WeaponType.Wand, 1);
        double expectedDamage = helper.getHeroDamageByLevelAndArmorsAndWeapon(hero, 1, 0, weapon);

        hero.equip(weapon);
        double damage = hero.damage();

        assertEquals(expectedDamage, damage, 1e-3);
    }

    @Test
    void damage_withWeaponAndArmor_shouldEqualWeaponDamageModifiedByTheirAttributes() throws InvalidWeaponException, InvalidArmorException {
        Armor armor = helper.getArmorOfSlotAndTypeAndLevel(Slot.Legs, ArmorType.Cloth, 1);
        Weapon weapon = helper.getWeaponOfTypeAndLevel(WeaponType.Staff, 1);
        double expectedDamage = helper.getHeroDamageByLevelAndArmorsAndWeapon(hero, 1, 1, weapon);

        hero.equip(armor);
        hero.equip(weapon);
        double damage = hero.damage();

        assertEquals(expectedDamage, damage, 1e-3);
    }

    @Test
    void damage_withWeaponAndAllArmor_shouldEqualWeaponDamageModifiedByTheirAttributes() throws InvalidWeaponException, InvalidArmorException {
        Armor armor1 = helper.getArmorOfSlotAndTypeAndLevel(Slot.Head, ArmorType.Cloth, 1);
        Armor armor2 = helper.getArmorOfSlotAndTypeAndLevel(Slot.Body, ArmorType.Cloth, 1);
        Armor armor3 = helper.getArmorOfSlotAndTypeAndLevel(Slot.Legs, ArmorType.Cloth, 1);
        Weapon weapon = helper.getWeaponOfTypeAndLevel(WeaponType.Wand, 1);
        double expectedDamage = helper.getHeroDamageByLevelAndArmorsAndWeapon(hero, 1, 3, weapon);

        hero.equip(armor1);
        hero.equip(armor2);
        hero.equip(armor3);
        hero.equip(weapon);
        double damage = hero.damage();

        assertEquals(expectedDamage, damage, 1e-3);
    }

    @Test
    void damage_withReplacedWeapon_shouldBeCalculatedWithNewWeapon() throws InvalidWeaponException {
        Weapon oldWeapon = helper.getWeaponOfTypeAndLevel(WeaponType.Staff, 1);
        Weapon newWeapon = new Weapon("Replacement", 1, WeaponType.Wand, 9001);
        double expectedDamage = helper.getHeroDamageByLevelAndArmorsAndWeapon(hero, 1, 0, newWeapon);

        hero.equip(oldWeapon);
        hero.equip(newWeapon);
        double damage = hero.damage();

        assertEquals(expectedDamage, damage, 1e-3);
    }

    @Test
    void display_atBeginning_shouldDisplayName() {
        String display = hero.display();
        String name = hero.getName();

        boolean hasDisplayName = display.contains("Name: " + name);

        assertTrue(hasDisplayName);
    }

    @Test
    void display_atBeginning_shouldDisplayClass() {
        String display = hero.display();

        boolean hasClassName = display.contains("Class: Mage");

        assertTrue(hasClassName);
    }

    @Test
    void display_atBeginning_mageShouldNotDisplayOtherClasses() {
        String display = hero.display();

        boolean isWarrior = display.contains("Warrior");
        boolean isRogue = display.contains("Rogue");
        boolean isRanger = display.contains("Ranger");

        assertFalse(isWarrior || isRogue || isRanger);
    }

    @Test
    void display_atBeginning_shouldDisplayLevelOne() {
        String display = hero.display();

        boolean hasLevel = display.contains("Level: 1");

        assertTrue(hasLevel);
    }

    @Test
    void display_atBeginning_shouldDisplayBaseLevelAttributes() {
        String display = hero.display();
        HeroAttribute baseAttributes = helper.getHeroBaseAttributes(hero);

        boolean hasStrength = display.contains("Strength: " + baseAttributes.getStrength());
        boolean hasDexterity = display.contains("Dexterity: " + baseAttributes.getDexterity());
        boolean hasIntelligence = display.contains("Intelligence: " + baseAttributes.getIntelligence());

        assertTrue(hasStrength && hasDexterity && hasIntelligence);
    }

    @Test
    void display_atBeginning_shouldDisplayBaseDamage() {
        String display = hero.display();
        double damage = helper.getHeroDamageByLevelAndArmorsAndWeapon(hero, 1, 0, null);

        boolean hasDamage = display.contains("Damage: " + damage);

        assertTrue(hasDamage);
    }

    @Test
    void display_whenLevelingUp_shouldDisplayUpdatedLevel() {
        int levels = 11;
        IntStream.range(0, levels).forEach((i) -> hero.levelUp());
        String display = hero.display();

        boolean hasLevel = display.contains("Level: " + (levels + 1));

        assertTrue(hasLevel);
    }

    @Test
    void display_withLevelUpsAndWeaponAndArmor_shouldDisplayUpdatedInformation() throws InvalidWeaponException, InvalidArmorException {
        int levels = 10;
        IntStream.range(0, levels).forEach((i) -> hero.levelUp());
        Weapon weapon = new Weapon("Sword", 1, WeaponType.Wand, 1234);
        hero.equip(weapon);
        hero.equip(helper.getArmorOfSlotAndTypeAndLevel(Slot.Head, ArmorType.Cloth, 1));
        hero.equip(helper.getArmorOfSlotAndTypeAndLevel(Slot.Body, ArmorType.Cloth, 1));
        hero.equip(helper.getArmorOfSlotAndTypeAndLevel(Slot.Legs, ArmorType.Cloth, 1));
        HeroAttribute totalAttributes = helper.getAttributesWithLevelAndNumberOfArmorsEquipped(hero, 1 + levels, 3);
        double damage = helper.getHeroDamageByLevelAndArmorsAndWeapon(hero, 1 + levels, 3, weapon);

        String display = hero.display();
        boolean hasStrength = display.contains("Strength: " + totalAttributes.getStrength());
        boolean hasDexterity = display.contains("Dexterity: " + totalAttributes.getDexterity());
        boolean hasIntelligence = display.contains("Intelligence: " + totalAttributes.getIntelligence());
        boolean hasDamage = display.contains("Damage: " + damage);

        assertTrue(hasStrength && hasDexterity && hasIntelligence && hasDamage);
    }
}
