import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PatientDashboard extends JFrame {

    // =========================================================
    // USER
    // =========================================================

    private final String username;

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

    private final Color CARD_BACKGROUND =
            new Color(5, 35, 39, 225);

    // =========================================================
    // BACKGROUND
    // =========================================================

    private BufferedImage backgroundImage;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public PatientDashboard(String username) {

        this.username = username;

        // =====================================================
        // WINDOW SETTINGS
        // =====================================================

        setTitle(
                "APU Medical Centre - Patient Dashboard"
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
        // LOAD HOSPITAL BACKGROUND
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
        // ROOT BACKGROUND
        // =====================================================

        DashboardBackground background =
                new DashboardBackground();

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
        // MAIN BODY
        // =====================================================

        JPanel body =
                new JPanel(
                        new BorderLayout()
                );

        body.setOpaque(false);

        // =====================================================
        // SIDEBAR
        // =====================================================

        JPanel sidebar =
                createSidebar();

        // =====================================================
        // DASHBOARD CONTENT
        // =====================================================

        JPanel content =
                createDashboardContent();

        body.add(
                sidebar,
                BorderLayout.WEST
        );

        body.add(
                content,
                BorderLayout.CENTER
        );

        // =====================================================
        // BOTTOM BAR
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
                body,
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
                        16,
                        28,
                        14,
                        30
                )
        );

        // =====================================================
        // LEFT BRAND
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
                        32
                )
        );

        cross.setPreferredSize(
                new Dimension(
                        52,
                        52
                )
        );

        cross.setMaximumSize(
                new Dimension(
                        52,
                        52
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
                Color.WHITE
        );

        hospitalName.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
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
                        10
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
                brandText
        );

        // =====================================================
        // RIGHT USER INFORMATION
        // =====================================================

        JPanel userPanel =
                new JPanel();

        userPanel.setOpaque(false);

        userPanel.setLayout(
                new BoxLayout(
                        userPanel,
                        BoxLayout.X_AXIS
                )
        );

        JPanel userText =
                new JPanel();

        userText.setOpaque(false);

        userText.setLayout(
                new BoxLayout(
                        userText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel activePortal =
                new JLabel(
                        "PATIENT PORTAL"
                );

        activePortal.setForeground(
                PATIENT_BLUE
        );

        activePortal.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        activePortal.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        JLabel usernameLabel =
                new JLabel(
                        username
                );

        usernameLabel.setForeground(
                TEXT_WHITE
        );

        usernameLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        usernameLabel.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        userText.add(
                activePortal
        );

        userText.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );

        userText.add(
                usernameLabel
        );

        PatientCircle patientCircle =
                new PatientCircle();

        patientCircle.setPreferredSize(
                new Dimension(
                        46,
                        46
                )
        );

        patientCircle.setMinimumSize(
                new Dimension(
                        46,
                        46
                )
        );

        patientCircle.setMaximumSize(
                new Dimension(
                        46,
                        46
                )
        );

        userPanel.add(
                userText
        );

        userPanel.add(
                Box.createRigidArea(
                        new Dimension(
                                14,
                                0
                        )
                )
        );

        userPanel.add(
                patientCircle
        );

        header.add(
                brandPanel,
                BorderLayout.WEST
        );

        header.add(
                userPanel,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        SidebarPanel sidebar =
                new SidebarPanel();

        sidebar.setPreferredSize(
                new Dimension(
                        275,
                        0
                )
        );

        sidebar.setLayout(
                new BorderLayout()
        );

        sidebar.setBorder(
                new EmptyBorder(
                        25,
                        22,
                        22,
                        22
                )
        );

        // =====================================================
        // TOP
        // =====================================================

        JPanel top =
                new JPanel();

        top.setOpaque(false);

        top.setLayout(
                new BoxLayout(
                        top,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel navigationLabel =
                new JLabel(
                        "PATIENT WORKSPACE"
                );

        navigationLabel.setForeground(
                PATIENT_BLUE
        );

        navigationLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        navigationLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel navigationTitle =
                new JLabel(
                        "Navigation"
                );

        navigationTitle.setForeground(
                TEXT_WHITE
        );

        navigationTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        21
                )
        );

        navigationTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JPanel divider =
                new JPanel();

        divider.setBackground(
                new Color(
                        PATIENT_BLUE.getRed(),
                        PATIENT_BLUE.getGreen(),
                        PATIENT_BLUE.getBlue(),
                        120
                )
        );

        divider.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        1
                )
        );

        divider.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        top.add(
                navigationLabel
        );

        top.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );

        top.add(
                navigationTitle
        );

        top.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                15
                        )
                )
        );

        top.add(
                divider
        );

        top.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                20
                        )
                )
        );

        // =====================================================
        // MENU BUTTONS
        // =====================================================

        JButton profileButton =
                createSidebarButton(
                        "P",
                        "My Profile"
                );

        JButton bookButton =
                createSidebarButton(
                        "+",
                        "Book Appointment"
                );

        JButton appointmentsButton =
                createSidebarButton(
                        "A",
                        "My Appointments"
                );

        JButton historyButton =
                createSidebarButton(
                        "H",
                        "Medical History"
                );

        JButton prescriptionsButton =
                createSidebarButton(
                        "Rx",
                        "My Prescriptions"
                );

        JButton feedbackButton =
                createSidebarButton(
                        "F",
                        "Feedback & Ratings"
                );

        top.add(
                profileButton
        );

        top.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                9
                        )
                )
        );

        top.add(
                bookButton
        );

        top.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                9
                        )
                )
        );

        top.add(
                appointmentsButton
        );

        top.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                9
                        )
                )
        );

        top.add(
                historyButton
        );

        top.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                9
                        )
                )
        );

        top.add(
                prescriptionsButton
        );

        top.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                9
                        )
                )
        );

        top.add(
                feedbackButton
        );

        // =====================================================
        // LOGOUT
        // =====================================================

        JButton logoutButton =
                createLogoutButton();

        // =====================================================
        // ACTIONS
        // =====================================================

        profileButton.addActionListener(
                e -> {

                    PatientProfileFrame frame =
                            new PatientProfileFrame(
                                    username
                            );

                    frame.setVisible(true);
                }
        );

        bookButton.addActionListener(
                e -> {

                    BookAppointmentFrame frame =
                            new BookAppointmentFrame(
                                    username
                            );

                    frame.setVisible(true);
                }
        );

        appointmentsButton.addActionListener(
                e -> {

                    MyAppointmentsFrame frame =
                            new MyAppointmentsFrame(
                                    username
                            );

                    frame.setVisible(true);
                }
        );

        historyButton.addActionListener(
                e -> {

                    MedicalHistoryFrame frame =
                            new MedicalHistoryFrame(
                                    username
                            );

                    frame.setVisible(true);
                }
        );

        prescriptionsButton.addActionListener(
                e -> {

                    MyPrescriptionsFrame frame =
                            new MyPrescriptionsFrame(
                                    username
                            );

                    frame.setVisible(true);
                }
        );

        feedbackButton.addActionListener(
                e -> {

                    FeedbackFrame frame =
                            new FeedbackFrame(
                                    username
                            );

                    frame.setVisible(true);
                }
        );

        logoutButton.addActionListener(
                e -> logout()
        );

        sidebar.add(
                top,
                BorderLayout.NORTH
        );

        sidebar.add(
                logoutButton,
                BorderLayout.SOUTH
        );

        return sidebar;
    }

    // =========================================================
    // DASHBOARD CONTENT
    // =========================================================

    private JPanel createDashboardContent() {

        JPanel wrapper =
                new JPanel(
                        new BorderLayout()
                );

        wrapper.setOpaque(false);

        wrapper.setBorder(
                new EmptyBorder(
                        30,
                        35,
                        25,
                        45
                )
        );

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
        // WELCOME SECTION
        // =====================================================

        JLabel smallTitle =
                new JLabel(
                        "PERSONAL HEALTH WORKSPACE"
                );

        smallTitle.setForeground(
                PATIENT_BLUE
        );

        smallTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        smallTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel welcome =
                new JLabel(
                        "Welcome back, " + username
                );

        welcome.setForeground(
                TEXT_WHITE
        );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        34
                )
        );

        welcome.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel description =
                new JLabel(
                        "Manage your appointments, medical information and hospital services."
                );

        description.setForeground(
                new Color(
                        185,
                        210,
                        204
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

        content.add(
                smallTitle
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
                welcome
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
                description
        );

        content.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                28
                        )
                )
        );

        // =====================================================
        // FEATURE CARDS
        // =====================================================

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                16,
                                16
                        )
                );

        cards.setOpaque(false);

        cards.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        315
                )
        );

        cards.setPreferredSize(
                new Dimension(
                        850,
                        315
                )
        );

        DashboardCard appointmentsCard =
                new DashboardCard(
                        "A",
                        "Appointments",
                        "Book, reschedule or review your hospital appointments."
                );

        DashboardCard historyCard =
                new DashboardCard(
                        "H",
                        "Medical History",
                        "Access your previous medical records and clinical history."
                );

        DashboardCard prescriptionCard =
                new DashboardCard(
                        "Rx",
                        "Prescriptions",
                        "Review medicines and prescriptions issued by doctors."
                );

        DashboardCard profileCard =
                new DashboardCard(
                        "P",
                        "My Profile",
                        "View and maintain your personal account information."
                );

        DashboardCard feedbackCard =
                new DashboardCard(
                        "F",
                        "Feedback",
                        "Share ratings and feedback about your hospital experience."
                );

        DashboardCard securityCard =
                new DashboardCard(
                        "S",
                        "Account Security",
                        "Your account is protected through role-based authentication and secure login access.",
                        false
                );

        // =====================================================
        // CARD ACTIONS
        // =====================================================

        appointmentsCard.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        MyAppointmentsFrame frame =
                                new MyAppointmentsFrame(
                                        username
                                );

                        frame.setVisible(true);
                    }
                }
        );

        historyCard.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        MedicalHistoryFrame frame =
                                new MedicalHistoryFrame(
                                        username
                                );

                        frame.setVisible(true);
                    }
                }
        );

        prescriptionCard.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        MyPrescriptionsFrame frame =
                                new MyPrescriptionsFrame(
                                        username
                                );

                        frame.setVisible(true);
                    }
                }
        );

        profileCard.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        PatientProfileFrame frame =
                                new PatientProfileFrame(
                                        username
                                );

                        frame.setVisible(true);
                    }
                }
        );

        feedbackCard.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        FeedbackFrame frame =
                                new FeedbackFrame(
                                        username
                                );

                        frame.setVisible(true);
                    }
                }
        );

        cards.add(
                appointmentsCard
        );

        cards.add(
                historyCard
        );

        cards.add(
                prescriptionCard
        );

        cards.add(
                profileCard
        );

        cards.add(
                feedbackCard
        );

        cards.add(
                securityCard
        );

        content.add(
                cards
        );

        content.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                22
                        )
                )
        );

        // =====================================================
        // QUICK ACTION
        // =====================================================

        QuickActionPanel quickAction =
                new QuickActionPanel();

        quickAction.setLayout(
                new BorderLayout()
        );

        quickAction.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        80
                )
        );

        quickAction.setPreferredSize(
                new Dimension(
                        850,
                        80
                )
        );

        quickAction.setBorder(
                new EmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );

        JPanel quickText =
                new JPanel();

        quickText.setOpaque(false);

        quickText.setLayout(
                new BoxLayout(
                        quickText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel quickTitle =
                new JLabel(
                        "Need to see a doctor?"
                );

        quickTitle.setForeground(
                TEXT_WHITE
        );

        quickTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        JLabel quickSubtitle =
                new JLabel(
                        "Schedule an appointment through your Patient Portal."
                );

        quickSubtitle.setForeground(
                TEXT_MUTED
        );

        quickSubtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        quickText.add(
                quickTitle
        );

        quickText.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );

        quickText.add(
                quickSubtitle
        );

        JButton quickBookButton =
                new JButton(
                        "BOOK APPOINTMENT  >"
                );

        quickBookButton.setBackground(
                PATIENT_BLUE
        );

        quickBookButton.setForeground(
                new Color(
                        3,
                        28,
                        35
                )
        );

        quickBookButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        quickBookButton.setFocusPainted(false);
        quickBookButton.setBorderPainted(false);

        quickBookButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        quickBookButton.setPreferredSize(
                new Dimension(
                        175,
                        38
                )
        );

        quickBookButton.addActionListener(
                e -> {

                    BookAppointmentFrame frame =
                            new BookAppointmentFrame(
                                    username
                            );

                    frame.setVisible(true);
                }
        );

        quickAction.add(
                quickText,
                BorderLayout.WEST
        );

        quickAction.add(
                quickBookButton,
                BorderLayout.EAST
        );

        content.add(
                quickAction
        );

        wrapper.add(
                content,
                BorderLayout.NORTH
        );

        return wrapper;
    }

    // =========================================================
    // SIDEBAR BUTTON
    // =========================================================

    private JButton createSidebarButton(
            String symbol,
            String text
    ) {

        JButton button =
                new JButton(
                        symbol + "    " + text
                );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        button.setPreferredSize(
                new Dimension(
                        225,
                        45
                )
        );

        button.setBackground(
                new Color(
                        7,
                        42,
                        45
                )
        );

        button.setForeground(
                new Color(
                        215,
                        232,
                        228
                )
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        PATIENT_BLUE.getRed(),
                                        PATIENT_BLUE.getGreen(),
                                        PATIENT_BLUE.getBlue(),
                                        70
                                ),
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                0,
                                15,
                                0,
                                10
                        )
                )
        );

        button.setFocusPainted(false);

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
    // LOGOUT BUTTON
    // =========================================================

    private JButton createLogoutButton() {

        JButton button =
                new JButton(
                        "<  LOGOUT"
                );

        button.setBackground(
                new Color(
                        45,
                        28,
                        32
                )
        );

        button.setForeground(
                new Color(
                        255,
                        145,
                        145
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

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                160,
                                65,
                                70
                        ),
                        1
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        220,
                        42
                )
        );

        return button;
    }

    // =========================================================
    // LOGOUT
    // =========================================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (
            choice
            ==
            JOptionPane.YES_OPTION
        ) {

            RoleSelectionFrame roleSelectionFrame =
                    new RoleSelectionFrame();

            roleSelectionFrame.setVisible(true);

            dispose();
        }
    }

    // =========================================================
    // BOTTOM STATUS BAR
    // =========================================================

    private JPanel createBottomBar() {

        JPanel bottom =
                new JPanel(
                        new BorderLayout()
                );

        bottom.setBackground(
                new Color(
                        2,
                        28,
                        29,
                        240
                )
        );

        bottom.setBorder(
                new EmptyBorder(
                        10,
                        30,
                        10,
                        30
                )
        );

        JPanel left =
                new JPanel();

        left.setOpaque(false);

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.X_AXIS
                )
        );

        JLabel status =
                new JLabel(
                        "●  SYSTEM READY"
                );

        status.setForeground(
                EMERALD
        );

        status.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        JLabel secure =
                new JLabel(
                        "     |     SECURE PATIENT SESSION"
                );

        secure.setForeground(
                TEXT_MUTED
        );

        secure.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        left.add(
                status
        );

        left.add(
                secure
        );

        JLabel footer =
                new JLabel(
                        "APU MEDICAL CENTRE  •  Care Beyond Treatment"
                );

        footer.setForeground(
                new Color(
                        115,
                        165,
                        154
                )
        );

        footer.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        bottom.add(
                left,
                BorderLayout.WEST
        );

        bottom.add(
                footer,
                BorderLayout.EAST
        );

        return bottom;
    }

    // =========================================================
    // BACKGROUND PANEL
    // =========================================================

    private class DashboardBackground
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
            // IMAGE
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
                                    205
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
            // HEADER GLASS
            // =================================================

            g2.setColor(
                    new Color(
                            0,
                            18,
                            22,
                            180
                    )
            );

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    86
            );

            // =================================================
            // BLUE HEADER ACCENT
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
                    84,
                    getWidth(),
                    2
            );

            // =================================================
            // DECORATIVE GLOW
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
                    650,
                    -150,
                    450,
                    450
            );

            g2.dispose();
        }
    }

    // =========================================================
    // SIDEBAR PANEL
    // =========================================================

    private class SidebarPanel
            extends JPanel {

        public SidebarPanel() {

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
                            2,
                            29,
                            32,
                            238
                    )
            );

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );

            g2.setColor(
                    new Color(
                            PATIENT_BLUE.getRed(),
                            PATIENT_BLUE.getGreen(),
                            PATIENT_BLUE.getBlue(),
                            80
                    )
            );

            g2.fillRect(
                    getWidth() - 1,
                    0,
                    1,
                    getHeight()
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // DASHBOARD CARD
    // =========================================================

    private class DashboardCard
            extends JPanel {

        private final String symbol;
        private final String title;
        private final String description;
        private final boolean actionable;

        private boolean hover = false;

        public DashboardCard(
                String symbol,
                String title,
                String description
        ) {

            this(
                    symbol,
                    title,
                    description,
                    true
            );
        }

        public DashboardCard(
                String symbol,
                String title,
                String description,
                boolean actionable
        ) {

            this.symbol = symbol;
            this.title = title;
            this.description = description;
            this.actionable = actionable;

            setOpaque(false);

            setCursor(
                    new Cursor(
                            actionable
                                    ? Cursor.HAND_CURSOR
                                    : Cursor.DEFAULT_CURSOR
                    )
            );

            addMouseListener(
                    new MouseAdapter() {

                        @Override
                        public void mouseEntered(
                                MouseEvent e
                        ) {

                            if (actionable) {
                                hover = true;
                                repaint();
                            }
                        }

                        @Override
                        public void mouseExited(
                                MouseEvent e
                        ) {

                            if (actionable) {
                                hover = false;
                                repaint();
                            }
                        }
                    }
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
                            90
                    )
            );

            g2.fillRoundRect(
                    7,
                    8,
                    w - 11,
                    h - 11,
                    20,
                    20
            );

            // =================================================
            // BACKGROUND
            // =================================================

            GradientPaint gradient =
                    new GradientPaint(
                            0,
                            0,
                            hover
                                    ? new Color(
                                            PATIENT_BLUE.getRed(),
                                            PATIENT_BLUE.getGreen(),
                                            PATIENT_BLUE.getBlue(),
                                            65
                                    )
                                    : new Color(
                                            PATIENT_BLUE.getRed(),
                                            PATIENT_BLUE.getGreen(),
                                            PATIENT_BLUE.getBlue(),
                                            28
                                    ),
                            0,
                            h,
                            CARD_BACKGROUND
                    );

            g2.setPaint(
                    gradient
            );

            g2.fillRoundRect(
                    2,
                    2,
                    w - 8,
                    h - 9,
                    18,
                    18
            );

            // =================================================
            // BORDER
            // =================================================

            g2.setColor(
                    hover
                            ? PATIENT_BLUE
                            : new Color(
                                    PATIENT_BLUE.getRed(),
                                    PATIENT_BLUE.getGreen(),
                                    PATIENT_BLUE.getBlue(),
                                    110
                            )
            );

            g2.setStroke(
                    new BasicStroke(
                            hover
                                    ? 1.8f
                                    : 1.0f
                    )
            );

            g2.drawRoundRect(
                    2,
                    2,
                    w - 8,
                    h - 9,
                    18,
                    18
            );

            // =================================================
            // ICON CIRCLE
            // =================================================

            g2.setColor(
                    new Color(
                            PATIENT_BLUE.getRed(),
                            PATIENT_BLUE.getGreen(),
                            PATIENT_BLUE.getBlue(),
                            hover
                                    ? 70
                                    : 35
                    )
            );

            g2.fillOval(
                    20,
                    18,
                    42,
                    42
            );

            g2.setColor(
                    PATIENT_BLUE
            );

            g2.drawOval(
                    20,
                    18,
                    42,
                    42
            );

            // =================================================
            // ICON
            // =================================================

            int symbolSize =
                    symbol.equals("Rx")
                            ? 14
                            : 17;

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            symbolSize
                    )
            );

            FontMetrics symbolMetrics =
                    g2.getFontMetrics();

            int symbolX =
                    20
                            + (
                            42
                                    - symbolMetrics.stringWidth(
                                    symbol
                            )
                    ) / 2;

            int symbolY =
                    18
                            + (
                            42
                                    + symbolMetrics.getAscent()
                                    - symbolMetrics.getDescent()
                    ) / 2;

            g2.drawString(
                    symbol,
                    symbolX,
                    symbolY
            );

            // =================================================
            // TITLE
            // =================================================

            g2.setColor(
                    TEXT_WHITE
            );

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            14
                    )
            );

            g2.drawString(
                    title,
                    20,
                    83
            );

            // =================================================
            // DESCRIPTION
            // =================================================

            g2.setColor(
                    new Color(
                            165,
                            192,
                            187
                    )
            );

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            10
                    )
            );

            drawWrappedText(
                    g2,
                    description,
                    20,
                    103,
                    w - 42,
                    14
            );

            // =================================================
            // ACCESS
            // =================================================

            g2.setColor(
                    actionable
                            ? PATIENT_BLUE
                            : EMERALD
            );

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            9
                    )
            );

            g2.drawString(
                    actionable
                            ? "OPEN  >"
                            : "●  PROTECTED",
                    20,
                    h - 18
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // QUICK ACTION PANEL
    // =========================================================

    private class QuickActionPanel
            extends JPanel {

        public QuickActionPanel() {

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

            GradientPaint gradient =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    PATIENT_BLUE.getRed(),
                                    PATIENT_BLUE.getGreen(),
                                    PATIENT_BLUE.getBlue(),
                                    45
                            ),
                            getWidth(),
                            0,
                            new Color(
                                    4,
                                    36,
                                    40,
                                    235
                            )
                    );

            g2.setPaint(
                    gradient
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    18,
                    18
            );

            g2.setColor(
                    new Color(
                            PATIENT_BLUE.getRed(),
                            PATIENT_BLUE.getGreen(),
                            PATIENT_BLUE.getBlue(),
                            130
                    )
            );

            g2.drawRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    18,
                    18
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // PATIENT CIRCLE
    // =========================================================

    private class PatientCircle
            extends JPanel {

        public PatientCircle() {

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
                    1,
                    1,
                    43,
                    43
            );

            g2.setColor(
                    PATIENT_BLUE
            );

            g2.setStroke(
                    new BasicStroke(
                            1.4f
                    )
            );

            g2.drawOval(
                    1,
                    1,
                    43,
                    43
            );

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            19
                    )
            );

            FontMetrics metrics =
                    g2.getFontMetrics();

            String letter =
                    "P";

            int x =
                    (
                        45
                        - metrics.stringWidth(
                                letter
                        )
                    ) / 2;

            int y =
                    (
                        45
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
    // TEXT WRAPPING
    // =========================================================

    private void drawWrappedText(
            Graphics2D g2,
            String text,
            int x,
            int y,
            int maxWidth,
            int lineHeight
    ) {

        FontMetrics metrics =
                g2.getFontMetrics();

        String[] words =
                text.split(" ");

        StringBuilder line =
                new StringBuilder();

        int currentY =
                y;

        for (
            String word : words
        ) {

            String testLine =
                    line.length() == 0
                            ? word
                            : line + " " + word;

            if (
                metrics.stringWidth(
                        testLine
                )
                >
                maxWidth
            ) {

                g2.drawString(
                        line.toString(),
                        x,
                        currentY
                );

                line =
                        new StringBuilder(
                                word
                        );

                currentY +=
                        lineHeight;

            } else {

                if (
                    line.length() > 0
                ) {

                    line.append(
                            " "
                    );
                }

                line.append(
                        word
                );
            }
        }

        if (
            line.length() > 0
        ) {

            g2.drawString(
                    line.toString(),
                    x,
                    currentY
            );
        }
    }
}