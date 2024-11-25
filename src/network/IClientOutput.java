package src.network;

import java.io.BufferedReader;
import java.io.IOException;

public interface IClientOutput {
	void writeMessage(String message) throws IOException;

	BufferedReader getInput() throws IOException;
}
