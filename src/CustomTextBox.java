import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CustomTextBox extends Custom implements KeyListener {

	private static final long serialVersionUID = 1L;

	private Graphics2D g2d;

	private FontMetrics fm;

	private int markerIndex = 0;

	private int leftPadding = 2;

	private int markersTick = 0;

	private final int VISIBLE_MARKER = 400 / this.refreshRate;

	private final int INVISIBLE_MARKER = 800;

	private final int RIGHT = 0;

	private final int LEFT = 1;

	private final int NO_SELECTION = -1;

	private float textWidth;

	private float textHeight;

	private Cursor textCursor = new Cursor(Cursor.TEXT_CURSOR);

	private final String acceptedCharacters = "qwertyuioplkjhgfdsazxcvbnm" + "QWERTYUIOPLKJHGFDSAZXCVBNM" + "1234567890" + "!@#$%^&*()" + "`-=[];'\\,./ " + "~_+{}:\"|<>?";

	private int selectionStartIndex = NO_SELECTION;

	private int selectionEndIndex = NO_SELECTION;

	private Color selectionColor = this.getLightTheme();

	private Color borderColor = this.getDefaultTheme();
	
	private ConcurrentLinkedQueue<Character> charsToInsert = new ConcurrentLinkedQueue<Character>();

	public CustomTextBox() {

		this(Themes.Black);

	}

	public CustomTextBox(Themes theme) {

		super(theme);

		objects.add(this);

		this.addKeyListener(this);

		this.addMouseMotionListener(this);

		this.setSize(100, 25);

		this.setLocation(0, 0);

		this.setForeground(Color.BLACK);

		this.setCursor(textCursor);

	}

	public Color getSelectionColor() {
		return this.selectionColor;
	}

	public void setSelectionColor(Color newSelectionColor) {
		this.selectionColor = newSelectionColor;
	}

	public Color getBorderColor() {
		return this.borderColor;
	}

	public void setBorderColor(Color newSelectionColor) {
		this.borderColor = newSelectionColor;
	}

	@Override
	public void setTheme(Themes newTheme) {

		super.setTheme(newTheme);

		selectionColor = this.getLightTheme();

		borderColor = this.getDefaultTheme();

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		try {

			paintTextArea(g2d);

			paintMarker(g2d);

			paintSelection(g2d);
			
			paintText(g2d);

		} catch (Exception e) {
			e.printStackTrace();
			
			//System.err.println("Error");
			
		}

		g2d.dispose();

	}

	private void paintTextArea(Graphics2D g2d) {

		if (!this.isEnabled()) g2d.setPaint(this.getDarkTheme());
		else if (this.getMouseOnTop() || this.getIsMouseDown()) g2d.setPaint(this.getLightTheme());
		else g2d.setPaint(this.borderColor);

		g2d.draw(new Rectangle2D.Float(0, 0, this.getWidth() - 1, this.getHeight() - 1));

		g2d.setPaint(this.getBackground());

		g2d.fill(new Rectangle2D.Float(1, 1, this.getWidth() - 2, this.getHeight() - 2));

	}

	private void paintSelection(Graphics2D g2d) {

		if (selectionEndIndex <= NO_SELECTION || selectionStartIndex <= NO_SELECTION || !this.isEnabled() || !this.hasFocus()) return;

		g2d.setPaint(this.selectionColor);

		String selected;

		fm = g2d.getFontMetrics(this.getFont());

		float start;

		float end;

		if (selectionEndIndex > selectionStartIndex) {

			selected = this.getText().substring(0, selectionStartIndex);

			start = (float) fm.getStringBounds(selected, g2d).getWidth() + leftPadding;

			if (start == leftPadding) start = 1;

			if (selectionEndIndex > this.getText().length()) selectionEndIndex = this.getText().length();

			selected = this.getText().substring(selectionStartIndex, selectionEndIndex);

			end = Math.abs((float) fm.getStringBounds(selected, g2d).getWidth() + leftPadding);

		} else {

			selected = this.getText().substring(0, selectionEndIndex);

			start = (float) fm.getStringBounds(selected, g2d).getWidth() + leftPadding;

			if (start == leftPadding) start = 1;

			if (selectionStartIndex > this.getText().length()) selectionStartIndex = this.getText().length();

			selected = this.getText().substring(selectionEndIndex, selectionStartIndex);

			end = Math.abs((float) fm.getStringBounds(selected, g2d).getWidth() + leftPadding);

		}

		g2d.fill(new Rectangle2D.Float(start, 0, end, this.getHeight()));

	}

	private void paintText(Graphics2D g2d) {

		manageCharacters(CharacterType.Add);

		g2d.setPaint(this.getForeground());

		float y = (this.getHeight() / 2) + (textHeight / 3);

		g2d.drawString(this.getText(), leftPadding, y);

		fm = g2d.getFontMetrics(this.getFont());

		textWidth = (float) fm.getStringBounds(this.getText(), g2d).getWidth();

		textHeight = (float) fm.getStringBounds(this.getText(), g2d).getHeight();

	}

	private void paintMarker(Graphics2D g2d) {

		if (!this.hasFocus() || !this.isEnabled()) return;
		
		markersTick++;

		String selected = this.getText().substring(0, markerIndex);

		fm = g2d.getFontMetrics(this.getFont());

		float width = (float) fm.getStringBounds(selected, g2d).getWidth() + leftPadding;

		if (markerIndex == 0) width = 1;

		if (markersTick * this.refreshRate >= 500) {

			g2d.setPaint(this.getDefaultTheme());

			g2d.draw(new Line2D.Float(width, 0, width, this.getHeight() - 1));

			if (markersTick * this.refreshRate >= INVISIBLE_MARKER) markersTick = 0;

		}

	}

	private boolean shouldMoveMarkerUp(int mouseX, float textWidth, int index) {

		if (index == 0) return true;

		float difference = textWidth - mouseX;

		String character = this.getText().substring(index - 1, index);

		fm = g2d.getFontMetrics(this.getFont());

		float charWidth = (float) fm.getStringBounds(character, g2d).getWidth();

		difference = charWidth - difference;

		if (difference >= charWidth / 2) return true;
		else return false;

	}

	private int getIndexFromXPoint(int x) {

		for (int i = 0; i <= this.getText().length(); i++) {

			fm = g2d.getFontMetrics(this.getFont());

			float width = (float) fm.getStringBounds(this.getText().substring(0, i), g2d).getWidth() + leftPadding;

			if (width >= x) {

				if (shouldMoveMarkerUp(x, width, i)) return i;
				else return i - 1;

			} else if (i == this.getText().length() && width < x) return i;

		}

		return -1;

	}

	private int indexOfNextSpace(boolean previous) {

		int startSearchIndex = markerIndex;

		if (previous) {

			if (selectionStartIndex == markerIndex) startSearchIndex = selectionEndIndex;

			if (selectionEndIndex == markerIndex) startSearchIndex = selectionStartIndex;

			if (startSearchIndex >= this.getText().length()) startSearchIndex = this.getText().length() - 1;

			for (int i = startSearchIndex; i >= 0; i--)
				if (this.getText().charAt(i) == ' ' && startSearchIndex - i > 0) return i;

		} else {

			if (selectionEndIndex == markerIndex) startSearchIndex = selectionStartIndex;

			if (selectionStartIndex == markerIndex) startSearchIndex = selectionEndIndex;

			int spaceIndexAfterOne = this.getText().indexOf(" ", startSearchIndex) + 1;

			if (spaceIndexAfterOne > this.getText().length() || spaceIndexAfterOne <= 0) return this.getText().length();
			else return spaceIndexAfterOne;

		}

		return 0;
	}

	private void manageCharacters(CharacterType type) {
		
		String partOne = this.getText().substring(0, markerIndex);
		String partTwo = this.getText().substring(markerIndex, this.getText().length());

		if (type == CharacterType.Add) {

			for(char c : charsToInsert) {
				
				partOne += c;
				
				markerIndex++;
				
			}

		} else if (type == CharacterType.Remove_Backwards) {

			if (selectionEndIndex > NO_SELECTION && selectionStartIndex > NO_SELECTION) {

				int measuringStartIndex = Math.min(selectionStartIndex, selectionEndIndex);
				int measuringEndIndex = Math.max(selectionStartIndex, selectionEndIndex);

				partOne = this.getText().substring(0, measuringStartIndex);
				partTwo = this.getText().substring(measuringEndIndex, this.getText().length());
				
				markerIndex = measuringStartIndex;

			} else if(partOne.length() > 0) {

				partOne = partOne.substring(0, partOne.length() - 1);

				markerIndex--;

			}

		} else if (type == CharacterType.Remove_Forewords) {

			if (partTwo.length() > 0) partTwo = partTwo.substring(1);

		}

		String text = partOne + partTwo;

		this.setText(text);

		charsToInsert.clear();

	}

	private void jump(int direction) {

		if (direction == RIGHT) {

			int newIndex = this.getText().indexOf(" ", markerIndex + 1);

			if (newIndex == -1) markerIndex = this.getText().length();
			else markerIndex = newIndex;

		} else if (direction == LEFT) {

			int newIndex = -1;

			for (int i = markerIndex - 1; i >= 0; i--) {

				if (this.getText().charAt(i) == ' ') {

					newIndex = i;

					break;

				}

			}

			if (newIndex == -1) markerIndex = 0;
			else markerIndex = newIndex;

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

		super.mousePressed(e);

		markersTick = VISIBLE_MARKER;

		selectionEndIndex = NO_SELECTION;

		if (e.getX() > textWidth) markerIndex = this.getText().length();
		else markerIndex = getIndexFromXPoint(e.getX());

		selectionStartIndex = markerIndex;

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		if (e.getX() < textWidth) selectionEndIndex = getIndexFromXPoint(e.getX());
		else selectionEndIndex = this.getText().length();

	}

	@Override
	public void keyPressed(KeyEvent e) {

		markersTick = VISIBLE_MARKER;

		boolean cleanSelect = true;

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			if (e.isControlDown() && e.isShiftDown()) {

				int space = indexOfNextSpace(false);

				if (selectionStartIndex <= NO_SELECTION) selectionStartIndex = markerIndex;

				if (selectionEndIndex == markerIndex) selectionStartIndex = space;
				else selectionEndIndex = space;

				cleanSelect = false;

			} else if (e.isControlDown()) jump(RIGHT);
			else if (markerIndex < this.getText().length()) markerIndex++;

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

			if (e.isControlDown() && e.isShiftDown()) {

				int space = indexOfNextSpace(true);

				if (selectionEndIndex <= NO_SELECTION) selectionEndIndex = markerIndex;

				if (selectionStartIndex == markerIndex) selectionEndIndex = space;
				else selectionStartIndex = space;

				cleanSelect = false;

			} else if (e.isControlDown()) jump(LEFT);
			else if (markerIndex > 0) markerIndex--;

		} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			manageCharacters(CharacterType.Remove_Backwards);
		} else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			manageCharacters(CharacterType.Remove_Forewords);
		} else if (e.getKeyCode() == KeyEvent.VK_HOME) markerIndex = 0;
		else if (e.getKeyCode() == KeyEvent.VK_END) markerIndex = this.getText().length();
		else {

			for (char letter : acceptedCharacters.toCharArray()) {

				if (letter == e.getKeyChar()) {

					charsToInsert.offer(e.getKeyChar());
					break;

				}

			}

		}

		if (cleanSelect) {
			selectionEndIndex = NO_SELECTION;
			selectionStartIndex = NO_SELECTION;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	private enum CharacterType {

		Add, Remove_Backwards, Remove_Forewords

	}
}
