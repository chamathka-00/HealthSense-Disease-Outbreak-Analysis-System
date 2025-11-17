package healthsense.analysis;

public class MergeSort {

    // Sorts an array using merge sort and returns a new sorted array
    public static int[] mergeSortArray(int[] array) {
        if (array.length <= 1) {
            return array; // Array is already sorted if it has 0 or 1 element
        }

        int mid = array.length / 2; // Find the midpoint

        int[] left = new int[mid]; // Create left half
        int[] right = new int[array.length - mid]; // Create right half

        for (int i = 0; i < mid; i++) {
            left[i] = array[i]; // Copy first half
        }

        for (int j = 0; j < right.length; j++) {
            right[j] = array[mid + j]; // Copy second half
        }

        left = mergeSortArray(left); // Sort left half
        right = mergeSortArray(right); // Sort right half

        return mergeSortedArrays(left, right); // Merge both halves and return
    }

    // Merges two sorted arrays into one sorted array
    public static int[] mergeSortedArrays(int[] arr1, int[] arr2) {
        int i = 0, j = 0, k = 0; // Pointers for arr1, arr2, and merged array
        int[] merged = new int[arr1.length + arr2.length]; // New array to hold merged result

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                merged[k++] = arr1[i++]; // Add smaller value from arr1
            } else {
                merged[k++] = arr2[j++]; // Add smaller value from arr2
            }
        }

        while (i < arr1.length) {
            merged[k++] = arr1[i++]; // Add remaining elements from arr1
        }

        while (j < arr2.length) {
            merged[k++] = arr2[j++]; // Add remaining elements from arr2
        }

        return merged; // Return the merged array
    }
}
