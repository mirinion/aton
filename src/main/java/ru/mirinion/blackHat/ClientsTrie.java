package ru.mirinion.blackHat;

public class ClientsTrie {
	private final Node root = new Node(null);

	public void addClient (String phone, String name) {
		Node parent = root;
		for (int i = 0; i < phone.length(); i++) {
			char c = phone.charAt(i);
			Node child = parent.getChild(c);
			if (child == null) {
				child = new Node(null);
				parent.addChild(c, child);
			}
			parent = child;
		}
		parent.clientName = name;
	}

	public String searchClientName(String phone) {
		Node node = findNode(phone);
		if (node != null) {
			return node.clientName;
		} else {
			return null;
		}
	}

	private Node findNode(String prefix) {
		Node node = root;
		for (int i = 0; i < prefix.length(); i++) {
			char c = prefix.charAt(i);
			node = node.getChild(c);
			if (node == null) {
				return null;
			}
		}
		return node;
	}

	private static class Node {
		String clientName;
		Node[] childrenArr = new Node[10];

		public Node(String clientName) {
			this.clientName = clientName;
		}

		void addChild(char c, Node child) {
			childrenArr[c - '0'] = child;
		}

		Node getChild(char c) {
			return childrenArr[c - '0'];
		}
	}
}
