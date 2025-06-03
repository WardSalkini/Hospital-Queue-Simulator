package Hospital;
class PatientPriority {
     double arrivalTime, serviceTime, departureTime;
     int criticalityLevel;
     PatientPriority next;

 public PatientPriority(double arrivalTime, double serviceTime, int criticalityLevel) {
     this.arrivalTime = arrivalTime;
     this.serviceTime = serviceTime;
     this.criticalityLevel = criticalityLevel;
     this.next = null;
 }
}

class PriorityHospitalQueue {
     private PatientPriority head;
     private int numServers;
     private double[] serverAvailableTime;

 public PriorityHospitalQueue(int numServers) {
     this.head = null;
     this.numServers = numServers;
     this.serverAvailableTime = new double[numServers];
 }

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
 public void processQueue() {
     PatientPriority current = head;

     while (current != null) {
         int availableServer = 0;
         for (int j = 1; j < numServers; j++) {
             if (serverAvailableTime[j] < serverAvailableTime[availableServer]) {
                 availableServer = j;
             }
         }
         double startTime = Math.max(serverAvailableTime[availableServer], current.arrivalTime);
         current.departureTime = startTime + current.serviceTime;
         serverAvailableTime[availableServer] = current.departureTime;
         current = current.next;
     }
 }

 public void printLog() {
     System.out.println("Priority Queue:");
     System.out.printf("%-12s | %-12s | %-14s | %-10s%n", "Arrival Time", "Service Time", "Departure Time", "Criticality");
     PatientPriority current = head;

     while (current != null) {
         String critLevel = switch (current.criticalityLevel) {
             case 1 -> "Critical";
             case 2 -> "Urgent";
             default -> "Normal";
         };
         System.out.printf("%-12.2f | %-12.2f | %-14.2f | %s%n",
                 current.arrivalTime, current.serviceTime, current.departureTime, critLevel);
         current = current.next;
     }
 }
}

