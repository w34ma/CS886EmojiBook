package culture;

import java.util.Vector;

/**
 * Implementation of mathematical model in D. R. Heise, 2007. <i>Expressive
 * Order: Confirming Sentiments in Social Actions</i>. New York: Springer.
 */
public class MathModel {

	/** Iteration start point when solving for actor. */
	static final int FIND_ACTOR = 0;

	/** Iteration start point when solving for behavior. */
	public static final int FIND_BEHAVIOR = 3;

	/** Iteration start point when solving for object. */
	static final int FIND_OBJECT = 6;

	/** Flag for wanting object person's optimal response. */
	static final int FIND_RESPONSE = -1;

	/** Term definitions. [Number-of-terms] [Number-of-1st-order-varaibles]. */
	boolean term[][];

	/** Coefficent values. [Number-of-terms] [Number-of-outcomes]. */
	double coef[][];

	/** The <b>H</b> matrix; used in <code>optimalProfile</code>. */
	double[][] H;

	/** The <b>h</b> vector; used in <code>optimalProfile</code>. */
	double[] h;

	/** Constructor method: for inputing a set of equations. */
	MathModel(String nameOfSet, String[] coefficientsText) {
		Vector equationLines = new Vector();
		Vector oneLine = new Vector();
		int firstLineLength = 0;
		int numberOfTerms = coefficientsText.length;
		boolean passedTermZero = false;
		// Parse the one-dimensional string array
		// taken from an input resource bundle.
		for (int i = 0; i < numberOfTerms; i++) {
			// Examine a term in the string array.
			if (coefficientsText[i].startsWith("Z")) {
				// Z marks a boolean term, and start of line.
				if (passedTermZero) { // Get past first line.
					if (firstLineLength == 0) { // Define firstLineLength.
						firstLineLength = i;
					} else {
						// Test for correct number of terms in prior line.
						if ((i % firstLineLength) != 0) { // Use modulo.
							System.err.println(Interact.InteractText
								.getString("missingTerm")
								+ nameOfSet
								+ " "
								+ (i + 1));
						}
					}
					equationLines.addElement(oneLine.clone());
					// Copy values in prior line into the array vector.
					oneLine.removeAllElements(); // Clear the line vector.
				}
				passedTermZero = true;
				// Include the Z boolean term in the line.
				oneLine.addElement(coefficientsText[i]); 
			} else {
				// Numerical term.
				// Include the coefficient in the line.
				oneLine.addElement(coefficientsText[i]);
				if (i == (numberOfTerms - 1)) { // Handle the final line.
					// Use modulo on i+1.
					if (((i + 1) % firstLineLength) != 0) { 
						System.err.println(Interact.InteractText
							.getString("missingTerm")
							+ nameOfSet + " " + (i + 1));
					}
					// Copy values in final line into the array vector.
					equationLines.addElement(oneLine.clone());
				}
			}
		} // End for.
		// Make the arrays used in calculations.
		int cols, termCols, rows;
		String termIdentifier;
		rows = equationLines.size();
		oneLine = (Vector) equationLines.firstElement();
		// Number of coefficient columns = all columns - boolean column.
		cols = oneLine.size() - 1;
		if (cols == 3) {
			// Modifier-identity equations have six 1st-order terms.
			termCols = 6;
		} else {
			// Sentence equations have as many 1st-order terms
			// as predicted values.
			termCols = cols;
		}
		// Create the boolean array defining predictor-variables.
		term = new boolean[rows][termCols];
		// Create the array of decimal coefficients.
		coef = new double[rows][cols];
		for (int i = 0; i < rows; i++) {
			oneLine = (Vector) equationLines.elementAt(i);
			// Parse the Z term to fill the boolean array.
			termIdentifier = (String) oneLine.firstElement();
			for (int j = 0; j < termCols; j++) {
				if (termIdentifier.charAt(j + 1) == '1') {
					term[i][j] = true;
				} else {
					term[i][j] = false;
				}
			}
			// Fill the decimal array.
			for (int j = 0; j < cols; j++) {
				// Convert each number string to a decimal number,
				// using locale conventions.
				Interact.startingCharacter.setIndex(0);
				coef[i][j] =
					Interact.readLocaleDecimal((String) oneLine
						.elementAt(j + 1));
			}
		} // End for rows.
	} // End of Equations constructor.

	/** Compute EPA profile for modifier-identity combination. */
	static double[] amalgamate(
		boolean male, int modifierIndex, int identityIndex) {
		MathModel eqSet;
		double[] outcome = { 0, 0, 0 };
		double[] modEPA, identEPA;
		double product;
		if (male) {
			modEPA =
				((Data) Interact.modifiers.elementAt(modifierIndex)).maleEPA;
			identEPA =
				((Data) Interact.identities.elementAt(identityIndex)).maleEPA;
			if (((Data) Interact.modifiers.elementAt(modifierIndex)).divisionConceptGate[0]) {
				eqSet = Interact.maleEmotion;
			} else {
				eqSet = Interact.maleTrait;
			}
		} else {
			modEPA =
				((Data) Interact.modifiers.elementAt(modifierIndex)).femaleEPA;
			identEPA =
				((Data) Interact.identities.elementAt(identityIndex)).femaleEPA;
			if (((Data) Interact.modifiers.elementAt(modifierIndex)).divisionConceptGate[0]) {
				eqSet = Interact.femaleEmotion;
			} else {
				eqSet = Interact.femaleTrait;
			}
		}
		for (int j = 0; j < Interact.N_DIMENSIONS; j++) {
			// for each amalgamation equation, E,P, or A
			outcome[j] = 0;
			for (int i = 0; i < eqSet.coef.length; i++) {
				// for each row of the coefficient matrix
				product = eqSet.coef[i][j];
				// set "product" equal to the coefficient
				// in ith row, jth equation
				for (int jj = 0; jj < 6; jj++) {
					// for each first-order predictor
					if (eqSet.term[i][jj]) {
						// if the predictor is in this/ term include it
						if (jj < 3) {
							// if the predictor is part of the modifier profile
							product = product * modEPA[jj];
							// multiply coefficient and modifier value
						} else {
							// or if the predictor is part
							// of the identity profile
							product = product * identEPA[jj - 3];
							// multiply coefficient and identity value
						}
					}
				} // End for jj.
				// Have multiplied the coefficient times modifier and/or
				// identity predictors
				outcome[j] = outcome[j] + product;
				// add this result to the total for this equation
			} // End for i; start term on next row
		} // End for j; start next equation
		return outcome;
	} // End amalgamate.

	/**
	 * Compute transients and deflection resulting from an event, and distribute
	 * results to interactants and history.
	 */
	public void impressions(EventRecord theEvent) {
		int time =
			Interact.person[theEvent.vao[0]].serialEvents.indexOf(theEvent);
		int numberOfTerms, numberOfOutcomes, epa, slot, col;
		double[] t, tau;
		double deflection, temp;
		numberOfTerms = this.term.length;
		numberOfOutcomes = this.term[0].length;
		// double[] epaProfile = new double[3];

		// Assemble vector t.
		t = new double[numberOfTerms];
		t[0] = 1; // Constant.
		// First-order variables next.
		for (slot = 0; slot < (numberOfOutcomes / 3); slot++) {
			for (epa = 0; epa < 3; epa++) {
				t[(3 * slot) + epa + 1] = theEvent.abosTransientsIn[slot][epa];
			}
		}
		// Interactions next.
		for (int i = numberOfOutcomes + 1; i < numberOfTerms; i++) {
			t[i] = 1;
			for (col = 0; col < numberOfOutcomes; col++) {
				if (this.term[i][col]) {
					t[i] = t[i] * t[col + 1];
				}
			}
		}
		// Now compute outcome impressions.
		tau = new double[numberOfOutcomes];
		for (col = 0; col < numberOfOutcomes; col++) {
			tau[col] = 0;
			for (int i = 0; i < numberOfTerms; i++) {
				tau[col] = tau[col] + t[i] * this.coef[i][col];
			}
		}
		// Distribute results into event record and compute deflection.
		deflection = 0;
		for (col = 0; col < numberOfOutcomes; col++) {
			slot = (int) col / 3;
			epa = col - (3 * slot);
			theEvent.abosTransientsOut[slot][epa] = tau[col];
			temp =
				theEvent.abosFundamentals[slot][epa]
				                                - theEvent.abosTransientsOut[slot][epa];
			deflection = deflection + temp * temp;
		}
		theEvent.deflection = deflection;
		/*
		// Distribute results for actor and object into history array.
		for (int i = 0; i < 3; i++) {
			epaProfile[i] = theEvent.abosTransientsOut[0][i];
		}
		SentimentChange.incrementTransientsHistory(theEvent.vao[0],
			theEvent.vao[1], epaProfile, time);
		for (int i = 0; i < 3; i++) {
			epaProfile[i] = theEvent.abosTransientsOut[2][i];
		}
		SentimentChange.incrementTransientsHistory(theEvent.vao[0],
			theEvent.vao[2], epaProfile, time);
		*/
	} // End impressions.

	/** Compute EPA profile for an attribution or emotion. */
	public double[] computeModifier(double[] nounEPA, double[] outcomeEPA) {
		double[][] Pmatrix = new double[3][3];
		double[][] Rmatrix = new double[3][3];
		double[] tempVector = new double[3];
		double[] returnVector = new double[3];
		// Make P and R.
		for (int i = 0; i < 3; i++) {
			returnVector[i] = Interact.MISSING_VALUE;
			for (int j = 0; j < 3; j++) {
				Pmatrix[i][j] = this.coef[j + 1][i];
				Rmatrix[i][j] = this.coef[j + 4][i];
			}
		}
		// Make Ir{diag}s times Qs.
		// Coefficient matrix for Re interactions.
		double[][] Qe = new double[3][3]; 
		// Coefficient matrix for Rp interactions.
		double[][] Qp = new double[3][3]; 
		// Coefficient matrix for Ra interactions.
		double[][] Qa = new double[3][3]; 
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Qe[i][j] = 0;
				Qp[i][j] = 0;
				Qa[i][j] = 0;
			}
		}
		int modifierIndex, nounIndex;
		for (int coefRow = 7; coefRow < this.term.length; coefRow++) {
			// For rows of term/coef matricies past 1st order terms.
			modifierIndex = nounIndex = -1;
			for (int i = 0; i < 6; i++) {
				// Go through elements of the zero/one pattern.
				if (this.term[coefRow][i]) {
					if (i < 3) {
						modifierIndex = i;
						// Index EPA of the modifier component of an
						// interaction.
					} else {
						nounIndex = i - 3;
						// Index EPA of the noun component of an interaction.
					}
				}
			} // Finished checking the pattern.
			if ((modifierIndex < 0) || (nounIndex < 0)) {
				// Error check.
				System.err.println(Interact.InteractText
					.getString("modifierIdentityError"));
				return returnVector;
			}
			for (int equation = 0; equation < 3; equation++) {
				// Collect the Q matricies and multiply them
				// by the diagonals with noun EPA.
				switch (nounIndex) {
				case 0:
					// Compute IRe*Qe. IRe is a diagonal matrix with
					// Re in all diagonal cells.
					Qe[equation][modifierIndex] =
						coef[coefRow][equation] * nounEPA[0];
					break;
				case 1:
					// Compute IRp*Qp. IRp is a diagonal matrix with
					// Rp in all diagonal cells.
					Qp[equation][modifierIndex] =
						coef[coefRow][equation] * nounEPA[1];
					break;
				case 2:
					// Compute IRa*Qa. IRa is a diagonal matrix with
					// Ra in all diagonal cells.
					Qa[equation][modifierIndex] =
						coef[coefRow][equation] * nounEPA[2];
				}
			}
		} // End of going through interaction terms in coef array.
		/*
		 * Now compute the sum, P + IRe*Qe + IRp*Qp + IRa*Qa. P is parallel to E
		 * in emotion deriviations.
		 */
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Pmatrix[i][j] = Pmatrix[i][j] + Qe[i][j] + Qp[i][j] + Qa[i][j];
			}
		}
		// Make inv(P + IRe*Qe + IRp*Qp + IRa*Qa).
		invertMatrix(Pmatrix);
		// Make tau-Rr-d. tau is person's reidentification profile (attribution)
		// or transient (emotion).
		for (int i = 0; i < 3; i++) {
			tempVector[i] = outcomeEPA[i] - this.coef[0][i];
			// d, or the vector of equation constants.
			for (int j = 0; j < 3; j++) {
				tempVector[i] = tempVector[i] - Rmatrix[i][j] * nounEPA[j];
			}
		}
		// Multiply inv(P + IRe*Qe + IRp*Qp + IRa*Qa)*(tau-Rr-d)
		for (int i = 0; i < 3; i++) {
			returnVector[i] = 0;
			for (int j = 0; j < 3; j++) {
				returnVector[i] =
					returnVector[i] + Pmatrix[i][j] * tempVector[j];
			}
		}
		return returnVector;
	} // End computeModifier.

	/**
	 * Compute EPA profile for problem at hand: FIND_ACTOR = 0; FIND_BEHAVIOR =
	 * 3; FIND_OBJECT = 6; FIND_RESPONSE = -1;
	 */
	public double[] optimalProfile(int desiredSolution, EventRecord thisEvent) {
		boolean[][] selector;
		boolean[] G;
		double[] I_diagonal;
		double[][] squareMatrix;
		double[] quadraticVector, theSolution;
		int col, target;
		boolean zeroRowInSelector;
		int numberOfTerms = this.coef.length;
		int range = this.coef[0].length; // 6, 9 or 12.
		int numberOfSlots = range / 3;
		int fullSize = range + numberOfTerms;
		boolean originalActorObject = true;
		boolean findingNextActorBehavior;

		if (desiredSolution == FIND_RESPONSE) {
			// Find object person's optimal response.
			desiredSolution = FIND_BEHAVIOR;
			originalActorObject = false;
		}
		// Determine whether to use output transients from current event in
		// order to solve for next event.
		findingNextActorBehavior = true; // Use output transients.
		if ((Interact.analyzeEvents.presentFutureMenu.getSelectedIndex() == 0)
				// presentFutureMenu is set to find current behavior
				| (SentimentChange.changingSentiments)) {
			// get present behavior in order to predict observed behavior
			findingNextActorBehavior = false; // so use input transients.
		}
		if (desiredSolution != FIND_BEHAVIOR) {
			findingNextActorBehavior = false;
			// Don't use output transients in reidentifications.
		}
		if ((Interact.controls.operationsChoice.getSelectedIndex() 
				== Interact.controls.TEST_EMOTIONS) |
				(Interact.controls.operationsChoice.getSelectedIndex() 
						== Interact.controls.BUILD_INTERACTION)){	
			findingNextActorBehavior = false;
			// Don't use output transients on Test Emotions form
			// or on constructing interaction form.
		}
		// Compose selector array;
		selector = new boolean[Interact.N_DIMENSIONS][fullSize];
		G = new boolean[fullSize];
		for (int j = 0; j < fullSize; j++) {
			G[j] = false;
			for (int i = 0; i < Interact.N_DIMENSIONS; i++) {
				selector[i][j] = false;
			}
		}
		for (int i = 0; i < Interact.N_DIMENSIONS; i++) {
			// Take care of fundamental portion.
			target = desiredSolution + i;
			selector[i][target] = true;
		}
		for (int j = 0; j < range; j++) {
			if ((j < desiredSolution) | (j > (desiredSolution + 2))) {
				G[j] = true;
			}
		}
		for (int j = 0; j < numberOfTerms; j++) {
			// Now the transient portion.
			col = range + j;
			// Continue from after fundamental portion.
			zeroRowInSelector = true;
			for (int i = 0; i < Interact.N_DIMENSIONS; i++) {
				target = desiredSolution + i;
				selector[i][col] = this.term[j][target];
				if ((selector[i][col]) & zeroRowInSelector) {
					zeroRowInSelector = false;
				}
			}
			if (zeroRowInSelector) { // G vector is one.
				G[col] = true;
			}
		}
		// Compute I_diagonal.
		I_diagonal = new double[fullSize];
		for (int i = 0; i < fullSize; i++) {
			I_diagonal[i] = 1;
		}
		if (originalActorObject) {
			// Do ideal behavior of actor.
			for (int slot = 0; slot < numberOfSlots; slot++) { 
				// slot: actor, behavior, object
				for (int epa = 0; epa < Interact.N_DIMENSIONS; epa++) {
					col = (3 * slot) + epa; 
					// Index for fundamental section of I_diagonal.
					if ((col < desiredSolution) | (col > (desiredSolution + 2))) {
						// Excluding unknown terms.
						I_diagonal[col] = thisEvent.abosFundamentals[slot][epa];
						// Now multiply relevant transients.
						for (int i = range + 1; i < fullSize; i++) { 
							// Leave 1 for equation constant.
							if (this.term[i - range][col]) {
								// Implement presentFutureMenu for Actor
								// behavior.
								if (findingNextActorBehavior) {
									I_diagonal[i] =
										I_diagonal[i]
										           * thisEvent.abosTransientsOut[slot][epa];
								} else {
									I_diagonal[i] =
										I_diagonal[i]
										           * thisEvent.abosTransientsIn[slot][epa];
								}
							}
						}
					}
				}
			}
		} else {
			// Do ideal response of object in dyad.
			for (int slot = 0; slot < numberOfSlots; slot++) {
				if (slot == 0) {
					target = 2;
				} else if (slot == 2) {
					target = 0;
				} else {
					target = slot;
				}
				for (int epa = 0; epa < Interact.N_DIMENSIONS; epa++) {
					col = (3 * slot) + epa;
					if ((col < desiredSolution) | (col > (desiredSolution + 2))) {
						// Excluding unknown terms.
						I_diagonal[col] =
							thisEvent.abosFundamentals[target][epa];
						// Now multiply relevant transients.
						for (int i = range + 1; i < fullSize; i++) { 
							// Skip equation constant.
							if (this.term[i - range][col]) {
								I_diagonal[i] =
									I_diagonal[i]
									           * thisEvent.abosTransientsOut[target][epa];
							}
						}
					}
				}
			} // end for/slot
		} // end else/do ideal response

		// Now compute both H and h.
		this.composeH();
		// Now compose squareMatrix and multiplier vector.
		squareMatrix = new double[3][3];
		quadraticVector = new double[3];
		for (int i = 0; i < 3; i++) {
			quadraticVector[i] = 0;
			for (int j = 0; j < 3; j++) {
				squareMatrix[i][j] = 0;
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < fullSize; k++) {
				if (selector[i][k]) {
					// Only make computations when S is not zero.
					for (int kk = 0; kk < fullSize; kk++) {
						for (int j = 0; j < 3; j++) {
							if (selector[j][kk]) {
								// Only make computations when S is not zero.
								squareMatrix[i][j] =
									squareMatrix[i][j] + H[k][kk]
									                          * I_diagonal[k] * I_diagonal[kk];
							}
						}
						if (G[kk]) { 
							// Only make computations when G is not zero.
							quadraticVector[i] =
								quadraticVector[i] + H[k][kk] * I_diagonal[k]
								                                           * I_diagonal[kk];
						}
					} // End for kk
					// In case this is inference from emotions:
					quadraticVector[i] =
						quadraticVector[i] + (h[k] / 2) * I_diagonal[k];
				} // End if selector i,k
			} // End for k
		} // End for i

		// Obtain the solution profile.
		invertMatrix(squareMatrix);
		// Multiply inverted matrix by quadratic vector.
		theSolution = new double[3];
		for (int i = 0; i < 3; i++) {
			theSolution[i] = 0;
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				theSolution[i] =
					theSolution[i] - squareMatrix[i][j] * quadraticVector[j];
			}
		}
		return theSolution;
	} // End optimalProfile.

	/**
	 * Composes large matrix <b>H</b> and also <b>h</b> vector used in optimal
	 * profile.
	 */
	private void composeH() {
		// Also makes h at end.
		int row, col;
		int numberOfTerms = this.coef.length;
		int numberOfEquations = this.coef[0].length; // 6, 9 or 12.
		int fullSize = numberOfEquations + numberOfTerms;
		double[] actorEmotionVector = new double[3];
		double[] objectEmotionVector = new double[3];
		// Zero H and h for later summations.
		H = new double[fullSize][fullSize];
		h = new double[fullSize];
		for (int i = 0; i < fullSize; i++) {
			h[i] = 0;
			for (int j = 0; j < fullSize; j++) {
				H[i][j] = 0;
			}
		}
		// Fill upper-left quadrant of H using actor and object sub-matrices of H.
		double[][] actorPhi = new double[3][3];
		double[][] objectPhi = new double[3][3];
		if (ControlsMenuBar.inferringFromEmotion) {
			// Emotion of actor/object included in calculations only if an emotion is selected.
			boolean maintainActorEmotion =
				(Interact.studyEmotions.actorEmotionList.getSelectedIndex() > -1);
			boolean maintainObjectEmotion =
				(Interact.studyEmotions.objectEmotionList.getSelectedIndex() > -1);
			// Get emotion data from Test Emotion Card.
			actorEmotionVector =
				Interact.readProfile(Interact.studyEmotions.actorEmotionEPA.getText());
			objectEmotionVector =
				Interact.readProfile(Interact.studyEmotions.objectEmotionEPA.getText());
			// Put (Phi-transpose)(Phi) into H for actor and object.
			if (maintainActorEmotion) { 
				actorPhi = makeCapPhi(actorEmotionVector);
			} else {
				actorPhi = identityMatrix(3);
			}
			if (maintainObjectEmotion) { 
				objectPhi = makeCapPhi(objectEmotionVector);
			} else {
				objectPhi = identityMatrix(3);
			}
			// Insert actor and object sub-Phis into H and an I sub-matrix for behavior.
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					for (int k = 0; k < 3; k++) {
						// Actor-Phi-transpose * Actor-Phi
						H[i][j] = H[i][j] + actorPhi[i][k]  * actorPhi[j][k]; 
						//THE OLD ERROR: H[i][j] = H[i][j] + actorPhi[k][i]  * actorPhi[k][j]; 
						// Put one in the diagonal for behavior part.
						H[i + 3][i + 3] = 1; 
						// Object-Phi-transpose * Object-Phi
						H[i + 6][j + 6] =
							H[i + 6][j + 6] + objectPhi[i][k] * objectPhi[j][k]; 
					} // End for k.
				} // End for j.
			} // End for i.
		} else {
			// Not inferring from emotion, so:
			// Make upper-left quadrant of H an identity matrix and make sub-Phi's
			// into identity matricies.
			for (int i = 0; i < numberOfEquations; i++) {
				H[i][i] = 1;
			}
			actorPhi = identityMatrix(3);
			objectPhi = identityMatrix(3);
		} // End of filling upper left quadrant of H.
		// Now fill the rest of H.
		// Make a full-size Phi.
		double[][] fullPhi = new double[12][12];
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if ((i < 3) && (j < 3)) {
					fullPhi[i][j] = actorPhi[i][j];
				} else {
					if (((i > 5) & (i < 9)) & ((j > 5) & (j < 9))) {
						fullPhi[i][j] = objectPhi[i - 6][j - 6];
					} else {
						if (i == j) {
							fullPhi[i][j] = 1;
						} else {
							fullPhi[i][j] = 0;
						}
					}
				}
			}
		}
		// Get M and convert to -M'.
		double[][] minusMtranspose =
			new double[numberOfEquations][numberOfTerms];
		for (int j = 0; j < numberOfTerms; j++) {
			for (int i = 0; i < numberOfEquations; i++) {
				minusMtranspose[i][j] = -this.coef[j][i];
			}
		}
		// Now put Phi'&-M' in upper-right quadrant.
		for (int i = 0; i < numberOfEquations; i++) {
			for (int j = 0; j < numberOfTerms; j++) {
				col = numberOfEquations + j;
				for (int k = 0; k < numberOfEquations; k++) {
					H[i][col] =
						H[i][col] + fullPhi[k][i] * minusMtranspose[k][j];
				}
			}
		}
		// Fill upper triangle of lower-right quadrant.
		for (int i = 0; i < numberOfTerms; i++) {
			for (int ii = i; ii < numberOfTerms; ii++) {
				for (int j = 0; j < numberOfEquations; j++) {
					row = numberOfEquations + i;
					col = numberOfEquations + ii;
					H[row][col] =
						H[row][col] + this.coef[i][j] * this.coef[ii][j];
				}
			}
		}
		// Symmetrize.
		for (int i = 0; i < fullSize; i++) {
			//necessary?
			for (int j = Math.max(numberOfEquations, i + 1); j < fullSize; j++) {
				//				for (int j = i + 1; j < fullSize; j++) {
				H[j][i] = H[i][j];
			}
		}
		// Now make h.
		if (!ControlsMenuBar.inferringFromEmotion) {
			return; // Leave h a zero vector.
		} else {
			double[] smallPhi =
				makeLittlePhi(actorEmotionVector, objectEmotionVector);
			for (int i = 0; i < numberOfEquations; i++) { 
				// Compute fullPhi-transpose * small-phi.
				for (int k = 0; k < numberOfEquations; k++) {
					h[i] = h[i] + fullPhi[k][i] * smallPhi[k];
				}
			}
			for (int i = 0; i < numberOfTerms; i++) {
				for (int k = 0; k < numberOfEquations; k++) { 
					// Compute -M * small-phi.
					h[i + numberOfEquations] =
						h[i + numberOfEquations] + minusMtranspose[k][i]
						                                              * smallPhi[k];
				}
			}
			for (int i = 0; i < fullSize; i++) {
				h[i] = 2 * h[i];
			}
		}
	} // End composeH.

	/**
	 * Makes R_I_Q component of PHI for actor or object, as in Equation 15.6 of
	 * <i>Expressive Order</i>.
	 */
	private double[][] makeCapPhi(double[] emotionEPA) {
		double[][] Rmatrix = new double[3][3];
		double[][] subPhiMatrix = new double[3][3];
		MathModel emotionEqs;
		if (Interact.studyEmotions.male.getState()) {
			emotionEqs = Interact.maleEmotion;
		} else {
			emotionEqs = Interact.femaleEmotion;
		}
		// Make R-transpose.
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Rmatrix[i][j] = emotionEqs.coef[j + 4][i];
			}
		}
		// Make IE{diag}s times Q-hats.
		// Coefficient matrix for Ee interactions.
		double[][] Qhat_e = new double[3][3]; 
		// Coefficient matrix for Ep interactions.
		double[][] Qhat_p = new double[3][3]; 
		// Coefficient matrix for Ea interactions.
		double[][] Qhat_a = new double[3][3]; 
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Qhat_e[i][j] = 0;
				Qhat_p[i][j] = 0;
				Qhat_a[i][j] = 0;
				subPhiMatrix[i][j] = Interact.MISSING_VALUE;
			}
		}
		int modifierIndex, identityIndex;
		modifierIndex = identityIndex = -1;
		for (int coefRow = 7; coefRow < emotionEqs.term.length; coefRow++) {
			// For each row of term & coef matricies past 1st order terms.
			for (int termCol = 0; termCol < 6; termCol++) {
				// Go through elements of the true/false pattern in this interactions-row.
				if (emotionEqs.term[coefRow][termCol]) {
					if (termCol < 3) {
						// This term indexes the modifier component of the interaction.
						modifierIndex = termCol; // Corresponds to E, P, or A.
					} else {
						// This term indexes the noun component of the interaction.
						identityIndex = termCol - 3; // Corresponds to E, P, or A.
					}
				}
			} // Identified the modifier-noun interaction for this interaction-row.
			if ((modifierIndex < 0) || (identityIndex < 0)) {
				// Error check. There must be an interaction in the row.
				System.err.println(Interact.InteractText
					.getString("modifierIdentityError"));
				return subPhiMatrix;
			}
			for (int equation = 0; equation < 3; equation++) {
				// Use the appropriate coefficient to apply
				// this interaction in each amalgamation equation.
				switch (modifierIndex) { 
				// Add to the Qhat corresponding to this modifier EPA.
				case 0:
					// Compute IEe*Qhat_e where IEe is a diagonal matrix
					// with Ee in all diagonal cells and Qhat has coefs
					// for Ee interactions.
					Qhat_e[equation][identityIndex] =
						emotionEqs.coef[coefRow][equation] * emotionEPA[0];
					break;
				case 1:
					// Compute IEp*Qhat_p where IEp is a diagonal matrix
					// with Ep in all diagonal cells and Qhat has coefs
					// for Ep interactions.
					Qhat_p[equation][identityIndex] =
						emotionEqs.coef[coefRow][equation] * emotionEPA[1];
					break;
				case 2:
					// Compute IEa*Qhat_a where IEa is a diagonal matrix
					// with Ea in all diagonal cells and Qhat has coefs
					// for Ea interactions.
					Qhat_a[equation][identityIndex] =
						emotionEqs.coef[coefRow][equation] * emotionEPA[2];
				}
			}
		} // End of going through interaction-rows in coef array.
		// Obtain upper-left sub-matrix of PHI, in Expressive Order Eq. 15.6
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				subPhiMatrix[i][j] =
					Rmatrix[i][j] + Qhat_e[i][j] + Qhat_p[i][j] + Qhat_a[i][j];
			}
		}
		return subPhiMatrix;
	} // End makeSubCapPhi/

	/** Compute phi as in Equation 15.7 in <i>Expressive Order</i>. */
	private double[] makeLittlePhi(
		double[] actorEmotionEPA, double[] objectEmotionEPA) {
		double[] returnVector = new double[12];
		for (int i = 0; i < 12; i++) {
			returnVector[i] = 0;
		}
		// Return zero phi if not working on feeling-effects form.
		if (!ControlsMenuBar.inferringFromEmotion) { 
			return returnVector;
		}
		MathModel emotionEqs;
		if (Interact.studyEmotions.male.getState()) {
			emotionEqs = Interact.maleEmotion;
		} else {
			emotionEqs = Interact.femaleEmotion;
		}
		// Maintain actor emotion if an emotion is selected for actor.
		boolean maintainActorEmotion =
			(Interact.studyEmotions.actorEmotionList.getSelectedIndex() > -1);
		// Maintain object emotion if an emotion is selected for object.
		boolean maintainObjectEmotion =
			(Interact.studyEmotions.objectEmotionList.getSelectedIndex() > -1);

		for (int equation = 0; equation < 3; equation++) {
			if (maintainActorEmotion) {
				// Add equation constants to actor component of little-phi.
				returnVector[equation] = emotionEqs.coef[0][equation];
			}
			if (maintainObjectEmotion) {
				// Add equation constants to object component of little-phi.
				returnVector[equation + 6] = emotionEqs.coef[0][equation];
			}
			// Now add E-matrix * e-vector. E-matrix after constants row.
			for (int coefRow = 1; coefRow < 4; coefRow++) {
				int epaIndex = coefRow - 1;
				if (maintainActorEmotion) {
					returnVector[equation] = returnVector[equation] + 
					emotionEqs.coef[coefRow][equation] * actorEmotionEPA[epaIndex];
				}
				if (maintainObjectEmotion ) {
					returnVector[equation + 6] =
						returnVector[equation + 6] + 
						emotionEqs.coef[coefRow][equation] * objectEmotionEPA[epaIndex];
				}
			}
		}
		return returnVector;
	} // End makeLittlePhi.

	/** Invert a 3x3 matrix. */
	private void invertMatrix(double[][] theMatrix) {
		double d;
		int r, c;
		r = c = 3;

		for (int i = 0; i < r; i++) {
			d = theMatrix[i][0];
			for (int j = 0; j < c - 1; j++) {
				theMatrix[i][j] = theMatrix[i][j + 1] / d;
			}
			theMatrix[i][c - 1] = 1 / d;
			for (int k = 0; k < r; k++) {
				if (k != i) {
					d = theMatrix[k][0];
					for (int m = 0; m < c - 1; m++) {
						theMatrix[k][m] =
							theMatrix[k][m + 1] - theMatrix[i][m] * d;
					}
					theMatrix[k][c - 1] = -theMatrix[i][c - 1] * d;
				}
			} // End for k.
		} // End for i.
	} // End invertMatrix.

	public double[][]identityMatrix(int order){
		double[][] workMat = new double[order][order];
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++) {
				workMat[i][j] = 0;
				workMat[i][i] = 1;
			}
		}
		return workMat;
	} // End identityMatrix.

} // End class MathModel.
