public class CTicket {
    private int idTicket;
    private double priceTicket;
    private TypeTicket typeTicket;

    public CTicket(int idTicket, String nameTicket, double priceTicket, TypeTicket typeTicket) {
        this.idTicket = idTicket;
        this.typeTicket = typeTicket;
        this.priceTicket = setPriceByType();
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
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
        switch (typeTicket) {
            case STANDARD:
                return 26.0;
            case REDUCED:
                return 22.0;
            case STUDENT:
                return 21.0;
            case SENIOR:
                return 22.0;
            default:
                return 0.0;
        }
    }

}


