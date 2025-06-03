# 🏥 Hospital Queue Simulation Report

We started this project by trying to really understand how queues work in a hospital setting — especially the difference between a normal queue and a priority queue. In a normal queue, it's simple: whoever comes first gets served first. But that doesn't always make sense in real life, especially in healthcare. That's where the priority queue comes in, where patients are treated based on how critical their condition is.

Before jumping into code, we spent time studying how these two systems behave and how they could be modeled in Java. Once we were confident with the theory, we moved on to implementation. We decided to use linked lists to manage both queues inserting patients in the correct position depending on either their arrival time (for the normal queue) or their criticality level (for the priority queue).

At first, everything seemed to work fine — until we ran into a problem. In our early version of the priority queue, if a critical patient arrived while another (less critical) patient was already in a room being treated, the new one would kick the current patient out to get immediate service. That’s obviously not how hospitals work. So we had to fix it.

To solve that, we made sure that once a doctor starts treating someone, they finish with them. The critical patients are still prioritized in the queue, but they don't interrupt active treatments. We realized this issue when a critical patient would sometimes "kick out" someone already being treated, which doesn’t make sense in real life. So we adjusted our logic to fix that.

We did this by checking which doctor becomes available the soonest, instead of just assigning a critical patient to the first one we find. Then, we assign the patient to that available doctor only when they’re actually free. Here’s the key part of the code that makes that happen:

```java
if (serverAvailableTime[j] < serverAvailableTime[availableServer]) {
    availableServer = j;
}

double startTime = Math.max(serverAvailableTime[availableServer], current.arrivalTime);
current.departureTime = startTime + current.serviceTime;
serverAvailableTime[availableServer] = current.departureTime;
```

This way, each doctor always finishes their current case, and critical patients are still prioritized in the queue they just wait for the next available doctor instead of cutting the line mid-treatment.

Another issue we ran into was that patients weren’t always sorted correctly in the priority queue. Sometimes, someone with lower priority would be served before a more critical case. That was a bug in our insertion logic — we were only comparing arrival times, not always checking the criticality properly. We rewrote the condition to check criticality first, and only if it's the same, then compare arrival times. Here's the code fix for that:

```java
public void addPatient(PatientPriority newPatient) {
    if (head == null ||
            newPatient.criticalityLevel < head.criticalityLevel ||
            (newPatient.criticalityLevel == head.criticalityLevel &&
                    newPatient.arrivalTime < head.arrivalTime)) {
        newPatient.next = head;
        head = newPatient;
    } else {
        PatientPriority current = head;
        while (current.next != null &&
                (current.next.criticalityLevel < newPatient.criticalityLevel ||
                        (current.next.criticalityLevel == newPatient.criticalityLevel &&
                                current.next.arrivalTime <= newPatient.arrivalTime))) {
            current = current.next;
        }
        newPatient.next = current.next;
        current.next = newPatient;
    }
}
```
We also had problems with negative time values being generated when using Gaussian distributions — especially for service times. That obviously breaks the logic of a simulation like this, so we wrapped those calculations with Math.max(0, value) to ensure we never had negative values for time. Here's the fix we added:

```java
double serviceTime = Math.max(0, serviceMean + serviceStd * random.nextGaussian());
```

After that, we moved on to adding bonus features to make our simulation more advanced. The original task asked for just two levels of priority Normal and Critical but we expanded that to three: Normal, Urgent, and Critical. We also made sure patient arrivals follow an exponential (Poisson-style) distribution, and service times are drawn from a Gaussian distribution. Lastly, we added the ability to change the number of available doctors, to simulate how a hospital might scale up during busy hours.

---

## 🧱 Implementation Logic

### Code Structure

The logic behind our hospital queue simulation was designed to handle two different types of queues: a **normal queue** (based on arrival time) and a **priority queue** (based on the criticality level of patients). Here’s a breakdown of how we structured the code:

- **Classes:**
   - **PatientNormal**: Represents a patient in the normal queue. Contains fields for arrival time and service time.
   - **PatientPriority**: Represents a patient in the priority queue. Includes additional fields for criticality level (Critical, Urgent, Normal), as well as arrival time and service time.
   - **NormalHospitalQueue**: Handles the normal queue, where patients are added based on arrival time. This queue inserts patients in the order they arrive.
   - **PriorityHospitalQueue**: Manages the priority queue. It sorts patients first by their criticality level (Critical > Urgent > Normal) and then by arrival time for patients of the same priority level.

- **Key Functions:**
   - **addPatient()**: 
     - In **NormalHospitalQueue**, this method adds patients in the order of their **arrival time**.
     - In **PriorityHospitalQueue**, this method first compares patients by **criticality level** (lower numbers are more critical) and then by **arrival time** for patients with the same criticality level.
   - **processQueue()**: Processes patients from both queues. It checks which doctor is available next (using an array `serverAvailableTime[]`) and assigns the next patient accordingly.

- **Patient Generation:**
   - Patients are generated using **Poisson (exponential distribution)** for arrival times and **Gaussian (normal distribution)** for service times.
   - **generateNormalPatient()** and **generatePriorityPatient()** handle the creation of new patients based on these distributions.

This approach provided a realistic simulation of how patients would be handled in a hospital setting, with an efficient system for managing both normal and priority queues while addressing real-world constraints like doctor availability and treatment interruptions.

---

### Simulation

We generated patients using the following logic:
- **Arrival time**: Based on an exponential distribution (using Poisson process logic).
- **Service time**: Based on a Gaussian (normal) distribution.
- **Criticality level**: Randomly chosen (1 → Critical, 2 → Urgent, 3 → Normal).

---

## ⭐ Bonus Implementations

We went beyond the base requirements to implement the following bonus features:

### Multiple Levels of Criticality
Instead of just “Normal” and “Critical,” we added a third level: **Urgent**, resulting in:
- `1` → Critical
- `2` → Urgent
- `3` → Normal

This adds realism by reflecting common hospital triage levels.

### Dynamic Patient Generation
- **Arrival Times**: Generated using an **exponential distribution** (Poisson logic).
- **Service Times**: Generated using a **normal distribution**.

This mimics real-world randomness in both arrivals and treatment durations.

---

## 📊 Output Example

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
