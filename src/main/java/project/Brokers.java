package project;

import org.cloudbus.cloudsim.DatacenterBroker;

import java.util.ArrayList;
import java.util.List;

class Brokers {

    private List<DatacenterBroker> brokers = new ArrayList<>();

    Brokers(int numOfBrokers) {
        for(int i=0;i<numOfBrokers;i++) {
            brokers.add(createBroker(i));
        }
    }

    private DatacenterBroker createBroker(int id) {
        DatacenterBroker broker = null;
        try {
            broker = new DatacenterBroker("Broker" + id);
            return broker;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    int getNumberOfBrokers() {
        return brokers.size();
    }
}
