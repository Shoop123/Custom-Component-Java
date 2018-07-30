import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;

public abstract class Custom extends JComponent implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;

	private Thread paintThread;
	
	private ThemeFactory tf;
	
	private Themes theme;
	private Color darkTheme;
	private Color lightTheme;
	private Color defaultTheme;
	
	private Color disabledColor = new Color(96, 96, 96);
	private Color defaultColor = Color.BLACK;
	
	private String text;
	
	protected static ArrayList<Custom> objects = new ArrayList<Custom>();
	protected static ScheduledThreadPoolExecutor pool = null;
	
	protected int refreshRate = 1000 / 60;
	
	private boolean isMouseOnTop = false;
	private boolean isMouseDown = false;
	
	public Custom() {
		this(Themes.Black);
	}
	
	public Custom(Themes theme) {
		this.paintThread = new Thread(new PaintThread());
		
		this.theme = theme;
		
		this.tf = new ThemeFactory(this.theme);
		
		if(pool == null)
		{
			pool = new ScheduledThreadPoolExecutor(2);
			pool.scheduleAtFixedRate(paintThread, 0, refreshRate, TimeUnit.MILLISECONDS);	
		}
		
		this.addMouseListener(this);
		
		initializeComponent();
		
	}
	
	public Themes getTheme(){ return this.theme; }
	public void setTheme(Themes newTheme) { 
		
		this.theme = newTheme; 
	
		tf.setTheme(this.theme);
		
		setColors();
		
	}
	
	public String getText(){ return this.text; }
	public void setText(String newText) {  this.text = newText; }
	
	public boolean getMouseOnTop() { return isMouseOnTop; }
	public boolean getIsMouseDown() { return isMouseDown; }
	
	public Color getLightTheme() { return lightTheme; }
	public Color getDefaultTheme() { return defaultTheme; }
	public Color getDarkTheme() { return darkTheme; }
	
	public Color getDisabledColor() { return disabledColor; }
	public void setDisabledColor(Color newDisabledColor) { 
		
		if(newDisabledColor == null) this.disabledColor = new Color(96, 96, 96);
		else this.disabledColor = newDisabledColor;
		
	}
	
	public Color getDefaultColor() { return defaultColor; }
	public void setDefaultColor(Color newDefaultColor) { 
		
		if(newDefaultColor == null) this.defaultColor = Color.BLACK;
		else this.defaultColor = newDefaultColor; 
		
	}
	
	private void setColors() {
		
		darkTheme = new Color(tf.getRDark(), tf.getGDark(), tf.getBDark());
		lightTheme = new Color(tf.getRLight(), tf.getGLight(), tf.getBLight());
		defaultTheme = new Color(tf.getRDefault(), tf.getGDefault(), tf.getBDefault());
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
	}
	
	private void initializeComponent() {
		
		this.setFont(new Font("Serif", 0, 15));
	
		this.setForeground(Color.white);
		
		this.setText("");
		
	}
	
	class PaintThread implements Runnable {
		
		@Override
		public void run() {
			
			for(Custom object : objects)
				object.repaint();
			
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { this.isMouseOnTop = true; }

	@Override
	public void mouseExited(MouseEvent e) { this.isMouseOnTop = false; }

	@Override
	public void mousePressed(MouseEvent e) {
		this.isMouseDown = true;
		this.isMouseOnTop = false;
		this.requestFocus();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.isMouseDown = false;
		this.isMouseOnTop = true;
		
	}

	@Override
	public void mouseDragged(MouseEvent e) { }

	@Override
	public void mouseMoved(MouseEvent e) { }


}
