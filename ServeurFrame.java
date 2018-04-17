import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ServeurFrame extends JFrame{
	JTextArea text = new JTextArea();
	public ServeurFrame(){
		setSize(400,200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		text.setText("Serveur started");
		this.add(text, BorderLayout.CENTER);
		
		this.validate();
	}
	
	public void aff(String t){
		text.setText(t);
	}
	
	public void add(String t) {
		text.append("\n"+t);
	}
}
