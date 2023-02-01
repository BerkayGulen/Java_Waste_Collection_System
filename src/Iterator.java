interface AbstractIterator{
    void first();
    void next();
    Boolean isDone();
    TrashBin currentItem();
}

interface Aggregate{
    AbstractIterator createIterator();
    TrashBin get(int index);
    int getCount();
}

class Gps implements AbstractIterator{
    private final Aggregate city;
    private int currentIndex;

    public Gps(Aggregate city) {
        this.city=city;
        this.currentIndex = 0;
    }

    @Override
    public void first() {
        this.currentIndex = 0;
    }

    @Override
    public void next() {
        this.currentIndex += 1;
    }

    @Override
    public Boolean isDone() {
        return currentIndex >= city.getCount();
    }

    @Override
    public TrashBin currentItem() {
       return (city.get(currentIndex));
    }
}