abstract public class CBase {
    private int id=-1;

    public int getId() {
        return id;
    }
    protected void setId(int id) {
        this.id = id;
    }

    public abstract String serialize();

    public abstract void deserialize(String data);
}
