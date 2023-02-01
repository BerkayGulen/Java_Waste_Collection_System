import java.util.ArrayList;
import java.util.HashMap;

//This class is concrete observer class it keeps sensors state with their id in hashmap if any sensor notified
//update method trriger and update sensor state in hashmap and also it has two worker TruckDriver and ManInCharge
// if at least 3 trashbins  get full manInChrage create new command their giveOrder method.

public class WasteCollectionDepartment implements Observer { //This is the "Singleton" class.
    private static WasteCollectionDepartment uniqueInstance = null;
    private final HashMap<Integer, Boolean> sensorRepository = new HashMap<Integer, Boolean>();
    private final ArrayList<Integer> sensorsToBeCollected = new ArrayList<>();
    private ManInCharge manInCharge;
    private TruckDriver truckDriver;
    private int orderCount;

    //Singleton
    public static WasteCollectionDepartment getInstance() { //Defines an Instance operation that lets clients access its unique
        if (uniqueInstance == null) {
            uniqueInstance = new WasteCollectionDepartment();
        }
        return uniqueInstance;
    }

    private WasteCollectionDepartment() {}

    public void addToSensorRepository(int sensorId, boolean state) {
        sensorRepository.put(sensorId, state);
    }

    public void Update(Sensor sensor) { //method get sensor id use gps iterator and find related trashbin with sensor id.
        if (!sensor.sensorState) { // TrashBin got empty
            for (int sensorId : sensorRepository.keySet()) {
                if (sensorId == sensor.getSensorId()) {
                    sensorRepository.put(sensorId, sensor.sensorState);
                    System.out.println("***** WASTE COLLECTION DEPARTMENT *****");
                    System.out.println("sensor ID: " + sensorId + " notified! sensor's trash bin is got emptied. Updating the state... ");
                    System.out.println();
                }
            }
        } else { // TrashBin got full
            for (int sensorId : sensorRepository.keySet()) {
                if (sensorId == sensor.getSensorId()) {
                    sensorRepository.put(sensorId, sensor.sensorState);
                    System.out.println("***** WASTE COLLECTION DEPARTMENT *****");
                    System.out.println("sensor ID: " + sensorId + " notified! sensor's trash bin is over 80 percent! Updating the state... ");
                    System.out.println();
                    sensorsToBeCollected.add(sensorId);
                    orderCount++;
                }
            }
        }

        if (orderCount == 3) {
            orderCount = 0;
            createCollectionOrder();
            manInCharge.giveOrder();
        }
    }

    public void createCollectionOrder() {
        for (Integer sensorID : sensorsToBeCollected) {
            manInCharge.addCommandToCollectionOrder(new collectTrashBinsCommand(truckDriver, sensorID));
        }
        System.out.println("Collection order created by Waste Collection Department");
        System.out.println();
    }

    public void setManInCharge(ManInCharge manInCharge) {
        this.manInCharge = manInCharge;
    }

    public void setTruckDriver(TruckDriver truckDriver) {
        this.truckDriver = truckDriver;
    }

    void print() {
        System.out.println("***** SENSOR REPOSITORY *****");
        for (int x : sensorRepository.keySet()) {
            if (sensorRepository.get(x) == true){
                System.out.println("ID: " + x + " sensor's trash bin is full");
            }else{
                System.out.println("ID: " + x + " sensor's trash bin is empty");
            }

        }
    }

}
class Municipality { //This class is a municipality of City. It creates sensor and attach sensor to related trash bins.
    private ArrayList<TrashBin> trashBins;
    private ArrayList<Sensor> sensors;
    private WasteCollectionDepartment wasteCollectionDepartment = WasteCollectionDepartment.getInstance();


    public Municipality(ArrayList<TrashBin> trashBins) {
        this.trashBins = trashBins;
        sensors = new ArrayList<>();
    }

    public void createAndAttachSensors() { // Created the sensors and added their states Waste Collection Dep.'s repository (Hashmap). Database has information about sensor ids and their states.
        // Created the sensors and added their states Waste Collection Dep.'s database (Hashmap). Database has information about sensor ids and their states.
        for (int i = 0; i < trashBins.size(); i++) {
            sensors.add(new Sensor());
            wasteCollectionDepartment.addToSensorRepository(sensors.get(i).getSensorId(), sensors.get(i).sensorState);
        }
        // The municipality installs sensors on each trash bin
        for (int i = 0; i < trashBins.size();i++){
            trashBins.get(i).setSensor(sensors.get(i));
        }
        // Attach sensors to Waste Collection Dep.
        for (Sensor sensor : sensors) {
            sensor.Attach(wasteCollectionDepartment);
        }
    }
    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }
    public void print(){
        System.out.println(trashBins);
    }

}

