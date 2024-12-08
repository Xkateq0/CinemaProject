public class CSeat extends CBase {
    private boolean isOccupied;

    public CSeat(int idSeat, boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
    @Override
    public String serialize() {
        return getId() + ";" + isOccupied;
    }

    public void deserialize(String data) {
        String[] fields = data.split(";");
        setId(Integer.parseInt(fields[0]));
        this.isOccupied = Boolean.parseBoolean(fields[1]);
    }
}
