package culture;

import java.awt.*;

/*
 Self analysis involves four classes: SelfIdentitiesCard, IdentitiesChartSubcard, IdentitiesListsSubcard, and Scatter.
 */
public class IdentitiesListsSubcard extends Panel {
	Panel selfActualizingList = new Panel();

	Panel circuitingList = new Panel();

	Label selfActualizingTitle = new Label();

	Label circuitingTitle = new Label();

	TextArea selfActualizingIdentities = new TextArea(20, 40);

	TextArea circuitingIdentities = new TextArea(20, 40);

	VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();

	VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();

	GridLayout gridLayout1 = new GridLayout(1, 2);

	public IdentitiesListsSubcard() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(gridLayout1);
		selfActualizingList.setLayout(verticalFlowLayout1);
		circuitingList.setLayout(verticalFlowLayout2);
		selfActualizingTitle.setAlignment(Label.CENTER);
		selfActualizingTitle.setText(Interact.InteractText
			.getString("actualizingList"));
		circuitingTitle.setText(Interact.InteractText
			.getString("circuitingList"));
		circuitingTitle.setAlignment(Label.CENTER);
		selfActualizingIdentities.setText("");
		circuitingIdentities.setText("");
		this.add(selfActualizingList, null);
		this.add(circuitingList, null);
		selfActualizingList.add(selfActualizingTitle, null);
		circuitingList.add(circuitingTitle, null);
		selfActualizingList.add(selfActualizingIdentities, null);
		circuitingList.add(circuitingIdentities, null);

	} // End jbInit.

	void fillIdentitiesLists() {
		DataList dic, matches;
		Retrieval relevantIdentityLine;
		String identityWord, distance, textLine;
		double[] selfProfile, divergenceProfile;
		double[] referenceProfile = { 0, 0, 0 };

		if (Interact.analyzeSelf.identitiesChart.viewIdentitiesScatter.identitySequence
			.size() == 0) {
			selfActualizingTitle.setText(Interact.InteractText
				.getString("actualizingList"));
			circuitingTitle.setText(Interact.InteractText
				.getString("circuitingList"));
		} else {
			selfActualizingTitle.setText(Interact.InteractText
				.getString("redeemingList"));
			circuitingTitle.setText(Interact.InteractText
				.getString("distantList"));
		}
		dic = Interact.identities;
		selfProfile =
			Interact.readProfile(Interact.analyzeSelf.selfEPA.getText());
		divergenceProfile =
			((Retrieval) Interact.analyzeSelf.identitiesChart.viewIdentitiesScatter.cumulativeDivergence).profile;
		for (int epa = 0; epa < 3; epa++) {
			// Self sentiment is the reference point when no identities have
			// been clicked (divergence = 0).
			// Otherwise the reference point corresponds to the complement of
			// the cumulative divergence.
			referenceProfile[epa] = selfProfile[epa] - divergenceProfile[epa];
		}
		matches = new DataList();
		matches =
			dic.getMatches(referenceProfile,
				Interact.analyzeSelf.maleRater.getState(),
				EventRecord.ALL_TRUE_PAIR_CONCEPT_GATE,
				EventRecord.ALL_TRUE_DIVISION_CONCEPT_GATE,
				EventRecord.ALL_FALSE_COMPLEX_CONCEPT_GATE);
		selfActualizingIdentities.setText("");
		// Print the self profile and cumulative divergence at top of box of
		// actualizing identities.
		textLine = Interact.InteractText.getString("selfProfile") + ":";
		for (int epa = 0; epa < 3; epa++) {
			textLine =
				textLine + Interact.formatLocaleDecimal(selfProfile[epa]);
		}
		textLine = textLine + "\n";
		textLine =
			textLine + Interact.InteractText.getString("divergenceProfile")
				+ ":";
		for (int epa = 0; epa < 3; epa++) {
			textLine =
				textLine + Interact.formatLocaleDecimal(divergenceProfile[epa]);
		}
		textLine = textLine + "\n" + "\n";
		selfActualizingIdentities.append(textLine);
		// List the actualizing identities, within institutions.
		for (int i = 0; i < Data.NUMBER_DIVISION_CONCEPT_GATES; i++) {
			selfActualizingIdentities
				.append(Interact.identityConceptGateLines[i + 2] + "\n");
			for (int j = 0; j < matches.size(); j++) {
				relevantIdentityLine = (Retrieval) matches.elementAt(j);
				identityWord = relevantIdentityLine.word;
				distance = Interact.formatLocaleDecimal(relevantIdentityLine.D);
				if ((relevantIdentityLine.D <= Interact.analyzeSelf.actualizingRadius)
					&& (((Data) dic.elementAt(dic.getIndex(identityWord))).divisionConceptGate[i])) {
					selfActualizingIdentities.append(identityWord + "\t"
						+ distance + "\n");
				}
			}
			selfActualizingIdentities.append("\n");
		}
		// List circuiting identities in the second box.
		circuitingIdentities.setText("");
		for (int i = 0; i < Data.NUMBER_DIVISION_CONCEPT_GATES; i++) {
			circuitingIdentities
				.append(Interact.identityConceptGateLines[i + 2] + "\n");
			for (int j = 0; j < matches.size(); j++) {
				relevantIdentityLine = (Retrieval) matches.elementAt(j);
				identityWord = relevantIdentityLine.word;
				distance = Interact.formatLocaleDecimal(relevantIdentityLine.D);
				if ((relevantIdentityLine.D > Interact.analyzeSelf.actualizingRadius)
					&& (relevantIdentityLine.D <= Interact.analyzeSelf.circuitRadius)
					&& (((Data) dic.elementAt(dic.getIndex(identityWord))).divisionConceptGate[i])) {
					circuitingIdentities.append(identityWord + "\t" + distance
						+ "\n");
				}
			}
			circuitingIdentities.append("\n");
		}
	} // End fillIdentitiesLists.

}