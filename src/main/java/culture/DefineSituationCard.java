package culture;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.*;

/** Page for defining interactants' definitions of persons and setting. */

class DefineSituationCard extends Panel {

	Panel namingArea, inputs;

	Panel settingArea, identityArea, modifierArea, identityTitleArea;

	Choice egoMenu, alterMenu, institutionMenu;

	TextField settingEPA, identityEPA, modifierEPA, combinationEPA;

	Label forViewer, naming, settingIs, stateOfBeing, combinationTitle,
	institutionTitle, copyrightText;

	java.awt.List settingList, identityList, modifierList;

	BorderLayout borderLayout1 = new BorderLayout();

	GridBagLayout gridbag;

	GridBagConstraints constraints;

	int currentInstitution = 0;

	boolean[][] situationConceptGates; // [a

	// setting+numberOfInteractants][nInstitutionConceptGates];

	int[] colWidths = { 212, 212, 212 };

	Data dictionaryLine;

	public DefineSituationCard() { // Constructor method.
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(borderLayout1);

		situationConceptGates =
			new boolean[1 + Interact.numberOfInteractants][ConceptGatesPopup.N_INSTITUTION_CONCEPT_GATES];
		for (int i = 0; i <= Interact.numberOfInteractants; i++) {
			for (int j = 0; j < ConceptGatesPopup.N_INSTITUTION_CONCEPT_GATES; j++) {
				situationConceptGates[i][j] = false;
			}
		}
		setLayout(new BorderLayout(5, 5));

		// Definition of the viewer at top.
		namingArea = new Panel();
		namingArea.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		this.add(namingArea, BorderLayout.NORTH);
		forViewer =
			new Label(Interact.InteractText.getString("viewer"), Label.RIGHT);
		namingArea.add(forViewer);
		egoMenu = new Choice();
		namingArea.add(egoMenu);
		for (int i = 0; i < Interact.numberOfInteractants; i++) {
			egoMenu.add(Interact.person[i].name);
		}
		egoMenu.select(0);

		// Definition of the situation.
		inputs = new Panel();
		this.add(inputs, BorderLayout.CENTER);
		gridbag = new GridBagLayout();
		gridbag.columnWidths = colWidths;
		inputs.setLayout(gridbag);
		constraints = new GridBagConstraints();
		// Titles and alter choice.
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		settingIs =
			new Label(Interact.InteractText.getString("theSettingIs"),
				Label.CENTER);
		gridbag.setConstraints(settingIs, constraints);
		inputs.add(settingIs);
		constraints.gridx = 1;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		identityTitleArea = new Panel();
		gridbag.setConstraints(identityTitleArea, constraints);
		inputs.add(identityTitleArea);
		alterMenu = new Choice();
		identityTitleArea.add(alterMenu);
		for (int i = 0; i < Interact.numberOfInteractants; i++) {
			alterMenu.add(Interact.person[i].name);
		}
		alterMenu.select(0);
		stateOfBeing =
			new Label(Interact.InteractText.getString("thePersonIs"),
				Label.CENTER);
		identityTitleArea.add(stateOfBeing);

		// Left side.
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 20.0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets.right = 4;
		settingList = new java.awt.List();
		gridbag.setConstraints(settingList, constraints);
		inputs.add(settingList);
		constraints.insets.right = 0;
		constraints.gridy = 4;
		settingEPA = new TextField("", 15);
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(settingEPA, constraints);
		inputs.add(settingEPA);
		// Institution designator.
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.gridwidth = 1;
		institutionTitle =
			new Label(Interact.InteractText.getString("institution"),
				Label.RIGHT);
		gridbag.setConstraints(institutionTitle, constraints);
		inputs.add(institutionTitle);
		constraints.gridheight = GridBagConstraints.REMAINDER;
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.anchor = GridBagConstraints.NORTH;
		institutionMenu = new Choice();
		gridbag.setConstraints(institutionMenu, constraints);
		inputs.add(institutionMenu);
		institutionMenu.add(Interact.InteractText.getString("allConceptGates"));
		for (int i = ConceptGatesPopup.N_PAIR_CONCEPT_GATES; i < ConceptGatesPopup.N_PAIR_CONCEPT_GATES
		+ ConceptGatesPopup.N_INSTITUTION_CONCEPT_GATES; i++) {
			institutionMenu.add(Interact.identityConceptGateLines[i]);
		}
		institutionMenu.select(currentInstitution);

		// Center.
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weighty = 20.0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridy = 3;
		identityList = new java.awt.List();
		gridbag.setConstraints(identityList, constraints);
		inputs.add(identityList);
		constraints.gridy = 4;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.NONE;
		identityEPA = new TextField("", 15);
		gridbag.setConstraints(identityEPA, constraints);
		inputs.add(identityEPA);

		// Right side.
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.gridheight = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 1.0;
		constraints.weighty = 20.0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets.left = 3;
		modifierList = new java.awt.List();
		gridbag.setConstraints(modifierList, constraints);
		inputs.add(modifierList);
		constraints.insets.left = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridy = 4;
		constraints.weighty = 1.0;
		modifierEPA = new TextField("", 15);
		gridbag.setConstraints(modifierEPA, constraints);
		inputs.add(modifierEPA);
		constraints.gridheight = GridBagConstraints.REMAINDER;
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridwidth = 1;
		combinationTitle =
			new Label(Interact.InteractText.getString("combination"),
				Label.RIGHT);
		gridbag.setConstraints(combinationTitle, constraints);
		inputs.add(combinationTitle);
		constraints.gridx = 2;
		constraints.gridy = 5;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		combinationEPA = new TextField("", 15);
		combinationEPA.setBackground(Color.lightGray);
		combinationEPA.setEditable(false);
		gridbag.setConstraints(combinationEPA, constraints);
		inputs.add(combinationEPA);

		// Fill the lists.
		fillSettings(0);
		fillIdentities(0);
		fillModifiers(0);

		// Bottom.
		copyrightText =
			new Label(Interact.InteractText.getString("copyright"),
				Label.CENTER);
		copyrightText.setForeground(Color.blue);
		this.add("South", copyrightText);

		egoMenu.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				egoMenu_itemStateChanged(e);
			}
		});
		alterMenu.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				alterMenu_itemStateChanged(e);
			}
		});
		institutionMenu.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				institutionMenu_itemStateChanged(e);
			}
		});
		settingList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				settingList_itemStateChanged();
			}
		});
		identityList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				identityList_itemStateChanged();
			}
		});
		modifierList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				modifierList_itemStateChanged();
			}
		});
		settingEPA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingEPA_actionPerformed(e);
			}
		});
		identityEPA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				identityEPA_actionPerformed(e);
			}
		});
		modifierEPA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifierEPA_actionPerformed(e);
			}
		});
	} // End constructor method.

	/** Store choices regarding given ego and alter. */
	public void storeEgo() {
		//		public void storeEgo(int anEgo, int anAlter) {
		int indexNum;
		int anEgo = egoMenu.getSelectedIndex();
		int anAlter = alterMenu.getSelectedIndex();
		// Get ego's setting.
		Interact.person[anEgo].setting.nounWordNumber =
			Interact.settings.getIndex(settingList.getSelectedItem());
		// Get alter's identity, for ego as viewer.
		indexNum = Interact.identities.getIndex(identityList.getSelectedItem());
		Interact.person[anEgo].viewOfPerson[anAlter].nounWordNumber = indexNum;
		// Get alter's modifier, for ego as viewer.
		indexNum = Interact.modifiers.getIndex(modifierList.getSelectedItem());
		Interact.person[anEgo].viewOfPerson[anAlter].modifierWordNumber =
			indexNum;
		if (SentimentChange.changingSentiments) {
			// Use selected identity as basis for mutator initial EPA profile.
			Data userSelectedIdentity =
				(Data) Interact.identities.elementAt(indexNum);
			String mutatorIdentity =
				"Mutator_" + Integer.toString(anEgo + 1) + "_"
				+ Integer.toString(anAlter + 1);
			int replacementIndexNum =
				Interact.identities.getIndex(mutatorIdentity);
			Data alterMutatorIdentity =
				(Data) Interact.identities.elementAt(replacementIndexNum);
			for (int epa = 0; epa < Interact.N_DIMENSIONS; epa++) {
				alterMutatorIdentity.maleEPA[epa] =
					userSelectedIdentity.maleEPA[epa];
				alterMutatorIdentity.femaleEPA[epa] =
					userSelectedIdentity.femaleEPA[epa];
			}
			Interact.person[anEgo].viewOfPerson[anAlter].nounWordNumber =
				replacementIndexNum;
			setSelections();
		}
	} // End storeEgo.

	/**
	 * Copy Person 1's person definitions to others who do not have definitions.
	 */
	public void copyPersonOneSelections() {
		// Called from egoMenu and from Controls when leaving definePerson.
		for (int interactant = 1; interactant < Interact.numberOfInteractants; interactant++) {
			for (int i = 0; i < Interact.numberOfInteractants; i++) {
				if (Interact.person[interactant].viewOfPerson[i].nounWordNumber == -1) {
					Interact.person[interactant].viewOfPerson[i].nounWordNumber =
						Interact.person[0].viewOfPerson[i].nounWordNumber;
					Interact.person[interactant].viewOfPerson[i].modifierWordNumber =
						Interact.person[0].viewOfPerson[i].modifierWordNumber;
				}
			}
		}
	} // End copyPersonOneSelections.

	/** Recall past choices when moving through menus of ego and alter. */
	public void setSelections() {
		int index;
		int egoHere = egoMenu.getSelectedIndex();
		int alterHere = alterMenu.getSelectedIndex();
		this.invalidate();
		// Select ego's setting.
		index = Interact.person[egoHere].setting.nounWordNumber;
		selectEgoSetting(index);
		writeSettingEPA(Interact.person[egoHere].sex);
		// Select alter's identity, for ego as viewer.
		selectEgoAlterIdentity(Interact.person[egoHere].viewOfPerson[alterHere].nounWordNumber);
		writeIdentityEPA(Interact.person[egoHere].sex);
		// Select alter's modifier, for ego as viewer.
		index =
			Interact.person[egoHere].viewOfPerson[alterHere].modifierWordNumber;
		if (index >= 0) {
			modifierList.select(index);
			modifierList.makeVisible(index);
		} else {
			modifierList.deselect(modifierList.getSelectedIndex());
		}
		writeModifierEPA(Interact.person[egoHere].sex);
		this.validate();
	} // End setSelections.

	/** Write EPA profile corresponding to choice in list. */
	public void writeSettingEPA(boolean malesex) {
		double[] profile = { 0, 0, 0 };
		String boxText = "";
		int index = Interact.settings.getIndex(settingList.getSelectedItem());
		if (index >= 0) {
			if (malesex) {
				profile = ((Data) Interact.settings.elementAt(index)).maleEPA;
			} else {
				profile = ((Data) Interact.settings.elementAt(index)).femaleEPA;
			}
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
		}
		settingEPA.setText(boxText);
	}

	/** Write EPA profile corresponding to choice in list. */
	public void writeIdentityEPA(boolean malesex) {
		double[] profile = { 0, 0, 0 };
		String boxText = "";
		if (identityList.getSelectedIndex() >= 0) {
			int index =
				Interact.identities.getIndex(identityList.getSelectedItem());
			if (malesex) {
				profile = ((Data) Interact.identities.elementAt(index)).maleEPA;
			} else {
				profile =
					((Data) Interact.identities.elementAt(index)).femaleEPA;
			}
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
		}
		identityEPA.setText(boxText);
		writeCombinationEPA(malesex);
	}

	/** Write EPA profile corresponding to choice in list. */
	public void writeModifierEPA(boolean malesex) {
		double[] profile = { 0, 0, 0 };
		String boxText = "";
		if (modifierList.getSelectedIndex() >= 0) {
			int index =
				Interact.modifiers.getIndex(modifierList.getSelectedItem());
			if (malesex) {
				profile = ((Data) Interact.modifiers.elementAt(index)).maleEPA;
			} else {
				profile =
					((Data) Interact.modifiers.elementAt(index)).femaleEPA;
			}
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
		}
		modifierEPA.setText(boxText);
		writeCombinationEPA(malesex);
	}

	/**
	 * Write EPA profile corresponding to amalgamation of identity and modifier
	 * choices.
	 */
	public void writeCombinationEPA(boolean malesex) {
		double[] profile = { 0, 0, 0 };
		String boxText = "";
		if ((modifierList.getSelectedIndex() >= 0)
				& (identityList.getSelectedIndex() >= 0)) {
			int identityIndex =
				Interact.identities.getIndex(identityList.getSelectedItem());
			int modifierIndex =
				Interact.modifiers.getIndex(modifierList.getSelectedItem());
			profile =
				MathModel.amalgamate(malesex, modifierIndex, identityIndex);
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
		}
		combinationEPA.setText(boxText);
	}

	/** Make a new entry for a list from a typed-in EPA profile. */
	public static Data newDataEntry(String boxText) {
		double[] profile = { 0, 0, 0 };
		double[] doubleProfile = { 0, 0, 0, 0, 0, 0 };
		boolean[] filters = new boolean[Data.MAX_NUMBER_OF_CONCEPT_GATES];
		profile = Interact.readProfile(boxText);
		for (int i = 0; i < 3; i++) {
			doubleProfile[i] = profile[i];
			doubleProfile[i + 3] = profile[i];
		}
		boxText = "_" + boxText.replace(' ', '_');
		boxText = boxText.replace(',', '_');
		for (int i = 0; i < Data.MAX_NUMBER_OF_CONCEPT_GATES; i++) {
			filters[i] = true; // all filters yes.
		}
		Data newEntry = new Data(boxText, doubleProfile, filters);
		return newEntry;
	}

	void egoMenu_itemStateChanged(ItemEvent e) {
		// Copy Person 1's selections to undefined others as a default.
		copyPersonOneSelections(); 
		// Do NOT try to reset alterMenu with: alterMenu.select(0);
		setSelections();
	}

	void alterMenu_itemStateChanged(ItemEvent e) {
		setSelections();
	}

	void institutionMenu_itemStateChanged(ItemEvent e) {
		// Get the user's choice of institution.
		int institutionNumber = institutionMenu.getSelectedIndex();
		// Fill the setting list with settings in selected institution.
		fillSettings(institutionNumber);
		// Select the setting previously chosen for ego.
		int anEgo = egoMenu.getSelectedIndex();
		int anAlter = alterMenu.getSelectedIndex();
		selectEgoSetting(Interact.person[anEgo].setting.nounWordNumber);
		// Fill the identity list with identities in selected institution.
		fillIdentities(institutionNumber);
		// Select the identity previously chosen for ego view of alter.
		selectEgoAlterIdentity(Interact.person[anEgo].viewOfPerson[anAlter].nounWordNumber);
		// No need to change modifier list or EPA boxes.
	}

	void settingList_itemStateChanged() {
		int anEgo = egoMenu.getSelectedIndex();
		int dictionaryNumber =
			Interact.settings.getIndex(settingList.getSelectedItem());
		if (Interact.person[anEgo].setting.nounWordNumber == dictionaryNumber) {
			// Unselect if user reselects the currently-selected setting.
			Interact.person[anEgo].setting.nounWordNumber = -1;
			settingList.deselect(settingList.getSelectedIndex());
		}
		storeEgo();
		writeSettingEPA(Interact.person[anEgo].sex);
	}

	void identityList_itemStateChanged() {
		int anEgo = egoMenu.getSelectedIndex();
		int anAlter = alterMenu.getSelectedIndex();
		int dictionaryNumber =
			Interact.identities.getIndex(identityList.getSelectedItem());
		if (Interact.person[anEgo].viewOfPerson[anAlter].nounWordNumber == dictionaryNumber) {
			// Unselect if user reselects the currently-selected identity.
			Interact.person[anEgo].viewOfPerson[anAlter].nounWordNumber = -1;
			identityList.deselect(identityList.getSelectedIndex());
			// Unselect modifier when no identity is selected.
			Interact.person[anEgo].viewOfPerson[anAlter].modifierWordNumber = -1;
			modifierList.deselect(modifierList.getSelectedIndex());
			writeModifierEPA(Interact.person[anEgo].sex);
		}
		storeEgo();
		writeIdentityEPA(Interact.person[anEgo].sex);
	}

	void modifierList_itemStateChanged() {
		int anEgo = egoMenu.getSelectedIndex();
		int anAlter = alterMenu.getSelectedIndex();
		int selectedModifier =
			Interact.modifiers.getIndex(modifierList.getSelectedItem());
		if ((Interact.person[anEgo].viewOfPerson[anAlter].nounWordNumber < 0)
				|| (Interact.person[anEgo].viewOfPerson[anAlter].modifierWordNumber == selectedModifier)) {
			// Unselect if no identity has been selected or 
			// if user reselects a selected modifier.
			Interact.person[anEgo].viewOfPerson[anAlter].modifierWordNumber = -1;
			modifierList.deselect(modifierList.getSelectedIndex());
		}
		storeEgo();
		writeModifierEPA(Interact.person[anEgo].sex);
	}

	void settingEPA_actionPerformed(ActionEvent e) {
		Data newEntry;
		newEntry = newDataEntry(settingEPA.getText());
		Interact.settings.addElement(newEntry);
		settingList.add(newEntry.word);
		settingList.select(settingList.getItemCount() - 1);
		settingList.makeVisible(settingList.getSelectedIndex());
		storeEgo();
	}

	void identityEPA_actionPerformed(ActionEvent e) {
		Data newEntry;
		newEntry = newDataEntry(identityEPA.getText());
		Interact.identities.addElement(newEntry);
		identityList.add(newEntry.word);
		identityList.select(identityList.getItemCount() - 1);
		identityList.makeVisible(identityList.getSelectedIndex());
		storeEgo();
	}

	void modifierEPA_actionPerformed(ActionEvent e) {
		Data newEntry;
		newEntry = newDataEntry(modifierEPA.getText());
		Interact.modifiers.addElement(newEntry);
		modifierList.add(newEntry.word);
		modifierList.select(modifierList.getItemCount() - 1);
		modifierList.makeVisible(modifierList.getSelectedIndex());
		storeEgo();
	}

	/** Fill the settings list, allowing for institutional selection. */
	void fillSettings(int institutionSelection) {
		settingList.removeAll();
		if (institutionSelection == 0) { // Include all.
			for (int i = 0; i < Interact.settings.size(); i++) {
				settingList.add(((Data) Interact.settings.elementAt(i)).word);
			}
		} else {
			--institutionSelection; // Remove "All" index.
			for (int i = 0; i < Interact.settings.size(); i++) {
				dictionaryLine = (Data) Interact.settings.elementAt(i);
				if (dictionaryLine.divisionConceptGate[institutionSelection]) {
					settingList.add(dictionaryLine.word);
				}
			}
		}
	} // End fillSettings.

	/** Fill the identities list, allowing for institutional selection. */
	void fillIdentities(int institutionSelection) {
		identityList.removeAll();
		if (institutionSelection == 0) { // Include all.
			for (int i = 0; i < Interact.identities.size(); i++) {
				identityList
				.add(((Data) Interact.identities.elementAt(i)).word);
			}
		} else {
			--institutionSelection; // Remove "All" index.
			for (int i = 0; i < Interact.identities.size(); i++) {
				dictionaryLine = (Data) Interact.identities.elementAt(i);
				if (dictionaryLine.divisionConceptGate[institutionSelection]) {
					identityList.add(dictionaryLine.word);
				}
			}
		}
	} // End fillIdentities.

	/** Fill the modifiers list. */
	void fillModifiers(int modifierSelection) {
		if (modifierSelection == 0) { // Include all.
			for (int i = 0; i < Interact.modifiers.size(); i++) {
				modifierList.add(((Data) Interact.modifiers.elementAt(i)).word);
			}
		}
	} // End fillModifiers.

	void selectEgoSetting(int egoSettingIndex) {
		String userSelectedWord;
		if (egoSettingIndex < 0) {
			// No setting chosen for ego, so make sure nothing is selected.
			settingList.deselect(settingList.getSelectedIndex());
			return;
		}
		// See if the selected setting is in the current setting list.
		userSelectedWord = Interact.settings.getWord(egoSettingIndex);
		String[] wordList = settingList.getItems();
		int i = 0;
		do {
			if (wordList[i] == userSelectedWord) {
				// The selected setting is in the list; select and show it.
				settingList.select(i);
				settingList.makeVisible(i);
				return;
			} else {
				++i;
			}
		} while (i < wordList.length);
		// The selected setting is not in current word list.
		// Add, select, and show it.
		settingList.add(userSelectedWord);
		settingList.select(i);
		settingList.makeVisible(i);
	} // End selectEgoSetting.

	void selectEgoAlterIdentity(int egoAlterIdentityIndex) {
		String userSelectedWord;
		if (egoAlterIdentityIndex < 0) {
			// No identity chosen for ego view of alter,
			// so make sure nothing is selected.
			identityList.deselect(identityList.getSelectedIndex());
			return;
		}
		// See if the chosen identity is in the current identity list.
		userSelectedWord = Interact.identities.getWord(egoAlterIdentityIndex);
		String[] wordList = identityList.getItems();
		int i = 0;
		do {
			if (wordList[i] == userSelectedWord) {
				// The selected identity is in the list; select and show it.
				identityList.select(i);
				identityList.makeVisible(i);
				return;
			} else {
				++i;
			}
		} while (i < wordList.length);
		// The selected identity is not in current word list.
		// Add, select, and show it.
		identityList.add(userSelectedWord);
		identityList.select(i);
		identityList.makeVisible(i);
	} // End selectEgoAlterIdentity.

} // End class DefinePersonCard.
