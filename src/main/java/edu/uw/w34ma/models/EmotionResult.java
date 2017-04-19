package edu.uw.w34ma.models;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 03/04/17.
 */
public class EmotionResult {
    public final BookCharacter character;
    public final double E;
    public final double P;
    public final double A;
    public final String label;

    public EmotionResult(BookCharacter character, double E, double P, double A, String label) {
        this.character = character;
        this.E = E;
        this.P = P;
        this.A = A;
        this.label = label;
    }

    @Override
    public String toString() {
        return character.toString() + ": " + label + "(" + String.format("%.2f, %.2f, %.2f", E, P, A) + ")";
    }
}
