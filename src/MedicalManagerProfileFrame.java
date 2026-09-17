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

public class MedicalManagerProfileFrame extends JFrame {

    private final Color MANAGER_GOLD = new Color(235, 184, 72);
    private final Color GOLD_DARK = new Color(128, 88, 18);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(174, 185, 178);
    private final Color FIELD_BG = new Color(43, 41, 32);

    private final String username;

    private JTextField userIdField;
    private JTextField nameField;
    private JTextField usernameField;
    private JTextField contactField;
    private JTextField managerIdField;

    private final String filePath = "data/users.txt";
    private BufferedImage backgroundImage;

    public MedicalManagerProfileFrame(String username) {

        this.username = username;

        setTitle("APU Medical Centre - Medical Manager Profile");
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
        cross.setBackground(new Color(255, 247, 222));
        cross.setForeground(GOLD_DARK);
        cross.setFont(new Font("Arial", Font.BOLD, 28));
        cross.setPreferredSize(new Dimension(48, 48));
        cross.setMaximumSize(new Dimension(48, 48));

        JPanel brandText = new JPanel();
        brandText.setOpaque(false);
        brandText.setLayout(new BoxLayout(brandText, BoxLayout.Y_AXIS));

        JLabel hospital = new JLabel("APU MEDICAL CENTRE");
        hospital.setForeground(TEXT_WHITE);
        hospital.setFont(new Font("Arial", Font.BOLD, 19));

        JLabel portal = new JLabel("MEDICAL MANAGER PORTAL  /  ACCOUNT PROFILE");
        portal.setForeground(MANAGER_GOLD);
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

        JLabel secure = new JLabel("●  MANAGEMENT SESSION");
        secure.setForeground(MANAGER_GOLD);
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
        wrapper.setBorder(new EmptyBorder(20, 45, 22, 45));

        ProfileCard card = new ProfileCard();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(700, 505));
        card.setBorder(new EmptyBorder(26, 36, 24, 36));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("MANAGER ACCOUNT");
        small.setForeground(MANAGER_GOLD);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("My Profile");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel subtitle = new JLabel(
                "Review your account identity and maintain your manager contact information."
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
        form.setBorder(new EmptyBorder(22, 0, 18, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        userIdField = createField(false);
        nameField = createField(true);
        usernameField = createField(false);
        contactField = createField(true);
        managerIdField = createField(false);

        addFieldRow(form, gbc, 0, "User ID", userIdField, true);
        addFieldRow(form, gbc, 1, "Name", nameField, false);
        addFieldRow(form, gbc, 2, "Username", usernameField, true);
        addFieldRow(form, gbc, 3, "Contact No.", contactField, false);
        addFieldRow(form, gbc, 4, "Manager ID", managerIdField, true);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);

        JButton closeButton = new JButton("CLOSE");
        JButton saveButton = new JButton("SAVE CHANGES");

        styleSecondaryButton(closeButton);
        stylePrimaryButton(saveButton);

        saveButton.addActionListener(e -> saveProfile());
        closeButton.addActionListener(e -> dispose());

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
                editable ? TEXT_WHITE : new Color(190, 174, 137)
        );
        field.setCaretColor(MANAGER_GOLD);
        field.setBackground(
                editable ? FIELD_BG : new Color(33, 32, 28)
        );
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        MANAGER_GOLD.getRed(),
                                        MANAGER_GOLD.getGreen(),
                                        MANAGER_GOLD.getBlue(),
                                        editable ? 105 : 48
                                )
                        ),
                        new EmptyBorder(0, 12, 0, 12)
                )
        );

        return field;
    }

    private void addFieldRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JTextField field,
            boolean locked
    ) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.31;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(createLabelPanel(labelText, locked), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.69;
        form.add(field, gbc);
    }

    private JPanel createLabelPanel(String text, boolean locked) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(text);
        label.setForeground(TEXT_WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel status = new JLabel(locked ? "LOCKED" : "EDITABLE");
        status.setForeground(locked ? TEXT_MUTED : MANAGER_GOLD);
        status.setFont(new Font("Arial", Font.BOLD, 8));

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 2)));
        panel.add(status);

        return panel;
    }

    private void stylePrimaryButton(JButton button) {
        button.setPreferredSize(new Dimension(150, 39));
        button.setBackground(MANAGER_GOLD);
        button.setForeground(new Color(39, 29, 10));
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton button) {
        button.setPreferredSize(new Dimension(100, 39));
        button.setBackground(new Color(43, 41, 32));
        button.setForeground(TEXT_WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                MANAGER_GOLD.getRed(),
                                MANAGER_GOLD.getGreen(),
                                MANAGER_GOLD.getBlue(),
                                105
                        )
                )
        );
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JPanel createBottomBar() {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(24, 24, 22, 242));
        bottom.setBorder(new EmptyBorder(9, 28, 9, 28));

        JLabel status = new JLabel(
                "●  SYSTEM READY     |     MANAGER ACCOUNT"
        );
        status.setForeground(MANAGER_GOLD);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("ROLE-BASED PROFILE ACCESS");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);

        return bottom;
    }

    private void loadProfile() {

        try {

            Path path =
                    Path.of(filePath);

            if (!Files.exists(path)) {
                return;
            }

            List<String> lines =
                    Files.readAllLines(path);

            for (String line : lines) {

                String[] data =
                        line.split(",", -1);

                if (data.length >= 8) {

                    String userId =
                            data[0].trim();

                    String name =
                            data[1].trim();

                    String savedUsername =
                            data[2].trim();

                    String contactNo =
                            data[4].trim();

                    String role =
                            data[5].trim();

                    String managerId =
                            data[6].trim();

                    if (
                        savedUsername.equalsIgnoreCase(username)
                        &&
                        role.equalsIgnoreCase(
                                "MedicalManager"
                        )
                    ) {

                        userIdField.setText(userId);
                        nameField.setText(name);
                        usernameField.setText(savedUsername);
                        contactField.setText(contactNo);
                        managerIdField.setText(managerId);

                        return;
                    }
                }
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load Medical Manager profile.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void saveProfile() {

        String newName =
                nameField.getText().trim();

        String newContact =
                contactField.getText().trim();

        if (
            newName.isEmpty()
            ||
            newContact.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Name and contact number cannot be empty.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            Path path =
                    Path.of(filePath);

            List<String> lines =
                    Files.readAllLines(path);

            boolean updated = false;

            for (
                int i = 0;
                i < lines.size();
                i++
            ) {

                String[] data =
                        lines.get(i)
                                .split(",", -1);

                if (data.length >= 8) {

                    String savedUsername =
                            data[2].trim();

                    String role =
                            data[5].trim();

                    if (
                        savedUsername.equalsIgnoreCase(username)
                        &&
                        role.equalsIgnoreCase(
                                "MedicalManager"
                        )
                    ) {

                        data[1] = newName;
                        data[4] = newContact;

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

                Files.write(
                        path,
                        lines
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Profile updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadProfile();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Medical Manager profile not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update Medical Manager profile.\n"
                            + e.getMessage(),
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

            g2.setColor(new Color(20, 21, 20));
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
                    new Color(22, 23, 21, 248),
                    getWidth(),
                    0,
                    new Color(19, 22, 22, 222)
            );
            g2.setPaint(overlay);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(19, 20, 19, 195));
            g2.fillRect(0, 0, getWidth(), 84);

            GradientPaint accent = new GradientPaint(
                    0,
                    0,
                    MANAGER_GOLD,
                    getWidth(),
                    0,
                    new Color(
                            MANAGER_GOLD.getRed(),
                            MANAGER_GOLD.getGreen(),
                            MANAGER_GOLD.getBlue(),
                            0
                    )
            );
            g2.setPaint(accent);
            g2.fillRect(0, 82, getWidth(), 2);

            g2.setColor(
                    new Color(
                            MANAGER_GOLD.getRed(),
                            MANAGER_GOLD.getGreen(),
                            MANAGER_GOLD.getBlue(),
                            10
                    )
            );
            g2.fillOval(530, 100, 320, 320);

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

            GradientPaint gradient = new GradientPaint(
                    0,
                    0,
                    new Color(
                            MANAGER_GOLD.getRed(),
                            MANAGER_GOLD.getGreen(),
                            MANAGER_GOLD.getBlue(),
                            26
                    ),
                    0,
                    getHeight(),
                    new Color(38, 37, 31, 246)
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
                            MANAGER_GOLD.getRed(),
                            MANAGER_GOLD.getGreen(),
                            MANAGER_GOLD.getBlue(),
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
