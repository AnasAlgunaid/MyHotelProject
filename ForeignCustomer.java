public class ForeignCustomer extends Customer{
    // Attributes
    private String passport;
    private String country;

    // Constructors
    public ForeignCustomer(String ID, String firstName, String lastName, String phoneNumber, String password, String passport, String country) {
        super(ID, firstName, lastName, phoneNumber, password);
        this.passport = passport;
        this.country = country;
    }

    // Getters and setters
//    public String getPassport() {
//        return passport;
//    }

//    public void setPassport(String passport) {
//        this.passport = passport;
//    }

//    public String getCountry() {
//        return country;
//    }

//    public void setCountry(String country) {
//        this.country = country;
//    }

    // Other methods
    public static void printHeader(){
        System.out.println();
        System.out.printf("%-17s %-20s %-17s %-17s %-15s %-15s\n", "National ID", "Full name", "Phone number", "Passport", "Country", "Reservations");
        System.out.println("--------------------------------------------------------------------------------------------------------");
    }

    @Override
    public String toString() {
        return (String.format("%-17s %-20s %-17s %-17s %-15s %-15d", getID(), (getFirstName() + " " + getLastName()),getPhoneNumber(), this.passport, this.country, getNumOfReservations() ));
    }
}
