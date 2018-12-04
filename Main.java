import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class Main {

	private JFrame frame;
	private JTextField FilePathtextField;
	private JTextField txtKeyGenerate;
	private String filePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
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
	public Main() {
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
		
		JButton btnPickFile = new JButton("Pick File");
		btnPickFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	FilePathtextField.setText(chooser.getSelectedFile().getName());
		        	filePath = chooser.getSelectedFile().getAbsolutePath();
		        }
			}
		});
		btnPickFile.setBounds(305, 23, 97, 25);
		frame.getContentPane().add(btnPickFile);
		
		JButton btnStartProcess = new JButton("Start Process");
		btnStartProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thrd1 = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							BufferedReader br = new BufferedReader(new FileReader(new File(filePath))) ;
							String line = "";
							int pwd = 0;
							int bitp = 0;
							
						    while ((line = br.readLine()) != null) {
						    	if (line.contains("password")) {
						    		pwd++;
						    	}else if (line.contains("bitp3123")) {
						    		bitp++;
						    	}
						    }
						    
						    txtKeyGenerate.setText(String.valueOf(pwd*bitp));
						    
						    
						       // process the line.
						    }catch(Exception e) {
						    	e.printStackTrace();
						    }
						
						
						    
						}
						
					}
					
				);
				
				thrd1.start();
			}
		});
		btnStartProcess.setBounds(305, 84, 97, 25);
		frame.getContentPane().add(btnStartProcess);
		
		JButton btnSendKey = new JButton("Send Key");
		btnSendKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Thread thrd2 = new Thread(new Runnable() {
					
					public void run() {
						
						try {
							Socket skt = new Socket("127.0.0.1", 8082);
							DataOutputStream out = new DataOutputStream(skt.getOutputStream());
							out.write(filePath.getBytes());
							skt.close();
							
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
				
				thrd2.start();
				
				
			}
		});
		btnSendKey.setBounds(305, 155, 97, 25);
		frame.getContentPane().add(btnSendKey);
		
		JLabel lblFilePath = new JLabel("File Path: ");
		lblFilePath.setBounds(12, 27, 77, 16);
		frame.getContentPane().add(lblFilePath);
		
		JLabel lblKeyGenerate = new JLabel("Key Generate");
		lblKeyGenerate.setBounds(12, 88, 77, 16);
		frame.getContentPane().add(lblKeyGenerate);
		
		FilePathtextField = new JTextField();
		FilePathtextField.setText("File Path");
		FilePathtextField.setBounds(118, 24, 116, 22);
		frame.getContentPane().add(FilePathtextField);
		FilePathtextField.setColumns(10);
		
		txtKeyGenerate = new JTextField();
		txtKeyGenerate.setText("generated key here");
		txtKeyGenerate.setBounds(118, 85, 116, 22);
		frame.getContentPane().add(txtKeyGenerate);
		txtKeyGenerate.setColumns(10);
		
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setBounds(12, 134, 56, 16);
		frame.getContentPane().add(lblOutput);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 155, 256, 85);
		frame.getContentPane().add(textArea);
	}
}
