package culture;

/**
 * A slice of History, giving a viewer's fundamentals and transients for an
 * interactant.
 */
class Situation {

	double[] mutatingFundamental, simultaneityTransient,
			simultaneityReidentification;

	int nAveragedEvents, nSimultaneousEvents, simultaneitySetIndex;

	boolean beginZeroRestart;

	public Situation() {
		mutatingFundamental = new double[3];
		mutatingFundamental[0] = Interact.MISSING_VALUE;
		mutatingFundamental[1] = Interact.MISSING_VALUE;
		mutatingFundamental[2] = Interact.MISSING_VALUE;
		simultaneityTransient = new double[3];
		simultaneityTransient[0] = Interact.MISSING_VALUE;
		simultaneityTransient[1] = Interact.MISSING_VALUE;
		simultaneityTransient[2] = Interact.MISSING_VALUE;
		simultaneityReidentification = new double[3];
		simultaneityReidentification[0] = Interact.MISSING_VALUE;
		simultaneityReidentification[1] = Interact.MISSING_VALUE;
		simultaneityReidentification[2] = Interact.MISSING_VALUE;
		nSimultaneousEvents = 1;
		nAveragedEvents = 0;
		simultaneitySetIndex = 1;
		beginZeroRestart = false;
	}
}
