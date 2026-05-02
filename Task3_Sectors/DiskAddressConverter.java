// CS4310.03 - Operating Systems - Spring 2026
// Jeannette Ruiz - 018120531
// Project 2
// Task 3 - Disk Address Converter

import java.util.*;

public class DiskAddressConverter {
    public static void main(String[] args) {

        // Create scanner to get user input
        Scanner scanner = new Scanner(System.in);

        // Program Explanantion
        System.out.println("This program converts a logical block number into <cylinder, track, sector> format.\n");



        // GETTING USER INPUT ---------------------------------------------

        // Get logical block number
        System.out.print("Enter a logical block number: ");
        int block_num = scanner.nextInt();

        // Get num of cylinders
        System.out.print("Enter HD number of cylinders: ");
        int num_cylinders = scanner.nextInt();

        // Get num of tracks
        System.out.print("Enter HD number of tracks: ");
        int num_tracks = scanner.nextInt();

        // Get num of sectors
        System.out.print("Enter HD number of sectors: ");
        int num_sectors = scanner.nextInt();



        // Validation ------------------------------------------------------

        // The logical block number must be over 0 and less than or equal to the total number of blocks
        int total_blocks = num_cylinders * num_tracks * num_sectors;

        if (block_num < 0 || block_num >= total_blocks) {
            System.out.println("The logical block number is out of range for this disk. ");
            scanner.close();
            return;
        }
        


        // Calculations ----------------------------------------------------

        // Blocks per cylinder
        int blocks_per_cylinder = num_tracks * num_sectors;

        // Cylinder
        int cylinder = block_num / blocks_per_cylinder;

        // Remainder
        int remainder = block_num % blocks_per_cylinder;

        // Track / plate
        int track = remainder / num_sectors;

        // Computing sector
        int sector = remainder % num_sectors;


        // Output ----------------------------------------------------------

        // Printing the converted disk address
        System.out.println("The logical block number " + block_num 
            + " is located at:\n\t< " + 
            cylinder + " , " + 
            track + " , "  + 
            sector + " >\n");


        scanner.close();
    }
}