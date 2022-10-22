public class Receptionist extends User{
    // Attributes
    private double salary;

    // Constructors
    public Receptionist(String firstName, String lastName, String phoneNumber, String password, double salary) {
        super(firstName, lastName, phoneNumber, password);
        this.salary = salary;
    }

    // Setters and getters
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Other methods

    @Override
    public String toString()  {
        return (String.format("%-15s %-25s %-12s %.2f", super.getID(), (super.getFirstName() + super.getLastName()), super.getPhoneNumber(), this.salary));
    }
}
