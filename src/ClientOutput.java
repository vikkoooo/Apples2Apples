package src;

import java.io.IOException;

public interface ClientOutput {
	void writeMessage(String message) throws IOException;
}