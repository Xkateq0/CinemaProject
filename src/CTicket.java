public class CTicket{
    private TypeTicket typeTicket;
    String seat;

    public CTicket(){}
    public CTicket(TypeTicket typeTicket) {
        this.typeTicket = typeTicket;
    }

    public double getPriceTicket() {
        return typeTicket.getPrice();
    }

    public TypeTicket getTypeTicket() {
        return typeTicket;
    }

    public void setTypeTicket(TypeTicket typeTicket) {
        this.typeTicket = typeTicket;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String serialize() {
        return typeTicket.name() + "," + seat;
    }

    public void deserialize(String data) {
        String[] fields = data.split(",");
        this.typeTicket = TypeTicket.valueOf(fields[0]);
        this.seat = fields[1];

    }
}


