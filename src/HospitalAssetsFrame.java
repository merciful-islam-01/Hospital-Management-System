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

public class HospitalAssetsFrame extends JFrame {

    private final Color ADMIN_PURPLE = new Color(180, 105, 255);
    private final Color PURPLE_DARK = new Color(82, 42, 122);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(170, 180, 180);
    private final Color FIELD_BG = new Color(43, 34, 50);
    private final Color TABLE_BG = new Color(34, 29, 39);
    private final Color TABLE_ALT = new Color(42, 34, 48);

    // Compatibility aliases for original backend helper methods.
    private final Color DARK_GREEN = PURPLE_DARK;
    private final Color GREEN = ADMIN_PURPLE;
    private final Color LIGHT_GREEN = FIELD_BG;

    private final String filePath = "data/hospital_assets.txt";

    private JTable assetTable;
    private DefaultTableModel tableModel;

    private JTextField assetIdField;
    private JTextField assetNameField;
    private JComboBox<String> assetTypeComboBox;
    private JTextField locationField;
    private JComboBox<String> statusComboBox;

    private BufferedImage backgroundImage;

    public HospitalAssetsFrame() {
        setTitle("APU Medical Centre - Hospital Assets");
        setSize(1150, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            backgroundImage = ImageIO.read(new File("assets/hospital_portal_bg.png"));
        } catch (Exception e) {
            backgroundImage = null;
        }

        AssetsBackground root = new AssetsBackground();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createBottomBar(), BorderLayout.SOUTH);

        loadAssets();
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

        JLabel portal = new JLabel("ADMIN STAFF PORTAL  /  FACILITIES & ASSETS");
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

        JLabel mode = new JLabel("Hospital Asset Control");
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

        AssetsCard card = new AssetsCard();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 24, 18, 24));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("FACILITY RESOURCE ADMINISTRATION");
        small.setForeground(ADMIN_PURPLE);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Hospital Assets");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Maintain hospital equipment, facilities, quantities, locations and operational status."
        );
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        headingText.add(small);
        headingText.add(Box.createRigidArea(new Dimension(0, 4)));
        headingText.add(title);
        headingText.add(Box.createRigidArea(new Dimension(0, 5)));
        headingText.add(subtitle);

        JLabel directory = new JLabel("●  ASSET REGISTER");
        directory.setForeground(ADMIN_PURPLE);
        directory.setFont(new Font("Arial", Font.BOLD, 9));

        heading.add(headingText, BorderLayout.WEST);
        heading.add(directory, BorderLayout.EAST);

        String[] columns = {
                "Asset ID", "Asset Name", "Asset Type",
                "Location", "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        assetTable = new JTable(tableModel);
        styleTable();

        JScrollPane scroll = new JScrollPane(assetTable);
        scroll.setBorder(BorderFactory.createLineBorder(
                new Color(ADMIN_PURPLE.getRed(), ADMIN_PURPLE.getGreen(), ADMIN_PURPLE.getBlue(), 95)
        ));
        scroll.getViewport().setBackground(TABLE_BG);

        JPanel tableHolder = new JPanel(new BorderLayout());
        tableHolder.setOpaque(false);
        tableHolder.setBorder(new EmptyBorder(16, 0, 12, 0));
        tableHolder.add(scroll, BorderLayout.CENTER);

        assetIdField = createField();
        assetNameField = createField();

        assetTypeComboBox = new JComboBox<>(new String[]{
                "Medical Equipment",
                "Furniture",
                "IT Equipment",
                "Facility",
                "Other"
        });
        styleCombo(assetTypeComboBox);

        locationField = createField();

        statusComboBox = new JComboBox<>(new String[]{
                "Available",
                "In Use",
                "Under Maintenance",
                "Out of Service"
        });
        styleCombo(statusComboBox);

        JPanel form = new JPanel(new GridLayout(1, 5, 10, 10));
        form.setOpaque(false);
        form.add(fieldGroup("Asset ID", assetIdField));
        form.add(fieldGroup("Asset Name", assetNameField));
        form.add(fieldGroup("Asset Type", assetTypeComboBox));
        form.add(fieldGroup("Location", locationField));
        form.add(fieldGroup("Status", statusComboBox));

        JButton addButton = new JButton("ADD ASSET");
        JButton updateButton = new JButton("UPDATE ASSET");
        JButton deleteButton = new JButton("DELETE ASSET");
        JButton refreshButton = new JButton("REFRESH");
        JButton clearButton = new JButton("CLEAR");
        JButton closeButton = new JButton("CLOSE");

        stylePrimary(addButton, 110);
        stylePrimary(updateButton, 125);
        styleDanger(deleteButton, 120);
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

        assetTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedAsset();
            }
        });

        addButton.addActionListener(e -> addAsset());
        updateButton.addActionListener(e -> updateAsset());
        deleteButton.addActionListener(e -> deleteAsset());
        refreshButton.addActionListener(e -> loadAssets());
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
        required.setForeground(ADMIN_PURPLE);
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
        assetTable.setRowHeight(31);
        assetTable.setFont(new Font("Arial", Font.PLAIN, 11));
        assetTable.setForeground(TEXT_WHITE);
        assetTable.setBackground(TABLE_BG);
        assetTable.setGridColor(new Color(79, 59, 91));
        assetTable.setSelectionBackground(new Color(91, 55, 116));
        assetTable.setSelectionForeground(Color.WHITE);
        assetTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        assetTable.setShowVerticalLines(false);

        assetTable.getTableHeader().setReorderingAllowed(false);
        assetTable.getTableHeader().setPreferredSize(new Dimension(0, 35));
        assetTable.getTableHeader().setBackground(new Color(69, 43, 82));
        assetTable.getTableHeader().setForeground(new Color(240, 218, 255));
        assetTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));

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
                    ((JLabel) c).setBorder(new EmptyBorder(0, 9, 0, 9));
                }
                return c;
            }
        };

        for (int i = 0; i < assetTable.getColumnCount(); i++) {
            assetTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
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
                "●  SYSTEM READY     |     HOSPITAL FACILITY & ASSET CONTROL"
        );
        status.setForeground(ADMIN_PURPLE);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("ADMIN AUTHORIZED ASSET REGISTER");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);
        return bottom;
    }

    private void loadAssets() {

        tableModel.setRowCount(0);

        try {

            Path path =
                    Path.of(filePath);

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
                    "Unable to load hospital assets.",
                    e
            );
        }
    }

    // ==========================================
    // LOAD SELECTED ASSET
    // ==========================================

    private void loadSelectedAsset() {

        int selectedRow =
                assetTable.getSelectedRow();

        if (selectedRow == -1) {

            return;
        }

        assetIdField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString()
        );

        assetNameField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString()
        );

        assetTypeComboBox.setSelectedItem(
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString()
        );

        locationField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString()
        );

        statusComboBox.setSelectedItem(
                tableModel
                        .getValueAt(
                                selectedRow,
                                4
                        )
                        .toString()
        );
    }

    // ==========================================
    // ADD ASSET
    // ==========================================

    private void addAsset() {

        String[] assetData =
                getFormData();

        if (assetData == null) {

            return;
        }

        try {

            Path path =
                    Path.of(filePath);

            if (!Files.exists(path)) {

                Files.createFile(path);
            }

            List<String> lines =
                    new ArrayList<>(
                            Files.readAllLines(
                                    path
                            )
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
                                    assetData[0]
                            )
                ) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Asset ID already exists.",
                            "Duplicate Asset",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }
            }

            lines.add(
                    String.join(
                            ",",
                            assetData
                    )
            );

            Files.write(
                    path,
                    lines
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Hospital asset added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadAssets();

        } catch (IOException e) {

            showFileError(
                    "Unable to add hospital asset.",
                    e
            );
        }
    }

    // ==========================================
    // UPDATE ASSET
    // ==========================================

    private void updateAsset() {

        int selectedRow =
                assetTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an asset first.",
                    "No Asset Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String[] updatedAsset =
                getFormData();

        if (updatedAsset == null) {

            return;
        }

        String originalAssetId =
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
                    new ArrayList<>(
                            Files.readAllLines(
                                    path
                            )
                    );

            boolean updated = false;

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
                                    originalAssetId
                            )
                ) {

                    lines.set(
                            i,
                            String.join(
                                    ",",
                                    updatedAsset
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
                        "Hospital asset updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

                loadAssets();
            }

        } catch (IOException e) {

            showFileError(
                    "Unable to update hospital asset.",
                    e
            );
        }
    }

    // ==========================================
    // DELETE ASSET
    // ==========================================

    private void deleteAsset() {

        int selectedRow =
                assetTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an asset first.",
                    "No Asset Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String assetId =
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString();

        String assetName =
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete "
                                + assetName
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
                            data.length >= 5
                            &&
                            data[0]
                                    .trim()
                                    .equalsIgnoreCase(
                                            assetId
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
                    "Hospital asset deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            loadAssets();

        } catch (IOException e) {

            showFileError(
                    "Unable to delete hospital asset.",
                    e
            );
        }
    }

    // ==========================================
    // GET FORM DATA
    // ==========================================

    private String[] getFormData() {

        String assetId =
                assetIdField
                        .getText()
                        .trim();

        String assetName =
                assetNameField
                        .getText()
                        .trim();

        String assetType =
                assetTypeComboBox
                        .getSelectedItem()
                        .toString();

        String location =
                locationField
                        .getText()
                        .trim();

        String status =
                statusComboBox
                        .getSelectedItem()
                        .toString();

        if (
            assetId.isEmpty()
            ||
            assetName.isEmpty()
            ||
            location.isEmpty()
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
                assetId,
                assetName.replace(",", ";"),
                assetType,
                location.replace(",", ";"),
                status
        };
    }

    // ==========================================
    // CLEAR
    // ==========================================

    private void clearFields() {

        assetTable.clearSelection();

        assetIdField.setText("");
        assetNameField.setText("");
        locationField.setText("");

        assetTypeComboBox.setSelectedIndex(0);
        statusComboBox.setSelectedIndex(0);
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

    private class AssetsBackground extends JPanel {
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

    private class AssetsCard extends JPanel {
        AssetsCard() {
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
            g2.fillRoundRect(7, 8, getWidth() - 11, getHeight() - 11, 22, 22);

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
            g2.fillRoundRect(1, 1, getWidth() - 8, getHeight() - 9, 20, 20);

            g2.setColor(new Color(
                    ADMIN_PURPLE.getRed(),
                    ADMIN_PURPLE.getGreen(),
                    ADMIN_PURPLE.getBlue(),
                    110
            ));
            g2.drawRoundRect(1, 1, getWidth() - 8, getHeight() - 9, 20, 20);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
