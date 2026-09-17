import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class DoctorShiftRosterFrame extends JFrame {

    private final Color MANAGER_GOLD = new Color(235, 184, 72);
    private final Color GOLD_DARK = new Color(128, 88, 18);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(174, 185, 178);
    private final Color FIELD_BG = new Color(43, 41, 32);
    private final Color TABLE_BG = new Color(37, 36, 31);
    private final Color TABLE_ALT = new Color(43, 41, 33);

    // Kept because the original helper method uses these names.
    private final Color DARK_GREEN = GOLD_DARK;
    private final Color GREEN = MANAGER_GOLD;
    private final Color LIGHT_GREEN = new Color(43, 41, 32);

    private final String rosterFile = "data/doctor_roster.txt";
    private final String usersFile = "data/users.txt";

    private JTable rosterTable;
    private DefaultTableModel tableModel;

    private JTextField shiftIdField;
    private JComboBox<String> doctorComboBox;
    private JTextField dateField;
    private JComboBox<String> shiftComboBox;
    private JTextField roomField;

    private BufferedImage backgroundImage;

    public DoctorShiftRosterFrame() {
        setTitle("APU Medical Centre - Doctor Shift Roster");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            backgroundImage = ImageIO.read(new File("assets/hospital_portal_bg.png"));
        } catch (Exception e) {
            backgroundImage = null;
        }

        RosterBackground root = new RosterBackground();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(createHeaderPanel(), BorderLayout.NORTH);
        root.add(createRosterContent(), BorderLayout.CENTER);
        root.add(createBottomBar(), BorderLayout.SOUTH);

        loadDoctors();
        loadRoster();
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(16, 28, 14, 30));
        header.setPreferredSize(new Dimension(0, 84));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.X_AXIS));

        JLabel cross = new JLabel("+", SwingConstants.CENTER);
        cross.setOpaque(true);
        cross.setBackground(new Color(255, 247, 222));
        cross.setForeground(GOLD_DARK);
        cross.setFont(new Font("Arial", Font.BOLD, 28));
        cross.setPreferredSize(new Dimension(48, 48));
        cross.setMaximumSize(new Dimension(48, 48));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel hospital = new JLabel("APU MEDICAL CENTRE");
        hospital.setForeground(TEXT_WHITE);
        hospital.setFont(new Font("Arial", Font.BOLD, 19));

        JLabel portal = new JLabel("MEDICAL MANAGER PORTAL  /  WORKFORCE SCHEDULING");
        portal.setForeground(MANAGER_GOLD);
        portal.setFont(new Font("Arial", Font.BOLD, 9));

        text.add(hospital);
        text.add(Box.createRigidArea(new Dimension(0, 3)));
        text.add(portal);

        brand.add(cross);
        brand.add(Box.createRigidArea(new Dimension(13, 0)));
        brand.add(text);

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JLabel session = new JLabel("●  MANAGEMENT SESSION");
        session.setForeground(MANAGER_GOLD);
        session.setFont(new Font("Arial", Font.BOLD, 9));
        session.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel mode = new JLabel("Clinical Workforce Roster");
        mode.setForeground(TEXT_WHITE);
        mode.setFont(new Font("Arial", Font.BOLD, 13));
        mode.setAlignmentX(Component.RIGHT_ALIGNMENT);

        right.add(session);
        right.add(Box.createRigidArea(new Dimension(0, 4)));
        right.add(mode);

        header.add(brand, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private JPanel createRosterContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(20, 30, 20, 30));

        RosterCard card = new RosterCard();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(22, 26, 20, 26));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("DOCTOR WORKFORCE COORDINATION");
        small.setForeground(MANAGER_GOLD);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Doctor Shift Roster");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel("Create, update and coordinate doctor shifts, dates and clinical locations.");
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        headingText.add(small);
        headingText.add(Box.createRigidArea(new Dimension(0, 4)));
        headingText.add(title);
        headingText.add(Box.createRigidArea(new Dimension(0, 5)));
        headingText.add(subtitle);

        JLabel directory = new JLabel("●  ACTIVE ROSTER");
        directory.setForeground(MANAGER_GOLD);
        directory.setFont(new Font("Arial", Font.BOLD, 9));

        heading.add(headingText, BorderLayout.WEST);
        heading.add(directory, BorderLayout.EAST);

        String[] columns = {
                "Shift ID", "Doctor", "Date", "Shift", "Room / Location"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        rosterTable = new JTable(tableModel);
        styleRosterTable();

        JScrollPane scroll = new JScrollPane(rosterTable);
        scroll.setBorder(BorderFactory.createLineBorder(
                new Color(MANAGER_GOLD.getRed(), MANAGER_GOLD.getGreen(), MANAGER_GOLD.getBlue(), 90)));
        scroll.getViewport().setBackground(TABLE_BG);

        JPanel tableHolder = new JPanel(new BorderLayout());
        tableHolder.setOpaque(false);
        tableHolder.setBorder(new EmptyBorder(17, 0, 13, 0));
        tableHolder.add(scroll, BorderLayout.CENTER);

        shiftIdField = createInputField();
        doctorComboBox = new JComboBox<>();
        styleCombo(doctorComboBox);
        dateField = createInputField();

        shiftComboBox = new JComboBox<>(new String[]{
                "Morning (08:00 - 14:00)",
                "Evening (14:00 - 20:00)",
                "Night (20:00 - 08:00)"
        });
        styleCombo(shiftComboBox);

        roomField = createInputField();

        JPanel form = new JPanel(new GridLayout(1, 5, 10, 0));
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(12, 0, 12, 0));
        form.add(fieldGroup("Shift ID", shiftIdField));
        form.add(fieldGroup("Doctor", doctorComboBox));
        form.add(fieldGroup("Date (DD-MM-YYYY)", dateField));
        form.add(fieldGroup("Shift", shiftComboBox));
        form.add(fieldGroup("Room / Location", roomField));

        JButton addButton = new JButton("ADD SHIFT");
        JButton updateButton = new JButton("UPDATE SHIFT");
        JButton deleteButton = new JButton("DELETE SHIFT");
        JButton refreshButton = new JButton("REFRESH");
        JButton clearButton = new JButton("CLEAR");
        JButton closeButton = new JButton("CLOSE");

        stylePrimary(addButton, 105);
        stylePrimary(updateButton, 120);
        styleDanger(deleteButton, 115);
        styleSecondary(refreshButton, 100);
        styleSecondary(clearButton, 85);
        styleSecondary(closeButton, 85);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);
        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);
        buttons.add(refreshButton);
        buttons.add(clearButton);
        buttons.add(closeButton);

        JPanel lower = new JPanel(new BorderLayout());
        lower.setOpaque(false);
        lower.add(form, BorderLayout.CENTER);
        lower.add(buttons, BorderLayout.SOUTH);

        rosterTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedShift();
            }
        });

        addButton.addActionListener(e -> addShift());
        updateButton.addActionListener(e -> updateShift());
        deleteButton.addActionListener(e -> deleteShift());
        refreshButton.addActionListener(e -> {
            loadDoctors();
            loadRoster();
        });
        clearButton.addActionListener(e -> clearFields());
        closeButton.addActionListener(e -> dispose());

        card.add(heading, BorderLayout.NORTH);
        card.add(tableHolder, BorderLayout.CENTER);
        card.add(lower, BorderLayout.SOUTH);
        wrapper.add(card, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel fieldGroup(String labelText, JComponent component) {
        JPanel group = new JPanel();
        group.setOpaque(false);
        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setForeground(TEXT_WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 10));

        JLabel required = new JLabel("REQUIRED");
        required.setForeground(MANAGER_GOLD);
        required.setFont(new Font("Arial", Font.BOLD, 8));

        group.add(label);
        group.add(Box.createRigidArea(new Dimension(0, 2)));
        group.add(required);
        group.add(Box.createRigidArea(new Dimension(0, 5)));
        group.add(component);
        return group;
    }

    private JTextField createInputField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(180, 35));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 11));
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(MANAGER_GOLD);
        field.setBackground(FIELD_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(MANAGER_GOLD.getRed(), MANAGER_GOLD.getGreen(), MANAGER_GOLD.getBlue(), 95)),
                new EmptyBorder(0, 9, 0, 9)));
        return field;
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setPreferredSize(new Dimension(180, 35));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        combo.setFont(new Font("Arial", Font.PLAIN, 10));
        combo.setForeground(TEXT_WHITE);
        combo.setBackground(FIELD_BG);
        combo.setFocusable(false);
    }

    private void styleRosterTable() {
        rosterTable.setRowHeight(32);
        rosterTable.setFont(new Font("Arial", Font.PLAIN, 11));
        rosterTable.setForeground(TEXT_WHITE);
        rosterTable.setBackground(TABLE_BG);
        rosterTable.setGridColor(new Color(83, 76, 55));
        rosterTable.setSelectionBackground(new Color(105, 82, 30));
        rosterTable.setSelectionForeground(Color.WHITE);
        rosterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rosterTable.setShowVerticalLines(false);

        rosterTable.getTableHeader().setReorderingAllowed(false);
        rosterTable.getTableHeader().setPreferredSize(new Dimension(0, 36));
        rosterTable.getTableHeader().setBackground(new Color(75, 61, 31));
        rosterTable.getTableHeader().setForeground(new Color(255, 236, 186));
        rosterTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? TABLE_BG : TABLE_ALT);
                    c.setForeground(TEXT_WHITE);
                }
                if (c instanceof JLabel) {
                    ((JLabel) c).setBorder(new EmptyBorder(0, 10, 0, 10));
                }
                return c;
            }
        };

        for (int i = 0; i < rosterTable.getColumnCount(); i++) {
            rosterTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private void stylePrimary(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 36));
        button.setBackground(MANAGER_GOLD);
        button.setForeground(new Color(39, 29, 10));
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondary(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 36));
        button.setBackground(new Color(43, 41, 32));
        button.setForeground(TEXT_WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(
                new Color(MANAGER_GOLD.getRed(), MANAGER_GOLD.getGreen(), MANAGER_GOLD.getBlue(), 105)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleDanger(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 36));
        button.setBackground(new Color(112, 40, 42));
        button.setForeground(new Color(255, 225, 225));
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(165, 70, 74)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JPanel createBottomBar() {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(24, 24, 22, 242));
        bottom.setBorder(new EmptyBorder(9, 28, 9, 28));

        JLabel status = new JLabel("●  SYSTEM READY     |     CLINICAL WORKFORCE SCHEDULING");
        status.setForeground(MANAGER_GOLD);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("MANAGER AUTHORIZED ROSTER");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);
        return bottom;
    }

    // ==========================================
    // LOAD DOCTORS
    // ==========================================

    private void loadDoctors() {

        doctorComboBox.removeAllItems();

        try {

            Path path =
                    Path.of(usersFile);

            if (!Files.exists(path)) {
                return;
            }

            List<String> lines =
                    Files.readAllLines(path);

            for (String line : lines) {

                String[] data =
                        line.split(",", -1);

                if (data.length >= 8) {

                    String role =
                            data[5].trim();

                    if (
                        role.equalsIgnoreCase(
                                "Doctor"
                        )
                    ) {

                        String username =
                                data[2].trim();

                        String name =
                                data[1].trim();

                        doctorComboBox.addItem(
                                username
                                        + " - "
                                        + name
                        );
                    }
                }
            }

        } catch (IOException e) {

            showFileError(
                    "Unable to load doctors.",
                    e
            );
        }
    }

    // ==========================================
    // LOAD ROSTER
    // ==========================================

    private void loadRoster() {

        tableModel.setRowCount(0);

        try {

            Path path =
                    Path.of(rosterFile);

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            List<String> lines =
                    Files.readAllLines(path);

            for (String line : lines) {

                String[] data =
                        line.split(",", -1);

                if (data.length >= 5) {

                    tableModel.addRow(
                            new Object[]{
                                    data[0].trim(),
                                    data[1].trim(),
                                    data[2].trim(),
                                    data[3].trim(),
                                    data[4].trim()
                            }
                    );
                }
            }

        } catch (IOException e) {

            showFileError(
                    "Unable to load doctor roster.",
                    e
            );
        }
    }

    // ==========================================
    // LOAD SELECTED SHIFT
    // ==========================================

    private void loadSelectedShift() {

        int row =
                rosterTable.getSelectedRow();

        if (row == -1) {
            return;
        }

        shiftIdField.setText(
                tableModel
                        .getValueAt(row, 0)
                        .toString()
        );

        String doctor =
                tableModel
                        .getValueAt(row, 1)
                        .toString();

        for (
            int i = 0;
            i < doctorComboBox.getItemCount();
            i++
        ) {

            if (
                doctorComboBox
                        .getItemAt(i)
                        .toString()
                        .startsWith(
                                doctor + " - "
                        )
            ) {

                doctorComboBox.setSelectedIndex(i);
                break;
            }
        }

        dateField.setText(
                tableModel
                        .getValueAt(row, 2)
                        .toString()
        );

        shiftComboBox.setSelectedItem(
                tableModel
                        .getValueAt(row, 3)
                        .toString()
        );

        roomField.setText(
                tableModel
                        .getValueAt(row, 4)
                        .toString()
        );
    }

    // ==========================================
    // ADD SHIFT
    // ==========================================

    private void addShift() {

        String[] shiftData =
                getFormData();

        if (shiftData == null) {
            return;
        }

        try {

            Path path =
                    Path.of(rosterFile);

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            List<String> lines =
                    new ArrayList<>(
                            Files.readAllLines(path)
                    );

            for (String line : lines) {

                String[] data =
                        line.split(",", -1);

                if (
                    data.length >= 5
                    &&
                    data[0]
                            .trim()
                            .equalsIgnoreCase(
                                    shiftData[0]
                            )
                ) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Shift ID already exists.",
                            "Duplicate Shift",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                if (
                    data.length >= 5
                    &&
                    data[1]
                            .trim()
                            .equalsIgnoreCase(
                                    shiftData[1]
                            )
                    &&
                    data[2]
                            .trim()
                            .equalsIgnoreCase(
                                    shiftData[2]
                            )
                    &&
                    data[3]
                            .trim()
                            .equalsIgnoreCase(
                                    shiftData[3]
                            )
                ) {

                    JOptionPane.showMessageDialog(
                            this,
                            "This doctor already has the same shift on this date.",
                            "Duplicate Roster",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }
            }

            lines.add(
                    String.join(
                            ",",
                            shiftData
                    )
            );

            Files.write(
                    path,
                    lines
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Doctor shift added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadRoster();

        } catch (IOException e) {

            showFileError(
                    "Unable to add doctor shift.",
                    e
            );
        }
    }

    // ==========================================
    // UPDATE SHIFT
    // ==========================================

    private void updateShift() {

        int row =
                rosterTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a shift first.",
                    "No Shift Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String originalId =
                tableModel
                        .getValueAt(row, 0)
                        .toString();

        String[] updatedShift =
                getFormData();

        if (updatedShift == null) {
            return;
        }

        try {

            Path path =
                    Path.of(rosterFile);

            List<String> lines =
                    new ArrayList<>(
                            Files.readAllLines(path)
                    );

            for (
                int i = 0;
                i < lines.size();
                i++
            ) {

                String[] data =
                        lines.get(i)
                                .split(",", -1);

                if (
                    data.length >= 5
                    &&
                    data[0]
                            .trim()
                            .equalsIgnoreCase(
                                    originalId
                            )
                ) {

                    lines.set(
                            i,
                            String.join(
                                    ",",
                                    updatedShift
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
                    "Doctor shift updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadRoster();

        } catch (IOException e) {

            showFileError(
                    "Unable to update doctor shift.",
                    e
            );
        }
    }

    // ==========================================
    // DELETE SHIFT
    // ==========================================

    private void deleteShift() {

        int row =
                rosterTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a shift first.",
                    "No Shift Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String shiftId =
                tableModel
                        .getValueAt(row, 0)
                        .toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete shift "
                                + shiftId
                                + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (
            choice
            !=
            JOptionPane.YES_OPTION
        ) {
            return;
        }

        try {

            Path path =
                    Path.of(rosterFile);

            List<String> lines =
                    new ArrayList<>(
                            Files.readAllLines(path)
                    );

            lines.removeIf(
                    line -> {

                        String[] data =
                                line.split(",", -1);

                        return (
                            data.length >= 5
                            &&
                            data[0]
                                    .trim()
                                    .equalsIgnoreCase(
                                            shiftId
                                    )
                        );
                    }
            );

            Files.write(
                    path,
                    lines
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Doctor shift deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadRoster();

        } catch (IOException e) {

            showFileError(
                    "Unable to delete doctor shift.",
                    e
            );
        }
    }

    // ==========================================
    // GET FORM DATA
    // ==========================================

    private String[] getFormData() {

        String shiftId =
                shiftIdField
                        .getText()
                        .trim();

        if (
            doctorComboBox
                    .getSelectedItem()
            == null
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No doctor is available.",
                    "Missing Doctor",
                    JOptionPane.WARNING_MESSAGE
            );

            return null;
        }

        String doctorSelection =
                doctorComboBox
                        .getSelectedItem()
                        .toString();

        String doctorUsername =
                doctorSelection
                        .split(" - ")[0]
                        .trim();

        String date =
                dateField
                        .getText()
                        .trim();

        String shift =
                shiftComboBox
                        .getSelectedItem()
                        .toString();

        String room =
                roomField
                        .getText()
                        .trim();

        if (
            shiftId.isEmpty()
            ||
            date.isEmpty()
            ||
            room.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all required fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return null;
        }

        return new String[]{
                shiftId,
                doctorUsername,
                date,
                shift,
                room.replace(",", ";")
        };
    }

    // ==========================================
    // HELPERS
    // ==========================================

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        return label;
    }

    private void styleGreenButton(
            JButton button
    ) {

        button.setBackground(GREEN);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );
    }

    private void clearFields() {

        rosterTable.clearSelection();

        shiftIdField.setText("");
        dateField.setText("");
        roomField.setText("");

        if (
            doctorComboBox.getItemCount() > 0
        ) {
            doctorComboBox.setSelectedIndex(0);
        }

        shiftComboBox.setSelectedIndex(0);
    }

    private void showFileError(
            String message,
            IOException e
    ) {

        JOptionPane.showMessageDialog(
                this,
                message
                        + "\n"
                        + e.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private class RosterBackground extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(20, 21, 20));
            g2.fillRect(0, 0, getWidth(), getHeight());

            if (backgroundImage != null) {
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }

            GradientPaint overlay = new GradientPaint(
                    0, 0, new Color(22, 23, 21, 248),
                    getWidth(), 0, new Color(19, 22, 22, 222));
            g2.setPaint(overlay);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(19, 20, 19, 195));
            g2.fillRect(0, 0, getWidth(), 84);

            GradientPaint accent = new GradientPaint(
                    0, 0, MANAGER_GOLD,
                    getWidth(), 0,
                    new Color(MANAGER_GOLD.getRed(), MANAGER_GOLD.getGreen(), MANAGER_GOLD.getBlue(), 0));
            g2.setPaint(accent);
            g2.fillRect(0, 82, getWidth(), 2);
            g2.dispose();
        }
    }

    private class RosterCard extends JPanel {
        RosterCard() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRoundRect(7, 8, getWidth() - 11, getHeight() - 11, 22, 22);

            GradientPaint gradient = new GradientPaint(
                    0, 0,
                    new Color(MANAGER_GOLD.getRed(), MANAGER_GOLD.getGreen(), MANAGER_GOLD.getBlue(), 25),
                    0, getHeight(),
                    new Color(38, 37, 31, 246));
            g2.setPaint(gradient);
            g2.fillRoundRect(1, 1, getWidth() - 8, getHeight() - 9, 20, 20);

            g2.setColor(new Color(
                    MANAGER_GOLD.getRed(), MANAGER_GOLD.getGreen(), MANAGER_GOLD.getBlue(), 110));
            g2.drawRoundRect(1, 1, getWidth() - 8, getHeight() - 9, 20, 20);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
