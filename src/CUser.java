abstract class CUser implements IUser {
    protected String username;
    protected String password;

    public CUser(String username,String password){
        this.username=username;
        this.password=password;
    }

    public CUser(){};

    @Override
    public String getPassword(){
        return password;
    }


    public abstract void displayOptions();
}
