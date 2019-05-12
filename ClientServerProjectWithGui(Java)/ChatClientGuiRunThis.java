import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class ChatClientGuiRunThis implements Runnable{
	
	static ChatClientGui window;
	
	static int serverSocket = 14001;
	static String ipAddress = "localhost";
	
	public boolean firstConnection = true;

	/* The server's socket. */
	public Socket server;
	
	/* The clients username, input when run. */
	public String username = "fail";
	
	/* Two threads, one for reading from the user and one for reading from the server. */
	public Thread thread1;
	public Thread thread2;
	
	public BufferedReader userIn;
	public PrintWriter serverOut;
	public BufferedReader serverIn;
	
	/* A boolean which allows thread 1 to close thread 2. */
	public boolean disconnect = false;

	/**
	 * Sets up both threads and runs the first thread.
	 */
	public ChatClientGuiRunThis() {
		try {
			thread1 = new Thread(this);
			disconnect = false;
			thread1.start();
		} catch (Exception e) {
		}
	}
	
	/**
	 * Manages the client through 2 threads.
	 */
	public void run() {
		try {
			String serverResponce;
			if (Thread.currentThread() == thread2) {
				//Runs the code for reading from the server if still connected.
				disconnect =false;
				while(!disconnect) {
					try {
						serverResponce = serverIn.readLine();
						//If null is received from the server that means the connection has been closed.
						if (serverResponce != null) {
							window.chatHistory.append(serverResponce + "\n");
						}else {
							//Breaks out of the infinite loop to then close the client.
							disconnectFromServer();
							disconnect = true;
						}
					}catch (IOException e) {
						disconnectFromServer();
						disconnect = true;
					}
				}
				firstConnection = !firstConnection;
			}else if (Thread.currentThread().getName().equals("Thread-0")){
				//This is run by the other thread.
				//Runs the code for managing the interface until disconnected.
				while(true) {
					
					try {
						//Manages the sending to the server.
						if (window.btnSend.getModel().isPressed() && !window.messageTxt.getText().equals("")) {
							//Looks if the user has just connected and sent nothing else so they can set a username.
							if (!firstConnection) {
								//Sends to server.
								serverOut.println(username + ": " + window.messageTxt.getText());
								window.messageTxt.setText("");
							}else {
								//Sets the username and resets for sending.
								username = window.messageTxt.getText();
								window.messageTxt.setText("");
								window.chatHistory.setText("");
								window.chatHistory.append("Chat away!\n");
								window.lblUsername.setText(username);
								firstConnection = !firstConnection;
							}
						}
					}catch(NullPointerException e) {
						
						
					}
					
					//Looks to connect to the server.
					if (window.btnConnect.getModel().isPressed()) {
						try {
							//Sets up connection.
							
							server = new Socket(ipAddress, serverSocket);
							serverOut = new PrintWriter(server.getOutputStream(), true);
							serverIn = new BufferedReader(new InputStreamReader(server.getInputStream()));
							
							//Starts the reading from the server.
							thread2 = new Thread(this);
							thread2.start();
							
							//Enables and disables buttons to make sure only the correct button can be pressed.
							window.btnConnect.setEnabled(false);
							window.btnDisconnect.setEnabled(true);
							window.btnSend.setEnabled(true);
							window.lblConnection.setText("Connected");
							
							//Resets chat history and tells user to input username.
							window.chatHistory.setText("");
							window.chatHistory.append("Enter username.\n");
							
						}catch(IOException e) {
							window.chatHistory.setText("");
							window.chatHistory.append("Can't connect.\n");
						}
					}
					
					//Checks if the port can be changed.
					if (window.serverPortInputTxt.getText() != window.lblCurrentServerPort.getText() && window.btnConfirmServerPort.getModel().isPressed()) {
						//Checks if the new port number is in the correct format.
						if (window.serverPortInputTxt.getText().matches("\\d+")) {
							//Makes the port equal to the new port.
							serverSocket = Integer.parseInt(window.serverPortInputTxt.getText().trim());
							window.lblCurrentServerPort.setText(window.serverPortInputTxt.getText());

							//Clears the input box and chat history.
							window.serverPortInputTxt.setText("");
							window.chatHistory.setText("");
							if (window.btnDisconnect.isEnabled()) {
								server.close();
							}
						}
					}
					
					//Checks if the IP address can be changed.
					if (window.ipAddressInputTxt.getText() != window.lblCurrentIPAddress.getText() && window.btnConfirmIPAddress.getModel().isPressed()) {
						if( !window.ipAddressInputTxt.getText().equals(window.lblCurrentIPAddress.getText())) {
							//Checks if the new IP address is in the correct format.
							if (window.ipAddressInputTxt.getText().matches("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)")) {
								//Makes the IP address equal to the new IP address.
								ipAddress = window.ipAddressInputTxt.getText().trim();
								window.lblCurrentIPAddress.setText(window.ipAddressInputTxt.getText());
								
								//Clears the input box and chat history.
								window.ipAddressInputTxt.setText("");
								window.chatHistory.setText("");
								
								//Sets to exit out of the other thread and modifies buttons.
								disconnect = true;
								window.btnDisconnect.setEnabled(false);
								window.btnConnect.setEnabled(true);
								window.lblUsername.setText("Not set.");
								
							}else if (window.ipAddressInputTxt.getText().equals("localhost")){
								//Makes the IP address equal to the new IP address.
								ipAddress = window.ipAddressInputTxt.getText().trim();
								window.lblCurrentIPAddress.setText(window.ipAddressInputTxt.getText());
								
								//Clears the input box and chat history.
								window.ipAddressInputTxt.setText("");
								window.chatHistory.setText("");
								
								if (window.btnDisconnect.isEnabled()) {
									server.close();
								}
							}
						}
					}
					
					//Manages the disconnect button.
					if (window.btnDisconnect.getModel().isPressed()) {
						server.close();
					}
				}
			}
		} catch (IOException e) {
			window.chatHistory.append("Failure to close.\n");
		}
	}
	
	/**
	 * Sets to exit out of the other thread and modifies buttons.
	 */
	public void disconnectFromServer() {
		try {
		serverOut.close();
		serverIn.close();
		window.btnDisconnect.setEnabled(false);
		window.btnConnect.setEnabled(true);
		window.btnSend.setEnabled(false);
		window.chatHistory.setText("");
		window.chatHistory.setText("Disconnected from server.\n");
		window.lblConnection.setText("Not connected.");
		window.lblUsername.setText("Not set.");
		}catch (IOException e) {
			window.chatHistory.setText("Failure to disconnect.");
		}
	}

	/**
	 * The main method sorts out the GUI then runs the client code.
	 */
	public static void main(String[] args) {
		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					try {
						window = new ChatClientGui();
						window.frmChatclient.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (InvocationTargetException e) {
		} catch (InterruptedException e) {
		}
		new ChatClientGuiRunThis();
	}
}
