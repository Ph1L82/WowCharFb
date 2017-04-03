package cl.philipsoft.ph1l.wowcharfb.views;

import android.util.Log;

import cl.philipsoft.ph1l.wowcharfb.data.CurrentUser;
import cl.philipsoft.ph1l.wowcharfb.data.Nodes;
import cl.philipsoft.ph1l.wowcharfb.models.Character;
import cl.philipsoft.ph1l.wowcharfb.models.CharacterClass;
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

    public void validation(Character character) {
        if ((character.getCharacterFaction() != null) && (character.getCharacterClass() != null)) {
            if (character.getCharacterName().trim().length() > 0) {
                character.setCharacterLevel(1);
                character.setStatsByLvl(character.getCharacterLevel());
                Faction characterFaction = Faction.findById(Faction.class, character.getCharacterFaction().getId());
                characterFaction.save();
                Race characterRace = Race.findById(Race.class, character.getCharacterRace().getId());
                characterRace.save();
                CharacterClass characterClass = CharacterClass.findById(CharacterClass.class, character.getCharacterClass().getId());
                characterClass.save();
                String uid = new CurrentUser().get().getUid();
                Log.d("WOWC", "validation: uid: " + uid);
                character.setId(uid + character.getCharacterName());
                Log.d("WOWC", "validation: characterID: " + character.getId());
                saveCharacter(character, uid);
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

    public void saveCharacter(Character character, String uid) {
        character.setFavorite(false);
        Log.d("WOWC", "saveCharacter: Character Faction: " + character.getCharacterFaction().getName());
        Log.d("WOWC", "saveCharacter: Character Race: " + character.getCharacterRace().getRaceName());
        Log.d("WOWC", "saveCharacter: Character CharacterClass: " + character.getCharacterClass().getClassName());
        Log.d("WOWC", "saveCharacter: Character Name: " + character.getCharacterName());
        new Nodes().userCharacters(uid).child(character.getId()).setValue(character);
    }
}
