

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    // Declare a scanner object
    static Scanner input = new Scanner(System.in);

    // Declare an ArrayList of customers
    static ArrayList<Customer> customers = new ArrayList<>();

    // Declare an ArrayList of rooms
    static ArrayList<Room> rooms = new ArrayList<>();

    // Declare an ArrayList of reservations
    static ArrayList<Reservation> reservations = new ArrayList<>();

    // Declare an ArrayList of receptionist
    static ArrayList<Receptionist> receptionists = new ArrayList<>();

    // Define the default username and password for the control panel
    static String adminUser = "admin";
    static String adminPassword = "admin";

    public static void main(String[] args) {

        // Default data
        rooms.add(new MeetingRoom(113, 250, 12));
        rooms.add(new MeetingRoom(114, 300, 15));
        rooms.add(new MeetingRoom(121, 700, 20));
        rooms.add(new NormalRoom(118, 400, 2));
        rooms.add(new NormalRoom(119, 600, 4));
        rooms.add(new NormalRoom(122, 800, 5));
        receptionists.add(new Receptionist("0000000000", "default", "default", "12345678", "123456", 1000));

        int choice;

        // Display main menu until the user choose to exit the system
        do{

            // Update the rooms availability
            refreshAvailability();

            //Get the choice from the user
            choice = mainMenu();

            switch(choice){
                case 1:
                    customersSystem();
                    break;

                case 2:
                    receptionistSystem();
                    break;

                case 3:
                    controlPanel();
                    break;

                case 4:
                    System.out.println("Thank your for using the program .. GoodBey");
                    break;

                default:
                    System.out.println("Please enter a valid choice");
            }

        }while(choice != 4);
    }

    public static int mainMenu(){
        System.out.println();
        System.out.println("====================== My Hotel System ======================");
        System.out.println("1- Customer system                                          +");
        System.out.println("2- Receptionist system                                      +");
        System.out.println("3- Control Panel                                            +");
        System.out.println("4- Exit                                                     +");
        System.out.println("-------------------------------------------------------------");

        // Return the choice
        return (readInt("Please enter your choice: "));
    }

    public static int customerLoginMenu(){

        int choice;
        do{
            System.out.println("========================= Login Menu ========================");
            System.out.println("1- Sign in                                                  +");
            System.out.println("2- Sign up                                                  +");
            System.out.println("3- Exit                                                     +");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the user
            choice = readInt("Please enter your choice: ");

            int customerIndex;
            switch(choice){

                // Sign in
                case 1:
                {
                    customerIndex = customerLogin();

                    // If the customer not exist
                    if(customerIndex < 0){
                        System.out.println("Customer not found .. Please try again\n");
                        break;
                    }

                    // Return the customer index if it is existed
                    return customerIndex;
                }

                // Sign up
                case 2:
                {
                    if(newCustomer()){
                        // Return the index of the new user
                        return (customers.size()-1);
                    }
                    break;
                }

                case 3:
                    break;

                default:
                    System.out.println("Please enter a valid choice");
            }
        }while(choice != 3);

        // If the customer choose to exit return -1
        return -1;
    }

    public static void customersSystem(){
        // Get the customer index from the login menu
        int customerIndex = customerLoginMenu();

        // If the customer choose to exit or if the customer not exist
        if(customerIndex < 0){
            return;
        }

        int choice;
        do{
            System.out.println();

            // Print welcome message
            System.out.println("\t\t\t\t\tWelcome " + customers.get(customerIndex).printFullName());
            System.out.println("-------------------------------------------------------------");

            // Display the menu
            System.out.println("============= My Hotel System - Customer System =============");
            System.out.println("1- New reservation                                          +");
            System.out.println("2- Update exist reservation                                 +");
            System.out.println("3- Cancel reservation                                       +");
            System.out.println("4- My reservations                                          +");
            System.out.println("5- Log out                                                  +");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the user
            choice = readInt("Please enter your choice: ");

            switch(choice){
                case 1:
                    newReservation(customerIndex);
                    break;

                case 2:
                    updateReservation(customerIndex);
                    break;

                case 3:
                    cancelReservation(customerIndex);
                    break;

                case 4:
                    displayReservationsOfCustomer(customerIndex);
                    break;

                case 5:
                    break;
                default:
                    System.out.println("Please enter a valid choice");

            }
        }while(choice != 5);
    }

    public static void newReservation(int customerIndex){

        // If there are no rooms
        if(rooms.isEmpty()){
            System.out.println("Sorry, there are no rooms");
            return;
        }

        System.out.println();
        System.out.println("============= My Hotel System - New Reservation =============");

        // Get the index of the selected room
        int indexOfSelectedRoom = chooseRoom();

        // If the user choose to go back or choose a room that doesn't exist
        if(indexOfSelectedRoom < 0){
            return;
        }

        // Create the reservation
        addReservation(customers.get(customerIndex), rooms.get(indexOfSelectedRoom));

    }

    // Display the rooms and let the user choose
    public static int chooseRoom(){

        int choice;
        do{
            System.out.println();
            System.out.println("================== Step [1/2]: Choose room ==================");
            System.out.println("1- Normal Room                                              +");
            System.out.println("2- Meeting Room                                             +");
            System.out.println("3- Go back                                                  +");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the user
            choice = readInt("Please enter your choice: ");

            switch(choice){
                case 1:
                {
                    displayAvailableNormalRooms();
                    break;
                }

                case 2:
                {
                    displayAvailableMeetingRooms();
                    break;
                }

                case 3:
                {
                    return -1;
                }

                default:
                    System.out.println("Please enter a valid choice");
            }
        }while(choice != 1 && choice != 2);

        // No match value
        int index = -1;

        // Get the number of rooms
        int numOfRooms = rooms.size();

        do{
            // Get the room number
            int roomNum = readInt("Enter the room number: ");

            // Search for index of the room
            for(int i = 0; i < numOfRooms; i++){
                if((rooms.get(i).getRoomNumber() == roomNum) && rooms.get(i).isAvailable()){
                    index = i;
                    return index;
                }
            }
            System.out.println("Please enter a valid room number from the above table\n");
        }while(index < 0);

        return index;
    }

    // Method to create new reservation
    public static void addReservation(User customer, Room room){
        // Create a new reservation and add the customer and the room
        reservations.add(new Reservation(((Customer) customer), room));

        // Store the index of the new reservation
        int indexOfNewReservation = reservations.size()-1;

        System.out.println("================== Step [2/2]: Enter dates ==================");

        // Get the check-in and check-out date
        getDates(reservations.get(indexOfNewReservation));

        // Increment the number of reservations of the customer
        ((Customer) customer).addReservation();

        // Updating the room's availability
        reservations.get(indexOfNewReservation).automaticUpdateAvailability();

        // Print the bill
        System.out.println("================ My Hotel System - The bill =================");
        Reservation.printHeader();
        System.out.println(reservations.get(indexOfNewReservation).toString());
        System.out.println("-------------------------------------------------------------------------------------------------------------");

        // Print success message
        System.out.println("Reservation created successfully");
    }

    // Method to Create a new account for a customer
    public static boolean newCustomer(){
        int choice;
        do{
            System.out.println();
            System.out.println("=============== My Hotel System - New Account ===============");

            System.out.println("1- Citizen                                                  +");
            System.out.println("2- Resident                                                 +");
            System.out.println("3- Go back                                                  +");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the user
            choice = readInt("Please enter your choice: ");

            // Go back
            if(choice == 3){
                return false;
            }
        }while(choice != 1 && choice != 2);

        // Get the national ID from the user.
        System.out.print("Enter the national ID: ");
        String ID = input.next();

        // Check if the national ID is already exists
        int numOfCustomers = customers.size();
        for(int i = 0; i < numOfCustomers; i++){
            if(ID.equals(customers.get(i).getID())){
                System.out.println("Sorry .. The national ID is already exists.");
                return false;
            }
        }

        // Get the first name
        System.out.print("Enter the first name: ");
        String firstName = input.next();

        // Get the last name
        System.out.print("Enter the last name: ");
        String lastName = input.next();

        // Get the phone number
        System.out.print("Enter the phone number: ");
        String phoneNumber = input.next();

        // Check if the phone number is already exists
        for(int i = 0; i < numOfCustomers; i++){
            if(phoneNumber.equals(customers.get(i).getPhoneNumber())){
                System.out.println("The phone number is already exists .. You can sign in");
                return false;
            }
        }

        // Get the password
        System.out.print("Enter the password: ");
        String password = input.next();

        // if the customer is a citizen
        if(choice == 1){
            // Store the customer
            customers.add(new Customer(ID, firstName, lastName, phoneNumber, password));

            // Print the new customer information
            System.out.println("============ My Hotel System - Account information ==========");
            Customer.printHeader();
            System.out.println(customers.get(customers.size()-1).toString());
        }

        // if the customer is a resident
        else if(choice == 2){
            // Get the country
            System.out.print("Enter the country: ");
            String country = input.next();

            // Get the passport number
            System.out.print("Enter the passport number: ");
            String passport = input.next();

            // Store the customer
            customers.add(new ForeignCustomer(ID, firstName, lastName, phoneNumber, password, passport, country));

            // Print the new customer information
            System.out.println("============ My Hotel System - Account information ==========");
            ForeignCustomer.printHeader();
            System.out.println(customers.get(customers.size()-1).toString());
        }

        // Success message
        System.out.println("\n--------------------+ Account successfully created +---------------------");
        return true;
    }

    // Method to check for customers login
    public static int customerLogin(){

        // Search for the index of the customer
        int index = searchCustomer();

        // If the customer doesn't exist
        if(index < 0){
            return -1;
        }

        // Check for the password
            System.out.print("Enter the password: ");
            String password = input.next();

            // If the password is correct return the index
            if(customers.get(index).getPassword().equals(password)){
                return index;
            }

            // If the password is wrong return -1
            else{
                return -1;
            }
    }


    // Get the customer's current reservations
    public static ArrayList<Integer> getCurrentReservations(int index){
        // Get the number of reservations
        int numOfReservations = reservations.size();

        // Get the customer's phone number
        String customerPhoneNumber = customers.get(index).getPhoneNumber();

        // Store the indexes of reservations that are related to the customer
        ArrayList<Integer> indexesOfRelated = new ArrayList<>();

        // Get the related and current reservations of the customer
        for (int i = 0; i < numOfReservations; i++) {

            // Check if the reservation related to the customer
            boolean relatedReservation = reservations.get(i).getCustomer().getPhoneNumber().equals(customerPhoneNumber);

            // Check if the reservation is valid (current)
            if (relatedReservation) {
                if (reservations.get(i).isValid()) {
                    indexesOfRelated.add(i);
                }
            }
        }

        return indexesOfRelated;
    }

    // ------------------------- Update exist reservation -------------------------

    // Get the check-in and check-out dates from the user
    public static void getDates(Reservation reservation){

        boolean isItCorrectDate;

        do{
            isItCorrectDate = reservation.setCheck_in(inputDate("Please enter the check-in date"));
            if(!isItCorrectDate){
                System.out.println("Check-in date can not be in the past .. Please enter a valid date\n");
                continue;
            }

            // Get the check-out date from the user
            isItCorrectDate = reservation.setCheck_out(inputDate("Please enter the check-out date"));
            if(!isItCorrectDate){
                System.out.println("Check-out date can not be before Check-in date .. Please enter a valid date\n");
            }
        }while(!isItCorrectDate);
    }

    // Method to update the reservation by the customer
    public static void updateReservation(int customerIndex){

        // Get the indexes of current reservations
        ArrayList<Integer> currentReservationsIndexes = getCurrentReservations(customerIndex);


        // Check if there is no reservation
        if(currentReservationsIndexes.isEmpty()){
            System.out.println("Sorry there are no current reservations for the customer");
            return;
        }

        // Print the related and current reservations of the customer
        for(int i : currentReservationsIndexes){
            Reservation.printHeader();
            System.out.println(reservations.get(i).toString());
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------");

        // Get the reservation ID from the customer
        System.out.print("Enter the reservation ID: ");
        String reservationID = input.next();

        // Search for the reservation
        boolean existReservation = false;
        for(int i : currentReservationsIndexes) {
            // If the reservation is existed
            if(reservations.get(i).getReservationID().equals(reservationID)){
                existReservation = true;
                System.out.println("--- Please enter new dates to update the reservation ---\n");

                // Get the check-in and check-out date
                getDates(reservations.get(i));

                // Print the reservation after the update
                System.out.println("--------------------- After the update ----------------------");
                Reservation.printHeader();
                System.out.println(reservations.get(i).toString());
            }
        }

        // If the reservation not found
        if(!existReservation){
            System.out.println("Reservation not found .. Please try again");
        }

    }

    // Method to cancel the reservation by the customer
    public static void cancelReservation(int customerIndex){

        // Get the indexes of customer's current reservations
        ArrayList<Integer> currentReservationsIndexes = getCurrentReservations(customerIndex);

        // Check if there are reservations
        if(currentReservationsIndexes.isEmpty()){
            System.out.println("Sorry there are no current reservations for the customer");
            return;
        }

        // Print the related and current reservations of the customer
        for(int i : currentReservationsIndexes){
            Reservation.printHeader();
            System.out.println(reservations.get(i).toString());
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------");

        // Get the reservation ID from the customer
        System.out.print("Enter the reservation ID: ");
        String reservationID = input.next();

        // Search for the reservation
        boolean existReservation = false;
        for(int i : currentReservationsIndexes) {
            if (reservations.get(i).getReservationID().equals(reservationID)) {
                existReservation = true;

                // Print the reservation before delete it
                System.out.println("The selected reservation: ");
                Reservation.printHeader();
                System.out.println(reservations.get(i).toString());
                System.out.println("-------------------------------------------------------------------------------------------------------------");

                // Make sure the user wants to cancel the reservation
                System.out.print("are you sure you want to cancel the reservation? [yes/no]: ");
                if(checkConfirm()){

                    // Set the room to available and decrement the number of reservations of the customer
                    reservations.get(i).getRoom().setAvailable(true);
                    reservations.get(i).getCustomer().cancelReservation();

                    // Remove the reservation
                    reservations.remove(i);
                    System.out.println("Reservation deleted successfully\n");
                    return;
                }
            }
        }

        // If the reservation not found
        if(!existReservation){
            System.out.println("Reservation not found .. Please try again\n");
        }
    }

    // Display all customer's reservations
    public static void displayReservationsOfCustomer(int customerIndex){

        // Get the number of the reservations
        int numOfReservations = reservations.size();

        // Store the phone number of the customer
        String customerPhoneNumber = customers.get(customerIndex).getPhoneNumber();

        boolean isThereReservation = false;

        // Print the related reservations of the customer
        for(int i = 0; i < numOfReservations; i++){
            boolean relatedReservation = reservations.get(i).getCustomer().getPhoneNumber().equals(customerPhoneNumber);
            if(relatedReservation){
                isThereReservation = true;
                Reservation.printHeader();
                System.out.println(reservations.get(i).toString());
            }
        }

        // If the customer has no reservations
        if(!isThereReservation){
            System.out.println("Sorry you don't have any reservations");
        }
    }

    // Make sure the customer wants to continue
    public static boolean checkConfirm(){
        String confirm = input.next();
        if(confirm.equalsIgnoreCase("yes")){
            return true;
        }
        else{
            return false;
        }
    }


    // Display the available normal rooms to the user
    public static void displayAvailableNormalRooms(){
        // Get the number of rooms
        int roomsArraySize = rooms.size();

        // Print only the available normal rooms from the rooms list
        System.out.println("======================= Normal Rooms ========================");
        NormalRoom.printHeader();

        for(int i = 0; i < roomsArraySize; i++){
            if((rooms.get(i) instanceof NormalRoom) && (rooms.get(i).isAvailable())){
                System.out.println(rooms.get(i).toString());
            }
        }
        System.out.println("---------------------------------------------------------------------");

    }
    // Display the available normal rooms to the user
    public static void displayAvailableMeetingRooms(){
        // Get the number of rooms
        int roomsArraySize = rooms.size();

        // Print only the available meeting rooms from the rooms list
        System.out.println("======================= Meeting Rooms =======================");
        MeetingRoom.printHeader();
        for(int i = 0; i < roomsArraySize; i++){
            if((rooms.get(i) instanceof MeetingRoom)  && (rooms.get(i).isAvailable())){
                System.out.println(rooms.get(i).toString());
            }
        }
        System.out.println("---------------------------------------------------------------------");
    }

    // Method to get a valid date
    public static LocalDate inputDate(String prompt){
        LocalDate enteredDate;

        while(true){
            System.out.println(prompt);
            int year = readInt("Year: ");
            int month = readInt("Month: ");
            int day = readInt("Day: ");

            try{
                enteredDate = LocalDate.of(year, month, day);
                break;
            }catch(Exception e){
                System.out.println("Please enter valid date\n");
            }
        }

        return enteredDate;
    }

    // Receptionist system menu
    public static void receptionistSystem(){
        int index = receptionistLogin();

        // If the receptionist not found
        if(index < 0){
            System.out.println("Receptionist not found .. Please try again");
            return;
        }

        else{
            System.out.println("-------------------------------------------------------------");
            System.out.println("\t\t\t\t\tWelcome " + receptionists.get(index).printFullName());
            System.out.println("-------------------------------------------------------------");

        }

        int choice;
        do{
            // Update the rooms availability
            refreshAvailability();

            System.out.println();
            System.out.println("=========== My Hotel System - Receptionist System ===========");
            System.out.println("1- Reservations                                             +");
            System.out.println("2- Customers                                                +");
            System.out.println("3- Rooms                                                    +");
            System.out.println("4- Exit                                                     +");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the user
            choice = readInt("Please enter your choice: ");

            switch(choice){
                case 1:
                    reservationsMenu();
                    break;

                case 2:
                    customersMenu();
                    break;

                case 3:
                    roomsMenu();
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Please enter a valid choice");

            }

        }while(choice != 4);
    }

    // Reservations menu
    public static void reservationsMenu(){
        int choice;
        do{
            System.out.println();
            System.out.println("============ My Hotel System - Reservations Menu ============");
            System.out.println("1- New reservation                                          +");
            System.out.println("2- Update reservation                                       +");
            System.out.println("3- Cancel reservation                                       +");
            System.out.println("4- Print all reservations                                   +");
            System.out.println("5- Exit                                                     +");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the user
            choice = readInt("Please enter your choice: ");

            switch(choice){
                case 1:
                    int customerIndex = customerLoginMenu();

                    // If the customer not found
                    if(customerIndex < 0){
                        continue;
                    }

                    newReservation(customerIndex);
                    break;

                case 2:
                    updateReservationByReceptionist();
                    break;

                case 3:
                    cancelReservationByReceptionist();
                    break;

                case 4:
                    reservationsReport();
                case 5:

                    break;
                default:
                    System.out.println("Please enter a valid choice");
            }

        }while(choice != 5);
    }

    // Get the receptionist login information and return the index
    public static int receptionistLogin(){
        int index = -1;

        System.out.println();
        System.out.println("===================== Receptionist Login ====================");

        // Get the phone number from the receptionist to search
        System.out.print("Enter the phone number: ");
        String phoneNumber = input.next();

        // Get the number of receptionists
        int numOfReceptionists = receptionists.size();

        // Search for the receptionist by the phone number and return the index
        for(int i = 0; i < numOfReceptionists; i++){
            //Check for the phone number
            if(receptionists.get(i).getPhoneNumber().equals(phoneNumber)){

                // Get the password from the user
                System.out.print("Enter the password: ");
                String password = input.next();

                // Check for the password
                if(receptionists.get(i).getPassword().equals(password)){
                    index = i;
                    return index;
                }
            }
        }

        // If no match return -1
        return index;
    }


    // Update a reservation by the customer
    public static void updateReservationByReceptionist(){

        // Print all current reservations and get the indexes of them
        ArrayList<Integer> indexesOfCurrent = getCurrentReservations();

        // Check if there are no reservations
        if(indexesOfCurrent.isEmpty()){
            System.out.println("Sorry there are no current reservations");
            return;
        }

        System.out.println("-------------------------------------------------------------");

        // Get the reservation ID to search for it
        System.out.print("Enter the reservation ID: ");
        String reservationID = input.next();

        boolean existReservation = false;

        for(int i : indexesOfCurrent){
            // If the reservation is found
            if(reservationID.equals(reservations.get(i).getReservationID())){
                existReservation = true;

                // Get new dates
                System.out.println("--- Please enter new dates to update the reservation ---\n");
                getDates(reservations.get(i));

                // Display success message
                System.out.println("Reservation updated successfully\n");

                // Print the reservation after the update
                System.out.println("--------------------- After the update ----------------------");
                Reservation.printHeader();
                System.out.println(reservations.get(i).toString());
            }
        }

        // If the reservation not found
        if(!existReservation){
            System.out.println("Reservation not found .. Please try again");
        }
    }

    // Return all valid (current) reservations
    public static ArrayList<Integer> getCurrentReservations(){
        // Get the number of reservations
        int numOfReservations = reservations.size();

        ArrayList<Integer> indexesOfCurrent = new ArrayList<>();

        // Print all current reservations and add the indexes of them to [indexesOfCurrent]
        for(int i = 0; i < numOfReservations; i++){
            if(reservations.get(i).isValid()){
                indexesOfCurrent.add(i);
                Reservation.printHeader();
                System.out.println(reservations.get(i).toString());
            }
        }

        return indexesOfCurrent;
    }

    // Cancel a reservation by a customer
    public static void cancelReservationByReceptionist(){

        // Print all current reservations and get the indexes of them
        ArrayList<Integer> indexesOfCurrent = getCurrentReservations();

        // Check if there are no reservations
        if(indexesOfCurrent.isEmpty()){
            System.out.println("Sorry there are no current reservations");
            return;
        }

        System.out.println("-------------------------------------------------------------");

        // Get the reservation ID to search
        System.out.print("Enter the reservation ID: ");
        String reservationID = input.next();

        boolean correctReservationID = false;

        for(int i = 0; i < indexesOfCurrent.size(); i++){
            // If the reservation is existed
            if(reservationID.equals(reservations.get(indexesOfCurrent.get(i)).getReservationID())){
                correctReservationID = true;

                // Make sure the user wants to cancel the reservation
                System.out.print("Are you sure you want to remove the reservation? [yes/no]: ");
                if(checkConfirm()){

                    // Set the room to available and decrement the number of reservations of the customer
                    reservations.get(i).getRoom().setAvailable(true);
                    reservations.get(i).getCustomer().cancelReservation();

                    // Remove the reservation
                    reservations.remove(indexesOfCurrent.get(i));
                    System.out.println("Reservation deleted successfully\n");
                    return;
                }
            }
        }

        // If the reservation not found
        if(!correctReservationID){
            System.out.println("Reservation not found .. Please try again\n");
        }

    }

    // Display a report of all reservations
    public static void reservationsReport(){

        int numOfReservations = reservations.size();

        // Check if there are no reservations
        if(reservations.isEmpty()){
            System.out.println("Sorry, there are no reservations");
            return;
        }

        Reservation.printHeader();
        for(int i = 0; i < numOfReservations; i++){
            System.out.print(reservations.get(i).toString());
        }
        System.out.println();
    }

    // Customer menu
    public static void customersMenu(){
        int choice;
        do{
            System.out.println();
            System.out.println("============== My Hotel System - Customers Menu =============");
            System.out.println("1- Add new customer                                         +");
            System.out.println("2- Update customer                                          +");
            System.out.println("3- Delete customer                                          +");
            System.out.println("4- Citizen customers report                                 +");
            System.out.println("5- Foreign customers report                                 +");
            System.out.println("6- Exit                                                     +");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the user
            choice = readInt("Please enter your choice: ");

            switch(choice){
                case 1:
                    newCustomer();
                    break;

                case 2:
                    updateCustomer();
                    break;

                case 3:
                    deleteCustomer();
                    break;

                case 4:
                    displayCitizenCustomers();
                    break;

                case 5:
                    displayForeignCustomers();
                    break;

                case 6:
                    break;

                default:
                    System.out.println("Please enter a valid choice");
            }

        }while(choice != 6);
    }

    // Method to search for a customer
    public static int searchCustomer(){

        // Get the phone number from the user
        System.out.print("Please enter customer's phone number: ");
        String phoneNumber = input.next();

        // Default value if there is no match
        int index = -1;

        // Search for the customer by his phone number
        int numOfCustomers = customers.size();
        for(int i = 0; i < numOfCustomers; i++){
            if(customers.get(i).getPhoneNumber().equals(phoneNumber)){
                index = i;
                break;
            }
        }

        return index;
    }

    // Method to update the customer by the receptionist
    public static void updateCustomer(){

        int customerIndex = searchCustomer();

        // Check if the customer is existed
        if(customerIndex < 0){
            System.out.println("Customer not found .. Please try again");
            return;
        }

        int numOfCustomers = customers.size();
        int choice;
        do{
            System.out.println();
            System.out.println("============== My Hotel System - Update Customer ============");

            // Print the appropriate header
            if(customers.get(customerIndex) instanceof ForeignCustomer){
                ForeignCustomer.printHeader();
            }
            else{
                Customer.printHeader();
            }

            // Print customer's information
            System.out.println(customers.get(customerIndex).toString());

            System.out.println("-------------------------------------------------------------");
            System.out.println("1- Update phone number                                      +");
            System.out.println("2- Update password                                          +");
            System.out.println("3- Update name                                              +");
            System.out.println("4- Exit                                                     +");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the user
            choice = readInt("Please enter your choice: ");

            switch(choice){

                // Update the phone number of customer
                case 1:
                {
                    System.out.print("Enter the new phone number: ");
                    String newPhoneNumber = input.next();

                    // Check if the phone number is already exists
                    boolean isExist = false;
                    for(int i = 0; i < numOfCustomers; i++){
                        if((newPhoneNumber.equals(customers.get(i).getPhoneNumber())) && (i != customerIndex)){
                            isExist = true;
                            System.out.println("Sorry, The phone number is already exists");
                            break;
                        }
                    }

                    // Go back to update customer menu if the phone number is exist
                    if(isExist){
                        continue;
                    }

                    // Update the phone number and print it
                    customers.get(customerIndex).setPhoneNumber(newPhoneNumber);
                    System.out.println("customer phone number updated successfully");
                    System.out.println("New number: " + customers.get(customerIndex).getPhoneNumber() + "\n");

                    break;
                }

                // Update the password
                case 2:
                {
                    // Get the new password
                    System.out.print("Enter the new password: ");
                    customers.get(customerIndex).setPassword(input.next());

                    // Success message
                    System.out.println("customer password updated successfully");
                    System.out.println("New password: " + customers.get(customerIndex).getPassword() + "\n");

                    break;
                }

                // Update customer's name
                case 3:
                {
                    // Get the full name and update it
                    System.out.print("Enter the new first name: ");
                    String firstName = input.next();
                    customers.get(customerIndex).setFirstName(firstName);

                    System.out.print("Enter the new last name: ");
                    String lastName = input.next();
                    customers.get(customerIndex).setLastName(lastName);

                    // Success message
                    System.out.println("customer name updated successfully");
                    System.out.println("New password: " + customers.get(customerIndex).printFullName() + "\n");
                }

                case 4:
                    break;

                default:
                    System.out.println("Please enter a valid choice");
            }
        }while(choice != 4);
    }

    // Method to delete a customer
    public static void deleteCustomer(){

        // Search for a specific customer by phone number
        int customerIndex = searchCustomer();

        // Check if there is a match
        if(customerIndex < 0){
            System.out.println("Customer not found .. Please try again");
            return;
        }

        // Print the information of customer before delete it

        // If the customer is resident
        if(customers.get(customerIndex) instanceof ForeignCustomer){
            ForeignCustomer.printHeader();
        }

        // If the customer is citizen
        else{
            Customer.printHeader();
            System.out.println(customers.get(customerIndex).toString());
        }

        // Make sure the user wants to delete the customer
        System.out.print("are you sure you want to delete the customer? [yes/no]: ");
        if(checkConfirm()){
            customers.remove(customerIndex);
            System.out.println("Customer deleted successfully");
        }

    }

    // Display all citizen customers
    public static void displayCitizenCustomers(){

        int numOfCustomers = customers.size();

        // Check if there are no customers
        if(customers.isEmpty()){
            System.out.println("Sorry, there are no customers");
            return;
        }

        System.out.println();
        System.out.println("============= My Hotel System - Citizen Customers ===========");
        Customer.printHeader();

        int numOfCitizens = 0;

        // Print all citizens and count them
        for(int i = 0; i < numOfCustomers; i++){
            if(!(customers.get(i) instanceof ForeignCustomer)){
                System.out.print(customers.get(i).toString());
                numOfCitizens++;
            }
        }

        // If there are no citizens
        if(numOfCitizens == 0){
            System.out.println("Sorry .. there are no citizen customers");
        }
    }

    // Display all residents customers
    public static void displayForeignCustomers(){

        int numOfCustomers = customers.size();

        // Check if there are no customers
        if(customers.isEmpty()){
            System.out.println("Sorry, there are no customers");
            return;
        }

        System.out.println();
        System.out.println("============ My Hotel System - Foreign Customers ============");
        ForeignCustomer.printHeader();

        // Print all residents and count them
        int numOfResidents = 0;
        for(int i = 0; i < numOfCustomers; i++){
            if((customers.get(i) instanceof ForeignCustomer)){
                System.out.print(customers.get(i).toString());
                numOfResidents++;
            }
        }

        // If there are no residents
        if(numOfResidents == 0){
            System.out.println("Sorry .. there are no foreign customers");
        }

    }

    // Rooms menu
    public static void roomsMenu(){
        int choice;
        do{
            System.out.println();
            System.out.println("================ My Hotel System - Rooms Menu ===============");
            System.out.println("1- Add new room                                             +");
            System.out.println("2- Update room                                              +");
            System.out.println("3- Delete room                                              +");
            System.out.println("4- Print all rooms                                          +");
            System.out.println("5- Exit                                                     +");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the user
            choice = readInt("Please enter your choice: ");

            switch(choice){
                case 1:
                    newRoom();
                    break;

                case 2:
                    updateRoom();
                    break;

                case 3:
                    deleteRoom();
                    break;

                case 4:
                    roomsReport();
                    break;

                case 5:
                    break;

                default:
                    System.out.println("Please enter a valid choice");
            }

        }while(choice != 5);
    }

    // Method to add a new room by the receptionist
    public static void newRoom() {
        System.out.println();

        int roomType;
        do{
            System.out.println("================ My Hotel System - New Room =================");
            System.out.println("1- Normal room                                              +");
            System.out.println("2- Meeting room                                             +");
            System.out.println("3- Go back                                                  +");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the user
            roomType = readInt("Please enter your choice: ");

            if(roomType == 3){
                return;
            }
        }while(roomType != 1 && roomType != 2);

        // Get the room number from the user
        int roomNumber = readInt("Enter room number: ");

        // Get the number of rooms
        int numOfRooms = rooms.size();

        // Check if the room number is already exists
        for(int i = 0; i < numOfRooms; i++){
            if(roomNumber == rooms.get(i).getRoomNumber()){
                System.out.println("Room number is already exists");
                return;
            }
        }

        // Get the room price from the user
        double roomPrice = readDouble("Enter the price: ");

        // Get the number of beds or the capacity (Depends on the room type)

        // Normal Room
        if(roomType == 1){
            int numOfBeds = readInt("Enter the number of beds: ");

            rooms.add(new NormalRoom(roomNumber, roomPrice, numOfBeds));

            // Print the new room information
            System.out.println("============== My Hotel System - Room information ===========");
            NormalRoom.printHeader();
            System.out.println(rooms.get(rooms.size()-1).toString());
        }

        // Meeting room
        else if(roomType == 2){
            int capacity = readInt("Enter the capacity (how many people?): ");

            rooms.add(new MeetingRoom(roomNumber, roomPrice, capacity));

            // Print the new room information
            System.out.println("============== My Hotel System - Room information ===========");
            MeetingRoom.printHeader();
            System.out.println(rooms.get(rooms.size()-1).toString());

        }

        // Success message
        System.out.println("-------------+ Room has been added successfully +------------");
    }

    // Method to update a room by the receptionist
    public static void updateRoom(){

        System.out.println();
        System.out.println("-------------------------------------------------------------");

        // Get the room number from the user to search for it
        int roomNum = readInt("Enter the room number: ");

        // Get the number of rooms
        int numOfRooms = rooms.size();

        // Variable to store the index of the room
        int roomIndex = -1;

        // Search for the room
        for(int i = 0; i < numOfRooms; i++){
            if(rooms.get(i).getRoomNumber() == (roomNum)){
                roomIndex = i;
                break;
            }
        }

        // If the room is not found
        if(roomIndex < 0){
            System.out.println("room not found .. Please try again");
            return;
        }

        boolean isItNormalRoom = rooms.get(roomIndex) instanceof NormalRoom;
        int choice;
        do{
            System.out.println();
            System.out.println("=============== My Hotel System - After updating ============");

            // Print the appropriate header
            if(isItNormalRoom){
                NormalRoom.printHeader();
            }
            else{
               MeetingRoom.printHeader();
            }

            // Print the information of room before update it
            System.out.println(rooms.get(roomIndex).toString());

            System.out.println("-------------------------------------------------------------");
            System.out.println("1- Update price                                              ");
            System.out.println("2- Update room number                                        ");
            System.out.println("3- Update " + ((isItNormalRoom)? "Number of beds": "Capacity"));
            System.out.println("4- Exit                                                      ");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the user
            choice = readInt("Please enter your choice: ");

            switch(choice){
                // Update price
                case 1:
                {
                    rooms.get(roomIndex).setPrice(readDouble("Enter the new price: "));
                    System.out.println("room price updated successfully");
                    System.out.println("New price: " + rooms.get(roomIndex).getPrice() + "\n");
                    break;
                }

                // Update room number
                case 2:
                {
                    rooms.get(roomIndex).setRoomNumber(readInt("Enter the new room number: "));
                    System.out.println("room number updated successfully");
                    System.out.println("New room number: " + rooms.get(roomIndex).getRoomNumber() + "\n");
                    break;
                }

                // Update number of beds/capacity
                case 3:
                {
                    // For normal rooms
                    if(isItNormalRoom){
                        ((NormalRoom) rooms.get(roomIndex)).setNumOfBeds(readInt("Enter the new number of beds: "));
                        System.out.println("number of beds updated successfully");
                        System.out.println("New number of beds: " + ((NormalRoom) rooms.get(roomIndex)).getNumOfBeds() + "\n");
                    }

                    // For meeting rooms
                    else {
                        ((MeetingRoom) rooms.get(roomIndex)).setCapacity(readInt("Enter the new capacity: "));
                        System.out.println("Capacity updated successfully");
                        System.out.println("New capacity: " + ((MeetingRoom) rooms.get(roomIndex)).getCapacity() + "\n");
                    }
                }

                case 4:
                    break;

                default:
                    System.out.println("Please enter a valid choice");
            }
        }while(choice != 4);
    }

    // Delete a room by receptionist
    public static void deleteRoom(){

        // Get the room number from the user to search for it
        int roomNum = readInt("Enter the room number: ");

        // Get the number of rooms
        int numOfRooms = rooms.size();

        // No match value
        int roomIndex = -1;

        // Search for the room
        for(int i = 0; i < numOfRooms; i++){
            if(rooms.get(i).getRoomNumber() == roomNum){
                roomIndex = i;
                break;
            }
        }

        // If the room is not found
        if(roomIndex < 0){
            System.out.println("Room not found .. Please try again");
            return;
        }

        boolean isItNormalRoom = rooms.get(roomIndex) instanceof NormalRoom;

        // Print the appropriate header
        if(isItNormalRoom){
            NormalRoom.printHeader();
        }
        else{
            MeetingRoom.printHeader();
        }

        // Print the information of room before delete it
        System.out.println(rooms.get(roomIndex).toString());

        // Make sure the user wants to delete the room
        System.out.print("are you sure you want to delete the room? [yes/no]: ");
        if(checkConfirm()){
            rooms.remove(roomIndex);
            System.out.println("Room deleted successfully");
        }

    }

    // Display rooms report
    public static void roomsReport(){

        // Get the number of rooms
        int numOfRooms = rooms.size();

        // Check if there are no rooms
        if(rooms.isEmpty()){
            System.out.println("Sorry, there are no rooms");
            return;
        }

        int choice;
        do{
            System.out.println("====================== Choose Room Type =====================");
            System.out.println("1- Normal Rooms");
            System.out.println("2- Meeting Rooms");
            System.out.println("3- Go back");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the receptionist
            choice = readInt("Please enter your choice: ");

            System.out.println();

            switch(choice){

                // Normal rooms report
                case 1:
                {
                    System.out.println("==================== Normal Rooms Report ====================");
                    NormalRoom.printHeader();

                    // Print all normal rooms
                    for(int i = 0; i < numOfRooms; i++){
                        if(rooms.get(i) instanceof NormalRoom){
                            System.out.println(rooms.get(i).toString());
                        }
                    }
                    break;
                }

                // Meeting rooms report
                case 2:
                {
                    System.out.println("==================== Meeting Rooms Report ===================");
                    MeetingRoom.printHeader();

                    // Print all meeting rooms
                    for(int i = 0; i < numOfRooms; i++){
                        if(rooms.get(i) instanceof MeetingRoom){
                            System.out.println(rooms.get(i).toString());
                        }
                    }
                }

                // Go back
                case 3:
                    return;

                default:
                    System.out.println("Please enter a valid choice");
            }
        }while(choice != 3);

    }

    // Control panel system
    // default username: admin, default password: admin
    public static void controlPanel(){
        System.out.println();

        System.out.println("=================== Control Panel - Login ===================");

        // Get the username
        System.out.print("Enter the username: ");
        String username = input.next();

        // Get the password
        System.out.print("Enter the password: ");
        String password = input.next();

        // Check if the information is correct
        if(!(username.equals(adminUser) && password.equals(adminPassword))){
            System.out.println("The username or password you entered isn’t correct.\n");
            return;
        }

        int choice;
        do{
            System.out.println();
            System.out.println("======================= Control Panel =======================");
            System.out.println("1- Add new receptionist                                     +");
            System.out.println("2- Update exist receptionist                                +");
            System.out.println("3- Delete receptionist                                      +");
            System.out.println("4- Receptionists report                                     +");
            System.out.println("5- Update the control panel                                 +");
            System.out.println("6- Exit                                                     +");
            System.out.println("-------------------------------------------------------------");

            // Get the choice
            choice = readInt("Please enter your choice: ");

            switch(choice){
                case 1:
                {
                    Receptionist newReceptionist = receptionistInfo();

                    // If the receptionist wasn't successfully added go back to the menu
                    if(newReceptionist == null){
                        continue;
                    }

                    receptionists.add(newReceptionist);
                    System.out.println("---------+ Receptionist has been added successfully +--------");
                    break;
                }

                // Update exist receptionist
                case 2:
                {
                    // Check if there are no receptionists
                    if(receptionists.isEmpty()){
                        System.out.println("Sorry, there are no receptionists");
                        continue;
                    }

                    // Get the phone number of receptionist
                    System.out.print("Enter receptionist's phone number: ");
                    String phoneNumber = input.next();

                    // Search for the receptionist
                    int numOfReceptionists = receptionists.size();
                    boolean isExist = false;
                    for(int i = 0; i < numOfReceptionists; i++){
                        if(receptionists.get(i).getPhoneNumber().equals(phoneNumber)){
                            isExist = true;

                            // Display receptionist's name before update it
                            System.out.println("--------- Update receptionist: " + receptionists.get(i).printFullName() + "---------");

                            // Store the receptionist and remove it from the arraylist
                            Receptionist buffer = receptionists.get(i);
                            receptionists.set(i, null);

                            // Get the new information
                            Receptionist updatedReceptionist = receptionistInfo();
                            if(updatedReceptionist == null){
                                // Bring back the previous information if it is incorrect and go back.
                                receptionists.set(i, buffer);
                                continue;
                            }

                            // Replace the old information
                            receptionists.set(i, updatedReceptionist);
                            System.out.println("--------- Receptionist has been updated successfully --------");
                            break;
                        }
                    }

                    // If the receptionist not found
                    if(!isExist){
                        System.out.println("Receptionist not found .. Please try again");
                    }
                    break;
                }

                case 3:
                {
                    deleteReceptionist();
                    break;
                }

                case 4:
                {
                    int numOfReceptionists = receptionists.size();

                    // Check if there are no receptionists
                    if(receptionists.isEmpty()){
                        System.out.println("Sorry, there are no receptionists");
                        continue;
                    }
                    System.out.println();
                    System.out.println("=========== My Hotel System - Receptionists report ==========");
                    Receptionist.printHeader();

                    // Print all receptionists
                    for(int i = 0; i < numOfReceptionists; i++){
                        System.out.println(receptionists.get(i).toString());
                    }
                    System.out.println("-------------------------------------------------------------------");
                    break;
                }

                case 5:
                {
                    // Get the new username
                    System.out.print("Please enter the new username: ");
                    adminUser = input.next();

                    // Get the new password
                    System.out.print("Please enter the new password: ");
                    adminPassword = input.next();

                    System.out.println("----------- Login information updated successfully ----------");
                    break;
                }

                case 6:
                    break;

                default:
                    System.out.println("Please enter a valid choice");

            }
        }while(choice != 6);

    }

    // Ask the user for the information of a receptionist
    public static Receptionist receptionistInfo(){
        // Get the information of new receptionist.

        // Get the number of receptionists
        int numOfReceptionists = receptionists.size();

        // Check if the ID is already exists
        System.out.print("Enter the national ID: ");
        String ID = input.next();
        for(int i = 0; i < numOfReceptionists; i++){
            if(receptionists.get(i) == null){
                continue;
            }

            if(ID.equals(receptionists.get(i).getID())){
                System.out.println("The ID is already exists");
                return null;
            }
        }

        // Get the full name
        System.out.print("Enter the first name: ");
        String firstName = input.next();
        System.out.print("Enter the last name: ");
        String lastName = input.next();

        // Check if the phone number is already exists
        System.out.print("Enter the phone number: ");
        String phoneNumber = input.next();
        for(int i = 0; i < numOfReceptionists; i++){
            if(receptionists.get(i) == null){
                continue;
            }

            if(phoneNumber.equals(receptionists.get(i).getPhoneNumber())){
                System.out.println("The phone number is already exists");
                return null;
            }
        }

        // Get the password
        System.out.print("Enter the password: ");
        String receptionistPassword = input.next();

        double salary = readDouble("Enter the salary: ");

        return (new Receptionist(ID, firstName, lastName, phoneNumber, receptionistPassword, salary));
    }

    // Method to delete a receptionist from the control panel
    public static void deleteReceptionist(){

        // Get the receptionist's phone number
        System.out.print("Enter receptionist's phone number: ");
        String phoneNumber = input.next();

        // No match value
        int receptionistIndex = -1;

        // Search for the receptionist
        int numOfReceptionists = receptionists.size();
        for(int i = 0; i < numOfReceptionists; i++){
            if(receptionists.get(i).getPhoneNumber().equals(phoneNumber)){
                receptionistIndex = i;
                break;
            }
        }

        // If the receptionist not found
        if(receptionistIndex < 0){
            System.out.println("Receptionist not found .. Please try again");
            return;
        }

        // Print the information of Receptionist before delete it
        Receptionist.printHeader();
        System.out.println(receptionists.get(receptionistIndex).toString());


        // Make sure the user wants to delete the receptionist
        System.out.print("are you sure you want to delete the receptionist? [yes/no]: ");
        if(checkConfirm()){
            receptionists.remove(receptionistIndex);
            System.out.println("Receptionist deleted successfully");
        }

    }

    // Read integer from the user (with validation)
    public static int readInt(String prompt){
        int num = 0;
        while (true) {
            try {
                System.out.print(prompt);
                num = Integer.parseInt(input.next());
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid number\n");
            }
        }

        return num;
    }

    // Read double from the user (with validation)
    public static double readDouble(String prompt){
        double num = 0;
        while (true) {
            try {
                System.out.print(prompt);
                num = Double.parseDouble(input.next());
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid number\n");
            }
        }

        return num;
    }

    // Update the availability of all rooms depending on today's date by going through all reservations.
    public static void refreshAvailability(){
        int numOfReservations = reservations.size();
        for(int i = 0; i < numOfReservations; i++){
            reservations.get(i).automaticUpdateAvailability();
        }
    }
} // End of application class
