import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ConsultationNotesFrame extends JFrame {

    private final Color DOCTOR_GREEN = new Color(32, 224, 157);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(155, 185, 180);
    private final Color FIELD_BG = new Color(6, 39, 42);

    private final String doctorUsername;

    private JTextField patientField;
    private JTextField dateField;
    private JTextField bloodPressureField;
    private JTextField heartRateField;
    private JTextArea notesArea;
    private JTextArea resultArea;

    private BufferedImage backgroundImage;

    public ConsultationNotesFrame(String doctorUsername) {
        this.doctorUsername = doctorUsername;

        setTitle("APU Medical Centre - Consultation Notes");
        setSize(900, 740);
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

        ConsultationBackground root = new ConsultationBackground();
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

        JPanel brandText = new JPanel();
        brandText.setOpaque(false);
        brandText.setLayout(new BoxLayout(brandText, BoxLayout.Y_AXIS));

        JLabel hospital = new JLabel("APU MEDICAL CENTRE");
        hospital.setForeground(TEXT_WHITE);
        hospital.setFont(new Font("Arial", Font.BOLD, 19));

        JLabel portal = new JLabel("DOCTOR PORTAL  /  CONSULTATION RECORD");
        portal.setForeground(DOCTOR_GREEN);
        portal.setFont(new Font("Arial", Font.BOLD, 9));

        brandText.add(hospital);
        brandText.add(Box.createRigidArea(new Dimension(0, 3)));
        brandText.add(portal);

        brand.add(cross);
        brand.add(Box.createRigidArea(new Dimension(13, 0)));
        brand.add(brandText);

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
        wrapper.setBorder(new EmptyBorder(18, 42, 20, 42));

        ConsultationCard card = new ConsultationCard();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(735, 565));
        card.setBorder(new EmptyBorder(23, 32, 22, 32));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel section = new JLabel("CLINICAL DOCUMENTATION");
        section.setForeground(DOCTOR_GREEN);
        section.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Consultation Notes");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Document patient observations, consultation findings and diagnosis."
        );
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        heading.add(section);
        heading.add(Box.createRigidArea(new Dimension(0, 4)));
        heading.add(title);
        heading.add(Box.createRigidArea(new Dimension(0, 5)));
        heading.add(subtitle);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(15, 0, 14, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField doctorField = createField(false);
        doctorField.setText(doctorUsername);

        patientField = createField(true);
        dateField = createField(true);
        bloodPressureField = createField(true);
        heartRateField = createField(true);

        bloodPressureField.setToolTipText("Example: 120/80");

        notesArea = createTextArea(5);
        resultArea = createTextArea(3);

        JScrollPane notesScroll = createScrollPane(notesArea, 105);
        JScrollPane resultScroll = createScrollPane(resultArea, 78);

        addFieldRow(form, gbc, 0, "Doctor", doctorField, true);
        addFieldRow(form, gbc, 1, "Patient Username", patientField, false);
        addFieldRow(form, gbc, 2, "Date (DD-MM-YYYY)", dateField, false);
        addFieldRow(form, gbc, 3, "Blood Pressure", bloodPressureField, false);
        addFieldRow(form, gbc, 4, "Heart Rate", heartRateField, false);
        addAreaRow(form, gbc, 5, "Consultation Notes", notesScroll);
        addAreaRow(form, gbc, 6, "Result / Diagnosis", resultScroll);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);

        JButton closeButton = new JButton("CLOSE");
        JButton saveButton = new JButton("SAVE CONSULTATION");

        styleSecondaryButton(closeButton);
        stylePrimaryButton(saveButton);

        closeButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveConsultation());

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
        field.setPreferredSize(new Dimension(390, 36));
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setForeground(
                editable ? TEXT_WHITE : new Color(130, 178, 169)
        );
        field.setCaretColor(DOCTOR_GREEN);
        field.setBackground(
                editable ? FIELD_BG : new Color(4, 31, 34)
        );
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

    private JTextArea createTextArea(int rows) {
        JTextArea area = new JTextArea(rows, 30);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Arial", Font.PLAIN, 12));
        area.setForeground(TEXT_WHITE);
        area.setCaretColor(DOCTOR_GREEN);
        area.setBackground(FIELD_BG);
        area.setBorder(new EmptyBorder(8, 10, 8, 10));

        return area;
    }

    private JScrollPane createScrollPane(JTextArea area, int height) {
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(390, height));
        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                DOCTOR_GREEN.getRed(),
                                DOCTOR_GREEN.getGreen(),
                                DOCTOR_GREEN.getBlue(),
                                100
                        )
                )
        );
        scrollPane.getViewport().setBackground(FIELD_BG);

        return scrollPane;
    }

    private void addFieldRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JTextField field,
            boolean locked
    ) {
        JPanel labelPanel = createLabelPanel(labelText, locked);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.31;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(labelPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.69;
        form.add(field, gbc);
    }

    private void addAreaRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JScrollPane scrollPane
    ) {
        JPanel labelPanel = createLabelPanel(labelText, false);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.31;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(labelPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.69;
        form.add(scrollPane, gbc);
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

    private void stylePrimaryButton(JButton button) {
        button.setPreferredSize(new Dimension(175, 38));
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
                "●  SYSTEM READY     |     CONSULTATION DOCUMENTATION"
        );
        status.setForeground(DOCTOR_GREEN);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("CONFIDENTIAL CLINICAL RECORD");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);

        return bottom;
    }

    private void saveConsultation() {
        String patient = patientField.getText().trim();
        String date = dateField.getText().trim();
        String bloodPressure = bloodPressureField.getText().trim();
        String heartRate = heartRateField.getText().trim();
        String notes = notesArea.getText().trim();
        String result = resultArea.getText().trim();

        if (
                patient.isEmpty()
                        ||
                date.isEmpty()
                        ||
                bloodPressure.isEmpty()
                        ||
                heartRate.isEmpty()
                        ||
                notes.isEmpty()
                        ||
                result.isEmpty()
        ) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String recordId = "MR" + System.currentTimeMillis();
        String filePath = "data/medical_records.txt";

        try (
                FileWriter fileWriter = new FileWriter(filePath, true);
                PrintWriter writer = new PrintWriter(fileWriter)
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
                            + notes.replace(",", ";")
                            + ","
                            + result.replace(",", ";")
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Consultation notes saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save consultation notes.\n"
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
        notesArea.setText("");
        resultArea.setText("");
    }

    private class ConsultationBackground extends JPanel {
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
            g2.fillOval(540, 100, 320, 320);

            g2.dispose();
        }
    }

    private class ConsultationCard extends JPanel {
        ConsultationCard() {
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
