import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.SwingConstants;


public class Sender {

	private JFrame frame;
	private JTextField txtFilePath;
	private JTextField txtKey;
	private File file;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sender window = new Sender();
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
	public Sender() {
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

		JLabel lblFilePath = new JLabel("File Path:");
		lblFilePath.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFilePath.setBounds(0, 36, 66, 14);
		frame.getContentPane().add(lblFilePath);

		txtFilePath = new JTextField();
		txtFilePath.setBounds(70, 33, 187, 20);
		frame.getContentPane().add(txtFilePath);
		txtFilePath.setColumns(10);

		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBounds(70, 144, 187, 91);
		frame.getContentPane().add(textArea);

		

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Runnable run = new Runnable(){
					@Override
					public void run(){
						try {
							Socket clientSocket = new Socket("localhost",8081);
							DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
							outToServer.writeUTF(txtKey.getText());
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
		btnSend.setBounds(267, 95, 89, 23);
		frame.getContentPane().add(btnSend);

		JLabel lblOutput = new JLabel("Output:");
		lblOutput.setBounds(70, 129, 46, 14);
		frame.getContentPane().add(lblOutput);
		
		txtKey = new JTextField();
		txtKey.setBounds(70, 96, 86, 20);
		frame.getContentPane().add(txtKey);
		txtKey.setColumns(10);
		
		JLabel lblKey = new JLabel("Key:");
		lblKey.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKey.setBounds(20, 99, 46, 14);
		frame.getContentPane().add(lblKey);
		
		JButton btnPickFile = new JButton("Pick File");
		btnPickFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fileChooser.showOpenDialog(null);
				txtFilePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		btnPickFile.setBounds(267, 32, 89, 23);
		frame.getContentPane().add(btnPickFile);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread threadStartProcess = new Thread(){
					public void run(){
						ReadFileThread readFile = new ReadFileThread(txtFilePath.getText());
						Thread threadReadFile = new Thread(readFile);
						threadReadFile.start();
					}
				};
				threadStartProcess.start();
			}
		});
		btnStart.setBounds(267, 61, 89, 23);
		frame.getContentPane().add(btnStart);
	}

	class ReadFileThread implements Runnable{
		int password=0, bitp=0;
		String filePath;
		File file;
		public ReadFileThread(String filePath){
			this.filePath = filePath;
			file = new File(filePath);
		}
		public void run(){
			FileReader reader = null;
			try {
				reader = new FileReader(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(reader);

			String read;
			try {
				while((read = br.readLine()) != null){
					if(read.contains("password"))
						password++;
					else if(read.contains("bitp3123"))
						bitp++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			txtKey.setText(String.valueOf(getMultiplied()));

		}
		
		public int getMultiplied(){
			return password*bitp;
		}
	}
}
