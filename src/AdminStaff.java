public class AdminStaff extends User {

    private String staffId;

    public AdminStaff(
            String userId,
            String name,
            String username,
            String password,
            String contactNo,
            String staffId
    ) {

        super(userId, name, username, password, contactNo);

        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void createUser() {
        System.out.println("Admin Staff is creating a user.");
    }

    public void deleteUser() {
        System.out.println("Admin Staff is deleting a user.");
    }

    public void assignDoctor() {
        System.out.println("Admin Staff is assigning a doctor.");
    }

    public void configureRates() {
        System.out.println("Admin Staff is configuring rates.");
    }

    @Override
    public String getDashboard() {
        return "Admin Staff Dashboard";
    }
}