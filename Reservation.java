import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Reservation {
    // Data Fields
    private String reservationID;
    private Customer customer;
    private Room room;
    private LocalDate check_in;
    private LocalDate check_out;
    private double totalPrice;

    private static int startID = 0;
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

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
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

    // Other methods
    public double calculateTotalPrice(){
        return ((DAYS.between(check_in, check_out)) * this.room.getPrice());
    }

    // Generate a unique ID
    public String generateReservationID(){
        int ID = ++startID;
        return String.format("%06d", ID);
    }

    // Check the status of the reservation to see if it's finished, and change the room's availability based on that.
    public void automaticUpdateAvailability(){
        LocalDate todayDate = LocalDate.now();

        if((DAYS.between(todayDate, this.check_in) <= 0) && (DAYS.between(todayDate, this.check_out) >= 0))
        {
            getRoom().setAvailable(false);
        }
        else{
            getRoom().setAvailable(true);
        }

    }

    // Check if the reservation is valid based on today's date
    public boolean isValid(){
        LocalDate todayDate = LocalDate.now();

        if(DAYS.between(todayDate, this.check_out) > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public static void printHeader(){
        System.out.println();
        System.out.printf("%-20s %-15s %-15s %-15s %-15s %-10s %-10s\n", "Reservation ID", "Phone number", "Room number", "Check-in", "Check-out", "Status", "Total Price");
        System.out.println("-------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public String toString() {
        return (String.format("%-20s %-15s %-15s %-15s %-15s %-10s %.2f",
                this.reservationID, customer.getPhoneNumber(),
                room.getRoomNumber(), this.check_in.toString(),
                this.check_out.toString(), (isValid()? "Valid": "Expired"),
                this.totalPrice));
    }
} // End of Reservation class
