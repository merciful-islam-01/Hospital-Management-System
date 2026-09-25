
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DoctorAppointmentsFrame extends JFrame {

    private final String doctorUsername;
    private final DefaultTableModel tableModel;
    private final JTable appointmentTable;

    private static final Color BACKGROUND = new Color(17, 22, 25);
    private static final Color PANEL = new Color(28, 38, 39);
    private static final Color EMERALD = new Color(32, 224, 157);

    public DoctorAppointmentsFrame(String doctorUsername) {
        this.doctorUsername = doctorUsername;

        setTitle("APU Medical Centre - My Appointments");
        setSize(1150, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
            "Appointment ID",
            "Patient Username",
            "Patient Name",
            "Date",
            "Time",
            "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        appointmentTable = new JTable(tableModel);

        initializeUI();
        loadAppointments();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(30, 35, 30, 35)
        );

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel branding = new JLabel("APU MEDICAL CENTRE");
        branding.setFont(new Font("SansSerif", Font.BOLD, 17));
        branding.setForeground(EMERALD);

        JLabel title = new JLabel("My Appointments");
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
            "View patients who have booked appointments with you."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(170, 190, 185));

        header.add(branding);
        header.add(Box.createVerticalStrut(15));
        header.add(title);
        header.add(Box.createVerticalStrut(7));
        header.add(subtitle);

        mainPanel.add(header, BorderLayout.NORTH);

        appointmentTable.setRowHeight(40);
        appointmentTable.setFont(
            new Font("SansSerif", Font.PLAIN, 13)
        );
        appointmentTable.setForeground(Color.WHITE);
        appointmentTable.setBackground(PANEL);
        appointmentTable.setGridColor(new Color(55, 75, 70));
        appointmentTable.setSelectionBackground(
            new Color(24, 110, 85)
        );
        appointmentTable.setSelectionForeground(Color.WHITE);
        appointmentTable.setFillsViewportHeight(true);

        appointmentTable.getTableHeader().setFont(
            new Font("SansSerif", Font.BOLD, 13)
        );
        appointmentTable.getTableHeader().setBackground(
            new Color(25, 75, 62)
        );
        appointmentTable.getTableHeader().setForeground(Color.WHITE);
        appointmentTable.getTableHeader().setPreferredSize(
            new Dimension(0, 42)
        );

        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        scrollPane.getViewport().setBackground(PANEL);
        scrollPane.setBorder(
            BorderFactory.createLineBorder(
                new Color(45, 100, 82)
            )
        );

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel footer = new JPanel(
            new FlowLayout(FlowLayout.RIGHT, 12, 0)
        );
        footer.setOpaque(false);

        JButton refreshButton = createButton("REFRESH");
        JButton closeButton = createButton("CLOSE");

        refreshButton.addActionListener(
            e -> loadAppointments()
        );
        closeButton.addActionListener(
            e -> dispose()
        );

        footer.add(refreshButton);
        footer.add(closeButton);

        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);

        button.setFont(
            new Font("SansSerif", Font.BOLD, 12)
        );
        button.setBackground(new Color(22, 120, 90));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(
            BorderFactory.createEmptyBorder(13, 28, 13, 28)
        );

        return button;
    }

    private Map<String, String> loadPatientNames() {
        Map<String, String> patientNames = new HashMap<>();

        File file = new File("data/users.txt");

        if (!file.exists()) {
            return patientNames;
        }

        try (
            BufferedReader reader = new BufferedReader(
                new FileReader(file)
            )
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", -1);

                if (fields.length < 6) {
                    continue;
                }

                String fullName = fields[1].trim();
                String username = fields[2].trim();
                String role = fields[5].trim();

                if (role.equalsIgnoreCase("Patient")) {
                    patientNames.put(
                        username.toLowerCase(),
                        fullName
                    );
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Unable to read patient information.",
                "File Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        return patientNames;
    }

    private void loadAppointments() {
        tableModel.setRowCount(0);

        File file = new File("data/appointments.txt");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(
                this,
                "No appointment records were found.",
                "My Appointments",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        Map<String, String> patientNames = loadPatientNames();

        try (
            BufferedReader reader = new BufferedReader(
                new FileReader(file)
            )
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",", -1);

                if (fields.length < 6) {
                    continue;
                }

                String appointmentId = fields[0].trim();
                String patientUsername = fields[1].trim();
                String storedDoctor = fields[2].trim();
                String date = fields[3].trim();
                String time = fields[4].trim();
                String status = fields[5].trim();

                // Older records may contain a department:
                // doctor02 - General Medicine
                String storedDoctorUsername = storedDoctor;

                int separator = storedDoctor.indexOf(" - ");

                if (separator >= 0) {
                    storedDoctorUsername = storedDoctor
                        .substring(0, separator)
                        .trim();
                }

                // Display only the logged-in doctor's bookings.
                if (!storedDoctorUsername.equalsIgnoreCase(
                    doctorUsername
                )) {
                    continue;
                }

                String patientName = patientNames.getOrDefault(
                    patientUsername.toLowerCase(),
                    "Unknown Patient"
                );

                tableModel.addRow(new Object[] {
                    appointmentId,
                    patientUsername,
                    patientName,
                    date,
                    time,
                    status
                });
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Unable to load appointments:\n"
                    + e.getMessage(),
                "Appointment Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
