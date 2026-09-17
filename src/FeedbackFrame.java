import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FeedbackFrame extends JFrame {

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
            new Color(5, 38, 43);

    // =========================================================
    // USER
    // =========================================================

    private final String patientUsername;

    // =========================================================
    // COMPONENTS
    // =========================================================

    private JComboBox<String> doctorComboBox;
    private JComboBox<String> ratingComboBox;
    private JTextArea commentArea;

    // =========================================================
    // BACKGROUND
    // =========================================================

    private BufferedImage backgroundImage;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public FeedbackFrame(String patientUsername) {

        this.patientUsername = patientUsername;

        setTitle(
                "APU Medical Centre - Feedback & Ratings"
        );

        setSize(
                920,
                700
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
        // ROOT
        // =====================================================

        FeedbackBackground root =
                new FeedbackBackground();

        root.setLayout(
                new BorderLayout()
        );

        setContentPane(root);

        root.add(
                createHeader(),
                BorderLayout.NORTH
        );

        JPanel center =
                new JPanel(
                        new GridBagLayout()
                );

        center.setOpaque(false);

        center.setBorder(
                new EmptyBorder(
                        12,
                        35,
                        12,
                        35
                )
        );

        center.add(
                createFeedbackCard()
        );

        root.add(
                center,
                BorderLayout.CENTER
        );

        root.add(
                createBottomBar(),
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
                        17,
                        28,
                        16,
                        30
                )
        );

        JPanel brand =
                new JPanel();

        brand.setOpaque(false);

        brand.setLayout(
                new BoxLayout(
                        brand,
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

        JLabel hospital =
                new JLabel(
                        "APU MEDICAL CENTRE"
                );

        hospital.setForeground(
                TEXT_WHITE
        );

        hospital.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        JLabel portal =
                new JLabel(
                        "PATIENT PORTAL"
                );

        portal.setForeground(
                PATIENT_BLUE
        );

        portal.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        brandText.add(hospital);

        brandText.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                3
                        )
                )
        );

        brandText.add(portal);

        brand.add(cross);

        brand.add(
                Box.createRigidArea(
                        new Dimension(
                                13,
                                0
                        )
                )
        );

        brand.add(brandText);

        // =====================================================
        // SESSION
        // =====================================================

        JPanel session =
                new JPanel();

        session.setOpaque(false);

        session.setLayout(
                new BoxLayout(
                        session,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel secure =
                new JLabel(
                        "●  SECURE SESSION"
                );

        secure.setForeground(
                EMERALD
        );

        secure.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        secure.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        JLabel username =
                new JLabel(
                        patientUsername
                );

        username.setForeground(
                TEXT_WHITE
        );

        username.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        username.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        session.add(secure);

        session.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );

        session.add(username);

        header.add(
                brand,
                BorderLayout.WEST
        );

        header.add(
                session,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================================================
    // FEEDBACK CARD
    // =========================================================

    private FeedbackCard createFeedbackCard() {

        FeedbackCard card =
                new FeedbackCard();

        card.setPreferredSize(
                new Dimension(
                        800,
                        530
                )
        );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        25,
                        35,
                        24,
                        35
                )
        );

        // =====================================================
        // TITLE AREA
        // =====================================================

        JPanel titlePanel =
                new JPanel(
                        new BorderLayout()
                );

        titlePanel.setOpaque(false);

        JPanel titleText =
                new JPanel();

        titleText.setOpaque(false);

        titleText.setLayout(
                new BoxLayout(
                        titleText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel small =
                new JLabel(
                        "PATIENT EXPERIENCE"
                );

        small.setForeground(
                PATIENT_BLUE
        );

        small.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        JLabel title =
                new JLabel(
                        "Feedback & Ratings"
                );

        title.setForeground(
                TEXT_WHITE
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        JLabel description =
                new JLabel(
                        "Tell us about your experience with your doctor and hospital care."
                );

        description.setForeground(
                TEXT_MUTED
        );

        description.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        titleText.add(small);

        titleText.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );

        titleText.add(title);

        titleText.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );

        titleText.add(description);

        FeedbackIcon feedbackIcon =
                new FeedbackIcon();

        feedbackIcon.setPreferredSize(
                new Dimension(
                        65,
                        65
                )
        );

        titlePanel.add(
                titleText,
                BorderLayout.WEST
        );

        titlePanel.add(
                feedbackIcon,
                BorderLayout.EAST
        );

        // =====================================================
        // FORM
        // =====================================================

        JPanel form =
                new JPanel(
                        new GridBagLayout()
                );

        form.setOpaque(false);

        form.setBorder(
                new EmptyBorder(
                        15,
                        0,
                        10,
                        0
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        6,
                        8,
                        6,
                        8
                );

        gbc.anchor =
                GridBagConstraints.WEST;

        // =====================================================
        // PATIENT
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        form.add(
                createFormLabel("PATIENT"),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(
                createLockedField(
                        patientUsername
                ),
                gbc
        );

        // =====================================================
        // DOCTOR
        // =====================================================

        doctorComboBox =
                new JComboBox<>(
                        new String[]{
                                "doctor01 - Cardiology",
                                "doctor02 - General Medicine",
                                "doctor03 - Dermatology"
                        }
                );

        styleComboBox(
                doctorComboBox
        );

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        form.add(
                createFormLabel("DOCTOR"),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(
                doctorComboBox,
                gbc
        );

        // =====================================================
        // RATING
        // =====================================================

        ratingComboBox =
                new JComboBox<>(
                        new String[]{
                                "5 - Excellent",
                                "4 - Good",
                                "3 - Average",
                                "2 - Poor",
                                "1 - Very Poor"
                        }
                );

        styleComboBox(
                ratingComboBox
        );

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;

        form.add(
                createFormLabel("RATING"),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(
                ratingComboBox,
                gbc
        );

        // =====================================================
        // RATING GUIDE
        // =====================================================

        JLabel ratingGuide =
                new JLabel(
                        "5 = Excellent     •     1 = Very Poor"
                );

        ratingGuide.setForeground(
                TEXT_MUTED
        );

        ratingGuide.setFont(
                new Font(
                        "Arial",
                        Font.ITALIC,
                        10
                )
        );

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1;

        form.add(
                ratingGuide,
                gbc
        );

        // =====================================================
        // COMMENT LABEL
        // =====================================================

        JLabel commentLabel =
                createFormLabel(
                        "COMMENT"
                );

        commentLabel.setVerticalAlignment(
                SwingConstants.TOP
        );

        // =====================================================
        // COMMENT TEXT AREA
        // =====================================================

        commentArea =
                new JTextArea();

        commentArea.setRows(5);
        commentArea.setColumns(30);

        commentArea.setLineWrap(true);

        commentArea.setWrapStyleWord(true);

        commentArea.setBackground(
                FIELD_BACKGROUND
        );

        commentArea.setForeground(
                TEXT_WHITE
        );

        commentArea.setCaretColor(
                PATIENT_BLUE
        );

        commentArea.setSelectionColor(
                new Color(
                        20,
                        91,
                        120
                )
        );

        commentArea.setSelectedTextColor(
                Color.WHITE
        );

        commentArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        commentArea.setMargin(
                new Insets(
                        10,
                        12,
                        10,
                        12
                )
        );

        // =====================================================
        // COMMENT SCROLL PANE
        // THIS FIXES THE COLLAPSED COMMENT BOX
        // =====================================================

        JScrollPane commentScroll =
                new JScrollPane(
                        commentArea
                );

        Dimension commentSize =
                new Dimension(
                        480,
                        115
                );

        commentScroll.setPreferredSize(
                commentSize
        );

        commentScroll.setMinimumSize(
                commentSize
        );

        commentScroll.setMaximumSize(
                commentSize
        );

        commentScroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        commentScroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        commentScroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                PATIENT_BLUE.getRed(),
                                PATIENT_BLUE.getGreen(),
                                PATIENT_BLUE.getBlue(),
                                150
                        ),
                        1
                )
        );

        commentScroll.getViewport()
                .setBackground(
                        FIELD_BACKGROUND
                );

        // =====================================================
        // COMMENT ROW
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill =
                GridBagConstraints.NONE;

        gbc.anchor =
                GridBagConstraints.NORTHWEST;

        gbc.insets =
                new Insets(
                        8,
                        8,
                        6,
                        8
                );

        form.add(
                commentLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        form.add(
                commentScroll,
                gbc
        );

        // =====================================================
        // ACTIONS
        // =====================================================

        JPanel actions =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        actions.setOpaque(false);

        JButton submitButton =
                createPrimaryButton(
                        "SUBMIT FEEDBACK",
                        165
                );

        JButton closeButton =
                createSecondaryButton(
                        "CLOSE",
                        100
                );

        submitButton.addActionListener(
                e -> submitFeedback()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        actions.add(
                submitButton
        );

        actions.add(
                closeButton
        );

        card.add(
                titlePanel,
                BorderLayout.NORTH
        );

        card.add(
                form,
                BorderLayout.CENTER
        );

        card.add(
                actions,
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // FORM LABEL
    // =========================================================

    private JLabel createFormLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setForeground(
                new Color(
                        180,
                        210,
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

        label.setPreferredSize(
                new Dimension(
                        105,
                        38
                )
        );

        return label;
    }

    // =========================================================
    // LOCKED PATIENT FIELD
    // =========================================================

    private JPanel createLockedField(
            String value
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                new Color(
                        4,
                        32,
                        37
                )
        );

        panel.setPreferredSize(
                new Dimension(
                        480,
                        38
                )
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        40,
                                        92,
                                        97
                                ),
                                1
                        ),
                        new EmptyBorder(
                                0,
                                12,
                                0,
                                12
                        )
                )
        );

        JLabel text =
                new JLabel(
                        value
                );

        text.setForeground(
                TEXT_WHITE
        );

        text.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        JLabel lock =
                new JLabel(
                        "LOCKED"
                );

        lock.setForeground(
                TEXT_MUTED
        );

        lock.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        8
                )
        );

        panel.add(
                text,
                BorderLayout.WEST
        );

        panel.add(
                lock,
                BorderLayout.EAST
        );

        return panel;
    }

    // =========================================================
    // COMBO BOX STYLE
    // =========================================================

    private void styleComboBox(
            JComboBox<String> comboBox
    ) {

        comboBox.setPreferredSize(
                new Dimension(
                        480,
                        42
                )
        );

        comboBox.setBackground(
                FIELD_BACKGROUND
        );

        comboBox.setForeground(
                TEXT_WHITE
        );

        comboBox.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        comboBox.setFocusable(false);

        comboBox.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                PATIENT_BLUE.getRed(),
                                PATIENT_BLUE.getGreen(),
                                PATIENT_BLUE.getBlue(),
                                100
                        ),
                        1
                )
        );

        comboBox.setRenderer(
                new DefaultListCellRenderer() {

                    @Override
                    public Component getListCellRendererComponent(
                            JList<?> list,
                            Object value,
                            int index,
                            boolean isSelected,
                            boolean cellHasFocus
                    ) {

                        JLabel label =
                                (JLabel)
                                        super.getListCellRendererComponent(
                                                list,
                                                value,
                                                index,
                                                isSelected,
                                                cellHasFocus
                                        );

                        label.setBorder(
                                new EmptyBorder(
                                        7,
                                        10,
                                        7,
                                        10
                                )
                        );

                        if (isSelected) {

                            label.setBackground(
                                    new Color(
                                            20,
                                            91,
                                            120
                                    )
                            );

                            label.setForeground(
                                    Color.WHITE
                            );

                        } else {

                            label.setBackground(
                                    FIELD_BACKGROUND
                            );

                            label.setForeground(
                                    TEXT_WHITE
                            );
                        }

                        return label;
                    }
                }
        );
    }

    // =========================================================
    // PRIMARY BUTTON
    // =========================================================

    private JButton createPrimaryButton(
            String text,
            int width
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(
                        width,
                        42
                )
        );

        button.setBackground(
                PATIENT_BLUE
        );

        button.setForeground(
                new Color(
                        2,
                        24,
                        30
                )
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
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
            String text,
            int width
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(
                        width,
                        42
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
                        10
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                PATIENT_BLUE.getRed(),
                                PATIENT_BLUE.getGreen(),
                                PATIENT_BLUE.getBlue(),
                                110
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
                        "●  SYSTEM READY     |     PATIENT EXPERIENCE"
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
    // SUBMIT FEEDBACK
    // =========================================================

    private void submitFeedback() {

        String selectedDoctor =
                doctorComboBox
                        .getSelectedItem()
                        .toString();

        String selectedRating =
                ratingComboBox
                        .getSelectedItem()
                        .toString();

        String comment =
                commentArea
                        .getText()
                        .trim();

        // =====================================================
        // COMMENT VALIDATION
        // =====================================================

        if (comment.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a comment.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            commentArea.requestFocusInWindow();

            return;
        }

        // =====================================================
        // DOCTOR USERNAME
        // =====================================================

        String doctorUsername =
                selectedDoctor
                        .split(" - ")[0]
                        .trim();

        // =====================================================
        // RATING
        // =====================================================

        String rating;

        if (selectedRating.startsWith("5")) {

            rating = "5";

        } else if (selectedRating.startsWith("4")) {

            rating = "4";

        } else if (selectedRating.startsWith("3")) {

            rating = "3";

        } else if (selectedRating.startsWith("2")) {

            rating = "2";

        } else {

            rating = "1";
        }

        // =====================================================
        // FEEDBACK ID
        // =====================================================

        String feedbackId =
                "FB"
                        + System.currentTimeMillis();

        String filePath =
                "data/feedback.txt";

        // =====================================================
        // SAVE TO FILE
        // =====================================================

        try (
                FileWriter fileWriter =
                        new FileWriter(
                                filePath,
                                true
                        );

                PrintWriter writer =
                        new PrintWriter(
                                fileWriter
                        )
        ) {

            writer.println(
                    feedbackId
                            + ","
                            + patientUsername
                            + ","
                            + doctorUsername
                            + ","
                            + rating
                            + ","
                            + comment.replace(
                                    ",",
                                    ";"
                            )
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Feedback submitted successfully!\n"
                            + "Rating: "
                            + rating
                            + "/5",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            commentArea.setText("");

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save feedback.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // BACKGROUND
    // =========================================================

    private class FeedbackBackground
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
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY
            );

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

            // Header shade

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

            // Header accent

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

            // Ambient glow

            g2.setColor(
                    new Color(
                            PATIENT_BLUE.getRed(),
                            PATIENT_BLUE.getGreen(),
                            PATIENT_BLUE.getBlue(),
                            13
                    )
            );

            g2.fillOval(
                    500,
                    100,
                    350,
                    350
            );

            g2.dispose();
        }
    }

    // =========================================================
    // FEEDBACK CARD
    // =========================================================

    private class FeedbackCard
            extends JPanel {

        public FeedbackCard() {

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

            int w = getWidth();
            int h = getHeight();

            // Shadow

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

            // Card

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

            // Border

            g2.setColor(
                    new Color(
                            PATIENT_BLUE.getRed(),
                            PATIENT_BLUE.getGreen(),
                            PATIENT_BLUE.getBlue(),
                            125
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

            // Top glow

            GradientPaint glow =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    PATIENT_BLUE.getRed(),
                                    PATIENT_BLUE.getGreen(),
                                    PATIENT_BLUE.getBlue(),
                                    50
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
                    85,
                    22,
                    22
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // FEEDBACK ICON
    // =========================================================

    private class FeedbackIcon
            extends JPanel {

        public FeedbackIcon() {

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
                            22
                    )
            );

            String text = "F";

            FontMetrics fm =
                    g2.getFontMetrics();

            int x =
                    5
                            + (
                            54
                                    - fm.stringWidth(text)
                    ) / 2;

            int y =
                    5
                            + (
                            54
                                    + fm.getAscent()
                                    - fm.getDescent()
                    ) / 2;

            g2.drawString(
                    text,
                    x,
                    y
            );

            g2.dispose();
        }
    }
}