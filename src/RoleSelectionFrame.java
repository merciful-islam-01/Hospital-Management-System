import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RoleSelectionFrame extends JFrame {

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

    private BufferedImage backgroundImage;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public RoleSelectionFrame() {

        setTitle(
                "APU Medical Centre - Hospital Management System"
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
        // BACKGROUND PANEL
        // =====================================================

        BackgroundPanel background =
                new BackgroundPanel();

        background.setLayout(
                new BorderLayout()
        );

        setContentPane(background);

        // =====================================================
        // HEADER
        // =====================================================

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
        // HEADER LEFT
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
        // HEADER RIGHT
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
                new Color(
                        33,
                        255,
                        172
                )
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

        // =====================================================
        // MAIN AREA
        // =====================================================

        JPanel mainArea =
                new JPanel(
                        new BorderLayout()
                );

        mainArea.setOpaque(false);

        mainArea.setBorder(
                new EmptyBorder(
                        20,
                        40,
                        20,
                        40
                )
        );

        // =====================================================
        // LEFT MESSAGE
        // =====================================================

        JPanel leftPanel =
                new JPanel();

        leftPanel.setOpaque(false);

        leftPanel.setPreferredSize(
                new Dimension(
                        245,
                        0
                )
        );

        leftPanel.setLayout(
                new BoxLayout(
                        leftPanel,
                        BoxLayout.Y_AXIS
                )
        );

        leftPanel.add(
                Box.createVerticalStrut(
                        45
                )
        );

        JLabel roleSmall =
                new JLabel(
                        "YOUR WORKSPACE"
                );

        roleSmall.setForeground(
                EMERALD
        );

        roleSmall.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        roleSmall.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel slogan =
                new JLabel(
                        "<html>"
                                + "Your Role<br>"
                                + "Makes a<br>"
                                + "Healthier<br>"
                                + "Tomorrow"
                                + "</html>"
                );

        slogan.setForeground(
                new Color(
                        102,
                        240,
                        201
                )
        );

        slogan.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        28
                )
        );

        slogan.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JPanel accentLine =
                new JPanel();

        accentLine.setBackground(
                EMERALD
        );

        accentLine.setMaximumSize(
                new Dimension(
                        75,
                        2
                )
        );

        accentLine.setPreferredSize(
                new Dimension(
                        75,
                        2
                )
        );

        accentLine.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel instruction =
                new JLabel(
                        "<html>"
                                + "Select your portal<br>"
                                + "to continue"
                                + "</html>"
                );

        instruction.setForeground(
                new Color(
                        220,
                        235,
                        230
                )
        );

        instruction.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        instruction.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        leftPanel.add(
                roleSmall
        );

        leftPanel.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                12
                        )
                )
        );

        leftPanel.add(
                slogan
        );

        leftPanel.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                18
                        )
                )
        );

        leftPanel.add(
                accentLine
        );

        leftPanel.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                20
                        )
                )
        );

        leftPanel.add(
                instruction
        );

        // =====================================================
        // PORTAL GRID
        // =====================================================

        JPanel portalArea =
                new JPanel();

        portalArea.setOpaque(false);

        portalArea.setPreferredSize(
                new Dimension(
                        590,
                        0
                )
        );

        portalArea.setBorder(
                new EmptyBorder(
                        15,
                        5,
                        15,
                        15
                )
        );

        portalArea.setLayout(
                new GridLayout(
                        2,
                        2,
                        14,
                        14
                )
        );

        // =====================================================
        // DOCTOR PORTAL
        // =====================================================

        PortalCard doctorCard =
                new PortalCard(
                        "DOCTOR",
                        "Clinical Access",
                        "D",
                        DOCTOR_COLOR,
                        "Doctor"
                );

        // =====================================================
        // PATIENT PORTAL
        // =====================================================

        PortalCard patientCard =
                new PortalCard(
                        "PATIENT",
                        "Personal Access",
                        "P",
                        PATIENT_COLOR,
                        "Patient"
                );

        // =====================================================
        // MEDICAL MANAGER PORTAL
        // =====================================================

        PortalCard managerCard =
                new PortalCard(
                        "MEDICAL MANAGER",
                        "Operations Access",
                        "M",
                        MANAGER_COLOR,
                        "Medical Manager"
                );

        // =====================================================
        // ADMIN STAFF PORTAL
        // =====================================================

        PortalCard adminCard =
                new PortalCard(
                        "ADMIN STAFF",
                        "System Access",
                        "A",
                        ADMIN_COLOR,
                        "Admin Staff"
                );

        portalArea.add(
                doctorCard
        );

        portalArea.add(
                patientCard
        );

        portalArea.add(
                managerCard
        );

        portalArea.add(
                adminCard
        );

        // =====================================================
        // RIGHT VISUAL SPACE
        // =====================================================

        JPanel rightVisual =
                new JPanel();

        rightVisual.setOpaque(false);

        rightVisual.setPreferredSize(
                new Dimension(
                        300,
                        0
                )
        );

        mainArea.add(
                leftPanel,
                BorderLayout.WEST
        );

        mainArea.add(
                portalArea,
                BorderLayout.CENTER
        );

        mainArea.add(
                rightVisual,
                BorderLayout.EAST
        );

        // =====================================================
        // BOTTOM STATUS BAR
        // =====================================================

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
                        "Authorized personnel only"
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

        // =====================================================
        // ADD EVERYTHING
        // =====================================================

        background.add(
                header,
                BorderLayout.NORTH
        );

        background.add(
                mainArea,
                BorderLayout.CENTER
        );

        background.add(
                bottom,
                BorderLayout.SOUTH
        );
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

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY
            );

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // =================================================
            // BASE BACKGROUND
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
                                    40
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
            // TOP DARK GLASS
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
            // HEADER ACCENT LINE
            // =================================================

            GradientPaint accent =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    21,
                                    230,
                                    154,
                                    210
                            ),
                            getWidth(),
                            0,
                            new Color(
                                    21,
                                    230,
                                    154,
                                    0
                            )
                    );

            g2.setPaint(
                    accent
            );

            g2.fillRect(
                    0,
                    88,
                    getWidth(),
                    2
            );

            // =================================================
            // DECORATIVE CIRCLES
            // =================================================

            g2.setColor(
                    new Color(
                            28,
                            224,
                            158,
                            15
                    )
            );

            g2.fillOval(
                    -130,
                    200,
                    320,
                    320
            );

            g2.setColor(
                    new Color(
                            53,
                            170,
                            255,
                            10
                    )
            );

            g2.fillOval(
                    420,
                    -160,
                    360,
                    360
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
                            34,
                            225,
                            157,
                            70
                    )
            );

            int baseY =
                    getHeight() - 110;

            int startX =
                    40;

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
    // PORTAL CARD
    // =========================================================

    private class PortalCard
            extends JPanel {

        private final String title;
        private final String subtitle;
        private final String iconText;
        private final Color accentColor;
        private final String selectedRole;

        private boolean hover =
                false;

        public PortalCard(
                String title,
                String subtitle,
                String iconText,
                Color accentColor,
                String selectedRole
        ) {

            this.title =
                    title;

            this.subtitle =
                    subtitle;

            this.iconText =
                    iconText;

            this.accentColor =
                    accentColor;

            this.selectedRole =
                    selectedRole;

            setOpaque(false);

            setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );

            MouseAdapter mouse =
                    new MouseAdapter() {

                        @Override
                        public void mouseEntered(
                                MouseEvent e
                        ) {

                            hover =
                                    true;

                            repaint();
                        }

                        @Override
                        public void mouseExited(
                                MouseEvent e
                        ) {

                            hover =
                                    false;

                            repaint();
                        }

                        @Override
                        public void mouseClicked(
                                MouseEvent e
                        ) {

                            portalSelected(
                                    PortalCard.this.selectedRole
                            );
                        }
                    };

            addMouseListener(
                    mouse
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
                            100
                    )
            );

            g2.fillRoundRect(
                    7,
                    9,
                    w - 12,
                    h - 12,
                    22,
                    22
            );

            // =================================================
            // CARD BACKGROUND
            // =================================================

            Color topColor;
            Color bottomColor;

            if (hover) {

                topColor =
                        new Color(
                                accentColor.getRed(),
                                accentColor.getGreen(),
                                accentColor.getBlue(),
                                85
                        );

                bottomColor =
                        new Color(
                                5,
                                35,
                                35,
                                235
                        );

            } else {

                topColor =
                        new Color(
                                accentColor.getRed(),
                                accentColor.getGreen(),
                                accentColor.getBlue(),
                                45
                        );

                bottomColor =
                        new Color(
                                4,
                                31,
                                32,
                                225
                        );
            }

            GradientPaint gradient =
                    new GradientPaint(
                            0,
                            0,
                            topColor,
                            0,
                            h,
                            bottomColor
                    );

            g2.setPaint(
                    gradient
            );

            g2.fillRoundRect(
                    2,
                    2,
                    w - 7,
                    h - 9,
                    20,
                    20
            );

            // =================================================
            // BORDER
            // =================================================

            g2.setColor(
                    hover
                            ? accentColor
                            : new Color(
                                    accentColor.getRed(),
                                    accentColor.getGreen(),
                                    accentColor.getBlue(),
                                    180
                            )
            );

            g2.setStroke(
                    new BasicStroke(
                            hover
                                    ? 2.2f
                                    : 1.3f
                    )
            );

            g2.drawRoundRect(
                    2,
                    2,
                    w - 7,
                    h - 9,
                    20,
                    20
            );

            // =================================================
            // TOP GLOW
            // =================================================

            GradientPaint topGlow =
                    new GradientPaint(
                            20,
                            0,
                            accentColor,
                            w - 20,
                            0,
                            new Color(
                                    accentColor.getRed(),
                                    accentColor.getGreen(),
                                    accentColor.getBlue(),
                                    20
                            )
                    );

            g2.setPaint(
                    topGlow
            );

            g2.fillRoundRect(
                    18,
                    2,
                    w - 40,
                    3,
                    3,
                    3
            );

            // =================================================
            // ICON CIRCLE
            // =================================================

            int iconX =
                    25;

            int iconY =
                    24;

            int iconSize =
                    48;

            g2.setColor(
                    new Color(
                            accentColor.getRed(),
                            accentColor.getGreen(),
                            accentColor.getBlue(),
                            hover
                                    ? 60
                                    : 35
                    )
            );

            g2.fillOval(
                    iconX,
                    iconY,
                    iconSize,
                    iconSize
            );

            g2.setColor(
                    accentColor
            );

            g2.setStroke(
                    new BasicStroke(
                            1.5f
                    )
            );

            g2.drawOval(
                    iconX,
                    iconY,
                    iconSize,
                    iconSize
            );

            // =================================================
            // ICON TEXT
            // =================================================

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            24
                    )
            );

            FontMetrics iconMetrics =
                    g2.getFontMetrics();

            int iconTextX =
                    iconX
                            + (
                            iconSize
                                    - iconMetrics.stringWidth(
                                    iconText
                            )
                    ) / 2;

            int iconTextY =
                    iconY
                            + (
                            iconSize
                                    + iconMetrics.getAscent()
                                    - iconMetrics.getDescent()
                    ) / 2;

            g2.drawString(
                    iconText,
                    iconTextX,
                    iconTextY
            );

            // =================================================
            // ROLE TITLE
            // =================================================

            g2.setColor(
                    TEXT_WHITE
            );

            int titleSize =
                    title.equals(
                            "MEDICAL MANAGER"
                    )
                            ? 15
                            : 17;

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            titleSize
                    )
            );

            g2.drawString(
                    title,
                    25,
                    100
            );

            // =================================================
            // SUBTITLE
            // =================================================

            g2.setColor(
                    accentColor
            );

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            11
                    )
            );

            g2.drawString(
                    subtitle,
                    25,
                    119
            );

            // =================================================
            // ACCESS TEXT
            // =================================================

            g2.setColor(
                    new Color(
                            205,
                            220,
                            215
                    )
            );

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            10
                    )
            );

            g2.drawString(
                    "ACCESS PORTAL",
                    25,
                    h - 25
            );

            // =================================================
            // ARROW BUTTON
            // =================================================

            int circleSize =
                    34;

            int circleX =
                    w
                            - circleSize
                            - 22;

            int circleY =
                    h
                            - circleSize
                            - 17;

            g2.setColor(
                    new Color(
                            accentColor.getRed(),
                            accentColor.getGreen(),
                            accentColor.getBlue(),
                            hover
                                    ? 90
                                    : 40
                    )
            );

            g2.fillOval(
                    circleX,
                    circleY,
                    circleSize,
                    circleSize
            );

            g2.setColor(
                    accentColor
            );

            g2.setStroke(
                    new BasicStroke(
                            1.5f
                    )
            );

            g2.drawOval(
                    circleX,
                    circleY,
                    circleSize,
                    circleSize
            );

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            20
                    )
            );

            g2.drawString(
                    ">",
                    circleX + 11,
                    circleY + 24
            );

            g2.dispose();
        }
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
    // PORTAL SELECTION
    // =========================================================
    //
    // IMPORTANT:
    //
    // The selected role is now passed to LoginFrame.
    //
    // Doctor          -> LoginFrame("Doctor")
    // Patient         -> LoginFrame("Patient")
    // Medical Manager -> LoginFrame("Medical Manager")
    // Admin Staff     -> LoginFrame("Admin Staff")
    //
    // LoginFrame will verify that the account actually belongs
    // to the selected portal.
    // =========================================================

    private void portalSelected(
            String role
    ) {

        LoginFrame loginFrame =
                new LoginFrame(
                        role
                );

        loginFrame.setVisible(
                true
        );

        dispose();
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    RoleSelectionFrame frame =
                            new RoleSelectionFrame();

                    frame.setVisible(
                            true
                    );
                }
        );
    }
}