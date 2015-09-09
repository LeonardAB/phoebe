package net.fauxpark.phoebe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A simple configuration class.
 *
 * @author fauxpark
 */
public class Config {
	/**
	 * The location of the configuration file.
	 */
	private static final String PROPERTIES_LOCATION = "phoebe.properties";

	/**
	 * The default configuration.
	 */
	private static Properties defaultProperties = new Properties();

	/**
	 * The user-supplied configuration, if it exists.
	 */
	private static Properties properties = new Properties();

	private static final Logger log = LogManager.getLogger(Config.class);

	/**
	 * Load configuration.
	 *
	 * @throws IOException if the default configuration could not be read.
	 */
	public static void load() throws IOException {
		InputStream is = Config.class.getClassLoader().getResourceAsStream(PROPERTIES_LOCATION);
		defaultProperties.load(is);
		File file = new File(PROPERTIES_LOCATION);
		String path = file.getAbsolutePath();

		if(file.exists()) {
			try {
				InputStream fis = new FileInputStream(file);
				properties.load(fis);

				if(properties.size() > 0) {
					log.info("Loaded configuration from " + path);
				} else {
					log.warn("No properties defined in " + path + ". Using default values instead.");
				}
			} catch(Exception e) {
				log.error("Error while reading properties file.", e);
			}
		} else {
			log.info("Could not find properties file at " + path + ". Using default values instead.");
		}
	}

	/**
	 * Retrieve a string from the properties.
	 *
	 * @param property The property key to get.
	 * @return The value of the property, or null.
	 */
	public static String getString(String property) {
		return properties.getProperty(property, defaultProperties.getProperty(property, null));
	}

	/**
	 * Retrieve an integer from the properties.
	 *
	 * @param property The property key to get.
	 * @return The value of the property, or null.
	 */
	public static Integer getInt(String property) {
		try {
			return Integer.parseInt(getString(property));
		} catch(NumberFormatException e) {
			return null;
		}
	}
}
