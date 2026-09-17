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

public class HospitalReportsFrame extends JFrame {

    private final Color MANAGER_GOLD = new Color(235, 184, 72);
    private final Color GOLD_DARK = new Color(128, 88, 18);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(174, 185, 178);

    private JLabel patientsValue;
    private JLabel doctorsValue;
    private JLabel appointmentsValue;
    private JLabel bookedValue;
    private JLabel cancelledValue;
    private JLabel recordsValue;
    private JLabel prescriptionsValue;
    private JLabel feedbackValue;
    private JLabel averageRatingValue;
    private JLabel departmentsValue;
    private JLabel labRequestsValue;

    private BufferedImage backgroundImage;

    public HospitalReportsFrame() {
        setTitle("APU Medical Centre - Hospital Reports");
        setSize(1050, 720);
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

        ReportsBackground root = new ReportsBackground();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createBottomBar(), BorderLayout.SOUTH);

        loadReport();
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

        JLabel portal = new JLabel(
                "MEDICAL MANAGER PORTAL  /  HOSPITAL REPORTING"
        );
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

        JLabel session = new JLabel("●  MANAGEMENT ANALYTICS");
        session.setForeground(MANAGER_GOLD);
        session.setFont(new Font("Arial", Font.BOLD, 9));
        session.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel mode = new JLabel("Hospital Activity Summary");
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
        wrapper.setBorder(new EmptyBorder(20, 32, 18, 32));

        ReportsCard mainCard = new ReportsCard();
        mainCard.setLayout(new BorderLayout());
        mainCard.setBorder(new EmptyBorder(22, 26, 20, 26));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("OPERATIONAL INTELLIGENCE");
        small.setForeground(MANAGER_GOLD);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Hospital Reports");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Live summary generated from the Hospital Management System data files."
        );
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));

        headingText.add(small);
        headingText.add(Box.createRigidArea(new Dimension(0, 4)));
        headingText.add(title);
        headingText.add(Box.createRigidArea(new Dimension(0, 5)));
        headingText.add(subtitle);

        JLabel status = new JLabel("●  SYSTEM DATA OVERVIEW");
        status.setForeground(MANAGER_GOLD);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        heading.add(headingText, BorderLayout.WEST);
        heading.add(status, BorderLayout.EAST);

        JPanel reportGrid = new JPanel(new GridLayout(3, 4, 12, 12));
        reportGrid.setOpaque(false);
        reportGrid.setBorder(new EmptyBorder(20, 0, 18, 0));

        patientsValue = addReportCard(reportGrid, "Total Patients", "P");
        doctorsValue = addReportCard(reportGrid, "Total Doctors", "D");
        appointmentsValue = addReportCard(reportGrid, "Total Appointments", "A");
        bookedValue = addReportCard(reportGrid, "Booked Appointments", "B");
        cancelledValue = addReportCard(reportGrid, "Cancelled Appointments", "C");
        recordsValue = addReportCard(reportGrid, "Medical Records", "MR");
        prescriptionsValue = addReportCard(reportGrid, "Prescriptions", "RX");
        feedbackValue = addReportCard(reportGrid, "Patient Feedback", "F");
        averageRatingValue = addReportCard(reportGrid, "Average Rating", "R");
        departmentsValue = addReportCard(reportGrid, "Departments", "DP");
        labRequestsValue = addReportCard(reportGrid, "Lab / Imaging Requests", "L");

        JPanel refreshCard = new JPanel(new GridBagLayout());
        refreshCard.setOpaque(false);

        JButton refreshButton = new JButton("REFRESH REPORT");
        stylePrimaryButton(refreshButton, 155);
        refreshButton.addActionListener(e -> loadReport());
        refreshCard.add(refreshButton);

        reportGrid.add(refreshCard);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);

        JButton closeButton = new JButton("CLOSE");
        styleSecondaryButton(closeButton, 100);
        closeButton.addActionListener(e -> dispose());

        buttons.add(closeButton);

        mainCard.add(heading, BorderLayout.NORTH);
        mainCard.add(reportGrid, BorderLayout.CENTER);
        mainCard.add(buttons, BorderLayout.SOUTH);

        wrapper.add(mainCard, BorderLayout.CENTER);
        return wrapper;
    }

    private JLabel addReportCard(
            JPanel parent,
            String title,
            String symbol
    ) {
        MetricCard card = new MetricCard(symbol);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(15, 17, 14, 17));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(TEXT_MUTED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 10));

        JLabel valueLabel = new JLabel("0");
        valueLabel.setForeground(MANAGER_GOLD);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel valuePanel = new JPanel();
        valuePanel.setOpaque(false);
        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.Y_AXIS));
        valuePanel.add(Box.createVerticalGlue());
        valuePanel.add(valueLabel);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valuePanel, BorderLayout.SOUTH);

        parent.add(card);
        return valueLabel;
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
                "●  SYSTEM READY     |     HOSPITAL REPORTING & ANALYTICS"
        );
        status.setForeground(MANAGER_GOLD);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel source = new JLabel("LIVE SYSTEM DATA SUMMARY");
        source.setForeground(TEXT_MUTED);
        source.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(source, BorderLayout.EAST);
        return bottom;
    }

    private void loadReport() {
        int totalPatients = 0;
        int totalDoctors = 0;

        int totalAppointments = 0;
        int bookedAppointments = 0;
        int cancelledAppointments = 0;

        int totalMedicalRecords = 0;
        int totalPrescriptions = 0;

        int totalFeedback = 0;
        double totalRatings = 0;

        int totalDepartments = 0;
        int totalLabRequests = 0;

        try {
            Path usersPath = Path.of("data/users.txt");

            if (Files.exists(usersPath)) {
                List<String> lines = Files.readAllLines(usersPath);

                for (String line : lines) {
                    String[] data = line.split(",", -1);

                    if (data.length >= 8) {
                        String role = data[5].trim();

                        if (role.equalsIgnoreCase("Patient")) {
                            totalPatients++;
                        } else if (role.equalsIgnoreCase("Doctor")) {
                            totalDoctors++;
                        }
                    }
                }
            }

            Path appointmentsPath = Path.of("data/appointments.txt");

            if (Files.exists(appointmentsPath)) {
                List<String> lines = Files.readAllLines(appointmentsPath);

                for (String line : lines) {
                    String[] data = line.split(",", -1);

                    if (data.length >= 6) {
                        totalAppointments++;

                        String status = data[5].trim();

                        if (status.equalsIgnoreCase("Booked")) {
                            bookedAppointments++;
                        } else if (status.equalsIgnoreCase("Cancelled")) {
                            cancelledAppointments++;
                        }
                    }
                }
            }

            Path recordsPath = Path.of("data/medical_records.txt");

            if (Files.exists(recordsPath)) {
                List<String> lines = Files.readAllLines(recordsPath);

                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        totalMedicalRecords++;
                    }
                }
            }

            Path prescriptionsPath = Path.of("data/prescriptions.txt");

            if (Files.exists(prescriptionsPath)) {
                List<String> lines = Files.readAllLines(prescriptionsPath);

                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        totalPrescriptions++;
                    }
                }
            }

            Path feedbackPath = Path.of("data/feedback.txt");

            if (Files.exists(feedbackPath)) {
                List<String> lines = Files.readAllLines(feedbackPath);

                for (String line : lines) {
                    String[] data = line.split(",", 5);

                    if (data.length >= 5) {
                        try {
                            double rating =
                                    Double.parseDouble(data[3].trim());

                            totalRatings += rating;
                            totalFeedback++;
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
            }

            Path departmentsPath = Path.of("data/departments.txt");

            if (Files.exists(departmentsPath)) {
                List<String> lines = Files.readAllLines(departmentsPath);

                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        totalDepartments++;
                    }
                }
            }

            Path labRequestsPath = Path.of("data/lab_requests.txt");

            if (Files.exists(labRequestsPath)) {
                List<String> lines = Files.readAllLines(labRequestsPath);

                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        totalLabRequests++;
                    }
                }
            }

            patientsValue.setText(String.valueOf(totalPatients));
            doctorsValue.setText(String.valueOf(totalDoctors));
            appointmentsValue.setText(String.valueOf(totalAppointments));
            bookedValue.setText(String.valueOf(bookedAppointments));
            cancelledValue.setText(String.valueOf(cancelledAppointments));
            recordsValue.setText(String.valueOf(totalMedicalRecords));
            prescriptionsValue.setText(String.valueOf(totalPrescriptions));
            feedbackValue.setText(String.valueOf(totalFeedback));

            if (totalFeedback > 0) {
                double average = totalRatings / totalFeedback;

                averageRatingValue.setText(
                        String.format("%.1f / 5", average)
                );
            } else {
                averageRatingValue.setText("0.0 / 5");
            }

            departmentsValue.setText(String.valueOf(totalDepartments));
            labRequestsValue.setText(String.valueOf(totalLabRequests));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to generate hospital report.\n"
                            + e.getMessage(),
                    "Report Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private class ReportsBackground extends JPanel {
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

    private class ReportsCard extends JPanel {
        ReportsCard() {
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

    private class MetricCard extends JPanel {
        private final String symbol;

        MetricCard(String symbol) {
            this.symbol = symbol;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(48, 45, 34, 238));
            g2.fillRoundRect(
                    1,
                    1,
                    getWidth() - 3,
                    getHeight() - 3,
                    16,
                    16
            );

            g2.setColor(
                    new Color(
                            MANAGER_GOLD.getRed(),
                            MANAGER_GOLD.getGreen(),
                            MANAGER_GOLD.getBlue(),
                            75
                    )
            );

            g2.drawRoundRect(
                    1,
                    1,
                    getWidth() - 3,
                    getHeight() - 3,
                    16,
                    16
            );

            g2.setColor(
                    new Color(
                            MANAGER_GOLD.getRed(),
                            MANAGER_GOLD.getGreen(),
                            MANAGER_GOLD.getBlue(),
                            24
                    )
            );
            g2.fillOval(getWidth() - 57, 12, 40, 40);

            g2.setColor(MANAGER_GOLD);
            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            symbol.length() > 1 ? 10 : 15
                    )
            );

            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(
                    symbol,
                    getWidth() - 37 - fm.stringWidth(symbol) / 2,
                    37
            );

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
