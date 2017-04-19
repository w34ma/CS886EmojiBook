package edu.uw.w34ma.models;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 26/03/17.
 */
public class BookCharacter {
    public enum Gender {
        MALE, FEMALE
    }

    public final String name;
    public final String identity;
    public final Gender gender;

    public BookCharacter(String name, String identity, Gender gender) {
        this.name = name;
        this.identity = identity;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return name + " (" + identity + ", " + gender.name() + ")";
    }
}
