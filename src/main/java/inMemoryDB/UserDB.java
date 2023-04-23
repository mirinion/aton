package inMemoryDB;

import java.util.*;

public class UserDB {
	private final Map<Long, User> accountToUser = new HashMap<>();
	private final Trie<User> nameToUserTrie = new Trie<>();
	private final Map<Double, Set<User>> valueToUser = new HashMap<>();

	public void insert(User user) throws AccountAlreadyExistsException {
		if (accountToUser.containsKey(user.getAccount())) {
			throw new AccountAlreadyExistsException("Account "
					+ user.getAccount() + " already exists");
		}
		accountToUser.put(user.getAccount(), user);
		nameToUserTrie.addValue(user.getName(), user);
		insertIntoValueToUser(user);
	}

	public void insert(Collection<User> users)
			throws AccountAlreadyExistsException {
		for (User user : users) {
			this.insert(user);
		}
	}

	private void insertIntoValueToUser(User user) {
		valueToUser.putIfAbsent(user.getValue(), new HashSet<>());
		valueToUser.get(user.getValue()).add(user);
	}

	public void delete(User user) {
		accountToUser.remove(user.getAccount());
		nameToUserTrie.removeValue(user.getName(), user);
		valueToUser.get(user.getValue()).remove(user);
	}

	public User selectWhereAccountIs(long account) {
		return accountToUser.get(account);
	}

	public Set<User> selectWhereValueIs(double value) {
		Set<User> result = valueToUser.get(value);
		return result == null ? Collections.emptySet() : result;
	}

	public Set<User> selectWhereNameIs(String name) {
		return nameToUserTrie.search(name);
	}

	public void updateNameWhereAccountIs(long account, String newName) {
		User user = accountToUser.get(account);
		if (user == null || user.getName().equals(newName)) {
			return;
		}
		nameToUserTrie.removeValue(user.getName(), user);
		user.setName(newName);
		nameToUserTrie.addValue(newName, user);
	}

	public void updateValueWhereAccountIs(long account, double newValue) {
		User user = accountToUser.get(account);
		if (user == null || user.getValue() == newValue) {
			return;
		}
		double oldValue = user.getValue();
		Set<User> oldValueUsersSet = selectWhereValueIs(oldValue);
		oldValueUsersSet.remove(user);
		if (oldValueUsersSet.isEmpty()) {
			valueToUser.remove(oldValue);
		}
		user.setValue(newValue);
		insertIntoValueToUser(user);
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

}
