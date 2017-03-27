package cl.philipsoft.ph1l.wowcharfb.models;

import java.io.Serializable;

/**
 * Created by phil_ on 11-02-2017.
 */

public class Character implements Serializable {
    private String id;
    private Faction characterFaction;
    private Race characterRace;
    private Class characterClass;
    private String characterName;
    private int characterLevel = 1;
    private int strength, agility, stamina, intellect, spirit;
    private double statMultiplier;
    private boolean shared;

    public boolean isShared() {
        return shared;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getIntellect() {
        return intellect;
    }

    public void setIntellect(int intellect) {
        this.intellect = intellect;
    }

    public int getSpirit() {
        return spirit;
    }

    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }

    public int getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(int characterLevel) {
        this.characterLevel = characterLevel;
    }

    public Character() {
    }

    public Character(String characterName) {
        this.characterName = characterName;
    }

    public Character(Faction characterFaction, Race characterRace, Class characterClass, String characterName, int characterLevel) {
        this.characterFaction = characterFaction;
        this.characterRace = characterRace;
        this.characterClass = characterClass;
        this.characterName = characterName;
        this.characterLevel = characterLevel;
    }

    public Character(Faction characterFaction, Race characterRace, Class characterClass, String characterName) {
        this.characterFaction = characterFaction;
        this.characterRace = characterRace;
        this.characterClass = characterClass;
        this.characterName = characterName;
    }

    public Faction getCharacterFaction() {
        return characterFaction;
    }

    public void setCharacterFaction(Faction characterFaction) {
        this.characterFaction = characterFaction;
    }

    public Race getCharacterRace() {
        return characterRace;
    }

    public void setCharacterRace(Race characterRace) {
        this.characterRace = characterRace;
    }

    public Class getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(Class characterClass) {
        this.characterClass = characterClass;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    private void setStats() {
        this.setAgility(this.getCharacterRace().getBaseAgility() + this.getCharacterClass().getBonusAgility());
        this.setIntellect(this.getCharacterRace().getBaseIntellect() + this.getCharacterClass().getBonusIntellect());
        this.setStamina(this.getCharacterRace().getBaseStamina() + this.getCharacterClass().getBonusStamina());
        this.setStrength(this.getCharacterRace().getBaseStrength() + this.getCharacterClass().getBonusStrength());
        this.setSpirit(this.getCharacterRace().getBaseSpirit() + this.getCharacterClass().getBonusSpirit());
    }

    public void setStatsByLvl(int lvl) {
        statMultiplier = ((lvl - 1) * 0.2);
        this.setStats();
        this.setAgility((int) (this.getAgility() + statMultiplier));
        this.setStamina((int) (this.getStamina() + statMultiplier));
        this.setIntellect((int) (this.getIntellect() + statMultiplier));
        this.setStrength((int) (this.getStrength() + statMultiplier));
        this.setSpirit((int) (this.getSpirit() + statMultiplier));
    }
}
