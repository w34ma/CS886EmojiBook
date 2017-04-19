package culture;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

/** Page for searching dictionaries to find matches to an EPA profile. */

class FindConceptsCard extends Panel {

	Panel scaleArea = new Panel();

	Panel profileArea = new Panel();

	Panel parameterArea = new Panel();

	Panel Area = new Panel();

	Panel cutoffArea = new Panel();

	Checkbox maleRater = new Checkbox();

	Checkbox femaleRater = new Checkbox();

	Checkbox identitiesCheckbox = new Checkbox(Interact.caseLines[0]);

	Checkbox behaviorsCheckbox = new Checkbox(Interact.caseLines[1]);

	Checkbox modifiersCheckbox = new Checkbox(Interact.caseLines[2]);

	Checkbox settingsCheckbox = new Checkbox(Interact.caseLines[3]);

	Checkbox[] conceptGateCheckbox =
		new Checkbox[Data.NUMBER_OF_CONCEPT_GATES_USED];

	CheckboxGroup raterSex = new CheckboxGroup();

	CheckboxGroup wordCase = new CheckboxGroup();

	TextArea wordList = new TextArea();

	TextField profileEPA = new TextField();

	RateEPA ratingScale = new RateEPA();

	TextField cutoff = new TextField();

	Label profileTitle = new Label();

	Label cutoffTitle = new Label();

	int[] colWidths = { 152, 152, 332 };

	int caseInUse;

	boolean[][] States = new boolean[4][Data.NUMBER_OF_CONCEPT_GATES_USED];

	GridBagLayout gridbag = new GridBagLayout();

	GridBagConstraints constraints = new GridBagConstraints();

	public FindConceptsCard() { // Constructor method.
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(gridbag);
		gridbag.columnWidths = colWidths;

		// EPA area.
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.gridheight = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(scaleArea, constraints);
		this.add(scaleArea);
		scaleArea.add(ratingScale, null);
		constraints.gridx = 2;
		constraints.gridy = 1;
		gridbag.setConstraints(profileArea, constraints);
		this.add(profileArea);
		profileTitle.setText(Interact.InteractText.getString("EPAprofile"));
		profileArea.add(profileTitle, null);
		profileEPA.setColumns(14);
		profileEPA.setText("0.0,0.0,0.0");
		profileArea.add(profileEPA, null);
		profileTitle.setVisible(false);
		profileEPA.setVisible(false);
		// Left side.
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 2.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(parameterArea, constraints);
		this.add(parameterArea);
		parameterArea.setLayout(new GridLayout(7, 1));
		// Sex.
		maleRater.setLabel(Interact.InteractText.getString("male"));
		maleRater.setCheckboxGroup(raterSex);
		maleRater.setState(true);
		femaleRater.setLabel(Interact.InteractText.getString("female"));
		femaleRater.setCheckboxGroup(raterSex);
		femaleRater.setState(false);
		parameterArea.add(maleRater, null);
		parameterArea.add(femaleRater, null);
		parameterArea.add(new Panel()); // Spacer.
		// Case.
		identitiesCheckbox.setCheckboxGroup(wordCase);
		behaviorsCheckbox.setCheckboxGroup(wordCase);
		modifiersCheckbox.setCheckboxGroup(wordCase);
		settingsCheckbox.setCheckboxGroup(wordCase);
		identitiesCheckbox.setState(true);
		parameterArea.add(identitiesCheckbox, null);
		parameterArea.add(behaviorsCheckbox, null);
		parameterArea.add(modifiersCheckbox, null);
		parameterArea.add(settingsCheckbox, null);

		// ConceptGates in middle.
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 2.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(Area, constraints);
		this.add(Area);
		Area.setLayout(new GridLayout(16, 1));
		for (int i = 0; i < Data.NUMBER_OF_CONCEPT_GATES_USED; i++) {
			conceptGateCheckbox[i] =
				new Checkbox(Interact.identityConceptGateLines[i]);
			if ((i == 2) | (i == 11)) {
				Area.add(new Panel(), null); // White space between types of
												// gates.
			}
			Area.add(conceptGateCheckbox[i], null);
			// Opens on identity search, so 
			// add checkmarks and blank out complex gates.
			if (Interact.identityConceptGateLines[i].equals("")) {
				conceptGateCheckbox[i].setVisible(false);
				conceptGateCheckbox[i].setState(false);
			} else {
				conceptGateCheckbox[i].setState(true);
			}
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < Data.NUMBER_OF_CONCEPT_GATES_USED; j++) {
				// Blank checkboxes are set false in changeCase.
				States[i][j] = true; 
				if (j >= Data.NUMBER_OF_STANDARD_CONCEPT_GATES) {
					States[i][j] = false; // Complex gates off by default.
				}
			}
		}

		// Word list on right.
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = GridBagConstraints.REMAINDER;
		constraints.weighty = 20.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(wordList, constraints);
		this.add(wordList);

		// Set the cutoff value.
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(cutoffArea, constraints);
		this.add(cutoffArea);
		cutoffTitle.setText(Interact.InteractText
			.getString("cutoffInstruction"));
		cutoffArea.add(cutoffTitle, null);
		cutoff.setText(Interact.formatLocaleDecimal(Interact.searchCutoff));
		cutoffArea.add(cutoff, null);

		cutoff.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cutoff_actionPerformed(e);
			}
		});
		identitiesCheckbox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				identitiesCheckbox_itemStateChanged(e);
			}
		});
		behaviorsCheckbox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				behaviorsCheckbox_itemStateChanged(e);
			}
		});
		modifiersCheckbox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modifiersCheckbox_itemStateChanged(e);
			}
		});
		settingsCheckbox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				settingsCheckbox_itemStateChanged(e);
			}
		});
		profileEPA.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profileEPA_actionPerformed(e);
			}
		});
		maleRater.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				maleRater_itemStateChanged(e);
			}
		});
		femaleRater.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				femaleRater_itemStateChanged(e);
			}
		});
		for (int i = 0; i < Data.NUMBER_OF_CONCEPT_GATES_USED; i++) {
			// for (int i = 0; i < Data.NUMBER_OF_STANDARD_CONCEPT_GATES; i++) {
			conceptGateCheckbox[i]
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						_itemStateChanged(e);
					}
				});
		}

	} // End constructor method.

	/** Store new maximum distance for ending dictionary searches. */
	void cutoff_actionPerformed(ActionEvent e) {
		Interact.searchCutoff = Interact.readNumber(cutoff.getText());
		search();
	}

	void profileEPA_actionPerformed(ActionEvent e) {
		search();
	}

	void maleRater_itemStateChanged(ItemEvent e) {
		search();
	}

	void femaleRater_itemStateChanged(ItemEvent e) {
		search();
	}

	void _itemStateChanged(ItemEvent e) {
		search();
	}

	public void search() {
		String lineText, boxText;
		double[] profile = { 0, 0, 0 };
		Retrieval resultLine;
		DataList dic, matches;
		if (identitiesCheckbox.getState()) {
			dic = Interact.identities;
			caseInUse = 0;
		} else if (behaviorsCheckbox.getState()) {
			dic = Interact.behaviors;
			caseInUse = 1;
		} else if (modifiersCheckbox.getState()) {
			dic = Interact.modifiers;
			caseInUse = 2;
		} else {
			dic = Interact.settings;
			caseInUse = 3;
		}
		int endPair = Data.NUMBER_PAIR_CONCEPT_GATES;
		int endDivision =
			Data.NUMBER_PAIR_CONCEPT_GATES + Data.NUMBER_DIVISION_CONCEPT_GATES;
		int endComplex = Data.NUMBER_OF_CONCEPT_GATES_USED;
		boolean male = maleRater.getState();
		boolean showingNumbers =
			(Interact.controls.complexityChoice.getSelectedItem() == Interact.displayLines[1]);
		boolean[] pairConceptGate = new boolean[Data.NUMBER_PAIR_CONCEPT_GATES];
		for (int i = 0; i < endPair; i++) {
			pairConceptGate[i] = conceptGateCheckbox[i].getState();
			States[caseInUse][i] = conceptGateCheckbox[i].getState();
		}
		boolean[] divisionConceptGate =
			new boolean[Data.NUMBER_DIVISION_CONCEPT_GATES];
		for (int i = endPair; i < endDivision; i++) {
			divisionConceptGate[i - endPair] =
				conceptGateCheckbox[i].getState();
			States[caseInUse][i] = conceptGateCheckbox[i].getState();
		}
		boolean[] complexConceptGate =
			new boolean[Data.NUMBER_COMPLEX_CONCEPT_GATES];
		for (int i = endDivision; i < endComplex; i++) {
			complexConceptGate[i - endDivision] =
				conceptGateCheckbox[i].getState();
			States[caseInUse][i] = conceptGateCheckbox[i].getState();
		}
		profile = Interact.readProfile(profileEPA.getText());
		matches =
			dic.getMatches(profile, male, pairConceptGate, divisionConceptGate,
				complexConceptGate);
		boxText = "";
		for (int i = 0; i < matches.size(); i++) {
			resultLine = (Retrieval) matches.elementAt(i);
			lineText = resultLine.word;
			if (showingNumbers) {
				lineText =
					lineText
						+ Interact.InteractText.getString("clauseSeparation");
				for (int j = 0; j < 3; j++) {
					lineText =
						lineText
							+ Interact
								.formatLocaleDecimal(resultLine.profile[j]);
				}
				lineText =
					lineText
						+ Interact.InteractText.getString("clauseSeparation")
						+ Interact.formatLocaleDecimal(resultLine.D);
			}
			boxText =
				boxText + lineText
					+ Interact.InteractText.getString("paragraphCommand");
		}
		wordList.setText(boxText);
	}

	/** Switch among identities, behaviors, modifiers, and settings. */
	void changeCase(String[] List) {
		for (int j = 0; j < Data.NUMBER_OF_CONCEPT_GATES_USED; j++) {
			if (List[j].equals("")) {
				conceptGateCheckbox[j].setState(false);
				conceptGateCheckbox[j].setVisible(false);
			} else {
				conceptGateCheckbox[j].setState(States[caseInUse][j]);
				conceptGateCheckbox[j].setLabel(List[j]);
				conceptGateCheckbox[j].setVisible(true);
			}
		}
		search();
	}

	void identitiesCheckbox_itemStateChanged(ItemEvent e) {
		caseInUse = 0;
		changeCase(Interact.identityConceptGateLines);
	}

	void behaviorsCheckbox_itemStateChanged(ItemEvent e) {
		caseInUse = 1;
		changeCase(Interact.behaviorConceptGateLines);
	}

	void modifiersCheckbox_itemStateChanged(ItemEvent e) {
		caseInUse = 2;
		changeCase(Interact.modifierConceptGateLines);
	}

	void settingsCheckbox_itemStateChanged(ItemEvent e) {
		caseInUse = 3;
		changeCase(Interact.settingConceptGateLines);
	}

} // End class FindConceptsCard.

