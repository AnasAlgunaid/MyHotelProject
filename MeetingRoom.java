public class MeetingRoom extends Room{
    // Attributes
    private int capacity;

    // Constructors
    public MeetingRoom(int roomNumber, double price, int capacity) {
        super(roomNumber, price);
        this.capacity = capacity;
    }

    // Getters and setters
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return (String.format("%-15d %-15s %-15b %-20d", super.getRoomNumber(),  super.getPrice(),  super.isAvailable(), this.capacity));
    }
}
