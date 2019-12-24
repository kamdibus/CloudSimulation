package project;

import org.cloudbus.cloudsim.core.CloudSim;

import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        init();

        AllocationStrategy allocationStrategy = new AllocationStrategy();
        Simulation simulation = new Simulation(allocationStrategy);
        simulation.run();
    }

    private static void init() {
        int numBrokers = 1;
        Calendar calendar = Calendar.getInstance();
        boolean trace_flag = false;
        CloudSim.init(numBrokers, calendar, trace_flag);
    }
}

