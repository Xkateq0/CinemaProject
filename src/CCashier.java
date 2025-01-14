import javax.swing.*;
import java.io.IOException;
import java.util.Map;

class CCashier extends CUser{
    public CCashier(String username, String password) {
        super(username, password);
    }
    @Override
    public void displayOptions() {
        System.out.println("1. Add a reservation");
    }

    public CCashier(){};

    @Override
    public String getName() {
        return "Kasjer";
    }
}
