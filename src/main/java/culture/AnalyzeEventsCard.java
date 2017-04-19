package culture;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

/** Page for analyzing events and observing outcomes. */

public class AnalyzeEventsCard extends Panel {

	static final boolean ACTOR = true;

	static final boolean OBJECT = false;

	int currentEvent;

	Vector<String> usedBehaviors;

	Panel experienceArea = new Panel();

	Panel eventArea = new Panel();

	Panel outputs = new Panel();

	Panel middlePanel = new Panel();

	Panel faceArea = new Panel();

	Panel graphArea = new Panel();

	Panel actorBehaviorTitleArea = new Panel();

	Label experiencesOf = new Label();

	Choice viewerMenu = new Choice();

	Choice presentFutureMenu = new Choice();

	Label viewerFeatures = new Label();

	Button conceptGates = new Button();

	java.awt.List eventList = new java.awt.List();

	Label actorEmotionTitle = new Label();

	Label actorBehaviorTitle = new Label();

	Label actorAttributeTitle = new Label();

	Label actorLabelTitle = new Label();

	Label objectEmotionTitle = new Label();

	Label objectBehaviorTitle = new Label();

	Label objectAttributeTitle = new Label();

	Label objectLabelTitle = new Label();

	java.awt.List actorEmotions = new java.awt.List();

	java.awt.List actorBehaviors = new java.awt.List();

	java.awt.List actorAttributes = new java.awt.List();

	java.awt.List actorLabels = new java.awt.List();

	java.awt.List objectEmotions = new java.awt.List();

	java.awt.List objectBehaviors = new java.awt.List();

	java.awt.List objectAttributes = new java.awt.List();

	java.awt.List objectLabels = new java.awt.List();

	TextField actorEmotionEPA = new TextField();

	TextField actorBehaviorEPA = new TextField();

	TextField actorAttributeEPA = new TextField();

	TextField actorLabelEPA = new TextField();

	TextField objectEmotionEPA = new TextField();

	TextField objectBehaviorEPA = new TextField();

	TextField objectAttributeEPA = new TextField();

	TextField objectLabelEPA = new TextField();

	BorderLayout borderLayout1 = new BorderLayout();

	BorderLayout borderLayout2 = new BorderLayout();

	BorderLayout borderLayout3 = new BorderLayout();

	GridBagLayout gridbag = new GridBagLayout();

	GridBagLayout faceLayout = new GridBagLayout();

	GridBagConstraints constraints = new GridBagConstraints();

	GridBagConstraints faceLimits = new GridBagConstraints();

	Face actorFace = new Face();

	Face objectFace = new Face();

	DeflectionGraph graph = new DeflectionGraph();

	int[] colWidths = { 212, 212, 212 };

	String newLine = Interact.InteractText.getString("paragraphCommand");

	String comma = Interact.InteractText.getString("clauseSeparation");

	String continuing = Interact.InteractText.getString("continuing");

	String colonSpace = Interact.InteractText.getString("colon")
		+ Interact.InteractText.getString("space");

	String dotSpace = Interact.InteractText.getString("sentenceSeparation");

	EventRecord currentEventForAlter;

	public AnalyzeEventsCard() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(borderLayout1);

		this.add(experienceArea, BorderLayout.NORTH);
		experienceArea.setLayout(borderLayout2);
		experienceArea.add(eventArea, BorderLayout.CENTER);
		experiencesOf.setAlignment(Label.RIGHT);
		experiencesOf.setText(Interact.InteractText.getString("experiences"));
		conceptGates.setLabel(Interact.InteractText
			.getString("changeConceptGatesButton"));
		eventArea.add(experiencesOf, null);
		eventArea.add(viewerMenu, null);
		for (int i = 0; i < Interact.numberOfInteractants; i++) {
			viewerMenu.add(Interact.person[i].name);
		}
		eventArea.add(viewerFeatures, null);
		eventArea.add(conceptGates, null);
		experienceArea.add(eventList, BorderLayout.SOUTH);
		for (int i = 0; i < Interact.person[Interact.viewer].serialEvents
			.size(); i++) {
			EventRecord anEvent =
				(EventRecord) Interact.person[Interact.viewer].serialEvents
					.elementAt(i);
			String line = anEvent.toString(EventRecord.SITUATION);
			// Remove Return from end of line.
			line = line.substring(0, line.length() - 1);
			eventList.add(line);
		}
		viewerMenu.select(Interact.viewer);
		viewerFeatures.setText(Interact
			.identifyViewerSexAndSetting(Interact.viewer));

		outputs.setLayout(gridbag);
		this.add(outputs, BorderLayout.CENTER);
		gridbag.columnWidths = colWidths;

		actorEmotionTitle.setAlignment(Label.CENTER);
		actorBehaviorTitle.setAlignment(Label.CENTER);
		actorAttributeTitle.setAlignment(Label.CENTER);
		actorLabelTitle.setAlignment(Label.CENTER);
		objectEmotionTitle.setAlignment(Label.CENTER);
		objectBehaviorTitle.setAlignment(Label.CENTER);
		objectAttributeTitle.setAlignment(Label.CENTER);
		objectLabelTitle.setAlignment(Label.CENTER);

		actorEmotionTitle.setText(Interact.InteractText
			.getString("actorEmotions"));
		actorBehaviorTitle.setText(Interact.InteractText
			.getString("actorBehaviors"));
		actorAttributeTitle.setText(Interact.InteractText
			.getString("actorAttributes"));
		actorLabelTitle.setText(Interact.InteractText.getString("actorLabels"));
		objectEmotionTitle.setText(Interact.InteractText
			.getString("objectEmotions"));
		objectBehaviorTitle.setText(Interact.InteractText
			.getString("objectBehaviors"));
		objectAttributeTitle.setText(Interact.InteractText
			.getString("objectAttributes"));
		objectLabelTitle.setText(Interact.InteractText
			.getString("objectLabels"));
		// Actor.
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		gridbag.setConstraints(actorEmotionTitle, constraints);
		outputs.add(actorEmotionTitle);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 1;
		constraints.weighty = 4.0;
		constraints.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(actorEmotions, constraints);
		outputs.add(actorEmotions);
		constraints.gridy = 2;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(actorEmotionEPA, constraints);
		outputs.add(actorEmotionEPA);
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.gridy = 3;
		gridbag.setConstraints(actorBehaviorTitleArea, constraints);
		outputs.add(actorBehaviorTitleArea);
		actorBehaviorTitleArea.add(actorBehaviorTitle);
		actorBehaviorTitleArea.add(presentFutureMenu);
		presentFutureMenu.add(Interact.InteractText.getString("present"));
		presentFutureMenu.add(Interact.InteractText.getString("future"));
		presentFutureMenu.select(1);
		presentFutureMenu.setVisible(false);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 4;
		constraints.weighty = 4.0;
		constraints.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(actorBehaviors, constraints);
		outputs.add(actorBehaviors);
		constraints.gridy = 5;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(actorBehaviorEPA, constraints);
		outputs.add(actorBehaviorEPA);
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.gridy = 6;
		gridbag.setConstraints(actorAttributeTitle, constraints);
		outputs.add(actorAttributeTitle);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 7;
		constraints.weighty = 4.0;
		actorAttributes = new java.awt.List();
		constraints.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(actorAttributes, constraints);
		outputs.add(actorAttributes);
		constraints.gridy = 8;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(actorAttributeEPA, constraints);
		outputs.add(actorAttributeEPA);
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.gridy = 9;
		gridbag.setConstraints(actorLabelTitle, constraints);
		outputs.add(actorLabelTitle);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 10;
		constraints.weighty = 4.0;
		constraints.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(actorLabels, constraints);
		outputs.add(actorLabels);
		constraints.gridy = 11;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(actorLabelEPA, constraints);
		outputs.add(actorLabelEPA);
		// Middle panel for graphics.
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridheight = 12;
		constraints.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(middlePanel, constraints);
		outputs.add(middlePanel);
		middlePanel.setLayout(new BorderLayout());
		middlePanel.add(faceArea, BorderLayout.NORTH);
		middlePanel.add(graphArea, BorderLayout.CENTER);
		// Faces.
		faceArea.setLayout(faceLayout);
		faceLimits.gridx = 0;
		faceLimits.gridy = 0;
		faceLimits.gridwidth = 1;
		faceLimits.gridheight = 1;
		faceLimits.weightx = 1.0;
		faceLimits.weighty = 1.0;
		faceLimits.insets.top = 0;
		faceLimits.insets.bottom = 25;
		faceLimits.fill = GridBagConstraints.NONE;
		faceLayout.setConstraints(actorFace, faceLimits);
		faceArea.add(actorFace);
		faceLimits.gridx = 1;
		faceLimits.insets.top = 25;
		faceLimits.insets.bottom = 0;
		faceLayout.setConstraints(objectFace, faceLimits);
		faceArea.add(objectFace);
		actorFace.setPictureChoice(0);
		actorFace.setProfile(Face.BLANKING_PROFILE);
		objectFace.setPictureChoice(3);
		objectFace.setProfile(Face.BLANKING_PROFILE);
		// Graph of deflections.
		graphArea.setLayout(borderLayout3);
		graphArea.add(graph, BorderLayout.CENTER);
		// Object.
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(objectEmotionTitle, constraints);
		outputs.add(objectEmotionTitle);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 1;
		constraints.weighty = 4.0;
		constraints.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(objectEmotions, constraints);
		outputs.add(objectEmotions);
		constraints.gridy = 2;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(objectEmotionEPA, constraints);
		outputs.add(objectEmotionEPA);
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.gridy = 3;
		gridbag.setConstraints(objectBehaviorTitle, constraints);
		outputs.add(objectBehaviorTitle);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 4;
		constraints.weighty = 4.0;
		constraints.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(objectBehaviors, constraints);
		outputs.add(objectBehaviors);
		constraints.gridy = 5;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(objectBehaviorEPA, constraints);
		outputs.add(objectBehaviorEPA);
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.gridy = 6;
		gridbag.setConstraints(objectAttributeTitle, constraints);
		outputs.add(objectAttributeTitle);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 7;
		constraints.weighty = 4.0;
		constraints.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(objectAttributes, constraints);
		outputs.add(objectAttributes);
		constraints.gridy = 8;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(objectAttributeEPA, constraints);
		outputs.add(objectAttributeEPA);
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.gridy = 9;
		gridbag.setConstraints(objectLabelTitle, constraints);
		outputs.add(objectLabelTitle);
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridy = 10;
		constraints.weighty = 4.0;
		constraints.fill = GridBagConstraints.BOTH;
		gridbag.setConstraints(objectLabels, constraints);
		outputs.add(objectLabels);
		constraints.gridy = 11;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(objectLabelEPA, constraints);
		outputs.add(objectLabelEPA);

		currentEvent = -1;
		usedBehaviors = new Vector<String>();

		viewerMenu.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				viewerMenu_itemStateChanged(e);
			}
		});
		presentFutureMenu.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				presentFutureMenu_itemStateChanged(e);
			}
		});
		conceptGates.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conceptGates_actionPerformed(e);
			}
		});
		eventList.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				eventList_itemStateChanged();
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
		actorEmotions.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				actorEmotions_itemStateChanged();
			}
		});
		objectEmotions.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				objectEmotions_itemStateChanged();
			}
		});
		actorBehaviors.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				actorBehaviors_itemStateChanged();
			}
		});
		objectBehaviors.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				objectBehaviors_itemStateChanged();
			}
		});
		actorAttributes.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				actorAttributes_itemStateChanged();
			}
		});
		objectAttributes.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				objectAttributes_itemStateChanged();
			}
		});
		actorLabels.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				actorLabels_itemStateChanged();
			}
		});
		objectLabels.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				objectLabels_itemStateChanged();
			}
		});
	} // End jbInit.

	void viewerMenu_itemStateChanged(ItemEvent e) {
		// Change Interact.viewer.
		Interact.viewer = viewerMenu.getSelectedIndex();
		viewerFeatures.setText(Interact
			.identifyViewerSexAndSetting(Interact.viewer));
		clearAllResults(); // Clear results boxes.
		// Refill events box with this viewer's perceptions.
		eventList.removeAll();
		for (int i = 0; i < Interact.person[Interact.viewer].serialEvents
			.size(); i++) {
			EventRecord anEvent =
				(EventRecord) Interact.person[Interact.viewer].serialEvents
					.elementAt(i);
			String line = anEvent.toString(EventRecord.SITUATION);
			// Remove Return from end of line.
			line = line.substring(0, line.length() - 1);
			eventList.add(line);
		}
	}

	void presentFutureMenu_itemStateChanged(ItemEvent e) {
		// Reanalyze the event.
		if (eventList.getSelectedIndex() > -1) {
			eventList_itemStateChanged();
		}
	}

	/** Show conceptGates dialog. */
	void conceptGates_actionPerformed(ActionEvent e) {
		if (Interact.analyzeEvents.eventList.getSelectedIndex() < 0) {
			// Can show conceptGates only for an implemented event.
			Cancel_OK_Dialog noEvent =
				new Cancel_OK_Dialog((Frame) Interact.appletFrame,
					Interact.InteractText.getString("conceptGateProblemTitle"),
					Interact.InteractText.getString("noEvent"),
					Interact.InteractText.getString("ok"), null);
			noEvent.setVisible(true);
		} else {
			ConceptGatesPopup choose =
				new ConceptGatesPopup((Frame) Interact.appletFrame);
			choose.setVisible(true);
		}
	} // End conceptGates_actionPerformed.

	/** Process the clicked event. */
	void eventList_itemStateChanged() {
		double[] profile = { 0, 0, 0 };
		int modifierIndex, nounIndex;
		int selectedEvent = eventList.getSelectedIndex();
		do {
			EventRecord evntRcrd =
				(EventRecord) Interact.person[Interact.viewer].serialEvents
					.elementAt(selectedEvent);
			if (!Interact.person[Interact.viewer].serialEvents
				.assembleInputs(selectedEvent)) { // Assemble the inputs.
				currentEvent = -1; // Quit if unsuccessful.
			} else { // Successfully assembled inputs.
				currentEvent = selectedEvent;
				if ((currentEvent == 0) || evntRcrd.beginNewAnalysis) {
					// Starting new analysis.
					usedBehaviors.removeAllElements();
					if (evntRcrd.restartAtZero) {
						// Set fundamentals for each interactant to zero.
						for (int position = 0; position < 4; position =
							position + 2) {
							for (int epa = 0; epa < Interact.N_DIMENSIONS; epa++) {
								evntRcrd.abosFundamentals[position][epa] = 0;
							}
						}
						if (SentimentChange.changingSentiments) {
							/*
							 * Seek optimal current behavior (not next behavior)
							 * for predicting observed behaviors.
							 * findingNextActorBehavior = false in Equations.
							 * Get index of first mutator identity.
							 */
							String MutatorName =
								"Mutator_" + Integer.toString(1) + "_"
									+ Integer.toString(1);
							int index =
								Interact.identities.getIndex(MutatorName);
							// Set fundamentals for each mutator identity to
							// zero.
							for (int i = index; i < index
								+ Interact.numberOfInteractants
								* Interact.numberOfInteractants; i++) {
								Data affectedLine =
									(Data) Interact.identities.elementAt(i);
								for (int epa = 0; epa < Interact.N_DIMENSIONS; epa++) {
									affectedLine.maleEPA[epa] = 0;
									affectedLine.femaleEPA[epa] =
										affectedLine.maleEPA[epa];
								}
							}
						} // end if/changingSentiments
					} // end if/restartAtZero
					for (int alter = 0; alter < Interact.numberOfInteractants; alter++) {
						// Initialize remaining history (i.e., future).
						if (alter == evntRcrd.vao[1]) { // Event actor.
							fillHistory(Interact.viewer, alter,
								evntRcrd.abosFundamentals[0], currentEvent);
						} else if (alter == evntRcrd.vao[2]) { // Event object.
							fillHistory(Interact.viewer, alter,
								evntRcrd.abosFundamentals[2], currentEvent);
						} else {
							// Interactant not in this event.
							DataList happenings =
								Interact.person[Interact.viewer].serialEvents;
							modifierIndex = -1;
							nounIndex = -1;
							boolean viewerMale =
								Interact.person[Interact.viewer].sex;
							preview: for (int t = currentEvent + 1; t < happenings
								.size(); t++) {
								// Check future events for alter's fundamental.
								EventRecord definedEvent =
									(EventRecord) happenings.elementAt(t);
								if (definedEvent.vao[1] == alter) {
									modifierIndex =
										definedEvent.abosIndexes[0][0];
									nounIndex = definedEvent.abosIndexes[0][1];
								} else if (definedEvent.vao[2] == alter) {
									modifierIndex =
										definedEvent.abosIndexes[2][0];
									nounIndex = definedEvent.abosIndexes[2][1];
								}
								if (nounIndex > -1) { // Found alter.
									if (modifierIndex > -1) {
										// For a modified identity, amalgamate
										// modifier + noun.
										profile =
											MathModel.amalgamate(viewerMale,
												modifierIndex, nounIndex);
									} else {
										// Retrieve the dictionary line for the
										// specified noun (or verb).
										Data datum =
											((Data) Interact.identities
												.elementAt(nounIndex));
										if (viewerMale) {
											System.arraycopy(datum.maleEPA, 0,
												profile, 0, 3);
										} else {
											System.arraycopy(datum.femaleEPA,
												0, profile, 0, 3);
										}
									}
									fillHistory(Interact.viewer, alter,
										profile, currentEvent);
									break preview;
								} // end if/found alter.
							} // end preview: for/t.
						} // end else interactant not in starting event.
					} // end for/alter
				} // end if/ Starting new analysis
				int behaviorDicIndex = evntRcrd.abosIndexes[1][1];
				if (behaviorDicIndex > -1) {
					usedBehaviors.addElement(((Data) Interact.behaviors
						.elementAt(behaviorDicIndex)).word);
					// Remember this behavior as used.
				} else { // Seeking optimal behavior.
					presentFutureMenu.select(0);
					// Seek optimal current behavior, not next behavior.
					if (Interact.controls.complexityChoice.getSelectedIndex() == 0) {
						Interact.controls
							.setComplexityLevel(ControlsMenuBar.ADVANCED);
						// So user sees current on presentFutureMenu.
					}
				}
				// Make the event happen.
				implementEvent();
				// Show the current event in the event list.
				if (eventList.getItemCount() > (selectedEvent + 1)) {
					eventList.makeVisible(selectedEvent);
				}
			} // End successful input assembly.
			if (Interact.batch) {
				eventList.select(++selectedEvent);
			}
		} while (Interact.batch & selectedEvent < eventList.getItemCount());
	} // End eventList_itemStateChanged.

	void actorEmotionEPA_actionPerformed(ActionEvent e) {
		// Display the entered emotion EPA profile as a facial expression.
		actorFace.setPictureChoice(Interact.person[0].visage);
		actorFace.setProfile(Interact.readProfile(actorEmotionEPA.getText()));
		actorFace.repaint();
	}

	/** Display the entered emotion EPA profile as a facial expression. */
	void objectEmotionEPA_actionPerformed(ActionEvent e) {
		objectFace.setPictureChoice(Interact.person[1].visage);
		objectFace.setProfile(Interact.readProfile(objectEmotionEPA.getText()));
		objectFace.repaint();
	}

	/** Show actor's face for selected actor emotion. */
	void actorEmotions_itemStateChanged() {
		String theEntry = actorEmotions.getSelectedItem();
		int wordBeginsAt = theEntry.indexOf(comma);
		if (wordBeginsAt < 0) {
			wordBeginsAt = 0;
		} else { // Showing distances before words.
			wordBeginsAt = wordBeginsAt + comma.length();
		}
		theEntry = theEntry.substring(wordBeginsAt);
		int wordNum = Interact.modifiers.getIndex(theEntry);
		double[] newProfile;
		if (Interact.person[Interact.viewer].sex) {
			newProfile = ((Data) Interact.modifiers.elementAt(wordNum)).maleEPA;
		} else {
			newProfile =
				((Data) Interact.modifiers.elementAt(wordNum)).femaleEPA;
		}
		String boxText = "";
		for (int i = 0; i < 3; i++) {
			boxText = boxText + Interact.formatLocaleDecimal(newProfile[i]);
		}
		actorEmotionEPA.setText(boxText);
		actorFace.setProfile(newProfile);
		EventRecord sourceOfEmotion =
			(EventRecord) Interact.person[Interact.viewer].serialEvents
				.elementAt(currentEvent);
		actorFace
			.setPictureChoice(Interact.person[sourceOfEmotion.vao[1]].visage);
		actorFace.repaint();
	} // End actorEmotions_itemStateChanged.

	/** Show object person's face for selected object emotion. */
	void objectEmotions_itemStateChanged() {
		String theEntry = objectEmotions.getSelectedItem();
		int wordBeginsAt = theEntry.indexOf(comma);
		if (wordBeginsAt < 0) {
			wordBeginsAt = 0;
		} else { // Showing distances before words.
			wordBeginsAt = wordBeginsAt + comma.length();
		}
		theEntry = theEntry.substring(wordBeginsAt);
		int wordNum = Interact.modifiers.getIndex(theEntry);
		double[] newProfile;
		if (Interact.person[Interact.viewer].sex) {
			newProfile = ((Data) Interact.modifiers.elementAt(wordNum)).maleEPA;
		} else {
			newProfile =
				((Data) Interact.modifiers.elementAt(wordNum)).femaleEPA;
		}
		String boxText = "";
		for (int i = 0; i < 3; i++) {
			boxText = boxText + Interact.formatLocaleDecimal(newProfile[i]);
		}
		objectEmotionEPA.setText(boxText);
		objectFace.setProfile(newProfile);
		EventRecord sourceOfEmotion =
			(EventRecord) Interact.person[Interact.viewer].serialEvents
				.elementAt(currentEvent);
		objectFace
			.setPictureChoice(Interact.person[sourceOfEmotion.vao[2]].visage);
		objectFace.repaint();
	} // End objectEmotions_itemStateChanged.

	/** Empty the events scroll-box and re-fill it. */
	void finishRevisingEvent() {
		eventList.removeAll();
		for (int i = 0; i < Interact.person[Interact.viewer].serialEvents
			.size(); i++) {
			EventRecord anEvent =
				(EventRecord) Interact.person[Interact.viewer].serialEvents
					.elementAt(i);
			String line = anEvent.toString(EventRecord.SITUATION);
			line = line.substring(0, line.length() - 1); // Remove Return from
															// end of line.
			eventList.add(line);
		}
		// Scroll to the new event.
		eventList.makeVisible(Math.min((currentEvent + 1),
			(eventList.getItemCount() - 1)));
		Interact.controls.changedAnEvent = true;
	}

	void actorBehaviors_itemStateChanged() {
		if (Interact.analyzeEvents.presentFutureMenu.getSelectedIndex() == 0) {
			// Implement the selected ideal actor behavior as the current event.
			String theEntry = actorBehaviors.getSelectedItem();
			int wordBeginsAt = theEntry.indexOf(comma);
			if (wordBeginsAt < 0) {
				wordBeginsAt = 0;
			} else { // Showing distances before words.
				wordBeginsAt = wordBeginsAt + comma.length();
			}
			theEntry = theEntry.substring(wordBeginsAt);
			int wordNum = Interact.behaviors.getIndex(theEntry);
			DataList eventSet = Interact.person[Interact.viewer].serialEvents;
			EventRecord anEvent =
				(EventRecord) eventSet.elementAt(currentEvent);
			anEvent.abosIndexes[1][1] = wordNum; // [behavior][noun] changed in
													// eventSet.
			finishRevisingEvent();
			eventList.select(currentEvent);
			eventList_itemStateChanged();
		} else {
			// Iimplement the selected ideal actor behavior as the next event.
			String theEntry = actorBehaviors.getSelectedItem();
			int wordBeginsAt = theEntry.indexOf(comma);
			if (wordBeginsAt < 0) {
				wordBeginsAt = 0;
			} else { // Showing distances before words.
				wordBeginsAt = wordBeginsAt + comma.length();
			}
			theEntry = theEntry.substring(wordBeginsAt);
			int theBehavior = Interact.behaviors.getIndex(theEntry);
			// theBehavior is the index of the behavior.
			DataList eventSet = Interact.person[Interact.viewer].serialEvents;
			EventRecord anEvent =
				// Ego's view of the current event.
				(EventRecord) eventSet.elementAt(currentEvent); 
			// Implement the response behavior with current actor and object.
			int newActor = anEvent.vao[1];
			int newObject = anEvent.vao[2];
			EventRecord aNewEvent =
				new EventRecord(anEvent.vao[0], newActor, theBehavior,
					newObject);
			// From current event transfer data for actor, object and setting.
			System.arraycopy(anEvent.abosIndexes[0], 0,
				aNewEvent.abosIndexes[0], 0, 2);
			System.arraycopy(anEvent.abosIndexes[2], 0,
				aNewEvent.abosIndexes[2], 0, 2);
			System.arraycopy(anEvent.abosIndexes[3], 0,
				aNewEvent.abosIndexes[3], 0, 2);
			// Insert the new event in the event set.
			if ((currentEvent + 1) >= eventSet.size()) {
				eventSet.addElement(aNewEvent);
			} else {
				eventSet.insertElementAt(aNewEvent, currentEvent + 1);
			}
			finishRevisingEvent();
			eventList.select(currentEvent + 1);
			eventList_itemStateChanged(); // Implement the selected event.
		}
	} // End actorBehaviors_itemStateChanged.

	void objectBehaviors_itemStateChanged() {
		int newViewer, newActor, theBehavior, newObject;
		// Iimplement the selected ideal object behavior as the next event.
		String theEntry = objectBehaviors.getSelectedItem();
		int wordBeginsAt = theEntry.indexOf(comma);
		if (wordBeginsAt < 0) {
			wordBeginsAt = 0;
		} else { // Showing distances before words.
			wordBeginsAt = wordBeginsAt + comma.length();
		}
		theEntry = theEntry.substring(wordBeginsAt);
		theBehavior = Interact.behaviors.getIndex(theEntry);
		// theBehavior is the index of the behavior.
		DataList currentViewerEventSet =
			Interact.person[Interact.viewer].serialEvents;
		EventRecord theCurrentEvent =
			(EventRecord) currentViewerEventSet.elementAt(currentEvent);
		// Stay with viewer of last event.
		newViewer = theCurrentEvent.vao[0]; 
		Interact.viewer = newViewer;
		viewerMenu.select(newViewer);
		// Switch current actor and object.
		newActor = theCurrentEvent.vao[2];
		newObject = theCurrentEvent.vao[1];
		EventRecord aNewEvent =
			new EventRecord(newViewer, newActor, theBehavior, newObject);
		aNewEvent.abosIndexes[0][0] =
			Interact.person[newViewer].viewOfPerson[newActor].modifierWordNumber;
		aNewEvent.abosIndexes[0][1] =
			Interact.person[newViewer].viewOfPerson[newActor].nounWordNumber;
		aNewEvent.abosIndexes[1][1] = theBehavior;
		aNewEvent.abosIndexes[2][0] =
			Interact.person[newViewer].viewOfPerson[newObject].modifierWordNumber;
		aNewEvent.abosIndexes[2][1] =
			Interact.person[newViewer].viewOfPerson[newObject].nounWordNumber;
		aNewEvent.abosIndexes[3][1] =
			Interact.person[newViewer].setting.nounWordNumber;
		DataList newViewerEventSet = Interact.person[newViewer].serialEvents;
		// Insert the new event in the event set.
		if ((currentEvent + 1) >= newViewerEventSet.size()) {
			newViewerEventSet.addElement(aNewEvent);
		} else {
			newViewerEventSet.insertElementAt(aNewEvent, currentEvent + 1);
		}
		finishRevisingEvent();
		eventList.select(currentEvent + 1);
		eventList_itemStateChanged(); // Implement the selected event.
	} // End objectBehaviors_itemStateChanged.

	void actorAttributes_itemStateChanged() {
		// Incorporate the selected actor attribution.
		String theEntry = actorAttributes.getSelectedItem();
		int wordBeginsAt = theEntry.indexOf(comma);
		if (wordBeginsAt < 0) {
			wordBeginsAt = 0;
		} else { // Showing distances before words.
			wordBeginsAt = wordBeginsAt + comma.length();
		}
		theEntry = theEntry.substring(wordBeginsAt);
		int wordNum = Interact.modifiers.getIndex(theEntry);
		DataList eventSet = Interact.person[Interact.viewer].serialEvents;
		EventRecord anEvent = (EventRecord) eventSet.elementAt(currentEvent);
		anEvent.abosIndexes[0][0] = wordNum; // [actor][modifier] changed in
												// eventSet.
		// Change for future events generated by Interact.
		Interact.person[anEvent.vao[0]].viewOfPerson[anEvent.vao[1]].modifierWordNumber =
			wordNum;
		// Show the new event in the event listing.
		finishRevisingEvent();
	} // End actorAttributes_itemStateChanged.

	void objectAttributes_itemStateChanged() {
		// Incorporate the selected object attribution.
		String theEntry = objectAttributes.getSelectedItem();
		int wordBeginsAt = theEntry.indexOf(comma);
		if (wordBeginsAt < 0) {
			wordBeginsAt = 0;
		} else { // Showing distances before words.
			wordBeginsAt = wordBeginsAt + comma.length();
		}
		theEntry = theEntry.substring(wordBeginsAt);
		int wordNum = Interact.modifiers.getIndex(theEntry);
		DataList eventSet = Interact.person[Interact.viewer].serialEvents;
		EventRecord anEvent = (EventRecord) eventSet.elementAt(currentEvent);
		anEvent.abosIndexes[2][0] = wordNum; // [object][modifier] changed in
												// eventSet.
		// Change for future events generated by Interact.
		Interact.person[anEvent.vao[0]].viewOfPerson[anEvent.vao[2]].modifierWordNumber =
			wordNum;
		// Show the new event in the event listing.
		finishRevisingEvent();
	} // End objectAttributes_itemStateChanged.

	void actorLabels_itemStateChanged() {
		// Incorporate the selected actor label.
		String theEntry = actorLabels.getSelectedItem();
		int wordBeginsAt = theEntry.indexOf(comma);
		if (wordBeginsAt < 0) {
			wordBeginsAt = 0;
		} else { // Showing distances before words.
			wordBeginsAt = wordBeginsAt + comma.length();
		}
		theEntry = theEntry.substring(wordBeginsAt);
		int wordNum = Interact.identities.getIndex(theEntry);
		DataList eventSet = Interact.person[Interact.viewer].serialEvents;
		EventRecord anEvent = (EventRecord) eventSet.elementAt(currentEvent);
		anEvent.abosIndexes[0][1] = wordNum; // [actor][identity] changed in
												// eventSet.
		// Change for future events generated by Interact.
		Interact.person[anEvent.vao[0]].viewOfPerson[anEvent.vao[1]].modifierWordNumber =
			-1;
		Interact.person[anEvent.vao[0]].viewOfPerson[anEvent.vao[1]].nounWordNumber =
			wordNum;
		// Show the new event in the event listing.
		finishRevisingEvent();
	} // End actorLabels_itemStateChanged.

	void objectLabels_itemStateChanged() {
		// Incorporate the selected object label.
		String theEntry = objectLabels.getSelectedItem();
		int wordBeginsAt = theEntry.indexOf(comma);
		if (wordBeginsAt < 0) {
			wordBeginsAt = 0;
		} else { // Showing distances before words.
			wordBeginsAt = wordBeginsAt + comma.length();
		}
		theEntry = theEntry.substring(wordBeginsAt);
		int wordNum = Interact.identities.getIndex(theEntry);
		DataList eventSet = Interact.person[Interact.viewer].serialEvents;
		EventRecord anEvent = (EventRecord) eventSet.elementAt(currentEvent);
		// [object][identity] changed in eventSet.
		anEvent.abosIndexes[2][1] = wordNum; 
		// Change for future events generated by Interact.
		Interact.person[anEvent.vao[0]].viewOfPerson[anEvent.vao[2]].modifierWordNumber =
			-1;
		Interact.person[anEvent.vao[0]].viewOfPerson[anEvent.vao[2]].nounWordNumber =
			wordNum;
		// Show the new event in the event listing.
		finishRevisingEvent();
	} // End objectLabels_itemStateChanged.

	/** Setup and perform computations for a selected event. */
	void implementEvent() {
		boolean male, showingNumbers, interpersonalActionRatherThanSelfDirected;
		EventRecord theEvent;
		MathModel equationSet, emotionEqs, traitEqs;
		double[] profile = { 0, 0, 0 };
		double[] labelingProfile, identityProfile;
		String boxText, lineText;
		DataList matchesToProfile;
		Data datum;
		Retrieval resultLine = new Retrieval(0, "", profile);
		Interact.viewer = viewerMenu.getSelectedIndex();
		clearAllResults();

		theEvent =
			(EventRecord) Interact.person[Interact.viewer].serialEvents
				.elementAt(currentEvent);
		if (Interact.keepingRecord) {
			Interact.reportExpert =
				Interact.reportExpert + theEvent.toString(EventRecord.VIEWER);
			Interact.reportAdvanced =
				Interact.reportAdvanced + theEvent.toString(EventRecord.VIEWER);
		}

		// Set display parameters.
		showingNumbers =
			(Interact.controls.complexityChoice.getSelectedIndex() == ControlsMenuBar.ADVANCED);

		// Select right equations for kind of event and viewer sex.
		male = Interact.person[Interact.viewer].sex;
		interpersonalActionRatherThanSelfDirected = true;
		if (male) {
			emotionEqs = Interact.maleEmotion;
			traitEqs = Interact.maleTrait;
			if (theEvent.vao[1] == theEvent.vao[2]) {
				// Male self-directed event.
				interpersonalActionRatherThanSelfDirected = false;
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
				interpersonalActionRatherThanSelfDirected = false;
				equationSet = Interact.femaleSelf;
			} else {
				if (theEvent.abosIndexes[3][1] > -1) {
					equationSet = Interact.femaleaboS;
				} else {
					equationSet = Interact.femaleabo;
				}
			}
		}

		/*
		 * Set conceptGates for selecting emotions, traits and institutionally
		 * appropriate behaviors and identities.
		 */
		theEvent.setConceptGates();
		boolean[] modifierPairConceptGate = { true, true };

		// If the behavior for this event is unspecified, find the optimal
		// behaviors.
		if (theEvent.abosIndexes[1][1] < 0) { // Unspecified behavior.
			profile =
				equationSet.optimalProfile(MathModel.FIND_BEHAVIOR, theEvent);
			boxText = "";
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
			actorBehaviorEPA.setText(boxText);
			// Add the optimal profile to the behavior list,
			// so it will appear as a behavior definition that
			// can be selected.
			Data newEntry = DefineSituationCard.newDataEntry(boxText);
			Interact.behaviors.addElement(newEntry);
			matchesToProfile =
				Interact.behaviors.getMatches(profile, male,
					theEvent.actorBehaviorPairConceptGate,
					theEvent.actorBehaviorDivisionConceptGate,
					theEvent.actorBehaviorComplexConceptGate);
			for (int i = 0; i < matchesToProfile.size(); i++) {
				resultLine = (Retrieval) matchesToProfile.elementAt(i);
				if (showingNumbers) {
					lineText =
						Interact.formatLocaleDecimal(resultLine.D) + comma
							+ resultLine.word;
				} else {
					lineText = resultLine.word;
				}
				actorBehaviors.add(lineText);
			}
			// Archive the result for reporting.
			if (Interact.keepingRecord) {
				writeReport(Interact.InteractText.getString("actorBehaviors"),
					boxText, matchesToProfile);
				Interact.reportExpert = Interact.reportExpert + newLine;
				Interact.reportAdvanced = Interact.reportAdvanced + newLine;
			}
			if (Interact.actorBehaviorBatch)
				System.out.println(Interact.InteractText
					.getString("actorBehaviors") + boxText);
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			return;
		}

		// Compute impressions and deflection.
		equationSet.impressions(theEvent);
		graph.repaint();

		// Archive event information for reporting.
		if (Interact.keepingRecord) {
			Interact.reportAdvanced =
				Interact.reportAdvanced
					+ theEvent.toString(EventRecord.SITUATION);
			Interact.reportAdvanced =
				Interact.reportAdvanced
					+ theEvent.toString(EventRecord.DEFLECTION1);
			Interact.reportExpert =
				Interact.reportExpert
					+ theEvent.toString(EventRecord.SITUATION);
			Interact.reportExpert =
				Interact.reportExpert
					+ theEvent.toString(EventRecord.QUANTITIES);
			Interact.reportExpert =
				Interact.reportExpert
					+ theEvent.toString(EventRecord.DEFLECTION2);
		}
		if (Interact.listingEvents)
			System.out.print(theEvent.toString(EventRecord.SITUATION));
		if (Interact.impressionsBatch)
			System.out.println(theEvent.toString(EventRecord.QUANTITIES));
		if (Interact.deflectionBatch)
			System.out.println(theEvent.toString(EventRecord.DEFLECTION2));

		// Actor emotion.
		profile =
			emotionEqs.computeModifier(theEvent.abosFundamentals[0],
				theEvent.abosTransientsOut[0]);
		// Store emotion EPA profile for display and for use by face routine.
		boxText = "";
		for (int i = 0; i < 3; i++) {
			boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
		}
		actorEmotionEPA.setText(boxText);
		// Show matching words.
		matchesToProfile =
			Interact.modifiers.getMatches(profile, male,
				modifierPairConceptGate,
				EventRecord.EMOTION_DIVISION_CONCEPT_GATE,
				EventRecord.ALL_FALSE_COMPLEX_CONCEPT_GATE);
		for (int i = 0; i < matchesToProfile.size(); i++) {
			resultLine = (Retrieval) matchesToProfile.elementAt(i);
			if (showingNumbers) { // With distances, if showing numbers.
				lineText =
					Interact.formatLocaleDecimal(resultLine.D) + comma
						+ resultLine.word;
			} else {
				lineText = resultLine.word;
			}
			actorEmotions.add(lineText);
		}
		// Archive the result for reporting.
		if (Interact.keepingRecord) {
			writeReport(Interact.InteractText.getString("actorEmotions"),
				boxText, matchesToProfile);
		}
		if (Interact.actorEmotionBatch)
			System.out.println(Interact.InteractText.getString("actorEmotions")
				+ boxText);
		// Set sex and emotion EPA of actor face.
		actorFace.setProfile(profile);
		actorFace.setPictureChoice(Interact.person[theEvent.vao[1]].visage);

		// Object emotion.
		objectFace.setProfile(Face.BLANKING_PROFILE);
		// Erase object face with self acts.
		if (!interpersonalActionRatherThanSelfDirected) {
			profile = Face.BLANKING_PROFILE;
		} else {
			profile =
				emotionEqs.computeModifier(theEvent.abosFundamentals[2],
					theEvent.abosTransientsOut[2]);
			// Store emotion EPA profile for display and for use by face
			// routine.
			boxText = "";
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
			objectEmotionEPA.setText(boxText);
			// Show matching words.
			matchesToProfile =
				Interact.modifiers.getMatches(profile, male,
					modifierPairConceptGate,
					EventRecord.EMOTION_DIVISION_CONCEPT_GATE,
					EventRecord.ALL_FALSE_COMPLEX_CONCEPT_GATE);
			for (int i = 0; i < matchesToProfile.size(); i++) {
				resultLine = (Retrieval) matchesToProfile.elementAt(i);
				if (showingNumbers) {
					lineText =
						Interact.formatLocaleDecimal(resultLine.D) + comma
							+ resultLine.word;
				} else {
					lineText = resultLine.word;
				}
				objectEmotions.add(lineText);
			}
			// Archive the result for reporting.
			if (Interact.keepingRecord) {
				writeReport(Interact.InteractText.getString("objectEmotions"),
					boxText, matchesToProfile);
			}
			if (Interact.objectEmotionBatch)
				System.out.println(Interact.InteractText
					.getString("objectEmotions") + boxText);
			// Set sex and emotion EPA of object face.
			objectFace.setProfile(profile);
			objectFace
				.setPictureChoice(Interact.person[theEvent.vao[2]].visage);
		}
		if (!Interact.objectEmotionBatch) {
			// Don't slow batch runs with face drawing.
			actorFace.repaint();
			objectFace.repaint();
		}

		// Actor ideal behavior.
		profile = equationSet.optimalProfile(MathModel.FIND_BEHAVIOR, theEvent);
		boxText = "";
		for (int i = 0; i < 3; i++) {
			boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
		}
		actorBehaviorEPA.setText(boxText + computeEntityStress(theEvent, 0));
		matchesToProfile =
			Interact.behaviors.getMatches(profile, male,
				theEvent.actorBehaviorPairConceptGate,
				theEvent.actorBehaviorDivisionConceptGate,
				theEvent.actorBehaviorComplexConceptGate);
		for (int i = 0; i < matchesToProfile.size(); i++) {
			resultLine = (Retrieval) matchesToProfile.elementAt(i);
			if (!((usedBehaviors.contains(resultLine.word)) & (DefineEventsCard.behaviorMemory == DefineEventsCard.NOT_ALLOWING_REPEATED_BEHAVIORS))) {
				if (showingNumbers) {
					lineText =
						Interact.formatLocaleDecimal(resultLine.D) + comma
							+ resultLine.word;
				} else {
					lineText = resultLine.word;
				}
				actorBehaviors.add(lineText);
			}
		}
		// Archive the result for reporting.
		if (Interact.keepingRecord) {
			writeReport(Interact.InteractText.getString("actorBehaviors"),
				boxText, matchesToProfile);
		}
		if (Interact.actorBehaviorBatch)
			System.out.println(Interact.InteractText
				.getString("actorBehaviors") + boxText);

		// Object ideal response.
		boxText = "";
		if (!interpersonalActionRatherThanSelfDirected) {
			objectBehaviorEPA.setText(boxText);
		} else {
			// Compute and show expected response from actor's viewpoint.
//			objectBehaviors.setForeground(Color.black); // Black for normal
//														// results.
//			objectBehaviorEPA.setForeground(Color.black);
			objectBehaviorTitle.setText(Interact.InteractText
				.getString("objectBehaviors"));
			profile =
				equationSet.optimalProfile(MathModel.FIND_RESPONSE, theEvent);
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
			objectBehaviorEPA.setText(boxText
				+ computeEntityStress(theEvent, 2));
			matchesToProfile =
				Interact.behaviors.getMatches(profile, male,
					theEvent.objectBehaviorPairConceptGate,
					theEvent.objectBehaviorDivisionConceptGate,
					theEvent.objectBehaviorComplexConceptGate);
			for (int i = 0; i < matchesToProfile.size(); i++) {
				resultLine = (Retrieval) matchesToProfile.elementAt(i);
				if (!((usedBehaviors.contains(resultLine.word)) & (DefineEventsCard.behaviorMemory == DefineEventsCard.NOT_ALLOWING_REPEATED_BEHAVIORS))) {
					if (showingNumbers) {
						lineText =
							Interact.formatLocaleDecimal(resultLine.D) + comma
								+ resultLine.word;
					} else {
						lineText = resultLine.word;
					}
					objectBehaviors.add(lineText);
				}
			}
			// Archive the result for reporting.
			if (Interact.keepingRecord) {
				writeReport(Interact.InteractText.getString("objectBehaviors"),
					boxText, matchesToProfile);
			}
			if (Interact.objectBehaviorBatch)
				System.out.println(Interact.InteractText
					.getString("objectBehaviors") + boxText);
		}

		// Actor label.
		labelingProfile =
			equationSet.optimalProfile(MathModel.FIND_ACTOR, theEvent);
		boxText = "";
		for (int i = 0; i < 3; i++) {
			boxText =
				boxText + Interact.formatLocaleDecimal(labelingProfile[i]);
		}
		actorLabelEPA.setText(boxText);
		matchesToProfile =
			Interact.identities.getMatches(labelingProfile, male,
				theEvent.actorPairConceptGate,
				theEvent.actorDivisionConceptGate,
				theEvent.actorComplexConceptGate);
		for (int i = 0; i < matchesToProfile.size(); i++) {
			resultLine = (Retrieval) matchesToProfile.elementAt(i);
			if (showingNumbers) {
				lineText =
					Interact.formatLocaleDecimal(resultLine.D) + comma
						+ resultLine.word;
			} else {
				lineText = resultLine.word;
			}
			actorLabels.add(lineText);
		}
		// Archive the result for reporting.
		if (Interact.keepingRecord) {
			writeReport(Interact.InteractText.getString("actorLabels"),
				boxText, matchesToProfile);
		}
		if (Interact.actorLabelBatch)
			System.out.println(Interact.InteractText.getString("actorLabels")
				+ boxText);

		// Actor attribute.
		identityProfile = new double[3];
		datum =
			((Data) Interact.identities.elementAt(theEvent.abosIndexes[0][1])); // Actor
																				// identity.
		if (male) {
			System.arraycopy(datum.maleEPA, 0, identityProfile, 0, 3);
		} else {
			System.arraycopy(datum.femaleEPA, 0, identityProfile, 0, 3);
		}
		profile = traitEqs.computeModifier(identityProfile, labelingProfile);
		boxText = "";
		for (int i = 0; i < 3; i++) {
			boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
		}
		actorAttributeEPA.setText(boxText);
		matchesToProfile =
			Interact.modifiers.getMatches(profile, male,
				modifierPairConceptGate,
				EventRecord.TRAIT_DIVISION_CONCEPT_GATE,
				EventRecord.ALL_FALSE_COMPLEX_CONCEPT_GATE);
		for (int i = 0; i < matchesToProfile.size(); i++) {
			resultLine = (Retrieval) matchesToProfile.elementAt(i);
			if (showingNumbers) {
				lineText =
					Interact.formatLocaleDecimal(resultLine.D) + comma
						+ resultLine.word;
			} else {
				lineText = resultLine.word;
			}
			actorAttributes.add(lineText);
		}
		// Archive the result for reporting.
		if (Interact.keepingRecord) {
			writeReport(Interact.InteractText.getString("actorAttributes"),
				boxText, matchesToProfile);
		}
		if (Interact.actorAttributeBatch)
			System.out.println(Interact.InteractText
				.getString("actorAttributes") + boxText);
		// Distribute reidentification result for actor into history array.
		SentimentChange.incrementReidentificationHistory(theEvent.vao[0],
			theEvent.vao[1], labelingProfile, currentEvent);

		// Object label.
		if (interpersonalActionRatherThanSelfDirected) {
			labelingProfile =
				equationSet.optimalProfile(MathModel.FIND_OBJECT, theEvent);
			boxText = "";
			for (int i = 0; i < 3; i++) {
				boxText =
					boxText + Interact.formatLocaleDecimal(labelingProfile[i]);
			}
			objectLabelEPA.setText(boxText);
			matchesToProfile =
				Interact.identities.getMatches(labelingProfile, male,
					theEvent.objectPairConceptGate,
					theEvent.objectDivisionConceptGate,
					theEvent.objectComplexConceptGate);
			for (int i = 0; i < matchesToProfile.size(); i++) {
				resultLine = (Retrieval) matchesToProfile.elementAt(i);
				if (showingNumbers) {
					lineText =
						Interact.formatLocaleDecimal(resultLine.D) + comma
							+ resultLine.word;
				} else {
					lineText = resultLine.word;
				}
				objectLabels.add(lineText);
			}
			// Archive the result for reporting.
			if (Interact.keepingRecord) {
				writeReport(Interact.InteractText.getString("objectLabels"),
					boxText, matchesToProfile);
			}
			if (Interact.objectLabelBatch)
				System.out.println(Interact.InteractText
					.getString("objectLabels") + boxText);

			// Object attribute.
			identityProfile = new double[3];
			datum =
				((Data) Interact.identities
					.elementAt(theEvent.abosIndexes[2][1])); // Object
																// identity.
			if (male) {
				System.arraycopy(datum.maleEPA, 0, identityProfile, 0, 3);
			} else {
				System.arraycopy(datum.femaleEPA, 0, identityProfile, 0, 3);
			}
			profile =
				traitEqs.computeModifier(identityProfile, labelingProfile);
			boxText = "";
			for (int i = 0; i < 3; i++) {
				boxText = boxText + Interact.formatLocaleDecimal(profile[i]);
			}
			objectAttributeEPA.setText(boxText);
			matchesToProfile =
				Interact.modifiers.getMatches(profile, male,
					modifierPairConceptGate,
					EventRecord.TRAIT_DIVISION_CONCEPT_GATE,
					EventRecord.ALL_FALSE_COMPLEX_CONCEPT_GATE);
			for (int i = 0; i < matchesToProfile.size(); i++) {
				resultLine = (Retrieval) matchesToProfile.elementAt(i);
				if (showingNumbers) {
					lineText =
						Interact.formatLocaleDecimal(resultLine.D) + comma
							+ resultLine.word;
				} else {
					lineText = resultLine.word;
				}
				objectAttributes.add(lineText);
			}
			// Archive the result for reporting.
			if (Interact.keepingRecord) {
				writeReport(
					Interact.InteractText.getString("objectAttributes"),
					boxText, matchesToProfile);
			}
			if (Interact.objectAttributeBatch)
				System.out.println(Interact.InteractText
					.getString("objectAttributes") + boxText);
			// Distribute reidentification result for object into history array.
			SentimentChange.incrementReidentificationHistory(theEvent.vao[0],
				theEvent.vao[2], labelingProfile, currentEvent);
		}
		// Deal with non-participants.
		SentimentChange.updateSimultaneitySet(theEvent.vao[0], currentEvent);
		if (Interact.keepingRecord) {
			Interact.reportExpert = Interact.reportExpert + newLine;
			Interact.reportAdvanced = Interact.reportAdvanced + newLine;
		}
	} // End implementEvent.

	void clearAllResults() {
		actorEmotions.removeAll();
		actorBehaviors.removeAll();
		actorAttributes.removeAll();
		actorLabels.removeAll();
		objectEmotions.removeAll();
		objectBehaviors.removeAll();
		objectAttributes.removeAll();
		objectLabels.removeAll();
		actorEmotionEPA.setText("");
		actorBehaviorEPA.setText("");
		actorAttributeEPA.setText("");
		actorLabelEPA.setText("");
		objectEmotionEPA.setText("");
		objectBehaviorEPA.setText("");
		objectAttributeEPA.setText("");
		objectLabelEPA.setText("");
		actorFace.setProfile(Face.BLANKING_PROFILE);
		actorFace.repaint();
		objectFace.setProfile(Face.BLANKING_PROFILE);
		objectFace.repaint();
	} // End clearAllResults.

	void writeReport(
		String title, String idealEPAprofile, DataList matchesToProfile) {
		Retrieval resultLine;
		Interact.reportExpert = Interact.reportExpert + title + colonSpace;
		Interact.reportAdvanced = Interact.reportAdvanced + title + colonSpace;
		// Expert text report shows EPA profile and one retrieval word.
		resultLine = (Retrieval) matchesToProfile.elementAt(0);
		Interact.reportExpert =
			Interact.reportExpert + idealEPAprofile + dotSpace
				+ Interact.formatLocaleDecimal(resultLine.D) + comma
				+ resultLine.word + dotSpace + newLine;
		// Advanced report shows four retrieval words, without profile.
		for (int i = 0; i < Math.min(4, matchesToProfile.size()); i++) { 
			resultLine = (Retrieval) matchesToProfile.elementAt(i);
			Interact.reportAdvanced =
				Interact.reportAdvanced + resultLine.word + dotSpace;
		}
		Interact.reportAdvanced = Interact.reportAdvanced + newLine;
	} // End writeReport.

	/**
	 * Initialize future (i.e., remaining history) with the starting
	 * fundamentals.
	 */
	static void fillHistory(
		int ego, int alter, double[] fundamentalEPA, int firstAppearance) {
		for (int t = 0; t < SentimentChange.maxHistory; t++) {
			SentimentChange.history[ego][alter][t].nAveragedEvents = 0;
			for (int epa = 0; epa < Interact.N_DIMENSIONS; epa++) {
				SentimentChange.history[ego][alter][t].mutatingFundamental[epa] =
					fundamentalEPA[epa];
			}
		}
		if (SentimentChange.changingSentiments) {
			// Attach initial fundamental to Mutator identity in dictionary.
			String MutatorName =
				"Mutator_" + Integer.toString(ego + 1) + "_"
					+ Integer.toString(alter + 1);
			Data affectedLine =
				(Data) Interact.identities.elementAt(Interact.identities
					.getIndex(MutatorName));
			for (int epa = 0; epa < Interact.N_DIMENSIONS; epa++) {
				affectedLine.maleEPA[epa] = fundamentalEPA[epa];
				affectedLine.femaleEPA[epa] = fundamentalEPA[epa];
			}
		}
	} // End fillHistory.

	static String computeEntityStress(EventRecord theEvent, int position) {
		// position is 0 for actor, 2 for object.
		double sum, difference;
		sum = 0.0;
		for (int epa = 0; epa < 3; epa++) {
			difference =
				theEvent.abosFundamentals[position][epa]
					- theEvent.abosTransientsOut[position][epa];
			sum = sum + difference * difference;
		}
		return Interact.InteractText.getString("identityPressureLeft")
			+ Interact.formatLocaleDecimal(sum)
			+ Interact.InteractText.getString("identityPressureRight");
	}

} // End EventAnalysisCard.
