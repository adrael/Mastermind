import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class RoundButton extends JButton {
	private Shape shape = null;

	public RoundButton(Color c, int s) {
		super();

		setSize(s, s);
		setPreferredSize(new Dimension(s, s));
		setContentAreaFilled(false);
		
		if(!(c == null))
			setBackground(c);
	}

	protected void paintComponent(Graphics g) {
		if (getModel().isArmed())
			g.setColor(Color.lightGray);
		else 
			g.setColor(getBackground());
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

		super.paintComponent(g);
	}

	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	}

	public boolean contains(int x, int y) {
		if (this.shape == null || !this.shape.getBounds().equals(getBounds()))
			this.shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		return this.shape.contains(x, y);
	}
}