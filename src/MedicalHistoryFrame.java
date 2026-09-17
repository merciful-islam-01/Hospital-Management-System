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

public class MedicalHistoryFrame extends JFrame {

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

    private final Color TABLE_ALT_BACKGROUND =
            new Color(6, 42, 47);

    private final Color TABLE_SELECTION =
            new Color(17, 83, 105);

    // =========================================================
    // USER
    // =========================================================

    private final String patientUsername;

    // =========================================================
    // TABLE
    // =========================================================

    private JTable historyTable;
    private DefaultTableModel tableModel;

    // =========================================================
    // DATA FILE
    // =========================================================

    private final String filePath =
            "data/medical_records.txt";

    // =========================================================
    // BACKGROUND
    // =========================================================

    private BufferedImage backgroundImage;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public MedicalHistoryFrame(String patientUsername) {

        this.patientUsername =
                patientUsername;

        // =====================================================
        // WINDOW SETTINGS
        // =====================================================

        setTitle(
                "APU Medical Centre - Medical History"
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

        HistoryBackground root =
                new HistoryBackground();

        root.setLayout(
                new BorderLayout()
        );

        setContentPane(root);

        // =====================================================
        // HEADER
        // =====================================================

        root.add(
                createHeader(),
                BorderLayout.NORTH
        );

        // =====================================================
        // CENTER
        // =====================================================

        JPanel centerWrapper =
                new JPanel(
                        new BorderLayout()
                );

        centerWrapper.setOpaque(false);

        centerWrapper.setBorder(
                new EmptyBorder(
                        25,
                        35,
                        20,
                        35
                )
        );

        HistoryCard historyCard =
                createHistoryCard();

        centerWrapper.add(
                historyCard,
                BorderLayout.CENTER
        );

        root.add(
                centerWrapper,
                BorderLayout.CENTER
        );

        // =====================================================
        // BOTTOM BAR
        // =====================================================

        root.add(
                createBottomBar(),
                BorderLayout.SOUTH
        );

        // =====================================================
        // LOAD DATA
        // =====================================================

        loadMedicalHistory();
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

        brandText.add(
                hospital
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
                portal
        );

        brand.add(
                cross
        );

        brand.add(
                Box.createRigidArea(
                        new Dimension(
                                13,
                                0
                        )
                )
        );

        brand.add(
                brandText
        );

        // =====================================================
        // RIGHT USER
        // =====================================================

        JPanel user =
                new JPanel();

        user.setOpaque(false);

        user.setLayout(
                new BoxLayout(
                        user,
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

        JLabel patient =
                new JLabel(
                        patientUsername
                );

        patient.setForeground(
                TEXT_WHITE
        );

        patient.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        patient.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        user.add(
                secure
        );

        user.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );

        user.add(
                patient
        );

        header.add(
                brand,
                BorderLayout.WEST
        );

        header.add(
                user,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================================================
    // HISTORY CARD
    // =========================================================

    private HistoryCard createHistoryCard() {

        HistoryCard card =
                new HistoryCard();

        card.setLayout(
                new BorderLayout(
                        0,
                        18
                )
        );

        card.setBorder(
                new EmptyBorder(
                        24,
                        28,
                        22,
                        28
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

        JPanel titleText =
                new JPanel();

        titleText.setOpaque(false);

        titleText.setLayout(
                new BoxLayout(
                        titleText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel category =
                new JLabel(
                        "CLINICAL RECORDS"
                );

        category.setForeground(
                PATIENT_BLUE
        );

        category.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        JLabel title =
                new JLabel(
                        "Medical History"
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

        JLabel description =
                new JLabel(
                        "Review your recorded vital signs, consultation notes and clinical results."
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

        titleText.add(
                category
        );

        titleText.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );

        titleText.add(
                title
        );

        titleText.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );

        titleText.add(
                description
        );

        RecordIcon icon =
                new RecordIcon();

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
        // TABLE
        // =====================================================

        String[] columns = {
                "Record ID",
                "Doctor",
                "Date",
                "Blood Pressure",
                "Heart Rate",
                "Consultation Notes",
                "Result"
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

        historyTable =
                new JTable(
                        tableModel
                );

        styleTable();

        JScrollPane scrollPane =
                new JScrollPane(
                        historyTable
                );

        scrollPane.setBorder(
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

        scrollPane.getViewport()
                .setBackground(
                        TABLE_BACKGROUND
                );

        scrollPane.setBackground(
                TABLE_BACKGROUND
        );

        // =====================================================
        // BUTTONS
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

        JButton refreshButton =
                createSecondaryButton(
                        "REFRESH"
                );

        JButton detailsButton =
                createPrimaryButton(
                        "VIEW DETAILS"
                );

        JButton closeButton =
                createSecondaryButton(
                        "CLOSE"
                );

        refreshButton.addActionListener(
                e -> loadMedicalHistory()
        );

        detailsButton.addActionListener(
                e -> viewDetails()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                detailsButton
        );

        buttonPanel.add(
                closeButton
        );

        // =====================================================
        // ADD CARD COMPONENTS
        // =====================================================

        card.add(
                titleArea,
                BorderLayout.NORTH
        );

        card.add(
                scrollPane,
                BorderLayout.CENTER
        );

        card.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        return card;
    }

    // =========================================================
    // TABLE STYLE
    // =========================================================

    private void styleTable() {

        historyTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        historyTable.setForeground(
                new Color(
                        215,
                        232,
                        228
                )
        );

        historyTable.setBackground(
                TABLE_BACKGROUND
        );

        historyTable.setSelectionBackground(
                TABLE_SELECTION
        );

        historyTable.setSelectionForeground(
                Color.WHITE
        );

        historyTable.setRowHeight(
                38
        );

        historyTable.setShowVerticalLines(
                false
        );

        historyTable.setShowHorizontalLines(
                true
        );

        historyTable.setGridColor(
                new Color(
                        24,
                        70,
                        74
                )
        );

        historyTable.setIntercellSpacing(
                new Dimension(
                        0,
                        1
                )
        );

        historyTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        historyTable.setFillsViewportHeight(
                true
        );

        JTableHeader header =
                historyTable.getTableHeader();

        header.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        header.setForeground(
                PATIENT_BLUE
        );

        header.setBackground(
                new Color(
                        3,
                        29,
                        34
                )
        );

        header.setPreferredSize(
                new Dimension(
                        header.getPreferredSize().width,
                        38
                )
        );

        header.setReorderingAllowed(
                false
        );

        // =====================================================
        // ALTERNATING ROW RENDERER
        // =====================================================

        DefaultTableCellRenderer renderer =
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

                        Component component =
                                super.getTableCellRendererComponent(
                                        table,
                                        value,
                                        isSelected,
                                        hasFocus,
                                        row,
                                        column
                                );

                        if (isSelected) {

                            component.setBackground(
                                    TABLE_SELECTION
                            );

                            component.setForeground(
                                    Color.WHITE
                            );

                        } else {

                            component.setBackground(
                                    row % 2 == 0
                                            ? TABLE_BACKGROUND
                                            : TABLE_ALT_BACKGROUND
                            );

                            component.setForeground(
                                    new Color(
                                            215,
                                            232,
                                            228
                                    )
                            );
                        }

                        setBorder(
                                new EmptyBorder(
                                        0,
                                        9,
                                        0,
                                        9
                                )
                        );

                        return component;
                    }
                };

        for (
            int i = 0;
            i < historyTable.getColumnCount();
            i++
        ) {

            historyTable
                    .getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(
                            renderer
                    );
        }

        // =====================================================
        // COLUMN WIDTHS
        // =====================================================

        historyTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(100);

        historyTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(110);

        historyTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(100);

        historyTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(125);

        historyTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(95);

        historyTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(280);

        historyTable
                .getColumnModel()
                .getColumn(6)
                .setPreferredWidth(170);
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
                        115,
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
                        "●  SYSTEM READY     |     MEDICAL RECORD ACCESS"
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
    // LOAD MEDICAL HISTORY
    // =========================================================

    private void loadMedicalHistory() {

        tableModel.setRowCount(0);

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
                        line.split(",");

                if (data.length >= 8) {

                    String recordId =
                            data[0].trim();

                    String patient =
                            data[1].trim();

                    String doctor =
                            data[2].trim();

                    String date =
                            data[3].trim();

                    String bloodPressure =
                            data[4].trim();

                    String heartRate =
                            data[5].trim();

                    String notes =
                            data[6].trim();

                    String result =
                            data[7].trim();

                    if (
                        patient.equalsIgnoreCase(
                                patientUsername
                        )
                    ) {

                        tableModel.addRow(
                                new Object[] {
                                        recordId,
                                        doctor,
                                        date,
                                        bloodPressure,
                                        heartRate,
                                        notes,
                                        result
                                }
                        );
                    }
                }
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load medical history.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // VIEW DETAILS
    // =========================================================

    private void viewDetails() {

        int selectedRow =
                historyTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a medical record first.",
                    "No Record Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String recordId =
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

        String date =
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString();

        String bloodPressure =
                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString();

        String heartRate =
                tableModel
                        .getValueAt(
                                selectedRow,
                                4
                        )
                        .toString();

        String notes =
                tableModel
                        .getValueAt(
                                selectedRow,
                                5
                        )
                        .toString();

        String result =
                tableModel
                        .getValueAt(
                                selectedRow,
                                6
                        )
                        .toString();

        JOptionPane.showMessageDialog(
                this,
                "Record ID: "
                        + recordId
                        + "\nDoctor: "
                        + doctor
                        + "\nDate: "
                        + date
                        + "\nBlood Pressure: "
                        + bloodPressure
                        + "\nHeart Rate: "
                        + heartRate
                        + "\n\nConsultation Notes:\n"
                        + notes
                        + "\n\nResult:\n"
                        + result,
                "Medical Record Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // BACKGROUND PANEL
    // =========================================================

    private class HistoryBackground
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
            // HEADER GLASS
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
            // BLUE GLOW
            // =================================================

            g2.setColor(
                    new Color(
                            PATIENT_BLUE.getRed(),
                            PATIENT_BLUE.getGreen(),
                            PATIENT_BLUE.getBlue(),
                            11
                    )
            );

            g2.fillOval(
                    700,
                    70,
                    430,
                    430
            );

            g2.dispose();
        }
    }

    // =========================================================
    // HISTORY CARD
    // =========================================================

    private class HistoryCard
            extends JPanel {

        public HistoryCard() {

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
            // CARD
            // =================================================

            GradientPaint gradient =
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
                    gradient
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
            // HEADER GLOW
            // =================================================

            GradientPaint glow =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    PATIENT_BLUE.getRed(),
                                    PATIENT_BLUE.getGreen(),
                                    PATIENT_BLUE.getBlue(),
                                    55
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
                    82,
                    22,
                    22
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // RECORD ICON
    // =========================================================

    private class RecordIcon
            extends JPanel {

        public RecordIcon() {

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

            FontMetrics fm =
                    g2.getFontMetrics();

            String symbol =
                    "H";

            int x =
                    5
                    +
                    (
                        54
                        -
                        fm.stringWidth(
                                symbol
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
                    symbol,
                    x,
                    y
            );

            g2.dispose();
        }
    }
}