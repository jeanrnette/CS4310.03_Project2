// CS4310.03 - Operating Systems - Spring 2026
// Jeannette Ruiz - 018120531
// Project 2
// Task 3 - Disk Scheduling Simulator

import java.io.*;
import java.util.*;

public class DiskSchedulingSim {
    // For a disk with 5,000 cylinders
    static final int MIN_CYLINDER = 0;
    static final int MAX_CYLINDER = 4999;
    static final int RAND_REQUEST_COUNT = 1000;

    public static void main(String[] args) {
        
        // GETTING USER INPUT ---------------------------------------------
        // [1] Passing initial disk head position from command line
        // Validation
        if (args.length < 2) {
            System.out.println("You must include current head and previous head.");
            System.out.println("Usage: java DiskSchedulingSim [current_head] [previous_head]");
            return;
        }
        // Read
        int curr_head = Integer.parseInt(args[0]);
        int prev_head = Integer.parseInt(args[1]);

        // [2] Validating input
        if (curr_head < MIN_CYLINDER || curr_head > MAX_CYLINDER || 
            prev_head < MIN_CYLINDER || prev_head > MAX_CYLINDER) {
            System.err.println("Error: The head positions are out range, WHOOPS. Try again...");
            return;
        }
        

        // HANDLING REQUESTS -----------------------------------------------
        // [1] For reading a random Series of requests
        List<Integer> random_requests = generateRandRequests(RAND_REQUEST_COUNT);

        // [2] For reading from input.txt file
        List<Integer> file_requests = readFileRequests("input.txt");
        
        

        // PROCESSING REQUESTS ----------------------------------------------
        // [1] Running all algorithms on RANDOM requests
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|                                                       |");
        System.out.println("|   Running algorithms on randomly gemerated requests   |");
        System.out.println("|                                                       |");
        System.out.println("|-------------------------------------------------------|\n");
        runAlgorithms(random_requests, curr_head, prev_head);
        System.out.println("\n");
        
        // [2] Running all algorithms on FILE requests
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|                                                       |");
        System.out.println("|       Running algorithms on input.txt requests        |");
        System.out.println("|                                                       |");
        System.out.println("|-------------------------------------------------------|\n");
        runAlgorithms(file_requests, curr_head, prev_head);
        System.out.println("\n");
    }



    // FUNCTIONS to HANDLE REQUESTS -----------------------------------------
    // [1] Function (generate_rand_requests) : To generate a list of random requests
    public static List<Integer> generateRandRequests(int count) {
        Random rand = new Random(); // For random number generation
        List<Integer> request_list = new ArrayList<>();

        // Populate list with random numbers ([0-4999])
        for (int i = 0; i < count; i++) {
            request_list.add(rand.nextInt(MAX_CYLINDER + 1)); 
            // +1 since Random doesnt include final num
        }

        // Save the randomly generated list so the results are reproducible
        saveRequestsToFile(request_list, "random_requests.txt");

        // Returning the list of randomly generated requests
        return request_list;
    }
        
    // [2] Function (read_requests) : To read from input.txt and get the list of requests
    public static List<Integer> readFileRequests(String filename) {
        List<Integer> request_list = new ArrayList<>();

        try {
            Scanner file_scanner = new Scanner(new File(filename));
            while (file_scanner.hasNextInt()) {
                int request = file_scanner.nextInt();

                // Make sure request is within range
                if (request >= MIN_CYLINDER && request <= MAX_CYLINDER) {
                    request_list.add(request);
                }
            }

            file_scanner.close();
        } catch (FileNotFoundException e) {
             System.err.println("Error: Could not find the file " + filename + ", whoopsiees.");
        }
        
        return request_list;
    }
    


    // FUNCTION (runAlgorithms) : To run all algorithms on a request list
    public static void runAlgorithms(List<Integer> request_list, int curr_head, int prev_head) {
        printResult("FCFS", Algorithms.FCFS(request_list, curr_head, prev_head));
        printResult("SSTF", Algorithms.SSTF(request_list, curr_head, prev_head));
        printResult("SCAN", Algorithms.SCAN(request_list, curr_head, prev_head));
        printResult("C-SCAN", Algorithms.CSCAN(request_list, curr_head, prev_head));
    }

    // FUNCTION (printResult) : To print the results
    public static void printResult(String alg_name, Algorithms.Results result) {
        System.out.println("" + alg_name + ":\nTotal head movement = " + result.total_movement + ",\nTotal direction changes = " + result.total_direction_changes + "\n");
    }

    // FUNCTION (saveRequestsToFile) : To save randomly generated requestsin separate file to duplciate results / debug
    public static void saveRequestsToFile(List<Integer> request_list, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new File(filename));
            for (int request : request_list) writer.print(request + " ");
            writer.close();
        } 
        catch (FileNotFoundException e) {
            System.err.println("Error: Could not write to file " + filename + " i guess .... :(");
        }
    }
}