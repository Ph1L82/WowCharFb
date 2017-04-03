package cl.philipsoft.ph1l.wowcharfb.data;

import java.util.ArrayList;
import java.util.List;

import cl.philipsoft.ph1l.wowcharfb.models.CharacterClass;
import cl.philipsoft.ph1l.wowcharfb.models.Faction;
import cl.philipsoft.ph1l.wowcharfb.models.Race;

/**
 * Created by phil_ on 05-03-2017.
 */

public class DataSeeder {
    public List<Faction> createFactions() {
        List<Faction> factions = new ArrayList<>();
        Faction alliance = new Faction((long) 1, "Alliance");
        Faction horde = new Faction((long) 2, "Horde");

        factions.add(alliance);
        factions.add(horde);

        Faction.saveInTx(factions);

        return factions;
    }

    public List<Race> createRaces() {
        List<Race> races = new ArrayList<>();
        Race human = new Race((long) 1, 20, 20, 20, 20, 20, "Human");
        Race dwarf = new Race((long) 2, 25, 16, 21, 19, 19, "Dwarf");
        Race nightElf = new Race((long) 3, 16, 24, 20, 20, 20, "Night Elf");
        Race gnome = new Race((long) 4, 15, 22, 20, 20, 22, "Gnome");
        Race draenei = new Race((long) 5, 21, 17, 20, 20, 22, "Draenei");
        Race worgen = new Race((long) 6, 23, 22, 20, 16, 19, "Worgen");
        Race orc = new Race((long) 7, 23, 17, 21, 17, 22, "Orc");
        Race undead = new Race((long) 8, 19, 18, 20, 18, 25, "Undead");
        Race tauren = new Race((long) 9, 25, 16, 21, 16, 22, "Tauren");
        Race troll = new Race((long) 10, 21, 22, 20, 16, 21, "Troll");
        Race bloodElf = new Race((long) 11, 17, 22, 20, 23, 18, "Blood Elf");
        Race goblin = new Race((long) 12, 17, 22, 20, 23, 18, "Goblin");

        races.add(human);
        races.add(dwarf);
        races.add(nightElf);
        races.add(gnome);
        races.add(draenei);
        races.add(worgen);
        races.add(orc);
        races.add(undead);
        races.add(tauren);
        races.add(troll);
        races.add(bloodElf);
        races.add(goblin);

        Race.saveInTx(races);

        return races;
    }

    public List<CharacterClass> createClasses() {
        List<CharacterClass> classes = new ArrayList<>();
        CharacterClass druid = new CharacterClass(1, 0, 0, 2, 2, "Druid");
        CharacterClass hunter = new CharacterClass(0, 3, 1, 0, 1, "Hunter");
        CharacterClass mage = new CharacterClass(0, 0, 0, 3, 2, "Mage");
        CharacterClass paladin = new CharacterClass( 2, 0, 2, 0, 1, "Paladin");
        CharacterClass priest = new CharacterClass(0, 0, 0, 2, 3, "Priest");
        CharacterClass rogue = new CharacterClass(1, 3, 1, 0, 0, "Rogue");
        CharacterClass shaman = new CharacterClass(1, 0, 1, 1, 2, "Shaman");
        CharacterClass warlock = new CharacterClass(0, 0, 1, 2, 2, "Warlock");
        CharacterClass warrior = new CharacterClass(3, 0, 2, 0, 0, "Warrior");

        classes.add(druid);
        classes.add(hunter);
        classes.add(mage);
        classes.add(paladin);
        classes.add(priest);
        classes.add(rogue);
        classes.add(shaman);
        classes.add(warlock);
        classes.add(warrior);

        CharacterClass.saveInTx(classes);

        return classes;
    }
}
