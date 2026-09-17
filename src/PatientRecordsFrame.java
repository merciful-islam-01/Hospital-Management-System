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

public class PatientRecordsFrame extends JFrame {

    private final Color DOCTOR_GREEN = new Color(32, 224, 157);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(155, 185, 180);
    private final Color TABLE_BG = new Color(5, 35, 39);
    private final Color TABLE_ALT = new Color(7, 43, 46);

    private JTable recordsTable;
    private DefaultTableModel tableModel;

    private final String filePath = "data/medical_records.txt";
    private BufferedImage backgroundImage;

    public PatientRecordsFrame() {

        setTitle("APU Medical Centre - Patient Records");
        setSize(1180, 650);
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

        RecordsBackground root = new RecordsBackground();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createBottomBar(), BorderLayout.SOUTH);

        loadRecords();
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
        cross.setBackground(new Color(225, 255, 245));
        cross.setForeground(new Color(10, 105, 72));
        cross.setFont(new Font("Arial", Font.BOLD, 28));
        cross.setPreferredSize(new Dimension(48, 48));
        cross.setMaximumSize(new Dimension(48, 48));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel hospital = new JLabel("APU MEDICAL CENTRE");
        hospital.setForeground(TEXT_WHITE);
        hospital.setFont(new Font("Arial", Font.BOLD, 19));

        JLabel portal = new JLabel("DOCTOR PORTAL  /  CLINICAL RECORDS");
        portal.setForeground(DOCTOR_GREEN);
        portal.setFont(new Font("Arial", Font.BOLD, 9));

        text.add(hospital);
        text.add(Box.createRigidArea(new Dimension(0, 3)));
        text.add(portal);

        brand.add(cross);
        brand.add(Box.createRigidArea(new Dimension(13, 0)));
        brand.add(text);

        JLabel secure = new JLabel("●  AUTHORIZED CLINICAL ACCESS");
        secure.setForeground(DOCTOR_GREEN);
        secure.setFont(new Font("Arial", Font.BOLD, 9));

        header.add(brand, BorderLayout.WEST);
        header.add(secure, BorderLayout.EAST);

        return header;
    }

    private JPanel createMainContent() {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(22, 30, 22, 30));

        RecordsCard card = new RecordsCard();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 25, 20, 25));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        heading.setBorder(new EmptyBorder(0, 0, 18, 0));

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("PATIENT CLINICAL DATABASE");
        small.setForeground(DOCTOR_GREEN);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Patient Records");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Review recorded vital signs, consultation notes and clinical results."
        );
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        headingText.add(small);
        headingText.add(Box.createRigidArea(new Dimension(0, 4)));
        headingText.add(title);
        headingText.add(Box.createRigidArea(new Dimension(0, 5)));
        headingText.add(subtitle);

        JLabel access = new JLabel("READ-ONLY RECORD VIEW");
        access.setForeground(new Color(130, 178, 169));
        access.setFont(new Font("Arial", Font.BOLD, 9));

        heading.add(headingText, BorderLayout.WEST);
        heading.add(access, BorderLayout.EAST);

        createTable();

        JScrollPane scrollPane = new JScrollPane(recordsTable);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                DOCTOR_GREEN.getRed(),
                                DOCTOR_GREEN.getGreen(),
                                DOCTOR_GREEN.getBlue(),
                                90
                        )
                )
        );
        scrollPane.getViewport().setBackground(TABLE_BG);
        scrollPane.setBackground(TABLE_BG);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);
        buttons.setBorder(new EmptyBorder(18, 0, 0, 0));

        JButton refreshButton = new JButton("REFRESH");
        JButton detailsButton = new JButton("VIEW DETAILS");
        JButton closeButton = new JButton("CLOSE");

        stylePrimaryButton(refreshButton, 110);
        stylePrimaryButton(detailsButton, 145);
        styleSecondaryButton(closeButton, 95);

        refreshButton.addActionListener(e -> loadRecords());
        detailsButton.addActionListener(e -> viewDetails());
        closeButton.addActionListener(e -> dispose());

        buttons.add(refreshButton);
        buttons.add(detailsButton);
        buttons.add(closeButton);

        card.add(heading, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);
        card.add(buttons, BorderLayout.SOUTH);

        wrapper.add(card, BorderLayout.CENTER);

        return wrapper;
    }

    private void createTable() {

        String[] columns = {
                "Record ID",
                "Patient",
                "Doctor",
                "Date",
                "Blood Pressure",
                "Heart Rate",
                "Consultation Notes",
                "Result"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        recordsTable = new JTable(tableModel);
        recordsTable.setRowHeight(34);
        recordsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        recordsTable.setForeground(TEXT_WHITE);
        recordsTable.setBackground(TABLE_BG);
        recordsTable.setSelectionBackground(
                new Color(
                        DOCTOR_GREEN.getRed(),
                        DOCTOR_GREEN.getGreen(),
                        DOCTOR_GREEN.getBlue(),
                        80
                )
        );
        recordsTable.setSelectionForeground(Color.WHITE);
        recordsTable.setGridColor(new Color(25, 70, 70));
        recordsTable.setShowVerticalLines(false);
        recordsTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        recordsTable.setFillsViewportHeight(true);

        JTableHeader header = recordsTable.getTableHeader();
        header.setPreferredSize(new Dimension(0, 36));
        header.setReorderingAllowed(false);
        header.setFont(new Font("Arial", Font.BOLD, 10));
        header.setForeground(new Color(3, 30, 29));
        header.setBackground(DOCTOR_GREEN);

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

                        if (!isSelected) {
                            component.setBackground(
                                    row % 2 == 0
                                            ? TABLE_BG
                                            : TABLE_ALT
                            );
                            component.setForeground(TEXT_WHITE);
                        }

                        setBorder(new EmptyBorder(0, 8, 0, 8));

                        if (column == 0) {
                            setForeground(
                                    isSelected
                                            ? Color.WHITE
                                            : DOCTOR_GREEN
                            );
                        }

                        return component;
                    }
                };

        for (int i = 0; i < recordsTable.getColumnCount(); i++) {
            recordsTable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(renderer);
        }

        recordsTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        recordsTable.getColumnModel().getColumn(1).setPreferredWidth(90);
        recordsTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        recordsTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        recordsTable.getColumnModel().getColumn(4).setPreferredWidth(105);
        recordsTable.getColumnModel().getColumn(5).setPreferredWidth(85);
        recordsTable.getColumnModel().getColumn(6).setPreferredWidth(230);
        recordsTable.getColumnModel().getColumn(7).setPreferredWidth(160);
    }

    private void stylePrimaryButton(JButton button, int width) {

        button.setPreferredSize(new Dimension(width, 38));
        button.setBackground(DOCTOR_GREEN);
        button.setForeground(new Color(3, 30, 29));
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton button, int width) {

        button.setPreferredSize(new Dimension(width, 38));
        button.setBackground(new Color(6, 39, 42));
        button.setForeground(TEXT_WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                DOCTOR_GREEN.getRed(),
                                DOCTOR_GREEN.getGreen(),
                                DOCTOR_GREEN.getBlue(),
                                100
                        )
                )
        );
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JPanel createBottomBar() {

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(2, 27, 30, 240));
        bottom.setBorder(new EmptyBorder(9, 28, 9, 28));

        JLabel status = new JLabel(
                "●  SYSTEM READY     |     PATIENT RECORDS"
        );
        status.setForeground(DOCTOR_GREEN);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel(
                "CONFIDENTIAL CLINICAL INFORMATION"
        );
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);

        return bottom;
    }

    private void loadRecords() {

        tableModel.setRowCount(0);

        try {

            Path path = Path.of(filePath);

            if (!Files.exists(path)) {
                return;
            }

            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {

                String[] data = line.split(",");

                if (data.length >= 8) {

                    String recordId = data[0].trim();
                    String patient = data[1].trim();
                    String doctor = data[2].trim();
                    String date = data[3].trim();
                    String bloodPressure = data[4].trim();
                    String heartRate = data[5].trim();
                    String notes = data[6].trim();
                    String result = data[7].trim();

                    tableModel.addRow(
                            new Object[]{
                                    recordId,
                                    patient,
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

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load patient records.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void viewDetails() {

        int selectedRow = recordsTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient record first.",
                    "No Record Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String recordId =
                tableModel.getValueAt(selectedRow, 0).toString();

        String patient =
                tableModel.getValueAt(selectedRow, 1).toString();

        String doctor =
                tableModel.getValueAt(selectedRow, 2).toString();

        String date =
                tableModel.getValueAt(selectedRow, 3).toString();

        String bloodPressure =
                tableModel.getValueAt(selectedRow, 4).toString();

        String heartRate =
                tableModel.getValueAt(selectedRow, 5).toString();

        String notes =
                tableModel.getValueAt(selectedRow, 6).toString();

        String result =
                tableModel.getValueAt(selectedRow, 7).toString();

        JOptionPane.showMessageDialog(
                this,
                "Record ID: " + recordId
                        + "\nPatient: " + patient
                        + "\nDoctor: " + doctor
                        + "\nDate: " + date
                        + "\nBlood Pressure: " + bloodPressure
                        + "\nHeart Rate: " + heartRate
                        + "\n\nConsultation Notes:\n"
                        + notes
                        + "\n\nResult:\n"
                        + result,
                "Patient Record Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private class RecordsBackground extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(2, 22, 24));
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
                    new Color(0, 22, 25, 248),
                    getWidth(),
                    0,
                    new Color(0, 18, 25, 215)
            );

            g2.setPaint(overlay);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(0, 18, 22, 190));
            g2.fillRect(0, 0, getWidth(), 84);

            GradientPaint accent = new GradientPaint(
                    0,
                    0,
                    DOCTOR_GREEN,
                    getWidth(),
                    0,
                    new Color(
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            0
                    )
            );

            g2.setPaint(accent);
            g2.fillRect(0, 82, getWidth(), 2);

            g2.dispose();
        }
    }

    private class RecordsCard extends JPanel {

        RecordsCard() {
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
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            25
                    ),
                    0,
                    getHeight(),
                    new Color(4, 34, 38, 245)
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
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            105
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
