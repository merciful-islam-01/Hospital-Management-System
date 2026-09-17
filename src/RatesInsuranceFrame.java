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

public class RatesInsuranceFrame extends JFrame {

    private final Color ADMIN_PURPLE = new Color(180, 105, 255);
    private final Color PURPLE_DARK = new Color(82, 42, 122);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(170, 180, 180);
    private final Color FIELD_BG = new Color(43, 34, 50);
    private final Color TABLE_BG = new Color(34, 29, 39);
    private final Color TABLE_ALT = new Color(42, 34, 48);

    // Kept because the original backend uses these names.
    private final Color DARK_GREEN = PURPLE_DARK;
    private final Color GREEN = ADMIN_PURPLE;
    private final Color LIGHT_GREEN = FIELD_BG;

    private final String ratesFile = "data/consultation_rates.txt";
    private final String insuranceFile = "data/insurance_networks.txt";

    private JTable ratesTable;
    private JTable insuranceTable;

    private DefaultTableModel ratesModel;
    private DefaultTableModel insuranceModel;

    private JTextField rateIdField;
    private JTextField departmentField;
    private JTextField rateField;

    private JTextField insuranceIdField;
    private JTextField insuranceNameField;

    private BufferedImage backgroundImage;

    public RatesInsuranceFrame() {
        setTitle("APU Medical Centre - Rates & Insurance");
        setSize(1120, 720);
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

        RatesBackground root = new RatesBackground();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createBottomBar(), BorderLayout.SOUTH);

        loadRates();
        loadInsurance();
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

        JLabel portal = new JLabel(
                "ADMIN STAFF PORTAL  /  RATES & INSURANCE"
        );
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

        JLabel mode = new JLabel("Financial Configuration");
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

        RatesCard card = new RatesCard();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 24, 18, 24));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));

        JLabel small = new JLabel(
                "SERVICE PRICING & COVERAGE ADMINISTRATION"
        );
        small.setForeground(ADMIN_PURPLE);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Rates & Insurance");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Configure consultation rates and accepted insurance networks."
        );
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        headingText.add(small);
        headingText.add(Box.createRigidArea(new Dimension(0, 4)));
        headingText.add(title);
        headingText.add(Box.createRigidArea(new Dimension(0, 5)));
        headingText.add(subtitle);

        JLabel state = new JLabel("●  ADMIN CONFIGURATION");
        state.setForeground(ADMIN_PURPLE);
        state.setFont(new Font("Arial", Font.BOLD, 9));

        heading.add(headingText, BorderLayout.WEST);
        heading.add(state, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 11));
        tabs.setBackground(new Color(37, 30, 43));
        tabs.setForeground(TEXT_WHITE);
        tabs.setFocusable(false);
        tabs.setBorder(new EmptyBorder(14, 0, 0, 0));

        tabs.addTab("Consultation Rates", createRatesPanel());
        tabs.addTab("Insurance Networks", createInsurancePanel());

        JPanel bottomActions =
                new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        bottomActions.setOpaque(false);
        bottomActions.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton closeButton = new JButton("CLOSE");
        styleSecondary(closeButton, 90);
        closeButton.addActionListener(e -> dispose());
        bottomActions.add(closeButton);

        card.add(heading, BorderLayout.NORTH);
        card.add(tabs, BorderLayout.CENTER);
        card.add(bottomActions, BorderLayout.SOUTH);

        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createRatesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(35, 29, 40));
        panel.setBorder(new EmptyBorder(14, 14, 10, 14));

        ratesModel = new DefaultTableModel(
                new String[]{"Rate ID", "Department / Service", "Rate (RM)"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ratesTable = new JTable(ratesModel);
        styleTable(ratesTable);

        JScrollPane scroll = new JScrollPane(ratesTable);
        scroll.getViewport().setBackground(TABLE_BG);
        scroll.setBorder(BorderFactory.createLineBorder(
                new Color(
                        ADMIN_PURPLE.getRed(),
                        ADMIN_PURPLE.getGreen(),
                        ADMIN_PURPLE.getBlue(),
                        90
                )
        ));

        rateIdField = createField();
        departmentField = createField();
        rateField = createField();

        JPanel form = new JPanel(new GridLayout(1, 3, 10, 0));
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(10, 0, 8, 0));
        form.add(fieldGroup("Rate ID", rateIdField));
        form.add(fieldGroup("Department / Service", departmentField));
        form.add(fieldGroup("Rate (RM)", rateField));

        JButton add = new JButton("ADD RATE");
        JButton update = new JButton("UPDATE RATE");
        JButton delete = new JButton("DELETE RATE");
        JButton refresh = new JButton("REFRESH");
        JButton clear = new JButton("CLEAR");

        stylePrimary(add, 105);
        stylePrimary(update, 120);
        styleDanger(delete, 115);
        styleSecondary(refresh, 100);
        styleSecondary(clear, 85);

        JPanel actions =
                new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.add(add);
        actions.add(update);
        actions.add(delete);
        actions.add(refresh);
        actions.add(clear);

        JPanel lower = new JPanel(new BorderLayout());
        lower.setOpaque(false);
        lower.add(form, BorderLayout.CENTER);
        lower.add(actions, BorderLayout.SOUTH);

        ratesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRate();
            }
        });

        add.addActionListener(e -> addRate());
        update.addActionListener(e -> updateRate());
        delete.addActionListener(e -> deleteRate());
        refresh.addActionListener(e -> loadRates());
        clear.addActionListener(e -> clearRateFields());

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(lower, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createInsurancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(35, 29, 40));
        panel.setBorder(new EmptyBorder(14, 14, 10, 14));

        insuranceModel = new DefaultTableModel(
                new String[]{"Insurance ID", "Insurance Network / Provider"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        insuranceTable = new JTable(insuranceModel);
        styleTable(insuranceTable);

        JScrollPane scroll = new JScrollPane(insuranceTable);
        scroll.getViewport().setBackground(TABLE_BG);
        scroll.setBorder(BorderFactory.createLineBorder(
                new Color(
                        ADMIN_PURPLE.getRed(),
                        ADMIN_PURPLE.getGreen(),
                        ADMIN_PURPLE.getBlue(),
                        90
                )
        ));

        insuranceIdField = createField();
        insuranceNameField = createField();

        JPanel form = new JPanel(new GridLayout(1, 2, 10, 0));
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(10, 0, 8, 0));
        form.add(fieldGroup("Insurance ID", insuranceIdField));
        form.add(fieldGroup(
                "Insurance Network / Provider",
                insuranceNameField
        ));

        JButton add = new JButton("ADD NETWORK");
        JButton update = new JButton("UPDATE");
        JButton delete = new JButton("DELETE");
        JButton refresh = new JButton("REFRESH");
        JButton clear = new JButton("CLEAR");

        stylePrimary(add, 120);
        stylePrimary(update, 100);
        styleDanger(delete, 95);
        styleSecondary(refresh, 100);
        styleSecondary(clear, 85);

        JPanel actions =
                new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.add(add);
        actions.add(update);
        actions.add(delete);
        actions.add(refresh);
        actions.add(clear);

        JPanel lower = new JPanel(new BorderLayout());
        lower.setOpaque(false);
        lower.add(form, BorderLayout.CENTER);
        lower.add(actions, BorderLayout.SOUTH);

        insuranceTable.getSelectionModel()
                .addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        loadSelectedInsurance();
                    }
                });

        add.addActionListener(e -> addInsurance());
        update.addActionListener(e -> updateInsurance());
        delete.addActionListener(e -> deleteInsurance());
        refresh.addActionListener(e -> loadInsurance());
        clear.addActionListener(e -> clearInsuranceFields());

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(lower, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel fieldGroup(
            String labelText,
            JComponent component
    ) {
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
        field.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 34)
        );
        field.setFont(new Font("Arial", Font.PLAIN, 11));
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(ADMIN_PURPLE);
        field.setBackground(FIELD_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                ADMIN_PURPLE.getRed(),
                                ADMIN_PURPLE.getGreen(),
                                ADMIN_PURPLE.getBlue(),
                                90
                        )
                ),
                new EmptyBorder(0, 9, 0, 9)
        ));

        return field;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(31);
        table.setFont(new Font("Arial", Font.PLAIN, 11));
        table.setForeground(TEXT_WHITE);
        table.setBackground(TABLE_BG);
        table.setGridColor(new Color(79, 59, 91));
        table.setSelectionBackground(new Color(91, 55, 116));
        table.setSelectionForeground(Color.WHITE);
        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        table.setShowVerticalLines(false);

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(
                new Dimension(0, 35)
        );
        table.getTableHeader().setBackground(
                new Color(69, 43, 82)
        );
        table.getTableHeader().setForeground(
                new Color(240, 218, 255)
        );
        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 10)
        );

        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable t,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column
                    ) {
                        Component c =
                                super.getTableCellRendererComponent(
                                        t,
                                        value,
                                        isSelected,
                                        hasFocus,
                                        row,
                                        column
                                );

                        if (!isSelected) {
                            c.setBackground(
                                    row % 2 == 0
                                            ? TABLE_BG
                                            : TABLE_ALT
                            );
                            c.setForeground(TEXT_WHITE);
                        }

                        if (c instanceof JLabel) {
                            ((JLabel) c).setBorder(
                                    new EmptyBorder(0, 9, 0, 9)
                            );
                        }

                        return c;
                    }
                };

        for (int i = 0;
             i < table.getColumnCount();
             i++) {
            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(renderer);
        }
    }

    private void stylePrimary(
            JButton button,
            int width
    ) {
        button.setPreferredSize(new Dimension(width, 35));
        button.setBackground(ADMIN_PURPLE);
        button.setForeground(new Color(31, 18, 38));
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }

    private void styleSecondary(
            JButton button,
            int width
    ) {
        button.setPreferredSize(new Dimension(width, 35));
        button.setBackground(new Color(43, 34, 50));
        button.setForeground(TEXT_WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(
                new Color(
                        ADMIN_PURPLE.getRed(),
                        ADMIN_PURPLE.getGreen(),
                        ADMIN_PURPLE.getBlue(),
                        105
                )
        ));
        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }

    private void styleDanger(
            JButton button,
            int width
    ) {
        button.setPreferredSize(new Dimension(width, 35));
        button.setBackground(new Color(112, 40, 54));
        button.setForeground(new Color(255, 225, 230));
        button.setFont(new Font("Arial", Font.BOLD, 9));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(
                new Color(165, 70, 88)
        ));
        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }

    private JPanel createBottomBar() {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(22, 18, 27, 244));
        bottom.setBorder(new EmptyBorder(9, 28, 9, 28));

        JLabel status = new JLabel(
                "●  SYSTEM READY     |     RATES & INSURANCE CONFIGURATION"
        );
        status.setForeground(ADMIN_PURPLE);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel(
                "ADMIN AUTHORIZED FINANCIAL SETTINGS"
        );
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);

        return bottom;
    }

    private void loadRates() {

        ratesModel.setRowCount(0);

        try {

            Path path =
                    Path.of(ratesFile);

            if (!Files.exists(path)) {

                Files.createFile(path);
            }

            List<String> lines =
                    Files.readAllLines(path);

            for (String line : lines) {

                String[] data =
                        line.split(",", -1);

                if (data.length >= 3) {

                    ratesModel.addRow(
                            new Object[]{
                                    data[0].trim(),
                                    data[1].trim(),
                                    data[2].trim()
                            }
                    );
                }
            }

        } catch (IOException e) {

            showFileError(
                    "Unable to load consultation rates.",
                    e
            );
        }
    }

    // ==========================================
    // ADD RATE
    // ==========================================

    private void addRate() {

        String id =
                rateIdField.getText().trim();

        String department =
                departmentField.getText().trim();

        String rate =
                rateField.getText().trim();

        if (
            id.isEmpty()
            ||
            department.isEmpty()
            ||
            rate.isEmpty()
        ) {

            showMissing();

            return;
        }

        try {

            double amount =
                    Double.parseDouble(rate);

            if (amount < 0) {

                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Base rate must be a valid positive number.",
                    "Invalid Rate",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            Path path =
                    Path.of(ratesFile);

            List<String> lines =
                    new ArrayList<>(
                            Files.readAllLines(path)
                    );

            for (String line : lines) {

                String[] data =
                        line.split(",", -1);

                if (
                    data.length >= 3
                    &&
                    data[0]
                            .trim()
                            .equalsIgnoreCase(id)
                ) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Rate ID already exists.",
                            "Duplicate ID",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }
            }

            lines.add(
                    id
                            + ","
                            + department.replace(",", ";")
                            + ","
                            + rate
            );

            Files.write(
                    path,
                    lines
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Consultation rate added successfully!"
            );

            clearRateFields();

            loadRates();

        } catch (IOException e) {

            showFileError(
                    "Unable to add consultation rate.",
                    e
            );
        }
    }

    // ==========================================
    // UPDATE RATE
    // ==========================================

    private void updateRate() {

        int row =
                ratesTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a rate first."
            );

            return;
        }

        String originalId =
                ratesModel
                        .getValueAt(row, 0)
                        .toString();

        String id =
                rateIdField.getText().trim();

        String department =
                departmentField.getText().trim();

        String rate =
                rateField.getText().trim();

        if (
            id.isEmpty()
            ||
            department.isEmpty()
            ||
            rate.isEmpty()
        ) {

            showMissing();

            return;
        }

        try {

            double amount =
                    Double.parseDouble(rate);

            if (amount < 0) {

                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Base rate must be a valid positive number."
            );

            return;
        }

        try {

            Path path =
                    Path.of(ratesFile);

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
                    data.length >= 3
                    &&
                    data[0]
                            .trim()
                            .equalsIgnoreCase(
                                    originalId
                            )
                ) {

                    lines.set(
                            i,
                            id
                                    + ","
                                    + department.replace(",", ";")
                                    + ","
                                    + rate
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
                    "Consultation rate updated successfully!"
            );

            clearRateFields();

            loadRates();

        } catch (IOException e) {

            showFileError(
                    "Unable to update consultation rate.",
                    e
            );
        }
    }

    // ==========================================
    // DELETE RATE
    // ==========================================

    private void deleteRate() {

        int row =
                ratesTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a rate first."
            );

            return;
        }

        String id =
                ratesModel
                        .getValueAt(row, 0)
                        .toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete rate " + id + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {

            return;
        }

        try {

            Path path =
                    Path.of(ratesFile);

            List<String> lines =
                    new ArrayList<>(
                            Files.readAllLines(path)
                    );

            lines.removeIf(
                    line -> {

                        String[] data =
                                line.split(",", -1);

                        return (
                            data.length >= 3
                            &&
                            data[0]
                                    .trim()
                                    .equalsIgnoreCase(id)
                        );
                    }
            );

            Files.write(
                    path,
                    lines
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Consultation rate deleted successfully!"
            );

            clearRateFields();

            loadRates();

        } catch (IOException e) {

            showFileError(
                    "Unable to delete consultation rate.",
                    e
            );
        }
    }

    // ==========================================
    // LOAD INSURANCE
    // ==========================================

    private void loadInsurance() {

        insuranceModel.setRowCount(0);

        try {

            Path path =
                    Path.of(insuranceFile);

            if (!Files.exists(path)) {

                Files.createFile(path);
            }

            List<String> lines =
                    Files.readAllLines(path);

            for (String line : lines) {

                String[] data =
                        line.split(",", -1);

                if (data.length >= 2) {

                    insuranceModel.addRow(
                            new Object[]{
                                    data[0].trim(),
                                    data[1].trim()
                            }
                    );
                }
            }

        } catch (IOException e) {

            showFileError(
                    "Unable to load insurance networks.",
                    e
            );
        }
    }

    // ==========================================
    // ADD INSURANCE
    // ==========================================

    private void addInsurance() {

        String id =
                insuranceIdField.getText().trim();

        String name =
                insuranceNameField.getText().trim();

        if (
            id.isEmpty()
            ||
            name.isEmpty()
        ) {

            showMissing();

            return;
        }

        try {

            Path path =
                    Path.of(insuranceFile);

            List<String> lines =
                    new ArrayList<>(
                            Files.readAllLines(path)
                    );

            for (String line : lines) {

                String[] data =
                        line.split(",", -1);

                if (
                    data.length >= 2
                    &&
                    data[0]
                            .trim()
                            .equalsIgnoreCase(id)
                ) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Insurance ID already exists."
                    );

                    return;
                }
            }

            lines.add(
                    id
                            + ","
                            + name.replace(",", ";")
            );

            Files.write(
                    path,
                    lines
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Insurance provider added successfully!"
            );

            clearInsuranceFields();

            loadInsurance();

        } catch (IOException e) {

            showFileError(
                    "Unable to add insurance provider.",
                    e
            );
        }
    }

    // ==========================================
    // UPDATE INSURANCE
    // ==========================================

    private void updateInsurance() {

        int row =
                insuranceTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an insurance provider first."
            );

            return;
        }

        String originalId =
                insuranceModel
                        .getValueAt(row, 0)
                        .toString();

        String id =
                insuranceIdField.getText().trim();

        String name =
                insuranceNameField.getText().trim();

        if (
            id.isEmpty()
            ||
            name.isEmpty()
        ) {

            showMissing();

            return;
        }

        try {

            Path path =
                    Path.of(insuranceFile);

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
                    data.length >= 2
                    &&
                    data[0]
                            .trim()
                            .equalsIgnoreCase(
                                    originalId
                            )
                ) {

                    lines.set(
                            i,
                            id
                                    + ","
                                    + name.replace(",", ";")
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
                    "Insurance provider updated successfully!"
            );

            clearInsuranceFields();

            loadInsurance();

        } catch (IOException e) {

            showFileError(
                    "Unable to update insurance provider.",
                    e
            );
        }
    }

    // ==========================================
    // DELETE INSURANCE
    // ==========================================

    private void deleteInsurance() {

        int row =
                insuranceTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an insurance provider first."
            );

            return;
        }

        String id =
                insuranceModel
                        .getValueAt(row, 0)
                        .toString();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete insurance provider " + id + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {

            return;
        }

        try {

            Path path =
                    Path.of(insuranceFile);

            List<String> lines =
                    new ArrayList<>(
                            Files.readAllLines(path)
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
                                    .equalsIgnoreCase(id)
                        );
                    }
            );

            Files.write(
                    path,
                    lines
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Insurance provider deleted successfully!"
            );

            clearInsuranceFields();

            loadInsurance();

        } catch (IOException e) {

            showFileError(
                    "Unable to delete insurance provider.",
                    e
            );
        }
    }

    // ==========================================
    // TABLE SELECTION HELPERS
    // ==========================================

    private void loadSelectedRate() {

        int row =
                ratesTable.getSelectedRow();

        if (row == -1) {

            return;
        }

        rateIdField.setText(
                ratesModel
                        .getValueAt(row, 0)
                        .toString()
        );

        departmentField.setText(
                ratesModel
                        .getValueAt(row, 1)
                        .toString()
        );

        rateField.setText(
                ratesModel
                        .getValueAt(row, 2)
                        .toString()
        );
    }

    private void loadSelectedInsurance() {

        int row =
                insuranceTable.getSelectedRow();

        if (row == -1) {

            return;
        }

        insuranceIdField.setText(
                insuranceModel
                        .getValueAt(row, 0)
                        .toString()
        );

        insuranceNameField.setText(
                insuranceModel
                        .getValueAt(row, 1)
                        .toString()
        );
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

    private void clearRateFields() {

        ratesTable.clearSelection();

        rateIdField.setText("");
        departmentField.setText("");
        rateField.setText("");
    }

    private void clearInsuranceFields() {

        insuranceTable.clearSelection();

        insuranceIdField.setText("");
        insuranceNameField.setText("");
    }

    private void showMissing() {

        JOptionPane.showMessageDialog(
                this,
                "Please fill in all required fields.",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE
        );
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

    private class RatesBackground extends JPanel {
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
                    new Color(24, 18, 29, 248),
                    getWidth(),
                    0,
                    new Color(13, 22, 24, 224)
            );

            g2.setPaint(overlay);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(18, 16, 22, 200));
            g2.fillRect(0, 0, getWidth(), 84);

            GradientPaint accent = new GradientPaint(
                    0,
                    0,
                    ADMIN_PURPLE,
                    getWidth(),
                    0,
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

    private class RatesCard extends JPanel {
        RatesCard() {
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
                            ADMIN_PURPLE.getRed(),
                            ADMIN_PURPLE.getGreen(),
                            ADMIN_PURPLE.getBlue(),
                            25
                    ),
                    0,
                    getHeight(),
                    new Color(37, 30, 43, 246)
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

            g2.setColor(new Color(
                    ADMIN_PURPLE.getRed(),
                    ADMIN_PURPLE.getGreen(),
                    ADMIN_PURPLE.getBlue(),
                    110
            ));

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
