package culture;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

/** Draws faces with emotional expressions for events. */
public class Face extends Canvas {

	static final int HALFWIDTH = 50;

	static final int HEIGHT = 135;

	static final int LENGTH = HALFWIDTH * HEIGHT;

	public static final double[] BLANKING_PROFILE = { Float.MAX_VALUE,
		Float.MAX_VALUE, Float.MAX_VALUE };

	static final int NUMBER_OF_FACES = 6;

	static final int FIRST_MALE = 0;

	static final int SECOND_MALE = 1;

	static final int FIRST_FEMALE = 3;

	static final int SECOND_FEMALE = 4;
	
	static double AMPLIFIER = 1.0;

	int picture;

	Image left, right;

	MediaTracker tracker;

	private double[] profile = new double[3];

	private double[] amplifiedProfile = new double[3];

	int hairX[][] = {
		{ 50, 38, 34, 21, 16, 10, 4, 2, 2, 4, 8, 11, 13, 12, 11, 11, 14, 20,
			27, 40, 50 }, // male1
		{ 50, 39, 30, 22, 15, 8, 4, 2, 6, 9, 12, 13, 14, 14, 13, 14, 13, 15,
			21, 32, 40, 50 }, // male2
		{ 50, 41, 32, 24, 17, 12, 8, 6, 6, 7, 8, 9, 9, 11, 11, 13, 12, 10, 11,
			15, 17, 24, 32, 41, 50 }, // male3

		{ 50, 36, 25, 18, 9, 4, 1, 1, 3, 2, 27, 23, 23, 20, 18, 16, 13, 10, 8,
			8, 9, 13, 14, 20, 23, 31, 42, 50 }, // female1
		{ 50, 36, 25, 18, 10, 2, 0, 0, 2, 4, 17, 16, 14, 7, 5, 3, 4, 6, 10, 13,
			14, 17, 24, 35, 50 }, // female2
		{ 50, 35, 20, 10, 2, 0, 22, 22, 15, 12, 6, 3, 1, 1, 7, 8, 8, 11, 17,
			29, 38, 50 } // female3
		};

	int hairY[][] = {
		{ 0, 0, 2, 9, 17, 21, 38, 45, 55, 65, 68, 70, 65, 53, 50, 41, 30, 23,
			21, 23, 22 },
		{ 0, 0, 3, 8, 15, 26, 38, 49, 63, 68, 72, 67, 61, 54, 50, 44, 34, 29,
			22, 21, 21, 19 },
		{ 0, 1, 4, 10, 16, 23, 31, 42, 51, 56, 61, 63, 72, 72, 64, 50, 47, 42,
			35, 25, 18, 11, 5, 2, 1 },

		{ 2, 1, 7, 14, 19, 26, 48, 96, 115, 130, 130, 121, 113, 107, 103, 89,
			89, 85, 77, 69, 62, 64, 54, 45, 37, 32, 29, 31 },
		{ 0, 3, 6, 10, 14, 22, 30, 88, 90, 98, 106, 100, 89, 84, 81, 63, 60,
			60, 72, 72, 52, 46, 32, 25, 23 },
		{ 0, 0, 7, 20, 35, 64, 132, 123, 113, 99, 94, 90, 85, 64, 69, 81, 70,
			54, 42, 29, 24, 23 } };

	int browX[][] = { { 19, 25, 31, 39, 42, 37, 29 },
		{ 21, 27, 32, 39, 44, 35, 30 }, { 19, 24, 32, 41, 39, 34, 31, 25, 20 },

		{ 21, 26, 31, 37, 42, 39, 34, 29 }, { 19, 21, 29, 36, 39, 36, 33, 24 },
		{ 18, 24, 35, 40, 40, 30, 24 } };

	int browY[][] = { { 55, 51, 51, 52, 53, 57, 55 },
		{ 54, 50, 48, 49, 55, 54, 54 }, { 52, 45, 44, 47, 50, 48, 48, 50, 53 },

		{ 63, 60, 59, 59, 63, 64, 63, 62 }, { 61, 58, 57, 58, 61, 62, 61, 61 },
		{ 60, 53, 53, 55, 58, 55, 55 } };

	int outerEarX[][] = { { 4, 2, 1, 1, 2, 3, 7, 11 },
		{ 9, 6, 2, 1, 3, 6, 10, 12 }, { 8, 4, 2, 2, 3, 9, 11, 13 },

		{ 12, 13, 12, 15 }, { 13, 6 }, { 6, 4 } };

	int outerEarY[][] = { { 66, 66, 68, 80, 87, 94, 96, 97 },
		{ 67, 63, 63, 67, 78, 93, 94, 95 }, { 63, 62, 65, 76, 82, 90, 95, 95 },

		{ 68, 71, 77, 81 }, { 89, 87 }, { 71, 67 } };

	int innerEar1X[][] = { { 10, 10 }, { 8, 5, 3, 4 }, { 5, 5 },

	{ 50, 50 }, { 9, 6 }, { 4, 4 } };

	int innerEar1Y[][] = { { 74, 93 }, { 71, 67, 67, 75 }, { 67, 75 },

	{ 0, 0 }, { 69, 69 }, { 74, 71 } };

	int innerEar2X[][] = { { 4, 8 }, { 6, 5 }, { 3, 7 },

	{ 50, 50 }, { 6, 4, 8 }, { 3, 3, 7 } };

	int innerEar2Y[][] = { { 85, 88 }, { 71, 75 }, { 65, 68 },

	{ 0, 0 }, { 69, 73, 76 }, { 74, 77, 84 } };

	int innerEar3X[][] = { { 3, 8 }, { 8, 6, 9 }, { 9, 7, 7, 10 },

	{ 50, 50 }, { 3, 3 }, { 7, 3 } };

	int innerEar3Y[][] = { { 69, 71 }, { 85, 81, 74 }, { 76, 80, 83, 84 },

	{ 0, 0 }, { 50, 50 }, { 86, 86 } };

	int innerEar4X[][] = { { 8, 6 }, { 11, 10 }, { 10, 9 },

	{ 50, 50 }, { 6, 10 }, { 3, 1 } };

	int innerEar4Y[][] = { { 76, 69 }, { 91, 80 }, { 74, 79 },

	{ 0, 0 }, { 80, 82 }, { 86, 83 } };

	int chinX[][] = { { 11, 14, 18, 27, 40, 50 }, { 13, 13, 19, 29, 52 },
		{ 13, 14, 18, 26, 31, 41, 50 },

		{ 23, 34, 41, 45, 50 }, { 12, 14, 16, 18, 18, 26, 34, 39, 50 },
		{ 8, 14, 15, 22, 28, 41, 50 } };

	int chinY[][] = { { 97, 113, 122, 126, 130, 130 },
		{ 89, 102, 116, 126, 129 }, { 88, 101, 110, 118, 125, 131, 131 },

		{ 112, 122, 127, 128, 129 },
		{ 80, 87, 103, 105, 108, 117, 126, 128, 129 },
		{ 93, 104, 113, 123, 128, 130, 130 } };

	int noseX[][] = { { 50, 45, 42, 42, 40, 39, 40, 43 },
		{ 50, 48, 47, 42, 41, 41, 38, 42 }, { 50, 49, 46, 44, 42, 40, 42 },

		{ 50, 48, 47, 45, 42, 41, 43, 44 },
		{ 50, 48, 46, 42, 40, 37, 37, 39, 41 },
		{ 46, 47, 45, 42, 40, 39, 39, 43 } };

	int noseY[][] = { { 88, 85, 85, 87, 87, 85, 82, 79 },
		{ 86, 85, 83, 83, 84, 86, 80, 76 }, { 90, 89, 88, 88, 90, 87, 81 },

		{ 94, 94, 93, 93, 94, 91, 89, 87 },
		{ 95, 94, 92, 92, 93, 92, 88, 86, 85 },
		{ 88, 87, 87, 87, 89, 86, 83, 81 } };

	int upperLipX[][] = { { 50, 46, 40, 35, 41, 50 },
		{ 50, 47, 44, 41, 35, 50 }, { 50, 48, 44, 40, 35, 40, 45, 50 },

		{ 50, 47, 43, 41, 37, 42, 50 }, { 50, 45, 41, 35, 41, 50 },
		{ 50, 46, 39, 34, 44, 50 } };

	int upperLipY[][] = { { 101, 100, 100, 103, 103, 103 },
		{ 98, 97, 97, 98, 101, 101 }, { 100, 99, 99, 101, 103, 103, 103, 103 },

		{ 104, 103, 103, 104, 105, 106, 106 },
		{ 104, 103, 103, 105, 106, 107 }, { 101, 100, 101, 105, 104, 104 } };

	int lowerLipX[][] = { { 50, 47, 41, 35, 40, 50 },
		{ 50, 45, 40, 35, 42, 47, 50 }, { 50, 45, 40, 35, 39, 43, 50 },

		{ 50, 48, 43, 37, 41, 45, 50 }, { 50, 40, 34, 36, 38, 42, 50 },
		{ 50, 44, 39, 34, 40, 45, 50 } };

	int lowerLipY[][] = { { 103, 103, 103, 103, 106, 107 },
		{ 101, 100, 100, 100, 105, 105, 103 },
		{ 103, 103, 103, 103, 105, 108, 107 },

		{ 106, 106, 105, 105, 108, 110, 111 },
		{ 107, 106, 105, 108, 109, 111, 111 },
		{ 104, 104, 104, 105, 108, 110, 110 } };

	int eyeX[][] = { { 26, 28, 35, 41, 38, 34, 29, 26 },
		{ 25, 28, 34, 38, 40, 38, 29, 25 }, { 23, 28, 35, 40, 39, 34, 29, 24 },

		{ 26, 30, 36, 40, 40, 36, 30, 26 }, { 23, 25, 33, 38, 34, 33, 28, 26 },
		{ 23, 28, 35, 39, 39, 35, 27, 23 } };

	int eyeY[][] = { { 64, 62, 62, 64, 66, 67, 67, 64 },
		{ 63, 61, 60, 62, 63, 65, 65, 63 }, { 62, 58, 58, 61, 62, 63, 63, 62 },

		{ 70, 68, 68, 69, 70, 73, 73, 70 }, { 70, 66, 66, 71, 70, 70, 70, 70 },
		{ 65, 62, 62, 64, 65, 67, 67, 65 } };

	int pupilCenterX[] = { 33, 33, 32,

	33, 31, 31 };

	int pupilCenterY[] = { 64, 63, 60,

	70, 68, 64 };

	public Face() { // Constructor method.
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		tracker = new MediaTracker(this);
	}

	public synchronized void paint(Graphics g) { // Paint the graph.
		if (profile[0] == Float.MAX_VALUE) { 
			// No emotion defined, so leave blank.
			return;
		}
		amplifiedProfile = new double[3]; 
		// Exaggerate visual effects, but not beyond physical limits.
		double upperLimit = 4.3;
		double lowerLimit = -4.3;
		for (int i = 0; i < 3; i++) {
			amplifiedProfile[i] = (double) AMPLIFIER * profile[i];
			if (amplifiedProfile[i] > upperLimit) 
				amplifiedProfile[i] = upperLimit;
			if (amplifiedProfile[i] < lowerLimit) 
				amplifiedProfile[i] = lowerLimit;
		}

		// Create an off-screen image.
		Image offscreen = createImage(HALFWIDTH, HEIGHT);
		Graphics offscreenGraphics = offscreen.getGraphics();
		leftSide(offscreenGraphics);
		left = offscreen;
		right = rightSide(left);

		// Drawing down one pixel from top prevents distortion.
		g.drawImage(left, 0, 1, null); 
		g.drawImage(right, HALFWIDTH, 1, null);
		offscreenGraphics.dispose();
	} // End Paint.

	public void leftSide(Graphics offscreenG) {

		// Draw the head.
		offscreenG.fillPolygon(hairX[picture], hairY[picture],
			hairX[picture].length);
		offscreenG.drawPolyline(outerEarX[picture], outerEarY[picture],
			outerEarX[picture].length);
		offscreenG.drawPolyline(innerEar1X[picture], innerEar1Y[picture],
			innerEar1X[picture].length);
		offscreenG.drawPolyline(innerEar2X[picture], innerEar2Y[picture],
			innerEar2X[picture].length);
		offscreenG.drawPolyline(innerEar3X[picture], innerEar3Y[picture],
			innerEar3X[picture].length);
		offscreenG.drawPolyline(innerEar4X[picture], innerEar4Y[picture],
			innerEar4X[picture].length);
		offscreenG.drawPolyline(chinX[picture], chinY[picture],
			chinX[picture].length);
		offscreenG.drawPolyline(noseX[picture], noseY[picture],
			noseX[picture].length);

		// Draw the expression.
		drawBrow(amplifiedProfile[0], amplifiedProfile[1], picture, offscreenG);
		drawEye(amplifiedProfile[2], picture, offscreenG);
		drawMouth(amplifiedProfile, picture, offscreenG);
	}

	public Image rightSide(Image image) {
		// Turns the left image into a pixel array, flips,
		// then turns the array into the right image.
		Image flippedImage;
		int oldpix[] = new int[LENGTH];
		int[] newpix = new int[LENGTH];

		PixelGrabber grabber =
			new PixelGrabber(image, 0, 0, HALFWIDTH, HEIGHT, oldpix, 0,
				HALFWIDTH);
		boolean noerror = true;
		try {
			noerror = grabber.grabPixels();
		} catch (InterruptedException e) {
			;
		}
		if (!noerror) System.err.println("Couldn't grab pixels from image.");

		int s = 0;
		for (int y = 0; y < HEIGHT; y++) { // Horizontal flipping procedure.
			for (int x = 0; x < HALFWIDTH; x++) {
				int d = 0;
				d += y * HALFWIDTH;
				d += (HALFWIDTH - x - 1);
				newpix[d] = oldpix[s];
				s++;
			}
		}

		flippedImage =
			createImage(new MemoryImageSource(HALFWIDTH, HEIGHT, newpix, 0,
				HALFWIDTH));
		tracker.addImage(flippedImage, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException e) {
			;
		}
		if (tracker.isErrorID(0))
			System.err.println("Error creating new image.");

		return flippedImage;
	}

	public void drawBrow(double E, double P, int picture, Graphics g) {
		int[] X, Y;
		double weight, angle;
		int number, offset;
		double center, adjustedE;
		double leftmost = 5000;
		double rightmost = 0;

		// Get the point arrays for drawing a brow.
		number = browX[picture].length;
		X = new int[number];
		System.arraycopy(browX[picture], 0, X, 0, number);
		Y = new int[number];
		System.arraycopy(browY[picture], 0, Y, 0, number);

		// Find the leftmost and rightmost points of the brow,
		// and the center point.
		leftmost = arrayMin(X);
		rightmost = arrayMax(X);
		center = (leftmost + rightmost) / 2;

		// Bow upward for E+, bow downward for E-.
		adjustedE = E + 1; // For more upward bowing than downward.
		for (int i = 0; i < number; i++) {
			Y[i] =
				Y[i]
					- (int) (adjustedE * (1 - (Math.abs(X[i] - center) / (center - leftmost))));
		}

		// Flex inner brow down for P+, up for P-; moreso with E- than with E+.
		weight = (5 - E) / 9;
		if (weight < 0) weight = 0;
		// Radian conversion. Less potency angle with E+:
		angle = Math.max(-0.5, (P / 5) * weight);
		// Move whole brow inward for frowns.
		offset = (int) Math.max(0, P); // Pixels. 
		for (int i = 0; i < number; i++) {
			if (X[i] >= center) { // Only work on inner brow.
				Y[i] =
					Y[i] + (int) Math.round((X[i] - center) * Math.tan(angle));
			}
			X[i] = X[i] + offset;
		}
		g.fillPolygon(X, Y, number);
	}

	public void drawEye(double A, int picture, Graphics g) {
		int[] X, Y;
		int upperLidTop, lowerLidBottom, pupilCenter_X, pupilCenter_Y, maxHeight;
		int number = 8; // Need eight for below.
		int pupilRadius = 3;
		int pupilDiameter = 2 * pupilRadius;

		// Get the point arrays for drawing an eye.
		X = new int[number];
		System.arraycopy(eyeX[picture], 0, X, 0, number);
		Y = new int[number];
		System.arraycopy(eyeY[picture], 0, Y, 0, number);
		pupilCenter_X = pupilCenterX[picture];
		pupilCenter_Y = pupilCenterY[picture];

		// Flat part of eyelids must be defined in following positions:
		// {_, t, t, _, _, b, b, _}.
		Y[5] = Y[6] = (int) Math.round(Y[6] + A / 7); // Lower lid.
		// At most, one pixel blank above pupil.
		maxHeight = pupilCenter_Y - 5;
		Y[1] = Y[2] = (int) Math.max(maxHeight, Math.round(Y[2] - A / 2));
		// Upper lid no lower than lower lid.
		Y[1] = Y[2] = (int) Math.min(Y[6], Y[2]);
		g.drawPolygon(X, Y, number);
		upperLidTop = Y[2];
		lowerLidBottom = Y[5];
		Graphics g2 = g.create();
		g2.clipRect(X[1], upperLidTop, X[3] - X[1], lowerLidBottom
			- upperLidTop);
		g2.fillOval(pupilCenter_X - pupilRadius, pupilCenter_Y - pupilRadius,
			pupilDiameter, pupilDiameter);
	}

	public void drawMouth(double[] EPA, int picture, Graphics g) {
		int[] Xt, Yt, Xb, Yb;
		int numberTop, numberBottom, xMeasure, neutralLipCornerX, neutralLipCornerY;
		int cornerTop, cornerBottom, mouthLift, topLipLift, bottomLipDrop;
		double narrowingFactor, maxLipUp, maxLipDown, maxLipOutX;
		double E_polarization, currentLipCornerX, currentLipCornerY, shiftWeight;
		double maxE = 5.0;
		int rightMost = 50;

		// Get the numbers for drawing a mouth.
		numberTop = upperLipX[picture].length;
		Xt = new int[numberTop];
		System.arraycopy(upperLipX[picture], 0, Xt, 0, numberTop);
		Yt = new int[numberTop];
		System.arraycopy(upperLipY[picture], 0, Yt, 0, numberTop);
		numberBottom = lowerLipX[picture].length;
		Xb = new int[numberBottom];
		System.arraycopy(lowerLipX[picture], 0, Xb, 0, numberBottom);
		Yb = new int[numberBottom];
		System.arraycopy(lowerLipY[picture], 0, Yb, 0, numberBottom);
		maxLipUp =
			(arrayMax(noseY[picture]) + arrayMin(upperLipY[picture])) / 2;
		maxLipDown =
			(arrayMax(chinY[picture]) + arrayMin(upperLipY[picture])) / 2;
		maxLipOutX =
			(arrayMin(eyeX[picture]) + arrayMin(upperLipX[picture])) / 2;
		neutralLipCornerX = arrayMin(upperLipX[picture]);
		neutralLipCornerY = arrayMax(upperLipY[picture]);
		// Find the leftmost point of the mouth.
		cornerTop = 0;
		xMeasure = Integer.MAX_VALUE;
		for (int i = 0; i < numberTop; i++) { // Upper lip.
			if (xMeasure >= Xt[i]) {
				xMeasure = Xt[i];
				cornerTop = i;
			}
		}
		cornerBottom = 0;
		xMeasure = Integer.MAX_VALUE;
		for (int i = 0; i < numberBottom; i++) { // Lower lip.
			if (xMeasure >= Xb[i]) {
				xMeasure = Xb[i];
				cornerBottom = i;
			}
		}
		// Lower lip lower with +A.
		bottomLipDrop =
			(int) Math.min(5, Math.max(0, Math.round(1.8 * EPA[2])));
		for (int i = 0; i < numberBottom; i++) {
			if (i != cornerBottom) {
				Yb[i] = Yb[i] + bottomLipDrop;
			}
		}
		// Mouth narrower with +A.
		narrowingFactor = Math.max(0.0, EPA[2] / 15);
		for (int i = 0; i < numberTop; i++) {
			Xt[i] =
				Xt[i] + (int) Math.round(narrowingFactor * (rightMost - Xt[i]));
		}
		for (int i = 0; i < numberBottom; i++) {
			Xb[i] =
				Xb[i] + (int) Math.round(narrowingFactor * (rightMost - Xb[i]));
		}
		// Mouth curvature with E.
		double E_ForMouth = (double) 1.3 * EPA[0];
		E_polarization = Math.min(1, Math.abs(E_ForMouth) / maxE);
		currentLipCornerX =
			neutralLipCornerX - E_polarization
				* (neutralLipCornerX - maxLipOutX); // Horizontal stretch.
		if (EPA[0] >= 0) { // Vertical stretch up or down.
			currentLipCornerY =
				neutralLipCornerY - E_polarization
					* (neutralLipCornerY - maxLipUp);
		} else {
			currentLipCornerY =
				neutralLipCornerY + E_polarization
					* (maxLipDown - neutralLipCornerY);
			currentLipCornerX = 1.1 * currentLipCornerX; // Tweaking.
		}
		for (int i = 0; i < numberTop; i++) {
			shiftWeight =
				((double) (rightMost - Xt[i]))
					/ ((double) (rightMost - neutralLipCornerX));
			// Don't vertically curve lip middle.
			shiftWeight = Math.pow(shiftWeight, 3); 
			Yt[i] =
				(int) Math.round(Yt[i] - shiftWeight
					* (neutralLipCornerY - currentLipCornerY));
			// Only the very corner horizontally stretched.
			shiftWeight = (int) shiftWeight; 
			Xt[i] =
				(int) Math.round(Xt[i] - shiftWeight
					* (neutralLipCornerX - currentLipCornerX));
		}
		for (int i = 0; i < numberBottom; i++) {
			shiftWeight =
				((double) (rightMost - Xb[i]))
					/ ((double) (rightMost - neutralLipCornerX));
			// Don't vertically curve lip middle.
			shiftWeight = Math.pow(shiftWeight, 3); 
			Yb[i] =
				(int) Math.round(Yb[i] - shiftWeight
					* (neutralLipCornerY - currentLipCornerY));
			shiftWeight = (int) shiftWeight;
			Xb[i] =
				(int) Math.round(Xb[i] - shiftWeight
					* (neutralLipCornerX - currentLipCornerX));
		}
		// Mouth higher with +P.
		mouthLift = (int) Math.max(0, Math.round((EPA[1]) / 4));
		for (int i = 0; i < numberTop; i++) {
			Yt[i] = Yt[i] - mouthLift;
		}
		for (int i = 0; i < numberBottom; i++) {
			Yb[i] = Yb[i] - mouthLift;
		}
		// Upper lip higher with +P.
		topLipLift = (int) Math.round(EPA[1] / 2);
		for (int i = 0; i < numberTop; i++) {
			if (i != cornerTop) {
				Yt[i] = Yt[i] - topLipLift;
			}
		}
		// Draw the lips.
		g.fillPolygon(Xt, Yt, numberTop);
		g.fillPolygon(Xb, Yb, numberBottom);
	}

	/** Maximum value in an array. */
	int arrayMax(int[] integerArray) {
		int theExtreme = Integer.MIN_VALUE;
		for (int i = 0; i < integerArray.length; i++) {
			theExtreme = Math.max(theExtreme, integerArray[i]);
		}
		return theExtreme;
	}

	/** Minimum value in an array. */
	int arrayMin(int[] integerArray) {
		int theExtreme = Integer.MAX_VALUE;
		for (int i = 0; i < integerArray.length; i++) {
			theExtreme = Math.min(theExtreme, integerArray[i]);
		}
		return theExtreme;
	}

	public Dimension getPreferredSize() {
		return new Dimension(2 * HALFWIDTH, HEIGHT);
	}

	public void setPictureChoice(int choosenPicture) {
		picture = choosenPicture;
	}

	public void setProfile(double[] newProfile) {
		profile = newProfile;
	}

} // End class Face.

