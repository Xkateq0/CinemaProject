import javax.swing.*;
import java.io.IOException;
import java.util.Map;

class CCashier extends CUser{
    private CManage<CReservation> reservationManager;
    public CCashier(String username, String password) {
        super(username, password);
    }
    @Override
    public void displayOptions() {
        System.out.println("1. Add a reservation");
    }


//    public void finalizeReservation(CShowing seans, Map<String, CTicket> ticketsMap) throws IOException {
//        if (ticketsMap.isEmpty()) {
//            throw new IllegalArgumentException("Nie wybrano żadnych biletów.");
//        }
//
//        // Tworzenie rezerwacji
//        CReservation reservation = new CReservation(seans.getId());
//
//        for (CTicket ticket : ticketsMap.values()) {
//            reservation.addTicket(ticket);
//        }
//
//        // Zapisanie rezerwacji do bazy danych
//        reservationManager.save(reservation);
//        reservationManager.close(); // Zapis do pliku
//    }

//    public void setTicket(CTicket ticket, int idTicket,TypeTicket typeTicket){
//        ticket.setTypeTicket(typeTicket);
//        ticket.setPriceByType();
//    }
//
//    public void setReservation(CReservation reservation, int idReservation , int idTicket,int idShowing, int idMovie, int idSeat){
//        reservation.setIdTicket(idTicket);
//        reservation.setIdShowing(idShowing);
//        reservation.setIdMovie(idMovie);
//        reservation.setIdSeat(idSeat);
//    }


}
