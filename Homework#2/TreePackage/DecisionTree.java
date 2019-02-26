package TreePackage;

public class DecisionTree<String> extends BinaryTree<String> implements DecisionTreeInterface<String> {

    BinaryNode<String> currentNode;

    public DecisionTree(String data) {
        this(data, null, null);
    }

    public DecisionTree(String data, DecisionTree<String> left, DecisionTree<String> right) {
        setTree(data, left, right);
    }
    
    @Override
    public String getCurrentData() {
        if (currentNode != null) {
            return currentNode.getData();
        }
        return null;
    }

    @Override
    public void setCurrentData(String newData) {
        currentNode.setData(newData);
    }

    @Override
    public void setResponses(String responseForNo, String responseForYes) {
        BinaryNode<String> yes = new BinaryNode<>(responseForNo, null, null);
        BinaryNode<String> no = new BinaryNode<>(responseForYes, null, null);
        currentNode.setLeftChild(no);
        currentNode.setRightChild(yes);
    }

    @Override
    public boolean isAnswer() {
        if (!currentNode.hasLeftChild() && !currentNode.hasRightChild()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void advanceToNo() {
        currentNode = currentNode.getRightChild();
    }

    @Override
    public void advanceToYes() {
        currentNode = currentNode.getLeftChild();
    }

    @Override
    public void resetCurrentNode() {
        currentNode = root;
    }

    @Override
    public void setTree(String rootData) {
        super.setTree(rootData);
        currentNode = root;
    }

    @Override
    public void setTree(String rootData, BinaryTreeInterface<String> leftTree, BinaryTreeInterface<String> rightTree) {
        super.setTree(rootData, leftTree, rightTree);
        currentNode = root;
    }
}
