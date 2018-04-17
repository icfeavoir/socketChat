import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket socket;
	BufferedReader in;
	PrintWriter out;
	
	ClientFrame frame;
	
	public Client(String serverName, int serverPort) {
		try {
			socket = new Socket(serverName, serverPort);
			this.frame = new ClientFrame(socket);
			System.out.println("Connected: " + socket);
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream(), true);

			new Thread(new Runnable() {
				public void run() {
					try {
						startReading();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Exception.");
		}
	}

	private void startReading() throws IOException {
		String text = "";
		while(this.socket != null && this.socket.isConnected() && !text.equals("null")) {
			try{
				text = in.readLine();
				this.frame.add(text);
			}catch(Exception e){
				System.err.println("Socket Exception in read stream");
				e.printStackTrace();
			}
		}
		System.err.println("client disconnected.");
	}
	
	private void sendMsg(String msg) {
		if(this.socket.isConnected()) {
			try {
				PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
				out.println(msg);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("Cant send msg");
		}
	}
}
