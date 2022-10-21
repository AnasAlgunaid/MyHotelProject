import java.time.LocalDate;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

public class Reservation {
    // Data Fields
    private String reservationID;
    private Customer customer;
    private Room room;
    private LocalDate check_in;
    private LocalDate check_out;
    private double totalPrice;

    //Constructors
    public Reservation(Customer customer, Room room) {
        this.reservationID = generateReservationID();
        this.customer = customer;
        this.room = room;
        this.check_in = LocalDate.now();
        this.check_out = LocalDate.now().plusDays(1);
    }

    // Getters and setters


    public String getReservationID() {
        return reservationID;
    }

//    public void setReservationID(String reservationID) {
//        this.reservationID = reservationID;
//    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getCheck_in() {
        return check_in;
    }

    public boolean setCheck_in(LocalDate check_in) {

        LocalDate todayDate = LocalDate.now();

        int compareValue = check_in.compareTo(todayDate);
        if(compareValue >= 0){
            this.check_in = check_in;
            return true;
        }
        else{
            return false;
        }
    }

    public LocalDate getCheck_out() {
        return check_out;
    }

    public boolean setCheck_out(LocalDate check_out) {
        if(DAYS.between(this.check_in, check_out) > 0){
            this.check_out = check_out;
            this.totalPrice = calculateTotalPrice();
            return true;
        }
        else{
            return false;
        }

    }

    public double getTotalPrice() {
        return totalPrice;
    }

//    public void setTotalPrice(double totalPrice) {
//        this.totalPrice = totalPrice;
//    }

    // Other methods
    public double calculateTotalPrice(){
        return ((DAYS.between(check_in, check_out)) * this.room.getPrice());
    }

    public String generateReservationID(){
        // include the first two letters of the customer's name in the reservation ID.
//        String reservationID = "" + this.customer.getFirstName().substring(0, 3);
        // Generate random id of 5 numbers
        reservationID = "";
        Random random = new Random();
        for(int i = 0; i < 5; i++){
            int randomNum = random.nextInt(10);
            reservationID += randomNum;
        }

        return reservationID;
    }

    public boolean automaticUpdateAvailability(){
        LocalDate todayDate = LocalDate.now();

        // If checkout date came, set room available to true
        if(DAYS.between(this.check_out, todayDate) > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isPending(){
        LocalDate todayDate = LocalDate.now();

        if(DAYS.between(todayDate, this.check_out) > 0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString() {
        return (String.format("%-15s %-15s %-15s %-20s %-20s %-15s",
                this.reservationID, customer.getPhoneNumber(),
                room.getRoomNumber(), this.check_in.toString(),
                this.check_out.toString(), this.totalPrice));
    }
}
