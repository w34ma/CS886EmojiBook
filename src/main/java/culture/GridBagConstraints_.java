package culture;

import java.awt.*;

/** A convenience class to facilitate setting gridbag constraints. */

public class GridBagConstraints_ extends GridBagConstraints {
	public GridBagConstraints_(int gridx, int gridy, int gridwidth,
			int gridheight, double weightx, double weighty, int anchor,
			int fill, Insets insets, int ipadx, int ipady) {
		this.gridx = gridx;
		this.gridy = gridy;
		this.gridwidth = gridwidth;
		this.gridheight = gridheight;
		this.fill = fill;
		this.ipadx = ipadx;
		this.ipady = ipady;
		this.insets = insets;
		this.anchor = anchor;
		this.weightx = weightx;
		this.weighty = weighty;
	}

}
