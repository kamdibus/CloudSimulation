package project.model;

import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Vm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class VMs {

    private List<Vm> vms = new ArrayList<>();

    public VMs(int numOfVMs, int brokerId) {
        int mips = 250;
        long size = 10000L;
        int ram = 512;
        long bw = 1000L;
        int pesNumber = 1;
        String vmm = "Xen";

        IntStream.range(0,numOfVMs)
                .forEach(id -> vms.add(new Vm(id, brokerId, (double)mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared())));
    }

    public List<Vm> list() {
        return vms;
    }
}
