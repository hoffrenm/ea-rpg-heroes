# ea-rpg-heroes

RPG Heroes is a Java application that has been made as a part of an accelerated learning course. RPG Heroes has multiple classes with different attribute 
compositions, weapons and armors that heroes can equip in order to increase their attributes and damage.

Each hero is allowed only to use certain types of weapons and armors and have characteristic attribute that is used to calculate the damage that hero inflicts. 
Heroes also gain stats by leveling up.

| Hero     |     Armor     |      Weapon       | Main attribute |
|:---------|:--------------|-------------------|:--------------:|
| Mage     | Cloth         | Staff, Wand       | Intelligence   |
| Rogue    | Leather, Mail | Dagger, Sword     | Dexterity      |
| Ranger   | Leather, Mail | Bow               | Dexterity      |
| Warrior  | Mail, Plate   | Axe, Hammer, Sword| Strength       |
-----------------------------------------------------------------

### How to run

Application has no user interface but rather is "run" by executing tests. Project is configured to use gradle so you can clone the application and 
on root directory with command line you can execute following:

#### Build project

`gradle build`

#### Run tests

`gradle test`

#### Run tests and generate test coverage report

`gradle test jacocoTestReport`

### Test coverage

![image](https://user-images.githubusercontent.com/46321023/219689469-69ad43b3-cd60-4ad8-a6af-d2ef634b562b.png)

[Test coverage report available here](https://hoffrenm.github.io/ea-rpg-heroes/)
