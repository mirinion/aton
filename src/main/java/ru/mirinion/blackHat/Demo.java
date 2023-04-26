package ru.mirinion.blackHat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Demo {
	private static final String ORDERS_FILE = "src/main/resources/orders.txt";
	public static void main(String[] args) {
		//Список заказов (пример рандомных исходных данных)
		List<Order> orders = readOrders();
		ClientsDB clientsDB = new ClientsDB(orders);

		//Пример поиска
		System.out.println(clientsDB.searchNameByPhoneNumber("+7 (904) 886-10-63"));
		System.out.println(clientsDB.searchNameByPhoneNumber("+7 (986) 088-30-22"));

		//Номер, которого нет в базе
		System.out.println(clientsDB.searchNameByPhoneNumber("+1 (111) 111-11-11"));
	}

	private static List<Order> readOrders() {
		List<Order> orders = new ArrayList<>();
		try (BufferedReader in = new BufferedReader(
				new FileReader(Demo.ORDERS_FILE))) {
			String line;
			while ((line = in.readLine()) != null) {
				String[] parts = line.split(";");
				orders.add(new Order(
						new ClientDetails(parts[0], parts[1])
				));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

}
