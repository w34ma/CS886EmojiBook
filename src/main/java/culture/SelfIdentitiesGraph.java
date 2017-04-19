package culture;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

/** Draws a chart of identities near a self-sentiment. */
public class SelfIdentitiesGraph extends Canvas {
	/*
	 * Self analysis involves four classes: SelfIdentitiesCard,
	 * IdentitiesChartSubcard, IdentitiesListsSubcard, and SelfIdentitiesGraph.
	 */
	DataList relatedIdentities;

	Dimension canvasSize;

	Shape actualizingArea;

	Image offscreenChart;

	// For portion of identity sphere displayed on screen.
	double thicknessOfSlice; 

	boolean newTap = false;

	Retrieval cumulativeDivergence;

	Vector identitySequence;

	Graphics2D g;

	Font typeface;

	FontRenderContext frc;

	public SelfIdentitiesGraph() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // End Scatter.

	private void jbInit() throws Exception {
		identitySequence = new Vector();
		double[] workProfile = { 0, 0, 0 };
		cumulativeDivergence = new Retrieval(0, "@", workProfile);

		this.addMouseListener(new Scatter_this_mouseAdapter(this));
	} // End jbInit.

	class Scatter_this_mouseAdapter extends java.awt.event.MouseAdapter {
		SelfIdentitiesGraph adaptee;

		Scatter_this_mouseAdapter(SelfIdentitiesGraph adaptee) {
			this.adaptee = adaptee;
		}

		public void mouseReleased(MouseEvent e) {
			adaptee.this_mouseReleased(e);
		}
	} // End Scatter_this_mouseAdapter.

	void this_mouseReleased(MouseEvent e) {
		Point tapAt;
		Retrieval realizedIdentity, tappedIdentity;

		clearHighlightingCodes();
		tapAt = e.getPoint(); // Point at which user clicked.
		// Put the user's tap position into the coordinates of the self space.
		tapAt.translate((int) Math.round(-(canvasSize.getWidth() / 2)),
			(int) Math.round(-(canvasSize.getHeight() / 2)));
		newTap = true;
		// Find the identity that was tapped, using the current
		// rotation of the self space.
		tappedIdentity = whatIsTappedIdentity(tapAt);
		if (tappedIdentity == null) {
			// User tapped an empty region.
			reset();
		} else {
			// Make a copy of the tapped identity and add it to the sequence.
			realizedIdentity =
				new Retrieval(tappedIdentity.D, tappedIdentity.word,
					tappedIdentity.profile);
			identitySequence.addElement(realizedIdentity);
			// Maintain current sequence size if CTRL pressed
			// while clicking mouse.
			if ((e.getModifiers() & InputEvent.CTRL_MASK) != 0) {
				identitySequence.removeElementAt(0);
			}
			// Sum the recent divergences from self.
			getCumulativePosition();
		}
		repaint();
	} // End this_mouseReleased.

	void reset() {
		double[] zeroProfile = { 0, 0, 0 };
		clearHighlightingCodes();
		identitySequence.removeAllElements();
		cumulativeDivergence.profile = zeroProfile;
		newTap = false;
	} // End reset.

	void getCumulativePosition() {
		Retrieval sequenceItem;
		double[] sum = { 0, 0, 0 };
		double d = 0;
		double selfProfile[];

		selfProfile =
			Interact.readProfile(Interact.analyzeSelf.selfEPA.getText());
		int N = identitySequence.size();
		// Sum divergences from self on each EPA dimension.
		for (int i = 0; i < N; i++) {
			sequenceItem = (Retrieval) identitySequence.elementAt(i);
			for (int epa = 0; epa < 3; epa++) {
				sum[epa] =
					sum[epa] + sequenceItem.profile[epa] - selfProfile[epa];
			}
		}
		// Compute distance of sum from self.
		for (int epa = 0; epa < 3; epa++) {
			d = d + (sum[epa]) * (sum[epa]);
		}
		d = Math.sqrt(d);
		// Set the cross-subroutine value of the cumulative divergences.
		cumulativeDivergence = new Retrieval(d, "@", sum);
	} // End getCumulativePosition.

	Retrieval whatIsTappedIdentity(Point tapAt) {
		Retrieval testIdentity, tappedIdentity;
		Data dicLine;
		double circuitRadiusInPixels, x, y, d;
		double[] profile = { 0, 0, 0 };
		double selfProfile[];
		int index;

		// Work with list of related identities whose profiles are rotated
		// deviations from self.
		testIdentity = null;
		if (tapAt == null) { return testIdentity; } 
		// Show initial presentation without highlighting. 
		// Get number of identities.
		// Ignore EPA and @ markers at end.
		int identitiesN = relatedIdentities.size() - 4; 
		// Find the tapped identity.
		circuitRadiusInPixels =
			Math.min(canvasSize.getHeight(), canvasSize.getHeight()) / 2;
		int tappedIndex = 0;
		do {
			testIdentity = (Retrieval) relatedIdentities.elementAt(tappedIndex);
			if (Math.abs(testIdentity.profile[2]) < thicknessOfSlice) {
				// This identity is within the visible cross-section
				// so it might be the tapped one.
				Rectangle2D bounds =
					typeface.getStringBounds(testIdentity.word, frc); 
				// Identity word's rectangle.
				x =
					(testIdentity.profile[0] * circuitRadiusInPixels)
					- (bounds.getWidth() / 2); // Adjust for word centering.
				y =
					testIdentity.profile[1] * circuitRadiusInPixels
					- bounds.getHeight();
				bounds.setRect(x, y, bounds.getWidth(), bounds.getHeight());
				if (bounds.contains(tapAt)) {
					break;
				}
			}
		} while (++tappedIndex < identitiesN);
		if (tappedIndex == identitiesN) {
			// No identity was clicked on.
			testIdentity = null;
			return testIdentity;
		} else {
			// Set the code for the tapped identity in the corresponding element
			// of the identities dictionary so parameter changes do not cause
			// the code to be lost.
			index = Interact.identities.getIndex(testIdentity.word);
			dicLine = (Data) Interact.identities.elementAt(index);
			dicLine.highlight = 1;
			// Create an unrotated retrieval for tapped identity.
			d = 0;
			selfProfile =
				Interact.readProfile(Interact.analyzeSelf.selfEPA.getText());
			for (int epa = 0; epa < 3; epa++) {
				if (Interact.analyzeSelf.maleRater.getState()) {
					profile[epa] = dicLine.maleEPA[epa];
				} else {
					profile[epa] = dicLine.femaleEPA[epa];
				} // End if male.
				d =
					d + (selfProfile[epa] - profile[epa])
					* (selfProfile[epa] - profile[epa]);
			} // End for.
			tappedIdentity = new Retrieval(Math.sqrt(d), dicLine.word, profile);
		} // End if tappedIndex.
		return tappedIdentity;
	} // End whatIsTappedIdentity.

	void processGraphicElements() {
		double circuitRadiusInPixels, actualizingRadiusInPixels;

		double smallestBoxDimension =
			Math.min(canvasSize.getHeight(), canvasSize.getHeight());
		circuitRadiusInPixels = smallestBoxDimension / 2;
		actualizingRadiusInPixels =
			circuitRadiusInPixels * Interact.analyzeSelf.actualizingRadius
			/ Interact.analyzeSelf.circuitRadius;
		AffineTransform originInMiddle =
			AffineTransform.getTranslateInstance(canvasSize.width / 2,
				canvasSize.height / 2);

		if (newTap) {
			// Set highlight codes for all identities
			selectIdentities(newTap, cumulativeDivergence);
			setHighlights(circuitRadiusInPixels, actualizingRadiusInPixels, g,
				typeface, frc, originInMiddle);
			newTap = false;
		}
		// Get the identities to display, implementing user's choice of rater
		// sex, filters, rotation.
		selectIdentities(newTap, cumulativeDivergence);
		// Call the routine to set highlighting codes.
		setHighlights(circuitRadiusInPixels, actualizingRadiusInPixels, g,
			typeface, frc, originInMiddle);

	} // End processGraphicElements

	void setHighlights(
		double circuitRadiusInPixels, double actualizingRadiusInPixels,
		Graphics2D g, Font typeface, FontRenderContext frc, AffineTransform at) {
		Retrieval testIdentity;
		Data dicLine;
		double x, y, z, tx, ty, tz;
		int index;
		double rescalingConstant = 1 / Interact.analyzeSelf.circuitRadius;

		if (!newTap) { return; } 
		// No highlighting without tap. 
		// Change coordinates of cumulative position to proportions, 
		// then to pixels.
		x =
			cumulativeDivergence.profile[0] * rescalingConstant
			* circuitRadiusInPixels;
		y =
			cumulativeDivergence.profile[1] * rescalingConstant
			* circuitRadiusInPixels;
		z =
			cumulativeDivergence.profile[2] * rescalingConstant
			* circuitRadiusInPixels;
		// Now find the complements of the experienced divergence.
		// Ignore EPA markers at end.
		int identitiesN = relatedIdentities.size() - 3; 
		int i = 0;
		do {
			// Consider an identity, with coordinates already translated and
			// expressed as proportions.
			testIdentity = (Retrieval) relatedIdentities.elementAt(i);
			if (testIdentity.word.equalsIgnoreCase("@")) {
				break;
			} // The cumulation marker at end.
			index = Interact.identities.getIndex(testIdentity.word);
			dicLine = (Data) Interact.identities.elementAt(index);
			if (dicLine.highlight != 1) {
				// This is not the tapped identity, so see if it is a
				// complementary identity.
				// Get its coordinates.
				tx = testIdentity.profile[0] * circuitRadiusInPixels;
				ty = testIdentity.profile[1] * circuitRadiusInPixels;
				tz = testIdentity.profile[2] * circuitRadiusInPixels;
				// See if position of sum of tapped and test identity
				// discrepancies from self is within actualizing radius.
				if (Math.sqrt(x * x + 2 * x * tx + tx * tx + y * y + 2 * y * ty
					+ ty * ty + z * z + 2 * z * tz + tz * tz) < actualizingRadiusInPixels) {
					// This identity is a complement. Set the highlighting code
					// for a complement.
					dicLine.highlight = -1;
				} else {
					dicLine.highlight = 0;
				}
			}
		} while (++i < identitiesN);
	} // End setHighlights.

	void selectIdentities(boolean newTap, Retrieval cumulativePosition) {
		DataList dic = Interact.identities;
		double[] selfProfile;
		double[] workProfile;
		double[] revisedProfile;
		boolean male;
		double xyRotationRadians, xzRotationRadians, yzRotationRadians;
		boolean[] complexFilter = { false, false, false };
		boolean[] pairFilter = new boolean[Data.NUMBER_PAIR_CONCEPT_GATES];
		boolean[] divisionFilter =
			new boolean[Data.NUMBER_DIVISION_CONCEPT_GATES];
		DataList matches;
		Retrieval markerLine;
		Retrieval resultLine, transformedLine;

		// Read parameters set by user.
		selfProfile =
			Interact.readProfile(Interact.analyzeSelf.selfEPA.getText());
		male = Interact.analyzeSelf.maleRater.getState();
		xyRotationRadians =
			Math.PI
			* (double) Interact.analyzeSelf.identitiesChart.ScrollbarRotateXY
			.getValue()
			/ Interact.analyzeSelf.identitiesChart.ScrollbarRotateXY
			.getMaximum();
		xzRotationRadians =
			Math.PI
			* (double) Interact.analyzeSelf.identitiesChart.ScrollbarRotateXZ
			.getValue()
			/ Interact.analyzeSelf.identitiesChart.ScrollbarRotateXZ
			.getMaximum();
		yzRotationRadians =
			-Math.PI
			* (double) Interact.analyzeSelf.identitiesChart.ScrollbarRotateYZ
			.getValue()
			/ Interact.analyzeSelf.identitiesChart.ScrollbarRotateYZ
			.getMaximum();
		int endPairFilters = Data.NUMBER_PAIR_CONCEPT_GATES;
		int endDivisionFilters =
			Data.NUMBER_PAIR_CONCEPT_GATES + Data.NUMBER_DIVISION_CONCEPT_GATES;
		for (int i = 0; i < endPairFilters; i++) {
			pairFilter[i] =
				Interact.analyzeSelf.identitiesChart.conceptGateCheckbox[i]
				                                                         .getState();
		}
		for (int i = endPairFilters; i < endDivisionFilters; i++) {
			divisionFilter[i - endPairFilters] =
				Interact.analyzeSelf.identitiesChart.conceptGateCheckbox[i]
				                                                         .getState();
		}
		if (newTap) {
			// If user just clicked on chart, process all identities without
			// conceptGating. (Ordinarily, processing is confined to
			// identities called for by parameters.)
			pairFilter = EventRecord.ALL_TRUE_PAIR_CONCEPT_GATE;
			divisionFilter = EventRecord.ALL_TRUE_DIVISION_CONCEPT_GATE;
		}

		// Get the identities sorted by distance.
		matches =
			dic.getMatches(selfProfile, male, pairFilter, divisionFilter,
				complexFilter);
		// Add cumulative position.
		double offset = Interact.analyzeSelf.circuitRadius;
		workProfile =
			new double[] { selfProfile[0] + cumulativePosition.profile[0],
			selfProfile[1] + cumulativePosition.profile[1],
			selfProfile[2] + cumulativePosition.profile[2] };
		markerLine = new Retrieval(-1, "@", workProfile); 
		// Minus d to mark extra chart element.
		matches.addElement(markerLine);
		// Add EPA markers.
		workProfile =
			new double[] { offset + selfProfile[0] - .05, selfProfile[1],
			selfProfile[2] };
		markerLine = new Retrieval(-1, "E", workProfile); 
		// Minus d to mark extra chart element.
		matches.addElement(markerLine);
		workProfile =
			new double[] { selfProfile[0], offset + selfProfile[1] - .05,
			selfProfile[2] };
		markerLine = new Retrieval(-1, "P", workProfile); 
		// Minus d to mark extra chart element.
		matches.addElement(markerLine);
		workProfile =
			new double[] { selfProfile[0], selfProfile[1],
			offset + selfProfile[2] - .05 };
		markerLine = new Retrieval(-1, "A", workProfile); 
		// Minus d to mark extra chart element.
		matches.addElement(markerLine);
		// Change EPA profiles to discrepancies from self
		// and rotate per scrollbars.
		relatedIdentities = new DataList();
		int index = 0;
		do {
			resultLine = (Retrieval) matches.elementAt(index);
			revisedProfile =
				translateAndRotate(resultLine.profile, selfProfile,
					xyRotationRadians, xzRotationRadians, yzRotationRadians);
			// Avoid assignments-by-reference so as to not change dictionary.
			transformedLine =
				new Retrieval(resultLine.D, resultLine.word, revisedProfile);
			transformedLine.highlight = resultLine.highlight;
			relatedIdentities.addElement(transformedLine);
		} while (++index < (matches.size()));
	} // End selectIdentities.

	double[] translateAndRotate(
		double[] inProfile, double[] selfProfile, double xyRotationRadians,
		double xzRotationRadians, double yzRotationRadians) {
		// Perform translations and rotations for in-profile.
		double[] translatedProfile = new double[3];
		double[] outProfile = new double[3];
		double rotation_1_E, rotation_2_E, rotation_1_P, rotation_2_P, rotation_1_A, rotation_2_A;
		// Translate the identity profile, and rescale each value into a
		// proportion of the radius of the visible circle.
		translatedProfile[0] =
			(inProfile[0] - selfProfile[0])
			/ Interact.analyzeSelf.circuitRadius;
		translatedProfile[1] =
			(inProfile[1] - selfProfile[1])
			/ Interact.analyzeSelf.circuitRadius;
		translatedProfile[2] =
			(inProfile[2] - selfProfile[2])
			/ Interact.analyzeSelf.circuitRadius;
		// Rotate.
		rotation_1_E =
			translatedProfile[0] * Math.cos(xyRotationRadians)
			- translatedProfile[1] * Math.sin(xyRotationRadians);
		rotation_1_P =
			translatedProfile[0] * Math.sin(xyRotationRadians)
			+ translatedProfile[1] * Math.cos(xyRotationRadians);
		rotation_2_E =
			rotation_1_E * Math.cos(xzRotationRadians) - translatedProfile[2]
			                                                               * Math.sin(xzRotationRadians);
		rotation_1_A =
			rotation_1_E * Math.sin(xzRotationRadians) + translatedProfile[2]
			                                                               * Math.cos(xzRotationRadians);
		rotation_2_P =
			rotation_1_P * Math.cos(yzRotationRadians) - rotation_1_A
			* Math.sin(yzRotationRadians);
		rotation_2_A =
			rotation_1_P * Math.sin(yzRotationRadians) + rotation_1_A
			* Math.cos(yzRotationRadians);
		outProfile[0] = rotation_2_E;
		outProfile[1] = rotation_2_P;
		outProfile[2] = rotation_2_A;
		return outProfile;
	} // End translateAndRotate.

	void clearHighlightingCodes() {
		// Remove old highlighting codes.
		for (int i = 0; i < Interact.identities.size(); i++) {
			((Data) Interact.identities.elementAt(i)).highlight = 0;
		}
	} // End clearHighlightingCodes.

	public void update(Graphics g) {
		paint(g);
	} // Override clearing in order to stop flickering.

	public void paint(Graphics gBase) {
		// Double-buffered drawing to prevent flickering.
		canvasSize = getSize();
		processGraphicElements();
		// Create the offscreen chart.
		if (offscreenChart == null
				|| offscreenChart.getWidth(null) != canvasSize.width
				|| offscreenChart.getHeight(null) != canvasSize.height) {
			offscreenChart = createImage(canvasSize.width, canvasSize.height);
		}
		// Clear the offscreen chart.
		Graphics offscreenG = offscreenChart.getGraphics();
		offscreenG.setColor(getBackground());
		offscreenG.fillRect(0, 0, canvasSize.width, canvasSize.height);
		// Draw the offscreen chart.
		paintOffscreen(offscreenG);
		// Put the offscreen chart on screen.
		gBase.drawImage(offscreenChart, 0, 0, null);
	} // End paint.

	void paintOffscreen(Graphics gBase) {
		double circuitRadiusInPixels, actualizingRadiusInPixels;
		double smallestBoxDimension;
		double x, y, position;
		TextLayout wordTextLayout;
		double rescalingConstant = 1 / Interact.analyzeSelf.circuitRadius;
		Rectangle2D bounds;
		Retrieval resultRecord;
		Shape selfPoint, circuitArea, canvasArea;
		thicknessOfSlice =
			Interact.analyzeSelf.actualizingRadius
			/ Interact.analyzeSelf.circuitRadius;
		// Set up 2D graphics.
		g = (Graphics2D) gBase;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_OFF);
		typeface = new Font("Serif", Font.PLAIN, 9);
		g.setFont(typeface);
		frc = g.getFontRenderContext();
		// Transform the origin to the center of the window.
		AffineTransform originInMiddle =
			AffineTransform.getTranslateInstance(canvasSize.width / 2,
				canvasSize.height / 2);
		g.transform(originInMiddle);
		smallestBoxDimension =
			Math.min(canvasSize.getHeight(), canvasSize.getHeight());
		circuitRadiusInPixels = smallestBoxDimension / 2;
		// Color background gray.
		canvasArea =
			new Rectangle2D.Double(-circuitRadiusInPixels,
				-circuitRadiusInPixels, canvasSize.getHeight(),
				canvasSize.getHeight());
		g.setPaint(Color.LIGHT_GRAY);
		g.fill(canvasArea);
		// Show the circuit area.
		circuitArea =
			new Ellipse2D.Double(-circuitRadiusInPixels,
				-circuitRadiusInPixels, 2 * circuitRadiusInPixels,
				2 * circuitRadiusInPixels);
		g.setPaint(Color.yellow);
		g.fill(circuitArea);
		// Draw sector lines.
		Point2D middle, top1, top2, top3, top4, bottom1, bottom2, bottom3, bottom4;
		Line2D topLine1, topLine2, topLine3, topLine4, bottomLine1, bottomLine2, bottomLine3, bottomLine4;
		middle = new Point2D.Double(0, 0);
		top1 =
			new Point2D.Double(circuitRadiusInPixels * Math.cos(Math.PI / 8),
				-circuitRadiusInPixels * Math.sin(Math.PI / 8));
		top2 =
			new Point2D.Double(circuitRadiusInPixels
				* Math.cos(3 * Math.PI / 8), -circuitRadiusInPixels
				* Math.sin(3 * Math.PI / 8));
		top3 =
			new Point2D.Double(circuitRadiusInPixels
				* Math.cos(5 * Math.PI / 8), -circuitRadiusInPixels
				* Math.sin(5 * Math.PI / 8));
		top4 =
			new Point2D.Double(circuitRadiusInPixels
				* Math.cos(7 * Math.PI / 8), -circuitRadiusInPixels
				* Math.sin(7 * Math.PI / 8));
		bottom1 =
			new Point2D.Double(circuitRadiusInPixels * Math.cos(Math.PI / 8),
				circuitRadiusInPixels * Math.sin(Math.PI / 8));
		bottom2 =
			new Point2D.Double(circuitRadiusInPixels
				* Math.cos(3 * Math.PI / 8), circuitRadiusInPixels
				* Math.sin(3 * Math.PI / 8));
		bottom3 =
			new Point2D.Double(circuitRadiusInPixels
				* Math.cos(5 * Math.PI / 8), circuitRadiusInPixels
				* Math.sin(5 * Math.PI / 8));
		bottom4 =
			new Point2D.Double(circuitRadiusInPixels
				* Math.cos(7 * Math.PI / 8), circuitRadiusInPixels
				* Math.sin(7 * Math.PI / 8));
		topLine1 = new Line2D.Double(top1, middle);
		topLine2 = new Line2D.Double(top2, middle);
		topLine3 = new Line2D.Double(top3, middle);
		topLine4 = new Line2D.Double(top4, middle);
		bottomLine1 = new Line2D.Double(bottom1, middle);
		bottomLine2 = new Line2D.Double(bottom2, middle);
		bottomLine3 = new Line2D.Double(bottom3, middle);
		bottomLine4 = new Line2D.Double(bottom4, middle);
		g.setPaint(Color.white);
		g.draw(topLine1);
		g.draw(topLine2);
		g.draw(topLine3);
		g.draw(topLine4);
		g.draw(bottomLine1);
		g.draw(bottomLine2);
		g.draw(bottomLine3);
		g.draw(bottomLine4);
		// Show the actualizing area.
		actualizingRadiusInPixels =
			circuitRadiusInPixels * Interact.analyzeSelf.actualizingRadius
			/ Interact.analyzeSelf.circuitRadius;
		actualizingArea =
			new Ellipse2D.Double(-actualizingRadiusInPixels,
				-actualizingRadiusInPixels, 2 * actualizingRadiusInPixels,
				2 * actualizingRadiusInPixels);
		g.setPaint(Color.CYAN);
		g.fill(actualizingArea);
		// Draw the self point.
		selfPoint = new Ellipse2D.Double(-3, -3, 6, 6);
		g.setPaint(Color.blue);
		g.fill(selfPoint);
		// Plot the identities.
		g.setPaint(Color.black);
		int index = 0;
		do {
			resultRecord = (Retrieval) relatedIdentities.elementAt(index);
			if (resultRecord.word.equalsIgnoreCase("@")
					& (identitySequence.size() <= 1)) {
				continue; // Don't show cumulative position unless based on
				// multiple identities.
			}
			if (Math.abs(resultRecord.profile[2]) < thicknessOfSlice) {
				// This identity is within cross-section.
				if (resultRecord.D * rescalingConstant * circuitRadiusInPixels <= circuitRadiusInPixels) {
					// This identity is within circuiting area.
					bounds =
						g.getFont().getStringBounds(resultRecord.word, frc);
					x =
						(resultRecord.profile[0] * circuitRadiusInPixels)
						- (bounds.getWidth() / 2); // Center word on point.
					y = resultRecord.profile[1] * circuitRadiusInPixels;
					// Set up a textLayout for highlighting.
					wordTextLayout =
						new TextLayout(resultRecord.word, typeface, frc);
					g.setPaint(Color.black);
					// Black for identities that are neither tapped nor
					// complements.
					int pointer =
						Interact.identities.getIndex(resultRecord.word);
					int highLightValue = 0;
					if (pointer >= 0) {
						highLightValue =
							((Data) Interact.identities.elementAt(pointer)).highlight;
					} else {
						highLightValue = -2;
					}
					if ((highLightValue != 0)) {
						// This identity needs highlighting; get the
						// highlighting rectangle.
						Shape base =
							wordTextLayout.getLogicalHighlightShape(0,
								resultRecord.word.length());
						// Move the rectangle to required coordinates.
						AffineTransform at =
							AffineTransform.getTranslateInstance(x, y);
						Shape highlight = at.createTransformedShape(base);
						// Paint the backgound color.
						if (highLightValue > 0) {
							// Red shows this is the tapped identity.
							g.setPaint(Color.RED);
							g.fill(highlight);
							g.setPaint(Color.BLACK); // Color for text.
						} else if (highLightValue == -1) {
							// Black shows this is a complement of the tapped
							// identity.
							g.setPaint(Color.BLACK);
							g.fill(highlight);
							g.setPaint(Color.WHITE); // Color for  text.
						} else { // Markers for E, P, A, and @.
							g.setPaint(Color.BLACK);
						}
					}
					// Use gray text for identities in actualizing sphere.
					position =
						resultRecord.D * rescalingConstant
						* circuitRadiusInPixels;
					if ((position <= actualizingRadiusInPixels)
							& (position >= 0)) {
						if (highLightValue == -1){
							g.setPaint(Color.WHITE);
						} else { // Item is not highlighted.
							g.setPaint(Color.DARK_GRAY);
						}
					}
					// Print the word.
					wordTextLayout.draw(g, (float) x, (float) y);
				}
			}
		} while (++index < (relatedIdentities.size()));
	} // End paintOffscreen.

} // End Scatter.

