import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ClientFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	Socket s;
	boolean stop;
	
	JTextArea jta = new JTextArea();
	JTextArea chat = new JTextArea();
	JButton button = new JButton("Send");
	
	PrintWriter out;
	
	public ClientFrame(Socket s){
		this.s = s;
		try {
			this.out = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.stop = false;
		
		setVisible(true);
		setSize(400,200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		
		this.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onMyButtonPressed();
			}
		});
		
		this.jta.setText("Write here.");
		this.getContentPane().add(this.jta, BorderLayout.NORTH);
		this.chat.setEditable(false);
		this.chat.setBackground(Color.BLACK);
		this.chat.setForeground(Color.WHITE);
		this.getContentPane().add(this.chat, BorderLayout.CENTER);
		this.getContentPane().add(this.button, BorderLayout.SOUTH);
		validate();
	}

	private void onMyButtonPressed() {
		String text = this.jta.getText();
		try {
			this.out.println(text);
			this.out.flush();
		}catch(Exception e) {
			System.err.println("cant send msg");
			e.printStackTrace();
			this.stop = true;
		}
	}
	
	public boolean getStop() {
		return this.stop;
	}
	
	public void add(String text) {
		this.chat.append("\n"+text);
	}
}
