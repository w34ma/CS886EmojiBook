package culture;

/**
 * Routines for accomodating sentiments to experience, as discussed in D. R.
 * Heise, 2006. Sentiment formation in social interaction. In <i>Purpose,
 * Meaning, and ACTAction : Control Systems Theories in Sociology</i>, edited by K.
 * A. McClelland and T. J. Fararo. New York: Palgrave Macmillan.
 */
public class SentimentChange {

	/**
	 * A dimension of the history array; limits the number of events Interact
	 * can handle.
	 */
	static final int maxHistory = 3100;

	/** Switch for allowing sentiments to change as a result of events. */
	static boolean changingSentiments;

	/** Versus sentiments from reidentifications. */
	static boolean sentimentsFromTransients = false;

	/**
	 * Number of past events that form current sentiments. Used in the
	 * fillHistory and incrementHistory sub-rountines.
	 */
	static int maximumRecall;

	/**
	 * [viewer][alter][time < maxHistory]. Processed in this.fillHistory,
	 * this.incrementReidentificationHistory, DataList.assembleInputs,
	 * DefineEventsCard.storeViewer.
	 */
	static Situation history[][][];

	static void incrementReidentificationHistory(
		int viewer, int alter, double[] latestReident, int time) {
		// Called from EventAnalysisCard.implementEvent.
		double simultaneousReidentificationSum;
		int cumulativeN;
		// Incorporate the current Reidentification.
		if ((history[viewer][alter][time].nSimultaneousEvents != 1)
			& (history[viewer][alter][time].simultaneitySetIndex > 1)) {
			// Processing an event within a simultaneity set and beyond the
			// first entry.
			cumulativeN = history[viewer][alter][time - 1].nAveragedEvents;
			for (int epa = 0; epa < Interact.N_DIMENSIONS; epa++) {
				// Average simultaneous reidentifications so far, and store in
				// this slice of history,
				simultaneousReidentificationSum =
					history[viewer][alter][time - 1].simultaneityReidentification[epa]
						* cumulativeN;
				history[viewer][alter][time].simultaneityReidentification[epa] =
					(simultaneousReidentificationSum + latestReident[epa])
						/ (cumulativeN + 1);
			}
			history[viewer][alter][time].nAveragedEvents = cumulativeN + 1;
		} else {
			for (int epa = 0; epa < Interact.N_DIMENSIONS; epa++) {
				// Just store the latest reidentification.
				history[viewer][alter][time].simultaneityReidentification[epa] =
					latestReident[epa];
			}
			// history[viewer][alter][time].nAveragedEvents = 1 was set in
			// incrementTransientsHistory.
		}
		if (!sentimentsFromTransients) {
			// Try changing fundamental.
			computeFundamental(viewer, alter, time); 
		}
	} // End incrementReidentificationHistory.

	static void incrementTransientsHistory(
		int viewer, int alter, double[] latestImpression, int time) {
		double simultaneousTransientSum;
		int cumulativeN;
		if (! Interact.onInteractionsCard){
		// Incorporate the current transient.
		if ((history[viewer][alter][time].nSimultaneousEvents != 1)
			& (history[viewer][alter][time].simultaneitySetIndex > 1)) {
			// Processing an event within a simultaneity set and beyond the
			// first entry.
			cumulativeN = history[viewer][alter][time - 1].nAveragedEvents;
			for (int epa = 0; epa < Interact.N_DIMENSIONS; epa++) {
				// Average simultaneous transients so far, and store in this
				// slice of history,
				simultaneousTransientSum =
					history[viewer][alter][time - 1].simultaneityTransient[epa]
						* cumulativeN;
				history[viewer][alter][time].simultaneityTransient[epa] =
					(simultaneousTransientSum + latestImpression[epa])
						/ (cumulativeN + 1);
			}
			history[viewer][alter][time].nAveragedEvents = cumulativeN + 1;
		} else {
			for (int epa = 0; epa < Interact.N_DIMENSIONS; epa++) {
				// Just store the latest impression.
				history[viewer][alter][time].simultaneityTransient[epa] =
					latestImpression[epa];
			}
			if (latestImpression[0] != Interact.MISSING_VALUE) {
				history[viewer][alter][time].nAveragedEvents = 1;
			}
		}
		}
		if (sentimentsFromTransients) {
			// Try changing fundamental.
			computeFundamental(viewer, alter, time); 
		}
	} // End incrementTransientsHistory.

	/**
	 * Compute a changing fundamental from prior event outcomes - either
	 * reidentifications or event transients.
	 */
	static void computeFundamental(int viewer, int alter, int time) {
		double outcome, sum, N;
		int viewerAlterIndex, adjustedRecallLimit, priorEvent, startEvent;
		if (!changingSentiments) { return; }
		// Get the relevant dictionary entry.
		String MutatorName =
			"Mutator_" + Integer.toString(viewer + 1) + "_"
				+ Integer.toString(alter + 1);
		viewerAlterIndex = Interact.identities.getIndex(MutatorName);
		Data affectedLine =
			(Data) Interact.identities.elementAt(viewerAlterIndex);
		// Find the first event in the series.
		startEvent = 0;
		priorEvent = time + 1;
		do {
			--priorEvent;
			if (((EventRecord) Interact.person[viewer].serialEvents
				.elementAt(priorEvent)).restartAtZero) {
				startEvent = priorEvent;
				break;
			}
		} while (priorEvent > 0);
		// Average back from this event, or from last before current
		// simultaneity set.
		priorEvent = time + 1;
		do {
			--priorEvent;
		} // Go backward to the end of the last set of simultaneous events.
		while ((priorEvent >= 0)
			&& (history[viewer][alter][priorEvent].nSimultaneousEvents != history[viewer][alter][priorEvent].simultaneitySetIndex));
		priorEvent = Math.max(priorEvent, 0);
		// Compute the current fundamental from averaged event outcomes.
		for (int epa = 0; epa < Interact.N_DIMENSIONS; epa++) {
			// Compute mean, starting at priorEvent and going back over
			// specified number of recalled events.
			sum = 0;
			N = 0;
			adjustedRecallLimit = maximumRecall;
			for (int t = priorEvent; (t > priorEvent - adjustedRecallLimit)
				&& (t >= startEvent); t--) {
				/*
				 * The fundamental is set to the mean of all prior outcomes
				 * before the recall limit is reached, which implements the
				 * Anderson model of time-varying weights. The fundamental is a
				 * moving average after the recall limit is reached.
				 */
				if (sentimentsFromTransients) {
					outcome =
						history[viewer][alter][t].simultaneityTransient[epa];
				} else {
					outcome =
						history[viewer][alter][t].simultaneityReidentification[epa];
				}
				if ((outcome == Interact.MISSING_VALUE)
					|| (history[viewer][alter][t].nSimultaneousEvents != history[viewer][alter][t].simultaneitySetIndex)) {
					// Skip events not involving this alter, and also all but
					// the last event in a simultaneity set.
					adjustedRecallLimit = adjustedRecallLimit + 1;
				} else {
					sum = sum + outcome;
					N = N + 1;
				}
			}
			if (N > 0) { 
				// Don't change the fundamental if this is 
				// a simultaneity set at the beginning.
				history[viewer][alter][time].mutatingFundamental[epa] = sum / N;
				// Attach the fundamental to the "Mutator_viewer_alter"
				// dictionary entry.
				affectedLine.maleEPA[epa] =
					history[viewer][alter][time].mutatingFundamental[epa];
				affectedLine.femaleEPA[epa] = affectedLine.maleEPA[epa];
			} // end if/N>0
		} // end for/epa
	} // End computeFundamental.

	/**
	 * Sentiment learning: Deal with interactants who do not participate in
	 * every event in a simultaneity set.
	 */
	static void updateSimultaneitySet(int ego, int time) {
		EventRecord lastEvent, thisEvent;
		thisEvent =
			(EventRecord) Interact.person[ego].serialEvents.elementAt(time);
		for (int alter = 0; alter < Interact.numberOfInteractants; alter++) {
			if (history[ego][alter][time].simultaneityTransient[0] == Interact.MISSING_VALUE) {
				// alter is a non-participant in the event at this time.
				if (time == 0) {
					continue;
				} // No action needed for non-participant in first event.
				lastEvent =
					(EventRecord) Interact.person[ego].serialEvents
						.elementAt(time - 1);
				// Move alter's last transient up to this event.
				if ((history[ego][alter][time - 1].nSimultaneousEvents > 1)
					&& (thisEvent.simultaneityCode == lastEvent.simultaneityCode)) {
					// Bring alter forward and reduce size of averaging set for
					// alter.
					for (int epa = 0; epa < 3; epa++) {
						history[ego][alter][time].simultaneityTransient[epa] =
							history[ego][alter][time - 1].simultaneityTransient[epa];
						history[ego][alter][time].simultaneityReidentification[epa] =
							history[ego][alter][time - 1].simultaneityReidentification[epa];
					}
					history[ego][alter][time].nAveragedEvents =
						history[ego][alter][time - 1].nAveragedEvents;
				}
				if (history[ego][alter][time].simultaneitySetIndex == history[ego][alter][time].nSimultaneousEvents) {
					// Compute fundamental at last of simultaneity set.
					computeFundamental(ego, alter, time);
				}
			}
		} // end for/alter.
	} // End updateSimultaneitySet.

	/**
	 * Sentiment learning: Create special interactant identities whose EPA
	 * profiles change during interaction.
	 */
	static void commuteMutatorIdentities(boolean insertMutators) {
		int lineNumber = 0;
		if (insertMutators) {
			for (int i = 1; i < Interact.numberOfInteractants + 1; i++) {
				for (int j = 1; j < Interact.numberOfInteractants + 1; j++) {
					Data mutatorEntry =
						DefineSituationCard.newDataEntry("0 0 0");
					mutatorEntry.word =
						"Mutator_" + Integer.toString(i) + "_"
							+ Integer.toString(j);
					Interact.identities.addElement(mutatorEntry);
				}
			}
		} else { // Remove mutators.
			while (lineNumber >= 0) {
				lineNumber = Interact.identities.getIndex("Mutator_");
				if (lineNumber >= 0) {
					Interact.identities.removeElementAt(lineNumber);
				}
			}
		}
	} // End commuteMutatorIdentities.

}
