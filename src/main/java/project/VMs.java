package project;

import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Vm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class VMs {

    List<Vm> vms = new ArrayList<>();

    VMs(int numOfVMs) {
        int vmid = 0;
        int mips = 250;
        long size = 10000L;
        int ram = 512;
        long bw = 1000L;
        int pesNumber = 1;
        String vmm = "Xen";

        int brokerId = 0; // what should be the initial allocation?

        IntStream.range(0,numOfVMs)
                .forEach(id -> vms.add(new Vm(vmid, brokerId, (double)mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared())));
    }
}
