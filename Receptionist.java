public class Receptionist extends User{
    // Attributes
    private double salary;

    // Constructors
    public Receptionist(String ID, String firstName, String lastName, String phoneNumber, String password, double salary) {
        super(ID, firstName, lastName, phoneNumber, password);
        this.salary = salary;
    }

    // Setters and getters
//    public double getSalary() {
//        return salary;
//    }

//    public void setSalary(double salary) {
//        this.salary = salary;
//    }

    // Other methods

    public static void printHeader(){
        System.out.println();
        System.out.printf("%-17s %-20s %-17s %-10s \n", "National ID", "Full name", "Phone number", "Salary");
        System.out.println("-------------------------------------------------------------------");
    }
    @Override
    public String toString()  {
        return (String.format("%-17s %-20s %-17s %.2f", super.getID(), (super.getFirstName() + " " + super.getLastName()), super.getPhoneNumber(), this.salary));
    }
}
