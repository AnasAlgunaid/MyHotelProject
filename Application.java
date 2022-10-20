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


    public static void main(String[] args) {

        rooms.add(new MeetingRoom(113, 250, 12));
        rooms.add(new MeetingRoom(114, 300, 15));
        rooms.add(new NormalRoom(118, 400, 2));
        rooms.add(new NormalRoom(119, 600, 4));

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
            System.out.println("4- Past reservations                                        +");
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
                    System.out.println(customers.get(customers.size()-1).getPhoneNumber());
                    break;

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
                int searchResult = searchCustomer();
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

        // Get the check-out date from the user
        System.out.println("Please enter the Check-in date");
        LocalDate check_in = inputDate();

        // Get the check-out date from the user
        System.out.println("Please enter the Check-out date");
        LocalDate check_out = inputDate();

        // Create the reservation
        addReservation(customers.get(indexOfCustomer), rooms.get(indexOfSelectedRoom), check_in, check_out);

    }

    // Method to create new reservation
    public static void addReservation(User customer, Room room, LocalDate check_in, LocalDate check_out){
        reservations.add(new Reservation(((Customer) customer), room, check_in, check_out));
        room.setAvailable(false);

        System.out.println("Reservation created successfully");
    }

    // Method to Create a new account for a customer
    public static void newCustomer(){
        System.out.println();
        System.out.println("============= My Hotel System - New Account =============");

        // Get the personal information from the user.
        System.out.print("Enter the first name: ");
        String firstName = input.next();
        System.out.print("Enter the last name: ");
        String lastName = input.next();
        System.out.print("Enter the phone number: ");
        String phoneNumber = input.next();
        System.out.print("Enter the password: ");
        String password = input.next();

        // Store this customer
        customers.add(new Customer(firstName, lastName, phoneNumber, password));

        System.out.println(customers.get(0));
        // Success message
        System.out.println("Customer has been added successfully");
    }

    // Method to search for a customer by the phone number
    public static int searchCustomer(){
        int index = -1;

        // Get the phone number from the user to search
        System.out.print("Enter the phone number: ");
        String phoneNumber = input.next();

        // Get the number of customers
        int customersArraySize = customers.size();

        // Search for the customer by the phone number and return the index
        for(int i = 0; i < customersArraySize; i++){
            if(customers.get(i).getPhoneNumber().equals(phoneNumber)){
                index = i;
                return index;
            }
        }
        System.out.println("THE INDEX IS: " + index);
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

//            switch(choice){
//
//            }
        }while(choice != 5);

    }



}
