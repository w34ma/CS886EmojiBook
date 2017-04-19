package culture;

/** Form for defining data on one person. */
public class Person {

	static public final boolean MALE = true;

	static public final boolean FEMALE = false;

	public String name;

	public boolean sex;

	public int visage;

	public String language;

	// Limiting control to some ABOS positions or some dimensions
	// not implemented:
	public boolean controllingCase[], controllingEPA[];

	public Phrase setting;

	public Phrase[] viewOfPerson;

	public DataList serialEvents;
	
	public float currentTension;

	public Person(int indexNumber) { // Constructor method.
		name =
			new String(Interact.InteractText.getString("personText")
				+ (new Integer(indexNumber + 1)).toString());
		sex = MALE;
		visage = 0;
		language = "notUsedYet";
		controllingCase = new boolean[4]; // unused
		controllingEPA = new boolean[3]; // unused
		for (int i = 0; i < 4; i++) {
			controllingCase[i] = true; // unused
		}
		for (int i = 0; i < 3; i++) {
			controllingEPA[i] = true; // unused
		}
		setting = new Phrase(-1, -1); // Unspecified modifier-noun for setting.
		// Unspecified modifier-identity for view of each person.
		viewOfPerson = new Phrase[Interact.numberOfInteractants];
		for (int i = 0; i < Interact.numberOfInteractants; i++) {
			viewOfPerson[i] = new Phrase(-1, -1);
		}
		serialEvents = new DataList();
		currentTension = 0;
	}

	public String toString() {
		// Transform a person's data to textual form.
		String output;
		int index;

		output =
			this.name + Interact.InteractText.getString("clauseSeparation");
		if (this.sex) {
			output =
				output + Interact.InteractText.getString("male")
					+ Interact.InteractText.getString("clauseSeparation")
					+ Interact.InteractText.getString("settingTitle") + "=";
		} else {
			output =
				output + Interact.InteractText.getString("female")
					+ Interact.InteractText.getString("clauseSeparation")
					+ Interact.InteractText.getString("settingTitle") + "=";
		}
		index = setting.nounWordNumber;
		if (index < 0) {
			output =
				output + Interact.InteractText.getString("empty")
					+ Interact.InteractText.getString("sentenceSeparation");
		} else {
			output =
				output + ((Data) Interact.settings.elementAt(index)).word
					+ Interact.InteractText.getString("sentenceSeparation");
		}
		for (int i = 0; i < Interact.numberOfInteractants; i++) {
			output =
				output + Interact.person[i].name
					+ Interact.InteractText.getString("leftBracket");
			index = this.viewOfPerson[i].modifierWordNumber;
			if (index < 0) {
				output =
					output + Interact.InteractText.getString("empty")
						+ Interact.InteractText.getString("clauseSeparation");
			} else {
				output =
					output + ((Data) Interact.modifiers.elementAt(index)).word
						+ Interact.InteractText.getString("clauseSeparation");
			}
			index = this.viewOfPerson[i].nounWordNumber;
			if (index < 0) {
				output =
					output + Interact.InteractText.getString("empty")
						+ Interact.InteractText.getString("rightBracket")
						+ Interact.InteractText.getString("space");
			} else {
				output =
					output + ((Data) Interact.identities.elementAt(index)).word
						+ Interact.InteractText.getString("rightBracket")
						+ Interact.InteractText.getString("space");
			}
		}
		return (output + Interact.InteractText.getString("paragraphCommand"));
	}
}
