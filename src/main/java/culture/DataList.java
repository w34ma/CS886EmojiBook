package culture;

import java.awt.Frame;
import java.text.ParsePosition;
import java.util.Vector;

/**
 * This extension of Vector is a container for methods applying to Vectors of
 * Data (dictionary entries), Vectors of EventRecord (experience sequences),
 * Vectors of Retrieval (computation result).
 */
public class DataList extends Vector {

	public static final int LENGTH_OF_WORD_AND_EPA = 7;

	public DataList() { // Constructor method.
	}

	public DataList(String nameOfSet, String[] dictionaryText) {
		// Constructor method.
		int numberOfTerms = dictionaryText.length;
		Number entry;
		String theWord;
		double[] theValues = new double[LENGTH_OF_WORD_AND_EPA - 1];
		boolean[] theConceptGates =
			new boolean[Data.MAX_NUMBER_OF_CONCEPT_GATES];
		int lineLength = -1;
		int linePosition = 0;
		theWord = dictionaryText[0]; // Get the word for the first entry.
		for (int i = 0; i < numberOfTerms; i++) { 
			// Examine a term in the string array.
			entry =
				Interact.localeDecimal.parse(dictionaryText[i],
					new ParsePosition(0));
			if (entry == null) { // Word string is not convertible to number.
				if (linePosition == lineLength) { 
					// The last line was the right length.
					this.addElement(new Data(theWord, theValues,
						theConceptGates));
				} else {
					if (lineLength == -1) {
						if (i > 0) {
							// Set lineLength the first time through.
							lineLength = linePosition; 
							this.addElement(new Data(theWord, theValues,
								theConceptGates));
						}
					} else { // Error in data.
						System.err.println("Missing term in " + nameOfSet
							+ ", for word " + theWord);
					}
				}
				// Start a new line.
				theWord = dictionaryText[i];
				linePosition = 0;
			} else { // The string converted to a number.
				if ((++linePosition) < LENGTH_OF_WORD_AND_EPA) { 
					// This is an EPA value.
					theValues[(linePosition) - 1] = entry.doubleValue();
				} else { 
					// This is a ConceptGate indicator.
					for (int f = 0; f < dictionaryText[i].length(); f++) {
						theConceptGates[f] = dictionaryText[i].charAt(f) == '1';
					} // end for.
				} // end else.
			} // end else.
		} // end for.
		// Include last entry.
		this.addElement(new Data(theWord, theValues, theConceptGates)); 
	} // End DataList(String, String[]) constructor method.

	// ROUTINES INVOLVING DataList AS (Data).

	/**
	 * Returns word for lineNumber; no-data character if the lineNumber is
	 * negative.
	 */
	String getWord(int lineNumber) {
		String output;
		if (lineNumber >= 0) {
			output = ((Data) this.elementAt(lineNumber)).word;
		} else {
			output = Interact.InteractText.getString("empty");
		}
		return output;
	} // End getWord.

	/** Returns lineNumber for word; -1 if the word is not found. */
	public int getIndex(String givenWord) {
		String testWord;
		int i;
		// Check for null.
		try {
			i = givenWord.length(); // Dummy assignment.
		} catch (Exception ex) {
			return -1;
		}
		// Check for empty dictionary.
		if (this.isEmpty()) { return -1; }
		// Return the index number of the word, or -1 if not found in this
		// dictionary.
		for (i = 0; i < this.size(); i++) {
			testWord = ((Data) this.elementAt(i)).word;
			if (givenWord.equals(testWord)) { return i; }
		}
		return -1;
	} // End getIndex.

	/** Returns list of words closest to a given profile. */
	public DataList getMatches(
		double[] aProfile, boolean male, boolean[] pairConceptGate,
		boolean[] divisionConceptGate, boolean[] complexConceptGate) {
		Data entry;
		DataList retrievedEntries = new DataList();
		if (Interact.initializing) { return (retrievedEntries); }
		boolean pairTest, divisionTest, complexTest;
		double[] tempProfile;
		double cutoff = Interact.searchCutoff;
		if ((ControlsMenuBar.inferringFromEmotion)
			|| (ControlsMenuBar.exploringSelf)) {
			// No limit for showing closest entries during emotion inference
			// or in searches for self analysis.
			cutoff = 20000;
		}
		retrievedEntries.removeAllElements();
		// For each entry in this dictionary:
		for (int i = 0; i < this.size(); i++) { 
			entry = (Data) this.elementAt(i);
			// Apply the ConceptGates.
			pairTest = divisionTest = complexTest = false;
			// Check whether the entry's ConceptGate profile matches the test
			// profile, in each of the three sets of ConceptGates.
			// Count to the number of ConceptGates in the largest set.
			for (int j = 0; j < Data.NUMBER_DIVISION_CONCEPT_GATES; j++) {
				divisionTest =
					divisionTest
						| (divisionConceptGate[j] & entry.divisionConceptGate[j]);
				if (j < Data.NUMBER_PAIR_CONCEPT_GATES) {
					pairTest =
						pairTest
							| (pairConceptGate[j] & entry.pairConceptGate[j]);
				}
				if (j < Data.NUMBER_COMPLEX_CONCEPT_GATES) {
					complexTest =
						complexTest
							| (complexConceptGate[j] & entry.complexConceptGate[j]);
				}
			}
			if (pairTest & (divisionTest | complexTest)) { 
				// Dropping through ConceptGates.
				// Compute the distance.
				if (male) {
					tempProfile = entry.maleEPA;
				} else {
					tempProfile = entry.femaleEPA;
				}
				double D = 0;
				for (int j = 0; j < 3; j++) {
					D =
						D + (tempProfile[j] - aProfile[j])
							* (tempProfile[j] - aProfile[j]);
				}
				D = Math.sqrt(D);
				if (D <= cutoff) {
					retrievedEntries.addElement(new Retrieval(D, entry.word,
						tempProfile));
				}
			}
		}
		if (retrievedEntries.size() == 0) {
			double[] dummy =
				{ Interact.MISSING_VALUE, Interact.MISSING_VALUE,
					Interact.MISSING_VALUE };
			retrievedEntries.addElement(new Retrieval(Interact.MISSING_VALUE,
				Interact.InteractText.getString("emptyResults"), dummy));
		}
		retrievedEntries.numericSort();
		return retrievedEntries;
	} // End getMatches.

	// ROUTINE INVOLVING DataList AS (EventRecord).

	/** Assemble materials required to implement a given event. */
	boolean assembleInputs(int thisEvent) {
		EventRecord occurringEvent, anEvent, priorEvent;
		int viewer, numberOfCaseSlots, modifierIndex, designatorIndex, historyError;
		boolean viewerMale;
		Data datum;
		double[] profile;
		boolean successful = true;
		boolean unsuccessful = false;
		// Put in no-data codes for this and future defined events.
		for (int i = this.size() - 1; i >= thisEvent; i--) {
			anEvent = (EventRecord) this.elementAt(i);
			anEvent.deflection = -1;
			for (int slot = 0; slot < 4; slot++) {
				for (int epa = 0; epa < 3; epa++) {
					anEvent.abosFundamentals[slot][epa] =
						Interact.MISSING_VALUE;
					anEvent.abosTransientsIn[slot][epa] =
						Interact.MISSING_VALUE;
					anEvent.abosTransientsOut[slot][epa] =
						Interact.MISSING_VALUE;
				}
			}
		}
		// Also for history array.
		for (int i = SentimentChange.maxHistory - 1; i >= thisEvent; i--) {
			for (int ego = 0; ego < Interact.numberOfInteractants; ego++) {
				for (int alter = 0; alter < Interact.numberOfInteractants; alter++) {
  					for (int epa = 0; epa < 3; epa++) {
						SentimentChange.history[ego][alter][i].simultaneityTransient[epa] =
							Interact.MISSING_VALUE;
					}
				}
			}
		}
		// Event record to fill out.
		occurringEvent = (EventRecord) this.elementAt(thisEvent); 
		// Get viewer and viewer sex.
		viewer = occurringEvent.vao[0];
		viewerMale = Interact.person[viewer].sex;
		// Determine number of slots being handled by equations.
		if (occurringEvent.vao[1] == occurringEvent.vao[2]) {
			numberOfCaseSlots = 2; // Self-directed.
		} else if (occurringEvent.abosIndexes[3][1] > -1) {
			numberOfCaseSlots = 4; // Dyadic event with setting.
		} else {
			numberOfCaseSlots = 3; // Dyadic event.
		}
		for (int position = 0; position < numberOfCaseSlots; position++) {
			// For each slot in use:
			datum = new Data();
			profile = new double[3]; // Create holders of data to point to.
			// Provide a fundamental and set of ConceptGates.
			modifierIndex = occurringEvent.abosIndexes[position][0];
			designatorIndex = occurringEvent.abosIndexes[position][1];
			if ((designatorIndex == -1) & (position == 1)) {
				continue; // Unspecified behavior; go to next element.
			}
			if (modifierIndex > -1) { // For a modified identity:
				// Amalgamate modifier + noun.
				profile =
					MathModel.amalgamate(viewerMale, modifierIndex,
						designatorIndex);
				// Get the dictionary line for the identity to flag that an
				// identification exists.
				datum = ((Data) Interact.identities.elementAt(designatorIndex));
			} else {
				// Retrieve the dictionary line for the specified noun (or
				// verb).
				switch (position) {
				case 0: // Actor
					datum =
						((Data) Interact.identities.elementAt(designatorIndex));
					break;
				case 1: // Behavior
					datum =
						((Data) Interact.behaviors.elementAt(designatorIndex));
					break;
				case 2: // Object
					datum =
						((Data) Interact.identities.elementAt(designatorIndex));
					break;
				case 3: // Setting
					datum =
						((Data) Interact.settings.elementAt(designatorIndex));
				}
				if (viewerMale) {
					System.arraycopy(datum.maleEPA, 0, profile, 0, 3);
				} else {
					System.arraycopy(datum.femaleEPA, 0, profile, 0, 3);
				}
			}
			occurringEvent.abosFundamentals[position] = profile;
			// Now the ConceptGates.
			// Copy the ConceptGates associated with the noun or verb.
			System.arraycopy(datum.pairConceptGate, 0,
				occurringEvent.abosPairConceptGates[position], 0,
				Data.NUMBER_PAIR_CONCEPT_GATES);
			System.arraycopy(datum.divisionConceptGate, 0,
				occurringEvent.abosDivisionConceptGates[position], 0,
				Data.NUMBER_DIVISION_CONCEPT_GATES);
			System.arraycopy(datum.complexConceptGate, 0,
				occurringEvent.abosComplexConceptGates[position], 0,
				Data.NUMBER_COMPLEX_CONCEPT_GATES);
			/*
			 * Find an output transient from prior events to use as input
			 * transient in this event.
			 */
			historyError = -2; // No error.
			if ((thisEvent == 0) || (occurringEvent.beginNewAnalysis == true)) {
				// Transients = fundamentals on initial event.
				occurringEvent.abosTransientsIn[position] =
					occurringEvent.abosFundamentals[position];
				SentimentChange.history[occurringEvent.vao[0]][occurringEvent.vao[1]][thisEvent].nAveragedEvents =
					1;
				SentimentChange.history[occurringEvent.vao[0]][occurringEvent.vao[2]][thisEvent].nAveragedEvents =
					1;
			} else if ((position == 1)
				& (DefineEventsCard.behaviorMemory != DefineEventsCard.USING_BEHAVIOR_TRANSIENTS)) {
				// User doesn't want repeated behaviors to change meaning.
				occurringEvent.abosTransientsIn[position] =
					occurringEvent.abosFundamentals[position];
			} else {
				remembering: for (int time = thisEvent - 1; time >= 0; time--) {
					// Go backwards in time from present.
					historyError = -2; // No error.
					priorEvent = (EventRecord) this.elementAt(time);
					if (priorEvent.deflection < 0) {
						// The current event follows unimplemented events.
						historyError = time;
						break remembering; // Leave the remembering:for block.
					}
					/*
					 * Actor and object transients are sought in the history
					 * array to allow for conglomerate transients formed by
					 * simultaneous events.
					 */
					if (position == 0) {
						// Look for data on the current actor in the history
						// array.
						Situation historySlice =
							(Situation) SentimentChange.history[occurringEvent.vao[0]][occurringEvent.vao[1]][time];
						if ((historySlice.simultaneityTransient[0] != Interact.MISSING_VALUE)
							& (historySlice.nSimultaneousEvents == historySlice.simultaneitySetIndex)) {
							System.arraycopy(
								historySlice.simultaneityTransient, 0,
								occurringEvent.abosTransientsIn[0], 0, 3);
							break remembering;
						} // Else keep going back in time.
					} else if (position == 2) {
						// Look for data on the current object in the history
						// array.
						Situation historySlice =
							(Situation) SentimentChange.history[occurringEvent.vao[0]][occurringEvent.vao[2]][time];
						if ((historySlice.simultaneityTransient[0] != Interact.MISSING_VALUE)
							& (historySlice.nSimultaneousEvents == historySlice.simultaneitySetIndex)) {
							System.arraycopy(
								historySlice.simultaneityTransient, 0,
								occurringEvent.abosTransientsIn[2], 0, 3);
							break remembering;
						} // Else keep going back in time.
					} else if ((occurringEvent.abosIndexes[position][1] == priorEvent.abosIndexes[position][1])
						&& (priorEvent.abosTransientsOut[position][0] != Interact.MISSING_VALUE)) {
						// Copy prior transient for same behavior and/or for
						// setting.
						System.arraycopy(
							priorEvent.abosTransientsOut[position], 0,
							occurringEvent.abosTransientsIn[position], 0, 3);
						// Note: this takes account of shifting meanings of a
						// repeated behavior.
						break remembering;
					}
					// This prior event didn't involve the current interactant,
					// behavior, or setting.
					historyError = -1;
				} // End remembering:for.
				if (historyError == -1) { // Initial appearance of this element.
					System.arraycopy(occurringEvent.abosFundamentals[position],
						0, occurringEvent.abosTransientsIn[position], 0, 3);
				} else if (historyError > -1) {
					Cancel_OK_Dialog historyProblem =
						new Cancel_OK_Dialog((Frame) Interact.appletFrame,
							Interact.InteractText
								.getString("eventProblemTitle"),
							Interact.InteractText.getString("earlierEvent")
								+ (new Integer(historyError + 1)).toString()
								+ Interact.InteractText.getString("isNot")
								+ Interact.InteractText
									.getString("sentenceSeparation")
								+ Interact.InteractText
									.getString("paragraphCommand"),
							Interact.InteractText.getString("ok"), null);
					historyProblem.setVisible(true);
					return unsuccessful;
				} // End if.
			} // End else.
		} // End for(position).
		return successful;
	} // End assembleInputs.

	// ROUTINE INVOLVING DataList AS (Retrieval)

	/** ShellSort the distances to reference profile. */
	boolean numericSort() {
		Retrieval result1, result2;
		int first, last, gap, i, j, k;
		boolean changed = false;
		first = 0;
		last = this.size() - 1;
		gap = (last - first + 1) / 2;
		while (gap > 0) {
			for (i = (first + gap); i <= last; i++) {
				j = i - gap;
				while (j >= first) {
					result1 = (Retrieval) this.elementAt(j);
					k = j + gap;
					result2 = (Retrieval) this.elementAt(k);
					if (result1.D <= result2.D) {
						j = 0;
					} else {
						// Switch elements.
						this.insertElementAt(result2, j);
						this.removeElementAt(j + 1);
						this.insertElementAt(result1, k);
						this.removeElementAt(k + 1);
						changed = true;
					}
					j = j - gap;
				}
			}
			gap = gap / 2;
		}
		return changed;
	} // End numericSort.

} // End class DataList.

