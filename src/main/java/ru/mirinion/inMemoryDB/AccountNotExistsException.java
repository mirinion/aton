package ru.mirinion.inMemoryDB;

public class AccountNotExistsException extends Exception {
	public AccountNotExistsException(String message) {
		super(message);
	}
}
