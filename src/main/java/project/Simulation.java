package project;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.CloudSim;
import project.model.Cluster;
import project.model.TaskQueue;
import project.model.VMs;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;

class Simulation {

    private TaskQueue taskQueue;
    private DatacenterBroker broker;
    private Cluster cluster;
    private VMs vms;

    Simulation() {
        this.cluster = new Cluster();
        try {
            this.broker = new DatacenterBroker("broker0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.taskQueue = new TaskQueue(1000, broker.getId());
        this.vms = new VMs(20, broker.getId());

    }

    void run() {
        broker.submitVmList(vms.list());
        broker.submitCloudletList(taskQueue.list());
        submitTasksToVms(broker);

        CloudSim.terminateSimulation(20000);
        Log.printLine("Starting Simulation");
        CloudSim.startSimulation();
        List<Cloudlet> newList = broker.getCloudletReceivedList();
        CloudSim.stopSimulation();
        printCloudletList(newList);
        Log.printLine("Simulation finished");
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
        String indent = "\t";
        Log.printLine();
        Log.printLine("======================================== OUTPUT ========================================");
        Log.printLine("Cloudlet ID"
                + indent + indent + "STATUS"
                + indent + "Datacenter ID"
                + indent + "VM ID"
                + indent + "Time"
                + indent + "Start Time"
                + indent + "Finish Time"
                //+ indent + "History"
                + indent + indent + "Resources Names"
                + indent + "Processing Cost");
        DecimalFormat dft = new DecimalFormat("###");

        double totalCost = 0;
        double totalTime = 0;
        double finishTime = 0;
        Map<Integer, VmStats> map = new HashMap<>();
        for(int i = 0; i < size; ++i) {
            Cloudlet cloudlet = (Cloudlet)list.get(i);
            totalCost += cloudlet.getProcessingCost();
            totalTime += cloudlet.getActualCPUTime();
            if(i==size-1)
                finishTime = cloudlet.getFinishTime();

            if(!map.containsKey(cloudlet.getVmId())) {
                map.put(cloudlet.getVmId(),
                        new VmStats(
                                cloudlet.getResourceId()
                        ));
            }
            map.get(cloudlet.getVmId()).cpuTime.add((int)cloudlet.getActualCPUTime());
            map.get(cloudlet.getVmId()).cost.add((int)cloudlet.getProcessingCost());

//            Log.printLine(
//                    indent + indent + cloudlet.getCloudletId()
//                    + indent + indent + cloudlet.getCloudletStatusString()
//                    + indent + indent + cloudlet.getResourceName(cloudlet.getResourceId())
//                    + indent + cloudlet.getVmId()
//                    + indent + indent + dft.format(cloudlet.getActualCPUTime())
//                    + indent + indent + dft.format(cloudlet.getExecStartTime())
//                    + indent + indent + dft.format(cloudlet.getFinishTime())
//                    //+ indent + indent + cloudlet.getCloudletHistory()
//                    + indent + indent + Arrays.toString(cloudlet.getAllResourceName())
//                    + indent + indent + dft.format(cloudlet.getProcessingCost()));
        }

        Log.printLine("Total Processing Cost:"+ indent + (int)totalCost);
        Log.printLine("Total Processing Time:"+ indent + (int)totalTime);
        Log.printLine("Finish Time:"+ indent + (int)finishTime);

        map.forEach((k,v) -> {
            IntSummaryStatistics statsCpu = v.cpuTime.stream()
                    .mapToInt((x) -> x)
                    .summaryStatistics();
            IntSummaryStatistics statsCost = v.cost.stream()
                    .mapToInt((x) -> x)
                    .summaryStatistics();
            System.out.println(k + "," + statsCpu.toString()+ "," + statsCost.toString());
        });

    }
}
