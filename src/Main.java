import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            RoleSelectionFrame roleSelectionFrame =
                    new RoleSelectionFrame();

            roleSelectionFrame.setVisible(true);

        });
    }
}