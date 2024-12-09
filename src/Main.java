import javax.swing.*;
import java.io.IOException;
import java.time.*;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        CManage<CShowing> showingManager = new CManage<>(CShowing.class);


        List<CShowing> allShows = showingManager.getAll();

        // Wyświetlamy dane o wszystkich pokazach
        for (CShowing showing : allShows) {
            System.out.println(showing);  // Wywołuje metodę toString() dla każdego obiektu
        }


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}