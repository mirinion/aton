package blackHat;

public class Order {
	ClientDetails clientDetails;

	public Order(ClientDetails clientDetails) {
		this.clientDetails = clientDetails;
	}

	public ClientDetails getClientDetails() {
		return clientDetails;
	}
}
