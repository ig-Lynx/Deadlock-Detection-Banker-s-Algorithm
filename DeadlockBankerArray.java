import java.util.*;

public class DeadlockBankerArray {
    private int numProcesses;
    private int numResources;
    private int[] allocation;
    private int[] max;
    private int[] need;
    private int[] available;
    private List<Integer>[] waitForGraph;

    public DeadlockBankerArray(int numProcesses, int numResources) {
        this.numProcesses = numProcesses;
        this.numResources = numResources;
        allocation = new int[numProcesses];
        max = new int[numProcesses];
        need = new int[numProcesses];
        available = new int[numResources];
        waitForGraph = new ArrayList[numProcesses];
        for (int i = 0; i < numProcesses; i++) {
            waitForGraph[i] = new ArrayList<>();
        }
    }

    public void initialize() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Allocation Array:");
        for (int i = 0; i < numProcesses; i++) {
            allocation[i] = sc.nextInt();
        }

        System.out.println("Enter Max Array:");
        for (int i = 0; i < numProcesses; i++) {
            max[i] = sc.nextInt();
            need[i] = max[i] - allocation[i];
        }

        System.out.println("Enter Available Resources:");
        for (int i = 0; i < numResources; i++) {
            available[i] = sc.nextInt();
        }
    }

    public boolean isSafe() {
        boolean[] finish = new boolean[numProcesses];
        int[] work = available.clone();
        List<Integer> safeSequence = new ArrayList<>();

        int count = 0;
        while (count < numProcesses) {
            boolean found = false;
            for (int i = 0; i < numProcesses; i++) {
                if (!finish[i] && need[i] <= work[0]) {
                    work[0] += allocation[i];
                    finish[i] = true;
                    found = true;
                    count++;
                    safeSequence.add(i);
                }
            }
            if (!found) {
                System.out.println("System is in an unsafe state. No safe sequence available.");
                return false; // Unsafe state
            }
        }

        System.out.println("System is in a safe state.");
        System.out.println("Safe sequence: " + safeSequence);
        return true; // Safe state
    }

    public boolean detectDeadlock() {
        boolean[] visited = new boolean[numProcesses];
        boolean[] recursionStack = new boolean[numProcesses];

        for (int i = 0; i < numProcesses; i++) {
            if (!visited[i] && detectCycle(i, visited, recursionStack)) {
                return true;
            }
        }
        return false;
    }

    private boolean detectCycle(int process, boolean[] visited, boolean[] recursionStack) {
        visited[process] = true;
        recursionStack[process] = true;

        for (int neighbor : waitForGraph[process]) {
            if (!visited[neighbor] && detectCycle(neighbor, visited, recursionStack)) {
                return true;
            } else if (recursionStack[neighbor]) {
                return true;
            }
        }

        recursionStack[process] = false;
        return false;
    }

    public void requestResources(int processId, int request) {
        if (request > need[processId] || request > available[0]) {
            System.out.println("Request cannot be granted.");
            return;
        }

        // Tentative allocation
        available[0] -= request;
        allocation[processId] += request;
        need[processId] -= request;

        if (detectDeadlock()) {
            System.out.println("Deadlock detected. Reverting changes.");
            available[0] += request;
            allocation[processId] -= request;
            need[processId] += request;
        } else if (isSafe()) {
            System.out.println("Request granted.");
        } else {
            System.out.println("Request leads to unsafe state. Rolling back.");
            available[0] += request;
            allocation[processId] -= request;
            need[processId] += request;
        }
    }

    public void addDependency(int from, int to) {
        waitForGraph[from].add(to);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of processes:");
        int numProcesses = sc.nextInt();
        System.out.println("Enter number of resources:");
        int numResources = sc.nextInt();

        DeadlockBankerArray system = new DeadlockBankerArray(numProcesses, numResources);
        system.initialize();

        System.out.println("Enter number of dependencies:");
        int dependencies = sc.nextInt();
        System.out.println("Enter dependencies (from to):");
        for (int i = 0; i < dependencies; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            system.addDependency(from, to);
        }

        if (system.detectDeadlock()) {
            System.out.println("Deadlock detected initially.");
        } else {
            System.out.println("No deadlock detected initially.");
        }

        System.out.println("Enter process ID for resource request:");
        int processId = sc.nextInt();
        System.out.println("Enter resource request:");
        int request = sc.nextInt();

        system.requestResources(processId, request);
    }
}