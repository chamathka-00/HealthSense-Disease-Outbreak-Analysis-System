package healthsense.datastructures;

public class CustomQueue {

    private int[] queueArray;
    private int front;
    private int rear;
    private int maxSize;

    // Constructor to initialize queue with a fixed size
    public CustomQueue(int size) {
        maxSize = size;
        queueArray = new int[maxSize];
        front = 0;
        rear = -1;
    }

    // Returns true if the queue is empty
    public boolean isEmpty() {
        return (rear == -1);
    }

    // Returns true if the queue is full
    public boolean isFull() {
        return (rear == maxSize - 1);
    }

    // Resizes the queue array to a new capacity
    private void resize(int newCapacity) {
        int[] newArray = new int[newCapacity]; // New array with more space
        int size = rear - front + 1; // Number of valid elements

        // Copy elements from old array to new array
        for (int i = 0; i < size; i++) {
            newArray[i] = queueArray[front + i];
        }

        queueArray = newArray;
        front = 0;
        rear = size - 1;
        maxSize = newCapacity;
    }

    // Adds an item to the rear of the queue
    public void enqueue(int item) {
        if (isFull()) {
            resize(maxSize * 2); // Double the size if full
        }
        queueArray[++rear] = item; // Add item and increment rear
    }

    // Removes and returns the item at the front of the queue
    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot dequeue.");
            return -1;
        }
        int removedItem = queueArray[front++]; // Remove and move front forward

        // Reset queue if it becomes empty
        if (front > rear) {
            front = 0;
            rear = -1;
        }

        return removedItem; // Return removed value
    }
}
