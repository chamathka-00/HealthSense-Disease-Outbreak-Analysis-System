package healthsense.model;

public class Hospital {
    private String name;
    private DiseaseLinkedList diseaseList;

    public Hospital(String name) { // Constructor to initialize a new hospital
        this.name = name;
        this.diseaseList = new DiseaseLinkedList();
    }

    public String getName() { // Returns the hospital's name
        return name; // Output the name field
    }

    // Add a disease record (name and patient count)
    public void addDiseaseRecord(String diseaseName, int count) {
        diseaseList.addRecord(diseaseName, count); // Add the disease to the linked list
    }

    // Calculate the total number of patients
    public int getTotalPatients() {
        int total = 0; // Stores the total patient count
        int[] counts = diseaseList.getAllPatientCounts(); // Get an array of patient counts from the disease list
        for (int c : counts) { // Loop through each count
            total += c;
        }
        return total; // Return the total number of patients
    }

    // Sort the disease records by patient count in descending order
    public void sortDiseaseRecords() {
        diseaseList.sortByCountDescending(); // Call the sorting method in DiseaseLinkedList
    }

    // Display all disease records
    public void displayDiseaseRecords() {
        System.out.println("Hospital: " + name);
        diseaseList.display();
    }

    // Find the patient count for a specific disease
    public int findDisease(String diseaseName) {
        return diseaseList.findDiseaseCount(diseaseName); // Call the method to find and return the count
    }

    // Get the disease list object
    public DiseaseLinkedList getDiseaseList() {
        return diseaseList; // Return the internal linked list object
    }

    // Replace the disease list with a new one
    public void setDiseaseList(DiseaseLinkedList newList) {
        this.diseaseList = newList; // Replace the old disease list with the given one
    }
}
