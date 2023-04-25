package ru.mirinion.threads;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Actor extends Thread {
	private final ScenarioReader scenarioReader;
	private final String name;

	public Actor(String name, ScenarioReader scenarioReader) {
		this.name = name;
		this.scenarioReader = scenarioReader;
	}

	@Override
	public void run() {
		ConcurrentLinkedQueue<Phrase> phraseQueue = scenarioReader.getPhraseQueue();
		while (scenarioReader.isAlive() || !phraseQueue.isEmpty()) {
			Phrase nextPhrase = phraseQueue.peek();
			if (nextPhrase != null
					&& nextPhrase.actorName().equals(name)) {
				System.out.println(nextPhrase);
				phraseQueue.poll();
			}
		}
	}
}
