package culture;

import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.beans.PropertyChangeEvent;

/**
 * Graphic scales for defining an EPA profile that serves as a reference for
 * dictionary searching.
 */
public class RateEPA extends Panel {
	CheckboxGroup checkboxGroup1 = new CheckboxGroup();

	Label evaluationRightAdjective = new Label();

	Label potencyRightAdjective = new Label();

	Label activityRightAdjective = new Label();

	Label evaluationLeftAdjective = new Label();

	Label potencyLeftAdjective = new Label();

	Label activityLeftAdjective = new Label();

	Label infiniteL = new Label();

	Label infinteR = new Label();

	Label neutral = new Label();

	Slider slider1 = new Slider();

	Slider slider2 = new Slider();

	Slider slider3 = new Slider();

	private int epaDimension;

	private int scaleValue;

	GridBagLayout gridBagLayout1 = new GridBagLayout();

	GridBagLayout gridBagLayout4 = new GridBagLayout();

	GridBagConstraints gridbag1 = new GridBagConstraints();

	int[] colWidths = { 146, 115, 114, 115, 146 };

	PropertyChangeEvent lastRating = null;

	double[] profile = { 0, 0, 0 };

	public RateEPA() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		infinteR.setAlignment(2);
		neutral.setAlignment(1);
		infiniteL.setText(Interact.InteractText.getString("infinitely"));
		infinteR.setText(Interact.InteractText.getString("infinitely"));
		neutral.setText(Interact.InteractText.getString("neutral"));
		infiniteL.setFont(new java.awt.Font("Dialog", 0, 9));
		infinteR.setFont(new java.awt.Font("Dialog", 0, 9));
		neutral.setFont(new java.awt.Font("Dialog", 0, 9));
		this.setBackground(Color.white);
		slider1.setShowingDigitLabels(false);
		slider1.setRatingRange(344);
		slider1.setSnappingToOrigin(false);
		slider2.setShowingDigitLabels(false);
		slider2.setRatingRange(344);
		slider2.setSnappingToOrigin(false);
		slider3.setShowingDigitLabels(false);
		slider3.setRatingRange(344);
		slider3.setSnappingToOrigin(false);
		evaluationRightAdjective.setText(Interact.InteractText
			.getString("PosEval"));
		evaluationRightAdjective.setFont(new java.awt.Font("Dialog", 1, 12));
		potencyRightAdjective.setText(Interact.InteractText
			.getString("PosPotn"));
		potencyRightAdjective.setFont(new java.awt.Font("Dialog", 1, 12));
		activityRightAdjective.setText(Interact.InteractText
			.getString("PosActiv"));
		activityRightAdjective.setFont(new java.awt.Font("Dialog", 1, 12));
		evaluationLeftAdjective.setAlignment(2);
		evaluationLeftAdjective.setText(Interact.InteractText
			.getString("NegEval"));
		evaluationLeftAdjective.setFont(new java.awt.Font("Dialog", 1, 12));
		potencyLeftAdjective.setAlignment(2);
		potencyLeftAdjective
			.setText(Interact.InteractText.getString("NegPotn"));
		potencyLeftAdjective.setFont(new java.awt.Font("Dialog", 1, 12));
		activityLeftAdjective.setAlignment(2);
		activityLeftAdjective.setText(Interact.InteractText
			.getString("NegActiv"));
		activityLeftAdjective.setFont(new java.awt.Font("Dialog", 1, 12));

		this.setLayout(gridBagLayout1);
		gridBagLayout1.columnWidths = colWidths;
		gridbag1.fill = GridBagConstraints.NONE;
		gridbag1.weightx = 1.0;
		gridbag1.weighty = 1.0;
		gridbag1.gridwidth = 1;
		gridbag1.gridheight = 1;
		gridbag1.gridy = 3;
		gridbag1.gridx = 1;
		gridbag1.anchor = GridBagConstraints.WEST;
		gridBagLayout1.setConstraints(infiniteL, gridbag1);
		this.add(infiniteL);
		gridbag1.gridx = 2;
		gridbag1.anchor = GridBagConstraints.CENTER;
		gridBagLayout1.setConstraints(neutral, gridbag1);
		this.add(neutral);
		gridbag1.gridx = 3;
		gridbag1.anchor = GridBagConstraints.EAST;
		gridBagLayout1.setConstraints(infinteR, gridbag1);
		this.add(infinteR);
		gridbag1.anchor = GridBagConstraints.CENTER;
		gridbag1.gridx = 1;
		gridbag1.gridy = 0;
		gridbag1.gridwidth = 3;
		gridBagLayout1.setConstraints(slider1, gridbag1);
		this.add(slider1);
		gridbag1.gridy = 1;
		gridBagLayout1.setConstraints(slider2, gridbag1);
		this.add(slider2);
		gridbag1.gridy = 2;
		gridBagLayout1.setConstraints(slider3, gridbag1);
		this.add(slider3);
		gridbag1.anchor = GridBagConstraints.NORTHWEST;
		gridbag1.gridwidth = 1;
		gridbag1.gridx = 4;
		gridbag1.gridy = 0;
		gridBagLayout1.setConstraints(evaluationRightAdjective, gridbag1);
		this.add(evaluationRightAdjective);
		gridbag1.gridy = 1;
		gridBagLayout1.setConstraints(potencyRightAdjective, gridbag1);
		this.add(potencyRightAdjective);
		gridbag1.gridy = 2;
		gridBagLayout1.setConstraints(activityRightAdjective, gridbag1);
		this.add(activityRightAdjective);
		gridbag1.anchor = GridBagConstraints.NORTHEAST;
		gridbag1.gridx = 0;
		gridbag1.gridy = 0;
		gridBagLayout1.setConstraints(evaluationLeftAdjective, gridbag1);
		this.add(evaluationLeftAdjective);
		gridbag1.gridy = 1;
		gridBagLayout1.setConstraints(potencyLeftAdjective, gridbag1);
		this.add(potencyLeftAdjective);
		gridbag1.gridy = 2;
		gridBagLayout1.setConstraints(activityLeftAdjective, gridbag1);
		this.add(activityLeftAdjective);

		// Set listeners.
		slider1
			.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {
					slider1_propertyChange(e);
				}
			});
		slider2
			.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {
					slider2_propertyChange(e);
				}
			});
		slider3
			.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent e) {
					slider3_propertyChange(e);
				}
			});
	}

	int getEpaDimension() {
		return epaDimension;
	}

	int getScaleValue() {
		return scaleValue;
	}

	void reset() {
		// Put pointers at middle and set the EPA profile to zeros.
		slider1.setScaleValue(0.0);
		slider2.setScaleValue(0.0);
		slider3.setScaleValue(0.0);
		slider1.snapToOrigin();
		slider2.snapToOrigin();
		slider3.snapToOrigin();
		slider1.setSnappingToOrigin(false);
		slider2.setSnappingToOrigin(false);
		slider3.setSnappingToOrigin(false);
		for (int i = 0; i < 3; i++) {
			profile[i] = 0;
		}
	}

	private void slider1_propertyChange(PropertyChangeEvent e) {
		profile =
			Interact.readProfile(Interact.findConcepts.profileEPA.getText());
		profile[0] = slider1.getScaleValue();
		String profileText = "";
		for (int i = 0; i < 3; i++) {
			profileText =
				profileText + Interact.formatLocaleDecimal(profile[i]) + " ";
		}
		Interact.findConcepts.profileEPA.setText(profileText);
		Interact.findConcepts.search();
	}

	private void slider2_propertyChange(PropertyChangeEvent e) {
		profile =
			Interact.readProfile(Interact.findConcepts.profileEPA.getText());
		profile[1] = slider2.getScaleValue();
		String profileText = "";
		for (int i = 0; i < 3; i++) {
			profileText =
				profileText + Interact.formatLocaleDecimal(profile[i]) + " ";
		}
		Interact.findConcepts.profileEPA.setText(profileText);
		Interact.findConcepts.search();
	}

	private void slider3_propertyChange(PropertyChangeEvent e) {
		profile =
			Interact.readProfile(Interact.findConcepts.profileEPA.getText());
		profile[2] = slider3.getScaleValue();
		String profileText = "";
		for (int i = 0; i < 3; i++) {
			profileText =
				profileText + Interact.formatLocaleDecimal(profile[i]) + " ";
		}
		Interact.findConcepts.profileEPA.setText(profileText);
		Interact.findConcepts.search();
	}

}