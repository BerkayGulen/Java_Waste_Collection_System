import java.util.ArrayList;

interface Command { //declares an interface for executing an operation.
    void execute();
}

class TruckDriver { //Reciver class  knows how to go corresponding trashbin make trash empty and dumb to landfill.
    private City city;
    private AbstractIterator gps;

    private double medicalTrashStorage;
    private double generalTrashStorage;

    public TruckDriver(City city) {
        this.city = city;
        this.gps = city.createIterator();
        this.generalTrashStorage = 0;
        this.medicalTrashStorage = 0;
    }

    public void emptyTrashBin(int sensorId) { //call goToCorresponding get trashbin then call trashbin class emptyTrash method and make empty trashbin.
        System.out.println("****** TRUCK DRIVER ******");
        System.out.println("Truck driver enters the sensor ID: "+sensorId+" to his GPS");
        TrashBin trashBin = goToCorrespondingTrashBin(sensorId);
        System.out.println("****** TRUCK DRIVER ******");
        System.out.println("Truck Driver arrived the trash bin.");
        System.out.println("Truck Driver emptied the trash bin." );
        System.out.println();

        if (trashBin.getName().equals("Medical Trash Bin")) medicalTrashStorage += trashBin.emptyTrash();
        else generalTrashStorage += trashBin.emptyTrash();
        print();

    }

    public TrashBin goToCorrespondingTrashBin(int SensorId) { //method get sensor id use gps iterator and find related trashbin with sensor id.
        for (gps.first(); !gps.isDone(); gps.next()) {
            int id = gps.currentItem().getSensor().getSensorId();
            if (id == SensorId) {
                System.out.println("****** GPS ******");
                System.out.println("Trash Bin is located. Initializing  the shortest path...");
                System.out.println("Type of the Trash Bin: "+gps.currentItem().getName());
                System.out.println("Fill Rate of the Trash Bin: %"+(gps.currentItem().getTrash()/gps.currentItem().getMaxStorage())*100);
                System.out.println("Sensor ID of the Trash Bin: "+gps.currentItem().getSensor().getSensorId());
                System.out.println();
                return gps.currentItem();
            }
        }
        return null;
    }


    public void dumpTrashToLandfill(Landfill landfill) { //take landfill reference parameter call landfill dumbTrash method and upload   their trash to related landfill and make own storage to 0.
    	if (landfill instanceof MedicalWasteLandfill) { // if medical lanfill given medical storage of truck is dump
            System.out.println("Truck Driver Arrived to the Medical Landfill");
            landfill.print();
            print();
            landfill.dumpTrash(medicalTrashStorage);
			this.medicalTrashStorage = 0;
		}else if (landfill instanceof GeneralUseLandfill) {  // if general lanfill given general storage of truck is dump
            System.out.println("Truck Driver Arrived to the General Landfill");
            landfill.print();
            print();
            landfill.dumpTrash(generalTrashStorage);
			this.generalTrashStorage = 0;
		}
    }
    
    public void print(){ // print current truck storage of truck
        System.out.println("****** TRUCK STORAGE ******");
        System.out.println("Truck medical trash storage :" + medicalTrashStorage);
        System.out.println("Truck general trash storage :" + generalTrashStorage);
        System.out.println();
    }
}

class ManInCharge { // Invoker class implements Execute by invoking the corresponding operation(s) on Receiver
    private final ArrayList<Command> collectionOrder = new ArrayList<Command>();

    public void giveOrder() {
        for (Command command : collectionOrder) {
            command.execute();
        }
    }

    public void addCommandToCollectionOrder(Command c) {
        collectionOrder.add(c);
    }
}

class collectTrashBinsCommand implements Command { // ConcreteCommand class implements Execute by invoking the corresponding operation(s) on Receiver
    private TruckDriver truckDriver;
    private int sensorId;

    public collectTrashBinsCommand(TruckDriver truckDriver, int sensorId) {
        this.truckDriver = truckDriver;
        this.sensorId = sensorId;
    }

    public void execute() { // #this method call receiver class emptyTrashBin method.
        truckDriver.emptyTrashBin(this.sensorId);
    }

}
