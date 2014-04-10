package btree;

import java.util.ArrayList;

public class BTreeNode {

	public static final int T = 2;

	private ArrayList<Integer> values = new ArrayList<Integer>();
	private ArrayList<BTreeNode> childNodes = new ArrayList<BTreeNode>();
	private BTreeNode parent;
	protected BTree wrapper;

	/**
	 * searches the tree for the given element.
	 * @param x the element to search for.
	 * @return true if the tree contains the element, false if not.
	 */
	public boolean search(int x) {
		if (values.contains(x)) return true;
		if (childNodes.isEmpty()) return false;
		for (int i = 0; i < values.size(); i++) {
			if (x < values.get(i)) return childNodes.get(i).search(x);
		}
		return childNodes.get(childNodes.size() - 1).search(x);
	}

	/**
	 * inserts the given element into the tree.
	 * @param x the element to be inserted into the tree.
	 * @return 0 if direct insertion was possible, 1 if a node was splitted, 2 if a new level was added.
	 */
	public int insert(int x) {
		if (values.contains(x)) throw new ArithmeticException();
		// if current Node is leaf, insert element.
		if (this.isLeaf()) {
			int i = 0;
			while (i < values.size() && x > values.get(i)) i++;

			values.add(i, x);
			
			// if the current node is the root node a overflow might happen.
			if (this.isRoot() && this.isFull()) {
				BTreeNode root = new BTreeNode();
				root.childNodes.add(this);
				root.split(0);
				wrapper.setRoot(root);
				return 2;
			}
			
			return 0;

		} else {	// if current element is inner Node, insert in child
			int i = 0;
			while(i < values.size() && x > values.get(i)) i++;
			
			if(childNodes.get(i).isFull()){	// if child node is full, split child
				int r = split(i);
				insert(x);
				return r;
			} else {
				return childNodes.get(i).insert(x);
			}
		}
	}

	/**
	 * Splits the node at position pos of the node in to equally sized child nodes. The median element of the old child node gets inserted into the node.
	 * @param pos the position of the node to be splitted
	 * @return 1 if splitting was possible without adding a new level to the tree, if not 2.
	 */
	private int split(int pos) {
		BTreeNode left = new BTreeNode();
		BTreeNode right = new BTreeNode();
		BTreeNode child = childNodes.get(pos);
		int median = child.values.get(T - 1);

		left.parent = this;
		right.parent = this;

		// Copy all elements left to the median to new left tree
		for (int i = 0; i < T - 1; i++) {
			left.values.add(child.values.get(i));
			if(!child.isLeaf()){
				left.childNodes.add(child.childNodes.get(i));
			}
		}
		if(!child.isLeaf()){
			left.childNodes.add(child.childNodes.get(T - 1));
		}
		
		// Copy all elements right to the median to new right tree
		for (int i = T; i < child.values.size(); i++) {
			right.values.add(child.values.get(i));
			if(!child.isLeaf()){
				right.childNodes.add(child.childNodes.get(i));
			}
		}
		if(!child.isLeaf()){
			right.childNodes.add(child.childNodes.get(child.childNodes.size() - 1));
		}

		childNodes.remove(child);
		
		// insert median in this node
		int i = 0;
		while (i < values.size() && median > values.get(i)) i++;

		// insert new child nodes, remove old child node
		values.add(i, median);
		childNodes.add(i, left);
		childNodes.add(i + 1, right);
		
		// if the current node is the root node a overflow might happen.
		if (this.isFull() && isRoot()) {
			BTreeNode root = new BTreeNode();
			root.childNodes.add(this);
			root.split(0);
			wrapper.setRoot(root);
			return 2;
		}
		return 1;
	}

	/**
	 * Checks if the node is full. A node is full, if it contains (2 * T - 1) or more values.
	 * @return
	 */
	public boolean isFull() {
		return values.size() == 2 * T - 1;
	}

	/**
	 * Checks if the node has a parant node.
	 * @return
	 */
	public boolean isRoot() {
		return parent == null;
	}

	/**
	 * Checks if the node has Child nodes.
	 * @return
	 */
	public boolean isLeaf() {
		return childNodes.size() == 0;
	}

	@Override
	public String toString() {
		String ret = "";
		
		ret += values.toString();
		if(isLeaf()) ret += "*";
		for (int i = 0; i < childNodes.size() && !isLeaf(); i++) {	
			ret += childNodes.get(i).toString();
		}

		return ret;
	}

}