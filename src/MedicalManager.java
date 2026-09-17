public class MedicalManager extends User {

    private String managerId;

    public MedicalManager(
            String userId,
            String name,
            String username,
            String password,
            String contactNo,
            String managerId
    ) {

        super(userId, name, username, password, contactNo);

        this.managerId = managerId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void createDepartment() {
        System.out.println("Medical Manager is creating a department.");
    }

    public void designShiftRoster() {
        System.out.println("Medical Manager is designing a shift roster.");
    }

    public void viewReports() {
        System.out.println("Medical Manager is viewing hospital reports.");
    }

    @Override
    public String getDashboard() {
        return "Medical Manager Dashboard";
    }
}