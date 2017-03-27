package cl.philipsoft.ph1l.wowcharfb.views;

import cl.philipsoft.ph1l.wowcharfb.data.CurrentUser;
import cl.philipsoft.ph1l.wowcharfb.data.Nodes;
import cl.philipsoft.ph1l.wowcharfb.models.Character;
import cl.philipsoft.ph1l.wowcharfb.models.Class;
import cl.philipsoft.ph1l.wowcharfb.models.Faction;
import cl.philipsoft.ph1l.wowcharfb.models.Race;

/**
 * Created by phil_ on 04-03-2017.
 */

public class CreateCharacter {
    private CharacterCallback characterCallback;

    public CreateCharacter(CharacterCallback callback) {
        this.characterCallback = callback;
    }

    public void validation(Character character, long charFaction, long charRace, long charClass) {
        if ((character.getCharacterFaction() != null) && (character.getCharacterClass() != null)) {
            if (character.getCharacterName().trim().length() > 0) {
                character.setCharacterLevel(1);
                character.setStatsByLvl(character.getCharacterLevel());
                Faction characterFaction = Faction.findById(Faction.class, charFaction);
                characterFaction.save();
                Race characterRace = Race.findById(Race.class, charRace);
                characterRace.save();
                Class characterClass = Class.findById(Class.class, charClass);
                characterClass.save();
//                character.save();
                String uid = new CurrentUser().userID();
                String characterID = uid + character.getCharacterName();
                character.setId(characterID);
                new Nodes().userCharacters(uid).child(characterID).setValue(character);
                characterCallback.created(character);
            } else {
                characterCallback.noName();
            }
        } else if (character.getCharacterFaction() != null) {
            characterCallback.noClass();
        } else {
            characterCallback.noFaction();
        }
    }
}
