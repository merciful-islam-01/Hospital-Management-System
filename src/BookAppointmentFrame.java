import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BookAppointmentFrame extends JFrame {

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
    // COMPONENTS
    // =========================================================

    private JComboBox<String> doctorComboBox;
    private JTextField dateField;
    private JComboBox<String> timeComboBox;

    // =========================================================
    // USER + FILES
    // =========================================================

    private final String patientUsername;

    private final String USERS_FILE =
            "data/users.txt";

    private final String APPOINTMENTS_FILE =
            "data/appointments.txt";

    // =========================================================
    // STRICT DATE FORMAT
    // =========================================================

    private final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter
                    .ofPattern("dd-MM-uuuu")
                    .withResolverStyle(
                            ResolverStyle.STRICT
                    );

    // =========================================================
    // BACKGROUND
    // =========================================================

    private BufferedImage backgroundImage;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public BookAppointmentFrame(
            String patientUsername
    ) {

        this.patientUsername =
                patientUsername;

        setTitle(
                "APU Medical Centre - Book Appointment"
        );

        setSize(
                920,
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
        // ROOT
        // =====================================================

        AppointmentBackground root =
                new AppointmentBackground();

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
                        15,
                        35,
                        15,
                        35
                )
        );

        center.add(
                createAppointmentCard()
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
                        15,
                        30
                )
        );

        // =====================================================
        // LEFT BRAND
        // =====================================================

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
        // RIGHT SESSION
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
    // APPOINTMENT CARD
    // =========================================================

    private AppointmentCard createAppointmentCard() {

        AppointmentCard card =
                new AppointmentCard();

        card.setPreferredSize(
                new Dimension(
                        800,
                        475
                )
        );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        26,
                        36,
                        25,
                        36
                )
        );

        // =====================================================
        // TITLE AREA
        // =====================================================

        JPanel titleArea =
                new JPanel(
                        new BorderLayout()
                );

        titleArea.setOpaque(false);

        JPanel titles =
                new JPanel();

        titles.setOpaque(false);

        titles.setLayout(
                new BoxLayout(
                        titles,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel small =
                new JLabel(
                        "APPOINTMENT SERVICES"
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
                        "Book Appointment"
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
                        "Select your doctor, preferred date and available consultation time."
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

        titles.add(small);

        titles.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );

        titles.add(title);

        titles.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );

        titles.add(description);

        AppointmentIcon icon =
                new AppointmentIcon();

        icon.setPreferredSize(
                new Dimension(
                        65,
                        65
                )
        );

        titleArea.add(
                titles,
                BorderLayout.WEST
        );

        titleArea.add(
                icon,
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
                        20,
                        0,
                        12,
                        0
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8,
                        8,
                        8,
                        8
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.WEST;

        // =====================================================
        // PATIENT
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        form.add(
                createFormLabel(
                        "PATIENT"
                ),
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
                new JComboBox<>();

        styleComboBox(
                doctorComboBox
        );

        // Existing business logic
        loadDoctors();

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        form.add(
                createFormLabel(
                        "DOCTOR"
                ),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(
                doctorComboBox,
                gbc
        );

        // =====================================================
        // DATE
        // =====================================================

        dateField =
                new JTextField();

        styleTextField(
                dateField
        );

        dateField.setToolTipText(
                "Example: 30-10-2026"
        );

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;

        form.add(
                createFormLabel(
                        "DATE"
                ),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        JPanel dateContainer =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        dateContainer.setOpaque(false);

        dateContainer.add(
                dateField,
                BorderLayout.CENTER
        );

        JLabel format =
                new JLabel(
                        "DD-MM-YYYY"
                );

        format.setForeground(
                PATIENT_BLUE
        );

        format.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        format.setPreferredSize(
                new Dimension(
                        85,
                        38
                )
        );

        dateContainer.add(
                format,
                BorderLayout.EAST
        );

        form.add(
                dateContainer,
                gbc
        );

        // =====================================================
        // TIME
        // =====================================================

        timeComboBox =
                new JComboBox<>(
                        new String[]{
                                "09:00",
                                "10:00",
                                "11:00",
                                "14:00",
                                "15:00",
                                "16:00"
                        }
                );

        styleComboBox(
                timeComboBox
        );

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;

        form.add(
                createFormLabel(
                        "TIME"
                ),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(
                timeComboBox,
                gbc
        );

        // =====================================================
        // INFORMATION
        // =====================================================

        JPanel infoPanel =
                new JPanel(
                        new BorderLayout()
                );

        infoPanel.setBackground(
                new Color(
                        7,
                        48,
                        54,
                        190
                )
        );

        infoPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        PATIENT_BLUE.getRed(),
                                        PATIENT_BLUE.getGreen(),
                                        PATIENT_BLUE.getBlue(),
                                        75
                                ),
                                1
                        ),
                        new EmptyBorder(
                                8,
                                12,
                                8,
                                12
                        )
                )
        );

        JLabel info =
                new JLabel(
                        "●  Doctors are loaded automatically from hospital user records."
                );

        info.setForeground(
                new Color(
                        185,
                        211,
                        207
                )
        );

        info.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        infoPanel.add(
                info,
                BorderLayout.WEST
        );

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1;

        form.add(
                infoPanel,
                gbc
        );

        // =====================================================
        // BUTTONS
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

        JButton bookButton =
                createPrimaryButton(
                        "BOOK APPOINTMENT",
                        165
                );

        JButton cancelButton =
                createSecondaryButton(
                        "CANCEL",
                        95
                );

        bookButton.addActionListener(
                e -> bookAppointment()
        );

        cancelButton.addActionListener(
                e -> dispose()
        );

        actions.add(bookButton);
        actions.add(cancelButton);

        card.add(
                titleArea,
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
                        100,
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
                new JLabel(value);

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

        JLabel locked =
                new JLabel(
                        "LOCKED"
                );

        locked.setForeground(
                TEXT_MUTED
        );

        locked.setFont(
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
                locked,
                BorderLayout.EAST
        );

        return panel;
    }

    // =========================================================
    // TEXT FIELD STYLE
    // =========================================================

    private void styleTextField(
            JTextField field
    ) {

        field.setPreferredSize(
                new Dimension(
                        385,
                        38
                )
        );

        field.setBackground(
                FIELD_BACKGROUND
        );

        field.setForeground(
                TEXT_WHITE
        );

        field.setCaretColor(
                PATIENT_BLUE
        );

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        PATIENT_BLUE.getRed(),
                                        PATIENT_BLUE.getGreen(),
                                        PATIENT_BLUE.getBlue(),
                                        100
                                ),
                                1
                        ),
                        new EmptyBorder(
                                0,
                                11,
                                0,
                                11
                        )
                )
        );
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
                        38
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
                        39
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
                        39
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
                        "●  SYSTEM READY     |     APPOINTMENT SERVICES"
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
    // LOAD DOCTORS FROM USERS.TXT
    // =========================================================

    private void loadDoctors() {

        doctorComboBox.removeAllItems();

        Path path =
                Paths.get(
                        USERS_FILE
                );

        if (!Files.exists(path)) {

            JOptionPane.showMessageDialog(
                    this,
                    "users.txt could not be found.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            List<String> lines =
                    Files.readAllLines(path);

            for (String line : lines) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data =
                        line.split(",", -1);

                /*
                 * users.txt format:
                 *
                 * 0 = User ID
                 * 1 = Name
                 * 2 = Username
                 * 3 = Password
                 * 4 = Contact
                 * 5 = Role
                 * 6 = Role ID
                 * 7 = Specialty / Extra
                 */

                if (data.length >= 8) {

                    String name =
                            data[1].trim();

                    String username =
                            data[2].trim();

                    String role =
                            data[5].trim();

                    String specialty =
                            data[7].trim();

                    if (role.equalsIgnoreCase(
                            "Doctor"
                    )) {

                        String displayText =
                                name
                                        + " ("
                                        + username
                                        + ") - "
                                        + specialty;

                        doctorComboBox.addItem(
                                displayText
                        );
                    }
                }
            }

            if (
                    doctorComboBox.getItemCount()
                            == 0
            ) {

                doctorComboBox.addItem(
                        "No doctors available"
                );
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load doctors.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // BOOK APPOINTMENT
    // =========================================================

    private void bookAppointment() {

        if (
                doctorComboBox.getItemCount()
                        == 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No doctors are currently available.",
                    "Doctor Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String selectedDoctor =
                (String)
                        doctorComboBox
                                .getSelectedItem();

        if (
                selectedDoctor == null
                        || selectedDoctor.equals(
                        "No doctors available"
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No doctor is available for booking.",
                    "Doctor Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String date =
                dateField
                        .getText()
                        .trim();

        String time =
                (String)
                        timeComboBox
                                .getSelectedItem();

        // =====================================================
        // EMPTY DATE CHECK
        // =====================================================

        if (date.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter appointment date.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =====================================================
        // STRICT DD-MM-YYYY VALIDATION
        // =====================================================

        LocalDate appointmentDate;

        try {

            appointmentDate =
                    LocalDate.parse(
                            date,
                            DATE_FORMATTER
                    );

        } catch (
                DateTimeParseException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date.\n\n"
                            + "Please use DD-MM-YYYY format.\n"
                            + "Example: 30-10-2026",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =====================================================
        // PREVENT PAST DATE
        // =====================================================

        if (
                appointmentDate.isBefore(
                        LocalDate.now()
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "You cannot book an appointment in the past.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =====================================================
        // EXTRACT DOCTOR USERNAME
        // =====================================================

        String doctorUsername =
                extractDoctorUsername(
                        selectedDoctor
                );

        if (doctorUsername == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to identify the selected doctor.",
                    "Booking Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // =====================================================
        // CHECK DOCTOR TIME SLOT
        // =====================================================

        if (
                isDoctorAlreadyBooked(
                        doctorUsername,
                        date,
                        time
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "This doctor already has an appointment\n"
                            + "on "
                            + date
                            + " at "
                            + time
                            + ".\n\n"
                            + "Please choose another time.",
                    "Time Slot Unavailable",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =====================================================
        // CREATE APPOINTMENT
        // =====================================================

        String appointmentId =
                "APT"
                        + System.currentTimeMillis();

        String status =
                "Booked";

        try (
                FileWriter fileWriter =
                        new FileWriter(
                                APPOINTMENTS_FILE,
                                true
                        );

                PrintWriter writer =
                        new PrintWriter(
                                fileWriter
                        )
        ) {

            /*
             * Appointment format:
             *
             * AppointmentID,
             * PatientUsername,
             * DoctorUsername,
             * Date,
             * Time,
             * Status
             */

            writer.println(
                    appointmentId
                            + ","
                            + patientUsername
                            + ","
                            + doctorUsername
                            + ","
                            + date
                            + ","
                            + time
                            + ","
                            + status
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Appointment booked successfully!\n\n"
                            + "Doctor: "
                            + selectedDoctor
                            + "\n"
                            + "Date: "
                            + date
                            + "\n"
                            + "Time: "
                            + time,
                    "Appointment Confirmed",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dateField.setText("");

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save appointment.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // EXTRACT DOCTOR USERNAME
    // =========================================================

    private String extractDoctorUsername(
            String displayText
    ) {

        int start =
                displayText.indexOf("(");

        int end =
                displayText.indexOf(")");

        if (
                start == -1
                        || end == -1
                        || end <= start
        ) {

            return null;
        }

        return displayText
                .substring(
                        start + 1,
                        end
                )
                .trim();
    }

    // =========================================================
    // CHECK WHETHER DOCTOR IS ALREADY BOOKED
    // =========================================================

    private boolean isDoctorAlreadyBooked(
            String doctorUsername,
            String date,
            String time
    ) {

        Path path =
                Paths.get(
                        APPOINTMENTS_FILE
                );

        if (!Files.exists(path)) {

            return false;
        }

        try {

            List<String> lines =
                    Files.readAllLines(path);

            for (String line : lines) {

                if (line.trim().isEmpty()) {

                    continue;
                }

                String[] data =
                        line.split(",", -1);

                if (data.length >= 6) {

                    String savedDoctor =
                            extractStoredDoctorUsername(
                                    data[2].trim()
                            );

                    String savedDate =
                            data[3].trim();

                    String savedTime =
                            data[4].trim();

                    String savedStatus =
                            data[5].trim();

                    if (
                            savedDoctor.equalsIgnoreCase(
                                    doctorUsername
                            )
                                    && savedDate.equals(
                                    date
                            )
                                    && savedTime.equals(
                                    time
                            )
                                    && !savedStatus.equalsIgnoreCase(
                                    "Cancelled"
                            )
                    ) {

                        return true;
                    }
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Unable to check appointment availability: "
                            + e.getMessage()
            );
        }

        return false;
    }

    // =========================================================
    // SUPPORT OLD + NEW APPOINTMENT RECORDS
    // =========================================================

    private String extractStoredDoctorUsername(
            String storedDoctor
    ) {

        /*
         * NEW format:
         * doctor02
         *
         * OLD format:
         * doctor02 - General Medicine
         */

        if (
                storedDoctor.contains(
                        " - "
                )
        ) {

            return storedDoctor
                    .substring(
                            0,
                            storedDoctor.indexOf(
                                    " - "
                            )
                    )
                    .trim();
        }

        return storedDoctor.trim();
    }

    // =========================================================
    // BACKGROUND
    // =========================================================

    private class AppointmentBackground
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

            g2.setPaint(overlay);

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

            g2.setPaint(accent);

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
                    510,
                    110,
                    350,
                    350
            );

            g2.dispose();
        }
    }

    // =========================================================
    // APPOINTMENT CARD
    // =========================================================

    private class AppointmentCard
            extends JPanel {

        public AppointmentCard() {

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

            // Main card

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

            g2.setPaint(glow);

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
    // APPOINTMENT ICON
    // =========================================================

    private class AppointmentIcon
            extends JPanel {

        public AppointmentIcon() {

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
                            21
                    )
            );

            String text = "+";

            FontMetrics fm =
                    g2.getFontMetrics();

            int x =
                    5
                            + (
                            54
                                    - fm.stringWidth(
                                    text
                            )
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