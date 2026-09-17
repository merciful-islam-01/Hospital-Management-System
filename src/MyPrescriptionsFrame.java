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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class MyPrescriptionsFrame extends JFrame {

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

    private JTable prescriptionTable;
    private DefaultTableModel tableModel;

    // =========================================================
    // FILES
    // =========================================================

    private final String PRESCRIPTIONS_FILE =
            "data/prescriptions.txt";

    private final String USERS_FILE =
            "data/users.txt";

    // =========================================================
    // BACKGROUND
    // =========================================================

    private BufferedImage backgroundImage;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public MyPrescriptionsFrame(String patientUsername) {

        this.patientUsername = patientUsername;

        setTitle(
                "APU Medical Centre - My Prescriptions"
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

            backgroundImage = null;
        }

        // =====================================================
        // ROOT
        // =====================================================

        PrescriptionBackground root =
                new PrescriptionBackground();

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
                createPrescriptionCard(),
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
        // LOAD PRESCRIPTIONS
        // =====================================================

        loadPrescriptions();
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
    // PRESCRIPTION CARD
    // =========================================================

    private PrescriptionCard createPrescriptionCard() {

        PrescriptionCard card =
                new PrescriptionCard();

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
        // CARD TITLE
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
                        "CLINICAL MEDICATION RECORDS"
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
                        "My Prescriptions"
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
                        "Review medicines, dosage instructions and treatment guidance issued by your doctors."
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

        PrescriptionIcon icon =
                new PrescriptionIcon();

        icon.setPreferredSize(
                new Dimension(
                        65,
                        65
                )
        );

        titleArea.add(
                titleText,
                BorderLayout.WEST
        );

        titleArea.add(
                icon,
                BorderLayout.EAST
        );

        // =====================================================
        // TABLE MODEL
        // =====================================================

        String[] columns = {
                "Prescription ID",
                "Doctor",
                "Specialism",
                "Date",
                "Medicine",
                "Dosage",
                "Instructions"
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

        prescriptionTable =
                new JTable(
                        tableModel
                );

        prescriptionTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        prescriptionTable.setForeground(
                TEXT_WHITE
        );

        prescriptionTable.setBackground(
                TABLE_BACKGROUND
        );

        prescriptionTable.setSelectionBackground(
                new Color(
                        20,
                        91,
                        120
                )
        );

        prescriptionTable.setSelectionForeground(
                Color.WHITE
        );

        prescriptionTable.setGridColor(
                new Color(
                        25,
                        67,
                        72
                )
        );

        prescriptionTable.setRowHeight(
                38
        );

        prescriptionTable.setShowVerticalLines(
                false
        );

        prescriptionTable.setShowHorizontalLines(
                true
        );

        prescriptionTable.setIntercellSpacing(
                new Dimension(
                        0,
                        1
                )
        );

        prescriptionTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        prescriptionTable.setFillsViewportHeight(
                true
        );

        // =====================================================
        // TABLE HEADER
        // =====================================================

        JTableHeader tableHeader =
                prescriptionTable
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
        // TABLE RENDERER
        // =====================================================

        prescriptionTable.setDefaultRenderer(
                Object.class,
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column
                    ) {

                        JLabel cell =
                                (JLabel)
                                        super.getTableCellRendererComponent(
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

                            if (column == 4) {

                                cell.setForeground(
                                        EMERALD
                                );

                            } else if (column == 2) {

                                cell.setForeground(
                                        PATIENT_BLUE
                                );

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

        prescriptionTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(135);

        prescriptionTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(125);

        prescriptionTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(155);

        prescriptionTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(105);

        prescriptionTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(135);

        prescriptionTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(90);

        prescriptionTable
                .getColumnModel()
                .getColumn(6)
                .setPreferredWidth(230);

        // =====================================================
        // SCROLL
        // =====================================================

        JScrollPane scrollPane =
                new JScrollPane(
                        prescriptionTable
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

        JPanel tableWrapper =
                new JPanel(
                        new BorderLayout()
                );

        tableWrapper.setOpaque(false);

        tableWrapper.setBorder(
                new EmptyBorder(
                        18,
                        0,
                        15,
                        0
                )
        );

        tableWrapper.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // BOTTOM ACTION AREA
        // =====================================================

        JPanel bottomArea =
                new JPanel(
                        new BorderLayout()
                );

        bottomArea.setOpaque(false);

        JLabel hint =
                new JLabel(
                        "Select a prescription to view its complete medication instructions."
                );

        hint.setForeground(
                TEXT_MUTED
        );

        hint.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

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
                        110
                );

        JButton detailsButton =
                createPrimaryButton(
                        "VIEW DETAILS",
                        135
                );

        JButton closeButton =
                createSecondaryButton(
                        "CLOSE",
                        100
                );

        refreshButton.addActionListener(
                e -> loadPrescriptions()
        );

        detailsButton.addActionListener(
                e -> viewDetails()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        actions.add(
                refreshButton
        );

        actions.add(
                detailsButton
        );

        actions.add(
                closeButton
        );

        bottomArea.add(
                hint,
                BorderLayout.WEST
        );

        bottomArea.add(
                actions,
                BorderLayout.EAST
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
                bottomArea,
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // LOAD PRESCRIPTIONS
    // =========================================================

    private void loadPrescriptions() {

        tableModel.setRowCount(0);

        try {

            Path path =
                    Path.of(
                            PRESCRIPTIONS_FILE
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

                /*
                 * prescriptions.txt format:
                 *
                 * 0 = Prescription ID
                 * 1 = Patient Username
                 * 2 = Doctor Username
                 * 3 = Date
                 * 4 = Medicine
                 * 5 = Dosage
                 * 6 = Instructions
                 */

                String[] data =
                        line.split(",", -1);

                if (data.length >= 7) {

                    String prescriptionId =
                            data[0].trim();

                    String patient =
                            data[1].trim();

                    String doctorUsername =
                            data[2].trim();

                    String date =
                            data[3].trim();

                    String medicine =
                            data[4].trim();

                    String dosage =
                            data[5].trim();

                    String instructions =
                            data[6].trim();

                    if (
                            patient.equalsIgnoreCase(
                                    patientUsername
                            )
                    ) {

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
                                        prescriptionId,
                                        doctorName,
                                        specialism,
                                        date,
                                        medicine,
                                        dosage,
                                        instructions
                                }
                        );
                    }
                }
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load prescriptions.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // GET DOCTOR NAME + SPECIALISM
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
    // VIEW DETAILS
    // =========================================================

    private void viewDetails() {

        int selectedRow =
                prescriptionTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a prescription first.",
                    "No Prescription Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String prescriptionId =
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString();

        String doctor =
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString();

        String specialism =
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString();

        String date =
                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString();

        String medicine =
                tableModel
                        .getValueAt(
                                selectedRow,
                                4
                        )
                        .toString();

        String dosage =
                tableModel
                        .getValueAt(
                                selectedRow,
                                5
                        )
                        .toString();

        String instructions =
                tableModel
                        .getValueAt(
                                selectedRow,
                                6
                        )
                        .toString();

        JOptionPane.showMessageDialog(
                this,
                "Prescription ID: "
                        + prescriptionId
                        + "\n\n"
                        + "Doctor: "
                        + doctor
                        + "\n"
                        + "Specialism: "
                        + specialism
                        + "\n"
                        + "Date: "
                        + date
                        + "\n\n"
                        + "Medicine: "
                        + medicine
                        + "\n"
                        + "Dosage: "
                        + dosage
                        + "\n\n"
                        + "Instructions:\n"
                        + instructions,
                "Prescription Details",
                JOptionPane.INFORMATION_MESSAGE
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
                        "●  SYSTEM READY     |     PRESCRIPTION RECORDS"
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
    // BACKGROUND
    // =========================================================

    private class PrescriptionBackground
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
    // PRESCRIPTION CARD
    // =========================================================

    private class PrescriptionCard
            extends JPanel {

        public PrescriptionCard() {

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

    // =========================================================
    // PRESCRIPTION ICON
    // =========================================================

    private class PrescriptionIcon
            extends JPanel {

        public PrescriptionIcon() {

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
                            18
                    )
            );

            String text =
                    "Rx";

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