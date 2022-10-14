public class Customer extends User{
    // Data fields
    private int numOfReservations;

    // Constructors
    public Customer(String ID, String firstName, String lastName, String phoneNumber, String password) {
        super(ID, firstName, lastName, phoneNumber, password);
    }

    // Methods
    public int getNumOfReservations(){
        return numOfReservations;
    }

    @Override
    public String toString() {
        return (String.format("%-15s %-25s %-12s %-10d", getID(), (getFirstName() + getLastName()), getPhoneNumber(), numOfReservations));
    }
}
