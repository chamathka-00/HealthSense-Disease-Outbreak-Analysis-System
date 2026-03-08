# HealthSense — Disease Outbreak Analysis System

A command-line analytics system designed to help epidemiologists monitor and analyse disease outbreak data across multiple hospitals and regions. HealthSense uses custom data structures and algorithms to manage hospital disease records, sort and search trends, process outbreak reports, classify severity, and support undo operations — simulating real-world public health analytics workflows.



## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Data Structures and Algorithms](#data-structures-and-algorithms)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [Usage Guide](#usage-guide)
- [File Handling](#file-handling)
- [Testing](#testing)
- [License](#license)



## Overview

HealthSense provides a structured, menu-driven interface for managing disease outbreak data. Users can register hospitals and their associated disease records, search and sort data, queue and process outbreak reports, classify severity using a Binary Search Tree, and analyse cross-hospital trends using Merge Sort. All core data structures — linked lists, stacks, queues, dynamic arrays, and BSTs — are implemented from scratch without relying on Java's built-in collections framework.



## Features

### Add Hospitals and Disease Records
- Register hospitals and store associated disease records (disease name + patient count)
- Hospital disease lists are backed by a custom singly linked list
- Records are persisted to structured text files

### Display All Hospital Data
- Lists every registered hospital alongside its full disease record list

### Search Functions
- Search by disease name across all hospitals
- Search hospitals by total patient count
- Both searches use Linear Search

### Sort Disease Records
- Sorts disease case counts within a selected hospital using Bubble Sort (descending order)
- Automatically creates a backup of the unsorted state before sorting to support undo

### Undo Last Sort
- Restores the most recent unsorted disease list using a custom LIFO stack
- Stores the last 3 sort operations for rollback

### Outbreak Report Queue
- Add outbreak reports containing region, disease name, and case count
- Reports are stored in a custom FIFO queue and processed in arrival order

### Process Outbreak Reports
- Dequeues and processes reports in order
- Each case count is inserted into a Binary Search Tree for severity classification

### BST Severity Classification
- Classifies outbreaks into three levels:
  - Mild: fewer than 20 cases
  - Moderate: 21–50 cases
  - Severe: more than 50 cases
- Supports in-order, pre-order, and post-order tree traversal

### Trend Analysis
- Uses Merge Sort to sort diseases by case count across all hospitals
- Merges sorted results to identify the top 3 peak outbreaks system-wide

### Exit
- Clean termination with a confirmation message



## Data Structures and Algorithms

| Component | Implementation |
|---|---|
| Hospital disease lists | Custom singly linked list |
| Record storage | Custom dynamic array |
| Undo support | Custom stack (LIFO) |
| Outbreak report processing | Custom queue (FIFO) |
| Severity classification | Binary Search Tree with recursive traversal |
| Disease sorting (per hospital) | Bubble Sort (descending) |
| Trend analysis (cross-hospital) | Merge Sort |
| Search (disease name, patient count) | Linear Search |



## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code with Java extension) or command-line `javac`/`java`

### Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd healthsense
   ```

2. Compile the project:
   ```bash
   javac -d out src/**/*.java
   ```
   Or open in your IDE and build using the built-in build tool.

### Running the Application

```bash
java -cp out Main
```

Or run `Main.java` directly from your IDE. The system launches a numbered menu — enter the corresponding number to select any operation.



## Usage Guide

| Menu Option | Description |
|---|---|
| 1 | Add a hospital and disease records |
| 2 | Display all hospitals and their disease lists |
| 3 | Search by disease name across all hospitals |
| 4 | Search hospitals by total patient count |
| 5 | Sort disease records for a hospital (Bubble Sort) |
| 6 | Undo the last sort operation |
| 7 | Add an outbreak report to the queue |
| 8 | Process queued outbreak reports (BST insertion) |
| 9 | Display BST severity classification and traversals |
| 10 | Run trend analysis — top 3 peak outbreaks (Merge Sort) |
| 0 | Exit the application |

**Input validation** is applied throughout — invalid entries are rejected with a clear error message and the prompt is re-displayed.



## File Handling

Hospital and disease records are read from and written to structured text files in the project directory. This allows data to persist between sessions. The file format stores one record per line with fields separated by a consistent delimiter.



## Testing

The project includes a test plan covering all major system operations. Validated scenarios include:

- Adding hospitals and disease records with valid and invalid inputs
- Searching by disease name and patient count
- Sorting and verifying descending order output
- Undo restoring the correct pre-sort state
- Enqueueing and processing outbreak reports in FIFO order
- BST insertion and correct severity classification for boundary values (19, 20, 50, 51)
- In-order, pre-order, and post-order traversal output validation
- Merge Sort producing correctly ranked top-3 peak outbreaks
- Empty data edge cases handled gracefully across all operations



## License

This project is licensed under the [MIT License](LICENSE).
