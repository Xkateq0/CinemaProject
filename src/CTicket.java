public class CTicket extends CBase {
    private double priceTicket;
    private TypeTicket typeTicket;

    public CTicket(int idTicket, String nameTicket, double priceTicket, TypeTicket typeTicket) {
        this.typeTicket = typeTicket;
        this.priceTicket = setPriceByType();
    }

    public double getPriceTicket() {
        return priceTicket;
    }

    public TypeTicket getTypeTicket() {
        return typeTicket;
    }

    public void setTypeTicket(TypeTicket typeTicket) {
        this.typeTicket = typeTicket;
    }

    public double setPriceByType() {

        return switch (typeTicket) {
            case STANDARD -> 26.0;
            case REDUCED -> 22.0;
            case STUDENT -> 21.0;
            case SENIOR -> 22.0;
        };
    }
    @Override
    public String serialize() {
        return getId() + "," + typeTicket + "," + priceTicket;
    }

    @Override
    public void deserialize(String data) {
        String[] fields = data.split(",");
        setId(Integer.parseInt(fields[0]));
        this.typeTicket = TypeTicket.valueOf(fields[1]);
        this.priceTicket = setPriceByType();
    }
}


