package TreePackage;

import java.util.Iterator;
import java.util.NoSuchElementException;
import QueuePackage.*;
import StackPackage.*;

public class BinaryTree<T> implements BinaryTreeInterface<T> {

    protected BinaryNode<T> root;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(T rootData) {
        root = new BinaryNode<>(rootData);
    }

    public BinaryTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
        initializeTree(rootData, leftTree, rightTree);
    }

    public BinaryNode<T> getRoot() {
        return root;
    }

    @Override
    public T getRootData() {
        if (!isEmpty()) {
            return root.getData();
        }
        return null;
    }

    @Override
    public int getHeight() {
        return root.getHeight();
    }

    @Override
    public int getNumberOfNodes() {
        return root.getNumberOfNodes();
    }

    @Override
    public Iterator<T> getPreorderIterator() {
        return null;
    }

    @Override
    public Iterator<T> getPostorderIterator() {
        return new PostorderIterator();
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public Iterator<T> getInorderIterator() {
        return new InOrderIterator();
    }

    @Override
    public Iterator<T> getLevelOrderIterator() {
        return new LevelOrderIterator();
    }

    @Override
    public void setTree(T rootData) {
        setTree(rootData, null, null);
    }

    @Override
    public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree) {
        initializeTree(rootData, (BinaryTree<T>) leftTree, (BinaryTree<T>) rightTree);
    }

    private void initializeTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
        root = new BinaryNode<>(rootData);

        if ((leftTree != null) && !leftTree.isEmpty()) {
            root.setLeftChild(leftTree.root);
        }
        if ((rightTree != null) && !rightTree.isEmpty()) {
            if (rightTree != leftTree) {
                root.setRightChild(rightTree.root);
            } else {
                root.setRightChild(rightTree.root.copy());
            }
        }
        if ((leftTree != null) && (leftTree != this)) {
            leftTree.clear();
        }
        if ((rightTree != null) && (rightTree != this)) {
            rightTree.clear();
        }
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    // traversal that doesn't use an iterator (for demonstration purposes only)
    public void iterativeInorderTraverse() {
        StackInterface<BinaryNode<T>> nodeStack = new ArrayStack<>();
        BinaryNode<T> currentNode = root;

        while (!nodeStack.isEmpty() || (currentNode != null)) {
            while (currentNode != null) {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            }

            if (!nodeStack.isEmpty()) {
                BinaryNode<T> nextNode = nodeStack.pop();

                System.out.println(nextNode.getData());
                currentNode = nextNode.getRightChild();
            }
        }
    }

    private class PostorderIterator implements Iterator<T> {

        private StackInterface<BinaryNode<T>> nodeStack;
        private BinaryNode<T> currentNode;

        public PostorderIterator() {
            nodeStack = new ArrayStack<>();
            currentNode = root;
        }

        @Override
        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        }

        @Override
        public T next() {
            BinaryNode<T> nextNode = null;
            while (currentNode != null) {
                nodeStack.push(currentNode);
                if (currentNode.hasLeftChild()) {
                    currentNode = currentNode.getLeftChild();
                } else {
                    currentNode = currentNode.getRightChild();
                }
            }
            if (!nodeStack.isEmpty()) {
                nextNode = nodeStack.pop();
                if (nodeStack.isEmpty()) {
                    return nextNode.getData();
                }
                BinaryNode<T> parent = nodeStack.peek();
                if (nextNode == parent.getLeftChild()) {
                    currentNode = parent.getRightChild();
                } else {
                    currentNode = null;
                }
            } else {
                throw new NoSuchElementException();
            }
            return nextNode.getData();
        }
    }

    private class LevelOrderIterator implements Iterator<T> {

        private LinkedQueue<BinaryNode<T>> nodeQueue;
        private BinaryNode<T> currentNode;
        private boolean initialized;

        public LevelOrderIterator() {
            nodeQueue = new LinkedQueue<>();
            currentNode = root;
        }

        @Override
        public boolean hasNext() {
            return !nodeQueue.isEmpty() || !initialized;
        }

        @Override
        public T next() {
            BinaryNode<T> nextNode = null;

            if (!initialized) {
                nodeQueue.enqueue(currentNode);
                initialized = true;
            }

            if (!nodeQueue.isEmpty()) {
                nextNode = nodeQueue.dequeue();
                if (nextNode.hasLeftChild()) {
                    nodeQueue.enqueue(nextNode.getLeftChild());
                }
                if (nextNode.hasRightChild()) {
                    nodeQueue.enqueue(nextNode.getRightChild());
                }
            } else {
                throw new NoSuchElementException();
            }
            return nextNode.getData();
        }
    }

    private class InOrderIterator implements Iterator<T> {

        private StackInterface<BinaryNode<T>> nodeStack;
        private BinaryNode<T> currentNode;

        public InOrderIterator() {
            nodeStack = new ArrayStack<>();
            currentNode = root;
        }

        @Override
        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        }

        @Override
        public T next() {
            BinaryNode<T> nextNode = null;
            // find leftmost node with no left child
            while (currentNode != null) {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            }

            if (!nodeStack.isEmpty()) {
                nextNode = nodeStack.pop();
                currentNode = nextNode.getRightChild();
            } else {
                throw new NoSuchElementException();
            }
            return nextNode.getData();
        }
    }
}
