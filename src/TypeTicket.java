public enum TypeTicket {
    STANDARD(26.0),
    REDUCED(22.0),
    STUDENT(21.0),
    SENIOR(22.0);

    private final double price;

    TypeTicket(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
