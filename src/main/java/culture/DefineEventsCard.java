package culture;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

/**
 * Page for defining events, impact of experience on behavior meanings, and kind
 * of interaction.
 */
public class DefineEventsCard extends Panel {

	static final int NOT_ALLOWING_REPEATED_BEHAVIORS = 0;

	static final int USING_CONSTANT_BEHAVIOR_MEANINGS = 1;

	static final int USING_BEHAVIOR_TRANSIENTS = 2;

	static int behaviorMemory = 0;

	Panel viewerArea = new Panel();

	Panel buttonArea = new Panel();

	Panel definitionArea = new Panel();

	Panel actorArea = new Panel();

	Panel objectArea = new Panel();

	Panel behaviorArea = new Panel();

	Label viewerTitle = new Label();

	Label viewerFeatures = new Label();

	Label actorTitle = new Label();

	Label objectTitle = new Label();

	Label behaviorTypesTitle = new Label();

	Choice viewerMenu = new Choice();

	Choice actorMenu = new Choice();

	Choice objectMenu = new Choice();

	Choice behaviorMeaning = new Choice();

	TextArea eventSet = new TextArea();

	java.awt.List behaviorList = new java.awt.List();

	TextField behaviorEPA = new TextField();

	Button usePriorButton = new Button();

	Button insertButton = new Button();

	Checkbox corporalCheckbox = new Checkbox(
		Interact.behaviorConceptGateLines[13]);

	Checkbox overtCheckbox = new Checkbox(Interact.behaviorConceptGateLines[0]);

	Checkbox triadicCheckbox = new Checkbox(
		Interact.behaviorConceptGateLines[12]);

	BorderLayout borderLayout1 = new BorderLayout(15, 5);

	BorderLayout borderLayout2a = new BorderLayout(2, 2);

	BorderLayout borderLayout2b = new BorderLayout(2, 2);

	BorderLayout borderLayout2c = new BorderLayout(2, 2);

	GridBagLayout gridbag = new GridBagLayout();

	GridBagConstraints constrnts = new GridBagConstraints();

	int priorBehaviorSelection = -1;

	char comma = Interact.InteractText.getString("comma").charAt(0);

	char leftBracket = Interact.InteractText.getString("leftBracket").charAt(0);

	char rightBracket = Interact.InteractText.getString("rightBracket").charAt(
		0);

	String simultaneityMarker;

	int nInSimultaneitySet, firstInSimultaneitySet;

	public DefineEventsCard() { // Constructor method.
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(borderLayout1);

		this.add(viewerArea, BorderLayout.NORTH);
		this.add(definitionArea, BorderLayout.EAST);
		definitionArea.setLayout(gridbag);
		this.add(buttonArea, BorderLayout.SOUTH);

		// Top.
		viewerTitle.setText(Interact.InteractText.getString("experiences"));
		viewerTitle.setAlignment(Label.RIGHT);
		viewerArea.add(viewerTitle, null);
		viewerArea.add(viewerMenu, null);
		for (int i = 0; i < Interact.numberOfInteractants; i++) {
			viewerMenu.add(Interact.person[i].name);
		}
		viewerMenu.select(Interact.viewer);
		viewerFeatures.setText(Interact
			.identifyViewerSexAndSetting(Interact.viewer));
		viewerArea.add(viewerFeatures, null);

		// Left side.
		this.add(eventSet, BorderLayout.CENTER);
		if (!Interact.person[Interact.viewer].serialEvents.isEmpty()) {
			fillEventList(Interact.viewer);
		}

		// Right side.
		insertButton.setLabel(Interact.InteractText.getString("insert"));
		constrnts.gridx = 0;
		constrnts.gridy = 0;
		constrnts.gridheight = 3;
		constrnts.gridwidth = 1;
		constrnts.weightx = 1.0;
		constrnts.weighty = 1.0;
		constrnts.anchor = GridBagConstraints.CENTER;
		constrnts.fill = GridBagConstraints.NONE;
		constrnts.insets.left = 15;
		constrnts.insets.right = 15;
		gridbag.setConstraints(insertButton, constrnts);
		definitionArea.add(insertButton);
		definitionArea.setBackground(Color.cyan);

		constrnts.gridwidth = 1;
		constrnts.gridheight = 1;
		constrnts.gridx = 0;
		constrnts.gridy = 0;
		constrnts.weightx = 1.0;
		constrnts.fill = GridBagConstraints.NONE;
		constrnts.anchor = GridBagConstraints.EAST;
		constrnts.insets.left = 0;
		constrnts.insets.right = 0;
		actorTitle.setText(Interact.InteractText.getString("actor"));
		actorTitle.setAlignment(Label.RIGHT);
		gridbag.setConstraints(actorTitle, constrnts);
		definitionArea.add(actorTitle);
		constrnts.gridx = 1;
		constrnts.anchor = GridBagConstraints.WEST;
		gridbag.setConstraints(actorMenu, constrnts);
		definitionArea.add(actorMenu);
		for (int i = 0; i < Interact.numberOfInteractants; i++) {
			actorMenu.add(Interact.person[i].name);
		}
		actorMenu.select(0);

		constrnts.gridx = 0;
		constrnts.gridy = 1;
		constrnts.fill = GridBagConstraints.NONE;
		constrnts.anchor = GridBagConstraints.NORTHEAST;
		objectTitle.setText(Interact.InteractText.getString("object"));
		objectTitle.setAlignment(Label.RIGHT);
		gridbag.setConstraints(objectTitle, constrnts);
		definitionArea.add(objectTitle);
		constrnts.gridx = 1;
		constrnts.anchor = GridBagConstraints.NORTHWEST;
		gridbag.setConstraints(objectMenu, constrnts);
		definitionArea.add(objectMenu);
		for (int i = 0; i < Interact.numberOfInteractants; i++) {
			objectMenu.add(Interact.person[i].name);
		}
		objectMenu.add(Interact.InteractText.getString("selfText"));
		objectMenu.select(1);

		constrnts.gridx = 1;
		constrnts.gridy = 2;
		constrnts.weighty = 20.0;
		constrnts.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(behaviorArea, constrnts);
		definitionArea.add(behaviorArea);
		behaviorArea.setLayout(borderLayout2c);
		behaviorArea.add(behaviorList, BorderLayout.CENTER);
		behaviorEPA.setColumns(14);
		behaviorEPA.setText("");
		behaviorArea.add(behaviorEPA, BorderLayout.SOUTH);
		if (Interact.controls.complexityChoice.getSelectedItem() == Interact.displayLines[1]) {
			behaviorEPA.setVisible(true);
		} else {
			behaviorEPA.setVisible(false);
		}
		// Fill the behavior list.
		String wordText;
		for (int i = 0; i < Interact.behaviors.size(); i++) {
			wordText = ((Data) Interact.behaviors.elementAt(i)).word;
			behaviorList.add(wordText);
		}

		// Bottom.
		usePriorButton.setLabel(Interact.InteractText.getString("prior"));
		buttonArea.add(usePriorButton, null);
		for (int i = 0; i < Interact.behaviorMeaningLines.length; i++) {
			behaviorMeaning.add(Interact.behaviorMeaningLines[i]);
		}
		buttonArea.add(behaviorMeaning, null);
		buttonArea.add(behaviorTypesTitle, null);
		behaviorTypesTitle.setText(Interact.InteractText
			.getString("includeBehaviors"));
		buttonArea.add(corporalCheckbox, null);
		buttonArea.add(overtCheckbox, null);
		buttonArea.add(triadicCheckbox, null);
		overtCheckbox.setState(true);

		viewerMenu.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				viewerMenu_itemStateChanged(e);
			}
		});
		insertButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertButton_actionPerformed(e);
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
		behaviorEPA.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				behaviorEPA_actionPerformed(e);
			}
		});
		usePriorButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usePriorButton_actionPerformed(e);
			}
		});
		behaviorMeaning.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				behaviorMeaning_itemStateChanged(e);
			}
		});
	} // End constructor method.

	/** Show the EPA profile for a selected behavior. */
	void writeBehaviorEPA(boolean malesex) {
		double[] profile = { 0, 0, 0 };
		String boxText;
		int index;
		index = behaviorList.getSelectedIndex();
		if (index >= 0) {
			if (malesex) {
				profile = ((Data) Interact.behaviors.elementAt(index)).maleEPA;
			} else {
				profile =
					((Data) Interact.behaviors.elementAt(index)).femaleEPA;
			}
			boxText = "";
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
			behaviorEPA.setText(boxText);
		}
	}

	/** Fill the text area showing events with theViewer's events. */
	void fillEventList(int theViewer) {
		eventSet.setText("");
		for (int i = 0; i < Interact.person[theViewer].serialEvents.size(); i++) {
			eventSet
				.append(((EventRecord) Interact.person[theViewer].serialEvents
					.elementAt(i)).toString(EventRecord.SITUATION));
		}
	} // End fillEventList.

	/** Store events and parameters for a given viewer. */
	void storeViewer(int thisViewer) {
		int tokenType, tokenPosition, errorType, index, actor, action, object, lineNumber;
		int actorIdentity, actorModifier, objectIdentity, objectModifier, settingNoun;
		EventRecord theEvent;
		String theWord;
		simultaneityMarker = "";
		nInSimultaneitySet = 1;
		firstInSimultaneitySet = 0;

		settingNoun = Interact.person[thisViewer].setting.nounWordNumber;

		Interact.person[thisViewer].serialEvents.removeAllElements();
		actor =
			action =
				object =
					actorIdentity =
						actorModifier = 
							objectIdentity = 
								objectModifier = 0;

		// Parse the eventSet text area to store thisViewer's events.
		StringReader eventsText = new StringReader(eventSet.getText());
		StreamTokenizer eventsInput = new StreamTokenizer(eventsText);
		eventsInput.wordChars(' ', 255);
		eventsInput.whitespaceChars(comma, comma);
		eventsInput.whitespaceChars(leftBracket, leftBracket);
		eventsInput.whitespaceChars(rightBracket, rightBracket);
		eventsInput.eolIsSignificant(true);
		// Loop thru lines of text.
		tokenPosition = 0;
		errorType = -1;
		lineNumber = 0;
		boolean zeroRestart = false;
		boolean startNewAnalysis = false;
		boolean continuingNewAnalysis = false;
		boolean continuingZeroRestart = false;
		boolean processingSimultaneityLine = false;
		try {
			out: while (true) {
				tokenType = eventsInput.nextToken(); // Get word or
				// parameter.
				routing: switch (tokenType) {
				case StreamTokenizer.TT_EOF:
					// Done.
					if (tokenPosition != 7) { // EOF is not at end of an event
						// line.
						if (nInSimultaneitySet > 1) {
							finishSimultaneitySet(thisViewer, lineNumber - 1);
						} // lastSimultaneousEvent is last line.
						break out;
					}
					// Drop through to store the last event.
				case StreamTokenizer.TT_EOL:
					// Done reading one line.
					if (Interact.person[thisViewer].serialEvents.size() == SentimentChange.maxHistory) {
						errorType = 12; // Too many events.
						break out;
					}
					if (tokenPosition == 7) { 
						// Ignore incomplete or blank lines.
						theEvent =
							new EventRecord(thisViewer, actor, action, object);
						theEvent.abosIndexes[0][0] = actorModifier;
						theEvent.abosIndexes[0][1] = actorIdentity;
						theEvent.abosIndexes[1][1] = action;
						theEvent.abosIndexes[2][0] = objectModifier;
						theEvent.abosIndexes[2][1] = objectIdentity;
						theEvent.abosIndexes[3][1] = settingNoun;
						theEvent.restartAtZero = zeroRestart;
						theEvent.beginNewAnalysis = startNewAnalysis;
						theEvent.simultaneityCode = simultaneityMarker;
						Interact.person[thisViewer].serialEvents
							.addElement(theEvent);
						continuingZeroRestart = zeroRestart;
						continuingNewAnalysis = startNewAnalysis;
						zeroRestart = false;
						startNewAnalysis = false;
						processingSimultaneityLine = false;
					} else {
						errorType = 11; // Incomplete line.
						break out;
					}
					if (tokenType == StreamTokenizer.TT_EOF) {
						break out;
					}
					tokenPosition = 0;
					++lineNumber;
					break routing;
				case StreamTokenizer.TT_WORD: // Done reading one word.
					theWord = eventsInput.sval;
					switch (++tokenPosition) {
					case 1: // The actor. First word in a line.
						if (theWord.equals("#")) { // Word # to restart
							// averaging with
							// fundamentals set to zero.
							zeroRestart = true;
							startNewAnalysis = true;
							continuingNewAnalysis = true;
							continuingZeroRestart = true;
							tokenPosition = 0;
							break routing;
						} else if (theWord.equals("$")) { // Word $ separates
							// subanalyses.
							startNewAnalysis = true;
							continuingNewAnalysis = true;
							tokenPosition = 0;
							break routing;
						} else if (theWord.substring(0, 1).equals("&")) {
							// Word "&" with some second character delineates
							// simultaneous events.
							if (theWord.equalsIgnoreCase(simultaneityMarker)) {
								// Continuing a simultaneity set.
								zeroRestart = continuingZeroRestart;
								startNewAnalysis = continuingNewAnalysis;
								++nInSimultaneitySet;
								for (int alter = 0; alter < Interact.numberOfInteractants; alter++) {
									// Distribute this event's position in
									// simultaneity set (2nd, 3rd, etc.) to all
									// alters.
									SentimentChange.history[thisViewer][alter][lineNumber].simultaneitySetIndex =
										nInSimultaneitySet;
								}
							} else { // Starting a new simultaneity set.
								// lastSimultaneousEvent is last line.
								finishSimultaneitySet(thisViewer,
									lineNumber - 1); 
								simultaneityMarker = theWord;
							}
							processingSimultaneityLine = true;
							tokenPosition = 0;
							break routing;
						} else { // Deal with actor.
							if (!(processingSimultaneityLine
								| continuingNewAnalysis | continuingZeroRestart)) {
								// This is an unmarked line.
								continuingNewAnalysis = false;
								continuingZeroRestart = false;
								finishSimultaneitySet(thisViewer,
									lineNumber - 1); // lastSimultaneousEvent
								// is last line.
							}
							// Process the actor.
							for (int i = 0; i < Interact.numberOfInteractants; i++) {
								if (theWord.equals(Interact.person[i].name)) {
									actor = i; // The actor.
									break routing;
								}
							}
							errorType = 0; // Actor name unrecognized.
							break out;
						}
					case 2: // Actor modifier. Third word in a line.
						if (theWord.equals(Interact.InteractText
							.getString("empty"))) {
							actorModifier = -1;
							break routing;
						} else {
							index = Interact.modifiers.getIndex(theWord);
							if (index < 0) {
								// Actor's modifier is not in dictionary.
								errorType = 3; 
								break out;
							} else {
								actorModifier = index;
								break routing;
							}
						}
					case 3: // Actor identity. Second word in a line.
						if (theWord.equals(Interact.InteractText
							.getString("empty"))) {
							errorType = 1; // Actor has no identity.
							break out;
						} else {
							index = Interact.identities.getIndex(theWord);
							if (index < 0) {
								errorType = 2; // Actor's identity is not in
								// dictionary.
								break out;
							} else {
								actorIdentity = index;
								break routing;
							}
						}
					case 4: // Behavior. Fourth word in a line.
						if (theWord.equals(Interact.InteractText
							.getString("empty"))) {
							action = -1;
							break routing;
						} else {
							index = Interact.behaviors.getIndex(theWord);
							if (index < 0) {
								// Behavior is not in dictionary.
								errorType = 4; 
								break out;
							} else {
								action = index;
								break routing;
							}
						}
					case 5: // The object. Fifth word in a line.
						for (int i = 0; i < Interact.numberOfInteractants; i++) {
							if (theWord.equals(Interact.person[i].name)) {
								object = i;
								break routing;
							}
						}
						errorType = 5; // Object name unrecognized.
						break out;
					case 6: // Object modifier. Seventh word in a line.
						if (theWord.equals(Interact.InteractText
							.getString("empty"))) {
							objectModifier = -1;
							break routing;
						} else {
							index = Interact.modifiers.getIndex(theWord);
							if (index < 0) {
								// Object's modifier is not in dictionary.
								errorType = 8; 
								break out;
							} else {
								objectModifier = index;
								break routing;
							}
						}
					case 7: // Object identity. Sixth word in a line.
						if (theWord.equals(Interact.InteractText
							.getString("empty"))) {
							errorType = 6; // Object has no identity.
							break out;
						} else {
							index = Interact.identities.getIndex(theWord);
							if (index < 0) {
								// Object's identity is not in dictionary.
								errorType = 7; 
								break out;
							} else {
								objectIdentity = index;
								break routing;
							}
						}
					default:
						errorType = 9; // Too many words on a line.
						break out;
					} // End switch (++tokenPosition).
				default:
					errorType = 10; // Unrecognized token.
					break out;
				} // End switch (tokenType).
			} // End out: while (true).
			if (errorType > -1) {
				Cancel_OK_Dialog eventProblem =
					new Cancel_OK_Dialog((Frame) Interact.appletFrame,
						Interact.InteractText.getString("eventProblemTitle"),
						Interact.person[thisViewer].name + ", "
							+ Interact.InteractText.getString("space")
							+ (new Integer(lineNumber + 1)).toString()
							+ Interact.InteractText.getString("colon")
							+ Interact.InteractText.getString("space")
							+ Interact.storeErrorLines[errorType],
						Interact.InteractText.getString("ok"), null);
				eventProblem.setVisible(true);
			}
		} // End try.
		catch (IOException e) {
			System.out.println("Bad data"
				+ Interact.InteractText.getString("paragraphCommand"));
		}
	} // End storeViewer.

	void viewerMenu_itemStateChanged(ItemEvent e) {
		storeViewer(Interact.viewer);
		Interact.viewer = viewerMenu.getSelectedIndex();
		viewerFeatures.setText(Interact
			.identifyViewerSexAndSetting(Interact.viewer));
		fillEventList(Interact.viewer);
		if ((Interact.viewer == 0)
			|| (Interact.person[0].serialEvents.isEmpty())
			|| (!Interact.person[Interact.viewer].serialEvents.isEmpty())) {
			usePriorButton.setEnabled(false);
		} else {
			usePriorButton.setEnabled(true);
		}
		writeBehaviorEPA(Interact.person[Interact.viewer].sex);
	}

	/* Write a selected event into the text area showing events. */
	void insertButton_actionPerformed(ActionEvent e) {
		int aViewer, anActor, aBehavior, anObject;
		aViewer = viewerMenu.getSelectedIndex();
		anActor = actorMenu.getSelectedIndex();
		if (objectMenu.getSelectedItem() == Interact.InteractText
			.getString("selfText")) {
			anObject = anActor;
		} else {
			anObject = objectMenu.getSelectedIndex();
		}
		aBehavior = behaviorList.getSelectedIndex();
		EventRecord newEvent =
			new EventRecord(aViewer, anActor, aBehavior, anObject);
		newEvent.abosIndexes[0][0] =
			Interact.person[aViewer].viewOfPerson[anActor].modifierWordNumber;
		newEvent.abosIndexes[0][1] =
			Interact.person[aViewer].viewOfPerson[anActor].nounWordNumber;
		newEvent.abosIndexes[1][1] = aBehavior;
		newEvent.abosIndexes[2][0] =
			Interact.person[aViewer].viewOfPerson[anObject].modifierWordNumber;
		newEvent.abosIndexes[2][1] =
			Interact.person[aViewer].viewOfPerson[anObject].nounWordNumber;
		eventSet.append(newEvent.toString(EventRecord.SITUATION));
	}

	void behaviorList_itemStateChanged() {
		if ((priorBehaviorSelection == behaviorList.getSelectedIndex())
			& (priorBehaviorSelection >= 0)) {
			// Unselect if user reselects a selected behavior.
			behaviorList.deselect(priorBehaviorSelection);
		} else {
			writeBehaviorEPA(Interact.person[Interact.viewer].sex);
		}
		priorBehaviorSelection = behaviorList.getSelectedIndex();
	}

	/**
	 * Add a behavior corresponding to an EPA profile and named with the
	 * profile.
	 */
	void behaviorEPA_actionPerformed(ActionEvent e) {
		Data newEntry = DefineSituationCard.newDataEntry(behaviorEPA.getText());
		Interact.behaviors.addElement(newEntry);
		behaviorList.add(newEntry.word);
		behaviorList.select(behaviorList.getItemCount() - 1);
		behaviorList.makeVisible(behaviorList.getSelectedIndex());
	}

	/** Use Person 1 events. */
	void usePriorButton_actionPerformed(ActionEvent e) {
		fillEventList(0);
		usePriorButton.setEnabled(false);
	}

	void behaviorMeaning_itemStateChanged(ItemEvent e) {
		behaviorMemory = behaviorMeaning.getSelectedIndex();
	}

	/**
	 * Distribute number of simultaneous events, to each event in the
	 * simultaneity set.
	 */
	void finishSimultaneitySet(int thisViewer, int lastSimultaneousEvent) {
		for (int alter = 0; alter < Interact.numberOfInteractants; alter++) {
			for (int j = firstInSimultaneitySet; j <= lastSimultaneousEvent; j++) {
				SentimentChange.history[thisViewer][alter][j].nSimultaneousEvents =
					nInSimultaneitySet;
			}
		}
		// Reset defaults for simultaneity variables.
		simultaneityMarker = "";
		nInSimultaneitySet = 1;
		firstInSimultaneitySet = lastSimultaneousEvent + 1;
	} // End finishSimultaneitySet.

} // End class DefineEventsCard.

