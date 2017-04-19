package culture;

/** Class defining a line, or record, in a dictionary. */
public class Data {

	static final int MAX_NUMBER_OF_CONCEPT_GATES = 32;

	// ConceptGate definitions also in EventRecord.
	static int NUMBER_PAIR_CONCEPT_GATES = 2;

	static int NUMBER_DIVISION_CONCEPT_GATES = 9;

	static int NUMBER_COMPLEX_CONCEPT_GATES = 3;

	static final int NUMBER_OF_STANDARD_CONCEPT_GATES =
		NUMBER_PAIR_CONCEPT_GATES + NUMBER_DIVISION_CONCEPT_GATES;

	static final int NUMBER_OF_CONCEPT_GATES_USED = NUMBER_PAIR_CONCEPT_GATES
		+ NUMBER_DIVISION_CONCEPT_GATES + NUMBER_COMPLEX_CONCEPT_GATES;

	static double[] NEUTRAL_EPA = { 0, 0, 0 };

	static final String ALL_TRUE_CONCEPT_GATES = "11111111111111";

	// Texts.
	String word;

	// EPA values.
	double maleEPA[], femaleEPA[];

	// ConceptGates.
	boolean pairConceptGate[], divisionConceptGate[], complexConceptGate[];

	// Highlighting for self analysis.
	int highlight;

	public Data() { // Constructor method.
	}

	public Data(String theWord, double[] theValues, boolean[] theConceptGates) {
		// Constructor method.

		// Establish the text.
		word = new String(theWord);

		// Establish the EPA variables.
		maleEPA = new double[3];
		femaleEPA = new double[3];
		for (int i = 0; i < 3; i++) {
			maleEPA[i] = theValues[i];
			femaleEPA[i] = theValues[i + 3];
		}

		// Establish the ConceptGates.
		pairConceptGate = new boolean[NUMBER_PAIR_CONCEPT_GATES];
		divisionConceptGate = new boolean[NUMBER_DIVISION_CONCEPT_GATES];
		complexConceptGate = new boolean[NUMBER_COMPLEX_CONCEPT_GATES];
		for (int i = 0; i < NUMBER_OF_CONCEPT_GATES_USED; i++) {
			if (i < NUMBER_PAIR_CONCEPT_GATES) {
				// E.g., male female
				pairConceptGate[i] = theConceptGates[i];
			} else {
				if (i < NUMBER_PAIR_CONCEPT_GATES
					+ NUMBER_DIVISION_CONCEPT_GATES) {
					// E.g., Lay, Business, Law, ...
					divisionConceptGate[i - NUMBER_PAIR_CONCEPT_GATES] =
						theConceptGates[i];
				} else {
					// Monadic, group, corporal.
					complexConceptGate[i - NUMBER_PAIR_CONCEPT_GATES
						- NUMBER_DIVISION_CONCEPT_GATES] = theConceptGates[i];
				}
			}
		}
		highlight = 0;
	}

	public String toString() {

		// Transform a line to textual form.

		String output;

		output = word + ", ";
		for (int i = 0; i < 3; i++) {
			output = output + maleEPA[i] + ", ";
		}
		for (int i = 0; i < 3; i++) {
			output = output + femaleEPA[i] + ", ";
		}
		for (int i = 0; i < NUMBER_PAIR_CONCEPT_GATES; i++) {
			if (pairConceptGate[i]) {
				output = output + "1";
			} else {
				output = output + "0";
			}
		}
		output = output + " ";
		for (int i = 0; i < NUMBER_DIVISION_CONCEPT_GATES; i++) {
			if (divisionConceptGate[i]) {
				output = output + "1";
			} else {
				output = output + "0";
			}
		}
		output = output + " ";
		for (int i = 0; i < NUMBER_COMPLEX_CONCEPT_GATES; i++) {
			if (complexConceptGate[i]) {
				output = output + "1";
			} else {
				output = output + "0";
			}
		}
		output = output + Interact.InteractText.getString("paragraphCommand");
		return output;
	}

}
