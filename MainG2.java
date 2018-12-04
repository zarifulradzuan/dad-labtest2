import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class MainG2 {
	
	

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainG2 window = new MainG2();
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
	public MainG2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea receiverListenerArea = new JTextArea();
		receiverListenerArea.setBounds(31, 37, 349, 154);
		frame.getContentPane().add(receiverListenerArea);
		
		JButton btnSaveToFile = new JButton("Save to file");
		btnSaveToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

						try {
							String input = receiverListenerArea.getText();
						    BufferedWriter writer;
							writer = new BufferedWriter(new FileWriter("Welcome.txt", true));
							writer.append("\n");
						    writer.append(input);
						    writer.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					    				
			}
		});
		btnSaveToFile.setBounds(142, 204, 97, 25);
		frame.getContentPane().add(btnSaveToFile);
		
		JLabel lblMessageFromPartner = new JLabel("Message from partner");
		lblMessageFromPartner.setBounds(31, 13, 165, 16);
		frame.getContentPane().add(lblMessageFromPartner);
		
		Listener listener = new Listener(receiverListenerArea);
		listener.start();
	}
	
	

}
