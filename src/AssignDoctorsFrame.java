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

public class AssignDoctorsFrame extends JFrame {

    private final Color ADMIN_PURPLE = new Color(180, 105, 255);
    private final Color PURPLE_DARK = new Color(82, 42, 122);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(170, 180, 180);
    private final Color FIELD_BG = new Color(43, 34, 50);
    private final Color TABLE_BG = new Color(34, 29, 39);
    private final Color TABLE_ALT = new Color(42, 34, 48);

    // Compatibility aliases used by the preserved original backend helpers.
    private final Color DARK_GREEN = PURPLE_DARK;
    private final Color GREEN = ADMIN_PURPLE;
    private final Color LIGHT_GREEN = FIELD_BG;

    private final String usersFile = "data/users.txt";
    private final String assignmentFile = "data/doctor_manager_assignments.txt";

    private JComboBox<String> doctorComboBox;
    private JComboBox<String> managerComboBox;

    private JTable assignmentTable;
    private DefaultTableModel tableModel;

    private BufferedImage backgroundImage;

    public AssignDoctorsFrame() {
        setTitle("APU Medical Centre - Assign Doctors");
        setSize(1050, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            backgroundImage = ImageIO.read(new File("assets/hospital_portal_bg.png"));
        } catch (Exception e) {
            backgroundImage = null;
        }

        AssignBackground root = new AssignBackground();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createBottomBar(), BorderLayout.SOUTH);

        loadData();
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
        cross.setBackground(new Color(247, 232, 255));
        cross.setForeground(PURPLE_DARK);
        cross.setFont(new Font("Arial", Font.BOLD, 28));
        cross.setPreferredSize(new Dimension(48, 48));
        cross.setMaximumSize(new Dimension(48, 48));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel hospital = new JLabel("APU MEDICAL CENTRE");
        hospital.setForeground(TEXT_WHITE);
        hospital.setFont(new Font("Arial", Font.BOLD, 19));

        JLabel portal = new JLabel("ADMIN STAFF PORTAL  /  CLINICAL ASSIGNMENTS");
        portal.setForeground(ADMIN_PURPLE);
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

        JLabel session = new JLabel("●  ADMIN SESSION");
        session.setForeground(ADMIN_PURPLE);
        session.setFont(new Font("Arial", Font.BOLD, 9));
        session.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel mode = new JLabel("Doctor Management Assignment");
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

    private JPanel createMainContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(20, 30, 18, 30));

        AssignCard card = new AssignCard();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(22, 26, 20, 26));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("CLINICAL OVERSIGHT CONFIGURATION");
        small.setForeground(ADMIN_PURPLE);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Assign Doctors");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Assign doctors to medical managers and maintain clinical management responsibility."
        );
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        headingText.add(small);
        headingText.add(Box.createRigidArea(new Dimension(0, 4)));
        headingText.add(title);
        headingText.add(Box.createRigidArea(new Dimension(0, 5)));
        headingText.add(subtitle);

        JLabel status = new JLabel("●  ASSIGNMENT DIRECTORY");
        status.setForeground(ADMIN_PURPLE);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        heading.add(headingText, BorderLayout.WEST);
        heading.add(status, BorderLayout.EAST);

        String[] columns = {"Doctor Username", "Medical Manager Username"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        assignmentTable = new JTable(tableModel);
        styleTable();

        JScrollPane scroll = new JScrollPane(assignmentTable);
        scroll.setBorder(BorderFactory.createLineBorder(
                new Color(ADMIN_PURPLE.getRed(), ADMIN_PURPLE.getGreen(), ADMIN_PURPLE.getBlue(), 95)
        ));
        scroll.getViewport().setBackground(TABLE_BG);

        JPanel tableHolder = new JPanel(new BorderLayout());
        tableHolder.setOpaque(false);
        tableHolder.setBorder(new EmptyBorder(18, 0, 14, 0));
        tableHolder.add(scroll, BorderLayout.CENTER);

        doctorComboBox = new JComboBox<>();
        managerComboBox = new JComboBox<>();
        styleCombo(doctorComboBox);
        styleCombo(managerComboBox);

        JPanel selectors = new JPanel(new GridLayout(1, 2, 14, 0));
        selectors.setOpaque(false);
        selectors.add(fieldGroup("Doctor", doctorComboBox));
        selectors.add(fieldGroup("Medical Manager", managerComboBox));

        JButton assignButton = new JButton("ASSIGN DOCTOR");
        JButton removeButton = new JButton("REMOVE ASSIGNMENT");
        JButton refreshButton = new JButton("REFRESH");
        JButton closeButton = new JButton("CLOSE");

        stylePrimary(assignButton, 135);
        styleDanger(removeButton, 165);
        styleSecondary(refreshButton, 100);
        styleSecondary(closeButton, 90);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);
        buttons.setBorder(new EmptyBorder(12, 0, 0, 0));
        buttons.add(assignButton);
        buttons.add(removeButton);
        buttons.add(refreshButton);
        buttons.add(closeButton);

        JPanel lower = new JPanel(new BorderLayout());
        lower.setOpaque(false);
        lower.add(selectors, BorderLayout.CENTER);
        lower.add(buttons, BorderLayout.SOUTH);

        assignButton.addActionListener(e -> assignDoctor());
        removeButton.addActionListener(e -> removeAssignment());
        refreshButton.addActionListener(e -> loadData());
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
        required.setForeground(ADMIN_PURPLE);
        required.setFont(new Font("Arial", Font.BOLD, 8));

        group.add(label);
        group.add(Box.createRigidArea(new Dimension(0, 2)));
        group.add(required);
        group.add(Box.createRigidArea(new Dimension(0, 5)));
        group.add(component);
        return group;
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setPreferredSize(new Dimension(300, 36));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        combo.setFont(new Font("Arial", Font.PLAIN, 11));
        combo.setForeground(TEXT_WHITE);
        combo.setBackground(FIELD_BG);
        combo.setFocusable(false);
    }

    private void styleTable() {
        assignmentTable.setRowHeight(32);
        assignmentTable.setFont(new Font("Arial", Font.PLAIN, 11));
        assignmentTable.setForeground(TEXT_WHITE);
        assignmentTable.setBackground(TABLE_BG);
        assignmentTable.setGridColor(new Color(79, 59, 91));
        assignmentTable.setSelectionBackground(new Color(91, 55, 116));
        assignmentTable.setSelectionForeground(Color.WHITE);
        assignmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        assignmentTable.setShowVerticalLines(false);

        assignmentTable.getTableHeader().setReorderingAllowed(false);
        assignmentTable.getTableHeader().setPreferredSize(new Dimension(0, 36));
        assignmentTable.getTableHeader().setBackground(new Color(69, 43, 82));
        assignmentTable.getTableHeader().setForeground(new Color(240, 218, 255));
        assignmentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column
            ) {
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );

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

        for (int i = 0; i < assignmentTable.getColumnCount(); i++) {
            assignmentTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private void stylePrimary(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 36));
        button.setBackground(ADMIN_PURPLE);
        button.setForeground(new Color(31, 18, 38));
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondary(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 36));
        button.setBackground(new Color(43, 34, 50));
        button.setForeground(TEXT_WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(
                new Color(ADMIN_PURPLE.getRed(), ADMIN_PURPLE.getGreen(), ADMIN_PURPLE.getBlue(), 105)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleDanger(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 36));
        button.setBackground(new Color(112, 40, 54));
        button.setForeground(new Color(255, 225, 230));
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(165, 70, 88)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JPanel createBottomBar() {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(22, 18, 27, 244));
        bottom.setBorder(new EmptyBorder(9, 28, 9, 28));

        JLabel status = new JLabel(
                "●  SYSTEM READY     |     DOCTOR-MANAGER ASSIGNMENT CONTROL"
        );
        status.setForeground(ADMIN_PURPLE);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("ADMIN AUTHORIZED CONFIGURATION");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);
        return bottom;
    }

    private void loadData() {
        loadUsers();
        loadAssignments();
    }

    private void loadUsers() {

        doctorComboBox.removeAllItems();

        managerComboBox.removeAllItems();

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

                    String name =
                            data[1].trim();

                    String username =
                            data[2].trim();

                    String role =
                            data[5].trim();

                    if (
                        role.equalsIgnoreCase(
                                "Doctor"
                        )
                    ) {

                        doctorComboBox.addItem(
                                username
                                        + " - "
                                        + name
                        );
                    }

                    else if (
                        role.equalsIgnoreCase(
                                "MedicalManager"
                        )
                    ) {

                        managerComboBox.addItem(
                                username
                                        + " - "
                                        + name
                        );
                    }
                }
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load users.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // ASSIGN DOCTOR
    // ==========================================

    private void assignDoctor() {

        if (
            doctorComboBox.getSelectedItem()
                    == null
            ||
            managerComboBox.getSelectedItem()
                    == null
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Doctor or Medical Manager is unavailable.",
                    "Missing User",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String doctorSelection =
                doctorComboBox
                        .getSelectedItem()
                        .toString();

        String managerSelection =
                managerComboBox
                        .getSelectedItem()
                        .toString();

        String doctorUsername =
                doctorSelection
                        .split(" - ")[0]
                        .trim();

        String managerUsername =
                managerSelection
                        .split(" - ")[0]
                        .trim();

        try {

            Path path =
                    Path.of(
                            assignmentFile
                    );

            if (!Files.exists(path)) {

                Files.createFile(path);
            }

            List<String> lines =
                    new ArrayList<>(
                            Files.readAllLines(
                                    path
                            )
                    );

            // Prevent duplicate or multiple manager assignments
            for (String line : lines) {

                String[] data =
                        line.split(",", -1);

                if (data.length >= 2) {

                    String savedDoctor =
                            data[0].trim();

                    String savedManager =
                            data[1].trim();

                    if (
                        savedDoctor.equalsIgnoreCase(
                                doctorUsername
                        )
                        &&
                        savedManager.equalsIgnoreCase(
                                managerUsername
                        )
                    ) {

                        JOptionPane.showMessageDialog(
                                this,
                                "This assignment already exists.",
                                "Duplicate Assignment",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }

                    if (
                        savedDoctor.equalsIgnoreCase(
                                doctorUsername
                        )
                    ) {

                        JOptionPane.showMessageDialog(
                                this,
                                "This doctor is already assigned to "
                                        + savedManager
                                        + ".\nRemove the existing assignment first.",
                                "Doctor Already Assigned",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }
                }
            }

            lines.add(
                    doctorUsername
                            + ","
                            + managerUsername
            );

            Files.write(
                    path,
                    lines
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Doctor assigned successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadAssignments();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save assignment.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // LOAD ASSIGNMENTS
    // ==========================================

    private void loadAssignments() {

        tableModel.setRowCount(0);

        try {

            Path path =
                    Path.of(
                            assignmentFile
                    );

            if (!Files.exists(path)) {

                return;
            }

            List<String> lines =
                    Files.readAllLines(path);

            for (String line : lines) {

                String[] data =
                        line.split(",", -1);

                if (data.length >= 2) {

                    tableModel.addRow(
                            new Object[]{
                                    data[0].trim(),
                                    data[1].trim()
                            }
                    );
                }
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load assignments.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // REMOVE ASSIGNMENT
    // ==========================================

    private void removeAssignment() {

        int selectedRow =
                assignmentTable
                        .getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an assignment first.",
                    "No Assignment Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String doctorUsername =
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString();

        String managerUsername =
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Remove assignment?\n"
                                + doctorUsername
                                + " → "
                                + managerUsername,
                        "Confirm Removal",
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
                    Path.of(
                            assignmentFile
                    );

            List<String> lines =
                    new ArrayList<>(
                            Files.readAllLines(
                                    path
                            )
                    );

            lines.removeIf(
                    line -> {

                        String[] data =
                                line.split(",", -1);

                        return (
                            data.length >= 2
                            &&
                            data[0]
                                    .trim()
                                    .equalsIgnoreCase(
                                            doctorUsername
                                    )
                            &&
                            data[1]
                                    .trim()
                                    .equalsIgnoreCase(
                                            managerUsername
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
                    "Assignment removed successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadAssignments();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to remove assignment.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private class AssignBackground extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(18, 17, 22));
            g2.fillRect(0, 0, getWidth(), getHeight());

            if (backgroundImage != null) {
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }

            GradientPaint overlay = new GradientPaint(
                    0, 0, new Color(24, 18, 29, 248),
                    getWidth(), 0, new Color(13, 22, 24, 224)
            );
            g2.setPaint(overlay);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(18, 16, 22, 200));
            g2.fillRect(0, 0, getWidth(), 84);

            GradientPaint accent = new GradientPaint(
                    0, 0, ADMIN_PURPLE,
                    getWidth(), 0,
                    new Color(
                            ADMIN_PURPLE.getRed(),
                            ADMIN_PURPLE.getGreen(),
                            ADMIN_PURPLE.getBlue(),
                            0
                    )
            );
            g2.setPaint(accent);
            g2.fillRect(0, 82, getWidth(), 2);

            g2.dispose();
        }
    }

    private class AssignCard extends JPanel {
        AssignCard() {
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
                    7, 8,
                    getWidth() - 11,
                    getHeight() - 11,
                    22, 22
            );

            GradientPaint gradient = new GradientPaint(
                    0, 0,
                    new Color(
                            ADMIN_PURPLE.getRed(),
                            ADMIN_PURPLE.getGreen(),
                            ADMIN_PURPLE.getBlue(),
                            25
                    ),
                    0, getHeight(),
                    new Color(37, 30, 43, 246)
            );

            g2.setPaint(gradient);
            g2.fillRoundRect(
                    1, 1,
                    getWidth() - 8,
                    getHeight() - 9,
                    20, 20
            );

            g2.setColor(new Color(
                    ADMIN_PURPLE.getRed(),
                    ADMIN_PURPLE.getGreen(),
                    ADMIN_PURPLE.getBlue(),
                    110
            ));
            g2.drawRoundRect(
                    1, 1,
                    getWidth() - 8,
                    getHeight() - 9,
                    20, 20
            );

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
