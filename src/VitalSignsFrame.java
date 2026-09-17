import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VitalSignsFrame extends JFrame {

    private final Color DOCTOR_GREEN = new Color(32, 224, 157);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(155, 185, 180);
    private final Color FIELD_BG = new Color(6, 39, 42);

    private final String doctorUsername;

    private JTextField patientField;
    private JTextField dateField;
    private JTextField bloodPressureField;
    private JTextField heartRateField;
    private JTextField temperatureField;
    private JTextField oxygenField;

    private BufferedImage backgroundImage;

    public VitalSignsFrame(String doctorUsername) {

        this.doctorUsername = doctorUsername;

        setTitle("APU Medical Centre - Record Vital Signs");
        setSize(860, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            backgroundImage = ImageIO.read(
                    new File("assets/hospital_portal_bg.png")
            );
        } catch (Exception e) {
            backgroundImage = null;
        }

        VitalBackground root = new VitalBackground();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createBottomBar(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(16, 28, 14, 30));
        header.setPreferredSize(new Dimension(0, 84));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.X_AXIS));

        JLabel cross = new JLabel("+", SwingConstants.CENTER);
        cross.setOpaque(true);
        cross.setBackground(new Color(225, 255, 245));
        cross.setForeground(new Color(10, 105, 72));
        cross.setFont(new Font("Arial", Font.BOLD, 28));
        cross.setPreferredSize(new Dimension(48, 48));
        cross.setMaximumSize(new Dimension(48, 48));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel hospital = new JLabel("APU MEDICAL CENTRE");
        hospital.setForeground(TEXT_WHITE);
        hospital.setFont(new Font("Arial", Font.BOLD, 19));

        JLabel portal = new JLabel("DOCTOR PORTAL  /  CLINICAL OBSERVATIONS");
        portal.setForeground(DOCTOR_GREEN);
        portal.setFont(new Font("Arial", Font.BOLD, 9));

        text.add(hospital);
        text.add(Box.createRigidArea(new Dimension(0, 3)));
        text.add(portal);

        brand.add(cross);
        brand.add(Box.createRigidArea(new Dimension(13, 0)));
        brand.add(text);

        JPanel session = new JPanel();
        session.setOpaque(false);
        session.setLayout(new BoxLayout(session, BoxLayout.Y_AXIS));

        JLabel secure = new JLabel("●  CLINICAL SESSION");
        secure.setForeground(DOCTOR_GREEN);
        secure.setFont(new Font("Arial", Font.BOLD, 9));
        secure.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel doctor = new JLabel(doctorUsername);
        doctor.setForeground(TEXT_WHITE);
        doctor.setFont(new Font("Arial", Font.BOLD, 14));
        doctor.setAlignmentX(Component.RIGHT_ALIGNMENT);

        session.add(secure);
        session.add(Box.createRigidArea(new Dimension(0, 4)));
        session.add(doctor);

        header.add(brand, BorderLayout.WEST);
        header.add(session, BorderLayout.EAST);

        return header;
    }

    private JPanel createMainContent() {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(20, 45, 22, 45));

        VitalCard card = new VitalCard();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(690, 515));
        card.setBorder(new EmptyBorder(24, 32, 23, 32));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("PATIENT OBSERVATION ENTRY");
        small.setForeground(DOCTOR_GREEN);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Record Vital Signs");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Enter the patient's current clinical observations and save them to the medical record."
        );
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        heading.add(small);
        heading.add(Box.createRigidArea(new Dimension(0, 4)));
        heading.add(title);
        heading.add(Box.createRigidArea(new Dimension(0, 5)));
        heading.add(subtitle);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(18, 0, 15, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        patientField = createField();
        dateField = createField();
        bloodPressureField = createField();
        heartRateField = createField();
        temperatureField = createField();
        oxygenField = createField();

        patientField.setToolTipText("Example: patient01");
        dateField.setToolTipText("DD-MM-YYYY");
        bloodPressureField.setToolTipText("Example: 120/80");
        heartRateField.setToolTipText("Example: 72");
        temperatureField.setToolTipText("Example: 36.8");
        oxygenField.setToolTipText("Example: 98");

        addRow(form, gbc, 0, "Doctor", createLockedDoctorField());
        addRow(form, gbc, 1, "Patient Username", patientField);
        addRow(form, gbc, 2, "Date (DD-MM-YYYY)", dateField);
        addRow(form, gbc, 3, "Blood Pressure", bloodPressureField);
        addRow(form, gbc, 4, "Heart Rate (BPM)", heartRateField);
        addRow(form, gbc, 5, "Temperature (°C)", temperatureField);
        addRow(form, gbc, 6, "Oxygen Saturation (%)", oxygenField);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);

        JButton closeButton = new JButton("CLOSE");
        JButton saveButton = new JButton("SAVE VITAL SIGNS");

        styleSecondaryButton(closeButton);
        stylePrimaryButton(saveButton);

        closeButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveVitalSigns());

        buttons.add(closeButton);
        buttons.add(saveButton);

        card.add(heading, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);
        card.add(buttons, BorderLayout.SOUTH);

        wrapper.add(card);

        return wrapper;
    }

    private JTextField createField() {

        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(360, 37));
        field.setBackground(FIELD_BG);
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(DOCTOR_GREEN);
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        DOCTOR_GREEN.getRed(),
                                        DOCTOR_GREEN.getGreen(),
                                        DOCTOR_GREEN.getBlue(),
                                        100
                                )
                        ),
                        new EmptyBorder(0, 12, 0, 12)
                )
        );

        return field;
    }

    private JTextField createLockedDoctorField() {

        JTextField field = createField();
        field.setText(doctorUsername);
        field.setEditable(false);
        field.setForeground(new Color(130, 178, 169));
        field.setBackground(new Color(4, 31, 34));

        return field;
    }

    private void addRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JTextField field
    ) {

        JLabel label = new JLabel(labelText);
        label.setForeground(TEXT_WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 11));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.34;
        form.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.66;
        form.add(field, gbc);
    }

    private void stylePrimaryButton(JButton button) {

        button.setPreferredSize(new Dimension(165, 38));
        button.setBackground(DOCTOR_GREEN);
        button.setForeground(new Color(3, 30, 29));
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton button) {

        button.setPreferredSize(new Dimension(100, 38));
        button.setBackground(new Color(6, 39, 42));
        button.setForeground(TEXT_WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                DOCTOR_GREEN.getRed(),
                                DOCTOR_GREEN.getGreen(),
                                DOCTOR_GREEN.getBlue(),
                                100
                        )
                )
        );
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JPanel createBottomBar() {

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(2, 27, 30, 240));
        bottom.setBorder(new EmptyBorder(9, 28, 9, 28));

        JLabel status = new JLabel(
                "●  SYSTEM READY     |     VITAL SIGNS ENTRY"
        );
        status.setForeground(DOCTOR_GREEN);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("SECURE CLINICAL RECORD");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);

        return bottom;
    }

    private void saveVitalSigns() {

        String patient = patientField.getText().trim();
        String date = dateField.getText().trim();
        String bloodPressure = bloodPressureField.getText().trim();
        String heartRate = heartRateField.getText().trim();
        String temperature = temperatureField.getText().trim();
        String oxygen = oxygenField.getText().trim();

        if (
                patient.isEmpty()
                        ||
                date.isEmpty()
                        ||
                bloodPressure.isEmpty()
                        ||
                heartRate.isEmpty()
                        ||
                temperature.isEmpty()
                        ||
                oxygen.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            int bpm = Integer.parseInt(heartRate);

            if (bpm < 30 || bpm > 220) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a realistic heart rate.",
                        "Invalid Heart Rate",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Heart rate must be a number.",
                    "Invalid Heart Rate",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            double temp = Double.parseDouble(temperature);

            if (temp < 30 || temp > 45) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a realistic temperature.",
                        "Invalid Temperature",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Temperature must be a number.",
                    "Invalid Temperature",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            int oxygenValue = Integer.parseInt(oxygen);

            if (oxygenValue < 50 || oxygenValue > 100) {

                JOptionPane.showMessageDialog(
                        this,
                        "Oxygen saturation must be between 50 and 100.",
                        "Invalid Oxygen Level",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Oxygen saturation must be a number.",
                    "Invalid Oxygen Level",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String recordId = "MR" + System.currentTimeMillis();
        String notes = "Vitals recorded";

        String result =
                "BP:"
                        + bloodPressure
                        + " HR:"
                        + heartRate
                        + " Temp:"
                        + temperature
                        + "C O2:"
                        + oxygen
                        + "%";

        String filePath = "data/medical_records.txt";

        try (
                FileWriter fileWriter =
                        new FileWriter(filePath, true);

                PrintWriter writer =
                        new PrintWriter(fileWriter)
        ) {

            writer.println(
                    recordId
                            + ","
                            + patient
                            + ","
                            + doctorUsername
                            + ","
                            + date
                            + ","
                            + bloodPressure
                            + ","
                            + heartRate
                            + ","
                            + notes
                            + ","
                            + result
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Vital signs saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save vital signs.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearFields() {

        patientField.setText("");
        dateField.setText("");
        bloodPressureField.setText("");
        heartRateField.setText("");
        temperatureField.setText("");
        oxygenField.setText("");
    }

    private class VitalBackground extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(2, 22, 24));
            g2.fillRect(0, 0, getWidth(), getHeight());

            if (backgroundImage != null) {
                g2.drawImage(
                        backgroundImage,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        null
                );
            }

            GradientPaint overlay = new GradientPaint(
                    0,
                    0,
                    new Color(0, 22, 25, 248),
                    getWidth(),
                    0,
                    new Color(0, 18, 25, 218)
            );

            g2.setPaint(overlay);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(0, 18, 22, 190));
            g2.fillRect(0, 0, getWidth(), 84);

            GradientPaint accent = new GradientPaint(
                    0,
                    0,
                    DOCTOR_GREEN,
                    getWidth(),
                    0,
                    new Color(
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            0
                    )
            );

            g2.setPaint(accent);
            g2.fillRect(0, 82, getWidth(), 2);

            g2.setColor(
                    new Color(
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            10
                    )
            );

            g2.fillOval(500, 100, 320, 320);

            g2.dispose();
        }
    }

    private class VitalCard extends JPanel {

        VitalCard() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRoundRect(
                    7,
                    8,
                    getWidth() - 11,
                    getHeight() - 11,
                    22,
                    22
            );

            GradientPaint gradient = new GradientPaint(
                    0,
                    0,
                    new Color(
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            28
                    ),
                    0,
                    getHeight(),
                    new Color(4, 34, 38, 245)
            );

            g2.setPaint(gradient);
            g2.fillRoundRect(
                    1,
                    1,
                    getWidth() - 8,
                    getHeight() - 9,
                    20,
                    20
            );

            g2.setColor(
                    new Color(
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            110
                    )
            );

            g2.drawRoundRect(
                    1,
                    1,
                    getWidth() - 8,
                    getHeight() - 9,
                    20,
                    20
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }
}
