
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import controller.Controller;

public class Main {

	public static void main(String[] args) {
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
		} catch (SecurityException | IOException e) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.OFF);
		}

		new Controller();
	}

}