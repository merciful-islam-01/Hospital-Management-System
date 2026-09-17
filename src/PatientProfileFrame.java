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

public class PatientProfileFrame extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private final Color PATIENT_BLUE =
            new Color(52, 169, 255);

    private final Color EMERALD =
            new Color(32, 224, 157);

    private final Color TEXT_WHITE =
            new Color(240, 248, 245);

    private final Color TEXT_MUTED =
            new Color(155, 185, 180);

    private final Color FIELD_BACKGROUND =
            new Color(5, 37, 42);

    private final Color LOCKED_BACKGROUND =
            new Color(4, 29, 33);

    // =========================================================
    // USER
    // =========================================================

    private final String username;

    // =========================================================
    // FIELDS
    // =========================================================

    private JTextField userIdField;
    private JTextField nameField;
    private JTextField usernameField;
    private JTextField contactField;
    private JTextField patientIdField;

    // =========================================================
    // DATA FILE
    // =========================================================

    private final String filePath =
            "data/users.txt";

    // =========================================================
    // BACKGROUND IMAGE
    // =========================================================

    private BufferedImage backgroundImage;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public PatientProfileFrame(String username) {

        this.username = username;

        // =====================================================
        // WINDOW SETTINGS
        // =====================================================

        setTitle(
                "APU Medical Centre - My Profile"
        );

        setSize(
                820,
                650
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setResizable(false);

        // =====================================================
        // LOAD BACKGROUND
        // =====================================================

        try {

            backgroundImage =
                    ImageIO.read(
                            new File(
                                    "assets/hospital_portal_bg.png"
                            )
                    );

        } catch (Exception e) {

            backgroundImage = null;
        }

        // =====================================================
        // ROOT PANEL
        // =====================================================

        ProfileBackground root =
                new ProfileBackground();

        root.setLayout(
                new BorderLayout()
        );

        setContentPane(root);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                createHeader();

        // =====================================================
        // MAIN CONTENT
        // =====================================================

        JPanel contentWrapper =
                new JPanel(
                        new GridBagLayout()
                );

        contentWrapper.setOpaque(false);

        contentWrapper.setBorder(
                new EmptyBorder(
                        20,
                        55,
                        20,
                        55
                )
        );

        ProfileCard profileCard =
                createProfileCard();

        contentWrapper.add(
                profileCard
        );

        // =====================================================
        // BOTTOM STATUS
        // =====================================================

        JPanel bottomBar =
                createBottomBar();

        root.add(
                header,
                BorderLayout.NORTH
        );

        root.add(
                contentWrapper,
                BorderLayout.CENTER
        );

        root.add(
                bottomBar,
                BorderLayout.SOUTH
        );

        // =====================================================
        // LOAD USER DATA
        // =====================================================

        loadProfile();
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setOpaque(false);

        header.setBorder(
                new EmptyBorder(
                        18,
                        28,
                        17,
                        30
                )
        );

        // =====================================================
        // LEFT BRAND
        // =====================================================

        JPanel left =
                new JPanel();

        left.setOpaque(false);

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.X_AXIS
                )
        );

        JLabel cross =
                new JLabel("+");

        cross.setOpaque(true);

        cross.setBackground(
                new Color(
                        225,
                        255,
                        245
                )
        );

        cross.setForeground(
                new Color(
                        10,
                        105,
                        72
                )
        );

        cross.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        cross.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );

        cross.setPreferredSize(
                new Dimension(
                        46,
                        46
                )
        );

        cross.setMaximumSize(
                new Dimension(
                        46,
                        46
                )
        );

        JPanel brandText =
                new JPanel();

        brandText.setOpaque(false);

        brandText.setLayout(
                new BoxLayout(
                        brandText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel hospitalName =
                new JLabel(
                        "APU MEDICAL CENTRE"
                );

        hospitalName.setForeground(
                TEXT_WHITE
        );

        hospitalName.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        JLabel systemName =
                new JLabel(
                        "PATIENT PORTAL"
                );

        systemName.setForeground(
                PATIENT_BLUE
        );

        systemName.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        brandText.add(
                hospitalName
        );

        brandText.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                3
                        )
                )
        );

        brandText.add(
                systemName
        );

        left.add(
                cross
        );

        left.add(
                Box.createRigidArea(
                        new Dimension(
                                13,
                                0
                        )
                )
        );

        left.add(
                brandText
        );

        // =====================================================
        // RIGHT
        // =====================================================

        JPanel right =
                new JPanel();

        right.setOpaque(false);

        right.setLayout(
                new BoxLayout(
                        right,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel secureLabel =
                new JLabel(
                        "●  SECURE SESSION"
                );

        secureLabel.setForeground(
                EMERALD
        );

        secureLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        secureLabel.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        JLabel userLabel =
                new JLabel(
                        username
                );

        userLabel.setForeground(
                TEXT_WHITE
        );

        userLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        userLabel.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        right.add(
                secureLabel
        );

        right.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );

        right.add(
                userLabel
        );

        header.add(
                left,
                BorderLayout.WEST
        );

        header.add(
                right,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================================================
    // PROFILE CARD
    // =========================================================

    private ProfileCard createProfileCard() {

        ProfileCard card =
                new ProfileCard();

        card.setPreferredSize(
                new Dimension(
                        680,
                        460
                )
        );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        25,
                        35,
                        25,
                        35
                )
        );

        // =====================================================
        // CARD HEADER
        // =====================================================

        JPanel cardHeader =
                new JPanel(
                        new BorderLayout()
                );

        cardHeader.setOpaque(false);

        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel sectionLabel =
                new JLabel(
                        "PERSONAL INFORMATION"
                );

        sectionLabel.setForeground(
                PATIENT_BLUE
        );

        sectionLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        JLabel title =
                new JLabel(
                        "My Profile"
                );

        title.setForeground(
                TEXT_WHITE
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Review your account details and update your personal information."
                );

        subtitle.setForeground(
                TEXT_MUTED
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        titlePanel.add(
                sectionLabel
        );

        titlePanel.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );

        titlePanel.add(
                title
        );

        titlePanel.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );

        titlePanel.add(
                subtitle
        );

        ProfileIcon profileIcon =
                new ProfileIcon();

        profileIcon.setPreferredSize(
                new Dimension(
                        65,
                        65
                )
        );

        cardHeader.add(
                titlePanel,
                BorderLayout.WEST
        );

        cardHeader.add(
                profileIcon,
                BorderLayout.EAST
        );

        // =====================================================
        // FORM
        // =====================================================

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        formPanel.setOpaque(false);

        formPanel.setBorder(
                new EmptyBorder(
                        20,
                        0,
                        15,
                        0
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets =
                new Insets(
                        7,
                        7,
                        7,
                        7
                );

        gbc.weightx =
                1.0;

        // =====================================================
        // CREATE FIELDS
        // =====================================================

        userIdField =
                createField(
                        false
                );

        nameField =
                createField(
                        true
                );

        usernameField =
                createField(
                        false
                );

        contactField =
                createField(
                        true
                );

        patientIdField =
                createField(
                        false
                );

        // =====================================================
        // ROW 1 - USER ID
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.30;

        formPanel.add(
                createFieldLabel(
                        "USER ID"
                ),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.70;

        formPanel.add(
                userIdField,
                gbc
        );

        // =====================================================
        // ROW 2 - NAME
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.30;

        formPanel.add(
                createFieldLabel(
                        "FULL NAME"
                ),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.70;

        formPanel.add(
                nameField,
                gbc
        );

        // =====================================================
        // ROW 3 - USERNAME
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.30;

        formPanel.add(
                createFieldLabel(
                        "USERNAME"
                ),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.70;

        formPanel.add(
                usernameField,
                gbc
        );

        // =====================================================
        // ROW 4 - CONTACT
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.30;

        formPanel.add(
                createFieldLabel(
                        "CONTACT NO."
                ),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.70;

        formPanel.add(
                contactField,
                gbc
        );

        // =====================================================
        // ROW 5 - PATIENT ID
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.30;

        formPanel.add(
                createFieldLabel(
                        "PATIENT ID"
                ),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.70;

        formPanel.add(
                patientIdField,
                gbc
        );

        // =====================================================
        // BUTTON AREA
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        JButton closeButton =
                createSecondaryButton(
                        "CLOSE"
                );

        JButton saveButton =
                createPrimaryButton(
                        "SAVE CHANGES"
                );

        closeButton.addActionListener(
                e -> dispose()
        );

        saveButton.addActionListener(
                e -> saveProfile()
        );

        buttonPanel.add(
                closeButton
        );

        buttonPanel.add(
                saveButton
        );

        // =====================================================
        // ADD TO CARD
        // =====================================================

        card.add(
                cardHeader,
                BorderLayout.NORTH
        );

        card.add(
                formPanel,
                BorderLayout.CENTER
        );

        card.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // FIELD LABEL
    // =========================================================

    private JLabel createFieldLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setForeground(
                new Color(
                        185,
                        211,
                        205
                )
        );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        return label;
    }

    // =========================================================
    // TEXT FIELD
    // =========================================================

    private JTextField createField(
            boolean editable
    ) {

        JTextField field =
                new JTextField();

        field.setEditable(
                editable
        );

        field.setPreferredSize(
                new Dimension(
                        300,
                        38
                )
        );

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        field.setForeground(
                editable
                        ? TEXT_WHITE
                        : new Color(
                                130,
                                165,
                                160
                        )
        );

        field.setCaretColor(
                PATIENT_BLUE
        );

        field.setBackground(
                editable
                        ? FIELD_BACKGROUND
                        : LOCKED_BACKGROUND
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                editable
                                        ? new Color(
                                                PATIENT_BLUE.getRed(),
                                                PATIENT_BLUE.getGreen(),
                                                PATIENT_BLUE.getBlue(),
                                                130
                                        )
                                        : new Color(
                                                75,
                                                105,
                                                105
                                        ),
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                7,
                                12,
                                7,
                                12
                        )
                )
        );

        return field;
    }

    // =========================================================
    // PRIMARY BUTTON
    // =========================================================

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(
                        145,
                        38
                )
        );

        button.setBackground(
                PATIENT_BLUE
        );

        button.setForeground(
                new Color(
                        3,
                        25,
                        31
                )
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // SECONDARY BUTTON
    // =========================================================

    private JButton createSecondaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(
                        100,
                        38
                )
        );

        button.setBackground(
                new Color(
                        7,
                        39,
                        43
                )
        );

        button.setForeground(
                TEXT_WHITE
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                PATIENT_BLUE.getRed(),
                                PATIENT_BLUE.getGreen(),
                                PATIENT_BLUE.getBlue(),
                                120
                        ),
                        1
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // BOTTOM BAR
    // =========================================================

    private JPanel createBottomBar() {

        JPanel bottom =
                new JPanel(
                        new BorderLayout()
                );

        bottom.setBackground(
                new Color(
                        2,
                        27,
                        30,
                        240
                )
        );

        bottom.setBorder(
                new EmptyBorder(
                        9,
                        25,
                        9,
                        25
                )
        );

        JLabel status =
                new JLabel(
                        "●  SYSTEM READY     |     PROFILE ACCESS"
                );

        status.setForeground(
                EMERALD
        );

        status.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        JLabel secure =
                new JLabel(
                        "SECURE PATIENT SESSION"
                );

        secure.setForeground(
                TEXT_MUTED
        );

        secure.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        bottom.add(
                status,
                BorderLayout.WEST
        );

        bottom.add(
                secure,
                BorderLayout.EAST
        );

        return bottom;
    }

    // =========================================================
    // LOAD PROFILE
    // =========================================================

    private void loadProfile() {

        try {

            Path path =
                    Path.of(filePath);

            if (
                !Files.exists(path)
            ) {

                return;
            }

            List<String> lines =
                    Files.readAllLines(path);

            for (
                String line : lines
            ) {

                String[] data =
                        line.split(",");

                if (
                    data.length >= 8
                ) {

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

                    String roleId =
                            data[6].trim();

                    if (
                        savedUsername.equalsIgnoreCase(
                                username
                        )
                        &&
                        role.equalsIgnoreCase(
                                "Patient"
                        )
                    ) {

                        userIdField.setText(
                                userId
                        );

                        nameField.setText(
                                name
                        );

                        usernameField.setText(
                                savedUsername
                        );

                        contactField.setText(
                                contactNo
                        );

                        patientIdField.setText(
                                roleId
                        );

                        return;
                    }
                }
            }

        } catch (
            IOException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load profile.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // SAVE PROFILE
    // =========================================================

    private void saveProfile() {

        String newName =
                nameField
                        .getText()
                        .trim();

        String newContact =
                contactField
                        .getText()
                        .trim();

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

            boolean updated =
                    false;

            for (
                int i = 0;
                i < lines.size();
                i++
            ) {

                String[] data =
                        lines
                                .get(i)
                                .split(",");

                if (
                    data.length >= 8
                ) {

                    String savedUsername =
                            data[2].trim();

                    String role =
                            data[5].trim();

                    if (
                        savedUsername.equalsIgnoreCase(
                                username
                        )
                        &&
                        role.equalsIgnoreCase(
                                "Patient"
                        )
                    ) {

                        data[1] =
                                newName;

                        data[4] =
                                newContact;

                        lines.set(
                                i,
                                String.join(
                                        ",",
                                        data
                                )
                        );

                        updated =
                                true;

                        break;
                    }
                }
            }

            if (
                updated
            ) {

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
                        "Patient profile not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (
            IOException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update profile.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // BACKGROUND PANEL
    // =========================================================

    private class ProfileBackground
            extends JPanel {

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY
            );

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // =================================================
            // BASE
            // =================================================

            g2.setColor(
                    new Color(
                            2,
                            22,
                            24
                    )
            );

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );

            // =================================================
            // HOSPITAL IMAGE
            // =================================================

            if (
                backgroundImage != null
            ) {

                g2.drawImage(
                        backgroundImage,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        null
                );
            }

            // =================================================
            // DARK OVERLAY
            // =================================================

            GradientPaint overlay =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    0,
                                    22,
                                    25,
                                    248
                            ),
                            getWidth(),
                            0,
                            new Color(
                                    0,
                                    18,
                                    25,
                                    218
                            )
                    );

            g2.setPaint(
                    overlay
            );

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );

            // =================================================
            // HEADER
            // =================================================

            g2.setColor(
                    new Color(
                            0,
                            18,
                            22,
                            190
                    )
            );

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    82
            );

            // =================================================
            // BLUE ACCENT
            // =================================================

            GradientPaint accent =
                    new GradientPaint(
                            0,
                            0,
                            PATIENT_BLUE,
                            getWidth(),
                            0,
                            new Color(
                                    PATIENT_BLUE.getRed(),
                                    PATIENT_BLUE.getGreen(),
                                    PATIENT_BLUE.getBlue(),
                                    0
                            )
                    );

            g2.setPaint(
                    accent
            );

            g2.fillRect(
                    0,
                    80,
                    getWidth(),
                    2
            );

            // =================================================
            // DECORATIVE BLUE GLOW
            // =================================================

            g2.setColor(
                    new Color(
                            PATIENT_BLUE.getRed(),
                            PATIENT_BLUE.getGreen(),
                            PATIENT_BLUE.getBlue(),
                            12
                    )
            );

            g2.fillOval(
                    450,
                    60,
                    400,
                    400
            );

            g2.dispose();
        }
    }

    // =========================================================
    // PROFILE CARD
    // =========================================================

    private class ProfileCard
            extends JPanel {

        public ProfileCard() {

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int w =
                    getWidth();

            int h =
                    getHeight();

            // =================================================
            // SHADOW
            // =================================================

            g2.setColor(
                    new Color(
                            0,
                            0,
                            0,
                            110
                    )
            );

            g2.fillRoundRect(
                    7,
                    8,
                    w - 12,
                    h - 12,
                    24,
                    24
            );

            // =================================================
            // CARD BACKGROUND
            // =================================================

            GradientPaint cardGradient =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    5,
                                    42,
                                    47,
                                    242
                            ),
                            0,
                            h,
                            new Color(
                                    3,
                                    27,
                                    32,
                                    246
                            )
                    );

            g2.setPaint(
                    cardGradient
            );

            g2.fillRoundRect(
                    1,
                    1,
                    w - 10,
                    h - 11,
                    22,
                    22
            );

            // =================================================
            // BORDER
            // =================================================

            g2.setColor(
                    new Color(
                            PATIENT_BLUE.getRed(),
                            PATIENT_BLUE.getGreen(),
                            PATIENT_BLUE.getBlue(),
                            130
                    )
            );

            g2.setStroke(
                    new BasicStroke(
                            1.2f
                    )
            );

            g2.drawRoundRect(
                    1,
                    1,
                    w - 10,
                    h - 11,
                    22,
                    22
            );

            // =================================================
            // TOP BLUE GLOW
            // =================================================

            GradientPaint glow =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    PATIENT_BLUE.getRed(),
                                    PATIENT_BLUE.getGreen(),
                                    PATIENT_BLUE.getBlue(),
                                    60
                            ),
                            w,
                            0,
                            new Color(
                                    PATIENT_BLUE.getRed(),
                                    PATIENT_BLUE.getGreen(),
                                    PATIENT_BLUE.getBlue(),
                                    0
                            )
                    );

            g2.setPaint(
                    glow
            );

            g2.fillRoundRect(
                    1,
                    1,
                    w - 10,
                    80,
                    22,
                    22
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // PROFILE ICON
    // =========================================================

    private class ProfileIcon
            extends JPanel {

        public ProfileIcon() {

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    new Color(
                            PATIENT_BLUE.getRed(),
                            PATIENT_BLUE.getGreen(),
                            PATIENT_BLUE.getBlue(),
                            35
                    )
            );

            g2.fillOval(
                    5,
                    5,
                    54,
                    54
            );

            g2.setColor(
                    PATIENT_BLUE
            );

            g2.setStroke(
                    new BasicStroke(
                            1.5f
                    )
            );

            g2.drawOval(
                    5,
                    5,
                    54,
                    54
            );

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            23
                    )
            );

            FontMetrics fm =
                    g2.getFontMetrics();

            String letter =
                    "P";

            int x =
                    5
                    +
                    (
                        54
                        -
                        fm.stringWidth(
                                letter
                        )
                    ) / 2;

            int y =
                    5
                    +
                    (
                        54
                        +
                        fm.getAscent()
                        -
                        fm.getDescent()
                    ) / 2;

            g2.drawString(
                    letter,
                    x,
                    y
            );

            g2.dispose();
        }
    }
}