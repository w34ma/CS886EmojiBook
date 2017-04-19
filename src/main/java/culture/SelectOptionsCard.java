package culture;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

/** Page for setting various parameters. */

public class SelectOptionsCard extends Panel {
	Label batchOptionsTitle1 = new Label(
		Interact.InteractText.getString("writeTo"));

	Label batchOptionsTitle2 = new Label(
		Interact.InteractText.getString("javaConsole"));

	Label processingTitle = new Label(
		Interact.InteractText.getString("processing"));

	Label sentimentFormationTitle = new Label(
		Interact.InteractText.getString("sentimentFormation"));

	Label numberToRememberLabel = new Label(
		Interact.InteractText.getString("rememberN"));

	Label searchCutoffLabel = new Label(
		Interact.InteractText.getString("searchCutoff"));

	Label numberOfInteractantsLabel = new Label(
		Interact.InteractText.getString("interactantsN"));

	Checkbox impressionsCheckbox = new Checkbox(
		Interact.InteractText.getString("impressions"));

	Checkbox deflectionCheckbox = new Checkbox(
		Interact.InteractText.getString("deflections"));

	Checkbox actorEmotionCheckbox = new Checkbox(
		Interact.InteractText.getString("actorEmotions"));

	Checkbox objectEmotionCheckbox = new Checkbox(
		Interact.InteractText.getString("objectEmotions"));

	Checkbox actorBehaviorCheckbox = new Checkbox(
		Interact.InteractText.getString("actorBehaviors"));

	Checkbox objectBehaviorCheckbox = new Checkbox(
		Interact.InteractText.getString("objectBehaviors"));

	Checkbox actorLabelCheckbox = new Checkbox(
		Interact.InteractText.getString("actorLabels"));

	Checkbox objectLabelCheckbox = new Checkbox(
		Interact.InteractText.getString("objectLabels"));

	Checkbox actorAttributeCheckbox = new Checkbox(
		Interact.InteractText.getString("actorAttributes"));

	Checkbox objectAttributeCheckbox = new Checkbox(
		Interact.InteractText.getString("objectAttributes"));

	Checkbox eventCheckbox = new Checkbox(
		Interact.InteractText.getString("verbalEvents"));

	Checkbox sentimentFromReidentsCheckbox = new Checkbox(
		Interact.InteractText.getString("fromReidents"));

	Checkbox sentimentFromTransientsCheckbox = new Checkbox(
		Interact.InteractText.getString("fromTransients"));

	Checkbox runBatchCheckbox = new Checkbox(
		Interact.InteractText.getString("batchRuns"));

	Checkbox keepRecordCheckbox = new Checkbox(
		Interact.InteractText.getString("keepRecords"));

	CheckboxGroup sentimentSourceCheckboxGroup = new CheckboxGroup();

	TextField eventsToRememberTextfield = new TextField();

	TextField searchDistanceTextfield = new TextField();

	TextField numberOfInteractantsTextfield = new TextField();

	GridBagLayout gridBagLayout1 = new GridBagLayout();

	Font titleFont;

	int[] colWidths = { 212, 212, 212 };

	public SelectOptionsCard() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(gridBagLayout1);
		this.setBackground(Color.white);
		gridBagLayout1.columnWidths = colWidths;
		titleFont = new Font("SansSerif", Font.BOLD, 14);
		batchOptionsTitle1.setFont(titleFont);
		batchOptionsTitle2.setFont(titleFont);
		processingTitle.setFont(titleFont);
		sentimentFormationTitle.setFont(titleFont);
		sentimentFromReidentsCheckbox
			.setCheckboxGroup(sentimentSourceCheckboxGroup);
		sentimentFromTransientsCheckbox
			.setCheckboxGroup(sentimentSourceCheckboxGroup);
		sentimentSourceCheckboxGroup
			.setSelectedCheckbox(sentimentFromReidentsCheckbox);
		numberOfInteractantsTextfield.setVisible(false);
		numberOfInteractantsLabel.setVisible(false);
		this.add(batchOptionsTitle1, new GridBagConstraints(0, 0, 1, 1, 0.0,
			0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(batchOptionsTitle2, new GridBagConstraints(0, 1, 1, 1, 0.0,
			0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(impressionsCheckbox, new GridBagConstraints(0, 2, 1, 1, 0.0,
			0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(deflectionCheckbox, new GridBagConstraints(0, 3, 1, 1, 0.0,
			0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(actorEmotionCheckbox, new GridBagConstraints(0, 4, 1, 1, 0.0,
			0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(objectEmotionCheckbox, new GridBagConstraints(0, 5, 1, 1, 0.0,
			0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(actorBehaviorCheckbox, new GridBagConstraints(0, 6, 1, 1, 0.0,
			0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(objectBehaviorCheckbox, new GridBagConstraints(0, 7, 1, 1,
			0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(actorLabelCheckbox, new GridBagConstraints(0, 8, 1, 1, 0.0,
			0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(objectLabelCheckbox, new GridBagConstraints(0, 9, 1, 1, 0.0,
			0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(actorAttributeCheckbox, new GridBagConstraints(0, 10, 1, 1,
			0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(objectAttributeCheckbox, new GridBagConstraints(0, 11, 1, 1,
			0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(eventCheckbox, new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0,
			GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0,
				0, 0), 0, 0));
		this.add(processingTitle, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0,
				0, 0), 0, 0));
		this.add(runBatchCheckbox, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0,
				0, 0), 0, 0));
		this.add(keepRecordCheckbox, new GridBagConstraints(1, 3, 1, 1, 0.0,
			0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(sentimentFormationTitle, new GridBagConstraints(2, 0, 1, 1,
			0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(numberToRememberLabel, new GridBagConstraints(2, 5, 1, 1, 0.0,
			0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(eventsToRememberTextfield, new GridBagConstraints(2, 6, 1, 1,
			0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 100), 0, 0));
		this.add(searchCutoffLabel, new GridBagConstraints(1, 6, 1, 1, 0.0,
			0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
				0, 0, 0, 0), 0, 0));
		this.add(searchDistanceTextfield, new GridBagConstraints(1, 7, 1, 1,
			0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 100), 0, 0));
		this.add(sentimentFromReidentsCheckbox, new GridBagConstraints(2, 2, 1,
			1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(sentimentFromTransientsCheckbox, new GridBagConstraints(2, 3,
			1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(numberOfInteractantsLabel, new GridBagConstraints(1, 9, 1, 1,
			0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
			new Insets(0, 0, 0, 0), 0, 0));
		this.add(numberOfInteractantsTextfield, new GridBagConstraints(1, 10,
			1, 1, 0.0, 0.0, GridBagConstraints.WEST,
			GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 100), 0, 0));
	} // End jbInit.

	public void recallOptions() {
		impressionsCheckbox.setState(Interact.impressionsBatch);
		deflectionCheckbox.setState(Interact.deflectionBatch);
		actorEmotionCheckbox.setState(Interact.actorEmotionBatch);
		objectEmotionCheckbox.setState(Interact.objectEmotionBatch);
		actorBehaviorCheckbox.setState(Interact.actorBehaviorBatch);
		objectBehaviorCheckbox.setState(Interact.objectBehaviorBatch);
		actorLabelCheckbox.setState(Interact.actorLabelBatch);
		objectLabelCheckbox.setState(Interact.objectLabelBatch);
		actorAttributeCheckbox.setState(Interact.actorAttributeBatch);
		objectAttributeCheckbox.setState(Interact.objectAttributeBatch);
		eventCheckbox.setState(Interact.listingEvents);
		runBatchCheckbox.setState(Interact.batch);
		keepRecordCheckbox.setState(Interact.keepingRecord);
		sentimentFromReidentsCheckbox
			.setState(!SentimentChange.sentimentsFromTransients);
		sentimentFromTransientsCheckbox
			.setState(SentimentChange.sentimentsFromTransients);
		numberOfInteractantsTextfield.setText(Interact
			.formatLocaleDecimal(Interact.numberOfInteractants));
		searchDistanceTextfield.setText(Interact
			.formatLocaleDecimal(Interact.searchCutoff));
		eventsToRememberTextfield.setText(Interact
			.formatLocaleDecimal(SentimentChange.maximumRecall));
	} // End recallOptions.

	public void storeOptions() {
		Interact.impressionsBatch = impressionsCheckbox.getState();
		Interact.deflectionBatch = deflectionCheckbox.getState();
		Interact.actorEmotionBatch = actorEmotionCheckbox.getState();
		Interact.objectEmotionBatch = objectEmotionCheckbox.getState();
		Interact.actorBehaviorBatch = actorBehaviorCheckbox.getState();
		Interact.objectBehaviorBatch = objectBehaviorCheckbox.getState();
		Interact.actorLabelBatch = actorLabelCheckbox.getState();
		Interact.objectLabelBatch = objectLabelCheckbox.getState();
		Interact.actorAttributeBatch = actorAttributeCheckbox.getState();
		Interact.objectAttributeBatch = objectAttributeCheckbox.getState();
		Interact.listingEvents = eventCheckbox.getState();
		Interact.batch = runBatchCheckbox.getState();
		Interact.keepingRecord = keepRecordCheckbox.getState();
		SentimentChange.sentimentsFromTransients =
			sentimentFromTransientsCheckbox.getState();
		Interact.numberOfInteractants =
			(int) Interact.readNumber(numberOfInteractantsTextfield.getText());
		Interact.searchCutoff =
			Interact.readNumber(searchDistanceTextfield.getText());
		SentimentChange.maximumRecall =
			(int) Interact.readNumber(eventsToRememberTextfield.getText());
		if (SentimentChange.maximumRecall == 0) {
			SentimentChange.changingSentiments = false;
			SentimentChange.commuteMutatorIdentities(false);
		} else {
			SentimentChange.changingSentiments = true;
			SentimentChange.commuteMutatorIdentities(true);
		}
	} // End storeOptions.

} // End OptionsCard.
