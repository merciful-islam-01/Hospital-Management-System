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

public class DoctorPerformanceFrame extends JFrame {

    private final Color MANAGER_GOLD = new Color(235, 184, 72);
    private final Color GOLD_DARK = new Color(128, 88, 18);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(174, 185, 178);
    private final Color TABLE_BG = new Color(37, 36, 31);
    private final Color TABLE_ALT = new Color(43, 41, 33);

    private final String feedbackFile = "data/feedback.txt";

    private JTable feedbackTable;
    private DefaultTableModel tableModel;

    private JLabel averageRatingLabel;
    private JLabel totalFeedbackLabel;

    private BufferedImage backgroundImage;

    public DoctorPerformanceFrame() {

        setTitle("APU Medical Centre - Doctor Performance & Feedback");
        setSize(1050, 680);
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

        PerformanceBackground root = new PerformanceBackground();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createBottomBar(), BorderLayout.SOUTH);

        loadFeedback();
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

        JPanel brandText = new JPanel();
        brandText.setOpaque(false);
        brandText.setLayout(new BoxLayout(brandText, BoxLayout.Y_AXIS));

        JLabel hospital = new JLabel("APU MEDICAL CENTRE");
        hospital.setForeground(TEXT_WHITE);
        hospital.setFont(new Font("Arial", Font.BOLD, 19));

        JLabel portal = new JLabel(
                "MEDICAL MANAGER PORTAL  /  PERFORMANCE ANALYTICS"
        );
        portal.setForeground(MANAGER_GOLD);
        portal.setFont(new Font("Arial", Font.BOLD, 9));

        brandText.add(hospital);
        brandText.add(Box.createRigidArea(new Dimension(0, 3)));
        brandText.add(portal);

        brand.add(cross);
        brand.add(Box.createRigidArea(new Dimension(13, 0)));
        brand.add(brandText);

        JPanel status = new JPanel();
        status.setOpaque(false);
        status.setLayout(new BoxLayout(status, BoxLayout.Y_AXIS));

        JLabel session = new JLabel("●  MANAGEMENT ANALYTICS");
        session.setForeground(MANAGER_GOLD);
        session.setFont(new Font("Arial", Font.BOLD, 9));
        session.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel mode = new JLabel("Patient Feedback Overview");
        mode.setForeground(TEXT_WHITE);
        mode.setFont(new Font("Arial", Font.BOLD, 13));
        mode.setAlignmentX(Component.RIGHT_ALIGNMENT);

        status.add(session);
        status.add(Box.createRigidArea(new Dimension(0, 4)));
        status.add(mode);

        header.add(brand, BorderLayout.WEST);
        header.add(status, BorderLayout.EAST);

        return header;
    }

    private JPanel createMainContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(22, 34, 22, 34));

        PerformanceCard card = new PerformanceCard();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 28, 22, 28));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("QUALITY & EXPERIENCE MONITORING");
        small.setForeground(MANAGER_GOLD);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Doctor Performance & Feedback");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Review patient ratings and comments recorded for hospital doctors."
        );
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        heading.add(small);
        heading.add(Box.createRigidArea(new Dimension(0, 4)));
        heading.add(title);
        heading.add(Box.createRigidArea(new Dimension(0, 5)));
        heading.add(subtitle);

        JPanel summary = new JPanel(new GridLayout(1, 2, 12, 0));
        summary.setOpaque(false);
        summary.setPreferredSize(new Dimension(390, 72));

        averageRatingLabel = createSummaryLabel("Average Rating: 0.0 / 5");
        totalFeedbackLabel = createSummaryLabel("Total Feedback: 0");

        summary.add(averageRatingLabel);
        summary.add(totalFeedbackLabel);

        top.add(heading, BorderLayout.WEST);
        top.add(summary, BorderLayout.EAST);

        String[] columns = {
                "Feedback ID",
                "Patient",
                "Doctor",
                "Rating",
                "Comment"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        feedbackTable = new JTable(tableModel);
        styleTable(feedbackTable);

        JScrollPane tableScroll = new JScrollPane(feedbackTable);
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
        tableScroll.getViewport().setBackground(TABLE_BG);

        JPanel tableHolder = new JPanel(new BorderLayout());
        tableHolder.setOpaque(false);
        tableHolder.setBorder(new EmptyBorder(20, 0, 18, 0));
        tableHolder.add(tableScroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0)
        );
        buttons.setOpaque(false);

        JButton refreshButton = new JButton("REFRESH");
        JButton detailsButton = new JButton("VIEW DETAILS");
        JButton closeButton = new JButton("CLOSE");

        styleSecondaryButton(refreshButton, 110);
        stylePrimaryButton(detailsButton, 145);
        styleSecondaryButton(closeButton, 100);

        refreshButton.addActionListener(e -> loadFeedback());
        detailsButton.addActionListener(e -> viewDetails());
        closeButton.addActionListener(e -> dispose());

        buttons.add(refreshButton);
        buttons.add(detailsButton);
        buttons.add(closeButton);

        card.add(top, BorderLayout.NORTH);
        card.add(tableHolder, BorderLayout.CENTER);
        card.add(buttons, BorderLayout.SOUTH);

        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    private JLabel createSummaryLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(50, 46, 32));
        label.setForeground(new Color(255, 231, 170));
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        MANAGER_GOLD.getRed(),
                                        MANAGER_GOLD.getGreen(),
                                        MANAGER_GOLD.getBlue(),
                                        105
                                )
                        ),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );
        return label;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(34);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setForeground(TEXT_WHITE);
        table.setBackground(TABLE_BG);
        table.setGridColor(new Color(83, 76, 55));
        table.setSelectionBackground(new Color(105, 82, 30));
        table.setSelectionForeground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowVerticalLines(false);

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.getTableHeader().setBackground(new Color(75, 61, 31));
        table.getTableHeader().setForeground(new Color(255, 236, 186));
        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 11)
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

                        if (component instanceof JLabel) {
                            ((JLabel) component).setBorder(
                                    new EmptyBorder(0, 10, 0, 10)
                            );
                        }

                        return component;
                    }
                };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(renderer);
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(115);
        table.getColumnModel().getColumn(1).setPreferredWidth(115);
        table.getColumnModel().getColumn(2).setPreferredWidth(115);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(4).setPreferredWidth(420);
    }

    private void stylePrimaryButton(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 38));
        button.setBackground(MANAGER_GOLD);
        button.setForeground(new Color(39, 29, 10));
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton button, int width) {
        button.setPreferredSize(new Dimension(width, 38));
        button.setBackground(new Color(43, 41, 32));
        button.setForeground(TEXT_WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 10));
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
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JPanel createBottomBar() {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(24, 24, 22, 242));
        bottom.setBorder(new EmptyBorder(9, 28, 9, 28));

        JLabel status = new JLabel(
                "●  SYSTEM READY     |     PERFORMANCE & FEEDBACK ANALYTICS"
        );
        status.setForeground(MANAGER_GOLD);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("PATIENT EXPERIENCE DATA");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);

        return bottom;
    }

    private void loadFeedback() {
        tableModel.setRowCount(0);

        double totalRating = 0;
        int feedbackCount = 0;

        try {
            Path path = Path.of(feedbackFile);

            if (!Files.exists(path)) {
                return;
            }

            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                String[] data = line.split(",", 5);

                if (data.length >= 5) {
                    String feedbackId = data[0].trim();
                    String patient = data[1].trim();
                    String doctor = data[2].trim();
                    String rating = data[3].trim();
                    String comment = data[4].trim();

                    tableModel.addRow(
                            new Object[]{
                                    feedbackId,
                                    patient,
                                    doctor,
                                    rating + "/5",
                                    comment
                            }
                    );

                    try {
                        totalRating += Double.parseDouble(rating);
                        feedbackCount++;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            if (feedbackCount > 0) {
                double average = totalRating / feedbackCount;

                averageRatingLabel.setText(
                        String.format(
                                "Average Rating: %.1f / 5",
                                average
                        )
                );
            } else {
                averageRatingLabel.setText(
                        "Average Rating: 0.0 / 5"
                );
            }

            totalFeedbackLabel.setText(
                    "Total Feedback: " + feedbackCount
            );

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load feedback.\n" + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void viewDetails() {
        int selectedRow = feedbackTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a feedback record first.",
                    "No Feedback Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String feedbackId =
                tableModel.getValueAt(selectedRow, 0).toString();

        String patient =
                tableModel.getValueAt(selectedRow, 1).toString();

        String doctor =
                tableModel.getValueAt(selectedRow, 2).toString();

        String rating =
                tableModel.getValueAt(selectedRow, 3).toString();

        String comment =
                tableModel.getValueAt(selectedRow, 4).toString();

        JOptionPane.showMessageDialog(
                this,
                "Feedback ID: "
                        + feedbackId
                        + "\nPatient: "
                        + patient
                        + "\nDoctor: "
                        + doctor
                        + "\nRating: "
                        + rating
                        + "\n\nComment:\n"
                        + comment,
                "Feedback Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private class PerformanceBackground extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(20, 21, 20));
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
                    new Color(22, 23, 21, 248),
                    getWidth(),
                    0,
                    new Color(19, 22, 22, 222)
            );
            g2.setPaint(overlay);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(19, 20, 19, 195));
            g2.fillRect(0, 0, getWidth(), 84);

            GradientPaint accent = new GradientPaint(
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
            g2.fillRect(0, 82, getWidth(), 2);

            g2.dispose();
        }
    }

    private class PerformanceCard extends JPanel {
        PerformanceCard() {
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
                            MANAGER_GOLD.getRed(),
                            MANAGER_GOLD.getGreen(),
                            MANAGER_GOLD.getBlue(),
                            25
                    ),
                    0,
                    getHeight(),
                    new Color(38, 37, 31, 246)
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
