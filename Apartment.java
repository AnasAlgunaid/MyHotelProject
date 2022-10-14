public class Apartment {
    // Data fields
    private int numOfRooms;
    private int numOfBathRooms;
    private double pricePerNight;
    private boolean available;

    // Constructor
    public Apartment(int numOfRooms, int numOfBathRooms, double pricePerNight, boolean available) {
        this.numOfRooms = numOfRooms;
        this.numOfBathRooms = numOfBathRooms;
        this.pricePerNight = pricePerNight;
        this.available = available;
    }

    // Methods

    public int getNumOfRooms() {
        return numOfRooms;
    }

    public void setNumOfRooms(int numOfRooms) {
        this.numOfRooms = numOfRooms;
    }

    public int getNumOfBathRooms() {
        return numOfBathRooms;
    }

    public void setNumOfBathRooms(int numOfBathRooms) {
        this.numOfBathRooms = numOfBathRooms;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return (String.format("%-12d %-12d %-12f %-12b", numOfRooms, numOfBathRooms, pricePerNight, available));

    }
}
