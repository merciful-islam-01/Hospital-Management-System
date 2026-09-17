import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminStaffDashboard extends JFrame {

    private final String username;

    private final Color ADMIN_PURPLE = new Color(180, 105, 255);
    private final Color PURPLE_DARK = new Color(82, 42, 122);
    private final Color TEXT_WHITE = new Color(240, 248, 245);
    private final Color TEXT_MUTED = new Color(170, 180, 180);

    private BufferedImage backgroundImage;

    public AdminStaffDashboard(String username) {
        this.username = username;

        setTitle("APU Medical Centre - Admin Staff Portal");
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

        AdminBackground root = new AdminBackground();
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
        cross.setBackground(new Color(247, 232, 255));
        cross.setForeground(PURPLE_DARK);
        cross.setFont(new Font("Arial", Font.BOLD, 27));
        cross.setPreferredSize(new Dimension(46, 46));
        cross.setMaximumSize(new Dimension(46, 46));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel hospital = new JLabel("APU MEDICAL CENTRE");
        hospital.setForeground(TEXT_WHITE);
        hospital.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel portal = new JLabel("ADMIN STAFF PORTAL");
        portal.setForeground(ADMIN_PURPLE);
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

        JLabel secure = new JLabel("●  ADMIN SESSION");
        secure.setForeground(ADMIN_PURPLE);
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
        sidebar.setBackground(new Color(22, 18, 28, 240));
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(new EmptyBorder(24, 18, 22, 18));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel workspace = new JLabel("ADMIN WORKSPACE");
        workspace.setForeground(ADMIN_PURPLE);
        workspace.setFont(new Font("Arial", Font.BOLD, 10));

        JLabel nav = new JLabel("Navigation");
        nav.setForeground(TEXT_WHITE);
        nav.setFont(new Font("Arial", Font.BOLD, 21));

        sidebar.add(workspace);
        sidebar.add(Box.createRigidArea(new Dimension(0, 4)));
        sidebar.add(nav);
        sidebar.add(Box.createRigidArea(new Dimension(0, 22)));

        JButton users = createMenuButton("Manage Users");
        JButton assign = createMenuButton("Assign Doctors");
        JButton assets = createMenuButton("Hospital Assets");
        JButton rates = createMenuButton("Rates & Insurance");

        users.addActionListener(e ->
                new ManageUsersFrame().setVisible(true));

        assign.addActionListener(e ->
                new AssignDoctorsFrame().setVisible(true));

        assets.addActionListener(e ->
                new HospitalAssetsFrame().setVisible(true));

        rates.addActionListener(e ->
                new RatesInsuranceFrame().setVisible(true));

        sidebar.add(users);
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));
        sidebar.add(assign);
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));
        sidebar.add(assets);
        sidebar.add(Box.createRigidArea(new Dimension(0, 8)));
        sidebar.add(rates);

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

        JLabel small = new JLabel("HOSPITAL ADMINISTRATION CENTRE");
        small.setForeground(ADMIN_PURPLE);
        small.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel title = new JLabel("Welcome, Admin Staff");
        title.setForeground(TEXT_WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 31));

        JLabel subtitle = new JLabel(
                "Manage hospital users, doctor assignments, facilities and service configuration."
        );
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));

        heading.add(small);
        heading.add(Box.createRigidArea(new Dimension(0, 4)));
        heading.add(title);
        heading.add(Box.createRigidArea(new Dimension(0, 5)));
        heading.add(subtitle);

        JPanel cards = new JPanel(new GridLayout(2, 2, 15, 15));
        cards.setOpaque(false);
        cards.setBorder(new EmptyBorder(24, 0, 0, 0));

        DashboardCard users = new DashboardCard(
                "U",
                "Manage Users",
                "Create, review, update and remove hospital user accounts across system roles."
        );

        DashboardCard assign = new DashboardCard(
                "D",
                "Assign Doctors",
                "Coordinate doctor-to-medical-manager assignments for clinical oversight."
        );

        DashboardCard assets = new DashboardCard(
                "A",
                "Hospital Assets",
                "Maintain hospital facilities, equipment and operational asset information."
        );

        DashboardCard rates = new DashboardCard(
                "R",
                "Rates & Insurance",
                "Configure consultation service rates and supported insurance networks."
        );

        users.addMouseListener(click(() ->
                new ManageUsersFrame().setVisible(true)));

        assign.addMouseListener(click(() ->
                new AssignDoctorsFrame().setVisible(true)));

        assets.addMouseListener(click(() ->
                new HospitalAssetsFrame().setVisible(true)));

        rates.addMouseListener(click(() ->
                new RatesInsuranceFrame().setVisible(true)));

        cards.add(users);
        cards.add(assign);
        cards.add(assets);
        cards.add(rates);

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
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setPreferredSize(new Dimension(210, 44));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(0, 16, 0, 10));
        button.setBackground(new Color(45, 34, 53));
        button.setForeground(TEXT_WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(62, 42, 74));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(45, 34, 53));
            }
        });

        return button;
    }

    private JButton createLogoutButton() {
        JButton button = new JButton("Logout");
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setPreferredSize(new Dimension(210, 42));
        button.setBackground(new Color(80, 32, 44));
        button.setForeground(new Color(255, 220, 225));
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createLineBorder(new Color(150, 60, 82))
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
        bottom.setBackground(new Color(22, 18, 27, 244));
        bottom.setBorder(new EmptyBorder(9, 25, 9, 25));

        JLabel status = new JLabel(
                "●  SYSTEM READY     |     HOSPITAL ADMINISTRATION WORKSPACE"
        );
        status.setForeground(ADMIN_PURPLE);
        status.setFont(new Font("Arial", Font.BOLD, 9));

        JLabel secure = new JLabel("SECURE ADMIN SESSION");
        secure.setForeground(TEXT_MUTED);
        secure.setFont(new Font("Arial", Font.PLAIN, 9));

        bottom.add(status, BorderLayout.WEST);
        bottom.add(secure, BorderLayout.EAST);

        return bottom;
    }

    private class AdminBackground extends JPanel {
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
            g2.fillRect(0, 0, getWidth(), 82);

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
            g2.fillRect(0, 80, getWidth(), 2);

            g2.setColor(
                    new Color(
                            ADMIN_PURPLE.getRed(),
                            ADMIN_PURPLE.getGreen(),
                            ADMIN_PURPLE.getBlue(),
                            11
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

            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRoundRect(
                    6,
                    7,
                    w - 10,
                    h - 10,
                    22,
                    22
            );

            g2.setColor(
                    hover
                            ? new Color(55, 38, 66, 248)
                            : new Color(39, 31, 46, 243)
            );

            g2.fillRoundRect(
                    1,
                    1,
                    w - 8,
                    h - 10,
                    20,
                    20
            );

            g2.setColor(
                    new Color(
                            ADMIN_PURPLE.getRed(),
                            ADMIN_PURPLE.getGreen(),
                            ADMIN_PURPLE.getBlue(),
                            hover ? 205 : 105
                    )
            );

            g2.setStroke(new BasicStroke(1.2f));

            g2.drawRoundRect(
                    1,
                    1,
                    w - 8,
                    h - 10,
                    20,
                    20
            );

            g2.setColor(
                    new Color(
                            ADMIN_PURPLE.getRed(),
                            ADMIN_PURPLE.getGreen(),
                            ADMIN_PURPLE.getBlue(),
                            32
                    )
            );
            g2.fillOval(22, 22, 50, 50);

            g2.setColor(ADMIN_PURPLE);
            g2.drawOval(22, 22, 50, 50);

            g2.setFont(new Font("Arial", Font.BOLD, 18));

            FontMetrics symbolMetrics = g2.getFontMetrics();

            g2.drawString(
                    symbol,
                    47 - symbolMetrics.stringWidth(symbol) / 2,
                    54
            );

            g2.setColor(TEXT_WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString(title, 22, 104);

            g2.setColor(TEXT_MUTED);
            g2.setFont(new Font("Arial", Font.PLAIN, 11));

            drawWrapped(
                    g2,
                    description,
                    22,
                    128,
                    w - 52,
                    15
            );

            g2.setColor(ADMIN_PURPLE);
            g2.setFont(new Font("Arial", Font.BOLD, 9));
            g2.drawString("OPEN  >", 22, h - 20);

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
                String test =
                        line.length() == 0
                                ? word
                                : line + " " + word;

                if (
                        fm.stringWidth(test) > maxWidth
                                && line.length() > 0
                ) {
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
