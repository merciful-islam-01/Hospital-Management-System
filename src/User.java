public abstract class User {

    // Encapsulation: private fields
    private String userId;
    private String name;
    private String username;
    private String password;
    private String contactNo;

    // Constructor
    public User(String userId, String name, String username,
                String password, String contactNo) {

        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.contactNo = contactNo;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getContactNo() {
        return contactNo;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    // Common method
    public boolean login(String inputUsername, String inputPassword) {

        return username.equals(inputUsername)
                && password.equals(inputPassword);
    }

    public void logout() {
        System.out.println(username + " logged out.");
    }

    public void editProfile(String newName, String newContactNo) {

        this.name = newName;
        this.contactNo = newContactNo;
    }

    // Abstract method
    public abstract String getDashboard();
}