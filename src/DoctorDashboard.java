
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DoctorDashboard extends JFrame {

    private final String username;

    private final Color DOCTOR_GREEN = new Color(32, 224, 157);
    private final Color GREEN_DARK = new Color(10, 105, 72);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(155, 185, 180);

    private BufferedImage backgroundImage;

    public DoctorDashboard(String username) {
        this.username = username;

        setTitle("APU Medical Centre - Doctor Portal");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            backgroundImage = ImageIO.read(
                    new File("assets/hospital_portal_bg.png")
            );
        } catch (Exception e) {
            backgroundImage = null;
        }

        DoctorBackground root = new DoctorBackground();
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createWorkspace(), BorderLayout.CENTER);
        root.add(createBottomBar(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(16, 26, 14, 30));
        header.setPreferredSize(new Dimension(0, 82));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.X_AXIS));

        JLabel cross = new JLabel("+", SwingConstants.CENTER);
        cross.setOpaque(true);
        cross.setBackground(new Color(225, 255, 245));
        cross.setForeground(GREEN_DARK);
        cross.setFont(new Font("Arial", Font.BOLD, 27));
        cross.setPreferredSize(new Dimension(46, 46));
        cross.setMaximumSize(new Dimension(46, 46));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel hospital = new JLabel("APU MEDICAL CENTRE");
        hospital.setForeground(TEXT_WHITE);
        hospital.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel portal = new JLabel("DOCTOR PORTAL");
        portal.setForeground(DOCTOR_GREEN);
        portal.setFont(new Font("Arial", Font.BOLD, 9));

        text.add(hospital);
        text.add(Box.createRigidArea(new Dimension(0, 3)));
        text.add(portal);

        brand.add(cross);
        brand.add(Box.createRigidArea(new Dimension(13, 0)));
        brand.add(text);

        JPanel session = new JPanel();
        session.setOpaque(false);
        session.setLayout(new BoxLayout(session, BoxLayout.Y_AXIS));

        JLabel secure = new JLabel("●  CLINICAL SESSION");
        secure.setForeground(DOCTOR_GREEN);
        secure.setFont(new Font("Arial", Font.BOLD, 9));
        secure.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel user = new JLabel(username);
        user.setForeground(TEXT_WHITE);
        user.setFont(new Font("Arial", Font.BOLD, 14));
        user.setAlignmentX(Component.RIGHT_ALIGNMENT);

        session.add(secure);
        session.add(Box.createRigidArea(new Dimension(0, 5)));
        session.add(user);

        header.add(brand, BorderLayout.WEST);
        header.add(session, BorderLayout.EAST);

        return header;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(2, 28, 31, 235));
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(new EmptyBorder(24, 18, 22, 18));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel workspace = new JLabel("DOCTOR WORKSPACE");
        workspace.setForeground(DOCTOR_GREEN);
        workspace.setFont(new Font("Arial", Font.BOLD, 10));

        JLabel nav = new JLabel("Navigation");
        nav.setForeground(TEXT_WHITE);
        nav.setFont(new Font("Arial", Font.BOLD, 21));

        sidebar.add(workspace);
        sidebar.add(Box.createRigidArea(new Dimension(0, 4)));
        sidebar.add(nav);
        sidebar.add(Box.createRigidArea(new Dimension(0, 22)));

        JButton profile = createMenuButton("My Profile");
        JButton appointments = createMenuButton("My Appointments");
        JButton records = createMenuButton("Patient Records");
        JButton vitals = createMenuButton("Vital Signs");
        JButton notes = createMenuButton("Consultation Notes");
        JButton prescriptions = createMenuButton("Prescriptions");
        JButton lab = createMenuButton("Lab / Imaging Request");

        profile.addActionListener(
                e -> new DoctorProfileFrame(username).setVisible(true)
        );

        appointments.addActionListener(
                e -> new DoctorAppointmentsFrame(username).setVisible(true)
        );

        records.addActionListener(
                e -> new PatientRecordsFrame().setVisible(true)
        );

        vitals.addActionListener(
                e -> new VitalSignsFrame(username).setVisible(true)
        );

        notes.addActionListener(
                e -> new ConsultationNotesFrame(username).setVisible(true)
        );

        prescriptions.addActionListener(
                e -> new DoctorPrescriptionFrame(username).setVisible(true)
        );

        lab.addActionListener(
                e -> new LabImagingRequestFrame(username).setVisible(true)
        );

        sidebar.add(profile);
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));

        sidebar.add(appointments);
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));

        sidebar.add(records);
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));

        sidebar.add(vitals);
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));

        sidebar.add(notes);
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));

        sidebar.add(prescriptions);
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));

        sidebar.add(lab);

        sidebar.add(Box.createVerticalGlue());

        JButton logout = createLogoutButton();
        logout.addActionListener(e -> logout());
        sidebar.add(logout);

        return sidebar;
    }

    private JPanel createWorkspace() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);
        outer.setBorder(new EmptyBorder(25, 30, 20, 30));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel small = new JLabel("CLINICAL CONTROL CENTRE");
        small.setForeground(DOCTOR_GREEN);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Welcome, Doctor");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 31));

        JLabel subtitle = new JLabel(
                "Manage patient care, clinical records, prescriptions and diagnostic requests."
        );
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));

        heading.add(small);
        heading.add(Box.createRigidArea(new Dimension(0, 4)));
        heading.add(title);
        heading.add(Box.createRigidArea(new Dimension(0, 5)));
        heading.add(subtitle);

        JPanel cards = new JPanel(new GridLayout(2, 4, 14, 14));
        cards.setOpaque(false);
        cards.setBorder(new EmptyBorder(24, 0, 0, 0));

        DashboardCard profile = new DashboardCard(
                "D",
                "My Profile",
                "Review and update your doctor profile and professional contact information."
        );

        DashboardCard appointments = new DashboardCard(
                "A",
                "My Appointments",
                "View patients booked with you, including appointment dates and times."
        );

        DashboardCard records = new DashboardCard(
                "R",
                "Patient Records",
                "Access patient medical records and review clinical information."
        );

        DashboardCard vitals = new DashboardCard(
                "V",
                "Vital Signs",
                "Record patient blood pressure, heart rate and other vital observations."
        );

        DashboardCard notes = new DashboardCard(
                "N",
                "Consultation Notes",
                "Document consultation findings, clinical notes and treatment outcomes."
        );

        DashboardCard prescription = new DashboardCard(
                "Rx",
                "Prescriptions",
                "Issue medication prescriptions with dosage and treatment instructions."
        );

        DashboardCard lab = new DashboardCard(
                "L",
                "Lab / Imaging",
                "Create laboratory and imaging requests for patient investigations."
        );

        profile.addMouseListener(
                click(() -> new DoctorProfileFrame(username).setVisible(true))
        );

        appointments.addMouseListener(
                click(() -> new DoctorAppointmentsFrame(username).setVisible(true))
        );

        records.addMouseListener(
                click(() -> new PatientRecordsFrame().setVisible(true))
        );

        vitals.addMouseListener(
                click(() -> new VitalSignsFrame(username).setVisible(true))
        );

        notes.addMouseListener(
                click(() -> new ConsultationNotesFrame(username).setVisible(true))
        );

        prescription.addMouseListener(
                click(() -> new DoctorPrescriptionFrame(username).setVisible(true))
        );

        lab.addMouseListener(
                click(() -> new LabImagingRequestFrame(username).setVisible(true))
        );

        cards.add(profile);
        cards.add(appointments);
        cards.add(records);
        cards.add(vitals);
        cards.add(notes);
        cards.add(prescription);
        cards.add(lab);

        outer.add(heading, BorderLayout.NORTH);
        outer.add(cards, BorderLayout.CENTER);

        return outer;
    }

    private MouseAdapter click(Runnable action) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        };
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);

        button.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 44)
        );
        button.setPreferredSize(new Dimension(210, 44));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(0, 16, 0, 10));
        button.setBackground(new Color(5, 43, 46));
        button.setForeground(TEXT_WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private JButton createLogoutButton() {
        JButton button = new JButton("Logout");

        button.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 42)
        );
        button.setPreferredSize(new Dimension(210, 42));
        button.setBackground(new Color(80, 32, 36));
        button.setForeground(new Color(255, 220, 220));
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createLineBorder(new Color(150, 60, 68))
        );
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void logout() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            new RoleSelectionFrame().setVisible(true);
            dispose();
        }
    }

    private JPanel createBottomBar() {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(2, 27, 30, 240));
        bottom.setBorder(new EmptyBorder(9, 25, 9, 25));

        JLabel status = new JLabel(
                "●  SYSTEM READY     |     CLINICAL WORKSPACE"
        );
        status.setForeground(DOCTOR_GREEN);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("SECURE DOCTOR SESSION");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);

        return bottom;
    }

    private class DoctorBackground extends JPanel {
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
                    0, 0, new Color(0, 22, 25, 248),
                    getWidth(), 0, new Color(0, 18, 25, 220)
            );
            g2.setPaint(overlay);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(0, 18, 22, 190));
            g2.fillRect(0, 0, getWidth(), 82);

            GradientPaint accent = new GradientPaint(
                    0, 0, DOCTOR_GREEN,
                    getWidth(), 0,
                    new Color(
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            0
                    )
            );

            g2.setPaint(accent);
            g2.fillRect(0, 80, getWidth(), 2);

            g2.setColor(
                    new Color(
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            10
                    )
            );
            g2.fillOval(720, 105, 430, 430);

            g2.dispose();
        }
    }

    private class DashboardCard extends JPanel {

        private final String symbol;
        private final String title;
        private final String description;
        private boolean hover = false;

        DashboardCard(
                String symbol,
                String title,
                String description
        ) {
            this.symbol = symbol;
            this.title = title;
            this.description = description;

            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hover = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hover = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int w = getWidth();
            int h = getHeight();

            g2.setColor(new Color(0, 0, 0, 90));
            g2.fillRoundRect(
                    6, 7, w - 10, h - 10, 22, 22
            );

            g2.setColor(
                    hover
                            ? new Color(7, 55, 55, 246)
                            : new Color(5, 41, 45, 240)
            );
            g2.fillRoundRect(
                    1, 1, w - 8, h - 10, 20, 20
            );

            g2.setColor(
                    new Color(
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            hover ? 190 : 95
                    )
            );
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRoundRect(
                    1, 1, w - 8, h - 10, 20, 20
            );

            g2.setColor(
                    new Color(
                            DOCTOR_GREEN.getRed(),
                            DOCTOR_GREEN.getGreen(),
                            DOCTOR_GREEN.getBlue(),
                            30
                    )
            );
            g2.fillOval(20, 20, 48, 48);

            g2.setColor(DOCTOR_GREEN);
            g2.drawOval(20, 20, 48, 48);

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            symbol.length() > 1 ? 14 : 18
                    )
            );

            FontMetrics sfm = g2.getFontMetrics();
            g2.drawString(
                    symbol,
                    44 - sfm.stringWidth(symbol) / 2,
                    50
            );

            g2.setColor(TEXT_WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString(title, 20, 95);

            g2.setColor(TEXT_MUTED);
            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            drawWrapped(
                    g2,
                    description,
                    20,
                    116,
                    w - 48,
                    14
            );

            g2.setColor(DOCTOR_GREEN);
            g2.setFont(new Font("Arial", Font.BOLD, 9));
            g2.drawString("OPEN  >", 20, h - 18);

            g2.dispose();
            super.paintComponent(g);
        }

        private void drawWrapped(
                Graphics2D g2,
                String text,
                int x,
                int y,
                int maxWidth,
                int lineHeight
        ) {
            FontMetrics fm = g2.getFontMetrics();
            String[] words = text.split(" ");
            StringBuilder line = new StringBuilder();
            int currentY = y;

            for (String word : words) {
                String test = line.length() == 0
                        ? word
                        : line + " " + word;

                if (fm.stringWidth(test) > maxWidth
                        && line.length() > 0) {

                    g2.drawString(
                            line.toString(),
                            x,
                            currentY
                    );

                    currentY += lineHeight;
                    line = new StringBuilder(word);

                } else {
                    line = new StringBuilder(test);
                }
            }

            if (line.length() > 0) {
                g2.drawString(
                        line.toString(),
                        x,
                        currentY
                );
            }
        }
    }
}
