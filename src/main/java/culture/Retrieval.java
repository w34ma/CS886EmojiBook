package culture;

/** Record of a word, its distance from a search profile, and its EPA. */

public class Retrieval {

	public String word;

	double D;

	double[] profile;

	// Chart highlighting: 0 = none; 1 = selected; -1 = complement.
	int highlight;

	public Retrieval(double theD, String theWord, double[] theProfile) {
		// Constructor method.
		D = theD;
		word = theWord;
		profile = new double[3];
		profile = theProfile;
		highlight = 0;
	}

}