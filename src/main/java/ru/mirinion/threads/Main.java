package ru.mirinion.threads;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
	private final static Path SCENARIO_PATH = Path.of("src/main/resources/scenario.txt");
	public static void main(String[] args) {
		ScenarioReader scenarioReader = new ScenarioReader(SCENARIO_PATH);

		String[] actorNames = {"Chandler", "Joey", "Monica",
				"Phoebe", "Rachel", "Ross"};
		List<Actor> actors = Arrays.stream(actorNames)
				.map(name -> new Actor(name, scenarioReader)).toList();

		scenarioReader.start();

		for (Actor actor : actors) {
			actor.start();
		}
	}
}
