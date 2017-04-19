package edu.uw.w34ma.models;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 03/04/17.
 */
public class EmotionEvent implements BaseEvent {
    public final BookCharacter character;
    public final String emotion;
    public final double E;
    public final double P;
    public final double A;

    public EmotionEvent(BookCharacter character, String emotion, double E, double P, double A) {
        this.character = character;
        this.emotion = emotion;
        this.E = E;
        this.P = P;
        this.A = A;
    }

    @Override
    public String toString() {
        return character.name + " is [" + emotion + "]";
    }
}
