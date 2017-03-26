package cl.philipsoft.ph1l.wowcharfb.views;

import cl.philipsoft.ph1l.wowcharfb.models.Character;

/**
 * Created by phil_ on 04-03-2017.
 */

public interface CharacterCallback {
    void created(Character character);

    void noName();

    void noFaction();

    void noClass();
}
