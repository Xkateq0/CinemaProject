import javax.swing.*;
import java.io.IOException;
import java.time.*;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        CManage<CShowing> showingManager = new CManage<>(CShowing.class);
        CManage<CMovie>showingMovie=new CManage<>(CMovie.class);
        CManage<CReservation> reservationManager = new CManage<>(CReservation.class);

        List<CReservation> allReservations= reservationManager.getAll();
        List<CShowing> allShows = showingManager.getAll();
        List<CMovie>allMovie =showingMovie.getAll();


        // Wyświetlamy dane o wszystkich pokazach
        for (CShowing showing : allShows) {
            System.out.println(showing);  // Wywołuje metodę toString() dla każdego obiektu
        }
        for (CMovie showing : allMovie) {
            System.out.println(showing);  // Wywołuje metodę toString() dla każdego obiektu
        }
        for (CReservation showing : allReservations) {
            System.out.println(showing);  // Wywołuje metodę toString() dla każdego obiektu
        }



        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}