package ru.mirinion.threads;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Scenario {
	private final Queue<Phrase> phrases = new ArrayDeque<>();
	private final Set<String> actorNamesSet = new HashSet<>();
	private final Path path;

	public Scenario(Path path) {
		this.path = path;
		this.parse();
	}

	public void parse() {
		try (Scanner in = new Scanner(path)) {
			while (in.hasNext()) {
				String[] lineParts = in.nextLine().split(":");
				phrases.offer(new Phrase(lineParts[0], lineParts[1].trim()));
				actorNamesSet.add(lineParts[0]);
			}
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	public synchronized void read() {
		try {
			if (!phrases.isEmpty()) {
				String nextActorName = phrases.peek().actorName;
				if (nextActorName.equals(Thread.currentThread().getName())) {
					((Actor) Thread.currentThread()).say(phrases.poll().text);
					notifyAll();
				} else {
					wait();
				}
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public boolean isEmpty() {
		return phrases.isEmpty();
	}

	public Set<String> getActorNamesSet() {
		return actorNamesSet;
	}

	public record Phrase (String actorName, String text) {
	}

}
