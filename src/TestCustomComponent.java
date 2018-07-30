import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TestCustomComponent {

	public static void main(String args[]) {
		JFrame frame = new JFrame("Test");
		frame.setLayout(null);

		Action action = new Action();
		
		CustomButton cbtn = new CustomButton();
		cbtn.setTheme(Themes.Black);
		cbtn.setLocation(0, 0);
		cbtn.setText("Text");
		cbtn.addActionListener(action);
		cbtn.setRenderingStyle(CustomButton.RenderingStyle.Gradient);
		frame.add(cbtn);

		CustomButton cbtn2 = new CustomButton();
		cbtn2.setTheme(Themes.Blue);
		cbtn2.setLocation(75, 0);
		cbtn2.setText("Text");
		cbtn2.addActionListener(action);
		cbtn2.setRenderingStyle(CustomButton.RenderingStyle.Gradient);
		frame.add(cbtn2);

		CustomButton cbtn3 = new CustomButton();
		cbtn3.setTheme(Themes.Green);
		cbtn3.setLocation(0, 25);
		cbtn3.setText("Text");
		cbtn3.addActionListener(action);
		cbtn3.setRenderingStyle(CustomButton.RenderingStyle.Gradient);
		frame.add(cbtn3);

		CustomButton cbtn4 = new CustomButton();
		cbtn4.setTheme(Themes.Red);
		cbtn4.setLocation(75, 25);
		cbtn4.setText("Text");
		cbtn4.addActionListener(action);
		cbtn4.setRenderingStyle(CustomButton.RenderingStyle.Gradient);
		frame.add(cbtn4);

		CustomButton cbtn5 = new CustomButton();
		cbtn5.setTheme(null);
		cbtn5.setLocation(0, 50);
		cbtn5.setText("Text");
		cbtn5.addActionListener(action);
		cbtn5.setRenderingStyle(CustomButton.RenderingStyle.Gradient);
		frame.add(cbtn5);

		CustomButton cbtn6 = new CustomButton();
		cbtn6.setTheme(Themes.Purple);
		cbtn6.setLocation(75, 50);
		cbtn6.setText("Text");
		cbtn6.addActionListener(action);
		cbtn6.setRenderingStyle(CustomButton.RenderingStyle.Gradient);
		frame.add(cbtn6);

		CustomButton cbtn7 = new CustomButton();
		cbtn7.setTheme(Themes.LightBlue);
		cbtn7.setLocation(0, 75);
		cbtn7.setText("Text");
		cbtn7.addActionListener(action);
		cbtn7.setRenderingStyle(CustomButton.RenderingStyle.Gradient);
		frame.add(cbtn7);

		CustomButton cbtn8 = new CustomButton();
		cbtn8.setTheme(Themes.Orange);
		cbtn8.setLocation(75, 75);
		cbtn8.setText("Text");
		cbtn8.addActionListener(action);
		cbtn8.setRenderingStyle(CustomButton.RenderingStyle.Gradient);
		frame.add(cbtn8);
		
		JButton jbtn = new JButton();
		jbtn.setText("Text");
		jbtn.setLocation(150, 0);

		CustomTextBox ctxt = new CustomTextBox();
		ctxt.setLocation(0, 105);
		ctxt.setTheme(null);
		ctxt.setText("Test Text");
		ctxt.setEnabled(true);
		frame.add(ctxt);

		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

	
	
	static class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println(((CustomButton)e.getSource()).getText());
			// System.out.println(e.getID());
			// System.out.println(e.getModifiers());
			// System.out.println(e.getActionCommand());
		}

	}

}
