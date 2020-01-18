package project.model;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The cluster consists of two datacenters with two hosts in each one. Each host has two Processing Elements which
 * corresponds to two core processors.
 *
 * Two datacenters can be interpreted as the cluster being spread across two availability zones.
 *
 * Default allocation policy used works as follows:
 *  * VmAllocationPolicySimple is an VmAllocationPolicy that chooses, as the host for a VM, the host
 *  * with less PEs in use. It is therefore a Worst Fit policy, allocating VMs into the
 *  * host with most available PE.
 */
public class Cluster {
    private Datacenter datacenter0;
    private Datacenter datacenter1;

    public Cluster() {
        datacenter0 = createDatacenter("Datacenter_0");
        datacenter1 = createDatacenter("Datacenter_1");
    }

    private Datacenter createDatacenter(String name) {
        List<Host> hostList = createHosts(5);
        LinkedList<Storage> storageList = new LinkedList<>();
        DatacenterCharacteristics characteristics = createCharacteristics(hostList);
        Datacenter datacenter = null;

        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList),
                    storageList, 0.0D);
        } catch (Exception var26) {
            var26.printStackTrace();
        }

        return datacenter;
    }

    private List<Host> createHosts(int numOfHosts) {
        List<Host> hostList = new ArrayList<>();

        for(int i=0;i<numOfHosts;i++) {
            List<Pe> peList = createPes(2);
            int ram = 2048;
            long storage = 1000000L;
            int bw = 10000;
            hostList.add(new Host(i, new RamProvisionerSimple(ram), new BwProvisionerSimple((long)bw),
                    storage, peList, new VmSchedulerSpaceShared(peList)));
        }

        return hostList;
    }

    private List<Pe> createPes(int numOfPes) {
        List<Pe> peList = new ArrayList<>();
        for(int i=0;i<numOfPes;i++) {
            int mips = 1000;
            peList.add(new Pe(i, new PeProvisionerSimple((double) mips)));
        }

        return peList;
    }

    private DatacenterCharacteristics createCharacteristics(List<Host> hostList) {
        String arch = "x86";
        String os = "Linux";
        String vmm = "Xen";
        double time_zone = 10.0D;
        double cost = 30.0D;
        double costPerMem = 30.00D;
        double costPerStorage = 20.00D;
        double costPerBw = 10.0D;

        return new DatacenterCharacteristics(arch, os, vmm, hostList,
                time_zone, cost, costPerMem, costPerStorage, costPerBw);
    }
}
