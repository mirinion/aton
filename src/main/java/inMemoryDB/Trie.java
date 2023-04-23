package inMemoryDB;

import java.util.*;

public class Trie<T> {
	private Node<T> root;

	public void addValue (String prefix, T value) {
		if (root == null) {
			root = new Node<>();
		}
		var parent = root;
		for (int i = 0; i < prefix.length(); i++) {
			char c = prefix.charAt(i);
			var child = parent.getChild(c);
			if (child == null) {
				child = new Node<>();
				parent.addChild(c, child);
			}
			parent = child;
		}
		parent.addValue(value);
	}


	public Set<T> search(String prefix) {
		var node = findNode(prefix);
		if (node != null) {
			return Set.copyOf(node.values);
		}
		return Collections.emptySet();
	}

	public boolean removeValue(String prefix, T value) {
		if (root == null) {
			return false;
		}
		Node<T> node = findNode(prefix);
		if (node == null) {
			return false;
		}
		return node.getValues().remove(value);
	}

	private Node<T> findNode(String prefix) {
		var node = root;
		for (int i = 0; i < prefix.length(); i++) {
			char c = prefix.charAt(i);
			node = node.getChild(c);
			if (node == null) {
				return null;
			}
		}
		return node;
	}


	private static class Node<T> {
		final Set<T> values = new HashSet<>();
		final Map<Character, Node<T>> childrenMap = new HashMap<>();

		public void addValue(T value) {
			values.add(value);
		}

		public Set<T> getValues() {
			return values;
		}

		public void addChild(char c, Node<T> child) {
			childrenMap.put(c, child);
		}

		public Node<T> getChild(char c) {
			return childrenMap.get(c);
		}
	}
}
