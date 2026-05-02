<div align="center">

# Project 2

**CS 4310.03 — Operating Systems**

</div>

---


## HOW TO RUN PROGRAMS (IMPORTANT)

All programs are written in Java and require Java (JDK 8 or higher).

⚠️ VERY IMPORTANT ⚠️
For Task 2, the program MUST be run from the same directory that contains:

* DiskSchedulingSim.jar
* input.txt
* random_requests.txt

If this is not followed, the program will NOT read input.txt correctly.

---

## Task 1: Virtual Address Calculator

Run: ```java -jar VirtualAddressCalculator.jar```

Follow prompts:

* Enter page size (KB)
* Enter virtual address

---

## Task 2: Disk Scheduling Simulator

STEP 1: Open terminal and navigate to the correct folder: ```cd path\to\Task2_DiskScheduling```

STEP 2: Run: ```java -jar DiskSchedulingSim.jar <current_head> <previous_head>```

Example: ```java -jar DiskSchedulingSim.jar 53 0```

Program behavior:

* Reads requests from input.txt
* Generates 1000 random requests
* Saves random requests to random_requests.txt
* Runs FCFS, SSTF, SCAN, and C-SCAN

---

## Task 3: Disk Address Converter

Run: ```java -jar DiskAddressConverter.jar```

Follow prompts:

* Enter logical block number
* Enter number of cylinders
* Enter number of tracks
* Enter number of sectors

---

## FILES INCLUDED

* VirtualAddressCalculator.java
* DiskSchedulingSim.java
* Algorithms.java
* DiskAddressConverter.java
* input.txt
* random_requests.txt
* JAR files for each task

---

## NOTES

* input.txt contains the lecture example queue
* random_requests.txt stores generated requests for reproducibility
* DiskSchedulingSim.jar must be executed from its directory for correct file access
