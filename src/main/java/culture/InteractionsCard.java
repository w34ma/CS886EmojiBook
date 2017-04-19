package culture;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BoxLayout;

/** 
 * Page for generating extemporaneous interactions. 
 * */


public class InteractionsCard extends Panel implements ActionListener {

	Panel behaviorPanel = new Panel();

	Panel interactantsLeft = new Panel(); // Contains panels for persons 1 and 3.
	Panel interactantsRight = new Panel(); // Contains panels for person 2 and 4.

	Panel[] actorPanel = new Panel[4];
	Panel[] actionPanel = new Panel[4];
	Panel[] emotionPanel = new Panel[4];
	Panel[] faceArea = new Panel[4];

	Label[] name = new Label[4];
	Label[] tensionTitle = new Label[4];
	Label[] optimalObject = new Label[4];
	Label[] behaviorListTitle = new Label[4];

	Choice[] objectMenu = new Choice[4];
	List[] optimalBehaviors = new List[4];
	TextField[] behaviorEPA = new TextField[4];
	TextField[] emotionEPA = new TextField[4];
	Button[] doActionButton = new Button[4];

	Face[] face = new Face[4];

	MathModel equationSet, emotionEqs, traitEqs;
	boolean male, arraysUninitialized;

	String tensionEquals, optimalAlterIs;
	String behaviorsTitle, buttonText;
	String optimalActTag, myself;
	String[] buttonIndex = new String[4];
	String possiblesTitle;

	int numberOfActors;
	double[][][] fundamentals = new double[4][4][3];
	double[][][] currentTransients = new double[4][4][3];
	double [] currentTensions = new double[4];
	DataList[] possibleEvents = new DataList[4];
	EventRecord[] optimalNextEvent = new EventRecord[4];
	Data datum = new Data();

	double[] workingProfile = { 1, 1, 1 };

	String newLine = Interact.InteractText.getString("paragraphCommand");
	String dotSpace = Interact.InteractText.getString("sentenceSeparation");
	String comma = Interact.InteractText.getString("clauseSeparation");
	String space = Interact.InteractText.getString("space");
	String colonSpace = Interact.InteractText.getString("colon")
	+ Interact.InteractText.getString("space");
	String leftBracket = Interact.InteractText.getString("leftBracket");
	String rightBracket = Interact.InteractText.getString("rightBracket");

	int ACTOR = 0; 
	int BEHAVIOR = 1;
	int OBJECT = 2;
	int BEHAVIOR_LIST_LENGTH = 15;

	public InteractionsCard() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		/*
		 * Format the card and initialize variables.
		 */
		// Set basic strings.
		tensionEquals = Interact.InteractText.getString("personalTensionEquals");
		optimalAlterIs = Interact.InteractText.getString("optimalObjectIs");
		buttonText = Interact.InteractText.getString("actionButtonText");
		optimalActTag =Interact.InteractText.getString("optimalBehavior");
		myself = Interact.InteractText.getString("selfText");
		possiblesTitle = Interact.InteractText.getString("titleForBehaviors");

		// Section card into two parts.
		this.setLayout(new GridLayout(0,2));
		this.add(interactantsLeft);
		this.add(interactantsRight);


		// Create components for interactants.
		for (int i=0; i < 4; i++) {
			buttonIndex[i] = new String((new Integer(i)).toString());
			actorPanel[i] = new Panel();
			actionPanel[i] = new Panel();
			emotionPanel[i] = new Panel();
			faceArea[i] = new Panel();
			actorPanel[i].setLayout(new GridLayout(0,2,15,15));
			actionPanel[i].setLayout((LayoutManager) new BoxLayout(actionPanel[i], BoxLayout.PAGE_AXIS));
			emotionPanel[i].setLayout(new VerticalFlowLayout());
			name[i] = new Label();
			tensionTitle[i] = new Label();
			optimalObject[i] = new Label();
			objectMenu[i] = new Choice();
			behaviorListTitle[i] = new Label();
			optimalBehaviors[i] = new List();
			behaviorEPA[i] = new TextField();
			emotionEPA[i] = new TextField();
			doActionButton[i] = new Button(buttonText);
			face[i] = new Face();
			// Position components.
			actorPanel[i].add(actionPanel[i]);
			actorPanel[i].add(emotionPanel[i]);
			actionPanel[i].add(name[i]);
			actionPanel[i].add(tensionTitle[i]);
			actionPanel[i].add(optimalObject[i]);	
			actionPanel[i].add(objectMenu[i]);
			actionPanel[i].add(behaviorListTitle[i]);
			actionPanel[i].add(optimalBehaviors[i]);
			actionPanel[i].add(behaviorEPA[i]);
			actionPanel[i].add(doActionButton[i]);
			emotionPanel[i].add(face[i]);
			emotionPanel[i].add(emotionEPA[i]);
			doActionButton[i].setActionCommand(buttonIndex[i]);
			doActionButton[i].addActionListener(this);
		}

		// Set up interactants in two rows.
		interactantsLeft.setLayout(new GridLayout(2,1));
		interactantsRight.setLayout(new GridLayout(2,1));
		interactantsLeft.add(actorPanel[0]);
		interactantsRight.add(actorPanel[1]);
		interactantsLeft.add(actorPanel[2]);
		interactantsRight.add(actorPanel[3]);

		arraysUninitialized = true;
		initializeInteractants(); // Routine to fill components.

		optimalBehaviors[0].addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {
				optimalBehaviors_itemStateChanged(0);
			}
		});
		optimalBehaviors[1].addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {
				optimalBehaviors_itemStateChanged(1);
			}
		});
		optimalBehaviors[2].addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {
				optimalBehaviors_itemStateChanged(2);
			}
		});
		optimalBehaviors[3].addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {
				optimalBehaviors_itemStateChanged(3);
			}
		});
		objectMenu[0].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				objectMenu_itemStateChanged(0);
			}
		});
		objectMenu[1].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				objectMenu_itemStateChanged(1);
			}
		});
		objectMenu[2].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				objectMenu_itemStateChanged(2);
			}
		});
		objectMenu[3].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				objectMenu_itemStateChanged(3);
			}
		});
	} // End jbInit.

	
	public void objectMenu_itemStateChanged(int viewerActor){
		/*
		 * Change behaviors of the viewer-actor to those 
		 * that are optimal for a new object.
		 */
		this.invalidate();
		double[] behaviorProfile = {0, 0, 0};
		int choice = objectMenu[viewerActor].getSelectedIndex();
		String actorName = objectMenu[viewerActor].getItem(choice);
		int theNewObject = 0;
		while (! Interact.person[theNewObject++].name.equalsIgnoreCase(actorName));
		theNewObject--;
		
		// Search the list of optimal possible events for the one
		// whose object is the one chosen by the user.
		for (int i = 0; i < possibleEvents[viewerActor].size(); i++){
			EventRecord testEvent = (EventRecord) possibleEvents[viewerActor].elementAt(i);
			if (testEvent.vao[2] == theNewObject){
				behaviorProfile = testEvent.abosFundamentals[1];
			}
		}
		// Show the optimal behavior and some verbalizations for this object.
		showBehaviors(viewerActor, behaviorProfile);
		this.validate();
	} // End objectMenu_itemStateChanged.

	
	public void actionPerformed(ActionEvent e) {
		/*
		 * Respond to click of a perform-this-action button.
		 * The ActionEvent e identifies which button was
		 * clicked and thereby which person should act. 
		 * Transients and emotions produced by the person's action 
		 * are computed for each interactant, and then the next
		 * round of interaction is set up by predicting each
		 * interactant's next behavior. 
		 */
		implementEvent(e.getActionCommand());
		setUpNextRound();
	} // End actionPerformed.


	void setUpNextRound(){
		/*
		 * Find everyone's optimal object and optimal behaviors.
		 */
		double[] profile = {0, 0, 0};
		Interact.reportExpert = Interact.reportExpert + newLine
		+ newLine + Interact.InteractText.getString("future");

		this.invalidate();
		for (int viewer = 0; viewer < numberOfActors; viewer++){
			// Find new optimal object.
			optimalNextEvent[viewer] = getOptimalObjectEvent(viewer);

			// Identify the optimal object on the card display.
			int opObj = optimalNextEvent[viewer].vao[2];
			optimalObject[viewer].setText(optimalAlterIs + (opObj+1));
			// Refill the object menu.
			int selectedItem = -1;
			int menuItem =-1;
			objectMenu[viewer].removeAll();
			for (int j = 0; j < numberOfActors; j++) {
				if (j != viewer){
					++menuItem;
					objectMenu[viewer].add(Interact.person[j].name);
					if (j == opObj) selectedItem = menuItem;
				}
			}
			objectMenu[viewer].select(selectedItem);

			profile = optimalNextEvent[viewer].abosFundamentals[BEHAVIOR];

			showBehaviors(viewer, profile);
			this.validate();

			// Archive the optimal behavior EPA.
			Interact.reportExpert = Interact.reportExpert
			+ newLine + Interact.person[viewer].name
			+ comma + Interact.InteractText.getString("titleForBehaviors")
			+ space;
			// Present optimal events for each object.
			for (int i = 0; i < possibleEvents[viewer].size(); i++){
				Interact.reportExpert = Interact.reportExpert + leftBracket;
				EventRecord testEvent = (EventRecord) possibleEvents[viewer].elementAt(i);
				if (testEvent.vao[2] == opObj){
					Interact.reportExpert = Interact.reportExpert
					+ Interact.InteractText.getString("optimalBehavior") + colonSpace;
				}
				profile = testEvent.abosFundamentals[1];
				Interact.reportExpert = Interact.reportExpert
				+ profileToString(profile).trim() + comma
				+ Interact.person[testEvent.vao[2]].name 
				+ Interact.formatLocaleDecimal(testEvent.deflection)
				+ rightBracket + space;
			} // End for-i
		} // End for-viewer.
		Interact.reportExpert = Interact.reportExpert + newLine +newLine;
	} // End setUpNextRound.


	void showBehaviors(int viewerActor, double[] behaviorProfile){
		/*
		 * Show the given behavior's EPA profile, and some verbalizations.
		 */	
		boolean[] pairFilter = {true,false};
		boolean[] complexFilter = {false, false, false};
		double savedSearchCutoff;
		// Use the actor's institutions to select behaviors.
		datum = new Data();
		int viewerActorIdentity =
			Interact.person[viewerActor].viewOfPerson[viewerActor].nounWordNumber;
		datum = ((Data) Interact.identities.elementAt(viewerActorIdentity));
		boolean[] institutionsFilter = datum.divisionConceptGate;

		String lineText;
		optimalBehaviors[viewerActor].removeAll();
		optimalBehaviors[viewerActor].add(optimalActTag);
		boolean sex = Interact.person[viewerActor].sex;
		// Change searchCutoff so as to report a standard number of behaviors.
		savedSearchCutoff = Interact.searchCutoff;
		Interact.searchCutoff = 100.0;
		DataList retrievedBehaviors =
			Interact.behaviors.getMatches(behaviorProfile, sex,
				pairFilter, institutionsFilter, complexFilter);
		// Change searchCutoff back to its proper value.
		Interact.searchCutoff = savedSearchCutoff;
		Retrieval behaviorResultLine;
		for (int j = 0; j < BEHAVIOR_LIST_LENGTH; j++) {
			behaviorResultLine = (Retrieval) retrievedBehaviors.elementAt(j);
			if (behaviorResultLine.word.startsWith("_")){
				// Don't show optimal-profile behaviors.
				retrievedBehaviors.removeElementAt(j--);
			} else {
				lineText =
					Interact.formatLocaleDecimal(behaviorResultLine.D) 
					+ comma + behaviorResultLine.word;
				optimalBehaviors[viewerActor].add(lineText);
			}
		}
		// Select option 1, the optimal-act tag, for the initial display.
		optimalBehaviors[viewerActor].select(0);
		optimalBehaviors[viewerActor].validate();
		behaviorEPA[viewerActor].setText(profileToString(behaviorProfile));
	} // End showBehaviors.


	void optimalBehaviors_itemStateChanged(int actor) {
		/*
		 * Reset the displayed behavior EPA when the user selects 
		 * a different behavior in an optimal-behavior list.
		 */
		double[] profile = {0, 0, 0};
		String boxText;
		this.invalidate();

		if (optimalBehaviors[actor].getSelectedIndex() == 0) {
			// Show ideal behavior EPA.
			profile = optimalNextEvent[actor].abosFundamentals[1]; 			
		} else {
			// Show EPA for the selected behavior.
			String theEntry = optimalBehaviors[actor].getSelectedItem();
			int wordBeginsAt = theEntry.indexOf(comma);
			wordBeginsAt = wordBeginsAt + comma.length();
			theEntry = theEntry.substring(wordBeginsAt);
			int theBehavior = Interact.behaviors.getIndex(theEntry);
			datum =
				((Data) Interact.behaviors.elementAt(theBehavior));
			if (Interact.person[actor].sex) {
				System.arraycopy(datum.maleEPA, 0, profile, 0, 3);
			} else {
				System.arraycopy(datum.femaleEPA, 0, profile, 0, 3);
			}
		}
		boxText = "";
		for (int epa = 0; epa < 3; epa++) {
			boxText = boxText + Interact.formatLocaleDecimal(profile[epa]);
		}
		behaviorEPA[actor].setText(boxText);
		this.validate();
	} // End optimalBehaviors_itemStateChanged.


	void implementEvent(String actorNumber) {
		/** 
		 * Setup and perform computations for a selected event. 
		 * The actor is identified by the passed parameter, 
		 * which is the person number in the form of a string.
		 * */
		int theActor, theViewer, theBehavior, theObject;
		double[] profile = { 0, 0, 0 };
		String boxText;
		DataList performedActionList;
		equationSet = Interact.maleabo; // Dummy initialization.
		emotionEqs = Interact.maleEmotion; // Dummy initialization.
		this.invalidate();

		theActor = Integer.parseInt(actorNumber);

		String theEntry = optimalBehaviors[theActor].getSelectedItem();
		int wordBeginsAt = theEntry.indexOf(comma);
		if (wordBeginsAt < 0){
			wordBeginsAt = 0;
		} else {
			wordBeginsAt = wordBeginsAt + comma.length();
		}
		theEntry = theEntry.substring(wordBeginsAt);
		if (theEntry == optimalActTag){
			// The actor's optimal profile behavior was selected.
			// Add the optimal profile to the behavior
			// list so it can be retrieved.
			// The string version of the profile is the word
			// and the EPA is the same for males and females.
			boxText = behaviorEPA[theActor].getText();
			Data newEntry = DefineSituationCard.newDataEntry(boxText);
			Interact.behaviors.addElement(newEntry);
			theEntry = newEntry.word; 
		}
		theBehavior = Interact.behaviors.getIndex(theEntry);

		// The object of action is the one selected on the actor's object menu.
		int choice = objectMenu[theActor].getSelectedIndex();
		String actorName = objectMenu[theActor].getItem(choice);
		theObject = 0;
		while (! Interact.person[theObject++].name.equalsIgnoreCase(actorName));
		theObject--;

		// Compute results of the event for each interactant.
		for (theViewer = 0; theViewer < numberOfActors; theViewer++){
			// Define the event for this viewer.
			performedActionList = new DataList();
			EventRecord theEventForThisViewer =
				new EventRecord(theViewer, theActor, theBehavior, theObject);
			theEventForThisViewer.abosIndexes[0][0] =
				Interact.person[theViewer].viewOfPerson[theActor].modifierWordNumber;
			theEventForThisViewer.abosIndexes[0][1] =
				Interact.person[theViewer].viewOfPerson[theActor].nounWordNumber;
			theEventForThisViewer.abosIndexes[1][1] = theBehavior;
			theEventForThisViewer.abosIndexes[2][0] =
				Interact.person[theViewer].viewOfPerson[theObject].modifierWordNumber;
			theEventForThisViewer.abosIndexes[2][1] =
				Interact.person[theViewer].viewOfPerson[theObject].nounWordNumber;
			performedActionList.addElement(theEventForThisViewer);

			System.arraycopy(fundamentals[theViewer][theActor], 0, 
				theEventForThisViewer.abosFundamentals[ACTOR], 0, 3);
			System.arraycopy(fundamentals[theViewer][theObject], 0, 
				theEventForThisViewer.abosFundamentals[OBJECT], 0, 3);
			System.arraycopy(currentTransients[theViewer][theActor], 0, 
				theEventForThisViewer.abosTransientsIn[ACTOR], 0, 3);
			System.arraycopy(currentTransients[theViewer][theObject], 0, 
				theEventForThisViewer.abosTransientsIn[OBJECT], 0, 3);
			datum =
				((Data) Interact.behaviors.elementAt(theBehavior));
			if (Interact.person[theViewer].sex) { 
				System.arraycopy(datum.maleEPA, 0, 
					theEventForThisViewer.abosFundamentals[BEHAVIOR], 0, 3);
				System.arraycopy(datum.maleEPA, 0, 
					theEventForThisViewer.abosTransientsIn[BEHAVIOR], 0, 3);
			} else {
				System.arraycopy(datum.femaleEPA, 0, 
					theEventForThisViewer.abosFundamentals[BEHAVIOR], 0, 3);
				System.arraycopy(datum.femaleEPA, 0, 
					theEventForThisViewer.abosTransientsIn[BEHAVIOR], 0, 3);
			}

			// Select right equations, and compute impressions and deflection.
			setEquations(theEventForThisViewer);
			equationSet.impressions(theEventForThisViewer);

			// Copy the new transients into currentTransients
			System.arraycopy(theEventForThisViewer.abosTransientsOut[ACTOR], 0, 
				currentTransients[theViewer][theActor], 0, 3);
			System.arraycopy(theEventForThisViewer.abosTransientsOut[OBJECT], 0, 
				currentTransients[theViewer][theObject], 0, 3);
			// Archive event information for reporting.
			Interact.reportExpert =
				Interact.reportExpert
				+ Interact.InteractText.getString("viewer") 
				+ space + Interact.person[theViewer].name + newLine;
			Interact.reportExpert =
				Interact.reportExpert
				+ theEventForThisViewer.toString(EventRecord.SITUATION);
			Interact.reportExpert =
				Interact.reportExpert
				+ theEventForThisViewer.toString(EventRecord.QUANTITIES);
			Interact.reportExpert =
				Interact.reportExpert
				+ theEventForThisViewer.toString(EventRecord.DEFLECTION2);

			// Change actor's emotion display if viewer is actor.
			if (theViewer == theActor){
				clearResultsOfLastEvent(theViewer);
				profile =
					emotionEqs.computeModifier(
						theEventForThisViewer.abosFundamentals[ACTOR],
						theEventForThisViewer.abosTransientsOut[ACTOR]);
				face[theActor].setProfile(profile);
				emotionEPA[theActor].setText(profileToString(profile));
				Interact.reportExpert = Interact.reportExpert
					+ Interact.InteractText.getString("actorEmotions") 
					+ space + profileToString(profile) + newLine;
			}

			// Change object's emotion display if viewer is object.
			if (theViewer == theObject){
				clearResultsOfLastEvent(theViewer);
				profile =
					emotionEqs.computeModifier(
						theEventForThisViewer.abosFundamentals[OBJECT],
						theEventForThisViewer.abosTransientsOut[OBJECT]);
				face[theObject].setProfile(profile);
				emotionEPA[theObject].setText(profileToString(profile));
				Interact.reportExpert = Interact.reportExpert
				+ Interact.InteractText.getString("objectEmotions") 
				+ space + profileToString(profile) + newLine;
			}
		} // End for-theViewer.
		// Show the current tensions.
		computeTensionValues();
		for (int viewer = 0; viewer < numberOfActors; viewer++){
			// Show the new personal tensions.
			tensionTitle[viewer].invalidate();
			tensionTitle[viewer].setText(tensionEquals 
				+ Interact.formatLocaleDecimal(currentTensions[viewer]));
			actionPanel[viewer].validate();
		}
		this.validate();
	} // End implementEvent.


	void clearResultsOfLastEvent(int viewerActor) {
		/*
		 * Blank the behavior and emotion presentations
		 * for the given viewerActor.
		 */
		optimalBehaviors[viewerActor].removeAll();
		behaviorEPA[viewerActor].setText(" -xxxx -xxxx -xxxx");
		emotionEPA[viewerActor].setText(" -xxxx -xxxx -xxxx");
		face[viewerActor].setProfile(Face.BLANKING_PROFILE);
		face[viewerActor].repaint();
	} // End clearResultsOfLastEvent.


	void writeReport(
		String title, String idealEPAprofile, DataList matchesToProfile) {
		Retrieval resultLine;
		Interact.reportExpert = Interact.reportExpert + title + colonSpace;
		// Expert text report shows EPA profile and one retrieval word.
		resultLine = (Retrieval) matchesToProfile.elementAt(0);
		Interact.reportExpert =
			Interact.reportExpert + idealEPAprofile + dotSpace
			+ Interact.formatLocaleDecimal(resultLine.D) + comma
			+ resultLine.word + dotSpace + newLine;
	} // End writeReport.


	void initializeInteractants () {
		/*
		 * Set up the card when it is first called from the controls menu.
		 * (After the first time, the card is re-presented as it was.)
		 */
		double[] profile = {0, 0, 0};

		// Count actors here because showInteractants is called when invoking this page.
		if (Interact.person[0].viewOfPerson[0].nounWordNumber == -1){
			numberOfActors = -1; // Initialization of Interact.			
		} else {			
			for (numberOfActors = 0; numberOfActors < 4; numberOfActors++){ 
				if (Interact.person[numberOfActors].
						viewOfPerson[numberOfActors].nounWordNumber == -1) break;
			}
		}
		if (numberOfActors < 2) return; // Not enough interactants.

		// Set interactants' names and faces. Blank out non-present interactants.
		for (int i=0; i < 4; i++) {
			if (Interact.person[i].viewOfPerson[i].nounWordNumber > -1) {
				name[i].setText(Interact.person[i].name);
				face[i].setPictureChoice(Interact.person[i].visage);
			}
			if (i < numberOfActors){
				actorPanel[i].setVisible(true);
			} else {
				actorPanel[i].setVisible(false);
			}
		}

		// Fill fundamentals and currentTransients arrays.
		for (int viewer = 0; viewer < numberOfActors; viewer++) {
			if (arraysUninitialized){
				male = Interact.person[viewer].sex;
				for (int interactant = 0; interactant < numberOfActors; interactant++) {
					int modifierIndex = Interact.person[viewer].viewOfPerson[interactant].modifierWordNumber;
					int nounIndex = Interact.person[viewer].viewOfPerson[interactant].nounWordNumber;
					if (nounIndex > -1){
						if (modifierIndex > -1) { // For a modified identity:
							// Amalgamate modifier + noun.
							profile =
								MathModel.amalgamate(male, modifierIndex,nounIndex);
							System.arraycopy(profile, 0, 
								fundamentals[viewer][interactant], 0, 3);
							System.arraycopy(profile, 0, 
								currentTransients[viewer][interactant], 0, 3);
						} else {
							datum =
								((Data) Interact.identities.elementAt(nounIndex));
							if (male) {
								System.arraycopy(datum.maleEPA, 0, 
									fundamentals[viewer][interactant], 0, 3);
								System.arraycopy(datum.maleEPA, 0, 
									currentTransients[viewer][interactant], 0, 3);
							} else {
								System.arraycopy(datum.femaleEPA, 0, 
									fundamentals[viewer][interactant], 0, 3);
								System.arraycopy(datum.femaleEPA, 0, 
									currentTransients[viewer][interactant], 0, 3);
							}
						}
					}
				}
				// Create optimal-event lists.
				possibleEvents[viewer] = new DataList();
			}
			showEmotion(viewer);
		}
		arraysUninitialized = false;

		setUpNextRound();
		
		this.validate();

	} //End initializeInteractants.


	EventRecord getOptimalObjectEvent (int viewerActor) {
		/*
		 * For the given viewer-actor, formulate all events with others
		 * as objects. Find the one that produces the smallest deflection,
		 * and return that event.
		 */
		EventRecord testEvent;
		double[] profile = { 0, 0, 0 };
		possibleEvents[viewerActor].removeAllElements();
		// Create a list of possible events for viewerActor.
		for (int testObject = 0; testObject < numberOfActors; testObject++){
			if (testObject != viewerActor){
				testEvent = new EventRecord(viewerActor, viewerActor, -1, testObject);
				testEvent.abosIndexes[0][0] =
					Interact.person[viewerActor].viewOfPerson[viewerActor].modifierWordNumber;
				testEvent.abosIndexes[0][1] =
					Interact.person[viewerActor].viewOfPerson[viewerActor].nounWordNumber;
				testEvent.abosIndexes[1][1] = -1;
				testEvent.abosIndexes[2][0] =
					Interact.person[viewerActor].viewOfPerson[testObject].modifierWordNumber;
				testEvent.abosIndexes[2][1] =
					Interact.person[viewerActor].viewOfPerson[testObject].nounWordNumber;
				// Actor fundamentals and in-transients.
				System.arraycopy(fundamentals[viewerActor][viewerActor], 0, 
					testEvent.abosFundamentals[0], 0, 3);
				System.arraycopy(currentTransients[viewerActor][viewerActor], 0, 
					testEvent.abosTransientsIn[0], 0, 3);
				// Object fundamentals and in-transients.
				System.arraycopy(fundamentals[viewerActor][testObject], 0, 
					testEvent.abosFundamentals[2], 0, 3);
				System.arraycopy(currentTransients[viewerActor][testObject], 0, 
					testEvent.abosTransientsIn[2], 0, 3);
				possibleEvents[viewerActor].addElement(testEvent);
			}
		}

		// Find the optimal behavior profile for each event and store it.
		for (int i=0; i < possibleEvents[viewerActor].size(); i++){
			testEvent = (EventRecord) possibleEvents[viewerActor].elementAt(i);
			// Get the optimal behavior profile.
			setEquations (testEvent);
			profile =
				equationSet.optimalProfile(MathModel.FIND_BEHAVIOR,testEvent);
			System.arraycopy(profile, 0, testEvent.abosFundamentals[1], 0, 3);
			System.arraycopy(profile, 0, testEvent.abosTransientsIn[1], 0, 3);
			// Replace the event in the list with the updated version.
		}

		// Find the resulting deflections for each event.
		double minimumTransient = 1000;
		int bestEvent = -1;
		for (int i=0; i < possibleEvents[viewerActor].size(); i++){
			testEvent = (EventRecord) possibleEvents[viewerActor].elementAt(i);
			setEquations (testEvent);
			equationSet.impressions(testEvent);
			// Identify the best event, with the smallest deflection.
			if (testEvent.deflection <= minimumTransient){
				minimumTransient = testEvent.deflection;
				bestEvent = i;
			}
		}

		// Return the event with the smallest deflection.
		testEvent = (EventRecord) possibleEvents[viewerActor].elementAt(bestEvent);
		return testEvent;
	} // End getOptimalObjectEvent.

	void setEquations (EventRecord theEvent){
		/*
		 * Select right equations for kind of event and viewer sex.
		 * This card does not use self-directed or ABOS actions.
		 */
		// 
		int theViewer = theEvent.vao[0];
		boolean male = Interact.person[theViewer].sex;
		if (male) {
			emotionEqs = Interact.maleEmotion;
			traitEqs = Interact.maleTrait;
			if (theEvent.vao[1] == theEvent.vao[2]) {
				// Male self-directed event.
				equationSet = Interact.maleSelf;
			} else {
				if (theEvent.abosIndexes[3][1] > -1) {
					equationSet = Interact.maleaboS;
				} else {
					equationSet = Interact.maleabo;
				}
			}
		} else {
			emotionEqs = Interact.femaleEmotion;
			traitEqs = Interact.femaleTrait;
			if (theEvent.vao[1] == theEvent.vao[2]) {
				// Female self-directed event.
				equationSet = Interact.femaleSelf;
			} else {
				if (theEvent.abosIndexes[3][1] > -1) {
					equationSet = Interact.femaleaboS;
				} else {
					equationSet = Interact.femaleabo;
				}
			}
		}
	} // End setEquations.


	void showEmotion(int interactant) {
		/*
		 * Use fundamentals and current transients to reset the
		 * given interactant's facial expression of emotion
		 * and displayed emotion EPA profile.
		 */
		double[] profile = { 0, 0, 0 };
		boolean male = Interact.person[interactant].sex;

		if (male) {
			emotionEqs = Interact.maleEmotion;
		} else {
			emotionEqs = Interact.femaleEmotion;
		}
		profile =
			emotionEqs.computeModifier(fundamentals[interactant][interactant],
				currentTransients[interactant][interactant]);
		face[interactant].setProfile(profile);
		emotionEPA[interactant].setText(profileToString(profile));
	} // End showEmotion.


	String profileToString (double[] profile){
		/*
		 * Convert a numerical EPA profile into a string.
		 */
		String boxText = "";
		for (int i = 0; i < 3; i++) {
			boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
		}
		return boxText;
	} // End profileToString.


	void computeTensionValues (){
		/* 
		 * Compute everyone's tensions from values stored in the
		 * fundamentals and current-transient arrays, and store results
		 * in the currentTensions array. 
		 * Each tension is the squared EPA deflection computed from a 
		 * viewer-actor's fundamental and transient EPA profiles.
		 */
		for (int who = 0; who < 4; who++){
			double diff;
			double tension = 0;
			if (who < numberOfActors){
				for (int epa = 0; epa < 3; epa++){
					diff = fundamentals[who][who][epa] - currentTransients[who][who][epa];
					tension = tension + diff * diff;
				}
				currentTensions[who] = tension;
			}
		}
	} // End computeTensionValues.

	
} // End InteractionsCard.
