import java.util.*;

public class DeadlockBankerMatrix {
    private int numProcesses;
    private int numResources;
    private int[][] allocation;
    private int[][] max;
    private int[][] need;
    private int[] available;
    private List<Integer>[] waitForGraph;

    public DeadlockBankerMatrix(int numProcesses, int numResources) {
        this.numProcesses = numProcesses;
        this.numResources = numResources;
        allocation = new int[numProcesses][numResources];
        max = new int[numProcesses][numResources];
        need = new int[numProcesses][numResources];
        available = new int[numResources];
        waitForGraph = new ArrayList[numProcesses];
        for (int i = 0; i < numProcesses; i++) {
            waitForGraph[i] = new ArrayList<>();
        }
    }

    public void initialize() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Allocation Matrix:");
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                allocation[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter Max Matrix:");
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                max[i][j] = sc.nextInt();
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        System.out.println("Enter Available Resources:");
        for (int i = 0; i < numResources; i++) {
            available[i] = sc.nextInt();
        }
    }

    public boolean isSafe() {
        boolean[] finish = new boolean[numProcesses];
        int[] work = available.clone();

        int count = 0;
        while (count < numProcesses) {
            boolean found = false;
            for (int i = 0; i < numProcesses; i++) {
                if (!finish[i]) {
                    int j;
                    for (j = 0; j < numResources; j++) {
                        if (need[i][j] > work[j]) {
                            break;
                        }
                    }
                    if (j == numResources) {
                        for (j = 0; j < numResources; j++) {
                            work[j] += allocation[i][j];
                        }
                        finish[i] = true;
                        found = true;
                        count++;
                    }
                }
            }
            if (!found) {
                return false; // Unsafe state
            }
        }
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

    public void requestResources(int processId, int[] request) {
        for (int i = 0; i < numResources; i++) {
            if (request[i] > need[processId][i] || request[i] > available[i]) {
                System.out.println("Request cannot be granted.");
                return;
            }
        }

        // Tentative allocation
        for (int i = 0; i < numResources; i++) {
            available[i] -= request[i];
            allocation[processId][i] += request[i];
            need[processId][i] -= request[i];
        }

        if (detectDeadlock()) {
            System.out.println("Deadlock detected. Reverting changes.");
            for (int i = 0; i < numResources; i++) {
                available[i] += request[i];
                allocation[processId][i] -= request[i];
                need[processId][i] += request[i];
            }
        } else if (isSafe()) {
            System.out.println("Request granted.");
        } else {
            System.out.println("Request leads to unsafe state. Rolling back.");
            for (int i = 0; i < numResources; i++) {
                available[i] += request[i];
                allocation[processId][i] -= request[i];
                need[processId][i] += request[i];
            }
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

        DeadlockBankerMatrix system = new DeadlockBankerMatrix(numProcesses, numResources);
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
        int[] request = new int[numResources];
        System.out.println("Enter resource request:");
        for (int i = 0; i < numResources; i++) {
            request[i] = sc.nextInt();
        }

        system.requestResources(processId, request);
    }
}
