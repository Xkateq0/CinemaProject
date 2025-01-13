import javax.swing.*;
import java.io.IOException;
import java.util.Map;

class CCashier extends CUser{
    public CCashier(String username, String password) {
        super(username, password);
    }
    @Override
    public void displayOptions() {
        System.out.println("1. Add a reservation");
    }


//    public void finalizeReservation(CShowing seans, Map<String, CTicket> ticketsMap, JButton[] seatButtons) {
//        // Tworzymy obiekt CReservation dla danego seansu
//        CReservation reservation = new CReservation(seans.getId());
//
//        // Dodajemy wszystkie wybrane bilety do rezerwacji
//        for (Map.Entry<String, CTicket> entry : ticketsMap.entrySet()) {
//            CTicket ticket = entry.getValue();
//            reservation.addTicket(ticket);
//
//            String seat = ticket.getSeat();
//            for (int i = 0; i < seans.getSeats().length; i++) {
//                if (seatButtons[i].getText().equals(seat)) {
//                    seans.getSeats()[i].setOccupied(true);
//                    break;
//                }
//            }
//        }
//
//        // Zapisujemy rezerwację w bazie danych
//        CManage<CReservation> reservationManager = new CManage<>(CReservation.class);
//        reservationManager.save(reservation);
//
//        CManage<CShowing> showingManager = new CManage<>(CShowing.class);
//        showingManager.save(seans);
//
//        try {
//            reservationManager.close(); // Zapisujemy zmiany w pliku
//            showingManager.close();
//            new resFrame(reservation).setVisible(true);
//        } catch (IOException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas zapisywania rezerwacji.", "Błąd", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Informacja o sukcesie
//        JOptionPane.showMessageDialog(null, "Rezerwacja została pomyślnie zapisana!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
//    }
}
