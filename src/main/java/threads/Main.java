package threads;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		try {
			Scenario scenario = new Scenario(Path.of("src/main/resources/scenario"));
			Set<String> actorNames = scenario.getActorNamesSet();
			List<Actor> actors = actorNames.stream()
					.map(name -> new Actor(name, scenario)).toList();
			for (Actor actor : actors) {
				actor.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
