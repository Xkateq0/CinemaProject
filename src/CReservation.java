public class CReservation extends CBase{
    private int idTicket;
    private int idShowing;
    private int idMovie;
    private int idSeat;

    public CReservation(int idReservation,int idTicket,int idShowing,int idMovie,int idSeat){
        this.idTicket=idTicket;
        this.idShowing=idShowing;
        this.idMovie=idMovie;
        this.idSeat=idSeat;
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

    @Override
    public String serialize() {
        return getId() + ";" + idTicket + ";" + idShowing + ";" + idMovie + ";" + idSeat;
    }

    @Override
    public void deserialize (String data) {
        String[] parts = data.split(";");
        setId(Integer.parseInt(parts[0]));
        idTicket = Integer.parseInt(parts[1]);
        idShowing=Integer.parseInt(parts[2]);
        idMovie = Integer.parseInt(parts[3]);
        idSeat = Integer.parseInt(parts[4]);
    }
}
