package ru.mirinion.inMemoryDB;

import java.util.*;

public class UserDB {
	private final Map<Long, User> accountToUserMap = new TreeMap<>();
	private final Map<String, Set<User>> nameToUserMap = new TreeMap<>();
	private final Map<Double, Set<User>> valueToUserMap = new TreeMap<>();

	public void insertAll(User user) throws AccountAlreadyExistsException {
		if (accountToUserMap.containsKey(user.getAccount())) {
			throw new AccountAlreadyExistsException("Account "
					+ user.getAccount() + " already exists");
		}
		accountToUserMap.put(user.getAccount(), user);
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
		accountToUserMap.remove(user.getAccount());
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
		return accountToUserMap.get(account);
	}

	public Set<User> selectWhereValueIs(double value) {
		Set<User> result = valueToUserMap.get(value);
		return result == null ? Collections.emptySet() : result;
	}

	public Set<User> selectWhereNameIs(String name) {
		return nameToUserMap.get(name);
	}

	public void updateNameWhereAccountIs(long account, String newName) {
		User user = accountToUserMap.get(account);
		if (user == null || user.getName().equals(newName)) {
			return;
		}
		removeFromNameToUser(user);
		user.setName(newName);
		putToValueToUser(user);
	}

	public void updateValueWhereAccountIs(long account, double newValue) {
		User user = accountToUserMap.get(account);
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
		if (accountToUserMap.containsKey(newAccount)) {
			throw new AccountAlreadyExistsException("Account "
					+ newAccount + " is occupied");
		}
		User user = accountToUserMap.get(oldAccount);
		user.setAccount(newAccount);
		accountToUserMap.remove(oldAccount);
		accountToUserMap.put(newAccount, user);
	}

	private void putToValueToUser(User user) {
		valueToUserMap.putIfAbsent(user.getValue(), new HashSet<>());
		valueToUserMap.get(user.getValue()).add(user);
	}

	private void putToNameToUser(User user) {
		nameToUserMap.putIfAbsent(user.getName(), new HashSet<>());
		nameToUserMap.get(user.getName()).add(user);
	}

	private void removeFromValueToUser(User user) {
		valueToUserMap.get(user.getValue()).remove(user);
		if (valueToUserMap.get(user.getValue()).isEmpty()) {
			valueToUserMap.remove(user.getValue());
		}
	}

	private void removeFromNameToUser(User user) {
		nameToUserMap.get(user.getName()).remove(user);
		if (nameToUserMap.get(user.getName()).isEmpty()) {
			nameToUserMap.remove(user.getName());
		}
	}
}
