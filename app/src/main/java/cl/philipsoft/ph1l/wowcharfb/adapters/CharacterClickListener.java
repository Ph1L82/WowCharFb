package cl.philipsoft.ph1l.wowcharfb.adapters;

import cl.philipsoft.ph1l.wowcharfb.models.Character;

/**
 * Created by phil_ on 06-03-2017.
 */

public interface CharacterClickListener {

    void clickedId(Long id);

    void clickedCharacter(Character character);
    void clickedIds(Long factionID, Long raceID, Long classID, Long characterID);
}
