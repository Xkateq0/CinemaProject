import javax.swing.*;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        CManage<CMovie> movieManager= new CManage<> (CMovie.class);
        List<CMovie> allMovies =movieManager.getAll();
        if (allMovies.isEmpty()) {
            System.out.println("No movies found.");
        } else {
            for (CMovie movie : allMovies) {
                System.out.println(movie); // Wydrukuj film
            }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}}