package src.network;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Interface defining client-side network output operations.
 * Provides methods for sending messages to clients and reading their input.
 */
public interface IClientOutput {
	/**
	 * Sends a message to the connected client.
	 * 
	 * @param message The string message to send
	 * @throws IOException if writing to network stream fails
	 */
	void writeMessage(String message) throws IOException;

	/**
	 * Gets the input reader for receiving messages from the client.
	 * 
	 * @return BufferedReader configured for client input stream
	 * @throws IOException if creating reader fails
	 */
	BufferedReader getInput() throws IOException;
}