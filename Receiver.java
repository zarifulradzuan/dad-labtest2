import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver {

	public Receiver() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket varServer = new ServerSocket(8082);
			Socket skt = varServer.accept();
			DataInputStream in = new DataInputStream(skt.getInputStream());
			String strVar1 = in.readUTF();
			System.out.print(strVar1);
			skt.close();
			varServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
