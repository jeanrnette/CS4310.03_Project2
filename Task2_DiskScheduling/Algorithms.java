// CS4310.03 - Operating Systems - Spring 2026
// Jeannette Ruiz - 018120531
// Project 2
// Task 3 - Disk Scheduling Simulator
// ALGORITHM FUNCTIONS

import java.util.*;

public class Algorithms {
    // For a disk with 5,000 cylinders
    static final int MIN_CYLINDER = 0;
    static final int MAX_CYLINDER = 4999;

    // Class to store the results of the algorithm
    // (A) Total amount of head movement
    // (B) Total number of changes in direction
    public static class Results {
        final int total_movement;
        final int total_direction_changes;

        public Results(int total_movement, int total_direction_changes) {
            this.total_movement = total_movement;
            this.total_direction_changes = total_direction_changes;
        }
    }

    // Class to keep track of the head
    static class HeadTracker {
        int curr_position;
        int total_movement; // accumulated movement
        int total_direction_changes; // number of reversals
        int prev_direction; // What was the previous direction? 1 = up, -1 = down, 0 = nothing yet

        public HeadTracker(int curr_head) {
            this.curr_position = curr_head;
            this.total_movement = 0;
            this.total_direction_changes = 0;
            this.prev_direction = 0;
        }

        // Function to update movement and direction changes
        public void moveTo(int next_position)  {
            // Determining the direction of the move (up or down)
            // 1 = up, -1 = down, 0 = no movement
            int new_direction;
            if (next_position > curr_position) new_direction = 1;
            else if (next_position < curr_position) new_direction = -1;
            else new_direction = 0;

            // Updating the distance traveled
            total_movement += Math.abs(next_position - curr_position);

            // Check to see if the direction changed
            if (new_direction != 0 && prev_direction != 0 && new_direction != prev_direction) {
                // If the direction DID change, increment the total ammount of direction changes.
                total_direction_changes++;
            }

            // Updating the previous direction to the current position
            if (new_direction != 0) {
                prev_direction = new_direction;
            }

            // Updating the current head position to the next one
            curr_position = next_position;
        }

        // Function to get the final results
        public Results toResult() {
            return new Results(total_movement, total_direction_changes);
        }
    }



    // ALGORITHM FUNCTIONS -----------------------------------------------------------------

    // [1] Function (FCFS) : To perform first come first serve
    public static Results FCFS(List<Integer> request_list, int curr_head, int prev_head) {
        HeadTracker tracker = new HeadTracker(curr_head);

        // Simply move to the next request in the list
        for (int request : request_list) tracker.moveTo(request);

        return tracker.toResult();
    }

    // [2] Function (SSTF) : To perform shortest seek time
    public static Results SSTF(List<Integer> request_list, int curr_head, int prev_head) {
        // Create a copy of the request list because we will remove requests as we process them
        List<Integer> pending = new ArrayList<>(request_list);
        HeadTracker tracker = new HeadTracker(curr_head);

        // While we still have requests waiting to be attended to...
        while (!pending.isEmpty()) {
            // We will assume that the first request is the best
            int best_index = 0; 

            // Computing distance from the current head to the first request
            int best_distance = Math.abs(pending.get(0) - tracker.curr_position);

            // Now we will scan thorugh the remaining requests to find the closted one to the current head
            for (int i = 1; i < pending.size(); i++) {
                // Get distance between the current candidate and the request we are on
                int distance = Math.abs(pending.get(i) - tracker.curr_position);

                // If the distance from the candidiate is closer than our best distance, we will update our best index
                if (distance < best_distance) {
                    best_distance = distance;
                    best_index = i;
                // However, if they are equal, the tiebreaker is the cylinder number.
                } else if (distance == best_distance && (pending.get(i) < pending.get(best_index))) best_index = i;
            }
            // Now we have the closest request and we must remove it
            int next_request = pending.remove(best_index);
            
            // Move to the next request
            tracker.moveTo(next_request);
        }

        return tracker.toResult(); 
    }

    // [3] Function (SCAN) : To perform SCAN
    public static Results SCAN(List<Integer> request_list, int curr_head, int prev_head) {
        // Keep track of the requests less than and greater than the current head.
        List<Integer> left = new ArrayList<>(); // Less than
        List<Integer> right = new ArrayList<>(); // Greater than
        HeadTracker tracker = new HeadTracker(curr_head);

        // Split the requests into the lists
        for (int request : request_list) {
            if (request < curr_head) left.add(request);
            else right.add(request);
        }

        // Sort the lists to properly ascend / descend
        Collections.sort(left);
        Collections.sort(right);

        // Determine is we are moving up or down at beginning
        boolean movingUp;
        if (curr_head >= prev_head) movingUp = true;
        else movingUp = false;

        // If we are moving UP/RIGHT
        if (movingUp) {
            // Service the requests going up the right list
            for (int request : right) tracker.moveTo(request);

            // Go to the END 4999 (very right) of the cylinder
            if (tracker.curr_position != MAX_CYLINDER) tracker.moveTo(MAX_CYLINDER);

            // Now we reverse and service the requests going down the left list
            for (int i = left.size() - 1; i >= 0; i--) tracker.moveTo(left.get(i));
        }
        // Otherwise, we are moving DOWN/LEFT
        else {
            // Service the requests going down the left list
            for (int i = left.size() - 1; i >= 0; i--) tracker.moveTo(left.get(i));

            // Go to the START 0 (very left) of the cylinder
            if (tracker.curr_position != MIN_CYLINDER) tracker.moveTo(MIN_CYLINDER);

            // Now we reverse and service the requests going up the right list
            for (int request : right) tracker.moveTo(request);
        }
        return tracker.toResult(); 
    }
        
    

    // [4] Function (CSCAN) : To peform C-SCAN
    public static Results CSCAN(List<Integer> request_list, int curr_head, int prev_head) {
        // Keep track of the requests less than and greater than the current head.
        List<Integer> left = new ArrayList<>(); // Less than
        List<Integer> right = new ArrayList<>(); // Greater than
        HeadTracker tracker = new HeadTracker(curr_head);

        // Split the requests into the lists
        for (int request : request_list) {
            if (request < curr_head) left.add(request);
            else right.add(request);
        }

        // Sort the lists to properly ascend / descend
        Collections.sort(left);
        Collections.sort(right);

        // Determine is we are moving up or down at beginning
        boolean movingUp;
        if (curr_head >= prev_head) movingUp = true;
        else movingUp = false;

        // If we are moving UP/RIGHT
        if (movingUp) {
            // Service the requests going up the right list
            for (int request : right) tracker.moveTo(request);

            // Go to the END 4999 (very right) of the cylinder
            if (tracker.curr_position != MAX_CYLINDER) tracker.moveTo(MAX_CYLINDER);

            // Go to the START 0 WITHOUT counting it as a direction change
            if (!left.isEmpty()) {
                tracker.total_movement += (MAX_CYLINDER - MIN_CYLINDER);
                tracker.curr_position = MIN_CYLINDER;
            }

            // Now we service the requests going up the left list
            for (int request : left) tracker.moveTo(request);
        }
        // Otherwise, we are moving DOWN/LEFT
        else {
            // Service the requests going down the left list
            for (int i = left.size() - 1; i >= 0; i--) tracker.moveTo(left.get(i));

            // Go to the START 0 (very left) of the cylinder
            if (tracker.curr_position != MIN_CYLINDER) tracker.moveTo(MIN_CYLINDER);

            // Go to the END 4999 WITHOUT counting it as a direction change
            if (!right.isEmpty()) {
                tracker.total_movement += (MAX_CYLINDER - MIN_CYLINDER);
                tracker.curr_position = MAX_CYLINDER;
            }   

            // Now we reverse and service the requests going up the right list
            for (int i = right.size() - 1; i >= 0; i--) tracker.moveTo(right.get(i));
        }
        return tracker.toResult(); 
    }
}
