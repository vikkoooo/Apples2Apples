package src.network;

import java.io.*;
import java.net.*;

/**
 * Manages network communication with a connected player.
 * Wraps a Socket connection with convenient input/output methods.
 */
public class PlayerConnection implements IClientOutput {
	private Socket socket; // Socket connection to the client
	private BufferedReader input; // Buffered reader for receiving client messages
	private DataOutputStream output; // utput stream for sending messages to client

	/**
	 * Creates a new connection handler for a player socket.
	 * Initializes input and output streams for communication.
	 * 
	 * @param socket The established socket connection to the client
	 * @throws IOException if stream creation fails
	 */
	public PlayerConnection(Socket socket) throws IOException {
		this.socket = socket;
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.output = new DataOutputStream(socket.getOutputStream());
	}

	/**
	 * Gets the input reader for receiving client messages.
	 * 
	 * @return BufferedReader configured for client input
	 */
	public BufferedReader getInput() {
		return input;
	}

	/**
	 * Sends a message to the connected client.
	 * Automatically appends newline to messages.
	 * 
	 * @param message The string message to send
	 * @throws IOException if writing to stream fails
	 */
	@Override
	public void writeMessage(String message) throws IOException {
		output.writeBytes(message + "\n");
	}

	/**
	 * Closes all network streams and the socket connection.
	 * Should be called when disconnecting the player.
	 * 
	 * @throws IOException if closing streams fails
	 */
	public void close() throws IOException {
		input.close();
		output.close();
		socket.close();
	}
}