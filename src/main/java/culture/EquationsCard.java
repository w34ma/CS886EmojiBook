package culture;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ResourceBundle;
import java.util.Locale;

/**
 * Page for displaying and inputting equations.
 * 
 * To add a new set of equations: 1. Add a new en_US_... locale in Interact and
 * a Coefficients class/ListResourceBundle for that locale with the
 * coefficients. 2. Expand "equationStudies" in InteractTexts with a name for
 * the new equations. 3. Expand if (tempLocale==...) in Controls to properly
 * show the active equations on the equationSet chooser on this page. 4. Expand
 * the switch statement in loadNewEquationSet() below.
 */
public class EquationsCard extends Panel {

	static final int MAX_TERMS = 13 * 65; // 13 cols, 65 rows.

	static MathModel adHocEquations;

	String[] equationTextLines;

	Choice equationStudies = new Choice();

	Choice equationSet = new Choice();

	TextArea equationDisplay = new TextArea();

	Panel choicePlace = new Panel();

	BorderLayout borderLayout1 = new BorderLayout(5, 5);

	Button importEquationCoefficientsButton = new Button();

	public EquationsCard() { // Constructor method.
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		this.add(choicePlace, BorderLayout.NORTH);
		this.add(equationDisplay, BorderLayout.CENTER);

		choicePlace.add(equationStudies, null);
		for (int i = 0; i < Interact.equationStudyLines.length; i++) {
			equationStudies.add(Interact.equationStudyLines[i]);
		}
		choicePlace.add(equationSet, null);
		for (int i = 0; i < Interact.equationTextLines.length; i++) {
			equationSet.add(Interact.equationTextLines[i]);
		}
		choicePlace.add(importEquationCoefficientsButton, null);
		importEquationCoefficientsButton.setLabel(Interact.InteractText
			.getString("importEquationCoefficientsButton"));

		Font fixedWidth = new Font("Monospaced", Font.PLAIN, 10);
		equationDisplay.setFont(fixedWidth);
		equationDisplay.setText("");

		equationStudies.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				equationStudies_itemStateChanged(e);
			}
		});
		equationSet.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				equationSet_itemStateChanged(e);
			}
		});
		importEquationCoefficientsButton
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					importEquationCoefficientsButton_actionPerformed(e);
				}
			});
	} // End constructor method.

	void equationStudies_itemStateChanged(ItemEvent e) {
		loadNewEquationSet();
	}

	void equationSet_itemStateChanged(ItemEvent e) {
		showEquations();
	}

	public void loadNewEquationSet() {
		/* Clear the equation display and the subset chooser. */
		equationDisplay.setText("");
		/* Change locale to that associated with unique coefficients. */
		Locale workLocale = Interact.America;
		ResourceBundle Coefficients;
		int studyIndex = equationStudies.getSelectedIndex();
		switch (studyIndex) {
		case 0:
			Interact.currentLocale = Interact.America;
			break;
		case 1:
			workLocale = Interact.Japan;
			break;
		case 2:
			workLocale = Interact.Canada01;
			break;
		case 3:
			workLocale = Interact.China;
			break;
		case 4:
			workLocale = Interact.Germany;
			break;
		}
		/*
		 * This procedure doesn't work properly when dealing with
		 * foreign-language locales (even though Interact.inputCulture() works
		 * fine). Thus equations for the Deutsch locale are read from an English
		 * version of culture.Coefficients.
		 */
		Coefficients =
			ResourceBundle.getBundle("culture.Coefficients", workLocale);
		// Set up the equations.
		Interact.maleabo =
			new MathModel("ABOmale",
				(String[]) Coefficients.getStringArray("ABOmale"));
		Interact.femaleabo =
			new MathModel("ABOfemale",
				(String[]) Coefficients.getStringArray("ABOfemale"));
		Interact.maleaboS =
			new MathModel("ABOSmale",
				(String[]) Coefficients.getStringArray("ABOSmale"));
		Interact.femaleaboS =
			new MathModel("ABOSfemale",
				(String[]) Coefficients.getStringArray("ABOSfemale"));
		Interact.maleSelf =
			new MathModel("ABmale",
				(String[]) Coefficients.getStringArray("ABmale"));
		Interact.femaleSelf =
			new MathModel("ABfemale",
				(String[]) Coefficients.getStringArray("ABfemale"));
		Interact.maleTrait =
			new MathModel("TraitMale",
				(String[]) Coefficients.getStringArray("TraitMale"));
		Interact.femaleTrait =
			new MathModel("TraitFemale",
				(String[]) Coefficients.getStringArray("TraitFemale"));
		Interact.maleEmotion =
			new MathModel("EmotionMale",
				(String[]) Coefficients.getStringArray("EmotionMale"));
		Interact.femaleEmotion =
			new MathModel("EmotionFemale",
				(String[]) Coefficients.getStringArray("EmotionFemale"));
		showEquations();
	} // End loadNewEquationSet.

	public void showEquations() {
		String theLine = equationSet.getSelectedItem(); // Read the menu.
		String theTable = "";
		MathModel eqs = Interact.maleabo; // Arbitrary initialization.
		equationDisplay.setText("");
		if (theLine == Interact.equationTextLines[0]) {
			return; // Interact.equationTextLines[0] is an instruction.
		} else if (theLine == Interact.equationTextLines[1]) {
			eqs = Interact.maleabo;
		} else if (theLine == Interact.equationTextLines[2]) {
			eqs = Interact.femaleabo;
		} else if (theLine == Interact.equationTextLines[3]) {
			eqs = Interact.maleaboS;
		} else if (theLine == Interact.equationTextLines[4]) {
			eqs = Interact.femaleaboS;
		} else if (theLine == Interact.equationTextLines[5]) {
			eqs = Interact.maleSelf;
		} else if (theLine == Interact.equationTextLines[6]) {
			eqs = Interact.femaleSelf;
		} else if (theLine == Interact.equationTextLines[7]) {
			eqs = Interact.maleTrait;
		} else if (theLine == Interact.equationTextLines[8]) {
			eqs = Interact.femaleTrait;
		} else if (theLine == Interact.equationTextLines[9]) {
			eqs = Interact.maleEmotion;
		} else if (theLine == Interact.equationTextLines[10]) {
			eqs = Interact.femaleEmotion;
		}
		int coefLength = eqs.coef.length;
		int coefWidth = eqs.term[0].length;
		for (int i = 0; i < coefLength; i++) {
			theTable = theTable + "Z";
			for (int j = 0; j < coefWidth; j++) {
				if (eqs.term[i][j]) {
					theTable = theTable + "1";
				} else
					theTable = theTable + "0";
			}
			for (int j = 0; j < eqs.coef[0].length; j++) {
				theTable =
					theTable + Interact.formatLocaleDecimal(eqs.coef[i][j]);
			}
			theTable =
				theTable + Interact.InteractText.getString("paragraphCommand");
		}
		this.equationDisplay.setText(theTable);
	} // End showEquations.

	void importEquationCoefficientsButton_actionPerformed(ActionEvent e) {
		if (equationSet.getSelectedIndex() == 0) { return; }
		String equationInput[];
		StringReader equationsText =
			new StringReader(equationDisplay.getText());
		StreamTokenizer equationsInput = new StreamTokenizer(equationsText);
		equationsInput.resetSyntax();
		equationsInput.wordChars('-', '.');
		equationsInput.wordChars('0', '9');
		equationsInput.wordChars('Z', 'Z');
		equationsInput.whitespaceChars(0, ' ');
		equationsInput.eolIsSignificant(false);
		// Set line length for error checking.
		int linelength;
		int menuSelection = equationSet.getSelectedIndex();
		if (menuSelection <= 2) {
			linelength = 10; // ABO equation.
		} else if (menuSelection <= 4) {
			linelength = 13; // ABOS equation.
		} else if (menuSelection <= 6) {
			linelength = 7; // AB equation;
		} else {
			linelength = 4; // Modifier-Identity equation.
		}
		// Loop thru lines of text.
		String tokenArray[] = new String[MAX_TERMS];
		int tokenPosition = 0;
		int errorType = -1;
		int tokenType;
		try {
			out: while (true) {
				// Get word or parameter.
				tokenType = equationsInput.nextToken(); 
				routing: switch (tokenType) {
				case StreamTokenizer.TT_EOF:
					// Done.
					break out;
				case StreamTokenizer.TT_EOL:
					break routing;
				case StreamTokenizer.TT_WORD: 
					// Done reading one word or number.
					tokenArray[tokenPosition++] = equationsInput.sval;
					if (tokenPosition % linelength != 1) {
						break routing;
					} else if (equationsInput.sval.startsWith("Z")) {
						break routing;
					} else {
						errorType = 2;
						break out;
					}
				case StreamTokenizer.TT_NUMBER: 
					// Unused; coefficients made into words.
					tokenArray[tokenPosition++] = equationsInput.sval;
					System.out.println(equationsInput.sval);
					break routing;
				default:
					errorType = 1; // Unrecognized token.
					break out;
				} // End switch (tokenType).
			} // End out: while (true).
			if (errorType > -1) {
				int line = 1 + ((tokenPosition - 1) / linelength);
				int lastline = line - 1;
				System.out.println("Error # " + errorType);
				Cancel_OK_Dialog importProblem =
					new Cancel_OK_Dialog((Frame) Interact.appletFrame,
						Interact.InteractText.getString("error"),
						Interact.InteractText
							.getString("equationImportProblem")
							+ lastline
							+ "-" + line,
						Interact.InteractText.getString("ok"), null);
				importProblem.setVisible(true);
				return;
			}
		} // End try.
		catch (IOException err) {
			System.out.println("Bad data 1"
				+ Interact.InteractText.getString("paragraphCommand"));
		}
		int numberOfTerms;
		for (numberOfTerms = 0; numberOfTerms < MAX_TERMS; numberOfTerms++) {
			if (tokenArray[numberOfTerms] == null) {
				break;
			}
		}
		if (numberOfTerms == MAX_TERMS) {
			System.out.println("Too many terms and coefficients."
				+ Interact.InteractText.getString("paragraphCommand"));
		} else {
			equationInput = new String[numberOfTerms];
			for (int i = 0; i < numberOfTerms; i++) {
				equationInput[i] = tokenArray[i];
			}
			try {
				adHocEquations =
					new MathModel("Equation Import", equationInput);
			} catch (Exception ex) {
				System.out.println("Bad data 2"
					+ Interact.InteractText.getString("paragraphCommand"));
			}

			switch (equationSet.getSelectedIndex()) {
			case 1:
				Interact.maleabo = adHocEquations;
				break;
			case 2:
				Interact.femaleabo = adHocEquations;
				break;
			case 3:
				Interact.maleaboS = adHocEquations;
				break;
			case 4:
				Interact.femaleaboS = adHocEquations;
				break;
			case 5:
				Interact.maleSelf = adHocEquations;
				break;
			case 6:
				Interact.femaleSelf = adHocEquations;
				break;
			case 7:
				Interact.maleTrait = adHocEquations;
				break;
			case 8:
				Interact.femaleTrait = adHocEquations;
				break;
			case 9:
				Interact.maleEmotion = adHocEquations;
				break;
			case 10:
				Interact.femaleEmotion = adHocEquations;
				break;
			default:
				break;
			}
		}
		showEquations();
		Cancel_OK_Dialog success =
			new Cancel_OK_Dialog((Frame) Interact.appletFrame, "",
				Interact.InteractText.getString("done"),
				Interact.InteractText.getString("ok"), null);
		success.setVisible(true);
	} // End importEquationCoefficientsButton_actionPerformed.

} // End class EquationsCard.

