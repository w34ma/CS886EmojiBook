package culture;

import java.awt.*;
import java.io.*;
import java.awt.event.*;

/** Page for importing or exporting dictionary entries. */

public class ImportExportCard extends Panel {

	BorderLayout borderLayout1 = new BorderLayout();

	TextArea inputBox = new TextArea();

	Panel buttonArea = new Panel();

	CheckboxGroup caseCheckboxGroup = new CheckboxGroup();

	Checkbox identityCheckbox = new Checkbox();

	Checkbox behaviorCheckbox = new Checkbox();

	Checkbox modifierCheckbox = new Checkbox();

	Checkbox settingCheckbox = new Checkbox();

	Button readDataButton = new Button(
		Interact.InteractText.getString("inputButton"));

	Button writeDataButton = new Button(
		Interact.InteractText.getString("exportButton"));

	Panel erasePanel = new Panel();

	Checkbox eraseCheck = new Checkbox();

	public ImportExportCard() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		identityCheckbox.setCheckboxGroup(caseCheckboxGroup);
		behaviorCheckbox.setCheckboxGroup(caseCheckboxGroup);
		modifierCheckbox.setCheckboxGroup(caseCheckboxGroup);
		settingCheckbox.setCheckboxGroup(caseCheckboxGroup);
		caseCheckboxGroup.setSelectedCheckbox(identityCheckbox);
		identityCheckbox.setLabel(Interact.caseLines[0]);
		behaviorCheckbox.setLabel(Interact.caseLines[1]);
		modifierCheckbox.setLabel(Interact.caseLines[2]);
		settingCheckbox.setLabel(Interact.caseLines[3]);
		eraseCheck.setLabel(Interact.InteractText.getString("eraseData"));
		this.add(inputBox, BorderLayout.CENTER);
		this.add(buttonArea, BorderLayout.NORTH);
		buttonArea.add(identityCheckbox, null);
		buttonArea.add(behaviorCheckbox, null);
		buttonArea.add(modifierCheckbox, null);
		buttonArea.add(settingCheckbox, null);
		buttonArea.add(readDataButton, null);
		buttonArea.add(writeDataButton, null);
		this.add(erasePanel, BorderLayout.SOUTH);
		erasePanel.add(eraseCheck, null);
		readDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addData();
			}
		});
		writeDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDictionary();
			}
		});
	} // End jbInit.

	void addData() {
		if (inputBox.getText().isEmpty()) { return; }
		int TERMS_IN_LINE = 7; // Word, Male EPA, Female EPA.
		int MAX_TERMS = 7000; // 7*1000.
		String tokenArray[] = new String[MAX_TERMS];
		String[] clippedTokenArray;
		StringReader dicTextReader = new StringReader(inputBox.getText());
		StreamTokenizer dicTokenizer = new StreamTokenizer(dicTextReader);
		DataList workDic;
		DataList bufferDic = new DataList();
		if (identityCheckbox.getState()) {
			workDic = Interact.identities;
		} else {
			if (behaviorCheckbox.getState()) {
				workDic = Interact.behaviors;
			} else {
				if (modifierCheckbox.getState()) {
					workDic = Interact.modifiers;
				} else { // settingCheckbox
					workDic = Interact.settings;
				}
			}
		}
		dicTokenizer.resetSyntax();
		dicTokenizer.wordChars('-', '.');
		dicTokenizer.wordChars('0', '9');
		dicTokenizer.wordChars('A', 'z');
		dicTokenizer.whitespaceChars(0, ' ');
		dicTokenizer.whitespaceChars(',', ',');
		dicTokenizer.eolIsSignificant(false);
		// Loop thru lines of text.
		int tokenPosition = 0;
		int errorType = -1;
		int tokenType;
		try {
			out: while (true) {
				tokenType = dicTokenizer.nextToken(); // Get word or parameter.
				routing: switch (tokenType) {
				case StreamTokenizer.TT_EOF:
					// Done.
					break out;
				case StreamTokenizer.TT_EOL:
					break routing;
				case StreamTokenizer.TT_WORD: // Done reading one word.
					tokenArray[tokenPosition++] = dicTokenizer.sval;
					break routing;
				case StreamTokenizer.TT_NUMBER: // Done reading one word.
					tokenArray[tokenPosition++] = dicTokenizer.sval;
					break routing;
				default:
					errorType = 1; // Unrecognized token.
					break out;
				} // End routing: switch (tokenType).
			} // End out: while (true).
			if (errorType > -1) {
				Cancel_OK_Dialog importProblem =
					new Cancel_OK_Dialog((Frame) Interact.appletFrame,
						Interact.InteractText.getString("dicImportProblem"),
						Interact.InteractText.getString("badCharacters")+
							tokenArray[tokenPosition-1],
						Interact.InteractText.getString("ok"), null);
				importProblem.setVisible(true);
				return;
			}
		} // End try.
		catch (IOException err) {
			System.out.println("Bad data"
				+ Interact.InteractText.getString("paragraphCommand"));
		}
		int numberOfTerms = tokenPosition;
		if ((numberOfTerms % TERMS_IN_LINE) != 0) {
			// Wrong number of terms.
			Cancel_OK_Dialog countProblem =
				new Cancel_OK_Dialog((Frame) Interact.appletFrame,
					Interact.InteractText.getString("dicImportProblem"),
					Interact.InteractText.getString("countProblem"),
					Interact.InteractText.getString("ok"), null);
			countProblem.setVisible(true);
			return;
		} else {
			// Make a new array the right length, after adding all-yes filters
			// to end of each line.
			int newArrayLength = numberOfTerms + numberOfTerms / TERMS_IN_LINE;
			clippedTokenArray = new String[newArrayLength];
			int j = 0;
			for (int i = 0; i < numberOfTerms; i++) {
				clippedTokenArray[j++] = tokenArray[i];
				if ((i + 1) % TERMS_IN_LINE == 0) {
					clippedTokenArray[j++] = Data.ALL_TRUE_CONCEPT_GATES;
				}
			}
			bufferDic = new DataList("NewEntries", clippedTokenArray);
		}
		if (eraseCheck.getState()) {
			// Replace existing dictionary.
			workDic.removeAllElements();
		}
		// Supplement the dictionary.
		for (int i = bufferDic.size() - 1; i >= 0; i--) { 
			// Insert at top of dictionary.
			workDic.insertElementAt(bufferDic.elementAt(i), 0);
		}
		Interact.fillLists();
		inputBox.setText(Interact.InteractText.getString("ok"));
		showDictionary();
		Cancel_OK_Dialog success =
			new Cancel_OK_Dialog((Frame) Interact.appletFrame, "",
				Interact.InteractText.getString("done"),
				Interact.InteractText.getString("ok"), null);
		success.setVisible(true);
	} // End addData.

	void showDictionary() {
		DataList workDictionary;
		Data dicLine;
		String newLine = Interact.InteractText.getString("paragraphCommand");
		String theTable =
			Interact.InteractText.getString("termsOfUse") + newLine + newLine;
		if (identityCheckbox.getState()) {
			workDictionary = Interact.identities;
		} else if (behaviorCheckbox.getState()) {
			workDictionary = Interact.behaviors;
		} else if (modifierCheckbox.getState()) {
			workDictionary = Interact.modifiers;
		} else {
			workDictionary = Interact.settings;
		}
		int dicLength = workDictionary.size();
		for (int i = 0; i < dicLength; i++) {
			dicLine = (Data) workDictionary.elementAt(i);
			theTable = theTable + dicLine.toString();
		}
		inputBox.setText(theTable);
	} // End showDictionary.
}