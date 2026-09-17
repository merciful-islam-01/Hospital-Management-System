public class Doctor extends User {

    private String doctorId;
    private String specialty;

    public Doctor(
            String userId,
            String name,
            String username,
            String password,
            String contactNo,
            String doctorId,
            String specialty
    ) {

        super(userId, name, username, password, contactNo);

        this.doctorId = doctorId;
        this.specialty = specialty;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void logVitals() {
        System.out.println("Doctor is logging patient vital signs.");
    }

    public void writePrescription() {
        System.out.println("Doctor is writing a prescription.");
    }

    public void requestLabTest() {
        System.out.println("Doctor is requesting a lab test.");
    }

    @Override
    public String getDashboard() {
        return "Doctor Dashboard";
    }
}