public class CReservation {
    private int idReservation;
    private int idTicket;
    private int idShowing;
    private int idMovie;
    private int idSeat;

    public CReservation(int idReservation,int idTicket,int idShowing,int idMovie,int idSeat){
        this.idReservation=idReservation;
        this.idTicket=idTicket;
        this.idShowing=idShowing;
        this.idMovie=idMovie;
        this.idSeat=idSeat;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public int getIdShowing() {
        return idShowing;
    }

    public void setIdShowing(int idShowing) {
        this.idShowing = idShowing;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public int getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(int idSeat) {
        this.idSeat = idSeat;
    }
}
