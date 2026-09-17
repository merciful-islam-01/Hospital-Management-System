import java.awt.*;
import javax.swing.*;

public class ImageTest extends JFrame {

    public ImageTest() {

        setTitle("Hospital Background Test");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon originalIcon =
                new ImageIcon("assets/hospital_portal_bg.png");

        if (originalIcon.getIconWidth() == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Image not found!\n"
                    + "Check: assets/hospital_portal_bg.png"
            );

            return;
        }

        Image image =
                originalIcon
                        .getImage()
                        .getScaledInstance(
                                1000,
                                600,
                                Image.SCALE_SMOOTH
                        );

        JLabel imageLabel =
                new JLabel(
                        new ImageIcon(image)
                );

        add(imageLabel);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ImageTest frame =
                    new ImageTest();

            frame.setVisible(true);
        });
    }
}