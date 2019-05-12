import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends Thread{
	/* The int holding the server port from which clients can connect. Set to 14001 by default. */
	static int serverPortNumber= 14001;
	
	static ServerSocket serverSocket = null;
	
	public Socket s;
	
	/* This list holds the threads which manage each of the clients. Each client gets a separate thread. */
	private static List<ChatServer> clientList = new ArrayList<ChatServer>();
	
	/* A variable that keeps track if the server is closing so the correct message can be output. */
	private static boolean closeServer =false;
	
	/* A boolean to separate the first run of run() from the others. */
	private static boolean serverReading =false;
	
	/**
	 * Default constructor, only called when looking to run the first thread that manages the input from the user.
	 */
	public ChatServer() {
	}
	
	/**
	 * Constructor that sets up its socket.
	 */
	public ChatServer(Socket socket) {
		s= socket;
	}
	
	/**
	 * This is only run when start() is used to create a thread. 
	 * It is used to take in inputs from clients and then pass on these inputs.
	 */
	public void run() {
		try {
			//This is always only run by the first thread, manages the console input.
			if (!serverReading) {
				serverReading = !serverReading;
				BufferedReader serverIn = new BufferedReader(new InputStreamReader(System.in));
				while(true) {
					if ((serverIn.readLine()).equals("EXIT")) {
						//Sets closeServer to true so it can be printed later that the server is closing.
						closeServer=true;
						
						//Sends a message to all clients that the server is shutting down.
						sendToAll(null,true);
						
						//Loops through all threads closing their client's sockets and readers.
						for(int i = clientList.size()-1; i>-1; i--) {
							ChatServer st = clientList.get(i);
							st.s.close();
							serverIn.close();
						}
						//Once all sockets are closed the server socket is closed and the program is exited.
						serverSocket.close();
						System.exit(0);
					}
				}
			}		
			System.out.println("Server accepted connection on " + serverSocket.getLocalPort() + " ; " + s.getPort() );
			
			//Buffers for reading from each threads client.
			InputStreamReader r = new InputStreamReader(s.getInputStream());
			BufferedReader clientIn = new BufferedReader(r);

			String userInput;
			
			//Reads in a line, if null that means the user has disconnected so the while is not run and the connection is closed.
			while((userInput = clientIn.readLine())!=null) {
				//"null" is the users command to exit the program, so has its connection terminated by breaking out of the loop.
				if (!userInput.equals("null")) {
					//The userInput is sent to all users using sendToAll.
					sendToAll(userInput,false);
					
				}else {
					break;
				}
			}
			//Closing connection.
			System.out.println("Server closing connection " + serverSocket.getLocalPort() + " ; " + s.getPort() );
			s.close();
			clientIn.close();
		
		} catch (IOException e) {
		}
	}

	/**
	 * This is used to send a message to all other clients connected to the server.
	 */
	void sendToAll(String msg, boolean close) {
		try {
			//Cycles through threads that are running.
			for(int i = clientList.size()-1; i>-1; i--) {
				ChatServer thread = clientList.get(i);

				//Stops sending back to the person who sent the message.
				if (!(thread.equals(this))) {
					//Checks if alive, if not remove it from the list.
					if(thread.isAlive()) {
						PrintWriter clientOut = new PrintWriter(thread.s.getOutputStream(), true);
						//Checks if it has been called because of the server closing.
						if (close == false) {
							clientOut.println(msg);
						}else {
							clientOut.println("Server shutting down.");
						}
					}else {
						clientList.remove(i);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();	
		}
	}

	/**
	 * The main method takes arguments and creates threads to deal with inputs.
	 */
	public static void main(String[] args) {
		//Loops through given arguments looking for a command for a new serverPortNumber.
		for (int i=0; i<args.length;i++) {
			if (args[i].contentEquals("-csp")){
				i++;
				serverPortNumber = Integer.parseInt(args[i]);
			}
		}
		
		try {
			//Sets up server socket.
            serverSocket = new ServerSocket(serverPortNumber);
            
            System.out.println("Server listening.");
            //Sets up the thread that reads user input from the console.
            ChatServer readingThread = new ChatServer();
            readingThread.start();
            
            while (true) {
            	//Creates and runs a thread for a connected client.
            	ChatServer thread = new ChatServer(serverSocket.accept());
            	thread.start();
            	clientList.add(thread);
            }
        }catch (IOException ex) {
        	//Checks if the server has been closed deliberately or not and gives a correct responce.
        	if (closeServer == true) {
        		System.out.println("Server Shutdown.");
        	}else {
        		System.out.println("Unable to start server.");
        	}
        } finally {
            try {
            	//make sure the serverSocket has been closed.
                if (serverSocket != null) {
                	serverSocket.close();
                }
            } catch (IOException ex) {
            }
        }
    }
}
