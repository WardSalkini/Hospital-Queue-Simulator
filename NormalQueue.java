package Hospital;
class PatientNormal {
    double arrivalTime, serviceTime, departureTime;
    PatientNormal next;

 public PatientNormal(double arrivalTime, double serviceTime) {
     this.arrivalTime = arrivalTime;
     this.serviceTime = serviceTime;
     this.next = null;
 }
}

//NormalHospitalQueue class
class NormalHospitalQueue {
     private PatientNormal head;
     private int numServers;
     private double[] serverAvailableTime;

 public NormalHospitalQueue(int numServers) {
     this.head = null;
     this.numServers = numServers;
     this.serverAvailableTime = new double[numServers];
 }

 public void addPatient(PatientNormal newPatient) {
     if (head == null || newPatient.arrivalTime < head.arrivalTime) {
         newPatient.next = head;
         head = newPatient;
     } else {
         PatientNormal current = head;
         while (current.next != null && current.next.arrivalTime <= newPatient.arrivalTime) {
             current = current.next;
         }
         newPatient.next = current.next;
         current.next = newPatient;
     }
 }

 public void processQueue() {
     PatientNormal current = head;

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
     System.out.println("Normal Queue:");
     System.out.printf("%-12s | %-12s | %-14s%n", "Arrival Time", "Service Time", "Departure Time");
     PatientNormal current = head;

     while (current != null) {
         System.out.printf("%-12.2f | %-12.2f | %-14.2f%n",
                 current.arrivalTime, current.serviceTime, current.departureTime);
         current = current.next;
     }
 }
}

