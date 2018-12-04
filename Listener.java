import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

public class Listener extends Thread {
	private JTextArea receiverListenerArea;

	public Listener(JTextArea receiverListenerArea) {
		// TODO Auto-generated constructor stub
		this.receiverListenerArea = receiverListenerArea;
	}
	
	public void run() {
		
		try {
			ServerSocket varServer = new ServerSocket(88);
			while(true){
			Socket skt = varServer.accept();
			DataInputStream in = new DataInputStream(skt.getInputStream());
			receiverListenerArea.setText(in.readUTF());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public Listener(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
	}

	public Listener(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public Listener(ThreadGroup group, Runnable target) {
		super(group, target);
		// TODO Auto-generated constructor stub
	}

	public Listener(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}

	public Listener(Runnable target, String name) {
		super(target, name);
		// TODO Auto-generated constructor stub
	}

	public Listener(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}

	public Listener(ThreadGroup group, Runnable target, String name, long stackSize) {
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}
	


}
