import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class Server {
	
	ArrayList<Socket> alSocket;
	ServerSocket ss;
	
//	ServeurFrame serv;
	
	Server(int port){
		this.ss = null;
		this.alSocket = new ArrayList<>();
		try {
			ss = new ServerSocket(port);		
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		this.serv = new ServeurFrame();
		
		Thread acceptClient = new Thread(new Runnable() {
			@Override
			public void run() {
				acceptClient();
			}
		});
		acceptClient.start();
	}
	
	private void acceptClient() {
		while(true) {
			try {
				Socket s = this.ss.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				this.alSocket.add(s);
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);
				out.println("Welcome");
				out.flush();
				new Thread(new Runnable() {
					@Override
					public void run() {
						readStream(alSocket.size(), in, s);
					}
				}).start();
				
//				this.serv.add("New client, number "+this.clientList.size());
			} catch (IOException e) {
				System.err.println("Cant add cilent");
				e.printStackTrace();
			}
		}
	}
	
	private void readStream(int nb, BufferedReader in, Socket s) {
		String text = "";
 		while(s != null && s.isConnected() && !text.equals("null")) {
			try{
				text = in.readLine();
//				this.serv.add("["+nb+"] : "+text);
				this.sendMsgToAllExcept(nb, text);
			}catch(Exception e){
				System.err.println("Socket Exception in read stream");
				e.printStackTrace();
			}
		}
		System.out.println("Client is disconnected");
	}
	
	private void sendMsgToAllExcept(int nb, String text) {
		for(int i=1; i<=this.alSocket.size(); i++) {
			if(this.alSocket.get(i-1).isConnected()) {
				try {
					PrintWriter out = new PrintWriter(this.alSocket.get(i-1).getOutputStream(), true);
					out.println("["+(i==nb ? "You" : nb)+"] : "+text);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("Just noticed that "+i+" is disconnected");
			}
		}
	}
}
