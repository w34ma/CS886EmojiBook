package culture;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ItemEvent;

/*
 Self analysis involves four classes: SelfIdentitiesCard, 
 IdentitiesChartSubcard, IdentitiesListsSubcard, and SelfIdentitiesGraph.
 */
public class IdentitiesChartSubcard extends Panel {
	SelfIdentitiesGraph viewIdentitiesScatter = new SelfIdentitiesGraph();

	Panel instrumentsPanel = new Panel();

	Panel dataSelectionPanel = new Panel();

	Panel rotationPanel = new Panel();

	Panel conceptGatesPanel = new Panel();

	Checkbox[] conceptGateCheckbox =
		new Checkbox[Data.NUMBER_OF_CONCEPT_GATES_USED];

	Scrollbar ScrollbarRotateXY = new Scrollbar();

	Scrollbar ScrollbarRotateXZ = new Scrollbar();

	Scrollbar ScrollbarRotateYZ = new Scrollbar();

	Label conceptGateTitle = new Label();

	BorderLayout borderLayout1 = new BorderLayout();

	BorderLayout borderLayout2 = new BorderLayout();

	VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();

	VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();

	GridLayout gridLayout1 = new GridLayout(1, 3, 2, 0);

	public IdentitiesChartSubcard() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		instrumentsPanel.setLayout(borderLayout2);
		rotationPanel.setLayout(gridLayout1);
		conceptGatesPanel.setLayout(verticalFlowLayout1);
		conceptGateTitle.setText(Interact.InteractText
			.getString("conceptGates"));
		dataSelectionPanel.setLayout(verticalFlowLayout2);
		ScrollbarRotateXY.setVisibleAmount(2);
		ScrollbarRotateXZ.setVisibleAmount(2);
		ScrollbarRotateYZ.setVisibleAmount(2);
		this.add(viewIdentitiesScatter, BorderLayout.CENTER);
		this.add(instrumentsPanel, BorderLayout.EAST);
		this.add(conceptGatesPanel, BorderLayout.WEST);
		instrumentsPanel.add(dataSelectionPanel, BorderLayout.NORTH);
		instrumentsPanel.add(rotationPanel, BorderLayout.CENTER);
		rotationPanel.add(ScrollbarRotateXY, null);
		rotationPanel.add(ScrollbarRotateXZ, null);
		rotationPanel.add(ScrollbarRotateYZ, null);

		for (int i = 0; i < 2 + Data.NUMBER_DIVISION_CONCEPT_GATES; i++) {
			if (i == 0) {
				conceptGatesPanel.add(conceptGateTitle);
			}
			if (i == 2) {
				conceptGatesPanel.add(new Panel());
			}
			conceptGateCheckbox[i] =
				new Checkbox(Interact.identityConceptGateLines[i]);
			conceptGateCheckbox[i].setState(false);
			conceptGatesPanel.add(conceptGateCheckbox[i], null);
		}
		conceptGateCheckbox[0].setState(true); // Open male conceptGate.
		conceptGateCheckbox[2].setState(true); // Open lay conceptGate.

		for (int i = 0; i < 2 + Data.NUMBER_DIVISION_CONCEPT_GATES; i++) {
			conceptGateCheckbox[i]
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						parameterChanged();
					}
				});
		}
		ScrollbarRotateXY
			.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent e) {
					parameterChanged();
				}
			});
		ScrollbarRotateXZ
			.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent e) {
					parameterChanged();
				}
			});
		ScrollbarRotateYZ
			.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent e) {
					parameterChanged();
				}
			});
	} // End jbInit.

	void parameterChanged() {
		viewIdentitiesScatter.repaint();
	} // End parameterChanged.

} // End IdentitiesChartSubcard.
