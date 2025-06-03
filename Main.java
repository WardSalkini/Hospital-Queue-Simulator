package Hospital;
import java.util.Random;

public class Main {
    public static PatientNormal generateNormalPatient(double currentTime, double arrivalMean, double serviceMean, double serviceStd) {
        Random random = new Random();
        double arrivalTime = Math.max(0, -Math.log(1 - random.nextDouble()) * arrivalMean);
        double serviceTime = Math.max(0, serviceMean + serviceStd * random.nextGaussian());
        return new PatientNormal(currentTime + arrivalTime, serviceTime);
    }

    public static PatientPriority generatePriorityPatient(double currentTime, double arrivalMean, double serviceMean, double serviceStd) {
        Random random = new Random();
        double arrivalTime = Math.max(0, -Math.log(1 - random.nextDouble()) * arrivalMean);
        double serviceTime = Math.max(0, serviceMean + serviceStd * random.nextGaussian());
        int criticality = random.nextInt(3) + 1;  // 1 to 3
        return new PatientPriority(currentTime + arrivalTime, serviceTime, criticality);
    }

    public static void main(String[] args) {
        int numPatients = 2;
        int numServers = 1;
        double arrivalMean = 5;
        double serviceMean = 8;
        double serviceStd = 3;

        // Normal queue simulation
        NormalHospitalQueue normalQueue = new NormalHospitalQueue(numServers);
        double currentTimeNormal = 0;
        for (int i = 0; i < numPatients; i++) {
            PatientNormal p = generateNormalPatient(currentTimeNormal, arrivalMean, serviceMean, serviceStd);
            normalQueue.addPatient(p);
            currentTimeNormal = p.arrivalTime;
        }
        normalQueue.processQueue();

        // Priority queue simulation
        PriorityHospitalQueue priorityQueue = new PriorityHospitalQueue(numServers);
        double currentTimePriority = 0;
        for (int i = 0; i < numPatients; i++) {
            PatientPriority p = generatePriorityPatient(currentTimePriority, arrivalMean, serviceMean, serviceStd);
            priorityQueue.addPatient(p);
            currentTimePriority = p.arrivalTime;
        }
        priorityQueue.processQueue();

        // Print both logs
        System.out.println("=== Simulation Results ===");
        System.out.println();
        normalQueue.printLog();
        System.out.println();
        priorityQueue.printLog();
    }
}

