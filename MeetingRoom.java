public class MeetingRoom extends Room{
    // Attributes
    private int capacity;

    // Constructors
    public MeetingRoom(int roomNumber, double price, boolean available, int capacity) {
        super(roomNumber, price, available);
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
        return "MeetingRoom{" +
                "capacity=" + capacity +
                '}';
    }
}
