/*
 * VerticalFlowLayout.java
 * Copyright (C) 1999 dog <dog@dog.net.uk>
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * You may retrieve the latest version of this library from
 * http://www.dog.net.uk/knife/
 */

package culture;

import java.awt.*;

/**
 * A vertical flow layout.
 * 
 * @author dog@dog.net.uk (http://bluezoo.org/knife/)
 * @version 1.0.1
 */
public class VerticalFlowLayout implements LayoutManager {

	/**
	 * The top alignment variable.
	 */
	public static final int TOP = 1;

	/**
	 * The centre alignment variable.
	 */
	public static final int CENTER = 0;

	/**
	 * The bottom alignment variable.
	 */
	public static final int BOTTOM = 2;

	/**
	 * Left alignment variable.
	 */
	public static final boolean LEFT = true;

	int align;

	int hgap;

	int vgap;

	boolean maximise;

	boolean hAlign = LEFT; // Now set to always left align.

	/**
	 * Constructs a new layout with a centered alignment and a default 5-unit
	 * horizontal and vertical gap.
	 */
	public VerticalFlowLayout() {
		this(CENTER, 5, 5, false);
	}

	/**
	 * Constructs a new layout with the specified alignment and a default 5-unit
	 * horizontal and vertical gap.
	 * 
	 * @param align
	 *            the alignment value
	 */
	public VerticalFlowLayout(int align) {
		this(align, 5, 5, false);
	}

	/**
	 * Constructs a new layout with the specified alignment and gap values.
	 * 
	 * @param align
	 *            the alignment value
	 * @param hgap
	 *            the horizontal gap variable
	 * @param vgap
	 *            the vertical gap variable
	 */
	public VerticalFlowLayout(int align, int hgap, int vgap) {
		this(align, hgap, vgap, false);
	}

	/**
	 * Constructs a new layout with the specified alignment and gap values.
	 * 
	 * @param align
	 *            the alignment value
	 * @param hgap
	 *            the horizontal gap variable
	 * @param vgap
	 *            the vertical gap variable
	 */
	public VerticalFlowLayout(int align, int hgap, int vgap, boolean maximise) {
		if (align != TOP && align != CENTER && align != BOTTOM)
			throw new IllegalArgumentException("illegal alignment value:"
				+ align);
		if (hgap < 0)
			throw new IllegalArgumentException("horizontal gap is less than 0");
		if (vgap < 0)
			throw new IllegalArgumentException("vertical gap is less than 0");
		this.align = align;
		this.hgap = hgap;
		this.vgap = vgap;
		this.maximise = maximise;
	}

	/**
	 * Returns the alignment value for this layout (TOP, CENTER, or BOTTOM).
	 */
	public int getAlignment() {
		return align;
	}

	/**
	 * Sets the alignment value for this layout.
	 * 
	 * @param align
	 *            the alignment value (TOP, CENTER, or BOTTOM).
	 */
	public void setAlignment(int align) {
		if (align != TOP && align != CENTER && align != BOTTOM)
			throw new IllegalArgumentException("illegal alignment value:"
				+ align);
		this.align = align;
	}

	/**
	 * Returns the horizontal gap between components.
	 */
	public int getHgap() {
		return hgap;
	}

	/**
	 * Sets the horizontal gap between components.
	 * 
	 * @param hgap
	 *            the horizontal gap between components
	 */
	public void setHgap(int hgap) {
		if (hgap < 0)
			throw new IllegalArgumentException("horizontal gap is less than 0");
		this.hgap = hgap;
	}

	/**
	 * Returns the vertical gap between components.
	 */
	public int getVgap() {
		return vgap;
	}

	/**
	 * Sets the vertical gap between components.
	 * 
	 * @param vgap
	 *            the vertical gap between components
	 */
	public void setVgap(int vgap) {
		if (vgap < 0)
			throw new IllegalArgumentException("vertical gap is less than 0");
		this.vgap = vgap;
	}

	/**
	 * Adds the specified component to the layout. Not used by this class.
	 * 
	 * @param name
	 *            the name of the component
	 * @param comp
	 *            the the component to be added
	 */
	public void addLayoutComponent(String name, Component comp) {
	}

	/**
	 * Removes the specified component from the layout. Not used by this class.
	 * 
	 * @param comp
	 *            the component to remove
	 */
	public void removeLayoutComponent(Component comp) {
	}

	/**
	 * Returns the preferred dimensions for this layout given the components in
	 * the specified target container.
	 * 
	 * @param target
	 *            the component which needs to be laid out
	 * @see Container
	 * @see #minimumLayoutSize
	 */
	public Dimension preferredLayoutSize(Container target) {
		Dimension dim = new Dimension(0, 0);
		int nmembers = target.getComponentCount();
		for (int i = 0; i < nmembers; i++) {
			Component m = target.getComponent(i);
			if (m.isVisible()) {
				Dimension d =
					maximise ? m.getMaximumSize() : m.getPreferredSize();
				dim.width = Math.max(dim.width, d.width);
				if (i > 1) dim.height += vgap;
				dim.height += d.height;
			}
		}
		Insets insets = target.getInsets();
		dim.width += insets.left + insets.right + hgap * 2;
		dim.height += insets.top + insets.bottom + vgap * 2;
		return dim;
	}

	/**
	 * Returns the minimum dimensions needed to layout the components contained
	 * in the specified target container.
	 * 
	 * @param target
	 *            the component which needs to be laid out
	 * @see #preferredLayoutSize
	 */
	public Dimension minimumLayoutSize(Container target) {
		Dimension dim = new Dimension(0, 0);
		int nmembers = target.getComponentCount();
		for (int i = 0; i < nmembers; i++) {
			Component m = target.getComponent(i);
			if (m.isVisible()) {
				Dimension d = m.getMinimumSize();
				dim.width = Math.max(dim.width, d.width);
				if (i > 0) dim.height += vgap;
				dim.height += d.height;
			}
		}
		Insets insets = target.getInsets();
		dim.width += insets.left + insets.right + hgap * 2;
		dim.height += insets.top + insets.bottom + vgap * 2;
		return dim;
	}

	/**
	 * Centers the elements in the specified column, if there is any slack.
	 * 
	 * @param target
	 *            the component which needs to be moved
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param width
	 *            the width dimensions
	 * @param height
	 *            the height dimensions
	 * @param columnStart
	 *            the beginning of the column
	 * @param columnEnd
	 *            the the ending of the column
	 */
	private void moveComponents(
		Container target, int x, int y, int width, int height, int columnStart,
		int columnEnd) {
		switch (align) {
		case TOP:
			break;
		case CENTER:
			y += height / 2;
			break;
		case BOTTOM:
			y += height;
			break;
		}
		for (int i = columnStart; i < columnEnd; i++) {
			Component m = target.getComponent(i);
			Rectangle mbounds = m.getBounds();
			if (m.isVisible()) {
				if (hAlign) {
					m.setLocation(x, y);
				} else { // Center.
					m.setLocation(x + (width - mbounds.width) / 2, y);
				}
				y += vgap + mbounds.height;
			}
		}
	}

	/**
	 * Lays out the container. This method will actually reshape the components
	 * in the target in order to satisfy the constraints of the BorderLayout
	 * object.
	 * 
	 * @param target
	 *            the specified component being laid out.
	 * @see Container
	 */
	public void layoutContainer(Container target) {
		Insets insets = target.getInsets();
		Rectangle bounds = target.getBounds();
		int maxheight = bounds.height - (insets.top + insets.bottom + vgap * 2);
		int nmembers = target.getComponentCount();
		int y = 0, x = insets.left + hgap;
		int rowv = 0, start = 0;

		for (int i = 0; i < nmembers; i++) {
			Component m = target.getComponent(i);
			if (m.isVisible()) {
				Dimension d =
					maximise ? m.getPreferredSize() : m.getPreferredSize();
				m.setSize(d.width, d.height);

				if ((y == 0) || ((y + d.height) <= maxheight)) {
					if (y > 0) y += vgap;
					y += d.height;
					rowv = Math.max(rowv, d.width);
				} else {
					moveComponents(target, x, insets.top + vgap, rowv,
						maxheight - y, start, i);
					x += hgap + rowv;
					y = d.height;
					rowv = d.width;
					start = i;
				}
			}
		}
		moveComponents(target, x, insets.top + vgap, rowv, maxheight - y,
			start, nmembers);
	}

	/**
	 * Returns the string representation of this layout's values.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getClass().getName());
		buffer.append("[hgap=");
		buffer.append(hgap);
		buffer.append(",vgap=");
		buffer.append(vgap);
		switch (align) {
		case TOP:
			buffer.append(",align=top");
			break;
		case CENTER:
			buffer.append(",align=center");
			break;
		case BOTTOM:
			buffer.append(",align=bottom");
			break;
		}
		buffer.append("]");
		return buffer.toString();
	}
}
