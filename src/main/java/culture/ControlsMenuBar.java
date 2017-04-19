package culture;

import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.util.Locale;

/** Menu bar for changing cultures, complexity, card-page, and for calling help. */
public class ControlsMenuBar extends Panel {

	// Define choice positions on level-of-functioning menu.
	static final int BASIC = 0;

	static final int ADVANCED = 1;

	// Define choice positions on operations menu.
	final int SELECT_OPTIONS = 0;

	final int EXPLORE_SELF = 1;

	final int DEFINE_INTERACTANTS = 2;

	final int DEFINE_SITUATION = 3;

	final int DEFINE_EVENT = 4;

	final int ANALYZE_EVENTS = 5;

	final int BUILD_INTERACTION = 6;

	final int FIND_CONCEPTS = 7;

	final int TEST_EMOTIONS = 8;

	final int VIEW_EQUATIONS = 9;

	final int INPUT = 10;

	final int REPORT = 11;

	static boolean inferringFromEmotion = false;

	static boolean exploringSelf = false;

	Choice operationsChoice = new Choice();

	Choice complexityChoice = new Choice();

	Choice cultureChoice = new Choice();

	int currentCardIndex;

	boolean changedAnEvent = false;

	FlowLayout flow = new FlowLayout(FlowLayout.CENTER);

	ControlsMenuBar() { // Constructor method for control menus.
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setBackground(Color.gray);
		this.setLayout(flow);
		this.add(cultureChoice);
		this.add(complexityChoice);
		this.add(operationsChoice);
		fillMenus();
		operationsChoice.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				operationsChoice_itemStateChanged(e);
			}
		});
		complexityChoice.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				complexityChoice_itemStateChanged(e);
			}
		});
		cultureChoice.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cultureChoice_itemStateChanged(e);
			}
		});
	} // End Controls constructor.

	void fillMenus() {
		cultureChoice.removeAll();
		complexityChoice.removeAll();
		operationsChoice.removeAll();
		for (int i = 0; i < Interact.cultureLines.length; i++) {
			cultureChoice.add(Interact.cultureLines[i]);
		}
		for (int i = 0; i < Interact.displayLines.length; i++) {
			complexityChoice.add(Interact.displayLines[i]);
		}
		for (int i = 0; i < Interact.operationLines.length; i++) {
			operationsChoice.add(Interact.operationLines[i]);
		}
	}

	void helpChoice_itemStateChanged(ItemEvent e) {
		// Applet (Interact.java) listens for HELP requests and handles them.
	}

	void operationsChoice_itemStateChanged(ItemEvent e) {
		// Move from one form to another.
		boolean atBasicLevel = complexityChoice.getSelectedIndex() == BASIC;
		int theChoice = operationsChoice.getSelectedIndex();
		Interact.onInteractionsCard = false;
		// Store user's choice of options on leaving set-options card.
		if (currentCardIndex == SELECT_OPTIONS) {
			Interact.setOptions.storeOptions();
		}
		// Store interactants' data on leaving interactants definition card.
		if (currentCardIndex == DEFINE_INTERACTANTS) {
			Interact.defineInteractants.storeInformation();
		}
		if (currentCardIndex == EXPLORE_SELF) {
			exploringSelf = false;
		}
		// Store situations data on leaving person definition card.
		if (currentCardIndex == DEFINE_SITUATION) {
			// Make unspecified definitions parallel.
			Interact.definePerson.copyPersonOneSelections();
		}
		// Store event data on leaving event definition card.
		if (currentCardIndex == DEFINE_EVENT) {
			Interact.defineEvents.storeViewer(Interact.defineEvents.viewerMenu
				.getSelectedIndex());
		}
		// Convert back to zero Phi's on leaving test-emotion card.
		if (currentCardIndex == TEST_EMOTIONS) {
			inferringFromEmotion = false;
		}

		if (theChoice == SELECT_OPTIONS) {
			Interact.setOptions.recallOptions();
		}
		if (theChoice == EXPLORE_SELF) {
			exploringSelf = true;
		}
		if (theChoice == DEFINE_SITUATION) {
			Interact.viewer = Interact.definePerson.egoMenu.getSelectedIndex();
			Interact.definePerson.setSelections();
		}
		if (theChoice == DEFINE_EVENT) {
			// Show current viewer.
			Interact.viewer = Interact.defineEvents.viewerMenu.getSelectedIndex();
			// Describe viewer.
			Interact.defineEvents.viewerFeatures.setText(Interact
				.identifyViewerSexAndSetting(Interact.viewer)); 
			// Show viewer's events.
			Interact.defineEvents.fillEventList(Interact.viewer); 
			// Can't copy Person 1's events on arrival.
			Interact.defineEvents.usePriorButton.setEnabled(false); 
		}
		if (theChoice == ANALYZE_EVENTS) {
			Interact.viewer = Interact.analyzeEvents.viewerMenu.getSelectedIndex();
			Interact.analyzeEvents.viewerFeatures.setText(Interact
				.identifyViewerSexAndSetting(Interact.viewer));
			Interact.analyzeEvents.clearAllResults(); // Clear results boxes.
			// Refill events box with current viewer's perceptions.
			Interact.analyzeEvents.eventList.removeAll();
			for (int i = 0; i < Interact.person[Interact.viewer].serialEvents
			.size(); i++) {
				EventRecord anEvent =
					(EventRecord) Interact.person[Interact.viewer].serialEvents
					.elementAt(i);
				String line = anEvent.toString(EventRecord.SITUATION);
				// Remove Return from end of line.
				line = line.substring(0, line.length() - 1); 
				Interact.analyzeEvents.eventList.add(line);
			}
		}
		if (theChoice == BUILD_INTERACTION) {
			Interact.onInteractionsCard = true;
			if (Interact.interactions.arraysUninitialized){
				Interact.interactions.initializeInteractants();
			}
		}
		if (theChoice == FIND_CONCEPTS) {
			Interact.findConcepts.ratingScale.reset();
			Interact.findConcepts.scaleArea.invalidate();
			Interact.findConcepts.ratingScale.invalidate();
			Interact.findConcepts.cutoff.invalidate();
			Interact.findConcepts.profileEPA.setText("0.0,0.0,0.0");
			Interact.findConcepts.search();
			Interact.findConcepts.cutoff.setText(Interact
				.formatLocaleDecimal(Interact.searchCutoff));
			Interact.findConcepts.cutoff.validate();
			Interact.findConcepts.cutoff.validate();
			Interact.findConcepts.ratingScale.validate();

		}
		if (theChoice == VIEW_EQUATIONS) {
			Interact.viewEquations.equationDisplay.setText("");
			Interact.viewEquations.showEquations();
		}
		if (theChoice == EXPLORE_SELF) {
		}
		if (theChoice == TEST_EMOTIONS) {
			// Trigger Phi computations in Equations routines.
			inferringFromEmotion = true;
			// Fill the lists on the form with entries from current
			// dictionaries.
			Interact.studyEmotions.fillModifierLists();
			Interact.studyEmotions.fill_ABO_Lists();
		}
		if (theChoice == INPUT) {
		}
		if (theChoice == REPORT) {
			if (atBasicLevel) {
				Interact.listResults.results.setText(Interact.reportAdvanced);
				// No numbers except deflection.
			} else {
				Interact.listResults.results.setText(ViewReportCard
					.dictionaryStatistics() + Interact.reportExpert);
			}
		}
		// Display the relevant display.
		currentCardIndex = theChoice;
		setComplexityLevel(complexityChoice.getSelectedIndex());
	} // End operationsChoice_itemStateChanged.

	void complexityChoice_itemStateChanged(ItemEvent e) {
		// Go through operations choice to change notBasic card.
		operationsChoice_itemStateChanged(e);
	} // End complexityChoice_itemStateChanged.

	void cultureChoice_itemStateChanged(ItemEvent e) {
		int chosenComplexity, chosenOperation;
		chosenComplexity = complexityChoice.getSelectedIndex();
		chosenOperation = operationsChoice.getSelectedIndex();
		// Destroy current card to indicate something is happening and to
		// prepare for reconstruction.
		Interact.reportAdvanced = "";
		Interact.reportExpert = "";
		Interact.display.remove(Interact.defineInteractants);
		Interact.display.remove(Interact.setOptions);
		Interact.display.remove(Interact.analyzeSelf);
		Interact.display.remove(Interact.definePerson);
		Interact.display.remove(Interact.defineEvents);
		Interact.display.remove(Interact.analyzeEvents);
		Interact.display.remove(Interact.interactions);
		Interact.display.remove(Interact.findConcepts);
		Interact.display.remove(Interact.studyEmotions);
		Interact.display.remove(Interact.viewEquations);
		Interact.display.remove(Interact.importExport);
		Interact.display.remove(Interact.listResults);
		// Read the new culture.
		int currentCultureIndex = cultureChoice.getSelectedIndex();
		switch (currentCultureIndex) {
		case 0:
			Interact.currentLocale = Interact.America;
			Interact.inputCulture();
			break;
		case 1:
			Interact.currentLocale = Interact.TX98;
			Interact.inputCulture();
			break;
		case 2:
			Interact.currentLocale = Interact.UNC78;
			Interact.inputCulture();
			break;
		case 3:
			Interact.currentLocale = Interact.Canada81;
			Interact.inputCulture();
			break;
		case 4:
			Interact.currentLocale = Interact.Canada01;
			Interact.inputCulture();
			break;
		case 5:
			Interact.currentLocale = Interact.Ireland;
			Interact.inputCulture();
			break;
		case 6:
			Interact.currentLocale = Interact.Germany;
			Interact.inputCulture();
			break;
		case 7:
			Interact.currentLocale = Interact.Germany07;
			Interact.inputCulture();
			break;
		case 8:
			Interact.currentLocale = Interact.Japan;
			Interact.inputCulture();
			break;
		case 9:
			Interact.currentLocale = Interact.China;
			Interact.inputCulture();
			break;
		case 10:
			Interact.currentLocale = Interact.Deutsch;
			Interact.inputCulture();
			break;
		case 11:
			Interact.currentLocale = Interact.Deutsch07;
			Interact.inputCulture();
			break;
		}
		// Re-create the interactants.
		Interact.setDefaultPeople();
		// Re-create displays.
		Interact.defineTheCards();
		// Set the equations.
		Locale tempLocale = Interact.currentLocale;
		if (tempLocale == Interact.America | tempLocale == Interact.TX98
				| tempLocale == Interact.UNC78 | tempLocale == Interact.Ireland) {
			Interact.viewEquations.equationStudies.select(0);
		} else if (tempLocale == Interact.Japan | tempLocale == Interact.Nippon) {
			Interact.viewEquations.equationStudies.select(1);
		} else if (tempLocale == Interact.Canada81
				| tempLocale == Interact.Canada01) {
			Interact.viewEquations.equationStudies.select(2);
		} else if (tempLocale == Interact.China | tempLocale == Interact.PRC) {
			Interact.viewEquations.equationStudies.select(3);
		} else if (tempLocale == Interact.Germany | tempLocale == Interact.Germany07) {
			Interact.viewEquations.equationStudies.select(4);
		}
		currentCardIndex = 1;
		operationsChoice_itemStateChanged(e);
		// Go through operations choice to revalidate current card.
		this.invalidate();
		fillMenus();
		complexityChoice.select(chosenComplexity);
		cultureChoice.select(currentCultureIndex);
		operationsChoice.select(chosenOperation);
		this.validate();
	} // End cultureChoice_itemStateChanged.

	void setComplexityLevel(int level) {
		// Set the elements and functions that are visible.
		int i;
		if (level == BASIC) {
			// No settings, attributes, labeling, EPAs, deflections,
			// conceptGates.
			Interact.definePerson.settingIs.setVisible(false);
			Interact.definePerson.settingList.setVisible(false);
			Interact.definePerson.modifierList.setVisible(false);
			Interact.definePerson.institutionTitle.setVisible(false);
			Interact.definePerson.institutionMenu.setVisible(false);
			Interact.definePerson.constraints.fill = GridBagConstraints.NONE;
			Interact.definePerson.constraints.weightx = 1.0;
			Interact.definePerson.constraints.weighty = 1.0;
			Interact.definePerson.constraints.gridwidth = 1;
			Interact.definePerson.constraints.gridheight = 1;
			Interact.definePerson.constraints.anchor = GridBagConstraints.SOUTH;
			Interact.definePerson.constraints.gridx = 1;
			Interact.definePerson.constraints.gridy = 1;
			Interact.definePerson.gridbag.setConstraints(
				Interact.definePerson.identityTitleArea,
				Interact.definePerson.constraints);
			Interact.defineEvents.behaviorMeaning.setVisible(false);
			Interact.defineEvents.behaviorTypesTitle.setVisible(false);
			Interact.defineEvents.corporalCheckbox.setVisible(false);
			Interact.defineEvents.overtCheckbox.setVisible(false);
			Interact.defineEvents.triadicCheckbox.setVisible(false);
			Interact.analyzeEvents.actorAttributes.setVisible(false);
			Interact.analyzeEvents.actorAttributeTitle.setVisible(false);
			Interact.analyzeEvents.actorLabels.setVisible(false);
			Interact.analyzeEvents.actorLabelTitle.setVisible(false);
			Interact.analyzeEvents.objectAttributes.setVisible(false);
			Interact.analyzeEvents.objectAttributeTitle.setVisible(false);
			Interact.analyzeEvents.objectLabels.setVisible(false);
			Interact.analyzeEvents.objectLabelTitle.setVisible(false);
			Interact.analyzeEvents.conceptGates.setVisible(false);
			Interact.analyzeEvents.presentFutureMenu.setVisible(false);
			Interact.definePerson.settingEPA.setVisible(false);
			Interact.definePerson.identityEPA.setVisible(false);
			Interact.definePerson.modifierEPA.setVisible(false);
			Interact.definePerson.combinationEPA.setVisible(false);
			Interact.definePerson.combinationTitle.setVisible(false);
			Interact.defineEvents.behaviorEPA.setVisible(false);
			Interact.analyzeEvents.actorEmotionEPA.setVisible(false);
			Interact.analyzeEvents.actorBehaviorEPA.setVisible(false);
			Interact.analyzeEvents.actorAttributeEPA.setVisible(false);
			Interact.analyzeEvents.actorLabelEPA.setVisible(false);
			Interact.analyzeEvents.objectEmotionEPA.setVisible(false);
			Interact.analyzeEvents.objectBehaviorEPA.setVisible(false);
			Interact.analyzeEvents.objectAttributeEPA.setVisible(false);
			Interact.analyzeEvents.objectLabelEPA.setVisible(false);
			Interact.analyzeEvents.graphArea.setVisible(false);
			Interact.findConcepts.cutoffTitle.setVisible(false);
			Interact.findConcepts.cutoff.setVisible(false);
			Interact.findConcepts.profileTitle.setVisible(false);
			Interact.findConcepts.profileEPA.setVisible(false);
			Interact.findConcepts.ratingScale.setVisible(true);
			i = Interact.definePerson.identityList.getSelectedIndex();
			if (i >= 0) {
				Interact.definePerson.identityList.makeVisible(i);
			} // Go to selected item.
			for (int f = 0; f < Data.NUMBER_OF_STANDARD_CONCEPT_GATES; f++) {
				Interact.findConcepts.conceptGateCheckbox[f].setVisible(false);
			}
		} else {
			// Advanced: show settings, attributes, labeling, conceptGates,
			// EPAs, deflections.
			Interact.definePerson.settingIs.setVisible(true);
			Interact.definePerson.settingList.setVisible(true);
			Interact.definePerson.modifierList.setVisible(true);
			Interact.definePerson.institutionTitle.setVisible(true);
			Interact.definePerson.institutionMenu.setVisible(true);
			Interact.definePerson.constraints.fill = GridBagConstraints.NONE;
			Interact.definePerson.constraints.weightx = 1.0;
			Interact.definePerson.constraints.weighty = 1.0;
			Interact.definePerson.constraints.gridwidth = 2;
			Interact.definePerson.constraints.gridheight = 1;
			Interact.definePerson.constraints.anchor = GridBagConstraints.SOUTH;
			Interact.definePerson.constraints.gridx = 1;
			Interact.definePerson.constraints.gridy = 1;
			Interact.definePerson.gridbag.setConstraints(
				Interact.definePerson.identityTitleArea,
				Interact.definePerson.constraints);
			Interact.defineEvents.behaviorMeaning.setVisible(true);
			Interact.defineEvents.behaviorTypesTitle.setVisible(true);
			Interact.defineEvents.corporalCheckbox.setVisible(true);
			Interact.defineEvents.overtCheckbox.setVisible(true);
			Interact.defineEvents.triadicCheckbox.setVisible(true);
			Interact.analyzeEvents.actorAttributes.setVisible(true);
			Interact.analyzeEvents.actorAttributeTitle.setVisible(true);
			Interact.analyzeEvents.actorLabels.setVisible(true);
			Interact.analyzeEvents.actorLabelTitle.setVisible(true);
			Interact.analyzeEvents.objectAttributes.setVisible(true);
			Interact.analyzeEvents.objectAttributeTitle.setVisible(true);
			Interact.analyzeEvents.objectLabels.setVisible(true);
			Interact.analyzeEvents.objectLabelTitle.setVisible(true);
			Interact.analyzeEvents.objectLabels.validate();
			Interact.analyzeEvents.conceptGates.setVisible(true);
			Interact.analyzeEvents.presentFutureMenu.setVisible(true);
			Interact.definePerson.settingEPA.setVisible(true);
			Interact.definePerson.identityEPA.setVisible(true);
			Interact.definePerson.modifierEPA.setVisible(true);
			Interact.definePerson.combinationEPA.setVisible(true);
			Interact.definePerson.combinationTitle.setVisible(true);
			Interact.defineEvents.behaviorEPA.setVisible(true);
			Interact.analyzeEvents.actorEmotionEPA.setVisible(true);
			Interact.analyzeEvents.actorBehaviorEPA.setVisible(true);
			Interact.analyzeEvents.actorAttributeEPA.setVisible(true);
			Interact.analyzeEvents.actorLabelEPA.setVisible(true);
			Interact.analyzeEvents.objectEmotionEPA.setVisible(true);
			Interact.analyzeEvents.objectBehaviorEPA.setVisible(true);
			Interact.analyzeEvents.objectAttributeEPA.setVisible(true);
			Interact.analyzeEvents.objectLabelEPA.setVisible(true);
			Interact.analyzeEvents.graphArea.setVisible(true);
			Interact.findConcepts.cutoffTitle.setVisible(true);
			Interact.findConcepts.cutoff.setVisible(true);
			Interact.findConcepts.profileTitle.setVisible(true);
			Interact.findConcepts.profileEPA.setVisible(true);
			i = Interact.definePerson.settingList.getSelectedIndex();
			if (i >= 0) {
				Interact.definePerson.settingList.makeVisible(i);
			}
			i = Interact.definePerson.identityList.getSelectedIndex();
			if (i >= 0) {
				Interact.definePerson.identityList.makeVisible(i);
			}
			i = Interact.definePerson.modifierList.getSelectedIndex();
			if (i >= 0) {
				Interact.definePerson.modifierList.makeVisible(i);
			}
			for (int f = 0; f < Data.NUMBER_OF_STANDARD_CONCEPT_GATES; f++) {
				Interact.findConcepts.conceptGateCheckbox[f].setVisible(true);
			}
		}
		Interact.findConcepts.search();
		Interact.display.invalidate();
		// Show the relevant display.
		Interact.card.show(Interact.display,
			Interact.operationMenuLines[currentCardIndex]);
		Interact.display.validate();
	} // End setComplexityLevel.

} // End class ControlMenus.

