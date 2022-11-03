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

    public static void printHeader(){
        System.out.println();
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%-20s %-15s %-15s %-20s \n", "Room number", "Price", "Available", "Number of beds");
        System.out.println("---------------------------------------------------------------------");
    }

    @Override
    public String toString() {
        return (String.format("%-20d %-15s %-15s %-20d", super.getRoomNumber(),  super.getPrice(),  (super.isAvailable()?"Yes": "No"), this.numOfBeds));
    }
}
