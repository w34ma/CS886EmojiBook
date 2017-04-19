package culture;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.beans.*;
import java.text.*;

/** A graphic scale with a slider to position. */

public class Slider extends Canvas implements MouseListener,
		MouseMotionListener {

	private static final int DEFAULT_padding = 1;// 20;

	private static final int DEFAULT_VERTICAL_SPACER = 4;

	private static final int DEFAULT_pointerTop = 0;

	private static final int DEFAULT_pointerBottom = 14;

	private static final int DEFAULT_pointerHalfWidth = 7;

	// Total width of scale = ratingRange + pointerWidth
	private static final int DEFAULT_ratingRange = 430; 

	private static final int DEFAULT_minorTickLength = 4;

	private static final int DEFAULT_majorTickLength = 6;

	private static final double DEFAULT_infinityMultiplier = 1.3;

	private int ratingRange = DEFAULT_ratingRange; // Property.

	private int minRatingPixel, maxRatingPixel;

	private int pointerTop = DEFAULT_pointerTop + DEFAULT_VERTICAL_SPACER;

	private int pointerBottom = DEFAULT_pointerBottom + DEFAULT_VERTICAL_SPACER;

	private int pointerHalfWidth = DEFAULT_pointerHalfWidth;

	private Rectangle pointerArea;

	private int verticalSpacer = DEFAULT_VERTICAL_SPACER;

	private boolean zeroAtMiddle = true; // Property.

	private int unitInterval, zeroOffset;

	private double infinityMultiplier = DEFAULT_infinityMultiplier;

	private int x[] = new int[11]; // Tick positions.

	private int numberOfTicks;

	private int startoffTickAt, rightmostTickAt, pointerAt, cursorAt;

	private int majorTickLength = DEFAULT_majorTickLength; // Property.

	private int minorTickLength = DEFAULT_minorTickLength; // Property.

	private boolean showingTicks = true; // Property.

	private boolean showingDigitLabels = true; // Property.

	private boolean reflectedScale = false; // Property.

	private boolean foundPointer = false;

	private boolean clickedOnPointer = false;

	private boolean sliderMoved = false;

	private boolean erasePointer = false;

	private boolean snappingToOrigin = true;

	private transient PropertyChangeSupport propertyChangeListeners =
		new PropertyChangeSupport(this);

	private double scaleValue;

	Locale local;

	public Slider() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		addMouseListener(this);
		addMouseMotionListener(this);

		// Compute the tick mark positions, that define the measurement scale.
		x[0] = DEFAULT_padding + pointerHalfWidth;

		verticalSpacer = (int) getMajorTickLength() / 2;
		setZeroAtMiddle(zeroAtMiddle); // Set up one of the two forms of slider.
		pointerAt = startoffTickAt;
		pointerArea =
			new Rectangle(2 * pointerHalfWidth, pointerBottom - pointerTop);

		setCursor(Cursor.getDefaultCursor());
	} // End jbInit.

	public Dimension getPreferredSize() {
		int canvasWidth, canvasHeight;
		canvasWidth = rightmostTickAt + pointerHalfWidth + DEFAULT_padding;
		canvasHeight = pointerBottom + 2 * verticalSpacer;
		if (showingTicks) {
			canvasHeight = canvasHeight + verticalSpacer + getMajorTickLength();
		}
		if (showingDigitLabels) {
			int letterHeight =
				this.getGraphics().getFontMetrics(this.getFont()).getHeight();
			canvasHeight = canvasHeight + verticalSpacer + letterHeight;
		}
		return new Dimension(canvasWidth, canvasHeight);
	} // End getPreferredSize.

	public synchronized void paint(Graphics g) {
		int[] pointerX = { 0, 0, 0, 0, 0 };
		int[] pointerY = { 0, 0, 0, 0, 0 };
		int[] slideBarX = { 0, 0, 0, 0 };
		int[] slideBarY = { 0, 0, 0, 0 };

		// Draw the pointer.
		if (erasePointer) {
			g.setColor(this.getBackground());
		} else {
			g.setColor(Color.darkGray);
		}
		pointerX[0] = pointerAt - pointerHalfWidth;
		pointerX[1] = pointerAt - pointerHalfWidth;
		pointerX[2] = pointerAt;
		pointerX[3] = pointerAt + pointerHalfWidth;
		pointerX[4] = pointerAt + pointerHalfWidth;
		pointerY[0] = pointerTop;
		pointerY[1] = pointerTop + (pointerBottom - pointerTop) / 2;
		pointerY[2] = pointerBottom;
		pointerY[3] = pointerTop + (pointerBottom - pointerTop) / 2;
		pointerY[4] = pointerTop;
		g.fillPolygon(pointerX, pointerY, 5);
		pointerArea.setLocation(pointerX[0], pointerY[0]);
		if (erasePointer) { return; }

		// Draw the slide bar.
		g.setColor(Color.lightGray);
		// Slide bar's top is one pixel below top of pointer.
		// Slide bar's thickness fits pointer's rectangular part.
		int slideBarTop = pointerTop + 1;
		int slideBarThickness = ((pointerBottom - pointerTop) / 2) - 1;
		slideBarY[0] = slideBarTop;
		slideBarY[1] = slideBarTop;
		slideBarY[2] = slideBarTop + slideBarThickness;
		slideBarY[3] = slideBarTop + slideBarThickness;
		// Slide bar to the left of the pointer.
		slideBarX[0] = pointerAt - pointerHalfWidth;
		slideBarX[1] = x[0] - pointerHalfWidth - 1;
		slideBarX[2] = x[0] - pointerHalfWidth - 1;
		slideBarX[3] = pointerAt - pointerHalfWidth;
		g.fillPolygon(slideBarX, slideBarY, 4);
		g.setColor(Color.black);
		g.drawPolyline(slideBarX, slideBarY, 4);
		g.setColor(Color.lightGray);
		// Slide bar to the right of the pointer.
		slideBarX[0] = pointerAt + pointerHalfWidth;
		slideBarX[1] = rightmostTickAt + pointerHalfWidth;
		slideBarX[2] = rightmostTickAt + pointerHalfWidth;
		slideBarX[3] = pointerAt + pointerHalfWidth;
		g.fillPolygon(slideBarX, slideBarY, 4);
		g.setColor(Color.darkGray);
		slideBarX[3] = slideBarX[3] - 1;
		g.drawPolyline(slideBarX, slideBarY, 4);

		// Draw tick marks.
		if (showingTicks) {
			int tickTop = pointerBottom + verticalSpacer;
			int minorTickBottom = tickTop + getMinorTickLength();
			int majorTickBottom = tickTop + getMajorTickLength();
			g.setColor(Color.darkGray);
			for (int j = 0; j <= numberOfTicks - 1; j++) {
				if ((j == 0) || ((j == 4) && zeroAtMiddle)
					|| (j == (numberOfTicks - 1))) {
					g.drawLine(x[j], tickTop, x[j], majorTickBottom);
				} else {
					g.drawLine(x[j], tickTop, x[j], minorTickBottom);
				}
			}
		}

		// Write numeric labels.
		if (showingDigitLabels) {
			int integerValue, sign, rightmostLabelIndex, zeroIndex;
			double delta, decimalValue;
			String digitString;
			int digitTop, digitHeight, digitBottom, halfDigitWidth;

			local = this.getLocale();
			NumberFormat localeNumberFormat =
				NumberFormat.getNumberInstance(local);
			DecimalFormat localeDecimal = (DecimalFormat) localeNumberFormat;
			localeDecimal.applyPattern("##0.#");

			digitTop =
				pointerBottom + getMajorTickLength() + 2 * verticalSpacer;
			FontMetrics fontM = g.getFontMetrics();
			digitHeight = fontM.getHeight();
			digitBottom = digitTop + digitHeight;
			zeroIndex = -1; // 1-10 interval-spaced tickmarks.
			if (zeroAtMiddle) {
				zeroIndex = 4;
			} // Semantic differential codings.
			rightmostLabelIndex = numberOfTicks - 1;
			for (int j = 0; j <= rightmostLabelIndex; j++) {
				integerValue = j - zeroIndex;
				if (reflectedScale) {
					integerValue = -integerValue;
				}
				digitString = localeDecimal.format((double) integerValue);
				if (zeroAtMiddle) {
					if ((j == 0) || (j == rightmostLabelIndex)) { 
						// Endpoints have decimal values.
						sign =
							(int) ((j - zeroIndex) / Math.abs(j - zeroIndex));
						delta = (double) infinityMultiplier - 1;
						decimalValue = (double) (j - zeroIndex + sign * delta);
						if (reflectedScale) {
							decimalValue = -decimalValue;
						}
						digitString = localeDecimal.format(decimalValue);
					}
				} else {
					if (reflectedScale) {
						integerValue = rightmostLabelIndex + 1 - j;
					}
					digitString = localeDecimal.format((double) integerValue);
				}
				halfDigitWidth = (int) fontM.stringWidth(digitString) / 2;
				g.drawString(digitString, x[j] - halfDigitWidth, digitBottom);
			}
		}

		g.setColor(Color.black);
	} // End paint.

	public void setShowingTicks(boolean newShowingTicks) {
		showingTicks = newShowingTicks;
		if (!showingTicks) {
			setShowingDigitLabels(false);
		}
	}

	public boolean isShowingTicks() {
		return showingTicks;
	}

	public void setShowingDigitLabels(boolean newShowingDigitLabels) {
		showingDigitLabels = newShowingDigitLabels;
	}

	public boolean isShowingDigitLabels() {
		return showingDigitLabels;
	}

	public void setReflectedScale(boolean newReflectedScale) {
		reflectedScale = newReflectedScale;
	}

	public boolean isReflectedScale() {
		return reflectedScale;
	}

	public void setZeroAtMiddle(boolean newZeroAtMiddle) {
		zeroAtMiddle = newZeroAtMiddle;
		if (zeroAtMiddle) {
			// Define a semantic differential scale with nine adverbs and zero
			// at middle.
			// The scale has eight intervals, but the two end intervals are 1.3
			// units wide.
			numberOfTicks = 9;
			unitInterval = (int) Math.round(ratingRange / 8.6);
			x[1] = (int) Math.round(x[0] + infinityMultiplier * unitInterval);
			for (int i = 2; i <= 7; i++) {
				x[i] = x[i - 1] + unitInterval;
			}
			x[8] = (int) Math.round(x[7] + infinityMultiplier * unitInterval);
			startoffTickAt = x[4];
			zeroOffset = x[4] - x[0];
		} else {
			// Define a 1-10 equal interval scale increasing from leftmost tick.
			numberOfTicks = 10;
			unitInterval = (int) Math.round(ratingRange / 9.0);
			for (int i = 1; i <= 9; i++) {
				x[i] = x[i - 1] + unitInterval;
			}
			startoffTickAt = x[0];
			zeroOffset = -unitInterval;
		}
		rightmostTickAt = x[numberOfTicks - 1];
		minRatingPixel = x[0];
		maxRatingPixel = x[numberOfTicks - 1];
		ratingRange = maxRatingPixel - minRatingPixel;
		pointerAt = startoffTickAt;
	}

	public boolean isZeroAtMiddle() {
		return zeroAtMiddle;
	}

	public void setMajorTickLength(int newMajorTickLength) {
		majorTickLength = newMajorTickLength;
		verticalSpacer = (int) newMajorTickLength / 2;
	}

	public int getMajorTickLength() {
		return majorTickLength;
	}

	public void setMinorTickLength(int newMinorTickLength) {
		minorTickLength = newMinorTickLength;
	}

	public int getMinorTickLength() {
		return minorTickLength;
	}

	public void setRatingRange(int newRatingRange) {
		ratingRange = newRatingRange;
		setZeroAtMiddle(zeroAtMiddle);
	}

	public int getRatingRange() {
		return ratingRange;
	}

	public void setScaleValue(double newScaleValue) {
		double oldScaleValue = -99.0; 
			// Must be different from newScaleValue to fire property change.
		scaleValue = newScaleValue;
		propertyChangeListeners.firePropertyChange("scaleValue", new Double(
			oldScaleValue), new Double(newScaleValue));
	}

	public double getScaleValue() {
		return scaleValue;
	}

	public synchronized void removePropertyChangeListener(
		PropertyChangeListener l) {
		propertyChangeListeners.removePropertyChangeListener(l);
	}

	public synchronized
		void addPropertyChangeListener(PropertyChangeListener l) {
		propertyChangeListeners.addPropertyChangeListener(l);
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (foundPointer) {
			clickedOnPointer = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (sliderMoved) {
			// Compute the decimal scale value of the pointer's final postion,
			// minus spacer pixels to the left of the minimum rating point, and
			// minus the number of pixels to the scale zero.
			// Divide this number of pixels from zero by the number of pixels
			// per scale unit.
			// Then round the result to two places.
			double rating =
				(double) (pointerAt - minRatingPixel - zeroOffset)
					/ unitInterval;
			rating = (double) Math.round(100 * rating) / 100;
			if (reflectedScale) {
				if (zeroAtMiddle) {
					rating = -rating;
				} else {
					rating = (double) numberOfTicks + 1 - rating;
				}
			}
			setScaleValue(rating);
		}
		if (snappingToOrigin) {
			snapToOrigin(); // Reset for next rating.
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
		if (snappingToOrigin) {
			snapToOrigin();
		}
	}

	public void mouseDragged(MouseEvent e) {
		// Move the pointer with dragging.
		cursorAt = e.getX();
		if (clickedOnPointer) { // Make sure the pointer is being dragged.
			// Don't let it be dragged off the scale.
			if (cursorAt < minRatingPixel) {
				cursorAt = minRatingPixel;
			}
			if (cursorAt > maxRatingPixel) {
				cursorAt = maxRatingPixel;
			}
			// Erase the existing pointer.
			erasePointer = true;
			paint(this.getGraphics());
			// Redraw the scale with pointer at the new location.
			erasePointer = false;
			pointerAt = cursorAt;
			paint(this.getGraphics());
			// Signal that the user moved the pointer.
			sliderMoved = true;
		}
	}

	public void mouseMoved(MouseEvent e) {
		// Change between default and hand cursors.
		cursorAt = e.getX();
		if (pointerArea.contains(cursorAt, e.getY())) {
			// Hand cursor if over the scale pointer.
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			foundPointer = true;
		} else {
			// Default cursor otherwise.
			setCursor(Cursor.getDefaultCursor());
			foundPointer = false;
		}
	}

	public void setSnappingToOrigin(boolean newSnappingToOrigin) {
		snappingToOrigin = newSnappingToOrigin;
	}

	public boolean isSnappingToOrigin() {
		return snappingToOrigin;
	}

	public void snapToOrigin() {
		// Reset.
		clickedOnPointer = false;
		foundPointer = false;
		sliderMoved = false;
		setCursor(Cursor.getDefaultCursor());
		erasePointer = true;
		paint(this.getGraphics());
		pointerAt = startoffTickAt;
		erasePointer = false;
		paint(this.getGraphics());
	}

	public boolean isSliderMoved() {
		return sliderMoved;
	}

} // End of Slider.java.