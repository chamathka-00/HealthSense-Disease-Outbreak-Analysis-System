package healthsense.model;

public class DiseaseLinkedList {

    private static class DiseaseNode {
        String diseaseName;
        int patientCount;
        DiseaseNode next;

        DiseaseNode(String name, int count) { // Constructor to initialize the node
            this.diseaseName = name;
            this.patientCount = count;
            this.next = null;
        }
    }

    private DiseaseNode head;
    private int size;

    public DiseaseLinkedList() { // Constructor to initialize an empty list
        this.head = null;
        this.size = 0;
    }

    // Adds a new disease record to the end of the list
    public void addRecord(String diseaseName, int patientCount) {
        DiseaseNode newNode = new DiseaseNode(diseaseName, patientCount); // Create a new node
        if (head == null) { // If list is empty, set new node as head
            head = newNode;
        } else {
            DiseaseNode current = head; // Start at the head
            while (current.next != null) { // Traverse to the end
                current = current.next;
            }
            current.next = newNode; // Append the new node
        }
        size++; // Increase the list size
    }

    // Prints all disease records
    public void display() {
        if (head == null) { // If list is empty
            System.out.println("   [No disease records]");
            return;
        }
        DiseaseNode current = head; // Start from the head
        System.out.print("   Diseases: ");
        while (current != null) { // Loop through the list
            System.out.print(current.diseaseName + "(" + current.patientCount + ")");
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println(" -> null"); // End of list
    }

    // Searches for a disease by name
    public int findDiseaseCount(String diseaseName) {
        if (head == null) {
            return -1;
        }

        DiseaseNode current = head;
        String target = diseaseName.toLowerCase();
        int totalCount = 0;

        while (current != null) {
            if (current.diseaseName.toLowerCase().equals(target)) {
                totalCount += current.patientCount;
            }
            current = current.next;
        }

        return totalCount == 0 ? -1 : totalCount;
    }

    // Sorts the disease list in descending order of patient count using bubble sort
    public void sortByCountDescending() {
        if (head == null || head.next == null) {
            return; // List has 0 or 1 node, no sorting needed
        }
        boolean swapped;
        do {
            swapped = false;
            DiseaseNode current = head; // Start from the head
            while (current != null && current.next != null) {
                if (current.patientCount < current.next.patientCount) {
                    // Swap the data between current node and next node
                    String tempName = current.diseaseName;
                    int tempCount = current.patientCount;
                    current.diseaseName = current.next.diseaseName;
                    current.patientCount = current.next.patientCount;
                    current.next.diseaseName = tempName;
                    current.next.patientCount = tempCount;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped); // Repeat until no swaps
    }

    // Returns all disease names in the list
    public String[] getAllDiseaseNames() {
        String[] names = new String[size]; // Create array with current size
        DiseaseNode current = head; // Start from head
        int index = 0;
        while (current != null) {
            names[index++] = current.diseaseName; // Add disease name to array
            current = current.next;
        }
        return names;
    }

    // Returns all patient counts in the list
    public int[] getAllPatientCounts() {
        int[] counts = new int[size]; // Create array with current size
        DiseaseNode current = head; // Start from head
        int index = 0;
        while (current != null) {
            counts[index++] = current.patientCount; // Add count to array
            current = current.next;
        }
        return counts;
    }
}
