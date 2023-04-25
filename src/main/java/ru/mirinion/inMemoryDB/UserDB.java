package ru.mirinion.inMemoryDB;

import java.util.*;

public class UserDB {
	private final Map<Long, User> accountToUser = new TreeMap<>();
	private final Map<String, Set<User>> nameToUser = new TreeMap<>();
	private final Map<Double, Set<User>> valueToUser = new TreeMap<>();

	public void insertAll(User user) throws AccountAlreadyExistsException {
		if (accountToUser.containsKey(user.getAccount())) {
			throw new AccountAlreadyExistsException("Account "
					+ user.getAccount() + " already exists");
		}
		accountToUser.put(user.getAccount(), user);
		putToValueToUser(user);
		putToNameToUser(user);
	}

	public void insertAll(Collection<User> users)
			throws AccountAlreadyExistsException {
		for (User user : users) {
			this.insertAll(user);
		}
	}

	public void delete(User user) {
		accountToUser.remove(user.getAccount());
		removeFromNameToUser(user);
		removeFromValueToUser(user);
	}

	public void delete(long account) {
		User user = selectWhereAccountIs(account);
		if (user != null) {
			delete(user);
		}
	}

	public User selectWhereAccountIs(long account) {
		return accountToUser.get(account);
	}

	public Set<User> selectWhereValueIs(double value) {
		Set<User> result = valueToUser.get(value);
		return result == null ? Collections.emptySet() : result;
	}

	public Set<User> selectWhereNameIs(String name) {
		return nameToUser.get(name);
	}

	public void updateNameWhereAccountIs(long account, String newName) {
		User user = accountToUser.get(account);
		if (user == null || user.getName().equals(newName)) {
			return;
		}
		removeFromNameToUser(user);
		user.setName(newName);
		putToValueToUser(user);
	}

	public void updateValueWhereAccountIs(long account, double newValue) {
		User user = accountToUser.get(account);
		if (user == null || user.getValue() == newValue) {
			return;
		}
		removeFromValueToUser(user);
		user.setValue(newValue);
		putToValueToUser(user);
	}

	public void updateAccountWhereAccountIs(long oldAccount, long newAccount)
			throws AccountAlreadyExistsException {
		if (oldAccount == newAccount) {
			return;
		}
		if (accountToUser.containsKey(newAccount)) {
			throw new AccountAlreadyExistsException("Account "
					+ newAccount + " is occupied");
		}
		User user = accountToUser.get(oldAccount);
		user.setAccount(newAccount);
		accountToUser.remove(oldAccount);
		accountToUser.put(newAccount, user);
	}

	private void putToValueToUser(User user) {
		valueToUser.putIfAbsent(user.getValue(), new HashSet<>());
		valueToUser.get(user.getValue()).add(user);
	}

	private void putToNameToUser(User user) {
		nameToUser.putIfAbsent(user.getName(), new HashSet<>());
		nameToUser.get(user.getName()).add(user);
	}

	private void removeFromValueToUser(User user) {
		valueToUser.get(user.getValue()).remove(user);
		if (valueToUser.get(user.getValue()).isEmpty()) {
			valueToUser.remove(user.getValue());
		}
	}

	private void removeFromNameToUser(User user) {
		nameToUser.get(user.getName()).remove(user);
		if (nameToUser.get(user.getName()).isEmpty()) {
			nameToUser.remove(user.getName());
		}
	}
}
