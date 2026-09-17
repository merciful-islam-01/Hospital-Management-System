import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {

    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTextField usernameField;
    private JPasswordField passwordField;

    // =========================================================
    // SELECTED PORTAL
    // =========================================================

    private String selectedRole;

    // =========================================================
    // COLORS
    // =========================================================

    private final Color EMERALD =
            new Color(32, 224, 157);

    private final Color DOCTOR_COLOR =
            new Color(27, 220, 145);

    private final Color PATIENT_COLOR =
            new Color(52, 169, 255);

    private final Color MANAGER_COLOR =
            new Color(235, 171, 43);

    private final Color ADMIN_COLOR =
            new Color(188, 77, 232);

    private final Color TEXT_WHITE =
            new Color(240, 248, 245);

    private final Color TEXT_MUTED =
            new Color(160, 188, 180);

    private Color roleColor;

    // =========================================================
    // BACKGROUND IMAGE
    // =========================================================

    private BufferedImage backgroundImage;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public LoginFrame(String selectedRole) {

        this.selectedRole =
                selectedRole;

        this.roleColor =
                getRoleColor(
                        selectedRole
                );

        setTitle(
                "APU Medical Centre - "
                        + selectedRole
                        + " Portal"
        );

        setSize(
                1280,
                720
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setResizable(false);

        // =====================================================
        // LOAD BACKGROUND IMAGE
        // =====================================================

        try {

            backgroundImage =
                    ImageIO.read(
                            new File(
                                    "assets/hospital_portal_bg.png"
                            )
                    );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load hospital background image.\n"
                            + "Expected location:\n"
                            + "assets/hospital_portal_bg.png",
                    "Image Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        // =====================================================
        // MAIN BACKGROUND
        // =====================================================

        BackgroundPanel background =
                new BackgroundPanel();

        background.setLayout(
                new BorderLayout()
        );

        setContentPane(
                background
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                createHeader();

        // =====================================================
        // MAIN CONTENT
        // =====================================================

        JPanel mainContent =
                new JPanel(
                        new BorderLayout()
                );

        mainContent.setOpaque(false);

        mainContent.setBorder(
                new EmptyBorder(
                        25,
                        55,
                        25,
                        70
                )
        );

        // =====================================================
        // LEFT INFORMATION AREA
        // =====================================================

        JPanel leftArea =
                createLeftArea();

        // =====================================================
        // LOGIN AREA
        // =====================================================

        JPanel loginArea =
                new JPanel(
                        new GridBagLayout()
                );

        loginArea.setOpaque(false);

        loginArea.setPreferredSize(
                new Dimension(
                        470,
                        0
                )
        );

        LoginCard loginCard =
                new LoginCard();

        loginCard.setPreferredSize(
                new Dimension(
                        410,
                        480
                )
        );

        loginCard.setLayout(
                new BorderLayout()
        );

        loginCard.setBorder(
                new EmptyBorder(
                        28,
                        38,
                        25,
                        38
                )
        );

        createLoginContents(
                loginCard
        );

        loginArea.add(
                loginCard
        );

        // =====================================================
        // ADD MAIN AREAS
        // =====================================================

        mainContent.add(
                leftArea,
                BorderLayout.CENTER
        );

        mainContent.add(
                loginArea,
                BorderLayout.EAST
        );

        // =====================================================
        // BOTTOM STATUS BAR
        // =====================================================

        JPanel bottom =
                createBottomBar();

        // =====================================================
        // ADD EVERYTHING
        // =====================================================

        background.add(
                header,
                BorderLayout.NORTH
        );

        background.add(
                mainContent,
                BorderLayout.CENTER
        );

        background.add(
                bottom,
                BorderLayout.SOUTH
        );
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
                        12,
                        30
                )
        );

        // =====================================================
        // BRAND
        // =====================================================

        JPanel brandPanel =
                new JPanel();

        brandPanel.setOpaque(false);

        brandPanel.setLayout(
                new BoxLayout(
                        brandPanel,
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
                        34
                )
        );

        cross.setPreferredSize(
                new Dimension(
                        55,
                        55
                )
        );

        cross.setMaximumSize(
                new Dimension(
                        55,
                        55
                )
        );

        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel hospitalName =
                new JLabel(
                        "APU MEDICAL CENTRE"
                );

        hospitalName.setForeground(
                Color.WHITE
        );

        hospitalName.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        23
                )
        );

        JLabel systemName =
                new JLabel(
                        "HOSPITAL MANAGEMENT SYSTEM"
                );

        systemName.setForeground(
                new Color(
                        135,
                        219,
                        191
                )
        );

        systemName.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        titlePanel.add(
                hospitalName
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
                systemName
        );

        brandPanel.add(
                cross
        );

        brandPanel.add(
                Box.createRigidArea(
                        new Dimension(
                                14,
                                0
                        )
                )
        );

        brandPanel.add(
                titlePanel
        );

        // =====================================================
        // LIVE STATUS
        // =====================================================

        JPanel livePanel =
                new JPanel();

        livePanel.setOpaque(false);

        livePanel.setLayout(
                new BoxLayout(
                        livePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel live =
                new JLabel(
                        "●  LIVE"
                );

        live.setForeground(
                EMERALD
        );

        live.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        live.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        JLabel secure =
                new JLabel(
                        "Secure  |  Reliable  |  Trusted"
                );

        secure.setForeground(
                new Color(
                        210,
                        230,
                        223
                )
        );

        secure.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        secure.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        livePanel.add(
                live
        );

        livePanel.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );

        livePanel.add(
                secure
        );

        header.add(
                brandPanel,
                BorderLayout.WEST
        );

        header.add(
                livePanel,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================================================
    // LEFT AREA
    // =========================================================

    private JPanel createLeftArea() {

        JPanel leftArea =
                new JPanel();

        leftArea.setOpaque(false);

        leftArea.setLayout(
                new BoxLayout(
                        leftArea,
                        BoxLayout.Y_AXIS
                )
        );

        leftArea.setBorder(
                new EmptyBorder(
                        55,
                        30,
                        20,
                        20
                )
        );

        // =====================================================
        // SMALL TITLE
        // =====================================================

        JLabel secureAccess =
                new JLabel(
                        "SECURE HOSPITAL ACCESS"
                );

        secureAccess.setForeground(
                roleColor
        );

        secureAccess.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        secureAccess.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // =====================================================
        // MAIN TITLE
        // =====================================================

        JLabel mainTitle =
                new JLabel(
                        "<html>"
                                + "Care Beyond<br>"
                                + "Treatment"
                                + "</html>"
                );

        mainTitle.setForeground(
                new Color(
                        225,
                        247,
                        240
                )
        );

        mainTitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        39
                )
        );

        mainTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // =====================================================
        // ACCENT LINE
        // =====================================================

        JPanel accentLine =
                new JPanel();

        accentLine.setBackground(
                roleColor
        );

        accentLine.setMaximumSize(
                new Dimension(
                        95,
                        3
                )
        );

        accentLine.setPreferredSize(
                new Dimension(
                        95,
                        3
                )
        );

        accentLine.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // =====================================================
        // DESCRIPTION
        // =====================================================

        JLabel description =
                new JLabel(
                        "<html>"
                                + "A secure digital environment designed for<br>"
                                + "efficient hospital operations, clinical care<br>"
                                + "and trusted patient services."
                                + "</html>"
                );

        description.setForeground(
                new Color(
                        198,
                        220,
                        213
                )
        );

        description.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        description.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // =====================================================
        // PORTAL INDICATOR
        // =====================================================

        JPanel portalIndicator =
                createPortalIndicator();

        portalIndicator.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // =====================================================
        // ADD
        // =====================================================

        leftArea.add(
                secureAccess
        );

        leftArea.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                15
                        )
                )
        );

        leftArea.add(
                mainTitle
        );

        leftArea.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                22
                        )
                )
        );

        leftArea.add(
                accentLine
        );

        leftArea.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                22
                        )
                )
        );

        leftArea.add(
                description
        );

        leftArea.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                35
                        )
                )
        );

        leftArea.add(
                portalIndicator
        );

        return leftArea;
    }

    // =========================================================
    // PORTAL INDICATOR
    // =========================================================

    private JPanel createPortalIndicator() {

        JPanel panel =
                new JPanel();

        panel.setOpaque(false);

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.X_AXIS
                )
        );

        panel.setMaximumSize(
                new Dimension(
                        350,
                        65
                )
        );

        // =====================================================
        // ROLE ICON
        // =====================================================

        RoleCircle roleCircle =
                new RoleCircle(
                        getRoleLetter(
                                selectedRole
                        )
                );

        roleCircle.setPreferredSize(
                new Dimension(
                        55,
                        55
                )
        );

        roleCircle.setMinimumSize(
                new Dimension(
                        55,
                        55
                )
        );

        roleCircle.setMaximumSize(
                new Dimension(
                        55,
                        55
                )
        );

        // =====================================================
        // ROLE TEXT
        // =====================================================

        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel active =
                new JLabel(
                        "ACTIVE PORTAL"
                );

        active.setForeground(
                new Color(
                        150,
                        180,
                        172
                )
        );

        active.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        JLabel role =
                new JLabel(
                        selectedRole.toUpperCase()
                                + " PORTAL"
                );

        role.setForeground(
                roleColor
        );

        role.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        textPanel.add(
                active
        );

        textPanel.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );

        textPanel.add(
                role
        );

        panel.add(
                roleCircle
        );

        panel.add(
                Box.createRigidArea(
                        new Dimension(
                                14,
                                0
                        )
                )
        );

        panel.add(
                textPanel
        );

        return panel;
    }

    // =========================================================
    // LOGIN CARD CONTENT
    // =========================================================

    private void createLoginContents(
            JPanel loginCard
    ) {

        JPanel content =
                new JPanel();

        content.setOpaque(false);

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        // =====================================================
        // SECURITY LABEL
        // =====================================================

        JLabel secureLabel =
                new JLabel(
                        "SECURE ACCESS"
                );

        secureLabel.setForeground(
                roleColor
        );

        secureLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        secureLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // =====================================================
        // ROLE TITLE
        // =====================================================

        JLabel portalTitle =
                new JLabel(
                        selectedRole.toUpperCase()
                                + " PORTAL"
                );

        portalTitle.setForeground(
                TEXT_WHITE
        );

        portalTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        selectedRole.equalsIgnoreCase(
                                "Medical Manager"
                        )
                                ? 24
                                : 28
                )
        );

        portalTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // =====================================================
        // SUBTITLE
        // =====================================================

        JLabel portalSubtitle =
                new JLabel(
                        getRoleSubtitle(
                                selectedRole
                        )
                );

        portalSubtitle.setForeground(
                roleColor
        );

        portalSubtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        portalSubtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // =====================================================
        // DIVIDER
        // =====================================================

        JPanel divider =
                new JPanel();

        divider.setBackground(
                new Color(
                        roleColor.getRed(),
                        roleColor.getGreen(),
                        roleColor.getBlue(),
                        140
                )
        );

        divider.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        1
                )
        );

        divider.setPreferredSize(
                new Dimension(
                        300,
                        1
                )
        );

        divider.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // =====================================================
        // USERNAME
        // =====================================================

        JLabel usernameLabel =
                createFieldLabel(
                        "USERNAME"
                );

        usernameField =
                createTextField();

        // =====================================================
        // PASSWORD
        // =====================================================

        JLabel passwordLabel =
                createFieldLabel(
                        "PASSWORD"
                );

        passwordField =
                createPasswordField();

        // =====================================================
        // SHOW PASSWORD
        // =====================================================

        JCheckBox showPassword =
                new JCheckBox(
                        "Show password"
                );

        showPassword.setOpaque(false);

        showPassword.setForeground(
                new Color(
                        180,
                        205,
                        198
                )
        );

        showPassword.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        showPassword.setFocusPainted(
                false
        );

        showPassword.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        showPassword.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        char defaultEcho =
                passwordField.getEchoChar();

        showPassword.addActionListener(
                e -> {

                    if (
                        showPassword.isSelected()
                    ) {

                        passwordField.setEchoChar(
                                (char) 0
                        );

                    } else {

                        passwordField.setEchoChar(
                                defaultEcho
                        );
                    }
                }
        );

        // =====================================================
        // LOGIN BUTTON
        // =====================================================

        JButton loginButton =
                createLoginButton();

        loginButton.addActionListener(
                e -> login()
        );

        // =====================================================
        // BACK BUTTON
        // =====================================================

        JButton backButton =
                createBackButton();

        backButton.addActionListener(
                e -> backToRoleSelection()
        );

        // =====================================================
        // SECURITY FOOTER
        // =====================================================

        JLabel securityFooter =
                new JLabel(
                        "●  ROLE-BASED AUTHENTICATION ENABLED"
                );

        securityFooter.setForeground(
                new Color(
                        135,
                        165,
                        157
                )
        );

        securityFooter.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        securityFooter.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // =====================================================
        // ADD COMPONENTS
        // =====================================================

        content.add(
                secureLabel
        );

        content.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                7
                        )
                )
        );

        content.add(
                portalTitle
        );

        content.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );

        content.add(
                portalSubtitle
        );

        content.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                18
                        )
                )
        );

        content.add(
                divider
        );

        content.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                20
                        )
                )
        );

        content.add(
                usernameLabel
        );

        content.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                6
                        )
                )
        );

        content.add(
                usernameField
        );

        content.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                14
                        )
                )
        );

        content.add(
                passwordLabel
        );

        content.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                6
                        )
                )
        );

        content.add(
                passwordField
        );

        content.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                8
                        )
                )
        );

        content.add(
                showPassword
        );

        content.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                18
                        )
                )
        );

        content.add(
                loginButton
        );

        content.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                10
                        )
                )
        );

        content.add(
                backButton
        );

        content.add(
                Box.createVerticalGlue()
        );

        content.add(
                securityFooter
        );

        loginCard.add(
                content,
                BorderLayout.CENTER
        );

        // =====================================================
        // ENTER KEY LOGIN
        // =====================================================

        getRootPane().setDefaultButton(
                loginButton
        );
    }

    // =========================================================
    // FIELD LABEL
    // =========================================================

    private JLabel createFieldLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setForeground(
                new Color(
                        200,
                        220,
                        214
                )
        );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }

    // =========================================================
    // USERNAME FIELD
    // =========================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        43
                )
        );

        field.setPreferredSize(
                new Dimension(
                        320,
                        43
                )
        );

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        field.setForeground(
                new Color(
                        230,
                        245,
                        240
                )
        );

        field.setCaretColor(
                roleColor
        );

        field.setBackground(
                new Color(
                        7,
                        39,
                        39
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        roleColor.getRed(),
                                        roleColor.getGreen(),
                                        roleColor.getBlue(),
                                        130
                                ),
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                12,
                                8,
                                12
                        )
                )
        );

        field.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return field;
    }

    // =========================================================
    // PASSWORD FIELD
    // =========================================================

    private JPasswordField createPasswordField() {

        JPasswordField field =
                new JPasswordField();

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        43
                )
        );

        field.setPreferredSize(
                new Dimension(
                        320,
                        43
                )
        );

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        field.setForeground(
                new Color(
                        230,
                        245,
                        240
                )
        );

        field.setCaretColor(
                roleColor
        );

        field.setBackground(
                new Color(
                        7,
                        39,
                        39
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        roleColor.getRed(),
                                        roleColor.getGreen(),
                                        roleColor.getBlue(),
                                        130
                                ),
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                12,
                                8,
                                12
                        )
                )
        );

        field.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return field;
    }

    // =========================================================
    // LOGIN BUTTON
    // =========================================================

    private JButton createLoginButton() {

        JButton button =
                new JButton(
                        "SECURE LOGIN   >"
                );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        46
                )
        );

        button.setPreferredSize(
                new Dimension(
                        320,
                        46
                )
        );

        button.setBackground(
                roleColor
        );

        button.setForeground(
                new Color(
                        3,
                        28,
                        26
                )
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(
                false
        );

        button.setBorderPainted(
                false
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return button;
    }

    // =========================================================
    // BACK BUTTON
    // =========================================================

    private JButton createBackButton() {

        JButton button =
                new JButton(
                        "<  CHANGE PORTAL"
                );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        button.setPreferredSize(
                new Dimension(
                        320,
                        38
                )
        );

        button.setBackground(
                new Color(
                        9,
                        42,
                        41
                )
        );

        button.setForeground(
                new Color(
                        190,
                        215,
                        208
                )
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                100,
                                135,
                                126
                        ),
                        1
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return button;
    }

    // =========================================================
    // LOGIN METHOD
    // =========================================================

    private void login() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );

        // =====================================================
        // EMPTY INPUT VALIDATION
        // =====================================================

        if (
            username.isEmpty()
            ||
            password.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your username and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =====================================================
        // CHECK USERNAME + PASSWORD
        // =====================================================

        User loggedInUser =
                validateUser(
                        username,
                        password
                );

        if (
            loggedInUser == null
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Incorrect username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText(
                    ""
            );

            return;
        }

        // =====================================================
        // ROLE-BASED ACCESS CHECK
        // =====================================================

        if (
            !userMatchesSelectedPortal(
                    loggedInUser
            )
        ) {

            String actualRole =
                    getUserDisplayRole(
                            loggedInUser
                    );

            JOptionPane.showMessageDialog(
                    this,
                    "Access denied.\n\n"
                            + "This account is registered as "
                            + actualRole
                            + ".\n"
                            + "Please use the "
                            + actualRole
                            + " Portal.",
                    "Wrong Portal",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText(
                    ""
            );

            return;
        }

        // =====================================================
        // POLYMORPHISM
        // =====================================================

        String dashboard =
                loggedInUser
                        .getDashboard();

        // =====================================================
        // SUCCESS MESSAGE
        // =====================================================

        JOptionPane.showMessageDialog(
                this,
                "Authentication successful!\n\n"
                        + "Welcome, "
                        + loggedInUser.getName()
                        + "\n"
                        + dashboard,
                selectedRole + " Login Successful",
                JOptionPane.INFORMATION_MESSAGE
        );

        // =====================================================
        // OPEN DASHBOARD
        // =====================================================

        openDashboard(
                loggedInUser
        );
    }

    // =========================================================
    // OPEN CORRECT DASHBOARD
    // =========================================================

    private void openDashboard(
            User loggedInUser
    ) {

        // =====================================================
        // DOCTOR
        // =====================================================

        if (
            loggedInUser
                    instanceof Doctor
        ) {

            dispose();

            DoctorDashboard doctorDashboard =
                    new DoctorDashboard(
                            loggedInUser
                                    .getUsername()
                    );

            doctorDashboard.setVisible(
                    true
            );
        }

        // =====================================================
        // PATIENT
        // =====================================================

        else if (
            loggedInUser
                    instanceof Patient
        ) {

            dispose();

            PatientDashboard patientDashboard =
                    new PatientDashboard(
                            loggedInUser
                                    .getUsername()
                    );

            patientDashboard.setVisible(
                    true
            );
        }

        // =====================================================
        // ADMIN STAFF
        // =====================================================

        else if (
            loggedInUser
                    instanceof AdminStaff
        ) {

            dispose();

            AdminStaffDashboard adminDashboard =
                    new AdminStaffDashboard(
                            loggedInUser
                                    .getUsername()
                    );

            adminDashboard.setVisible(
                    true
            );
        }

        // =====================================================
        // MEDICAL MANAGER
        // =====================================================

        else if (
            loggedInUser
                    instanceof MedicalManager
        ) {

            dispose();

            MedicalManagerDashboard managerDashboard =
                    new MedicalManagerDashboard(
                            loggedInUser
                                    .getUsername()
                    );

            managerDashboard.setVisible(
                    true
            );
        }
    }

    // =========================================================
    // ROLE SECURITY CHECK
    // =========================================================

    private boolean userMatchesSelectedPortal(
            User user
    ) {

        if (
            selectedRole.equalsIgnoreCase(
                    "Doctor"
            )
        ) {

            return user
                    instanceof Doctor;
        }

        if (
            selectedRole.equalsIgnoreCase(
                    "Patient"
            )
        ) {

            return user
                    instanceof Patient;
        }

        if (
            selectedRole.equalsIgnoreCase(
                    "Admin Staff"
            )
        ) {

            return user
                    instanceof AdminStaff;
        }

        if (
            selectedRole.equalsIgnoreCase(
                    "Medical Manager"
            )
        ) {

            return user
                    instanceof MedicalManager;
        }

        return false;
    }

    // =========================================================
    // GET ACTUAL USER ROLE
    // =========================================================

    private String getUserDisplayRole(
            User user
    ) {

        if (
            user instanceof Doctor
        ) {

            return "Doctor";
        }

        if (
            user instanceof Patient
        ) {

            return "Patient";
        }

        if (
            user instanceof AdminStaff
        ) {

            return "Admin Staff";
        }

        if (
            user instanceof MedicalManager
        ) {

            return "Medical Manager";
        }

        return "Unknown";
    }

    // =========================================================
    // READ users.txt
    // =========================================================

    private User validateUser(
            String username,
            String password
    ) {

        String filePath =
                "data/users.txt";

        try (
            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(
                                    filePath
                            )
                    )
        ) {

            String line;

            while (
                (line = reader.readLine())
                        != null
            ) {

                if (
                    line.trim().isEmpty()
                ) {

                    continue;
                }

                String[] data =
                        line.split(
                                ",",
                                -1
                        );

                if (
                    data.length >= 8
                ) {

                    String userId =
                            data[0].trim();

                    String name =
                            data[1].trim();

                    String savedUsername =
                            data[2].trim();

                    String savedPassword =
                            data[3].trim();

                    String contactNo =
                            data[4].trim();

                    String role =
                            data[5].trim();

                    String roleId =
                            data[6].trim();

                    String extra =
                            data[7].trim();

                    // =================================================
                    // USERNAME + PASSWORD MATCH
                    // =================================================

                    if (
                        username.equals(
                                savedUsername
                        )
                        &&
                        password.equals(
                                savedPassword
                        )
                    ) {

                        // =============================================
                        // DOCTOR
                        // =============================================

                        if (
                            role.equalsIgnoreCase(
                                    "Doctor"
                            )
                        ) {

                            return new Doctor(
                                    userId,
                                    name,
                                    savedUsername,
                                    savedPassword,
                                    contactNo,
                                    roleId,
                                    extra
                            );
                        }

                        // =============================================
                        // PATIENT
                        // =============================================

                        else if (
                            role.equalsIgnoreCase(
                                    "Patient"
                            )
                        ) {

                            return new Patient(
                                    userId,
                                    name,
                                    savedUsername,
                                    savedPassword,
                                    contactNo,
                                    roleId
                            );
                        }

                        // =============================================
                        // ADMIN STAFF
                        // =============================================

                        else if (
                            role.equalsIgnoreCase(
                                    "AdminStaff"
                            )
                        ) {

                            return new AdminStaff(
                                    userId,
                                    name,
                                    savedUsername,
                                    savedPassword,
                                    contactNo,
                                    roleId
                            );
                        }

                        // =============================================
                        // MEDICAL MANAGER
                        // =============================================

                        else if (
                            role.equalsIgnoreCase(
                                    "MedicalManager"
                            )
                        ) {

                            return new MedicalManager(
                                    userId,
                                    name,
                                    savedUsername,
                                    savedPassword,
                                    contactNo,
                                    roleId
                            );
                        }
                    }
                }
            }

        } catch (
            IOException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to read users.txt\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return null;
    }

    // =========================================================
    // BACK TO ROLE SELECTION
    // =========================================================

    private void backToRoleSelection() {

        RoleSelectionFrame roleSelection =
                new RoleSelectionFrame();

        roleSelection.setVisible(
                true
        );

        dispose();
    }

    // =========================================================
    // ROLE COLOR
    // =========================================================

    private Color getRoleColor(
            String role
    ) {

        if (
            role.equalsIgnoreCase(
                    "Doctor"
            )
        ) {

            return DOCTOR_COLOR;
        }

        if (
            role.equalsIgnoreCase(
                    "Patient"
            )
        ) {

            return PATIENT_COLOR;
        }

        if (
            role.equalsIgnoreCase(
                    "Medical Manager"
            )
        ) {

            return MANAGER_COLOR;
        }

        if (
            role.equalsIgnoreCase(
                    "Admin Staff"
            )
        ) {

            return ADMIN_COLOR;
        }

        return EMERALD;
    }

    // =========================================================
    // ROLE LETTER
    // =========================================================

    private String getRoleLetter(
            String role
    ) {

        if (
            role.equalsIgnoreCase(
                    "Doctor"
            )
        ) {

            return "D";
        }

        if (
            role.equalsIgnoreCase(
                    "Patient"
            )
        ) {

            return "P";
        }

        if (
            role.equalsIgnoreCase(
                    "Medical Manager"
            )
        ) {

            return "M";
        }

        if (
            role.equalsIgnoreCase(
                    "Admin Staff"
            )
        ) {

            return "A";
        }

        return "?";
    }

    // =========================================================
    // ROLE SUBTITLE
    // =========================================================

    private String getRoleSubtitle(
            String role
    ) {

        if (
            role.equalsIgnoreCase(
                    "Doctor"
            )
        ) {

            return "Clinical Access";
        }

        if (
            role.equalsIgnoreCase(
                    "Patient"
            )
        ) {

            return "Personal Access";
        }

        if (
            role.equalsIgnoreCase(
                    "Medical Manager"
            )
        ) {

            return "Operations Access";
        }

        if (
            role.equalsIgnoreCase(
                    "Admin Staff"
            )
        ) {

            return "System Access";
        }

        return "Secure Access";
    }

    // =========================================================
    // BOTTOM STATUS BAR
    // =========================================================

    private JPanel createBottomBar() {

        JPanel bottom =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                0,
                                0
                        )
                );

        bottom.setBackground(
                new Color(
                        3,
                        35,
                        31,
                        235
                )
        );

        bottom.setBorder(
                new EmptyBorder(
                        12,
                        35,
                        12,
                        35
                )
        );

        bottom.add(
                createStatus(
                        "◆",
                        "SYSTEM READY",
                        "All services operational"
                )
        );

        bottom.add(
                createStatus(
                        "▣",
                        "SECURE LOGIN",
                        "Authentication required"
                )
        );

        bottom.add(
                createStatus(
                        "●",
                        "ROLE-BASED ACCESS",
                        "Portal verification active"
                )
        );

        JPanel footerBrand =
                new JPanel();

        footerBrand.setOpaque(false);

        footerBrand.setLayout(
                new BoxLayout(
                        footerBrand,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel footerName =
                new JLabel(
                        "APU MEDICAL CENTRE"
                );

        footerName.setForeground(
                new Color(
                        80,
                        218,
                        175
                )
        );

        footerName.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        footerName.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        JLabel footerTag =
                new JLabel(
                        "Care Beyond Treatment"
                );

        footerTag.setForeground(
                TEXT_MUTED
        );

        footerTag.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        footerTag.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        footerBrand.add(
                footerName
        );

        footerBrand.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                3
                        )
                )
        );

        footerBrand.add(
                footerTag
        );

        bottom.add(
                footerBrand
        );

        return bottom;
    }

    // =========================================================
    // STATUS ITEM
    // =========================================================

    private JPanel createStatus(
            String symbol,
            String title,
            String subtitle
    ) {

        JPanel panel =
                new JPanel();

        panel.setOpaque(false);

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.X_AXIS
                )
        );

        JLabel icon =
                new JLabel(
                        symbol
                );

        icon.setForeground(
                EMERALD
        );

        icon.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        JPanel text =
                new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setForeground(
                new Color(
                        193,
                        235,
                        220
                )
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        JLabel subtitleLabel =
                new JLabel(
                        subtitle
                );

        subtitleLabel.setForeground(
                TEXT_MUTED
        );

        subtitleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        text.add(
                titleLabel
        );

        text.add(
                subtitleLabel
        );

        panel.add(
                icon
        );

        panel.add(
                Box.createRigidArea(
                        new Dimension(
                                9,
                                0
                        )
                )
        );

        panel.add(
                text
        );

        return panel;
    }

    // =========================================================
    // LOGIN CARD
    // =========================================================

    private class LoginCard
            extends JPanel {

        public LoginCard() {

            setOpaque(
                    false
            );
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

            int width =
                    getWidth();

            int height =
                    getHeight();

            // =================================================
            // OUTER SHADOW
            // =================================================

            g2.setColor(
                    new Color(
                            0,
                            0,
                            0,
                            125
                    )
            );

            g2.fillRoundRect(
                    10,
                    12,
                    width - 18,
                    height - 18,
                    28,
                    28
            );

            // =================================================
            // CARD BACKGROUND
            // =================================================

            GradientPaint gradient =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    roleColor.getRed(),
                                    roleColor.getGreen(),
                                    roleColor.getBlue(),
                                    38
                            ),
                            0,
                            height,
                            new Color(
                                    2,
                                    27,
                                    29,
                                    245
                            )
                    );

            g2.setPaint(
                    gradient
            );

            g2.fillRoundRect(
                    2,
                    2,
                    width - 12,
                    height - 14,
                    26,
                    26
            );

            // =================================================
            // BORDER
            // =================================================

            g2.setColor(
                    new Color(
                            roleColor.getRed(),
                            roleColor.getGreen(),
                            roleColor.getBlue(),
                            190
                    )
            );

            g2.setStroke(
                    new BasicStroke(
                            1.3f
                    )
            );

            g2.drawRoundRect(
                    2,
                    2,
                    width - 12,
                    height - 14,
                    26,
                    26
            );

            // =================================================
            // TOP GLOW
            // =================================================

            GradientPaint glow =
                    new GradientPaint(
                            25,
                            0,
                            roleColor,
                            width - 25,
                            0,
                            new Color(
                                    roleColor.getRed(),
                                    roleColor.getGreen(),
                                    roleColor.getBlue(),
                                    20
                            )
                    );

            g2.setPaint(
                    glow
            );

            g2.fillRoundRect(
                    25,
                    2,
                    width - 60,
                    3,
                    3,
                    3
            );

            g2.dispose();

            super.paintComponent(
                    g
            );
        }
    }

    // =========================================================
    // ROLE CIRCLE
    // =========================================================

    private class RoleCircle
            extends JPanel {

        private String letter;

        public RoleCircle(
                String letter
        ) {

            this.letter =
                    letter;

            setOpaque(
                    false
            );
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

            int size =
                    Math.min(
                            getWidth(),
                            getHeight()
                    ) - 4;

            // =================================================
            // GLOW
            // =================================================

            g2.setColor(
                    new Color(
                            roleColor.getRed(),
                            roleColor.getGreen(),
                            roleColor.getBlue(),
                            40
                    )
            );

            g2.fillOval(
                    0,
                    0,
                    size,
                    size
            );

            // =================================================
            // BORDER
            // =================================================

            g2.setColor(
                    roleColor
            );

            g2.setStroke(
                    new BasicStroke(
                            1.5f
                    )
            );

            g2.drawOval(
                    1,
                    1,
                    size - 2,
                    size - 2
            );

            // =================================================
            // LETTER
            // =================================================

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            24
                    )
            );

            FontMetrics metrics =
                    g2.getFontMetrics();

            int x =
                    (
                        size
                        - metrics.stringWidth(
                                letter
                        )
                    ) / 2;

            int y =
                    (
                        size
                        + metrics.getAscent()
                        - metrics.getDescent()
                    ) / 2;

            g2.drawString(
                    letter,
                    x,
                    y
            );

            g2.dispose();
        }
    }

    // =========================================================
    // BACKGROUND PANEL
    // =========================================================

    private class BackgroundPanel
            extends JPanel {

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(
                    g
            );

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
                            23,
                            22
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

            GradientPaint darkOverlay =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    0,
                                    24,
                                    22,
                                    245
                            ),
                            getWidth(),
                            0,
                            new Color(
                                    0,
                                    15,
                                    18,
                                    65
                            )
                    );

            g2.setPaint(
                    darkOverlay
            );

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );

            // =================================================
            // RIGHT LOGIN DARKENING
            // =================================================

            GradientPaint rightDark =
                    new GradientPaint(
                            getWidth() / 2,
                            0,
                            new Color(
                                    0,
                                    20,
                                    20,
                                    0
                            ),
                            getWidth(),
                            0,
                            new Color(
                                    0,
                                    18,
                                    20,
                                    150
                            )
                    );

            g2.setPaint(
                    rightDark
            );

            g2.fillRect(
                    getWidth() / 2,
                    90,
                    getWidth() / 2,
                    getHeight() - 145
            );

            // =================================================
            // HEADER GLASS
            // =================================================

            g2.setColor(
                    new Color(
                            0,
                            20,
                            20,
                            135
                    )
            );

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    90
            );

            // =================================================
            // HEADER ACCENT
            // =================================================

            GradientPaint headerAccent =
                    new GradientPaint(
                            0,
                            0,
                            roleColor,
                            getWidth(),
                            0,
                            new Color(
                                    roleColor.getRed(),
                                    roleColor.getGreen(),
                                    roleColor.getBlue(),
                                    0
                            )
                    );

            g2.setPaint(
                    headerAccent
            );

            g2.fillRect(
                    0,
                    88,
                    getWidth(),
                    2
            );

            // =================================================
            // DECORATIVE ROLE GLOW
            // =================================================

            g2.setColor(
                    new Color(
                            roleColor.getRed(),
                            roleColor.getGreen(),
                            roleColor.getBlue(),
                            13
                    )
            );

            g2.fillOval(
                    -130,
                    180,
                    380,
                    380
            );

            // =================================================
            // ECG LINE
            // =================================================

            g2.setStroke(
                    new BasicStroke(
                            1.4f
                    )
            );

            g2.setColor(
                    new Color(
                            roleColor.getRed(),
                            roleColor.getGreen(),
                            roleColor.getBlue(),
                            75
                    )
            );

            int baseY =
                    getHeight() - 110;

            int startX =
                    55;

            g2.drawLine(
                    startX,
                    baseY,
                    startX + 65,
                    baseY
            );

            g2.drawLine(
                    startX + 65,
                    baseY,
                    startX + 78,
                    baseY - 18
            );

            g2.drawLine(
                    startX + 78,
                    baseY - 18,
                    startX + 91,
                    baseY + 22
            );

            g2.drawLine(
                    startX + 91,
                    baseY + 22,
                    startX + 108,
                    baseY - 48
            );

            g2.drawLine(
                    startX + 108,
                    baseY - 48,
                    startX + 125,
                    baseY
            );

            g2.drawLine(
                    startX + 125,
                    baseY,
                    startX + 210,
                    baseY
            );

            g2.dispose();
        }
    }

    // =========================================================
    // OPTIONAL MAIN FOR DIRECT TESTING
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    LoginFrame frame =
                            new LoginFrame(
                                    "Doctor"
                            );

                    frame.setVisible(
                            true
                    );
                }
        );
    }
}