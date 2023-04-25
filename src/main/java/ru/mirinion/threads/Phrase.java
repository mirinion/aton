package ru.mirinion.threads;

public record Phrase(String actorName, String text) {
	@Override
	public String toString() {
		return String.format("%s: %s", actorName, text);
	}
}
