package culture;

import java.awt.*;
import java.awt.event.*;

/* Dialog for viewing and resetting ConceptGates. */
public class ConceptGatesPopup extends Dialog {

	/*
	 * Explanation of concept gates (used in DataList).
	 * 
	 * Three groups of qualitative codes apply to every concept.
	 * 
	 * The first group ("pair concept gates") splits the entries into two groups
	 * - Male and Female for identities, Overt and Surmised for behaviors.
	 * (Interact selects only Overt behaviors, unless you change the selection
	 * using the concept gates dialog.) Places and Times for settings. (The
	 * places/times division is not implemented in Interact currently.)
	 * Adjectives and Adverbs for modifiers. (Only adjectives work in Interact
	 * currently.) The second group (called "division concept gates" in
	 * Interact) indicates the alignment of the identity, behavior, or setting
	 * with social institutions: Lay, Business, Law, Politics, Academe,
	 * Medicine, Religion, Family or Sexual. or classifies the modifier as
	 * Emotion, Trait, Status, Feature. The third group
	 * ("complex concept gates") indicates whether the identity, behavior, or
	 * setting entails more than two people - Assemblage. the identity,
	 * behavior, or setting is appropriate for self-directed (monadic) events in
	 * which actor and object are identical. the identity, behavior, or setting
	 * entails physical contact - Corporal; no longer used in Interact. Complex
	 * concept gates are undefined for modifiers. Behavior selection: A behavior
	 * with a particular institutional code is selected if that code is True for
	 * the identities of both actor and object or if the code is True for the
	 * setting. Lay behaviors are selected if the actor and object identities do
	 * not share an institutional affiliation and no setting is specified. All
	 * institutional codes are ignored if the actor and object are identical,
	 * and the monadic concept gate is used to select behaviors. Assemblage
	 * behaviors are selected if the actor identity is Assemblage or there are
	 * more than two interactants. Corporal behaviors are selected if the actor
	 * identity is corporal. Identity selection: When a new identity (i.e., a
	 * label) is being predicted codes are used as follows. Male identities are
	 * selected if the interactant was defined as male on the Define-person
	 * screen. Female identities are reported if the interactant was defined as
	 * Female. An identity with a particular institutional code is selected if
	 * that code is True for the interactant's original identity or for the
	 * identity of the other interactant or if the code is True for the setting.
	 * The Assemblage and Corporal classifications are unused in selecting
	 * identities. Modifer selection: When emotions are being predicted, only
	 * words classified as Emotion are retrieved. When traits are being
	 * predicted, only words classified as Trait are retrieved, though a user
	 * might want to mark Emotion with this form instead in order to predict the
	 * attribution of moods.
	 * ************************************************************************
	 */

	static final int N_PAIR_CONCEPT_GATES = 2;

	static final int N_INSTITUTION_CONCEPT_GATES = 9;

	static final int N_COMPLEX_CONCEPT_GATES = 3;

	static final int N_TOTAL_CONCEPT_GATES = N_PAIR_CONCEPT_GATES
		+ N_INSTITUTION_CONCEPT_GATES + N_COMPLEX_CONCEPT_GATES;

	static final int N_MODIFIER_CONCEPT_GATES = 4;

	Checkbox[] emotionCheckbox = new Checkbox[14];

	Checkbox[] traitCheckbox = new Checkbox[14];

	Checkbox[] actorCheckbox = new Checkbox[14];

	Checkbox[] objectCheckbox = new Checkbox[14];

	Checkbox[] actorBehaviorCheckbox = new Checkbox[14];

	Checkbox[] objectBehaviorCheckbox = new Checkbox[14];

	Panel conceptGatesDialog = new Panel();

	Panel messageArea = new Panel();

	Panel boxArea = new Panel();

	Panel pBottom = new Panel();

	Label emotions = new Label();

	Label traits = new Label();

	Label actorRoles = new Label();

	Label objectRoles = new Label();

	Label behaviors = new Label();

	Label responses = new Label();

	Label messageLine1 = new Label();

	Label messageLine2 = new Label();

	Label messageLine3 = new Label();

	Button goBack = new Button();

	BorderLayout dialogLayout = new BorderLayout(10, 10);

	BorderLayout messageLayout = new BorderLayout(0, 0);

	GridLayout boxAreaLayout = new GridLayout(15, 6);

	FlowLayout bottomLayout = new FlowLayout(FlowLayout.CENTER, 15, 15);

	// Assemble event information.
	Person theViewer = Interact.person[Interact.viewer];

	DataList events = theViewer.serialEvents;

	int selectedEvent = Interact.analyzeEvents.eventList.getSelectedIndex();

	EventRecord theCurrentEvent = (EventRecord) events.elementAt(selectedEvent);

	public ConceptGatesPopup(Frame parent) { // Constructor.
		// Create the window.
		super(parent, Interact.InteractText.getString("conceptGates"), true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
			add(conceptGatesDialog);
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		// Specify a LayoutManager for main panel.
		conceptGatesDialog.setLayout(dialogLayout);
		conceptGatesDialog.setBackground(Color.yellow);

		// Put the message labels at the top of the window.
		messageArea.setLayout(messageLayout);
		conceptGatesDialog.add(messageArea, BorderLayout.NORTH);
		messageLine1
			.setText(Interact.InteractText.getString("conceptGateMsg1"));
		messageLine1.setAlignment(Label.CENTER);
		messageArea.add(messageLine1, BorderLayout.NORTH);
		messageLine2
			.setText(Interact.InteractText.getString("conceptGateMsg2"));
		messageLine2.setAlignment(Label.CENTER);
		messageArea.add(messageLine2, BorderLayout.CENTER);
		messageLine3
			.setText(Interact.InteractText.getString("conceptGateMsg3"));
		messageLine3.setAlignment(Label.CENTER);
		messageArea.add(messageLine3, BorderLayout.SOUTH);

		// Create main panel for checkboxes.
		boxArea.setLayout(boxAreaLayout);
		conceptGatesDialog.add(boxArea, BorderLayout.CENTER);
		// Add column titles.
		emotions.setText(Interact.InteractText.getString("emotions"));
		traits.setText(Interact.InteractText.getString("traits"));
		actorRoles.setText(Interact.InteractText.getString("actorRoles"));
		objectRoles.setText(Interact.InteractText.getString("objectRoles"));
		behaviors.setText(Interact.InteractText.getString("behaviors"));
		responses.setText(Interact.InteractText.getString("responses"));
		boxArea.add(emotions, null);
		boxArea.add(traits, null);
		boxArea.add(actorRoles, null);
		boxArea.add(objectRoles, null);
		boxArea.add(behaviors, null);
		boxArea.add(responses, null);

		for (int i = 0; i < N_TOTAL_CONCEPT_GATES; i++) {
			// Distribute checkboxes into six columns of box layout.
			if (i < N_PAIR_CONCEPT_GATES) {
				emotionCheckbox[i] =
					new Checkbox(Interact.modifierConceptGateLines[i]);
				boxArea.add(emotionCheckbox[i], null);
				emotionCheckbox[i].setState(true);
				// Pair checkboxes hidden for modifiers.
				emotionCheckbox[i].setVisible(false);

				traitCheckbox[i] =
					new Checkbox(Interact.modifierConceptGateLines[i]);
				boxArea.add(traitCheckbox[i], null);
				traitCheckbox[i].setState(true);
				// Pair checkboxes hidden for modifiers.
				traitCheckbox[i].setVisible(false);

				actorCheckbox[i] =
					new Checkbox(Interact.identityConceptGateLines[i]);
				boxArea.add(actorCheckbox[i], null);
				actorCheckbox[i]
					.setState(theCurrentEvent.actorPairConceptGate[i]);
				actorCheckbox[i].setForeground(Color.red);

				objectCheckbox[i] =
					new Checkbox(Interact.identityConceptGateLines[i]);
				boxArea.add(objectCheckbox[i], null);
				objectCheckbox[i]
					.setState(theCurrentEvent.objectPairConceptGate[i]);
				objectCheckbox[i].setForeground(Color.red);

				actorBehaviorCheckbox[i] =
					new Checkbox(Interact.behaviorConceptGateLines[i]);
				boxArea.add(actorBehaviorCheckbox[i], null);
				actorBehaviorCheckbox[i]
					.setState(theCurrentEvent.actorBehaviorPairConceptGate[i]);
				actorBehaviorCheckbox[i].setForeground(Color.red);

				objectBehaviorCheckbox[i] =
					new Checkbox(Interact.behaviorConceptGateLines[i]);
				boxArea.add(objectBehaviorCheckbox[i], null);
				objectBehaviorCheckbox[i]
					.setState(theCurrentEvent.objectBehaviorPairConceptGate[i]);
				objectBehaviorCheckbox[i].setForeground(Color.red);
			} else if (i < N_PAIR_CONCEPT_GATES + N_INSTITUTION_CONCEPT_GATES) {
				emotionCheckbox[i] =
					new Checkbox(Interact.modifierConceptGateLines[i]);
				boxArea.add(emotionCheckbox[i], null);
				emotionCheckbox[i]
					.setState(EventRecord.EMOTION_DIVISION_CONCEPT_GATE[i - 2]);

				traitCheckbox[i] =
					new Checkbox(Interact.modifierConceptGateLines[i]);
				boxArea.add(traitCheckbox[i], null);
				traitCheckbox[i]
					.setState(EventRecord.TRAIT_DIVISION_CONCEPT_GATE[i - 2]);

				actorCheckbox[i] =
					new Checkbox(Interact.identityConceptGateLines[i]);
				boxArea.add(actorCheckbox[i], null);
				actorCheckbox[i]
					.setState(theCurrentEvent.actorDivisionConceptGate[i - 2]);

				objectCheckbox[i] =
					new Checkbox(Interact.identityConceptGateLines[i]);
				boxArea.add(objectCheckbox[i], null);
				objectCheckbox[i]
					.setState(theCurrentEvent.objectDivisionConceptGate[i - 2]);

				actorBehaviorCheckbox[i] =
					new Checkbox(Interact.behaviorConceptGateLines[i]);
				boxArea.add(actorBehaviorCheckbox[i], null);
				actorBehaviorCheckbox[i]
					.setState(theCurrentEvent.actorBehaviorDivisionConceptGate[i - 2]);

				objectBehaviorCheckbox[i] =
					new Checkbox(Interact.behaviorConceptGateLines[i]);
				boxArea.add(objectBehaviorCheckbox[i], null);
				objectBehaviorCheckbox[i]
					.setState(theCurrentEvent.objectBehaviorDivisionConceptGate[i - 2]);
			} else {
				emotionCheckbox[i] =
					new Checkbox(Interact.modifierConceptGateLines[i]);
				boxArea.add(emotionCheckbox[i], null);
				emotionCheckbox[i]
					.setState(EventRecord.ALL_FALSE_COMPLEX_CONCEPT_GATE[i - 11]);

				traitCheckbox[i] =
					new Checkbox(Interact.modifierConceptGateLines[i]);
				boxArea.add(traitCheckbox[i], null);
				traitCheckbox[i]
					.setState(EventRecord.ALL_FALSE_COMPLEX_CONCEPT_GATE[i - 11]);

				actorCheckbox[i] =
					new Checkbox(Interact.identityConceptGateLines[i]);
				boxArea.add(actorCheckbox[i], null);
				actorCheckbox[i]
					.setState(theCurrentEvent.actorComplexConceptGate[i - 11]);
				actorCheckbox[i].setForeground(Color.green);

				objectCheckbox[i] =
					new Checkbox(Interact.identityConceptGateLines[i]);
				boxArea.add(objectCheckbox[i], null);
				objectCheckbox[i]
					.setState(theCurrentEvent.objectComplexConceptGate[i - 11]);
				objectCheckbox[i].setForeground(Color.green);

				actorBehaviorCheckbox[i] =
					new Checkbox(Interact.behaviorConceptGateLines[i]);
				boxArea.add(actorBehaviorCheckbox[i], null);
				actorBehaviorCheckbox[i]
					.setState(theCurrentEvent.actorBehaviorComplexConceptGate[i - 11]);
				actorBehaviorCheckbox[i].setForeground(Color.green);

				objectBehaviorCheckbox[i] =
					new Checkbox(Interact.behaviorConceptGateLines[i]);
				boxArea.add(objectBehaviorCheckbox[i], null);
				objectBehaviorCheckbox[i]
					.setState(theCurrentEvent.objectBehaviorComplexConceptGate[i - 11]);
				objectBehaviorCheckbox[i].setForeground(Color.green);
			}
			if (Interact.modifierConceptGateLines[i].equals("")) {
				emotionCheckbox[i].setVisible(false);
				traitCheckbox[i].setVisible(false);
			}
			if (Interact.identityConceptGateLines[i].equals("")) {
				actorCheckbox[i].setVisible(false);
				objectCheckbox[i].setVisible(false);
			}
			if (Interact.behaviorConceptGateLines[i].equals("")) {
				actorBehaviorCheckbox[i].setVisible(false);
				objectBehaviorCheckbox[i].setVisible(false);
			}
		} // End for.

		// Bottom panel for centered return button and error message.
		conceptGatesDialog.add(pBottom, BorderLayout.SOUTH);
		pBottom.setLayout(bottomLayout);
		goBack.setLabel(Interact.InteractText.getString("conceptGatesButton"));
		pBottom.add(goBack, null);

		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBack_actionPerformed(e);
			}
		});
	} // End jbInit.

	public boolean storeAndCheck() {
		for (int i = 0; i < N_TOTAL_CONCEPT_GATES; i++) {
			if (i < N_PAIR_CONCEPT_GATES) {
				theCurrentEvent.actorPairConceptGate[i] =
					actorCheckbox[i].getState();
				theCurrentEvent.objectPairConceptGate[i] =
					objectCheckbox[i].getState();
				theCurrentEvent.actorBehaviorPairConceptGate[i] =
					actorBehaviorCheckbox[i].getState();
				theCurrentEvent.objectBehaviorPairConceptGate[i] =
					objectBehaviorCheckbox[i].getState();

			} else if (i < N_PAIR_CONCEPT_GATES + N_INSTITUTION_CONCEPT_GATES) {
				EventRecord.EMOTION_DIVISION_CONCEPT_GATE[i - 2] =
					emotionCheckbox[i].getState();
				EventRecord.TRAIT_DIVISION_CONCEPT_GATE[i - 2] =
					traitCheckbox[i].getState();
				theCurrentEvent.actorDivisionConceptGate[i - 2] =
					actorCheckbox[i].getState();
				theCurrentEvent.objectDivisionConceptGate[i - 2] =
					objectCheckbox[i].getState();
				theCurrentEvent.actorBehaviorDivisionConceptGate[i - 2] =
					actorBehaviorCheckbox[i].getState();
				theCurrentEvent.objectBehaviorDivisionConceptGate[i - 2] =
					objectBehaviorCheckbox[i].getState();

			} else {
				theCurrentEvent.actorComplexConceptGate[i - 11] =
					actorCheckbox[i].getState();
				theCurrentEvent.objectComplexConceptGate[i - 11] =
					objectCheckbox[i].getState();
				theCurrentEvent.actorBehaviorComplexConceptGate[i - 11] =
					actorBehaviorCheckbox[i].getState();
				theCurrentEvent.objectBehaviorComplexConceptGate[i - 11] =
					objectBehaviorCheckbox[i].getState();
			}
		}
		return true;
	} // End storeAndCheck.

	class WindowEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent evt) {
			dispose();
		}
	}

	void goBack_actionPerformed(ActionEvent e) {
		storeAndCheck();
		Interact.analyzeEvents.clearAllResults();
		Interact.analyzeEvents.implementEvent();
		dispose();
		dispose();
	}

} // End class ConceptGates.
