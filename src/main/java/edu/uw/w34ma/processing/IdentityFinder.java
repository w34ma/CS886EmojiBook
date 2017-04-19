package edu.uw.w34ma.processing;

import culture.Identities;
import edu.uw.w34ma.models.ACTIdentity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 06/04/17.
 */
public class IdentityFinder {
    private final Map<String, ACTIdentity> identities;

    public IdentityFinder() {
        this.identities = loadIdentities();
    }

    private Map<String, ACTIdentity> loadIdentities() {
        Map<String, ACTIdentity> identities = new HashMap<>();

        String[] identityData = (String[]) new Identities().getContents()[1][1];
        String label = null;
        double EM = 0f, PM = 0f, AM = 0f, EF = 0f, PF = 0f, AF = 0f;
        for (int i = 0; i < identityData.length; i++) {
            if (i % 8 == 0) {
                label = identityData[i];
            } else if (i % 8 == 1) {
                EM = Double.parseDouble(identityData[i]);
            } else if (i % 8 == 2) {
                PM = Double.parseDouble(identityData[i]);
            } else if (i % 8 == 3) {
                AM = Double.parseDouble(identityData[i]);
            } else if (i % 8 == 4) {
                EF = Double.parseDouble(identityData[i]);
            } else if (i % 8 == 5) {
                PF = Double.parseDouble(identityData[i]);
            } else if (i % 8 == 6) {
                AF = Double.parseDouble(identityData[i]);
            } else {
                identities.put(label, new ACTIdentity(label, EM, PM, AM, EF, PF, AF));
            }
        }
        return identities;
    }

    public ACTIdentity findByLabel(String label) {
        return identities.getOrDefault(label, null);
    }
}
