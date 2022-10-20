public class NormalRoom extends Room{
    // Attributes
    private int numOfBeds;

    // Constructors
    public NormalRoom(int roomNumber, double price, boolean available, int numOfBeds) {
        super(roomNumber, price, available);
        this.numOfBeds = numOfBeds;
    }

    // Getters and setters
    public int getNumOfBeds() {
        return numOfBeds;
    }

    public void setNumOfBeds(int numOfBeds) {
        this.numOfBeds = numOfBeds;
    }

    // Other methods

    @Override
    public String toString() {
        return "NormalRoom{" +
                "numOfBeds=" + numOfBeds +
                '}';
    }
}
