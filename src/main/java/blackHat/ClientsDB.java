package blackHat;

import java.util.List;

public class ClientsDB {
	private final Trie<String> clientDetailsTrie = new Trie<>();

	public ClientsDB (List<Order> orders) {
		for (Order order : orders) {
			this.addClient(order.clientDetails);
		}
	}

	public void addClient(ClientDetails clientDetails) {
		clientDetailsTrie.addValue(clientDetails.phoneNum(), clientDetails.fullName());
	}

	public String searchNameByPhoneNumber(String phoneNumber) {
		return clientDetailsTrie.search(phoneNumber);
	}
}
