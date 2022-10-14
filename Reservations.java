import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Reservations {
    // Data Fields
    private String reservationID;
    private Customer customer;
    private Apartment apartment;
    private LocalDate check_in;
    private LocalDate check_out;
    private double totalPrice;

    //Constructors
    public Reservations(String reservationID, Customer customer, Apartment apartment, LocalDate check_in, LocalDate check_out, double totalPrice) {
        this.reservationID = reservationID;
        this.customer = customer;
        this.apartment = apartment;
        this.check_in = check_in;
        this.check_out = check_out;
        this.totalPrice = totalPrice;
    }

    // Methods
    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public LocalDate getCheck_in() {
        return check_in;
    }

    public void setCheck_in(LocalDate check_in) {
        this.check_in = check_in;
    }

    public LocalDate getCheck_out() {
        return check_out;
    }

    public void setCheck_out(LocalDate check_out) {
        this.check_out = check_out;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double calculateTotalPrice(){
        return (DAYS.between(check_in, check_out));
    }
}
