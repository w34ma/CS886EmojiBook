package culture;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;

/**
 * Page for deriving behaviors for given identities plus emotions. Also gives
 * characteristic emotions.
 */
class FeelingEffectsCard extends Panel {
	/*******************************************************************************************************************
	 * Emotion goals screen.
	 ******************************************************************************************************************/
	static final boolean ACTOR = true;

	static final boolean OBJECT = false;

	Panel setupArea = new Panel();

	Panel analysisArea = new Panel();

	Panel ButtonPanel = new Panel();

	Panel line1 = new Panel();

	Panel line2 = new Panel();

	Panel actorSelfFundamentalPanel = new Panel();

	Panel objectSelfFundamentalPanel = new Panel();

	Panel actorSelfTransientPanel = new Panel();

	Panel objectSelfTransientPanel = new Panel();

	Label solveFor = new Label();

	Label comesClosestTo = new Label();

	Label actorTitle = new Label();

	Label actorAmalgamationTitle = new Label();

	Label objectTitle = new Label();

	Label objectAmalgamationTitle = new Label();

	Label behaviorTitle = new Label();

	Label behaviorFundamentalTitle = new Label();

	Label givenActorIdentity = new Label();

	Label givenObjectIdentity = new Label();

	CheckboxGroup sex = new CheckboxGroup();

	CheckboxGroup task = new CheckboxGroup();

	Checkbox female, male;

	Checkbox solveForActor, solveForBehavior, solveForObject;

	java.awt.List actorEmotionList = new java.awt.List();

	java.awt.List objectEmotionList = new java.awt.List();

	java.awt.List actorIdentityList = new java.awt.List();

	java.awt.List objectIdentityList = new java.awt.List();

	java.awt.List behaviorList = new java.awt.List();

	TextField actorEmotionEPA = new TextField("", 12);

	TextField actorIdentityEPA = new TextField("", 12);

	TextField actorAmalgamationEPA = new TextField("", 12);

	TextField objectEmotionEPA = new TextField("", 12);

	TextField objectIdentityEPA = new TextField("", 12);

	TextField objectAmalgamationEPA = new TextField("", 12);

	TextField behaviorEPA = new TextField("", 12);

	Button compute = new Button();

	Button actorCharacteristicEmotion = new Button();

	Button objectCharacteristicEmotion = new Button();

	BorderLayout layoutCard = new BorderLayout(10, 10);

	BorderLayout layoutSetup = new BorderLayout(0, 0);

	GridBagLayout gridbag = new GridBagLayout();

	GridBagConstraints constraints = new GridBagConstraints();

	int[] colWidths = { 212, 212, 212 };

	Font defaultFont, smallFont;

	Color backgroundColor = Color.yellow;

	int actorEmotionChoice, objectEmotionChoice, actorIdentityChoice,
	objectIdentityChoice, behaviorChoice;

	String zeroProfile = "0.0, 0.0, 0.0";

	String boxText;

	double[] profile = { 0, 0, 0 };

	boolean resetProfile = true;

	EventRecord testEvent = new EventRecord(-1, -1, -1, -1);

	DataList matchesToProfile;

	Retrieval resultLine = new Retrieval(0, "", profile);

	ActionEvent dummyAction;

	boolean[] combinedDivisionConceptGate =
		new boolean[Data.NUMBER_DIVISION_CONCEPT_GATES];

	boolean[] actorDivisionConceptGate =
		new boolean[Data.NUMBER_DIVISION_CONCEPT_GATES];

	boolean[] objectDivisionConceptGate =
		new boolean[Data.NUMBER_DIVISION_CONCEPT_GATES];

	public FeelingEffectsCard() { // Constructor method.
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		setLayout(layoutCard);
		defaultFont = this.getFont();
		smallFont = new Font("Dialog", Font.PLAIN, 9);
		male =
			new Checkbox(Interact.InteractText.getString("maleRaters"), sex,
				true);
		female =
			new Checkbox(Interact.InteractText.getString("femaleRaters"), sex,
				false);
		compute.setLabel(Interact.InteractText.getString("compute"));

		// Top.
		solveFor.setText(Interact.InteractText.getString("solveFor"));
		solveFor.setAlignment(Label.RIGHT);
		line2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.add(line2, BorderLayout.NORTH);
		line2.add(solveFor);
		solveForActor =
			new Checkbox(Interact.InteractText.getString("actorIdentity"),
				task, false);
		solveForBehavior =
			new Checkbox(Interact.InteractText.getString("behavior"), task,
				true);
		solveForObject =
			new Checkbox(Interact.InteractText.getString("objectIdentity"),
				task, false);
		line2.add(solveForActor);
		line2.add(solveForBehavior);
		line2.add(solveForObject);

		// Analysis panel.
		this.add(analysisArea, BorderLayout.CENTER);
		analysisArea.setLayout(gridbag);

		actorTitle.setText(Interact.InteractText.getString("actorEmotions"));
		givenActorIdentity.setText(Interact.InteractText
			.getString("givenActor"));
		actorTitle.setAlignment(Label.CENTER);
		givenActorIdentity.setAlignment(Label.CENTER);
		actorAmalgamationTitle.setAlignment(Label.CENTER);
		objectTitle.setText(Interact.InteractText.getString("objectEmotions"));
		givenObjectIdentity.setText(Interact.InteractText
			.getString("givenObject"));
		actorAmalgamationTitle.setText(Interact.InteractText
			.getString("individualTransient"));
		objectAmalgamationTitle.setText(Interact.InteractText
			.getString("individualTransient"));
		objectTitle.setAlignment(Label.CENTER);
		givenObjectIdentity.setAlignment(Label.CENTER);
		objectAmalgamationTitle.setAlignment(Label.CENTER);
		behaviorTitle.setText(Interact.InteractText.getString("thisBehavior"));
		behaviorTitle.setAlignment(Label.CENTER);
		actorEmotionEPA.setText(zeroProfile);
		actorIdentityEPA.setText(zeroProfile);
		objectEmotionEPA.setText(zeroProfile);
		objectIdentityEPA.setText(zeroProfile);
		behaviorEPA.setText(zeroProfile);
		showAmalgamation(ACTOR);
		showAmalgamation(OBJECT);
		actorCharacteristicEmotion.setLabel(Interact.InteractText
			.getString("characteristicEmotion"));
		objectCharacteristicEmotion.setLabel(Interact.InteractText
			.getString("characteristicEmotion"));

		// Left side.
		gridbag.columnWidths = colWidths;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridheight = 1;
		constraints.insets.left = 2;
		constraints.insets.right = 2;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;

		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.SOUTH;
		gridbag.setConstraints(actorTitle, constraints);
		analysisArea.add(actorTitle);
		constraints.gridy = 2;
		constraints.weighty = 4.0;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(actorEmotionList, constraints);
		analysisArea.add(actorEmotionList);
		constraints.gridy = 3;
		constraints.weighty = 1.0;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(actorEmotionEPA, constraints);
		analysisArea.add(actorEmotionEPA);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.SOUTH;
		gridbag.setConstraints(givenActorIdentity, constraints);
		analysisArea.add(givenActorIdentity);
		constraints.gridy = 5;
		constraints.weighty = 4.0;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(actorIdentityList, constraints);
		analysisArea.add(actorIdentityList);
		constraints.gridy = 6;
		constraints.weighty = 1.0;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(actorIdentityEPA, constraints);
		analysisArea.add(actorIdentityEPA);
		constraints.gridy = 7;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(actorAmalgamationTitle, constraints);
		analysisArea.add(actorAmalgamationTitle);
		constraints.gridy = 8;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(actorAmalgamationEPA, constraints);
		analysisArea.add(actorAmalgamationEPA);
		constraints.gridy = 9;
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(actorCharacteristicEmotion, constraints);
		analysisArea.add(actorCharacteristicEmotion);

		// Right side.
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.SOUTH;
		gridbag.setConstraints(objectTitle, constraints);
		analysisArea.add(objectTitle);
		constraints.gridy = 2;
		constraints.weighty = 4.0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(objectEmotionList, constraints);
		analysisArea.add(objectEmotionList);
		constraints.gridy = 3;
		constraints.weighty = 1.0;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(objectEmotionEPA, constraints);
		analysisArea.add(objectEmotionEPA);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.SOUTH;
		gridbag.setConstraints(givenObjectIdentity, constraints);
		analysisArea.add(givenObjectIdentity);
		constraints.gridy = 5;
		constraints.weighty = 4.0;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(objectIdentityList, constraints);
		analysisArea.add(objectIdentityList);
		constraints.gridy = 6;
		constraints.weighty = 1.0;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(objectIdentityEPA, constraints);
		analysisArea.add(objectIdentityEPA);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 7;
		gridbag.setConstraints(objectAmalgamationTitle, constraints);
		analysisArea.add(objectAmalgamationTitle);
		constraints.gridy = 8;
		constraints.anchor = GridBagConstraints.NORTH;
		gridbag.setConstraints(objectAmalgamationEPA, constraints);
		analysisArea.add(objectAmalgamationEPA);
		constraints.gridy = 9;
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(objectCharacteristicEmotion, constraints);
		analysisArea.add(objectCharacteristicEmotion);

		// Middle.
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTH;
		gridbag.setConstraints(line1, constraints);
		analysisArea.add(line1);
		line1.add(male);
		line1.add(female);
		constraints.gridwidth = 1;
		constraints.gridy = 3;
		gridbag.setConstraints(ButtonPanel, constraints);
		analysisArea.add(ButtonPanel);
		ButtonPanel.add(compute);
		constraints.gridx = 1;
		constraints.gridy = 4;
		gridbag.setConstraints(behaviorTitle, constraints);
		analysisArea.add(behaviorTitle);
		constraints.gridy = 5;
		constraints.weighty = 4.0;
		constraints.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(behaviorList, constraints);
		analysisArea.add(behaviorList);
		constraints.gridy = 6;
		constraints.weighty = 1.0;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(behaviorEPA, constraints);
		analysisArea.add(behaviorEPA);

		fillModifierLists(); // Fill the actor and object emotion lists.
		fill_ABO_Lists(); // Fill the identity and behavior lists.

		actorEmotionChoice =
			objectEmotionChoice =
				actorIdentityChoice =
					objectIdentityChoice = behaviorChoice = -1;
		for (int i = 0; i < 4; i++) {
			testEvent.abosPairConceptGates[i] =
				EventRecord.ALL_TRUE_PAIR_CONCEPT_GATE;
			testEvent.abosDivisionConceptGates[i] =
				EventRecord.ALL_TRUE_DIVISION_CONCEPT_GATE;
			testEvent.abosComplexConceptGates[i] =
				EventRecord.ALL_TRUE_COMPLEX_CONCEPT_GATE;
		}

		solveForActor.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				solveForActor_itemStateChanged(e);
			}
		});
		solveForBehavior.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				solveForBehavior_itemStateChanged(e);
			}
		});
		solveForObject.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				solveForObject_itemStateChanged(e);
			}
		});
		compute.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compute_actionPerformed(e);
			}
		});
		actorEmotionList.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				actorEmotionList_itemStateChanged();
			}
		});
		actorIdentityList.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				actorIdentityList_itemStateChanged();
			}
		});
		objectEmotionList.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				objectEmotionList_itemStateChanged();
			}
		});
		objectIdentityList.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				objectIdentityList_itemStateChanged();
			}
		});
		behaviorList.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				behaviorList_itemStateChanged();
			}
		});
		actorEmotionEPA.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actorEmotionEPA_actionPerformed(e);
			}
		});
		objectEmotionEPA.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				objectEmotionEPA_actionPerformed(e);
			}
		});
		actorIdentityEPA.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actorIdentityEPA_actionPerformed(e);
			}
		});
		objectIdentityEPA
		.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				objectIdentityEPA_actionPerformed(e);
			}
		});
		behaviorEPA.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				behaviorEPA_actionPerformed(e);
			}
		});
		actorAmalgamationEPA
		.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				amalgamationEPA_actionPerformed(ACTOR);
			}
		});
		objectAmalgamationEPA
		.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				amalgamationEPA_actionPerformed(OBJECT);
			}
		});
		male.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				male_itemStateChanged(e);
			}
		});
		female.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				female_itemStateChanged(e);
			}
		});
		actorCharacteristicEmotion
		.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profile = Interact.readProfile(actorIdentityEPA.getText());
				boxText = "";
				for (int i = 0; i < 3; i++) {
					boxText =
						boxText + Interact.formatLocaleDecimal(profile[i]);
				}
				actorAmalgamationEPA.setText(boxText);
				amalgamationEPA_actionPerformed(true);
				actorEmotionEPA_actionPerformed(dummyAction);
			}
		});
		objectCharacteristicEmotion
		.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profile = Interact.readProfile(objectIdentityEPA.getText());
				boxText = "";
				for (int i = 0; i < 3; i++) {
					boxText =
						boxText + Interact.formatLocaleDecimal(profile[i]);
				}
				objectAmalgamationEPA.setText(boxText);
				amalgamationEPA_actionPerformed(false);
				objectEmotionEPA_actionPerformed(dummyAction);
			}
		});
	} // End jbInit.

	void solveForActor_itemStateChanged(ItemEvent e) {
		fill_ABO_Lists();
		actorTitle.setText(Interact.InteractText.getString("actorMood"));
		objectTitle.setText(Interact.InteractText.getString("objectEmotions"));
		actorAmalgamationTitle.setText(Interact.InteractText
			.getString("individualTransient"));
		objectAmalgamationTitle.setText(Interact.InteractText
			.getString("individualTransient"));
		actorAmalgamationTitle.invalidate();
		objectAmalgamationTitle.invalidate();
		analysisArea.validate();
	}

	void solveForBehavior_itemStateChanged(ItemEvent e) {
		fill_ABO_Lists();
		actorTitle.setText(Interact.InteractText.getString("actorEmotions"));
		objectTitle.setText(Interact.InteractText.getString("objectEmotions"));
		actorAmalgamationTitle.setText(Interact.InteractText
			.getString("individualTransient"));
		objectAmalgamationTitle.setText(Interact.InteractText
			.getString("individualTransient"));
		actorAmalgamationTitle.invalidate();
		objectAmalgamationTitle.invalidate();
		analysisArea.validate();
	}

	void solveForObject_itemStateChanged(ItemEvent e) {
		fill_ABO_Lists();
		actorAmalgamationTitle.setText(Interact.InteractText
			.getString("individualTransient"));
		actorTitle.setText(Interact.InteractText.getString("actorEmotions"));
		objectTitle.setText(Interact.InteractText.getString("objectMood"));
		objectAmalgamationTitle.setText(Interact.InteractText
			.getString("individualTransient"));
		actorAmalgamationTitle.invalidate();
		objectAmalgamationTitle.invalidate();
		analysisArea.validate();
	}

	void actorEmotionList_itemStateChanged() {
		int selectedLine = actorEmotionList.getSelectedIndex();
		String selectedLineText = actorEmotionList.getSelectedItem();
		int selectedModifier = Interact.modifiers.getIndex(selectedLineText);
		if (actorEmotionChoice == selectedModifier) {
			// Unselect if user reselects a selected emotion.
			if (actorEmotionChoice >= 0) {
				actorEmotionList.deselect(selectedLine);
			}
			actorEmotionChoice = -1;
			actorEmotionEPA.setText(zeroProfile);
			showAmalgamation(ACTOR);
			return;
		}
		boxText = "";
		actorEmotionChoice = selectedModifier;
		if (actorEmotionChoice >= 0) {
			if (male.getState()) {
				profile =
					((Data) Interact.modifiers.elementAt(actorEmotionChoice)).maleEPA;
			} else {
				profile =
					((Data) Interact.modifiers.elementAt(actorEmotionChoice)).femaleEPA;
			}
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
		}
		actorEmotionEPA.setText(boxText);
		showAmalgamation(ACTOR);
	} // End actorEmotionList_itemStateChanged.

	void actorIdentityList_itemStateChanged() {
		if (actorIdentityChoice == actorIdentityList.getSelectedIndex()) {
			// Unselect if user reselects a selected identity.
			if (actorIdentityChoice >= 0) {
				actorIdentityList.deselect(actorIdentityChoice);
			}
			actorIdentityChoice = -1;
			actorIdentityEPA.setText(zeroProfile);
			actorDivisionConceptGate =
				EventRecord.ALL_TRUE_DIVISION_CONCEPT_GATE;
			return;
		}
		boxText = "";
		actorIdentityChoice = actorIdentityList.getSelectedIndex();
		if (actorIdentityChoice >= 0) {
			actorDivisionConceptGate =
				((Data) Interact.identities.elementAt(actorIdentityChoice)).divisionConceptGate;
			if (male.getState()) {
				profile =
					((Data) Interact.identities.elementAt(actorIdentityChoice)).maleEPA;
			} else {
				profile =
					((Data) Interact.identities.elementAt(actorIdentityChoice)).femaleEPA;
			}
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
		}
		actorIdentityEPA.setText(boxText);
		showAmalgamation(ACTOR);
	} // End actorIdentityList_itemStateChanged.

	void objectEmotionList_itemStateChanged() {
		int selectedLine = objectEmotionList.getSelectedIndex();
		String selectedLineText = objectEmotionList.getSelectedItem();
		int selectedModifier = Interact.modifiers.getIndex(selectedLineText);
		if (objectEmotionChoice == selectedModifier) {
			// Unselect if user reselects a selected emotion.
			if (objectEmotionChoice >= 0) {
				objectEmotionList.deselect(selectedLine);
			}
			objectEmotionChoice = -1;
			objectEmotionEPA.setText(zeroProfile);
			showAmalgamation(OBJECT);
			return;
		}
		boxText = "";
		objectEmotionChoice = selectedModifier;
		if (objectEmotionChoice >= 0) {
			if (male.getState()) {
				profile =
					((Data) Interact.modifiers.elementAt(objectEmotionChoice)).maleEPA;
			} else {
				profile =
					((Data) Interact.modifiers.elementAt(objectEmotionChoice)).femaleEPA;
			}
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
		}
		objectEmotionEPA.setText(boxText);
		showAmalgamation(OBJECT);
	} // End objectEmotionList_itemStateChanged.

	void objectIdentityList_itemStateChanged() {
		if (objectIdentityChoice == objectIdentityList.getSelectedIndex()) {
			// Unselect if user reselects a selected identity.
			if (objectIdentityChoice >= 0) {
				objectIdentityList.deselect(objectIdentityChoice);
			}
			objectIdentityChoice = -1;
			objectIdentityEPA.setText(zeroProfile);
			objectDivisionConceptGate =
				EventRecord.ALL_TRUE_DIVISION_CONCEPT_GATE;
			return;
		}
		boxText = "";
		objectIdentityChoice = objectIdentityList.getSelectedIndex();
		if (objectIdentityChoice >= 0) {
			objectDivisionConceptGate =
				((Data) Interact.identities.elementAt(objectIdentityChoice)).divisionConceptGate;
			if (male.getState()) {
				profile =
					((Data) Interact.identities.elementAt(objectIdentityChoice)).maleEPA;
			} else {
				profile =
					((Data) Interact.identities.elementAt(objectIdentityChoice)).femaleEPA;
			}
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
		}
		objectIdentityEPA.setText(boxText);
		showAmalgamation(OBJECT);
	} // End objectIdentityList_itemStateChanged.

	void behaviorList_itemStateChanged() {
		if (behaviorChoice == behaviorList.getSelectedIndex()) {
			// Unselect if user reselects a selected behavior.
			if (behaviorChoice >= 0) {
				behaviorList.deselect(behaviorChoice);
			}
			behaviorChoice = -1;
			behaviorEPA.setText(zeroProfile);
			return;
		}
		boxText = "";
		behaviorChoice = behaviorList.getSelectedIndex();
		if (behaviorChoice >= 0) {
			if (male.getState()) {
				profile =
					((Data) Interact.behaviors.elementAt(behaviorChoice)).maleEPA;
			} else {
				profile =
					((Data) Interact.behaviors.elementAt(behaviorChoice)).femaleEPA;
			}
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
		}
		behaviorEPA.setText(boxText);
	} // End behaviorList_itemStateChanged.

	void actorEmotionEPA_actionPerformed(ActionEvent e) {
		actorEmotionChoice = -1;
		actorEmotionList.select(actorEmotionChoice);
		actorEmotionEPA.selectAll();
		// Show emotion nearest to profile at top of emotions list.
		profile = Interact.readProfile(actorEmotionEPA.getText());
		matchesToProfile =
			Interact.modifiers.getMatches(profile, male.getState(),
				EventRecord.ALL_TRUE_PAIR_CONCEPT_GATE,
				EventRecord.EMOTION_DIVISION_CONCEPT_GATE,
				EventRecord.ALL_TRUE_COMPLEX_CONCEPT_GATE);
		resultLine = (Retrieval) matchesToProfile.elementAt(0);
		int i = 0;
		while (i < actorEmotionList.getItemCount()) {
			if (actorEmotionList.getItem(i) == resultLine.word) {
				actorEmotionList.makeVisible(i);
				i = actorEmotionList.getItemCount();
			}
			i++;
		}
		showAmalgamation(ACTOR);
	}

	void actorIdentityEPA_actionPerformed(ActionEvent e) {
		actorIdentityChoice = -1;
		actorIdentityList.select(actorIdentityChoice);
		actorDivisionConceptGate = EventRecord.ALL_TRUE_DIVISION_CONCEPT_GATE;
		actorIdentityEPA.selectAll();
		showAmalgamation(ACTOR);
	}

	void objectEmotionEPA_actionPerformed(ActionEvent e) {
		objectEmotionChoice = -1;
		objectEmotionList.select(objectEmotionChoice);
		objectEmotionEPA.selectAll();
		// Show emotion nearest to profile at top of emotions list.
		profile = Interact.readProfile(objectEmotionEPA.getText());
		matchesToProfile =
			Interact.modifiers.getMatches(profile, male.getState(),
				EventRecord.ALL_TRUE_PAIR_CONCEPT_GATE,
				EventRecord.EMOTION_DIVISION_CONCEPT_GATE,
				EventRecord.ALL_TRUE_COMPLEX_CONCEPT_GATE);
		resultLine = (Retrieval) matchesToProfile.elementAt(0);
		int i = 0;
		while (i < objectEmotionList.getItemCount()) {
			if (objectEmotionList.getItem(i) == resultLine.word) {
				objectEmotionList.makeVisible(i);
				i = objectEmotionList.getItemCount();
			}
			i++;
		}
		showAmalgamation(OBJECT);
	}

	void objectIdentityEPA_actionPerformed(ActionEvent e) {
		objectIdentityChoice = -1;
		objectIdentityList.select(objectIdentityChoice);
		objectDivisionConceptGate = EventRecord.ALL_TRUE_DIVISION_CONCEPT_GATE;
		objectIdentityEPA.selectAll();
		showAmalgamation(OBJECT);
	}

	void behaviorEPA_actionPerformed(ActionEvent e) {
		behaviorChoice = -1;
		behaviorList.select(behaviorChoice);
		behaviorEPA.selectAll();
	}

	void compute_actionPerformed(ActionEvent e) {
		int unknownElement;
		MathModel equationSet;
		String lineText;
		// Look at radio buttons to see what the unknown quantity is.
		if (solveForActor.getState()) {
			unknownElement = MathModel.FIND_ACTOR;
		} else {
			if (solveForBehavior.getState()) {
				unknownElement = MathModel.FIND_BEHAVIOR;
			} else {
				unknownElement = MathModel.FIND_OBJECT;
			}
		}
		// Read numeric profiles for fundamentals and transients of ABO from
		// profile boxes on screen.
		testEvent.abosModifier[0] =
			Interact.readProfile(actorEmotionEPA.getText());
		testEvent.abosFundamentals[0] =
			Interact.readProfile(actorIdentityEPA.getText());
		if (Interact.studyEmotions.actorEmotionList.getSelectedIndex() > -1){
			// Solve with actor's emotion specified.
			testEvent.abosTransientsIn[0] =
				Interact.readProfile(actorAmalgamationEPA.getText());
			testEvent.abosTransientsOut[0] =
				Interact.readProfile(actorAmalgamationEPA.getText());
		} else{
			// Solve as on Analyze-Events form.
			testEvent.abosTransientsIn[0] =
				Interact.readProfile(actorIdentityEPA.getText());
			testEvent.abosTransientsOut[0] =
				Interact.readProfile(actorIdentityEPA.getText());
		}
		testEvent.abosFundamentals[1] =
			Interact.readProfile(behaviorEPA.getText());
		testEvent.abosTransientsIn[1] =
			Interact.readProfile(behaviorEPA.getText());
		testEvent.abosTransientsOut[1] =
			Interact.readProfile(behaviorEPA.getText());
		testEvent.abosModifier[2] =
			Interact.readProfile(objectEmotionEPA.getText());
		testEvent.abosFundamentals[2] =
			Interact.readProfile(objectIdentityEPA.getText());
		if (Interact.studyEmotions.objectEmotionList.getSelectedIndex() > -1){
			// Solve with object's emotion specified.
			testEvent.abosTransientsIn[2] =
				Interact.readProfile(objectAmalgamationEPA.getText());
			testEvent.abosTransientsOut[2] =
				Interact.readProfile(objectAmalgamationEPA.getText());
		} else{
			// Solve as on Analyze-Events form.
			testEvent.abosTransientsIn[2] =
				Interact.readProfile(objectIdentityEPA.getText());
			testEvent.abosTransientsOut[2] =
				Interact.readProfile(objectIdentityEPA.getText());
		}
		if (male.getState()) {
			equationSet = Interact.maleabo;
		} else {
			equationSet = Interact.femaleabo;
		}
		profile = equationSet.optimalProfile(unknownElement, testEvent);
		boxText = "";
		for (int i = 0; i < 3; i++) {
			boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
		}
		// Compute amalgamation with optimal profile, not zeros.
		resetProfile = false; 
		switch (unknownElement) {
		case MathModel.FIND_ACTOR:
			actorIdentityEPA.setText(boxText);
			showAmalgamation(ACTOR);
			// Show matching identities.
			actorIdentityList.removeAll();
			matchesToProfile =
				Interact.identities.getMatches(profile, male.getState(),
					EventRecord.ALL_TRUE_PAIR_CONCEPT_GATE,
					objectDivisionConceptGate,
					EventRecord.ALL_TRUE_COMPLEX_CONCEPT_GATE);
			for (int i = 0; i < matchesToProfile.size(); i++) {
				resultLine = (Retrieval) matchesToProfile.elementAt(i);
				lineText =
					Interact.formatLocaleDecimal(resultLine.D)
					+ Interact.InteractText.getString("clauseSeparation")
					+ resultLine.word;
				actorIdentityList.add(lineText);
			}
			break;
		case MathModel.FIND_BEHAVIOR:
			behaviorEPA.setText(boxText);
			// Show matching behaviors.
			behaviorList.removeAll();
			for (int i = 0; i < Data.NUMBER_DIVISION_CONCEPT_GATES; i++) {
				combinedDivisionConceptGate[i] =
					actorDivisionConceptGate[i] | objectDivisionConceptGate[i];
			}
			matchesToProfile =
				Interact.behaviors.getMatches(profile, male.getState(),
					EventRecord.ALL_TRUE_PAIR_CONCEPT_GATE,
					combinedDivisionConceptGate,
					EventRecord.ALL_TRUE_COMPLEX_CONCEPT_GATE);
			for (int i = 0; i < matchesToProfile.size(); i++) {
				resultLine = (Retrieval) matchesToProfile.elementAt(i);
				lineText =
					Interact.formatLocaleDecimal(resultLine.D)
					+ Interact.InteractText.getString("clauseSeparation")
					+ resultLine.word;
				behaviorList.add(lineText);
			}
			break;
		case MathModel.FIND_OBJECT:
			objectIdentityEPA.setText(boxText);
			showAmalgamation(OBJECT);
			// Show matching identities.
			objectIdentityList.removeAll();
			matchesToProfile =
				Interact.identities.getMatches(profile, male.getState(),
					EventRecord.ALL_TRUE_PAIR_CONCEPT_GATE,
					actorDivisionConceptGate,
					EventRecord.ALL_TRUE_COMPLEX_CONCEPT_GATE);
			for (int i = 0; i < matchesToProfile.size(); i++) {
				resultLine = (Retrieval) matchesToProfile.elementAt(i);
				lineText =
					Interact.formatLocaleDecimal(resultLine.D)
					+ Interact.InteractText.getString("clauseSeparation")
					+ resultLine.word;
				objectIdentityList.add(lineText);
			}
		}
	} // End compute_actionPerformed.

	void showAmalgamation(boolean actorNotObject) {
		double[] modEPA = new double[3];
		double[] identEPA = new double[3];
		double[] amalgamationProfile = new double[3];
		double product;
		MathModel eqSet;
		if (male.getState()) {
			eqSet = Interact.maleEmotion;
		} else {
			eqSet = Interact.femaleEmotion;
		}
		if (actorNotObject) {
			if (resetProfile & solveForActor.getState()) {
				actorIdentityList.removeAll();
				actorIdentityEPA.setText(zeroProfile);
			}
			modEPA = Interact.readProfile(actorEmotionEPA.getText());
			identEPA = Interact.readProfile(actorIdentityEPA.getText());
		} else {
			if (resetProfile & solveForObject.getState()) {
				objectIdentityList.removeAll();
				objectIdentityEPA.setText(zeroProfile);
			}
			modEPA = Interact.readProfile(objectEmotionEPA.getText());
			identEPA = Interact.readProfile(objectIdentityEPA.getText());
		}
		resetProfile = true;
		for (int j = 0; j < 3; j++) { 
			// for each amalgamation equation, E,P, or A
			amalgamationProfile[j] = 0;
			for (int i = 0; i < eqSet.coef.length; i++) {
				// for each row of the coefficient matrix
				product = eqSet.coef[i][j];
				// set "product" equal to the coefficient
				// in ith row, jth equation
				for (int jj = 0; jj < 6; jj++) {
					// for each first-order predictor
					if (eqSet.term[i][jj]) {
						// if the predictor is in this term include it
						if (jj < 3) {
							// if the predictor is part of the modifier profile
							product = product * modEPA[jj];
							// multiply coefficient and modifier value
						} else {
							// or if the predictor is part of
							// the identity profile
							product = product * identEPA[jj - 3];
							// multiply coefficient and identity value
						}
					}
				} // End for jj. Have multiplied the coefficient times modifier
				// and/or identity predictors
				amalgamationProfile[j] = amalgamationProfile[j] + product;
				// add this result to the total for this equation
			} // End for i; start term on next row
		} // End for j; start next equation
		boxText = "";
		for (int i = 0; i < 3; i++) {
			boxText =
				boxText + Interact.formatLocaleDecimal(amalgamationProfile[i]);
		}
		if (actorNotObject) {
			actorAmalgamationEPA.setText(boxText);
		} else {
			objectAmalgamationEPA.setText(boxText);
		}
	} // End showAmalgamation.

	void amalgamationEPA_actionPerformed(boolean doingActor) {
		// Show the emotion/mood EPA that combines with the current identity EPA
		// to produce the amalgamation/transient that was just entered.
		double[] identityProfile = new double[3];
		double[] amalgamationProfile = new double[3];
		double[] impliedEmotion = new double[3];
		MathModel eqSet;
		boxText = "";
		if (doingActor) {
			actorEmotionList.select(-1);
			identityProfile = Interact.readProfile(actorIdentityEPA.getText());
			amalgamationProfile =
				Interact.readProfile(actorAmalgamationEPA.getText());
		} else {
			objectEmotionList.select(-1);
			identityProfile = Interact.readProfile(objectIdentityEPA.getText());
			amalgamationProfile =
				Interact.readProfile(objectAmalgamationEPA.getText());
		}
		if (male.getState()) {
			eqSet = Interact.maleEmotion;
		} else {
			eqSet = Interact.femaleEmotion;
		}
		impliedEmotion =
			eqSet.computeModifier(identityProfile, amalgamationProfile);
		for (int i = 0; i < 3; i++) {
			boxText = boxText + Interact.formatLocaleDecimal(impliedEmotion[i]);
		}
		if (doingActor) {
			actorEmotionEPA.setText(boxText);
			actorEmotionEPA.selectAll();
		} else {
			objectEmotionEPA.setText(boxText);
			objectEmotionEPA.selectAll();
		}
	} // End amalgamationEPA_actionPerformed.

	void fill_ABO_Lists() {
		// Refill the actor and object identity lists and the behavior list.
		String wordText;
		actorIdentityList.removeAll();
		behaviorList.removeAll();
		objectIdentityList.removeAll();
		actorIdentityList.setBackground(Color.white);
		behaviorList.setBackground(Color.white);
		objectIdentityList.setBackground(Color.white);
		if (this.solveForBehavior.getState()) {
			objectIdentityList.select(objectIdentityChoice);
			for (int i = 0; i < Interact.identities.size(); i++) {
				wordText = ((Data) Interact.identities.elementAt(i)).word;
				actorIdentityList.add(wordText);
				objectIdentityList.add(wordText);
			}
			behaviorList.setBackground(backgroundColor);
			behaviorChoice = -1;
			actorIdentityList.select(actorIdentityChoice);
			objectIdentityList.select(objectIdentityChoice);
		} else {
			if (this.solveForActor.getState()) {
				actorIdentityList.setBackground(backgroundColor);
				for (int i = 0; i < Interact.behaviors.size(); i++) {
					wordText = ((Data) Interact.behaviors.elementAt(i)).word;
					behaviorList.add(wordText);
				}
				for (int i = 0; i < Interact.identities.size(); i++) {
					wordText = ((Data) Interact.identities.elementAt(i)).word;
					objectIdentityList.add(wordText);
				}
				actorIdentityChoice = -1;
				behaviorList.select(behaviorChoice);
				objectIdentityList.select(objectIdentityChoice);
			} else { // solveForObject
				objectIdentityList.setBackground(backgroundColor);
				for (int i = 0; i < Interact.behaviors.size(); i++) {
					wordText = ((Data) Interact.behaviors.elementAt(i)).word;
					behaviorList.add(wordText);
				}
				for (int i = 0; i < Interact.identities.size(); i++) {
					wordText = ((Data) Interact.identities.elementAt(i)).word;
					actorIdentityList.add(wordText);
				}
				objectIdentityChoice = -1;
				behaviorList.select(behaviorChoice);
				actorIdentityList.select(actorIdentityChoice);
			}
		}
		resetSelections();
	} // End fill_ABO_Lists.

	void fillModifierLists() {
		// Refill the modifier lists with just emotions.
		String wordText;
		actorEmotionList.removeAll();
		objectEmotionList.removeAll();
		for (int i = 0; i < Interact.modifiers.size(); i++) {
			wordText = ((Data) Interact.modifiers.elementAt(i)).word;
			if (((Data) Interact.modifiers.elementAt(i)).divisionConceptGate[0]) {
				actorEmotionList.add(wordText);
				objectEmotionList.add(wordText);
			}
		}
	}

	void male_itemStateChanged(ItemEvent e) {
		// Prevent choices from being cancelled.
		actorEmotionChoice =
			objectEmotionChoice =
				actorIdentityChoice =
					objectIdentityChoice = behaviorChoice = -1;
		// Re-display EPAs.
		behaviorList_itemStateChanged();
		actorIdentityList_itemStateChanged();
		objectIdentityList_itemStateChanged();
		actorEmotionList_itemStateChanged();
		objectEmotionList_itemStateChanged();
		resetSelections();
	}

	void female_itemStateChanged(ItemEvent e) {
		// Prevent choices from being cancelled.
		actorEmotionChoice =
			objectEmotionChoice =
				actorIdentityChoice =
					objectIdentityChoice = behaviorChoice = -1;
		// Re-display EPAs.
		behaviorList_itemStateChanged();
		actorIdentityList_itemStateChanged();
		objectIdentityList_itemStateChanged();
		actorEmotionList_itemStateChanged();
		objectEmotionList_itemStateChanged();
		resetSelections();
	}

	void resetSelections() {
		actorEmotionChoice = actorEmotionList.getSelectedIndex();
		if (actorEmotionChoice == -1) {
			actorEmotionEPA.selectAll();
		} else {
			actorEmotionList.makeVisible(actorEmotionChoice);
		}
		actorIdentityChoice = actorIdentityList.getSelectedIndex();
		if (actorIdentityChoice == -1) {
			actorIdentityEPA.selectAll();
		} else {
			actorIdentityList.makeVisible(actorIdentityChoice);
		}
		objectEmotionChoice = objectEmotionList.getSelectedIndex();
		if (objectEmotionChoice == -1) {
			objectEmotionEPA.selectAll();
		} else {
			objectEmotionList.makeVisible(objectEmotionChoice);
		}
		objectIdentityChoice = objectIdentityList.getSelectedIndex();
		if (objectIdentityChoice == -1) {
			objectIdentityEPA.selectAll();
		} else {
			objectIdentityList.makeVisible(objectIdentityChoice);
		}
		behaviorChoice = behaviorList.getSelectedIndex();
		if (behaviorChoice == -1) {
			behaviorEPA.selectAll();
		} else {
			behaviorList.makeVisible(behaviorChoice);
		}
	}

} // End class FeelingEffectsCard.

