import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LabImagingRequestFrame extends JFrame {

    private final Color DOCTOR_GREEN = new Color(32, 224, 157);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(155, 185, 180);
    private final Color FIELD_BG = new Color(6, 39, 42);

    private final String doctorUsername;

    private JTextField patientField;
    private JTextField dateField;
    private JComboBox<String> requestTypeComboBox;
    private JTextArea reasonArea;

    private BufferedImage backgroundImage;

    public LabImagingRequestFrame(String doctorUsername) {
        this.doctorUsername = doctorUsername;

        setTitle("APU Medical Centre - Lab / Imaging Request");
        setSize(880, 700);
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

        RequestBackground root = new RequestBackground();
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

        JLabel portal = new JLabel("DOCTOR PORTAL  /  DIAGNOSTIC SERVICES");
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
        wrapper.setBorder(new EmptyBorder(20, 45, 22, 45));

        RequestCard card = new RequestCard();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(700, 510));
        card.setBorder(new EmptyBorder(25, 34, 24, 34));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("DIAGNOSTIC INVESTIGATION ORDER");
        small.setForeground(DOCTOR_GREEN);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Lab / Imaging Request");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Submit laboratory or imaging investigations for a patient."
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
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField doctorField = createField(false);
        doctorField.setText(doctorUsername);

        patientField = createField(true);
        dateField = createField(true);

        patientField.setToolTipText("Example: patient01");
        dateField.setToolTipText("DD-MM-YYYY");

        requestTypeComboBox = new JComboBox<>(
                new String[]{
                        "Blood Test",
                        "Urine Test",
                        "X-Ray",
                        "Ultrasound",
                        "CT Scan",
                        "MRI",
                        "ECG",
                        "Other"
                }
        );
        styleComboBox(requestTypeComboBox);

        reasonArea = new JTextArea(6, 30);
        reasonArea.setLineWrap(true);
        reasonArea.setWrapStyleWord(true);
        reasonArea.setFont(new Font("Arial", Font.PLAIN, 12));
        reasonArea.setForeground(TEXT_WHITE);
        reasonArea.setCaretColor(DOCTOR_GREEN);
        reasonArea.setBackground(FIELD_BG);
        reasonArea.setBorder(new EmptyBorder(8, 10, 8, 10));

        JScrollPane reasonScroll = new JScrollPane(reasonArea);
        reasonScroll.setPreferredSize(new Dimension(390, 115));
        reasonScroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                DOCTOR_GREEN.getRed(),
                                DOCTOR_GREEN.getGreen(),
                                DOCTOR_GREEN.getBlue(),
                                100
                        )
                )
        );
        reasonScroll.getViewport().setBackground(FIELD_BG);

        addFieldRow(form, gbc, 0, "Doctor", doctorField, true);
        addFieldRow(form, gbc, 1, "Patient Username", patientField, false);
        addFieldRow(form, gbc, 2, "Date (DD-MM-YYYY)", dateField, false);
        addComboRow(form, gbc, 3, "Request Type", requestTypeComboBox);
        addAreaRow(form, gbc, 4, "Reason / Notes", reasonScroll);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);

        JButton closeButton = new JButton("CLOSE");
        JButton submitButton = new JButton("SUBMIT REQUEST");

        styleSecondaryButton(closeButton);
        stylePrimaryButton(submitButton);

        closeButton.addActionListener(e -> dispose());
        submitButton.addActionListener(e -> saveRequest());

        buttons.add(closeButton);
        buttons.add(submitButton);

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

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setPreferredSize(new Dimension(390, 37));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox.setForeground(TEXT_WHITE);
        comboBox.setBackground(FIELD_BG);
        comboBox.setFocusable(false);
        comboBox.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                DOCTOR_GREEN.getRed(),
                                DOCTOR_GREEN.getGreen(),
                                DOCTOR_GREEN.getBlue(),
                                100
                        )
                )
        );
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

    private void addComboRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String label,
            JComboBox<String> comboBox
    ) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.31;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(createLabelPanel(label, false), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.69;
        form.add(comboBox, gbc);
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
        button.setPreferredSize(new Dimension(160, 38));
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
                "●  SYSTEM READY     |     DIAGNOSTIC REQUESTS"
        );
        status.setForeground(DOCTOR_GREEN);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("AUTHORIZED CLINICAL ORDER");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);

        return bottom;
    }

    private void saveRequest() {
        String patient = patientField.getText().trim();
        String date = dateField.getText().trim();

        String requestType =
                requestTypeComboBox
                        .getSelectedItem()
                        .toString();

        String reason = reasonArea.getText().trim();

        if (
                patient.isEmpty()
                        ||
                date.isEmpty()
                        ||
                reason.isEmpty()
        ) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String requestId = "LR" + System.currentTimeMillis();
        String status = "Pending";
        String filePath = "data/lab_requests.txt";

        try (
                FileWriter fileWriter =
                        new FileWriter(filePath, true);

                PrintWriter writer =
                        new PrintWriter(fileWriter)
        ) {
            writer.println(
                    requestId
                            + ","
                            + patient
                            + ","
                            + doctorUsername
                            + ","
                            + date
                            + ","
                            + requestType.replace(",", ";")
                            + ","
                            + reason.replace(",", ";")
                            + ","
                            + status
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Lab / imaging request submitted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save request.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearFields() {
        patientField.setText("");
        dateField.setText("");
        reasonArea.setText("");
        requestTypeComboBox.setSelectedIndex(0);
    }

    private class RequestBackground extends JPanel {
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
            g2.fillOval(530, 95, 320, 320);

            g2.dispose();
        }
    }

    private class RequestCard extends JPanel {
        RequestCard() {
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
