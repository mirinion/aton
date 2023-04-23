package threads;

public class Actor extends Thread {
	private final Scenario scenario;

	public Actor(String name, Scenario scenario) {
		this.scenario = scenario;
		this.setName(name);
	}

	@Override
	public void run() {
		while (!scenario.isEmpty()) {
			scenario.say();
		}
	}
}
