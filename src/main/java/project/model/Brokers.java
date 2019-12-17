package project.model;

import org.cloudbus.cloudsim.DatacenterBroker;

public class Brokers {

    public DatacenterBroker broker;

    public Brokers() {
        broker = createBroker(0);
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
}
