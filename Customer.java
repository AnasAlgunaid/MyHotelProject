public class Customer extends User{
    // Attributes
    private int numOfReservations;

    // Constructors
    public Customer(){}
    public Customer(String firstName, String lastName, String phoneNumber, String password) {
        super(firstName, lastName, phoneNumber, password);
    }

    // Getter
    public int getNumOfReservations(){
        return numOfReservations;
    }

    // Other methods
    public void addReservation(){
        numOfReservations++;
    }

    public void cancelReservation(){
        numOfReservations--;
    }

    public static void printHeader(){
        System.out.println();
        System.out.printf("%-17s %-20s %-17s %-15s \n", "National ID", "Full name", "Phone number", "Reservations");
        System.out.println("-------------------------------------------------------------------------");
    }

    @Override
    public String toString() {
        return (String.format("%-17s %-20s %-17s %-15d", getID(), (getFirstName() + " " + getLastName()), getPhoneNumber(), getNumOfReservations()));
    }
}
