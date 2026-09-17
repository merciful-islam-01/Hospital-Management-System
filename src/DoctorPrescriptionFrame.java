import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DoctorPrescriptionFrame extends JFrame {

    private final Color DOCTOR_GREEN = new Color(32, 224, 157);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(155, 185, 180);
    private final Color FIELD_BG = new Color(6, 39, 42);

    private final String doctorUsername;

    private JTextField patientField;
    private JTextField dateField;
    private JTextField medicineField;
    private JTextField dosageField;
    private JTextArea instructionsArea;

    private BufferedImage backgroundImage;

    public DoctorPrescriptionFrame(String doctorUsername) {
        this.doctorUsername = doctorUsername;

        setTitle("APU Medical Centre - Issue Prescription");
        setSize(880, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            backgroundImage = ImageIO.read(new File("assets/hospital_portal_bg.png"));
        } catch (Exception e) {
            backgroundImage = null;
        }

        PrescriptionBackground root = new PrescriptionBackground();
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

        JLabel portal = new JLabel("DOCTOR PORTAL  /  MEDICATION MANAGEMENT");
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

        PrescriptionCard card = new PrescriptionCard();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(700, 510));
        card.setBorder(new EmptyBorder(25, 34, 24, 34));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("PATIENT MEDICATION ORDER");
        small.setForeground(DOCTOR_GREEN);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Issue Prescription");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Create a medication prescription with dosage and treatment instructions."
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
        form.setBorder(new EmptyBorder(18, 0, 16, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField doctorField = createField(false);
        doctorField.setText(doctorUsername);

        patientField = createField(true);
        dateField = createField(true);
        medicineField = createField(true);
        dosageField = createField(true);
        instructionsArea = createTextArea();

        patientField.setToolTipText("Example: patient01");
        dateField.setToolTipText("DD-MM-YYYY");
        medicineField.setToolTipText("Example: Paracetamol");
        dosageField.setToolTipText("Example: 500mg");

        JScrollPane instructionsScroll = new JScrollPane(instructionsArea);
        instructionsScroll.setPreferredSize(new Dimension(390, 100));
        instructionsScroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                DOCTOR_GREEN.getRed(),
                                DOCTOR_GREEN.getGreen(),
                                DOCTOR_GREEN.getBlue(),
                                100
                        )
                )
        );
        instructionsScroll.getViewport().setBackground(FIELD_BG);

        addFieldRow(form, gbc, 0, "Doctor", doctorField, true);
        addFieldRow(form, gbc, 1, "Patient Username", patientField, false);
        addFieldRow(form, gbc, 2, "Date (DD-MM-YYYY)", dateField, false);
        addFieldRow(form, gbc, 3, "Medicine", medicineField, false);
        addFieldRow(form, gbc, 4, "Dosage", dosageField, false);
        addAreaRow(form, gbc, 5, "Instructions", instructionsScroll);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);

        JButton closeButton = new JButton("CLOSE");
        JButton saveButton = new JButton("ISSUE PRESCRIPTION");

        styleSecondaryButton(closeButton);
        stylePrimaryButton(saveButton);

        closeButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> savePrescription());

        buttons.add(closeButton);
        buttons.add(saveButton);

        card.add(heading, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);
        card.add(buttons, BorderLayout.SOUTH);

        wrapper.add(card);

        return wrapper;
    }

    private JTextField createField(boolean editable) {
        JTextField field = new JTextField();
        field.setEditable(editable);
        field.setPreferredSize(new Dimension(390, 37));
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setForeground(editable ? TEXT_WHITE : new Color(130, 178, 169));
        field.setCaretColor(DOCTOR_GREEN);
        field.setBackground(editable ? FIELD_BG : new Color(4, 31, 34));
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        DOCTOR_GREEN.getRed(),
                                        DOCTOR_GREEN.getGreen(),
                                        DOCTOR_GREEN.getBlue(),
                                        editable ? 100 : 45
                                )
                        ),
                        new EmptyBorder(0, 12, 0, 12)
                )
        );
        return field;
    }

    private JTextArea createTextArea() {
        JTextArea area = new JTextArea(4, 30);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Arial", Font.PLAIN, 12));
        area.setForeground(TEXT_WHITE);
        area.setCaretColor(DOCTOR_GREEN);
        area.setBackground(FIELD_BG);
        area.setBorder(new EmptyBorder(8, 10, 8, 10));
        return area;
    }

    private JPanel createLabelPanel(String text, boolean locked) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(text);
        label.setForeground(TEXT_WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel status = new JLabel(locked ? "LOCKED" : "REQUIRED");
        status.setForeground(locked ? TEXT_MUTED : DOCTOR_GREEN);
        status.setFont(new Font("Arial", Font.BOLD, 8));

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 2)));
        panel.add(status);

        return panel;
    }

    private void addFieldRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String label,
            JTextField field,
            boolean locked
    ) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.31;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(createLabelPanel(label, locked), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.69;
        form.add(field, gbc);
    }

    private void addAreaRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String label,
            JScrollPane scrollPane
    ) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.31;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(createLabelPanel(label, false), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.69;
        form.add(scrollPane, gbc);
    }

    private void stylePrimaryButton(JButton button) {
        button.setPreferredSize(new Dimension(180, 38));
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
                "●  SYSTEM READY     |     PRESCRIPTION MANAGEMENT"
        );
        status.setForeground(DOCTOR_GREEN);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("AUTHORIZED MEDICATION ORDER");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);

        return bottom;
    }

    private void savePrescription() {
        String patient = patientField.getText().trim();
        String date = dateField.getText().trim();
        String medicine = medicineField.getText().trim();
        String dosage = dosageField.getText().trim();
        String instructions = instructionsArea.getText().trim();

        if (
                patient.isEmpty()
                        || date.isEmpty()
                        || medicine.isEmpty()
                        || dosage.isEmpty()
                        || instructions.isEmpty()
        ) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String prescriptionId = "PR" + System.currentTimeMillis();
        String filePath = "data/prescriptions.txt";

        try (
                FileWriter fileWriter = new FileWriter(filePath, true);
                PrintWriter writer = new PrintWriter(fileWriter)
        ) {
            writer.println(
                    prescriptionId
                            + ","
                            + patient
                            + ","
                            + doctorUsername
                            + ","
                            + date
                            + ","
                            + medicine.replace(",", ";")
                            + ","
                            + dosage.replace(",", ";")
                            + ","
                            + instructions.replace(",", ";")
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Prescription issued successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save prescription.\n" + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearFields() {
        patientField.setText("");
        dateField.setText("");
        medicineField.setText("");
        dosageField.setText("");
        instructionsArea.setText("");
    }

    private class PrescriptionBackground extends JPanel {
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
                    0, 0, new Color(0, 22, 25, 248),
                    getWidth(), 0, new Color(0, 18, 25, 218)
            );
            g2.setPaint(overlay);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(0, 18, 22, 190));
            g2.fillRect(0, 0, getWidth(), 84);

            GradientPaint accent = new GradientPaint(
                    0, 0, DOCTOR_GREEN,
                    getWidth(), 0,
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
            g2.fillOval(530, 95, 320, 320);

            g2.dispose();
        }
    }

    private class PrescriptionCard extends JPanel {
        PrescriptionCard() {
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
                    7, 8,
                    getWidth() - 11,
                    getHeight() - 11,
                    22, 22
            );

            GradientPaint gradient = new GradientPaint(
                    0, 0,
                    new Color(
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            28
                    ),
                    0, getHeight(),
                    new Color(4, 34, 38, 245)
            );

            g2.setPaint(gradient);
            g2.fillRoundRect(
                    1, 1,
                    getWidth() - 8,
                    getHeight() - 9,
                    20, 20
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
                    1, 1,
                    getWidth() - 8,
                    getHeight() - 9,
                    20, 20
            );

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
