import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class AssignedDoctorsFrame extends JFrame {

    private final Color MANAGER_GOLD = new Color(235, 184, 72);
    private final Color GOLD_DARK = new Color(128, 88, 18);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(174, 185, 178);
    private final Color TABLE_BG = new Color(37, 36, 31);
    private final Color TABLE_ALT = new Color(43, 41, 33);

    private final String managerUsername;

    private final String usersFile =
            "data/users.txt";

    private final String assignmentsFile =
            "data/doctor_manager_assignments.txt";

    private JTable doctorTable;
    private DefaultTableModel tableModel;

    private BufferedImage backgroundImage;

    public AssignedDoctorsFrame(String managerUsername) {

        this.managerUsername = managerUsername;

        setTitle("APU Medical Centre - Assigned Doctors");
        setSize(1050, 650);
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

        AssignedDoctorsBackground root =
                new AssignedDoctorsBackground();

        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(
                createHeader(),
                BorderLayout.NORTH
        );

        root.add(
                createMainContent(),
                BorderLayout.CENTER
        );

        root.add(
                createBottomBar(),
                BorderLayout.SOUTH
        );

        loadAssignedDoctors();
    }

    private JPanel createHeader() {

        JPanel header =
                new JPanel(new BorderLayout());

        header.setOpaque(false);

        header.setBorder(
                new EmptyBorder(
                        16,
                        28,
                        14,
                        30
                )
        );

        header.setPreferredSize(
                new Dimension(
                        0,
                        84
                )
        );

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
                new JLabel(
                        "+",
                        SwingConstants.CENTER
                );

        cross.setOpaque(true);

        cross.setBackground(
                new Color(
                        255,
                        247,
                        222
                )
        );

        cross.setForeground(GOLD_DARK);

        cross.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        cross.setPreferredSize(
                new Dimension(
                        48,
                        48
                )
        );

        cross.setMaximumSize(
                new Dimension(
                        48,
                        48
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

        hospital.setForeground(TEXT_WHITE);

        hospital.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );

        JLabel portal =
                new JLabel(
                        "MEDICAL MANAGER PORTAL  /  DOCTOR ASSIGNMENTS"
                );

        portal.setForeground(MANAGER_GOLD);

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
                        "●  MANAGEMENT SESSION"
                );

        secure.setForeground(MANAGER_GOLD);

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

        JLabel manager =
                new JLabel(
                        managerUsername
                );

        manager.setForeground(TEXT_WHITE);

        manager.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        manager.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        session.add(secure);

        session.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );

        session.add(manager);

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

    private JPanel createMainContent() {

        JPanel wrapper =
                new JPanel(
                        new BorderLayout()
                );

        wrapper.setOpaque(false);

        wrapper.setBorder(
                new EmptyBorder(
                        22,
                        34,
                        22,
                        34
                )
        );

        AssignedDoctorsCard card =
                new AssignedDoctorsCard();

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        24,
                        28,
                        22,
                        28
                )
        );

        JPanel heading =
                new JPanel(
                        new BorderLayout()
                );

        heading.setOpaque(false);

        JPanel headingText =
                new JPanel();

        headingText.setOpaque(false);

        headingText.setLayout(
                new BoxLayout(
                        headingText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel small =
                new JLabel(
                        "CLINICAL TEAM OVERSIGHT"
                );

        small.setForeground(MANAGER_GOLD);

        small.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        JLabel title =
                new JLabel(
                        "Assigned Doctors"
                );

        title.setForeground(TEXT_WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Doctors currently assigned to your management responsibility."
                );

        subtitle.setForeground(TEXT_MUTED);

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        headingText.add(small);

        headingText.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                4
                        )
                )
        );

        headingText.add(title);

        headingText.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                5
                        )
                )
        );

        headingText.add(subtitle);

        JLabel readOnly =
                new JLabel(
                        "●  READ-ONLY ASSIGNMENT VIEW"
                );

        readOnly.setForeground(MANAGER_GOLD);

        readOnly.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        9
                )
        );

        heading.add(
                headingText,
                BorderLayout.WEST
        );

        heading.add(
                readOnly,
                BorderLayout.EAST
        );

        String[] columns = {
                "Doctor ID",
                "Name",
                "Username",
                "Contact",
                "Specialty"
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

        doctorTable =
                new JTable(tableModel);

        styleTable(doctorTable);

        JScrollPane tableScroll =
                new JScrollPane(
                        doctorTable
                );

        tableScroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                MANAGER_GOLD.getRed(),
                                MANAGER_GOLD.getGreen(),
                                MANAGER_GOLD.getBlue(),
                                90
                        )
                )
        );

        tableScroll
                .getViewport()
                .setBackground(TABLE_BG);

        JPanel tableHolder =
                new JPanel(
                        new BorderLayout()
                );

        tableHolder.setOpaque(false);

        tableHolder.setBorder(
                new EmptyBorder(
                        20,
                        0,
                        18,
                        0
                )
        );

        tableHolder.add(
                tableScroll,
                BorderLayout.CENTER
        );

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttons.setOpaque(false);

        JButton refreshButton =
                new JButton("REFRESH");

        JButton detailsButton =
                new JButton("VIEW DETAILS");

        JButton closeButton =
                new JButton("CLOSE");

        styleSecondaryButton(
                refreshButton,
                110
        );

        stylePrimaryButton(
                detailsButton,
                145
        );

        styleSecondaryButton(
                closeButton,
                100
        );

        refreshButton.addActionListener(
                e -> loadAssignedDoctors()
        );

        detailsButton.addActionListener(
                e -> viewDoctorDetails()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        buttons.add(refreshButton);
        buttons.add(detailsButton);
        buttons.add(closeButton);

        card.add(
                heading,
                BorderLayout.NORTH
        );

        card.add(
                tableHolder,
                BorderLayout.CENTER
        );

        card.add(
                buttons,
                BorderLayout.SOUTH
        );

        wrapper.add(
                card,
                BorderLayout.CENTER
        );

        return wrapper;
    }

    private void styleTable(
            JTable table
    ) {

        table.setRowHeight(34);

        table.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        table.setForeground(TEXT_WHITE);
        table.setBackground(TABLE_BG);

        table.setGridColor(
                new Color(
                        83,
                        76,
                        55
                )
        );

        table.setSelectionBackground(
                new Color(
                        105,
                        82,
                        30
                )
        );

        table.setSelectionForeground(
                Color.WHITE
        );

        table.setSelectionMode(
                ListSelectionModel
                        .SINGLE_SELECTION
        );

        table.setShowVerticalLines(false);

        table.getTableHeader()
                .setReorderingAllowed(false);

        table.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                38
                        )
                );

        table.getTableHeader()
                .setBackground(
                        new Color(
                                75,
                                61,
                                31
                        )
                );

        table.getTableHeader()
                .setForeground(
                        new Color(
                                255,
                                236,
                                186
                        )
                );

        table.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                11
                        )
                );

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
                                super
                                        .getTableCellRendererComponent(
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

                            component.setForeground(
                                    TEXT_WHITE
                            );
                        }

                        if (
                            component
                                    instanceof JLabel
                        ) {

                            ((JLabel) component)
                                    .setBorder(
                                            new EmptyBorder(
                                                    0,
                                                    10,
                                                    0,
                                                    10
                                            )
                                    );
                        }

                        return component;
                    }
                };

        for (
            int i = 0;
            i < table.getColumnCount();
            i++
        ) {

            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(
                            renderer
                    );
        }
    }

    private void stylePrimaryButton(
            JButton button,
            int width
    ) {

        button.setPreferredSize(
                new Dimension(
                        width,
                        38
                )
        );

        button.setBackground(MANAGER_GOLD);

        button.setForeground(
                new Color(
                        39,
                        29,
                        10
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
    }

    private void styleSecondaryButton(
            JButton button,
            int width
    ) {

        button.setPreferredSize(
                new Dimension(
                        width,
                        38
                )
        );

        button.setBackground(
                new Color(
                        43,
                        41,
                        32
                )
        );

        button.setForeground(TEXT_WHITE);

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
                                MANAGER_GOLD.getRed(),
                                MANAGER_GOLD.getGreen(),
                                MANAGER_GOLD.getBlue(),
                                105
                        )
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    private JPanel createBottomBar() {

        JPanel bottom =
                new JPanel(
                        new BorderLayout()
                );

        bottom.setBackground(
                new Color(
                        24,
                        24,
                        22,
                        242
                )
        );

        bottom.setBorder(
                new EmptyBorder(
                        9,
                        28,
                        9,
                        28
                )
        );

        JLabel status =
                new JLabel(
                        "●  SYSTEM READY     |     DOCTOR ASSIGNMENT OVERVIEW"
                );

        status.setForeground(
                MANAGER_GOLD
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
                        "MANAGER AUTHORIZED VIEW"
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

    private void loadAssignedDoctors() {

        tableModel.setRowCount(0);

        try {

            Path usersPath =
                    Path.of(usersFile);

            Path assignmentsPath =
                    Path.of(assignmentsFile);

            if (
                !Files.exists(usersPath)
                ||
                !Files.exists(assignmentsPath)
            ) {

                return;
            }

            List<String> userLines =
                    Files.readAllLines(usersPath);

            Map<String, String[]> doctors =
                    new HashMap<>();

            for (String line : userLines) {

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

                        doctors.put(
                                username.toLowerCase(),
                                data
                        );
                    }
                }
            }

            List<String> assignmentLines =
                    Files.readAllLines(
                            assignmentsPath
                    );

            for (String line : assignmentLines) {

                String[] assignment =
                        line.split(",", -1);

                if (assignment.length >= 2) {

                    String doctorUsername =
                            assignment[0].trim();

                    String savedManager =
                            assignment[1].trim();

                    if (
                        savedManager.equalsIgnoreCase(
                                managerUsername
                        )
                    ) {

                        String[] doctorData =
                                doctors.get(
                                        doctorUsername
                                                .toLowerCase()
                                );

                        if (doctorData != null) {

                            String name =
                                    doctorData[1].trim();

                            String username =
                                    doctorData[2].trim();

                            String contact =
                                    doctorData[4].trim();

                            String doctorId =
                                    doctorData[6].trim();

                            String specialty =
                                    doctorData[7].trim();

                            tableModel.addRow(
                                    new Object[]{
                                            doctorId,
                                            name,
                                            username,
                                            contact,
                                            specialty
                                    }
                            );
                        }
                    }
                }
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load assigned doctors.\n"
                            + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void viewDoctorDetails() {

        int selectedRow =
                doctorTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a doctor first.",
                    "No Doctor Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String doctorId =
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString();

        String name =
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString();

        String username =
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString();

        String contact =
                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString();

        String specialty =
                tableModel
                        .getValueAt(
                                selectedRow,
                                4
                        )
                        .toString();

        JOptionPane.showMessageDialog(
                this,
                "Doctor ID: "
                        + doctorId
                        + "\n\nName: "
                        + name
                        + "\nUsername: "
                        + username
                        + "\nContact: "
                        + contact
                        + "\nSpecialty: "
                        + specialty,
                "Doctor Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private class AssignedDoctorsBackground
            extends JPanel {

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    new Color(
                            20,
                            21,
                            20
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
                                    22,
                                    23,
                                    21,
                                    248
                            ),
                            getWidth(),
                            0,
                            new Color(
                                    19,
                                    22,
                                    22,
                                    222
                            )
                    );

            g2.setPaint(overlay);

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );

            g2.setColor(
                    new Color(
                            19,
                            20,
                            19,
                            195
                    )
            );

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    84
            );

            GradientPaint accent =
                    new GradientPaint(
                            0,
                            0,
                            MANAGER_GOLD,
                            getWidth(),
                            0,
                            new Color(
                                    MANAGER_GOLD.getRed(),
                                    MANAGER_GOLD.getGreen(),
                                    MANAGER_GOLD.getBlue(),
                                    0
                            )
                    );

            g2.setPaint(accent);

            g2.fillRect(
                    0,
                    82,
                    getWidth(),
                    2
            );

            g2.dispose();
        }
    }

    private class AssignedDoctorsCard
            extends JPanel {

        AssignedDoctorsCard() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    new Color(
                            0,
                            0,
                            0,
                            100
                    )
            );

            g2.fillRoundRect(
                    7,
                    8,
                    getWidth() - 11,
                    getHeight() - 11,
                    22,
                    22
            );

            GradientPaint gradient =
                    new GradientPaint(
                            0,
                            0,
                            new Color(
                                    MANAGER_GOLD.getRed(),
                                    MANAGER_GOLD.getGreen(),
                                    MANAGER_GOLD.getBlue(),
                                    25
                            ),
                            0,
                            getHeight(),
                            new Color(
                                    38,
                                    37,
                                    31,
                                    246
                            )
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
                            MANAGER_GOLD.getRed(),
                            MANAGER_GOLD.getGreen(),
                            MANAGER_GOLD.getBlue(),
                            110
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
