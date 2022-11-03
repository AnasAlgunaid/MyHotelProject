import java.util.Random;

public class User {
    // Data Fields
    private String ID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;

    // Constructors
    public User(){};

    public User(String ID, String firstName, String lastName, String phoneNumber, String password) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getters and setters
    public String getID() {
        return ID;
    }

//    public void setID(String ID) {
//        this.ID = ID;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Other methods
    public String printFullName(){
        return (firstName + " " + lastName);
    }

//    public static String generateID(){
//        String id = "";
//        // Generate random id of 5 numbers
//        Random random = new Random();
//        for(int i = 0; i < 5; i++){
//            int randomNum = random.nextInt(10);
//            id += randomNum;
//        }
//
//        return id;
//    }

    @Override
    public String toString() {
        return (String.format("%-15s %-25s %-12s", ID, (firstName + lastName), phoneNumber));
    }


}
