import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ChatServerGui {

	public JFrame frmChatserver;
	public JTextField portTxtBox;
	public JLabel currentPort;
	public JTextArea serverConnections;
	public JTextArea chatHistory;
	public JButton btnConfirm;
	private JScrollPane scrollPane_1;

	/**
	 * Create the application.
	 */
	public ChatServerGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatserver = new JFrame();
		frmChatserver.setTitle("ChatServer");
		frmChatserver.setBounds(100, 100, 450, 300);
		frmChatserver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatserver.getContentPane().setLayout(null);
		
		serverConnections = new JTextArea(100,20);
		serverConnections.setEditable(false);
		serverConnections.setBounds(10, 152, 193, 109);
		frmChatserver.getContentPane().add(serverConnections);
		
		chatHistory = new JTextArea(100,20);
		chatHistory.setEditable(false);
		chatHistory.setBounds(236, 145, 193, 108);
		frmChatserver.getContentPane().add(chatHistory);
		
		portTxtBox = new JTextField();
		portTxtBox.setBounds(23, 46, 130, 26);
		frmChatserver.getContentPane().add(portTxtBox);
		portTxtBox.setColumns(10);
		
		JLabel lblInputNewIp = new JLabel("Input new port.");
		lblInputNewIp.setBounds(23, 18, 134, 16);
		frmChatserver.getContentPane().add(lblInputNewIp);
		
		JLabel lblCurrentIpAdress = new JLabel("Current port:");
		lblCurrentIpAdress.setBounds(23, 84, 130, 16);
		frmChatserver.getContentPane().add(lblCurrentIpAdress);
		
		currentPort = new JLabel("14001");
		currentPort.setBounds(108, 84, 61, 16);
		frmChatserver.getContentPane().add(currentPort);
		
		JLabel lblServerConnectionHistory = new JLabel("Server connections.");
		lblServerConnectionHistory.setBounds(20, 127, 149, 16);
		frmChatserver.getContentPane().add(lblServerConnectionHistory);
		
		JLabel lblChatHistory = new JLabel("Chat history.");
		lblChatHistory.setBounds(230, 127, 149, 16);
		frmChatserver.getContentPane().add(lblChatHistory);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(165, 46, 117, 29);
		frmChatserver.getContentPane().add(btnConfirm);
		
		JScrollPane scrollPane = new JScrollPane(serverConnections);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 150, 200, 110);
		frmChatserver.getContentPane().add(scrollPane);
		
		scrollPane_1 = new JScrollPane(chatHistory);
		scrollPane_1.setBounds(230, 150, 200, 110);
		frmChatserver.getContentPane().add(scrollPane_1);
	}
}
