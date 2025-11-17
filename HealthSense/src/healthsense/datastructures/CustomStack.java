package healthsense.datastructures;

public class CustomStack {

    private int[] stackArray;
    private int top;

    // Constructor to create a stack with a fixed size
    public CustomStack(int size) {
        stackArray = new int[size];
        top = -1; // Stack is empty at start
    }

    // Returns true if the stack is empty
    public boolean isEmpty() {
        return top == -1;
    }

    // Returns true if the stack is full
    public boolean isFull() {
        return top == stackArray.length - 1;
    }

    // Resizes the stack array to a new capacity
    private void resize(int newCapacity) {
        int[] newArray = new int[newCapacity];
        System.arraycopy(stackArray, 0, newArray, 0, stackArray.length);
        stackArray = newArray;
    }

    // Pushes an item onto the top of the stack
    public void push(int item) {
        if (isFull()) {
            resize(stackArray.length * 2); // Double size if full
        }
        stackArray[++top] = item; // Add item and move top up
    }

    // Pops the top item off the stack and returns it
    public int pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty. Cannot pop.");
            return -1;
        }
        return stackArray[top--]; // Return item and move top down
    }
}
