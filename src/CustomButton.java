import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class CustomButton extends Custom {

	private static final long serialVersionUID = 1L;

	private FontMetrics fm;
	private Graphics2D g2d;

	private RenderingStyle style = RenderingStyle.Simple;

	private ArrayList<ActionListener> actions = new ArrayList<ActionListener>();
	private ActionEvent actionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, this.getName() + " Clicked");

	private Color currentColor = this.getDefaultColor();

	private int transitionSpeed = 5;

	private boolean setRenderingHint = true;

	public CustomButton() {

		this(Themes.Black);

	}

	public CustomButton(Themes theme) {

		super(theme);

		objects.add(this);

		this.setSize(75, 25);

		this.setLocation(0, 0);

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		g2d = (Graphics2D) g;

		setHint();

		paintButton(g2d);

		drawText(g2d);

		drawFocus(g2d);

		paintClicked(g2d);

		g2d.dispose();

	}

	private void setHint() {

		if (this.setRenderingHint) g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.setRenderingHint = false;

	}

	private void paintButton(Graphics2D g2d) {

		if (this.isEnabled()) {

			if (this.getMouseOnTop() || this.getIsMouseDown()) g2d.setPaint(this.getLightTheme());
			else g2d.setPaint(this.getDefaultTheme());

			if (style == RenderingStyle.Transitional) {

				g2d.setPaint(this.getNeededColor());

				this.currentColor = (Color) g2d.getPaint();

			} else if (style == RenderingStyle.Simple) {

				if (this.getIsMouseDown()) g2d.setPaint(this.getLightTheme());
				else if (this.getMouseOnTop()) g2d.setPaint(this.getDefaultTheme());
				else g2d.setPaint(this.getDarkTheme());

			}

			if (style == RenderingStyle.Simple || style == RenderingStyle.Transitional || style == RenderingStyle.Normal) g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
			else drawGradient(g2d);

		} else {

			g2d.setPaint(this.getDisabledColor());
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		}

	}

	private void drawFocus(Graphics2D g2d) {

		if (this.hasFocus() && !this.getIsMouseDown()) {
			g2d.setPaint(this.getLightTheme());
			g2d.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		}

	}

	private void drawText(Graphics2D g2d) {

		if (this.getText() == "" || this.getText() == null) return;

		g2d.setPaint(this.getForeground());

		fm = g2d.getFontMetrics(this.getFont());

		float width = (float) fm.getStringBounds(this.getText(), g2d).getWidth();
		float height = (float) fm.getStringBounds(this.getText(), g2d).getHeight();

		float x = (this.getWidth() / 2) - (width / 2);
		float y = (this.getHeight() / 2) + (height / 3);

		g2d.drawString(this.getText(), x, y);

	}

	private void drawGradient(Graphics2D g2d) {

		float[] fractions = { 0.5f, 1 };
		Color[] colors = { this.getLightTheme(), this.getDefaultTheme() };

		if (this.getMouseOnTop() || this.getIsMouseDown()) {
			g2d.setStroke(new BasicStroke(2));
			colors[0] = colors[1];
			colors[1] = (style == RenderingStyle.Invertable) ? this.getLightTheme() : colors[1];
		}

		RadialGradientPaint rgp = new RadialGradientPaint(getBounds(), fractions, colors, CycleMethod.REFLECT);

		g2d.setPaint(rgp);

		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		g2d.setStroke(new BasicStroke(1));
	}

	private void paintClicked(Graphics2D g2d) {

		if (!this.getIsMouseDown() || style == RenderingStyle.Simple || style == RenderingStyle.Transitional) return;

		g2d.setPaint(this.getDarkTheme());

		g2d.setStroke(new BasicStroke(2));

		// draws vertical line on the left
		g2d.draw(new Line2D.Float(0, 0, this.getWidth(), 0));

		// draws horizontal line on the top
		g2d.draw(new Line2D.Float(0, 0, 0, this.getHeight()));

		g2d.setStroke(new BasicStroke(1));

		// draws horizontal line on the bottom
		g2d.draw(new Line2D.Float(1, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1));

		// draws vertical line on the right
		g2d.draw(new Line2D.Float(this.getWidth() - 1, 1, this.getWidth() - 1, this.getHeight()));
	}

	private Color getNeededColor() {

		int fadeSpeed = transitionSpeed;

		int rDifference = this.getDarkTheme().getRed() - currentColor.getRed();
		int gDifference = this.getDarkTheme().getGreen() - currentColor.getGreen();
		int bDifference = this.getDarkTheme().getBlue() - currentColor.getBlue();

		int newR = currentColor.getRed();
		int newG = currentColor.getGreen();
		int newB = currentColor.getBlue();

		int requiredR = this.getDarkTheme().getRed();
		int requiredG = this.getDarkTheme().getGreen();
		int requiredB = this.getDarkTheme().getBlue();

		if (this.getIsMouseDown()) {

			rDifference = this.getLightTheme().getRed() - currentColor.getRed();
			gDifference = this.getLightTheme().getGreen() - currentColor.getGreen();
			bDifference = this.getLightTheme().getBlue() - currentColor.getBlue();

			requiredR = this.getLightTheme().getRed();
			requiredG = this.getLightTheme().getGreen();
			requiredB = this.getLightTheme().getBlue();

		} else if (this.getMouseOnTop()) {

			rDifference = this.getDefaultTheme().getRed() - currentColor.getRed();
			gDifference = this.getDefaultTheme().getGreen() - currentColor.getGreen();
			bDifference = this.getDefaultTheme().getBlue() - currentColor.getBlue();

			requiredR = this.getDefaultTheme().getRed();
			requiredG = this.getDefaultTheme().getGreen();
			requiredB = this.getDefaultTheme().getBlue();

		}

		if (rDifference != 0) {

			int futureValue = newR + transitionSpeed;

			if (futureValue > requiredR) fadeSpeed = Math.abs(newR - requiredR);

			if (rDifference > 0) newR += fadeSpeed;
			else newR -= fadeSpeed;
		}

		if (gDifference != 0) {

			int futureValue = newG + transitionSpeed;

			if (futureValue > requiredG) fadeSpeed = Math.abs(newG - requiredG);

			if (gDifference > 0) newG += fadeSpeed;
			else newG -= fadeSpeed;
		}

		if (bDifference != 0) {

			int futureValue = newB + transitionSpeed;

			if (futureValue > Math.abs(requiredB)) fadeSpeed = Math.abs(newB - requiredB);

			if (bDifference > 0) newB += fadeSpeed;
			else newB -= fadeSpeed;
		}

		return new Color(newR, newG, newB);

	}

	public void addActionListener(ActionListener action) {
		this.actions.add(action);
	}

	public void removeActionListener(ActionListener action) {
		this.actions.remove(action);
	}

	public void setRenderingStyle(RenderingStyle newStyle) {
		this.style = newStyle;
	}

	public RenderingStyle getRenderingStyle() {
		return this.style;
	}

	public int getTransitionSpeed() {
		return transitionSpeed;
	}

	public void setTransitionSpeed(int newTransitionSpeed) {

		if (newTransitionSpeed < 1) newTransitionSpeed = 1;
		else if (newTransitionSpeed > 10) newTransitionSpeed = 10;
		this.transitionSpeed = newTransitionSpeed;

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		for (ActionListener al : actions)
			al.actionPerformed(actionEvent);
	}

	public static enum RenderingStyle {
		Simple, Gradient, Invertable, Transitional, Normal
	}

}
