package ru.mirinion.blackHat;

import java.util.List;

public class ClientsDB {
	private final ClientsTrie clientDetailsTrie = new ClientsTrie();

	public ClientsDB (List<Order> orders) {
		for (Order order : orders) {
			this.addClient(order.clientDetails());
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
		return clientDetailsTrie.searchClientName(
				formatPhoneNumber(phoneNumber)
		);
	}
}
