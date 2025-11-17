package healthsense;

import java.util.Scanner;
import java.io.*;
import healthsense.datastructures.CustomQueue;
import healthsense.datastructures.CustomStack;
import healthsense.model.Hospital;
import healthsense.model.OutbreakReport;
import healthsense.model.DiseaseLinkedList;
import healthsense.analysis.BST;
import healthsense.analysis.MergeSort;

public class Main {
    private static final String dataFile = "hospitals_data.txt"; // File path for storing hospital data
    private static final String outbreakFile = "outbreak_reports.txt"; // File path for storing outbreak reports

    // Array of hospitals
    private static Hospital[] hospitals = new Hospital[2]; // Resizable hospital array
    private static int hospitalCount = 0; // Number of hospitals stored

    // Stack of undoing sorting operations
    private static CustomStack undoStack = new CustomStack(3); // Stack for undo operations
    private static BackupState[] backupStates = new BackupState[3]; // Stores backup of hospital data

    // Queue for outbreak reports
    private static CustomQueue outbreakQueue = new CustomQueue(2); // Queue for outbreak report processing
    private static OutbreakReport[] outbreakReports = new OutbreakReport[2]; // Holds all reports
    private static int outbreakCount = 0; // Number of outbreak reports

    // Region wise queues for outbreak report FIFO processing
    private static String[] regions = new String[2]; // Store names of regions
    private static CustomQueue[] regionQueues = new CustomQueue[2]; // Store a separate queue for each region
    private static int regionCount = 0; // Number of regions

    private static BST severityTree = new BST(); // Tree to classify outbreak severity

    private static class BackupState { // Inner class to hold original hospital data before sorting
        int hospitalIndex; // Which hospital is being backed up
        String[] diseaseNames; // Saved disease names
        int[] diseaseCounts; // Saved patient counts

        BackupState(int idx, String[] names, int[] counts) {
            this.hospitalIndex = idx;
            this.diseaseNames = names;
            this.diseaseCounts = counts;
        }
    }

    public static void main(String[] args) {
        loadData(); // Load hospital data from file
        loadOutbreakReports(); // Load outbreak reports from file

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Welcome to HealthSense - Disease Outbreak Analysis System");
        System.out.println("========================================================");

        while (!exit) {
            printMenu();
            String choiceInput = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Enter 1-11.");
                continue;
            }

            switch (choice) {
                case 1:
                    addHospitalData(scanner);
                    break;
                case 2:
                    displayAllData();
                    break;
                case 3:
                    searchByDiseaseName(scanner);
                    break;
                case 4:
                    searchByHospitalCount(scanner);
                    break;
                case 5:
                    sortHospitalData(scanner);
                    break;
                case 6:
                    undoLastSort();
                    break;
                case 7:
                    addOutbreakReport(scanner);
                    break;
                case 8:
                    processOutbreakReports();
                    break;
                case 9:
                    viewBSTAnalysis();
                    break;
                case 10:
                    performTrendAnalysis();
                    break;
                case 11:
                    saveData();
                    saveOutbreakReports();
                    System.out.println("Exiting the program...\nThank you for using HealthSense!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Enter a number from 1 to 11.");
            }
        }
        scanner.close();
    }

    private static void ensureHospitalCapacity() { // Resizes hospital array if full
        if (hospitalCount >= hospitals.length) {
            Hospital[] newHospitals = new Hospital[hospitals.length * 2];
            System.arraycopy(hospitals, 0, newHospitals, 0, hospitalCount);
            hospitals = newHospitals;
        }
    }

    private static void ensureOutbreakCapacity() { // Resizes outbreak report array if full
        if (outbreakCount >= outbreakReports.length) {
            OutbreakReport[] newReports = new OutbreakReport[outbreakReports.length * 2];
            System.arraycopy(outbreakReports, 0, newReports, 0, outbreakCount);
            outbreakReports = newReports;
        }
    }

    private static void ensureRegionCapacity() { // Resizes region queue if full
        if (regionCount >= regions.length) {
            String[] newRegions = new String[regions.length * 2];
            CustomQueue[] newQueues = new CustomQueue[regionQueues.length * 2];

            System.arraycopy(regions, 0, newRegions, 0, regionCount);
            System.arraycopy(regionQueues, 0, newQueues, 0, regionCount);

            regions = newRegions;
            regionQueues = newQueues;
        }
    }


    private static void printMenu() {
        System.out.println("\nMenu Options:");
        System.out.println(" 1. Add Hospital Data");
        System.out.println(" 2. Display All Hospital Data");
        System.out.println(" 3. Search by Disease Name");
        System.out.println(" 4. Search by Hospital Total Patient Count");
        System.out.println(" 5. Sort Disease Records for a Hospital");
        System.out.println(" 6. Undo Last Sort");
        System.out.println(" 7. Add Outbreak Report");
        System.out.println(" 8. Process Outbreak Reports");
        System.out.println(" 9. View BST Analysis");
        System.out.println("10. Trend Analysis");
        System.out.println("11. Exit");
        System.out.print("Enter your choice: ");
    }

    // Loads hospital and disease data from the file
    private static void loadData() {
        File file = new File(dataFile);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ensureHospitalCapacity();
                String[] parts = line.split(":", 2);
                if (parts.length != 2) continue;

                String name = parts[0];
                Hospital h = new Hospital(name);

                String[] recs = parts[1].split(";");
                for (String rec : recs) {
                    String[] kv = rec.split(",", 2);
                    if (kv.length != 2) continue;
                    String disease = kv[0];
                    int count = Integer.parseInt(kv[1]);
                    h.addDiseaseRecord(disease, count);
                }
                hospitals[hospitalCount++] = h;
            }
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    // Load outbreak reports from the file
    private static void loadOutbreakReports() {
        File file = new File(outbreakFile);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) continue;

                String region = parts[0].trim().toLowerCase();
                String disease = parts[1].trim();
                int cases = Integer.parseInt(parts[2].trim());

                ensureOutbreakCapacity();
                ensureRegionCapacity();

                OutbreakReport report = new OutbreakReport(region, disease, cases);
                outbreakReports[outbreakCount] = report;

                // Find region index or add new
                int regionIndex = -1;
                for (int i = 0; i < regionCount; i++) {
                    if (regions[i].equalsIgnoreCase(region)) {
                        regionIndex = i;
                        break;
                    }
                }

                if (regionIndex == -1) {
                    regionIndex = regionCount;
                    regions[regionCount] = region;
                    regionQueues[regionCount] = new CustomQueue(2);
                    regionCount++;
                }

                regionQueues[regionIndex].enqueue(outbreakCount);
                outbreakQueue.enqueue(outbreakCount);
                outbreakCount++;
            }
        } catch (IOException e) {
            System.err.println("Error loading outbreak reports: " + e.getMessage());
        }
    }

    // Saves all hospital and disease data to file
    private static void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFile))) {
            for (int i = 0; i < hospitalCount; i++) {
                Hospital h = hospitals[i];
                StringBuilder sb = new StringBuilder();
                sb.append(h.getName()).append(":");

                String[] names = h.getDiseaseList().getAllDiseaseNames();
                int[] counts = h.getDiseaseList().getAllPatientCounts();
                for (int j = 0; j < names.length; j++) {
                    sb.append(names[j]).append(",").append(counts[j]);
                    if (j < names.length - 1) sb.append(";");
                }
                writer.println(sb.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    // Save all outbreak reports to the file
    private static void saveOutbreakReports() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outbreakFile))) {
            for (int i = 0; i < outbreakCount; i++) {
                OutbreakReport report = outbreakReports[i];
                writer.println(report.getRegion() + "," + report.getDisease() + "," + report.getCases());
            }
        } catch (IOException e) {
            System.err.println("Error saving outbreak reports: " + e.getMessage());
        }
    }

    private static void addHospitalData(Scanner scanner) { // Adds a hospital record
        System.out.print("Enter hospital name: "); // Ask for hospital name
        String nameInput = scanner.nextLine().trim();
        int idx = findHospitalIndexByName(nameInput); // Search existing hospitals

        Hospital h;
        if (idx == -1) { // New hospital
            ensureHospitalCapacity(); // Expand array if needed
            h = new Hospital(nameInput); // Create hospital
            hospitals[hospitalCount++] = h; // Store in array
            System.out.println("Added new hospital: " + nameInput);
        } else {
            h = hospitals[idx]; // Get existing hospital
            System.out.println("Found existing hospital: " + h.getName());
        }

        System.out.print("Enter disease name: ");
        String diseaseInput = scanner.nextLine().trim();

        System.out.print("Enter number of patients: ");
        int ptCount;
        try {
            ptCount = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of patients.");
            return;
        }

        h.addDiseaseRecord(diseaseInput, ptCount); // Add disease data
        System.out.println("Added record: " + diseaseInput + " (" + ptCount + ") to " + h.getName());
        saveData();
    }

    private static void sortHospitalData(Scanner scanner) { // Sorts disease list for a hospital
        if (hospitalCount == 0) {
            System.out.println("No hospitals to sort.");
            return;
        }

        System.out.print("Enter hospital name to sort: ");
        String nameInput = scanner.nextLine().trim();
        int idx = findHospitalIndexByName(nameInput);
        if (idx == -1) {
            System.out.println("Hospital not found.");
            System.out.println("Available hospitals:");
            for (int i = 0; i < hospitalCount; i++) {
                System.out.println(" -> " + hospitals[i].getName());
            }
            return;
        }

        Hospital h = hospitals[idx];
        String[] oldNames = h.getDiseaseList().getAllDiseaseNames();
        int[] oldCounts = h.getDiseaseList().getAllPatientCounts();

        int backupPos = getUndoStackSize(); // Get next backup slot
        if (backupPos >= backupStates.length) { // Resize if needed
            BackupState[] newStates = new BackupState[backupStates.length * 2];
            System.arraycopy(backupStates, 0, newStates, 0, backupStates.length);
            backupStates = newStates;
        }
        backupStates[backupPos] = new BackupState(idx, oldNames, oldCounts); // Save state
        undoStack.push(backupPos); // Push index onto stack

        // Perform sort
        h.sortDiseaseRecords();
        System.out.println("Sorted records for " + h.getName() + ":");
        h.displayDiseaseRecords(); // Show updated, sorted list
    }

    private static void undoLastSort() { // Undo last sort using backup
        if (undoStack.isEmpty()) {
            System.out.println("No sort operations to undo.");
            return;
        }

        int pos = undoStack.pop(); // Get last backup index
        BackupState bs = backupStates[pos];
        Hospital h = hospitals[bs.hospitalIndex];

        DiseaseLinkedList restored = new DiseaseLinkedList(); // Restore old data
        for (int i = 0; i < bs.diseaseNames.length; i++) {
            restored.addRecord(bs.diseaseNames[i], bs.diseaseCounts[i]);
        }
        h.setDiseaseList(restored); // Replace current list

        System.out.println("Undo complete for " + h.getName());
        h.displayDiseaseRecords();
        saveData(); // Save restored data
    }

    private static void addOutbreakReport(Scanner scanner) { // Add new outbreak report
        System.out.print("Enter region name: ");
        String region = scanner.nextLine().trim().toLowerCase();

        System.out.print("Enter disease name: ");
        String disease = scanner.nextLine().trim();

        System.out.print("Enter number of cases: ");
        int cases;
        try {
            cases = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of cases.");
            return;
        }

        ensureRegionCapacity();
        ensureOutbreakCapacity();

        OutbreakReport report = new OutbreakReport(region, disease, cases); // Create a new report
        outbreakReports[outbreakCount] = report; // Store it in the array

        // Find region index in region array
        int regionIndex = -1;
        for (int i = 0; i < regionCount; i++) {
            if (regions[i].equalsIgnoreCase(region)) {
                regionIndex = i;
                break;
            }
        }

        // If region doesn't exist, add it to the regions array and make a new queue
        if (regionIndex == -1) {
            regionIndex = regionCount;
            regions[regionCount] = region;
            regionQueues[regionCount] = new CustomQueue(2);
            regionCount++;
        }

        // Add outbreak index to both region queue and global queue
        regionQueues[regionIndex].enqueue(outbreakCount); // Per region queue
        outbreakQueue.enqueue(outbreakCount++); // Global queue

        System.out.println("Enqueued outbreak report: " + report);
        saveOutbreakReports();
    }

    private static void processOutbreakReports() { // Process outbreak reports into BST
        if (outbreakQueue.isEmpty()) {
            System.out.println("No outbreak reports to process.");
            return;
        }

        for (int i = 0; i < regionCount; i++) {
            if (regionQueues[i] == null || regionQueues[i].isEmpty()) continue;

            System.out.println("Region: " + toTitleCase(regions[i]));

            while (!regionQueues[i].isEmpty()) {
                int index = regionQueues[i].dequeue();
                if (index < 0 || index >= outbreakCount || outbreakReports[index] == null) continue;

                OutbreakReport r = outbreakReports[index];
                System.out.println("  Disease: " + r.getDisease() + ", Cases: " + r.getCases());
                severityTree.insert(r.getCases());
            }
        }

        System.out.println("All reports processed.");
    }

    private static void displayAllData() { // Display all hospital data
        if (hospitalCount == 0) {
            System.out.println("No hospital data available.");
            return;
        }
        for (int i = 0; i < hospitalCount; i++) {
            hospitals[i].displayDiseaseRecords();
        }
    }

    private static void searchByDiseaseName(Scanner scanner) { // Search for disease across hospitals
        if (hospitalCount == 0) {
            System.out.println("No data available.");
            return;
        }

        System.out.print("Enter disease name to search: ");
        String search = scanner.nextLine().trim();

        boolean found = false;
        for (int i = 0; i < hospitalCount; i++) {
            int cnt = hospitals[i].findDisease(search); // Get count
            if (cnt != -1) {
                System.out.println(hospitals[i].getName() + ": " + cnt + " patients");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No hospitals report " + search + ".");
        }
    }

    private static void searchByHospitalCount(Scanner scanner) { // Search for hospital by patient total
        if (hospitalCount == 0) {
            System.out.println("No data available.");
            return;
        }

        System.out.print("Enter total patient count to find: ");
        int target;
        try {
            target = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        boolean found = false;
        for (int i = 0; i < hospitalCount; i++) {
            if (hospitals[i].getTotalPatients() == target) {
                System.out.println(hospitals[i].getName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No hospitals have exactly " + target + " patients.");
        }
    }

    private static void viewBSTAnalysis() { // Display in-order, pre-order, post-order
        if (severityTree.isEmpty()) {
            System.out.println("No severity data (process reports first).");
            return;
        }
        System.out.println("BST In-Order: " + severityTree.getInOrderTraversal());
        System.out.println("BST Pre-Order: " + severityTree.getPreOrderTraversal());
        System.out.println("BST Post-Order: " + severityTree.getPostOrderTraversal());
    }

    private static void performTrendAnalysis() { // Trend analysis
        if (hospitalCount == 0) {
            System.out.println("No hospitals available for trend analysis.");
            return;
        }

        int[][] realData = new int[hospitalCount][]; // Stores disease counts per hospital

        // Get patient counts from each hospital's disease list
        for (int i = 0; i < hospitalCount; i++) {
            int[] counts = hospitals[i].getDiseaseList().getAllPatientCounts();
            realData[i] = counts;

            System.out.print("Original data for " + hospitals[i].getName() + ": [");
            for (int j = 0; j < counts.length; j++) {
                System.out.print(counts[j] + (j < counts.length - 1 ? ", " : ""));
            }
            System.out.println("]");
        }

        // Sort each hospital's data
        int[][] sortedData = new int[hospitalCount][];
        for (int i = 0; i < hospitalCount; i++) {
            sortedData[i] = MergeSort.mergeSortArray(realData[i]);

            System.out.print("Sorted data for " + hospitals[i].getName() + ": [");
            for (int j = 0; j < sortedData[i].length; j++) {
                System.out.print(sortedData[i][j] + (j < sortedData[i].length - 1 ? ", " : ""));
            }
            System.out.println("]");
        }

        // Merge all sorted arrays into one big list
        int[] mergedAll = new int[0];
        for (int i = 0; i < hospitalCount; i++) {
            mergedAll = MergeSort.mergeSortedArrays(mergedAll, sortedData[i]);
        }

        // Show the final merged array and top peaks
        if (mergedAll.length > 0) {
            System.out.print("Merged all patient counts: [");
            for (int i = 0; i < mergedAll.length; i++) {
                System.out.print(mergedAll[i] + (i < mergedAll.length - 1 ? ", " : ""));
            }
            System.out.println("]");

            int peaks = Math.min(3, mergedAll.length); // Top 3 or less if there are few data
            System.out.print("Top " + peaks + " peaks: ");
            for (int k = 1; k <= peaks; k++) {
                System.out.print(mergedAll[mergedAll.length - k] + (k < peaks ? ", " : ""));
            }
            System.out.println();
        } else {
            System.out.println("No patient data to analyze.");
        }
    }

    private static int findHospitalIndexByName(String name) { // Finds hospital index by name
        for (int i = 0; i < hospitalCount; i++) {
            if (hospitals[i].getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    private static int getUndoStackSize() { // Counts how many backups exist
        int count = 0;
        for (BackupState bs : backupStates) {
            if (bs != null) count++;
        }
        return count;
    }

    private static String toTitleCase(String str) { // Helper method to change a string first letter to uppercase
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

}