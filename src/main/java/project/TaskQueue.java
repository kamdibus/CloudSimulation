package project;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class TaskQueue {

    private List<Cloudlet> tasks = new ArrayList<>();

    TaskQueue(int numOfTasks) {
        long length = 40000L;
        long fileSize = 300L;
        long outputSize = 300L;
        UtilizationModel utilizationModel = new UtilizationModelFull();
        int pesNumber = 1; // processing elements - number of VMs? cores?

        IntStream.range(0,numOfTasks)
                .forEach(id -> tasks.add(new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel)));
    }


}
