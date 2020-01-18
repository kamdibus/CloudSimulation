package project.model;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TaskQueue {

    private List<Cloudlet> tasks = new ArrayList<>();
    private final int fileSizeOffset = 100;
    private final int lengthOffset = 40000;

    public TaskQueue(int numOfTasks, int userId) {

        UtilizationModel utilizationModel = new UtilizationModelFull();
        int pesNumber = 1; // processing elements - number of VMs? cores?

        IntStream.range(0, numOfTasks)
                .forEach(id -> {
                    Cloudlet cl = new Cloudlet(id, length(id), pesNumber, fileSize(id), fileSize(id),
                            utilizationModel, utilizationModel, utilizationModel);
                    cl.setUserId(userId);
                    tasks.add(cl);
                });
    }

    private int fileSize(int id) {
        return 2 + (int)Math.abs(fileSizeOffset + 200* Math.sin(id));
    }

    private int length(int id) {
        return 2 + (int)Math.abs(lengthOffset + 100000* Math.sin(id));
    }

    public List<Cloudlet> list() {
        return tasks;
    }
}
