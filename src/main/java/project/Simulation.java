package project;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.CloudSim;
import project.model.Brokers;
import project.model.Cluster;
import project.model.TaskQueue;
import project.model.VMs;

import java.text.DecimalFormat;
import java.util.List;

class Simulation {

    private AllocationStrategy allocationStrategy;
    private TaskQueue taskQueue;
    private Brokers brokers;
    private Cluster cluster;
    private VMs vms;

    Simulation(AllocationStrategy allocationStrategy) {
        this.cluster = new Cluster();
        this.brokers = new Brokers();  // will I need more than one in this simulation? would that mean multiple concurrent data producers?
        int brokerId = brokers.broker.getId();
        this.taskQueue = new TaskQueue(1000, brokerId);
        this.vms = new VMs(10, brokerId);

        this.allocationStrategy = allocationStrategy;
    }

    void run() {
        DatacenterBroker broker = brokers.broker;

        broker.submitVmList(vms.list());
        broker.submitCloudletList(taskQueue.list());
        submitTasksToVms(broker);

        Log.printLine("Starting Simulation...");
        CloudSim.startSimulation();
        List<Cloudlet> newList = broker.getCloudletReceivedList();
        CloudSim.stopSimulation();
        printCloudletList(newList);
        Log.printLine("Simulation finished!");
    }

    private void submitTasksToVms(DatacenterBroker broker) {
        int vmPointer = 0;
        for(int cloudletId=0;cloudletId<taskQueue.list().size();cloudletId++) {
            broker.bindCloudletToVm(cloudletId, vmPointer);
            vmPointer = (vmPointer+1)%vms.list().size();
        }
    }

    private static void printCloudletList(List<Cloudlet> list) {
        int size = list.size();
        String indent = "    ";
        Log.printLine();
        Log.printLine("========== OUTPUT ==========");
        Log.printLine("Cloudlet ID" + indent + "STATUS" + indent + "Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");
        DecimalFormat dft = new DecimalFormat("###.##");

        for(int i = 0; i < size; ++i) {
            Cloudlet cloudlet = (Cloudlet)list.get(i);
            Log.print(indent + cloudlet.getCloudletId() + indent + indent);
            if (cloudlet.getCloudletStatus() == 4) {
                Log.print("SUCCESS");
                Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() + indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime()) + indent + indent + dft.format(cloudlet.getFinishTime()));
            }
        }

    }
}
