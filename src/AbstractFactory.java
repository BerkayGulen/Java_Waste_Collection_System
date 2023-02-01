
interface AbstractFactory {
    TrashBin createTrashBin();
    Landfill createLandfill();
}

class MedicalFactory implements AbstractFactory {
    @Override
    public TrashBin createTrashBin() {
        return new MedicalTrashBin();
    }

    @Override
    public Landfill createLandfill() {
        return new MedicalWasteLandfill();
    }

}

class GeneralFactory implements AbstractFactory{
    @Override
    public TrashBin createTrashBin() {
        return new GeneralTrashBin();
    }

    @Override
    public Landfill createLandfill() {
        return new GeneralUseLandfill();
    }
}


abstract class TrashBin {

    private final double maxStorage = 100;
    private double trash;
    private Sensor sensor ;
    protected boolean isNotified = false;

    public TrashBin() {
        this.trash=0;
    }

    public Sensor getSensor(){
        return sensor;
    }
    public void setSensor(Sensor sensor){
        this.sensor = sensor;
    }

    public void addTrash(double value){//It takes the value of the trash and adds it to bin. Warns if the value of the trash reaches the maximum storage value. It has double fillRate as percentage and notify sensor if It has not notified before.

        if (this.trash + value > this.maxStorage) {
        	System.out.println("You can not add your trash to this bin. " + "You have " +(this.trash+value-100) +" unit trash left. Please use another trash bin.");
            System.out.println();
            this.trash = this.maxStorage;
        }
        else {
            System.out.println(value+" unit trash dumped");
        	this.trash += value;
        }

        double fillRate = (this.trash/maxStorage)*100;

        if (!this.isNotified) {
            if (fillRate >= 80){
                isNotified = true;
                sensor.Notify(1);
            }
		}


    }
    public double emptyTrash(){ // make trash bin empty and trash bin’s sensor notify the observer
        double dummyTrash = this.trash;
        this.trash = 0;
        this.isNotified = false;
        sensor.Notify(0);
        return dummyTrash;
    }

    abstract String getName();

    public double getMaxStorage() {
        return maxStorage;
    }

    public double getTrash() {
        return trash;
    }

}

class MedicalTrashBin extends TrashBin{

    @Override
    public String getName() {
        return "Medical Trash Bin";
    }
}

class GeneralTrashBin extends TrashBin {

    @Override
    public String getName(){
        return "General Trash Bin";
    }
}

abstract class Landfill{
    protected final double maxStorage = 10000;
    protected double trash;

    public Landfill(){
        this.trash = 0;
    }
    
    public void dumpTrash(double value) {
        System.out.println(value+" unit trash is dumped");
    	trash += value;
        System.out.println("Available Storage: "+(this.maxStorage-this.trash));
        System.out.println();

    }
    
	public abstract void print();

}

class MedicalWasteLandfill extends Landfill{
	public void print() {
		System.out.println("***** MEDICAL LANDFILL *****");
        System.out.println("Available Storage: "+(this.maxStorage-this.trash));
        System.out.println();
	}

}

class GeneralUseLandfill extends Landfill{
	public void print() {
        System.out.println("***** GENERAL LANDFILL *****");
        System.out.println("Available Storage: "+(this.maxStorage-this.trash));
        System.out.println();
	}
}
