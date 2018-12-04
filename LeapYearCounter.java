import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;



public class LeapYearCounter {

	private JFrame frame;
	private File file;
	private int count = 0;
	private JTextArea textArea;
	private String toDisplay;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LeapYearCounter window = new LeapYearCounter();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LeapYearCounter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 405, 274);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBounds(25, 57, 207, 139);
		frame.getContentPane().add(textArea);

		String toDisplay;

		Thread threadListen = new Thread(){
			public void run(){
				ServerSocket serverSocket = null;
				try{
					serverSocket = new ServerSocket(88);
					while(true){

						setToDisplay("");
						Socket socket = serverSocket.accept();
						DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
						setToDisplay(dataInputStream.readUTF());
						try{
							int year = Integer.parseInt(getToDisplay());
							if(year%4==0){
								count++;
								setToDisplay("Leap year count "+count+"("+year+")\n");
							}
							else{
								setToDisplay(year+" is a regular year\n");
							}
						}catch(IllegalArgumentException e){
							setToDisplay("Please enter integer value only\n");
						}
						textArea.append(getToDisplay());
						DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
						dataOutputStream.writeUTF(getToDisplay());
						


					}
				}catch(IOException e1){
					e1.printStackTrace();
				}
			}
		};
		threadListen.start();
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Runnable run = new Runnable(){
					@Override
					public void run(){
						try {
							Socket clientSocket = new Socket("10.73.37.120",88);
							DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
							outToServer.writeUTF(textArea.getText());
							DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
							String fromServer = inFromServer.readUTF();
							textArea.setText(fromServer);
							clientSocket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		});
		btnSend.setBounds(258, 112, 89, 23);
		frame.getContentPane().add(btnSend);

		JLabel lblFromPartner = new JLabel("From partner:");
		lblFromPartner.setBounds(25, 30, 94, 14);
		frame.getContentPane().add(lblFromPartner);
	}

	public void setToDisplay(String toDisplay){
		this.toDisplay = toDisplay;
	}

	public String getToDisplay(){
		return toDisplay;
	}
}
