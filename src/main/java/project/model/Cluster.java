package project.model;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Cluster {
    private Datacenter datacenter0;
    private Datacenter datacenter1;

    public Cluster() {
        datacenter0 = createDatacenter("Datacenter_0");
        datacenter1 = createDatacenter("Datacenter_1");
    }

    private Datacenter createDatacenter(String name) {
        List<Host> hostList = new ArrayList<>();
        List<Pe> peList = new ArrayList<>();
        int mips = 1000;
        peList.add(new Pe(0, new PeProvisionerSimple((double)mips)));
        int hostId = 0;
        int ram = 2048;
        long storage = 1000000L;
        int bw = 10000;
        hostList.add(new Host(hostId, new RamProvisionerSimple(ram), new BwProvisionerSimple((long)bw), storage, peList, new VmSchedulerSpaceShared(peList)));
        String arch = "x86";
        String os = "Linux";
        String vmm = "Xen";
        double time_zone = 10.0D;
        double cost = 3.0D;
        double costPerMem = 0.05D;
        double costPerStorage = 0.001D;
        double costPerBw = 0.0D;
        LinkedList<Storage> storageList = new LinkedList();
        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);
        Datacenter datacenter = null;

        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0.0D);
        } catch (Exception var26) {
            var26.printStackTrace();
        }

        return datacenter;
    }
}
