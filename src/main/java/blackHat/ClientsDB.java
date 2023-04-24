package blackHat;

import java.util.List;

public class ClientsDB {
	private final ClientTrie<String> clientDetailsTrie = new ClientTrie<>();

	public ClientsDB (List<Order> orders) {
		for (Order order : orders) {
			this.addClient(order.clientDetails);
		}
	}

	/**
	 * Возвращает номер телефона в виде строки только из цифр
	 */
	private String formatPhoneNumber(String phoneNumber) {
		return phoneNumber.replaceAll("\\D", "");
	}

	public void addClient(ClientDetails clientDetails) {
		clientDetailsTrie.addClient(
				formatPhoneNumber(clientDetails.phoneNum()),
				clientDetails.fullName()
		);
	}

	public String searchNameByPhoneNumber(String phoneNumber) {
		return clientDetailsTrie.searchClient(
				formatPhoneNumber(phoneNumber)
		);
	}
}
