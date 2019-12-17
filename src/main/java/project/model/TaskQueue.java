package project.model;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TaskQueue {

    private List<Cloudlet> tasks = new ArrayList<>();

    public TaskQueue(int numOfTasks, int userId) {
        long length = 40000L;
        long fileSize = 300L;
        long outputSize = 300L;
        UtilizationModel utilizationModel = new UtilizationModelFull();
        int pesNumber = 1; // processing elements - number of VMs? cores?

        IntStream.range(0, numOfTasks)
                .forEach(id -> {
                    Cloudlet cl = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
                    cl.setUserId(userId);
                    tasks.add(cl);
                });
    }

    public List<Cloudlet> list() {
        return tasks;
    }
}
