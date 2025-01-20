import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public static void main(String args[]) {

    Runtime.getRuntime().addShutdownHook(new Thread(Logger::close));

    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Login().setVisible(true);
        }
    });
}