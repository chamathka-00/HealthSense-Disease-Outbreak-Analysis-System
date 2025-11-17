# HealthSense – Disease Outbreak Analysis System

CM1602 – Data Structures and Algorithms for AI

BSc (Hons) Artificial Intelligence & Data Science

Robert Gordon University (RGU)

Coursework – Y1S2

## 📌 Project Overview

HealthSense is a command-line analytics system designed to help epidemiologists monitor and analyze disease outbreak data across multiple hospitals and regions. Developed for the second-semester Data Structures and Algorithms for AI module, the system uses custom data structures and algorithms to manage hospital disease records, sort and search trends, process outbreak reports, classify severity, and support undo operations.

This project demonstrates structured algorithmic thinking, modular programming, and data-structure-driven problem solving while simulating real-world public-health analytics workflows.

## 🎯 Learning Objectives

This project demonstrates the ability to:

* Analyze requirements and design custom data structures.

* Apply sorting algorithms (Bubble Sort, Merge Sort) and searching (Linear Search).

* Implement stacks, queues, linked lists, dynamic arrays, and binary search trees.

* Perform recursive operations for tree traversal.

* Build a fully interactive text-based menu system.

* Manage undo operations using a custom stack.

* Use time-series data analysis and merging logic to identify outbreak peaks.

* Validate input and handle errors safely.

* Create test plans and evaluate system behaviour.

## 🖥️ System Features
🔹 Add Hospitals & Disease Records

* Add hospitals and store disease information (name + patient count).

* Hospital disease lists use a custom singly linked list.

* Data saved in structured format to text files.

🔹 Display All Hospital Data

Shows each hospital with its full disease list.

🔹 Search Functions

* Search by disease name across all hospitals.

* Search hospitals by total patient count.

* Implements Linear Search for both.

🔹 Sort Disease Records

* Sorts disease counts within a hospital using Bubble Sort (descending).

* Makes a backup before sorting for undo support.

🔹 Undo Last Sort

* Uses a custom stack (LIFO) to restore the last unsorted version.

* Stores the last 3 operations.

🔹 Outbreak Report Queue

* Add outbreak reports (region, disease, count).

* Stored in a custom queue, processed using FIFO.

🔹 Process Outbreak Reports

* Processes reports in arrival order.

* Each case count is inserted into a Binary Search Tree for classification.

🔹 BST Severity Classification

* Classifies outbreaks:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<20 = Mild, 21–50 = Moderate, >50 = Severe

* Supports:

  * &nbsp;In-order traversal

  * &nbsp;Pre-order traversal

  * &nbsp;Post-order traversal

🔹 Trend Analysis (Merge Sort)

* Uses Merge Sort to sort diseases by case count across all hospitals.

* Merges results to identify the top 3 peak outbreaks.

🔹 Exit

Clean termination with a confirmation message.

## ✔️ Technologies & Concepts Used

* Java / Custom Implementations

  * &nbsp;Singly Linked List

  * &nbsp;Dynamic Arrays

  * &nbsp;Custom Stack (LIFO)

  * &nbsp;Custom Queue (FIFO)

  * &nbsp;Binary Search Tree with recursion

* Algorithms

  * &nbsp;Bubble Sort (disease sorting)

  * &nbsp;Merge Sort (trend analysis)

  * &nbsp;Linear Search (diseases & hospitals)

* File handling (read/write structured records)

* Menu-driven CLI with validation

* Time-series trend merging

* Test plans to validate system operations

## 📜 Academic Integrity

This repository contains original code written for submission to RGU. Reusing or submitting this work elsewhere without attribution may violate academic integrity guidelines.

## 📘 License

This project is licensed under the Apache License 2.0. You may view, use, and adapt the code for learning and educational purposes, provided that proper attribution is given as required by the license.

Submitting this work, or any modified version of it, as part of an academic assessment is strictly prohibited.
