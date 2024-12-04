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
        return getId() + "," + idTicket + "," + idShowing + "," + idMovie + "," + idSeat;
    }

    @Override
    public void deserialize(String data) {
        String[] fields = data.split(",");
        setId(Integer.parseInt(fields[0]));
        this.idTicket = Integer.parseInt(fields[1]);
        this.idShowing=Integer.parseInt(fields[2]);
        this.idMovie = Integer.parseInt(fields[3]);
        this.idSeat = Integer.parseInt(fields[4]);
    }
}
