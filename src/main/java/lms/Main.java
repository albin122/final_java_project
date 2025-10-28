package lms;

import javax.swing.SwingUtilities;
import lms.ui.RoleSelectionFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RoleSelectionFrame().setVisible(true));
    }
}


