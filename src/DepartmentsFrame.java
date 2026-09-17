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

public class DepartmentsFrame extends JFrame {

    private final Color MANAGER_GOLD = new Color(235, 184, 72);
    private final Color GOLD_DARK = new Color(128, 88, 18);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(174, 185, 178);
    private final Color FIELD_BG = new Color(43, 41, 32);
    private final Color TABLE_BG = new Color(37, 36, 31);
    private final Color TABLE_ALT = new Color(43, 41, 33);

    private final String filePath = "data/departments.txt";

    private JTable departmentTable;
    private DefaultTableModel tableModel;

    private JTextField departmentIdField;
    private JTextField departmentNameField;
    private JTextField locationField;
    private JTextField headDoctorField;

    private BufferedImage backgroundImage;

    public DepartmentsFrame() {
        setTitle("APU Medical Centre - Department Management");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            backgroundImage = ImageIO.read(new File("assets/hospital_portal_bg.png"));
        } catch (Exception e) {
            backgroundImage = null;
        }

        DepartmentBackground root = new DepartmentBackground();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createBottomBar(), BorderLayout.SOUTH);

        loadDepartments();
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

        JLabel portal = new JLabel("MEDICAL MANAGER PORTAL  /  DEPARTMENT ADMINISTRATION");
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

        JLabel mode = new JLabel("Department Control");
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
        wrapper.setBorder(new EmptyBorder(20, 30, 20, 30));

        DepartmentCard card = new DepartmentCard();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(22, 26, 20, 26));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("HOSPITAL STRUCTURE MANAGEMENT");
        small.setForeground(MANAGER_GOLD);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Department Management");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel("Create and manage specialized hospital departments and department leadership.");
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        headingText.add(small);
        headingText.add(Box.createRigidArea(new Dimension(0, 4)));
        headingText.add(title);
        headingText.add(Box.createRigidArea(new Dimension(0, 5)));
        headingText.add(subtitle);

        JLabel fileLabel = new JLabel("●  DEPARTMENT DIRECTORY");
        fileLabel.setForeground(MANAGER_GOLD);
        fileLabel.setFont(new Font("Arial", Font.BOLD, 9));

        heading.add(headingText, BorderLayout.WEST);
        heading.add(fileLabel, BorderLayout.EAST);

        String[] columns = {
                "Department ID",
                "Department Name",
                "Location",
                "Head Doctor"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        departmentTable = new JTable(tableModel);
        styleTable(departmentTable);

        JScrollPane tableScroll = new JScrollPane(departmentTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(
                new Color(MANAGER_GOLD.getRed(), MANAGER_GOLD.getGreen(), MANAGER_GOLD.getBlue(), 90)));
        tableScroll.getViewport().setBackground(TABLE_BG);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(17, 0, 14, 0));
        center.add(tableScroll, BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 4, 12, 10));
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(15, 0, 0, 0));

        departmentIdField = createField();
        departmentNameField = createField();
        locationField = createField();
        headDoctorField = createField();

        form.add(createFieldGroup("Department ID", departmentIdField));
        form.add(createFieldGroup("Department Name", departmentNameField));
        form.add(createFieldGroup("Location", locationField));
        form.add(createFieldGroup("Head Doctor Username", headDoctorField));

        JButton addButton = new JButton("ADD");
        JButton updateButton = new JButton("UPDATE");
        JButton deleteButton = new JButton("DELETE");
        JButton refreshButton = new JButton("REFRESH");
        JButton clearButton = new JButton("CLEAR");
        JButton closeButton = new JButton("CLOSE");

        stylePrimaryButton(addButton, 95);
        stylePrimaryButton(updateButton, 105);
        styleDangerButton(deleteButton, 105);
        styleSecondaryButton(refreshButton, 105);
        styleSecondaryButton(clearButton, 90);
        styleSecondaryButton(closeButton, 90);

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

        departmentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedDepartment();
            }
        });

        addButton.addActionListener(e -> addDepartment());
        updateButton.addActionListener(e -> updateDepartment());
        deleteButton.addActionListener(e -> deleteDepartment());
        refreshButton.addActionListener(e -> loadDepartments());
        clearButton.addActionListener(e -> clearFields());
        closeButton.addActionListener(e -> dispose());

        card.add(heading, BorderLayout.NORTH);
        card.add(center, BorderLayout.CENTER);
        card.add(lower, BorderLayout.SOUTH);

        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createFieldGroup(String labelText, JTextField field) {
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
        group.add(field);
        return group;
    }

    private JTextField createField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(210, 35));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 11));
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(MANAGER_GOLD);
        field.setBackground(FIELD_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(MANAGER_GOLD.getRed(), MANAGER_GOLD.getGreen(), MANAGER_GOLD.getBlue(), 95)),
                new EmptyBorder(0, 10, 0, 10)));
        return field;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(32);
        table.setFont(new Font("Arial", Font.PLAIN, 11));
        table.setForeground(TEXT_WHITE);
        table.setBackground(TABLE_BG);
        table.setGridColor(new Color(83, 76, 55));
        table.setSelectionBackground(new Color(105, 82, 30));
        table.setSelectionForeground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowVerticalLines(false);

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(0, 36));
        table.getTableHeader().setBackground(new Color(75, 61, 31));
        table.getTableHeader().setForeground(new Color(255, 236, 186));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));

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

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private void stylePrimaryButton(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 36));
        button.setBackground(MANAGER_GOLD);
        button.setForeground(new Color(39, 29, 10));
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 36));
        button.setBackground(new Color(43, 41, 32));
        button.setForeground(TEXT_WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(
                new Color(MANAGER_GOLD.getRed(), MANAGER_GOLD.getGreen(), MANAGER_GOLD.getBlue(), 105)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleDangerButton(JButton button, int width) {
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

        JLabel status = new JLabel("●  SYSTEM READY     |     DEPARTMENT ADMINISTRATION");
        status.setForeground(MANAGER_GOLD);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("MANAGER AUTHORIZED CHANGES");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);
        return bottom;
    }

    private void loadDepartments() {
        tableModel.setRowCount(0);

        try {
            Path path = Path.of(filePath);

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                String[] data = line.split(",", -1);

                if (data.length >= 4) {
                    tableModel.addRow(new Object[]{
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim()
                    });
                }
            }

        } catch (IOException e) {
            showFileError("Unable to load departments.", e);
        }
    }

    private void loadSelectedDepartment() {
        int selectedRow = departmentTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        departmentIdField.setText(
                tableModel.getValueAt(selectedRow, 0).toString());

        departmentNameField.setText(
                tableModel.getValueAt(selectedRow, 1).toString());

        locationField.setText(
                tableModel.getValueAt(selectedRow, 2).toString());

        headDoctorField.setText(
                tableModel.getValueAt(selectedRow, 3).toString());
    }

    private void addDepartment() {
        String[] departmentData = getFormData();

        if (departmentData == null) {
            return;
        }

        try {
            Path path = Path.of(filePath);

            List<String> lines =
                    new ArrayList<>(Files.readAllLines(path));

            for (String line : lines) {
                String[] data = line.split(",", -1);

                if (data.length >= 4
                        && data[0].trim().equalsIgnoreCase(departmentData[0])) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Department ID already exists.",
                            "Duplicate Department",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            lines.add(String.join(",", departmentData));
            Files.write(path, lines);

            JOptionPane.showMessageDialog(
                    this,
                    "Department added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            clearFields();
            loadDepartments();

        } catch (IOException e) {
            showFileError("Unable to add department.", e);
        }
    }

    private void updateDepartment() {
        int selectedRow = departmentTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a department first.",
                    "No Department Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] updatedDepartment = getFormData();

        if (updatedDepartment == null) {
            return;
        }

        String originalId =
                tableModel.getValueAt(selectedRow, 0).toString();

        try {
            Path path = Path.of(filePath);

            List<String> lines =
                    new ArrayList<>(Files.readAllLines(path));

            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {
                String[] data = lines.get(i).split(",", -1);

                if (data.length >= 4
                        && data[0].trim().equalsIgnoreCase(originalId)) {

                    lines.set(i, String.join(",", updatedDepartment));
                    updated = true;
                    break;
                }
            }

            if (updated) {
                Files.write(path, lines);

                JOptionPane.showMessageDialog(
                        this,
                        "Department updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                clearFields();
                loadDepartments();
            }

        } catch (IOException e) {
            showFileError("Unable to update department.", e);
        }
    }

    private void deleteDepartment() {
        int selectedRow = departmentTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a department first.",
                    "No Department Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String departmentId =
                tableModel.getValueAt(selectedRow, 0).toString();

        String departmentName =
                tableModel.getValueAt(selectedRow, 1).toString();

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Delete department " + departmentName + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Path path = Path.of(filePath);

            List<String> lines =
                    new ArrayList<>(Files.readAllLines(path));

            lines.removeIf(line -> {
                String[] data = line.split(",", -1);

                return data.length >= 4
                        && data[0].trim().equalsIgnoreCase(departmentId);
            });

            Files.write(path, lines);

            JOptionPane.showMessageDialog(
                    this,
                    "Department deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            clearFields();
            loadDepartments();

        } catch (IOException e) {
            showFileError("Unable to delete department.", e);
        }
    }

    private String[] getFormData() {
        String id = departmentIdField.getText().trim();
        String name = departmentNameField.getText().trim();
        String location = locationField.getText().trim();
        String headDoctor = headDoctorField.getText().trim();

        if (id.isEmpty()
                || name.isEmpty()
                || location.isEmpty()
                || headDoctor.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);

            return null;
        }

        return new String[]{
                id,
                name.replace(",", ";"),
                location.replace(",", ";"),
                headDoctor
        };
    }

    private void clearFields() {
        departmentTable.clearSelection();
        departmentIdField.setText("");
        departmentNameField.setText("");
        locationField.setText("");
        headDoctorField.setText("");
    }

    private void showFileError(String message, IOException e) {
        JOptionPane.showMessageDialog(
                this,
                message + "\n" + e.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private class DepartmentBackground extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(20, 21, 20));
            g2.fillRect(0, 0, getWidth(), getHeight());

            if (backgroundImage != null) {
                g2.drawImage(
                        backgroundImage,
                        0, 0,
                        getWidth(), getHeight(),
                        null);
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
                    new Color(
                            MANAGER_GOLD.getRed(),
                            MANAGER_GOLD.getGreen(),
                            MANAGER_GOLD.getBlue(),
                            0));

            g2.setPaint(accent);
            g2.fillRect(0, 82, getWidth(), 2);

            g2.dispose();
        }
    }

    private class DepartmentCard extends JPanel {
        DepartmentCard() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRoundRect(
                    7, 8,
                    getWidth() - 11,
                    getHeight() - 11,
                    22, 22);

            GradientPaint gradient = new GradientPaint(
                    0, 0,
                    new Color(
                            MANAGER_GOLD.getRed(),
                            MANAGER_GOLD.getGreen(),
                            MANAGER_GOLD.getBlue(),
                            25),
                    0, getHeight(),
                    new Color(38, 37, 31, 246));

            g2.setPaint(gradient);
            g2.fillRoundRect(
                    1, 1,
                    getWidth() - 8,
                    getHeight() - 9,
                    20, 20);

            g2.setColor(new Color(
                    MANAGER_GOLD.getRed(),
                    MANAGER_GOLD.getGreen(),
                    MANAGER_GOLD.getBlue(),
                    110));

            g2.drawRoundRect(
                    1, 1,
                    getWidth() - 8,
                    getHeight() - 9,
                    20, 20);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
