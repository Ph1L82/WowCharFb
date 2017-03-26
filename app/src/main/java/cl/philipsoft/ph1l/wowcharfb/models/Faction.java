package cl.philipsoft.ph1l.wowcharfb.models;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by phil_ on 11-02-2017.
 * Faction : Agrupacion de razas
 */

public class Faction extends SugarRecord implements Serializable{
    private String name;

    public Faction() {
    }

    public Faction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
