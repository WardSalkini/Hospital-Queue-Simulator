# Hospital Queue Simulator

A Java-based simulation project that demonstrates the implementation and comparison of normal queues versus priority queues in a hospital environment.

## 📋 Project Overview

This simulation models patient flow through a hospital system using two different queue management approaches:
1. **Normal Queue**: First-come, first-served (FCFS) approach
2. **Priority Queue**: Patients are served based on criticality level (Critical, Urgent, Normal)

The project demonstrates how different queue management strategies can impact patient waiting times and overall system efficiency.

## 🧠 Key Concepts Applied

- **Queue Data Structure**: Implementation of queue operations
- **Priority Queue**: Enhanced queue with priority-based ordering
- **Discrete Event Simulation**: Modeling arrival and service times
- **Exponential & Normal Distributions**: Realistic modeling of arrival and service times
- **Linked List Implementation**: Used for queue management

## 🗂️ Project Structure

The project consists of three main Java files:

1. **Main.java**: Contains the simulation driver code, patient generation, and simulation execution
2. **NormalQueue.java**: Implements the standard first-come, first-served queue
3. **PriorityQueue.java**: Implements the priority-based queue where patients are served based on criticality level

## 🏥 Patient Types

### Normal Patient
- Properties: Arrival time, service time
- Ordered by: Arrival time only

### Priority Patient
- Properties: Arrival time, service time, criticality level (1-3)
- Ordered by: Criticality level first, then arrival time
- Criticality levels:
  - 1: Critical (highest priority)
  - 2: Urgent
  - 3: Normal

## 🚀 How to Run the Simulation

1. Clone this repository
2. Compile the Java files:<br><br>
    ```bash 
    javac Hospital/*.java

3. Run the simulation:<br><br>
     ```bash 
    java Hospital.Main

## ⚙️ Simulation Parameters

You can modify the following parameters in the `main` method of the `Main` class:

- `numPatients`: Number of patients to simulate
- `numServers`: Number of hospital service points (doctors/nurses)
- `arrivalMean`: Mean time between patient arrivals (exponential distribution)
- `serviceMean`: Mean time to serve a patient (normal distribution)
- `serviceStd`: Standard deviation of service time (normal distribution)

## 📊 Simulation Results

The simulation outputs arrival time, service time, and departure time for each patient in both queue types. For the priority queue, the criticality level is also displayed.

Example output:
```text
=== Simulation Results ===

Normal Queue:
Arrival Time | Service Time | Departure Time
--------------------------------------------
     3.75    |     11.81     |     15.56
     13.34   |     13.42     |     28.98
     ...

Priority Queue:
Arrival Time | Service Time | Departure Time | Criticality
-----------------------------------------------------------
     5.23    |     7.14     |     12.38     | Critical
     1.58    |     10.03    |     22.41     | Urgent
     ...
```

## 💡 Key Findings

The simulation demonstrates that:

1. **In a normal queue:**
   - Patients are served strictly in order of arrival
   - This can lead to critical cases waiting behind less urgent ones

2. **In a priority queue:**
   - Critical patients receive care first, regardless of arrival time
   - This can significantly reduce waiting times for urgent cases
   - Standard cases may experience longer waits but this is acceptable to prioritize critical care

## 🔍 Implementation Details

### Patient Generation
- Arrival times follow an exponential distribution (typical for random arrival processes)
- Service times follow a normal (Gaussian) distribution with specified mean and standard deviation

### Queue Processing
- Each queue maintains a linked list of patients
- The simulation tracks available servers and assigns patients as servers become available
- The process calculates departure times based on arrival time, service time, and server availability

## 🧪 Future Enhancements

Potential extensions to this project could include:
- Collecting and analyzing statistics on waiting times
- Simulating different hospital departments with varying parameters
- Incorporating real-world data to calibrate the simulation

## 👨‍💻 Contributors
- Mostafa Hany
- Nada Mostafa
- Ward Salkini
- Anas Mohamed

## 📝 License
This project is licensed under the MIT License - see the [LICENSE](https://github.com/Jiro75/Hospital-Queue-Simulator/blob/72f61dd3e29712d06b8355b717042a5368f38ce2/LICENSE) file for details

## 📫 Contact
Email: mohamed.ahmed056@eng-st.cu.edu.eg <br>
LinkedIn: [www.linkedin.com/in/mostafahany4705](https://www.linkedin.com/in/mohamed-sayed-283948332/)
