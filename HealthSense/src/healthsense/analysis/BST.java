package healthsense.analysis;

public class BST {

    private BSTNode root; // Root node of the tree

    public BST() { // Constructor to create an empty tree
        root = null;
    }

    // Inserts a value into the tree. Duplicates go to the left.
    public void insert(int value) {
        root = insertRec(root, value); // Recursive helper
    }

    // Recursive insertion logic
    private BSTNode insertRec(BSTNode root, int value) {
        if (root == null) { // Found a null spot
            root = new BSTNode(value); // Create new node
            return root;
        }
        if (value <= root.value) { // Go left if value is less than or equal
            root.left = insertRec(root.left, value);
        } else { // Go right otherwise
            root.right = insertRec(root.right, value);
        }
        return root; // Return updated root
    }

    // Returns values in sorted order with severity labels (in-order)
    public String getInOrderTraversal() {
        if (root == null) {
            return ""; // Return empty if tree is empty
        }
        StringBuilder result = new StringBuilder(); // Collect output
        inOrderRec(root, result); // Fill the result
        return result.toString(); // Return result
    }

    // Recursive helper for in-order traversal
    private void inOrderRec(BSTNode node, StringBuilder result) {
        if (node == null) return;
        inOrderRec(node.left, result); // Visit left
        result.append(node.value).append("(").append(getSeverity(node.value)).append(") "); // Visit node
        inOrderRec(node.right, result); // Visit right
    }

    // Returns values in pre-order with severity labels
    public String getPreOrderTraversal() {
        if (root == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        preOrderRec(root, result);
        return result.toString();
    }

    // Recursive helper for pre-order traversal
    private void preOrderRec(BSTNode node, StringBuilder result) {
        if (node == null) return;
        result.append(node.value).append("(").append(getSeverity(node.value)).append(") "); // Visit node
        preOrderRec(node.left, result); // Visit left
        preOrderRec(node.right, result); // Visit right
    }

    // Returns values in post-order with severity labels
    public String getPostOrderTraversal() {
        if (root == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        postOrderRec(root, result);
        return result.toString();
    }

    // Recursive helper for post-order traversal
    private void postOrderRec(BSTNode node, StringBuilder result) {
        if (node == null) return;
        postOrderRec(node.left, result); // Visit left
        postOrderRec(node.right, result); // Visit right
        result.append(node.value).append("(").append(getSeverity(node.value)).append(") "); // Visit node
    }

    // Returns severity level based on value
    private String getSeverity(int value) {
        if (value <= 20) {
            return "mild";
        } else if (value <= 50) {
            return "moderate";
        } else {
            return "severe";
        }
    }

    // Checks if the tree is empty
    public boolean isEmpty() {
        return root == null;
    }
}
