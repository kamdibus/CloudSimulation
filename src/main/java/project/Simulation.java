package project;

import org.cloudbus.cloudsim.core.CloudSim;
import project.model.Cluster;

import java.util.Calendar;

class Simulation {

    private AllocationStrategy allocationStrategy;
    private TaskQueue taskQueue;
    private Brokers brokers;
    private Cluster cluster;
    private VMs vms;

    Simulation(AllocationStrategy allocationStrategy, TaskQueue taskQueue, Brokers brokers, VMs vms) {
        this.allocationStrategy = allocationStrategy;
        this.taskQueue = taskQueue;
        this.brokers = brokers;
        this.vms = vms;
        this.cluster = new Cluster();
    }

    void run() {
        Calendar calendar = Calendar.getInstance();
        boolean trace_flag = false;
        CloudSim.init(brokers.getNumberOfBrokers(), calendar, trace_flag);

        assignBrokersToVMs(); // this step doesn't make sense to me

        submitTasksToBrokers();

        CloudSim.startSimulation();

        //gather results

        CloudSim.stopSimulation();


    }

    private void submitTasksToBrokers() {

    }

    private void assignBrokersToVMs() {

    }
}
