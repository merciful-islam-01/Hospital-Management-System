import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class MyAppointmentsFrame extends JFrame {

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

    private final Color TABLE_BACKGROUND =
            new Color(5, 35, 39);

    private final Color TABLE_ALT =
            new Color(7, 42, 47);

    // =========================================================
    // USER
    // =========================================================

    private final String patientUsername;

    // =========================================================
    // TABLE
    // =========================================================

    private JTable appointmentTable;
    private DefaultTableModel tableModel;

    // =========================================================
    // FILES
    // =========================================================

    private final String APPOINTMENTS_FILE =
            "data/appointments.txt";

    private final String USERS_FILE =
            "data/users.txt";

    // =========================================================
    // BACKGROUND
    // =========================================================

    private BufferedImage backgroundImage;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public MyAppointmentsFrame(
            String patientUsername
    ) {

        this.patientUsername =
                patientUsername;

        setTitle(
                "APU Medical Centre - My Appointments"
        );

        setSize(
                1180,
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

        // =====================================================
        // CENTER
        // =====================================================

        JPanel center =
                new JPanel(
                        new BorderLayout()
                );

        center.setOpaque(false);

        center.setBorder(
                new EmptyBorder(
                        15,
                        30,
                        15,
                        30
                )
        );

        center.add(
                createAppointmentCard(),
                BorderLayout.CENTER
        );

        root.add(
                center,
                BorderLayout.CENTER
        );

        root.add(
                createBottomBar(),
                BorderLayout.SOUTH
        );

        // =====================================================
        // LOAD APPOINTMENTS
        // =====================================================

        loadAppointments();
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
        // BRAND
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
    // APPOINTMENT CARD
    // =========================================================

    private AppointmentCard createAppointmentCard() {

        AppointmentCard card =
                new AppointmentCard();

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        23,
                        28,
                        22,
                        28
                )
        );

        // =====================================================
        // CARD HEADER
        // =====================================================

        JPanel titleArea =
                new JPanel(
                        new BorderLayout()
                );

        titleArea.setOpaque(false);

        JPanel titleText =
                new JPanel();

        titleText.setOpaque(false);

        titleText.setLayout(
                new BoxLayout(
                        titleText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel section =
                new JLabel(
                        "APPOINTMENT SERVICES"
                );

        section.setForeground(
                PATIENT_BLUE
        );

        section.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        JLabel title =
                new JLabel(
                        "My Appointments"
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
                        "Review, reschedule or cancel your hospital appointments."
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

        titleText.add(section);

        titleText.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                3
                        )
                )
        );

        titleText.add(title);

        titleText.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );

        titleText.add(subtitle);

        JLabel countInfo =
                new JLabel(
                        "PATIENT  /  "
                                + patientUsername
                );

        countInfo.setForeground(
                PATIENT_BLUE
        );

        countInfo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        titleArea.add(
                titleText,
                BorderLayout.WEST
        );

        titleArea.add(
                countInfo,
                BorderLayout.EAST
        );

        // =====================================================
        // TABLE MODEL
        // =====================================================

        String[] columns = {
                "Appointment ID",
                "Doctor",
                "Specialism",
                "Date",
                "Time",
                "Status"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };

        // =====================================================
        // TABLE
        // =====================================================

        appointmentTable =
                new JTable(
                        tableModel
                );

        appointmentTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        appointmentTable.setForeground(
                TEXT_WHITE
        );

        appointmentTable.setBackground(
                TABLE_BACKGROUND
        );

        appointmentTable.setSelectionBackground(
                new Color(
                        20,
                        91,
                        120
                )
        );

        appointmentTable.setSelectionForeground(
                Color.WHITE
        );

        appointmentTable.setGridColor(
                new Color(
                        25,
                        67,
                        72
                )
        );

        appointmentTable.setRowHeight(
                37
        );

        appointmentTable.setShowVerticalLines(
                false
        );

        appointmentTable.setShowHorizontalLines(
                true
        );

        appointmentTable.setIntercellSpacing(
                new Dimension(
                        0,
                        1
                )
        );

        appointmentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        appointmentTable.setFillsViewportHeight(
                true
        );

        // =====================================================
        // TABLE HEADER
        // =====================================================

        JTableHeader tableHeader =
                appointmentTable
                        .getTableHeader();

        tableHeader.setBackground(
                new Color(
                        7,
                        48,
                        55
                )
        );

        tableHeader.setForeground(
                PATIENT_BLUE
        );

        tableHeader.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        tableHeader.setPreferredSize(
                new Dimension(
                        0,
                        38
                )
        );

        tableHeader.setReorderingAllowed(
                false
        );

        // =====================================================
        // CELL RENDERER
        // =====================================================

        appointmentTable.setDefaultRenderer(
                Object.class,
                new DefaultTableCellRenderer() {

                    @Override
                    public Component
                    getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column
                    ) {

                        JLabel cell =
                                (JLabel)
                                        super
                                                .getTableCellRendererComponent(
                                                        table,
                                                        value,
                                                        isSelected,
                                                        hasFocus,
                                                        row,
                                                        column
                                                );

                        cell.setBorder(
                                new EmptyBorder(
                                        0,
                                        10,
                                        0,
                                        10
                                )
                        );

                        if (isSelected) {

                            cell.setBackground(
                                    new Color(
                                            20,
                                            91,
                                            120
                                    )
                            );

                            cell.setForeground(
                                    Color.WHITE
                            );

                        } else {

                            if (row % 2 == 0) {

                                cell.setBackground(
                                        TABLE_BACKGROUND
                                );

                            } else {

                                cell.setBackground(
                                        TABLE_ALT
                                );
                            }

                            // Status column
                            if (column == 5) {

                                String status =
                                        value == null
                                                ? ""
                                                : value.toString();

                                if (
                                        status.equalsIgnoreCase(
                                                "Cancelled"
                                        )
                                ) {

                                    cell.setForeground(
                                            new Color(
                                                    255,
                                                    116,
                                                    116
                                            )
                                    );

                                } else {

                                    cell.setForeground(
                                            EMERALD
                                    );
                                }

                            } else {

                                cell.setForeground(
                                        TEXT_WHITE
                                );
                            }
                        }

                        return cell;
                    }
                }
        );

        // =====================================================
        // COLUMN WIDTHS
        // =====================================================

        appointmentTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(185);

        appointmentTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(145);

        appointmentTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(180);

        appointmentTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(120);

        appointmentTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(85);

        appointmentTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(105);

        // =====================================================
        // SCROLL PANE
        // =====================================================

        JScrollPane scrollPane =
                new JScrollPane(
                        appointmentTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                PATIENT_BLUE.getRed(),
                                PATIENT_BLUE.getGreen(),
                                PATIENT_BLUE.getBlue(),
                                85
                        ),
                        1
                )
        );

        scrollPane.getViewport()
                .setBackground(
                        TABLE_BACKGROUND
                );

        scrollPane.setOpaque(false);

        scrollPane.setPreferredSize(
                new Dimension(
                        1030,
                        330
                )
        );

        JPanel tableWrapper =
                new JPanel(
                        new BorderLayout()
                );

        tableWrapper.setOpaque(false);

        tableWrapper.setBorder(
                new EmptyBorder(
                        18,
                        0,
                        16,
                        0
                )
        );

        tableWrapper.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // ACTION BUTTONS
        // =====================================================

        JPanel actions =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                9,
                                0
                        )
                );

        actions.setOpaque(false);

        JButton refreshButton =
                createSecondaryButton(
                        "REFRESH",
                        100
                );

        JButton rescheduleButton =
                createPrimaryButton(
                        "RESCHEDULE",
                        125
                );

        JButton cancelButton =
                createDangerButton(
                        "CANCEL APPOINTMENT",
                        165
                );

        JButton closeButton =
                createSecondaryButton(
                        "CLOSE",
                        90
                );

        refreshButton.addActionListener(
                e -> loadAppointments()
        );

        rescheduleButton.addActionListener(
                e -> rescheduleAppointment()
        );

        cancelButton.addActionListener(
                e -> cancelAppointment()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        actions.add(
                refreshButton
        );

        actions.add(
                rescheduleButton
        );

        actions.add(
                cancelButton
        );

        actions.add(
                closeButton
        );

        card.add(
                titleArea,
                BorderLayout.NORTH
        );

        card.add(
                tableWrapper,
                BorderLayout.CENTER
        );

        card.add(
                actions,
                BorderLayout.SOUTH
        );

        return card;
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
                        38
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
                                105
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
    // DANGER BUTTON
    // =========================================================

    private JButton createDangerButton(
            String text,
            int width
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(
                        width,
                        38
                )
        );

        button.setBackground(
                new Color(
                        125,
                        35,
                        42
                )
        );

        button.setForeground(
                new Color(
                        255,
                        225,
                        225
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

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                215,
                                76,
                                85
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
                        "●  SYSTEM READY     |     APPOINTMENT RECORDS"
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
    // LOAD APPOINTMENTS
    // =========================================================

    private void loadAppointments() {

        tableModel.setRowCount(0);

        try {

            Path path =
                    Path.of(
                            APPOINTMENTS_FILE
                    );

            if (!Files.exists(path)) {

                return;
            }

            List<String> lines =
                    Files.readAllLines(path);

            for (String line : lines) {

                if (line.trim().isEmpty()) {

                    continue;
                }

                String[] data =
                        line.split(",", -1);

                if (data.length >= 6) {

                    String appointmentId =
                            data[0].trim();

                    String patient =
                            data[1].trim();

                    String storedDoctor =
                            data[2].trim();

                    String date =
                            data[3].trim();

                    String time =
                            data[4].trim();

                    String status =
                            data[5].trim();

                    if (
                            patient.equalsIgnoreCase(
                                    patientUsername
                            )
                    ) {

                        String doctorUsername =
                                extractDoctorUsername(
                                        storedDoctor
                                );

                        String[] doctorDetails =
                                getDoctorDetails(
                                        doctorUsername
                                );

                        String doctorName =
                                doctorDetails[0];

                        String specialism =
                                doctorDetails[1];

                        tableModel.addRow(
                                new Object[]{
                                        appointmentId,
                                        doctorName,
                                        specialism,
                                        date,
                                        time,
                                        status
                                }
                        );
                    }
                }
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load appointments.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // GET DOCTOR DETAILS FROM USERS.TXT
    // =========================================================

    private String[] getDoctorDetails(
            String doctorUsername
    ) {

        Path path =
                Path.of(
                        USERS_FILE
                );

        if (!Files.exists(path)) {

            return new String[]{
                    doctorUsername,
                    "Unknown"
            };
        }

        try {

            List<String> users =
                    Files.readAllLines(path);

            for (String line : users) {

                if (line.trim().isEmpty()) {

                    continue;
                }

                String[] data =
                        line.split(",", -1);

                /*
                 * users.txt:
                 *
                 * 0 User ID
                 * 1 Name
                 * 2 Username
                 * 3 Password
                 * 4 Contact
                 * 5 Role
                 * 6 Role ID
                 * 7 Specialty
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

                    if (
                            username.equalsIgnoreCase(
                                    doctorUsername
                            )
                                    &&
                                    role.equalsIgnoreCase(
                                            "Doctor"
                                    )
                    ) {

                        return new String[]{
                                name,
                                specialty
                        };
                    }
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Unable to read doctor details: "
                            + e.getMessage()
            );
        }

        return new String[]{
                doctorUsername,
                "Unknown"
        };
    }

    // =========================================================
    // EXTRACT DOCTOR USERNAME
    // =========================================================

    private String extractDoctorUsername(
            String storedDoctor
    ) {

        /*
         * NEW:
         * doctor02
         *
         * OLD:
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
    // CANCEL APPOINTMENT
    // =========================================================

    private void cancelAppointment() {

        int selectedRow =
                appointmentTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an appointment first.",
                    "No Appointment Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String appointmentId =
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString();

        /*
         * 0 Appointment ID
         * 1 Doctor
         * 2 Specialism
         * 3 Date
         * 4 Time
         * 5 Status
         */

        String currentStatus =
                tableModel
                        .getValueAt(
                                selectedRow,
                                5
                        )
                        .toString();

        if (
                currentStatus.equalsIgnoreCase(
                        "Cancelled"
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "This appointment is already cancelled."
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to cancel this appointment?",
                        "Cancel Appointment",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                choice
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }

        try {

            Path path =
                    Path.of(
                            APPOINTMENTS_FILE
                    );

            List<String> lines =
                    Files.readAllLines(path);

            for (
                    int i = 0;
                    i < lines.size();
                    i++
            ) {

                String[] data =
                        lines
                                .get(i)
                                .split(",", -1);

                if (
                        data.length >= 6
                                &&
                                data[0]
                                        .trim()
                                        .equals(
                                                appointmentId
                                        )
                ) {

                    data[5] =
                            "Cancelled";

                    lines.set(
                            i,
                            String.join(
                                    ",",
                                    data
                            )
                    );

                    break;
                }
            }

            Files.write(
                    path,
                    lines
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Appointment cancelled successfully."
            );

            loadAppointments();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to cancel appointment.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // RESCHEDULE APPOINTMENT
    // =========================================================

    private void rescheduleAppointment() {

        int selectedRow =
                appointmentTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an appointment first.",
                    "No Appointment Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String appointmentId =
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString();

        String currentStatus =
                tableModel
                        .getValueAt(
                                selectedRow,
                                5
                        )
                        .toString();

        if (
                currentStatus.equalsIgnoreCase(
                        "Cancelled"
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "A cancelled appointment cannot be rescheduled.",
                    "Cannot Reschedule",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =====================================================
        // NEW DATE
        // =====================================================

        String newDate =
                JOptionPane.showInputDialog(
                        this,
                        "Enter new date (DD-MM-YYYY):",
                        tableModel.getValueAt(
                                selectedRow,
                                3
                        )
                );

        if (newDate == null) {

            return;
        }

        newDate =
                newDate.trim();

        if (!isValidDate(newDate)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid date.\n"
                            + "Format: DD-MM-YYYY\n"
                            + "Example: 30-10-2026",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =====================================================
        // PREVENT PAST DATE
        // =====================================================

        DateTimeFormatter formatter =
                DateTimeFormatter
                        .ofPattern(
                                "dd-MM-uuuu"
                        )
                        .withResolverStyle(
                                ResolverStyle.STRICT
                        );

        LocalDate selectedDate =
                LocalDate.parse(
                        newDate,
                        formatter
                );

        if (
                selectedDate.isBefore(
                        LocalDate.now()
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "You cannot reschedule an appointment to a past date.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =====================================================
        // NEW TIME
        // =====================================================

        String[] times = {
                "09:00",
                "10:00",
                "11:00",
                "14:00",
                "15:00",
                "16:00"
        };

        String newTime =
                (String)
                        JOptionPane
                                .showInputDialog(
                                        this,
                                        "Select new time:",
                                        "Reschedule Appointment",
                                        JOptionPane.PLAIN_MESSAGE,
                                        null,
                                        times,
                                        tableModel.getValueAt(
                                                selectedRow,
                                                4
                                        )
                                );

        if (newTime == null) {

            return;
        }

        // =====================================================
        // GET DOCTOR USERNAME
        // =====================================================

        String doctorUsername =
                getAppointmentDoctorUsername(
                        appointmentId
                );

        if (doctorUsername == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to identify the doctor for this appointment.",
                    "Reschedule Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // =====================================================
        // PREVENT DOUBLE BOOKING
        // =====================================================

        if (
                isDoctorAlreadyBooked(
                        doctorUsername,
                        newDate,
                        newTime,
                        appointmentId
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "This doctor already has another appointment\n"
                            + "on "
                            + newDate
                            + " at "
                            + newTime
                            + ".\n\n"
                            + "Please choose another time.",
                    "Time Slot Unavailable",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =====================================================
        // UPDATE FILE
        // =====================================================

        try {

            Path path =
                    Path.of(
                            APPOINTMENTS_FILE
                    );

            List<String> lines =
                    Files.readAllLines(path);

            for (
                    int i = 0;
                    i < lines.size();
                    i++
            ) {

                String[] data =
                        lines
                                .get(i)
                                .split(",", -1);

                if (
                        data.length >= 6
                                &&
                                data[0]
                                        .trim()
                                        .equals(
                                                appointmentId
                                        )
                ) {

                    data[3] =
                            newDate;

                    data[4] =
                            newTime;

                    data[5] =
                            "Booked";

                    lines.set(
                            i,
                            String.join(
                                    ",",
                                    data
                            )
                    );

                    break;
                }
            }

            Files.write(
                    path,
                    lines
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Appointment rescheduled successfully!"
            );

            loadAppointments();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to reschedule appointment.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // FIND DOCTOR FOR APPOINTMENT
    // =========================================================

    private String getAppointmentDoctorUsername(
            String appointmentId
    ) {

        try {

            Path path =
                    Path.of(
                            APPOINTMENTS_FILE
                    );

            if (!Files.exists(path)) {

                return null;
            }

            List<String> lines =
                    Files.readAllLines(path);

            for (String line : lines) {

                String[] data =
                        line.split(",", -1);

                if (
                        data.length >= 6
                                &&
                                data[0]
                                        .trim()
                                        .equals(
                                                appointmentId
                                        )
                ) {

                    return extractDoctorUsername(
                            data[2].trim()
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Unable to find appointment doctor: "
                            + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // CHECK DOCTOR AVAILABILITY DURING RESCHEDULE
    // =========================================================

    private boolean isDoctorAlreadyBooked(
            String doctorUsername,
            String date,
            String time,
            String currentAppointmentId
    ) {

        try {

            Path path =
                    Path.of(
                            APPOINTMENTS_FILE
                    );

            if (!Files.exists(path)) {

                return false;
            }

            List<String> lines =
                    Files.readAllLines(path);

            for (String line : lines) {

                if (line.trim().isEmpty()) {

                    continue;
                }

                String[] data =
                        line.split(",", -1);

                if (data.length >= 6) {

                    String appointmentId =
                            data[0].trim();

                    String savedDoctor =
                            extractDoctorUsername(
                                    data[2].trim()
                            );

                    String savedDate =
                            data[3].trim();

                    String savedTime =
                            data[4].trim();

                    String savedStatus =
                            data[5].trim();

                    if (
                            !appointmentId.equals(
                                    currentAppointmentId
                            )
                                    &&
                                    savedDoctor.equalsIgnoreCase(
                                            doctorUsername
                                    )
                                    &&
                                    savedDate.equals(
                                            date
                                    )
                                    &&
                                    savedTime.equals(
                                            time
                                    )
                                    &&
                                    !savedStatus.equalsIgnoreCase(
                                            "Cancelled"
                                    )
                    ) {

                        return true;
                    }
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Unable to check doctor availability: "
                            + e.getMessage()
            );
        }

        return false;
    }

    // =========================================================
    // DATE VALIDATION
    // =========================================================

    private boolean isValidDate(
            String date
    ) {

        DateTimeFormatter formatter =
                DateTimeFormatter
                        .ofPattern(
                                "dd-MM-uuuu"
                        )
                        .withResolverStyle(
                                ResolverStyle.STRICT
                        );

        try {

            LocalDate.parse(
                    date,
                    formatter
            );

            return true;

        } catch (
                DateTimeParseException e
        ) {

            return false;
        }
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

            g2.setPaint(
                    overlay
            );

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );

            // Header background

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

            // Blue ambient glow

            g2.setColor(
                    new Color(
                            PATIENT_BLUE.getRed(),
                            PATIENT_BLUE.getGreen(),
                            PATIENT_BLUE.getBlue(),
                            11
                    )
            );

            g2.fillOval(
                    650,
                    100,
                    400,
                    400
            );

            g2.dispose();
        }
    }

    // =========================================================
    // GLASS CARD
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

            int w =
                    getWidth();

            int h =
                    getHeight();

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
                            120
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
                                    43
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
                    90,
                    22,
                    22
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }
}