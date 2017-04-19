package culture;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;

/** Page for reporting results of simulations in text form. */
class ViewReportCard extends Panel {

	Label title = new Label();

	TextArea results = new TextArea();

	BorderLayout layout = new BorderLayout(0, 0);

	public ViewReportCard() { // Constructor method.
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(layout);
		title.setText(Interact.InteractText.getString("resultsHeader"));
		title.setAlignment(Label.CENTER);
		this.add(title, BorderLayout.NORTH);
		this.add(results, BorderLayout.CENTER);
	}

	static String dictionaryStatistics() {
		Data entry;
		double N, mean, variance;
		double[] sumProfile = new double[6];
		double[] squaresProfile = new double[6];
		for (int i = 0; i < 6; i++) {
			sumProfile[i] = 0;
			squaresProfile[i] = 0;
		}
		N = Interact.identities.size();
		String resultText =
			Interact.IdentitiesText.getString("Source")
				+ Interact.InteractText.getString("dicIdentities")
				+ Interact.formatLocaleDecimal(N);
		for (int i = 0; i < Interact.identities.size(); i++) {
			// For each entry in identities dictionary:
			entry = (Data) Interact.identities.elementAt(i);
			for (int j = 0; j < 3; j++) {
				sumProfile[j] = sumProfile[j] + entry.maleEPA[j];
				sumProfile[j + 3] = sumProfile[j + 3] + entry.femaleEPA[j];
				squaresProfile[j] =
					squaresProfile[j] + entry.maleEPA[j] * entry.maleEPA[j];
				squaresProfile[j + 3] =
					squaresProfile[j + 3] + entry.femaleEPA[j]
						* entry.maleEPA[j];
			}
		}
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand")
				+ Interact.InteractText.getString("dicMeans");
		for (int i = 0; i < 6; i++) {
			mean = sumProfile[i] / N;
			resultText = resultText + Interact.formatLocaleDecimal(mean);
		}
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand")
				+ Interact.InteractText.getString("dicVars");
		for (int i = 0; i < 6; i++) {
			variance =
				(N * squaresProfile[i] + sumProfile[i] * sumProfile[i])
					/ (N * N);
			resultText = resultText + Interact.formatLocaleDecimal(variance);
		}
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand");
		for (int i = 0; i < 6; i++) {
			sumProfile[i] = 0;
			squaresProfile[i] = 0;
		}
		N = Interact.behaviors.size();
		resultText =
			resultText + Interact.BehaviorsText.getString("Source")
				+ Interact.InteractText.getString("dicBehaviors")
				+ Interact.formatLocaleDecimal(N);
		for (int i = 0; i < Interact.behaviors.size(); i++) {
			// For each entry in behaviors dictionary:
			entry = (Data) Interact.behaviors.elementAt(i);
			for (int j = 0; j < 3; j++) {
				sumProfile[j] = sumProfile[j] + entry.maleEPA[j];
				sumProfile[j + 3] = sumProfile[j + 3] + entry.femaleEPA[j];
				squaresProfile[j] =
					squaresProfile[j] + entry.maleEPA[j] * entry.maleEPA[j];
				squaresProfile[j + 3] =
					squaresProfile[j + 3] + entry.femaleEPA[j]
						* entry.maleEPA[j];
			}
		}
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand")
				+ Interact.InteractText.getString("dicMeans");
		for (int i = 0; i < 6; i++) {
			mean = sumProfile[i] / N;
			resultText = resultText + Interact.formatLocaleDecimal(mean);
		}
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand")
				+ Interact.InteractText.getString("dicVars");
		for (int i = 0; i < 6; i++) {
			variance =
				(N * squaresProfile[i] + sumProfile[i] * sumProfile[i])
					/ (N * N);
			resultText = resultText + Interact.formatLocaleDecimal(variance);
		}
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand");
		for (int i = 0; i < 6; i++) {
			sumProfile[i] = 0;
			squaresProfile[i] = 0;
		}
		N = Interact.modifiers.size();
		resultText =
			resultText + Interact.ModifiersText.getString("Source")
				+ Interact.InteractText.getString("dicModifiers")
				+ Interact.formatLocaleDecimal(N);
		for (int i = 0; i < Interact.modifiers.size(); i++) {
			// For each entry in modifiers dictionary:
			entry = (Data) Interact.modifiers.elementAt(i);
			for (int j = 0; j < 3; j++) {
				sumProfile[j] = sumProfile[j] + entry.maleEPA[j];
				sumProfile[j + 3] = sumProfile[j + 3] + entry.femaleEPA[j];
				squaresProfile[j] =
					squaresProfile[j] + entry.maleEPA[j] * entry.maleEPA[j];
				squaresProfile[j + 3] =
					squaresProfile[j + 3] + entry.femaleEPA[j]
						* entry.maleEPA[j];
			}
		}
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand")
				+ Interact.InteractText.getString("dicMeans");
		for (int i = 0; i < 6; i++) {
			mean = sumProfile[i] / N;
			resultText = resultText + Interact.formatLocaleDecimal(mean);
		}
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand")
				+ Interact.InteractText.getString("dicVars");
		for (int i = 0; i < 6; i++) {
			variance =
				(N * squaresProfile[i] + sumProfile[i] * sumProfile[i])
					/ (N * N);
			resultText = resultText + Interact.formatLocaleDecimal(variance);
		}
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand");
		for (int i = 0; i < 6; i++) {
			sumProfile[i] = 0;
			squaresProfile[i] = 0;
		}
		N = Interact.settings.size();
		resultText =
			resultText + Interact.SettingsText.getString("Source")
				+ Interact.InteractText.getString("dicSettings")
				+ Interact.formatLocaleDecimal(N);
		for (int i = 0; i < Interact.settings.size(); i++) {
			// For each entry in settings dictionary:
			entry = (Data) Interact.settings.elementAt(i);
			for (int j = 0; j < 3; j++) {
				sumProfile[j] = sumProfile[j] + entry.maleEPA[j];
				sumProfile[j + 3] = sumProfile[j + 3] + entry.femaleEPA[j];
				squaresProfile[j] =
					squaresProfile[j] + entry.maleEPA[j] * entry.maleEPA[j];
				squaresProfile[j + 3] =
					squaresProfile[j + 3] + entry.femaleEPA[j]
						* entry.maleEPA[j];
			}
		}
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand")
				+ Interact.InteractText.getString("dicMeans");
		for (int i = 0; i < 6; i++) {
			mean = sumProfile[i] / N;
			resultText = resultText + Interact.formatLocaleDecimal(mean);
		}
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand")
				+ Interact.InteractText.getString("dicVars");
		for (int i = 0; i < 6; i++) {
			variance =
				(N * squaresProfile[i] + sumProfile[i] * sumProfile[i])
					/ (N * N);
			resultText = resultText + Interact.formatLocaleDecimal(variance);
		}
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand");
		resultText =
			resultText + Interact.InteractText.getString("paragraphCommand");
		return resultText;
	} // End dictionaryStatistics.

} // End class ViewReportCard.
