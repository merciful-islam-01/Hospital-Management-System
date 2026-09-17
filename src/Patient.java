public class Patient extends User {

    private String patientId;

    public Patient(
            String userId,
            String name,
            String username,
            String password,
            String contactNo,
            String patientId
    ) {

        super(userId, name, username, password, contactNo);

        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void bookAppointment() {
        System.out.println("Patient is booking an appointment.");
    }

    public void cancelAppointment() {
        System.out.println("Patient is cancelling an appointment.");
    }

    public void submitFeedback() {
        System.out.println("Patient is submitting feedback.");
    }

    @Override
    public String getDashboard() {
        return "Patient Dashboard";
    }
}