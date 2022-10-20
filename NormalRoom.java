public class NormalRoom extends Room{
    // Attributes
    private int numOfBeds;

    // Constructors
    public NormalRoom(int roomNumber, double price, int numOfBeds) {
        super(roomNumber, price);
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
        return (String.format("%-15d %-15s %-15b %-20d", super.getRoomNumber(),  super.getPrice(),  super.isAvailable(), this.numOfBeds));
    }
}
