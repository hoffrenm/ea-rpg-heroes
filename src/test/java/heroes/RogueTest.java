package heroes;

import character.Hero;
import character.Rogue;
import character.HeroAttribute;
import equipment.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class RogueTest {

    private Hero rogue;

    @BeforeEach
    void setUp() {
        rogue = new Rogue("Richard");
    }

    @Test
    void createRogue_validName_shouldReturnSameName() {
        rogue = new Rogue("Maxwell");

        String name = rogue.getName();

        assertEquals("Maxwell", name);
    }

    @Test
    void createRogue_atBeginning_shouldStartAtLevelOne() {
        int startingLevel = rogue.getLevel();

        assertEquals(1, startingLevel);
    }

    @Test
    void createRogue_atBeginning_shouldHaveCorrectBaseAttributes() {
        HeroAttribute attributes = rogue.getLevelAttributes();
        HeroAttribute baseAttributes = new HeroAttribute(2, 6, 1);

        assertEquals(baseAttributes, attributes);
    }

    @Test
    void levelUp_byOneLevel_shouldIncreaseLevelByOne() {
        rogue.levelUp();

        int level = rogue.getLevel();

        assertEquals(2, level);
    }

    @Test
    void levelUp_byFourLevelUps_shouldIncreaseLevelByFour() {
        IntStream.range(0, 4).forEach((i) -> rogue.levelUp());

        int level = rogue.getLevel();

        assertEquals(5, level);
    }

    @Test
    void levelUp_byOneLevelUp_shouldIncreaseLevelAttributesByOne() {
        HeroAttribute expected = new HeroAttribute(3, 10, 2);

        rogue.levelUp();
        HeroAttribute attributes = rogue.getLevelAttributes();

        assertEquals(expected, attributes);
    }

    @Test
    void levelUp_byFourLevelUps_shouldIncreaseLevelAttributesFourTimes() {
        HeroAttribute expected = new HeroAttribute(6, 22, 5);

        IntStream.range(0, 4).forEach((i) -> rogue.levelUp());
        HeroAttribute attributes = rogue.getLevelAttributes();

        assertEquals(expected, attributes);
    }

    @Test
    void equipment_atStart_shouldHaveNothingEquipped() {
        Map<Slot, Item> items = rogue.getEquipment();

        boolean hasNoItems = items.values().stream().allMatch(Objects::isNull);

        assertTrue(hasNoItems);
    }

    @Test
    void equipWeapon_validDagger_shouldBeAbleToEquip() throws InvalidWeaponException {
        Weapon dagger = new Weapon("Regular dagger", 1, WeaponType.Dagger, 1);
        rogue.equip(dagger);

        Item equippedItem = rogue.getEquipment().get(Slot.Weapon);

        assertEquals(dagger, equippedItem);
    }

    @Test
    void equipWeapon_validSword_shouldBeAbleToEquip() throws InvalidWeaponException {
        Weapon dagger = new Weapon("Regular sword", 1, WeaponType.Sword, 2);
        rogue.equip(dagger);

        Item equippedItem = rogue.getEquipment().get(Slot.Weapon);

        assertEquals(dagger, equippedItem);
    }

    @Test
    void equipWeapon_validTypeInvalidLevel_shouldThrowInvalidWeaponException() {
        Weapon highLvlDagger = new Weapon("Regular dagger", 9, WeaponType.Dagger, 3);
        String errorMessage = "Your level is too low to wield that weapon.";

        InvalidWeaponException exception = assertThrows(InvalidWeaponException.class, () -> rogue.equip(highLvlDagger));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipment_afterLevelingUp_shouldBeAbleToEquipHigherLevelItem() throws InvalidWeaponException {
        Weapon highLvlDagger = new Weapon("Regular dagger", 7, WeaponType.Dagger, 6);
        IntStream.range(0, 10).forEach((i) -> rogue.levelUp());

        rogue.equip(highLvlDagger);
        Item equippedItem = rogue.getEquipment().get(Slot.Weapon);

        assertEquals(highLvlDagger, equippedItem);
    }

    @Test
    void equipWeapon_invalidType_shouldThrowInvalidWeaponException() {
        Weapon staff = new Weapon("Magic staff", 1, WeaponType.Staff, 2);
        String errorMessage = "Rogue cannot equip weapon of type " + staff.getType() + ".";

        InvalidWeaponException exception = assertThrows(InvalidWeaponException.class, () -> rogue.equip(staff));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_validLeatherLegs_shouldBeAbleToEquip() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(1, 5, 0);
        Armor pants = new Armor("Leather pants", 1, Slot.Legs, ArmorType.Leather, armorAttributes);

        rogue.equip(pants);
        Item equippedItem = rogue.getEquipment().get(Slot.Legs);

        assertEquals(pants, equippedItem);
    }

    @Test
    void equipArmor_validMailBody_shouldBeAbleToEquip() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(3, 3, 1);
        Armor vest = new Armor("Mail vest", 1, Slot.Body, ArmorType.Mail, armorAttributes);

        rogue.equip(vest);
        Item equippedItem = rogue.getEquipment().get(Slot.Body);

        assertEquals(vest, equippedItem);
    }

    @Test
    void equipArmor_validLeatherHead_shouldBeAbleToEquip() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(3, 3, 1);
        Armor hat = new Armor("Fancy leather hat", 1, Slot.Head, ArmorType.Leather, armorAttributes);

        rogue.equip(hat);
        Item equippedItem = rogue.getEquipment().get(Slot.Head);

        assertEquals(hat, equippedItem);
    }

    @Test
    void equipArmor_invalidLevelValidType_shouldThrowInvalidArmorException() {
        HeroAttribute armorAttributes = new HeroAttribute(1, 1, 1);
        Armor pants = new Armor("Leather pants", 5, Slot.Legs, ArmorType.Mail, armorAttributes);
        String errorMessage = "Your level is too low to equip that armor.";

        InvalidArmorException exception = assertThrows(InvalidArmorException.class, () -> rogue.equip(pants));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_invalidTypeValidLevel_shouldThrowInvalidArmorException() {
        HeroAttribute armorAttributes = new HeroAttribute(10, 2, 0);
        Armor helmet = new Armor("Plate helmet", 1, Slot.Head, ArmorType.Plate, armorAttributes);
        String errorMessage = "Rogue cannot equip armor type of " + helmet.getType() + ".";

        InvalidArmorException exception = assertThrows(InvalidArmorException.class, () -> rogue.equip(helmet));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_toSameSlot_shouldReplaceOldArmor() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(1, 1, 1);
        Armor old = new Armor("Mail pants", 1, Slot.Legs, ArmorType.Mail, armorAttributes);
        Armor replacement = new Armor("Leather pants", 1, Slot.Legs, ArmorType.Leather, armorAttributes);

        rogue.equip(old);
        rogue.equip(replacement);
        Item equippedItem = rogue.getEquipment().get(Slot.Legs);

        assertEquals(replacement, equippedItem);
    }

    @Test
    void totalAttributes_atBeginning_shouldEqualBaseAttributes() {
        HeroAttribute expectedAttributes = new HeroAttribute(2, 6, 1);

        HeroAttribute attributes = rogue.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_afterLevelingUpFiveTimes_shouldEqualTheirSum() {
        HeroAttribute expectedAttributes = new HeroAttribute(7, 26, 6);

        IntStream.range(0, 5).forEach((i) -> rogue.levelUp());
        HeroAttribute attributes = rogue.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withOneArmorEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(12, 8, 4);
        HeroAttribute armorAttributes = new HeroAttribute(10, 2, 3);
        Armor pants = new Armor("Mail pants", 1, Slot.Legs, ArmorType.Mail, armorAttributes);

        rogue.equip(pants);
        HeroAttribute attributes = rogue.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withTwoArmorEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(22, 10, 7);
        HeroAttribute armorAttributes = new HeroAttribute(10, 2, 3);
        Armor pants = new Armor("Mail pants", 1, Slot.Legs, ArmorType.Mail, armorAttributes);
        Armor vest = new Armor("Leather vest", 1, Slot.Body, ArmorType.Leather, armorAttributes);

        rogue.equip(pants);
        rogue.equip(vest);
        HeroAttribute attributes = rogue.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withAllSlotsEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(11, 9, 22);
        HeroAttribute armorAttributes = new HeroAttribute(3, 1, 7);
        Armor hat = new Armor("Leather hat", 1, Slot.Head, ArmorType.Leather, armorAttributes);
        Armor vest = new Armor("Leather vest", 1, Slot.Body, ArmorType.Leather, armorAttributes);
        Armor pants = new Armor("Leather pants", 1, Slot.Legs, ArmorType.Leather, armorAttributes);

        rogue.equip(hat);
        rogue.equip(vest);
        rogue.equip(pants);
        HeroAttribute attributes = rogue.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withReplacedArmor_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(22, 14, 1);
        HeroAttribute armorAttributes = new HeroAttribute(3, 1, 7);
        HeroAttribute newArmorAttributes = new HeroAttribute(20, 8, 0);

        Armor vest = new Armor("Leather vest", 1, Slot.Body, ArmorType.Leather, armorAttributes);
        Armor newVest = new Armor("Much fancier vest", 1, Slot.Body, ArmorType.Leather, newArmorAttributes);

        rogue.equip(vest);
        rogue.equip(newVest);
        HeroAttribute attributes = rogue.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void damage_withoutWeaponOrArmor_shouldEqualBaseDamageModifiedByBaseAttributes() {
        double damage = rogue.damage();

        assertEquals(1.06, damage, 1e-3);
    }

    @Test
    void damage_withWeapon_shouldEqualWeaponDamageModifiedByBaseAttributes() throws InvalidWeaponException {
        Weapon weapon = new Weapon("Pointy thing", 1, WeaponType.Dagger, 15);
        rogue.equip(weapon);

        double damage = rogue.damage();

        assertEquals(15.9, damage, 1e-3);
    }

    @Test
    void damage_withWeaponAndArmor_shouldEqualWeaponDamageModifiedByTheirAttributes() throws InvalidWeaponException, InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(7, 22, 0);
        Armor pants = new Armor("Leather pants", 1, Slot.Legs, ArmorType.Leather, armorAttributes);
        Weapon weapon = new Weapon("Pointier thing", 1, WeaponType.Dagger, 27);

        rogue.equip(pants);
        rogue.equip(weapon);
        double damage = rogue.damage();

        assertEquals(34.56, damage, 1e-3);
    }

    @Test
    void damage_withWeaponAndAllArmor_shouldEqualWeaponDamageModifiedByTheirAttributes() throws InvalidWeaponException, InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(7, 11, 1);
        Armor hat = new Armor("Leather hat", 1, Slot.Head, ArmorType.Leather, armorAttributes);
        Armor vest = new Armor("Leather vest", 1, Slot.Body, ArmorType.Leather, armorAttributes);
        Armor pants = new Armor("Leather pants", 1, Slot.Legs, ArmorType.Leather, armorAttributes);
        Weapon weapon = new Weapon("Pointiest thing", 1, WeaponType.Sword, 44);


        rogue.equip(hat);
        rogue.equip(vest);
        rogue.equip(pants);
        rogue.equip(weapon);
        double damage = rogue.damage();

        assertEquals(61.16, damage, 1e-3);
    }

    @Test
    void damage_withReplacedWeapon_shouldBeCalculatedWithNewWeapon() throws InvalidWeaponException {
        Weapon oldWeapon = new Weapon("Poor sword", 1, WeaponType.Sword, 1);
        Weapon newWeapon = new Weapon("Magnificent dagger", 1, WeaponType.Dagger, 173);

        rogue.equip(oldWeapon);
        rogue.equip(newWeapon);
        double damage = rogue.damage();

        assertEquals(183.38, damage, 1e-3);
    }

    @Test
    void display_atBeginning_shouldDisplayName() {
        String display = rogue.display();
        String name = rogue.getName();

        boolean hasDisplayName = display.contains("Name: " + name);

        assertTrue(hasDisplayName);
    }

    @Test
    void display_atBeginning_shouldDisplayClass() {
        String display = rogue.display();

        boolean hasClassName = display.contains("Class: Rogue");

        assertTrue(hasClassName);
    }

    @Test
    void display_atBeginning_rogueShouldNotDisplayOtherClasses() {
        String display = rogue.display();

        boolean isMage = display.contains("Mage");
        boolean isWarrior = display.contains("Warrior");
        boolean isRanger = display.contains("Ranger");

        assertFalse(isMage && isWarrior && isRanger);
    }

    @Test
    void display_atBeginning_shouldDisplayLevelOne() {
        String display = rogue.display();

        boolean hasLevel = display.contains("Level: 1");

        assertTrue(hasLevel);
    }

    @Test
    void display_atBeginning_shouldDisplayBaseLevelAttributes() {
        String display = rogue.display();
        HeroAttribute baseAttributes = rogue.getLevelAttributes();

        boolean hasStrength = display.contains("Strength: " + baseAttributes.getStrength());
        boolean hasDexterity = display.contains("Dexterity: " + baseAttributes.getDexterity());
        boolean hasIntelligence = display.contains("Intelligence: " + baseAttributes.getIntelligence());

        assertTrue(hasStrength && hasDexterity && hasIntelligence);
    }

    @Test
    void display_atBeginning_shouldDisplayBaseDamage() {
        String display = rogue.display();
        double damage = rogue.damage();

        boolean hasDamage = display.contains("Damage: " + damage);

        assertTrue(hasDamage);
    }

    @Test
    void display_whenLevelingUp_shouldDisplayUpdatedLevel() {
        int levels = 11;
        IntStream.range(0, levels).forEach((i) -> rogue.levelUp());
        String display = rogue.display();

        boolean hasLevel = display.contains("Level: " + (levels + 1));

        assertTrue(hasLevel);
    }

    @Test
    void display_withLevelUpsAndWeaponAndArmor_shouldDisplayUpdatedInformation() throws InvalidWeaponException, InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(7, 16, 1);
        rogue.equip(new Armor("Leather hat", 1, Slot.Head, ArmorType.Leather, armorAttributes));
        rogue.equip(new Armor("Leather vest", 1, Slot.Body, ArmorType.Leather, armorAttributes));
        rogue.equip(new Armor("Leather pants", 1, Slot.Legs, ArmorType.Leather, armorAttributes));
        rogue.equip(new Weapon("Sword", 1, WeaponType.Sword, 61));
        IntStream.range(0, 10).forEach((i) -> rogue.levelUp());
        HeroAttribute totalAttributes = rogue.totalAttributes();
        double damage = rogue.damage();

        String display = rogue.display();
        boolean hasStrength = display.contains("Strength: " + totalAttributes.getStrength());
        boolean hasDexterity = display.contains("Dexterity: " + totalAttributes.getDexterity());
        boolean hasIntelligence = display.contains("Intelligence: " + totalAttributes.getIntelligence());
        boolean hasDamage = display.contains("Damage: " + damage);

        assertTrue(hasStrength && hasDexterity && hasIntelligence && hasDamage);
    }
}
