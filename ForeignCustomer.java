public class ForeignCustomer extends Customer{
    // Attributes
    private String passport;
    private String country;

    // Constructors
    public ForeignCustomer(String firstName, String lastName, String phoneNumber, String password, String passport, String country) {
        super(firstName, lastName, phoneNumber, password);
        this.passport = passport;
        this.country = country;
    }

    // Getters and setters
    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // Other methods

    @Override
    public String toString() {
        return (String.format("%-15s %-25s %-15s %-15s %-12s %-10d", getID(), (getFirstName() + getLastName()), this.passport, this.country ,getPhoneNumber(), getNumOfReservations()));
    }
}
