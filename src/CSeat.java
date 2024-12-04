public class CSeat {
    private int idSeat;
    private boolean isOccupied;

    public CSeat(int idSeat, boolean isOccupied) {
        this.idSeat = idSeat;
        this.isOccupied = isOccupied;
    }

    public int getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(int idSeat) {
        this.idSeat = idSeat;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public void setSeat(int idSeat,boolean occupied){
        setIdSeat(idSeat);
        setOccupied(occupied);
    }
}
