import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServerGuiRunThis extends Thread{

	/* The int holding the server port from which clients can connect. Set to 14001 by default. */
	static int serverPort= 14001;
	
	static ServerSocket serverSocket = null;
	Socket s;
	
	/* The GUI for the server. */
	static ChatServerGui window;
	
	/* This list holds the threads which manage each of the clients. Each client gets a separate thread. */
	private static List<ChatServerGuiRunThis> clientList = new ArrayList<ChatServerGuiRunThis>();
	
	
	/* A boolean to separate the first run of run() from the others. */
	private static boolean serverReading =false;
	
	
	/**
	 * Default constructor, only called when looking to run the first thread that manages the input from the user.
	 */
	public ChatServerGuiRunThis() {
	}
	
	/**
	 * Constructor that sets up its socket.
	 */
	public ChatServerGuiRunThis(Socket socket) {
		s= socket;
	}

	/**
	 * This is only run when start() is used to create a thread. 
	 * It is used to take in inputs from clients and then pass on these inputs.
	 */
	public void run() {
		try {
			//This is always only run by the first thread, manages the port input.
			if (!serverReading) {
				serverReading = !serverReading;
				while(true) {
					//Looks if the input is different from the current port number and checks if the button is being pressed.
					if(window.portTxtBox.getText()!=Integer.toString(serverPort) && window.btnConfirm.getModel().isPressed()) {
						//Makes sure the input is a single number.
						if (window.portTxtBox.getText().matches("\\d+")) {
							//Makes the port equal to the new port.
							serverPort = Integer.parseInt(window.portTxtBox.getText().trim());
							window.currentPort.setText(window.portTxtBox.getText());
							
							//Clears the input box and chat history.
							window.portTxtBox.setText("");
							window.chatHistory.setText("");
							window.serverConnections.append("Chat history cleared.\n");
							window.serverConnections.append("Changed port to: " + serverPort+"\n");
							
							//Sends a message to all clients that the server is closing and where it is moving to.
							sendToAll(null,true);
							
							//Disconnects all clients.
							for(int i = clientList.size()-1; i>-1; i--) {
								ChatServerGuiRunThis st = clientList.get(i);
								st.s.close();
							}
							
							//Starts looking for clients again.
							//serverSocket.close();
							runServer();
						}
					}
				}
			}else {
			//This runs unless it is the first thread.
			window.serverConnections.append("Server accepted connection on " + serverSocket.getLocalPort() + " ; " + s.getPort()+"\n");
			InputStreamReader r = new InputStreamReader(s.getInputStream());
			BufferedReader clientIn = new BufferedReader(r);

			String userInput;
			//Reads in a line, if null that means the user has disconnected so the while is not run and the connection is closed.
			while((userInput = clientIn.readLine())!=null) {
				//"null" is the users command to exit the program, so has its connection terminated by breaking out of the loop.
				if (!userInput.equals("null")) {
					window.chatHistory.append(userInput+"\n");
					//The userInput is sent to all users using sendToAll.
					sendToAll(userInput,false);
				}else {
					break;
				}
			}
			//Closing connection.
			window.serverConnections.append("Server closing connection " + serverSocket.getLocalPort() + " ; " + s.getPort()+"\n");
			this.s.close();
			clientIn.close();
			}
		
		} catch (IOException e) {
			window.serverConnections.append("Connection crash.");
		}catch(NullPointerException e) {
			System.out.println("GUI change port crash.");
		}
	}

	/**
	 * This is used to send a message to all other clients connected to the server.
	 */
	void sendToAll(String msg, boolean close) {
		try {
			//Cycles through threads that are running.
			for(int i = clientList.size()-1; i>-1; i--) {
				ChatServerGuiRunThis thread = clientList.get(i);
				//Checks if alive, if not remove it from the list.
				if(thread.isAlive()) {
					PrintWriter clientOut = new PrintWriter(thread.s.getOutputStream(), true);
					//Checks if it has been called because of the server closing.
					if (close == false) {
						clientOut.println(msg);
					}else {
						clientOut.println("Server shutting down and moving to socket: "+ serverPort);
						thread.s.close();
					}
				}else {
					clientList.remove(i);
				}

			}
		} catch (IOException e) {
			window.serverConnections.append("Printing crash");
		}
	}

	/**
	 * The main method sorts out the GUI then runs the server code.
	 */
	public static void main(String[] args) {
		try {
			//This creates the window and makes it visible before the rest of the code runs.
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					try {
						window = new ChatServerGui();
						window.frmChatserver.setVisible(true);
					} catch (Exception e) {
						System.out.println("GUI crash");
					}
				}
			});
			runServer();
        } catch (InvocationTargetException e) {
        	System.out.println("GUI crash");
		} catch (InterruptedException e) {
			System.out.println("GUI crash");
		}
	}
	
	/**
	 * This creates the threads for the clients and sets up the different aspects of the server.
	 */
	public static void runServer() {
		try {
			//Sets up the thread that reads user input from the GUI.
			ChatServerGuiRunThis readingThread = new ChatServerGuiRunThis();
			readingThread.start();
			//Sets up server socket.
			serverSocket = new ServerSocket(serverPort);
			window.serverConnections.append("Server listening.\n");
			
			while (true) {
				//Creates and runs a thread for a connected client.
				ChatServerGuiRunThis thread = new ChatServerGuiRunThis(serverSocket.accept());
				thread.start();
				clientList.add(thread);
			}
		}catch (BindException ex) {
			window.serverConnections.append("Unable to start server at server port: " + serverPort + "\n");
		}catch (SocketException ex) {
			window.currentPort.setText(Integer.toString(serverPort));
		} catch (IOException e) {
			window.serverConnections.append("Unable to start server at server port: " + serverPort + "\n");
		}
	}
}