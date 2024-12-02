class CAdministrator extends CUser {
    public CAdministrator(String username, String password) {
        super(username, password);
    }
    @Override
    public void displayOptions() {
        System.out.println("1. Add a movie");
        System.out.println("2. Set movie data");
        System.out.println("3. Set movie times");
        System.out.println("4. Assign halls to a movie");
    }

}
