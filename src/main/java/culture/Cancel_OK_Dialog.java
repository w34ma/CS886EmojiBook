package culture;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** Dialog with one, or two, answers. */
public class Cancel_OK_Dialog extends Dialog {

	final int LEFT = 1;

	final int RIGHT = -1;

	Panel dialogPanel = new Panel();

	Panel buttonPanel = new Panel();

	BorderLayout dialogPanelBorderLayout = new BorderLayout(15, 15);

	Label messageLabel = new Label();

	Button okButton = new Button();

	Button cancelButton = new Button();

	String message, left_label, right_label;

	/* For success messsages in green, use this with an empty title. */
	public Cancel_OK_Dialog(Frame parent, String title, String m, String y,
			String c) {
		super(parent, title, true); // Create the window.
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit(m, y, c);
			if (title.isEmpty()) {
				dialogPanel.setBackground(Color.GREEN);
			}
			add(dialogPanel);
			pack();
			Point upperLeftCorner = parent.getLocation();
			Dimension parentSize = parent.getSize();
			Dimension dialogSize = this.getSize();
			upperLeftCorner.translate(
				(parentSize.width - dialogSize.width) / 2,
				(parentSize.height - dialogSize.height) / 2);
			this.setLocation(upperLeftCorner);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* Use this for error messages: the background is pink. */
	void jbInit(String m, String y, String c) throws Exception {
		message = m;
		left_label = y;
		right_label = c;

		// Specify a LayoutManager for it
		dialogPanel.setLayout(dialogPanelBorderLayout);
		dialogPanel.setBackground(Color.pink);

		// Put the message label in the middle of the window.
		messageLabel.setText(message);
		dialogPanel.add(messageLabel, BorderLayout.CENTER);

		// Create a panel of buttons, center the row of buttons in
		// the panel, and put the pane at the bottom of the window.
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		buttonPanel.add(okButton, null);
		buttonPanel.add(cancelButton, null);
		okButton.setLabel(left_label);
		okButton.setVisible(false);
		cancelButton.setLabel(right_label);
		cancelButton.setVisible(false);
		if (left_label != null) okButton.setVisible(true);
		if (right_label != null) cancelButton.setVisible(true);
		dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

		okButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_actionPerformed(e);
			}
		});
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_actionPerformed(e);
			}
		});
	}

	void _actionPerformed(ActionEvent e) {
		if (right_label == null) {
			// For an OK dialog, right is null, and OK means erase 
			// with no action. So just fall through.
		} else {
			// Deal with a two-button dialog.
			if (e.toString() == left_label) { 
				// Comparison untested; just used OK.
				answer(LEFT);
			} else {
				answer(RIGHT);
			}
		}
		this.dispose();
	}

	protected void answer(int answer) {
		// NO ACTIONS YET.
	}

	class WindowEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent evt) {
			dispose();
		}
	}

} // End class Cancel_OK.
