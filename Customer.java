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

    @Override
    public String toString() {
        return (String.format("%-15s %-25s %-17s", getID(), (getFirstName() + " " + getLastName()), getPhoneNumber()));
    }
}
