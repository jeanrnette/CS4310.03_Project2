// CS4310.03 - Operating Systems - Spring 2026
// Jeannette Ruiz - 018120531
// Project 2
// Task 1 - Virtual Address Calculator

import java.util.*;

public class VirtualAddressCalculator {
    public static void main(String[] args) {
        // Create scanner to get user input
        Scanner scanner = new Scanner(System.in);

        // Program Explanantion
        System.out.println("This program will calculate the page number and offset.\n");

        // Get page size
        System.out.print("Enter page size: ");
        int page_size_kb = scanner.nextInt();

        // Get virtual address.
        System.out.print("Enter virtual address: ");
        int virtual_address = scanner.nextInt();

        // Converting the page size into bytes
        int page_size_bytes = page_size_kb * 1024;

        // Calculating the page number and offset
        int page_number = virtual_address / page_size_bytes;
        int offset = virtual_address % page_size_bytes;

        // Output the results
        System.out.println("The address " + virtual_address + " contains:\n\tpage number = " + page_number + "\n\toffset = " + offset);

        scanner.close();
    }
}