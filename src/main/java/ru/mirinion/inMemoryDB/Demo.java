package ru.mirinion.inMemoryDB;

import java.util.List;

public class Demo {
	public static void main(String[] args) {
		UserDB userDB = new UserDB();

		User u1 = new User(1, "Ivanov Ivan", 10_000);
		User u2 = new User(2, "Ivanov Stepan", 20_000);
		User u3 = new User(3, "Petrova Mariya", 20_000);

		try {
			userDB.insertAll(List.of(u1, u2, u3));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		System.out.println(userDB.selectWhereValueIs(20_000));

		System.out.println();

		try {
			userDB.updateNameWhereAccountIs(1, "Ivanov Igor");
			System.out.println(userDB.selectWhereNameIs("Ivanov Igor"));
		} catch (AccountAlreadyExistsException e) {
			System.err.println(e.getMessage());
		}

		System.out.println();

		try {
			userDB.updateValueWhereAccountIs(3, 40_000.50);
			System.out.println(userDB.selectWhereValueIs(40_000.50));
		} catch (AccountAlreadyExistsException e) {
			System.err.println(e.getMessage());
		}

		System.out.println();

		try {
			userDB.insert(new User(3, "Denisova Olga", 30_000));
		} catch (Exception e) {
			//выбросит исключение тк номер акк 3 уже занят
			System.err.println(e.getMessage());
		}

		try {
			userDB.updateAccountWhereAccountIs(2, 5);
			System.out.println(userDB.selectWhereAccountIs(2)); //null, тк account изменился с 2 на 5
			System.out.println();
			System.out.println(userDB.selectWhereAccountIs(5));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		System.out.println();

		try {
			userDB.updateAccountWhereAccountIs(1, 5);
		} catch (Exception e) {
			//выбросит исключение тк номер акк 5 уже занят
			System.err.println(e.getMessage());
		}

		userDB.delete(3);
		System.out.println(userDB.selectWhereAccountIs(3)); //null, тк мы удалили
	}
}
