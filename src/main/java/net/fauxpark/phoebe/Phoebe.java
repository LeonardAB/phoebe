package net.fauxpark.phoebe;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fauxpark.phoebe.task.JishoTask;
import net.fauxpark.phoebe.task.KanjiTask;

public class Phoebe {
	private static final Logger log = LogManager.getLogger(Phoebe.class);

	public static void main(String[] args) {
		try {
			Config.load();
		} catch(IOException e) {
			log.error("Could not load default configuration.", e);

			System.exit(1);
		}

		log.info("Running Kanji task");

		KanjiTask.run();

		log.info("Running Jisho Task");

		JishoTask.run();
	}
}
