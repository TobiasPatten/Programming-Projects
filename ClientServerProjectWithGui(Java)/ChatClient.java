import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient implements Runnable{

	/* The server's socket. */
	public Socket server;
	
	/* The clients username, input when run. */
	public String username;
	
	/* Two threads, one for reading from the user and one for reading from the server. */
	public Thread thread1;
	public Thread thread2;
	
	public BufferedReader userIn;
	public PrintWriter serverOut;
	public BufferedReader serverIn;

	/**
	 * Constructor that sets up the socket and runs the two threads required for input and output.
	 */
	public ChatClient(int socket, String ipAddress) {
		try {
			//Sets up and runs the two required threads as well as setting up the server socket.
			thread1 = new Thread(this);
			thread2 = new Thread(this);
			server = new Socket(ipAddress, socket);
			thread1.start();
			thread2.start();

		} catch (Exception e) {
			System.out.println("Can't find server.");
		}
	}

	/**
	 * The run method that the threads run to manage the output and input.
	 */
	public void run() {
		try {
			userIn = new BufferedReader(new InputStreamReader(System.in));
			serverOut = new PrintWriter(server.getOutputStream(), true);
			serverIn = new BufferedReader(new InputStreamReader(server.getInputStream()));
			String serverResponce;
			if (Thread.currentThread() == thread2) {
				while(true) {
					serverResponce = serverIn.readLine();
					
					//If null is received from the server that means the connection has been closed.
					if (serverResponce != null) {
						System.out.println(serverResponce);
					}else {
						//Breaks out of the infinite loop to then close the client.
						break;
					}
				}
			}else {
				//This is run by the other thread.
				System.out.println("Please input a username.");
				username = userIn.readLine();
				System.out.println("Joined chat!");
				
				//Infinite loop for reading in an input.
				while(true) {
					String userInput = userIn.readLine();
					
					//"null" is used to close the client if wanted.
					if (userInput.equals("null")) {
						break;
					}else {
						serverOut.println(username + ": " + userInput);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				//Closing the readers, socket and then client.
				System.out.println("ClientChat shutdown.");
				server.close();
				serverOut.close();
				serverIn.close();
				userIn.close();
				System.exit(0);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The main manages given arguments and runs the constructor.
	 */
	public static void main(String[] args) {
		//The preset values for the server's port and IP address
		String ipAddress = "localhost";
		int socket = 14001;
		
		//Loops through given arguments looking for a command for a new socket number and new IP address.
		for (int i=0; i<args.length;i++) {
			if (args[i].contentEquals("-cca")){
				i++;
				ipAddress = args[i];
			}
			if (args[i].contentEquals("-ccp")){
				i++;
				socket = Integer.parseInt(args[i]);
			}
		}
		new ChatClient(socket,ipAddress);
	}
}
