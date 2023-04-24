package blackHat;

public class ClientTrie<T> {
	private Node root;

	public void addClient (String phone, String name) {
		if (root == null) {
			root = new Node(null);
		}
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
		parent.name = name;
	}

	public String searchClient (String phone) {
		Node node = findNode(phone);
		if (node != null) {
			return node.name;
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
		String name;
		Node[] childrenArr = new Node[10];

		public Node(String  name) {
			this.name = name;
		}
		void addChild(char c, Node child) {
			childrenArr[c - '0'] = child;
		}

		Node getChild(char c) {
			return childrenArr[c - '0'];
		}
	}
}
