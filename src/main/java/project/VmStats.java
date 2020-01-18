package project;

import java.util.ArrayList;
import java.util.List;

public class VmStats {
    public int resourceId;
    public List<Integer> cpuTime;
    public List<Integer> cost;

    public VmStats(int resourceId) {
        this.resourceId = resourceId;
        this.cpuTime = new ArrayList<>();
        this.cost = new ArrayList<>();
    }
}
