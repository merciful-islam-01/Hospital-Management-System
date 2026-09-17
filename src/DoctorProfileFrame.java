import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DoctorProfileFrame extends JFrame {

    private final Color DOCTOR_GREEN = new Color(32, 224, 157);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(155, 185, 180);
    private final Color FIELD_BG = new Color(6, 39, 42);
    private final Color LOCKED_BG = new Color(4, 31, 34);

    private final String username;

    private JTextField userIdField;
    private JTextField nameField;
    private JTextField usernameField;
    private JTextField contactField;
    private JTextField doctorIdField;
    private JTextField specialtyField;

    private final String filePath = "data/users.txt";
    private BufferedImage backgroundImage;

    public DoctorProfileFrame(String username) {

        this.username = username;

        setTitle("APU Medical Centre - Doctor Profile");
        setSize(900, 680);
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

        ProfileBackground root = new ProfileBackground();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createBottomBar(), BorderLayout.SOUTH);

        loadProfile();
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

        JLabel portal = new JLabel("DOCTOR PORTAL");
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

        JLabel user = new JLabel(username);
        user.setForeground(TEXT_WHITE);
        user.setFont(new Font("Arial", Font.BOLD, 14));
        user.setAlignmentX(Component.RIGHT_ALIGNMENT);

        session.add(secure);
        session.add(Box.createRigidArea(new Dimension(0, 4)));
        session.add(user);

        header.add(brand, BorderLayout.WEST);
        header.add(session, BorderLayout.EAST);

        return header;
    }

    private JPanel createMainContent() {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(22, 45, 24, 45));

        ProfileCard card = new ProfileCard();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(720, 500));
        card.setBorder(new EmptyBorder(26, 34, 25, 34));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel section = new JLabel("PROFESSIONAL ACCOUNT");
        section.setForeground(DOCTOR_GREEN);
        section.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("My Profile");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Manage your doctor information and professional specialty."
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
        form.setBorder(new EmptyBorder(20, 0, 18, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        userIdField = createField(false);
        nameField = createField(true);
        usernameField = createField(false);
        contactField = createField(true);
        doctorIdField = createField(false);
        specialtyField = createField(true);

        addFormRow(form, gbc, 0, "User ID", userIdField, true);
        addFormRow(form, gbc, 1, "Name", nameField, false);
        addFormRow(form, gbc, 2, "Username", usernameField, true);
        addFormRow(form, gbc, 3, "Contact No.", contactField, false);
        addFormRow(form, gbc, 4, "Doctor ID", doctorIdField, true);
        addFormRow(form, gbc, 5, "Specialty", specialtyField, false);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);

        JButton closeButton = new JButton("CLOSE");
        styleSecondaryButton(closeButton);

        JButton saveButton = new JButton("SAVE CHANGES");
        stylePrimaryButton(saveButton);

        closeButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveProfile());

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
        field.setPreferredSize(new Dimension(390, 39));
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setForeground(
                editable ? TEXT_WHITE : new Color(125, 166, 158)
        );
        field.setCaretColor(DOCTOR_GREEN);
        field.setBackground(editable ? FIELD_BG : LOCKED_BG);
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        DOCTOR_GREEN.getRed(),
                                        DOCTOR_GREEN.getGreen(),
                                        DOCTOR_GREEN.getBlue(),
                                        editable ? 105 : 45
                                ),
                                1
                        ),
                        new EmptyBorder(0, 12, 0, 12)
                )
        );

        return field;
    }

    private void addFormRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JTextField field,
            boolean locked
    ) {

        JPanel labelPanel = new JPanel();
        labelPanel.setOpaque(false);
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setForeground(TEXT_WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel status = new JLabel(
                locked ? "LOCKED" : "EDITABLE"
        );
        status.setForeground(
                locked ? TEXT_MUTED : DOCTOR_GREEN
        );
        status.setFont(new Font("Arial", Font.BOLD, 8));

        labelPanel.add(label);
        labelPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        labelPanel.add(status);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.28;
        form.add(labelPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.72;
        form.add(field, gbc);
    }

    private void stylePrimaryButton(JButton button) {

        button.setPreferredSize(new Dimension(155, 38));
        button.setBackground(DOCTOR_GREEN);
        button.setForeground(new Color(3, 31, 29));
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
                "●  SYSTEM READY     |     PROFILE MANAGEMENT"
        );
        status.setForeground(DOCTOR_GREEN);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("SECURE DOCTOR SESSION");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);

        return bottom;
    }

    private void loadProfile() {

        try {

            Path path = Path.of(filePath);

            if (!Files.exists(path)) {
                return;
            }

            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {

                String[] data = line.split(",", -1);

                if (data.length >= 8) {

                    String userId = data[0].trim();
                    String name = data[1].trim();
                    String savedUsername = data[2].trim();
                    String contactNo = data[4].trim();
                    String role = data[5].trim();
                    String doctorId = data[6].trim();
                    String specialty = data[7].trim();

                    if (
                            savedUsername.equalsIgnoreCase(username)
                                    &&
                            role.equalsIgnoreCase("Doctor")
                    ) {

                        userIdField.setText(userId);
                        nameField.setText(name);
                        usernameField.setText(savedUsername);
                        contactField.setText(contactNo);
                        doctorIdField.setText(doctorId);
                        specialtyField.setText(specialty);

                        return;
                    }
                }
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load doctor profile.\n" + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void saveProfile() {

        String newName = nameField.getText().trim();
        String newContact = contactField.getText().trim();
        String newSpecialty = specialtyField.getText().trim();

        if (
                newName.isEmpty()
                        ||
                newContact.isEmpty()
                        ||
                newSpecialty.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Name, contact number and specialty cannot be empty.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            Path path = Path.of(filePath);
            List<String> lines = Files.readAllLines(path);

            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {

                String[] data = lines.get(i).split(",", -1);

                if (data.length >= 8) {

                    String savedUsername = data[2].trim();
                    String role = data[5].trim();

                    if (
                            savedUsername.equalsIgnoreCase(username)
                                    &&
                            role.equalsIgnoreCase("Doctor")
                    ) {

                        data[1] = newName;
                        data[4] = newContact;
                        data[7] = newSpecialty;

                        lines.set(
                                i,
                                String.join(",", data)
                        );

                        updated = true;
                        break;
                    }
                }
            }

            if (updated) {

                Files.write(path, lines);

                JOptionPane.showMessageDialog(
                        this,
                        "Doctor profile updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadProfile();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Doctor profile not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update doctor profile.\n" + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private class ProfileBackground extends JPanel {

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
                            12
                    )
            );

            g2.fillOval(520, 70, 330, 330);

            g2.dispose();
        }
    }

    private class ProfileCard extends JPanel {

        ProfileCard() {
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

            GradientPaint cardGradient = new GradientPaint(
                    0,
                    0,
                    new Color(
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            30
                    ),
                    0,
                    getHeight(),
                    new Color(4, 34, 38, 245)
            );

            g2.setPaint(cardGradient);
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
                            115
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
