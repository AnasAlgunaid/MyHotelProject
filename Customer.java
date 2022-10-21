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
// TODO: 10/21/2022
//    @Override
//    public String toString() {
//        return (String.format("%-15s %-25s %-12s %-10d", getID(), (getFirstName() + getLastName()), getPhoneNumber(), numOfReservations));
//    }

    @Override
    public String toString() {
        return (String.format("%-15s %-25s %-15s", getID(), (getFirstName() + " " + getLastName()), getPhoneNumber()));
    }
}
