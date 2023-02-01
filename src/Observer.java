import java.util.ArrayList;

interface Subject{ //Provides an interface for attaching and detaching Observer objects
    void Attach(Observer observer);
    void Detach(Observer observer);
    void Notify(int fullness);
}
interface Observer{ //Defines  updating interface for WasteCollectionDepartment that should be notified
    void Update(Sensor sensor);
}

//ConcreteSubject
class Sensor implements Subject { //Defines an updating interface for objects that should be notified
    private int sensorId;
    boolean sensorState = false;
    private static int sensorIdCount = 1000;
    private final ArrayList<Observer> observers = new ArrayList<>();

    public Sensor() {
        this.sensorId = sensorIdCount;
        sensorIdCount++;
    }


    @Override
    public void Attach(Observer observer) { //method to attach wasteCollectionDpaertment to sensor
        observers.add(observer);
    }

    @Override
    public void Detach(Observer observer) { //method to detach wasteCollectionDpaertment fromsensor
        observers.remove(observer);
    }

    @Override
    public void Notify(int fullness) {//if trashsBin state sensor call this method and this method call wasteCollectionDepartment update Method
        if (fullness == 0){
            setSensorState(false);
            for (Observer o: observers){
                o.Update(this);
            }
        }
        else {
            setSensorState(true);
            for (Observer o: observers){
                o.Update(this);
            }
        }
    }


    public boolean getSensorState() {
        return sensorState;
    }

    public void setSensorState(boolean sensorState) {
        this.sensorState = sensorState;
    }

    public static int getSensorIdCount() {
        return sensorIdCount;
    }
    public int getSensorId() {
        return sensorId;
    }



}

