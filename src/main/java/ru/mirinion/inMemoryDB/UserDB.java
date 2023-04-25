package ru.mirinion.inMemoryDB;

import java.util.*;

public class UserDB {
	private final Map<Long, User> accountToUserMap = new TreeMap<>();

	private final Map<String, Set<User>> nameToUserMap = new TreeMap<>();

	private final Map<Double, Set<User>> valueToUserMap = new TreeMap<>();

	public void insert(User user) throws AccountAlreadyExistsException {
		if (accountToUserMap.containsKey(user.getAccount())) {
			throw new AccountAlreadyExistsException(
					"Account " + user.getAccount() + " already exists");
		}
		accountToUserMap.put(user.getAccount(), user);
		putToValueToUser(user);
		putToNameToUser(user);
	}

	public void insertAll(Collection<User> users)
			throws AccountAlreadyExistsException {
		for (User user : users) {
			this.insert(user);
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
		Set<User> result = nameToUserMap.get(name);
		return result == null ? Collections.emptySet() : result;
	}

	public void updateNameWhereAccountIs(long account, String newName)
			throws AccountAlreadyExistsException {
		User user = accountToUserMap.get(account);
		if (user == null) {
			throw new AccountAlreadyExistsException(
					"Account " + account + " not exists");
		}
		if (!user.getName().equals(newName)) {
			removeFromNameToUser(user);
			user.setName(newName);
			putToNameToUser(user);
		}
	}

	public void updateValueWhereAccountIs(long account, double newValue)
			throws AccountAlreadyExistsException {
		User user = accountToUserMap.get(account);
		if (user == null) {
			throw new AccountAlreadyExistsException("Account " + account + " not exists");
		}
		if (user.getValue() != newValue) {
			removeFromValueToUser(user);
			user.setValue(newValue);
			putToValueToUser(user);
		}
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
		if (valueToUserMap.containsKey(user.getValue())) {
			valueToUserMap.get(user.getValue()).add(user);
		} else {
			TreeSet<User> newSet =
					new TreeSet<>(Comparator.comparingLong(User::getAccount));
			newSet.add(user);
			valueToUserMap.put(user.getValue(), newSet);
		}
	}

	private void putToNameToUser(User user) {
		if (nameToUserMap.containsKey(user.getName())) {
			nameToUserMap.get(user.getName()).add(user);
		} else {
			TreeSet<User> newSet =
					new TreeSet<>(Comparator.comparingLong(User::getAccount));
			newSet.add(user);
			nameToUserMap.put(user.getName(), newSet);
		}
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
