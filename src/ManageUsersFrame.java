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

public class ManageUsersFrame extends JFrame {

    private final Color ADMIN_PURPLE = new Color(180, 105, 255);
    private final Color PURPLE_DARK = new Color(82, 42, 122);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(170, 180, 180);
    private final Color FIELD_BG = new Color(43, 34, 50);
    private final Color TABLE_BG = new Color(34, 29, 39);
    private final Color TABLE_ALT = new Color(42, 34, 48);

    private final String filePath = "data/users.txt";

    private JTable userTable;
    private DefaultTableModel tableModel;

    private JTextField userIdField;
    private JTextField nameField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField contactField;
    private JComboBox<String> roleComboBox;
    private JTextField roleIdField;
    private JTextField extraField;

    private BufferedImage backgroundImage;

    public ManageUsersFrame() {
        setTitle("APU Medical Centre - Manage Users");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            backgroundImage = ImageIO.read(new File("assets/hospital_portal_bg.png"));
        } catch (Exception e) {
            backgroundImage = null;
        }

        UsersBackground root = new UsersBackground();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createBottomBar(), BorderLayout.SOUTH);

        loadUsers();
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

        JLabel portal = new JLabel("ADMIN STAFF PORTAL  /  USER ADMINISTRATION");
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

        JLabel mode = new JLabel("User Account Control");
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
        wrapper.setBorder(new EmptyBorder(18, 28, 18, 28));

        UsersCard card = new UsersCard();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 24, 18, 24));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("IDENTITY & ACCESS ADMINISTRATION");
        small.setForeground(ADMIN_PURPLE);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Manage Users");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Create, review, update and remove hospital user accounts across all system roles."
        );
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        headingText.add(small);
        headingText.add(Box.createRigidArea(new Dimension(0, 4)));
        headingText.add(title);
        headingText.add(Box.createRigidArea(new Dimension(0, 5)));
        headingText.add(subtitle);

        JLabel directory = new JLabel("●  USER DIRECTORY");
        directory.setForeground(ADMIN_PURPLE);
        directory.setFont(new Font("Arial", Font.BOLD, 9));

        heading.add(headingText, BorderLayout.WEST);
        heading.add(directory, BorderLayout.EAST);

        String[] columns = {
                "User ID", "Name", "Username", "Password",
                "Contact", "Role", "Role ID", "Extra"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userTable = new JTable(tableModel);
        styleTable();

        JScrollPane tableScroll = new JScrollPane(userTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(
                new Color(ADMIN_PURPLE.getRed(), ADMIN_PURPLE.getGreen(), ADMIN_PURPLE.getBlue(), 95)
        ));
        tableScroll.getViewport().setBackground(TABLE_BG);

        JPanel tableHolder = new JPanel(new BorderLayout());
        tableHolder.setOpaque(false);
        tableHolder.setBorder(new EmptyBorder(15, 0, 12, 0));
        tableHolder.add(tableScroll, BorderLayout.CENTER);

        userIdField = createField();
        nameField = createField();
        usernameField = createField();
        passwordField = createField();
        contactField = createField();

        roleComboBox = new JComboBox<>(new String[]{
                "Patient", "Doctor", "AdminStaff", "MedicalManager"
        });
        styleCombo(roleComboBox);

        roleIdField = createField();
        extraField = createField();

        JPanel form = new JPanel(new GridLayout(2, 4, 10, 10));
        form.setOpaque(false);

        form.add(fieldGroup("User ID", userIdField));
        form.add(fieldGroup("Name", nameField));
        form.add(fieldGroup("Username", usernameField));
        form.add(fieldGroup("Password", passwordField));
        form.add(fieldGroup("Contact", contactField));
        form.add(fieldGroup("Role", roleComboBox));
        form.add(fieldGroup("Role ID", roleIdField));
        form.add(fieldGroup("Extra / Specialty", extraField));

        JButton addButton = new JButton("ADD USER");
        JButton updateButton = new JButton("UPDATE USER");
        JButton deleteButton = new JButton("DELETE USER");
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
        buttons.setBorder(new EmptyBorder(12, 0, 0, 0));
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

        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedUser();
            }
        });

        addButton.addActionListener(e -> addUser());
        updateButton.addActionListener(e -> updateUser());
        deleteButton.addActionListener(e -> deleteUser());
        refreshButton.addActionListener(e -> loadUsers());
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

        JLabel required = new JLabel(
                labelText.equals("Extra / Specialty") ? "OPTIONAL" : "REQUIRED"
        );
        required.setForeground(
                labelText.equals("Extra / Specialty") ? TEXT_MUTED : ADMIN_PURPLE
        );
        required.setFont(new Font("Arial", Font.BOLD, 8));

        group.add(label);
        group.add(Box.createRigidArea(new Dimension(0, 2)));
        group.add(required);
        group.add(Box.createRigidArea(new Dimension(0, 4)));
        group.add(component);
        return group;
    }

    private JTextField createField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(180, 34));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        field.setFont(new Font("Arial", Font.PLAIN, 11));
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(ADMIN_PURPLE);
        field.setBackground(FIELD_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(ADMIN_PURPLE.getRed(), ADMIN_PURPLE.getGreen(), ADMIN_PURPLE.getBlue(), 90)
                ),
                new EmptyBorder(0, 9, 0, 9)
        ));
        return field;
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setPreferredSize(new Dimension(180, 34));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        combo.setFont(new Font("Arial", Font.PLAIN, 10));
        combo.setForeground(TEXT_WHITE);
        combo.setBackground(FIELD_BG);
        combo.setFocusable(false);
    }

    private void styleTable() {
        userTable.setRowHeight(30);
        userTable.setFont(new Font("Arial", Font.PLAIN, 11));
        userTable.setForeground(TEXT_WHITE);
        userTable.setBackground(TABLE_BG);
        userTable.setGridColor(new Color(79, 59, 91));
        userTable.setSelectionBackground(new Color(91, 55, 116));
        userTable.setSelectionForeground(Color.WHITE);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.setShowVerticalLines(false);

        userTable.getTableHeader().setReorderingAllowed(false);
        userTable.getTableHeader().setPreferredSize(new Dimension(0, 35));
        userTable.getTableHeader().setBackground(new Color(69, 43, 82));
        userTable.getTableHeader().setForeground(new Color(240, 218, 255));
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));

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
                    ((JLabel) c).setBorder(new EmptyBorder(0, 8, 0, 8));
                }

                return c;
            }
        };

        for (int i = 0; i < userTable.getColumnCount(); i++) {
            userTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private void stylePrimary(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 35));
        button.setBackground(ADMIN_PURPLE);
        button.setForeground(new Color(31, 18, 38));
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondary(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 35));
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
        button.setPreferredSize(new Dimension(width, 35));
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
                "●  SYSTEM READY     |     USER & ACCESS ADMINISTRATION"
        );
        status.setForeground(ADMIN_PURPLE);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("ADMIN AUTHORIZED CHANGES");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);
        return bottom;
    }

    private void loadUsers() {

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
                        line.split(",", -1);

                if (data.length >= 8) {

                    tableModel.addRow(
                            new Object[]{
                                    data[0].trim(),
                                    data[1].trim(),
                                    data[2].trim(),
                                    data[3].trim(),
                                    data[4].trim(),
                                    data[5].trim(),
                                    data[6].trim(),
                                    data[7].trim()
                            }
                    );
                }
            }

        } catch (IOException e) {

            showFileError(
                    "Unable to load users.",
                    e
            );
        }
    }

    // ==========================================
    // LOAD SELECTED USER
    // ==========================================

    private void loadSelectedUser() {

        int selectedRow =
                userTable.getSelectedRow();

        if (selectedRow == -1) {

            return;
        }

        userIdField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString()
        );

        nameField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString()
        );

        usernameField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString()
        );

        passwordField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString()
        );

        contactField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                4
                        )
                        .toString()
        );

        roleComboBox.setSelectedItem(
                tableModel
                        .getValueAt(
                                selectedRow,
                                5
                        )
                        .toString()
        );

        roleIdField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                6
                        )
                        .toString()
        );

        extraField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                7
                        )
                        .toString()
        );
    }

    // ==========================================
    // ADD USER
    // ==========================================

    private void addUser() {

        String[] newUser =
                getFormData();

        if (newUser == null) {

            return;
        }

        try {

            Path path =
                    Path.of(filePath);

            List<String> lines =
                    Files.readAllLines(path);

            for (String line : lines) {

                String[] data =
                        line.split(",", -1);

                if (data.length >= 8) {

                    if (
                        data[0].trim()
                                .equalsIgnoreCase(
                                        newUser[0]
                                )
                    ) {

                        JOptionPane.showMessageDialog(
                                this,
                                "User ID already exists.",
                                "Duplicate User ID",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }

                    if (
                        data[2].trim()
                                .equalsIgnoreCase(
                                        newUser[2]
                                )
                    ) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Username already exists.",
                                "Duplicate Username",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }
                }
            }

            String newLine =
                    String.join(
                            ",",
                            newUser
                    );

            lines.add(newLine);

            Files.write(
                    path,
                    lines
            );

            JOptionPane.showMessageDialog(
                    this,
                    "User created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadUsers();

        } catch (IOException e) {

            showFileError(
                    "Unable to create user.",
                    e
            );
        }
    }

    // ==========================================
    // UPDATE USER
    // ==========================================

    private void updateUser() {

        int selectedRow =
                userTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user first.",
                    "No User Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String[] updatedUser =
                getFormData();

        if (updatedUser == null) {

            return;
        }

        String originalUserId =
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString();

        try {

            Path path =
                    Path.of(filePath);

            List<String> lines =
                    Files.readAllLines(path);

            boolean updated =
                    false;

            for (
                int i = 0;
                i < lines.size();
                i++
            ) {

                String[] data =
                        lines.get(i)
                                .split(",", -1);

                if (
                    data.length >= 8
                    &&
                    data[0]
                            .trim()
                            .equalsIgnoreCase(
                                    originalUserId
                            )
                ) {

                    lines.set(
                            i,
                            String.join(
                                    ",",
                                    updatedUser
                            )
                    );

                    updated = true;

                    break;
                }
            }

            if (updated) {

                Files.write(
                        path,
                        lines
                );

                JOptionPane.showMessageDialog(
                        this,
                        "User updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

                loadUsers();
            }

        } catch (IOException e) {

            showFileError(
                    "Unable to update user.",
                    e
            );
        }
    }

    // ==========================================
    // DELETE USER
    // ==========================================

    private void deleteUser() {

        int selectedRow =
                userTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user first.",
                    "No User Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String userId =
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString();

        String selectedUsername =
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete user "
                                + selectedUsername
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
                    Path.of(filePath);

            List<String> lines =
                    Files.readAllLines(path);

            lines.removeIf(
                    line -> {

                        String[] data =
                                line.split(",", -1);

                        return (
                            data.length >= 8
                            &&
                            data[0]
                                    .trim()
                                    .equalsIgnoreCase(
                                            userId
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
                    "User deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadUsers();

        } catch (IOException e) {

            showFileError(
                    "Unable to delete user.",
                    e
            );
        }
    }

    // ==========================================
    // GET FORM DATA
    // ==========================================

    private String[] getFormData() {

        String userId =
                userIdField
                        .getText()
                        .trim();

        String name =
                nameField
                        .getText()
                        .trim();

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                passwordField
                        .getText()
                        .trim();

        String contact =
                contactField
                        .getText()
                        .trim();

        String role =
                roleComboBox
                        .getSelectedItem()
                        .toString();

        String roleId =
                roleIdField
                        .getText()
                        .trim();

        String extra =
                extraField
                        .getText()
                        .trim();

        if (
            userId.isEmpty()
            ||
            name.isEmpty()
            ||
            username.isEmpty()
            ||
            password.isEmpty()
            ||
            contact.isEmpty()
            ||
            roleId.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all required fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return null;
        }

        if (extra.isEmpty()) {

            extra = "-";
        }

        return new String[]{
                userId,
                name.replace(",", ";"),
                username,
                password,
                contact,
                role,
                roleId,
                extra.replace(",", ";")
        };
    }

    // ==========================================
    // CLEAR
    // ==========================================

    private void clearFields() {

        userTable.clearSelection();

        userIdField.setText("");
        nameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        contactField.setText("");
        roleIdField.setText("");
        extraField.setText("");

        roleComboBox.setSelectedIndex(0);
    }

    // ==========================================
    // FILE ERROR
    // ==========================================

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

    private class UsersBackground extends JPanel {
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

    private class UsersCard extends JPanel {
        UsersCard() {
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
