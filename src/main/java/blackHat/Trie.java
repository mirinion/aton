package blackHat;

import java.util.*;

public class Trie<T> {
	private Node<T> root;

	public void addValue (String prefix, T value) {
		if (root == null) {
			root = new Node<>(null);
		}
		Node<T> parent = root;
		for (int i = 0; i < prefix.length(); i++) {
			char c = prefix.charAt(i);
			Node<T> child = parent.getChild(c);
			if (child == null) {
				child = new Node<>(null);
				parent.addChild(c, child);
			}
			parent = child;
		}
		parent.setValue(value);
	}

	public T search (String prefix) {
		Node<T> node = findNode(prefix);
		if (node != null) {
			return node.value;
		} else {
			return null;
		}
	}

	private Node<T> findNode(String prefix) {
		Node<T> node = root;
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
		T value;
		final Map<Character, Node<T>> childrenMap = new HashMap<>();

		public Node(T value) {
			this.value = value;
		}
		void setValue(T value) {
			this.value = value;
		}

		void addChild(char c, Node<T> child) {
			childrenMap.put(c, child);
		}

		Node<T> getChild(char c) {
			return childrenMap.get(c);
		}
	}
}
