package project;

public class Main {

    public static void main(String[] args) {
        AllocationStrategy allocationStrategy = new AllocationStrategy();
        TaskQueue taskQueue = new TaskQueue(100);
        Brokers brokers = new Brokers(10);
        VMs vms = new VMs(15);

        Simulation simulation = new Simulation(allocationStrategy, taskQueue, brokers, vms);

        simulation.run();
    }
}

