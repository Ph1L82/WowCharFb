package cl.philipsoft.ph1l.wowcharfb.data;

import java.util.ArrayList;
import java.util.List;

import cl.philipsoft.ph1l.wowcharfb.models.Character;
import cl.philipsoft.ph1l.wowcharfb.models.Class;
import cl.philipsoft.ph1l.wowcharfb.models.Faction;
import cl.philipsoft.ph1l.wowcharfb.models.Race;

/**
 * Created by phil_ on 05-03-2017.
 */

public class Queries {

    public List<Faction> factions() {
        List<Faction> factions = new ArrayList<>();
        List<Faction> factionsList = Faction.listAll(Faction.class);
        if (factionsList != null && factionsList.size() > 0) {
            factions.addAll(factionsList);
        }
        return factions;
    }

    public List<Race> races() {
        List<Race> races = new ArrayList<>();
        List<Race> raceList = Race.listAll(Race.class);
        if (raceList != null && raceList.size() > 0) {
            races.addAll(raceList);
        }
        return races;
    }

    public List<Class> classes() {
        List<Class> classes = new ArrayList<>();
        List<Class> classList = Class.listAll(Class.class); //LOL!
        if (classList != null && classList.size() > 0) {
            classes.addAll(classList);
        }
        return classes;
    }
}
