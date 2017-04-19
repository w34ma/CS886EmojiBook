package edu.uw.w34ma.models;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 28/03/17.
 */
public class ACTAction {
    public final String label;
    public final double EM;
    public final double PM;
    public final double AM;
    public final double EF;
    public final double PF;
    public final double AF;

    public ACTAction(String label, double EM, double PM, double AM, double EF, double PF, double AF) {
        this.label = label;
        this.EM = EM;
        this.PM = PM;
        this.AM = AM;
        this.EF = EF;
        this.PF = PF;
        this.AF = AF;
    }
}
