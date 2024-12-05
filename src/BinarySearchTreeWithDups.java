import java.util.*;

public class BinarySearchTreeWithDups<T extends Comparable<? super T>> extends BinarySearchTree<T> {

	public BinarySearchTreeWithDups() {
		super();
	}

	public BinarySearchTreeWithDups(T rootEntry) {
		super(rootEntry);
	}

	@Override
	public boolean add(T newEntry) {
		if (isEmpty()) {
			return super.add(newEntry);
		} else {
			return addEntryHelperNonRecursive(newEntry);
		}
	}

	private boolean addEntryHelperNonRecursive(T newEntry) {
		BinaryNode<T> nodeToAdd = new BinaryNode<>(newEntry);
		BinaryNode<T> currentNode = root;

		while (true) {
			int comparison = newEntry.compareTo(currentNode.getData());
			if (comparison <= 0) {
				if (currentNode.hasLeftChild()){
					currentNode = currentNode.getLeftChild();
				} else {
					currentNode.setLeftChild(nodeToAdd);
					return true;
				}
			} else {
				if (currentNode.hasRightChild()){
					currentNode = currentNode.getRightChild();
				} else {
					currentNode.setRightChild(nodeToAdd);
					return true;
				}
			}
		}
	}

	public int countIterative(T target) {

		int count = 0;
		BinaryNode<T> currentNode = root;

		while (true) {
			int comparison = target.compareTo(currentNode.getData());
			if (comparison == 0) {
				count++;
				currentNode = currentNode.getLeftChild();
			} else if (comparison < 0) {
				currentNode = currentNode.getLeftChild();
			} else {
				currentNode = currentNode.getRightChild();
			}

			if (currentNode == null) {
				return count;
			}
		}
	}

	public int countGreaterRecursive(T target) {
		return countGreaterRecursiveHelper(target, root);
	}

	private int countGreaterRecursiveHelper(T target, BinaryNode<T> currentNode) {
		if (currentNode == null) {
			return 0;
		}

		int comparison = target.compareTo(currentNode.getData());
		if (comparison >= 0) {
			return countGreaterRecursiveHelper(target, currentNode.getRightChild());
		}

		return 1 + countGreaterRecursiveHelper(target, currentNode.getLeftChild())
					+ countGreaterRecursiveHelper(target, currentNode.getRightChild());
	}

	public int countGreaterIterative(T target) {
		int count = 0;
		BinaryNode<T> currentNode = root;
		Stack<BinaryNode<T>> nodeStack = new Stack<BinaryNode<T>>();
		nodeStack.push(currentNode);

		while (!nodeStack.isEmpty()) {
			currentNode = nodeStack.pop();
			int comparison = target.compareTo(currentNode.getData());
			if (comparison >= 0 && currentNode.hasRightChild()) {
				nodeStack.push(currentNode.getRightChild());
			} else if (comparison < 0) {
				count++;
				if (currentNode.hasLeftChild()) {
					nodeStack.push(currentNode.getLeftChild());
				}

				if (currentNode.hasRightChild()) {
					nodeStack.push(currentNode.getRightChild());
				}
			}
		}
		
		return count;
	}

	public int countUniqueValues() {
		// borrowing iterativeInorderTraverse() from BinaryTree
		// this will traverse the tree in ascending order, which lumps the duplicates together
		Stack<BinaryNode<T>> nodeStack = new Stack<>();
		BinaryNode<T> currentNode = root;
		int count = 0;
		T currentData = null;
		while (!nodeStack.isEmpty() || (currentNode != null)) {
			while (currentNode != null) {
				nodeStack.push(currentNode);
				currentNode = currentNode.getLeftChild();
			}

			if (!nodeStack.isEmpty()) {
				BinaryNode<T> nextNode = nodeStack.pop();
				// check if the new data is unique
				if (!nextNode.getData().equals(currentData)) {
					count++;
					currentData = nextNode.getData();
				}
				currentNode = nextNode.getRightChild();
			}
		}

		return count;
	}
}