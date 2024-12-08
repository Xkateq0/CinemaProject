class CCashier extends CUser{
    public CCashier(String username, String password) {
        super(username, password);
    }
    @Override
    public void displayOptions() {
        System.out.println("1. Add a reservation");
    }

    public void setTicket(CTicket ticket, int idTicket,TypeTicket typeTicket){
        ticket.setTypeTicket(typeTicket);
        ticket.setPriceByType();
    }

    public void setReservation(CReservation reservation, int idReservation , int idTicket,int idShowing, int idMovie, int idSeat){
        reservation.setIdTicket(idTicket);
        reservation.setIdShowing(idShowing);
        reservation.setIdMovie(idMovie);
        reservation.setIdSeat(idSeat);
    }


}
