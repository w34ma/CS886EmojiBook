package culture;

import java.awt.*;
import java.awt.event.*;

/** Page for choosing interactants' sexes and faces. */
public class DefineInteractantsCard extends Panel {
	static final int NUMBER_OF_VISAGES_PER_SEX = 3;

	Panel nameTitlePanel = new Panel();

	Panel biologyTitlePanel = new Panel();

	Panel languageTitlePanel = new Panel();

	Panel controllingTitlePanel = new Panel();

	Panel person1NamePanel = new Panel();

	Panel person2NamePanel = new Panel();

	Panel person3NamePanel = new Panel();

	Panel person4NamePanel = new Panel();

	Panel person1BiologyPanel = new Panel();

	Panel person2BiologyPanel = new Panel();

	Panel person3BiologyPanel = new Panel();

	Panel person4BiologyPanel = new Panel();

	Panel person1LanguagePanel = new Panel();

	Panel person2LanguagePanel = new Panel();

	Panel person3LanguagePanel = new Panel();

	Panel person4LanguagePanel = new Panel();

	Panel person1ControllingCasePanel = new Panel();

	Panel person2ControllingCasePanel = new Panel();

	Panel person3ControllingCasePanel = new Panel();

	Panel person4ControllingCasePanel = new Panel();

	Panel person1ControllingEPAPanel = new Panel();

	Panel person2ControllingEPAPanel = new Panel();

	Panel person3ControllingEPAPanel = new Panel();

	Panel person4ControllingEPAPanel = new Panel();

	Panel visagePanel = new Panel();

	Label nameTitleLabel = new Label();

	Label biologyTitleLabel = new Label();

	Label languageTitleLabel = new Label();

	Label controllingTitleLabel = new Label();

	TextField personName[] = new TextField[Interact.numberOfInteractants];

	Choice personBiologyChoice[] = new Choice[Interact.numberOfInteractants];

	Choice personLanguageChoice[] = new Choice[Interact.numberOfInteractants];

	GridBagLayout gridBagLayout1 = new GridBagLayout();

	Checkbox personControllingCase[][] =
		new Checkbox[Interact.numberOfInteractants][4];

	Checkbox personControllingEPA[][] =
		new Checkbox[Interact.numberOfInteractants][3];

	Face displayFace;

	GridLayout gridLayout1 = new GridLayout();

	GridLayout gridLayout2 = new GridLayout();

	public DefineInteractantsCard() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		this.setLayout(gridBagLayout1);
		person1ControllingCasePanel.setLayout(gridLayout1);
		person2ControllingCasePanel.setLayout(gridLayout1);
		person3ControllingCasePanel.setLayout(gridLayout1);
		person4ControllingCasePanel.setLayout(gridLayout1);
		person1ControllingEPAPanel.setLayout(gridLayout1);
		person2ControllingEPAPanel.setLayout(gridLayout1);
		person3ControllingEPAPanel.setLayout(gridLayout1);
		person4ControllingEPAPanel.setLayout(gridLayout1);
		gridLayout1.setRows(2);
		gridLayout1.setColumns(2);
		gridLayout2.setRows(1);
		gridLayout2.setColumns(2 * NUMBER_OF_VISAGES_PER_SEX);
		this.setBackground(Color.white);
		nameTitlePanel.setBackground(Color.white);
		biologyTitlePanel.setBackground(Color.white);
		languageTitlePanel.setBackground(Color.white);
		controllingTitlePanel.setBackground(Color.white);
		visagePanel.setBackground(Color.white);
		visagePanel.setLayout(gridLayout2);
		person1NamePanel.setBackground(Color.white);
		person2NamePanel.setBackground(Color.white);
		person3NamePanel.setBackground(Color.white);
		person4NamePanel.setBackground(Color.white);
		person1BiologyPanel.setBackground(Color.white);
		person2BiologyPanel.setBackground(Color.white);
		person3BiologyPanel.setBackground(Color.white);
		person4BiologyPanel.setBackground(Color.white);
		person1LanguagePanel.setBackground(Color.white);
		person2LanguagePanel.setBackground(Color.white);
		person3LanguagePanel.setBackground(Color.white);
		person4LanguagePanel.setBackground(Color.white);
		person1ControllingCasePanel.setBackground(Color.white);
		person2ControllingCasePanel.setBackground(Color.white);
		person3ControllingCasePanel.setBackground(Color.white);
		person4ControllingCasePanel.setBackground(Color.white);
		nameTitleLabel.setBackground(Color.white);
		biologyTitleLabel.setBackground(Color.white);
		languageTitleLabel.setBackground(Color.white);
		controllingTitleLabel.setBackground(Color.white);
		nameTitleLabel.setText(Interact.InteractText.getString("namingLabel"));
		biologyTitleLabel.setText(Interact.InteractText
			.getString("biologyLabel"));
		languageTitleLabel.setText(Interact.InteractText
			.getString("languageLabel"));
		controllingTitleLabel.setText(Interact.InteractText
			.getString("controllingLabel"));

		for (int i = 0; i < Interact.numberOfInteractants; i++) { 
			// Create popups and choice-boxes.
			personName[i] = new TextField();
			personName[i].setColumns(12);
			personBiologyChoice[i] = new Choice();
			personLanguageChoice[i] = new Choice();
			for (int abos = 0; abos < 4; abos++) {
				personControllingCase[i][abos] = new Checkbox();
			}
			for (int epa = 0; epa < 3; epa++) {
				personControllingEPA[i][epa] = new Checkbox();
			}
		}
		String prefix;
		for (int i = 0; i < Interact.numberOfInteractants; i++) { 
			// Fill pop-ups.
			prefix =
				Interact.InteractText.getString("male")
					+ Interact.InteractText.getString("space");
			for (int indexNumber = 0; indexNumber < NUMBER_OF_VISAGES_PER_SEX; indexNumber++) {
				personBiologyChoice[i].add(new String(prefix
					+ (new Integer(indexNumber + 1)).toString()));
			}
			prefix =
				Interact.InteractText.getString("female")
					+ Interact.InteractText.getString("space");
			for (int indexNumber = 0; indexNumber < NUMBER_OF_VISAGES_PER_SEX; indexNumber++) {
				personBiologyChoice[i].add(new String(prefix
					+ (new Integer(indexNumber + 1)).toString()));
			}
			// Blank out until the feature is implemented.
			personLanguageChoice[i].add(""); 
			for (int indexNumber = 0; indexNumber < Interact.cultureLines.length; indexNumber++) {
				personLanguageChoice[i].add(Interact.cultureLines[indexNumber]);
			}
			for (int abos = 0; abos < 4; abos++) { // Label ABOS checkboxes.
				if (abos == 0) {
					personControllingCase[i][abos]
						.setLabel(Interact.InteractText.getString("actor"));
				} else if (abos == 1) {
					personControllingCase[i][abos]
						.setLabel(Interact.InteractText.getString("behavior"));
				} else if (abos == 2) {
					personControllingCase[i][abos]
						.setLabel(Interact.InteractText.getString("obj"));
				} else {
					personControllingCase[i][abos]
						.setLabel(Interact.InteractText.getString("setting"));
				}
				personControllingCase[i][abos].setBackground(Color.white);
			}
			for (int epa = 0; epa < 3; epa++) { // Label EPA checkboxes.
				if (epa == 0) {
					personControllingEPA[i][epa].setLabel(Interact.InteractText
						.getString("evaluation"));
				} else if (epa == 1) {
					personControllingEPA[i][epa].setLabel(Interact.InteractText
						.getString("potency"));
				} else {
					personControllingEPA[i][epa].setLabel(Interact.InteractText
						.getString("activity"));
				}
				personControllingEPA[i][epa].setBackground(Color.white);
			}
			personLanguageChoice[i].setEnabled(false);
		}

		this.add(nameTitlePanel, new GridBagConstraints_(0, 0, 1, 1, 1.0, 1.0,
			GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0,
				0, 0), 0, 0));
		this.add(person1NamePanel, new GridBagConstraints_(0, 1, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 2, 0, 2), 0, 0));
		this.add(person2NamePanel, new GridBagConstraints_(0, 2, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person3NamePanel, new GridBagConstraints_(0, 3, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person4NamePanel, new GridBagConstraints_(0, 4, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		nameTitlePanel.add(nameTitleLabel, null);
		person1NamePanel.add(personName[0], null);
		person2NamePanel.add(personName[1], null);
		person3NamePanel.add(personName[2], null);
		person4NamePanel.add(personName[3], null);

		this.add(biologyTitlePanel, new GridBagConstraints_(1, 0, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person1BiologyPanel, new GridBagConstraints_(1, 1, 1, 1, 1.0,
			1.0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(person2BiologyPanel, new GridBagConstraints_(1, 2, 1, 1, 1.0,
			1.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person3BiologyPanel, new GridBagConstraints_(1, 3, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person4BiologyPanel, new GridBagConstraints_(1, 4, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		biologyTitlePanel.add(biologyTitleLabel, null);
		person1BiologyPanel.add(personBiologyChoice[0], null);
		person2BiologyPanel.add(personBiologyChoice[1], null);
		person3BiologyPanel.add(personBiologyChoice[2], null);
		person4BiologyPanel.add(personBiologyChoice[3], null);

		this.add(languageTitlePanel, new GridBagConstraints_(2, 0, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person1LanguagePanel, new GridBagConstraints_(2, 1, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person2LanguagePanel, new GridBagConstraints_(2, 2, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person3LanguagePanel, new GridBagConstraints_(2, 3, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person4LanguagePanel, new GridBagConstraints_(2, 4, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		languageTitlePanel.add(languageTitleLabel, null);
		person1LanguagePanel.add(personLanguageChoice[0], null);
		person2LanguagePanel.add(personLanguageChoice[1], null);
		person3LanguagePanel.add(personLanguageChoice[2], null);
		person4LanguagePanel.add(personLanguageChoice[3], null);
		languageTitlePanel.setVisible(false);
		person1LanguagePanel.setVisible(false);
		person2LanguagePanel.setVisible(false);
		person3LanguagePanel.setVisible(false);
		person4LanguagePanel.setVisible(false);

		this.add(controllingTitlePanel, new GridBagConstraints_(3, 0, 2, 1,
			1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		controllingTitlePanel.add(controllingTitleLabel, null);
		controllingTitlePanel.setVisible(false);

		this.add(person1ControllingEPAPanel, new GridBagConstraints_(4, 1, 1,
			1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person2ControllingEPAPanel, new GridBagConstraints_(4, 2, 1,
			1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person3ControllingEPAPanel, new GridBagConstraints_(4, 3, 1,
			1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person4ControllingEPAPanel, new GridBagConstraints_(4, 4, 1,
			1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0), 0, 0));
		person1ControllingEPAPanel.add(personControllingEPA[0][0], null);
		person1ControllingEPAPanel.add(personControllingEPA[0][1], null);
		person1ControllingEPAPanel.add(personControllingEPA[0][2], null);
		person2ControllingEPAPanel.add(personControllingEPA[1][0], null);
		person2ControllingEPAPanel.add(personControllingEPA[1][1], null);
		person2ControllingEPAPanel.add(personControllingEPA[1][2], null);
		person3ControllingEPAPanel.add(personControllingEPA[2][0], null);
		person3ControllingEPAPanel.add(personControllingEPA[2][1], null);
		person3ControllingEPAPanel.add(personControllingEPA[2][2], null);
		person4ControllingEPAPanel.add(personControllingEPA[3][0], null);
		person4ControllingEPAPanel.add(personControllingEPA[3][1], null);
		person4ControllingEPAPanel.add(personControllingEPA[3][2], null);
		person1ControllingEPAPanel.setVisible(false);
		person2ControllingEPAPanel.setVisible(false);
		person3ControllingEPAPanel.setVisible(false);
		person4ControllingEPAPanel.setVisible(false);

		this.add(person1ControllingCasePanel, new GridBagConstraints_(3, 1, 1,
			1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person2ControllingCasePanel, new GridBagConstraints_(3, 2, 1,
			1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person3ControllingCasePanel, new GridBagConstraints_(3, 3, 1,
			1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(person4ControllingCasePanel, new GridBagConstraints_(3, 4, 1,
			1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		person1ControllingCasePanel.add(personControllingCase[0][0], null);
		person1ControllingCasePanel.add(personControllingCase[0][1], null);
		person1ControllingCasePanel.add(personControllingCase[0][2], null);
		person1ControllingCasePanel.add(personControllingCase[0][3], null);
		person2ControllingCasePanel.add(personControllingCase[1][0], null);
		person2ControllingCasePanel.add(personControllingCase[1][1], null);
		person2ControllingCasePanel.add(personControllingCase[1][2], null);
		person2ControllingCasePanel.add(personControllingCase[1][3], null);
		person3ControllingCasePanel.add(personControllingCase[2][0], null);
		person3ControllingCasePanel.add(personControllingCase[2][1], null);
		person3ControllingCasePanel.add(personControllingCase[2][2], null);
		person3ControllingCasePanel.add(personControllingCase[2][3], null);
		person4ControllingCasePanel.add(personControllingCase[3][0], null);
		person4ControllingCasePanel.add(personControllingCase[3][1], null);
		person4ControllingCasePanel.add(personControllingCase[3][2], null);
		person4ControllingCasePanel.add(personControllingCase[3][3], null);
		person1ControllingCasePanel.setVisible(false);
		person2ControllingCasePanel.setVisible(false);
		person3ControllingCasePanel.setVisible(false);
		person4ControllingCasePanel.setVisible(false);

		this.add(visagePanel, new GridBagConstraints_(0, 5, 5, 1, 1.0, 2.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,
				0, 0, 0), 0, 0));
		for (int i = 0; i < Face.NUMBER_OF_FACES; i++) {
			displayFace = new Face();
			displayFace.setPictureChoice(i);
			visagePanel.add(displayFace);
		}
		recallInformation();
		personName[0].addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				personName_focusGained(0);
			}

			public void focusLost(FocusEvent e) {
				personName_focusLost(0);
			}
		});
		personName[1].addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				personName_focusGained(1);
			}

			public void focusLost(FocusEvent e) {
				personName_focusLost(1);
			}
		});
		personName[2].addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				personName_focusGained(2);
			}

			public void focusLost(FocusEvent e) {
				personName_focusLost(2);
			}
		});
		personName[3].addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				personName_focusGained(3);
			}

			public void focusLost(FocusEvent e) {
				personName_focusLost(3);
			}
		});
	}

	/** Recall information that already has been set. */
	void recallInformation() {
		for (int i = 0; i < Interact.numberOfInteractants; i++) {
			personName[i].setText(Interact.person[i].name);
			personBiologyChoice[i].select(Interact.person[i].visage);
			for (int abos = 0; abos < 4; abos++) {
				personControllingCase[i][abos]
					.setState(Interact.person[i].controllingCase[abos]);
			}
			for (int epa = 0; epa < 3; epa++) {
				personControllingEPA[i][epa]
					.setState(Interact.person[i].controllingEPA[epa]);
			}
		}
	} // End recallInformation.

	/** Store data displayed on the page. */
	void storeInformation() {
		int index;
		for (int i = 0; i < Interact.numberOfInteractants; i++) {
			Interact.person[i].name = personName[i].getText();
			index = personBiologyChoice[i].getSelectedIndex();
			Interact.person[i].visage = index;
			if (index < NUMBER_OF_VISAGES_PER_SEX) {
				Interact.person[i].sex = Person.MALE;
			} else {
				Interact.person[i].sex = Person.FEMALE;
			}
			for (int abos = 0; abos < 4; abos++) {
				Interact.person[i].controllingCase[abos] =
					personControllingCase[i][abos].getState();
			}
			for (int epa = 0; epa < 3; epa++) {
				Interact.person[i].controllingEPA[epa] =
					personControllingEPA[i][epa].getState();
			}
		}
		/**
		 * Incorporate the changes in menus, etc. on forms used elsewhere in
		 * Interact.
		 */
		Interact.display.remove(Interact.definePerson);
		Interact.display.remove(Interact.defineEvents);
		Interact.display.remove(Interact.analyzeEvents);
		Interact.definePerson = new DefineSituationCard();
		Interact.defineEvents = new DefineEventsCard();
		Interact.analyzeEvents = new AnalyzeEventsCard();
		int itemNumber = Interact.controls.DEFINE_SITUATION;
		Interact.display.add(Interact.operationMenuLines[itemNumber],
			Interact.definePerson);
		Interact.display.add(Interact.operationMenuLines[++itemNumber],
			Interact.defineEvents);
		Interact.display.add(Interact.operationMenuLines[++itemNumber],
			Interact.analyzeEvents);
	} // End storeInformation.

	void personName_focusGained(int theOne) {
		personName[theOne].selectAll();
	}

	void personName_focusLost(int theOne) {
		personName[theOne].select(0, 0);
	}

}