package ru.mirinion.threads;

import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ScenarioReader extends Thread {
	private final Path path;
	private final ConcurrentLinkedQueue<Phrase> phraseQueue;

	public ScenarioReader(Path path) {
		this.path = path;
		phraseQueue = new ConcurrentLinkedQueue<>();
	}

	@Override
	public void run() {
		try (Scanner in = new Scanner(path)) {
			while (in.hasNext()) {
				String[] lineParts = in.nextLine().split(":");
				phraseQueue.add(new Phrase(lineParts[0], lineParts[1].trim()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ConcurrentLinkedQueue<Phrase> getPhraseQueue() {
		return phraseQueue;
	}
}
