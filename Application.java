import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    // Declare a scanner object
    static Scanner input = new Scanner(System.in);

    // Declare an ArrayList of customers
    static ArrayList<User> customers = new ArrayList<>();

    // Declare an ArrayList of rooms
    static ArrayList<Room> rooms = new ArrayList<>();

    // Declare an ArrayList of reservations
    static ArrayList<Reservation> reservations = new ArrayList<>();

    // Declare an ArrayList of receptionist
    static ArrayList<Receptionist> receptionists = new ArrayList<>();

    public static void main(String[] args) {

        rooms.add(new MeetingRoom(113, 250, 12));
        rooms.add(new MeetingRoom(114, 300, 15));
        rooms.add(new NormalRoom(118, 400, 2));
        rooms.add(new NormalRoom(119, 600, 4));
        receptionists.add(new Receptionist("Anas", "Aljunaid", "0533039772", "pos123", 5000));

        int choice = 0;

        // Display main menu until the user choose to exit the system
        do{
            //Get the choice from the user
            choice = mainMenu();

            switch(choice){
                case 1:
                    customerMenu();
                    break;
                case 2:
                    receptionistMenu();
                    break;
                case 3:
                    System.out.println("Thank your for using the program .. GoodBey");
                    break;
                default:
                    System.out.println("Please enter a valid choice");
            }

        }while(choice != 3);
    }

    public static int mainMenu(){
        System.out.println();
        System.out.println("=============================================================");
        System.out.println("====================== My Hotel System ======================");
        System.out.println("1- Customer system                                          +");
        System.out.println("2- Receptionist system                                      +");
        System.out.println("3- Exit                                                     +");
        System.out.println("-------------------------------------------------------------");
        System.out.print("Please enter your choice: ");

        // CHECK_AND_TRY
        return (input.nextInt());
    }
    public static void customerMenu(){
        int choice = 0;
        do{
            System.out.println();
            System.out.println("=============================================================");
            System.out.println("============== My Hotel System - Customer Menu ==============");
            System.out.println("1- New reservation                                          +");
            System.out.println("2- Update exist reservation                                 +");
            System.out.println("3- Cancel reservation                                       +");
            System.out.println("4- Old reservations                                        +");
            System.out.println("5- Exit                                                     +");
            System.out.println("-------------------------------------------------------------");
            System.out.print("Please enter your choice: ");

            // Get the choice from the user
            choice = input.nextInt();

            switch(choice){
                case 1:
                    newReservation();
                    break;
                case 2:
                    updateReservation();
                    break;
                case 3:
                    cancelReservation();
                    break;
                case 4:
                    displayOldReservations();
                case 5:
                    break;
                default:
                    System.out.println("Please enter a valid choice");

            }
        }while(choice != 5);
    }

    public static void newReservation(){
        // If there are no rooms
        if(rooms.size() == 0){
            System.out.println("Sorry, there are no rooms");
            return;
        }


        int indexOfSelectedRoom = -1;
        int indexOfCustomer = -1;
        do{
        System.out.println();
        System.out.println("============= My Hotel System - New Reservation =============");
        System.out.println("1- New customer                                             +");
        System.out.println("2- Exist customer                                           +");
        System.out.println("3- Go Back                                                  +");
        System.out.println("-------------------------------------------------------------");

        //TRY_AND_CHECK
        // Get the choice from the user
        System.out.print("Please enter your choice: ");
        int choice = input.nextInt();

        switch(choice){
            case 1:
                newCustomer();
                indexOfCustomer = customers.size() -1;
                indexOfSelectedRoom = chooseRoom();
                break;

            case 2:
            {
                int searchResult = customerLogin();
                if(searchResult < 0){
                    System.out.println("Sorry, Customer not found");
                    return;
                }
                else{
                    indexOfCustomer = searchResult;
                    indexOfSelectedRoom = chooseRoom();
                    break;
                }
            }

            case 3:
                return;

            default:
                System.out.println("Please enter a valid choice");
        }
        }while(indexOfSelectedRoom < 0);



        // Create the reservation
        addReservation(customers.get(indexOfCustomer), rooms.get(indexOfSelectedRoom));

    }

    // Method to create new reservation
    public static void addReservation(User customer, Room room){
        reservations.add(new Reservation(((Customer) customer), room));

        // Store the index of the new reservation
        int indexOfNewReservation = reservations.size()-1;

        //
        boolean isItCorrectDate = false;

        // TODO: 10/21/2022 Duplicated?
        // Get the check-in date from the user
        do{
            System.out.println("Please enter the Check-in date");
            isItCorrectDate = reservations.get(indexOfNewReservation).setCheck_in(inputDate());
            if(!isItCorrectDate){
                System.out.println("Check-in date can not be in the past .. Please enter a valid date");
            }
        }while(!isItCorrectDate);

        // Set the variable back to false to check for check-out date
        isItCorrectDate = false;

        // Get the check-out date from the user
        do{
            System.out.println("Please enter the Check-out date");
            isItCorrectDate = reservations.get(indexOfNewReservation).setCheck_out(inputDate());
            if(!isItCorrectDate){
                System.out.println("Check-out date can not be before Check-in date .. Please enter a valid date");
            }
        }while(!isItCorrectDate);

        // If the reservation is created set the room to not available
        room.setAvailable(false);

        // Print the bill
        System.out.println("================ My Hotel System - The bill =================");
        System.out.printf("\n%-15s %-15s %-15s %-20s %-20s %-15s\n", "Reservation ID", "Phone number", "Room number", "Check-in", "Check-out", "Total Price");
        System.out.println(reservations.get(reservations.size()-1).toString());
        System.out.println("-------------------------------------------------------------");
        System.out.println("Reservation created successfully");
    }

    // Method to Create a new account for a customer
    public static void newCustomer(){
        System.out.println();
        System.out.println("============= My Hotel System - New Account =============");
        System.out.println("1- Citizen ");
        System.out.println("2- Resident ");
        System.out.print("Enter your choice: ");
        int isCitizen = input.nextInt();
        // Get the personal information from the user.
        System.out.print("Enter the first name: ");
        String firstName = input.next();
        System.out.print("Enter the last name: ");
        String lastName = input.next();
        System.out.print("Enter the phone number: ");
        String phoneNumber = input.next();
        System.out.print("Enter the password: ");
        String password = input.next();

        if(isCitizen == 1){
            // Store the customer
            customers.add(new Customer(firstName, lastName, phoneNumber, password));

            // Print the new customer information
            System.out.println("============ My Hotel System - Account information ==========");
            System.out.printf("%-15s %-25s %-12s \n", "ID", "Full name", "Phone number");
            System.out.println(customers.get(customers.size()-1).toString());

        }
        else if(isCitizen == 2){
            System.out.print("Enter the country: ");
            String country = input.next();
            System.out.print("Enter the passport number: ");
            String passport = input.next();

            // Store the customer
            customers.add(new ForeignCustomer(firstName, lastName, phoneNumber, password, passport, country));

            // Print the new customer information
            System.out.println("============ My Hotel System - Account information ==========");
            System.out.printf("%-15s %-25s %-15s %-15s %-15s\n", "ID", "Full name", "Phone number", "Passport", "Country");
            System.out.println(customers.get(customers.size()-1).toString());

        }
        else{
            System.out.println("Invalid choice .. Please try again");
            return;
        }

        // Success message
        System.out.println("-----+ Customer has been added successfully +-----");
    }

    // Method to search for a customer by the phone number
    public static int customerLogin(){
        int index = -1;

        // Get the phone number from the user to search
        System.out.print("Enter the phone number: ");
        String phoneNumber = input.next();

        // Get the number of customers
        int customersArraySize = customers.size();

        // Search for the customer by the phone number and return the index
        for(int i = 0; i < customersArraySize; i++){
            if(customers.get(i).getPhoneNumber().equals(phoneNumber)){
                // Check for the password
                System.out.print("Enter the password: ");
                String password = input.next();
                if(customers.get(i).getPassword().equals(password)){
                    index = i;
                    return index;
                }
            }
        }

        // If no match return -1
        return index;
    }

    public static int chooseRoom(){

        // No match value
        int selectedRoom = -1;

        do{
            System.out.println();
            System.out.println("=============== My Hotel System - Choose room ===============");
            System.out.println("1- Normal Room");
            System.out.println("2- Meeting Room");
            System.out.println("-------------------------------------------------------------");

            // Get the choice from the user
            System.out.print("Please enter your choice: ");
            int choice = input.nextInt();

            switch(choice){
                case 1:
                {
                    displayNormalRooms();
                    selectedRoom = searchRoom();
                    break;
                }

                case 2:
                {
                    displayMeetingRooms();
                    selectedRoom = searchRoom();
                    break;
                }

                default:
                    System.out.println("Please enter a valid choice");
            }
        }while(selectedRoom < 0);


        return selectedRoom;
    }

    // ------------------------- Update exist reservation -------------------------

    public static void updateReservation(){
        int index = customerLogin();
        if(index < 0){
            System.out.println("Customer not found .. Please try again");
        }
        else{
            int numOfReservations = reservations.size();
            String customerPhoneNumber = customers.get(index).getPhoneNumber();

            ArrayList<Integer> indexesOfRelated = new ArrayList<>();

            boolean isThereReservations = false;
            // Print the related and pending reservations of the customer
            for(int i = 0; i < numOfReservations; i++){
                boolean relatedReservation = reservations.get(i).getCustomer().getPhoneNumber().equals(customerPhoneNumber);
                if(relatedReservation){
                    if(reservations.get(i).isPending()){
                        isThereReservations = true;
                        indexesOfRelated.add(i);
                        System.out.printf("\n%-15s %-15s %-15s %-20s %-20s %-15s\n", "Reservation ID", "Phone number", "Room number", "Check-in", "Check-out", "Total Price");
                        System.out.println(reservations.get(i).toString());
                    }
                }
            }

            System.out.println("-------------------------------------------------------------");

            // Check if the user has pending and related reservations
            if(isThereReservations){
                System.out.print("Enter the reservation ID: ");
                String reservationID = input.next();


                for(int i = 0; i < indexesOfRelated.size(); i++){
                    if(reservationID.equals(reservations.get(indexesOfRelated.get(i)).getReservationID())){
                        System.out.println("Please enter new dates to update the reservation\n");
                        boolean isItCorrectDate = false;

                        // TODO: 10/21/2022 Duplicated?

                        // Get the check-in date from the user
                        do{
                            System.out.println("Please enter the new check-in date");
                            isItCorrectDate = reservations.get(indexesOfRelated.get(i)).setCheck_in(inputDate());
                            if(!isItCorrectDate){
                                System.out.println("Check-in date can not be in the past .. Please enter a valid date");
                            }
                        }while(!isItCorrectDate);

                        // Set the variable back to false to check for check-out date
                        isItCorrectDate = false;

                        // Get the check-out date from the user
                        do{
                            System.out.println("Please enter the new check-out date");
                            isItCorrectDate = reservations.get(indexesOfRelated.get(i)).setCheck_out(inputDate());
                            if(!isItCorrectDate){
                                System.out.println("Check-out date can not be before Check-in date .. Please enter a valid date");
                            }
                        }while(!isItCorrectDate);

                        System.out.println("Reservation updated successfully");
                        System.out.printf("\n%-15s %-15s %-15s %-20s %-20s %-15s\n", "Reservation ID", "Phone number", "Room number", "Check-in", "Check-out", "Total Price");
                        System.out.println(reservations.get(indexesOfRelated.get(i)).toString());
                        System.out.println("-------------------------------------------------------------");
                        break;
                    }
                    else{
                        System.out.println("Invalid reservation ID .. Please try again");
                    }
                }
            }
            else{
                System.out.println("Sorry there is no pending reservations");
            }

        }
    }

    // ------------------------- Cancel reservation -------------------------
    public static void cancelReservation(){
        int index = customerLogin();
        if(index < 0){
            System.out.println("Customer not found .. Please try again");
        }
        else{
            int numOfReservations = reservations.size();
            String customerPhoneNumber = customers.get(index).getPhoneNumber();

            ArrayList<Integer> indexesOfRelated = new ArrayList<>();

            boolean isThereReservations = false;
            // Print the related and pending reservations of the customer
            for(int i = 0; i < numOfReservations; i++){
                boolean relatedReservation = reservations.get(i).getCustomer().getPhoneNumber().equals(customerPhoneNumber);
                if(relatedReservation){
                    if(reservations.get(i).isPending()){
                        isThereReservations = true;
                        indexesOfRelated.add(i);
                        System.out.printf("\n%-15s %-15s %-15s %-20s %-20s %-15s\n", "Reservation ID", "Phone number", "Room number", "Check-in", "Check-out", "Total Price");
                        System.out.println(reservations.get(i).toString());
                    }
                }
            }

            System.out.println("-------------------------------------------------------------");

            // Check if the user has pending and related reservations
            if(isThereReservations){
                System.out.print("Enter the reservation ID: ");
                String reservationID = input.next();

                boolean correctReservationID = false;
                for(int i = 0; i < indexesOfRelated.size(); i++){
                    if(reservationID.equals(reservations.get(indexesOfRelated.get(i)).getReservationID())){
                        correctReservationID = true;
                        System.out.print("are you sure you want to remove the reservation? [yes/no]: ");
                        if(checkConfirm()){

                            // TODO: 10/21/2022 WHAAAAAT
                            int deletedIndex = indexesOfRelated.get(i);
                            reservations.remove(deletedIndex);
                            System.out.println("Reservation deleted successfully\n");
                            return;
                        }
                    }
                }
                if(!correctReservationID){
                    System.out.println("Invalid reservation ID .. Please try again");
                }
            }
            else{
                System.out.println("Sorry there is no pending reservations");
            }

        }
    }

    // ------------------------ Old reservations ------------------------
    public static void displayOldReservations(){
        int index = customerLogin();
        if(index < 0){
            System.out.println("Customer not found .. Please try again");
        }
        else{
            int numOfReservations = reservations.size();
            String customerPhoneNumber = customers.get(index).getPhoneNumber();

            boolean isThereReservations = false;
            // Print the related reservations of the customer
            for(int i = 0; i < numOfReservations; i++){
                boolean relatedReservation = reservations.get(i).getCustomer().getPhoneNumber().equals(customerPhoneNumber);
                if(relatedReservation){
                    isThereReservations = true;
                    System.out.printf("\n%-15s %-15s %-15s %-20s %-20s %-15s\n", "Reservation ID", "Phone number", "Room number", "Check-in", "Check-out", "Total Price");
                    System.out.println(reservations.get(i).toString());
                }
            }
            if(!isThereReservations){
                System.out.println("Sorry there are no old reservations");
            }
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

    public static void displayNormalRooms(){
        // Get the number of rooms
        int roomsArraySize = rooms.size();

        // Print only the available normal rooms from the rooms list
        System.out.println("======================= Normal Rooms ========================");
        System.out.printf("%-15s %-15s %-15s %-20s \n", "Room number", "Price", "Available", "Number of beds");
        for(int i = 0; i < roomsArraySize; i++){
            if((rooms.get(i) instanceof NormalRoom) && (rooms.get(i).isAvailable())){
                System.out.println(rooms.get(i).toString());
            }
        }
        System.out.println("-------------------------------------------------------------");

    }

    public static void displayMeetingRooms(){
        // Get the number of rooms
        int roomsArraySize = rooms.size();

        // Print only the available meeting rooms from the rooms list
        System.out.println("======================= Meeting Rooms =======================");
        System.out.printf("%-15s %-15s %-15s %-20s \n", "Room number", "Price", "Available", "Capacity(Persons)");
        for(int i = 0; i < roomsArraySize; i++){
            if((rooms.get(i) instanceof MeetingRoom)  && (rooms.get(i).isAvailable())){
                System.out.println(rooms.get(i).toString());
            }
        }
        System.out.println("-------------------------------------------------------------");
    }

    // Method to search for a room
    public static int searchRoom(){
        // No match value
        int index = -1;

        // Get the number of rooms
        int roomsArraySize = rooms.size();

        do{
            // Get the room number
            System.out.print("Enter the room number: ");
            int roomNum = input.nextInt();

            // Search for index of the room
            for(int i = 0; i < roomsArraySize; i++){
                if((rooms.get(i).getRoomNumber() == roomNum) && rooms.get(i).isAvailable()){
                    index = i;
                    return index;
                }
            }
            System.out.println("Please enter a valid room number from the above table\n");
        }while(index < 0);

        return index;
    }

    public static LocalDate inputDate(){
        System.out.print("Year: ");
        int year = input.nextInt();
        System.out.print("Month: ");
        int month = input.nextInt();
        System.out.print("day: ");
        int day = input.nextInt();

        LocalDate enteredDate = LocalDate.of(year, month, day);

        return enteredDate;
    }

    // ========================================================================
    public static void receptionistMenu(){
        int choice = 0;
        do{
            System.out.println();
            System.out.println("=============================================================");
            System.out.println("============= My Hotel System - Reception Menu ==============");
            System.out.println("1- Reservations                                             +");
            System.out.println("2- Customers                                                +");
            System.out.println("3- Rooms                                                    +");
            System.out.println("4- Exit                                                     +");
            System.out.println("-------------------------------------------------------------");
            System.out.print("Please enter your choice: ");

            // Get the choice from the user
            choice = input.nextInt();

            switch(choice){
                case 1:
                    reservationsMenu();
                    break;
                case 2:
                    updateReservation();
                    break;
                case 3:
                    cancelReservation();
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
        System.out.println("=============================================================");
        System.out.println("============ My Hotel System - Reservations Menu ============");
        System.out.println("1- New reservation for customer                             +");
        System.out.println("2- Update reservation                                       +");
        System.out.println("3- Cancel reservation                                       +");
        System.out.println("4- Print all reservations                                   +");
        System.out.println("5- Exit                                                     +");
        System.out.println("-------------------------------------------------------------");
        System.out.print("Please enter your choice: ");

        // Get the choice from the user
        choice = input.nextInt();
        switch(choice){
            case 1:
                newReservation();
                break;
            case 2:
                updateReservationByReceptionist();
                break;
            case 3:
                cancelReservationByReceptionist();
                break;
            case 4:
                displayAllReservations();
            case 5:
                break;
            default:
                System.out.println("Please enter a valid choice");
        }

    }while(choice != 5);

    }

    public static int receptionistLogin(){
        int index = -1;

        // Get the phone number from the receptionist to search
        System.out.print("Enter the phone number: ");
        String phoneNumber = input.next();

        // Get the number of receptionists
        int receptionistsArraySize = receptionists.size();

        // Search for the receptionist by the phone number and return the index
        for(int i = 0; i < receptionistsArraySize; i++){
            if(receptionists.get(i).getPhoneNumber().equals(phoneNumber)){
                // Check for the password
                System.out.print("Enter the password: ");
                String password = input.next();
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
        int index = receptionistLogin();
        if(index < 0){
            System.out.println("Receptionist not found .. Please try again");
        }
        else{

            // Print all pending reservations
            ArrayList<Integer> indexesOfPending = displayPendingReservations();
            int numOfPendingReservations = indexesOfPending.size();
            System.out.println("-------------------------------------------------------------");
            boolean isThereReservations = (numOfPendingReservations>0)?true:false;
            // Check if there are pending reservations
            if(isThereReservations){
                System.out.print("Enter the reservation ID: ");
                String reservationID = input.next();

                boolean correctReservationID = false;
                for(int i = 0; i < numOfPendingReservations; i++){
                    if(reservationID.equals(reservations.get(indexesOfPending.get(i)).getReservationID())){
                        correctReservationID = true;
                        System.out.println("Please enter new dates to update the reservation\n");
                        boolean isItCorrectDate = false;

                        // TODO: 10/21/2022 Duplicated?

                        // Get the check-in date from the user
                        do{
                            System.out.println("Please enter the new check-in date");
                            isItCorrectDate = reservations.get(indexesOfPending.get(i)).setCheck_in(inputDate());
                            if(!isItCorrectDate){
                                System.out.println("Check-in date can not be in the past .. Please enter a valid date");
                            }
                        }while(!isItCorrectDate);

                        // Set the variable back to false to check for check-out date
                        isItCorrectDate = false;

                        // Get the check-out date from the user
                        do{
                            System.out.println("Please enter the new check-out date");
                            isItCorrectDate = reservations.get(indexesOfPending.get(i)).setCheck_out(inputDate());
                            if(!isItCorrectDate){
                                System.out.println("Check-out date can not be before Check-in date .. Please enter a valid date");
                            }
                        }while(!isItCorrectDate);

                        System.out.println("Reservation updated successfully");
                        System.out.printf("\n%-15s %-15s %-15s %-20s %-20s %-15s\n", "Reservation ID", "Phone number", "Room number", "Check-in", "Check-out", "Total Price");
                        System.out.println(reservations.get(indexesOfPending.get(i)).toString());
                        System.out.println("-------------------------------------------------------------");
                        break;
                    }
                }
                if(!correctReservationID){
                    System.out.println("Invalid reservation ID .. Please try again");
                }
            }
            else{
                System.out.println("Sorry there are no pending reservations");
            }

        }
    }
    public static ArrayList<Integer> displayPendingReservations(){
        int numOfReservations = reservations.size();

        boolean isThereReservations = false;
        ArrayList<Integer> indexesOfPending = new ArrayList<>();

        // Print all pending reservations
        for(int i = 0; i < numOfReservations; i++){
            if(reservations.get(i).isPending()){
                isThereReservations = true;
                indexesOfPending.add(i);
                System.out.printf("\n%-15s %-15s %-15s %-20s %-20s %-15s\n", "Reservation ID", "Phone number", "Room number", "Check-in", "Check-out", "Total Price");
                System.out.println(reservations.get(i).toString());
            }
        }

        return indexesOfPending;
    }

    public static void cancelReservationByReceptionist(){
        int index = receptionistLogin();
        if(index < 0){
            System.out.println("Receptionist not found .. Please try again");
        }
        else{
            int numOfReservations = reservations.size();

            // Print all pending reservations
            ArrayList<Integer> indexesOfPending = displayPendingReservations();

            System.out.println("-------------------------------------------------------------");
            boolean isThereReservations = (indexesOfPending.size()>0)?true:false;

            System.out.println("-------------------------------------------------------------");

            // Check if the user has pending and related reservations
            if(isThereReservations){
                System.out.print("Enter the reservation ID: ");
                String reservationID = input.next();

                boolean correctReservationID = false;
                for(int i = 0; i < indexesOfPending.size(); i++){
                    if(reservationID.equals(reservations.get(indexesOfPending.get(i)).getReservationID())){
                        correctReservationID = true;
                        System.out.print("are you sure you want to remove the reservation? [yes/no]: ");
                        if(checkConfirm()){

                            // TODO: 10/21/2022 WHAAAAAT
                            int deletedIndex = indexesOfPending.get(i);
                            reservations.remove(deletedIndex);
                            System.out.println("Reservation deleted successfully\n");
                            return;
                        }
                    }
                }
                if(!correctReservationID){
                    System.out.println("Invalid reservation ID .. Please try again");
                }
            }
            else{
                System.out.println("Sorry there are no pending reservations");
            }

        }
    }

    public static void displayAllReservations(){
        int index = receptionistLogin();
        if(index < 0){
            System.out.println("Receptionist not found .. Please try again ");
            return;
        }
        int numOfReservations = reservations.size();

        if(numOfReservations == 0){
            System.out.println("Sorry, there are no reservations");
            return;
        }

        System.out.printf("\n%-15s %-15s %-15s %-20s %-20s %-15s %-10s\n", "Reservation ID", "Phone number", "Room number", "Check-in", "Check-out", "Total Price", "Status");
        for(int i = 0; i < numOfReservations; i++){
            System.out.print(reservations.get(i).toString());
            System.out.printf(" %-10s\n", reservations.get(i).isPending()? "Pending": "Finished");
        }
    }




} // End of application class
