package culture;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

/** Page for analyzing identities close to a self-sentiment. */

public class ExploreSelfCard extends Panel {
	/*
	 * Self analysis involves four classes: SelfIdentitiesCard,
	 * IdentitiesChartSubcard, IdentitiesListsSubcard, and SelfIdentitiesGraph.
	 */
	public double circuitRadius = 1.75;

	public double actualizingRadius = 0.75;

	final String DEFAULT_BOUNDS = "0.75 1.75";

	final String DEFAULT_EPA = "2.48 1.74 1.83"; 
	// Indiana male average self sentiment, 1994.

	IdentitiesChartSubcard identitiesChart;

	IdentitiesListsSubcard identitiesLists;

	Panel bottomPanel = new Panel();

	Panel identitiesDisplayPanel = new Panel();

	Panel dislayChoicePanel = new Panel();

	Panel ratersPanel = new Panel();

	Panel EPAPanel = new Panel();

	Panel boundsPanel = new Panel();

	Panel selfProfilePanel = new Panel();

	Checkbox showChart = new Checkbox();

	Checkbox showLists = new Checkbox();

	Checkbox maleRater = new Checkbox();

	Checkbox femaleRater = new Checkbox();

	CheckboxGroup displayType = new CheckboxGroup();

	CheckboxGroup raterSex = new CheckboxGroup();

	TextField selfEPA = new TextField();

	TextField bounds = new TextField();

	Label maleFemaleDataTitle = new Label();

	Label selfProfileTitle = new Label();

	Label boundsTitle = new Label();

	CardLayout displayCards = new CardLayout();

	BorderLayout borderLayout1 = new BorderLayout();

	BorderLayout borderLayout3 = new BorderLayout();

	FlowLayout choicesFlowLayout = new FlowLayout(FlowLayout.LEFT);

	FlowLayout ratersFlowLayout = new FlowLayout(FlowLayout.CENTER);

	FlowLayout EPAFlowLayout = new FlowLayout(FlowLayout.RIGHT);

	GridBagLayout gridBagLayout1 = new GridBagLayout();

	boolean newSelf = false;

	public ExploreSelfCard() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		identitiesDisplayPanel.setLayout(displayCards);
		bottomPanel.setLayout(gridBagLayout1);
		selfProfilePanel.setLayout(borderLayout3);
		selfProfileTitle
			.setText(Interact.InteractText.getString("selfProfile"));
		boundsTitle.setText(Interact.InteractText.getString("boundsTitle"));
		maleFemaleDataTitle
			.setText(Interact.InteractText.getString("raterSex"));
		selfProfileTitle.setAlignment(Label.RIGHT);
		maleFemaleDataTitle.setAlignment(Label.RIGHT);
		boundsTitle.setAlignment(Label.RIGHT);
		dislayChoicePanel.setLayout(choicesFlowLayout);
		ratersPanel.setLayout(ratersFlowLayout);
		EPAPanel.setLayout(EPAFlowLayout);
		this.add(identitiesDisplayPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.add(dislayChoicePanel, new GridBagConstraints(0, 0, 1, 1,
			1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(ratersPanel, new GridBagConstraints(1, 0, 1, 1, 1.0,
			1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		bottomPanel.add(EPAPanel, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,
				0, 0, 0), 0, 0));
		bottomPanel.add(boundsPanel, new GridBagConstraints(3, 0, 1, 1, 1.0,
			1.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(
				0, 0, 0, 0), 0, 0));
		// Choice of display.
		showChart.setLabel(Interact.InteractText.getString("chart"));
		showChart.setCheckboxGroup(displayType);
		showChart.setState(true);
		showLists.setLabel(Interact.InteractText.getString("lists"));
		showLists.setCheckboxGroup(displayType);
		dislayChoicePanel.add(showChart);
		dislayChoicePanel.add(showLists);
		// Self EPA.
		EPAPanel.add(selfProfileTitle);
		EPAPanel.add(selfEPA);
		selfEPA.setText(DEFAULT_EPA);
		// Boundaries for circles.
		boundsPanel.add(boundsTitle);
		boundsPanel.add(bounds);
		bounds.setText(DEFAULT_BOUNDS);
		// Sex of raters.
		ratersPanel.add(maleFemaleDataTitle, null);
		maleRater.setLabel(Interact.InteractText.getString("male"));
		maleRater.setCheckboxGroup(raterSex);
		maleRater.setState(true);
		femaleRater.setLabel(Interact.InteractText.getString("female"));
		femaleRater.setCheckboxGroup(raterSex);
		femaleRater.setState(false);
		ratersPanel.add(maleRater);
		ratersPanel.add(femaleRater);
		// Create sub-cards.
		identitiesChart = new IdentitiesChartSubcard();
		identitiesDisplayPanel.add("chart", identitiesChart);
		identitiesLists = new IdentitiesListsSubcard();
		identitiesDisplayPanel.add("lists", identitiesLists);

		showChart.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				displayCards.show(identitiesDisplayPanel, "chart");// Show the
				// chart.
			}
		});
		showLists.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Retrieval cumulation =
					Interact.analyzeSelf.identitiesChart.viewIdentitiesScatter.cumulativeDivergence;
				Interact.analyzeSelf.identitiesChart.viewIdentitiesScatter
					.selectIdentities(true, cumulation);
				displayCards.show(identitiesDisplayPanel, "lists");// Show the
				// lists.
				identitiesLists.fillIdentitiesLists();
			}
		});
		maleRater.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				identitiesLists.fillIdentitiesLists();
				Interact.analyzeSelf.identitiesChart.viewIdentitiesScatter
					.reset();
				parameterChanged();
			}
		});
		femaleRater.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				identitiesLists.fillIdentitiesLists();
				Interact.analyzeSelf.identitiesChart.viewIdentitiesScatter
					.reset();
				parameterChanged();
			}
		});
		selfEPA.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				identitiesLists.fillIdentitiesLists();
				newSelf = true;
				Interact.analyzeSelf.identitiesChart.viewIdentitiesScatter
					.reset();
				parameterChanged();
			}
		});
		bounds.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double[] boundsProfile = { 0, 0, 0 };
				boundsProfile =
					readTwoNumbers(Interact.analyzeSelf.bounds.getText());
				actualizingRadius = boundsProfile[0];
				circuitRadius = boundsProfile[1];
				identitiesLists.fillIdentitiesLists();
				newSelf = true;
				parameterChanged();
			}
		});
	} // End constructor method.

	void parameterChanged() {
		identitiesChart.viewIdentitiesScatter.repaint();
	} // End parameterChanged.

	double[] readTwoNumbers(String profileText) {
		double[] profile = { Interact.MISSING_VALUE, Interact.MISSING_VALUE };
		int index = -1;
		Interact.startingCharacter.setIndex(0);
		while (++index < 2) {
			profile[index] = Interact.readLocaleDecimal(profileText);
		}
		return profile;
	}

} // End SelfIdentitiesCard.
