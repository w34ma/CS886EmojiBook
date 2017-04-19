package culture;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.ResourceBundle;

// Conversion to app from: http://www.cs.tcu.edu/10403/AppletToApplication.pdf

/**
 * <b>Simulation of social interaction.</b>
 * 
 * @author David Heise.
 *         <P>
 * 
 */
public class Interact extends Frame implements WindowListener {

	/** E, P, and A. */
	static final int N_DIMENSIONS = 3;

	/** Initial value for the maximum Euclidean distance for compiling lists. */
	static final double STARTING_SEARCH_CUTOFF = 1.0;

	public static final double MISSING_VALUE = 99.99;

	static int numberOfInteractants = 4;

	/** The maximum Euclidean distance for compiling lists. */
	static double searchCutoff;

	/** The interactant whose psychology is being simulated at the moment. */
	public static int viewer = 0;

	/** The array of interactants. */
	public static Person[] person;

	/** Dictionary database. */
	public static DataList identities, behaviors, modifiers, settings;

	/** Equation database. */
	public static MathModel maleabo, maleaboS, maleSelf, maleTrait, maleEmotion,
	femaleabo, femaleaboS, femaleSelf, femaleTrait, femaleEmotion;

	/**
	 * String parameter to show all texts and analyses, with numbers. Used with
	 * ADVANCED FUNCTIONS.
	 */
	static String reportExpert;

	/**
	 * String parameter to show texts, but no numbers except deflection. Used
	 * with BASIC FUNCTIONS.
	 */
	static String reportAdvanced;

	/** Menu array. */
	static String[] operationLines, displayLines,cultureLines, caseLines;

	/** ConceptGate array. */
	static String[] identityConceptGateLines, behaviorConceptGateLines,
	modifierConceptGateLines, settingConceptGateLines;

	/** Error messages for Define-events page. */
	static String[] storeErrorLines;

	/** Parameter defining whether behaviors can repeat and change meanings. */
	static String[] behaviorMeaningLines;

	/** Lines for equation-studies-selection menu. */
	static public String[] equationStudyLines;

	/** Lines for equation-subset-selection menu. */
	static public String[] equationTextLines;

	/** Array of strings defining operations menu. */
	static String[] operationMenuLines = { "setOptions", "defineInteractants",
		"selfIdentities", "definePerson", "defineEvents", "analyzeEvents",
		"interactions", "findConcepts", "makeEmotions", "viewEquations", 
		"inputEntries", "outputReport" };

	/** Applet dimension. */
	int width, height;

	String language, country;

	/** Start-up flag. */
	static boolean initializing = true;

	static String nameDefaultLocale;

	/** Default interface texts. */
	static ResourceBundle InteractText;

	/** Default equations. */
	static ResourceBundle CoefficientsText;

	/** Default dictionary. */
	static ResourceBundle IdentitiesText, BehaviorsText, ModifiersText,
	SettingsText;

	// LOCALES
	/** "en", "US" */
	static Locale America = new Locale("en", "US");

	/** "en", "US", "TX" */
	static Locale TX98 = new Locale("en", "US", "TX");

	/** "en", "US", "NC" */
	static Locale UNC78 = new Locale("en", "US", "NC");

	/** "en", "CA", "81" */
	static Locale Canada81 = new Locale("en", "CA", "81");

	/** "en", "CA", "01" */
	static Locale Canada01 = new Locale("en", "CA", "01");

	/** "en", "CA", "01" */
	static Locale CanadaEq2 = new Locale("en", "CA", "EQ2");

	/** "en", "IE" */
	static Locale Ireland = new Locale("en", "IE");

	/** "en", "US", "DE" */
	static Locale Germany = new Locale("en", "US", "DE");

	/** "en", "US", "DE", "07" */
	static Locale Germany07 = new Locale("en", "US", "DE07");

	/** "en", "US", "JP" */
	static Locale Japan = new Locale("en", "US", "JP");

	/** "en", "US", "CN" */
	static Locale China = new Locale("en", "US", "CN");

	/** "de", "DE" */
	static Locale Deutsch = new Locale("de", "DE");

	/** "de", "DE", "07" */
	static Locale Deutsch07 = new Locale("de", "DE", "DE07");

	/** "ja", "JP" */
	static Locale Nippon = new Locale("ja", "JP");

	/** "zh", "CN" */
	static Locale PRC = new Locale("zh", "CN");

	static Locale currentLocale;

	static NumberFormat localeNumberFormat;

	static DecimalFormat localeDecimal;

	/** Flags for batch runs, set on options form. */
	static boolean listingEvents, batch, impressionsBatch, deflectionBatch,
	actorEmotionBatch, objectEmotionBatch, actorBehaviorBatch,
	objectBehaviorBatch, actorLabelBatch, objectLabelBatch,
	actorAttributeBatch, objectAttributeBatch;

	/** Flag to record analyses. */
	static boolean keepingRecord;

	/** Flag to stop history processes when constructing interaction. */
	static boolean onInteractionsCard = false;

	static ParsePosition startingCharacter = new ParsePosition(0);

	/** Interact's menu bar. */
	static ControlsMenuBar controls;

	/** Page for setting parameters. */
	static SelectOptionsCard setOptions;

	/** Page for analyzing a self. */
	static ExploreSelfCard analyzeSelf;

	/** Page for setting interactants' sexes and faces. */
	static DefineInteractantsCard defineInteractants;

	/** Page for defining situations. */
	static DefineSituationCard definePerson;

	/** Page for defining actions. */
	static DefineEventsCard defineEvents;

	/** Page for analyzing and generating events. */
	static AnalyzeEventsCard analyzeEvents;

	/** Page for generating multi-person interactions. */
	static InteractionsCard interactions;

	/** Page for finding concepts matching an EPA profile. */
	static FindConceptsCard findConcepts;

	/** Page for analyzing interplays of emotions with actions. */
	static FeelingEffectsCard studyEmotions;

	/** Page for viewing and inputting equations. */
	static EquationsCard viewEquations;

	/** Page for viewing text records of simulations. */
	static ViewReportCard listResults;

	/** Page for importing and exporting dictionaries. */
	static ImportExportCard importExport;

	/** Encompassing frame to define dialogs. */
	static Component appletFrame;

	/** Name of base directory for HTML calls. */
	static String baseDirectory;

	static CardLayout card;

	static Panel controlArea, display;


	// Initialize
	public Interact()  {

		// SET LOCALE.
		currentLocale = Locale.getDefault();

		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		Interact f = new Interact ();
//		f.setSize(800,600);
		f.setSize(1000,750);
		f.setVisible(true);
		f.setLayout(new BorderLayout(5, 5));
	}

	public void windowClosing(WindowEvent e) {
		dispose();
		System.exit(0);
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}
	
	// Component initialization
	private void jbInit() throws Exception {
		this.addWindowListener(this);

		// Set color.
		this.setBackground(Color.white);
		// Save applet's width and height.
		width = this.getSize().width;
		height = this.getSize().height;

		// Initialize Option form parameters.
		listingEvents =
			impressionsBatch =
				deflectionBatch =
					actorEmotionBatch =
						objectEmotionBatch =
							actorBehaviorBatch =
								objectBehaviorBatch =
									actorLabelBatch =
										objectLabelBatch =
											actorAttributeBatch =
												objectAttributeBatch =
													batch = false;
		keepingRecord = true;
		SentimentChange.changingSentiments = false;
		SentimentChange.maximumRecall = 0;

		// Set up the basic data structure for storing events.
		SentimentChange.history =
			new Situation[numberOfInteractants][numberOfInteractants][SentimentChange.maxHistory];
		for (int i = 0; i < numberOfInteractants; i++) {
			for (int j = 0; j < numberOfInteractants; j++) {
				for (int k = 0; k < SentimentChange.maxHistory; k++) {
					SentimentChange.history[i][j][k] = new Situation();
				}
			}
		}

		inputCulture();

		// Create the interactants.
		setDefaultPeople();

		// Set the cutoff distance for searching. May be changed on
		// Find-concepts form or Option form. Is changed temporarily
		// to 100.0 on the InteractionsCard.
		searchCutoff = STARTING_SEARCH_CUTOFF;

		// Layout the basic structure of a control area above
		// and card content below.
		setLayout(new BorderLayout(5, 5));
		controls = new ControlsMenuBar();
		add("North", controls);
		display = new Panel();
		add("Center", display);
		card = new CardLayout();
		display.setLayout(card);

		defineTheCards();

		controls.currentCardIndex = controls.DEFINE_SITUATION;
		controls.operationsChoice.select(controls.currentCardIndex);

		reportAdvanced = reportExpert = "";

		controls.setComplexityLevel(ControlsMenuBar.ADVANCED);
		controls.complexityChoice.select(ControlsMenuBar.ADVANCED);

		initializing = false;
	} // End jbInit.

	static void defineTheCards() {

		int itemNumber = 0;

		setOptions = new SelectOptionsCard();
		display.add(operationMenuLines[itemNumber], setOptions);

		analyzeSelf = new ExploreSelfCard();
		display.add(operationMenuLines[++itemNumber], analyzeSelf);

		defineInteractants = new DefineInteractantsCard();
		display.add(operationMenuLines[++itemNumber], defineInteractants);

		definePerson = new DefineSituationCard();
		display.add(operationMenuLines[++itemNumber], definePerson);

		defineEvents = new DefineEventsCard();
		display.add(operationMenuLines[++itemNumber], defineEvents);

		analyzeEvents = new AnalyzeEventsCard();
		display.add(operationMenuLines[++itemNumber], analyzeEvents);
		//@@@@@@@@@@@@@@
		interactions = new InteractionsCard();
		display.add(operationMenuLines[++itemNumber], interactions);

		findConcepts = new FindConceptsCard();
		display.add(operationMenuLines[++itemNumber], findConcepts);

		studyEmotions = new FeelingEffectsCard();
		display.add(operationMenuLines[++itemNumber], studyEmotions);

		viewEquations = new EquationsCard();
		display.add(operationMenuLines[++itemNumber], viewEquations);

		importExport = new ImportExportCard();
		display.add(operationMenuLines[++itemNumber], importExport);

		listResults = new ViewReportCard();
		display.add(operationMenuLines[++itemNumber], listResults);
	} // End defineTheCards.

	/**
	 * Format numbers to two decimals with 3 positions in front of decimal
	 * point.
	 */
	public static String formatLocaleDecimal(double number) {
		String theText;
		String blanks = "      ";
		double numberWith2DecimalPlaces;
		int decPosition;
		char decimalPoint =
			localeDecimal.getDecimalFormatSymbols().getDecimalSeparator();
		numberWith2DecimalPlaces =
			(double) ((Math.round(100.0 * number)) / 100.0);
		theText = localeDecimal.format(numberWith2DecimalPlaces);
		decPosition = theText.indexOf(decimalPoint);
		if (decPosition < 0) { // Must be an integer.
			theText = theText + decimalPoint + "00";
			decPosition = theText.indexOf(decimalPoint);
		} else {
			theText = theText + "00";
			theText = theText.substring(0, decPosition + 3);
		}
		if (decPosition > 3) {
			theText = "*" + theText; // Number too big.
		} else {
			theText = blanks.substring(0, 4 - decPosition) + theText;
		}
		return theText;
	}

	/** Read an EPA profile from text. */
	static double[] readProfile(String profileText) {
		double[] profile = { MISSING_VALUE, MISSING_VALUE, MISSING_VALUE };
		int epa = -1;
		startingCharacter.setIndex(0);
		while (++epa < 3) {
			profile[epa] = readLocaleDecimal(profileText);
		}
		return profile;
	}

	/**
	 * Read a double from its string representation, with the parse starting at
	 * zero and ending at the end of the string.
	 */
	static double readNumber(String numberText) {
		double theNumber = MISSING_VALUE;
		startingCharacter.setIndex(0);
		theNumber = readLocaleDecimal(numberText);
		return theNumber;
	}

	/**
	 * Obtain a double from its string representation, using locale conventions
	 * for decimals. Parsing index must be set by the calling routine. e.g.,
	 * Interact.startingCharacter.setIndex(0);
	 */
	static double readLocaleDecimal(String digits) throws NumberFormatException {
		Number entry;
		entry = null;
		int stringLength = digits.length();
		int firstDigit = startingCharacter.getIndex();
		// Work through the string to find the number.
		while (entry == null) {
			// Parse returns null if the starting character
			// is not part of a number.
			startingCharacter.setIndex(firstDigit++);
			entry = localeDecimal.parse(digits, startingCharacter);
			if (firstDigit > stringLength) {
				Cancel_OK_Dialog numberProblem =
					new Cancel_OK_Dialog((Frame) Interact.appletFrame,
						Interact.InteractText.getString("numberProblemTitle"),
						Interact.InteractText.getString("numberProblem"),
						Interact.InteractText.getString("ok"), null);
				numberProblem.setVisible(true);
				throw new NumberFormatException(
					Interact.InteractText.getString("numberProblem") + digits);
			}
		}
		return entry.doubleValue();
	}

	/** Form a string describing the viewer's sex and perceived setting. */
	static String identifyViewerSexAndSetting(int aViewer) {
		String features;
		if (Interact.person[aViewer].sex) {
			features = Interact.InteractText.getString("male");
		} else {
			features = Interact.InteractText.getString("female");
		}
		int index = Interact.person[aViewer].setting.nounWordNumber;
		if (index < 0) {
			features =
				features
				+ Interact.InteractText.getString("sentenceSeparation");
		} else {
			features =
				features + Interact.InteractText.getString("clauseSeparation")
				+ Interact.InteractText.getString("theSettingIs")
				+ Interact.InteractText.getString("colon")
				+ Interact.InteractText.getString("space")
				+ ((Data) Interact.settings.elementAt(index)).word
				+ Interact.InteractText.getString("sentenceSeparation");
		}
		return features;
	} // End identifyViewerSexAndSetting.

	/**
	 * Load a culture's identities, behaviors, modifers, settings, equations,
	 * and interface texts.
	 */
	static void inputCulture() {
		IdentitiesText =
			ResourceBundle.getBundle("culture.Identities", currentLocale);
		BehaviorsText =
			ResourceBundle.getBundle("culture.Behaviors", currentLocale);
		ModifiersText =
			ResourceBundle.getBundle("culture.Modifiers", currentLocale);
		SettingsText =
			ResourceBundle.getBundle("culture.Settings", currentLocale);
		InteractText =
			ResourceBundle.getBundle("culture.InteractTexts", currentLocale);
		CoefficientsText =
			ResourceBundle.getBundle("culture.Coefficients", currentLocale);
		// Get message texts.
		operationLines =
			(String[]) InteractText.getStringArray("operationType");
		displayLines = (String[]) InteractText.getStringArray("displayType");
		cultureLines = (String[]) InteractText.getStringArray("cultureType");
		caseLines = (String[]) InteractText.getStringArray("caseNames");
		identityConceptGateLines =
			(String[]) InteractText.getStringArray("identityConceptGates");
		behaviorConceptGateLines =
			(String[]) InteractText.getStringArray("behaviorConceptGates");
		modifierConceptGateLines =
			(String[]) InteractText.getStringArray("modifierConceptGates");
		settingConceptGateLines =
			(String[]) InteractText.getStringArray("settingConceptGates");
		equationStudyLines =
			(String[]) InteractText.getStringArray("equationStudies");
		equationTextLines = (String[]) InteractText.getStringArray("equations");
		storeErrorLines =
			(String[]) InteractText.getStringArray("storeEventsError");
		behaviorMeaningLines =
			(String[]) InteractText.getStringArray("behaviorMeaning");

		// Set up locale number formatting.
		localeNumberFormat = NumberFormat.getNumberInstance(currentLocale);
		localeDecimal = (DecimalFormat) localeNumberFormat;
		localeDecimal.applyPattern("##0.##");

		// Read the dictionaries.
		identities =
			new DataList("IdentityData",
				(String[]) IdentitiesText.getStringArray("IdentityData"));
		if (initializing) {
			nameDefaultLocale = (String) IdentitiesText.getString("Source");
		}
		behaviors =
			new DataList("BehaviorData",
				(String[]) BehaviorsText.getStringArray("BehaviorData"));
		modifiers =
			new DataList("ModifierData",
				(String[]) ModifiersText.getStringArray("ModifierData"));
		settings =
			new DataList("SettingData",
				(String[]) SettingsText.getStringArray("SettingData"));

		// Initialize the equations.
		maleabo =
			new MathModel("ABOmale",
				(String[]) CoefficientsText.getStringArray("ABOmale"));
		femaleabo =
			new MathModel("ABOfemale",
				(String[]) CoefficientsText.getStringArray("ABOfemale"));
		maleaboS =
			new MathModel("ABOSmale",
				(String[]) CoefficientsText.getStringArray("ABOSmale"));
		femaleaboS =
			new MathModel("ABOSfemale",
				(String[]) CoefficientsText.getStringArray("ABOSfemale"));
		maleSelf =
			new MathModel("ABmale",
				(String[]) CoefficientsText.getStringArray("ABmale"));
		femaleSelf =
			new MathModel("ABfemale",
				(String[]) CoefficientsText.getStringArray("ABfemale"));
		maleTrait =
			new MathModel("TraitMale",
				(String[]) CoefficientsText.getStringArray("TraitMale"));
		femaleTrait =
			new MathModel("TraitFemale",
				(String[]) CoefficientsText.getStringArray("TraitFemale"));
		maleEmotion =
			new MathModel("EmotionMale",
				(String[]) CoefficientsText.getStringArray("EmotionMale"));
		femaleEmotion =
			new MathModel("EmotionFemale",
				(String[]) CoefficientsText.getStringArray("EmotionFemale"));

		if (!initializing) {
			// Fill identity, behavior, setting, and modifier lists.
			fillLists(); 
		}
	} // End inputCulture.

	/**
	 * Fill selection lists when new data are imported. (Lists are initialized
	 * by each card's constructor.)
	 */
	static void fillLists() {
		definePerson.identityList.removeAll();
		definePerson.modifierList.removeAll();
		definePerson.settingList.removeAll();
		defineEvents.behaviorList.removeAll();
		// Fill the lists.
		String wordText = "";
		for (int i = 0; i < identities.size(); i++) {
			wordText = ((Data) identities.elementAt(i)).word;
			definePerson.identityList.add(wordText);
		}
		for (int i = 0; i < settings.size(); i++) {
			wordText = ((Data) settings.elementAt(i)).word;
			definePerson.settingList.add(wordText);
		}
		for (int i = 0; i < modifiers.size(); i++) {
			wordText = ((Data) modifiers.elementAt(i)).word;
			definePerson.modifierList.add(wordText);
		}
		for (int i = 0; i < behaviors.size(); i++) {
			wordText = ((Data) behaviors.elementAt(i)).word;
			defineEvents.behaviorList.add(wordText);
		}
	} // End fillLists.

	/** Set sexes and faces of interactants. */
	static void setDefaultPeople() {
		person = new Person[numberOfInteractants];
		for (int i = 0; i < numberOfInteractants; i++) {
			person[i] = new Person(i);
			if (i == 0) {
				person[i].sex = Person.MALE;
				person[i].visage = Face.FIRST_MALE;
			} else if (i == 1) {
				person[i].sex = Person.FEMALE;
				person[i].visage = Face.FIRST_FEMALE;
			} else if (i == 2) {
				person[i].sex = Person.MALE;
				person[i].visage = Face.SECOND_MALE;
			} else {
				person[i].sex = Person.FEMALE;
				person[i].visage = Face.SECOND_FEMALE;
			}
		}
	} // End setDefaultPeople.

} // End class Interact.
