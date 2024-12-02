abstract class CUser {
    protected String username;
    protected String password;

    public CUser(String username,String password){
        this.username=username;
        this.password=password;
    }

    public String getUsername(String username){
        return username;
    }

    public abstract void displayOptions();
}
