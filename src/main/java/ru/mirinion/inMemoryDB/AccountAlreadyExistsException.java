package ru.mirinion.inMemoryDB;

public class AccountAlreadyExistsException extends Exception{
	public AccountAlreadyExistsException(String message) {
		super(message);
	}
}
