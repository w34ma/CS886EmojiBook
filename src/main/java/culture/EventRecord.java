package culture;

import java.awt.Frame;

/** The class defining data on a setting or an identification. */

public class EventRecord {

	// Concept gate definitions also in Data module.
	static final int VIEWER = 0; 
		// For getting viewer's sex and setting.

	static final int SITUATION = 1; 
		// For verbalizing an event.

	static final int QUANTITIES = 2; 
		// For assembling fundamentals, in and out transients.

	static final int DEFLECTION1 = 3; 
		// For reporting deflection in Basic mode.

	static final int DEFLECTION2 = 4; 
		// For reporting deflection in Advanced mode.

	static final int MAX_PAIR_CONCEPT_GATES = Data.NUMBER_PAIR_CONCEPT_GATES;

	static final int MAX_DIVISION_CONCEPT_GATES =
		Data.NUMBER_DIVISION_CONCEPT_GATES;

	static final int MAX_COMPLEX_CONCEPT_GATES =
		Data.NUMBER_COMPLEX_CONCEPT_GATES;

	static final boolean[] ALL_FALSE_PAIR_CONCEPT_GATE = { false, false };

	static final boolean[] ALL_FALSE_DIVISION_CONCEPT_GATE = { false, false,
		false, false, false, false, false, false, false };

	public static final boolean[] ALL_FALSE_COMPLEX_CONCEPT_GATE = { false, false,
		false };

	static final boolean[] ALL_TRUE_PAIR_CONCEPT_GATE = { true, true };

	static final boolean[] ALL_TRUE_DIVISION_CONCEPT_GATE = { true, true, true,
		true, true, true, true, true, true };

	static final boolean[] ALL_TRUE_COMPLEX_CONCEPT_GATE = { true, true, true };

	public static final boolean[] EMOTION_DIVISION_CONCEPT_GATE = { true, false,
		false, false, false, false, false, false, false };

	public static final boolean[] TRAIT_DIVISION_CONCEPT_GATE = { false, true, false,
		false, false, false, false, false, false };

	public int[] vao; // Viewer, actor, object.

	public int[][] abosIndexes; // [4][2]
		// Actor, behavior, object, setting dictionary indexes 
		// for modifier, noun/verb designator.

	public double[][] abosFundamentals, abosTransientsIn, abosTransientsOut,
			abosModifier;

	boolean[][] abosPairConceptGates, abosDivisionConceptGates,
			abosComplexConceptGates;

	double deflection;

	public boolean beginNewAnalysis, restartAtZero;

	public String simultaneityCode;

	boolean eventConceptGatesHaveBeenSet;

	boolean[] actorPairConceptGate = { false, false };

	boolean[] objectPairConceptGate = { false, false };

	boolean[] actorBehaviorPairConceptGate = { true, false };

	boolean[] objectBehaviorPairConceptGate = { true, false };

	boolean[] actorDivisionConceptGate = { false, false, false, false, false,
		false, false, false, false };

	boolean[] objectDivisionConceptGate = { false, false, false, false, false,
		false, false, false, false };

	boolean[] actorBehaviorDivisionConceptGate = { false, false, false, false,
		false, false, false, false, false };

	boolean[] objectBehaviorDivisionConceptGate = { false, false, false, false,
		false, false, false, false, false };

	boolean[] actorComplexConceptGate = { false, false, false };

	boolean[] objectComplexConceptGate = { false, false, false };

	boolean[] actorBehaviorComplexConceptGate = { false, false, false };

	boolean[] objectBehaviorComplexConceptGate = { false, false, false };

	public EventRecord(int theViewer, int theActor, int theBehavior, int theObject) {
		// Constructor method.
		beginNewAnalysis = false;
		vao = new int[3];
		vao[0] = theViewer; // Index from 0 to numberOfInteractants.
		vao[1] = theActor; // Index from 0 to numberOfInteractants.
		vao[2] = theObject; // Index from 0 to numberOfInteractants.
		abosIndexes = new int[4][2];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				abosIndexes[i][j] = -1;
			}
		}
		// theBehavior is index of behavior in behaviors list,
		// or -1 if the behavior is an unknown.
		abosIndexes[1][1] = theBehavior; 
		abosFundamentals = new double[4][3];
		abosTransientsIn = new double[4][3];
		abosTransientsOut = new double[4][3];
		abosModifier = new double[4][3];
		for (int i = 0; i < 4; i++) {
			for (int epa = 0; epa < 3; epa++) {
				abosFundamentals[i][epa] = Interact.MISSING_VALUE;
				abosTransientsIn[i][epa] = Interact.MISSING_VALUE;
				abosTransientsOut[i][epa] = Interact.MISSING_VALUE;
				abosModifier[i][epa] = Interact.MISSING_VALUE;
			}
		}
		abosPairConceptGates = new boolean[4][2];
		abosDivisionConceptGates = new boolean[4][MAX_DIVISION_CONCEPT_GATES];
		abosComplexConceptGates = new boolean[4][3];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				abosPairConceptGates[i][j] = false;
			}
			for (int j = 0; j < MAX_DIVISION_CONCEPT_GATES; j++) {
				abosDivisionConceptGates[i][j] = false;
			}
			for (int j = 0; j < 3; j++) {
				abosComplexConceptGates[i][j] = false;
			}
		}
		deflection = -1;
		eventConceptGatesHaveBeenSet = false;
		simultaneityCode = "";
	} // End EventRecord constructor.

	public String toString(int typeOfReport) {
		// Transform an event to textual form.
		String output = "";
		int index;
		String comma = Interact.InteractText.getString("comma");
		String leftBracket = Interact.InteractText.getString("leftBracket");
		String rightBracket = Interact.InteractText.getString("rightBracket");
		String space = Interact.InteractText.getString("space");
		String newLine = Interact.InteractText.getString("paragraphCommand");
		String colon = Interact.InteractText.getString("colon");
		String dotSpace = Interact.InteractText.getString("sentenceSeparation");
		String fundamentalTitle =
			Interact.InteractText.getString("fundamental");
		String transientTitle = Interact.InteractText.getString("transient");
		String inputsTitle = Interact.InteractText.getString("inputs");
		String outputsTitle = Interact.InteractText.getString("outcomes");

		switch (typeOfReport) {
		// VIEWER = 0; SITUATION = 1; QUANTITIES = 2; DEFLECTION1 = 3;
		// DEFLECTION2 = 4
		case 0:
			// Viewer sex and setting.
			output =
				Interact.InteractText.getString("experiences")
					+ Interact.person[this.vao[0]].name + space
					+ Interact.identifyViewerSexAndSetting(this.vao[0])
					+ newLine;
			break;
		case 1:
			// Event meta-markers.
			if (this.beginNewAnalysis) {
				if (this.restartAtZero) {
					output = output + "#,";
				} else {
					output = output + "$,";
				}
			}
			if (this.simultaneityCode != "") {
				output = output + this.simultaneityCode + ",";
			}
			// Actor's modifier-identity, behavior, object's modifier-identity.
			output = output + Interact.person[this.vao[1]].name + leftBracket;
			// Actor modifier.
			index = this.abosIndexes[0][0]; // Actor modifier.
			if (index > -1) {
				output = output + Interact.modifiers.getWord(index) + comma;
			} else {
				output =
					output + Interact.InteractText.getString("empty") + comma;
			}
			// Actor identity.
			index = this.abosIndexes[0][1]; // Actor identity.
			if (index < 0) {
				// No actor identification.
				Cancel_OK_Dialog badRecord =
					new Cancel_OK_Dialog((Frame) Interact.appletFrame,
						Interact.InteractText.getString("eventProblemTitle"),
						Interact.storeErrorLines[1],
						Interact.InteractText.getString("ok"), null);
				badRecord.setVisible(true);
				return "";
			}
			output =
				output + Interact.identities.getWord(index) + rightBracket
					+ comma;
			// Behavior.
			index = this.abosIndexes[1][1]; // Behavior.
			if (index > -1) {
				output = output + Interact.behaviors.getWord(index) + comma;
			} else {
				output =
					output + Interact.InteractText.getString("empty") + comma;
			}
			// Object person number.
			output = output + Interact.person[this.vao[2]].name + leftBracket;
			// Object modifier.
			index = this.abosIndexes[2][0]; // Object modifier.
			if (index > -1) {
				output = output + Interact.modifiers.getWord(index) + comma;
			} else {
				output =
					output + Interact.InteractText.getString("empty") + comma;
			}
			// Object identity.
			index = this.abosIndexes[2][1]; // Object identity.
			if (index < 0) {
				// No object identification.
				Cancel_OK_Dialog badRecord =
					new Cancel_OK_Dialog((Frame) Interact.appletFrame,
						Interact.InteractText.getString("eventProblemTitle"),
						Interact.storeErrorLines[6],
						Interact.InteractText.getString("ok"), null);
				badRecord.setVisible(true);
				return "";
			}
			output =
				output + Interact.identities.getWord(index) + rightBracket
					+ newLine;
			break;

		case 2:
			// Fundamentals.
			output =
				Interact.InteractText.getString("actor") + space
					+ fundamentalTitle + colon;
			for (int epa = 0; epa < 3; epa++) {
				output =
					output
						+ Interact
							.formatLocaleDecimal(this.abosFundamentals[0][epa]);
				if (epa == 2) {
					output = output + dotSpace;
				}
			}
			output =
				output + Interact.InteractText.getString("behavior") + space
					+ fundamentalTitle + colon;
			for (int epa = 0; epa < 3; epa++) {
				output =
					output
						+ Interact
							.formatLocaleDecimal(this.abosFundamentals[1][epa]);
				if (epa == 2) {
					output = output + dotSpace;
				}
			}
			output =
				output + Interact.InteractText.getString("object") + space
					+ fundamentalTitle + colon;
			for (int epa = 0; epa < 3; epa++) {
				output =
					output
						+ Interact
							.formatLocaleDecimal(this.abosFundamentals[2][epa]);
				if (epa == 2) {
					output = output + dotSpace;
				}
			}
			output =
				output + Interact.InteractText.getString("settingTitle")
					+ space + fundamentalTitle + colon;
			for (int epa = 0; epa < 3; epa++) {
				output =
					output
						+ Interact
							.formatLocaleDecimal(this.abosFundamentals[3][epa]);
				if (epa == 2) {
					output = output + dotSpace;
				}
			}
			output = output + newLine;

			// Input transients.
			output =
				output + Interact.InteractText.getString("actor") + space
					+ transientTitle + inputsTitle + colon;
			for (int epa = 0; epa < 3; epa++) {
				output =
					output
						+ Interact
							.formatLocaleDecimal(this.abosTransientsIn[0][epa]);
				if (epa == 2) {
					output = output + dotSpace;
				}
			}
			output =
				output + Interact.InteractText.getString("behavior") + space
					+ transientTitle + inputsTitle + colon;
			for (int epa = 0; epa < 3; epa++) {
				output =
					output
						+ Interact
							.formatLocaleDecimal(this.abosTransientsIn[1][epa]);
				if (epa == 2) {
					output = output + dotSpace;
				}
			}
			output =
				output + Interact.InteractText.getString("object") + space
					+ transientTitle + inputsTitle + colon;
			for (int epa = 0; epa < 3; epa++) {
				output =
					output
						+ Interact
							.formatLocaleDecimal(this.abosTransientsIn[2][epa]);
				if (epa == 2) {
					output = output + dotSpace;
				}
			}
			output =
				output + Interact.InteractText.getString("settingTitle")
					+ space + transientTitle + inputsTitle + colon;
			for (int epa = 0; epa < 3; epa++) {
				output =
					output
						+ Interact
							.formatLocaleDecimal(this.abosTransientsIn[3][epa]);
				if (epa == 2) {
					output = output + dotSpace;
				}
			}
			output = output + newLine;

			// Output transients.
			output =
				output + Interact.InteractText.getString("actor") + space
					+ transientTitle + outputsTitle + colon;
			for (int epa = 0; epa < 3; epa++) {
				output =
					output
						+ Interact
							.formatLocaleDecimal(this.abosTransientsOut[0][epa]);
				if (epa == 2) {
					output = output + dotSpace;
				}
			}
			output =
				output + Interact.InteractText.getString("behavior") + space
					+ transientTitle + outputsTitle + colon;
			for (int epa = 0; epa < 3; epa++) {
				output =
					output
						+ Interact
							.formatLocaleDecimal(this.abosTransientsOut[1][epa]);
				if (epa == 2) {
					output = output + dotSpace;
				}
			}
			output =
				output + Interact.InteractText.getString("object") + space
					+ transientTitle + outputsTitle + colon;
			for (int epa = 0; epa < 3; epa++) {
				output =
					output
						+ Interact
							.formatLocaleDecimal(this.abosTransientsOut[2][epa]);
				if (epa == 2) {
					output = output + dotSpace;
				}
			}
			output =
				output + Interact.InteractText.getString("settingTitle")
					+ space + transientTitle + outputsTitle + colon;
			for (int epa = 0; epa < 3; epa++) {
				output =
					output
						+ Interact
							.formatLocaleDecimal(this.abosTransientsOut[3][epa]);
				if (epa == 2) {
					output = output + dotSpace;
				}
			}
			break;

		case 3:
			// Deflection
			output =
				output + Interact.InteractText.getString("deflection") + colon
					+ Interact.formatLocaleDecimal(this.deflection) + dotSpace
					+ newLine;
			break;

		case 4:
			// Deflection for expert
			double difference,
			actorD,
			objectD;
			actorD = objectD = 0;
			for (int epa = 0; epa < 3; epa++) {
				difference =
					this.abosFundamentals[0][epa]
						- this.abosTransientsOut[0][epa];
				actorD = actorD + difference * difference;
				difference =
					this.abosFundamentals[2][epa]
						- this.abosTransientsOut[2][epa];
				objectD = objectD + difference * difference;
			}
			output = output + newLine;
			output =
				output + Interact.InteractText.getString("deflection") + colon
					+ Interact.formatLocaleDecimal(this.deflection) + comma
					+ space + space + Interact.InteractText.getString("actor")
					+ colon + Interact.formatLocaleDecimal(actorD) + comma
					+ space + space + Interact.InteractText.getString("obj")
					+ colon + Interact.formatLocaleDecimal(objectD) + dotSpace
					+ newLine;
		}

		return output;
	} // End toString.

	public void setConceptGates() {
		// Set up concept gates for this event.
		// The gates determine what concepts are presented in lists
		// on the Analyze_events form.
		boolean setOneOrMoreConceptGates;
		if (eventConceptGatesHaveBeenSet) { return; }

		// MODIFIERS
		System.arraycopy(ALL_FALSE_DIVISION_CONCEPT_GATE, 0,
			EMOTION_DIVISION_CONCEPT_GATE, 0, MAX_DIVISION_CONCEPT_GATES);
		EMOTION_DIVISION_CONCEPT_GATE[0] = true;
		System.arraycopy(ALL_FALSE_DIVISION_CONCEPT_GATE, 0,
			TRAIT_DIVISION_CONCEPT_GATE, 0, MAX_DIVISION_CONCEPT_GATES);
		TRAIT_DIVISION_CONCEPT_GATE[1] = true;

		/* BEHAVIORS */
		/*
		 * By default, the pair ConceptGate is set to include overt behaviors
		 * and exclude surmised behaviors. If the user unchecks overtCheckbox on
		 * the DefineEvents form both kinds of behaviors are presented when
		 * analyzing events. actorBehaviorPairConceptGate[0] always is true.
		 */
		actorBehaviorPairConceptGate[0] = true;
		actorBehaviorPairConceptGate[1] =
			!Interact.defineEvents.overtCheckbox.getState();
		/*
		 * An institutional behavior ConceptGate is on if both interactants are
		 * in that institution. Use a laity relation if nothing else.
		 */
		setOneOrMoreConceptGates = false;
		for (int i = 0; i < MAX_DIVISION_CONCEPT_GATES; i++) {
			actorBehaviorDivisionConceptGate[i] =
				(abosDivisionConceptGates[0][i] & abosDivisionConceptGates[2][i]);
			setOneOrMoreConceptGates =
				setOneOrMoreConceptGates | actorBehaviorDivisionConceptGate[i];
		}
		if (!setOneOrMoreConceptGates) {
			actorBehaviorDivisionConceptGate[0] = true;
		}
		System.arraycopy(actorBehaviorDivisionConceptGate, 0,
			objectBehaviorDivisionConceptGate, 0, MAX_DIVISION_CONCEPT_GATES);

		// Complex behavior ConceptGates.
		System.arraycopy(ALL_FALSE_COMPLEX_CONCEPT_GATE, 0,
			actorBehaviorComplexConceptGate, 0, 3);
		System.arraycopy(ALL_FALSE_COMPLEX_CONCEPT_GATE, 0,
			objectBehaviorComplexConceptGate, 0, 3);
		if (vao[1] == vao[2]) {
			/*
			 * Choose monadic identities for self-directed events, and turn off
			 * institutional ConceptGateing so ONLY monadic behaviors are
			 * presented.
			 */
			actorBehaviorComplexConceptGate[0] = true;
			System
				.arraycopy(ALL_FALSE_DIVISION_CONCEPT_GATE, 0,
					actorBehaviorDivisionConceptGate, 0,
					MAX_DIVISION_CONCEPT_GATES);
			// Turng off all object ConceptGates so no object behaviors are
			// presented.
			System.arraycopy(ALL_FALSE_PAIR_CONCEPT_GATE, 0,
				objectBehaviorPairConceptGate, 0, MAX_PAIR_CONCEPT_GATES);
			System.arraycopy(ALL_FALSE_DIVISION_CONCEPT_GATE, 0,
				objectBehaviorDivisionConceptGate, 0,
				MAX_DIVISION_CONCEPT_GATES);
			System.arraycopy(ALL_FALSE_COMPLEX_CONCEPT_GATE, 0,
				objectBehaviorComplexConceptGate, 0, MAX_COMPLEX_CONCEPT_GATES);
		} else {
			// Include only triadic behaviors if the user requests.
			if (Interact.defineEvents.triadicCheckbox.getState()) {
				actorBehaviorComplexConceptGate[1] = true;
				System.arraycopy(ALL_FALSE_DIVISION_CONCEPT_GATE, 0,
					actorBehaviorDivisionConceptGate, 0,
					MAX_DIVISION_CONCEPT_GATES);
			}
			// Include only corporal behaviors if the user requests.
			if (Interact.defineEvents.corporalCheckbox.getState()) {
				actorBehaviorComplexConceptGate[2] = true;
				System.arraycopy(ALL_FALSE_DIVISION_CONCEPT_GATE, 0,
					actorBehaviorDivisionConceptGate, 0,
					MAX_DIVISION_CONCEPT_GATES);
			}
		}
		System.arraycopy(actorBehaviorPairConceptGate, 0,
			objectBehaviorPairConceptGate, 0, MAX_PAIR_CONCEPT_GATES);
		System.arraycopy(actorBehaviorDivisionConceptGate, 0,
			objectBehaviorDivisionConceptGate, 0, MAX_DIVISION_CONCEPT_GATES);
		System.arraycopy(actorBehaviorComplexConceptGate, 0,
			objectBehaviorComplexConceptGate, 0, MAX_COMPLEX_CONCEPT_GATES);
		// IDENTITIES
		// Male-female identity selection corresponds to
		// selected gender of person.
		actorPairConceptGate[0] = (Interact.person[vao[1]].sex == Person.MALE);
		actorPairConceptGate[1] =
			(Interact.person[vao[1]].sex == Person.FEMALE);
		objectPairConceptGate[0] = (Interact.person[vao[2]].sex == Person.MALE);
		objectPairConceptGate[1] =
			(Interact.person[vao[2]].sex == Person.FEMALE);
		// An institutional identity ConceptGate is on if
		// both interactants have it.
		// Laity relation if nothing else.
		setOneOrMoreConceptGates = false;
		for (int i = 0; i < MAX_DIVISION_CONCEPT_GATES; i++) {
			actorDivisionConceptGate[i] =
				abosDivisionConceptGates[0][i] & abosDivisionConceptGates[2][i];
			setOneOrMoreConceptGates =
				setOneOrMoreConceptGates | actorDivisionConceptGate[i];
		}
		if (!setOneOrMoreConceptGates) {
			actorDivisionConceptGate[0] = true;
		}
		System.arraycopy(actorDivisionConceptGate, 0,
			objectDivisionConceptGate, 0, MAX_DIVISION_CONCEPT_GATES);

		eventConceptGatesHaveBeenSet = true;
	} // End setConceptGates.

}