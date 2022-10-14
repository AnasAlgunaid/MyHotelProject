public class Admin extends User{
    // Data fields
    private String userName;
    private double salary;

    // Constructors

    public Admin(String ID, String firstName, String lastName, String phoneNumber, String password, String userName, double salary) {
        super(ID, firstName, lastName, phoneNumber, password);
        this.userName = userName;
        this.salary = salary;
    }

    // Methods

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}
