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

    // Method to print the header
    public static void printHeader(){
        System.out.println();
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%-20s %-15s %-15s %-20s \n", "Room number", "Price", "Available", "Capacity(People)");
        System.out.println("---------------------------------------------------------------------");
    }

    @Override
    public String toString() {
        return (String.format("%-20d %-15s %-15s %-20d", super.getRoomNumber(),  super.getPrice(),  (super.isAvailable()?"Yes": "No"), this.capacity));
    }
} // End of MeetingRoom class
