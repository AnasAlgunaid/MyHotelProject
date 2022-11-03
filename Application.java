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

    static String adminUser = "admin";
    static String adminPassword = "admin";

    public static void main(String[] args) {

        rooms.add(new MeetingRoom(113, 250, 12));
        rooms.add(new MeetingRoom(114, 300, 15));
        rooms.add(new NormalRoom(118, 400, 2));
        rooms.add(new NormalRoom(119, 600, 4));
        rooms.add(new MeetingRoom(121, 700, 4));
        rooms.add(new NormalRoom(122, 800, 4));
        receptionists.add(new Receptionist("Anas", "Aljunaid", "0533039772", "pos123", 5000));

        int choice = 0;

        // Display main menu until the user choose to exit the system
        do{

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

        // CHECK_AND_TRY
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
                case 1:
                {
                    customerIndex = customerLogin();
                    if(customerIndex < 0){
                        System.out.println("Customer not found .. Please try again\n");
                        break;
                    }
                    return customerIndex;
                }
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

        return -1;
    }

    public static void customersSystem(){
        int customerIndex = customerLoginMenu();

        if(customerIndex < 0){
            return;
        }

        int choice = 0;
        do{
            System.out.println();

            // Print welcome message
            System.out.println("\t\t\t\t\tWelcome " + customers.get(customerIndex).printFullName());
            System.out.println("-------------------------------------------------------------");

            System.out.println("============== My Hotel System - Customer Menu ==============");
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
                case 5:
                    break;
                default:
                    System.out.println("Please enter a valid choice");

            }
        }while(choice != 5);
    }

    public static void newReservation(int customerIndex){
        // If there are no rooms
        if(rooms.size() == 0){
            System.out.println("Sorry, there are no rooms");
            return;
        }

        int indexOfSelectedRoom = -1;

        System.out.println();
        System.out.println("============= My Hotel System - New Reservation =============");

        indexOfSelectedRoom = chooseRoom();

        // If the user choose to go back
        if(indexOfSelectedRoom < 0){
            return;
        }

        // Create the reservation
        addReservation(customers.get(customerIndex), rooms.get(indexOfSelectedRoom));

    }

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
        }while(choice < 0 && choice > 3);

        int selectedRoom = searchRoom();

        return selectedRoom;
    }

    // Method to create new reservation
    public static void addReservation(User customer, Room room){
        reservations.add(new Reservation(((Customer) customer), room));

        // Store the index of the new reservation
        int indexOfNewReservation = reservations.size()-1;

        boolean isItCorrectDate = false;

        System.out.println("================== Step [2/2]: Enter dates ==================");

        // Get the check-in and check-out date
        getDates(reservations.get(indexOfNewReservation));


        // TODO: 10/29/2022
        // Add a reservation to the customer
        ((Customer) customer).addReservation();

        // Update the availability
        reservations.get(indexOfNewReservation).automaticUpdateAvailability();

        // Print the bill
        System.out.println("================ My Hotel System - The bill =================");
        Reservation.printHeader();
        System.out.println(reservations.get(indexOfNewReservation).toString());
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println("Reservation created successfully");
    }

    // Method to Create a new account for a customer
    public static boolean newCustomer(){
        int isCitizen;
        do{
            System.out.println();
            System.out.println("=============== My Hotel System - New Account ===============");

            System.out.println("1- Citizen                                                  +");
            System.out.println("2- Resident                                                 +");
            System.out.println("3- Go back                                                  +");
            System.out.println("-------------------------------------------------------------");
            // Get the choice from the user
            isCitizen = readInt("Please enter your choice: ");

            // Go back
            if(isCitizen == 3){
                return false;
            }
        }while(isCitizen != 1 && isCitizen != 2);

        // Get the personal information from the user.
        System.out.print("Enter the first name: ");
        String firstName = input.next();
        System.out.print("Enter the last name: ");
        String lastName = input.next();
        System.out.print("Enter the phone number: ");
        String phoneNumber = input.next();

        // Check if the phone number is already exists
        int numOfCustomers = customers.size();
        for(int i = 0; i < numOfCustomers; i++){
            if(phoneNumber.equals(customers.get(i).getPhoneNumber())){
                System.out.println("The phone number is already exists .. You can sign in");
                return false;
            }
        }

        System.out.print("Enter the password: ");
        String password = input.next();

        // Citizen customer
        if(isCitizen == 1){
            // Store the customer
            customers.add(new Customer(firstName, lastName, phoneNumber, password));

            // Print the new customer information
            System.out.println("============ My Hotel System - Account information ==========");
            Customer.printHeader();
            System.out.println(customers.get(customers.size()-1).toString());

        }

        // Resident customer
        else if(isCitizen == 2){
            System.out.print("Enter the country: ");
            String country = input.next();
            System.out.print("Enter the passport number: ");
            String passport = input.next();

            // Store the customer
            customers.add(new ForeignCustomer(firstName, lastName, phoneNumber, password, passport, country));

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

        if(index < 0){
            return -1;
        }

        // Check for the password
            System.out.print("Enter the password: ");
            String password = input.next();
            if(customers.get(index).getPassword().equals(password)){
                return index;
            }
            else{
                return -1;
            }
    }



    public static ArrayList<Integer> getCurrentReservations(int index){
        int numOfReservations = reservations.size();
        String customerPhoneNumber = customers.get(index).getPhoneNumber();

        ArrayList<Integer> indexesOfRelated = new ArrayList<>();

        boolean isThereReservation = false;

        // Get the related and current reservations of the customer
        for (int i = 0; i < numOfReservations; i++) {

            // Check if the reservation related to the customer
            boolean relatedReservation = reservations.get(i).getCustomer().getPhoneNumber().equals(customerPhoneNumber);

            // Check if the reservation not in the past
            if (relatedReservation) {
                if (reservations.get(i).isValid()) {
                    isThereReservation = true;
                    indexesOfRelated.add(i);
                }
            }
        }

        return indexesOfRelated;
    }

    // ------------------------- Update exist reservation -------------------------

    public static void getDates(Reservation reservation){

        boolean isItCorrectDate = false;

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
                continue;
            }
        }while(!isItCorrectDate);

    }

    // TODO: 10/29/2022 Can we merge it? 
    public static void updateReservation(int customerIndex){

        // Get the indexes of current reservations
        ArrayList<Integer> currentReservationsIndexes = getCurrentReservations(customerIndex);

        int numOfCurrentReservations = currentReservationsIndexes.size();

        // Check if there are reservations
        if(numOfCurrentReservations < 1){
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

    // ------------------------- Cancel reservation -------------------------
    public static void cancelReservation(int customerIndex){

        int numOfReservations = reservations.size();
        String customerPhoneNumber = customers.get(customerIndex).getPhoneNumber();

        ArrayList<Integer> currentReservationsIndexes = getCurrentReservations(customerIndex);

        int numOfCurrentReservations = currentReservationsIndexes.size();

        // Check if there are reservations
        if(numOfCurrentReservations < 1){
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
                System.out.print("are you sure you want to cancel the reservation? [yes/no]: ");
                if(checkConfirm()){


                    // Remove the reservation
                    reservations.get(i).getRoom().setAvailable(true);
                    reservations.get(i).getCustomer().cancelReservation();
                    reservations.remove(i);
                    System.out.println("Reservation deleted successfully\n");
                    return;
                }
            }
        }
    }

    // ------------------------ Old reservations ------------------------
    public static void displayReservationsOfCustomer(int customerIndex){

        int numOfReservations = reservations.size();
        String customerPhoneNumber = customers.get(customerIndex).getPhoneNumber();

        boolean isThereReservations = false;
        // Print the related reservations of the customer
        for(int i = 0; i < numOfReservations; i++){
            boolean relatedReservation = reservations.get(i).getCustomer().getPhoneNumber().equals(customerPhoneNumber);
            if(relatedReservation){
                isThereReservations = true;
                Reservation.printHeader();
                System.out.println(reservations.get(i).toString());
            }
        }
        if(!isThereReservations){
            System.out.println("Sorry you don't have any reservations");
        }
    }

    // -------------------
    public static boolean checkConfirm(){
        String confirm = input.next();
        if(confirm.equals("yes")){
            return true;
        }
        else{
            return false;
        }
    }


    // =================================== Methods ============================

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

    // Method to search for a room
    public static int searchRoom(){
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

    public static LocalDate inputDate(String prompt){
        LocalDate enteredDate = null;

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

    // ========================================================================
    public static void receptionistSystem(){
        int index = receptionistLogin();
        if(index < 0){
            System.out.println("Receptionist not found .. Please try again");
            return;
        }

        else{
            System.out.println("-------------------------------------------------------------");
            System.out.println("\t\t\t\t\tWelcome " + receptionists.get(index).printFullName());
            System.out.println("-------------------------------------------------------------");

        }

        int choice = 0;
        do{
            refreshAvailability();

            System.out.println();
            System.out.println("============= My Hotel System - Reception Menu ==============");
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

public static void reservationsMenu(){
    int choice = 0;
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



    public static void updateReservationByReceptionist(){

        // Print all current reservations and get the indexes of them
        ArrayList<Integer> indexesOfCurrent = getCurrentReservations();

        // Get the number of current reservations
        int numOfCurrentReservations = indexesOfCurrent.size();

        // Check if there are reservations
        if(numOfCurrentReservations < 1){
            System.out.println("Sorry there are no current reservations");
            return;
        }

        System.out.println("-------------------------------------------------------------");

        // Get the reservation ID to search for it
        System.out.print("Enter the reservation ID: ");
        String reservationID = input.next();

        boolean existReservation = false;
        for(int i : indexesOfCurrent){
            if(reservationID.equals(reservations.get(i).getReservationID())){
                existReservation = true;

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

        if(!existReservation){
            System.out.println("Reservation not found .. Please try again");
        }
    }
    public static ArrayList<Integer> getCurrentReservations(){
        int numOfReservations = reservations.size();

        ArrayList<Integer> indexesOfCurrent = new ArrayList<>();

        // Print all current reservations
        for(int i = 0; i < numOfReservations; i++){
            if(reservations.get(i).isValid()){
                indexesOfCurrent.add(i);
                Reservation.printHeader();
                System.out.println(reservations.get(i).toString());
            }
        }

        return indexesOfCurrent;
    }

    public static void cancelReservationByReceptionist(){

        // Print all current reservations
        ArrayList<Integer> indexesOfCurrent = getCurrentReservations();

        // Get the number of current reservations
        int numOfCurrentReservations = indexesOfCurrent.size();

        // Check if there are reservations
        if(numOfCurrentReservations < 1){
            System.out.println("Sorry there are no current reservations");
            return;
        }

        System.out.println("-------------------------------------------------------------");

        // Check if the user has pending and related reservations
        System.out.print("Enter the reservation ID: ");
        String reservationID = input.next();

        boolean correctReservationID = false;

        for(int i = 0; i < indexesOfCurrent.size(); i++){

            // If the reservation is exist
            if(reservationID.equals(reservations.get(indexesOfCurrent.get(i)).getReservationID())){
                correctReservationID = true;

                System.out.print("Are you sure you want to remove the reservation? [yes/no]: ");
                if(checkConfirm()){

                    // Remove the reservation and decrement the number of reservations of the customer
                    reservations.get(i).getCustomer().cancelReservation();
                    reservations.remove(indexesOfCurrent.get(i));
                    System.out.println("Reservation deleted successfully\n");
                    return;
                }
            }
        }
        if(!correctReservationID){
            System.out.println("Invalid reservation ID .. Please try again");
        }

    }

    public static void reservationsReport(){

        int numOfReservations = reservations.size();

        if(numOfReservations == 0){
            System.out.println("Sorry, there are no reservations");
            return;
        }

        Reservation.printHeader();
        for(int i = 0; i < numOfReservations; i++){
            System.out.print(reservations.get(i).toString());
        }
        System.out.println();
    }

    public static void customersMenu(){
        int choice = 0;
        do{
            System.out.println();
            System.out.println("============== My Hotel System - Customers Menu =============");
            System.out.println("1- Add new customer                                         +");
            System.out.println("2- Update customer                                          +");
            System.out.println("3- Delete customer                                          +");
            System.out.println("4- Citizen customers report                                 +");
            System.out.println("5- Resident customers report                                +");
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
                    displayResidentCustomers();
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

    public static void updateCustomer(){

        int customerIndex = searchCustomer();

        if(customerIndex < 0){
            System.out.println("Customer not found .. Please try again");
            return;
        }

        int numOfCustomers = customers.size();
        int choice = 0;
        do{
            System.out.println();
            System.out.println("============== My Hotel System - Update Customer ============");

            // Print the information of customer before delete it
            if(customers.get(customerIndex) instanceof ForeignCustomer){
                ForeignCustomer.printHeader();
            }

            else{
                Customer.printHeader();
            }

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
                case 1:
                {
                    // TODO: 10/22/2022
                    System.out.print("Enter the new phone number: ");
                    String newPhoneNumber = input.next();

                    boolean isExist = false;
                    for(int i = 0; i < numOfCustomers; i++){
                        if((newPhoneNumber.equals(customers.get(i).getPhoneNumber())) && (i != customerIndex)){
                            isExist = true;
                            System.out.println("Sorry, The phone number is already exists");
                            break;
                        }
                    }

                    if(isExist){
                        continue;
                    }

                    customers.get(customerIndex).setPhoneNumber(newPhoneNumber);
                    System.out.println("customer phone number updated successfully");
                    System.out.println("New number: " + customers.get(customerIndex).getPhoneNumber() + "\n");

                    break;
                }

                case 2:
                {
                    System.out.print("Enter the new password: ");
                    customers.get(customerIndex).setPassword(input.next());

                    System.out.println("customer password updated successfully");
                    System.out.println("New password: " + customers.get(customerIndex).getPassword() + "\n");

                    break;
                }

                case 3:
                {
                    System.out.print("Enter the new first name: ");
                    String firstName = input.next();
                    customers.get(customerIndex).setFirstName(firstName);

                    System.out.print("Enter the new last name: ");
                    String lastName = input.next();
                    customers.get(customerIndex).setLastName(lastName);

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

    public static void deleteCustomer(){

        // Search for a specific customer by phone number
        int customerIndex = searchCustomer();

        // Check if there is a match
        if(customerIndex < 0){
            System.out.println("Customer not found .. Please try again");
            return;
        }

        // TODO: 11/1/2022 Foreign?
        // Print the information of customer before delete it
        if(customers.get(customerIndex) instanceof ForeignCustomer){
            ForeignCustomer.printHeader();
        }

        else{
            Customer.printHeader();
            System.out.println(customers.get(customerIndex).toString());
        }

        System.out.print("are you sure you want to delete the customer? [yes/no]: ");
        if(checkConfirm()){
            customers.remove(customerIndex);
            System.out.println("Customer deleted successfully");
        }

    }

    public static void displayCitizenCustomers(){

        int numOfCustomers = customers.size();

        if(numOfCustomers == 0){
            System.out.println("Sorry, there are no customers");
            return;
        }

        System.out.println();
        System.out.println("============= My Hotel System - Citizen Customers ===========");
        Customer.printHeader();

        for(int i = 0; i < numOfCustomers; i++){
            if(!(customers.get(i) instanceof ForeignCustomer)){
                System.out.print(customers.get(i).toString());
            }
        }
    }

    public static void displayResidentCustomers(){

        int numOfCustomers = customers.size();

        if(numOfCustomers == 0){
            System.out.println("Sorry, there are no customers");
            return;
        }

        System.out.println();
        System.out.println("============ My Hotel System - Resident Customers ===========");
        ForeignCustomer.printHeader();

        for(int i = 0; i < numOfCustomers; i++){
            if((customers.get(i) instanceof ForeignCustomer)){
                System.out.print(customers.get(i).toString());
            }
        }

    }

    public static void roomsMenu(){
        int choice = 0;
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
                case 5:
                    break;
                default:
                    System.out.println("Please enter a valid choice");
            }

        }while(choice != 5);
    }

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
            int capacity = readInt("Enter the capacity (how many persons?): ");

            rooms.add(new MeetingRoom(roomNumber, roomPrice, capacity));

            // Print the new room information
            System.out.println("============== My Hotel System - Room information ===========");
            MeetingRoom.printHeader();
            System.out.println(rooms.get(rooms.size()-1).toString());

        }

        // Success message
        System.out.println("-------------+ Room has been added successfully +------------");
    }

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
        int choice = 0;
        do{
            System.out.println();
            System.out.println("=============== My Hotel System - After updating ============");

            // Print the information of room before update it
            if(isItNormalRoom){
                NormalRoom.printHeader();
            }
            else{
               MeetingRoom.printHeader();
            }
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
                case 1:
                {
                    // TODO: 10/22/2022
                    rooms.get(roomIndex).setPrice(readDouble("Enter the new price: "));

                    if(true){
                        System.out.println("room price updated successfully");
                        System.out.println("New price: " + rooms.get(roomIndex).getPrice() + "\n");
                    }
                    break;
                }

                case 2:
                {
                    // TODO: 10/23/2022
                    rooms.get(roomIndex).setRoomNumber(readInt("Enter the new room number: "));
                    if(true){
                        System.out.println("room number updated successfully");
                        System.out.println("New room number: " + rooms.get(roomIndex).getRoomNumber() + "\n");
                    }
                    break;
                }

                case 3:
                {
                    if(isItNormalRoom){
                        ((NormalRoom) rooms.get(roomIndex)).setNumOfBeds(readInt("Enter the new number of beds: "));

                        // TODO: 10/28/2022
                        if(true){
                            System.out.println("number of beds updated successfully");
                            System.out.println("New number of beds: " + ((NormalRoom) rooms.get(roomIndex)).getNumOfBeds() + "\n");
                        }
                    }
                    else {
                        ((MeetingRoom) rooms.get(roomIndex)).setCapacity(readInt("Enter the new capacity: "));

                        if(true){
                            System.out.println("Capacity updated successfully");
                            System.out.println("New capacity: " + ((MeetingRoom) rooms.get(roomIndex)).getCapacity() + "\n");
                        }
                    }
                }
                case 4:
                    break;
                default:
                    System.out.println("Please enter a valid choice");
            }
        }while(choice != 4);
    }

    public static void deleteRoom(){

        // Get the room number from the user to search for it
        int roomNum = readInt("Enter the room number: ");

        // Get the number of rooms
        int numOfRooms = rooms.size();

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

        // Print the information of room before delete it
        if(isItNormalRoom){
            NormalRoom.printHeader();
        }
        else{
            MeetingRoom.printHeader();
        }

        System.out.println(rooms.get(roomIndex).toString());


        System.out.print("are you sure you want to delete the room? [yes/no]: ");
        if(checkConfirm()){
            rooms.remove(roomIndex);
            System.out.println("Room deleted successfully");
        }

    }

    public static void roomsReport(){

        // Get the number of rooms
        int numOfRooms = rooms.size();

        // Check if there are rooms
        if(numOfRooms == 0){
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
                case 3:
                    return;

                default:
                    System.out.println("Please enter a valid choice");
            }
        }while(choice != 3);

    }

    public static void controlPanel(){
        int index = -1;

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

        int choice = -1;
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
                    if(newReceptionist == null){
                        continue;
                    }

                    receptionists.add(newReceptionist);
                    System.out.println("---------+ Receptionist has been added successfully +--------");
                    break;
                }
                case 2:
                {
                    System.out.print("Enter receptionist's phone number: ");
                    String phoneNumber = input.next();

                    int numOfReceptionists = receptionists.size();
                    boolean isExist = false;
                    for(int i = 0; i < numOfReceptionists; i++){
                        if(receptionists.get(i).getPhoneNumber().equals(phoneNumber)){
                            isExist = true;
                            // Display receptionist's name before update it
                            System.out.println("--------- Update receptionist: " + receptionists.get(i).printFullName() + "---------");

                            // Update the receptionist
                            Receptionist buffer = receptionists.get(i);
                            receptionists.set(i, null);

                            Receptionist updatedReceptionist = receptionistInfo();
                            if(updatedReceptionist == null){
                                receptionists.set(i, buffer);
                                continue;
                            }

                            receptionists.set(i, updatedReceptionist);
                            System.out.println("--------- Receptionist has been updated successfully --------");
                            break;
                        }
                    }

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

                    if(numOfReceptionists == 0){
                        System.out.println("Sorry, there are no receptionists");
                        continue;
                    }
                    System.out.println();
                    System.out.println("=========== My Hotel System - Receptionists report ==========");
                    Receptionist.printHeader();

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

    public static Receptionist receptionistInfo(){
        // Get the information of new receptionist.
        System.out.print("Enter the first name: ");
        String firstName = input.next();
        System.out.print("Enter the last name: ");
        String lastName = input.next();

        // Get the number of receptionists
        int numOfReceptionists = receptionists.size();

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

        System.out.print("Enter the password: ");
        String receptionistPassword = input.next();

        double salary = readDouble("Enter the salary: ");

        return (new Receptionist(firstName, lastName, phoneNumber, receptionistPassword, salary));
    }

    public static void deleteReceptionist(){

        System.out.print("Enter receptionist's phone number: ");
        String phoneNumber = input.next();

        int receptionistIndex = -1;

        int numOfReceptionists = receptionists.size();
        for(int i = 0; i < numOfReceptionists; i++){
            if(receptionists.get(i).getPhoneNumber().equals(phoneNumber)){
                receptionistIndex = i;
                break;
            }
        }

        if(receptionistIndex < 0){
            System.out.println("Receptionist not found .. Please try again");
            return;
        }

        // Print the information of Receptionist before delete it
        Receptionist.printHeader();
        System.out.println(receptionists.get(receptionistIndex).toString());


        System.out.print("are you sure you want to delete the receptionist? [yes/no]: ");
        if(checkConfirm()){
            receptionists.remove(receptionistIndex);
            System.out.println("Receptionist deleted successfully");
        }

    }

    public static int readInt(String prompt){
        int num = 0;
        while (true) {
            try {
                System.out.print(prompt);
                num = Integer.parseInt(input.next());
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid number\n");
                continue;
            }
        }

        return num;
    }

    public static double readDouble(String prompt){
        double num = 0;
        while (true) {
            try {
                System.out.print(prompt);
                num = Double.parseDouble(input.next());
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid number\n");
                continue;
            }
        }

        return num;
    }

    public static void refreshAvailability(){
        int numOfReservations = reservations.size();
        for(int i = 0; i < numOfReservations; i++){
            reservations.get(i).automaticUpdateAvailability();
        }
    }
} // End of application class
