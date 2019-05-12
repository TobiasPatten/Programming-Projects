import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class ChatClientGui {

	public JFrame frmChatclient;
	public JTextField serverPortInputTxt;
	public JTextField ipAddressInputTxt;
	public JTextField messageTxt;
	public JTextArea chatHistory;
	public JButton btnConfirmServerPort;
	public JButton btnConfirmIPAddress;
	public JLabel lblCurrentIPAddress;
	public JButton btnConnect;
	public JLabel lblCurrentServerPort;
	public JButton btnDisconnect;
	public JButton btnSend;
	public JLabel lblConnection;
	public JLabel lblUsername;
	

	/**
	 * Create the application.
	 */
	public ChatClientGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatclient = new JFrame();
		frmChatclient.setTitle("ChatClient");
		frmChatclient.setBounds(100, 100, 450, 300);
		frmChatclient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatclient.getContentPane().setLayout(null);
		
		serverPortInputTxt = new JTextField();
		serverPortInputTxt.setBounds(20, 39, 130, 26);
		frmChatclient.getContentPane().add(serverPortInputTxt);
		serverPortInputTxt.setColumns(10);
		
		chatHistory = new JTextArea();
		chatHistory.setRows(100);
		chatHistory.setColumns(20);
		chatHistory.setBounds(289, 44, 142, 167);
		chatHistory.setEditable(false);
		frmChatclient.getContentPane().add(chatHistory);
		
		btnConfirmServerPort = new JButton("Confirm");
		btnConfirmServerPort.setBounds(147, 39, 117, 29);
		frmChatclient.getContentPane().add(btnConfirmServerPort);
		
		btnConfirmIPAddress = new JButton("Confirm");
		btnConfirmIPAddress.setBounds(147, 83, 117, 29);
		frmChatclient.getContentPane().add(btnConfirmIPAddress);
		
		btnSend = new JButton("Send");
		btnSend.setBounds(327, 243, 117, 29);
		btnSend.setEnabled(false);
		frmChatclient.getContentPane().add(btnSend);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(147, 172, 117, 29);
		btnDisconnect.setEnabled(false);
		frmChatclient.getContentPane().add(btnDisconnect);
		
		ipAddressInputTxt = new JTextField();
		ipAddressInputTxt.setBounds(20, 83, 130, 26);
		frmChatclient.getContentPane().add(ipAddressInputTxt);
		ipAddressInputTxt.setColumns(10);
		
		messageTxt = new JTextField();
		messageTxt.setBounds(20, 243, 298, 26);
		frmChatclient.getContentPane().add(messageTxt);
		messageTxt.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Input new server port.");
		lblNewLabel.setBounds(20, 21, 209, 16);
		frmChatclient.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Input new IP address.");
		lblNewLabel_1.setBounds(20, 65, 157, 16);
		frmChatclient.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Currnet IP address:");
		lblNewLabel_2.setBounds(20, 124, 142, 16);
		frmChatclient.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Current server port:");
		lblNewLabel_3.setBounds(20, 144, 130, 16);
		frmChatclient.getContentPane().add(lblNewLabel_3);
		
		lblCurrentIPAddress = new JLabel("localhost");
		lblCurrentIPAddress.setBounds(147, 124, 130, 16);
		frmChatclient.getContentPane().add(lblCurrentIPAddress);
		
		lblCurrentServerPort = new JLabel("14001");
		lblCurrentServerPort.setBounds(147, 144, 61, 16);
		frmChatclient.getContentPane().add(lblCurrentServerPort);
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(20, 172, 117, 29);
		frmChatclient.getContentPane().add(btnConnect);
		
		JLabel lblNewLabel_6 = new JLabel("Current status:");
		lblNewLabel_6.setBounds(20, 198, 101, 16);
		frmChatclient.getContentPane().add(lblNewLabel_6);
		
		lblConnection = new JLabel("Not connected");
		lblConnection.setBounds(127, 198, 117, 16);
		frmChatclient.getContentPane().add(lblConnection);
		
		JLabel lblNewLabel_7 = new JLabel("Chat history.");
		lblNewLabel_7.setBounds(276, 21, 142, 16);
		frmChatclient.getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Message.");
		lblNewLabel_8.setBounds(20, 225, 91, 16);
		frmChatclient.getContentPane().add(lblNewLabel_8);
		
		JScrollPane scrollPane = new JScrollPane(chatHistory);
		scrollPane.setBounds(276, 42, 150, 175);
		frmChatclient.getContentPane().add(scrollPane);
		
		JLabel lblUsernameTag = new JLabel("Username:");
		lblUsernameTag.setBounds(89, 226, 73, 16);
		frmChatclient.getContentPane().add(lblUsernameTag);
		
		lblUsername = new JLabel("Not set.");
		lblUsername.setBounds(158, 225, 150, 16);
		frmChatclient.getContentPane().add(lblUsername);
	}
}
